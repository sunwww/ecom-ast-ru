package ru.ecom.mis.ejb.form.expert;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.util.format.DateFormat;

import java.util.Date;
import java.util.List;

public class QualityEstimationPrepareCreateInterceptor  implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	QualityEstimationForm form = (QualityEstimationForm)aForm ;
    	String username = aContext.getSessionContext().getCallerPrincipal().toString() ;
    	form.setCreateUsername(username);
        Date date = new Date();
        form.setCreateDate(DateFormat.formatToDate(date));
        //SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        //form.setEntranceTime(timeFormat.format(date));
        if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/EditExpert")) {
            //form.addDisabledField("statCardNumber");
        	List<WorkFunction> listwf =  aContext.getEntityManager().createQuery("from WorkFunction where secUser.login = :login")
				.setParameter("login", username).getResultList() ;
	    	if (listwf.size()==0) {
	    		throw new IllegalArgumentException(
	    				"Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между рабочей функцией и пользователем (WorkFunction и SecUser)"
	    				);
	    	}
	    	WorkFunction wf = listwf.get(0) ;
	    	form.setExpert(wf.getId()) ; 
        }
    }
}