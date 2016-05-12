function autogenerate(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_worker/listAutogenerate.jsp") ;
}
function archives(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_worker/listArchives.jsp") ;
}
function running(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_worker/listRunning.jsp") ;
}
function empty(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_worker/listEmpty.jsp") ;
}
function pattern(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_worker/listEstablish.jsp") ;
}
function all(aForm,aCtx) {
	return aCtx.createForward("/WEB-INF/actions/mis_worker/listAll.jsp") ;
}