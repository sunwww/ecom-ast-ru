var map = new java.util.HashMap() ;

function printPriceList(aCtx,aParams) {
	var priceList = aParams.get("id") ;
	var mainGroupSql = "select pg.id,pg.code,pg.name from PricePosition pg" 
		+" where pg.priceList_id = '"+priceList+"' and pg.dtype='PriceGroup' and pg.parent_id is null"
		+" order by pg.code" ;
	var list = aCtx.manager.createNativeQuery(mainGroupSql).getResultList();
	var mainGroupList = new java.util.ArrayList() ;
	for (var i = 0; i<list.size(); i++) {
		var wq = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		var obj=list.get(i) ;
		wq.setSn(i+1) ;
		for (var j=0;j<obj.length; j++) {
			eval("wq.set"+(j+1)+"(obj["+(j)+"])") ;
		}
		wq.set4(getGroup(aCtx,priceList,obj[0])) ;
		mainGroupList.add(wq) ;
	}
	map.put("mainGroup",mainGroupList) ;
	return map ;
}
function getGroup(aCtx,aPriceList,aParent) {
	var wqM = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	var childGroup = new java.util.ArrayList() ;
	var childPosition = new java.util.ArrayList() ;
	
	var groupSql = "select pg.id,pg.code,pg.name,case when trim(pg.comment)='' then null else pg.comment end from PricePosition pg" 
		+" where pg.priceList_id = '"+aPriceList+"' and pg.dtype='PriceGroup' and pg.parent_id = '"+aParent+"'"
		+" order by pg.code" ;
	var list2 = aCtx.manager.createNativeQuery(groupSql).getResultList();
	
	for (var i = 0; i<list2.size(); i++) {
		var wqG = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		var obj1=list2.get(i) ;
		wqG.setSn(i+1) ;
		wqG.set1(obj1[1]) ;
		wqG.set2(obj1[2]) ;
		//wqG.set3(obj1[3]) ;
		var comment = new java.util.ArrayList() ;
		if (obj1[3]!=null) {
			var wqC = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			wqC.set1(obj1[3]) ;
			comment.add(wqC) ;
		}
		wqG.set3(comment) ;
		//throw ""+obj1[2];
		/*
		for (var j=1;j<obj1.length; j++) {
			eval("wqG.set"+j+"(ob1j["+(j)+"])") ;
		}*/
		wqG.set4(getGroup(aCtx,aPriceList,obj1[0])) ;
		childGroup.add(wqG) ;
	}
	var positionSql="select pp.id,pp.code,pp.name,pp.expUnit,pp.cost from PricePosition pp "
		+" where pp.parent_id = '"+aParent+"' and pp.dtype='PricePosition' ORDER BY pp.code" ;
	var list1 = aCtx.manager.createNativeQuery(positionSql).getResultList();
	for (var i = 0; i<list1.size(); i++) {
		var wqP = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		var obj1=list1.get(i) ;
		wqP.setSn(i+1) ;
		for (var j=0;j<obj1.length; j++) {
			eval("wqP.set"+(j+1)+"(obj1["+(j)+"])") ;
		}
		childPosition.add(wqP) ;
	}
	
	wqM.set1(childGroup) ; 
	wqM.set2(childPosition) ;
	return wqM ;
}
function printDogovogByNoPrePaidServicesMedServise(aCtx, aParams) {
	var pid = aParams.get("id");
	var sqlQuery ="select cams.id, pp.code,pp.name,cams.cost,cams.countMedService" 
		+"	, cams.countMedService*cams.cost as sumNoAccraulMedService" 
		+"  ,round((cams.cost*(100-coalesce(cao.discount,0))/100),2) as costDisc" 
		+"  ,round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) as sumNoAccraulMedServiceDisc" 
		+"		from ContractAccount ca"
		+"		left join ContractAccountMedService cams on cams.account_id=ca.id"
		+"		left join PriceMedService pms on pms.id=cams.medService_id"
		+"		left join PricePosition pp on pp.id=pms.pricePosition_id"
		+"		left join ContractAccountOperationByService caos on caos.accountMedService_id=cams.id"
		+"		left join ContractAccountOperation cao on cao.id=caos.accountOperation_id and cao.dtype='OperationAccrual'"
		+"		where ca.id='"+pid+"' and cao.id is null and caos.id is null"
		+"		group by  cams.id, pp.code, pp.name , cams.countMedService,cams.cost,cao.discount";
	var list = aCtx.manager.createNativeQuery(sqlQuery).getResultList();
	var servisec = new java.util.ArrayList() ;
	
	var allcost1=0;
	var allcost=0;
	for (var i = 0; i<list.size(); i++) {
		var wq = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		var obj=list.get(i) ;
		wq.setSn(i+1) ;
		for (var j=1;j<obj.length; j++) {
			eval("wq.set"+j+"(obj["+(j)+"])") ;
		}
		servisec.add(wq);
		var sumi = +obj[5] ;
		allcost1 = allcost1 + sumi ;
		sumi = +obj[7] ;
		allcost = allcost + sumi ;
	}
	map.put("serv",servisec) ;
	
	map.put("allcostNoDiscount", parseInt(allcost1));
	map.put("allcost", parseInt(allcost));
	map.put("allcostS", parseSymRub(allcost));
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
	
	
	var sqlQuery1 ="select mc.contractNumber,list(distinct cpp.lastname||' '||cpp.firstname||' '||cpp.middlename) as cpplastname,list(distinct cpp1.lastname||' '||cpp1.firstname||' '||cpp1.middlename) as cpp1lastname,min(cpp.id) as cppid, min(cpp1.id) as mincpp1id" 
		+",mc.id as mcid,max(ca.DiscountDefault) as DiscountDefault"
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
		+"		where ca.id='"+pid+"' and cao.id is null and caos.id is null group by mc.id,mc.contractnumber" ;
	var list1 = aCtx.manager.createNativeQuery(sqlQuery1).getResultList();
	var obj = list1.size()>0?list1.get(0):null ;
	//map.put("contract","jkljlkj") ;
	if (obj!=null) {
		map.put("discount",+obj[6]>0?+obj[6]:null) ;
		map.put("contractNumber",obj[0]!=null?obj[0]:"________") ;
		var contract = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.MedContract,java.lang.Long.valueOf(""+obj[5])) ;
		map.put("contract",contract) ;
		var servedPerson = null,customerPerson=null; 
		if (+obj[3]==(+obj[4])) {
			map.put("customer1.fio",obj[1]) ;
			map.put("customer2.fio",null) ;
			map.put("served.fio",null) ;
		} else {
			map.put("customer1.fio",null) ;
			map.put("customer2.fio",obj[1]) ;
			map.put("served.fio",obj[2]) ;
			servedPerson = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,java.lang.Long(obj[3])) ;
		}
		customerPerson = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,java.lang.Long(obj[4])) ;
		map.put("customerPerson",customerPerson) ;
		map.put("servedPerson",servedPerson) ;
		//throw ""+obj[4] ;
		map.put("customer.addressRegistration",customerPerson.addressRegistration) ;
		map.put("customer.passportInfo",getPassportInfo(customerPerson.passportType
				,customerPerson.passportSeries,customerPerson.passportNumber
				,customerPerson.passportDateIssue,customerPerson.passportWhomIssued)) ;
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
function printContractByAccrual(aCtx, aParams) {
	var pid = aParams.get("id");
	var sqlQuery ="select cams.id, pp.code,pp.name,cams.cost,cams.countMedService" 
		+"	, cams.countMedService*cams.cost as sumNoAccraulMedService"
		+"  ,round((cams.cost*(100-coalesce(cao.discount,0))/100),2) as costDisc" 
		+"  ,round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) as sumNoAccraulMedServiceDisc"
		
		+"		from MedContract mc "
		+"		left join ContractAccount ca on ca.contract_id = mc.id"
		+"		left join ContractAccountMedService cams on cams.account_id=ca.id"
		+"		left join PriceMedService pms on pms.id=cams.medService_id"
		+"		left join PricePosition pp on pp.id=pms.pricePosition_id"
		+"		left join ContractAccountOperationByService caos on caos.accountMedService_id=cams.id"
		+"		left join ContractAccountOperation cao on cao.id=caos.accountOperation_id and cao.dtype='OperationAccrual'"
		+"		where cao.id='"+pid+"'"
		+"		group by  cams.id, pp.code, pp.name , cams.countMedService,cams.cost,cao.discount";
	var list = aCtx.manager.createNativeQuery(sqlQuery).getResultList();
	var servisec = new java.util.ArrayList() ;
	
	var allcost1=0;
	var allcost=0;
	for (var i = 0; i<list.size(); i++) {
		var wq = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		var obj=list.get(i) ;
		wq.setSn(i+1) ;
		for (var j=1;j<obj.length; j++) {
			eval("wq.set"+j+"(obj["+(j)+"])") ;
		}
		servisec.add(wq);
		var sumi = +obj[5] ;
		allcost1 = allcost1 + sumi ;
		sumi = +obj[7] ;
		allcost = allcost + sumi ;
	}
	map.put("allcostNoDiscount", parseInt(allcost1));
	map.put("serv",servisec) ;
	map.put("allcost", parseInt(allcost));
	map.put("allcostS", parseSymRub(allcost));
	var currentDate = new Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	var login = aCtx.getSessionContext().getCallerPrincipal().toString() ;
	var list = aCtx.manager.createNativeQuery("select fullname from SecUser where login=:login")
		.setParameter("login",login)
		.setMaxResults(1)
		.getResultList() ;
	map.put("login",list.size()>0?list.get(0):login) ;
	
	
	var sqlQuery1 ="select mc.contractNumber,list(distinct cpp.lastname||' '||cpp.firstname||' '||cpp.middlename) as cpplastname,list(distinct cpp1.lastname||' '||cpp1.firstname||' '||cpp1.middlename) as cpp1lastname,min(cpp.id) as cppid, min(cpp1.id) as mincpp1id"+
	",mc.id as mcid,max(ca.DiscountDefault) as DiscountDefault" 
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
		+"		where cao.id='"+pid+"' group by mc.id,mc.contractnumber" ;
	var list1 = aCtx.manager.createNativeQuery(sqlQuery1).getResultList();
	var obj = list1.size()>0?list1.get(0):null ;
	if (obj!=null) {
		map.put("discount",+obj[6]>0?+obj[6]:null) ;
		var contract = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.MedContract,java.lang.Long.valueOf(""+obj[5])) ;
		map.put("contract",contract) ;
		map.put("contractNumber",obj[0]!=null?obj[0]:"________") ;
		var servedPerson = null,customerPerson=null; 
		if (+obj[3]==(+obj[4])) {
			map.put("customer1.fio",obj[1]) ;
			map.put("customer2.fio",null) ;
			map.put("served.fio",null) ;
		} else {
			map.put("customer1.fio",null) ;
			map.put("customer2.fio",obj[1]) ;
			map.put("served.fio",obj[2]) ;
			servedPerson = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,java.lang.Long(obj[3])) ;
		}
		customerPerson = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,java.lang.Long(obj[4])) ;
		map.put("customerPerson",customerPerson) ;
		map.put("servedPerson",servedPerson) ;
		
		//throw ""+obj[4] ;
		map.put("customer.passportInfo",getPassportInfo(customerPerson.passportType
				,customerPerson.passportSeries,customerPerson.passportNumber
				,customerPerson.passportDateIssue,customerPerson.passportWhomIssued)) ;
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
function parseInt(aNumeric) {
	var value = new java.math.BigDecimal(aNumeric) ;
	var kop =(value.doubleValue()-value.intValue())*100 ;
	if (kop<10) kop="0"+kop ;
	return value.intValue()+" руб. "+kop+" коп." ;
}
function parseSymRub(aNumeric) {
	var value = new java.math.BigDecimal(aNumeric) ;
	var kop=(value.doubleValue()-value.intValue())*100 ;
	if (kop<10) kop="0"+kop ;
	return Packages.ru.ecom.ejb.services.util.ConvertSql.toWords(value)+" руб. "+ kop +" коп." ;
}
function getPassportInfo(aPassportType,aPassportSeries,aPassportNumber,aPassportDateIssue,aPassportWhomIssued) {
	var passport = "" ; 
	if (aPassportType!=null) {
		passport=aPassportType.name ;
	}
	passport=passport+" серия " ;
	if (aPassportSeries!=null) {passport=passport+aPassportSeries ;} else {	passport=passport+"____________" ;}
	passport=passport+" номер " ;
	if (aPassportNumber!=null) {passport=passport+aPassportNumber ;} else {	passport=passport+"____________" ;}
	passport=passport+" выдан " ;
	if (aPassportDateIssue!=null) {passport=passport+aPassportDateIssue ;} else {	passport=passport+"____________" ;}
	passport=passport+" " ;
	if (aPassportWhomIssued!=null) {passport=passport+aPassportWhomIssued ;} else {	passport=passport+"______________________________" ;}
	return passport ;
}