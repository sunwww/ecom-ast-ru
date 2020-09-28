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
	if (!listDep.isEmpty()) {
		var dep = listDep.get(0) ;
		throw "Уже завели случай в отделелении. <a href='entitySubclassView-mis_medCase.do?id="+dep.id+"' onclick=\"$('errorMessageContainer').style.display='none'\">Перейти к нему</a>"
		
	}
	onPreSave(aForm,null,aContext) ;
	
}

/**
 * Перед сохранением
 */
function onPreSave(aForm,aEntity, aContext) {
	var dateStart = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateStart,aForm.entranceTime);
	var transferIs = false ;
	if (aEntity!=null) {
		var date = new java.util.Date() ;
		aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
		aForm.setEditTime(Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(new java.sql.Time (date.getTime()))) ;
		aForm.setEditUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;
		
	}
	if (aForm.mkbAdc==null||aForm.mkbAdc.equals("")) {
		var lMkb = aContext.manager.createNativeQuery("select vma.code from VocMkbAdc vma left join VocIdc10 mkb on mkb.code=vma.name where  mkb.id='"+aForm.clinicalMkb+"'").getResultList() ;
		if (!lMkb.isEmpty()) throw "Необходимо ввести дополнительный код по диагнозу" ;
	}
	var bedFund = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.lpu.BedFund, aForm.bedFund) ;
	var hosp = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase,aForm.parent) ;
	var prev = +aForm.prevMedCase>0?aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase,aForm.prevMedCase):null ;
	var isDoc=aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/OwnerFunction") ;
	var isNoPalat = aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Slo/NoRoomNumber") ;
	
	//Запрет на поступление в экстренном порядке на дневные койки
	if (hosp.emergency!=null&&hosp.emergency==true && bedFund.bedSubType!=null && (bedFund.bedSubType.code=="2" || bedFund.bedSubType.code=="3")) {
		if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Sso/CreateEmergencyDayHospBedFund")){
			throw "Установлен запрет на ЭКСТРЕННУЮ госпитализацию на тип коек '"+bedFund.bedSubType.name+"'";
		}
		
	} //else throw "123";
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
		//Отмечаем режимы и диеты как окончанные			
			
		aContext.manager.createNativeQuery("update prescription p set planEndDate =to_date('"+aForm.dateStart+"','dd.MM.yyyy'), planEndTime=cast('"+aForm.entranceTime
					+"' as time) where p.prescriptionList_id in (select id from prescriptionList where medcase_id='"+prev.id+"') " +
		"and p.dtype in ('DietPrescription', 'ModePrescription') and p.planEndDate is null").executeUpdate();

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
					||(hosp.hospType.code=="DAYTIMEHOSP" && +bedSubType==3) 
					|| (hosp.hospType.code=="ALLTIMEHOSP" && +bedSubType==1)
					|| (hosp.hospType.code=="HOUSE" && +bedSubType==3)) 
			{
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
	stat=aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Discharge/OnlyCurrentDay")
	&& !aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/DeleteAdmin");
	
	
	if (stat && hosp.dischargeTime!=null) {
		

		var dateFin = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(hosp.dateFinish,hosp.dischargeTime);
		var dateCur = new java.sql.Date(new java.util.Date().getTime()) ;
		var cal1 = java.util.Calendar.getInstance() ;
		var cal2 = java.util.Calendar.getInstance() ;
		var cal3 = java.util.Calendar.getInstance() ;
		cal3.setTime(dateCur) ;		
		cal2.setTime(dateCur) ;		
		cal1.setTime(dateFin) ;
		var cntHour = +getDefaultParameterByConfig("edit_slsDischarge_after_discharge", 24, aContext) ;
		cal3.add(java.util.Calendar.HOUR_OF_DAY,(-1*cntHour)) ;
		
		if (cal1.after(cal3)) {
			
			} else{
				var param = new java.util.HashMap() ;
				
				param.put("obj","DischargeMedCase") ;
				param.put("permission" ,"editAfterDischarge") ;
				param.put("id", hosp.id) ;
				var check=aContext.serviceInvoke("WorkerService", "checkPermission", param)+"";

				if (+check==0) {
					throw "У Вас стоит ограничение на дату выписки. Вы можете выписывать в течение "+cntHour+" часов.";
					
				}
			}
	}
//проверка на перевод из реанимации в реанимацию с галочкой не входит в омс
	if (aForm!=null && prev!=null 
			&& aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/CantTransferReanimationToReanimation")) { 
		var lpu = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.lpu.MisLpu,aForm.department); 
		if (true==lpu.getIsNoOmc() && true==prev.department.getIsNoOmc())
			throw "Нельзя переводить из одной реанимации в другую реанимацию!";
	}
}
function checkPrescriptionList(aForm, aEntity, aCtx) {
	var currentDate = new java.sql.Date(new java.util.Date().getTime()) ;
	var currentTime = new java.sql.Time (currentDate.getTime());
	var username = aCtx.getSessionContext().getCallerPrincipal().toString();
	var list = aCtx.manager.createNativeQuery("select id from prescriptionList pl where pl.medCase_id="+aEntity.id).getResultList();
	var pl = null;
	if (!list.isEmpty()) {
		var id = +list.get(0);
		pl = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.PrescriptList, new java.lang.Long(id));
	} else {
		pl = Packages.ru.ecom.mis.ejb.domain.prescription.PrescriptList();
		pl.setMedCase(aEntity);
		pl.setCreateUsername(username);
		pl.setCreateDate(currentDate);
		pl.setCreateTime(currentTime);
		pl.setWorkFunction(aEntity.ownerFunction);
		aCtx.manager.persist(pl);
		
	}
	if (+aForm.mode>0){
		var mode = new Packages.ru.ecom.mis.ejb.domain.prescription.ModePrescription();
		mode.setModePrescription(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.voc.VocModePrescription, aForm.mode));
		mode.setPrescriptionList(pl);
		mode.setPrescriptSpecial(aEntity.ownerFunction);
		mode.setPlanStartDate(aEntity.dateStart);
		mode.setPlanStartTime(aEntity.entranceTime);
		mode.setCreateDate(currentDate);
		mode.setCreateTime(currentTime);
		mode.setCreateUsername(username);
		aCtx.manager.persist(mode);
		
	} else {
		throw "Не указан режим!";
	}
	if (+aForm.diet>0){
		var diet = new Packages.ru.ecom.mis.ejb.domain.prescription.DietPrescription();
		diet.setDiet(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.diet.Diet, aForm.diet));
		diet.setPrescriptionList(pl);
		diet.setPrescriptSpecial(aEntity.ownerFunction);
		diet.setPlanStartDate(aEntity.dateStart);
		diet.setPlanStartTime(aEntity.entranceTime);
		diet.setCreateDate(currentDate);
		diet.setCreateTime(currentTime);
		diet.setCreateUsername(username);
		aCtx.manager.persist(diet);
		
	} else {
		throw "Не указана диета!";
	}
}
//При сохранении
function onCreate(aForm, aEntity, aContext) {
	if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Slo/ForceCreatePrescriptionList")) {
		checkPrescriptionList(aForm, aEntity, aContext);
	}

	//aEntity.setCreateTime(new java.sql.Time ((new java.util.Date()).getTime())) ;
	onSave(aForm, aEntity, aContext) ;
    transferObservRoomToChild(aForm, aEntity, aContext) ;
}
//Milamesher #101 проставить палату матери в обсервац. её новорождённым детям
function transferObservRoomToChild(aForm, aEntity, aContext) {
    if (aEntity.prevMedCase!=null && aEntity.department.getIsObservable() && aEntity.roomNumber!=null && aEntity.bedNumber!=null) { //если это перевод в обсервационное
        //Нужно брать палаты и койки по наименованию, но отделение - новорождённых
        var list = aContext.manager.createNativeQuery("select wp.id as wpid,wpBed.id as wpbid from workplace wp" +
            " left join mislpu lpu on lpu.id=wp.lpu_id" +
            " left join WorkPlace wp1 on wp1.id=wp.parent_id" +
            " left join WorkPlace wpBed on wpBed.parent_id=wp.id" +
            " where lpu.isnewborn=true and " +
            " wp.name=(select wp.name from workplace wp" +
            " left join mislpu lpu on lpu.id=wp.lpu_id" +
            " left join WorkPlace wp1 on wp1.id=wp.parent_id where wp.id=" + aEntity.roomNumber.id + " and" +
            " wp.dtype='HospitalRoom' and (wp.isnoactuality is null or wp.isnoactuality='0')) and" +
            " wp.dtype='HospitalRoom' and (wp.isnoactuality is null or wp.isnoactuality='0') and" +
            " wpBed.dtype='HospitalBed' and (wpBed.isnoactuality is null or wpBed.isnoactuality='0') and" +
            " wpBed.name = (select name from WorkPlace where id=" + aEntity.bedNumber.id + ") limit 1").getResultList();
        if (list.size() >0) {
        	var obj=list.get(0);
            var rid = obj[0];
            var bid = obj[1];
            aContext.manager.createNativeQuery("update medcase mc set roomnumber_id=" + rid +
                " ,bednumber_id= " + bid +
                " where id=ANY(select dmc2.id from medcase dmc" +
                " left join ChildBirth chb on chb.medcase_id=dmc.id" +
                " left join newborn nb on nb.childbirth_id=chb.id" +
                " left join patient pat on pat.id=nb.patient_id" +
                " left join medcase hmc on hmc.patient_id=pat.id" +
                " left join medcase dmc2 on dmc2.parent_id=hmc.id" +
                " left join mislpu lpu on lpu.id=mc.department_id" +
                " where hmc.datestart=nb.birthdate and dmc2.id is not null and mc.dtype='DepartmentMedCase'" +
                " and lpu.isnewborn=true and dmc.id= " + aEntity.prevMedCase.id + " )").executeUpdate();
        }
    }
}
function onSave(aForm, aEntity, aContext) {
	//var dat =(new java.util.Date()).getTime() ;
	//aEntity.setEditTime(new java.sql.Time (dat)) ;

	//Оставить след. строку. Если её закомментировать - то всё будет плохо!
		 aContext.manager.createQuery("from MedCase where prevMedCase_id=:prev").setParameter("prev",aForm.id).getResultList() ;
    /*    if (listDep.size()>0) {
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
		aContext.manager.persist(aEntity.prevMedCase);
	}
	Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DepartmentSaveInterceptor.setDiagnosis(aContext.manager, aEntity.getId(), aForm.getComplicationDiags(), "4", "4",null) ;
	Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DepartmentSaveInterceptor.setDiagnosis(aContext.manager, aEntity.getId(), aForm.getConcomitantDiags(), "4","3",null) ;
	Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DepartmentSaveInterceptor.setDiagnosis(aContext.manager, aEntity.getId(), aForm.getClinicalMkb()+"@#@ @#@"+aForm.getClinicalDiagnos(), "4", "1",aForm.clinicalActuity,aForm.mkbAdc) ;
    checkDouble(aForm,aEntity, aContext);
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
		,"протоколов"
		,"полисов, прикрепленных к случаю"
		,"случая смерти"
		,"данные родов"
		,"беременности, прикрепленных к случаю"
		,"данные обменной карты"
		,"предыдущие случаи лечения в отделении"
		] ;
		//throw medCase.getId() + "  getStatisticStub()="+medCase.getStatisticStub() ;
		var err_list = aContext.manager.createNativeQuery("select"
		+" (select count(*) from Diagnosis as d where d.medCase_id=ms.id) as cnt1"
		+" ,(select count(*) from MedCase as ms1 where ms1.parent_id=ms.id) as cnt2"
		+" ,(select count(*) from SurgicalOperation as so where so.medCase_id=ms.id) as cnt3"
		+" ,(select count(*) from Transfusion as tr where tr.medCase_id=ms.id) as cnt4"
		+" ,(select count(*) from Vaccination as v where v.medCase_id=ms.id) as cnt5"
		+" ,(select count(*) from PhoneMessage as pm where ms.id=pm.id) as cnt6"
		+" ,(select count(*) from TemperatureCurve as tc where tc.medCase_id=ms.id) as cnt7"
		+" ,(select count(*) from PrescriptionList as pl where pl.medCase_id=ms.id) as cnt8"
		+" ,(select count(*) from Diary as d where d.medCase_id=ms.id) as cnt10"
		+" ,(select count(*) from MedCase_MedPolicy as pol where pol.medCase_id=ms.id) as cnt11"
		+" ,(select count(*) from DeathCase as dc where dc.medCase_id=ms.id) as cnt13"
		+" ,(select count(*) from ChildBirth as cb where cb.medCase_id=ms.id) as cnt14"
		+" ,(select count(*) from PregnancyHistory as ph where ph.medCase_id=ms.id) as cnt15"
		+" ,(select count(*) from PregnanExchangeCard as pec where pec.medCase_id=ms.id) as cnt16"
		+" ,(select count(*) from MedCase as ms2 where ms2.prevMedCase_id=ms.id) as cnt17"
		+" ,(select max(ms2.id) from MedCase as ms2 where ms2.id=ms.prevMedCase_id) as revmed"
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
			aContext.manager.persist(medCase);
		}
	}
	
}

/**
 * При удалении
 */
function onDelete(aEntityId, aContext) {
}
//Проверка на дубли при создании первого СЛО в СЛС
function checkDouble(aForm,aEntity, aContext) {
    var listDep;
    var inBd = true;
    if (aForm.prevMedCase == 0) {
        listDep = aContext.manager.createNativeQuery("select id from MedCase where parent_id='" + aForm.parent + "' and dtype='DepartmentMedCase' and prevMedCase_id=null")
            .getResultList();
        if (listDep.isEmpty()) {
            inBd = false;
            listDep = aContext.manager.createQuery("from MedCase where parent_id=:parent and dtype='DepartmentMedCase' and prevMedCase_id=null")
                .setParameter("parent", aForm.parent)
                .getResultList();
        }
    }
    else {
        listDep = aContext.manager.createNativeQuery("select id from MedCase where parent_id='" + aForm.parent + "' and dtype='DepartmentMedCase' and prevMedCase_id="+aForm.prevMedCase)
            .getResultList();
        if (listDep.isEmpty() || (!listDep.isEmpty() &&  listDep.get(0)==aEntity.id)) {
            inBd = false;
            listDep = aContext.manager.createQuery("from MedCase where parent_id=:parent and dtype='DepartmentMedCase' and prevMedCase_id="+aForm.prevMedCase)
                .setParameter("parent", aForm.parent)
                .getResultList();
        }
	}
    for (var i = 0; i < listDep.size(); i++) {
        var obj = listDep.get(i);
        var sloId = inBd ?
            obj :
            obj.id;
        if (sloId != aEntity.id) {
            if (inBd)
                aContext.manager.createNativeQuery("delete from MedCase where id='" + aEntity.id + "'").executeUpdate();
            throw "Уже завели случай в отделелении. <a href='entitySubclassView-mis_medCase.do?id=" + sloId + "' onclick=\"$('errorMessageContainer').style.display='none'\">Перейти к нему</a>"
        }
    }
}