function onView(aForm, aMedCase, aCtx) {
}

function onPreCreate(aForm, aCtx) {
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
	if (aForm.attachedPolicies!="" && aForm.attachedPolicies>0) {
		var medPolicyOmc = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.MedPolicy,aForm.attachedPolicies) ;
		var mp1 = new Packages.ru.ecom.mis.ejb.domain.medcase.MedCaseMedPolicy() ;
		mp1.setPolicies(medPolicyOmc) ;
		mp1.setMedCase(aEntity) ;
		aCtx.manager.persist(mp1) ;
		//var sql="insert into medCase_medPolicy set medCase_id='"+aEntity.id+"',policies_id='"+aForm.attachedPolicies+"'" ;
		//aCtx.manager.createNativeQuery(sql).executeUpdate() ;
	}	if (aForm.attachedPolicyDmc!="" && aForm.attachedPolicyDmc>0) {
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
			
		}
		aEntity.setPregnancy(preg) ;
	}
}

function onPreDelete(aMedCaseId, aContext) {
	throw "Нельзя удалять поступление, удалится весь случай !!!"
}

function onSave(aForm,aEntity,aCtx) {
	var sql = "update MedCase set dateStart=:dateStart,entranceTime=:entranceTime,department_id=:dep, lpu_id=:lpu where parent_id=:idSLS and DTYPE='DepartmentMedCase' and prevMedCase_id is null"
	
	aCtx.manager.createNativeQuery(sql)
		.setParameter("dateStart",aEntity.dateStart)
		.setParameter("entranceTime",aEntity.entranceTime)
		.setParameter("dep",aForm.department)
		.setParameter("lpu",aForm.lpu)
		.setParameter("idSLS",aForm.id)
		.executeUpdate() ;

}

function onPreSave(aForm,aEntity, aCtx) {
	var aStatCardNumber = aForm.statCardNumber ;
	if (aStatCardNumber!=null && aStatCardNumber!="" && (+aForm.id>0)) {
		var year = aForm.dateStart.substring(6) ;
		//throw ""+year ;
		var list = aCtx.manager.createQuery("from StatisticStub where code=:number and year=:year and DTYPE='StatisticStubExist' and medCase_id='"+aForm.id+"'")
			.setParameter("number", aStatCardNumber).setParameter("year",java.lang.Long.valueOf(year)).getResultList() ;
		if (list.size()==0) {
			var alwaysCreate = aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Admission/AlwaysCreateStatCardNumber") ;
			if (!alwaysCreate) {
    			if (aForm.deniedHospitalizating>0) {
    				throw new IllegalArgumentException("Нельзя изменить номер стат.карты при отказе госпитализации");
    			}
    		}
			Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.StatisticStubStac.changeStatCardNumber(aForm.id, aStatCardNumber, "/Policy/Mis/MedCase/Stac/Ssl/Admission/ChangeStatCardNumber", aCtx.manager, aCtx.getSessionContext());
		} else {
			ret = false ;
		}
		
	}
	//if (ret==true) {
	//	throw "Номер стат.карты "+aStatCardNumber + " уже существует в "+year+" году!!!";
	//}

	var stat=aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Admission/CreateHour") ;
	var psych=aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/IsPsychiatry") ;
	var dateStart = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateStart,aForm.entranceTime);
	
	if (stat) {
		var check = Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.SecPolicy.isDateLessThen24Hour(dateStart);
		if (check) throw "У Вас стоит ограничение на дату поступления. Дата поступления меньше на 24 часа, чем текущая дата" ;
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
			if (aForm.getDepartment()!=null && aForm.getDepartment()!=0)
			 throw "При отказе от госпитализации и/или амбулаторном лечении поле отделение не заполняется!";
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
       
}