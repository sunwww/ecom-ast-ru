package ru.ecom.expomc.ejb.services.check.checkers.sql;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import java.lang.reflect.Method;
@Getter
@Setter
public class NativeSqlContext {

	public NativeSqlContext(EntityManager aManager) {
		manager = aManager ;
	}
	

	/** Идентификатор проверки */
	private long checkId ;
	
	/** Идентификатор времени импорта */
	private long timeId ;

	public String getEntitySqlValue(String aProperty, String aValue) {
		try {
			//String methodName = PropertyUtil.getGetterMethodNameForProperty(aProperty) ;
			Class type = PropertyUtil.getMethodFormProperty(entityClass, aProperty).getReturnType() ;
			String value = type.equals(String.class) ? "'"+aValue+"'" : aValue ;
			return value ;
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}
	}
	
	public String getColumnName(String aProperty) {
		try {
			Method method = PropertyUtil.getGetterMethod(entityClass, aProperty) ;
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
			Class type = PropertyUtil.getGetterMethod(entityClass, aProperty).getReturnType() ; //theEntityClass.getMethod(methodName).getReturnType() ;
			String emptyValue = type.equals(String.class) ? "''" : "0" ;
			return emptyValue ;
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}
	}
	
	
	public boolean isEntityTimeSupports() {
		try {
			return ( entityClass.newInstance() ) instanceof IImportData ;
		} catch (Exception e) {
			throw new IllegalArgumentException(e) ;
		} 
	}
	
	
	public String getDocumentSqlTableName(long aDocumentId) {
		try {
			ImportDocument doc = manager.find(ImportDocument.class, aDocumentId) ;
			Class clazz = ClassLoaderHelper.getInstance().loadClass(doc.getEntityClassName()) ;
			return EntityHelper.getInstance().getTableName(clazz) ;
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e) ;
		}
		
	}
	
	public String getDocumentSqlColumnName(long aDocumentId, String aProperty) {
		try {
			ImportDocument doc = manager.find(ImportDocument.class, aDocumentId) ;
			Class clazz = ClassLoaderHelper.getInstance().loadClass(doc.getEntityClassName()) ;
			return EntityHelper.getInstance().getColumnName(clazz, aProperty) ;
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}
		
	}
	
	/** Сущность для проверки */
	private Class entityClass ;
	/** Название проверяемой таблицы */
	private String tableName ;
	private final EntityManager manager ;

}
