/**
 * При просмотре
 */
function onView(aForm, aEntity, aContext) {
	
}


function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateTime(Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(new java.sql.Time (date.getTime()))) ;

	Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.SecPolicy.checkPolicyCreateHour(aCtx.getSessionContext()
	        , aForm.getDateStart(), aForm.getEntranceTime());
	onPreSave(aForm,null,aCtx)
    var date = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.getDateStart());
    var cal = java.util.Calendar.getInstance() ;
	cal.setTime(date) ;
	var year = cal.get(java.util.Calendar.YEAR) ;
 	var ret = false ;
	var aStatCardNumber = aForm.statCardNumber ;
	if (aStatCardNumber!=null && aStatCardNumber!="") {
		var list = aCtx.manager.createQuery("from StatisticStub where code=:number and year=:year and DTYPE='StatisticStubExist'")
			.setParameter("number", aStatCardNumber).setParameter("year",java.lang.Long.valueOf(year)).getResultList() ;
		ret = (list==null || list.isEmpty()) ? false : true ;
	}
	if (ret==true) {
		throw "Номер стат.карты "+aStatCardNumber + " уже существует в "+year+" году!!!";
	}
	
}
function onCreate(aForm, aEntity, aCtx) {
	//aEntity.setCreateTime(new java.sql.Time ((new java.util.Date()).getTime())) ;
	if (aForm.attachedPolicies!="" && aForm.attachedPolicies>0) {
		var medPolicyOmc = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.MedPolicy,aForm.attachedPolicies) ;
		var mp1 = new Packages.ru.ecom.mis.ejb.domain.medcase.MedCaseMedPolicy() ;
		mp1.setPolicies(medPolicyOmc) ;
		mp1.setMedCase(aEntity) ;
		aCtx.manager.persist(mp1) ;
		//var sql="insert into medCase_medPolicy set medCase_id='"+aEntity.id+"',policies_id='"+aForm.attachedPolicies+"'" ;
		//aCtx.manager.createNativeQuery(sql).executeUpdate() ;
	}	
	if (aForm.attachedPolicyDmc!="" && aForm.attachedPolicyDmc>0) {
		var medPolicyDmc = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.MedPolicy,aForm.attachedPolicyDmc) ;
		var mp2 = new Packages.ru.ecom.mis.ejb.domain.medcase.MedCaseMedPolicy() ;
		mp2.setPolicies(medPolicyDmc) ;
		mp2.setMedCase(aEntity) ;
		aCtx.manager.persist(mp2) ;
		//var sql="insert into medCase_medPolicy set medCase_id='"+aEntity.id+"',policies_id='"+aForm.attachedPolicyDmc+"'" ;
		//aCtx.manager.createNativeQuery(sql).executeUpdate() ;
	}
	if (aForm.pregnancyOrderNumber!=null && aForm.pregnancyOrderNumber>0) {
		var list = aCtx.manager.createQuery("from Pregnancy where patient=:pat and orderNumber=:number")
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
			aCtx.manager.persist(preg) ;
			//aEntity.setPregnancy(preg) ;

			
		}
		aEntity.setPregnancy(preg) ;
	}
}

function onPreDelete(aMedCaseId, aContext) {
	throw "Нельзя удалять поступление, удалится весь случай !!!"
}

function onSave(aForm,aEntity,aCtx) {
	//aEntity.setEditTime(new java.sql.Time ((new java.util.Date()).getTime())) ;
	
	var sql = "update MedCase set dateStart=:dateStart,entranceTime=:entranceTime,department_id=:dep, lpu_id=:lpu where parent_id=:idSLS and DTYPE='DepartmentMedCase' and prevMedCase_id is null"
	
	aCtx.manager.createNativeQuery(sql)
		.setParameter("dateStart",aEntity.dateStart)
		.setParameter("entranceTime",aEntity.entranceTime)
		.setParameter("dep",aForm.department)
		.setParameter("lpu",aForm.lpu)
		.setParameter("idSLS",aForm.id)
		.executeUpdate() ;
	var aStatCardNumber = aForm.statCardNumber ;
	
}

function onPreSave(aForm,aEntity, aCtx) {
	var pat = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,aForm.getPatient());
	if (pat.getDeathDate()!=null) {
		
		var deathDate = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(pat.getDeathDate(),"yyyy-MM-dd");
		if (date.getTime() > deathDate.getTime()) {
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
			if (+aForm.orderLpu>0) {} else {throw "При плановой госпитализации необходимо заполнять поле Кем направлен!!!" ;}
			if (+aForm.sourceHospType>0) {} else {throw "При плановой госпитализации необходимо заполнять поле Тип направившего ЛПУ!!!" ;}
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
			;
		//throw sql ;
		var list = aCtx.manager.createNativeQuery(sql)
	//.setParameter("number", aStatCardNumber).setParameter("year",java.lang.Long.valueOf(year))
				.getResultList() ;
		if (list.size()>0) {
			var obj = list.get(0) ;
			throw "Уже оформлена госпитализация за "+aForm.dateStart+" <a href='entitySubclassView-mis_medCase.do?id="+obj[0]+"'>№стат.карты "+obj[1]+"</a>" ;
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
			var dateStart = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateStart,aForm.entranceTime);
			var dateFinish = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateFinish,aForm.dischargeTime);
			if (!(dateFinish.getTime() > dateStart.getTime())) throw "Дата выписки "+
			aForm.dateFinish+" "+aForm.dischargeTime+" должна быть больше, чем дата поступления "+aForm.dateStart+" "
			+aForm.entranceTime;
			
		}
	
	//if (ret==true) {
	//	throw "Номер стат.карты "+aStatCardNumber + " уже существует в "+year+" году!!!";
	//}

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
           }/*else if (aForm.getBedType()==null || aForm.getBedType()==0) {
               throw "При госпитализации пациента нужно указывать отделение и профиль коек!";
           }*/
    }
    if (aEntity!=null && aEntity.getDeniedHospitalizating()!=null
    		&&+aForm.getDeniedHospitalizating()==0) {
    	var statCardNumber = aForm.statCardNumber ;
    	if (aEntity.statisticStub!=null) {
	    	if (statCardNumber==null || statCardNumber=="") {
	    		var hand = aCtx.getSessionContext().isCallerInRole(Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.StatisticStubStac.CreateStatCardBeforeDeniedByHand) ;
	    		
	    		if (hand) throw "Не указан номер стат. карты" ;
	    	} else{
	    		var year = aForm.getDateStart().substring(6) ;
	    		//throw ""+year ;
	    		var list = aCtx.getManager()
	    				.createNativeQuery("select id from StatisticStub where medCase_id='"+aForm.getId()+"' and DTYPE='StatisticStubExist' and code=:number and year=:year ")
	    			.setParameter("number", statCardNumber)
	    			.setParameter("year",java.lang.Long.valueOf(year))
	    			.getResultList() ;
	    		
	    		if (list.size()>0) throw "Номер стат. карты "+statCardNumber+" в "+year+" уже зарегистрирован!!!" ;
	    	}
    	}
    }
    
       
}