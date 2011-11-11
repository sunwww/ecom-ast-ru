/**
 * Выбор даты поступления
 */
function findByDate(aForm, aCtx) {
	
	return aCtx.createForward("/WEB-INF/actions/stac_sslAllInfo/listByDate.jsp") ;
}
/*
function address(aForm, aCtx) {
    var col = aCtx.invokeScript("HospitalPrintService", "addressUpdate"
        	, "" ) ;
	
}
*/