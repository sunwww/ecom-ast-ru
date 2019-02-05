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
/**
 * Печать формы кардиоскрининга новорождённых.
 *
 * @params содержит slo
 */
function printCardiacScreeningForm(aCtx, aParams) {
    var slo = aParams.get("id");
    var sql ="select mother.lastname||' '||mother.firstname||' '||mother.middlename||' '||EXTRACT(YEAR from AGE(mother.birthday))||cast(' лет' as varchar(4)) as fioagemother,\n" +
        "to_char(child.birthday,'dd.mm.yyyy') as drchild,\n" +
        "sex.name as sex,\n" +
        "round(chb.durationPregnancy,0) as dur,\n" +
        "born.birthWeight as weight,\n" +
        "to_char(scrI.createdate,'dd.mm.yyyy') as date1,\n" +
        "to_char(scrII.createdate,'dd.mm.yyyy') as date2,\n" +
        "sk1.name as skin1,\n" +
        "sk2.name as skin2,\n" +
        "vsap1.name as rightHandAP1,\n" +
        "vsap2.name as rightHandAP2,\n" +
        "vsap1_l.name as legAP1,\n" +
        "vsap2_l.name as legAP2,\n" +
        "scrI.rightHandPulse as rightHandPulse1,\n" +
        "scrII.rightHandPulse as rightHandPulse2,\n" +
        "scrI.LegPulse as legPulse1,\n" +
        "scrII.LegPulse as legPulse2,\n" +
        "scrII.RightHandAD as rightHandAD2,\n" +
        "scrII.LegAD as legAD2,\n" +
        "scrI.BreathingRate as breathingRate1,\n" +
        "scrII.BreathingRate as breathingRate2,\n" +
        "case when scrI.RetractionIntercostalGaps=true then 'Да' else 'Нет' end as retractionIntercostalGaps1,\n" +
        "case when scrII.RetractionIntercostalGaps=true then 'Да' else 'Нет' end as retractionIntercostalGaps2,\n" +
        "case when scrI.noseWingsMovement=true then 'Да' else 'Нет' end as noseWingsMovement1,\n" +
        "case when scrII.noseWingsMovement=true then 'Да' else 'Нет' end as noseWingsMovement2,\n" +
        "case when scrI.noisyBreathing=true then 'Да' else 'Нет' end as noisyBreathing1,\n" +
        "case when scrII.noisyBreathing=true then 'Да' else 'Нет' end as noisyBreathing2,\n" +
        "case when scrI.wheezing=true then 'Да' else 'Нет' end as wheezing1,\n" +
        "case when scrII.wheezing=true then 'Да' else 'Нет' end as wheezing2,\n" +
        "cns1.name as cns1,\n" +
        "cns2.name as cns2,\n" +
        "vsl_ap.name as apicalImpulseLocal2,\n" +
        "vsl_l.name as liverEdgeLocal2,\n" +
        "scrI.heartRate as heartRate1,\n" +
        "scrII.heartRate as heartRate2,\n" +
        "vsca1.name as cardiacActivity1,\n" +
        "vsca2.name as cardiacActivity2,\n" +
        "case when scrI.noisePresence=true then 'Да' else 'Нет' end as noisePresence1,\n" +
        "case when scrII.noisePresence=true then 'Да' else 'Нет' end as noisePresence2,\n" +
        "vsd.name as diuresis2,\n" +
        "scrII.ECG as ECG2,\n" +
        "scrI.extraInfo||' '||scrII.extraInfo as extraInfo2,\n" +
        "vwf1.name||' '||wp1.lastname||' '||wp1.firstname||' '||wp1.middlename as fio1,\n" +
        "vwf2.name||' '||wp2.lastname||' '||wp2.firstname||' '||wp2.middlename as fio2\n" +
        "from medcase slo \n" +
        "left join patient child on child.id=slo.patient_id\n" +
        "left join Kinsman k on k.person_id=child.id\n" +
        "left join patient mother on mother.id=k.kinsman_id \n" +
        "left join VocKinsmanRole vk on vk.id=k.KinsmanRole_id\n" +
        "left join VocSex sex on sex.id=child.sex_id\n" +
        "left join newborn born on born.patient_id=child.id\n" +
        "left join childbirth chb on chb.id=born.childbirth_id\n" +
        "left join screeningcardiac scrI on scrI.medcase_id=slo.id and scrI.dtype='ScreeningCardiacFirst'\n" +
        "left join screeningcardiac scrII on scrII.medcase_id=slo.id and scrII.dtype='ScreeningCardiacSecond'\n" +
        "left join VocScreeningSkin sk1 on sk1.id=scrI.skin_id\n" +
        "left join VocScreeningSkin sk2 on sk2.id=scrII.skin_id\n" +
        "left join VocScreeningArterialPulsation vsap1 on vsap1.id=scrI.RightHandAP_id\n" +
        "left join VocScreeningArterialPulsation vsap2 on vsap2.id=scrII.RightHandAP_id\n" +
        "left join VocScreeningArterialPulsation vsap1_l on vsap1_l.id=scrI.LegAP_id\n" +
        "left join VocScreeningArterialPulsation vsap2_l on vsap2_l.id=scrII.LegAP_id\n" +
        "left join VocScreeningCNS cns1 on cns1.id=scrI.cns_id\n" +
        "left join VocScreeningCNS cns2 on cns2.id=scrII.cns_id\n" +
        "left join VocScreeningLocalization vsl_ap on vsl_ap.id=scrII.apicalImpulseLocal_id\n" +
        "left join VocScreeningLocalization vsl_l on vsl_l.id=scrII.liverEdgeLocal_id\n" +
        "left join VocScreeningCardiacActivity vsca1 on vsca1.id=scrI.cardiacActivity_id\n" +
        "left join VocScreeningCardiacActivity vsca2 on vsca2.id=scrII.cardiacActivity_id\n" +
        "left join VocScreeningDiuresis vsd on vsd.id=scrII.diuresis_id\n" +
        "left join secUser secUser1 on secUser1.login=scrI.createusername\n" +
        "left join secUser secUser2 on secUser2.login=scrII.createusername\n" +
        "left join WorkFunction wf1 on wf1.secUser_id=secUser1.id\n" +
        "left join WorkFunction wf2 on wf2.secUser_id=secUser2.id\n" +
        "left join Worker w1 on w1.id=wf1.worker_id\n" +
        "left join Worker w2 on w2.id=wf2.worker_id\n" +
        "left join Patient wp1 on wp1.id=w1.person_id\n" +
        "left join Patient wp2 on wp2.id=w2.person_id\n" +
        "left join VocWorkFunction vwf1 on vwf1.id=wf1.workFunction_id\n" +
        "left join VocWorkFunction vwf2 on vwf2.id=wf2.workFunction_id\n" +
        "where slo.id='"+slo+"' and vk.code='38' limit 1" ;
    var list = aCtx.manager.createNativeQuery(sql).getResultList();
    var obj = list.size()>0?list.get(0):null ;
    if (obj!=null) {
        map.put("fioagemother", (obj[0]!=null)?obj[0]:"");
        map.put("drchild", (obj[1]!=null)?obj[1]:"");
        map.put("sex", (obj[2]!=null)?obj[2]:"");
        map.put("dur", (obj[3]!=null)?obj[3]:"");
        map.put("weight", (obj[4]!=null)?obj[4]:"");
        map.put("date1", (obj[5]!=null)?obj[5]:"");
        map.put("date2", (obj[6]!=null)?obj[6]:"");
        map.put("skin1", (obj[7]!=null)?obj[7]:"");
        map.put("skin2", (obj[8]!=null)?obj[8]:"");
        map.put("rightHandAP1", (obj[9]!=null)?obj[9]:"");
        map.put("rightHandAP2", (obj[10]!=null)?obj[10]:"");
        map.put("legAP1", (obj[11]!=null)?obj[11]:"");
        map.put("legAP2", (obj[12]!=null)?obj[12]:"");
        map.put("rightHandPulse1", (obj[13]!=null)?obj[13]:"");
        map.put("rightHandPulse2", (obj[14]!=null)?obj[14]:"");
        map.put("legPulse1", (obj[15]!=null)?obj[15]:"");
        map.put("legPulse2", (obj[16]!=null)?obj[16]:"");
        map.put("rightHandAD2", (obj[17]!=null)?obj[17]:"");
        map.put("legAD2", (obj[18]!=null)?obj[18]:"");
        map.put("breathingRate1", (obj[19]!=null)?obj[19]:"");
        map.put("breathingRate2", (obj[20]!=null)?obj[20]:"");
        map.put("retractionIntercostalGaps1", (obj[21]!=null)?obj[21]:"");
        map.put("retractionIntercostalGaps2", (obj[22]!=null)?obj[22]:"");
        map.put("noseWingsMovement1", (obj[23]!=null)?obj[23]:"");
        map.put("noseWingsMovement2", (obj[24]!=null)?obj[24]:"");
        map.put("noisyBreathing1", (obj[25]!=null)?obj[25]:"");
        map.put("noisyBreathing2", (obj[26]!=null)?obj[26]:"");
        map.put("wheezing1", (obj[27]!=null)?obj[27]:"");
        map.put("wheezing2", (obj[28]!=null)?obj[28]:"");
        map.put("CNS1", (obj[29]!=null)?obj[29]:"");
        map.put("CNS2", (obj[30]!=null)?obj[30]:"");
        map.put("apicalImpulseLocal2", (obj[31]!=null)?obj[31]:"");
        map.put("liverEdgeLocal2", (obj[32]!=null)?obj[32]:"");
        map.put("heartRate1", (obj[33]!=null)?obj[33]:"");
        map.put("heartRate2", (obj[34]!=null)?obj[34]:"");
        map.put("cardiacActivity1", (obj[35]!=null)?obj[35]:"");
        map.put("cardiacActivity2", (obj[36]!=null)?obj[36]:"");
        map.put("noisePresence1", (obj[37]!=null)?obj[37]:"");
        map.put("noisePresence2", (obj[38]!=null)?obj[38]:"");
        map.put("diuresis2", (obj[39]!=null)?obj[39]:"");
        map.put("ECG2", (obj[40]!=null)?obj[40]:"");
        map.put("extraInfo", (obj[41]!=null)?obj[41]:"");
        map.put("fio1", (obj[42]!=null)?obj[42]:"");
        map.put("fio2", (''+obj[43]!=''+obj[42])?obj[43]:"");
        map.put("provod", (''+obj[43]!=''+obj[42] && obj[43]!=null && obj[42]!=null)?"проводивших":"проводившего");
    }
    return map;
}