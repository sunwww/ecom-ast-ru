/**
 * Перед созданием
 */
function onCreate(aForm, aEntity, aCtx) {
	aEntity.patient = aEntity.getMedCase().getPatient() ;
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
/**
 * Перед сохранением
 */
function onSave(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	var patient = aEntity.getPatient();
	if (patient.getDeathDate()!= null) {
	} else {
		patient.setDeathDate(aEntity.getDeathDate());
	}
}
