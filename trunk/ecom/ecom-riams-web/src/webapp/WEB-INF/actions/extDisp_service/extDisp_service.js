function edit(aForm,aCtx) {
/*
        var col = aCtx.invokeScript("ExpertService", "findListCard"
        	, aCtx.request.getParameter("id"), 25, aCtx.request.getParameter("next") ) ;
        aCtx.request.setAttribute("list", col) ;

 */
	
	return aCtx.createForward("/WEB-INF/actions/extDisp_service/edit.jsp") ;
}
function save(aForm,aCtx) {
	var cntExam = +aCtx.request.getParameter("cntExam");
	var cntVisit = +aCtx.request.getParameter("cntVisit");
	var card = aCtx.request.getParameter("card");
	var fldExam = ["examId","examServiceType","examServiceDate","examIsPathology"] ;
	var fldPe = "" ;
	var fldVisit = ["visitId","visitServiceType","visitServiceDate","visitRecommendation","visitIsEtdccSuspicion"] ;
	//var fldPv = ["","","","",""] ;
	for (var i=0;i<cntExam;i++) {
		fldPe=card+":" ;
		for (var j=0; j<fldExam.length;j++) {
			var par=(aCtx.request.getParameter(fldExam[j]+i)!=null?aCtx.request.getParameter(fldExam[j]+i):"") ;
			fldPe=fldPe+par.trim()+":" ;
		}
		aCtx.invokeScript("ExpertService", "createExtDispExamService",fldPe) ;
	}
	return aCtx.createForward("/entityParentView-extDisp_card.do?id="+card) ;
}