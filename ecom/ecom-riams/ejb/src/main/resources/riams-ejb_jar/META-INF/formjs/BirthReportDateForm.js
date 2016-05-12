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

function onSave(f, aBirthReportDate, aCtx) {
	var allBorn = f.liveBornBoys + f.liveBornGirls + f.deadBornBoys + f.deadBornGirls ;
	var allBirth = 
		f.firstDeadBirth + f.firstLiveBirth
		+ f.secondDeadBirth + f.secondLiveBirth
		+ f.otherDeadBirth + f.otherLiveBirth
		+ f.tripletsBirth + f.twinsBirth ;
	if(allBorn!=allBirth) throw "Количество родов и родившихся должно совпадать" ;	
}

function onCreate(aForm, aBirthReportDate, aCtx) {
	onSave(aForm, aBirthReportDate, aCtx) ;
}