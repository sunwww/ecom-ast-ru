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
	if (aForm.anotherLpu<1) {
    list = aCtx.manager.createQuery("from DisabilityDocument where series = :series"
       	+" and number = :number and documentType_id = :doctype"
       	)
       	.setParameter("series",series)
       	.setParameter("number",number)
       	.setParameter("doctype",doctype)
       	.getResultList() ;
	errorThrow(list,"В базе уже существует документ с такими номером и серией") ;}
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	
	var reason = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityDocumentCloseReason, aForm.getCloseReason()) ;
	
	if (reason!=null && reason.getCodeF()!=null && (reason.getCodeF().equals("32")
			||reason.getCodeF().equals("33")
			||reason.getCodeF().equals("34")
			||reason.getCodeF().equals("36")
			)) {
		if (aForm.otherCloseDate!=null && !aForm.otherCloseDate.equals("")) {
			
		} else {
			throw "Нельзя закрыть документ, так как причина закрытия "+reason.getCodeF()+" "+reason.getName()+" должна быть указана иная дата закрытия!" ;
		}
	} else {
		aForm.setOtherCloseDate("") ;
	}
    list = aCtx.manager.createNativeQuery("select number, disabilitydocument_id from ElectronicDisabilityDocumentNumber where number =:num and disabilitydocument_id is not null").setParameter("num",number).getResultList();
    if (list.size()>0) {
        throw "Данный номер уже был использован в случае нетрудоспособности "+list.get(0)[1];
    }
}

function onCreate(aForm, aEntity, aCtx) {
	var dcase = new Packages.ru.ecom.mis.ejb.domain.disability.DisabilityCase() ;
	var pat = aEntity.patient ;
	dcase.setPatient(pat) ;
	var drecord = new Packages.ru.ecom.mis.ejb.domain.disability.DisabilityRecord() ;
	var reg = aCtx.manager.find(
	Packages.ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityRegime
	,aForm.regime) ;
	var wfunc = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction,aForm.workFunction) ;
	var wfunc_add = null ;
	if (aForm.dateTo != null && aForm.dateTo!="") {
		drecord.setDateTo(Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aForm.dateTo)) ;
	}
	if (aEntity.status!=null && +aEntity.status.code>0) {
		aEntity.setNoActuality(true) ;
	} else {
		aEntity.setNoActuality(false) ;
	}
	drecord.setDateFrom(Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aForm.dateFrom)) ;
	drecord.setRegime(reg) ;
	drecord.setWorkFunction(wfunc) ;
	drecord.setDisabilityDocument(aEntity) ;
	var wfunc_add ;
	
	if (+aForm.workFunctionAdd>0) {
		wfuncadd = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction,aForm.workFunctionAdd) ;
		drecord.setWorkFunctionAdd(wfuncadd) ;
		
	}
	// dcase.setJob(aForm.job) ;
	dcase.setEarlyPregnancyRegistration(aForm.earlyPregnancyRegistration) ;
	dcase.setPlacementService(aForm.placementService) ;
	dcase.setCreateDate(aEntity.getCreateDate()) ;
	dcase.setCreateUsername(aEntity.getCreateUsername()) ;
	if (+aForm.nursingPerson1>0) {
		var nur1 = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Kinsman,aForm.nursingPerson1) ;
		dcase.setNursingPerson1(nur1) ;
	}
	if (+aForm.nursingPerson2>0) {
		var nur2 = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Kinsman,aForm.nursingPerson2) ;
		dcase.setNursingPerson2(nur2) ;
	}
	aCtx.manager.persist(dcase) ;
	aCtx.manager.persist(drecord) ;
	aEntity.setDisabilityCase(dcase) ;
	aCtx.manager.persist(aEntity) ;
	if (aForm.isUpdateWork!=null && aForm.isUpdateWork==true) {
		//pat.works  = pat.getWorks();
		if (pat.works!=null && !pat.works.equals(aForm.getWorks())) {
			pat.setWorks(aForm.job) ;
			aCtx.manager.persist(pat) ;
		}
	}
    var elns = aCtx.manager.createQuery(" from ElectronicDisabilityDocumentNumber where number=:num").setParameter("num",aEntity.number).getResultList();
    if (elns.size()>0) { //Если номер из таблицы с полученнми номерами от ФСС, забираем номер.
        var eln = elns.get(0);
        if (eln.getDisabilityDocument()!=null) {throw "Случилось то, чего не должно случиться. Обратитесь к разработчикам. Ошибка: ELN_UJE_ZANYAT";}
        eln.setDisabilityDocument(aEntity);
        eln.setUsername(aEntity.getCreateUsername());
        eln.setReserveDate(null);
        eln.setReserveTime(null);
        aCtx.manager.persist(eln);

    }
}
function errorThrow(aList, aError) {
	if (aList.size()>0) {
		var error ="";
		for(var i=0; i<aList.size(); i++) {
			var doc = aList.get(i) ;
			error = error+" <a href='entityView-dis_document.do?id="+doc.id+"'>"+(doc.documentType!=null?doc.documentType.name:"-")+" "+ doc.series + " " +doc.number + "</a><br/>" ;
		}
		throw aError + error ;
	}
}