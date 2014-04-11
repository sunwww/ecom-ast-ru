package ru.ecom.mis.ejb.form.workcalendar.interceptor;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.form.workcalendar.WorkCalendarHospitalBedByVisitForm;
import ru.ecom.mis.ejb.form.workcalendar.WorkCalendarHospitalBedForm;
import ru.nuzmsh.util.format.DateFormat;

public class WorkCalendarHospitalBedCreate  implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	
    	WorkCalendarHospitalBedForm form = (WorkCalendarHospitalBedForm)aForm ;
        Date date = new Date();
        String username = aContext.getSessionContext().getCallerPrincipal().toString() ;
    	form.setCreateUsername(username);
    	form.setCreateDate(DateFormat.formatToDate(date));
        /*if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/ShortEnter")) {
        	form.setDateStart(DateFormat.formatToDate(date));
        	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            form.setEntranceTime(timeFormat.format(date));
        } else {
        	
        }*/
        EntityManager manager = aContext.getEntityManager();
        //MedCase parent = manager.find(MedCase.class, aParentId) ;
        List<Object[]> list= manager.createNativeQuery("select mkb.code,d.name,mkb.id,mc.serviceStream_id,mc.patient_id from MedCase mc"
        		+" left join Diagnosis d on mc.id=d.medCase_id "
        		+" left join VocIdc10 mkb on mkb.id=d.idc10_id"
        		+" where mc.id='"+aParentId+"' order by d.id").getResultList() ;
        StringBuilder res = new StringBuilder() ; 
        boolean isFirst = true ;
        for (Object[] obj:list) {
        	if (isFirst) {
        		form.setIdc10(ConvertSql.parseLong(obj[2])) ;
        		form.setServiceStream(ConvertSql.parseLong(obj[3])) ;
        		form.setPatient(ConvertSql.parseLong(obj[4])) ;
        	}
        	res.append(obj[0]).append(". ").append(obj[1]).append("\n") ;
        }
        form.setDiagnosis(res.toString()) ;
        list.clear() ;
    	list = manager.createNativeQuery("select wf.id as wfid,su.id as suid,case when vlf.code in ('7','3') then w.lpu_id else null end as wlpuid " 
    			+ " from SecUser su " 
    			+ " left join WorkFunction wf on wf.secuser_id=su.id " 
    			+ " left join Worker w on w.id=wf.worker_id " 
    			+ " left join MisLpu ml on w.lpu_id=ml.id " 
    			+ " left join VocLpuFunction vlf on vlf.id=ml.lpuFunction_id " 
    			+ " where su.login = :login and wf.id is not null") 
    		.setParameter("login", username).setMaxResults(1).getResultList() ;
    	if (list.size()>0) {
    		form.setWorkFunction(ConvertSql.parseLong(list.get(0)[0])) ;
    		form.setDepartment(ConvertSql.parseLong(list.get(0)[2])) ;
    	} else {
    		
    	}
    	
    }


}
