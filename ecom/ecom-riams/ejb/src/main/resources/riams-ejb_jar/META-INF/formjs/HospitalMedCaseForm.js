function onView(aForm, aVisit, aCtx) {
}

function onPreCreate(aForm, aCtx) {
}

function onPreDelete(aMedCaseId, aContext) {
	var medCase = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase, new java.lang.Long(aMedCaseId)) ;
	if (medCase!=null ) {
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
		,"родов"
		,"беременности, прикрепленных к случаю"
		,"данные обменной карты"
		,"карты COVID-19"
		,"оценки степени тяжести"
		,"чек-листы/карты оценки риска"
		] ;
		var err_list = aContext.manager.createNativeQuery("select"
		+" (select count(*) from Diagnosis as d where d.medCase_id=ms.id) as v0"
		+",(select count(*) from MedCase as ms1 where ms1.parent_id=ms.id) as v1"
		+",(select count(*) from SurgicalOperation as so where so.medCase_id=ms.id) as v12"
		+",(select count(*) from Transfusion as tr where tr.medCase_id=ms.id) as v3"
		+",(select count(*) from Vaccination as v where v.medCase_id=ms.id) as v4"
		+",(select count(*) from PhoneMessage as pm where ms.id=pm.id) as v5"
		+",(select count(*) from TemperatureCurve as tc where tc.medCase_id=ms.id) as v6"
		+",(select count(*) from PrescriptionList as pl where pl.medCase_id=ms.id) as v7"
		+",(select count(*) from Diary as d where d.medCase_id=ms.id) as v9"
		+",(select count(*) from MedCase_MedPolicy as pol where pol.medCase_id=ms.id) as v10"
		+",(select count(*) from DeathCase as dc where dc.medCase_id=ms.id) as v12"
		+",(select count(*) from ChildBirth as cb where cb.medCase_id=ms.id) as v13"
		+",(select count(*) from PregnancyHistory as ph where ph.medCase_id=ms.id) as v14"
		+",(select count(*) from PregnanExchangeCard as pec where pec.medCase_id=ms.id) as v15"
		+",(select count(*) from Covid19 as c where c.medCase_id=ms.id) as v16"
		+",(select count(*) from CovidMark as cm where cm.medCase_id=ms.id) as v17"
		+",(select count(*) from assessmentcard as ac where ac.medCase_id=ms.id) as v18"
		+" from MedCase as ms where ms.DTYPE='HospitalMedCase' and ms.id=:id")
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
		var stat = medCase.statisticStub ;
		if (stat!=null) {
			var restored = new Packages.ru.ecom.mis.ejb.domain.medcase.StatisticStubRestored() ;
			restored.setCode(stat.getCode());
			restored.setYear(stat.getYear());
			aContext.manager.persist(restored);
			//aContext.manager.refresh(restored);
			medCase.statisticStub = null ;
			aContext.manager.remove(stat) ;
		}
	}
	
}
function onDelete(aEntityId, aContext) {
}

function onSave(aForm, aVisit, aCtx) {
}