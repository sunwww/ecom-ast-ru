package ru.ecom.ejb.util;

import java.util.Date;

import ru.nuzmsh.util.format.DateFormat;

public class DurationUtil {

	public static String getDuration(Date aFrom, Date aTo) {
		if(aFrom==null) return "Нет даты начала" ;
		if(aTo==null) return "Нет даты окончания" ;
		
		long start = aFrom.getTime();
		long finish = aTo.getTime();
		if(finish<start) return "Дата окончания меньше даты начала";

		final int msecinday = 1000 * 60 *  60   * 24 ; 
		return String.valueOf(1 +( (finish-start) / msecinday));
		
	}
	
	
	public static String getDurationMedCase(Date aFrom, Date aTo, int aFull, int aDn ) {
		String add ="";
		if(aFrom==null) return aFull>0?"Нет даты начала":"" ;
		if(aTo==null) {
			if(aFull>0) {
				add = " (на текущий момент)" ;
				aTo = new Date() ;
			} else {
				return "" ;
			}
			
		}
		long start = aFrom.getTime();
		long finish = aTo.getTime();
		if(finish<start) return aFull>0?"Дата окончания меньше даты начала":"";
		
		final int msecinday = 1000 * 60 *  60   * 24 ;
		final int min = 1000 * 60 * 24 ;
		long durat = (finish-start) / msecinday ;
		if (((finish-start) / min)%60>55) ++durat ;
		if (durat<1) {
			durat=1;
		} else {
			if (aDn>0) durat=durat+1 ;
		}
		return new StringBuilder().append(durat).append(add).toString();
	}
	
	public static String getDurationMedCase(Date aFrom, Date aTo, int aFull ) {
		String add ="";
		if(aFrom==null) return aFull>0?"Нет даты начала":"" ;
		if(aTo==null) {
			if(aFull>0) {
				add = " (на текущий момент)" ;
				aTo = new Date() ;
			} else {
				return "" ;
			}
			
		}
		long start = aFrom.getTime();
		long finish = aTo.getTime();
		if(finish<start) return aFull>0?"Дата окончания меньше даты начала":"";
		
		final int msecinday = 1000 * 60 *  60   * 24 ;
		final int min = 1000 * 60 * 24 ;
		long durat = (finish-start) / msecinday ;
		if (((finish-start) / min)%60>55) ++durat ;
		if (durat<1) durat=1;
		return new StringBuilder().append(durat).append(add).toString();
	}
	
	public static String getDurationFull(Date aFrom, Date aTo) {
		StringBuilder ret = new StringBuilder() ;
		if(aFrom==null) {
			ret.append("нет даты начала") ;
		} else {
			ret.append(DateFormat.formatToDate(aFrom));
		}
		ret.append("-") ;
		if(aTo==null) {
			ret.append("нет даты окончания") ;
		} else {
			ret.append(DateFormat.formatToDate(aTo));
		}
		if (aFrom!=null && aTo!=null) {
			if( aTo.getTime() < aFrom.getTime()) {
				return "Дата окончания меньше даты начала";
			}
		}

		return ret.toString();
	}
	
}
