package ru.ecom.jboss.live;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.security.SecurityAssociation;

public class LiveTransactionInterceptor implements Interceptor, Serializable {

	private final static Logger LOG = Logger
			.getLogger(LiveTransactionInterceptor.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public String getName() {
		return this.getClass().getName() ;
	}

	public Object invoke(Invocation aInvocation) throws Throwable {
		if (CAN_DEBUG) LOG.debug("invoke: aInvocation = " + aInvocation); 
		try {
			LiveTransactionContext.open();
			return aInvocation.invokeNext() ;
		} finally {
			LiveTransactionContext.close();
//			try {
//				SecurityAssociation.clear() ;
//			} catch (Exception e) {
//				LOG.error("SecurityAssociation.clear() ;", e);
//			}
		}
	}

	
}
