/** 
 * Создаем java.util.Date из java.sql.Date + java.sql.Time
 */
function createTimeCal(aDate, aTime) {
	var cal = java.util.Calendar.getInstance() ;
	cal.setTime(aDate) ;
	
	var timeCal = java.util.Calendar.getInstance() ;
	timeCal.setTime(aTime) ;
	
	cal.set(java.util.Calendar.HOUR_OF_DAY, timeCal.get(java.util.Calendar.HOUR_OF_DAY)) ;
	cal.set(java.util.Calendar.MINUTE, timeCal.get(java.util.Calendar.MINUTE)) ;
	
	return cal.getTime() ;
}


/**
 *  Добавляем новый WorkCalendarTime по времени (aTime) и дню (aWorkCalendarDay)
 *  Проверяем, не занято ли такое время
 */
function createCalendarTime(aTime, aWorkCalendarDay, aManager) {
	// проверяем на занятость
	var list = aManager.createQuery(
		// "from WorkCalendarTime where medCase is null "
		 "from WorkCalendarTime where"
		+" workCalendarDay = :day"
		+" and timeFrom = :timeFrom")
		.setParameter("timeFrom", aTime)
		.setParameter("day", aWorkCalendarDay) 
		.getResultList() ;
	if(list.size()==0) { // свободно, создаем новый
		var t = Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime() ;
		aManager.persist(t) ;
		t.workCalendarDay = aWorkCalendarDay ;
		var sqlTime = new java.sql.Time(aTime.getTime()) ;
		t.timeFrom = sqlTime ;
	}	
	
	
}

/**
 * Создаем календарный день, если он уже есть - возвращаем первый существующий
 */
function createCalendarDay(aDate, aWorkCalendar, aManager) {
	var list = aManager.createQuery("from WorkCalendarDay where calendarDate = :calendarDate and workCalendar = :workCalendar")
		.setParameter("calendarDate", aDate)
		.setParameter("workCalendar", aWorkCalendar)
		.getResultList() ;
	var day ;	
	if(list.isEmpty()) { 
		day = Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDay() ;
		aManager.persist(day) ;
		day.workCalendar = aWorkCalendar ;
		day.calendarDate = new java.sql.Date(aDate.getTime()) ;
	} else {
		day = list.iterator().next() ;
	}
	return day ;
	
}

function deleteUnMedCasesCalendarTimes(aCalendar, aManager) {
	aManager.createNativeQuery(
	    " delete from WorkCalendarTime"
		+" where WorkCalendarTime.id in "
		+"      ( select WorkCalendarTime.id from WorkCalendarTime "
		+"         inner join WorkCalendarDay on WorkCalendarTime.workCalendarDay_id=WorkCalendarDay.id "
        +"         where WorkCalendarDay.workCalendar_id =:cal_id "
        +"      )"
        +"   and WorkCalendarTime.medcase_id is null"
       // +"   and WorkCalendarTime.id not in (select medcase.timePlan_id from medcase)"
       )
        .setParameter("cal_id", aCalendar.id)
		.executeUpdate() ;
}

function deleteUnMedCasesCalendarTimesOnDay(aCalendar, aWorkCalendarDay, aManager) {
	aManager.createNativeQuery(
	    " delete from WorkCalendarTime"
		+" where WorkCalendarTime.workCalendarDay_id =:day_id "
		+" and WorkCalendarTime.medcase_id is null"
		)
        .setParameter("day_id", aWorkCalendarDay.id)
		.executeUpdate() ;
}

function deleteEmptyCalendarDays(aCalendar, aManager) {
	var list = aManager.createQuery("from WorkCalendarDay where workCalendar = :cal")
		.setParameter("cal", aCalendar)
		.getResultList() ;
		
	for(var i=0; i<list.size(); i++) {
		var day = list.get(i) ;
		if(day.workCalendarTimes.size()==0) {
			aManager.remove(day) ;
		}
	}	
}

function onCreate(aForm, aPattern, aContext) {
	//if(!aPattern.dateFrom)	throw "Нет даты действия С" ;
	//if(!aPattern.dateTo)	throw "Нет даты действия ПО" ;
	/*if(aPattern.dateTo.compareTo(aPattern.dateFrom)<0) {
		throw "Дата действия по ["+aPattern.dateTo
			+"] меньше даты действия С ["+aPattern.dateFrom+"]" ;
	}*/
	if(aPattern.defaultTimeInterval<1) throw "Интервал рабочих времен должен быть больше нуля" ;
	
	// удаляем старые дни, где нет приемов
	//deleteUnMedCasesCalendarTimes(aPattern.workCalendar, aContext.manager) ; and
	//deleteEmptyCalendarDays(aPattern.workCalendar, aContext.manager) ;
	// создаем
	/*
	var cal = java.util.Calendar.getInstance() ;
	var m = "" ;
	cal.setTime(aPattern.dateFrom) ;
	var max = 365 ;
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
	}
	*/
	//throw m ;
}


function onSave(aForm, aPattern, aContext) {
	onCreate(aForm, aPattern, aContext) ;
}

function onPreCreate(aForm, aCtx){
	var list =null;
    list = aCtx.manager.createQuery("from WorkCalendarPattern where"
        	+" name=:name"
        	)
        	.setParameter("name",aForm.name)
        	.getResultList() ;
        errorThrow(list) ;
	
}
function onPreSave(aForm,aEntity , aCtx) {
	var thisid = aForm.getId() ;
	var list =null;
    list = aCtx.manager.createQuery("from WorkCalendarPattern where"
        	+" name=:name"
        	+" and id != '"+thisid+"'"
        )
        	.setParameter("name",aForm.name)
        	.getResultList() ;
	errorThrow(list) ;
	
}
function errorThrow(aList) {
	if (aList!=null && aList.size()>0) {
		var error ="";
		for(var i=0; i<aList.size() && i<10; i++) {
			var work = aList.get(i) ;
			error = error+"<br> <a href='entityParentView-cal_workCalendarPattern.do?id="+work.id+"'>"+(i+1)+". "+ work.info + "</a>; " ;
		}
		throw "<u>Уже существует шаблон с таким именем:</u>" + error ;
	}
}
