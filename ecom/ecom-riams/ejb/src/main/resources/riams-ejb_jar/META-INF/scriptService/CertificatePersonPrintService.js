var map = new java.util.HashMap() ;

function issueRefund(aCtx, aId) {
	var operationAccrual = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.OperationAccrual,java.lang.Long.valueOf(aId)) ;
	if (operationAccrual.getRepealOperation()!=null) throw "Возврат уже осуществлен" ;
	var operReturn = new Packages.ru.ecom.mis.ejb.domain.contract.OperationReturn() ;
	var date = new java.util.Date() ;
	var dateS = new java.sql.Date(date.getTime());
	var timeS = new java.sql.Time (date.getTime())
	var username = aCtx.getSessionContext().getCallerPrincipal().toString() ;
	operReturn.setAccount(operationAccrual.account) ;
	operReturn.setCost(operationAccrual.cost);
	operReturn.setIsPaymentTerminal(operationAccrual.isPaymentTerminal);
	operReturn.setDiscount(operationAccrual.discount) ;
	operReturn.setOperationDate(dateS) ;
	operReturn.setOperationTime(timeS) ;
	operReturn.setCreateDate(dateS) ;
	operReturn.setCreateTime(timeS) ;
	operReturn.setCreateUsername(username) ;
	var wf= aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunctionList").iterator().next() ;
	operReturn.setWorkFunction(wf) ;
	operReturn.setOperationDate(dateS);
	operReturn.setOperationTime(timeS);
	aCtx.manager.persist(operReturn) ;
	operationAccrual.setRepealOperation(operReturn);
	aCtx.manager.persist(operationAccrual) ;
	var servicies = aCtx.manager.createNativeQuery("select list(''||accountMedService_id) from ContractAccountOperationByService where accountOperation_id="+aId).getSingleResult() ;
	var servicies = (''+servicies).split(',') ;
	for(var i=0;i<servicies.length;i++) {
		var serv = +servicies[i] ;
		var cams = new Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountOperationByService() ;
		var ams = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountMedService,java.lang.Long.valueOf(serv)) ;
		cams.setAccountOperation(operReturn) ;
		cams.setAccountMedService(ams) ;
		aCtx.manager.persist(cams) ;
	}
	//Осуществляем операцию возврата
    var kkm =new Packages.ru.ecom.mis.ejb.service.contract.ContractServiceBean();
    var worker = wf.worker.person;
    var fio = wf.workFunction.name+" "+worker.lastname+" "+worker.firstname.substring(0,1)+". "+(worker.middlename!=null?worker.middlename.substring(0,1)+".":"");
    var res = kkm.sendKKMRequest("makeRefund",operationAccrual.account.id, operationAccrual.discount
		,operationAccrual.isPaymentTerminal!=null ? operationAccrual.isPaymentTerminal : false,null, fio, aCtx.manager, wf.getKkmEquipmentDefault()!=null ? wf.getKkmEquipmentDefault().getUrl() : "");

}

function printDefaultLpuRequisites(aCtx, aFldName) {
	var lpu =aCtx.manager.createNativeQuery( "select keyvalue from softconfig  where key = 'DEFAULT_LPU' ").getResultList();
	if (lpu.size()>0) {
		printLpuRequisites(aCtx,+lpu.get(0),aFldName);
	}
}
function printLpuRequisites(aCtx, aLpuId, aFldName) {
	var sql = "select code, value, name from MisLpuRequisite where lpu_id="+aLpuId;
	var list = aCtx.manager.createNativeQuery(sql).getResultList();
	for (var i=0;i<list.size();i++) {
		var obj = list.get(i);
		map.put(aFldName+"_"+obj[0],""+obj[1]);
		//throw ""+aFldName+"_"+obj[0]+"<>"+map.get(aFldName+"_"+obj[0]);
		map.put(aFldName+"_"+obj[0]+"Name",""+obj[2]);
	}
}
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

//Печать договора по неоплаченному счету
function printDogovogByNoPrePaidServicesMedServise(aCtx, aParams) {
	var pid = aParams.get("id");
	var sqlQuery ="select cams.id, pp.code,pp.name||' '||coalesce(pp.printComment,'') as ppname,cams.cost,cams.countMedService"
		+"	,cams.countMedService*cams.cost as sumNoAccraulMedService"
		+"  ,round((cams.cost*(100-coalesce(ca.discountDefault,0))/100),2) as costDisc" 
		+"  ,round(cams.countMedService*(cams.cost*(100-coalesce(ca.discountDefault,0))/100),2) as sumNoAccraulMedServiceDisc"
		+" ,ca.discountDefault as cadiscountDefault"
		+" ,priv.serialdoc||' '||priv.numberdoc||' ('||vpc.name||')' as privil" +
		" ,cams.cost*cams.countMedService as tarif "
		+"		from ContractAccount ca"
		+"		left join ContractAccountMedService cams on cams.account_id=ca.id"
		+"		left join PriceMedService pms on pms.id=cams.medService_id"
		+"		left join PricePosition pp on pp.id=pms.pricePosition_id"
		+"		left join ContractAccountOperationByService caos on caos.accountMedService_id=cams.id"
		+"		left join ContractAccountOperation cao on cao.id=caos.accountOperation_id and cao.dtype='OperationAccrual'" +
		"	    left join privilege priv on priv.id = ca.privilege_id" +
		" 		left join vocprivilegecategory vpc on vpc.id = priv.category_id"
		+"		where ca.id='"+pid+"' and cao.id is null and caos.id is null"
		+"		group by  cams.id, pp.code, pp.name, pp.printComment , cams.countMedService,cams.cost,ca.discountDefault,priv.numberdoc,priv.serialdoc,vpc.name";
	var list = aCtx.manager.createNativeQuery(sqlQuery).getResultList();
	var servisec = new java.util.ArrayList() ;
	var discount = 0, allcost1=0, allcost=0 ;
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
		discount = +obj[8] ;
	}
	map.put("allcostNoDiscount", parseInt(allcost1));
	map.put("allcostNoDiscountS", parseSymRub(allcost1));
	map.put("serv",servisec) ;
	map.put("allcost", parseInt(allcost1,discount,allcost));
	map.put("allcostS", parseSymRub(allcost));
	var currentDate = new Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	var login = aCtx.getSessionContext().getCallerPrincipal().toString() ;
	list = aCtx.manager.createNativeQuery("select fullname from SecUser where login=:login")
			.setParameter("login",login).setMaxResults(1).getResultList() ;
	map.put("login",list.size()>0 ? list.get(0) : login) ;
	map.put("accountNumber",pid) ;
	printAttorney(aCtx);
	
	var sqlQuery1 ="select mc.contractNumber,list(distinct cpp.lastname||' '||cpp.firstname||' '||coalesce(cpp.middlename,'')) as cpplastname,list(distinct cpp1.lastname||' '||cpp1.firstname||' '||coalesce(cpp1.middlename,'')) as cpp1lastname,min(cpp.id) as cppid, min(cpp1.id) as mincpp1id" 
		+",mc.id as mcid,max(ca.DiscountDefault) as DiscountDefault"
		+"		from MedContract mc "
		+"      left join ContractAccount ca on mc.id=ca.contract_id"
		+"		left join ContractAccountMedService cams on cams.account_id=ca.id"
		+"		left join ServedPerson sp on cams.servedPerson_id = sp.id"
		//+"		left join PriceMedService pms on pms.id=cams.medService_id"
		//+"		left join PricePosition pp on pp.id=pms.pricePosition_id"
		//+"		left join ContractAccountOperationByService caos on caos.accountMedService_id=cams.id"
		//+"		left join ContractAccountOperation cao on cao.id=caos.accountOperation_id and cao.dtype='OperationAccrual'"
		+"		left join ContractPerson cp on cp.id=sp.person_id left join patient cpp on cpp.id=cp.patient_id"
		+"		left join ContractPerson cp1 on cp1.id=mc.customer_id left join patient cpp1 on cpp1.id=cp1.patient_id"
		+"		where ca.id='"+pid+"' group by mc.id,mc.contractnumber" ;
	var list1 = aCtx.manager.createNativeQuery(sqlQuery1).getResultList();
	var obj = list1.size()>0 ? list1.get(0) : null ;
	var ret = new java.util.ArrayList() ;
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
			map.put("customer2.fio",obj[2]) ;
			map.put("served.fio",obj[1]) ;
			var parDep = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ; //Если заказчик !=обслуживаемая персона, то печатает 3 договора.
			ret.add(parDep);
			servedPerson = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,java.lang.Long(obj[3])) ;
		}
		map.put("print3Dogovor",ret);
		//throw ""+ret.size();
		customerPerson = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,java.lang.Long(obj[4])) ;
		map.put("customerPerson",customerPerson) ;
		map.put("servedPerson",servedPerson) ;
		map.put("customer.addressRegistration",customerPerson.addressRegistration) ;
		map.put("customer.passportInfo",getPassportInfo(customerPerson.passportType
				,customerPerson.passportSeries,customerPerson.passportNumber
				,customerPerson.passportDateIssued,customerPerson.passportWhomIssued)) ;
	} else {
		map.put("contractNumber","________") ;
		map.put("customer1.fio",null) ;
		map.put("customer2.fio",null) ;
		map.put("served.fio",null) ;
		map.put("customer.addressRegistration","____________________________________________________________________________________________________________") ;
		map.put("customer.passportInfo","____________________________________________________________________________________________________________") ;
	}
    printDefaultLpuRequisites(aCtx,"DefaultLpu");
	return map;
}
function printAttorney (aCtx) {
	//Доверенности
	var att_exeName = "";
	var att_exeAltName = "";
	var att_exeShortName = "";
	var att_number = "";
	var att_dateFrom = "";
	var att_altName = "";
	var login = aCtx.getSessionContext().getCallerPrincipal().toString() ;
	var sqlAttorneySql  = "select pat.lastname ||' '||pat.firstname ||' '||pat.middlename as f0, att.attorneyNumber as f1_number, to_char(att.attorneyStartDate,'dd.MM.yyyy') as f2_date" +
			" ,att.altPersonInfo as f3_altExecutorName ,pat.lastname||' '||substring(pat.firstname,1,1)||'.'||substring(pat.middlename,1,1)||'.' as f4_shortExecutorName" +
			" ,vat.altName as f5 " +
			" from attorney att" +
			" left join patient pat on pat.id=att.person_id" +
			" left join vocattorneytype vat on vat.id=att.type_id" +
			" where att.id=coalesce ((select attorney_id from workfunction wf left join secuser su on su.id=wf.secuser_id where su.login='"+login+"'),(select max(id) from attorney where isDefault='1'))";
	var list_attorney =aCtx.manager.createNativeQuery(sqlAttorneySql).getResultList();
	if (list_attorney.size()>0) {
		var p = list_attorney.get(0);
		att_exeName = ""+p[0];
		att_exeAltName = ""+p[3];
		att_exeShortName = ""+p[4];
		att_number = ""+p[1];
		att_dateFrom = ""+p[2];
		att_altName = ""+p[5];
	}
	map.put("executorFullName",att_exeName);
	map.put("executorAltFullName", att_exeAltName);
	map.put("executorShortName", att_exeShortName);
	map.put("attorneyNumber",att_number);
	map.put("attorneyDateFrom",att_dateFrom);
	map.put("attorneyTypeAltName",att_altName);
	
}

//Печать оплаченного счета
function printContractByAccrual(aCtx, aParams) {
	var pid = aParams.get("id");
    var sqlQuery ="select cams.id, pp.code,pp.name||' '||coalesce(pp.printComment,'') as ppname,cams.cost,cams.countMedService \n" +
        ", cams.countMedService*cams.cost as sumNoAccraulMedService\n" +
        ",round((cams.cost*(100-coalesce(cao.discount,0))/100),2) as costDisc \n" +
        ",round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) as sumNoAccraulMedServiceDisc\n" +
        ",ca.discountDefault as cadiscountDefault\n" +
        ",priv.serialdoc||' '||priv.numberdoc||' ('||vpc.name||')' as privil \n" +
        ",cams.cost*cams.countMedService as tarif \n" +
        "from MedContract mc \n" +
        "left join ContractAccount ca on ca.contract_id = mc.id\n" +
        "left join ContractAccountMedService cams on cams.account_id=ca.id\n" +
        "left join PriceMedService pms on pms.id=cams.medService_id\n" +
        "left join PricePosition pp on pp.id=pms.pricePosition_id\n" +
        "left join ContractAccountOperationByService caos on caos.accountMedService_id=cams.id\n" +
        "left join ContractAccountOperation cao on cao.id=caos.accountOperation_id and cao.dtype='OperationAccrual'\n" +
        "left join privilege priv on priv.id = ca.privilege_id \n" +
        "left join vocprivilegecategory vpc on vpc.id = priv.category_id\n" +
        "where cao.id="+pid+"\n" +
        "group by  cams.id, pp.code, pp.name, pp.printComment , cams.countMedService,cams.cost,ca.discountDefault,priv.numberdoc,priv.serialdoc,vpc.name,cao.discount";
	var list = aCtx.manager.createNativeQuery(sqlQuery).getResultList();
	var servisec = new java.util.ArrayList() ;
	
	var discount = 0, allcost1=0, allcost=0 ;
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
		discount = +obj[8] ;
	}
	map.put("allcostNoDiscount", parseInt(allcost1));
	map.put("allcostNoDiscountS", parseSymRub(allcost1));
	map.put("serv",servisec) ;
	map.put("allcost", parseInt(allcost1,discount,allcost));
	map.put("allcostS", parseSymRub(allcost));
	var currentDate = new Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	var login = aCtx.getSessionContext().getCallerPrincipal().toString() ;
	list = aCtx.manager.createNativeQuery("select fullname from SecUser where login=:login")
		.setParameter("login",login)
		.setMaxResults(1)
		.getResultList() ;
	map.put("login",list.size()>0 ? list.get(0) : login) ;
	
	printAttorney(aCtx) ;
	var sqlQuery1 ="select mc.contractNumber,list(distinct cpp.lastname||' '||cpp.firstname||' '||coalesce(cpp.middlename,'')) as cpplastname,list(distinct cpp1.lastname||' '||cpp1.firstname||' '||coalesce(cpp1.middlename,'')) as cpp1lastname,min(cpp.id) as cppid, min(cpp1.id) as mincpp1id"+
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
	var obj = list1.size()>0 ? list1.get(0) : null ;
	if (obj!=null) {
		map.put("discount",+obj[6]>0 ? +obj[6] : null) ;
		var contract = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.MedContract,java.lang.Long.valueOf(""+obj[5])) ;
		var ret = new java.util.ArrayList() ;
		map.put("contract",contract) ;
		map.put("contractNumber",obj[0]!=null?obj[0]:"________") ;
		var servedPerson = null,customerPerson=null;
		
		if (+obj[3]==(+obj[4])) {
			map.put("customer1.fio",obj[2]) ;
			map.put("customer2.fio",null) ;
			map.put("served.fio",null) ;
		} else {
			 
			map.put("customer1.fio",null) ;
			map.put("customer2.fio",obj[2]) ;
			map.put("served.fio",obj[1]) ;
			var parDep = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ; //Если заказчик !=обслуживаемая персона, то печатает 3 договора.
			ret.add(parDep);
			servedPerson = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,java.lang.Long(obj[3])) ;
		}
		
		map.put("print3Dogovor",ret);
		customerPerson = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,java.lang.Long(obj[4])) ;
		map.put("customerPerson",customerPerson) ;
		map.put("servedPerson",servedPerson) ;
		
		map.put("customer.passportInfo",getPassportInfo(customerPerson.passportType
				,customerPerson.passportSeries,customerPerson.passportNumber
				,customerPerson.passportDateIssued,customerPerson.passportWhomIssued)) ;
	} else {
		map.put("contractNumber","________") ;
		map.put("customer1.fio",null) ;
		map.put("customer2.fio",null) ;
		map.put("served.fio",null) ;
		map.put("customer.addressRegistration","____________________________________________________________________________________________________________") ;
		map.put("customer.pasportInfo","____________________________________________________________________________________________________________") ;
	}

    printDefaultLpuRequisites(aCtx,"DefaultLpu");
	return map;
}
function parseInt(aValue1,aDiscount,aValue2) {
	//if (+aR>0) return aNumeric ;
	var ret = "" ;
	var value = new java.math.BigDecimal(aValue1) ;
	var kop =(+aValue1 % 1).toFixed(2).slice(2) ;
	var rub = value.intValue();
	ret = ""+rub+" руб. "+kop+" коп." ;
	if (+aDiscount>0) {
		ret= ret+" СКИДКА: "+aDiscount+"%";
		ret =ret+". ИТОГО с учетом скидки:" ;
		value = new java.math.BigDecimal(aValue2) ;
		kop =(+aValue2 % 1).toFixed(2).slice(2) ;
		rub = value.intValue();
		ret = ret+" "+rub+" руб. "+kop+" коп." ;
	}
	return ret ;
}
function parseSymRub(aNumeric) {
	var value = new java.math.BigDecimal(aNumeric) ;
	var kop =(+aNumeric % 1).toFixed(2).slice(2) ;
	return Packages.ru.ecom.ejb.services.util.ConvertSql.toWords(value)+" руб. "+ kop +" коп." ;
}
function getPassportInfo(aPassportType,aPassportSeries,aPassportNumber,aPassportDateIssue,aPassportWhomIssued) {
	var passport = "" ; 
	if (aPassportType!=null) {
		passport=aPassportType.name ;
	}
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	passport=passport+" серия " ;
	if (aPassportSeries!=null) {passport=passport+aPassportSeries ;} else {	passport=passport+"____________" ;}
	passport=passport+" номер " ;
	if (aPassportNumber!=null) {passport=passport+aPassportNumber ;} else {	passport=passport+"____________" ;}
	passport=passport+" выдан " ;
	if (aPassportDateIssue!=null) {passport=passport+FORMAT_2.format(aPassportDateIssue) ;} else {	passport=passport+"____________" ;}
	passport=passport+" " ;
	if (aPassportWhomIssued!=null) {passport=passport+aPassportWhomIssued ;} else {	passport=passport+"______________________________" ;}
	return passport ;
}
//lastrelease milamesher #98 шаблоны лаб. анализов
function printLabAnalysisTemplateExtra (aCtx, aParams) {
    var pid = aParams.get("id");
    var sqlQuery1 ="select distinct mc.contractnumber, cpp.lastname||' '||cpp.firstname||' '||coalesce(cpp.middlename,'') as cpplastname\n" +
        ",CAST(EXTRACT(YEAR from (cpp.birthday)) as INTEGER) as ycpp,cpp.id as cppid\n" +
        "        from MedContract mc \n" +
        "        left join ContractAccount ca on mc.id=ca.contract_id\n" +
        "        left join ContractAccountMedService cams on cams.account_id=ca.id\n" +
        "        left join ServedPerson sp on cams.servedPerson_id = sp.id\n" +
        "        left join PriceMedService pms on pms.id=cams.medService_id\n" +
        "        left join PricePosition pp on pp.id=pms.pricePosition_id\n" +
        "        left join ContractAccountOperationByService caos on caos.accountMedService_id=cams.id\n" +
        "        left join ContractAccountOperation cao on cao.id=caos.accountOperation_id and cao.dtype='OperationAccrual'\n" +
        "        left join ContractPerson cp on cp.id=sp.person_id left join patient cpp on cpp.id=cp.patient_id\n" +
        "        where mc.id='"+pid+"'  and cao.id is null and caos.id is null and  CAST(EXTRACT(YEAR from (cpp.birthday)) as INTEGER) is not null group by mc.id,cpp.id" ;
    var list1 = aCtx.manager.createNativeQuery(sqlQuery1).getResultList();
    var obj = list1.size()>0 ? list1.get(0) : null ;
    if (obj!=null) {
        map.put("contractNumber", obj[0]);
        map.put("servedFIO", obj[1]);
        map.put("servedYear", obj[2]);
        var servedPerson = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,java.lang.Long(obj[3])) ;
        map.put("address",servedPerson.addressRegistration);
    }
    return map;
}