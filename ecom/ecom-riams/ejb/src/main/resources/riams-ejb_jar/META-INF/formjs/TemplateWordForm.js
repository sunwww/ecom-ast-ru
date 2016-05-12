function onPreCreate(aForm, aCtx) {
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(new java.util.Date())) ;
	aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
function onPreDelete(aEntityId, aContext) {
	var temp = aContext.manager.find(Packages.ru.ecom.diary.ejb.domain.protocol.template.TemplateWord,java.lang.Long.valueOf(aEntityId)) ;
	var username = aContext.getSessionContext().getCallerPrincipal().toString() ;
	if (temp==null || !temp.createUsername.equals(username)) {
		throw "Удалить шаблон может только пользователь, создавший его!!!" ;
	}
}
function onPreSave(aForm,aEntity , aCtx) {
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(new java.util.Date())) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}