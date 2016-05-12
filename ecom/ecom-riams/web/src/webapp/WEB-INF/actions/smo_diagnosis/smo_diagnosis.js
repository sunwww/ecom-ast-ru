function infoByPatient(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/smo_diagnosis/list_by_patient.jsp") ;
}
function infoShortByPatient(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/smo_diagnosis/list_by_patient_short.jsp") ;
}