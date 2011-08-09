package ru.ecom.jboss.live;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

public class LiveTransactionContext {
	
	private final static Logger LOG = Logger
			.getLogger(LiveTransactionContext.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
			
	private static ThreadLocal<LiveTransactionContext> THREAD = new ThreadLocal<LiveTransactionContext>() ;
	
	public static void open() {
		if(CAN_DEBUG) LOG.debug("Opening LiveTransaction Context "+THREAD+" ...") ;
		LiveTransactionContext ctx = THREAD.get() ;
		if(ctx!=null) {
			ctx.theCount++ ;
			//LOG.error("Ошибка использование LiveTransactionContext. Контекст уже открыт.") ;
			//throw new IllegalStateException("Ошибка использование LiveTransactionContext. Контекст уже открыт.") ;
		} else {
			ctx = new LiveTransactionContext() ;
			THREAD.set(ctx);
		}
		if(CAN_DEBUG) LOG.debug("  LiveTransactionContext = "+ctx) ;
	}
	public static LiveTransactionContext get() {
		LiveTransactionContext ctx = THREAD.get() ;
		return ctx ;
	}

	public static void close() {
		if(CAN_DEBUG) LOG.debug("Closing LiveTransaction Context "+THREAD+" ...") ;
		LiveTransactionContext ctx = THREAD.get() ;
		if(CAN_DEBUG) LOG.debug("  LiveTransactionContext = "+ctx+"   "+ctx.theCount) ;
		
		if(ctx!=null) {
			if(ctx.theCount==0) {
				try {
					ctx.theLiveTransaction = null ;
					if(ctx.theEntityManager!=null) {
						ctx.theEntityManager.close() ;
					}
				} finally {
					THREAD.remove() ;
				}
			} else {
				ctx.theCount-- ;
			}
		} else {
			throw new IllegalStateException("Нет контекста") ;
		}
	}
	
	
	/** Текущая транзакция */
	public Object getLiveTransaction() {
		return theLiveTransaction;
	}

	// LiveTransaction EntityBean
	public void setLiveTransaction(Object aLiveTransaction) {
		if(theLiveTransaction!=null) throw new IllegalStateException("LiveTransaction уже установлен") ;
		theLiveTransaction = aLiveTransaction;
	}

	/** EntityManager */
	public EntityManager getEntityManager() {
		return theEntityManager;
	}

	public void setEntityManager(EntityManager aEntityManager) {
		if(theEntityManager!=null) throw new IllegalStateException("EntityManager уже установлен") ;
		theEntityManager = aEntityManager;
	}

	/** EntityManager */
	private EntityManager theEntityManager;
	/** Текущая транзакция */
	private Object theLiveTransaction;
	private int theCount = 0 ;
}
