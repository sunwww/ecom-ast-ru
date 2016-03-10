function listTemplat1e(aForm, aCtx) {
        var col = aCtx.invokeScript("TemplateService", "findListPrescriptTemplate"
        	, aCtx.request.getParameter("id"), 25, aCtx.request.getParameter("next") ) ;
        aCtx.request.setAttribute("list", col) ;
        return aCtx.createForward("/WEB-INF/actions/pres_template/list.jsp") ;
}
function listTemplate(aForm, aCtx) {
	var isFindUser = +getIsFindByUser(aCtx) ;
	var col ;
	if (isFindUser==1) {
		col = aCtx.invokeScript("TemplateService", "findListPrescriptTemplateByUser"
				, aCtx.request.getParameter("id"), 25, aCtx.request.getParameter("next") ) ;
	} else {
		col = aCtx.invokeScript("TemplateService", "findListPrescriptTemplate"
				, aCtx.request.getParameter("id"), 25, aCtx.request.getParameter("next") ) ;
	}
	aCtx.request.setAttribute("list", col) ;
    return aCtx.createForward("/WEB-INF/actions/pres_template/list.jsp") ;
}

function getIsFindByUser(aCtx) {
	var isUsername = aCtx.request.getParameter("findByUsername") ;
	if (isUsername!=null ) {
		if (+isUsername==1||isUsername=="true") {
			aCtx.session.setAttribute("pres_template.findByUsername",1) ;
			var isUsername ='1';
		} else {
			aCtx.session.setAttribute("pres_template.findByUsername",0) ;
			var isUsername ='0';
		}
	} else {
		var isUsername = aCtx.session.getAttribute("pres_template.findByUsername") ;
		if (isUsername==null) {
			isUsername='1' ;
		} else {
			isUsername='0' ;
		}
	}
	//throw "iiiiiiiiiiiiiiii????????="+isUsername;
	aCtx.request.setAttribute("findByUsername",isUsername) ;
	return +isUsername ;
}