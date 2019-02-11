package ru.ecom.mis.ejb.form.workcalendar.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.form.workcalendar.WorkCalendarHospitalBedForm;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class WorkCalendarHospitalBedCreate  implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	WorkCalendarHospitalBedForm form = (WorkCalendarHospitalBedForm)aForm ;
        Date date = new Date();
        String username = aContext.getSessionContext().getCallerPrincipal().toString() ;
    	form.setCreateUsername(username);
    	form.setCreateDate(DateFormat.formatToDate(date));
        
        EntityManager manager = aContext.getEntityManager();
        //MedCase parent = manager.find(MedCase.class, aParentId) ;
        List<Object[]> list= manager.createNativeQuery("select mkb.code,d.name,mkb.id,mc.serviceStream_id,mc.patient_id,to_char(mc.dateFinish,'dd.mm.yyyy') as datefin,mc.moveToAnotherLPU_id "
        		+" ,(select max(slo.ownerFunction_Id) from medcase slo where slo.parent_id=mc.id and slo.datefinish is not null and slo.dtype='DepartmentMedCase') as wf " +
				" , coalesce(p.phone,'') as f9_phone"
        		+" from MedCase mc "
				+" left join patient p on p.id=mc.patient_id"
        		+" left join Diagnosis d on mc.id=d.medCase_id "
        		+" left join VocIdc10 mkb on mkb.id=d.idc10_id "
        		+" where mc.id=:parentId order by d.id").setParameter("parentId",aParentId).getResultList() ;
        StringBuilder res = new StringBuilder() ; 
        boolean isFirst = true ;
        for (Object[] obj:list) {
        	if (isFirst) {
        		form.setIdc10(ConvertSql.parseLong(obj[2])) ;
        		form.setServiceStream(ConvertSql.parseLong(obj[3])) ;
        		form.setPatient(ConvertSql.parseLong(obj[4])) ;
        		form.setDateFrom(ConvertSql.parseString(obj[5],true)) ;
        		form.setOrderLpu(ConvertSql.parseLong(obj[6])) ;
        		form.setWorkFunction(ConvertSql.parseLong(obj[7])) ;
        		form.setPhone(obj[8].toString());
        		isFirst=false;
        	}
        	res.append(obj[0]).append(". ").append(obj[1]).append("\n") ;
        }
        form.setDiagnosis(res.toString()) ;
        if (form.getWorkFunction()==null){
	    	list = manager.createNativeQuery("select wf.id as wfid,su.id as suid " 
	    			+ " from SecUser su " 
	    			+ " left join WorkFunction wf on wf.secuser_id=su.id " 
	    			+ " left join Worker w on w.id=wf.worker_id " 
	    			+ " left join MisLpu ml on w.lpu_id=ml.id " 
	    			+ " left join VocLpuFunction vlf on vlf.id=ml.lpuFunction_id " 
	    			+ " where su.login = :login and wf.id is not null") 
	    		.setParameter("login", username).setMaxResults(1).getResultList() ;
	    	if (!list.isEmpty()) {
	    		 form.setWorkFunction(ConvertSql.parseLong(list.get(0)[0])) ;
	    	}
        }
    }
}
