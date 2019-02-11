package ru.ecom.mis.ejb.form.contract.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.contract.MedContract;
import ru.ecom.mis.ejb.form.contract.ContractAccountForm;
import ru.nuzmsh.util.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ContractAccountPreCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	
    	ContractAccountForm form = (ContractAccountForm)aForm ;
    	if (aParentId!=null) {
    		MedContract parent = aContext.getEntityManager().find(MedContract.class, aParentId) ;
    		if (parent!=null) {
    			if (parent.getDateFrom()!=null) form.setDateFrom(DateFormat.formatToDate(parent.getDateFrom()) );
    			if (parent.getDateTo()!=null) form.setDateTo(DateFormat.formatToDate(parent.getDateTo()) );
    			if (parent.getPriceList()!=null) form.setPriceList(parent.getPriceList().getId()) ;
    			if (parent.getCustomer()!=null) form.setServedPerson(parent.getCustomer().getId()) ;
    			
    			
    		}
    		
    	}
    	form.setCreateUsername(aContext.getSessionContext().getCallerPrincipal().toString());
        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        form.setCreateDate(DateFormat.formatToDate(date));
        form.setCreateTime(timeFormat.format(date));
    }

}