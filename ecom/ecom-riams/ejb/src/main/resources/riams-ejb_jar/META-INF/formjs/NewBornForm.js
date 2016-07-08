function onCreate(aForm, aEntity, aContext){
	// Создание персоны
	var patient = new Packages.ru.ecom.mis.ejb.domain.patient.Patient() ;
	var mother = aEntity.childBirth.medCase.patient;
	
	patient.lastname=mother.lastname ;
	patient.firstname =  (aEntity.sex!=null?(aEntity.sex.omcCode=="1"?"У":"Х"):"Х") ;
	patient.middlename =  "X";
	patient.birthday = aEntity.birthDate ;
	patient.sex = aEntity.sex ;
	aContext.manager.persist(patient) ;
	var kinsman = new Packages.ru.ecom.mis.ejb.domain.patient.Kinsman() ;
	kinsman.person =  patient ;
	kinsman.kinsman = mother;
	var role = aContext.manager.createQuery("from VocKinsmanRole where omccode = '1'"
       	)
       	.getResultList() ;
       	
	kinsman.kinsmanRole = role.size()>0?role.get(0):null ;
	aContext.manager.persist(kinsman);
	aEntity.patient = patient ;
}
function onSave(aForm, aEntity, aContext) {
	aEntity.patient.lastname = aForm.lastname ;
	aEntity.patient.firstname = aForm.firstname ;
	aEntity.patient.middlename = aForm.middlename ;
	aEntity.patient.birthday = aEntity.birthDate ;
	aEntity.patient.sex = aEntity.sex ;
}
function onPreDelete(aEntityId, aCtx) {
	var entity = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.birth.NewBorn, new java.lang.Long(aEntityId)) ;
	var pat = entity.patient ;
	entity.patient = null ;
	
	//if (pat!=null) aCtx.manager.remove(pat) ;
}