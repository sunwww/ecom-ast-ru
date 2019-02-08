package ru.ecom.mis.ejb.form.birth.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.form.birth.NeonatalNewBornForm;
import ru.ecom.mis.ejb.form.birth.NewBornForm;
import ru.nuzmsh.util.format.DateFormat;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewBornPreCreateInterceptor implements IParentFormInterceptor {
	
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	StringBuilder sql = new StringBuilder() ;
    	NewBornForm form = (NewBornForm) aForm ; 
    	if (form instanceof NeonatalNewBornForm) {
    		List <Object[]> list = aContext.getEntityManager().createNativeQuery("select id from childBirth where medcase_id=:parent and pangsStartDate is not null and pangsStartTime is not null").setParameter("parent", aParentId).getResultList();
    		if (!list.isEmpty()) {
    			throw new IllegalStateException("В отделении уже заполнена информация по родам, новорожденные созданы. Создание новорожденного невозможно!!") ;
    		}
    		
    		list = aContext.getEntityManager().createNativeQuery("select mc.id from medcase mc left join mislpu ml on ml.id=mc.department_id" +
    				" where mc.id=:parent and ml.isMaternityWard='1'").setParameter("parent", aParentId).getResultList();
    		if (list.isEmpty()) {
    			throw new IllegalStateException("Новорожденный может быть создан только в родовом отделении!") ;
    		}
    		list = aContext.getEntityManager().createNativeQuery("select nb.id from newBorn nb left join vocnewborn vnb on vnb.id=nb.child_id  " +
    				"where medcase_id=:parent and (vnb.ispolycarpous is null or vnb.ispolycarpous='0')").setParameter("parent", aParentId).getResultList();
    		if (!list.isEmpty()) {
    			throw new IllegalStateException("Уже создан новорожденный с признаком 'одноплодный ребенок'") ;
    		}
        	sql.append("select p.lastname,p.id from MedCase mc")
    		.append(" left join Patient as p on p.id = mc.patient_id")
			.append(" where mc.id=:childBirth") ;
        	//list = aContext.getEntityManager().createNativeQuery(sql.toString()).setParameter("childBirth", aParentId).getResultList() ;
	    	
    	} else {
        	sql.append("select p.lastname,cb.birthFinishDate,cb.birthFinishTime,p.id from ChildBirth as cb")
    		.append(" left join MedCase as mc on mc.id = cb.medCase_id")
    		.append(" left join Patient as p on p.id = mc.patient_id")
			.append(" where cb.id=:childBirth") ;
        	List<Object[]> list = aContext.getEntityManager().createNativeQuery(sql.toString())
    		.setParameter("childBirth", aParentId).getResultList() ;
	    	if (!list.isEmpty()) {
	    		Object[] row = list.get(0) ;
	    		Date day = (Date)row[1]  ;
	    		Time time = (Time)row[2] ;
	    		if (day!=null) form.setBirthDate(DateFormat.formatToDate(day)) ;
	    		if (time!=null) form.setBirthTime(DateFormat.formatToTime(time)) ;
	    	}
    	}
    	form.setUsername(aContext.getSessionContext().getCallerPrincipal().toString());
        Date date = new Date();
        form.setCreateDate(DateFormat.formatToDate(date));
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        form.setCreateTime(timeFormat.format(date));
    	
    }

}