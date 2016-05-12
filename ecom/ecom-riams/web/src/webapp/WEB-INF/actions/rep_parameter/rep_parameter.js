function str_list(aForm, aCtx) {
	var id = aCtx.request.getParameter("id") ;
	if(!id) throw "Нет идентификатора отчета";
	//var spoId = aCtx.invokeScript("SmoVisitService", "closeSpo", id) ;
	return aCtx.createForward(
		"/WEB-INF/actions/rep_parameter/list_report_parameter.jsp?id="+id
	) ;
}
function report_list(aForm, aCtx) {
	return aCtx.createForward(
		"/WEB-INF/actions/rep_parameter/list_report.jsp"
	) ;
}