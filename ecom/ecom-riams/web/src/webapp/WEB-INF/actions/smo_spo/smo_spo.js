function cost_case(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/smo_spo/cost_case.jsp") ;
}
/* Удалить пустые СПО */
function deleteEmptySpo(aForm,aCtx) {
	//try {
		aCtx.invokeScript("SmoVisitService", "deleteEmptySpo") ;
		return aCtx.createForward("/smo_journal_closeSpo.do") ;
	//} catch(e) {
	//	return aCtx.createForward("/smo_journal_closeSpo.do") ;
	//}

}
/**
 * Закрыть СПО
 */
function closeSpo(aForm, aCtx) {
	var id = aCtx.request.getParameter("id") ;
	if(!id) throw "Нет идентификатора визита";
	var spoId = aCtx.invokeScript("SmoVisitService", "closeSpo", id) ;
	return aCtx.createForwardRedirect(
		"/entityView-smo_spo.do?id="+id
	) ;
}

/**
 * Открыть СПО
 */
function reopenSpo(aForm, aCtx) {
	var id = aCtx.request.getParameter("id") ;
	if(!id) throw "Нет идентификатора визита";
	var spoId = aCtx.invokeScript("SmoVisitService", "reopenSpo", id) ;
	return aCtx.createForwardRedirect(
		"/entityView-smo_spo.do?id="+id
	) ;
}
function spoClosedDateLastVisit(aForm, aCtx) {
	var method = aCtx.request.getParameter("method");
	var ids = aCtx.request.getParameter("closeSPOIds").split(",");
	var curator = aCtx.request.getParameter("curator") ;
	var department = aCtx.request.getParameter("department") ;
	
	for (var i=0;i<ids.length;i++) {
		try {
			var id=ids[i] ;
			aCtx.invokeScript("SmoVisitService", "closeSpo", id) ;
		} catch(e) {
			
		}
	}
	return aCtx.createForwardRedirect("/smo_journal_openSpo.do?department"+department+"&curator="+curator) ;
}
function spoClosedCurrentDate(aForm, aCtx) {
	var ids = aCtx.request.getParameter("closeSPOIds").split(",");
	var curator = aCtx.request.getParameter("curator") ;
	var department = aCtx.request.getParameter("department") ;
	for (var i=0;i<ids.length;i++) {
		try {
			var id=ids[i] ;
			aCtx.invokeScript("SmoVisitService", "closeSpoByCurrentDate", id) ;
		} catch(e) {
			
		}
	}
	return aCtx.createForwardRedirect("/smo_journal_openSpo.do?department"+department+"&curator="+curator) ;
}
/** создание талонов по отказам от госпитализации **/
function createNewVisitByDenied(aForm,aCtx) {
	throw "Запрещено";
/*	var department = aCtx.request.getParameter("department") ;
	var dateBegin = aCtx.request.getParameter("dateBegin") ;
	var dateEnd = aCtx.request.getParameter("dateEnd") ;
	aCtx.invokeScript("SmoVisitService", "createNewVisitByDenied",department,dateBegin,dateEnd) ;
	return aCtx.createForwardRedirect("/stac_journal_denied_without_diagnosis.do?department="+department) ; */
}
function createNewVisitByDeniedDiary(aForm,aCtx) {
	var vocWorkFunctions = aCtx.request.getParameter("vocWorkFunctions") ;
	var vocWorkFunction = aCtx.request.getParameter("vocWorkFunction") ;
	var filterMkb = aCtx.request.getParameter("filterMkb") ;
	var dateBegin = aCtx.request.getParameter("dateBegin") ;
	var dateEnd = aCtx.request.getParameter("dateEnd") ;
	
	
	
	aCtx.invokeScript("SmoVisitService", "createNewVisitByDeniedDiary",vocWorkFunctions,vocWorkFunction,filterMkb,dateBegin,dateEnd) ;
	return aCtx.createForwardRedirect("/stac_journal_denied_without_diagnosis.do") ;
	
}
function createNewEmergencySpec(aForm,aCtx) {
	var dep = aCtx.request.getParameter("department1") ;
	var group = aCtx.request.getParameter("group") ;
	
	aCtx.invokeScript("SmoVisitService", "createNewEmergencySpec",dep,group) ;
	return aCtx.createForwardRedirect("/stac_journal_denied_without_diagnosis.do") ;

}