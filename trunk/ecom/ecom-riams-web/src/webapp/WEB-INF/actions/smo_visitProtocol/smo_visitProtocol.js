function infoByPatient(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/smo_visitProtocol/list_by_patient.jsp") ;
}
function viewProtocol(aForm,aCtx) {
	var id=aCtx.request.getParameter("id") ;
	var list =id.split("!") ;
	return aCtx.createForwardRedirect(
		"/entityParentView-smo_visitProtocol.do?id="+list[2]) ;
	
}