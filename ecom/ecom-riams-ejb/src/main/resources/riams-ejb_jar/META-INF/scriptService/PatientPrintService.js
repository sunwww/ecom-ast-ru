function printInfo(aCtx, aParams) {
	var map = new java.util.HashMap() ;
	var patient = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient
		, new java.lang.Long(aParams.get("id"))) ;
	var polList = patient.medPolicies ;
	var policy = null ;
	//for(var i = 0; i<polList.size(); i++) {	
	policy = polList.size()>0?polList.get(polList.size()-1):"" ;
	//}	
	map.put("pat", patient) ;
	
	var currentDate = new Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	var cal = java.util.Calendar.getInstance() ;
	cal.setTime(currentDate) ;
	cal.add(java.util.Calendar.DATE,14) ;
	map.put("dateTo",FORMAT_2.format(cal.getTime())) ;
	map.put("policyText", policy!=null? policy.text : "") ;
	var medcard = patient.medcard.size()>0?patient.medcard.get(0):null ;
	if (medcard!=null) {
		map.put("card.code",medcard.number) ;
		map.put("card.cardIndex",medcard.cardIndex!=null?medcard.cardIndex.name:"") ;
	} else {
		map.put("card.code","") ;
		map.put("card.cardIndex","") ;
	}
	var listInv = aCtx.manager.createQuery("from Invalidity where patient_id=:pat order by dateFrom desc")
	.setParameter("pat",patient.id).setMaxResults(1).getResultList() ;
	var inv=listInv.size()>0?listInv.get(0):null;
	var groupInv = inv!=null?inv.group:null ;
	map.put("groupInv",groupInv) ;
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