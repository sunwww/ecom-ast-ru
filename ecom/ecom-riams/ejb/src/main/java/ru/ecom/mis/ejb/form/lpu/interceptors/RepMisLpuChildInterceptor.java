package ru.ecom.mis.ejb.form.lpu.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.lpu.rep.RepMisLpuChild;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;

public class RepMisLpuChildInterceptor implements IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, EntityManager aManager) {
		EntityManager manager = aManager ;
		manager.createQuery("delete from RepMisLpuChild").executeUpdate();
		List<MisLpu> list = manager.createQuery("from MisLpu").getResultList();
		HashSet<String> hash = new HashSet<>() ;
		for(MisLpu lpu : list) {
//			RepMisLpuChild rep = new RepMisLpuChild() ;
//			rep.setLpu(lpu);
//			rep.setChildLpu(lpu) ;
//			rep.setTrailName(createTrailName(lpu));
//			aManager.persist(rep);
			//listChilds(lpu, manager, hash);
			if(lpu!=null){
				if (lpu.getSubdivisions() == null || lpu.getSubdivisions().isEmpty()) {  //zav
				// только последний уровень
				insert(lpu, aManager, hash);
				}
			}
		}
	}
	
	private void listChilds(MisLpu aLpu, EntityManager aManager,HashSet<String> hash) {
		if(aLpu.getSubdivisions().isEmpty()) {
			// только последний уровень
			insert(aLpu, aManager, hash);
		}
	}

	
	private void insert(MisLpu aMain, MisLpu aChild, EntityManager aManager, HashSet<String> hash) {
		RepMisLpuChild rep = new RepMisLpuChild() ;
		
		rep.setLpu(aMain);
		rep.setChildLpu(aChild) ;
		String key = aMain+"_"+aChild ;
		if(!hash.contains(key)) {
			rep.setTrailName(aMain.getName()!=null ?
					aMain.getName().substring(0,aMain.getName().length()>100 ?
							100 : aMain.getName().length()-1)
					: "  -  "+(aChild.getName()!=null ? aChild.getName().substring(0,aChild.getName().length()>100 ? 100 : aChild.getName().length()-1):""));
			rep.setLpuLevel(getLevel(aMain));
			aManager.persist(rep);
			hash.add(key);
		} 
	}
	
	private void insert(MisLpu aLpu, EntityManager aManager,HashSet<String> hash) {
		if(aLpu==null) return ;
		insert(aLpu, aLpu, aManager, hash);
		MisLpu lpu = aLpu.getParent() ;
		while(lpu!=null) {

			insert(lpu, aLpu, aManager, hash);
			
			lpu = lpu.getParent();
		}
		if(aLpu.getParent()!=null) {
			insert(aLpu.getParent(), aManager, hash);
		}
	}
	

	private int getLevel(MisLpu aLpu) {
		int ret = 0 ;
		MisLpu lpu = aLpu ;
		while(lpu!=null) {
			ret++ ;
			lpu = lpu.getParent() ;
		}
		return ret ;
	}
	private String createTrailName(MisLpu aLpu) {
		StringBuilder sb = new StringBuilder() ;
		MisLpu lpu = aLpu ;
		while (lpu!=null) {
			sb.append(lpu.getName()) ;
			sb.append(" / ") ;
			lpu = lpu.getParent();
		}
		return sb.toString() ;
	}

}
