
/**
 * Список DisabilityCase по пациенту
 */
function findDisabilityCasesByPatient(aContext, aPatientId) {
	var patient = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient
		, new java.lang.Long(aPatientId)) ;
	var list = aContext.manager.createQuery(
		"from DisabilityCase where medCase.patient=:patient")
		.setParameter("patient", patient)
		.getResultList() ;
	var ret = new java.util.ArrayList() ;
	var iterator = list.iterator() ;
	while(iterator.hasNext()) {
		var dis = iterator.next() ;
		var map = new java.util.HashMap() ;
		map.put("id", new java.lang.Long(dis.id)) ;
		map.put("dateFrom", dis.dateFrom) ;
		map.put("dateTo", dis.dateTo) ;
		map.put("duration", dis.duration) ;
		ret.add(map) ; 
	}
	return ret ;	
} 
