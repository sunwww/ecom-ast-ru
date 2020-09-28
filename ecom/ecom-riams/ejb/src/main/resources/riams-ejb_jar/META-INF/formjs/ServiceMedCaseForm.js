/**
 * Перед созданием
 */
function onPreCreate(aForm, aContext) {
	
	var medCase = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase, new java.lang.Long(aForm.parent)) ;
	if (medCase!=null && medCase.patient!=null)
		aForm.setPatient(medCase.patient.id) ;
	checkDateStart(aContext,aForm.dateStart,aForm.timeExecute,aForm.id) ;
	
}
function onPreSave(aForm, aEntity, aContext) {
	checkDateStart(aContext,aForm.dateStart,aForm.timeExecute,aForm.id) ;
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
		protocol.dateRegistration = aEntity.dateStart ;
		protocol.timeRegistration = aEntity.timeExecute ;
		protocol.username = aEntity.username ;
		protocol.record = aText ;
		protocol.specialist = aEntity.workFunctionExecute ;
		aContext.manager.persist(protocol) ;
		//aWorkFunction.workCalendar = calendar ;		
	}
}


function checkDateStart(aCtx,aExecuteDate,aExecuteTime,aId) {
	
	stat=aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/MedService/OnlyCurrentDay") ;
	if (stat) {
		
			var cal1 = java.util.Calendar.getInstance() ;
			var cal2 = java.util.Calendar.getInstance() ;
			var dateStart = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aExecuteDate,aExecuteTime);
			var dateCur = new java.sql.Date(new java.util.Date().getTime()) ;
			cal2.setTime(dateCur) ;		
			cal1.setTime(dateStart) ;
			
			if (cal1.get(java.util.Calendar.YEAR)==cal2.get(java.util.Calendar.YEAR) &&
				cal1.get(java.util.Calendar.MONTH)==cal2.get(java.util.Calendar.MONTH) &&
				cal1.get(java.util.Calendar.DATE)==cal2.get(java.util.Calendar.DATE) 
			) {
			
			} else
                throw "У Вас стоит ограничение на дату оказания услуги. Вы можете регистрировать оказанную услугу только текущим числом!";
	}
}

//очищение caos при удалении услуги
function freeCaosAfterDeleteServiceMedcase(aEntityId, aCtx) {
    aCtx.manager.createNativeQuery("update contractaccountoperationbyservice set serviceid=null, servicetype=null, medcase_id=null" +
        " where id=ANY(select caos.id from contractaccountoperationbyservice caos" +
        " left join contractaccountmedservice cams on cams.id=caos.accountmedservice_id" +
        " left join pricemedservice pms on pms.id=cams.medservice_id" +
        " left join medservice ms on ms.id=pms.medservice_id" +
        " left join medcase smo on smo.medservice_id=ms.id and smo.parent_id=caos.medcase_id" +
        " where smo.id="+aEntityId+")").executeUpdate() ;
}

function onPreDelete(aEntityId, aCtx) {
	aCtx.manager.createNativeQuery("update diary set servicemedcase_id = null where servicemedcase_id="+aEntityId).executeUpdate(); //Очищаем информацию об услуге в дневнике
    freeCaosAfterDeleteServiceMedcase(aEntityId, aCtx); //Очищаем информацию о выполнении услуги в caos
}