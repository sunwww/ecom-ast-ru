
/**
 * List all voc entities
 */
function list(aForm, aCtx) {
	/*var service = aCtx.getService(Packages.ru.ecom.ejb.services.vocentity.IVocEntityService) ;
	var list = service.listVocEntities() ;
	var sortBy = aCtx.request.getParameter("sort") || "comment" ;
	var cmp = new JavaAdapter(Packages.java.util.Comparator, {
		compare : function (a,b) {
			if(sortBy=='count') {
				return a.count-b.count ;
			} else {
				//throw a[sortBy]+" = "+b[sortBy] ;
				return a[sortBy].compareTo(b[sortBy]) ;
			}
		}
	}) ;
	
	java.util.Collections.sort(list, cmp) ;
	aCtx.getRequest().setAttribute("list", list) ;
	return aCtx.createForward("/WEB-INF/actions/ecom_vocEntity/list.jsp") ;*/
	return aCtx.createForward("/ecom_vocEntity-list.do") ;
}

/**
 * Redirect from id=helloForm to "/entityList-hello.do" action
 */
function edit(aForm, aCtx) {
	var service = aCtx.getService(Packages.ru.ecom.ejb.services.vocentity.IVocEntityService) ;
	aCtx.request.setAttribute("vocEntityInfo"
		, service.getVocEntityInfo(aCtx.request.getParameter("id"))) ;
	return aCtx.createForward("/WEB-INF/actions/ecom_vocEntity/edit.jsp") ;
}
function encodingAccordance(aForm, aCtx) {
	var service = aCtx.getService(Packages.ru.ecom.ejb.services.vocentity.IVocEntityService) ;
	//aCtx.request.setAttribute("vocEntityInfo"
	//	, service.getVocEntityInfo(aCtx.request.getParameter("id"))) ;
	aCtx.request.setAttribute("vocEntityInfo.comment","Кодирующая система");
	return aCtx.createForward("/WEB-INF/actions/ecom_vocEntity/encodingAccordance.jsp") ;
}
