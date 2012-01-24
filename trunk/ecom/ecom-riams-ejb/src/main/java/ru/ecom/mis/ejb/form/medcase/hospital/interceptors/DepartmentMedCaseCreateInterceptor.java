package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.DepartmentMedCaseForm;
import ru.nuzmsh.util.format.DateFormat;


/**
 * Проверка закрыт ли случай стационарного лечения
 * если да, то есть ли права на открытие случая ????
 * @author stkacheva
 */
public class DepartmentMedCaseCreateInterceptor implements IParentFormInterceptor{
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	DepartmentMedCaseForm form = (DepartmentMedCaseForm) aForm ;
    	HospitalMedCase parentSSL = manager.find(HospitalMedCase.class, aParentId) ;
    	if (parentSSL!=null) {
    		if (parentSSL.getDateFinish()!=null && parentSSL.getDischargeTime()!=null) {
    			throw new IllegalStateException("Нельзя добавить случай лечения в отделении (СЛО) в закрытый случай стационарного лечения (ССЛ) !!!") ;
    		}
    		if (parentSSL.getEmergency()!=null && parentSSL.getEmergency()==Boolean.TRUE) {
    			form.setEmergency(Boolean.TRUE) ;
    		}
    		System.out.println("Check working create");
    		if (parentSSL.getDeniedHospitalizating()!=null) {
                throw new IllegalStateException("При отказе в госпитализации нельзя заводить случай лечения в отделении") ;
               
    		}
    		Object listDep = manager
    			.createNativeQuery("select count(*) from MedCase where parent_id = :parentId and DTYPE='DepartmentMedCase' ")
    			.setParameter("parentId", aParentId)
    			.getSingleResult() ;
    		if (listDep!=null && ConvertSql.parseLong(listDep).equals(Long.valueOf(0))) {
    			prepareForCreationFirstSlo(form, parentSSL) ;
    		} else {
    			prepareForCreationNextSlo(form,parentSSL,manager) ;
    		}
    		if (parentSSL.getLpu()!=null) form.setLpu(parentSSL.getLpu().getId());
    		form.setTypeCreate() ;
    	} else {
    		throw new IllegalStateException("Невозможно добавить случай лечения. Сначала надо определить  случай стационарного лечения (ССЛ)") ;
    	}
    }
    
    /**
     * Новый первый случай лечения в отделении
     * */
    private void prepareForCreationFirstSlo(DepartmentMedCaseForm aForm, HospitalMedCase aMedCase ) {
        aForm.setDateStart(DateFormat.formatToDate(aMedCase.getDateStart()));
        aForm.setEntranceTime(DateFormat.formatToTime(aMedCase.getEntranceTime()));
        aForm.setPatient(aMedCase.getPatient().getId());
        if (aMedCase.getDepartment()!=null) {
        	aForm.setDepartment(aMedCase.getDepartment().getId());
        	aForm.addDisabledField("department");
        }
        
        aForm.setlpuAndDate(new StringBuilder().append(aForm.getDepartment()).append(":")
        		.append(aForm.getDateStart())
        		.toString()) ;
        aForm.addDisabledField("dateStart");
        aForm.addDisabledField("entranceTime");
    }
    
    /**
     * Продолжение предыдущего случая лечения в отделении
     */
    private void prepareForCreationNextSlo(DepartmentMedCaseForm aForm, HospitalMedCase aMedCaseParent, EntityManager aManager) {
    	aForm.setPatient(aMedCaseParent.getPatient().getId());
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select max(ms1.dateFinish) as maxdatefinish")
    		.append(",ms.id, to_char(ms.transferDate,'dd.mm.yyyy') as mstransferdate,cast(ms.transferTime as varchar(5)) as mstransfertime,ms.transferDepartment_id as mstransferdepartment,ms.lpu_id as mslpu")
    		.append(" from MedCase as ms ")
    		.append(" left join MedCase as ms1 on ms1.parent_id=ms.parent_id and ms1.dtype='DepartmentMedCase'")
    		.append(" left join MedCase as ms2 on ms2.prevMedCase_id=ms.id and ms2.dtype='DepartmentMedCase'")
    		.append(" where ms.parent_id =:parentId and ms.DTYPE='DepartmentMedCase' ")
    		.append(" group by ms.id,ms.transferDate,ms.transferTime,ms.transferDepartment_id,ms.lpu_id,ms2.id")
    		.append(" having ms2.id is null ")
    		.append(" order by ms.transferDate desc,ms.transferTime desc");
    	//sql.append("select max(ms.dateFinish),ms.id,ms.transferDate,ms.transferTime,ms.transferDepartment_id,ms.lpu_id")
    	//		.append(" from MedCase as ms where ms.parent_id = :parentId and ms.DTYPE='DepartmentMedCase'") 
    	//		.append(" and (select count(*) from MedCase as ms1 where ms1.prevMedCase_id=ms.id and DTYPE='DepartmentMedCase')=0")
		//		.append(" order by ms.transferDate desc,ms.transferTime desc") ;
    	System.out.println(sql) ;
    	List<Object[]> list = aManager.createNativeQuery(sql.toString())
    			.setParameter("parentId", aMedCaseParent.getId()).setMaxResults(3).getResultList() ;
    	if (list.size()>0) {
    		Object[] obj = list.get(0) ;
    		if (obj[0]!=null) {
    			throw new IllegalStateException("Уже есть лечение в отделении с выпиской.") ;
    		}
    		if (obj[2]!=null) {
    			aForm.setDateStart((String)obj[2]);
                aForm.setEntranceTime((String)obj[3]);
                aForm.setPrevMedCase(ConvertSql.parseLong(obj[1]));
                aForm.setDepartment(ConvertSql.parseLong(obj[4]));
    		} else {
    			throw new IllegalStateException("Нет лечения в отделении с переводом") ;
    		}
    	} else {
    		throw new IllegalStateException("Нет случая лечения в отделении оформленного для перевода") ;
    	}
    	/*
		List<DepartmentMedCase> list = aManager.createQuery(
                "from MedCase where transferDate parent_id = :parentId and DTYPE='DepartmentMedCase'"
        ).setParameter("parentId",aMedCaseParent.getId()).setMaxResults(50).getResultList();
		long maxTime =0;
		Time maxHours=null ;
		MisLpu maxDep = null ;
		Long maxSlo = null ;
    	for (DepartmentMedCase slo:list){
    		if (slo.getDateFinish()!=null) throw new IllegalStateException("Уже есть лечение в отделении с выпиской.") ;
    		Date date = slo.getTransferDate() ;
            if (date==null ){
            	throw new IllegalStateException("Нет лечения в отделении с переводом") ;
            }            
            if(date!=null && date.getTime()>maxTime) {
                maxTime = date.getTime() ;
                maxHours = slo.getTransferTime() ;
                maxDep = slo.getTransferDepartment() ;
                maxSlo = slo.getId() ;
            } 

    	}
        if(maxTime!=0) {
            aForm.setDateStart(DateFormat.formatToDate(new Date(maxTime)));
            aForm.setEntranceTime(DateFormat.formatToTime(maxHours));
            aForm.setPrevMedCase(maxSlo);
            if(maxDep!=null) {
                aForm.setDepartment(maxDep.getId());
            }
        } else {
            throw new IllegalStateException("Нет лечения в отделении с переводом") ;
        }
        */
    }
    /*
    private void prepareForCreationNextSlo(SloEditForm aForm, Collection<StacSLO> aList) {
        long maxTime = 0 ;
        String maxHours = null ;
        VocStacOTD maxDep = null ;

        for (StacSLO slo : aList) {
            if(slo.getDischargeDate()!=null) throw new IllegalStateException("Уже есть лечение в отделении с выпиской.") ;
            Date date = slo.getTransferDate() ;
            if(date!=null && date.getTime()>maxTime) {
                maxTime = date.getTime() ;
                maxHours = slo.getTransferTime() ;
                maxDep = slo.getTransferDepartment() ;
            }
        }

        if(maxTime!=0) {
            aForm.setAdmissionDate(DateFormat.formatToDate(new Date(maxTime)));
            aForm.setAdmissionTime(maxHours);
            if(maxDep!=null) {
                aForm.setAdmissionDepartmentPk(maxDep.getId());
            }
        } else {
            throw new IllegalStateException("Нет лечения в отделении с переводом") ;
        }
    }
     */
}