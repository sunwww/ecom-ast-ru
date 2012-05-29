/**
 * Перед созданием
 */
function onPreCreate(aForm, aContext) {
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	//aForm.setEditTime(new java.sql.Time (date.getTime())) ;
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
	var dateStart = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateStart,aForm.entranceTime)
	var transferIs = false ;
	var prof = "ALLTIMEHOSP" ;
	if (aEntity!=null) {
		var date = new java.util.Date() ;
		aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
		//aForm.setEditTime(new java.sql.Time (date.getTime())) ;
		aForm.setEditUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;
		
	}
	var bedFund = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.lpu.BedFund, aForm.bedFund) ;
	var hosp = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase,aForm.parent) ;
	if (bedFund!=null && bedFund.bedSubType!=null) {
		var bedSubType = bedFund.bedSubType.code ;
		if (hosp.hospType!=null) {
			if ((hosp.hospType.code=="DAYTIMEHOSP" && +bedSubType==2) 
				|| (hosp.hospType.code=="ALLTIMEHOSP" && +bedSubType==1) ) {
			} else {
				throw "Не соответствует тип стационара "+hosp.hospType.name+" и профиль коек "+ bedFund.bedSubType.name;
			}
		}
		
	}
	if (aForm.transferDate != null && aForm.transferDate!="") {
		var dateTransfer ;
		
		try {
			dateTransfer = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.transferDate,aForm.transferTime)
		} catch(e) {
			throw "Неправильно введена дата перевода или время!" ;
		}
		//var dateCur = new java.sql.Date(new java.util.Date().getTime()) ;
		//var dateTsql = new java.sql.Date(dateTransfer.getTime()) ;
		if (!(dateTransfer.getTime() > dateStart.getTime())) throw "Дата перевода должна быть больше, чем дата поступления";
		//if ((((dateTsql.getTime()-dateCur.getTime())/1000/60/60)%24)>6) throw "Максимальная дата перевода - сегодняшняя" ;
		// необходимо проверить заполнено ли поле отделение перевода
		if (aForm.transferDepartment==null || aForm.transferDepartment==0) {
			throw "При оформлении перевода необходимо указывать отделение!"
		} else {

			if (aForm.transferDepartment.equals(aForm.department)) throw "Отделение перевода и поступления должны быть разными!";
		}
		// заполнены поля перевода
		transferIs = true ;
	} else {
		// необходимо проверить остальные поля перевода
		if (aForm.transferTime != null && aForm.transferTime!="") {
			if (aForm.transferDepartment!=null && aForm.transferDepartment!=0) {
				throw "Необходимо заполнить либо все поля перевода (дату, время и отделение), либо все поля выписки (дату и время)"
			}
		}
	}
	if (aForm.dateFinish != null && aForm.dateFinish !="") {
		if (transferIs) throw "Заполняются либо поля перевода, либо выписки!"
		var dateFinish ;
		try {
			dateFinish = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateFinish,aForm.dischargeTime)
		} catch(e) {
			throw "Неправильно введена дата выписки или время!"
		}
		if (!(dateFinish.getTime() > dateStart.getTime())) throw "Дата выписки должна быть больше, чем дата поступления";
	} else {
		if (aForm.dischargeTime!=null && aForm.dischargeTime!="") {
			if (transferIs) {
				throw "Заполняются либо поля перевода, либо выписки!"
			} else {
				throw "При оформлении выписки необходимо заполнить дату!"
			}
		}
	}
}

//При сохранении
function onSave(aForm, aEntity, aContext) {
	var listDep = aContext.manager.createQuery("from MedCase where prevMedCase_id=:prev")
		.setParameter("prev",aForm.id).getResultList() ;
	if (listDep.size()>0) {
		var medCase = listDep.get(0) ;
		medCase.dateStart = aEntity.transferDate ;
		medCase.entranceTime = aEntity.transferTime ;
		if (medCase.department != aEntity.transferDepartment) {
			medCase.department = aEntity.transferDepartment ;
		}
		aContext.manager.persist(medCase) ;
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
		+" (select count(*) from Diagnosis as d where d.medCase_id=ms.id)"
		+",(select count(*) from MedCase as ms1 where ms1.parent_id=ms.id)"
		+",(select count(*) from SurgicalOperation as so where so.medCase_id=ms.id)"
		+",(select count(*) from Transfusion as tr where tr.medCase_id=ms.id)"
		+",(select count(*) from Vaccination as v where v.medCase_id=ms.id)"
		+",(select count(*) from PhoneMessage as pm where ms.id=pm.id)"
		+",(select count(*) from TemperatureCurve as tc where tc.medCase_id=ms.id)"
		+",(select count(*) from PrescriptionList as pl where pl.medCase_id=ms.id)"
		+",(select count(*) from PrescriptionBlank as pb where pb.medCase_id=ms.id)"
		+",(select count(*) from Diary as d where d.medCase_id=ms.id)"
		+",(select count(*) from MedCase_MedPolicy as pol where pol.medCase_id=ms.id)"
		+",(select count(*) from BirthCase as bc where bc.medCase_id=ms.id)"
		+",(select count(*) from DeathCase as dc where dc.medCase_id=ms.id)"
		+",(select count(*) from ChildBirth as cb where cb.medCase_id=ms.id)"
		+",(select count(*) from PregnancyHistory as ph where ph.medCase_id=ms.id)"
		+",(select count(*) from PregnanExchangeCard as pec where pec.medCase_id=ms.id)"
		+",(select count(*) from MedCase as ms2 where ms2.prevMedCase_id=ms.id)"
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
	}
	
}


