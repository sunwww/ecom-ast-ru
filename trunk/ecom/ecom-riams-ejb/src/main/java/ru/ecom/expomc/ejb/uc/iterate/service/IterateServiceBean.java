package ru.ecom.expomc.ejb.uc.iterate.service;

import java.util.Iterator;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.monitor.MonitorId;
import ru.ecom.ejb.services.util.QueryIteratorUtil;
import ru.ecom.ejb.util.RhinoHelper;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.expomc.ejb.uc.iterate.domain.Iterate;

@Stateless
@Remote(IIterateService.class)
public class IterateServiceBean implements IIterateService {

	private final static Logger LOG = Logger
			.getLogger(IterateServiceBean.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public void executeIterate(MonitorId aMonitorId, long aIterateId) {
		IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Запуск перебора "+aIterateId) ;
		try {
			
			Iterate iterate = theManager.find(Iterate.class, aIterateId) ;
			long countAll = calcCount(iterate) ;
			monitor = theMonitorService.startMonitor(aMonitorId
					, "Выполнение Перебора: "+iterate.getName()
					, countAll) ;
			
			// выполнение запроса
			monitor.setText("Выполнение запроса: "+iterate.getHqlString());
			Iterator it = QueryIteratorUtil.iterate(theManager.createQuery(iterate.getHqlString()));
			long count = 0 ;
			
			// создание JavaScript context
			Context jsContext = Context.enter() ;
			jsContext.setOptimizationLevel(9);
			Scriptable scope = jsContext.initStandardObjects();
			theRhinoHelper.loadLibraryFiles(jsContext, scope);
			scope.put("manager", scope, theManager);
			scope.put("injection", scope, theInjection) ;

			jsContext.evaluateString(scope, iterate.getInitScript(), "init", 0, null);
			
			theRhinoHelper.addSource(jsContext, scope, iterate.getIterateScript(), "iterate" );
			while( it.hasNext() && !monitor.isCancelled() ) {
				count++ ;
				monitor.advice(1);
				Object entity = it.next();
				scope.put("entity", scope, entity);
				
				Object fObj = scope.get("onIterate", scope);
				Function f = (Function)fObj;
				f.call(jsContext, scope, scope, null);
				
				if(count%100==0) {
					monitor.setText("Перебор "+count +" из "+countAll) ;
				}
				theManager.flush();
				theManager.clear();
			}
			if (CAN_DEBUG)
				LOG.debug("fill: count = " + count); 

			monitor.finish(String.valueOf(iterate.getId()));
		} catch (Exception e) {
			monitor.error("Ошибка: "+e.getMessage(), e) ;
			throw new IllegalStateException(e);
		} finally {
			Context.exit() ;
		}	
	}
	
	
	private long calcCount(Iterate iterate) {
		Long count = (Long) theManager.createQuery("select count(*) "+iterate.getHqlString()).getSingleResult();
		return count ;
	}
	
	private @EJB ILocalMonitorService theMonitorService ;
	private @PersistenceContext EntityManager theManager ;
	private final EjbInjection theInjection = EjbInjection.getInstance() ;
	private final RhinoHelper  theRhinoHelper = RhinoHelper.getInstance() ;
}
