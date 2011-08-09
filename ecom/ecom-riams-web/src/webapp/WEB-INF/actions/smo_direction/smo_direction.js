
/**
 * Поиск направлений
 */
function findDirections(aForm, aCtx) {
	var col = aCtx.invokeScript("SmoDirectionService", "findDirections", null) ;
	aCtx.request.setAttribute("list", col) ;
	aCtx.request.setAttribute("worker", aCtx.invokeScript("WorkerService", "findLogginedWorkerName", null) ) ;
	return aCtx.createForward("/WEB-INF/actions/smo_direction/list.jsp") ;
}


 