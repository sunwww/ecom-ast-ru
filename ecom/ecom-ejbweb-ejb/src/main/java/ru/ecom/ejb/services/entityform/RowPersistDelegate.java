package ru.ecom.ejb.services.entityform;

import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.annotation.RowPersist;
import ru.ecom.ejb.services.entityform.annotation.RowPersistMatch;
import ru.ecom.ejb.services.entityform.annotation.RowPersistProperty;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.nuzmsh.util.PropertyUtil;

public class RowPersistDelegate {

	private final static Logger LOG = Logger
			.getLogger(RowPersistDelegate.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public boolean isRowPersistEnable(Class aFormClass) {
		return aFormClass.isAnnotationPresent(RowPersist.class);
	}
	
	public void load(IEntityForm aForm, EntityManager aManager, Object aParentEntity) {
		Class<IEntityForm> clazz = (Class<IEntityForm>) aForm.getClass() ;
		// some checks
		if(!isRowPersistEnable(clazz)) {
			throw new IllegalStateException("У класса "+clazz+" нет аннотации RowPersist") ;
		}
		RowPersist rowPersist = clazz.getAnnotation(RowPersist.class);
		// перебор всех методов с RowPersistProperty
		for(Method getter : clazz.getMethods()) {
			if(getter.isAnnotationPresent(RowPersistProperty.class)) {
				RowPersistProperty rowPersistProperty = getter.getAnnotation(RowPersistProperty.class);
				// поиск row с условием
				Object row = findRow(rowPersistProperty, rowPersist, aParentEntity, aManager);
				
				if(row!=null) {
					try {
						Object value = PropertyUtil.getPropertyValue(row, rowPersist.defaultProperty());
						PropertyUtil.setPropertyValue(aForm, PropertyUtil.getPropertyName(getter), value);
					} catch (Exception e) {
						throw new IllegalStateException("ОШибка установки значения для "+getter + " у строки "+row,e) ;
					}
				}
			}
		}
		
		
	}

	public void create(IEntityForm aForm, EntityManager aManager, Object aParentEntity) {
		save(aForm, aManager, aParentEntity);
	}
	
	public void save(IEntityForm aForm, EntityManager aManager, Object aParentEntity) {
		Class<IEntityForm> clazz = (Class<IEntityForm>) aForm.getClass() ;
		// some checks
		if(!isRowPersistEnable(clazz)) {
			throw new IllegalStateException("У класса "+clazz+" нет аннотации RowPersist") ;
		}
		RowPersist rowPersist = clazz.getAnnotation(RowPersist.class);
		
		// перебор всех методов с RowPersistProperty
		for(Method getter : clazz.getMethods()) {
			if(getter.isAnnotationPresent(RowPersistProperty.class)) {
				RowPersistProperty rowPersistProperty = getter.getAnnotation(RowPersistProperty.class);
				// поиск row с условием
				Object row = findRow(rowPersistProperty, rowPersist, aParentEntity, aManager);
				if(row==null) {
					// создание новой строки
					row = createRow(getter, rowPersistProperty, rowPersist, aForm, aManager, aParentEntity) ;
					aManager.persist(row);
				} else {
					// использование существующей
				}
				// установка значения
				// само значение
				try {
					Object value = getter.invoke(aForm) ;
					PropertyUtil.setPropertyValue(row, rowPersist.defaultProperty(), value);
				} catch (Exception e) {
					throw new IllegalStateException("ОШибка установки значения для "+getter + " у строки "+row,e) ;
				}
			}
		}
	}
	
	private Object createRow(Method aMethod, RowPersistProperty aRowPersistProperty
			, RowPersist aRowPersist
			, IEntityForm aForm
			, EntityManager aManager
			, Object aParentEntity) {
		try {
			Object row = aRowPersist.rowEntityClass().newInstance() ;
//			// само значение
//			Object value = PropertyUtil.getPropertyValue(aForm, aRowPersist.defaultProperty());
//			PropertyUtil.setPropertyValue(row, PropertyUtil.getPropertyName(aMethod), value);
			
			// parent
			PropertyUtil.setPropertyValue(row, aRowPersist.parentProperty(), aParentEntity);
			
			// matches
			for(RowPersistMatch match : aRowPersistProperty.matches()) {
				// find справочник
				Class  vocClass = getVocClass(aRowPersist.rowEntityClass(), match.property()) ;
				Object voc = findVoc(vocClass, match.matchProperty(), match.matchValue(), aManager) ;
				PropertyUtil.setPropertyValue(row, match.property(), voc);
			}
			
			return row ;
		} catch (Exception e) {
			throw new IllegalStateException("Ошибка создание строки "+aRowPersist.rowEntityClass(),e) ;
		}
		
	}
	
	
	private Object findVoc(Class aVocClass, String aProperty, String aValue, EntityManager aManager) {
		List list = aManager.createQuery("from "+theEntityHelper.getEntityName(aVocClass)
				+" where "+aProperty+" =:value")
				.setParameter("value", aValue)
				.getResultList();
		if(list.isEmpty()) {
			throw new IllegalStateException("Нет справочника "+aVocClass+" со значением свойства "+aProperty+" = "+aValue) ;
		} else if(list.size()>1) {
			throw new IllegalStateException("Больше двух значений в справочнике "+aVocClass+" со значением свойства "+aProperty+" = "+aValue) ;
		} else {
			return list.iterator().next();
		}
	}

	private Class getVocClass(Class aClass, String aProperty) throws NoSuchMethodException {
		return PropertyUtil.getGetterMethod(aClass, aProperty).getReturnType();
	}

	private Object findRow(RowPersistProperty aRowPersistProperty
			, RowPersist aRowPersist
			, Object aParentEntity
			, EntityManager aManager) {
		// построение запроса  для поиска
		StringBuilder sb = new StringBuilder(50) ;
		sb.append("from ") ;
		sb.append(theEntityHelper.getEntityName(aRowPersist.rowEntityClass())) ;
		sb.append(" where "+aRowPersist.parentProperty()+"_id =:parent ");
		for(RowPersistMatch match : aRowPersistProperty.matches()) {
			sb.append(" and "+match.property()+"."+match.matchProperty()+" =:"
					+ match.property()+match.matchProperty()) ;
		}
		if (CAN_DEBUG)	LOG.debug("findRow: query = " + sb);
		Query query = aManager.createQuery(sb.toString());
		Object parentValue  ;
		try {
			parentValue = PropertyUtil.getPropertyValue(aParentEntity, "id") ;
		} catch (Exception e) {
			throw new IllegalStateException("Ошибка получения значения у родительского свойства "
					+aRowPersist.parentProperty()+" у формы "+aParentEntity+": "+e.getMessage(), e) ;
		}
		if (CAN_DEBUG) LOG.debug("findRow: parentValue = " + parentValue); 

		query.setParameter("parent", parentValue) ;

		for(RowPersistMatch match : aRowPersistProperty.matches()) {
			if (CAN_DEBUG)
				LOG.debug("findRow: setParameter(...) " + match.property()+match.matchProperty() +" = "+ match.matchValue()); 

			query.setParameter(match.property()+match.matchProperty(), match.matchValue());
		}
		List list = query.getResultList();
		if(list.size()>1) {
			throw new IllegalStateException("Уже больше одной строки записано в базу ") ;
		} else {
			return list.isEmpty() ? null : list.iterator().next();
		}
	}
	
	private final EntityHelper theEntityHelper = EntityHelper.getInstance() ;
}
