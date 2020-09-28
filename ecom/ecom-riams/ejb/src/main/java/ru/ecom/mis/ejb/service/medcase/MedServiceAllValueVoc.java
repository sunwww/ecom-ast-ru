package ru.ecom.mis.ejb.service.medcase;

import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MedServiceAllValueVoc  implements IAllValue {

	public void destroy() {
		
	}
    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, AllValueContext aContext) throws VocServiceException {
    	String ret = null;
        if (aId != null) {
            for (VocValue value : listAll(aContext)) {
                if (aId.equals(value.getId())) {
                    ret = value.getName();
                }
            }
        }
        return ret;
    }
	
	//@SuppressWarnings("unchecked")
	public Collection<VocValue> listAll(AllValueContext aContext) {
		String addSql  ;
		SimpleDateFormat FORMAT_2 = new SimpleDateFormat("dd.MM.yyyy") ;
		if (aContext!=null && aContext.getVocAdditional()!=null 
				&& aContext.getVocAdditional().getParentId()!=null && !aContext.getVocAdditional().getParentId().equals("")) {
			addSql = aContext.getVocAdditional().getParentId() ;
			try {
				java.util.Date date = DateFormat.parseDate(addSql) ;
				SimpleDateFormat FORMAT_1 = new SimpleDateFormat("yyyy-MM-dd");
				addSql = FORMAT_1.format(date) ;
				addSql = " and dtype='MedServiceGroup'" +
						" and (startDate is null or startDate <= to_date('" +
						addSql + "','yyyy-mm-dd') ) and (finishDate is null or finishDate >=to_date('" +
						addSql + "','yyyy-mm-dd'))";
				//LOG.info(addSql) ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			addSql = " and dtype='MedServiceGroup'"+
			" and (startDate is null or startDate <= CURRENT_DATE ) and (finishDate is null or finishDate >=CURRENT_DATE)";
		}

		//LOG.info("SQL medService"+sql) ;
		List<MedService> medServs = aContext.getEntityManager().createQuery("from MedService where parent_id is null " + addSql + " order by name").getResultList() ;
		List<VocValue> ret = new ArrayList<>() ;
		
		//InterceptorContext context = new InterceptorContext(aContext.getEntityManager(), aContext.getSessionContext()) ;
		
		for(MedService medServ : medServs) {
			 try {
				 //theSecurity.checkParent("View", lpu.getId(), context) ;
				 //aContext.getEntityManager().refresh(lpu);
				 add(ret,addSql, medServ, "",aContext.getEntityManager(),FORMAT_2) ;
			 } catch (IllegalStateException e) {
				 //LOG.error(e) ;
			 }
		}
		//LOG.info("end list") ;
		return ret;
	}
	
	private static void add(List<VocValue> aValues,String aAddSql, MedService aMedService, String aAppend
			, EntityManager aManager, SimpleDateFormat aFormat) {
		StringBuilder name = new StringBuilder() ;
		name.append(aAppend).append(aMedService.getName()) ;
		/*
		name.append(" (") ;
		if (aMedService.getStartDate()!=null) {
			name.append(aFormat.format(aMedService.getStartDate())) ;
		} else {
			name.append("нет даты начала") ;
		}
		name.append("-") ;
		if (aMedService.getFinishDate()!=null) {
			name.append(aFormat.format(aMedService.getFinishDate())) ;
		} else {
			name.append("нет даты окончания") ;
		}
		name.append(")") ;*/
		aManager.refresh(aMedService);
		aValues.add(new VocValue(String.valueOf(aMedService.getId()), name.toString())) ;
		StringBuilder sql =new StringBuilder() ;
		sql.append("from MedService where parent_id='").append(aMedService.getId()).append("' ").append(aAddSql).append(" order by name") ;
		List<MedService> medServs =aManager.createQuery(sql.toString()).getResultList() ;
		//LOG.info("SQL add: "+sql) ;
		for(MedService medService : medServs) {
			add(aValues, aAddSql, medService, ".    "+aAppend,aManager,aFormat) ;
			//if (CAN_DEBUG) {
			//	LOG.info("medService=    "+aAppend+medService.getId()+"--"+medService.getName()) ;
			//}
		}
	}

}