 /**/
function edit(aForm, aCtx) {
	//var service = aCtx.getService(Packages.ru.ecom.ejb.services.vocentity.IVocEntityService) ;
	//aCtx.request.setAttribute("vocEntityInfo"
	//	, service.getVocEntityInfo(aCtx.request.getParameter("id"))) ;
	return aCtx.createForward("/WEB-INF/actions/stac_temperatureCurve/edit.jsp") ;
}
 /**/
function graph(aForm, aCtx) {
	//var service = aCtx.getService(Packages.ru.ecom.ejb.services.vocentity.IVocEntityService) ;
	//aCtx.request.setAttribute("vocEntityInfo"
	//	, service.getVocEntityInfo(aCtx.request.getParameter("id"))) ;
	return aCtx.createForward("/WEB-INF/actions/stac_temperatureCurve/graph.jsp") ;
}

function printgraph(aForm, aCtx) {
	//var service = aCtx.getService(Packages.ru.ecom.ejb.services.vocentity.IVocEntityService) ;
	//aCtx.request.setAttribute("vocEntityInfo"
	//	, service.getVocEntityInfo(aCtx.request.getParameter("id"))) ;
	return aCtx.createForward("/WEB-INF/actions/stac_temperatureCurve/printgraph.jsp") ;
}