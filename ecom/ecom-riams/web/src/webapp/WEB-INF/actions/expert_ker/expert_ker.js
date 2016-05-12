function infoBySmo(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/expert_ker/list_by_smo.jsp") ;
}
function infoByPatient(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/expert_ker/list_by_patient.jsp") ;
}