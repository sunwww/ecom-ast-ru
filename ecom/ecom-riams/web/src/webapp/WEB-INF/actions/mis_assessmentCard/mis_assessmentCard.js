function getData(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_assessmentCard/data.jsp") ;
}
function listByPatient(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_assessmentCard/list_by_patient.jsp");
}