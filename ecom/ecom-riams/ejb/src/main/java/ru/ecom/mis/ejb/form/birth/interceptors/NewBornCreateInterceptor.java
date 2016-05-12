package ru.ecom.mis.ejb.form.birth.interceptors;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.form.birth.NewBornForm;

public class NewBornCreateInterceptor implements IFormInterceptor {

	 private final static Logger LOG = Logger.getLogger(NewBornCreateInterceptor.class);
	 private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		try {
			NewBornForm form = (NewBornForm)aForm;
		
		} catch (Exception e) {
			
		}

	}
}