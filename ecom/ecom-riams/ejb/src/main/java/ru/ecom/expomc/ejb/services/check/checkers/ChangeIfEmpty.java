package ru.ecom.expomc.ejb.services.check.checkers;

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

import java.util.Collection;
import java.util.LinkedList;

/**
 * Установить значение, если поле пустое
 */
@Comment("Установить значение, если поле пустое")
public class ChangeIfEmpty implements IChangeCheck, INativeSqlMultipleQueriesSupports {


	public Collection<String> getNativeSql(NativeSqlContext aContext) {
		try {
			LinkedList<String> ret = new LinkedList<>() ;
			
			// сообщение 
			RequiredCheck requiredCheck = new RequiredCheck() ;
			requiredCheck.setProperty(property) ;
			ret.add(requiredCheck.getNativeSql(aContext)) ;
			
			// обновить 
			String query = "update ${TABLE} set ${COLUMN} = ${VALUE} "
				+" where (${COLUMN} is null or ${COLUMN} = ${EMPTY_VALUE} or ${COLUMN} = null)"
				+" and \"time\"=${TIME_ID}";
			ReplaceHelper replaceHelper = new ReplaceHelper() ;
			HashValueGetter hash = new HashValueGetter() ;
			hash.set("TIME_ID", aContext.getTimeId()) ;
			hash.set("EMPTY_VALUE", aContext.getEntitySqlEmptyValue(property)) ;
			hash.set("TABLE", aContext.getTableName()) ;
			hash.set("COLUMN", "\""+property+"\"") ;
			hash.set("VALUE", aContext.getEntitySqlValue(property, newValue)) ;
			ret.add((String) replaceHelper.replaceWithValues(query, hash)) ;
			
			return ret ;
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}	}

    public CheckResult check(ICheckContext aContext) throws CheckException {
        Object value = aContext.get(property);
        CheckResult ret = new CheckResult();
        if(value instanceof String) {
            String str = (String) value ;
            if(StringUtil.isNullOrEmpty(str)) {
                ret.setAccepted(true);
                ret.set(property, newValue);
            }
        } else if(value==null) {
            ret.setAccepted(true);
            ret.set(property, newValue);
        }
        return ret ;
    }

    public Collection<String> getBadProperties() {
    	return BadPropertyUtil.create(getProperty()) ;
	}

    /** Свойство */
    @Comment("Свойство")
    public String getProperty() { return property ; }
    public void setProperty(String aProperty) { property = aProperty ; }

    /** Свойство */
    private String property ;

    /** Новое значение */
    @Comment("Новое значение")
	public String getNewValue() { return newValue ; }
	public void setNewValue(String aNewValue) { newValue = aNewValue ; }
	
	/** Новое значение */
	private String newValue ;
	
	private final RequiredCheck requiredCheck = new RequiredCheck();

}
