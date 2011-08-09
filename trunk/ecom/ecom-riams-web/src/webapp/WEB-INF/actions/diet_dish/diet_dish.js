function listTemplate(aForm, aCtx) {
        var col = aCtx.invokeScript("TemplateService", "findListDish"
        	, aCtx.request.getParameter("id"), 25, aCtx.request.getParameter("next") ) ;
        aCtx.request.setAttribute("list", col) ;
        return aCtx.createForward("/WEB-INF/actions/diet_dish/list.jsp") ;
}