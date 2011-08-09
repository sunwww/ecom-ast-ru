function list(aForm, aCtx) {
        var col = aCtx.invokeScript("ExpertService", "findListCard"
        	, aCtx.request.getParameter("id"), 25, aCtx.request.getParameter("next") ) ;
        aCtx.request.setAttribute("list", col) ;
        return aCtx.createForward("/WEB-INF/actions/expert_card/list_simple.jsp") ;
}
function listIncompletely(aForm, aCtx) {
        //var col = aCtx.invokeScript("ExpertService", "findListCard"
        //	, aCtx.request.getParameter("id"), 25, aCtx.request.getParameter("next") ) ;
        //aCtx.request.setAttribute("list", col) ;
        return aCtx.createForward("/WEB-INF/actions/expert_card/listCardIncompletely.jsp") ;
}
function listIncompletelyByDepartment(aForm, aCtx) {
        //var col = aCtx.invokeScript("ExpertService", "findListCard"
        //	, aCtx.request.getParameter("id"), 25, aCtx.request.getParameter("next") ) ;
        //aCtx.request.setAttribute("list", col) ;
        return aCtx.createForward("/WEB-INF/actions/expert_card/list_cardIncompletelyByDepartment.jsp") ;
}