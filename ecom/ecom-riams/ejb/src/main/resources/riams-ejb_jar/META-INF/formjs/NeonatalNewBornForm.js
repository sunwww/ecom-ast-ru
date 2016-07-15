function onCreate(aForm, aEntity, aContext){
	// Создание персоны
	var patient = new Packages.ru.ecom.mis.ejb.domain.patient.Patient() ;
	var mother = aEntity.medCase.patient;
	var motherSls = aEntity.medCase.parent;
	var cardPrefix = aEntity.child.isPolycarpous==true?aEntity.child.birthOrder:"";
	if (aEntity.getLiveBorn().getCode()=='1'){ //Создаем пациента только живому ребенку
		patient.lastname=mother.lastname ;
		patient.firstname =  (aEntity.sex!=null?(aEntity.sex.omcCode=="1"?"У":"Х"):"Х") ;
		patient.middlename =  "X";
		patient.birthday = aEntity.birthDate ;
		patient.sex = aEntity.sex ;
		patient.newborn=aEntity.child ;
		patient.newbornWeight =aEntity.birthWeight.longValue() ;
		patient.nationality=mother.nationality ;
		patient.address = mother.address ;
		patient.flatNumber = mother.flatNumber ;
		patient.houseBuilding = mother.houseBuilding ;
		patient.houseNumber = mother.houseNumber ;
		patient.realRayon = mother.realRayon ;
		patient.rayon = mother.rayon ;
		patient.realAddress = mother.realAddress ;
		patient.realFlatNumber = mother.realFlatNumber ;
		patient.realHouseBuilding = mother.realHouseBuilding ;
		patient.realHouseNumber = mother.realHouseNumber ;
		aContext.manager.persist(patient) ;
		patient.setPatientSync("Н"+patient.getId()) ;
		aContext.manager.persist(patient) ;
		var kinsman = new Packages.ru.ecom.mis.ejb.domain.patient.Kinsman() ;
		kinsman.person =  patient ;
		kinsman.kinsman = mother ;
		var role = aContext.manager.createQuery("from VocKinsmanRole where omccode = '1'").getResultList() ;
	     	
		kinsman.kinsmanRole = role.size()>0?role.get(0):null ;
		aContext.manager.persist(kinsman);
		aEntity.patient = patient ;
}
	//Создаем госпитализацию
	if (aForm.department!=null&&+aForm.department>0 && aEntity.getLiveBorn().getCode()=='1') {
		var sls = new Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase() ;
		sls.patient = patient ;
		var serviceStream = aContext.manager.createQuery("from VocServiceStream where omcCode='4'").getResultList() ;
		sls.dateStart = aEntity.birthDate ;
		sls.entranceTime = aEntity.birthTime ;
		sls.serviceStream = serviceStream!=null?serviceStream.get(0):motherSls.serviceStream ;
		sls.emergency = java.lang.Boolean.TRUE ;
		var lDep = aContext.manager.createQuery("from MisLpu where id='"+aForm.department+"'").getResultList() ;
		var dep = lDep.get(0);
		sls.department = dep ;
		sls.lpu = dep.parent ;
		var lOR = aContext.manager.createQuery("from VocHospType where code='ALLTIMEHOSP'").getResultList() ;
		sls.hospType = lOR.size()>0?lOR.get(0):null ;
		
		lOR = aContext.manager.createQuery("from OmcFrm where voc_code='С'").getResultList() ;
		sls.orderType = lOR.size()>0?lOR.get(0):null ;
		
		lOR = aContext.manager.createQuery("from VocPreAdmissionTime where code='1'").getResultList() ;
		sls.preAdmissionTime = lOR.size()>0?lOR.get(0):null ;
		
		lOR = aContext.manager.createQuery("from VocHospitalization where code='1'").getResultList() ;
		sls.hospitalization = lOR.size()>0?lOR.get(0):null ;
		sls.kinsman = kinsman ;
		aContext.manager.persist(sls) ;
		var ss = new Packages.ru.ecom.mis.ejb.domain.medcase.StatisticStubExist() ;
		ss.medCase = sls ;
		ss.year = java.lang.Long.valueOf(aForm.birthDate.substring(6)) ;
		var ssMother = motherSls.statisticStub.code ;
		ss.code = ssMother.substring(1)+"Н"+cardPrefix ;
		aContext.manager.persist(ss) ;
		sls.statisticStub = ss ;
		aContext.manager.persist(sls) ;
}
	var childBirth = aContext.manager.createQuery(" from ChildBirth where medcase_id="+aEntity.medCase.id).getResultList();
	if (childBirth.size()>0) {
		aEntity.childBirth = childBirth.get(0);
	} else {
		//TODO доделать
		var childBirth = new Packages.ru.ecom.mis.ejb.domain.birth.ChildBirth() ;
		childBirth.medCase = aEntity.medCase ;
		childBirth.birthFinishDate = aEntity.birthDate ;
		aContext.manager.persist(childBirth);
		aEntity.childBirth = childBirth ;
	}
}