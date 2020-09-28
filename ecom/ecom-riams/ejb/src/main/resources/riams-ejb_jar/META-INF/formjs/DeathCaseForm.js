/**
 * Перед созданием
 */
function onCreate(aForm, aEntity, aCtx) {
	aEntity.patient = aEntity.getMedCase().getPatient() ;
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	//если не неонатологический случай смерти
    makePersonDead(aEntity,aCtx);

}
function makePersonDead(aEntity, aCtx) {
    if (aEntity.getIsNeonatologic()==null || aEntity.getIsNeonatologic()==false) {
        var patient = aEntity.getPatient();
        if (patient==null) {
        	var medcase = aEntity.getMedCase();
        	patient=medcase.getPatient();
		}
        if (patient!=null) {
        	patient.setDeathDate(aEntity.getDeathDate());
            aCtx.manager.persist(patient);
        }
    }
}
/**
 * Перед сохранением
 */
function onSave(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
    makePersonDead(aEntity,aCtx);
}
function onPreSave(aForm, aEntity, aCtx) {
	if (aForm.isAutopsy!=null && aForm.isAutopsy.equals(java.lang.Boolean.TRUE)) {
		if (!aCtx.manager.createNativeQuery("select dc.id from Certificate dc where dc.deathCase_id="+aForm.id+" and dc.dtype='DeathCertificate'").getResultList().isEmpty()) {
			throw "У данного пациента оформлено свидетельство о смерти, поэтому не могло быть произведено вскрытие!!!!!!!!!!!" ;
		}
	}
}

