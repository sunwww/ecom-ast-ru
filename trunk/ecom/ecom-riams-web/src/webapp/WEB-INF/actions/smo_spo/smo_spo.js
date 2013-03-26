/**
 * Закрыть СПО
 */
function closeSpo(aForm, aCtx) {
	var id = aCtx.request.getParameter("id") ;
	if(!id) throw "Нет идентификатора визита";
	var spoId = aCtx.invokeScript("SmoVisitService", "closeSpo", id) ;
	return aCtx.createForwardRedirect(
		"/entityView-smo_spo.do?id="+id
	) ;
}
/**
 * Открыть СПО
 */
function reopenSpo(aForm, aCtx) {
	var id = aCtx.request.getParameter("id") ;
	if(!id) throw "Нет идентификатора визита";
	var spoId = aCtx.invokeScript("SmoVisitService", "reopenSpo", id) ;
	return aCtx.createForwardRedirect(
		"/entityView-smo_spo.do?id="+id
	) ;
}
