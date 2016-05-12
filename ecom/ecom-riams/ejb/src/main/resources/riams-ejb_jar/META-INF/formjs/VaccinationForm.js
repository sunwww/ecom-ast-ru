function onCreate(aForm, aVac, aCtx) {
	aVac.patient = aVac.getMedCase().getPatient() ;
}
/**
 * Перед сохранением
 */
function onPreSave(aForm, aContext) {
	
	// Проверка введенных данных
	
}

/** Перед удалением */
function onPreDelete(aId, aContext) {
	var vac = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.vaccination.Vaccination, new java.lang.Long(aId)) ;
	if (vac!=null) {
		var err = [
		 "оценки"
		,"медотводы"
		] ;
		//throw medCase.getId() + "  getStatisticStub()="+medCase.getStatisticStub() ;
		var err_list = aContext.manager.createNativeQuery("select"
		+" (select count(*) from VaccinationAssesment as va where va.vaccination_id=v.id)"
		+",(select count(*) from VaccinationEstop as ve where ve.vaccination_id=v.id)"
		+" from Vaccination as v where v.id=:id")
		.setParameter("id",aId).getSingleResult() ;
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
