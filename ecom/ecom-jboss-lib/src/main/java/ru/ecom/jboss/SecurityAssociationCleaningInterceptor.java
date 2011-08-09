package ru.ecom.jboss;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.security.SecurityAssociation;

public class SecurityAssociationCleaningInterceptor implements Interceptor,
		Serializable {

	private final static ThreadLocal<Integer> THREAD_COUNT = new ThreadLocal<Integer>();

	private final static Logger LOG = Logger
			.getLogger(SecurityAssociationCleaningInterceptor.class);

	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();

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
			if (CAN_DEBUG)
				LOG.debug("CLEAN: increment " + count + "...");
			return aInvocation.invokeNext();
		} finally {
			count = THREAD_COUNT.get();
			if (count != null && count == 0) {
				THREAD_COUNT.set(null);
				try {
					if (CAN_DEBUG)
						LOG.debug("CLEANED ");
					SecurityAssociation.clear();
				} catch (Exception e) {
					LOG.error("SecurityAssociation.clear() ;", e);
				}
			} else {
				count--;
				if (CAN_DEBUG)
					LOG.debug("CLEAN: decrement" + count + "...");
				THREAD_COUNT.set(count);
			}
		}
	}

}
