function listWF(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/secuser/replaceCurrentWorkFunction.jsp") ;
}
function replaceWF(aForm,aCtx) {
	var id = aCtx.request.getParameter("id") ;
	aCtx.invokeScript("WorkerService", "replaceWorkFunction", id) ;
	return listWF(aForm,aCtx) ;
}