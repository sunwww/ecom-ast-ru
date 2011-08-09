function onPreDelete(aEntityId, aContext) {
	var patient = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient, new java.lang.Long(aEntityId)) ;
	if (patient!=null) {
		var err = [
		 "диагнозов"
		,"медицинские карты"
		,"случаи медицинского обслуживания"
		,"ведомственные прикрепления"
		,"награды"
		,"случаи нетрудоспособности"
		,"данные об образовании"
		,"данные о его родственниках"
		,"данные о родственниках"
		,"знания языков"
		,"полисы"
		,"льготы"
		,"сотрудника"
		,"псих.мед.карту"
		] ;
		
		//throw medCase.getId() + "  getStatisticStub()="+medCase.getStatisticStub() ;
		var err_list = aContext.manager.createNativeQuery("select"
		+" (select count(*) from Diagnosis as d where d.patient_id=p.id) as v0"
		+",(select count(*) from Medcard as ms1 where ms1.person_id=p.id) as v1"
		+",(select count(*) from MedCase as so where so.patient_id=p.id) as v2"
		+",(select count(*) from LpuAttachedByDepartment as tr where tr.patient_id=p.id) as v3"
		+",(select count(*) from Award as v where v.person_id=p.id) as v4"
		+",(select count(*) from DisabilityCase as dc where dc.patient_id=p.id) as v5"
		+",(select count(*) from Education as e where e.person_id=p.id) as v6"
		+",(select count(*) from Kinsman as k1 where k1.person_id=p.id) as v7"
		+",(select count(*) from Kinsman as k2 where k2.kinsman_id=p.id) as v8"
		+",(select count(*) from LanguageSkill as d where d.person_id=p.id) as v9"
		+",(select count(*) from MedPolicy as pol where pol.patient_id=p.id) as v10"
		+",(select count(*) from Privilege as bc where bc.person_id=p.id) as v11"
		+",(select count(*) from Worker as dc where dc.person_id=p.id) as v12"
		+",(select count(*) from PsychiatricCareCard as pcc where pcc.patient_id=p.id) as v13"
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
		
	}
	
}
