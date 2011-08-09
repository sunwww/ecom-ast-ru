function addTalk(aForm, aCtx) {
	var id=aCtx.request.getParameter("id") ;
	var col = aCtx.invokeScript("TicketService", "addTalk"
        	, id ) ;
        return aCtx.createForward("/poly_ticket_addTalk.do?id="+id) ;
}
function listDiag(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/poly_ticket/list_diag.jsp") ;
}
function journalRegisterVisit(aForm,aCtx) {
	var form = aCtx.session.getAttribute("poly_journalBySpecForm");
	if (form!=null) {
		var col = aCtx.invokeScript("TicketService","journalRegisterVisit", 
			form.beginDate+":"+form.finishDate+":"+form.specialist+":"+form.rayon+":"+form.primaryInYear) ;
		aCtx.request.setAttribute('listRegisterVisit',col) ;
	} else {
		aCtx.request.setAttribute('listRegisterVisit',new java.util.ArrayList()) ;
	}
	return aCtx.createForward("/poly_journalRegisterVisit_list.do") ;
}