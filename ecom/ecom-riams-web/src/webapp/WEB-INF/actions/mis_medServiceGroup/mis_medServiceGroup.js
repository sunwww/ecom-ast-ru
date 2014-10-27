function updateStartDate(aForm, aCtx) {
	var ids=Packages.ru.ecom.jaas.web.action.JaasUtil.convertToString(aCtx.request.getParameterValues("ids"));
	var id=aCtx.request.getParameter("id");
	aCtx.invokeScript("VocService", "updateStartDateByMedService", id,ids,aCtx.request.getParameter("plandate") );
	return aCtx.createForward("/entityParentView-mis_medServiceGroup.do?id="+id) ;
}
function updateEndDate(aForm, aCtx) {
	var ids=Packages.ru.ecom.jaas.web.action.JaasUtil.convertToString(aCtx.request.getParameterValues("ids"));
	var id=aCtx.request.getParameter("id") ;
	aCtx.invokeScript("VocService", "updateEndDateByMedService", id,ids,aCtx.request.getParameter("plandate"));
	return aCtx.createForward("/entityParentView-mis_medServiceGroup.do?id="+id) ;
}
function updateWorkFunction(aForm, aCtx) {
	var ids=Packages.ru.ecom.jaas.web.action.JaasUtil.convertToString(aCtx.request.getParameterValues("ids"));
	var id=aCtx.request.getParameter("id") ;
	aCtx.invokeScript("VocService", "updateWorkFunctionByMedService", ids,aCtx.request.getParameter("workFunction"),aCtx.request.getParameter("lpu"),aCtx.request.getParameter("action"));
	return aCtx.createForward("/entityParentView-mis_medServiceGroup.do?id="+id) ;
}
function deleteMedService(aForm, aCtx) {
	var ids=Packages.ru.ecom.jaas.web.action.JaasUtil.convertToString(aCtx.request.getParameterValues("ids"));
	var id=aCtx.request.getParameter("id") ;
	aCtx.invokeScript("VocService", "deleteByMedService", ids);
	return aCtx.createForward("/entityParentView-mis_medServiceGroup.do?id="+id) ;
}