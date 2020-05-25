function onPreDelete(aEntityId, aContext) {
	var patient = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient, new java.lang.Long(aEntityId)) ;
	if (patient!=null) {
		var err = [
		 "диагнозов"
		,"медицинские карты"
		,"случаи медицинского обслуживания"
		,"ведомственные прикрепления"
		,"случаи нетрудоспособности"
		,"данные об образовании"
		,"данные о его родственниках"
		,"данные о родственниках"
		,"знания языков"
		,"полисы"
		,"льготы"
		,"сотрудника"
		,"псих.мед.карту"
		,"данные о беременностях"
		,"карты диспансеризации"
		,"экспертные карты"
		,"план диспансеризации"
		,"карты COVID-19"
		] ;
		
		var err_list = aContext.manager.createNativeQuery("select"
		+" (select count(*) from Diagnosis as d where d.patient_id=p.id) as v0"
		+",(select count(*) from Medcard as ms1 where ms1.person_id=p.id) as v1"
		+",(select count(*) from MedCase as so where so.patient_id=p.id) as v2"
		+",(select count(*) from LpuAttachedByDepartment as tr where tr.patient_id=p.id) as v3"
		+",(select count(*) from DisabilityCase as dc where dc.patient_id=p.id) as v5"
		+",(select count(*) from Education as e where e.person_id=p.id) as v6"
		+",(select count(*) from Kinsman as k1 where k1.person_id=p.id) as v7"
		+",(select count(*) from Kinsman as k2 where k2.kinsman_id=p.id) as v8"
		+",(select count(*) from LanguageSkill as d where d.person_id=p.id) as v9"
		+",(select count(*) from MedPolicy as pol where pol.patient_id=p.id) as v10"
		+",(select count(*) from Privilege as bc where bc.person_id=p.id) as v11"
		+",(select count(*) from Worker as dc where dc.person_id=p.id) as v12"
		+",(select count(*) from PsychiatricCareCard as pcc where pcc.patient_id=p.id) as v13"
		+",(select count(*) from Pregnancy as pr where pr.patient_id=p.id) as v14"
		+",(select count(*) from ExtDispCard as edc where edc.patient_id=p.id) as v15"
		+",(select count(*) from QualityEstimationCard qec where qec.patient_id=p.id) as v16"
		+",(select count(*) from extdispplanpopulationrecord qec where qec.patient_id=p.id) as v17"
		+",(select count(*) from covid19  qec where qec.patient_id=p.id) as v18"
		+" from Patient as p where p.id=:id")
		.setParameter("id",aEntityId).getSingleResult() ;
		var err_mes="",isErr=false ;
		
		//throw err.toString()+err_list.toString() ;
		for (i=0;i<err.length;i++) {
			var cnt = err_list[i] ;
			if ((+cnt)>0) {
				isErr=true ;
				err_mes = err_mes + "; "+err[i] ;
			}
			
		}
		
		if(isErr) throw "Перед удалением необходимо удалить сведения: " + err_mes.substring(2) ;
		aContext.manager.createNativeQuery("update patientattachedimport set patient_id=null where patient_id="+aEntityId).executeUpdate() ;
		aContext.manager.createNativeQuery("update patientfond set patient=null where patient="+aEntityId).executeUpdate() ;
		aContext.manager.createNativeQuery("update journalpatientfondcheck set removedPatientId='"+aEntityId+"',patient_id=null where patient_id="+aEntityId).executeUpdate() ;
		aContext.manager.createNativeQuery("update JournalChangePatient set patient_id=null where patient_id="+aEntityId).executeUpdate() ;
		
	}
	
}

/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aContext) {
	updateAddress(aForm) ;
	changeData(aForm,aEntity,aContext) ;
	
}
function changeData(aForm,aEntity,aCtx) {
	var isChange = false ;
	if (!aForm.lastname.equals(aEntity.lastname)) {
		isChange=true ;
	} 
	if (!aForm.firstname.equals(aEntity.firstname)) {
		isChange=true ;
	} 
	if (!aForm.middlename.equals(aEntity.middlename)) {
		isChange=true ;
	} 
	var birthday = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aForm.birthday) ;
	if (aEntity.birthday!=null && birthday.getTime() != aEntity.birthday.getTime()) {
		isChange=true ;
	} 
	if (aEntity.address!=null && (+aForm.address)>0 && (+aEntity.address)!=(+aForm.address)) {
		isChange=true;
	}
	if (aEntity.realAddress!=null && (+aForm.realAddress)>0 && (+aEntity.realAddress)!=(+aForm.realAddress)) {
		isChange=true;
	}
	if (isChange) {
		var jcp = Packages.ru.ecom.mis.ejb.domain.patient.JournalChangePatient() ;
		jcp.lastname = aEntity.lastname ;
		jcp.firstname = aEntity.firstname ;
		jcp.middlename = aEntity.middlename ;
		jcp.birthday = birthday ;
		jcp.patient = aEntity ;
		jcp.address = aEntity.address ;
		jcp.realAddress = aEntity.realAddress ;
		var date = new java.util.Date() ;
		jcp.changeDate = new java.sql.Date(date.getTime()) ;
		jcp.changeTime = new java.sql.Time (date.getTime()) ;
		jcp.changeUsername = aCtx.getSessionContext().getCallerPrincipal().toString() ;
		aCtx.manager.persist(jcp) ;
	}
}
/**
 * Перед созданием
 */
function onPreCreate(aForm, aContext) {
	updateAddress(aForm) ;
}
function updateAddress(aForm) {
	if ((+aForm.realAddress>0) && (+aForm.address<1)) {
		aForm.address=aForm.realAddress ;
		aForm.flatNumber=aForm.realFlatNumber ;
		aForm.houseBuilding=aForm.realHouseBuilding ;
		aForm.houseNumber=aForm.realHouseNumber ;
	} else if ((+aForm.address>0) && (+aForm.realAddress<1)) {
		aForm.realAddress=aForm.address ;
		aForm.realFlatNumber=aForm.flatNumber ;
		aForm.realHouseBuilding=aForm.houseBuilding ;
		aForm.realHouseNumber=aForm.houseNumber ;
	}
	if (+aForm.rayon>0 && +aForm.realRayon<1) {
		aForm.realRayon=aForm.rayon ;
	} else if (+aForm.realRayon>0 && +aForm.rayon<1) {
		aForm.rayon=aForm.realRayon ;
	}
}

function onCreate(aForm, aEntity, aCtx) {
    if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/Contract/ContractPerson/NaturalPerson/Create")) {
    	//Создаем юридическое лицо при создании пациента
		aCtx.manager.persist(new Packages.ru.ecom.mis.ejb.domain.contract.NaturalPerson(aEntity));
	}

}
