var map = new java.util.HashMap() ;
function checkAllDiagnosis (aCtx, aSlsId) {
	if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/DontPrintWithoutDiagnosisInSlo")){
		var sql = "select sls.id,slo.id from medcase sls "
		 +" left join medcase slo on sls.id=slo.parent_id and slo.dtype='DepartmentMedCase'"
		 +" left join mislpu ml on ml.id=slo.department_id"
		 +" left join diagnosis d on slo.id = d.medcase_id"
		 +" left join vocdiagnosisregistrationtype vdrt on vdrt.id=d.registrationtype_id"
		 +" where sls.id='"+aSlsId+"' and (ml.isnoomc is null or ml.isnoomc='0') "
		 +" group by sls.id,slo.id	"
		 +" having count(case when (vdrt.code='3' or vdrt.code='4') and d.idc10_id is not null then 1 else null end)=0  "
		var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
		if (list.size()>0) {throw "Не полностью заполнены данные по диагнозам в отделениях!!!" ;}	
	}
}
function printStatCards(aCtx, aParams) {
	
	var ids1 = aParams.get("id") ;
	var ids = ids1.split(",") ;
	//infoSmo(aCtx,ids[0]) ;
	infoPrint(aCtx,ids[0]) ;

	var ret = new java.util.ArrayList() ;
	for (var i=0; i < ids.length; i++) {
		var id1=ids[i] ;
		var indlast = id1.lastIndexOf("!") ;
		var id = id1.substring(indlast+1) ;
		//throw indlast+"--"+id1 +"----"+id;
		
		checkAllDiagnosis (aCtx, id) ;
		var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase
				, new java.lang.Long(id)) ;
		//medCase.setPrintDate(curDate) ;
		//medCase.setPrintTime(curTime) ;
		//aCtx.manager.persist(medCase) ;
		var wqr = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		wqr.set1(recordPolicy(aCtx.manager.createQuery("from MedCaseMedPolicy where medCase=:mc").setParameter("mc", medCase).getResultList())) ;
		wqr.set2(recordPatient(medCase,aCtx)) ;
		wqr.set3(recordMedCaseDefaultInfo(medCase,aCtx)) ;
		wqr.set4(recordAttendant(medCase,aCtx)) ;
		
		var depDirect = "" ;
		var hospType = medCase.hospType ;
		var daysCount = Packages.ru.ecom.ejb.util.DurationUtil.getDurationMedCase(medCase.getDateStart(), medCase.getDateFinish(),0) ;
		if (hospType!=null) {
			if (hospType.code=="DAYTIMEHOSP") {
				depDirect = "(дневное)" ;
				daysCount = Packages.ru.ecom.ejb.util.DurationUtil.getDurationMedCase(medCase.getDateStart(), medCase.getDateFinish(),0,1) ;
			}
		}
		
		wqr.set5(medCase.department) ;//"sls.departmentDirection",
		wqr.set6(depDirect) ;//"sls.typeDirect",
		//wqr.set7(medCase.outcome) ;//"sls.outcome",
		//wqr.set8(medCase.result) ;//"sls.result",
		
		wqr.set9(daysCount) ;//"sls.daysCount", 
		
		//7. Доставлен по экстренным показания
		wqr.set10(medCase.emergency!=null && medCase.emergency==true ? "экстренные" : "планово") ;
		// через_________часов после начала заболевания, получения травмы; госпитализирован в плановом порядке
		wqr.set11(medCase.preAdmissionTime) ;//"sls.preAdmissionTime",
		//10. Диагноз клинический
		wqr.set12(getDiagnos(medCase.diagnosClinical)) ;//"sls.diagnosisClinical",
		
		wqr.set13(recordSloBySls(aCtx,medCase.id)) ;//,"listSlo"
		wqr.set14(recordSurgicalOperationBySls(aCtx,medCase.id)) ;//,"listOper"
		ret.add(wqr) ;
		
	}
	map.put("statCards",ret) ;
	return map ;
}
function recordSurgicalOperationBySls(aCtx,aSlsId) {
	var sql="select "
		+" so.id as soid0,case when so.base='1' then 'осн' else '' end as sobase1,case" 
		+" when so.operationdate=so.operationdateto or so.operationdateto is null" 
		+" 	then to_char(so.operationDate,'dd.mm.yyyy')||' '||coalesce(cast(so.operationTime as varchar(5)),'___:___')||' '||case when so.operationTimeTo is null then '' else '-'||coalesce(cast(so.operationTimeTo as varchar(5)),'___:___') end"
		+" else to_char(so.operationDate,'dd.mm.yyyy')||' '||coalesce(cast(so.operationTime as varchar(5)),'___:___')||'-'||to_char(so.operationDateTo,'dd.mm.yyyy')||' '||case when so.operationTimeTo is null then '' else ' '||coalesce(cast(so.operationTimeTo as varchar(5)),'___:___') end"
		+" end as operdate2"
		+" ,case when owf.code!=null and owf.code!='' then owf.code else case when ovwf.code!=null and ovwf.code!='' then ovwf.code else '' end||' '||owp.lastname||' '||substring(owp.firstname,1,1)||substring(owp.middlename,1,1) end as surgeon3"
		+" ,dep.id as depid4,dep.name as depname5,coalesce(vo.code,ms.code) as vocode6,coalesce(vo.name,ms.name) as voname7"
		+" ,list(vc.name) as vcname8,list(vc.code) as vccode9,list(vam.code) as vacode10"
		+" ,case when so.endoscopyuse='1' then 'X' else '' end as endoscopyuse11"
		+" ,case when so.laseruse='1' then 'X' else '' end as laseruse12"
		+" ,case when so.cryogenicuse='1' then 'X' else '' end as cryogenicuse13"
		+" ,vss.name as vssname14"
		+" from SurgicalOperation so"
		+" left join MedCase mc1 on mc1.id=so.medCase_id"
		+" left join WorkFunction owf on owf.id=so.surgeon_id"
		+" left join Worker ow on ow.id=owf.worker_id"
		+" left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id"
		+" left join Patient owp on owp.id=ow.person_id"
		+" left join MisLpu dep on dep.id=so.department_id"
		+" left join VocOperation vo on vo.id=so.operation_id"
		+" left join MedService ms on ms.id=so.medService_id"
		+" left join SurgComplication sc on so.id=sc.surgicaloperation_id"
		+" left join VocComplication vc on vc.id=sc.complication_id"
		+" left join Anesthesia an on so.id = an.surgicaloperation_id"
		+" left join VocAnesthesiaMethod vam on vam.id=an.type_id"
		+" left join VocServiceStream vss on vss.id=so.serviceStream_id"
		+" where (mc1.id='"+aSlsId+"' or mc1.parent_id='"+aSlsId+"')"
		+" group by so.id,so.operationdate,so.operationdateto"
		+" ,so.operationtime,so.operationtimeto"
		+" ,owf.code,ovwf.code,owp.lastname,owp.firstname,owp.middlename,dep.id,dep.name,vo.code,vo.name"
		+" ,so.base,so.endoscopyuse, so.laseruse, so.cryogenicuse,vss.name,ms.code,ms.name" ;
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	var ret = new java.util.ArrayList() ;
	
	if (!list.isEmpty()) {
		for (var i=0;i<list.size();i++) {
			var wq = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			var obj=list.get(i) ;
			wq.setSn(i+1) ;
			for (var j=1;j<15;j++) {
				eval("wq.set"+j+"(obj["+(j)+"])") ;
			}
			ret.add(wq) ;
		}
	} else {
		for (var i=0;i<5;i++) {
			ret.add(new Packages.ru.ecom.ejb.services.query.WebQueryResult()) ;
		}
	}
	return ret ;

}
function recordSloBySls(aCtx,aSlsId) {
	var sql = "select slo.id as sloid0,dep.id as depid1,dep.name as depname2,vbt.name as vbtname3"
		+" ,coalesce(owf.code"
		+" ,ovwf.code||' '||owp.lastname||' '||substring(owp.firstname,1,1)||substring(owp.middlename,1,1)"
		+" ,owp.lastname||' '||substring(owp.firstname,1,1)||substring(owp.middlename,1,1)) as owner4"
		+" ,to_char(slo.dateStart,'dd.mm.yyyy') as startdate5,cast(slo.entranceTime as varchar(5)) as entrancetime6"
		+" ,coalesce(to_char(slo.dateFinish,'dd.mm.yyyy'),to_char(slo.transferDate,'dd.mm.yyyy')) as dischargedate7"
		+" ,coalesce(cast(slo.dischargeTime as varchar(5)),cast(slo.transferTime as varchar(5))) as dischargetime8"
		+" ,os.model as osmodel9"
		+" ,list(case when vdrt.code='4' and vpd.code='1'  then mkb.code else null end) as diagMain10"
		+" ,list(case when vdrt.code='4' and vpd.code='3' then mkb.code else null end) as diagSoput11" 
		+" ,list(case when vdrt.code='4' and vpd.code='4' then mkb.code else null end) as diagOsl12"
		+" ,vss.name as vssname13"
		+" from MedCase slo" 
		+" left join MisLpu dep on dep.id=slo.department_id"
		+" left join BedFund bf on bf.id=slo.bedFund_id"
		+" left join VocBedType vbt on vbt.id=bf.bedType_id"
		+" left join WorkFunction owf on owf.id=slo.ownerFunction_id"
		+" left join Worker ow on ow.id=owf.worker_id"
		+" left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id"
		+" left join VocAcademicDegree ovwfd on ovwfd.id=owf.degrees_id"
		+" left join Patient owp on owp.id=ow.person_id"
		+" left join Omc_Standart os on os.id=slo.omcStandart_id"
		+" left join VocServiceStream vss on vss.id=slo.serviceStream_id"
		+" left join Diagnosis diag on diag.medCase_id=slo.id"
		+" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id"
		+" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
		+" left join VocIdc10 mkb on mkb.id=diag.idc10_id"
		+" where slo.parent_id='"+aSlsId+"' and slo.dtype='DepartmentMedCase'"
		+" group by slo.id,slo.dateStart,slo.entranceTime,slo.dateFinish,slo.transferDate,slo.dischargeTime"
		+" ,slo.transferTime,os.model,vss.name,dep.id,dep.name,vbt.name"
		+" ,owf.code,ovwf.code"
		+" ,ovwf.name,ovwfd.name,owp.lastname,owp.firstname,owp.middlename"
		+" order by slo.dateStart,slo.entranceTime" ;
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	var ret = new java.util.ArrayList() ;
	
	if (list.size()>0) {
		for (var i=0;i<list.size();i++) {
			var wq = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			var obj=list.get(i) ;
			wq.setSn(i+1) ;
			for (var j=1;j<14;j++) {
				eval("wq.set"+j+"(obj["+(j)+"])") ;
			}
			ret.add(wq) ;
		}
	} else {
		for (var i=0;i<6;i++) {
			var parDep = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			ret.add(parDep) ;
		}
	}
	return ret ;
}

function recordAttendant(medCase,aCtx) {
	var wqr = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	if (medCase.hotelServices!=null && medCase.hotelServices) {
		var kinsman = medCase.kinsman!=null?medCase.kinsman.kinsman:null ;
		if (kinsman!=null) {
			wqr.set1(getAge(kinsman.birthday,medCase.dateStart,aCtx.manager)) ; //"kinsman.age",
			//map.put("kinsman.age",) ;
			wqr.set2(kinsman.sex!=null?kinsman.sex.name:"муж-1, жен-2") ; //"kinsman.sex",
		} else {
			wqr.set1("_________") ; //"kinsman.age",
			wqr.set2("муж-1, жен-2") ;//"kinsman.sex",
		}
	} else {
		wqr.set1("_________") ; //"kinsman.age",
		wqr.set2("муж-1, жен-2") ;//"kinsman.sex",
	}
	
}
function recordPolicy(policies) {
	var rec="" ;
	for(var i=0; i<policies.size(); i++) {
		var casepol = policies.get(i);
		if (casepol!=null && casepol.policies!=null) {
			if (!rec.equals("")) rec = rec + "; " ;
			rec = rec+casepol.policies.text ;
		}
	}
	if (rec.equals("")) return "Полиса НЕТ";
	return rec;
}
function getMedServiceNameByProtocol (aCtx, aProtocolId) {
	var sql ="select mc.code||' '||mc.name "
		+" from diary d" +
		" left join medcase servmc on servmc.id=d.servicemedcase_id" +
		" left join medservice mc on mc.id=servmc.medservice_id" +
        " left join VocServiceType vst on vst.id=mc.serviceType_id" +
		" where d.id='"+aProtocolId+"' and (vst.code='DIAGNOSTIC' or vst.code='SERVICE' or vst.code='LABSURVEY') ";
	var res =aCtx.manager.createNativeQuery(sql).getResultList();
    var ret  ="";
	if (!res.isEmpty()) {
		for (var i=0;i<res.size();i++) {
			ret+=""+res.get(i)+"\n";
		}
	}
	return ret;
}

function printProtocols(aCtx, aParams) {
	var ids1 = aParams.get("id") ;
	var ids = ids1.split(",") ;
	infoPrint(aCtx,ids[0]) ;

	var ret = new java.util.ArrayList() ;
    var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
    var FORMAT_3 = new java.text.SimpleDateFormat("HH:mm") ;
	var current = new java.util.Date() ;
	var curDate = new java.sql.Date(current.getTime()) ;
	
	var curTime = new java.sql.Time(current.getTime()) ;
	for (var i=0; i < ids.length; i++) {
		var id1=ids[i] ;
		var indlast = id1.lastIndexOf("!") ;
		var id = id1.substring(indlast+1) ;
		
		var protocol = aCtx.manager.find(Packages.ru.ecom.poly.ejb.domain.protocol.Protocol
		, new java.lang.Long(id)) ;
		protocol.setPrintDate(curDate) ;
		protocol.setPrintTime(curTime) ;
		
		var mapS= new Packages.ru.ecom.mis.ejb.form.medcase.VisitProtocolForm()  ;
		mapS.timeRegistration=FORMAT_3.format(protocol.timeRegistration);
		mapS.dateRegistration=FORMAT_2.format(protocol.dateRegistration);
		mapS.specialistInfo=protocol.specialistInfo ;
		mapS.setTicket(null) ;
		mapS.setTypeInfo("") ;
		mapS.setTitle(protocol.title!=null ? recordMultiValue(protocol.title) : "");
		var protType=protocol.type ;
		if (protType!=null) {
			mapS.typeInfo=protType.name ;
			mapS.setTicket(protType.isPrintAdministrator==true ? java.lang.Long.valueOf(0) : null) ;
		} else {
			//стоит ли делать : если в визите были услуги - отображать их списком ?
		}
		mapS.setInfo(protocol.medCase!=null?protocol.medCase.info:"");
		mapS.setRecord(recordMultiValue(getMedServiceNameByProtocol(aCtx, id)+protocol.record));
		mapS.setEditUsername(protocol.state!=null?protocol.state.name:null) ;
		ret.add(mapS) ;
		
	}
	map.put("protocols",ret) ;
	
	return map ;
}
function printBillings(aCtx, aParams) {
	var ids1 = aParams.get("id") ;
	var ids = ids1.split(",") ;
	infoPrint(aCtx,ids[0]) ;
	
	//текущая дата
	var ret = new java.util.ArrayList() ;
    var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	var current = new java.util.Date() ;
	var curDate = new java.sql.Date(current.getTime()) ;
	var curTime = new java.sql.Time(current.getTime()) ;
	map.put("currentDate",FORMAT_2.format(curDate)) ;
	
	for (var i=0; i < ids.length; i++) {
		var id1=ids[i] ;
		var indlast = id1.lastIndexOf("!") ;
		var id = id1.substring(indlast+1) ;

		var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase
		, new java.lang.Long(id)) ;
		medCase.setPrintDate(curDate) ;
		medCase.setPrintTime(curTime) ;
		var mapS= new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		//Свединия по пациенту	
		mapS.set1(recordPatient(medCase,aCtx)) ;
		//Диагнозы
		mapS.set2(recordMedCaseDefaultInfo(medCase,aCtx)) ;
		mapS.set3(((medCase.emergency!=null && medCase.emergency) ? "в экстренном порядке":"в плановом порядке")) ;//"sls.emergency", 
		//5. Даты: поступления, выбытия
		mapS.set4(medCase.dateStart) ; //"sls.Start",
		mapS.set5(medCase.dateFinish) ;//"sls.Finish",
		//выписной диагноз
		mapS.set6(getDiagnos(medCase.diagnosConcluding)); //"sls.diagnosConcluding",
		mapS.set7(recordMultiText(Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.HospitalMedCaseViewInterceptor.getDischargeEpicrisis(medCase.getId(), aCtx.manager))); //"sls.dischargeRecord",
		var ret2 = new java.util.ArrayList() ;
		var ret1 = new java.util.ArrayList() ;
		var retwq = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		ret1.add(retwq) ;
		if (medCase.result!=null&& (medCase.result.code=="11" || medCase.result.code=="15")) {
			mapS.set8(ret2) ;
			mapS.set9(ret1) ;
		} else {
			mapS.set8(ret1) ; //death
			mapS.set9(ret2) ; //normal
			
		}
		ret.add(mapS) ;
		
	}
	map.put("discharge",ret) ;
	
	return map ;
}
function recordMultiText(aValue) {
	var val = aValue!=null ? "" +aValue : "" ;
	var n = /\n/ ;
	var items = val.split(n);
	var list = new java.util.ArrayList() ;
	for (var i = 0; i < items.length; i++) {
		var prot = Packages.ru.ecom.poly.ejb.form.protocol.ProtocolForm() ;
		prot.setRecord(items[i]);
		list.add(prot);
	}
	return list;
}
function recordMultiValue(aValue) {
	var ret = new java.lang.StringBuilder () ;
	var val = aValue!=null?"" +aValue:"" ;
	var n = /\n/ ;
	var items = val.split(n);
	for (var i = 0; i < items.length; i++) {
		ret.append(items[i]);
		ret.append("<text:line-break/>") ;
	}
	return ret.toString() ;
}
function recordPatient(medCase,aCtx) {
	var wqr = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	var patient = medCase.patient
	//1. Фамилия имя отчество 
	wqr.set1(patient) ; //"patient"
	wqr.set2(patient.lastname) ; //"pat.lastname",
	wqr.set3(patient.firstname) ; //"pat.firstname",
	wqr.set4(patient.middlename) ; //"pat.middlename",
	//3. Дата рождения
	wqr.set5(patient.birthday); //"pat.birthday",
	//2. Пол
	wqr.set6(patient.sex) ; //"pat.sex",
	wqr.set7(patient.rayon) ; //"pat.rayon",
	//3. Возраст (полных лет, для детей: до 1 года - месяцев, до 1 месяца - дней)
	wqr.set8(getAge(patient.birthday,medCase.dateStart,aCtx.manager)); //"pat.age",
	//4. Постоянное место жительства: город, село и адрес
	wqr.set9(getAddress(patient.address,patient)) ;//"pat.address",
	wqr.set10(patient.addressReal!=null?patient.addressReal:"") ; //"pat.addressReal",
	wqr.set11(patient.addressRegistration) ; //"pat.addressReg",
	//Дом, корпус
	wqr.set12(patient.addressHouse) ; //"pat.addressHouse",
	// Квартира
	wqr.set13(patient.flatNumber) ; //"pat.flatNumber",
	//5. Место работы, профессия или должность
	wqr.set14(patient.works!=null?patient.works:""); //"pat.works",
	var workDiv = "" ;
	if (medCase.patient.works!=null && !medCase.patient.works.equals("")&& medCase.patient.workPost!=null && !medCase.patient.workPost.equals("")) workDiv="," ;
	wqr.set15(workDiv) ; //"pat.workDiv",
	wqr.set16(medCase.patient.workPost) ; //"pat.wPost",
	//Документ, удостоверяющий личность
	wqr.set17(patient.passportType); //"pat.identityCard",

	var cardInfo = patient.passportType!=null?patient.passportType.name:"" ;
	cardInfo=cardInfo+" "+(patient.passportSeries!=null?patient.passportSeries:"") ;
	cardInfo=cardInfo+" "+(patient.passportNumber!=null&&patient.passportNumber!=""?"№"+patient.passportNumber:"") ;
	if (patient.passportDateIssued!=null) {
		var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
		cardInfo=cardInfo+" выдан "+FORMAT_2.format(patient.passportDateIssued) ;
		
	}
	cardInfo=cardInfo+" "+(patient.passportWhomIssued!=null?""+patient.passportWhomIssued:"") ;
	wqr.set18(cardInfo); //"pat.identityCardInfo",
	//Социальный статус
	wqr.set19(patient.socialStatus) ; //"pat.socialStatus",
	
	//Образование
	wqr.set20(patient.educationType) ; //"pat.educationType",
	//Национальность
	wqr.set21(patient.ethnicity) ; //"pat.ethnicity",
	//12 СНИЛС
	if (patient.snils==("")) {
		wqr.set22("______-______-______ ___") ; //"pat.snils",
	} else {
		wqr.set22(patient.snils); //"pat.snils",
	}
	return wqr ;
}
function recordMedCaseDefaultInfo(medCase,aCtx) {
	var wqr = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	wqr.set1(medCase) ; //"medCase",
	wqr.set2(medCase.lpu) ;//"sls.lpu", 
	wqr.set3(medCase.mainLpuInfo) ;//"lpu.name",
	wqr.set4( medCase.statCardNumber) ;//"sls.statCardNumber",
	wqr.set5(medCase.dateStart) ;//"sls.admissionDate", 
	wqr.set6(medCase.entranceTime) ;//"sls.admissionTime", 
	//дата выписки
	wqr.set7(medCase.dateFinish) ;//"sls.dischargeDate", 
	//время выписки
	wqr.set8(medCase.dischargeTime) ;//"sls.dischargeTime", 
	//6. Кем направлен больной
	//wqr.set9(medCase.orderLpu) ;//"sls.whosRefer",

	wqr.set10(medCase.hospitalization) ;//"sls.hospitalization",
	wqr.set11(medCase.kinsman!=null?medCase.kinsman.info:"") ;//"sls.kinsInfo",
	var otds =aCtx.manager.createNativeQuery("select d.name as depname,to_char(dmc.dateStart,'DD.MM.YYYY') as dateStart,COALESCE(to_char(dmc.dateFinish,'DD.MM.YYYY'),to_char(dmc.transferDate,'DD.MM.YYYY'),'____.____.______г.') as dateFinish"
			+", coalesce(vwfd.code||' ','')||' '||vwf.name||' '||p.lastname||' '|| p.firstname ||' '||p.middlename  as worker"
			+", d.name as dname,d.id as did"
			+",  coalesce(wf.code,'') as worker"
			+", case when d.IsNoOmc='1' then d.IsNoOmc else null end as IsNoOmc"
			+" from MedCase dmc "
			+" left join MisLpu d on d.id=dmc.department_id "
			+" left join WorkFunction wf on wf.id=dmc.ownerFunction_id "
			+" left join VocWorkFunction vwf on wf.workFunction_id=vwf.id "
			+" left join VocAcademicDegree vwfd on wf.degrees_id=vwfd.id "
			+" left join Worker w on w.id=wf.worker_id "
			+" left join Patient p on p.id=w.person_id "
			+" where dmc.parent_id='"+medCase.id+"' and dmc.DTYPE='DepartmentMedCase' order by dmc.dateStart,dmc.entranceTime ").getResultList();
		var otd = "";
		var lech = "" ;
		var lechCode = "" ;
		var lastotd = "";
		var lastotdId="" ;
		var result = medCase.result;
		for (var i=0; i<otds.size(); i++) {
			var dep = otds.get([i]) ;
			if (otd!="") {otd += ", ";}
			otd += dep[0] +" "+" c "+dep[1]+" по "+dep[2] ;
			lech += dep[3] ;
			
			if (result!=null && (result.code=="11" || result.code=="15")) {
				if (dep[7]==null) {
					lech = dep[3] ;
					lastotd = dep[4];
					lastotdId = dep[5] ;
					lechCode=dep[6] ;
				}
			} else{
				lech = dep[3] ;
				lastotd = dep[4];
				lastotdId = dep[5] ;
				lechCode=dep[6] ;
			}
		}
		wqr.set12(otd) ;//"sls.departments",
		wqr.set13(lech) ;//"sls.owner",
		wqr.set14(lechCode) ;//"sls.ownercode",
		wqr.set15(lastotd) ;//"sls.lastOtd",
		wqr.set16(recordZavOtd(aCtx,lastotdId,"dep.zav")) ;//
		var slsId = medCase.id ;
		//8. Диагноз направившего учреждения
		//wqr.set17(getDiagnos(medCase.diagnosOrder)) ;//"sls.diagnosisOrder",
		wqr.set17(recordDiagnosis(aCtx,slsId,"2","0","diagnosis.order.main")) ;
		//9. Диагноз при поступлении
		//wqr.set18(getDiagnos(medCase.diagnosEntrance)) ;//"sls.diagnosisAdmission",
		
		wqr.set18(recordDiagnosis(aCtx,slsId,"1","1","diagnosis.admission.main")) ;
		wqr.set19(recordDiagnosis(aCtx,slsId,"3","1","diagnosis.clinic.main")) ;
		wqr.set20(recordDiagnosis(aCtx,slsId,"3","3","diagnosis.clinic.related")) ;
		wqr.set21(recordDiagnosis(aCtx,slsId,"3","4","diagnosis.clinic.complication")) ;
		wqr.set22(recordDiagnosis(aCtx,slsId,"5","1","diagnosis.postmortem.main")) ;
		wqr.set23(recordDiagnosis(aCtx,slsId,"5","3","diagnosis.postmortem.related")) ;
		wqr.set24(recordDiagnosis(aCtx,slsId,"5","4","diagnosis.postmortem.complication")) ;
		wqr.set25(recordDisability(aCtx,slsId,"dis")) ;
		wqr.set26(medCase.orderDate!=null?medCase.orderDate:"") ;
		var dc =aCtx.manager.createQuery("from DeathCase where medCase_id="+medCase.id).getResultList() ;
		if (dc.size()>0) {
			wqr.set27(dc.get(0)) ;
		}
		return wqr ;
}
function recordDisability(aContext,aSlsId,aField) {
	var wqr = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	var sql="select dc.id,dd.id,dd.number,to_char(min(dr.dateFrom),'dd.mm.yyyy') as dateFrom,to_char(max(dr.dateTo),'dd.mm.yyyy') as dateTo,vddcr.name as vddcrname"
		+",vddt.name as vddtname"
		+" from DisabilityCase dc"
		+" 	left join Patient pat on pat.id=dc.patient_id"
		+" 	left join MedCase sls on sls.patient_id=pat.id"
		+" 	left join DisabilityDocument dd on dd.disabilityCase_id=dc.id"
		+" 	left join DisabilityRecord dr on dr.disabilityDocument_id=dd.id"
		+" 	left join VocDisabilityDocumentCloseReason vddcr on dd.closeReason_id=vddcr.id"
		+" 	left join VocDisabilityDocumentType vddt on dd.documentType_id=vddt.id"
		+" 	where sls.id='"+aSlsId+"' and dd.anotherlpu_id is null"
		+" 	group by dc.id,dd.id,sls.dateStart,sls.dateFinish,dd.number,vddcr.name,dd.issueDate,vddt.name"
		+" 	having min(dr.dateFrom) between sls.dateStart and coalesce(sls.dateFinish,current_date)"
		+"  order by dd.issueDate"
	var list = aContext.manager.createNativeQuery(sql).getResultList() ;
	if (list.size()>0) {
		var obj=list.get(0) ;
		wqr.set1("№"+obj[2]+" открыт с "+obj[3]+" по "+obj[4]+". Причина закрытия: "+obj[5]);//aField+".info",
		wqr.set2(null);//aField+".age",
		wqr.set3(null);//aField+".sex",
		wqr.set4(obj[6]);
		var ddinfo="" ;
		for (var i=0;i<list.size();i++){
			var obj1=list.get(i) ;
			ddinfo=ddinfo+obj1[6]+" №"+obj1[2]+" открыт с "+obj1[3]+" по "+obj1[4]+". Причина закрытия: "+obj1[5];//aField+".info",
			if (i+1<list.size()) ddinfo=ddinfo+", ";
		}
		wqr.set5(ddinfo);
	} 
	return wqr ;
}
function recordZavOtd(aCtx,aLastOtdId,aField) {
	var wqr = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	if (+aLastOtdId>0) {
		var sql = "select p.lastname||' '||p.firstname||' '||p.middlename,wf.id,p.lastname " 
			+" from workfunction wf " 
			+" left join worker w on w.id=wf.worker_id"
			+" left join patient p on p.id=w.person_id"
			+" where w.lpu_id='"+aLastOtdId+"' and wf.isAdministrator='1'" ;
		var list = aCtx.manager.createNativeQuery(sql).setMaxResults(1).getResultList() ;
		if (list.size()>0) {
			var obj=list.get(0) ;
			wqr.set1(obj[0]);//aField+".info",
			wqr.set2(obj[2]);//aField+".lastname"
		} 
	} 
	return wqr ;
}
// получить возраст (полных лет, для детей: до 1 года - месяцев, до 1 месяца - дней)
function getAge(aBirthday,aDate,aManager,aType) {
	if (aType==null) aType=3 ;
	if (aDate!=null && aBirthday!=null) {
		return Packages.ru.nuzmsh.util.date.AgeUtil.getAgeCache(aDate,aBirthday,aType) ;	
	} 
	return "" ;
}
function getAddress(aAddress,aPat) {
	var wqr = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	if (aAddress!=null) {
		if (aAddress.addressIsCity!=null && aAddress.addressIsCity==true) {
			wqr.set1("город") ;//aKey+".CityOrVillage",
		} else {
			if (aAddress.addressIsVillage!=null && aAddress.addressIsVillage==true) {
				wqr.set1("село") ;//aKey+".CityOrVillage",
			} else {
				wqr.set1("город, село (нужное подчеркнуть)") ;//aKey+".CityOrVillage",
			}
		}
		//map.put(aKey+".info",aAddress.getAddressInfo(aPat.houseNumber, aPat.houseBuilding, aPat.flatNumber)) ;
	}else{
		wqr.set1("город, село (нужное подчеркнуть)") ;//aKey+".CityOrVillage",
	}
	wqr.set2(aPat.getAddressRegistration()) ;//aKey+".info",
	wqr.set3(aPat.getAddressReal()) ;//aKey+".real",
	return wqr ;
}
function getDiagnos(aDiag) {
	var wqr = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	if (aDiag!=null) {
		wqr.set2(aDiag.name) ;//aKey+".text",
		wqr.set1("") ;//aKey+".mkb",
		if (aDiag.idc10!=null) wqr.set1("("+aDiag.idc10.code+")");//aKey+".mkb",
	}
	return wqr ;
}
function printSurOperations(aCtx,aParams) {
	
	var ids1 = aParams.get("id") ;
	var ids = ids1.split(",") ;
	infoPrint(aCtx,ids[0]) ;
	var ret = new java.util.ArrayList() ;
	var current = new java.util.Date() ;
	var curDate = new java.sql.Date(current.getTime()) ;
	var curTime = new java.sql.Time(current.getTime()) ;
	var username = aCtx.sessionContext.callerPrincipal.name ;
	for (var i=0; i < ids.length; i++) {
		var id1=ids[i] ;
		var indlast = id1.lastIndexOf("!") ;
		var id = id1.substring(indlast+1) ;
		var surOper = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.SurgicalOperation
		, new java.lang.Long(id)) ;
		var medCase = surOper.medCase ;
		surOper.setPrintDate(curDate) ;
		surOper.setPrintTime(curTime) ;
		surOper.setPrintUsername(username) ;
		var mapSO = new Packages.ru.ecom.ejb.services.query.WebQueryResult() ;
		var list=aCtx.manager.createNativeQuery("select list(' '||avwf.name||' '||awp.lastname||' '||awp.firstname||' '||awp.middlename) as anes,list(' '||vam.name||' (кол-во '||a.duration||')') as methodan from Anesthesia a"
				+" left join WorkFunction awf on awf.id=a.anesthesist_id"
				+" left join Worker aw on aw.id=awf.worker_id"
				+" left join Patient awp on awp.id=aw.person_id"
				+" left join VocWorkFunction avwf on avwf.id=awf.workFunction_id"
				+" left join VocAnesthesiaMethod vam on vam.id=a.method_id"
				+" where a.surgicalOperation_id=:id group by a.surgicalOperation_id"
			) .setParameter("id",surOper.id).setMaxResults(1).getResultList() ;
		var list1=aCtx.manager.createNativeQuery("select "
			+" coalesce(ss1.code,ss.code) as ss1,ss.code as ss2"
				+" from MedCase d"
				+" left join MedCase h on h.id=d.parent_id and h.dtype='HospitalMedCase'"
				+" left join StatisticStub ss on ss.id=h.statisticStub_id"
				+" left join StatisticStub ss1 on ss1.id=d.statisticStub_id"
				+" where d.id=:id"
			).setParameter("id",medCase.id).setMaxResults(1).getResultList() ;
		var list2=aCtx.manager.createNativeQuery("select list(' '||avwf.name||' '||awp.lastname||' '||awp.firstname||' '||awp.middlename) as asist from SurgicalOperation_WorkFunction sowf"
				+" left join WorkFunction awf on awf.id=sowf.surgeonfunctions_id"
				+" left join Worker aw on aw.id=awf.worker_id"
				+" left join Patient awp on awp.id=aw.person_id"
				+" left join VocWorkFunction avwf on avwf.id=awf.workFunction_id"
				+" where sowf.surgicalOperation_id=:id group by sowf.surgicalOperation_id"
			) .setParameter("id",surOper.id).setMaxResults(1).getResultList() ;
		var asist=list2.size()>0?list2.get(0):"" ;
		var anes = list.size()>0?list.get(0):null ;
		mapSO.set1(surOper.id);
		mapSO.set2(surOper.operationDate);
		mapSO.set3(surOper.operationTime);
		mapSO.set4(asist);
		mapSO.set5(surOper.operatingNurse!=null?surOper.operatingNurse.workFunctionInfo:"");
		mapSO.set6(anes!=null?(anes[0]!=null?anes[0]:""):"") ;
		mapSO.set7(anes!=null?(anes[1]!=null?anes[1]:""):"") ;
		var card1="" ;
		if (list1.size()>0) {
			var card = list1.get(0) ;
				
			if (card[0]!=null) {card1=card[0];} else {
				if (card[1]!=null) card1=card[1] ;
			}
		}
		mapSO.set8(card1) ;
		mapSO.set9(surOper.surgeonInfo) ;
		mapSO.set10(recordMultiValue(surOper.operationText));
		mapSO.set11(medCase.patient!=null?medCase.patient.fio:"") ; 
		mapSO.set12(surOper.numberInJournal!=null?surOper.numberInJournal:"") ; 
		mapSO.set13(surOper.idc10BeforeInfo) ;
		mapSO.set14(surOper.idc10AfterInfo) ;
		mapSO.set15(surOper.medService) ;
		mapSO.set16(surOper.outcome!=null?surOper.outcome.name:"") ;
		ret.add(mapSO) ;
	}
	map.put("surOperations",ret) ;
	return map ;
}
function recordDiagnosis(aCtx,aSlsId,aRegistrationType,aPriority,aField,aDtype) {
	var wqr = new Packages.ru.ecom.ejb.services.query.WebQueryResult() ;
	if (aDtype==null || aDtype=='') aDtype='HospitalMedCase' ;
	var prioritySql="" ;
	if (+aPriority>0) {prioritySql=" and vpd.code='"+aPriority+"' "}
	var sql="select sls.id,list(case when vdrt.code='"+aRegistrationType+"'"+prioritySql+"  then mkb.code else null end) as diagCode"
		+" ,list(case when vdrt.code='"+aRegistrationType+"' "+prioritySql+" then diag.name else null end) as diagText" 
		+" from MedCase sls" 
		+" left join Diagnosis diag on diag.medCase_id=sls.id"
		+" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id"
		+" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
		+" left join VocIdc10 mkb on mkb.id=diag.idc10_id"
		+" where sls.id='"+aSlsId+"' and sls.dtype='"+aDtype+"'"
		+" group by sls.id" ;
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	if (list.size()>0) {
		var obj = list.get(0) ;
		wqr.set1(obj[1]!=null?obj[1]:"") ;//aField+".mkb",
		wqr.set2(obj[2]!=null?obj[2]:"") ;//aField+".text",
	} else {
		wqr.set1("") ;//aField+".mkb",
		wqr.set2("") ;//aField+".text",
	}
	return wqr ;
}
function infoPrint(aCtx,aParams) {
	map.put("print.info","") ;
}