var map = new java.util.HashMap() ;	

function printDeathStac(aCtx, aParams) {
	
	var certificate = aCtx.manager.find(Packages.ru.ecom.document.ejb.domain.certificate.DeathCertificate
		, new java.lang.Long(aParams.get("id"))) ;
	var medCase =  certificate.deathCase.medCase;
	var deathCase =  certificate.deathCase;
	var deathReason = certificate.deathCase.deathReasons;
		
	map.put("doc.ser", certificate.seriesPreCertificate) ;
	map.put("doc.num", certificate.numberPreCertificate) ;
	map.put("doc.dateIssue", certificate.dateIssue) ;
	recordVocName("sls.lpu", medCase.lpu) ;
	
	//1. ФИО умершего
	map.put("pat.firstname",medCase.patient.firstname) ;
	map.put("pat.middlename",medCase.patient.middlename) ;
	map.put("pat.lastname",medCase.patient.lastname) ;
	
	//2. Пол
	record("pat.sex",medCase.patient.sex,1,2,"__");
	recordDate("cer.date",certificate.dateIssue);
	recordDate("pat.dateDeath",deathCase.deathDate);
	recordDate("pat.birthday",medCase.patient.birthday);
	recordDate("pat.accDate",deathCase.accidentDate);
	recordAge("pat.age",null,null,1,1,1);
	
	recordDate("pat.birthday",medCase.patient.birthday);
	
	map.put("pat.birthWeight","");
	map.put("pat.babyNum","");
	record("pat.prem",null,1,2,"__");
	
	//для детей до года
	if (isSmall(medCase.patient.birthday,deathCase.deathDate,1,0,0)==1) {
		recordAge("pat.age",medCase.patient.birthday,deathCase.deathDate,0,1,1);
		recordDate("baby.birthday",medCase.patient.birthday);
		
		
		// ФИО матери
		map.put("baby.mother",deathCase.mother.lastname+" "+deathCase.mother.firstname+" "+deathCase.mother.middlename);
		//10. Национальность матери
		recordVocName("pat.ethnicity",deathCase.mother.ethnicity);
		//11. Семейное положение матери
		record("pat.mar",deathCase.mother.marriageStatus,1,5,"__");
		//12. Образование матери
		record("pat.ed",deathCase.mother.educationType,1,7,"__");
		//13. где и кем работала мать
		map.put("pat.works",deathCase.mother.works!=null?deathCase.mother.works:"");
		map.put("pat.wPost",deathCase.mother.workPost) ;
		//5.место рождения
		map.put("pat.birthPl",deathCase.birthPlace);
		map.put("pat.birthPlAdr",deathCase.birthPlaceAdress);
		//для детей от 6 дней до 1 месяца
		if (isSmall(medCase.patient.birthday,deathCase.deathDate,0,1,6)==1) {
			//5. Для детей, умерших от 6 дней до 1 месяца
				//Доношенность
			record("pat.prem",deathCase.isPrematurity,1,2,"__");
		}
		//для детей от 6 дней до 1 года
		if (isSmall(medCase.patient.birthday,deathCase.deathDate,1,0,6)==1) {
			//6. для детей, умерших от 6 дней до 1 года
					//Вес при рождении
				map.put("pat.birthWeight",deathCase.birthWeight) ;
					//ребенок по счету
				map.put("pat.babyNum",deathCase.babyNumber) ;
		} else {
			
		}
	} else {
	// для всех, кто старше 1 года
		recordAge("pat.age",medCase.patient.birthday,deathCase.deathDate,1,0,0);
		recordDate("baby.birthday",null);
		//10. Национальность умершего
		recordVocName("pat.ethnicity",medCase.patient.ethnicity);
		//11. Семейное положение умершего
		record("pat.mar",medCase.patient.marriageStatus,1,5,"__");
		//12. Образование умершего
		record("pat.ed",medCase.patient.educationType,1,7,"__");
		//13. где и кем работал умерший
		recordVocName("pat.works",medCase.patient.works!=null?medCase.patient.works:"");
		map.put("pat.wPost",medCase.patient.workPost) ;
		map.put("baby.mother","");
	}
	
	
	
	//7. Место жительства
	if (medCase.patient.address!=null) {
		//Город, село
		recordBoolean("p.c",medCase.patient.address.addressIsCity);
		recordBoolean("p.v",medCase.patient.address.addressIsVillage);
		
		//Дом, корпус
		map.put("pat.addressHouse",medCase.patient.addressHouse) ;
		// Квартира
		map.put("pat.flatNumber",medCase.patient.flatNumber) ;
		// Улица
		map.put("pat.street",medCase.patient.address.addressStreet) ;
		// Республика, область (край)
		map.put("pat.region",medCase.patient.address.addressRegion) ;
		// Район
		map.put("pat.area",medCase.patient.address.addressArea) ;
		// Название города (села)
		map.put("pat.city",medCase.patient.address.addressCity) ;
		
	} else  {
		map.put("pat.region","");
		recordBoolean("p.c");
		recordBoolean("p.v");
		map.put("pat.addressHouse","");
		map.put("pat.flatNumber","") ;
		map.put("pat.street","") ;
		// Район
		map.put("pat.area","") ;
		// Название города (села)
		map.put("pat.city","") ;
		
	}
	
	//8. Место смерти (адрес)
	if (deathCase.deathPlaceAddress!=null){
		//Республика, область (край)
		map.put("deathAd.reg",deathCase.deathPlaceAddress.addressRegion);
		//Город, село
		recordBoolean("deathAd.c",deathCase.deathPlaceAddress.addressIsCity);
		recordBoolean("deathAd.v",deathCase.deathPlaceAddress.addressIsVillage);
		// Район
		map.put("deathAd.area",deathCase.deathPlaceAddress.addressArea) ;
		// Название города (села)
		map.put("deathAd.city",deathCase.deathPlaceAddress.addressCity) ;
		
	} else {
		//Республика, область (край)
		map.put("deathAd.reg","");
		//Город, село
		recordBoolean("deathAd.c","");
		recordBoolean("deathAd.v","");
		// Район
		map.put("deathAd.area","") ;
		// Название города (села)
		map.put("deathAd.city","") ;
	}
	
	//9. Место смерти
	record("d.dPl",deathCase.deathPlace,1,3,"__");

	//14. Причина смерти
	record("d.dR",deathCase.deathReason,1,6,"__");
	//15. Смерть от несчастного случая
	
	
	record("d.ac",deathCase.accidentSubType,1,6,"__");
	map.put("d.acPlace",deathCase.accidentPlace) ;
	map.put("d.acCir",deathCase.accidentCircumstance) ;
	//16. Кем установлена причина смерти
	record("d.wit",deathCase.deathWitnessFunction,1,5,"__");
	//17. Врач (фельдшер)
	map.put("pat.workerInfo",deathCase.deathWitness.workerInfo)
	recordVocName("pat.worker",deathCase.deathWitness)
		//Основание установления смерти
	recordArray("d.evi",deathCase.deathEvidence,1,4);
	//18. Причины смерти
	//18.a
	recordReason("d.reas",deathCase.deathReasons,1,5,5) ;
	
	//19. Смерть после окончания беременности
	record("d.preg",deathCase.afterPregnance,1,2,"__");
	//тип документа
	record("c.docType",certificate.certificateType,1,3,"_______________________________________");	
	
	
	map.put("pat.year","") ;
	

	return map ;

}
function record(aKey,aValue,aMinVal,aMaxVal,aPrint) {
	
	for (var i=aMinVal;i<=aMaxVal;i++) {
		map.put(aKey+i,"");
	}
	if (aValue!=null) map.put(aKey+aValue.code,aPrint) ;
}
function recordArray(aKey,aArray,aMinVal,aMaxVal) {
	
	for (var i=aMinVal;i<=aMaxVal;i++) {
		map.put(aKey+i,"");
	}
	if (aArray!=null) {
		for (var i=0;i<aArray.size();i++) {
			var elem = aArray.get(i) ;
			if (elem!=null) map.put(aKey+elem.code,"__") ;
		}
		
	}
}
function recordBoolean(aKey,aBool) {
	if (aBool!=null && aBool==true) {
		map.put(aKey,"__") ;
	} else {
		map.put(aKey,"") ;
	}
}
function recordVocName(aKey,aValue) {
	if (aValue!=null) {
		map.put(aKey,aValue.name) ;
	} else {
		map.put(aKey,"") ;
	}
}
function recordReason(aKey,aArrayReason,aMinLength,aMaxLength,aCountMkb) {
	for (var i=aMinLength;i<=aMaxLength;i++) {
		map.put(aKey+i+".text","");
		for (var j=1;j<=aCountMkb;j++) map.put(aKey+i+".mkb"+j,"") ;
	}
	if (aArrayReason!=null) {
		for (var i = 0; i<aArrayReason.size();i++) {
			var reas = aArrayReason.get(i);
			if (reas.reasonType!=null) {
				map.put(aKey+reas.reasonType.code+".text",reas.reason);
				if (reas.idc10!=null) {
					var mkb = reas.idc10.code ;
					for (var j=1; j<=mkb.length(); j++) {
						map.put(aKey+reas.reasonType.code+".mkb"+j,mkb.substr(j-1,1));
						//map.put(aKey+reas.reasonType.code+".mkb"+j,j);
					}
				} 
			}
		}
	}
}
function recordDate(aKey, aDate) {
	if (aDate!=null) {
		var calen = java.util.Calendar.getInstance() ;
		calen.setTime(aDate) ;
		map.put(aKey+".day",""+calen.get(java.util.Calendar.DAY_OF_MONTH)) ;
		
		
		
		var month = ""+(calen.get(java.util.Calendar.MONTH)+1) ;
		map.put(aKey+".month",month) ;
		map.put(aKey+".monthname","");
		if (month!=null && month.equals("1")) map.put(aKey+".monthname","ЯНВАРЯ");
		if (month!=null && month.equals("2")) map.put(aKey+".monthname","ФЕВРАЛЯ");
		if (month!=null && month.equals("3")) map.put(aKey+".monthname","МАРТА");
		if (month!=null && month.equals("4")) map.put(aKey+".monthname","АПРЕЛЯ");
		if (month!=null && month.equals("5")) map.put(aKey+".monthname","МАЯ");
		if (month!=null && month.equals("6")) map.put(aKey+".monthname","ИЮНЯ");
		if (month!=null && month.equals("7")) map.put(aKey+".monthname","ИЮЛЯ");
		if (month!=null && month.equals("8")) map.put(aKey+".monthname","АВГУСТА");
		if (month!=null && month.equals("9")) map.put(aKey+".monthname","СЕНТЯБРЯ");
		if (month!=null && month.equals("10")) map.put(aKey+".monthname","ОКТЯБРЯ");
		if (month!=null && month.equals("11")) map.put(aKey+".monthname","НОЯБРЯ");
		if (month!=null && month.equals("12")) map.put(aKey+".monthname","ДЕКАБРЯ");
		
		map.put(aKey+".year",""+calen.get(java.util.Calendar.YEAR)) ;
	} else {
		map.put(aKey+".day","") ;
		map.put(aKey+".month","") ;
		map.put(aKey+".year","") ;
	}
}
function recordAge(aKey,aDateBegin,aDateEnd,aIsYear,aIsMonth,aIsDay) {
	if (aDateBegin!=null && aDateEnd!=null) {
		var calenB = java.util.Calendar.getInstance() ;
		calenB.setTime(aDateBegin) ;
		var calenE = java.util.Calendar.getInstance() ;
		calenE.setTime(aDateEnd) ;
		if (aIsDay!=null && aIsDay==1) {
			map.put(aKey+".cntday",""+(calenE.get(java.util.Calendar.DAY_OF_MONTH)-calenB.get(java.util.Calendar.DAY_OF_MONTH)+1)) ;
		}
		if (aIsMonth!=null && aIsMonth==1) {
			map.put(aKey+".cntmonth",""+(calenE.get(java.util.Calendar.MONTH)-calenB.get(java.util.Calendar.MONTH))) ;
		}
		if (aIsYear!=null && aIsYear==1) {
			map.put(aKey+".cntyear",""+(calenE.get(java.util.Calendar.YEAR)-calenB.get(java.util.Calendar.YEAR))) ;
		}
	} else {
		map.put(aKey+".cntday","");
		map.put(aKey+".cntmonth","");
		map.put(aKey+".cntyear","");
	}
}

function isSmall(aDateBegin,aDateEnd,aYear,aMonth,aDay) {
	if (aDay>0) aDay= aDay-1;
	if (aDateBegin!=null && aDateEnd!=null) {
		var calenB = java.util.Calendar.getInstance() ;
		calenB.setTime(aDateBegin) ;
		var calenE = java.util.Calendar.getInstance() ;
		calenE.setTime(aDateEnd) ;
		calenE.add(java.util.Calendar.YEAR, (aYear*(-1))) ;
		calenE.add(java.util.Calendar.MONTH, (aMonth*(-1))) ;
		calenE.add(java.util.Calendar.DAY_OF_MONTH, (aDay*(-1))) ;
		if (calenE.getTime().getTime() < calenB.getTime().getTime()) return 1;
	} 
	return 0 ;
}
