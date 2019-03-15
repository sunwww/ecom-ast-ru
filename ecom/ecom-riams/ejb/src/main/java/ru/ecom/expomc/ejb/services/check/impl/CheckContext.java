package ru.ecom.expomc.ejb.services.check.impl;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.expomc.ejb.domain.format.Format;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;
import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.ecom.expomc.ejb.services.check.ICheckLog;
import ru.nuzmsh.util.PropertyUtil;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

/**
 * @author esinev Date: 23.08.2006 Time: 9:40:26
 */
public class CheckContext implements ICheckContext, ICheckLog {

	private static final Logger LOG = Logger.getLogger(CheckContext.class);
	private static final boolean CAN_DEBUG = LOG.isDebugEnabled();

	public CheckContext(Format aFormat, Map<String, Object> aValues,
			Set<String> aAllowedFields, Date aActualDate,
			EntityManager aManager, Object aEntity) {
		theValues = aValues;
		theAllowedFields = aAllowedFields;
		theFormat = aFormat;
		theActualDate = aActualDate;
		theManager = aManager;
		theEntity = aEntity;
	}

	public Object get(String aFieldName) {
		if (!theAllowedFields.contains(aFieldName)) {
			throw new IllegalArgumentException("Нет поля " + aFieldName);
		}
		return theValues.get(aFieldName);
	}

	public String getString(String aFieldName) {
		return (String) get(aFieldName);
	}

	public BigDecimal getBigDecimal(String aFieldName) {
		return new BigDecimal(getString(aFieldName));
	}

	public Date getDate(String aFieldName) {
		return (Date) get(aFieldName);
	}

	public Format getFormat() {
		return theFormat;
	}

	public Object getEntry() {
		return theEntity;
	}

	public ICheckLog getLog() {
		return this;
	}

	public void set(String aFieldName, String aValue) {
		theChangedValues.put(aFieldName, aValue);
	}

	public void debug(String aMessage) {
		if (CAN_DEBUG)
			LOG.debug("debug: " + aMessage);
	}

	public void info(String aMessage) {
		LOG.info("message: " + aMessage);
	}

	public void warn(String aMessage) {
		LOG.warn("warn: " + aMessage);
	}

	public void error(String aMessage) {
		LOG.error("error: " + aMessage);
	}

	public void error(Object... aArgs) {
		error(Arrays.toString(aArgs)) ; //.toString());
	}

	public void info(Object... aArgs) {
		info(Arrays.toString(aArgs)) ; //(aArgs.toString());
	}

	private static Object convertToPropertyObject(Class aClass,
			String aProperty, Object aValue) {
		if (aValue == null)
			return null;
		try {
			//String methodName = PropertyUtil
			//		.getGetterMethodNameForProperty(aProperty);
			Method method = PropertyUtil.getGetterMethod(aClass, aProperty) ; //aClass.getMethod(methodName);
			Class type = method.getReturnType();
			return PropertyUtil.convertValue(aValue.getClass(), type, aValue);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}

	}

	public Object findDomain(long aDocument, String aDocumentCodeProperty,
			Object aValue) throws CheckException {
		if (aValue == null)
			return null;
		try {

			ImportDocument doc = theManager.find(ImportDocument.class,
					aDocument);
			ImportTime time = findActualTime(doc);

			Class clazz = ClassLoaderHelper.getInstance().loadClass(
					doc.getEntityClassName());
			Object convertedValue = convertToPropertyObject(clazz,
					aDocumentCodeProperty, aValue);

			List list = theManager.createQuery(
					"from " + clazz.getSimpleName()
							+ " e where time = :time and "
							+ aDocumentCodeProperty + " = :value")
					.setParameter("time", time.getId()).setParameter("value",
							convertedValue).setMaxResults(1).getResultList();
			
			Object entity = !list.isEmpty() ? list.iterator().next() : null ; 
			
			Object ret = null;
			if (entity != null) {
				ret = PropertyUtil.getPropertyValue(entity,
						aDocumentCodeProperty);
			}
			return ret;
		} catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
			throw new CheckException("Ошибка: " + e, e);
		}
	}

	private ImportTime findActualTime(ImportDocument aDocument) {
		Collection<ImportTime> times = aDocument.getTimes();
		return times.iterator().next();
	}

	public Object findActual(Class aClass, String aProperty, Object aValue) {
		ImportDocument document = (ImportDocument) theManager
				.createQuery(
						"from ImportDocument d where entityClassName = :entityClassName")
				.setParameter("entityClassName", aClass.getName())
				.getSingleResult();

		ImportTime time = findActualTime(document);
		return theManager.createQuery(
				"from " + aClass.getSimpleName() + " where time = :time and "
						+ aProperty + " = :property").setParameter("time",
				time.getId()).setParameter("property", aValue)
				.getSingleResult();
	}

	/** Дата актуальности */
	public Date getActualDate() {
		return theActualDate;
	}
	
	@Override
	public String toString() {
		return getClass().getName() +
				" actualDate =" + theActualDate +
				" values =" + theValues +
				" allowed =" + theAllowedFields +
				" object =" + theEntity;
	}

	/** Дата актуальности */
	private final Date theActualDate;

	private final Map<String, Object> theValues;

	private final Set<String> theAllowedFields;

	private final Format theFormat;

	private final TreeMap<String, Object> theChangedValues = new TreeMap<>();

	private final EntityManager theManager;

	private final Object theEntity;

}
