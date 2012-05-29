function onCreate(aForm, aEntity, aContext) {
	var pat = aForm.getPatient() ;
	if (pat==null) {
		var sql = "select m.patient_id,m.number from medcard m where m.number='"+aForm.cardNumber+"'" ;
		var list = theManager.createNativeQuery(sql).setMaxResults(1).getResultList() ;
		if (list.size()>0) {
			if (+aForm.patient==list.get(0)[0]) {
				
			} else {
				throw "Номер мед.карты уже занят!!!" ;
			}
		} else {
			var medcard = new Packages.ru.ecom.poly.ejb.domain.Medcard() ;
			medcard.setPatient(aEntity.getPatient()) ;
			medcard.setNumber(aEntity.getCardNumber()) ;
			aContext.manager.persist(medcard) ;
		}
	}
	
	
}
function onSave(aForm, aEntity, aContext) {
	if (aForm.deathDate!=null && aForm.deathDate!='') {
		aEntity.patient.setDeathDate(aEntity.deathDate) ;
		aContext.manager.persist(aEntity.patient) ;
	}
}