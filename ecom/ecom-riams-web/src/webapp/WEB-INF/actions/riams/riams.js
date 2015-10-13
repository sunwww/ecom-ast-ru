function phone(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/riams/phone.htm") ;
}
function update_postgres(aForm,aCtx) {
	aCtx.invokeScript("UpdateService", "update_postgres") ;
	new Packages.ru.nuzmsh.web.messages.InfoMessage(aCtx.request, "Обновление завершено") ;
	return aCtx.createForwardRedirect("/riams_config.do") ;
}