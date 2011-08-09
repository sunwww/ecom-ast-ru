function print(aForm, aCtx) {
	//var service = aCtx.getService(Packages.ru.ecom.ejb.services.vocentity.IVocEntityService) ;
	//aCtx.request.setAttribute("vocEntityInfo"
	//	, service.getVocEntityInfo(aCtx.request.getParameter("id"))) ;
	return aCtx.createForward("/WEB-INF/actions/pres_prescriptList/print.jsp") ;
}