function print(aForm, aCtx) {
	//var service = aCtx.getService(Packages.ru.ecom.ejb.services.vocentity.IVocEntityService) ;
	//aCtx.request.setAttribute("vocEntityInfo"
	//	, service.getVocEntityInfo(aCtx.request.getParameter("id"))) ;
	return aCtx.createForward("/WEB-INF/actions/pres_prescriptList/print.jsp") ;
}
function list_by_department(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/pres_prescriptList/list_lab_prescript_by_department.jsp") ;
}
function journal_by_control(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/pres_prescriptList/list_lab_prescript.jsp") ;
}