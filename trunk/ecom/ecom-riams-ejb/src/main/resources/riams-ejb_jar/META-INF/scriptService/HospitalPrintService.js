var map = new java.util.HashMap() ;
function printAddressSheetArrival(aCtx, aParams) {
	var ids1 = aParams.get("id") ;
	var spec = aParams.get("spec") ;
	var dateBegin = aParams.get("dateBegin") ;
	var dateEnd = aParams.get("dateEnd") ;
	map.put("dates",dateBegin) ;
	map.put("datepo",dateEnd) ;
	var specInfo = "" ;
	var dep = aParams.get("department") ;
	if (spec!=null && +spec>0) {
		var list = aCtx.manager.createNativeQuery("select wp.lastname||' '||substring(wp.firstname,0,2)||' '||substring(wp.middlename,0,2),wp.id  from workfunction wf left join worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id where wf.id="+spec)
		.getResultList();
		if (list.size()>0) {		
			specInfo =list.get(0)[0];
		}
		list.clear() ;
	}
	map.put("spec",specInfo) ;
	var depAddress = "" ;
	var depName = "" ;
	if (dep!=null && +dep>0) {
		var list = aCtx.manager.createNativeQuery("select l.name,a.fullname,l.houseNumber , l.houseBuilding from mislpu l left join address2 a on a.addressid=l.address_addressid where l.id="+dep).getResultList() ;
		if (list.size()>0) {
			var ob = list.get(0) ;
			depName = ob[0] ;
			if (ob[1]!=null &&ob[1]!="") {
				depAddress = ob[1] ;
				if (ob[2]!=null&&ob[2]!="") depAddress = depAddress+" д."+ob[2] ;
				if (ob[3]!=null&&ob[3]!="") depAddress = depAddress+" корп."+ob[3] ;
			}
		}
	}
	map.put("depAddress",depAddress) ;
	map.put("depName",depName) ;
	var ids = ids1.split(",") ;
	var ret = new java.util.ArrayList() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	var sn=0 ;
	for (var i=0; i < ids.length; i++) {
		var id1=ids[i] ;
		var indlast = id1.lastIndexOf(":") ;
		var id = id1.substring(indlast+1) ;
		
		var medcase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase
		, new java.lang.Long(id)) ;
		var kinsman = medcase.kinsman!=null?medcase.kinsman.kinsman:null ;
		var pat = medcase.patient ;
		var compilationDate = medcase.dateStart!=null?FORMAT_2.format(medcase.dateStart):"" ;
		var dischargeDate = medcase.dateFinish!=null?FORMAT_2.format(medcase.dateFinish):"" ;
		if (pat!=null) {
			sn++ ;
			ret.add(saveInfoByPatient(pat,compilationDate,dischargeDate,sn,FORMAT_2)) ;
		}
		if (kinsman!=null) {
			sn++ ;
			var obj = saveInfoByPatient(kinsman,compilationDate,dischargeDate,sn,FORMAT_2) ;
			ret.add(obj) ;
		}
	}
	var ret1 = new java.util.ArrayList() ;
	
	for (var i=0; i < ret.size(); i++) {
		var par = new Packages.ru.ecom.mis.ejb.service.medcase.AddressSheetParentPrintForm()  ;
		par.setDoc1(ret.get(i)) ;
		i++ ;if (i<ret.size()) par.setDoc2(ret.get(i)) ;
		i++ ;if (i<ret.size()) par.setDoc3(ret.get(i)) ;
		i++ ;if (i<ret.size()) par.setDoc4(ret.get(i)) ;
		
		ret1.add(par) ;
		
	}
	
	map.put("list",ret1) ;
	//map.put("list1",ret) ;
	return map ;
	
}
function saveInfoByPatient(aPatient,aComplicationDate,aDischargeDate,aSn,aFormat2) {
	var obj = new Packages.ru.ecom.mis.ejb.service.medcase.AddressSheetPrintForm()  ;
	obj.setCompilationDate(aComplicationDate) ;
	obj.setDischargeDate(aDischargeDate) ;
	obj.setSn(aSn) ;
	obj.setAddress(aPatient.addressRegistration + ((aPatient.rayon!=null && aPatient.code!='ИО')?(" "+aPatient.rayon.name):"" ));
	obj.setLastname(aPatient.lastname) ;
	obj.setFirstname(aPatient.firstname);
	obj.setMiddlename(aPatient.middlename);
	obj.setBirthday(aPatient.birthday!=null?aFormat2.format(aPatient.birthday):"") ;
	obj.setNationality(aPatient.nationality!=null?aPatient.nationality.name:"") ;
	obj.setBirthPlace(aPatient.birthPlace!=null?aPatient.birthPlace:"") ;
	obj.setSex(aPatient.sex!=null? aPatient.sex.name:"") ;
	if (aPatient.passportType!=null) {
		obj.setDocument("вид "+aPatient.passportType.name
				+" серия "
				+(aPatient.passportSeries!=null?aPatient.passportSeries:"____")
				+" №"
				+(aPatient.passportNumber!=null?aPatient.passportNumber:"_____________")
				+" выдан "
				+(aPatient.passportWhomIssued!=null?aPatient.passportWhomIssued:
					"_____________________________________")
				
				+" "+(aPatient.passportDateIssued!=null?aFormat2.format(aPatient.passportDateIssued):"___.___.______")
				+" код подразделения, выдавшего документ "
				+(aPatient.passportCodeDivision!=null?aPatient.passportCodeDivision:"_________"));
	} else {
		obj.setDocument("документов не имеет") ;					
	}
	return obj ;

}

function printMedServicies(aCtx, aParams) {
	var ids1 = aParams.get("id") ;
	var ids = ids1.split(",") ;
	//infoServiceMedCase(aCtx,ids1[0]) ;
	//infoPrint(aCtx,ids1[0]) ;
	var ret = new java.lang.StringBuilder () ;
	//ret.append(ids) ;
	
	//throw ids ;
	var ii = 0 ;
	var ret = new java.util.ArrayList() ;
	map.put("medIds",ids1) ;
	var curDate = new java.util.Date() ;
	var curTime = new java.sql.Time(curDate.getTime()) ;
	for (var i=0; i < ids.length; i++) {
		var id1=ids[i] ;
		var indlast = id1.lastIndexOf(":") ;
		var id = id1.substring(indlast+1) ;
		
		var service = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.ServiceMedCase
		, new java.lang.Long(id)) ;
		
		var mapS= new Packages.ru.ecom.mis.ejb.form.medcase.ServiceMedCaseForm()  ;
		service.setPrintDate(curDate) ;
		service.setPrintTime(curTime) ;
		mapS.setId(service.id) ;
		mapS.setDateExecute(""+service.dateExecute) ;
		ret.add(mapS) ;
	}
	map.put("medServicies",ret) ;
	return map ;
}

function printSurOperation(aCtx,aParams) {
	var surOperation = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.SurgicalOperation
		, new java.lang.Long(aParams.get("id"))) ;
	var medCase = surOperation.medCase ;
	
	map.put("so",surOperation) ;
	map.put("medCase",medCase) ;
	map.put("pat",medCase.patient) ;
	var list=aCtx.manager.createNativeQuery("select count(*),list(' '||avwf.name||' '||awp.lastname||' '||awp.firstname||' '||awp.middlename),list(' '||vam.name||' (кол-во '||a.duration||')') from Anesthesia a"
		+" left join WorkFunction awf on awf.id=a.anesthesist_id"
		+" left join Worker aw on aw.id=awf.worker_id"
		+" left join Patient awp on awp.id=aw.person_id"
		+" left join VocWorkFunction avwf on avwf.id=awf.workFunction_id"
		+" left join VocAnesthesiaMethod vam on vam.id=a.method_id"
		+" where surgicalOperation_id=:id"
	) .setParameter("id",surOperation.id).getSingleResult() ;
	var list1=aCtx.manager.createNativeQuery("select "
	+" count(*), case when h.statisticStub_id is not null then list(ss.code) when d.statisticStub_id is not null then list(ss1.code) else '' end"
	+" from MedCase d"
	+" left join MedCase h on h.id=d.parent_id and h.dtype='HOSPITALMEDCASE'"
	+" left join StatisticStub ss on ss.id=h.statisticStub_id"
	+" left join StatisticStub ss1 on ss1.id=d.statisticStub_id"
	+" where d.id=:id"
	).setParameter("id",medCase.id).getSingleResult() ;
	map.put("surOper.anesthesist",list[1]) ;
	map.put("surOper.method",list[2]) ;
	map.put("surOper.statisticStub",list1[1]) ;
	return map ;
}

function printPregHistoryByMC(aCtx, aParams) {
	//var pregHistory = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.birth.PregnancyHistory
	//	, new java.lang.Long(aParams.get("id"))) ;
	var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase
		, new java.lang.Long(aParams.get("id"))) ;
	map.put("confCert",null) ;
	//var medCase = pregHistory.medCase ;
	//map.put("confCert",pregHistory.confinementCertificate) ;
	map.put("pregnancy", medCase.pregnancy) ;
	map.put("pregCard", medCase.pregnancy!=null?medCase.pregnancy.pregnanExchangeCard:null) ;
	var pregInspect = null ;
	for (var i=0 ; i<medCase.inspections.size();i++) {
		var inspection = medCase.inspections.get(i) ;
		if (inspection instanceof Packages.ru.ecom.mis.ejb.domain.birth.PregnanInspection) {
			pregInspect = inspection ;
		}
	}
	map.put("pregInspect",pregInspect) ;
	recordPolicy(medCase.policies);
	recordPatient(medCase,aCtx);
	recordMedCaseDefaultInfo(medCase) ;
	return map ;
	
}
function printPregHistory(aCtx, aParams) {
	var pregHistory = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.birth.PregnancyHistory
		, new java.lang.Long(aParams.get("id"))) ;
	//var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase
	//	, new java.lang.Long(aParams.get("id"))) ;
	var medCase = pregHistory.medCase ;
	map.put("confCert",pregHistory.confinementCertificate) ;
	map.put("pregnancy", medCase.pregnancy) ;
	map.put("pregCard", medCase.pregnancy!=null?medCase.pregnancy.pregnanExchangeCard:null) ;
	var pregInspect = null ;
	for (var i=0 ; i<medCase.inspections.size();i++) {
		var inspection = medCase.inspections.get(i) ;
		if (inspection instanceof Packages.ru.ecom.mis.ejb.domain.birth.PregnanInspection) {
			pregInspect = inspection ;
		}
	}
	map.put("pregInspect",pregInspect) ;
	recordPolicy(medCase.policies);
	recordPatient(medCase,aCtx);
	recordMedCaseDefaultInfo(medCase) ;
	return map ;
	
}

function printNewBornHistory(aCtx, aParams){
	var newBornHistory = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.birth.NewBorn
		, new java.lang.Long(aParams.get("id"))) ;
	var pregInspect = null ;
	for (var i=0 ; i<medCase.inspections.size();i++) {
		var inspection = medCase.inspections.get(i) ;
		if (inspection instanceof Packages.ru.ecom.mis.ejb.domain.birth.PregnanInspection) {
			pregInspect = inspection ;
		}
	}
	map.put("pregInspect",pregInspect) ;
}

function recordPolicy(policies) {
	var rec="" ;
	for(var i=0; i<policies.size(); i++) {
		var policy = policies.get(i);
		if (!rec.equals("")) rec = rec + "; " ;
		rec = rec+policy.text ;
	}
	if (!rec.equals("")) {
		map.put("policyIs","");
	} else {
		map.put("policyIs","Полиса НЕТ");
	}
	map.put("policyInfo",rec);
}

function recordPatient(medCase,aCtx) {
	//1. Фамилия имя отчество
	map.put("patient",medCase.patient) ;
			
	map.put("pat.lastname",medCase.patient.lastname) ;
	map.put("pat.firstname",medCase.patient.firstname) ;
	map.put("pat.middlename",medCase.patient.middlename) ;
	//3. Дата рождения
	map.put("pat.birthday",medCase.patient.birthday);
	//2. Пол
	map.put("pat.sex",medCase.patient.sex) ;
	map.put("pat.rayon",medCase.patient.rayon) ;
	//3. Возраст (полных лет, для детей: до 1 года - месяцев, до 1 месяца - дней)
	getAge("pat.age",medCase.patient.birthday,medCase.dateStart,aCtx.manager) ;
	//4. Постоянное место жительства: город, село и адрес
	getAddress("pat.address",medCase.patient.address,medCase.patient) ;
	map.put("pat.addressReal",medCase.patient.addressReal) ;
	map.put("pat.addressReg",medCase.patient.addressRegistration) ;
	//Дом, корпус
	map.put("pat.addressHouse",medCase.patient.addressHouse) ;
	// Квартира
	map.put("pat.flatNumber",medCase.patient.flatNumber) ;
	//5. Место работы, профессия или должность
	map.put("pat.works",medCase.patient.works);
	var workDiv = "" ;
	if (medCase.patient.works!=null && medCase.patient.workPost!=null && !medCase.patient.workPost.equals("")) workDiv="," ;
	map.put("pat.workDiv",workDiv) ;
	map.put("pat.wPost",medCase.patient.workPost) ;
	//Документ, удостоверяющий личность
	map.put("pat.identityCard",medCase.patient.passportType);
	map.put("pat.identityCardInfo",medCase.patient.passportInfo);
	//Социальный статус
	map.put("pat.socialStatus",medCase.patient.socialStatus) ;
	
	//Образование
	map.put("pat.educationType",medCase.patient.educationType) ;
	//Национальность
	map.put("pat.ethnicity",medCase.patient.ethnicity) ;
	//12 СНИЛС
	if (medCase.patient.snils==("")) {
		map.put("pat.snils","______-______-______ ___") ;
	} else {
		map.put("pat.snils",medCase.patient.snils);
	}
	//13 Доставлен
	map.put("sls.orderType",medCase.orderType) ;
	map.put("sls.attendant",medCase.attendant) ;
	//Номер направления
	map.put("sls.orderNumber",medCase.orderNumber) ;
	//дата напраления
	map.put("sls.orderDate",medCase.orderDate) ;
	map.put("sls.hospType",medCase.hospType) ;
	//Номер наряда
	map.put("sls.supplyOrderNumber",medCase.supplyOrderNumber) ;
	
	if(medCase.temperatureCurves.size()>0) {
		var temper = medCase.temperatureCurves.get(0) ;
		var temperInfo = "";
		if (temper.weight>0) {
			temperInfo = temperInfo+"вес "+temper.weight ;
		} else {
			temperInfo = temperInfo+"вес ____" ;
		}
		if (temper.degree>0) {
			temperInfo = temperInfo+" темп. "+temper.degree ;
		} else {
			temperInfo = temperInfo+" темп. ____" ;
		}
		map.put("sls.temperature",temperInfo) ;
	} else {
		map.put("sls.temperature","") ;
	}
	//опьянение
	if(medCase.intoxication!=null){
		map.put("intoxication",medCase.intoxication.name) ;
	/*
	if(medCase.intoxication.code.equals("1"))	{map.put("intoxication","<text:span text:style-name=\"T23\">Алкогольного-1</text:span> Наркотического-2")  ;}
	else {
	if(medCase.intoxication.code.equals("2")) map.put("intoxication","Алкогольного-1 <text:span text:style-name=\"T23\">Наркотического-2</text:span>")  ;
	else map.put("intoxication","Алкогольного-1 Наркотического-2")  ;
	
	}
	*/
	}else map.put("intoxication","Алкогольного-1 Наркотического-2")  ;
	//госпиитализирован впервые повтороно
	if(medCase.hospitalization!=null){
	if(medCase.hospitalization.code=="1") {map.put("hosp","<text:span text:style-name=\"T23\">впервые</text:span> повторно") ;}
	else {map.put("hosp","впервые <text:span text:style-name=\"T23\">повторно</text:span>") ;}
	}
	else map.put("hosp","впервые повторно")  ;
	
	
	if (medCase.pediculosis!=null) {
		map.put("pediculInfo",medCase.pediculosis.name) ;
	} else {
		map.put("pediculInfo","") ;
	}
	
	//Вид оплаты
	recordServiseStream("stream",medCase) ;
	
	//Вид травмы
	recordTrauma("Trauma",medCase) ;

}

function recordDisability(aContext,medCase) {
	var list = aContext.manager.createQuery("select id,name from VocSex").getResultList() ;
	//map.put("test",list.get(0).getName()) ;
}

function recordMedCaseDefaultInfo(medCase) {
	map.put("medCase",medCase) ;
	map.put("sls.lpu", medCase.lpu) ;
	map.put("lpu.name",medCase.mainLpuInfo) ;
	map.put("sls.statCardNumber", medCase.statCardNumber) ;
	map.put("sls.admissionDate", medCase.dateStart) ;
	map.put("sls.admissionTime", medCase.entranceTime) ;
	//дата выписки
	map.put("sls.dischargeDate", medCase.dateFinish) ;
	//время выписки
	map.put("sls.dischargeTime", medCase.dischargeTime) ;
	//6. Кем направлен больной
	map.put("sls.whosRefer",medCase.orderLpu) ;
	//8. Диагноз направившего учреждения
	getDiagnos("sls.diagnosisOrder",medCase.diagnosOrder) ;
	//9. Диагноз при поступлении
	getDiagnos("sls.diagnosisAdmission",medCase.diagnosEntrance) ;
	//10. Диагноз клинический
	getDiagnos("sls.diagnosisClinical",medCase.diagnosClinical) ;
	//11б осложнение основного
	getDiagnos("sls.diagnosisComplication",medCase.diagnosComplication);
	//11в сопутствующий
	getDiagnos("sls.diagnosisConcominant",medCase.diagnosConcominant);

	map.put("sls.hospitalization",medCase.hospitalization) ;
	map.put("sls.emergency",medCase.emergency) ;
	map.put("sls.kinsInfo",medCase.kinsman!=null?medCase.kinsman.info:"") ;
}

function printStatCardInfo(aCtx, aParams) {
	var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase
		, new java.lang.Long(aParams.get("id"))) ;
	
	recordPolicy(medCase.policies) ;
	recordPatient(medCase,aCtx) ;
	recordMedCaseDefaultInfo(medCase) ;

	recordDisability(aCtx,medCase) ;
	
	var depDirect = "" ;
	var hospType = medCase.hospType ;
	if (hospType!=null) {
		if (hospType.code=="DAYTIMEHOSP") {
			depDirect = "(дневное)" ;
		}
	}

	map.put("sls.departmentDirection",medCase.department) ;
	map.put("sls.typeDirect",depDirect) ;
	//if (medCase.diagnosClinical.establishDate.equals("")){
	//map.put("diag.establishDate", "1") ;}
	//else {
	//map.put("diag.establishDate",medCase.diagnosClinical.establishDate);
	//}
	map.put("sls.daysCount", medCase.daysCount) ;
	//recordVocProba("sls.hosp",medCase.hospitalization,1,2);
	//7. Доставлен по экстренным показания
	toBeOrNotToBe("sls.dostavlen",medCase.emergency) ;
	// через_________часов после начала заболевания, получения травмы; госпитализирован в плановом порядке
	map.put("sls.preAdmissionTime",medCase.preAdmissionTime) ;
	//10. Диагноз клинический
	getDiagnos("sls.diagnosisClinical",medCase.diagnosClinical) ;
	//11. Диагноз заключительный клинический
	//11а основной
	getDiagnos("sls.diagnosisConcluding",medCase.diagnosConcluding);
	//11б осложнение основного
	getDiagnos("sls.diagnosisComplication",medCase.diagnosComplication);
	//11в сопутствующий
	getDiagnos("sls.diagnosisConcominant",medCase.diagnosConcominant);
	
	return map ;
}

function printBilling(aCtx, aParams)
{
	var id = aParams.get("id") ;
	var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase
		, new java.lang.Long(id)) ;

	//Свединия по пациенту	
	recordPatient(medCase,aCtx) ;
	//Диагнозы
	recordMedCaseDefaultInfo(medCase) ;
	var otds =aCtx.manager.createNativeQuery("select d.name,to_char(dmc.dateStart,'DD.MM.YYYY'),COALESCE(to_char(dmc.dateFinish,'DD.MM.YYYY'),to_char(dmc.transferDate,'DD.MM.YYYY')) "
		+", ifnull(dateFinish,'',vwf.name||' '||p.lastname||' '|| p.firstname ||' '||p.middlename)"
		+", ifnull(dateFinish,'',d.name)"
		+"from MedCase dmc "
		+" left join MisLpu d on d.id=dmc.department_id "
		+" left join WorkFunction wf on wf.id=dmc.ownerFunction_id "
		+" left join VocWorkFunction vwf on wf.workFunction_id=vwf.id "
		+" left join Worker w on w.id=wf.worker_id "
		+" left join Patient p on p.id=w.person_id "
		+" where dmc.parent_id='"+id+"' and dmc.DTYPE='DepartmentMedCase'").getResultList();
	var otd = "";
	var lech = ""
	var lastotd = "";
	for (var i=0; i<otds.size(); i++) {
		var dep = otds.get([i]) ;
		if (otd!="") {otd += ", ";}
		otd += dep[0] +" "+" c "+dep[1]+" по "+dep[2] ;
		lech += dep[3] ;
		lastotd += dep[4] ;
	}
	map.put("sls.departments",otd) ;
	map.put("sls.owner",lech) ;
	map.put("sls.lastOtd",lastotd) ;
	//5. Даты: поступления, выбытия
	map.put("sls.Start",medCase.dateStart) ;
	map.put("sls.Finish",medCase.dateFinish) ;
	//выписной диагноз
	getDiagnos("sls.diagnosConcluding",medCase.diagnosConcluding);
	map.put("sls.dischargeRecord",recordMultiValue(medCase.dischargeEpicrisis));
	//текущая дата
	var currentDate = new Date() ;
	map.put("currentDate",currentDate.getDate()+"."+(currentDate.getMonth()+1)+"."+(1900+currentDate.getYear())+"г.") ;
	
	return map ;
}

function getCode(aKey, aValue)
{
	if(aValue!=null) map.put("aKey",aValue.code) ;
}
function printProtocols(aCtx, aParams) {
	var ids1 = aParams.get("id") ;
	var ids = ids1.split(",") ;
	infoSmo(aCtx,ids[0]) ;
	infoPrint(aCtx,ids[0]) ;
	var ret = new java.lang.StringBuilder () ;
	//ret.append(ids) ;
	//var list = new 
	//throw ids ;
	
	
	var ret = new java.util.ArrayList() ;
	var FORMAT_1 = new java.text.SimpleDateFormat("yyyy-MM-dd") ;
    var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
    var FORMAT_3 = new java.text.SimpleDateFormat("HH:mm") ;
	//var startDate =  FORMAT_2.parse(obj[0])) ;
	//var finishDate = FORMAT_1.format( FORMAT_2.parse(obj[1])) ;
	var current = new java.util.Date() ;
	var curDate = new java.sql.Date(current.getTime()) ;
	
	var curTime = new java.sql.Time(current.getTime()) ;
	for (var i=0; i < ids.length; i++) {
		var id1=ids[i] ;
		var indlast = id1.lastIndexOf(":") ;
		var id = id1.substring(indlast+1) ;
		
		var protocol = aCtx.manager.find(Packages.ru.ecom.poly.ejb.domain.protocol.Protocol
		, new java.lang.Long(id)) ;
		protocol.setPrintDate(curDate) ;
		protocol.setPrintTime(curTime) ;
		
		var mapS= new Packages.ru.ecom.poly.ejb.form.protocol.VisitProtocolForm()  ;
		mapS.timeRegistration=FORMAT_3.format(protocol.timeRegistration);
		mapS.dateRegistration=FORMAT_1.format(protocol.dateRegistration);
		mapS.specialistInfo=protocol.specialistInfo ;
		/*var n = '\n'  ;
		var items = protocol.record.split(n) ;
		//mapS.setId(service.id) ;
		//mapS.setDateExecute(""+service.dateExecute) ;
		var rec = new java.lang.StringBuilder() ;
		for (var j = 0; j < items.length; j++) {
			rec.append(items[j]) ;
			if (j < items.length-1) rec.append("<text:line-break/>") ;
			//rec.append("                                                      ");
		}
		*/
		mapS.setRecord(recordMultiValue(protocol.record));
		//mapS.typeInfo = protocol.medCase.info
		ret.add(mapS) ;
		
	}
	map.put("protocols",ret) ;
	
	
	/*
	
	for (var i=0; i < ids.length; i++) {
		var id1=ids[i] ;
		var indlast = id1.lastIndexOf(":") ;
		var id = id1.substring(indlast+1) ;
		//ret.append("id=") ;
		//ret.append(id).append(",") ;
		
		
		ret.append(protocol.dateRegistration).append(" ").append(protocol.timeRegistration).append("          ЗАКЛЮЧЕНИЕ") ;
		ret.append("</text:p><text:p>");
		ret.append();
		ret.append("</text:p><text:p>");
		ret.append("</text:p><text:p>").append(protocol.specialistInfo);
		ret.append("</text:p><text:p>");
		
		ret.append("</text:p><text:p> 	Подпись врача ______________________");
		ret.append("</text:p><text:p>");
		ret.append("</text:p><text:p>");
		ret.append("</text:p><text:p>");
		
	}*/
	
	//map.put("protocols","fdsfdsfsd") ;
	//throw ret.toString() ;
	//map.put("protocol.text",ret.toString()) ;

	//map.put("protocols.text","gsdfgsd") ;
	return map ;
}
function printMedServies(aCtx, aParams) {
	var ids1 = aParams.get("id") ;
	var ids = ids1.split(",") ;
	infoSmo(aCtx,ids1[0]) ;
	infoPrint(aCtx,ids1[0]) ;
	var ret = new java.lang.StringBuilder () ;
	//ret.append(ids) ;
	
	//throw ids ;
	for (var i=0; i < ids.length; i++) {
		var id1=ids[i] ;
		var indlast = id1.lastIndexOf(":") ;
		var id = id1.substring(indlast+1) ;
		//ret.append("id=") ;
		//ret.append(id).append(",") ;
		
		var protocol = aCtx.manager.find(Packages.ru.ecom.poly.ejb.domain.protocol.Protocol
		, new java.lang.Long(id)) ;
		ret.append(protocol.dateRegistration).append(" ").append(protocol.timeRegistration).append("          ЗАКЛЮЧЕНИЕ") ;
		ret.append("</text:p><text:p>");
		ret.append(recordMultiValue(protocol.record));
		ret.append("</text:p><text:p>");
		ret.append("</text:p><text:p>").append(protocol.specialistInfo);
		ret.append("</text:p><text:p>");
		
		ret.append("</text:p><text:p> 	Подпись врача ______________________");
		ret.append("</text:p><text:p>");
		ret.append("</text:p><text:p>");
		ret.append("</text:p><text:p>");
		
	}
	
	//map.put("protocols","fdsfdsfsd") ;
	//throw ret.toString() ;
	map.put("protocol.text",ret.toString()) ;

	//map.put("protocols.text","gsdfgsd") ;
	return map ;
}
function infoSmo(aCtx,aParams) {
	map.put("smo.info","") ;
}
function infoPrint(aCtx,aParams) {
	map.put("print.info","") ;
}
function printProtocol (aCtx,aParams){
	var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase
		, new java.lang.Long(aParams.get("id"))) ;
	//var list = aCtx.manager.createQuery("from Protocol where medCase_id=:sls")
		//.setParameter("sls",medCase.id).getResultList();
	//var protocol = !list.isEmpty()?list.iterator().next().record:"";
	var protocol = aCtx.manager.find(Packages.ru.ecom.poly.ejb.domain.protocol.Protocol
		, new java.lang.Long(aParams.get("id"))) ;
	var current = new java.util.Date() ;
	var curDate = new java.sql.Date(current.getTime()) ;
	var curTime = new java.sql.Time(current.getTime()) ;
	protocol.setPrintDate(curDate) ;
	protocol.setPrintTime(curTime) ;	
	map.put("prot.date",protocol.dateRegistration);
	map.put("prot.time",protocol.timeRegistration);
	map.put("protocol",protocol);
	map.put("prot.spec",protocol.specialistInfo);
	map.put("medCase.info",protocol.medCase.info) ;
	recordMultiText("prot.rec", protocol.record) ;

	return map ;
}
	// получить возраст (полных лет, для детей: до 1 года - месяцев, до 1 месяца - дней)
function getAge(aKey,aBirthday,aDate,aManager) {
	if (aDate!=null && aBirthday!=null) {
		/**/
		var calenB = java.util.Calendar.getInstance() ;
		calenB.setTime(aBirthday) ;
		var calenE = java.util.Calendar.getInstance() ;
		calenE.setTime(aDate) ;
		var year = calenE.get(java.util.Calendar.YEAR)-calenB.get(java.util.Calendar.YEAR) ;
		var dd=calenE.get(java.util.Calendar.DAY_OF_MONTH)-calenB.get(java.util.Calendar.DAY_OF_MONTH) ;
		var list = aManager.createNativeQuery("select top 1 $piece($$dif^Zcdat('"+
			Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(aBirthday)+"','"+
			Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(aDate)
			+"',0),'.',1),$piece($$dif^Zcdat('"+
			Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(aBirthday)+"','"+
			Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(aDate)
			+"',0),'.',2),$piece($$dif^Zcdat('"+
			Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(aBirthday)+"','"+
			Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(aDate)
			+"',0),'.',3), $$dif^Zcdat('"+
			Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(aBirthday)+"','"+
			Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(aDate)
			+"',3)  from VocSex").getResultList();
		if (list.size()>0) {
			var age=list.get(0) ;
			map.put(aKey,""+age[3]) ;
			
			/*if (+age[0]>1) {
				map.put(aKey,""+age[0]+" г") ;
			} else {
				if (+age[1]>0) {
					map.put(aKey,""+age[1] +" м") ;
				} else {
					var d = +age[2] ;
					d = d+1;
					map.put(aKey,""+d+" д") ;
				}
			}*/

		}
	} else {
		map.put(aKey,"");
	}
}
function toBeOrNotToBe(aKey,aValue) {
	map.put(aKey,(aValue!=null && aValue==true)? "Да": "Нет") ;
}
function getAddress(aKey, aAddress,aPat) {
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
		//map.put(aKey+".info",aAddress.getAddressInfo(aPat.houseNumber, aPat.houseBuilding, aPat.flatNumber)) ;
	}else{
		map.put(aKey+".CityOrVillage","город, село (нужное подчеркнуть)") ;
	}
	map.put(aKey+".info",aPat.getAddressRegistration()) ;
	map.put(aKey+".real",aPat.getAddressReal()) ;
}
function getDiagnos(aKey,aDiag) {
	if (aDiag!=null) {
		map.put(aKey+".text",aDiag.name) ;
		map.put(aKey+".mkb","") ;
		if (aDiag.idc10!=null) map.put(aKey+".mkb","("+aDiag.idc10.code+")");
	} else {
		map.put(aKey+".text","") ;
		map.put(aKey+".mkb","");
	}
}

function recordServiseStream(aKey,aMedCase) {
	var k1="<text:span text:style-name=\"T23\">" ;
	var k2="</text:span>" ;
	if(aMedCase.serviceStream!=null){
		if(aMedCase.serviceStream.code.equals("OBLIGATORYINSURANCE")){
			map.put(aKey,k1+"ОМС – 1"+k2+"; Бюджет – 2; Платные услуги – 3; в т.ч. ДМС – 4; Другое – 5.") ;
		 }
		if(aMedCase.serviceStream.code.equals("BUDGET")){
			map.put(aKey,"ОМС – 1; "+k1+"Бюджет – 2"+k2+"; Платные услуги – 3; в т.ч. ДМС – 4; Другое – 5.") ;
		 }
		if(aMedCase.serviceStream.code.equals("CHARGED")){
			map.put(aKey,"ОМС – 1; Бюджет – 2"+k1+"; Платные услуги – 3;"+k2+" в т.ч. ДМС – 4; Другое – 5.") ;
		 }		
		if(aMedCase.serviceStream.code.equals("PRIVATEINSURANCE")){
			map.put(aKey,"ОМС – 1; Бюджет – 2; Платные услуги – 3;"+k1+" в т.ч. ДМС – 4;"+k2+" Другое – 5.") ;
		 }	
		}
	else map.put(aKey,"ОМС – 1; Бюджет – 2; Платные услуги – 3; в т.ч. ДМС – 4;"+k1+" Другое – 5."+k2) ;
}

function recordTrauma(aKey,aMedCase) {
	var k1="<text:span text:style-name=\"T23\">" ;
	var k2="</text:span>" ;
	if(aMedCase.trauma!=null){
		if(aMedCase.Diagnosis.traumaType.omcCode.equals("1")){
			map.put(aKey,k1+"промышленная-1;"+k2+" транспортная-2; в т.ч. ДТП-3; с/хоз.-4; прочие-5;непроизводственная: бытовая-6; уличная-7;транспортная-8; в т.ч. ДТП-9; школьная-10; спортивная-11; противоправная травма-12; прочие-13.") ;
		 }
		if(aMedCase.Diagnosis.traumaType.omcCode.equals("2")){
			map.put(aKey,"промышленная-1;"+k1+" транспортная-2;"+k2+" в т.ч. ДТП-3; с/хоз.-4; прочие-5;непроизводственная: бытовая-6; уличная-7;транспортная-8; в т.ч. ДТП-9; школьная-10; спортивная-11; противоправная травма-12; прочие-13.") ;
		 }
		if(aMedCase.Diagnosis.traumaType.omcCode.equals("3")){
			map.put(aKey,"промышленная-1; транспортная-2; в т.ч."+k1+" ДТП-3"+k2+"; с/хоз.-4; прочие-5;непроизводственная: бытовая-6; уличная-7;транспортная-8; в т.ч. ДТП-9; школьная-10; спортивная-11; противоправная травма-12; прочие-13.") ;
		 }
		if(aMedCase.Diagnosis.traumaType.omcCode.equals("4")){
			map.put(aKey,"промышленная-1; транспортная-2; в т.ч. ДТП-3;"+k1+" с/хоз.-4;"+k2+" прочие-5;непроизводственная: бытовая-6; уличная-7;транспортная-8; в т.ч. ДТП-9; школьная-10; спортивная-11; противоправная травма-12; прочие-13.") ;
		 }
		if(aMedCase.Diagnosis.traumaType.omcCode.equals("5")){
			map.put(aKey,"промышленная-1; транспортная-2; в т.ч. ДТП-3; с/хоз.-4; "+k1+"прочие-5"+k2+";непроизводственная: бытовая-6; уличная-7;транспортная-8; в т.ч. ДТП-9; школьная-10; спортивная-11; противоправная травма-12; прочие-13.") ;
		 }
		if(aMedCase.Diagnosis.traumaType.omcCode.equals("6")){
			map.put(aKey,"промышленная-1; транспортная-2; в т.ч. ДТП-3; с/хоз.-4; прочие-5;непроизводственная:"+k1+" бытовая-6"+k2+"; уличная-7;транспортная-8; в т.ч. ДТП-9; школьная-10; спортивная-11; противоправная травма-12; прочие-13.") ;
		 }
		if(aMedCase.Diagnosis.traumaType.omcCode.equals("7")){
			map.put(aKey,"промышленная-1; транспортная-2; в т.ч. ДТП-3; с/хоз.-4; прочие-5;непроизводственная: бытовая-6;"+k1+" уличная-7"+k2+";транспортная-8; в т.ч. ДТП-9; школьная-10; спортивная-11; противоправная травма-12; прочие-13.") ;
		 }
		if(aMedCase.Diagnosis.traumaType.omcCode.equals("8")){
			map.put(aKey,"промышленная-1; транспортная-2; в т.ч. ДТП-3; с/хоз.-4; прочие-5;непроизводственная: бытовая-6; уличная-7;"+k1+"транспортная-8;"+k2+" в т.ч. ДТП-9; школьная-10; спортивная-11; противоправная травма-12; прочие-13.") ;
		 }
		if(aMedCase.Diagnosis.traumaType.omcCode.equals("9")){
			map.put(aKey,"промышленная-1; транспортная-2; в т.ч. ДТП-3; с/хоз.-4; прочие-5;непроизводственная: бытовая-6; уличная-7;транспортная-8; в т.ч."+k1+" ДТП-9"+k2+"; школьная-10; спортивная-11; противоправная травма-12; прочие-13.") ;
		 }
		if(aMedCase.Diagnosis.traumaType.omcCode.equals("10")){
			map.put(aKey,"промышленная-1; транспортная-2; в т.ч. ДТП-3; с/хоз.-4; прочие-5;непроизводственная: бытовая-6; уличная-7;транспортная-8; в т.ч. ДТП-9; "+k1+"школьная-10"+k2+"; спортивная-11; противоправная травма-12; прочие-13.") ;
		 }
		if(aMedCase.Diagnosis.traumaType.omcCode.equals("11")){
			map.put(aKey,"промышленная-1; транспортная-2; в т.ч. ДТП-3; с/хоз.-4; прочие-5;непроизводственная: бытовая-6; уличная-7;транспортная-8; в т.ч. ДТП-9; школьная-10; "+k1+"спортивная-11"+k2+"; противоправная травма-12; прочие-13.") ;
		 }
		if(aMedCase.Diagnosis.traumaType.omcCode.equals("12")){
			map.put(aKey,"промышленная-1; транспортная-2; в т.ч. ДТП-3; с/хоз.-4; прочие-5;непроизводственная: бытовая-6; уличная-7;транспортная-8; в т.ч. ДТП-9; школьная-10; спортивная-11; "+k1+"противоправная травма-12"+k2+"; прочие-13.") ;
		 }
		if(aMedCase.Diagnosis.traumaType.omcCode.equals("13")){
			map.put(aKey,"промышленная-1; транспортная-2; в т.ч. ДТП-3; с/хоз.-4; прочие-5;непроизводственная: бытовая-6; уличная-7;транспортная-8; в т.ч. ДТП-9; школьная-10; спортивная-11; противоправная травма-12; "+k1+"прочие-13"+k2+".") ;
		 }
	}
	else map.put(aKey,"промышленная-1; транспортная-2; в т.ч. ДТП-3; с/хоз.-4; прочие-5;непроизводственная: бытовая-6; уличная-7;транспортная-8; в т.ч. ДТП-9; школьная-10; спортивная-11; противоправная травма-12; прочие-13.") ;
}


function recordVocProba(aKey, aValue, aMin, aMax) {
	for (i=aMin;i<=aMax;i++) {
		map.put(aKey+i+".k1","");
		map.put(aKey+i+".k2","");
	}
	if (aValue!=null) {
		var ind= aValue.id ;
		map.put(aKey+ind+".k1","<text:span text:style-name=\"T14\">");
		map.put(aKey+ind+".k2","</text:span>");
	}
} 

function recordMultiText(aKey, aValue) {
	var ret = new java.lang.StringBuilder () ;
	var val = aValue!=null?"" +aValue:"" ;
	var n = /\n/ ;
	var items = val.split(n);
	//ret.append("</text:p>") ;
	for (var i = 0; i < items.length; i++) {
		//ret.append("<text:p text:style-name=\"P6\">") ;
		//ret.append("<text:tab/>") ;
		ret.append(items[i]);
		if (i < items.length-1) ret.append("<text:line-break/>") ;
	}
	//ret.append("<text:p>") ;
	map.put(aKey,ret.toString()) ;
}

function recordMultiValue(aValue) {
	var ret = new java.lang.StringBuilder () ;
	var val = aValue!=null?"" +aValue:"" ;
	var n = /\n/ ;
	var items = val.split(n);
	//ret.append("</text:p>") ;
	for (var i = 0; i < items.length; i++) {
		//ret.append("<text:p>") ;
		//ret.append("<text:tab/>") ;
		ret.append(items[i]);
		ret.append("<text:line-break/>") ;
	}
	//ret.append("<text:p>") ;
	return ret.toString() ;
}
function recordBoolean(aKey) {

		map.put(aKey+".k1","<text:span text:style-name=\"T23\">") ;
		map.put(aKey+".k2","</text:span>");

}