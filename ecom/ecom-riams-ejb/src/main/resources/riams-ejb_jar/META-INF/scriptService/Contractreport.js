var map = new java.util.HashMap() ;	

function printPriceList(aCtx,aParams) {
	var id = aParams.get("id") ;
	var date=aParam.get("date") ;
	var dateSql="CURRENT_DATE" ;
	if (date) {
		dateSql = "to_date('"+date+"','dd.mm.yyyy')"
	}
	var sqlMainGroup = "select id,code,name,comment from priceposition where parent_id is null and dtype='PriceGroup' order by code" ;
	var list = aCtx.manager.createNativeQuery(sqlMainGroup).getResultList() ;
	var ret = new java.util.ArrayList() ;
	for (var i=0;i<list.size();i++) {
		var obj = list.get(0) ;
		var mainWqr = getAdditionByGroup(obj,aCtx.manager);
		ret.add(mainWqr) ;
	}
	map.put("mainPrice",ret) ;
	return map ;
}

function getAdditionByGroup(aGroupObj,aManager) {
	var wqr = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	// Код группы
	wqr.set1(aGroupObj[1]) ;
	// Наименование группы
	wqr.set2(aGroupObj[2]) ;
	// Комментарий к группе
	wqr.set3(aGroupObj[3]) ;
	// Услуги
	var sqlService="select id,code,name,cost,expunit from priceposition where parent_id ='"+aGroupObj[0]+"' and dtype='PricePosition' order by code" ;
	var listService=aManager.createNativeQuery(sqlService).getResultList() ;
	var listWqrService = new java.util.ArrayList() ;
	for (var i=0;i<listService.size();i++) {
		var objS = listService.get(i) ;
		var wqrService = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		wqrService.set1(objS[1]) ;	wqrService.set2(objS[2]) ;
		wqrService.set3(objS[3]) ;	wqrService.set4(objS[4]) ;
		listWqrService.add(wqrService) ;
	}
	wqr.set5(listWqrService) ;
	// Подгруппы
	var sqlGroup = "select id,code,name,comment from priceposition where parent_id='"+aGroupObj[0]+"' and dtype='PriceGroup' order by code" ;
	var listGroup = aCtx.manager.createNativeQuery(sqlGroup).getResultList() ;
	var listWqrGroup = new java.util.ArrayList() ;
	for (var i=0;i<list.size();i++) {
		var objG = list.get(0) ;
		var wqrGroup = getAdditionByGroup(objG,aManager);
		listWqrGroup.add(wqrGroup) ;
	}
	wqr.set6(listWqrGroup) ;
	return wqr ;
}
function FinRep(aCtx, aParams){
	var sqlQuery = "SELECT " +
			"MC.id," +
			"CA.balancesum, " +
			"getKontragent(CA.id,' '), " +
			"getDateNum(CA.id,' '), " +
			"getSumKOplate(CA.id), " +
			"getsaldoEnd(CA.id, ' ') " +
			"FROM " +
			"  contractaccount as CA " +
			"LEFT JOIN servedPerson SP ON CA.servedperson_id=SP.id " +
			"LEFT JOIN medcontract MC ON SP.contract_id=MC.id " +
			"WHERE CA.dateFrom>=to_date('"+aParams.get("dateFrom")+"', 'dd.mm.yyyy') " +
			" AND CA.dateFrom<=to_date('"+aParams.get("dateTo")+"', 'dd.mm.yyyy') ";
	var list = aCtx.manager.createNativeQuery(sqlQuery).getResultList();
	var servisec = new java.util.ArrayList() ;
	for (var i = 0 ;i<list.size();i++) {
		var ref = new Packages.ru.ecom.mis.ejb.form.birth.ChildBirthForm() ;
		var numbercontr = list.get(i)[0];
		if(numbercontr==null) numbercontr=" ";
		var balasncesum = list.get(i)[1];
		if(balasncesum==null) balasncesum=" ";
		var kontragent = list.get(i)[2];
		if(kontragent==null) kontragent=" ";
		var datenum = list.get(i)[3];
		if(datenum==null) datenum=" ";
		var sumkopl = list.get(i)[4];
		if(sumkopl==null) sumkopl=" ";
		var saldoend = list.get(i)[5];
		if(saldoend==null) saldoend=" ";
		ref.setTreatmentFeatures(numbercontr);
		ref.setNotPostNatalDisease(balasncesum);
		ref.setPostNatalDisease(kontragent);
		ref.setHaemorrhoids(datenum);
		ref.setPerineumEdema(sumkopl);
		ref.setVulvaEdema(saldoend);
		
		servisec.add(ref);
	}
	map.put("serv",servisec) ;

	
	
	return map;
}