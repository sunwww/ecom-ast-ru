package ru.ecom.expomc.ejb.services.check.checkers;

import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.CheckResult;
import ru.ecom.expomc.ejb.services.check.ICheck;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.ecom.expomc.ejb.services.check.checkers.sql.INativeSqlSupports;
import ru.ecom.expomc.ejb.services.check.checkers.sql.NativeSqlContext;
import ru.ecom.report.replace.HashValueGetter;
import ru.ecom.report.replace.ReplaceHelper;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;

import javax.persistence.NoResultException;
import java.util.Collection;

/**
 * Проверка на вхохдение в домен
 * 

 */
@Comment("Проверка на вхождение в домен")
public class CheckInDomain implements ICheck, INativeSqlSupports {
	
	
	public String getNativeSql(NativeSqlContext aContext) {
		try {
			
			String query = "insert into MESSAGE (dataId,check_Id,importTime_id)"
				+" select id, ${CHECK_ID}, ${TIME_ID}" 
				+" from ${TABLE_NAME}"
				+" where  \"time\"=${TIME_ID}"
				+" and ${PROPERTY} is not null" 
				+" and ${PROPERTY} <> ${EMPTY_VALUE}" 
				+" and ${PROPERTY} not in"
				+" (select ${VOC_CODE} from ${VOC_TABLE_NAME})" ;
			
			ReplaceHelper replaceHelper = new ReplaceHelper() ;
			HashValueGetter hash = new HashValueGetter() ;
			hash.set("CHECK_ID", aContext.getCheckId()) ;
			hash.set("TIME_ID", aContext.getTimeId()) ;
			hash.set("EMPTY_VALUE", aContext.getEntitySqlEmptyValue(theProperty)) ;
			hash.set("TABLE_NAME", aContext.getTableName()) ;
			hash.set("PROPERTY", "\""+theProperty+"\"") ;
			hash.set("VOC_TABLE_NAME", aContext.getDocumentSqlTableName(theDocument)) ;
			hash.set("VOC_CODE", aContext.getDocumentSqlColumnName(theDocument, theDocumentCodeProperty)) ;
			
			return (String) replaceHelper.replaceWithValues(query, hash) ;
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}
	}

    public CheckResult check(ICheckContext aContext) throws CheckException {
        Object value = aContext.get(theProperty) ;
        if(value==null) return CheckResult.createAccepted(false);
        if(StringUtil.isNullOrEmpty(value.toString())) return CheckResult.createAccepted(false); 
        try {
        	
            Object domain = aContext.findDomain(theDocument, theDocumentCodeProperty, value) ;
            return CheckResult.createAccepted(false);
        } catch (NoResultException e) {
            return CheckResult.createAccepted(true);
        }
    }

    public Collection<String> getBadProperties() {
    	return BadPropertyUtil.create(getProperty()) ;
	}
    
    /** Поле */
    @Comment("Свойство")
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }

    /** Документ */
    @Comment("Документ")
    public long getDocument() { return theDocument ; }
    public void setDocument(long aDocument) { theDocument = aDocument ; }

    /** Идентификатор */
    @Comment("Идентификатор")
    public String getDocumentCodeProperty() { return theDocumentCodeProperty ; }
    public void setDocumentCodeProperty(String aDocumentCodeProperty) { theDocumentCodeProperty = aDocumentCodeProperty ; }

    /** Идентификатор */
    private String theDocumentCodeProperty ;
    /** Документ */
    private long theDocument ;
    /** Поле */
    private String theProperty ;
}
