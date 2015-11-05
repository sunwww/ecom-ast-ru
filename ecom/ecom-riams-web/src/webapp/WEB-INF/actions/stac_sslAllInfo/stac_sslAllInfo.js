function report007(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/stac_sslAllInfo/report007.jsp") ;
}

/**
 * Выбор даты поступления
 */
function findByDate(aForm, aCtx) {
	var dateI = aCtx.request.getParameter("dateI") ;
	var dateBegin = aCtx.request.getParameter("dateBegin") ;
	var dateEnd = aCtx.request.getParameter("dateEnd") ;
	var typeDate = aCtx.request.getParameter("typeDate") ;
	if (+typeDate==3) {
		aCtx.request.setAttribute("paramPeriod"," and m.dateFinish is null") ;
	} else {
		aCtx.request.setAttribute("paramPeriod"," and m."+dateI+"=to_date('"+dateBegin+"','dd.mm.yyyy')")
	}
	
	return listRecord(aCtx) ;
}
function findHospByPeriod(aForm, aCtx) {
	var dateI = aCtx.request.getParameter("dateI") ;
	var dateBegin = aCtx.request.getParameter("dateBegin") ;
	var dateEnd = aCtx.request.getParameter("dateEnd") ;
	var typeDate = aCtx.request.getParameter("typeDate") ;
	if (+typeDate==3) {
		aCtx.request.setAttribute("paramPeriod"," and m.dateFinish is null") ;
	} else {
		aCtx.request.setAttribute("paramPeriod"," and m."+dateI+" between to_date('"+dateBegin+"','dd.mm.yyyy') and to_date('"+dateEnd+"','dd.mm.yyyy')")
	}
	//var dep = +aCtx.request.getParameter("department") ;
	//if (dep>0) aCtx.request.setAttribute("addParam", " and m.department_id='"+dep+"' and m.deniedHospitalizating_id is null") ;
	return listRecord(aCtx) ;
}
function findDeniedByPeriod(aForm, aCtx) {
	
	var dateI = aCtx.request.getParameter("dateI") ;
	var dateBegin = aCtx.request.getParameter("dateBegin") ;
	var dateEnd = aCtx.request.getParameter("dateEnd") ;
	
	var typeDate = aCtx.request.getParameter("typeDate") ;
	if (+typeDate==3) {
		aCtx.request.setAttribute("paramPeriod"," and m.dateStart=current_date") ;
	} else {
		aCtx.request.setAttribute("paramPeriod"," and m."+dateI+" between to_date('"+dateBegin+"','dd.mm.yyyy') and to_date('"+dateEnd+"','dd.mm.yyyy')")
	}
	var dep = aCtx.request.getParameter("deniedHospitalizating") ;
	if (dep>0) aCtx.request.setAttribute("addParam", " and m.deniedHospitalizating_id='"+dep+"'") ;
	
	return listRecord(aCtx) ;
}
function listRecord(aCtx) {
	var dep = aCtx.request.getParameter("department") ;
	var pHole = aCtx.request.getParameter("pigeonHole") ;
	var typeEmergency = aCtx.request.getParameter("typeEmergency") ;
	var typeHosp = +aCtx.request.getParameter("typeHosp") ;
	var typeDost = +aCtx.request.getParameter("typeDost") ;
	var typePatient = aCtx.request.getParameter("typePatient") ;
	var typeFrm = aCtx.request.getParameter("typeFrm") ;
	if (+typeEmergency==1) {
		aCtx.request.setAttribute("emergency"," and m.emergency='1'") ;
	} else if (+typeEmergency==2) {
		aCtx.request.setAttribute("emergency"," and (m.emergency is null or m.emergency='0')") ;
	}
	if (+typeDost==1) {
		aCtx.request.setAttribute("dostSql"," and vpat.code='1'") ;
	} else if (+typeDost==2) {
		aCtx.request.setAttribute("dostSql"," and vpat.code='2'") ;
	} else if (+typeDost==3) {
		aCtx.request.setAttribute("dostSql"," and vpat.code='3'") ;
	}
	if (+typeHosp==1) {
		aCtx.request.setAttribute("hospSql"," and m.deniedHospitalizating_id is null") ;
	} else if (+typeHosp==2) {
		aCtx.request.setAttribute("hospSql"," and m.deniedHospitalizating_id is not null") ;
	}
	if (typeFrm=="kcp") {
		aCtx.request.setAttribute("frmSql"," and of1.voc_code='К'") ;
	} else if (typeFrm=="sam") {
		aCtx.request.setAttribute("frmSql"," and of1.voc_code='О'") ;
	}
	
	if (typePatient=="vil") {
		aCtx.request.setAttribute("patientSql"," and (oo.voc_code='643' or oo.id is null) and substring(a.kladr,1,2)='30' and a.addressIsVillage='1' ") ;
	} else if (typePatient=="city") {
		aCtx.request.setAttribute("patientSql"," and (oo.voc_code='643' or oo.id is null) and substring(a.kladr,1,2)='30' and a.addressIsCity='1' ") ;
	} else if (typePatient=="inog") {
		aCtx.request.setAttribute("patientSql"," and (oo.voc_code='643' or oo.id is null) and a.addressid is not null and substring(a.kladr,1,2)!='30' ") ;
	} else if (typePatient=="inostr") {
		aCtx.request.setAttribute("patientSql"," and oo.voc_code!='643' ") ;
	} else if (typePatient=="other") {
		aCtx.request.setAttribute("patientSql"," and (oo.voc_code='643' or oo.id is null) and (a.addressid is null or a.domen<3) ") ;
	}
	
	//throw typeFrm+"---"+aCtx.request.getAttribute("frmSql") ;
	if (dep>0) {
		aCtx.request.setAttribute("department", " and ml.id='"+dep+"'") ;
	}	
	if (+pHole>0) {
		aCtx.request.setAttribute("pigeonHole"," and (m.department_id is not null and ml.pigeonHole_id='"+pHole+"' or m.department_id is null and ml1.pigeonHole_id='"+pHole+"')") ;
	}
	//request.setAttribute("dateI",) ;
	return aCtx.createForward("/WEB-INF/actions/stac_sslAllInfo/listByDate.jsp") ;
}
