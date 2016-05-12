function infoByPatient(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/poly_protocol/list_by_patient.jsp") ;
}
function infoByMedcard(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/poly_protocol/list_by_medcard.jsp") ;
}
function infoByMedcardShort(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/poly_protocol/list_by_medcard_short.jsp") ;
}
function infoByTicket(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/poly_protocol/list_by_ticket.jsp") ;
}
