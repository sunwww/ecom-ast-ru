/**
 * При создании
 */
function onCreate(aForm, aEntity, aContext) {

	var illnesPrimary=aEntity.illnesPrimary  ;
	if (illnesPrimary!=null) {
		aEntity.setAcuity(illnesPrimary.getIllnesType()) ;
		aEntity.setPrimary(illnesPrimary.getPrimary()) ;
		
	}
	if (aEntity.getPatient()==null) {
		var pat = aEntity.getMedCase()!=null?aEntity.getMedCase().getPatient():null ;
		aEntity.setPatient(pat) ;
	}
	
	aContext.manager.persist(aEntity) ;
	checkPolyclinic(aForm,aEntity,aContext);
    //checkUniqueDiagnosis(aForm,aEntity,aContext);
}

//Проставить в карте коронавируса основной выписной диагноз
function setCovidDateResultHospAndMkb(aForm, aCtx) {
	if (aForm.getPriority() && aForm.getRegistrationType()) {
		var priority = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis,
			new java.lang.Long(aForm.getPriority()));
		var regtype = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType,
			new java.lang.Long(aForm.getRegistrationType()));

		if (priority && regtype && priority.getCode() == '1' && regtype.getCode() == '3')
			aCtx.manager.createNativeQuery("update Covid19 set mkbDischarge_id = " + aForm.getIdc10() +
				" where medcase_id = " + aForm.getMedCase() + " and id = (select max(id) from Covid19 where medcase_id = " + aForm.getMedCase() + ")").executeUpdate();
	}
}

/**
 * При сохранении
 */
function onSave(aForm, aEntity, aContext) {
	var illnesPrimary=aEntity.illnesPrimary  ;
	if (illnesPrimary!=null) {
		aEntity.setAcuity(illnesPrimary.getIllnesType()) ;
		aEntity.setPrimary(illnesPrimary.getPrimary()) ;
	}
	if (aEntity.getPatient()==null) {
		var pat = aEntity.getMedCase()!=null?aEntity.getMedCase().getPatient():null ;
		aEntity.setPatient(pat) ;
	}
	aContext.manager.persist(aEntity) ;
	checkPolyclinic(aForm,aEntity,aContext);
	//обновить в ковидной карте, если есть
	setCovidDateResultHospAndMkb(aForm,aContext);
}
//диагноз должен быть уникальным
function checkUniqueDiagnosis(aForm,aEntity,aCtx) {
	if (aForm!=null) {
        var medcase_id = aForm.getMedCase();
        if (medcase_id != null) {
            var sql = "select dtype from medcase where id=" + medcase_id;
            var list = aCtx.manager.createNativeQuery(sql).getResultList();
            var dtype = "";
            if (list.size() > 0) {
                dtype = list.get(0) + "";
            }
            var priority = aForm.getPriority();
            var regtype = aForm.getRegistrationType();
            var diagnosis = aForm.getIdc10();
            if (dtype == "HospitalMedCase") {
                var sql = "select lpu.name from medcase mcs" +
                    " left join mislpu lpu on mcs.department_id = lpu.id" +
                    " left join diagnosis ds on ds.medcase_id = " + medcase_id +
                    " where ds.priority_id = '" + priority + "')" +
                    " and ds.idc10_id = '" + diagnosis +
                    "' and ds.registrationtype_id = '" + regtype + "')" +
                    " and mcs.id = " + medcase_id;
                var list = aCtx.manager.createNativeQuery(sql).getResultList();
                if (list.size() > 0) {
                    throw "Такой же диагноз (тип регистрации, приоритет, код МКБ) уже есть в отделении " + list.get(0);
                }
            }
            else if (dtype == "DepartmentMedCase") {
                var sql = "select coalesce(ml.name, 'Приемное отделение') from diagnosis d" +
                    " left join medcase mc on mc.id=d.medcase_id" +
                    " left join mislpu ml on ml.id=mc.department_id" +
                    " where d.priority_id = (select id from vocprioritydiagnosis vds where vds.code = '" + priority + "')" +
                    " and d.idc10_id = '" + diagnosis +
                    "' and d.registrationtype_id=(select id from vocdiagnosisregistrationtype rt where rt.code = '" + regtype + "')" +
                    "  and d.medcase_id in (select id from medcase where id in (select id from medcase where id = " + medcase_id +
					"or (parent_id = " + medcase_id + " and dtype='DepartmentMedCase')))";
                var list = aCtx.manager.createNativeQuery(sql).getResultList();
                if (list.size() > 0) {
                    throw "Такой же диагноз (тип регистрации, приоритет, код МКБ) уже есть в отделении " + list.get(0);
                }
            }
        }
    }
}

function checkPolyclinic(aForm,aEntity,aCtx) {
	var medCase =aEntity.getMedCase() ; 
	if (medCase!=null) {
		if (medCase.getClass().getSimpleName().equals('Visit') && medCase.emergency!=null && medCase.emergency==true) {
			try {
			throw ''+medCase.getParent().getId() ;
			aCtx.serviceInvoke("SmoVisitService", "closeSpoWithoutDiagnosis",medCase.parent.getId(),aForm.idc10) ;
			} catch(e) {}
		}
	}
}
function onPreSave(aForm,aEntity, aCtx) {
	var date = new java.util.Date();
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	//aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	if (!isDiagnosisAllowed(aForm, aCtx)) {
		throw "Данный диагноз запрещен в отделении!";
	}
}

function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date();
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	//aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	
	if (!isDiagnosisAllowed(aForm, aCtx)) {
		throw "Данный диагноз запрещен в отделении!";
	}
}

function getMedCaseType (aId, aCtx) {
	var list = aCtx.manager.createNativeQuery("select dtype from medcase where id="+aId).getResultList() ;
	return list.size()>0?""+list.get(0):"";
}

function isDiagnosisAllowed(aForm, aCtx) {
	
	var dtype = getMedCaseType(aForm.getMedCase(),aCtx);
	var medcase;
	var department;
	if (dtype=='DepartmentMedCase') {
		medcase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase, 
				new java.lang.Long(aForm.getMedCase())) ;
		department = medcase.getDepartment().getId();
	} else if (dtype=='Visit'){ 
		medcase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit, 
				new java.lang.Long(aForm.getMedCase())) ;
		department = medcase.workFunctionExecute.worker.lpu.getId();		
	} else if (dtype=='HospitalMedCase') {
		try {
		medcase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase, 
				new java.lang.Long(aForm.getMedCase())) ;
		
		department = medcase.getDepartment().getId();
		} catch (e) {
			return true;
		}
	} else {
		return true;
	}
	
	if (medcase==null) {
		return true;
	}
	
	var clinicalMkb = aForm.getIdc10();
	//var department = medcase.workFunctionExecute.worker.lpu.getId();
	var patient = aForm.getPatient();
	var serviceStream = medcase.getServiceStream().getId();
	var diagnosisRegistrationType = aForm.getRegistrationType();
	var diagnosisPriority = aForm.getPriority();
	return Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DepartmentSaveInterceptor.isDiagnosisAllowed(clinicalMkb
			,department, patient, serviceStream, diagnosisRegistrationType,diagnosisPriority, aCtx.manager );
	
}
function onPreDelete(aEntityId, aCtx) {
	var diagnosis = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Diagnosis,new java.lang.Long(aEntityId));
    var dtype = getMedCaseType(diagnosis.getMedCase().getId(),aCtx);
    if (!aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/EditAfterOut")
		&& (dtype=='DepartmentMedCase' || dtype=='HospitalMedCase')) {
        var parent=(dtype=='DepartmentMedCase')? diagnosis.getMedCase().getParent() : diagnosis.getMedCase() ;
        if (parent.getDateFinish()!=null) throw "Пациент выписан. Нельзя удалять диагноз в закрытом СЛС!";
	}
}