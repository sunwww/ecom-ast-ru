package ru.ecom.mis.ejb.service.archive;

import ru.ecom.mis.ejb.domain.archive.ArchiveCase;
import ru.ecom.mis.ejb.domain.medcase.StatisticStub;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Stateless
@Local(IArchiveService.class)
@Remote(IArchiveService.class)
public class ArchiveServiceBean implements IArchiveService{

	public String createArchiveCase(String aStatCardIds, Long aWorkFunctionId, String aUsername) {
		String[] sId = aStatCardIds.split(",");
		StringBuilder ret = new StringBuilder();
		if (sId.length>0) {
			if (aWorkFunctionId==null) {
				aWorkFunctionId = Long.valueOf(
						theManager.createNativeQuery("select wf.id from workfunction wf left join secuser su on su.id=wf.secuser_id where su.login = '"+aUsername+"'").getSingleResult().toString());
			}
			for (int i=0;i<sId.length;i++) {
				Long aStatCardId = Long.valueOf(sId[i]);
				if (aStatCardId==null||aStatCardId==0) {return null;}
				StatisticStub ss = theManager.find(StatisticStub.class, aStatCardId);
				if (ss.getMedCase().getDateFinish()== null) {
					ret.append("СЛС с номером стат. карты №"+ss.getCode()+" не законцен").append("#");
				} else if (ss.getArchiveCase()!=null) {
					ret.append("История болезни находится в архиве!"+ss.getArchiveCase()).append("#");
				}else {
					ArchiveCase ac = new ArchiveCase();
					ac.setStatCard(ss.getId());
					ac.setPatient(ss.getMedCase().getPatient().getId());
					ac.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
					ac.setCreateTime(new java.sql.Time(new java.util.Date().getTime()));
					ac.setCreateUsername(aUsername);
					ac.setWorkFunction(aWorkFunctionId);
					theManager.persist(ac);
					ss.setArchiveCase(ac.getId());
					theManager.persist(ss);
				
				}
			}
			
		}
		
		// TODO Auto-generated method stub
		return ret.length()>0?ret.toString():"";
	}
	private @PersistenceContext EntityManager theManager;
}
