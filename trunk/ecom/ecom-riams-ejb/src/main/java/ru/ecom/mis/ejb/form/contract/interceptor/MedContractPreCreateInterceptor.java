package ru.ecom.mis.ejb.form.contract.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.contract.MedContract;
import ru.ecom.mis.ejb.form.contract.MedContractForm;
import ru.nuzmsh.util.format.DateFormat;

public class MedContractPreCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	
    	MedContractForm form = (MedContractForm)aForm ;
    	if (aParentId!=null) {
    		MedContract parent = aContext.getEntityManager().find(MedContract.class, aParentId) ;
    		if (parent!=null) {
        		if (parent.getPriceList()!=null) {
        			form.setPriceList(parent.getPriceList().getId()) ;
        		}
        		if (parent.getRulesProcessing()!=null) {
        			form.setRulesProcessing(parent.getRulesProcessing().getId()) ;
        		}
    		}
    		
    	}
    	form.setCreateUsername(aContext.getSessionContext().getCallerPrincipal().toString());
        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        form.setCreateDate(DateFormat.formatToDate(date));
        form.setCreateTime(timeFormat.format(date));
        form.setDateFrom(DateFormat.formatToDate(date));
    }

}