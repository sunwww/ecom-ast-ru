package ru.ecom.diary.ejb.service.protocol;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.diary.ejb.domain.Diary;
import ru.ecom.diary.ejb.domain.protocol.parameter.Parameter;
import ru.ecom.diary.ejb.domain.protocol.parameter.ParameterByForm;
import ru.ecom.diary.ejb.domain.protocol.parameter.ParameterGroup;
import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.diary.ejb.form.DiaryForm;
import ru.ecom.diary.ejb.service.protocol.tree.CheckNode;
import ru.ecom.diary.ejb.service.protocol.tree.CheckNodeByGroup;
import ru.ecom.diary.ejb.service.protocol.tree.CheckNodeByParameter;
import ru.ecom.ejb.services.entityform.EntityFormException;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.medcase.MedService;

/**
 * User: STkacheva
 * Протокол
 */
@Stateless
@Remote(IDiaryService.class)
public class DiaryServiceBean implements IDiaryService {
 public List<DiaryForm> findProtocol(long aSloId) {
        List<Diary> list = theManager.createQuery(
                "from Diary where sloId = :sloId"
        ).setParameter("sloId",aSloId).setMaxResults(50).getResultList();
        List<DiaryForm> ret = new LinkedList<DiaryForm>();
        for (Diary diary : list) {
            try {
                ret.add(theEntityFormService.loadForm(DiaryForm.class,  diary)) ;
            } catch (EntityFormException e) {
                throw new IllegalStateException(e) ;
            }
        }
        return ret ;
    }
	 public List<Object[]> loadParameterTableByMedService(long aTemplateId) {
		List<Object[]> list = theManager.createNativeQuery("select p.parameter_id,par.name||' ('||case when par.type='1' then 'Числовой' when par.type='4' then 'Числовой с плавающей точкой зн.'||par.cntDecimal when par.type='2' then 'Пользовательский справочник: '||coalesce(vd.name,'НЕ УКАЗАН!!!!!!!') when par.type='3' then 'Текстовое поле' when par.type='5' then 'Текстовое поле с ограничением' else 'неизвестный' end||') - '||coalesce(vmu.name,'') from ParameterByForm p left join Parameter par on par.id=p.parameter_id left join userDomain vd on vd.id=par.valueDomain_id left join vocMeasureUnit vmu on vmu.id=par.measureUnit_id where p.template_id=:temp order by p.position").setParameter("temp", aTemplateId).getResultList() ;
		return list ;
	 }

	public CheckNode loadParametersByMedService(long aTemplateId) {
		TreeSet<Long> parametersSet = new TreeSet<Long>() ;
		TemplateProtocol template = theManager.find(TemplateProtocol.class, aTemplateId) ;
		List<Object> list = theManager.createNativeQuery("select p.parameter_id from ParameterByForm p where p.template_id=:temp order by p.position").setParameter("temp", aTemplateId).getResultList() ;
		for (Object param: list) {
			if (param!=null) parametersSet.add(ConvertSql.parseLong(param)) ;
		}
		
		List<Object[]> rootGroups = findRootGroups() ;
		
		CheckNodeByGroup rootNode = new CheckNodeByGroup("g"+0, "/",false);
		//System.out.println("-G-"+rootNode.getName()) ;
		for (Object[] group : rootGroups) {
			Long id =ConvertSql.parseLong(group[0]) ;
			//System.out.println("-ROOT--"+id+"-"+group[1]) ;
			CheckNodeByGroup node = new CheckNodeByGroup("g"+id,""+group[1],false) ;
			rootNode.getChilds().add(node);
			addGroups(id,node,parametersSet) ;
			addParameters(id,node,parametersSet);
		}
		
		return rootNode;
	}
	private List<Object[]> findRootGroups() {
		return theManager.createNativeQuery("select id,name from ParameterGroup where parent_id is null order by name") .setMaxResults(100).getResultList();
	}
	private void addGroups (Long aGroup, CheckNode aNode, TreeSet<Long> aParameters) {
		List<Object[]> listChild = theManager.createNativeQuery("select id, name from ParameterGroup where parent_id = :parent order by name").setParameter("parent", aGroup).getResultList() ;
		for (Object[] group: listChild) {
			Long id = ConvertSql.parseLong(group[0]) ;
			CheckNodeByGroup node = new CheckNodeByGroup(
					"g"+group[0] , new StringBuilder().append(group[1]).toString(),false
					) ;
			aNode.getChilds().add(node) ;
			//System.out.println("-GR-"+aNode.getId()+"-"+node.getName()) ;
			addGroups(id,node ,aParameters) ;
			addParameters(id,node,aParameters);
		}
	}
	private void addParameters(Long aGroup, CheckNode aNode, TreeSet<Long> aParameters) {
		List<Object[]> listChild = theManager.createNativeQuery("select par.id,par.name||' ('||case when par.type='1' then 'Числовой' when par.type='4' then 'Числовой с плавающей точкой зн.'||par.cntDecimal when par.type='2' then 'Пользовательский справочник: '||coalesce(vd.name,'НЕ УКАЗАН!!!!!!!') when par.type='3' then 'Текстовое поле' when par.type='5' then 'Текстовое поле с ограничением' else 'неизвестный' end||') - '||coalesce(vmu.name,''),par.type as partype from Parameter par left join userDomain vd on vd.id=par.valueDomain_id left join vocMeasureUnit vmu on vmu.id=par.measureUnit_id where par.group_id = :parent order by par.name").setParameter("parent", aGroup).getResultList() ;
		
		for (Object[] param: listChild) {
			CheckNodeByParameter node = new CheckNodeByParameter("p"+param[0],
					new StringBuilder().append(param[1]).toString()
					,aParameters.contains(ConvertSql.parseLong(param[0])),ConvertSql.parseLong(param[2]));
			//System.out.println("-PAR-"+aNode.getId()+"-"+node.getName()) ;
			aNode.getChilds().add(node) ;
		}
	}
	
	public void saveParametersByTemplateProtocol(long aProtocolId, long[] aAdded, long[] aRemoved) {
		TemplateProtocol protocol = theManager.find(TemplateProtocol.class, aProtocolId) ;
		//theManager.refresh(protocol) ;
		//System.out.println("ADDING");
		int i=0;
		for (long idParam:aAdded) {
			i++ ;
			//System.out.println("adding id "+idParam);
			//Parameter param = theManager.find(Parameter.class, idParam) ;
			//if(param!=null &&!params.contains(param)) {
			//	params.add(param) ;
			//}
			List<ParameterByForm> list = theManager.createQuery("from ParameterByForm where template_id='"+aProtocolId+"' and parameter_id='"+idParam+"'").getResultList() ;
			if (list.isEmpty()) {
				Parameter param = theManager.find(Parameter.class, idParam) ;
				ParameterByForm frm = new ParameterByForm() ;
				frm.setParameter(param) ;
				frm.setTemplate(protocol) ;
				frm.setPosition(Long.valueOf(i)) ;
				theManager.persist(frm) ;
			} else {
				ParameterByForm frm = list.get(0) ;
				frm.setPosition(Long.valueOf(i)) ;
				theManager.persist(frm) ;
				if (list.size()>1) {
					for (int j=1;j<list.size();j++) {
						ParameterByForm fr = list.get(i) ;
						theManager.remove(fr) ;
					}
				}
				
			}
			//System.out.println("OK");
		}
		//System.out.println("REMOVED");
		for(long idParam:aRemoved) {
			//System.out.println("removed id "+idParam);
			theManager.createNativeQuery("delete from ParameterByForm where template_id='"+aProtocolId+"' and parameter_id='"+idParam+"'").executeUpdate() ;
			//System.out.println("OK");
		}
		//System.out.println("Size params="+params.size());
		//theManager.refresh(protocol) ;
		//System.out.println("Size params after saving="+protocol.getParameters().size());
		
	}
	@EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext EntityManager theManager ;
}
