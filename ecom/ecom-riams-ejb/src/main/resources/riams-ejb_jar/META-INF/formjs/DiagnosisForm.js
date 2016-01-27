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
	//
}


function checkPolyclinic(aForm,aEntity,aCtx) {
	var medCase =aEntity.getMedCase() ; 
	if (medCase!=null) {
		if (medCase.getClass().getSimpleName().equals('Visit') && medCase.emergency!=null && medCase.emergency) {
			try {
			//throw ''+medCase.getParent().getId() ;
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
	return ""+aCtx.manager.createNativeQuery("select dtype from medcase where id="+aId).getResultList().get(0);
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
