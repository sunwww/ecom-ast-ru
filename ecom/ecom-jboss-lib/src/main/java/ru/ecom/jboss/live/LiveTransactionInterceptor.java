package ru.ecom.jboss.live;

import org.apache.log4j.Logger;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.Invocation;

import java.io.Serializable;

public class LiveTransactionInterceptor implements Interceptor, Serializable {

	private static final Logger LOG = Logger
			.getLogger(LiveTransactionInterceptor.class);
	private static final boolean CAN_DEBUG = LOG.isDebugEnabled();
	
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
