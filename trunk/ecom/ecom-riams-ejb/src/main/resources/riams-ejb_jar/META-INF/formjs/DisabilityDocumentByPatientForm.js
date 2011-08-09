/**
* @author stkacheva
*/
function onPreCreate(aForm, aCtx) {
	if (aForm.anotherLpu<1) {
		if(aForm.workFunction<1) throw "Специалист является обязательным полем при создании нового документа нетрудоспособности" ;
	} else {
		aForm.workFunction = 0 ;
	}
	
	var series = aForm.getSeries() ;
	var number = aForm.getNumber() ;
	var doctype = aForm.getDocumentType() ;
	var list ;
	if (aForm.prevDocument>0 && aForm.prevDocument == aForm.id) {
		throw "Предыдущий документ не должен совпадать с текущим." ;
	}
    list = aCtx.manager.createQuery("from DisabilityDocument where series = :series"
       	+" and number = :number and documentType_id <> :doctype"
       	)
       	.setParameter("series",series)
       	.setParameter("number",number)
       	.setParameter("doctype",doctype)
       	.getResultList() ;
	errorThrow(list) ;
}

function onCreate(aForm, aEntity, aCtx) {
	var dcase = new Packages.ru.ecom.mis.ejb.domain.disability.DisabilityCase() ;
	dcase.setPatient(aEntity.patient) ;
	var drecord = new Packages.ru.ecom.mis.ejb.domain.disability.DisabilityRecord() ;
	var reg = aCtx.manager.find(
	Packages.ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityRegime
	,aForm.regime) ;
	var wfunc = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction,aForm.workFunction) ;

	if (aForm.dateTo != null && aForm.dateTo!="") {
		drecord.setDateTo(Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aForm.dateTo)) ;
	}
	drecord.setDateFrom(Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aForm.dateFrom)) ;
	drecord.setRegime(reg) ;
	drecord.setWorkFunction(wfunc) ;
	drecord.setDisabilityDocument(aEntity) ;
	dcase.setJob(aForm.job) ;
	dcase.setEarlyPregnancyRegistration(aForm.earlyPregnancyRegistration) ;
	dcase.setPlacementService(aForm.placementService) ;
	aCtx.manager.persist(dcase) ;
	aCtx.manager.persist(drecord) ;
	aEntity.setDisabilityCase(dcase) ;
	aCtx.manager.persist(aEntity) ;
}
function errorThrow(aList, aError) {
	if (aList.size()>0) {
		var error ="";
		for(var i=0; i<aList.size(); i++) {
			var doc = aList.get(i) ;
			error = error+" ИД = "+doc.id+" ДОКУМЕНТ: "+doc.documentTypeInfo+" "+ doc.series + " " +doc.number + "<br/>" ;
		}
		throw aError + error ;
	}
}