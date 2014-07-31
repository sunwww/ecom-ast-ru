/**
* @author stkacheva
*/
function onPreCreate(aForm, aCtx) {
	if (aForm.anotherLpu<1 && aForm.workFunction<1) throw "Специалист является обязательным полем при создании нового документа нетрудоспособности" ;
	if (aForm.regime.equals("")) throw "Режим нетрудоспособности является обязательным полем при создании нового документа нетрудоспособности" ;
	if (aForm.dateFrom.equals("")) throw "Дата начала является обязательным полем при создании нового документа нетрудоспособности" ;
	var series = aForm.getSeries() ;
	var number = aForm.getNumber() ;
	var doctype = aForm.getDocumentType() ;
	var dcase = aForm.getDisabilityCase() ;
	var list ;
	if (aForm.prevDocument>0 && aForm.prevDocument == aForm.id) {
		throw "Предыдущий документ не должен совпадать с текущим." ;
	}
	var primary = aCtx.manager.find(
		Packages.ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityDocumentPrimarity
		,aForm.primarity) ;
    list = aCtx.manager.createQuery("from DisabilityDocument where series = :series"
       	+" and number = :number and documentType_id <> :doctype"
       	)
       	.setParameter("series",series)
       	.setParameter("number",number)
       	.setParameter("doctype",doctype)
       	.getResultList() ;
		
	errorThrow(list, "В базе уже существует документ с такими номером и серией") ;
    if (aForm.workComboType==0 && primary!=null && primary.code.equals("1")) {
	    list = aCtx.manager.createNativeQuery("select count(*) from DisabilityDocument as dd"
			+ " inner join VocDisabilityDocumentPrimarity as vddp on vddp.id=dd.primarity_id"
			+ " where dd.disabilityCase_id=:dcase and (dd.noActuality is null or cast(dd.noActuality as int) = 0) and dd.workComboType_id is null and vddp.code='1'"
	       	)
	       	.setParameter("dcase",dcase)
	       	.getSingleResult() ;
		if (list>0) throw "В текущему случаю может быть только 1 первичный неиспорченный документ по основному месту работы" ;
	}
	if (aForm.workComboType!=0 && 
		(aForm.mainWorkDocumentNumber.equals("") || aForm.mainWorkDocumentSeries.equals("") ) )
			throw "При совмещении необходимо указывать номер и серию документа по основному месту работы" ;
	//errorThrow(list, ) ;
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	
}
function onCreate(aForm, aEntity, aCtx) {
	aEntity.setPatient(aEntity.disabilityCase.patient) ;
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
	if (+aForm.workFunctionAdd>0) {
		wfuncadd = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction,aForm.workFunctionAdd) ;
		drecord.setWorkFunctionAdd(wfuncadd) ;
		
	}
	aCtx.manager.persist(drecord) ;
	if (aEntity.status!=null && +aEntity.status.code>0) {
		aEntity.setNoActuality(true) ;
	} else {
		aEntity.setNoActuality(false) ;
	}
	aCtx.manager.persist(aEntity) ;
	if (aForm.isUpdateWork!=null && aForm.isUpdateWork==true) {
		var pat = aEntity.disabilityCase.patient ;
		pat.works  = pat.getWorks();
		if (pat.works!=null && !pat.works.equals(aForm.getWorks()) {
			pat.setWorks(aForm.job) ;
			aCtx.manager.persist(pat) ;
		}
}
function onSave(aForm, aEntity, aCtx) {
	if (aEntity.status!=null && +aEntity.status.code>0) {
		aEntity.setNoActuality(true) ;
	} else {
		aEntity.setNoActuality(false) ;
	}
	aCtx.manager.persist(aEntity) ;
	if (aForm.isUpdateWork!=null && aForm.isUpdateWork==true) {
		var pat = aEntity.disabilityCase.patient ;
		var org = pat.works ;
		if (org!=null) {
			org.setCode(aForm.job) ;
			aCtx.manager.persist(org) ;
		}
	}
	
}
function onPreSave(aForm,aEntity , aCtx) {
	var series = aForm.getSeries() ;
	var number = aForm.getNumber() ;
	var thisid = aForm.getId() ;
	var dcase = aForm.getDisabilityCase() ;
	var doctype = aForm.getDocumentType() ;
	var list ;
	if (aForm.prevDocument>0 && aForm.prevDocument == aForm.id) {
		throw "Предыдущий документ не должен совпадать с текущим." ;
	}
	if (aForm.workComboType!=0 && 
		(aForm.mainWorkDocumentNumber.equals("")  ) )
			throw "При совмещении необходимо указывать номер документа по основному месту работы" ;
    list = aCtx.manager.createQuery("from DisabilityDocument where "
       	+" (series = :series and number = :number and documentType_id <> :doctype)"
       	+" and id != '"+thisid+"'"
       	)
       	.setParameter("series",series)
       	.setParameter("number",number)
       	.setParameter("doctype",doctype)
       	.getResultList() ;
	errorThrow(list,"В базе уже существует документ с такими номером и серией") ;
	var date = new java.util.Date() ;
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
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


function onPreDelete(aEntityId, aContext) {
	var doc = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.disability.DisabilityDocument
			, new java.lang.Long(aEntityId)) ;
	//if (doc.duplicate!=null) throw "Невозможно удалить документ так"
	var list = aContext.manager.createQuery(
		"from DisabilityDocument where duplicate_id=:aid")
		.setParameter("aid", aEntityId)
		.getResultList() ;
	
	if (list.size()>0) {
		var orig = list.get(0) ;
		var id = orig.id ;
		orig.setDuplicate(null) ;
		var stat = aContext.manager.createQuery(
				"from VocDisabilityStatus where code='0'").getResultList() ;
		if (stat.size()>0) {
			orig.setStatus(stat.get(0)) ;
		} else {
			stat.setStatus(null) ;
		}
		aContext.manager.persist(orig);
	} 
	
}