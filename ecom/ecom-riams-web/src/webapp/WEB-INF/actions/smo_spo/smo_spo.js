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
function spoClosedDateLastVisit(aForm, aCtx) {
	var ids = aCtx.request.getParameterValues("id") ;
	var curator = aCtx.request.getParameter("curator") ;
	var department = aCtx.request.getParameter("department") ;
	for (var i=0;i<ids.length;i++) {
		try {
			var id=ids[i] ;
			aCtx.invokeScript("SmoVisitService", "closeSpo", id) ;
		} catch(e) {
			
		}
	}
	return aCtx.createForwardRedirect("/smo_journal_openSpo.do?department"+department+"&curator="+curator) ;
}
function spoClosedCurrentDate(aForm, aCtx) {
	var ids = aCtx.request.getParameterValues("id") ;
	var curator = aCtx.request.getParameter("curator") ;
	var department = aCtx.request.getParameter("department") ;
	var ids = aCtx.request.getParameterValues("id") ;
	for (var i=0;i<ids.length;i++) {
		try {
			var id=ids[i] ;
			aCtx.invokeScript("SmoVisitService", "closeSpoByCurrentDate", id) ;
		} catch(e) {
			
		}
	}
	return aCtx.createForwardRedirect("/smo_journal_openSpo.do?department"+department+"&curator="+curator) ;
}
/** создание талонов по отказам от госпитализации **/
function createNewVisitByDenied(aForm,aCtx) {
	var department = aCtx.request.getParameter("department") ;
	var dateBegin = aCtx.request.getParameter("dateBegin") ;
	var dateEnd = aCtx.request.getParameter("dateEnd") ;
	aCtx.invokeScript("SmoVisitService", "createNewVisitByDenied",department,dateBegin,dateEnd) ;
	return aCtx.createForwardRedirect("/stac_journal_denied_without_diagnosis.do?department="+department) ;
}