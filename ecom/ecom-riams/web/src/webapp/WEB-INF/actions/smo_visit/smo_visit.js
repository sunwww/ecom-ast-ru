function journal_diagnosis_data(aForm, aCtx) {
	var request = aCtx.request ;
	var id=request.getParameter("id").split(":") ;
	//${mkbCode}||':${mkbLike}:${dep}:${servStream}:${fldDate}
	var mkbCode = "and mkb.code = '"+id[0]+"'"
	if (+id[1]>0) {
		
		mkbCode=" and mkb.code like '"+id[0]+"%'" ;
	}
	var filterAdd=id[8] ;
	
	if (filterAdd!=null) {
		var fs =filterAdd.split('-') ; 
		if (fs.length>1) {
			mkbCode=mkbCode+" and mkb.code between '"+fs[0]+"' and '"+fs[1]+"'" ;
		} else {
			mkbCode=mkbCode+" and mkb.code like '"+filterAdd+"%'" ;
		}
	} 
	request.setAttribute("mkbCode",mkbCode) ;
	request.setAttribute("department",id[2]!=null?id[2]:"") ;
	request.setAttribute("serviceStream",id[3]!=null?id[3]:"") ;
	//request.setAttribute("fldDate",id[4]) ;
	request.setAttribute("dateBegin",id[4]) ;
	request.setAttribute("dateEnd",id[5]) ;
	request.setAttribute("workFunction",id[6]) ;
	request.setAttribute("priority",id[7]!=null?id[7]:"") ;
	//request.setAttribute("registrationType",id[8]!=null?id[8]:"") ;
	//request.setAttribute("prioritySql",id[9]!=null?id[9]:"") ;
	//request.setAttribute("regType",id[10]!=null?id[10]:"") ;
	//request.setAttribute("bedSubTypeSql",id[9]!=null?id[9]:"") ;
	//prioritySql} ${regType} ${bedSubTypeSql
	return aCtx.createForward("/WEB-INF/actions/smo_visit/list_diagnosis_data.jsp") ;
}

/**
 * Добавление нетрудоспособности из визита
 */
function addDisabilityByRedirectFromVisit(aForm, aCtx) {
	var id = aCtx.request.getParameter("id") ;
	if(!id) throw "Нет идентификатора визита";
	 
	var spoId = aCtx.invokeScript("SmoVisitService", "findSpoIdByVisit", id) ;
	return aCtx.createForwardRedirect(
		"/entityParentPrepareCreate-dis_case.do?id="+spoId
	) ;
}
 
function getCalDayId(aCtx) {
	var calendarDayId = aCtx.request.getParameter("id") ;
	//throw "3"+calendarDayId ;
	if (calendarDayId==null||calendarDayId=="null") calendarDayId=null ;
	if(calendarDayId==null) {
		calendarDayId = aCtx.session.getAttribute("smo_visit.currentCalendarDay") ;
		//throw "2"+calendarDayId ;
		if (calendarDayId==null) {
			calendarDayId=aCtx.invokeScript("SmoDirectionService", "findCurrentWorkCalendarDayByUser") ;
			//throw "1"+calendarDayId ;
			if (calendarDayId!=null) {
				aCtx.session.setAttribute("smo_visit.currentCalendarDay", calendarDayId) ;
			}
		}
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

function findPolyAdmissions(aForm, aCtx, aPath) {
	if (aPath==null || aPath=='') {
		aPath = "/WEB-INF/actions/smo_visit/listBySelectedDate.jsp" ;
	}
	if (Packages.ru.nuzmsh.web.tags.helper.RolesHelper.checkRoles("/Policy/Mis/MedCase/Visit/ViewAll",aCtx.request)) {
		return findPolyAdmissionsByZam(aForm, aCtx, aPath) ;
	} else {
		return findPolyAdmissionsBySpec(aForm, aCtx, aPath) ;
	}
}
function findOtherFunctionsPolyAdmissions(aForm, aCtx) {
	return findPolyAdmissions(aForm, aCtx, "/WEB-INF/actions/smo_visit/listBySelectedDate_otherFunction.jsp") ;
}
function findOtherDaysPolyAdmissions(aForm, aCtx) {
	return findPolyAdmissions(aForm, aCtx, "/WEB-INF/actions/smo_visit/listBySelectedDate_otherDays.jsp") ;
}
/**
 * Поиск направлений за определенную дату
 */
function findPolyAdmissionsBySpec(aForm, aCtx, aPathList) {
	
	var calendarDayId = getCalDayId(aCtx) ;
	if(calendarDayId==null) {
		setWorkerInfo(aCtx) ;
	} else {
		var workFuncId = aCtx.invokeScript("WorkerService", "getWorkFunctionByCalenDay", calendarDayId) ;
		setWorkFunctionInfo(aCtx,calendarDayId,workFuncId) ;
		aCtx.session.setAttribute("smo_visit.currentCalendarDay", calendarDayId) ;
	}
	//var col = aCtx.invokeScript("SmoDirectionService", "findPolyAdmissions", calendarDayId) ;
	//aCtx.request.setAttribute("list", col) ;
	if (calendarDayId==null) calendarDayId='-1' ;
	aCtx.request.setAttribute("calenDayId",calendarDayId) ;
	return aCtx.createForward(aPathList) ;
}

function findPolyAdmissionsByZam(aForm, aCtx, aPathList) {
	
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
	return aCtx.createForward(aPathList) ;
}

function setWorkerInfo(aCtx) {
	var workerInfo = aCtx.invokeScript("WorkerService", "findLogginedWorkerName") ;
	var calDayId = getCalDayId(aCtx) ;
	var append = "" ;
	var d ='' ;
	if(calDayId!=null) {
		d = aCtx.invokeScript("WorkerService", "getWorkCalendarDayCalendarDate", calDayId) ;
		var append = ", дата: "+d ;
	} else {
		
	}
	aCtx.request.setAttribute("worker", workerInfo + append) ;
	aCtx.request.setAttribute("date_on_which_the_doctor", d==''?'current_date':"to_date('"+d+"','dd.mm.yyyy')") ;
}
function setWorkFunctionInfo(aCtx,aCalenDayId,aWorkFuncId) {
	var workerInfo ="Выберите рабочую функцию" ;
	var append="" ;
	var d="" ;
	if(aCalenDayId!=null) {
		workerInfo = aCtx.invokeScript("WorkerService", "getWorkFunctionInfo", aWorkFuncId) ; 
		d = aCtx.invokeScript("WorkerService", "getWorkCalendarDayCalendarDate", aCalenDayId) ;
		append = ", дата: "+d ;
	} else {
	}
	aCtx.request.setAttribute("worker", workerInfo + append) ;
	aCtx.request.setAttribute("date_on_which_the_doctor", d==''?'current_date':"to_date('"+d+"','dd.mm.yyyy')") ;
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
function infoShortByPatient(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/smo_visit/listShort_by_patient.jsp") ;
}
function infoByPatient(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/smo_visit/list_by_patient.jsp") ;
}
function replaceWF(aForm,aCtx) {
	var id = aCtx.request.getParameter("id") ;
	try {
		
		//var d = aCtx.invokeScript("WorkerService", "getWorkCalendarDayCalendarDate", calendarDayId) ;
		var wf = aCtx.invokeScript("WorkerService", "getWorkFunctionByCalenDay", id) ;
		aCtx.invokeScript("WorkerService", "replaceWorkFunction", wf) ;
		///var wcd=aCtx.invokeScript("WorkerService", "getWorkCalendarDayByWCAndDate",id, d) ;
		return aCtx.createForward("/js-smo_visit-findPolyAdmissions.do?tmp=4324123&id="+id) ;
	} catch(e) {
		//var calendarDayId = getCalDayId(aCtx) ;
		//var d = aCtx.invokeScript("WorkerService", "getWorkCalendarDayCalendarDate", calendarDayId) ;
		//var wcd=aCtx.invokeScript("WorkerService", "getWorkCalendarDayByWCAndDate",id, d) ;
		return aCtx.createForward("/js-smo_visit-findPolyAdmissions.do?id="+id) ;
	}
}

