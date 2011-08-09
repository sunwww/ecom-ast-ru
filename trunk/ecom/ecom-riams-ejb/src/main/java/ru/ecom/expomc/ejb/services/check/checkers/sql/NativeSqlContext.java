package ru.ecom.expomc.ejb.services.check.checkers.sql;

import java.lang.reflect.Method;

import javax.persistence.Column;
import javax.persistence.EntityManager;

import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;

public class NativeSqlContext {

	public NativeSqlContext(EntityManager aManager) {
		theManager = aManager ;
	}
	
	/** Идентификатор проверки */
	public long getCheckId() { return theCheckId ; }
	public void setCheckId(long aCheckId) { theCheckId = aCheckId ; }
	
	/** Идентификатор проверки */
	private long theCheckId ;
	
	/** Идентификатор времени импорта */
	public long getTimeId() { return theTimeId ; }
	public void setTimeId(long aTimeId) { theTimeId = aTimeId ; }
	
	/** Идентификатор времени импорта */
	private long theTimeId ;
	
	/** Название проверяемой таблицы */
	public String getTableName() { return theTableName ; }
	public void setTableName(String aTableName) { theTableName = aTableName ; }

	/** Сущность для проверки */
	public Class getEntityClass() { return theEntityClass ; }
	public void setEntityClass(Class aEntityClass) { theEntityClass = aEntityClass ; }
	
	public String getEntitySqlValue(String aProperty, String aValue) {
		try {
			//String methodName = PropertyUtil.getGetterMethodNameForProperty(aProperty) ;
			Class type = PropertyUtil.getMethodFormProperty(theEntityClass, aProperty).getReturnType() ;
			String value = type.equals(String.class) ? "'"+aValue+"'" : aValue ;
			return value ;
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}
	}
	
	public String getColumnName(String aProperty) {
		try {
			Method method = PropertyUtil.getGetterMethod(theEntityClass, aProperty) ;
			String ret = PropertyUtil.getPropertyName(method) ;
			if(method.isAnnotationPresent(Column.class)) {
				Column column = method.getAnnotation(Column.class) ;
				if(!StringUtil.isNullOrEmpty(column.name())) {
					ret = column.name();
				}
			}
			return ret ;
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}
	}
	
	public String getEntitySqlEmptyValue(String aProperty) {
		try {
			
			//String methodName = PropertyUtil.getGetterMethodNameForProperty(aProperty) ;
			Class type = PropertyUtil.getGetterMethod(theEntityClass, aProperty).getReturnType() ; //theEntityClass.getMethod(methodName).getReturnType() ;
			//System.out.println(clazz+" "+theProperty+" "+type) ;
			String emptyValue = type.equals(String.class) ? "''" : "0" ;
			return emptyValue ;
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}
	}
	
	
	public boolean isEntityTimeSupports() {
		try {
			return ( theEntityClass.newInstance() ) instanceof IImportData ;
		} catch (Exception e) {
			throw new IllegalArgumentException(e) ;
		} 
	}
	
	
	public String getDocumentSqlTableName(long aDocumentId) {
		try {
			ImportDocument doc = theManager.find(ImportDocument.class, aDocumentId) ;
			Class clazz = ClassLoaderHelper.getInstance().loadClass(doc.getEntityClassName()) ;
			return EntityHelper.getInstance().getTableName(clazz) ;
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e) ;
		}
		
	}
	
	public String getDocumentSqlColumnName(long aDocumentId, String aProperty) {
		try {
			ImportDocument doc = theManager.find(ImportDocument.class, aDocumentId) ;
			Class clazz = ClassLoaderHelper.getInstance().loadClass(doc.getEntityClassName()) ;
			return EntityHelper.getInstance().getColumnName(clazz, aProperty) ;
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}
		
	}
	
	/** Сущность для проверки */
	private Class theEntityClass ;
	/** Название проверяемой таблицы */
	private String theTableName ;
	private final EntityManager theManager ;

}
