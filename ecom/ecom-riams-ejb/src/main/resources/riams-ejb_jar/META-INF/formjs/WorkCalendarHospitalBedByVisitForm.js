
function onPreSave(aForm, aEntity, aCtx) {
	//Очистить пред. время, если оно изменилось
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
