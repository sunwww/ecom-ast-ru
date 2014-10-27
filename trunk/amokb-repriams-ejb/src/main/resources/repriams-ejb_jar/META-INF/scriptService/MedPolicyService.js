function findColdMedPolcyOmc() {
}

function checkSeriesOmc(aSeries,aSmo) {
	return 1;
}

function checkNumberOmc(aSeries,aSmo) {
	var snilsReg = java.util.regex.Pattern.compile("[0-9]{7}") ;
    var m = snilsReg.matcher(str);
    if (!m.find()) return "Не верный формат номера" ;
}

function createDmcPolicy(aContext,aDmcExistId,aPatId) {
	var dmcExist = aContext.manager.find(Packages.ru.ecom.expomc.ejb.domain.externalbase.DmcExistMedicalPolicy
		, java.lang.Long.valueOf(aDmcExistId)) ;
	var patient = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient
		, java.lang.Long.valueOf(aPatId)) ;
	if (patient!=null && dmcExist!=null) {
		var reg_ic = aContext.manager.find(Packages.ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany
			, java.lang.Long.valueOf(dmcExist.insuranceCode)) ;
		var medpol = new Packages.ru.ecom.mis.ejb.domain.patient.MedPolicyDmc() ;
		medpol.lastname=dmcExist.lastname ;
		medpol.firstname=dmcExist.firstname ;
		medpol.middlename = dmcExist.middlename ;
		medpol.polNumber=dmcExist.number ;
		medpol.actualDateTo=dmcExist.endActualDate ;
		medpol.actualDateFrom=dmcExist.startActualDate ;
		medpol.company=reg_ic ;
		medpol.patient=patient ;
		
	}
	aContext.manager.persist(medpol) ;
}