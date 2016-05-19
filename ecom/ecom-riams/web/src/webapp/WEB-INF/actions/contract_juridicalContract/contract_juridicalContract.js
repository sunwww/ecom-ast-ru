function account_view(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/contract_juridicalContract/account_view.jsp") ;
}
function account_view_by_patient(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/contract_juridicalContract/account_by_patient.jsp") ;
}
function account_group_by_patient(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/contract_juridicalContract/account_group_by patient.jsp") ;
}
function account_print(aForm, aCtx) {
    return aCtx.createForward("/WEB-INF/actions/contract_juridicalContract/account_print.jsp") ;
}