function infoByPatient(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/smo_visitProtocol/list_by_patient.jsp") ;
}
function viewProtocol(aForm,aCtx) {
	var id=aCtx.request.getParameter("id") ;
	var list =id.split("!") ;
	return aCtx.createForwardRedirect(
			"/entityParentView-smo_visitProtocol.do?id="+list[2]) ;
	
}
function viewShortProtocol(aForm,aCtx) {
	var id=aCtx.request.getParameter("id") ;
	var list =id.split("!") ;
	return aCtx.createForwardRedirect(
		"/entityShortView-smo_visitProtocol.do?id="+list[2]) ;
	
}
function infoByMedcard(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/smo_visitProtocol/list_by_medcard.jsp") ;
}
function infoByMedcardShort(aForm,aCtx) {
	aCtx.request.setAttribute("short","Short") ;
	return aCtx.createForward("/WEB-INF/actions/smo_visitProtocol/list_by_medcard.jsp") ;
}