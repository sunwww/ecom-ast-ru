function listByDate(aForm,aCtx){
//"select id, number, phoneDate, phoneTime,phone,recieverOrganization from PhoneMessage &#xA;where phoneMessageType_id=1 and phoneDate=${param.id}" ;
	
	var id=aCtx.request.getParameter("id").split(":") ;
	aCtx.request.setAttribute("paramsPeriod"," and "+id[3]+"=to_date('"+id[4]+"','dd.mm.yyyy')") ;
	aCtx.request.setAttribute("messageType", (+id[5]>0)?" and pm.phoneMessageType_id='"+id[5]+"'":"") ;
	aCtx.request.setAttribute("messageSubType", (+id[6]>0)?" and pm.phoneMessageSubType_id='"+id[6]+"'":"") ;
	return listRecord(aCtx,id) ;
	//return aCtx.createForward("/WEB-INF/actions/stac_criminalMessages/listByDate.jsp") ;
}
function listByHospital(aForm,aCtx) {
	aCtx.request.setAttribute("addParam"," and m.deniedHospitalizating_id is null") ;
	return listSwod(aCtx) ;
}
function listByDenied(aForm,aCtx) {
	aCtx.request.setAttribute("addParam"," and m.deniedHospitalizating_id is not null") ;
	return listSwod(aCtx) ;
}
function listByObr(aForm,aCtx) {
	return listSwod(aCtx) ;
}
function listSwod(aCtx) {
	
	var id=aCtx.request.getParameter("id").split(":") ;
	aCtx.request.setAttribute("paramsPeriod"," and "+id[3]+" between to_date('"+id[4]+"','dd.mm.yyyy') and to_date('"+id[5]+"','dd.mm.yyyy')") ;
	aCtx.request.setAttribute("department", (+id[6]>0)?" and m.department_id='"+id[6]+"'":" and m.department_id is null ") ;
	aCtx.request.setAttribute("messageType", (+id[7]>0)?" and pm.phoneMessageType_id='"+id[7]+"'":"  and pm.phoneMessageType_id is null") ;
	aCtx.request.setAttribute("messageSubType", (+id[8]>0)?" and pm.phoneMessageSubType_id='"+id[8]+"'":"") ;
	return listRecord(aCtx,id) ;
}
function listRecord(aCtx,aId) {
	var dep = +aId[2] ;
	var pHole = +aId[1] ;
	var emer = +aId[0] ;
	if (emer==1) {
		aCtx.request.setAttribute("emergency"," and m.emergency='1'") ;
	} else if (emer==2) {
		aCtx.request.setAttribute("emergency"," and (m.emergency is null or m.emergency='0')") ;
	}
	if (dep>0) {
		aCtx.request.setAttribute("department", " and ml.id='"+dep+"'") ;
	}	
	if (+pHole>0) {
		aCtx.request.setAttribute("pigeonHole"," and (m.department_id is not null and ml.pigeonHole_id='"+pHole+"' or m.department_id is null and ml1.pigeonHole_id='"+pHole+"')") ;
	}
	return aCtx.createForward("/WEB-INF/actions/stac_criminalMessages/listByDate.jsp") ;
}