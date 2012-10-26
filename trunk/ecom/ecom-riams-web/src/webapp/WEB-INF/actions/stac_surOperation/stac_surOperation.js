function viewOperation(aForm,aCtx) {
	var id=aCtx.request.getParameter("id") ;
	var list =id.split("!") ;
	return aCtx.createForwardRedirect(
			"/entityParentView-stac_surOperation.do?id="+list[2]) ;
	
}
function viewShortOperation(aForm,aCtx) {
	var id=aCtx.request.getParameter("id") ;
	var list =id.split("!") ;
	return aCtx.createForwardRedirect(
		"/entityShortView-stac_surOperation.do?id="+list[2]) ;
	
}