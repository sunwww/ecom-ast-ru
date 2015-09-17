var map = new java.util.HashMap() ;

function printPrescription(aCtx,aParams) {
	var list = aCtx.createNativeQuery("").getResultList();
	for (var i=0;i<list.size();i++) {
		var obj = list.get(0) ;
		
	}
	map.put("list",list) ;
	return map ;
}

function updatePlanStartDate (aCtx, aIds, aDate,aTime) {
	updatePlanDate(aCtx,aIds,"Start", aDate, aTime);
	return ;
 
}
function updatePlanEndDate (aCtx, aIds, aDate,aTime) {
	updatePlanDate(aCtx,aIds,"End", aDate, aTime);
	return ;
 
}
function deletePrescription(aCtx,aIds) {
	aCtx.manager.createNativeQuery("delete from Prescription where id in ("+aIds+")").executeUpdate() ;
	return ;
}
function updatePlanDate (aCtx,aIds, aField, aDate,aTime) {
	var tm = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlTime(aTime);
	var dt = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aDate);
	var ids = aIds.split(",") ; ;
	for (var i = 0; i< ids.length ; i++) {
            var s = ids[i];
            
    aCtx.manager.createNativeQuery("update Prescription set plan"+aField+"Date =:dt,plan"+aField+"Time =:tm where id="+s)
		.setParameter("dt",dt)
		.setParameter("tm",tm)
		.executeUpdate() ;
    }
	/*aCtx.manager.createNativeQuery("update Prescription set plan"+aField+"Date =:dt,plan"+aField+"Time =:tm")
		.setParameter("dt",dt)
		.setParameter("tm",dt)
		.executeUpdate() ;
	*/
	return ;
 
}