package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.birth.NewBorn;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.NewBornMedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.NewBornMedCaseForm;
import ru.nuzmsh.util.format.DateFormat;

public class NewBornMedCaseCreateInterceptor implements IParentFormInterceptor{
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	NewBornMedCaseForm form = (NewBornMedCaseForm) aForm ;
    	NewBorn parent = manager.find(NewBorn.class, aParentId) ;
    	
    	if (parent!=null) {
    		System.out.println("Check working create");
    		if (parent.getLiveBorn()==null || parent.getLiveBorn().equals(Boolean.FALSE)) {
                throw new IllegalStateException("Нельзя заводить случай для мертворожденного") ;
               
    		}
    		List<Object> listDep = manager
    			.createNativeQuery("select id from MedCase where newBorn_id = :parentId and DTYPE='NewBornMedCase' ")
    			.setParameter("parentId", aParentId)
    			.getResultList() ;
    		if (listDep.isEmpty()) {
    			prepareForCreationFirstSlo(form, parent) ;
    		} else {
    			prepareForCreationNextSlo(form,parent,manager) ;
    		}
    		if (parent.getChildBirth().getMedCase().getLpu()!=null) form.setLpu(parent.getChildBirth().getMedCase().getLpu().getId());
    		form.setTypeCreate() ;
    	} else {
    		throw new IllegalStateException("Невозможно добавить случай лечения. Сначала надо определить  случай стационарного лечения (ССЛ)") ;
    	}
    }
    
    /**
     * Новый первый случай лечения в отделении
     * */
    private void prepareForCreationFirstSlo(NewBornMedCaseForm aForm, NewBorn aNewBorn ) {
        aForm.setDateStart(DateFormat.formatToDate(aNewBorn.getBirthDate()));
        aForm.setEntranceTime(DateFormat.formatToTime(aNewBorn.getBirthTime()));
        aForm.setPatient(aNewBorn.getPatient().getId());
        aForm.setlpuAndDate(new StringBuilder().append(aForm.getDepartment()).append(":")
        		.append(aForm.getDateStart())
        		.toString()) ;
    }
    
    /**
     * Продолжение предыдущего случая лечения в отделении
     */
    private void prepareForCreationNextSlo(NewBornMedCaseForm aForm, NewBorn aNewBorn, EntityManager aManager) {
    	aForm.setPatient(aNewBorn.getPatient().getId());
		List<NewBornMedCase> list = aManager.createQuery(
                "from MedCase where newBorn_id = :parentId and DTYPE='NewBornMedCase'"
        ).setParameter("parentId",aNewBorn.getId()).setMaxResults(50).getResultList();
		long maxTime =0;
		Time maxHours=null ;
		MisLpu maxDep = null ;
		Long maxSlo = null ;
    	for (NewBornMedCase slo:list){
    		if (slo.getDateFinish()!=null) throw new IllegalStateException("Уже есть лечение в отделении по новорожденному с выпиской.") ;
    		Date date = slo.getTransferDate() ;
            if (date==null ){
            	throw new IllegalStateException("Нет лечения в отделении по новорожденному с переводом") ;
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
            throw new IllegalStateException("Нет лечения в отделении по новорожденному с переводом") ;
        }
    }
}