/**
 * Выбор даты поступления
 */
function findByDate(aForm, aCtx) {
	
	return aCtx.createForward("/WEB-INF/actions/stac_slo/listByDate.jsp") ;
}

function list_protocols(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/stac_slo/list_protocols.jsp") ;
}

function list_edit_protocol(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/stac_slo/list_edit_protocol.jsp") ;
}