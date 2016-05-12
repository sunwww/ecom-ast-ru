function journal_medservice(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_lpu/medservice.jsp") ;
}

function showStandard (aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_lpu/list_standard.jsp") ;
}
function addOtherEquipment(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_lpu/equipment.jsp") ;
}
