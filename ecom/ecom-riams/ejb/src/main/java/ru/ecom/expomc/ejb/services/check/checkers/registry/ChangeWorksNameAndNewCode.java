package ru.ecom.expomc.ejb.services.check.checkers.registry;

import java.util.Collection;
import java.util.LinkedList;

import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.CheckResult;
import ru.ecom.expomc.ejb.services.check.IChangeCheck;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.ecom.expomc.ejb.services.check.checkers.BadPropertyUtil;
import ru.ecom.expomc.ejb.services.check.checkers.sql.INativeSqlMultipleQueriesSupports;
import ru.ecom.expomc.ejb.services.check.checkers.sql.NativeSqlContext;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Обновить название и новый код предпрятия по старому коду
 */
@Comment("Обновить название и новый код предпрятия по старому коду")
public class ChangeWorksNameAndNewCode implements IChangeCheck, INativeSqlMultipleQueriesSupports {


	public Collection<String> getNativeSql(NativeSqlContext aContext) {
		try {
			LinkedList<String> ret = new LinkedList<String>() ;
			
			String query1 = "update RegistryEntry set worksName = "
					+" (select name from OMC_ORG" 
					+" where OMC_ORG.voc_code=worksOldCode )"
					+" where \"time\"="+aContext.getTimeId() ;
			ret.add(query1) ;

			String query2 = "update RegistryEntry set worksCode = "
				+" (select newCode from OMC_ORG" 
				+" where OMC_ORG.voc_code=worksOldCode )"
				+" where \"time\"="+aContext.getTimeId() ;
			ret.add(query2) ;

			return ret ;
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}	
	}

    public CheckResult check(ICheckContext aContext) throws CheckException {
        CheckResult ret = new CheckResult();
        return ret ;
    }

    public Collection<String> getBadProperties() {
    	return BadPropertyUtil.create("worksCode","worksOldCode","worksName") ;
	}

}
