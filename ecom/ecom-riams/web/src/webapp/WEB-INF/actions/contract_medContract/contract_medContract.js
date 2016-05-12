/**
 * Список всех случаев нетрудоспособности по пациенту
 */
function list_accrual_service(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/contract_medContract/list_accrual_service.jsp") ;
}
 /**/
function list_accrual_by_customer(aForm, aCtx) {
    return aCtx.createForward("/WEB-INF/actions/contract_medContract/list_accrual_by_customer.jsp") ;
}

function issueRefund(aForm, aCtx) {
	var id = aCtx.request.getParameter("id") ;
	aCtx.invokeScript("CertificatePersonPrintService", "issueRefund"
			, aCtx.request.getParameter("id")) ;
	return aCtx.createForward("/entityParentView-contract_accountOperationAccrual.do?id="+id) ;
}
function updateCAOSbyCharged(aForm,aCtx){
	aCtx.invokeScript("Contractreport", "updateCAOSbyCharged",aCtx.request.getParameter("dateFrom")
			,aCtx.request.getParameter("dateTo")) ;
	return aCtx.createForward("/contract_policlinic_render.do")
}