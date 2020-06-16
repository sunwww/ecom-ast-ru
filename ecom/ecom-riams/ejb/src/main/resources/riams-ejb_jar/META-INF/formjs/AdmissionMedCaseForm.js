/**
 * При просмотре
 */
function onView(aForm, aEntity, aContext) {
	
}


function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateTime(Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(new java.sql.Time (date.getTime()))) ;
	if (aForm.getIsIdentified()!=null && aForm.getIsIdentified()==true) {
        aForm.setIdentDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
        aForm.setIdentTime(Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(new java.sql.Time (date.getTime()))) ;
        aForm.setIdentUsername(aForm.username);
	}
	Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.SecPolicy.checkPolicyCreateHour(aCtx.getSessionContext()
	        , aForm.getDateStart(), aForm.getEntranceTime());
	onPreSave(aForm,null,aCtx);
    date = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.getDateStart());
    var cal = java.util.Calendar.getInstance() ;
	cal.setTime(date) ;
	var year = cal.get(java.util.Calendar.YEAR) ;
	var aStatCardNumber = aForm.statCardNumber ;
	if (aStatCardNumber!=null && aStatCardNumber.trim()!="") {
		if (!aCtx.manager.createQuery("from StatisticStub where code=:number and year=:year and DTYPE='StatisticStubExist'")
			.setParameter("number", aStatCardNumber)
			.setParameter("year",java.lang.Long.valueOf(year)).getResultList().isEmpty()
		) {
			throw "Номер стат.карты "+aStatCardNumber + " уже существует в "+year+" году!!!";
		}
	}
}
function onCreate(aForm, aEntity, aCtx) {
	var manager = aCtx.manager;
	//aEntity.setCreateTime(new java.sql.Time ((new java.util.Date()).getTime())) ;
	if (aForm.attachedPolicies!="" && aForm.attachedPolicies>0) {
		var medPolicyOmc = manager.find(Packages.ru.ecom.mis.ejb.domain.patient.MedPolicy,aForm.attachedPolicies) ;
		var mp1 = new Packages.ru.ecom.mis.ejb.domain.medcase.MedCaseMedPolicy() ;
		mp1.setPolicies(medPolicyOmc) ;
		mp1.setMedCase(aEntity) ;
		manager.persist(mp1) ;
	}
	if (aForm.attachedPolicyDmc!="" && aForm.attachedPolicyDmc>0) {
		var medPolicyDmc = manager.find(Packages.ru.ecom.mis.ejb.domain.patient.MedPolicy,aForm.attachedPolicyDmc) ;
		var mp2 = new Packages.ru.ecom.mis.ejb.domain.medcase.MedCaseMedPolicy() ;
		mp2.setPolicies(medPolicyDmc) ;
		mp2.setMedCase(aEntity) ;
		manager.persist(mp2) ;
	}
	if (aForm.pregnancyOrderNumber!=null && aForm.pregnancyOrderNumber>0) {
		var list = manager.createQuery("from Pregnancy where patient=:pat and orderNumber=:number")
			.setParameter("pat",aEntity.patient)
			.setParameter("number",aForm.pregnancyOrderNumber)
			.getResultList() ;
		var preg ;
		if (list.size()>0) {
			preg = list.get(0) ;
		} else {
			preg = new Packages.ru.ecom.mis.ejb.domain.birth.Pregnancy() ;
			preg.setOrderNumber(aForm.pregnancyOrderNumber) ;
			preg.setChildbirthAmount(aForm.childbirthAmount) ;
			preg.setPatient(aEntity.patient) ;
			manager.persist(preg) ;
		}
		aEntity.setPregnancy(preg) ;
	}
    //Milamesher 28.08.2018 #108 - дополнение: при госпитализации пациента все открытые СПО закрываются датой последнего визита
	//Закрытие всех СПО
    var listSpoByPat = manager.createNativeQuery("select id from medcase where dtype='PolyclinicMedCase' and datefinish is null and patient_id="+aEntity.patient.id).getResultList();
    for (var ind=0 ; ind< listSpoByPat.size() ; ind++) {
        var opSpoId = listSpoByPat.get(ind);
        closeSpo(aCtx,opSpoId);
    }
    	var list = new Packages.ru.ecom.mis.ejb.domain.prescription.PrescriptList();
		list.setMedCase(aEntity);
		list.setCreateUsername(aForm.username);
		aCtx.manager.persist(list);

	if (+aForm.preHosp>0) { //Если есть предварительная госпитализация - связываем её с госпитализацией
		var preHosp = manager.find(Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarHospitalBed,aForm.preHosp) ;
		preHosp.setMedCase(aEntity);
		manager.persist(preHosp);

	}
	chekIfOutOfReceivingDep(aForm,aEntity,aCtx);
}
//Закрытие СПО по id (взято с SmoVisitService) - датой последнего визита
function closeSpo(aContext, aSpoId) {
    var listOpenVis = aContext.manager.createNativeQuery("select vis.id as visid"
        +" ,vis.dateStart as mkbid"
        +" from MedCase vis"
        +" where vis.parent_id="+aSpoId
        +" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase')"
        +" and vis.dateStart is null"
    ).setMaxResults(1).getResultList() ;
    var listVisLast = aContext.manager.createNativeQuery("select vis.id as visid"
        +" ,mkb.id as mkbid, to_char(vis.dateStart,'dd.mm.yyyy') as dateStart, vis.workFunctionExecute_id"
        +" from MedCase vis"
        +"     left join WorkFunction wf on vis.workFunctionExecute_id = wf.id"
        +"     left join VocWorkFunction vwf on vwf.id=wf.workFunction_id"
        +"     left join Worker w on wf.worker_id = w.id"
        +"     left join Patient pat on w.person_id = pat.id"
        +" left join VocReason vr on vis.visitReason_id = vr.id"
        +" left join VocServiceStream vss on vss.id=vis.serviceStream_id"
        +" left join Diagnosis diag on diag.medcase_id=vis.id"
        +" left join VocIdc10 mkb on mkb.id=diag.idc10_id"
        +" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
        +" left join VocVisitResult vvr on vvr.id=vis.visitResult_id"
        +" where vis.parent_id="+aSpoId
        +" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase')"
        +" and vis.dateStart is not null"
        +" and (vpd.code='1' or vpd.id is null)"
        +" group by vis.id, vis.dateStart,vis.workfunctionexecute_id, vis.timeExecute,vwf.name, pat.lastname,  pat.firstname,  pat.middlename"
        +" ,vr.name ,vss.name,vvr.name,vpd.code,vpd.id,mkb.id"
        +" order by vis.dateStart desc, vis.timeExecute desc").setMaxResults(1).getResultList() ;
    if (listOpenVis.isEmpty() && !listVisLast.isEmpty()) {
        var listVisFirst = aContext.manager.createNativeQuery("select vis.id as visid"
            +" ,mkb.id as mkbid, to_char(vis.dateStart,'dd.mm.yyyy') as dateStart, vis.workFunctionExecute_id"
            +" from MedCase vis"
            +"     left join WorkFunction wf on vis.workFunctionExecute_id = wf.id"
            +"     left join VocWorkFunction vwf on vwf.id=wf.workFunction_id"
            +"     left join Worker w on wf.worker_id = w.id"
            +"     left join Patient pat on w.person_id = pat.id"
            +" left join VocReason vr on vis.visitReason_id = vr.id"
            +" left join VocServiceStream vss on vss.id=vis.serviceStream_id"
            +" left join Diagnosis diag on diag.medcase_id=vis.id"
            +" left join VocIdc10 mkb on mkb.id=diag.idc10_id"
            +" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
            +" left join VocVisitResult vvr on vvr.id=vis.visitResult_id"
            +" where vis.parent_id="+aSpoId
            +" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase')"
            +" and vis.dateStart is not null"
            +" and (vpd.code='1' or vpd.id is null) "
            +" group by vis.id, vis.dateStart,vis.workfunctionexecute_id, vis.timeExecute,vwf.name, pat.lastname,  pat.firstname,  pat.middlename"
            +" ,vr.name ,vss.name,vvr.name,vpd.code,vpd.id,mkb.id"
            +" order by vis.dateStart, vis.timeExecute").setMaxResults(1).getResultList() ;
        var mkb = listVisLast.get(0)[1];
        if (mkb==null) {
            var listMkb = aContext.manager.createNativeQuery("select vis.id as visid"
                +" ,mkb.id as mkbid, to_char(vis.dateStart,'dd.mm.yyyy') as dateStart, vis.workFunctionExecute_id"
                +" from MedCase vis"
                +"     left join WorkFunction wf on vis.workFunctionExecute_id = wf.id"
                +"     left join VocWorkFunction vwf on vwf.id=wf.workFunction_id"
                +"     left join Worker w on wf.worker_id = w.id"
                +"     left join Patient pat on w.person_id = pat.id"
                +" left join VocReason vr on vis.visitReason_id = vr.id"
                +" left join VocServiceStream vss on vss.id=vis.serviceStream_id"
                +" left join Diagnosis diag on diag.medcase_id=vis.id"
                +" left join VocIdc10 mkb on mkb.id=diag.idc10_id"
                +" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
                +" left join VocVisitResult vvr on vvr.id=vis.visitResult_id"
                +" where vis.parent_id="+aSpoId
                +" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase')"
                +" and vis.dateStart is not null"
                +" and (vpd.code='1' or vpd.id is null) and (vis.noActuality='0' or vis.noActuality is null) and mkb.id is not null"
                +" group by vis.id, vis.dateStart,vis.workfunctionexecute_id, vis.timeExecute,vwf.name, pat.lastname,  pat.firstname,  pat.middlename"
                +" ,vr.name ,vss.name,vvr.name,vpd.code,vpd.id,mkb.id"
                +" order by vis.dateStart desc, vis.timeExecute desc").setMaxResults(1).getResultList() ;
            mkb = listMkb.isEmpty() ? null : listMkb.get(0)[1];
        }
        var dateStart = listVisFirst.get(0)[2];
        var dateFinish = listVisLast.get(0)[2];
        var startWF = listVisFirst.get(0)[3];
        var finishWF = listVisLast.get(0)[3];
        if (dateStart!=null && dateStart!='null' && dateStart!='' &&
            dateFinish!=null && dateFinish!='null' && dateFinish!='' &&
            startWF!=null && startWF!='null' && startWF!='' &&
            finishWF!=null && finishWF!='null' && finishWF!='')
        aContext.manager.createNativeQuery("update medcase set dateFinish=to_date('"+dateFinish
            +"','dd.mm.yyyy'),dateStart=to_date('"+dateStart
            +"','dd.mm.yyyy'),finishFunction_id='"+finishWF+"',startFunction_id='"+startWF
            +"'"+(mkb!=null?(",idc10_id='"+mkb+"'"):"")+" where id="+aSpoId).executeUpdate() ;
    }
    return aSpoId;
}
function onPreDelete(aMedCaseId, aContext) {
	throw "Нельзя удалять поступление, удалится весь случай !!!"
}

function onSave(aForm,aEntity,aCtx) {
	chekIfOutOfReceivingDep(aForm,aEntity,aCtx);
	//aEntity.setEditTime(new java.sql.Time ((new java.util.Date()).getTime())) ;


	var sql = "update MedCase set dateStart=:dateStart,entranceTime=:entranceTime,department_id=:dep, lpu_id=:lpu where parent_id=:idSLS and DTYPE='DepartmentMedCase' and prevMedCase_id is null";
	
	aCtx.manager.createNativeQuery(sql)
		.setParameter("dateStart",aEntity.dateStart)
		.setParameter("entranceTime",aEntity.entranceTime)
		.setParameter("dep",aForm.department)
		.setParameter("lpu",aForm.lpu)
		.setParameter("idSLS",aForm.id)
		.executeUpdate() ;
	aCtx.manager.createNativeQuery("update medcase set emergency='"+(aForm.emergency ? "1" : "0")+"' where parent_id="+aForm.id+" and dtype='DepartmentMedCase'").executeUpdate();
}

function onPreSave(aForm,aEntity, aCtx) {
	var pat = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,aForm.getPatient());
	var deathDate = pat.getDeathDate();
	if (deathDate != null) {
		var startDate = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.dateStart);
		if (startDate.getTime() > deathDate.getTime()) {
		throw "Невозможно создать СЛС позже даты смерти пациента: "
			+Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(deathDate);
		}
	}

	if (aForm.getEmergency()==null || !aForm.getEmergency()) {
		if (+aForm.orderType>0) throw "При плановой госпитализации раздел доставлен не заполняется" ;
		if (+aForm.intoxication>0) throw "При плановой госпитализации раздел доставлен не заполняется" ;
		if (+aForm.preAdmissionDefect>0) throw "При плановой госпитализации раздел доставлен не заполняется" ;
		if (aForm.supplyOrderNumber!=null&&aForm.supplyOrderNumber!="") throw "При плановой госпитализации раздел доставлен не заполняется" ;
		if (+aForm.preAdmissionTime>0) throw "При плановой госпитализации раздел доставлен не заполняется" ;
		if (pat.newborn==null) {
		//	if (aForm.orderDate ==null || aForm.orderDate.equals("")) {throw aForm.orderDate+"При плановой госпитализации необходимо заполнять поле Дата направления!!!" ;}
			if (+aForm.orderMkb>0) {} else {throw "При плановой госпитализации необходимо заполнять поле Код МКБ направителя!!!" ;}
			if (+aForm.orderLpu>0) {} else {throw "При плановой госпитализации необходимо заполнять поле Кем направлен!!!" ;}
			if (+aForm.sourceHospType>0) {} else {throw "При плановой госпитализации необходимо заполнять поле Тип направившего ЛПУ!!!" ;}
		}
		//Рост и вес - обязательные поля
        if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Admission/MustFillHeigthAndWeigth")) {
			if (+aForm.height==0 || +aForm.weight==0) {throw "При плановой госпитализации поля \"рост\", \"вес\" являются обязательными";}
		}
	} else {
		if (+aForm.orderType>0) {} else {throw "При экстренной госпитализации раздел доставлен является обязательным для заполнения!!!" ;}
		if (+aForm.preAdmissionTime>0) {} else {throw "При экстренной госпитализации раздел доставлен является обязательным для заполнения!!!" ;}
	}
	if (pat.newborn==null) {
		if (+aForm.orderLpu>0) {
			if (+aForm.orderMkb==0 && (aForm.getEmergency()!=null && !aForm.getEmergency())) {throw "Если указано поле кем направлен необходимо заполнить код МКБ направителя!!!" ;}
		}
	}
	
	if (aForm.deniedHospitalizating>0) {
		if (aEntity!=null) {
			var sql = "select count(*) from MedCase  m "
				+" where m.parent_id='"+aForm.id
				+"' and m.dtype='DepartmentMedCase'" ;
				;
			var list = aCtx.manager.createNativeQuery(sql)
					.getSingleResult() ;
			if (+list>0) {
				
				throw "Запрет на установку отказа от госпитализации. Уже оформлен случай лечения в отделении." ;
			}
		}
	} else {
		var sql = "select m.id, ss.code from MedCase  m "
			+" left join StatisticStub ss on ss.id=m.statisticStub_id "
			+" where m.patient_id='"+aForm.patient
			+"' and m.dateStart=to_date('"+aForm.dateStart+"','dd.mm.yyyy')"
			+" and m.dtype='HospitalMedCase' and m.department_id='"+aForm.department
			+"' and m.deniedHospitalizating_id is null and m.dateFinish!=to_date('"+aForm.dateStart+"','dd.mm.yyyy')" ;
		if (+aForm.id>0) {
			sql = sql+" and m.id!='"+aForm.id+"'" ;
		}	
		var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
		if (!list.isEmpty()) {
			var obj = list.get(0) ;
			throw "Уже оформлена госпитализация за "+aForm.dateStart+" <a href='entitySubclassView-mis_medCase.do?id="+obj[0]+"'>№стат.карты "+obj[1]+"</a>" ;
		}
		if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Slo/UnionSlo")||
				aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Admission/EditDepartment")){
		} else {
			list = aCtx.manager.createNativeQuery("select sls.id ,ml.name from medcase sls" +
	    			" left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'" +
	    			" left join mislpu ml on ml.id=slo.department_id" +
	    			" where sls.id =:sls" +
	    			" and slo.prevmedcase_id is null and "+aForm.getDepartment()+"!=slo.department_id").setParameter("sls",aForm.getId()).getResultList();
	    	if (!list.isEmpty()) {
	    		throw "Уже создан случай лечение в отделении ("+list.get(0)[1]+"), изменение отделения невозможно";
	    	}
		} 
		 
	}
	if (aEntity!=null) {
		var date = new java.util.Date() ;
		aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
		aForm.setEditTime(Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(new java.sql.Time (date.getTime()))) ;
		aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	}
	if (aForm.dateFinish!=null && aForm.dateFinish!=""
		  &&aForm.dischargeTime!=null &&aForm.dischargeTime!="") {
			var dateStart1 = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateStart,aForm.entranceTime);
			var dateFinish = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateFinish,aForm.dischargeTime);
			if (!(dateFinish.getTime() > dateStart1.getTime())) throw "Дата выписки "+
			aForm.dateFinish+" "+aForm.dischargeTime+" должна быть больше, чем дата поступления "+aForm.dateStart+" "
			+aForm.entranceTime;
			
		}
	
	var stat=aCtx.getSessionContext().isCallerInRole(Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.StatisticStubStac.CreateHour) ;
	var psych=aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/IsPsychiatry") ;
	var dateStart = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateStart,aForm.entranceTime);
	
	if (stat) {
		var check = Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.SecPolicy.isDateLessThen24Hour(dateStart);
		if (!check) throw "У Вас стоит ограничение на дату поступления. Дата поступления меньше на 24 часа, чем текущая дата" ;
	}
	
	// Проверка введенных данных
	if (aForm.getDeniedHospitalizating()!=null && aForm.getDeniedHospitalizating()!=0) {
		if (aForm.getAmbulanceTreatment()==null || !aForm.getAmbulanceTreatment()) {
			throw "При отказе от госпитализации указывается, что лечение амбулаторное!";
		}
	}
    if ( (aForm.getAmbulanceTreatment()!=null && aForm.getAmbulanceTreatment()) 
       || (aForm.getDeniedHospitalizating()!=null && aForm.getDeniedHospitalizating()!=0)
       ) {
			if (aForm.getDepartment()!=null && aForm.getDepartment()!=0) {
			 //throw "При отказе от госпитализации и/или амбулаторном лечении поле отделение не заполняется!";
			}
    } else {
           if (aForm.getDepartment()==null || aForm.getDepartment()==0) {
               throw "При госпитализации пациента нужно указывать отделение!";
           } else if (aForm.getHospitalization()==null || aForm.getHospitalization()==0) {
               throw "При госпитализации пациента нужно указывать первичность госпитализации по данному заболеванию!";
           } else if (psych && (aForm.getPsychReason()==null || aForm.getPsychReason()==0)) {
			   throw "При госпитализации пациента в психиатрический стационар нужно указывать причину!";
		   }
    }
    if (aEntity!=null && aEntity.getDeniedHospitalizating()!=null
    		&&+aForm.getDeniedHospitalizating()==0) {
    	var statCardNumber = aForm.statCardNumber ;
    	if (aEntity.statisticStub!=null) {
	    	if (statCardNumber==null || statCardNumber.trim()=="") {
	    		var hand = aCtx.getSessionContext().isCallerInRole(Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.StatisticStubStac.CreateStatCardBeforeDeniedByHand) ;
	    		
	    		if (hand) throw "Не указан номер стат. карты" ;
	    	} else {
	    		var year = aForm.getDateStart().substring(6) ;
	    		if (!aCtx.getManager()
	    				.createNativeQuery("select id from StatisticStub where medCase_id='"+aForm.getId()+"' and DTYPE='StatisticStubExist' and code=:number and year=:year ")
	    			.setParameter("number", statCardNumber)
	    			.setParameter("year",java.lang.Long.valueOf(year))
	    			.getResultList().isEmpty())  {
					throw "Номер стат. карты "+statCardNumber+" в "+year+" уже зарегистрирован!!!" ;
				}
	    		
	    	}
    	}
    }
    sendMsg(aForm,aEntity, aCtx,"dzaharov");
    sendMsg(aForm,aEntity, aCtx,"nkostenko");
}
//Milamesher 24042017
function sendMsg(aForm,aEntity, aCtx,user) {
	 var pat = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,aForm.getPatient());
	 var nationality=pat.getNationality(); 
	 if (nationality!=null && !nationality.getCode().equals("643")) {
	    	if (aForm.getEmergency()!=null && aForm.getEmergency() && aForm.getDepartment()!=null && aForm.getDepartment()!=0) {
	    		
	    		//здесь код, а потом этот код - в условия n!=171
	    	//	var listnat =  aCtx.getManager().createNativeQuery("select name from Omc_Oksm where id='" + n + "'").getResultList() ; 
	    		var list = aCtx.getManager().createNativeQuery("select name from mislpu where id=" + aForm.getDepartment() ).getResultList() ;
	    		var m = "Гражданин (" + nationality.getName() + ") " + pat.getPatientInfo() + " госпитализирован в " + list.get(0);
	    		var mes = new Packages.ru.ecom.ejb.services.live.domain.CustomMessage() ;
				mes.setMessageText(m) ;
				mes.setMessageTitle("Госпитализация иностранного гражданина") ;
				var date = new java.util.Date() ;
				var dispatchDate = new java.sql.Date(date.getTime()) ; 
				var dispatchTime = new java.sql.Time(date.getTime()) ;
				mes.setRecipient(user) ;   //whom
				mes.setDispatchDate(dispatchDate) ;
				mes.setDispatchTime(dispatchTime) ;
				mes.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString() ) ;  
				mes.setIsEmergency(false) ;
				aCtx.manager.persist(mes) ;
	    	}
	 }
}
//Milamesher 06092018 проверка на выбыл ли пациент из приёмника
//выбывает в случае, если меняется поле отказа с null на не null и наоборот
function chekIfOutOfReceivingDep(aForm,aEntity, aCtx) {
	var date = new java.util.Date().getTime() ;
		if (aForm.deniedHospitalizating>0
			&& aCtx.manager.createNativeQuery("select id from VocDeniedHospitalizating where id=" + aForm.deniedHospitalizating+" and code='IN_PIGEON_HOLE'").setMaxResults(1).getResultList().isEmpty()) { //отказ от госпитализации
			if (aEntity.deniedHospitalizating == null || aForm.deniedHospitalizating !== aEntity.deniedHospitalizating.id ) { //если новая причина отказа != старой причине отказа
				aEntity.setTransferDate(new java.sql.Date(date));
				aEntity.setTransferTime(new java.sql.Time(date));
				aCtx.manager.persist(aEntity);
			}
		} else { //нет отказа - нет даты выбытия из приемника
			aEntity.setTransferDate(null);
			aEntity.setTransferTime(null);
			aCtx.manager.persist(aEntity);
		}
}