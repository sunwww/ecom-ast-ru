function addTalk(aForm, aCtx) {
	var id=aCtx.request.getParameter("id") ;
	var col = aCtx.invokeScript("TicketService", "addTalk"
			, id ) ;
	return aCtx.createForward("/entityParentView-smo_ticket.do?id="+id) ;
}
function doNotAddTalk(aForm, aCtx) {
	var id=aCtx.request.getParameter("id") ;
	var col = aCtx.invokeScript("TicketService", "doNotAddTalk"
        	, id ) ;
        return aCtx.createForward("/entityParentView-smo_ticket.do?id="+id) ;
}
function listDiag(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/smo_ticket/list_diag.jsp") ;
}
function infoDiagByMedcard(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/smo_ticket/list_diag_by_medcard.jsp") ;
}
function list(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/smo_ticket/list.jsp") ;
}