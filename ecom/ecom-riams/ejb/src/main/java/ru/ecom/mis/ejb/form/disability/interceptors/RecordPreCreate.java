package ru.ecom.mis.ejb.form.disability.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.disability.DisabilityDocument;
import ru.ecom.mis.ejb.form.disability.DisabilityRecordForm;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.Calendar;

/**
 * Ограничение записи продления перед созданием формы  
 * @author stkacheva
 *
 */
public class RecordPreCreate implements IParentFormInterceptor{
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	DisabilityRecordForm form = (DisabilityRecordForm) aForm ;
    	DisabilityDocument doc = manager.find(DisabilityDocument.class, aParentId) ;
    	
    	if (doc!=null) {
    		if (doc.getIsClose()!=null && doc.getIsClose()) {
    			throw new IllegalStateException("Нельзя добавить продление в закрытый документ!!!") ;
    		}
    		
    		// Обязательно, чтобы все даты окончания были заполнены 
    		//(Новый период нетрудоспособности не должен заводиться если у последнего
    		//не проставлена да окончания)
    		//Новый период нетрудоспособности должен заводиться с датой последняя дата окончания + 1
			Object[] row =   (Object[]) manager.createNativeQuery("select max(dateTo),max(dateFrom) from DisabilityRecord where disabilityDocument_id=:doc").setParameter("doc", doc.getId()).getSingleResult() ;
    		Date dateToMax = (Date)row[0] ;
    		Date dateFromMax = (Date)row[1] ;
    		
    		if (dateFromMax!=null) {
    			if (dateToMax!=null) {
        			Calendar cal = Calendar.getInstance();
        			cal.setTime(dateToMax) ;
        			cal.add(Calendar.DAY_OF_MONTH, 1) ;
        			form.setDateFrom(DateFormat.formatToDate(cal.getTime())) ;
        			form.addDisabledField("dateFrom") ;
    			} else {
    				throw new IllegalStateException("Невозможно добавить продление. Сначала нужно завершить все предыдущие продления!!!") ;    				
    			}
    		} 
    	} else {
    		throw new IllegalStateException("Невозможно добавить продление. Сначала надо определить документ нетрудоспособности!!!") ;
    	}
    }
}
