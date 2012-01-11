var map = new java.util.HashMap() ;	

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