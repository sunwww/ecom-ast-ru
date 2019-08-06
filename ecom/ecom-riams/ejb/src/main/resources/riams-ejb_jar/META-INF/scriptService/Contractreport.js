var map = new java.util.HashMap() ;	
function updateCAOSbyCharged(aCtx,aDate1,aDate2) {
	try {
	var caosIds = "";
	var sql = "";
	
	for (var i=0;i<8;i++) {
		sql="	select";
		sql=sql+"	list(''||case when (wfs.lpu_id is null or wfs.lpu_id=w.lpu_id) and wfs.vocworkfunction_id=wf.workfunction_id";
		sql=sql+"	and ms.isPoliclinic='1' and ms.finishDate is null";
		sql=sql+"	and mc.dateStart between cao.operationdate and cao.operationdate+"+i+" and caos.medcase_id is null";
		sql=sql+"	then caos.id else null end)";
		sql=sql+"	from MedCase mc";
		sql=sql+"	left join Patient p on p.id=mc.patient_id";
		sql=sql+"	left join ContractPerson cp on cp.patient_id=p.id";
		sql=sql+"	left join ServedPerson sp on sp.person_id=cp.id";
		sql=sql+"	left join ContractAccountOperation cao on cao.account_id=sp.account_id and cao.dtype='OperationAccrual'";
		sql=sql+"	left join ContractAccountOperationByService caos on caos.accountOperation_id=cao.id and caos.medcase_id is null";
		sql=sql+"	left join ContractAccountMedService cams on cams.id=caos.accountMedService_id";
		sql=sql+"	left join ContractAccountOperationByService caos1 on caos1.medcase_id=mc.id";
		sql=sql+"	left join pricemedservice pms on cams.medService_id=pms.id";
		sql=sql+"	left join medservice ms on pms.medservice_id=ms.id";
		sql=sql+"	left join workfunctionservice wfs on ms.id=wfs.medservice_id";
		sql=sql+"	left join WorkFunction wf on wf.id=mc.workFunctionExecute_id";
		sql=sql+"	left join Worker w on w.id=wf.worker_id";
		sql=sql+"	left join Patient wp on wp.id=w.person_id";
		sql=sql+"	left join VocServiceStream vss on vss.id=mc.serviceStream_id";
		sql=sql+"	where mc.dtype='Visit'";
		sql=sql+"	and mc.dateStart between to_date('"+aDate1+"','dd.mm.yyyy') and to_date('"+aDate2+"','dd.mm.yyyy')";
		sql=sql+"	and vss.code='CHARGED' and (mc.noActuality='0' or mc.noActuality is null)";
		if (i>0){
            sql=sql+"	and caos1.id is null";
		}

		sql=sql+"	and cao.repealoperation_id is null";
		//sql=sql+"	group by p.lastname,wp.lastname,mc.id,mc.username,caos.id,wfs.lpu_id,w.lpu_id,wfs.vocworkfunction_id,wf.workfunction_id";
		//sql=sql+"	,ms.isPoliclinic,mc.dateStart,cao.operationdate,caos.medcase_id,ms.finishDate";
		var caosList = aCtx.manager.createNativeQuery(sql).getResultList();
		if (caosList!=null&&caosList.size()>0) {
			caosIds = ""+caosList.get(0);
			if (caosIds!="") {
				sql ="";
				sql=sql+"	﻿update ContractAccountOperationByService caos222";
				sql=sql+"	set medcase_id=";
				sql=sql+"	(";
				sql=sql+"	select max(mc.id)";
				sql=sql+"	from MedCase mc";
				sql=sql+"	left join Patient p on p.id=mc.patient_id";
				sql=sql+"	left join ContractPerson cp on cp.patient_id=p.id";
				sql=sql+"	left join ServedPerson sp on sp.person_id=cp.id";
				sql=sql+"	left join ContractAccountOperation cao on cao.account_id=sp.account_id and cao.dtype='OperationAccrual'";
				sql=sql+"	left join ContractAccountOperationByService caos on caos.accountOperation_id=cao.id and caos.medcase_id is null";
				sql=sql+"	left join ContractAccountMedService cams on cams.id=caos.accountMedService_id";
				if (i>0) {
                    sql=sql+"	left join ContractAccountOperationByService caos1 on caos1.medcase_id=mc.id";
				}
				sql=sql+"	left join pricemedservice pms on cams.medService_id=pms.id";
				sql=sql+"	left join medservice ms on pms.medservice_id=ms.id";
				sql=sql+"	left join workfunctionservice wfs on ms.id=wfs.medservice_id";
				sql=sql+"	left join WorkFunction wf on wf.id=mc.workFunctionExecute_id";
				sql=sql+"	left join Worker w on w.id=wf.worker_id";
				sql=sql+"	left join Patient wp on wp.id=w.person_id";
				sql=sql+"	left join VocServiceStream vss on vss.id=mc.serviceStream_id";
                sql=sql+"	left join medcase smc on smc.parent_id=mc.id";
				sql=sql+"	where mc.dtype='Visit' and smc.dtype='ServiceMedCase' and smc.medservice_id=ms.id";
				sql=sql+"	and mc.dateStart between cao.operationdate and cao.operationdate+"+i;
				sql=sql+"	and vss.code='CHARGED' and (mc.noActuality='0' or mc.noActuality is null)";
				sql=sql+"	and cao.repealoperation_id is null";
				if (i>0) {
                    sql=sql+"	and caos1.id is null ";
				}
                sql=sql+	" and caos.id=caos222.id";
				sql=sql+"	and (wfs.lpu_id is null or wfs.lpu_id=w.lpu_id) and wfs.vocworkfunction_id=wf.workfunction_id";
				sql=sql+"	and ms.isPoliclinic='1' and ms.finishDate is null";
				sql=sql+"	and caos.medcase_id is null";
				sql=sql+"	)";
				sql=sql+"	where id in (";
				sql=sql+caosIds;
				sql=sql+" )";
				aCtx.manager.createNativeQuery(sql).executeUpdate() ;
			}			
		}
	}
	} catch (e) {
		throw ""+e;
	}
}

function printPriceList(aCtx,aParams) {
	//var id = aParams.get("id") ;
	//var date=aParam.get("date") ;
/*	var dateSql="CURRENT_DATE" ;
	if (date) {
		dateSql = "to_date('"+date+"','dd.mm.yyyy')"
	}*/
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