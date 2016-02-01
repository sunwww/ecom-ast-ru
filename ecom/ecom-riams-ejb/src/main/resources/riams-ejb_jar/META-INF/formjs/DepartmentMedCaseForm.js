/**
 * Перед созданием
 */
function onPreCreate(aForm, aContext) {
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateTime(Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(new java.sql.Time (date.getTime()))) ;
	aForm.setUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;
	var listDep ;
	if (aForm.prevMedCase >0) {
		listDep=aContext.manager.createQuery("from MedCase where parent_id=:parent and dtype='DepartmentMedCase' and prevMedCase_id=:prev")
			.setParameter("parent",aForm.parent)
			.setParameter("prev",aForm.prevMedCase)
			.getResultList() ;
	} else {
		listDep=aContext.manager.createQuery("from MedCase where parent_id=:parent and dtype='DepartmentMedCase' and prevMedCase_id=null")
		.setParameter("parent",aForm.parent)
		.getResultList();
	}
	if (listDep.size()>0) {
		var dep = listDep.get(0) ;
		throw "Уже завели случай в отделелении. <a href='entitySubclassView-mis_medCase.do?id="+dep.id+"'>Перейти к нему</a>"
		
	}
	onPreSave(aForm,null,aContext) ;
	
}

/**
 * Перед сохранением
 */
function onPreSave(aForm,aEntity, aContext) {
	var cal = java.util.Calendar.getInstance() ;
	var dateStart = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateStart,aForm.entranceTime);
	var transferIs = false ;
	var prof = "ALLTIMEHOSP" ;
	if (aEntity!=null) {
		var date = new java.util.Date() ;
		aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
		aForm.setEditTime(Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(new java.sql.Time (date.getTime()))) ;
		aForm.setEditUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;
		
	}
	if (aForm.mkbAdc==null||aForm.mkbAdc.equals("")) {
		var lMkb = aContext.manager.createNativeQuery("select vma.code from VocMkbAdc vma left join VocIdc10 mkb on mkb.code=vma.name where  mkb.id='"+aForm.clinicalMkb+"'").getResultList() ;
		if (lMkb.size()>0) throw "Необходимо ввести дополнительный код по диагнозу" ;
	}
	var bedFund = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.lpu.BedFund, aForm.bedFund) ;
	var hosp = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase,aForm.parent) ;
	var prev = +aForm.prevMedCase>0?aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase,aForm.prevMedCase):null ;
	var isDoc=aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/OwnerFunction") ;
	var isNoPalat = aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Slo/NoRoomNumber") ;

	if (prev!=null) {
		var dateTransfer ;
		
		try {
			dateTransfer = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(prev.dateStart,prev.entranceTime) ;
		} catch(e) {
			throw "Неправильно введена дата перевода или время!" ;
		}
		//var dateCur = new java.sql.Date(new java.util.Date().getTime()) ;
		//var dateTsql = new java.sql.Date(dateTransfer.getTime()) ;
		if (!(dateTransfer.getTime() < dateStart.getTime())) throw "Дата перевода должна быть больше, чем дата поступления";
		//if ((((dateTsql.getTime()-dateCur.getTime())/1000/60/60)%24)>6) throw "Максимальная дата перевода - сегодняшняя" ;
		// необходимо проверить заполнено ли поле отделение перевода
		if (+prev.department.id == (+aForm.department)) {
			throw "Отделение, в которое переводится пациент, и отделение, из которого переводится, должны быть разными!";
		}
		// заполнены поля перевода
		transferIs = true ;
	} else {
		if (+hosp.department.id != (+aForm.department)) {
			throw "Отделение, в которое направили в приемном отделении, должно совпадать с отделением поступления!";
		}
		
		var dateEntrHosp ;
		
		try {
			dateEntrHosp = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(hosp.dateStart,hosp.entranceTime) ;
		} catch(e) {
			throw "Неправильно введена дата поступления в стационар или время (приемник)!" ;
		}
		//var dateCur = new java.sql.Date(new java.util.Date().getTime()) ;
		//var dateTsql = new java.sql.Date(dateTransfer.getTime()) ;
		if (isDoc) {
			if (!(dateEntrHosp.getTime() < dateStart.getTime())) throw "Дата поступления в отделение должна быть больше, чем дата поступления в стационар";
		} else {
			if (!(dateEntrHosp.getTime() <= dateStart.getTime())) throw "Дата поступления в отделение должна быть больше или равна, чем дата поступления в стационар";
		}
		
		
	}
	
	if (aForm.dateFinish != null && aForm.dateFinish !="" ||aForm.transferDate != null && aForm.transferDate !="" ) {
		//if (transferIs) throw "Заполняются либо поля перевода, либо выписки!"
		var dateFinish ;
		var dateFinishError ;
		try {
			if (aForm.dateFinish != null && aForm.dateFinish !="" ) {
				dateFinish = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateFinish,aForm.dischargeTime);
				dateFinishError = aForm.dateFinish+" "+aForm.dischargeTime ;
			} else {
				dateFinish = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.transferDate,aForm.transferTime);
				dateFinishError = aForm.transferDate+" "+aForm.transferTime ;
			}
		} catch(e) {
			throw "Неправильно введена дата выписки (перевода) или время!"
		}
		if (!(dateFinish.getTime() > dateStart.getTime())) throw "Дата выписки (перевода) "+dateFinishError+" должна быть больше, чем дата поступления "+aForm.dateStart+" "+aForm.entranceTime;
	} /*else {
		if (aForm.dischargeTime!=null && aForm.dischargeTime!="") {
			if (transferIs) {
				throw "Заполняются либо поля перевода, либо выписки!"
			} else {
				throw "При оформлении выписки необходимо заполнить дату!"
			}
		}
	}
	*/
	if (isDoc && !isNoPalat && ((+aForm.roomNumber==0)||(+aForm.bedNumber==0))) {
		throw "При госпитализации в отделение необходимо указывать палату и койку" ;
	}
	if (bedFund!=null && bedFund.bedSubType!=null) {
		var bedSubType = bedFund.bedSubType.code ;
		if (hosp.hospType!=null) {
			if ((hosp.hospType.code=="DAYTIMEHOSP" && +bedSubType==2) 
				|| (hosp.hospType.code=="ALLTIMEHOSP" && +bedSubType==1) ) {
			} else {
				if (prev!=null || +bedSubType>2) {
					throw "Не соответствует тип стационара "+hosp.hospType.name+" и профиль коек "+ bedFund.bedSubType.name;
				} else {
					//ru.ecom.mis.ejb.domain.medcase.voc.VocHospType
					var otherHospTypeCode = (+bedSubType==2)?"DAYTIMEHOSP":"ALLTIMEHOSP" ;
					var hospTypeList = aContext.manager.createQuery("from VocHospType where code=:code")
					.setParameter("code",otherHospTypeCode).setMaxResults(1).getResultList() ;
					if (hospTypeList.size()>0) {
						hosp.setHospType(hospTypeList.get(0)) ;
					} else {
						throw "Не соответствует тип стационара "+hosp.hospType.name+" и профиль коек "+ bedFund.bedSubType.name;
					}
				}
			}
		}
		
	}
}

//При сохранении
function onCreate(aForm, aEntity, aContext) {
	//aEntity.setCreateTime(new java.sql.Time ((new java.util.Date()).getTime())) ;
	onSave(aForm, aEntity, aContext) ;
}
function onSave(aForm, aEntity, aContext) {
	var dat =(new java.util.Date()).getTime() ;
	//aEntity.setEditTime(new java.sql.Time (dat)) ;
	
	var listDep = aContext.manager.createQuery("from MedCase where prevMedCase_id=:prev")
		.setParameter("prev",aForm.id).getResultList() ;
	/*if (listDep.size()>0) {
		var medCase = listDep.get(0) ;
		medCase.dateStart = aEntity.transferDate ;
		medCase.entranceTime = aEntity.transferTime ;
		if (medCase.department != aEntity.transferDepartment) {
			medCase.department = aEntity.transferDepartment ;
		}
		aContext.manager.persist(medCase) ;
	}*/
	if (aEntity.prevMedCase!=null) {
		aEntity.prevMedCase.transferDate = aEntity.dateStart ;
		aEntity.prevMedCase.transferTime = aEntity.entranceTime ;
		aEntity.prevMedCase.transferDepartment = aEntity.department ;
	}
}

// Перед удалением
function onPreDelete(aMedCaseId, aContext) {
	var medCase = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase, new java.lang.Long(aMedCaseId)) ;
	if (medCase!=null) {
		var err = [
		 "диагнозов"
		,"СМО"
		,"хирургических операций"
		,"переливаний"
		,"вакцинаций"
		,"сообщений"
		,"температурных данных"
		,"листа назначения"
		,"рецептурных бланков"
		,"протоколов"
		,"полисов, прикрепленных к случаю"
		,"случая рождения"
		,"случая смерти"
		,"данные родов"
		,"беременности, прикрепленных к случаю"
		,"данные обменной карты"
		,"предыдущие случаи лечения в отделении"
		] ;
		//throw medCase.getId() + "  getStatisticStub()="+medCase.getStatisticStub() ;
		var err_list = aContext.manager.createNativeQuery("select"
		+" (select count(*) from Diagnosis as d where d.medCase_id=ms.id) as cnt1 "
		+",(select count(*) from MedCase as ms1 where ms1.parent_id=ms.id) as cnt2 "
		+",(select count(*) from SurgicalOperation as so where so.medCase_id=ms.id) as cnt3 "
		+",(select count(*) from Transfusion as tr where tr.medCase_id=ms.id) as cnt4 "
		+",(select count(*) from Vaccination as v where v.medCase_id=ms.id) as cnt5 "
		+",(select count(*) from PhoneMessage as pm where ms.id=pm.id) as cnt6 "
		+",(select count(*) from TemperatureCurve as tc where tc.medCase_id=ms.id) as cnt7 "
		+",(select count(*) from PrescriptionList as pl where pl.medCase_id=ms.id) as cnt8 "
		+",(select count(*) from PrescriptionBlank as pb where pb.medCase_id=ms.id) as cnt9 "
		+",(select count(*) from Diary as d where d.medCase_id=ms.id) as cnt10 "
		+",(select count(*) from MedCase_MedPolicy as pol where pol.medCase_id=ms.id) as cnt11 "
		+",(select count(*) from BirthCase as bc where bc.medCase_id=ms.id) as cnt12 "
		+",(select count(*) from DeathCase as dc where dc.medCase_id=ms.id) as cnt13 "
		+",(select count(*) from ChildBirth as cb where cb.medCase_id=ms.id) as cnt14 "
		+",(select count(*) from PregnancyHistory as ph where ph.medCase_id=ms.id) as cnt15 "
		+",(select count(*) from PregnanExchangeCard as pec where pec.medCase_id=ms.id) as cnt16 "
		+",(select count(*) from MedCase as ms2 where ms2.prevMedCase_id=ms.id) as cnt17 "
		+",(select max(ms2.id) from MedCase as ms2 where ms2.id=ms.prevMedCase_id) as revmed "
		+" from MedCase as ms where ms.DTYPE='DepartmentMedCase' and ms.id=:id")
		.setParameter("id",aMedCaseId).getSingleResult() ;
		var err_mes="",isErr=false ;
		for (i=0;i<err.length;i++) {
			var cnt = err_list[i] ;
			if (cnt!=null && (+cnt)>0) {
				isErr=true ;
				err_mes = err_mes + "; "+err[i] ;
			}
		}
		if(isErr) throw "Перед удалением необходимо удалить сведения: " + err_mes.substring(2) ;
		var obj=err_list[err_list.length-1] ;
		
		if (obj!=null&&(+obj>0)) {
			//throw ""+obj ;
			var medCase = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase, new java.lang.Long(obj)) ;
			medCase.setTransferDate(null) ;
			medCase.setTransferTime(null) ;
			medCase.setTransferDepartment(null) ;
			medCase.setTargetHospType(null) ;
			
		}
	}
	
}

/**
 * При удалении
 */
function onDelete(aEntityId, aContext) {
}
