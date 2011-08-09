package ru.ecom.expomc.ejb.services.check.checkers;

import java.util.Collection;
import java.util.LinkedList;

import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.CheckResult;
import ru.ecom.expomc.ejb.services.check.IChangeCheck;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.ecom.expomc.ejb.services.check.checkers.sql.INativeSqlMultipleQueriesSupports;
import ru.ecom.expomc.ejb.services.check.checkers.sql.NativeSqlContext;
import ru.ecom.report.replace.HashValueGetter;
import ru.ecom.report.replace.ReplaceHelper;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;

/**
 * Установить значение, если поле пустое
 */
@Comment("Установить значение, если поле пустое")
public class ChangeIfEmpty implements IChangeCheck, INativeSqlMultipleQueriesSupports {


	public Collection<String> getNativeSql(NativeSqlContext aContext) {
		try {
			LinkedList<String> ret = new LinkedList<String>() ;
			
			// сообщение 
			RequiredCheck requiredCheck = new RequiredCheck() ;
			requiredCheck.setProperty(theProperty) ;
			ret.add(requiredCheck.getNativeSql(aContext)) ;
			
			// обновить 
			String query = "update ${TABLE} set ${COLUMN} = ${VALUE} "
				+" where (${COLUMN} is null or ${COLUMN} = ${EMPTY_VALUE} or ${COLUMN} = null)"
				+" and \"time\"=${TIME_ID}";
			ReplaceHelper replaceHelper = new ReplaceHelper() ;
			HashValueGetter hash = new HashValueGetter() ;
			hash.set("TIME_ID", aContext.getTimeId()) ;
			hash.set("EMPTY_VALUE", aContext.getEntitySqlEmptyValue(theProperty)) ;
			hash.set("TABLE", aContext.getTableName()) ;
			hash.set("COLUMN", "\""+theProperty+"\"") ;
			hash.set("VALUE", aContext.getEntitySqlValue(theProperty, theNewValue)) ;
			ret.add((String) replaceHelper.replaceWithValues(query, hash)) ;
			
			return ret ;
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}	}

    public CheckResult check(ICheckContext aContext) throws CheckException {
        Object value = aContext.get(theProperty);
        CheckResult ret = new CheckResult();
        if(value!=null && value instanceof String) {
            String str = (String) value ;
            if(StringUtil.isNullOrEmpty(str)) {
                ret.setAccepted(true);
                ret.set(theProperty, theNewValue);
            }
        } else if(value==null) {
            ret.setAccepted(true);
            ret.set(theProperty, theNewValue);
        }
        return ret ;
    }

    public Collection<String> getBadProperties() {
    	return BadPropertyUtil.create(getProperty()) ;
	}

    /** Свойство */
    @Comment("Свойство")
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }

    /** Свойство */
    private String theProperty ;

    /** Новое значение */
    @Comment("Новое значение")
	public String getNewValue() { return theNewValue ; }
	public void setNewValue(String aNewValue) { theNewValue = aNewValue ; }
	
	/** Новое значение */
	private String theNewValue ;
	
	private final RequiredCheck theRequiredCheck = new RequiredCheck();

}
