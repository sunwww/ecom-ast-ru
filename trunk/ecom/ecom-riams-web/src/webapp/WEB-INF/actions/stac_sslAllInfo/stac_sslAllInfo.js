function report007(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/stac_sslAllInfo/report007.jsp") ;
}

/**
 * Выбор даты поступления
 */
function findByDate(aForm, aCtx) {
	var id = aCtx.request.getParameter("id").split(":") ;
	if (+id[3]==3) {
		aCtx.request.setAttribute("paramPeriod"," and m.dateFinish is null") ;
	} else {
		aCtx.request.setAttribute("paramPeriod"," and m."+id[4]+"=to_date('"+id[5]+"','dd.mm.yyyy')")
	}
	
	return listRecord(aCtx,id) ;
}
function findHospByPeriod(aForm, aCtx) {
	var id = aCtx.request.getParameter("id").split(":") ;
	if (+id[3]==3) {
		aCtx.request.setAttribute("paramPeriod"," and m.dateFinish is null") ;
	} else {
		aCtx.request.setAttribute("paramPeriod"," and m."+id[4]+" between to_date('"+id[5]+"','dd.mm.yyyy') and to_date('"+id[6]+"','dd.mm.yyyy')")
	}
	var dep = +id[7] ;
	if (dep>0) aCtx.request.setAttribute("addParam", " and m.department_id='"+dep+"'") ;
	return listRecord(aCtx,id) ;
}
function findDeniedByPeriod(aForm, aCtx) {
	
	var id = aCtx.request.getParameter("id").split(":") ;
	if (+id[3]==3) {
		aCtx.request.setAttribute("paramPeriod"," and m.dateStart=current_date") ;
	} else {
		aCtx.request.setAttribute("paramPeriod"," and m."+id[4]+" between to_date('"+id[5]+"','dd.mm.yyyy') and to_date('"+id[6]+"','dd.mm.yyyy')")
	}
	var dep = +id[7] ;
	if (dep>0) aCtx.request.setAttribute("addParam", " and m.deniedHospitalizating_id='"+dep+"'") ;
	
	return listRecord(aCtx,id) ;
}
function listRecord(aCtx,aId) {
	var dep = +aId[2] ;
	var pHole = +aId[1] ;
	var emer = +aId[0] ;
	if (emer==1) {
		request.setAttribute("emergency"," and m.emergency='1'") ;
	} else if (emer==2) {
		request.setAttribute("emergency"," and (m.emergency is null or m.emergency='0')") ;
	}
	if (dep>0) {
		request.setAttribute("department", " and ml.id='"+dep+"'") ;
	}	
	if (+pHole>0) {
		request.setAttribute("pigeonHole"," and (m.department_id is not null and ml.pigeonHole_id='"+pHole+"' or m.department_id is null and ml1.pigeonHole_id='"+pHole+"')") ;
	}
	//request.setAttribute("dateI",) ;
	return aCtx.createForward("/WEB-INF/actions/stac_sslAllInfo/listByDate.jsp") ;
}
/*
function address(aForm, aCtx) {
    var col = aCtx.invokeScript("HospitalPrintService", "addressUpdate"
        	, "" ) ;
	
}
*/