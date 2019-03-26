package ru.ecom.jboss;

import org.apache.log4j.Logger;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.security.SecurityAssociation;

import java.io.Serializable;

public class SecurityAssociationCleaningInterceptor implements Interceptor,
		Serializable {

	private static final ThreadLocal<Integer> THREAD_COUNT = new ThreadLocal<Integer>();

	private static final Logger LOG = Logger.getLogger(SecurityAssociationCleaningInterceptor.class);


	public String getName() {
		return this.getClass().getName();
	}

	public Object invoke(Invocation aInvocation) throws Throwable {
		Integer count = THREAD_COUNT.get();
		try {
			if (count == null) {
				count = 0;
			} else {
				count++;
			}
			THREAD_COUNT.set(count);
			return aInvocation.invokeNext();
		} finally {
			count = THREAD_COUNT.get();
			if (count != null && count == 0) {
				THREAD_COUNT.set(null);
				try {
					SecurityAssociation.clear();
				} catch (Exception e) {
					LOG.error("SecurityAssociation.clear() ;", e);
				}
			} else if (count!=null) {
				count--;
				THREAD_COUNT.set(count);
			}
		}
	}
}
