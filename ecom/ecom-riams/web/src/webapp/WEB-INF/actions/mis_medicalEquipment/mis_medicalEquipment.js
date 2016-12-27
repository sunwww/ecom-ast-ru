function listAll(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_medicalEquipment/list_all.jsp") ;
}
function listByDep(aForm,aCtx) {
	aCtx.request.setAttribute("secUserId", aCtx.invokeScript("WorkerService", "findLogginedSecUserId"));
	return aCtx.createForward("/WEB-INF/actions/mis_medicalEquipment/list_by_dep.jsp") ;
}