
function onPreCreate(aForm, aCtx) {
	var param = new java.util.HashMap() ;
				param.put("obj","Ticket") ;
				param.put("permission" ,"dateClosePeriod") ;
				param.put("date",
					Packages.ru.nuzmsh.util.format.DateFormat.formatToJDBC(aForm.date)) ;
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
}
