/**
 * Добавление нетрудоспособности из визита
 */
function addDisabilityByRedirectFromVisit(aForm, aCtx) {
	var id = aCtx.request.getParameter("id") ;
	if(!id) throw "Нет идентификатора визита";
	 
	var spoId = aCtx.invokeScript("SmoVisitService", "findSpoIdByVisit", id) ;
	return aCtx.createForwardRedirect(
		"/entityParentPrepareCreate-dis_disabilityCase.do?id="+spoId
	) ;
}
 
function getCalDayId(aCtx) {
	var calendarDayId = aCtx.request.getParameter("id") ;
	if(calendarDayId==null) {
		calendarDayId = aCtx.session.getAttribute("smo_visit.currentCalendarDay") ;
	} else {
		aCtx.session.setAttribute("smo_visit.currentCalendarDay", calendarDayId) ;
	}
	return calendarDayId ;
}

function getWorkFunctionId(aCtx) {
	var workFunctionId = aCtx.request.getParameter("id") ;
	if(calendarDayId==null) {
		calendarDayId = aCtx.session.getAttribute("smo_visit.currentCalendarDay") ;
	} else {
		aCtx.session.setAttribute("smo_visit.currentCalendarDay", calendarDayId) ;
	}
	return calendarDayId ;
}

function findPolyAdmissions(aForm, aCtx) {
	
	if (Packages.ru.nuzmsh.web.tags.helper.RolesHelper.checkRoles("/Policy/Mis/MedCase/Visit/ViewAll",aCtx.request)) {
		return findPolyAdmissionsByZam(aForm, aCtx) ;
	} else {
		return findPolyAdmissionsBySpec(aForm, aCtx) ;
	}
}
/**
 * Поиск направлений за определенную дату
 */
function findPolyAdmissionsBySpec(aForm, aCtx) {
	setWorkerInfo(aCtx) ;
	var calendarDayId = getCalDayId(aCtx) ;
	if(calendarDayId==null) {
		calendarDayId = aCtx.session.getAttribute("smo_visit.currentCalendarDay") ;
	} else {
		aCtx.session.setAttribute("smo_visit.currentCalendarDay", calendarDayId) ;
	}
	var col = aCtx.invokeScript("SmoDirectionService", "findPolyAdmissions", calendarDayId) ;
	//aCtx.request.setAttribute("list", col) ;
	if (calendarDayId==null) calendarDayId='-1' ;
	aCtx.request.setAttribute("calenDayId",calendarDayId) ;
	return aCtx.createForward("/WEB-INF/actions/smo_visit/listBySelectedDate.jsp") ;
}

function findPolyAdmissionsByZam(aForm, aCtx) {
	
	var calendarDayId = getCalDayId(aCtx) ;
	var col = null;
	var workFuncId;
	
	if(calendarDayId==null) {
		calendarDayId = aCtx.session.getAttribute("smo_visit.currentCalendarDay") ;
		setWorkFunctionInfo(aCtx,null,null) ;
		//col=new java.util.ArrayList() ;
		
	} else {
		aCtx.session.setAttribute("smo_visit.currentCalendarDay", calendarDayId) ;
		workFuncId = aCtx.invokeScript("WorkerService", "getWorkFunctionByCalenDay", calendarDayId) ;
		setWorkFunctionInfo(aCtx,calendarDayId,workFuncId) ;
		//col = aCtx.invokeScript("SmoDirectionService", "findPolyAdmissionsByWorkFunc", calendarDayId, workFuncId) ;
		
	}
	if (calendarDayId==null) calendarDayId='-1' ;
	aCtx.request.setAttribute("calenDayId",calendarDayId) ;
	//aCtx.request.setAttribute("list", col ) ;
	return aCtx.createForward("/WEB-INF/actions/smo_visit/listBySelectedDate.jsp") ;
}

function setWorkerInfo(aCtx) {
	var workerInfo = aCtx.invokeScript("WorkerService", "findLogginedWorkerName") ;
	var calDayId = getCalDayId(aCtx) ;
	var append = "" ;
	if(calDayId!=null) {
		var d = aCtx.invokeScript("WorkerService", "getWorkCalendarDayCalendarDate", calDayId) ;
		var append = ", дата: "+d ;
	}
	aCtx.request.setAttribute("worker", workerInfo + append) ;
}
function setWorkFunctionInfo(aCtx,aCalenDayId,aWorkFuncId) {
	var workerInfo ="Выберите рабочую функцию" ;
	var append="" ;
	if(aCalenDayId!=null) {
		workerInfo = aCtx.invokeScript("WorkerService", "getWorkFunctionInfo", aWorkFuncId) ; 
		var d = aCtx.invokeScript("WorkerService", "getWorkCalendarDayCalendarDate", aCalenDayId) ;
		append = ", дата: "+d ;
	} else {
	}
	aCtx.request.setAttribute("worker", workerInfo + append) ;
}

/**
 * Выбор даты направления
 */
function findByAllPlanDates(aForm, aCtx) {
	setWorkerInfo(aCtx) ;
	var workerId = aCtx.invokeScript("WorkerService", "findLogginedSecUserId") ;
	aCtx.request.setAttribute("workerId", workerId+"") ;	
	//throw workerId+"";
	return aCtx.createForward("/WEB-INF/actions/smo_visit/listByAllPlanDates.jsp") ;
}

/**
 * Закрыть СПО
 */
function closeSpo(aForm, aCtx) {
	var id = aCtx.request.getParameter("id") ;
	if(!id) throw "Нет идентификатора визита";
	var spoId = aCtx.invokeScript("SmoVisitService", "closeSpoByVisit", id) ;
	return aCtx.createForwardRedirect(
		"/entityView-smo_spo.do?id="+spoId
	) ;
}
//Не явка на прием
function noPatient(aForm, aCtx) {
	var id = aCtx.request.getParameter("id") ;
	if(!id) throw "Нет идентификатора визита";
	var visitId = aCtx.invokeScript("SmoVisitService", "visitNoPatient", id) ;
	return aCtx.createForwardRedirect(
		"/entityView-smo_visit.do?id="+visitId
	) ;
}
function infoByPatient(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/smo_visit/list_by_patient.jsp") ;
}