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
	aForm.setDateCreate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setTimeCreate(new java.sql.Time (date.getTime())) ;
	aForm.setUsernameCreate(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	//aCtx.getSessionContext().set("dCreate","123");
}
function onPreDelete(aId, aCtx) {
	var param = new java.util.HashMap() ;
				param.put("obj","Ticket") ;
				param.put("permission" ,"dateClosePeriod") ;
				param.put("id", aId) ;
	var isClosedPeriod=aCtx.serviceInvoke("WorkerService", "checkPermission", param)+"";
	if (+isClosedPeriod==1) {
		var param1 = new java.util.HashMap() ;
		param1.put("obj","Ticket") ;
		param1.put("permission" ,"deleteDataInClosePeriod") ;
		param1.put("id", aId) ;
		var isDeleteClose = aCtx.serviceInvoke("WorkerService", "checkPermission", param1)+""; 
		//throw isDeleteClose ;
		if (+isDeleteClose!=1) throw "У вас стоит запрет на удаление данных в закрытом периоде";
	}
	aCtx.manager.createNativeQuery("delete from RenderedService where ticket_id='"+aId+"'").executeUpdate() ;
}

/**
 * При создании
 */
function onCreate(aForm, aEntity, aContext) {
	var illnesPrimary=aEntity.illnesPrimary  ;
	if (illnesPrimary!=null) {
		aEntity.setVocIllnesType(illnesPrimary.getIllnesType()) ;
		aEntity.setPrimary(illnesPrimary.getPrimary()) ;
	}
	aContext.manager.persist(aEntity) ;
}

/**
 * При сохранении
 */
function onSave(aForm, aEntity, aContext) {
	var illnesPrimary=aEntity.illnesPrimary  ;
	if (illnesPrimary!=null) {
		aEntity.setVocIllnesType(illnesPrimary.getIllnesType()) ;
		aEntity.setPrimary(illnesPrimary.getPrimary()) ;
	}
	aContext.manager.persist(aEntity) ;
}
