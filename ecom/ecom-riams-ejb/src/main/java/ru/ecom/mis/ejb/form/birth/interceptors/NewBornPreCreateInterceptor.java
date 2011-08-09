package ru.ecom.mis.ejb.form.birth.interceptors;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.form.birth.NeonatalNewBornForm;
import ru.ecom.mis.ejb.form.birth.NewBornForm;
import ru.nuzmsh.util.format.DateFormat;

public class NewBornPreCreateInterceptor implements IParentFormInterceptor {
	
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	StringBuilder sql = new StringBuilder() ;
    	NewBornForm form = (NewBornForm) aForm ; 
    	if (form instanceof NeonatalNewBornForm) {
        	sql.append("select p.lastname,p.id from MedCase mc")
    		.append(" left join Patient as p on p.id = mc.patient_id")
			.append(" where mc.id=:childBirth") ;
        	List<Object[]> list = aContext.getEntityManager().createNativeQuery(sql.toString())
    		.setParameter("childBirth", aParentId).getResultList() ;
	    	if (list.size()>0) {
	    		Object[] row = list.get(0) ;
	    		form.setLastname((String)row[0]) ;
	    		//Date day = (Date)row[1]  ;
	    		//Time time = (Time)row[2] ;
	    		//if (day!=null) form.setBirthDate(DateFormat.formatToDate(day)) ;
	    		//if (time!=null) form.setBirthTime(DateFormat.formatToTime(time)) ;
	    	} else {
	    		form.setLastname("Х") ;
	    	}
    	} else {
        	sql.append("select p.lastname,cb.birthFinishDate,cb.birthFinishTime,p.id from ChildBirth as cb")
    		.append(" left join MedCase as mc on mc.id = cb.medCase_id")
    		.append(" left join Patient as p on p.id = mc.patient_id")
			.append(" where cb.id=:childBirth") ;
        	List<Object[]> list = aContext.getEntityManager().createNativeQuery(sql.toString())
    		.setParameter("childBirth", aParentId).getResultList() ;
	    	if (list.size()>0) {
	    		Object[] row = list.get(0) ;
	    		form.setLastname((String)row[0]) ;
	    		Date day = (Date)row[1]  ;
	    		Time time = (Time)row[2] ;
	    		if (day!=null) form.setBirthDate(DateFormat.formatToDate(day)) ;
	    		if (time!=null) form.setBirthTime(DateFormat.formatToTime(time)) ;
	    		
	    	} else {
	    		form.setLastname("Х") ;
	    	}
    	}
    	form.setMiddlename("Х");
    	form.setUsername(aContext.getSessionContext().getCallerPrincipal().toString());
        Date date = new Date();
        form.setCreateDate(DateFormat.formatToDate(date));
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        form.setCreateTime(timeFormat.format(date));
    	
    }

}