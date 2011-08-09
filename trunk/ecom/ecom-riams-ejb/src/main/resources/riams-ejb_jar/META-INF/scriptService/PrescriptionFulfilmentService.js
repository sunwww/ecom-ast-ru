function isPrescriptionFulfilmentHospitalMedCase (aCtx, aPrescriptionFulfilmentId) {
 
 	var prescription = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.Prescription,
		new java.lang.Long(aPrescriptionFulfilmentId));
	
 	var medCase = prescription!=null
 		&& prescription.getPrescriptionList()!=null
 		&& prescription.getPrescriptionList().getMedCase() !=null
 	 	? prescription.getPrescriptionList().getMedCase(): null;
 	 	
 	return medCase!=null && (medCase.getClass().getSimpleName().equals( "HospitalMedCase" )
 		|| medCase.getClass().getSimpleName().equals("DepartmentMedCase") );
}