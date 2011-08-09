function listNext(aForm, aCtx) {
        var col = aCtx.invokeScript("TemplateService", "findListDeleteJournal"
        	, aCtx.request.getParameter("id"), 50, aCtx.request.getParameter("next") ) ;
        aCtx.request.setAttribute("list", col) ;
        return aCtx.createForward("/WEB-INF/actions/ecom_deleteJournal/list.jsp") ;
}