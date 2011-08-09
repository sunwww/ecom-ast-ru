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
	while (cal2.after(cal1)) {
		//System.out.println(format.format(cal1.getTime())) ;
		var sqlTime = new java.sql.Time(cal1.getTime().getTime()) ;
		var exam=new Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTimeExample() ;
		exam.setCalendarTime(sqlTime) ;
		exam.setDayPattern(aEntity.dayPattern) ;
		aContext.manager.persist(exam) ;
		cal1.add(java.util.Calendar.MINUTE, aForm.visitTime) ;
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
	checkedInterval(aForm.timeFrom,aForm.timeTo) ;
}
function checkedInterval(aTimeFrom,aTimeTo) {

	var timeTo = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlTime(aTimeTo);
	var timeFrom = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlTime(aTimeFrom);
	if (timeTo.compareTo(timeFrom)<0) {
		throw "Дата действия по ["+aTimeTo
			+"] меньше даты действия С ["+aTimeFrom+"]" ;
	}
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
	checkedInterval(aForm.timeFrom,aForm.timeTo) ;
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