function createComissia(aForm, aCtx) {
	var id = aCtx.request.getParameter("id") ;
	var col = aCtx.invokeScript("SmoDirectionService", "createComissia", id) ;
	return aCtx.createForward("/WEB-INF/actions/psych_careCard/edit.jsp") ;
}