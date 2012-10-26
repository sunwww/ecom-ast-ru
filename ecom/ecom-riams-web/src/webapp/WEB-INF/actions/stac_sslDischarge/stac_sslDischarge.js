function viewProtocol(aForm,aCtx) {
	var id=aCtx.request.getParameter("id") ;
	var list =id.split("!") ;
	return aCtx.createForwardRedirect(
			"/entityParentView-stac_sslDischarge.do?id="+list[2]) ;
	
}
function viewShortProtocol(aForm,aCtx) {
	var id=aCtx.request.getParameter("id") ;
	var list =id.split("!") ;
	return aCtx.createForwardRedirect(
		"/entityParentView-stac_sslDischarge.do?short=Short&id="+list[2]) ;
	
}