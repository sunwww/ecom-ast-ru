var map = new java.util.HashMap() ;	

function PrinCertificate(aCtx, aParams){
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
		+"		where ca.id='"+pid+"' and cao.id is null"
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