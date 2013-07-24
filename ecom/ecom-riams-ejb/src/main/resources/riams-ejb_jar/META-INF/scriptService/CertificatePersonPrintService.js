var map = new java.util.HashMap() ;	

function printDogovogByNoPrePaidServicesMedServise(aCtx, aParams) {
	var pid = aParams.get("id");
	var sqlQuery ="select cams.id, pp.code,pp.name,cams.cost,cams.countMedService" 
		+"	, cams.countMedService*cams.cost as sumNoAccraulMedService" 
		+"		from ServedPerson sp"
		+"		left join ContractAccount ca on ca.servedPerson_id = sp.id"
		+"		left join ContractAccountMedService cams on cams.account_id=ca.id"
		+"		left join PriceMedService pms on pms.id=cams.medService_id"
		+"		left join PricePosition pp on pp.id=pms.pricePosition_id"
		+"		left join ContractAccountOperationByService caos on caos.accountMedService_id=cams.id"
		+"		left join ContractAccountOperation cao on cao.id=caos.accountOperation_id and cao.dtype='OperationAccrual'"
		+"		left join ContractPerson cp on cp.id=sp.person_id left join patient p on p.id=cp.patient_id"
		+"		where ca.id='"+pid+"' and cao.id is null and caos.id is null"
		+"		group by  cams.id, pp.code, pp.name , cams.countMedService,cams.cost";
	var list = aCtx.manager.createNativeQuery(sqlQuery).getResultList();
	var servisec = new java.util.ArrayList() ;
	
	var allcost;
	allcost = 1-1;
	for (var i = 0; i<list.size(); i++) {
		var wq = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		var obj=list.get(i) ;
		wq.setSn(i+1) ;
		for (var j=1;j<obj.length; j++) {
			eval("wq.set"+j+"(obj["+(j)+"])") ;
		}
		servisec.add(wq);
		var sumi = +obj[5] ;
		allcost = allcost + sumi ;
	}
	map.put("serv",servisec) ;
	var rub;
	
	map.put("allcost", parseInt(allcost));
	var currentDate = new Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	var login = aCtx.getSessionContext().getCallerPrincipal().toString() ;
	var list = aCtx.manager.createNativeQuery("select fullname from SecUser where login=:login")
	.setParameter("login",login)
	.setMaxResults(1)
	.getResultList() ;
	map.put("login",list.size()>0?list.get(0):login) ;
	map.put("accountNumber",pid) ;
	return map;
}
function printContractByAccrual(aCtx, aParams) {
	var pid = aParams.get("id");
	var sqlQuery ="select cams.id, pp.code,pp.name,cams.cost,cams.countMedService" 
		+"	, cams.countMedService*cams.cost as sumNoAccraulMedService" 
		+"		from MedContract mc "
		+"		left join ContractAccount ca on ca.contract_id = mc.id"
		+"		left join ContractAccountMedService cams on cams.account_id=ca.id"
		+"		left join PriceMedService pms on pms.id=cams.medService_id"
		+"		left join PricePosition pp on pp.id=pms.pricePosition_id"
		+"		left join ContractAccountOperationByService caos on caos.accountMedService_id=cams.id"
		+"		left join ContractAccountOperation cao on cao.id=caos.accountOperation_id and cao.dtype='OperationAccrual'"
		+"		where cao.id='"+pid+"'"
		+"		group by  cams.id, pp.code, pp.name , cams.countMedService,cams.cost";
	var list = aCtx.manager.createNativeQuery(sqlQuery).getResultList();
	var servisec = new java.util.ArrayList() ;
	
	var allcost;
	allcost = 1-1;
	for (var i = 0; i<list.size(); i++) {
		var wq = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		var obj=list.get(i) ;
		wq.setSn(i+1) ;
		for (var j=1;j<obj.length; j++) {
			eval("wq.set"+j+"(obj["+(j)+"])") ;
		}
		servisec.add(wq);
		var sumi = +obj[5] ;
		allcost = allcost + sumi ;
	}
	map.put("serv",servisec) ;
	var rub;
	
	map.put("allcost", parseInt(allcost));
	
	var currentDate = new Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	var login = aCtx.getSessionContext().getCallerPrincipal().toString() ;
	var list = aCtx.manager.createNativeQuery("select fullname from SecUser where login=:login")
		.setParameter("login",login)
		.setMaxResults(1)
		.getResultList() ;
	map.put("login",list.size()>0?list.get(0):login) ;
	
	
	var sqlQuery1 ="select mc.contractNumber,list(distinct cpp.lastname||' '||cpp.firstname||' '||cpp.middlename) as cpplastname,list(distinct cpp1.lastname||' '||cpp1.firstname||' '||cpp1.middlename) as cpp1lastname,min(cpp.id) as cppid, min(cpp1.id) as mincpp1id" 
		+"		from MedContract mc "
		+"      left join ContractAccount ca on mc.id=ca.contract_id"
		+"		left join ContractAccountMedService cams on cams.account_id=ca.id"
		+"		left join ServedPerson sp on cams.servedPerson_id = sp.id"
		+"		left join PriceMedService pms on pms.id=cams.medService_id"
		+"		left join PricePosition pp on pp.id=pms.pricePosition_id"
		+"		left join ContractAccountOperationByService caos on caos.accountMedService_id=cams.id"
		+"		left join ContractAccountOperation cao on cao.id=caos.accountOperation_id and cao.dtype='OperationAccrual'"
		+"		left join ContractPerson cp on cp.id=sp.person_id left join patient cpp on cpp.id=cp.patient_id"
		+"		left join ContractPerson cp1 on cp1.id=mc.customer_id left join patient cpp1 on cpp1.id=cp1.patient_id"
		+"		where cao.id='"+pid+"' group by mc.contractnumber" ;
	var list1 = aCtx.manager.createNativeQuery(sqlQuery1).getResultList();
	var obj = list1.size()>0?list1.get(0):null ;
	
	if (obj!=null) {
		map.put("contractNumber",obj[0]!=null?obj[0]:"________") ;
		if (+obj[3]==(+obj[4])) {
			map.put("customer1.fio",obj[1]) ;
			map.put("customer2.fio",null) ;
			map.put("served.fio",null) ;
		} else {
			map.put("customer1.fio",null) ;
			map.put("customer2.fio",obj[1]) ;
			map.put("served.fio",obj[2]) ;
		}
		var customer = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,java.lang.Long(obj[4])) ;
		map.put("customerPerson",customer) ;
		//throw ""+obj[4] ;
		map.put("customer.addressRegistration",customer.addressRegistration) ;
		var passport = "" ; 
		if (customer.passportType!=null) {
			passport=customer.passportType.name ;
		}
		passport=passport+" серия " ;
		if (customer.passportSeries!=null) {passport=passport+customer.passportSeries ;} else {	passport=passport+"____________" ;}
		passport=passport+" номер " ;
		if (customer.passportNumber!=null) {passport=passport+customer.passportNumber ;} else {	passport=passport+"____________" ;}
		passport=passport+" выдан " ;
		if (customer.passportDateIssue!=null) {passport=passport+customer.passportDateIssue ;} else {	passport=passport+"____________" ;}
		passport=passport+" " ;
		if (customer.passportWhomIssued!=null) {passport=passport+customer.passportWhomIssued ;} else {	passport=passport+"______________________________" ;}
		map.put("customer.passportInfo",passport) ;
	} else {
		map.put("contractNumber","________") ;
		map.put("customer1.fio",null) ;
		map.put("customer2.fio",null) ;
		map.put("served.fio",null) ;
		map.put("customer.addressRegistration","____________________________________________________________________________________________________________") ;
		map.put("customer.pasportInfo","____________________________________________________________________________________________________________") ;
	}
	
	
	return map;
}