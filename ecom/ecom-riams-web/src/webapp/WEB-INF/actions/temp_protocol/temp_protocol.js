function listTemplate(aForm, aCtx) {
	var isFindUser = +getIsFindByUser(aCtx) ;
	var col ;
	if (isFindUser==1) {
		col = aCtx.invokeScript("TemplateService", "findListTemplateByUser"
				, aCtx.request.getParameter("id"), 25, aCtx.request.getParameter("next") ) ;
	} else {
		col = aCtx.invokeScript("TemplateService", "findListTemplate"
				, aCtx.request.getParameter("id"), 25, aCtx.request.getParameter("next") ) ;
	}
	aCtx.request.setAttribute("list", col) ;
    return aCtx.createForward("/WEB-INF/actions/temp_protocol/list.jsp") ;
}
function getIsFindByUser(aCtx) {
	var isUsername = aCtx.request.getParameter("findByUsername") ;
	if (isUsername!=null ) {
		if (+isUsername==1||isUsername=="true") {
			aCtx.session.setAttribute("temp_protocol.findByUsername",1) ;
			var isUsername =1;
		} else {
			aCtx.session.setAttribute("temp_protocol.findByUsername",0) ;
			var isUsername =0;
		}
	} else {
		var isUsername = aCtx.session.getAttribute("temp_protocol.findByUsername") ;
		if (isUsername==null) {
			isUsername=1 ;
		} else {
			isUsername=0 ;
		}
	}
	aCtx.request.setAttribute("findByUsername",isUsername) ;
	return isUsername ;
}