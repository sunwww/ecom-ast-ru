function onView(aForm, aVisit, aCtx) {
	if(aForm.parent!=0) {
		aForm.addDisabledField("parent") ;
	}
	//if(aForm.startWorker==0) {
	//	aForm.startWorker = aCtx.serviceInvoke("WorkerService", "findLogginedWorker").id ;
	//}
	// FIXME определять функцию правильно
	if(aForm.workFunctionExecute==0) {
		aForm.workFunctionExecute = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunctionList")
			.iterator().next().id ;
	}
}

function onCreate(aForm, aVisit, aCtx) {
	throw "Создавать визит можно только из направления" ;

}

function onSave(aForm, aVisit, aCtx) {
	if(true==aVisit.getNoActuality()) return ; // ничего не делаем, если не актуальный
	
	if(aVisit.dateStart==null) throw "Нет даты визита" ;
	if(aVisit.timeExecute==null) throw "Нет времени визита" ;
	
	// создание нового СПО
	if(aVisit.parent==null) {
		var spo = new Packages.ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase() ;
		aCtx.manager.persist(spo) ;
		var worker = aVisit.getWorkFunctionExecute().getWorker() ; 
		spo.setOwner(worker) ;
		spo.setDateStart(aVisit.getDateStart()) ;
		spo.setLpu(worker.getLpu()) ;
		spo.setPatient(aVisit.getPatient()) ;
		spo.setStartWorker(worker) ;
		spo.setServiceStream(aVisit.getServiceStream()) ;
		aVisit.setParent(spo) ;
	}
	//throw "spo="+spo.dateStart + " - "+aVisit.dateStart ;
	
}