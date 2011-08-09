package ru.ecom.expomc.ejb.uc.snils;

import java.util.Collection;
import java.util.StringTokenizer;

import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.CheckResult;
import ru.ecom.expomc.ejb.services.check.ICheck;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.ecom.expomc.ejb.services.check.checkers.BadPropertyUtil;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;

@Comment("Приведение СНИЛС у пациента к нормальному виду")
public class ChangeSnilsNormalizeChecker implements ICheck {

	public CheckResult check(ICheckContext aContext) throws CheckException {
		String snils = aContext.getString("patientSnils") ;
		CheckResult result = CheckResult.createAccepted(false) ;
		if(!StringUtil.isNullOrEmpty(snils)) {
			StringTokenizer st = new StringTokenizer(snils, "- \t=+;,.\\|/~`") ;
			String s3_1 = next(st) ; 
			String s3_2 = next(st) ; 
			String s3_3 = next(st) ; 
			String s2_4 = next(st) ;
			StringBuilder sb = new StringBuilder() ;
			append(sb, s3_1,3) ;
			sb.append('-') ;
			append(sb, s3_2,3) ;
			sb.append('-') ;
			append(sb, s3_3,3) ;
			sb.append(' ') ;
			append(sb, s2_4,2) ;
			if(!sb.toString().equals(snils)) {
				result.set("patientSnils", sb.toString()) ;
				result.setAccepted(true) ;
			}
			
		}
		return result ;
	}

	private static void append(StringBuilder aSb, String aStr, int aCount) {
		StringBuilder sb ;
		if(aStr==null) {
			sb = new StringBuilder() ;
		} else {
			if(aStr.length()>aCount) aStr = aStr.substring(0, aCount) ;
			sb = new StringBuilder(aStr) ;
		}
		while(sb.length()<aCount) {
			sb.insert(0,'0') ;
		}
		aSb.append(sb) ;
	}
	
	private static String next(StringTokenizer aSt) {
		if(aSt.hasMoreTokens()) {
			return aSt.nextToken() ;
		} else return null ;
	}
	public Collection<String> getBadProperties() {
		return BadPropertyUtil.create("patientSnils");
	}
	

}
