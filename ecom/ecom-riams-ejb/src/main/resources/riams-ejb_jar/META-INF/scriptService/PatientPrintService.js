function printInfo(aCtx, aParams) {
	var map = new java.util.HashMap() ;
	var patient = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient
		, new java.lang.Long(aParams.get("id"))) ;
	var polList = patient.medPolicies ;
	var policy = null ;
	for(var i = 0; i<polList.size(); i++) {	
		policy = polList.get(i) ;
	}	
	map.put("pat", patient) ;
	map.put("policyText", policy!=null? policy.text : "") ;
	var medcard = patient.medcard.size()>0?patient.medcard.get(0):null ;
	if (medcard!=null) {
		map.put("card.code",medcard.number) ;
		map.put("card.cardIndex",medcard.cardIndex!=null?medcard.cardIndex.name:"") ;
	} else {
		map.put("card.code","") ;
		map.put("card.cardIndex","") ;
	}
	var priv = patient.privileges.size()>0?patient.privileges.get(0):null ;
	map.put("priv.info","") ;
	map.put("strax",null) ;
	map.put("priv.doc",priv!=null?priv.document:null) ;
	var date = new java.util.Date() ;
	var cal = java.util.Calendar.getInstance() ;
	cal.setTime(date) ;
	cal.add(java.util.Calendar.DAY_OF_MONTH, 12) ;
	var toDate = cal.getTime() ;
	map.put("date", date) ;
	map.put("todate", toDate) ;
	
	var area = patient.getLpuArea() ;
	var areaText = "" ;
	if(area!=null) {
		areaText = area.getName() + ",  "+area.getComment()  ;
	}
	map.put("areaText", areaText) ;
	return map ;
	//throw aParams+"" ;
}
function printAgreement(aCtx, aParams) {
	var map = new java.util.HashMap() ;
	return map ;
}