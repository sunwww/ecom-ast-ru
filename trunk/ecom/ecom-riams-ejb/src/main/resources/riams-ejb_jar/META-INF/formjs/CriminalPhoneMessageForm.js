function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	aForm.setCreateTime(new java.sql.Time (date.getTime())) ;
	if (+aForm.recieverEmploye==0 && (aForm.recieverFio==null || aForm.recieverFio.trim().equals(""))) {
		throw "Необходимо заполнить информацию о принявшем сообщение!!!" ;
	}
}
/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	if (+aForm.recieverEmploye==0 && (aForm.recieverFio==null || aForm.recieverFio.trim().equals(""))) {
		throw "Необходимо заполнить информацию о принявшем сообщение!!!" ;
	}
}
