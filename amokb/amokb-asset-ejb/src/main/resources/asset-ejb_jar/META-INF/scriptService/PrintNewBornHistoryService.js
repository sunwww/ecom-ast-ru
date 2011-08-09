var map = new java.util.HashMap() ;

function printNewBornHistory(aCtx, aParams) {
	var newBornHistory = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.birth.NewBorn
		, new java.lang.Long(aParams.get("id"))) ;
	var medCase = newBornHistory.medCase ;
	
	recordPatient(newBornHistory);

	return map ;
	
}
//данные матери
function recordPatient(newBornHistory) {
	//Фамилия имя отчество
	map.put("pat",newBornHistory.patient) ;
	/*
	map.put("pat.lastname",newBornHistory.patient.lastname) ;
	map.put("pat.firstname",newBornHistory.patient.firstname) ;
	map.put("pat.middlename",newBornHistory.patient.middlename) ;
	//Дата рождения
	map.put("pat.birthday",newBornHistory.patient.birthday);
	//Возраст (полных лет, для детей: до 1 года - месяцев, до 1 месяца - дней)
	getAge("pat.age",newBornHistory.patient.birthday,newBornHistory.dateStart) ;
	
	//Постоянное место жительства: город, село и адрес
	//getAddress("pat.address",medCase.patient.address) ;
	map.put("pat.addressReal",newBornHistory.patient.addressReal) ;
	map.put("pat.addressReg",newBornHistory.patient.addressRegistration) ;
	//Дом, корпус
	map.put("pat.addressHouse",newBornHistory.patient.addressHouse) ;
	// Квартира
	map.put("pat.flatNumber",newBornHistory.patient.flatNumber) ;
	//Место работы, профессия или должность
	map.put("pat.works",newBornHistory.patient.works);
	var workDiv = "" ;
	if (newBornHistory.patient.works!=null && newBornHistory.patient.workPost!=null && !newBornHistory.patient.workPost.equals("")) workDiv="," ;
	map.put("pat.workDiv",workDiv) ;
	map.put("pat.wPost",newBornHistory.patient.workPost) ;
	//Социальный статус
	map.put("pat.socialStatus",newBornHistory.patient.socialStatus) ;
	//Образование
	map.put("pat.educationType",newBornHistory.patient.educationType) ;
	//Национальность
	map.put("pat.ethnicity",newBornHistory.patient.ethnicity) ;
	*/
}



function getAge(aKey,aBirthday,aDate) {
	if (aDate!=null && aBirthday!=null) {
		var calenB = java.util.Calendar.getInstance() ;
		calenB.setTime(aBirthday) ;
		var calenE = java.util.Calendar.getInstance() ;
		calenE.setTime(aDate) ;
		var year = calenE.get(java.util.Calendar.YEAR)-calenB.get(java.util.Calendar.YEAR) ;
		if (year>0) {
			map.put(aKey,""+year) ;
		} else {
			var month = calenE.get(java.util.Calendar.MONTH)-calenB.get(java.util.Calendar.MONTH) ;
			if (month>0) {
				map.put(aKey,""+month) ;
			} else {
				map.put(aKey,""+(calenE.get(java.util.Calendar.DAY_OF_MONTH)-calenB.get(java.util.Calendar.DAY_OF_MONTH)+1)) ;
			}
		}
	} else {
		map.put(aKey,"");
	}
}
function getAddress(aKey, aAddress) {
	if (aAddress!=null) {
		if (aAddress.addressIsCity!=null && aAddress.addressIsCity==true) {
			map.put(aKey+".CityOrVillage","город") ;
		} else {
			if (aAddress.addressIsVillage!=null && aAddress.addressIsVillage==true) {
				map.put(aKey+".CityOrVillage","село") ;
			} else {
				map.put(aKey+".CityOrVillage","город, село (нужное подчеркнуть)") ;
			}
		}
		map.put(aKey+".info",aAddress.addressInfo) ;
	}else{
		map.put(aKey+".CityOrVillage","город, село (нужное подчеркнуть)") ;
		map.put(aKey+".info","") ;
	}
}