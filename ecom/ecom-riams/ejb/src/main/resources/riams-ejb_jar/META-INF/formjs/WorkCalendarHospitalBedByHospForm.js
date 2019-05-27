
function onPreSave(aForm, aEntity, aCtx) {
	checkDouble(aForm.patient, aForm.dateFrom, aForm.id, aCtx);
	var wf = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction") ;
	aForm.setWorkFunction(wf.id) ;
}
/**
 * Перед сохранением
 */
function onSave(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	if (aForm.isOperation) {
		save_oper(aForm, aEntity,aCtx);
	}
}
/**
 * Перед сохранением
 */
function onCreate(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	if (aForm.isOperation) {
		save_oper(aForm, aEntity,aCtx);
	} 
}
function save_oper(aForm, aEntity,aCtx) {
	var date = new java.util.Date() ;
	var wct = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime, aForm.getSurgCalTime());
	wct.setPrePatient(aEntity.getPatient()) ;
	wct.setPreHospital(aEntity.getId());
	wct.setService(aForm.getSurgService());
	
	wct.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	wct.setCreateTime(new java.sql.Time (date.getTime())) ;
	wct.setCreateDate(new java.sql.Date(date.getTime())) ;
		//adMedService.setPrescriptSpecial(aEntity.getPrescriptSpecial()) ;
}

function onPreCreate(aForm, aCtx) {
	/*Проверка - есть ли у пациента пред. запись на это число*/
	checkDouble(aForm.patient, aForm.dateFrom, 0, aCtx);
}

function checkDouble(aPatientId, aDateHosp, aId, aCtx) {
	var sql = "select pre.id from WorkCalendarHospitalBed pre where pre.patient_id="+aPatientId+" and pre.dateFrom = to_date('"+aDateHosp+"','dd.MM.yyyy')" ;
	if (+aId>0) sql +=" and pre.id!="+aId;
	if (!aCtx.manager.createNativeQuery(sql).getResultList().isEmpty()) {
		throw "У пациента уже создана предварительная госпитализация на это число, создание еще одной невозможно!";
	}
}
