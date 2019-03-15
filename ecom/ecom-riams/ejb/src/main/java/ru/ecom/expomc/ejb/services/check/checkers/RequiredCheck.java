package ru.ecom.expomc.ejb.services.check.checkers;

import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.CheckResult;
import ru.ecom.expomc.ejb.services.check.ICheck;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.ecom.expomc.ejb.services.check.checkers.sql.INativeSqlSupports;
import ru.ecom.expomc.ejb.services.check.checkers.sql.NativeSqlContext;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;

import java.util.Collection;

/**
 * Проверка на обязательность поля
 * 
 * insert into MESSAGE (dataId,check_Id,importTime_id) 
 *  select id, 78, 72 from RegistryEntry 
 *  where (worksOldCode='' or worksOldCode is null or worksOldCode = null) 
 *  and "time"=72
 */
@Comment("Проверка на обязательность поля")
public class RequiredCheck implements ICheck, INativeSqlSupports {

	
	public String getNativeSql(NativeSqlContext aContext) {
		try {
			String emptyValue = aContext.getEntitySqlEmptyValue(theProperty) ;
			
			StringBuilder sb = new StringBuilder() ;
			sb.append("insert into MESSAGE (dataId,check_Id,importTime_id) ") ;
			sb.append(" select id, ").append(aContext.getCheckId()).append(", ").append(aContext.getTimeId()).append(" from ").append(aContext.getTableName());
			String p = "\""+aContext.getColumnName(theProperty)+"\"" ;
			sb.append(" where (").append(p).append("=").append(emptyValue).append(" or ")
				.append(p).append(" is null or ")
				.append(p).append(" = null )") ;
			if(aContext.isEntityTimeSupports()) {
				String timeColumn = "\""+aContext.getColumnName("time")+"\"" ;
					sb.append(" and ").append(timeColumn).append(" =" ).append(aContext.getTimeId());
			}
			return sb.toString() ;
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}
	}
	
    public CheckResult check(ICheckContext aContext) throws CheckException {
        boolean accepted = false ;
        Object obj = aContext.get(theProperty) ;
        if(obj==null) {
            accepted = true ;
        } else {
            if(obj instanceof String) {
                String str = (String) obj ;
                accepted = StringUtil.isNullOrEmpty(str) ;
            }
        }
        return CheckResult.createAccepted(accepted);
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
}
