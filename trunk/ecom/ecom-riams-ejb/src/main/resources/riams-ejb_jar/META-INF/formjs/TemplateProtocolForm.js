function onPreCreate(aForm, aCtx) {
	aForm.setDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(new java.util.Date())) ;
	aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
function onPreDelete(aEntityId, aContext) {
	var temp = aContext.manager.find(Packages.ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol,java.lang.Long.valueOf(aEntityId)) ;
	var username = aContext.getSessionContext().getCallerPrincipal().toString() ;
	if (temp==null || !temp.username.equals(username)) {
		throw "Удалить шаблон может только пользователь, создавший его!!!" ;
	}
}