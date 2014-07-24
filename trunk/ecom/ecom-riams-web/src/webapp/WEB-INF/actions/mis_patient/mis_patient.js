function viewDirection(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/smo_direction/list.jsp") ;
}
function CommRead(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_patient/CommRead.jar") ;
}
function jssc(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_patient/jssc.jar") ;
}
function barcode(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_patient/pc.shared.barcode.obs.jar") ;
}
