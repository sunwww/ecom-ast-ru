function listTemplate(aForm, aCtx) {
        var col = aCtx.invokeScript("TemplateService", "findListPrescriptTemplate"
        	, aCtx.request.getParameter("id"), 25, aCtx.request.getParameter("next") ) ;
        aCtx.request.setAttribute("list", col) ;
        return aCtx.createForward("/WEB-INF/actions/pres_template/list.jsp") ;
}