package ru.ecom.ejb.util;

import ru.nuzmsh.util.format.DateFormat;

import java.util.Date;

public class DurationUtil {

	/**
	 *
	 * @param aFrom дата начала
	 * @param aTo дата окончания
	 * @return Число дней длительности
	 */
	public static String getDuration(Date aFrom, Date aTo) {
		if(aFrom==null) return "Нет даты начала" ;
		if(aTo==null) return "Нет даты окончания" ;
		
		long start = aFrom.getTime();
		long finish = aTo.getTime();
		if(finish<start) return "Дата окончания меньше даты начала";

		final int msecinday = 1000 * 60 *  60   * 24 ; 
		return String.valueOf(1 +( (finish-start) / msecinday));
		
	}


	/**
	 *
	 * @param aFrom дата начала
	 * @param aTo дата окончания
	 * @param aFull признак - дата окончания может быть пустой (расчет будет на тек. дату)
	 * @param aDn признак - добавить день
	 * @return длительность случая в днях
	 */
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
		return durat + add;
	}
	
	public static String getDurationMedCase(Date aFrom, Date aTo, int aFull ) {
		return getDurationMedCase(aFrom,aTo,aFull,0);
	}

	/**
	 *
	 * @param aFrom Дата начала
	 * @param aTo Дата окончания
	 * @return строку вида дата.начала - дата.окончания в формате дд.мм.гггг
	 */
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
