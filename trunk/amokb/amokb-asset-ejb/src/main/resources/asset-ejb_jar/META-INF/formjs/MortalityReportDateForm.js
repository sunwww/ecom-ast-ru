function onView(aForm, aEntity, aCtx) {
	if(true==aEntity.getEditComplete()) { 
		aForm.addMessage(new Packages.ru.nuzmsh.forms.response.FormMessage("Редакция завершена")) ;
		aForm.setTypeViewOnly() ;
		aForm.addDisabledField("editComplete") ;
	}
	
}

function onPreSave(aForm, aEntity, aCtx) {
	if(true==aEntity.getEditComplete()) throw "Редакция завершена. Обратитесь к администраторы для изменения данных" ;
}
