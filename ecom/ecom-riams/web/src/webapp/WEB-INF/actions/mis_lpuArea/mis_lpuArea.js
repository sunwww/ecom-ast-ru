function print_area_by_address(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_lpuArea/print_area_by_address.jsp") ;
}
function print_lpu_by_address(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_lpuArea/print_lpu_by_address.jsp") ;
}
function elections(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_lpuArea/elections_report.jsp") ;
}
