


function onPreCreate(aForm, aCtx) {
	aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}

function onPreSave(aForm,aEntity,aCtx) {
	//aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(new java.util.Date())) ;
	//onPreCreate(aForm,aCtx);
	//throw("123");
	//aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}