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
	if (aForm.anotherLpu<1) {
	    list = aCtx.manager.createQuery("from DisabilityDocument where series = :series"
	       	+" and number = :number and documentType_id = :doctype"
	       	)
	       	.setParameter("series",series)
	       	.setParameter("number",number)
	       	.setParameter("doctype",doctype)
	       	.getResultList() ;
			
		errorThrow(list, "В базе уже существует документ с такими номером и серией") ;
	}
    if (aForm.workComboType==0 && primary!=null && primary.code.equals("1")) {
	    list = aCtx.manager.createNativeQuery("select count(*) from DisabilityDocument as dd"
			+ " inner join VocDisabilityDocumentPrimarity as vddp on vddp.id=dd.primarity_id"
			+ " where dd.disabilityCase_id=:dcase and (dd.noActuality is null or cast(dd.noActuality as int) = 0) and dd.workComboType_id is null and vddp.code='1'"
	       	)
	       	.setParameter("dcase",dcase)
	       	.getSingleResult() ;
		if (list>0) throw "В текущему случаю может быть только 1 первичный неиспорченный документ по основному месту работы" ;
	}
	if (aForm.workComboType!=0 && aForm.mainWorkDocumentNumber.equals("") )
			throw "При совмещении необходимо указывать номер и серию документа по основному месту работы" ;
	//errorThrow(list, ) ;
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
	if (!list.isEmpty()) {
		throw "Данный номер уже был использован в случае нетрудоспособности "+list.get(0)[1];
	}

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
		//pat.works  = pat.getWorks();
		if (pat.works!=null && !pat.works.equals(aForm.getWorks())) {
			pat.setWorks(aForm.job) ;
			aCtx.manager.persist(pat) ;
		}
	}
	var elns = aCtx.manager.createQuery(" from ElectronicDisabilityDocumentNumber where number=:num").setParameter("num",aEntity.number).getResultList();
	if (!elns.isEmpty()) { //Если номер из таблицы с полученнми номерами от ФСС, забираем номер.
		if (elns.size>1) {
			throw "Обнаружено более одного листа с номером "+aEntity.number+", обратитесь к разработчикам";
		}
		var eln = elns.get(0);
		if (eln.getDisabilityDocument()!=null) {throw "Случилось то, чего не должно случиться. Обратитесь к разработчикам. Ошибка: ELN_UJE_ZANYAT";}
		eln.setDisabilityDocument(aEntity);
		eln.setUsername(aForm.getCreateUsername());
		eln.setReserveDate(null);
        eln.setReserveTime(null);
        aCtx.manager.persist(eln);

	}
}
function onSave(aForm, aEntity, aCtx) {
	aEntity.setNoActuality(aEntity.status!=null && +aEntity.status.code>0) ;
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

function checkIsElectronic(aEntityId , aCtx, sql, aMessage){
    var elns = aCtx.manager.createNativeQuery("select id from ElectronicDisabilityDocumentNumber where disabilitydocument_id=:num "+sql)
        .setParameter("num",aEntityId).getResultList();
    if (!elns.isEmpty()) {
        throw aMessage;
    }
}

function onPreSave(aForm,aEntity , aCtx) {

    //checkIsElectronic(aEntity.getId(),aCtx,"and exportDate is not null","Сохранение невозможно, ЭЛН выгружен в ФСС");
	var series = aForm.getSeries() ;
	var number = aForm.getNumber() ;
	var thisid = aForm.getId() ;
//	var dcase = aForm.getDisabilityCase() ;
	var doctype = aForm.getDocumentType() ;
	var list ;
	if (aForm.prevDocument>0 && aForm.prevDocument == aForm.id) {
		throw "Предыдущий документ не должен совпадать с текущим." ;
	}
	if (aForm.workComboType!=0 && 
		(aForm.mainWorkDocumentNumber.equals("")  ) )
			throw "При совмещении необходимо указывать номер документа по основному месту работы" ;
	if (aForm.anotherLpu<1) {
		list = aCtx.manager.createQuery("from DisabilityDocument where "
	
       	+" (series = :series and number = :number and documentType_id = :doctype)"
       	+" and id != '"+thisid+"'"
       	)
       	.setParameter("series",series)
       	.setParameter("number",number)
       	.setParameter("doctype",doctype)
       	.getResultList() ;
		errorThrow(list,"В базе уже существует документ с такими номером и серией") ;
	}
	var date = new java.util.Date() ;
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	var reason = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityDocumentCloseReason, aForm.getCloseReason()) ;
	//throw ""+aForm.getDisabilityReason() ;
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
        aForm.setBeginWorkDate("");
	}
}
function errorThrow(aList, aError) {
	if (!aList.isEmpty()) {
		var error ="";
		for(var i=0; i<aList.size(); i++) {
			var doc = aList.get(i) ;
			error = error+" <a href='entityView-dis_document.do?id="+doc.id+"'>"+(doc.documentType!=null?doc.documentType.name:"-")+" "+ doc.series + " " +doc.number + "</a><br/>" ;
		}
		throw aError + error ;
	}
}


function onPreDelete(aEntityId, aContext) {

    checkIsElectronic(aEntityId,aContext,"","Невозможно удалить ЭЛН!");
//	var doc = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.disability.DisabilityDocument, new java.lang.Long(aEntityId)) ;
	//if (doc.duplicate!=null) throw "Невозможно удалить документ так"
	var list = aContext.manager.createQuery(
		"from DisabilityDocument where duplicate_id=:aid")
		.setParameter("aid", aEntityId)
		.getResultList() ;
	
	if (!list.isEmpty()) {
		var orig = list.get(0) ;
		//var id = orig.id ;
		orig.setDuplicate(null) ;
		var stat = aContext.manager.createQuery("from VocDisabilityStatus where code='0'").getResultList() ;
		orig.setStatus(stat.isEmpty() ? null : stat.get(0)) ;
		aContext.manager.persist(orig);
	} 
}
function onView (aForm, aEntity, aCtx){ //Если документ отправляли в ФСС - берем информациб о цего статусе
	var eln = aCtx.manager.createQuery("from ElectronicDisabilityDocumentNumber where number=:num").setParameter("num",aForm.getNumber()).getResultList();
	var status = "";
	if (eln.isEmpty()){
		eln = aCtx.manager.createNativeQuery("select id, result , to_char(requestdate,'dd.MM.yyyy') as f2_date from exportfsslog where disabilitynumber=:num").setParameter("num",aForm.getNumber()).getResultList();
		if (!eln.isEmpty()) {
			var e = eln.get(0);
			status = e[1]+" от "+e[2];
		}
	} else {
		var e = eln.get(0);
		try{
			status = e.getStatus()!=null?(e.getStatus().getName()+" от "+Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(e.getExportDate())):"Не выгружался в ФСС";
		} catch (e) { //бывали случаи что статус не нуль, а дата экспорта - пустая
			status="Неизвестно";
		}

	}
    aForm.setExportStatus(status);
}