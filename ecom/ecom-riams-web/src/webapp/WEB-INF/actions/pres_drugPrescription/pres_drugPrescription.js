function goto_prev(aForm, aCtx) {
	return aCtx.createForward("entityParentList-pres_drugPrescription.do?id="+aCtx.request.getParameter("id")) ;
}
function updatePlanStartDate(aForm, aCtx) {
	var ids=Packages.ru.ecom.jaas.web.action.JaasUtil.convertToString(aCtx.request.getParameterValues("ids"));
	aCtx.invokeScript("PrescriptionService", "updatePlanStartDate", ids,aCtx.request.getParameter("plandate"),aCtx.request.getParameter("plantime") );
	return aCtx.createForward("/entityParentList-pres_drugPrescription.do?id="+aCtx.request.getParameter("id")) ;
}
function updatePlanEndDate(aForm, aCtx) {
	var ids=Packages.ru.ecom.jaas.web.action.JaasUtil.convertToString(aCtx.request.getParameterValues("ids"));
	aCtx.invokeScript("PrescriptionService", "updatePlanEndDate", ids,aCtx.request.getParameter("plandate"),aCtx.request.getParameter("plantime"));
	return aCtx.createForward("/entityParentList-pres_drugPrescription.do?id="+aCtx.request.getParameter("id")) ;
}
function deletePrescription(aForm, aCtx) {
	var ids=Packages.ru.ecom.jaas.web.action.JaasUtil.convertToString(aCtx.request.getParameterValues("ids"));
	aCtx.invokeScript("PrescriptionService", "deletePrescription", ids);
	return aCtx.createForward("/entityParentList-pres_drugPrescription.do?id="+aCtx.request.getParameter("id")) ;
}
