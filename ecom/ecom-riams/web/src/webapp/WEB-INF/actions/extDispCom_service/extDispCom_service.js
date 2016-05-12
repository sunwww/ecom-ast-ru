function edit(aForm,aCtx) {
	/*
        var col = aCtx.invokeScript("ExpertService", "findListCard"
        	, aCtx.request.getParameter("id"), 25, aCtx.request.getParameter("next") ) ;
        aCtx.request.setAttribute("list", col) ;

	 */
	
	return aCtx.createForward("/WEB-INF/actions/extDispCom_service/edit.jsp") ;
}
function editPlan1(aForm,aCtx) {
	/*
        var col = aCtx.invokeScript("ExpertService", "findListCard"
        	, aCtx.request.getParameter("id"), 25, aCtx.request.getParameter("next") ) ;
        aCtx.request.setAttribute("list", col) ;

	 */
	
	return aCtx.createForward("/WEB-INF/actions/extDisp_vocPlan/service1.jsp") ;
}
function editPlan(aForm,aCtx) {
/*
        var col = aCtx.invokeScript("ExpertService", "findListCard"
        	, aCtx.request.getParameter("id"), 25, aCtx.request.getParameter("next") ) ;
        aCtx.request.setAttribute("list", col) ;

 */
	
	return aCtx.createForward("/WEB-INF/actions/extDisp_vocPlan/service.jsp") ;
}
function save(aForm,aCtx) {
	var cntExam = +aCtx.request.getParameter("cntExam");
	var cntVisit = +aCtx.request.getParameter("cntVisit");
	var card = aCtx.request.getParameter("card");
	var fldExam = ["examId","examServiceType","examServiceDate","examIsPathology"] ;
	var fldPe = "" ;
	var fldPv = "" ;
	var fldVisit = ["visitId","visitServiceType","visitServiceDate","visitRecommendation","visitIsEtdccSuspicion","workFunction","Idc10"] ;
	//var fldPv = ["","","","",""] ;
	for (var i=0;i<cntExam;i++) {
		var fldPe=card+":" ;
		for (var j=0; j<fldExam.length;j++) {
			var par=(aCtx.request.getParameter(fldExam[j]+i)!=null?aCtx.request.getParameter(fldExam[j]+i):"") ;
			if (par!="") {
				fldPe=fldPe+par.trim()+":" ;
			} else {
				fldPe=fldPe+":" ;
			}
		}
		aCtx.invokeScript("ExpertService", "createExtDispExamService",fldPe) ;
	}
	//throw "fsadf"+cntVisit ;
	for (var i=0;i<cntVisit;i++) {
		var fldPv=card+":" ;
		for (var j=0; j<fldVisit.length;j++) {
			var par=(aCtx.request.getParameter(fldVisit[j]+i)!=null?aCtx.request.getParameter(fldVisit[j]+i):"") ;
			if (par!="") {
				fldPv=fldPv+par.trim()+":" ;
			} else {
				fldPv=fldPv+":" ;
			}
		}
		aCtx.invokeScript("ExpertService", "createExtDispVisitService",fldPv) ;
	}
	return aCtx.createForward("/entityParentView-extDispCom_card.do?id="+card) ;
}