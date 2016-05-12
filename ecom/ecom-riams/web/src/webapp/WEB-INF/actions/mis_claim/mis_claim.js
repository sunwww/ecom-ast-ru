function listAll(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_bedFundCapacity/list_all.jsp") ;
}

function list_by_user (aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_claim/list_by_operator.jsp");
}
function my_claims (aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_claim/list_by_creator.jsp");
}

function list_pda (aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_claim/list_by_operator_pda.jsp");
}
function pda (aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_claim/view_pda.jsp");
}