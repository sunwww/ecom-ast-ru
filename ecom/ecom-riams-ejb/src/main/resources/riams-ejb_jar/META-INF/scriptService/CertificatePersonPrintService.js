var map = new java.util.HashMap() ;	

function PrinCertificate(aCtx, aParams){
	var pid = aParams.get("id");
	var sqlQuery ="select MS.name as nm, CAMS.countMedService*PP.cost as cc " 
			+", CAMS.countMedService as cnt " 
			+" from ContractAccountMedService CAMS "
			+" left join PricePosition PP ON PP.id = CAMS.medservice_id"
			+" left join PriceMedService PMS on pp.id=pms.pricePosition_id"
			+" left join MedService MS ON MS.id = PMS.medService_id"
			+" where CAMS.account_id = ";
	sqlQuery=sqlQuery+pid;
	var list = aCtx.manager.createNativeQuery(sqlQuery).getResultList();
	var servisec = new java.util.ArrayList() ;
	var allcost;
	allcost = 1-1;
	for (var i = 0; i<list.size(); i++) {
		var ref = new Packages.ru.ecom.mis.ejb.form.contract.PricePositionForm() ;
		var medserv = list.get(i)[0];
		var cost = list.get(i)[1];
		var count = list.get(i)[2];
		allcost = allcost + cost*1;
		ref.name = medserv;
		ref.cost = cost;
		ref.code = count;
		servisec.add(ref);
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