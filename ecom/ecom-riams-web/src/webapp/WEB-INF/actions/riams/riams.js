function phone(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/riams/phone.htm") ;
}
function instructions(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/riams/instructions.jsp") ;
}
function update_postgres(aForm,aCtx) {
	aCtx.invokeScript("UpdateService", "update_postgres") ;
	new Packages.ru.nuzmsh.web.messages.InfoMessage(aCtx.request, "Обновление завершено") ;
	return aCtx.createForwardRedirect("/riams_config.do") ;
}
function instruction(aForm,aCtx) {
	var id = aCtx.request.getParameter("id") ;
	return aCtx.createForward("/WEB-INF/actions/riams/instruction/"+id+".doc") ;
}