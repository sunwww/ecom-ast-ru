/**
 * Перед созданием
 */
function onPreCreate(aForm, aContext) {
	
	var medCase = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase, new java.lang.Long(aForm.parent)) ;
	if (medCase!=null && medCase.patient!=null)
		aForm.setPatient(medCase.patient.id) ;
	checkDateExecute(aContext,aForm.dateExecute,aForm.timeExecute,aForm.id) ;
	
}
function onPreSave(aForm, aEntity, aContext) {
	checkDateExecute(aContext,aForm.dateExecute,aForm.timeExecute,aForm.id) ;
}
/**
 * При сохранении
 */
function onSave(aForm, aEntity, aContext) {
	//createProtocol(aForm.record,aEntity,aContext) ;
}
/**
 * При создании
 */
function onCreate(aForm, aEntity, aContext) {
	createProtocol(aForm.record,aEntity,aContext) ;
}
function createProtocol(aText, aEntity , aContext) {
	if (aText!=null && aText.trim() !="") {
		var protocol = new Packages.ru.ecom.poly.ejb.domain.protocol.Protocol() ;
		protocol.medCase = aEntity ;
		protocol.dateRegistration = aEntity.dateExecute ;
		protocol.timeRegistration = aEntity.timeExecute ;
		protocol.username = aEntity.username ;
		protocol.record = aText ;
		protocol.specialist = aEntity.workFunctionExecute ;
		aContext.manager.persist(protocol) ;
		//aWorkFunction.workCalendar = calendar ;		
	}
}


function checkDateExecute(aCtx,aExecuteDate,aExecuteTime,aId) {
	
	stat=aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/MedService/OnlyCurrentDay") ;
	if (stat) {
		
			var cal1 = java.util.Calendar.getInstance() ;
			var cal2 = java.util.Calendar.getInstance() ;
			var dateExecute = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aExecuteDate,aExecuteTime);
			var dateCur = new java.sql.Date(new java.util.Date().getTime()) ;
			cal2.setTime(dateCur) ;		
			cal1.setTime(dateExecute) ;
			
			if (cal1.get(java.util.Calendar.YEAR)==cal2.get(java.util.Calendar.YEAR) &&
				cal1.get(java.util.Calendar.MONTH)==cal2.get(java.util.Calendar.MONTH) &&
				cal1.get(java.util.Calendar.DATE)==cal2.get(java.util.Calendar.DATE) 
			) {
			
			} else{
				var param = new java.util.HashMap() ;
				param.put("obj","ServiceMedCase") ;
				param.put("permission" ,"backdate") ;
				param.put("id", aId) ;
				//param.put("username", username) ;
				//param.put("ServiceMedCase", "backdate", aId, username) ;
				var check=aCtx.serviceInvoke("WorkerService", "checkPermission", param)+"";
				
				//var check=0 ;
				if (+check==0) {
					 throw "У Вас стоит ограничение на дату оказания услуги. Вы можете регистрировать оказанную услугу только текущим числом!";
				}
		}
	}
}