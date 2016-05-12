/**
 * Печать реестра по стационару
 */
function cost_case(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/stac_ssl/cost_case.jsp") ;
}
function printReestr(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/stac_ssl/reestr_patient_by_period.jsp") ;
}
function infoShortByPatient(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/stac_ssl/list_by_patient_short.jsp");
}