
function onPreCreate(aForm, aCtx) {
	
	var isHoliday = Packages.ru.nuzmsh.util.format.DateFormat.isHoliday(aForm.dateFinish) ;
	//throw ""+isHoliday;
	if (isHoliday==true && (aForm.emergency==null||aForm.emergency==false)) {
		throw "У вас стоит запрет на создание талонов на воскресенье!";
	}
	//Проверка на создание талона позже даты смерти пациента 
	var pat = aCtx.manager.createQuery(" from Patient where id = :pat").setParameter("pat", aForm.getPatient()).getResultList().get(0);
	if (pat.getDeathDate()!=null) {
		var dateVisit = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.getDateFinish());
		var deathDate = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(pat.getDeathDate(),"yyyy-MM-dd");
		if (dateVisit.getTime() > deathDate.getTime()) {
			throw "Невозможно создать направление. На дату направления пациент уже умер ("
				+Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(deathDate)+")";
		} 
	}
	var param = new java.util.HashMap() ;
				param.put("obj","Ticket") ;
				param.put("permission" ,"dateClosePeriod") ;
				param.put("date",
					Packages.ru.nuzmsh.util.format.DateFormat.formatToJDBC(aForm.dateFinish)) ;
				param.put("id", aForm.id) ;
	var isClosedPeriod=aCtx.serviceInvoke("WorkerService", "checkPermission", param)+"";
	if (+isClosedPeriod==1) {
		var param1 = new java.util.HashMap() ;
		param1.put("obj","Ticket") ;
		param1.put("permission" ,"createDataInClosePeriod") ;
		param1.put("id", aForm.id) ;
		var isCreateClose = aCtx.serviceInvoke("WorkerService", "checkPermission", param1)+""; 
		//throw isDeleteClose ;
		if (+isCreateClose!=1) throw "У вас стоит запрет на создание данных в закрытом периоде";
	}
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateTime(new java.sql.Time (date.getTime())) ;
	aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
function onCreate(aForm, aEntity, aContext) {
	aEntity.setPatient(aEntity.medcard.person) ;
    var timePlan =aEntity.timePlan;
	//Проверяем - если у рабочей фукнции стоит - разрешить делать направления на дату без указания времени - создаем направление (делаем календарь, workCalendarDay, workCalendarTime), Visit
	if (aEntity.workFunctionExecute.isDirectionNoTime&&timePlan!=null) {
	    aEntity.setWorkFunctionPlan(aEntity.getWorkFunctionExecute());
	    timePlan.setMedCase(aEntity);
	    aContext.manager.persist(timePlan);
    }
}

function onPreDelete() { //Очищать время в расписании

}
