/**
 * При создании
 * @param aForm форма
 * @param aEntity сущность
 * @param aContext контекст
 */
function onCreate(aForm, aEntity, aContext) {
	var timeFrom = aEntity.timeFrom ;
	var timeTo = aEntity.timeTo ;
	
	
	var cal1 = java.util.Calendar.getInstance() ;
	var cal2 = java.util.Calendar.getInstance() ;
	cal1.setTime(timeFrom) ;
	cal2.setTime(timeTo) ;

	//java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("HH:mm") ;
	if (+aForm.countVisits>0) {
		var cnt =aForm.countVisits.intValue() ;
		var hour1 = cal1.get(java.util.Calendar.HOUR_OF_DAY) ;
		var hour2 = cal2.get(java.util.Calendar.HOUR_OF_DAY) ;
		var min1 = cal1.get(java.util.Calendar.MINUTE) ;
		var min2 = cal2.get(java.util.Calendar.MINUTE) ;
		var dif = (hour2-hour1)*60 +min2- min1 ;
		//if (dif<cnt) throw "Разница между временами должна быть больше кол-ва посещений"  ;
		var ret = "" ;
		var format = new java.text.SimpleDateFormat("HH:mm") ;
		if (dif%cnt == 0) {
			var interval = dif/cnt ;
			while (cal2.after(cal1)) {
				var sqlTime = new java.sql.Time(cal1.getTime().getTime()) ;
				var exam=new Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTimeExample() ;
				exam.setCalendarTime(sqlTime) ;
				exam.setDayPattern(aEntity.dayPattern) ;
				aContext.manager.persist(exam) ;
				cal1.add(java.util.Calendar.MINUTE, interval) ;
			}
		} else {
			var interval = dif/cnt ;
			var dop = dif % cnt ;
			while (cal2.after(cal1)) {
				var sqlTime = new java.sql.Time(cal1.getTime().getTime()) ;
				var exam=new Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTimeExample() ;
				exam.setCalendarTime(sqlTime) ;
				exam.setDayPattern(aEntity.dayPattern) ;
				aContext.manager.persist(exam) ;
				cal1.add(java.util.Calendar.MINUTE, interval) ;
				if (dop>0) {
					--dop;
					cal1.add(java.util.Calendar.MINUTE, 1) ;
				}
			}			
		}
		
		
		
	} else {
		while (cal2.after(cal1)) {
			//System.out.println(format.format(cal1.getTime())) ;
			var sqlTime = new java.sql.Time(cal1.getTime().getTime()) ;
			var exam=new Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTimeExample() ;
			exam.setCalendarTime(sqlTime) ;
			exam.setDayPattern(aEntity.dayPattern) ;
			aContext.manager.persist(exam) ;
			cal1.add(java.util.Calendar.MINUTE, aForm.visitTime) ;
		}
	}
	/*
	while( cal.getTime().compareTo(aPattern.dateTo)<=0) {
		m = m + cal.getTime() ;
		deleteUnMedCasesCalendarTimesOnDay(aPattern.workCalendar, cal.getTime(), aContext.manager) ;	//and	
		var workCalendarDay = createCalendarDay(cal.getTime(), aPattern.workCalendar, aContext.manager) ;
		cal.add(java.util.Calendar.DATE, 1) ;
		
		// время по минутам
		var from = createTimeCal(cal.getTime(), aPattern.defaultTimeFrom) ;
		var to = createTimeCal(cal.getTime(), aPattern.defaultTimeTo) ;
		//throw from+" < "+to;
		var timeCal = java.util.Calendar.getInstance() ;
		timeCal.setTime(from) ;
		while ( timeCal.getTime().compareTo(to)<=0) {
			m = m + " time "+ timeCal.getTime() ;
			var workCalendarTime = createCalendarTime(timeCal.getTime(), workCalendarDay, aContext.manager) ;
			timeCal.add(java.util.Calendar.MINUTE, aPattern.defaultTimeInterval) ;
		}
		if(max--<0) throw "Превышен интервал в 365 дней" ;
	}*/

}

/**
 * Перед созданием
 */
function onPreCreate(aForm, aContext) {
	checkedInterval(aForm.timeFrom,aForm.timeTo,aForm.visitTime,aForm.countVisits) ;
}
function checkedInterval(aTimeFrom,aTimeTo,visitTime,countVisits) {

	var timeTo = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlTime(aTimeTo);
	var timeFrom = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlTime(aTimeFrom);
	if (timeTo.compareTo(timeFrom)<0) {
		throw "Дата действия по ["+aTimeTo
			+"] меньше даты действия С ["+aTimeFrom+"]" ;
	}
	var cal1 = java.util.Calendar.getInstance() ;
	var cal2 = java.util.Calendar.getInstance() ;
	cal1.setTime(timeFrom) ;
	cal2.setTime(timeTo) ;
	var cnt =countVisits.intValue() ;
	var hour1 = cal1.get(java.util.Calendar.HOUR_OF_DAY) ;
	var hour2 = cal2.get(java.util.Calendar.HOUR_OF_DAY) ;
	var min1 = cal1.get(java.util.Calendar.MINUTE) ;
	var min2 = cal2.get(java.util.Calendar.MINUTE) ;
	var dif = (hour2-hour1)*60 +min2- min1 ;
	if (dif<cnt) throw "Разница между временами должна быть больше кол-ва посещений"  ;
	if ((+visitTime<1) && (+countVisits<1)) throw "Необходимо заполнить либо среднее время посещения, либо кол-во визитов" ;
}

/**
 * При просмотре
 */
function onView(aForm, aEntity, aContext) {
}

/**
 * При сохранении
 */
function onSave(aForm, aEntity, aContext) {
}


/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aContext) {
	checkedInterval(aForm.timeFrom,aForm.timeTo,aForm.visitTime,aForm.countVisits) ;
}

/**
 * Перед удалением
 */
function onPreDelete(aEntityId, aContext) {
}

/**
 * При удалении
 */
function onDelete(aEntityId, aContext) {
}