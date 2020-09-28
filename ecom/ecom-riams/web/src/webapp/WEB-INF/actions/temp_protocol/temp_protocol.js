function listTemplate(aForm, aCtx) {
	var isFindUser = +getIsFindByUser(aCtx) ;
	var col ;
	var req = aCtx.request;
	if (isFindUser===1) {
		col = aCtx.invokeScript("TemplateService", "findListTemplateByUser"
				, req.getParameter("id"), 25, req.getParameter("next"),req.getParameter("search") ) ;
	} else {
		col = aCtx.invokeScript("TemplateService", "findListTemplate"
				, req.getParameter("id"), 25, req.getParameter("next"),req.getParameter("search")  ) ;
	}
	aCtx.request.setAttribute("list", col) ;
    return aCtx.createForward("/WEB-INF/actions/temp_protocol/list.jsp") ;
}

function getIsFindByUser(aCtx) {
	var isUsername = aCtx.request.getParameter("findByUsername") ;
	if (isUsername!=null ) {
		if (+isUsername==1||isUsername=="true") {
			aCtx.session.setAttribute("temp_protocol.findByUsername",1) ;
			isUsername =1;
		} else {
			aCtx.session.setAttribute("temp_protocol.findByUsername",0) ;
			isUsername =0;
		}
	} else {
		isUsername = aCtx.session.getAttribute("temp_protocol.findByUsername") ;
		if (isUsername==null) {
			isUsername=1 ;
		} else {
			isUsername=0 ;
		}
	}
	aCtx.request.setAttribute("findByUsername",isUsername) ;
	aCtx.request.setAttribute("findByUsernameC",isUsername>0?"true":"false") ;
	return isUsername ;
}