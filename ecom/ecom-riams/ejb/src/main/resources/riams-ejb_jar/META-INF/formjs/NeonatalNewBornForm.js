function onCreate(aForm, aEntity, aContext){
	// Создание персоны
	var patient = new Packages.ru.ecom.mis.ejb.domain.patient.Patient() ;
	patient.lastname=aForm.lastname ;
	patient.firstname = aEntity.child?aEntity.child.code.toUpperCase():"Х" ;
	patient.middlename =  aForm.middlename;
	patient.birthday = aEntity.birthDate ;
	patient.sex = aEntity.sex ;
	aContext.manager.persist(patient) ;
	var kinsman = new Packages.ru.ecom.mis.ejb.domain.patient.Kinsman() ;
	kinsman.person =  patient ;
	kinsman.kinsman = aEntity.medCase.patient ;
	var role = aContext.manager.createQuery("from VocKinsmanRole where omccode = '1'"
       	)
       	.getResultList() ;
       	
	kinsman.kinsmanRole = role.size()>0?role.get(0):null ;
	aContext.manager.persist(kinsman);
	aEntity.patient = patient ;
	if (aForm.childBirth>0) {
	} else {
		//TODO доделать
		var childBirth = new Packages.ru.ecom.mis.ejb.domain.birth.ChildBirth() ;
		childBirth.medCase = aEntity.medCase ;
		childBirth.birthFinishDate = aEntity.birthDate ;
		aContext.manager.persist(childBirth);
		aEntity.childBirth = childBirth ;
	}
}