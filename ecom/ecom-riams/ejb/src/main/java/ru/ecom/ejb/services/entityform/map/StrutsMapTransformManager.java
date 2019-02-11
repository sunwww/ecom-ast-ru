package ru.ecom.ejb.services.entityform.map;

import org.apache.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;

public class StrutsMapTransformManager {

	private static StrutsMapTransformManager INSTANCE = null ;
	private static final StrutsMapTransform ENTITY_TRANSFORM = new StrutsMapTransform("java/lang/Long") ; 
	
	private static final Logger LOG = Logger
			.getLogger(StrutsMapTransformManager.class);
	private static final boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	private StrutsMapTransformManager() {
		put(String.class, "java/lang/String");
		put(Long.class, "java/lang/Long");
		put(long.class, "java/lang/Long");
		put(Integer.class, "java/lang/Integer");
		put(int.class, "java/lang/Integer");
		put(Short.class, "java/lang/Short");
		put(short.class, "java/lang/Short");
		put(Float.class, "java/lang/Float");
		put(float.class, "java/lang/Float");
		put(Double.class, "java/lang/Double");
		put(double.class, "java/lang/Double");
		put(Boolean.class, "java/lang/Boolean");
		put(boolean.class, "java/lang/Boolean");
		put(BigDecimal.class, "java/lang/String");
		
		theHash.put(java.sql.Date.class
				, new StrutsMapTransform("java/lang/String", "java/lang/String"
						, new String[]{"Lru/nuzmsh/forms/validator/validators/DateString;"
						, "Lru/nuzmsh/forms/validator/transforms/DoDateString;"
				}));

		
		theHash.put(java.sql.Time.class
				, new StrutsMapTransform("java/lang/String", "java/lang/String"
						, new String[]{"Lru/nuzmsh/forms/validator/validators/TimeString;"
						, "Lru/nuzmsh/forms/validator/transforms/DoTimeString;"
				}));
	}
	
	private void put(Class aClass, String aClassname) {
		theHash.put(aClass, new StrutsMapTransform(aClassname));
	}
	
	public static StrutsMapTransformManager getInstance() {
		if(INSTANCE==null) {
			INSTANCE = new StrutsMapTransformManager() ;
		}
		return INSTANCE ;
	}
	
	public StrutsMapTransform getTransform(Method aMethod) {
		// FIXME убрать множественный выход
		Class type = aMethod.getReturnType() ;
		StrutsMapTransform ret = theHash.get(type) ;
		
		// FIXME ввод даты и времени в одном поле Timestamp. Пока только дата
		if(type.equals(Timestamp.class)) {
			LOG.warn("FIXME ввод даты и времени в одном поле Timestamp. Пока только дата.: "+aMethod);
			return null ;
		}
		if(ret==null) {
			// пропускаем коллекции
			if(aMethod.isAnnotationPresent(ManyToMany.class)
					|| aMethod.isAnnotationPresent(OneToMany.class)) {
				return null ;
			} else {
				if(aMethod.isAnnotationPresent(OneToOne.class)
						|| type.isAnnotationPresent(Entity.class)) {
					return ENTITY_TRANSFORM ;
				}
			}
		}
		if(ret==null) {
			throw new IllegalArgumentException("Нет трансформации для метода "+aMethod) ;
		}
		return ret ;
	}
	
	public static void destroy() {
		INSTANCE = null ;
	}
	

	private final HashMap<Class, StrutsMapTransform> theHash = new HashMap<>() ;
}
