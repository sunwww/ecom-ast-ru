var map = new java.util.HashMap() ;

function updatePlanStartDate (aCtx, aIds, aDate,aTime) {
	updatePlanDate(aCtx,aIds,"Start", aDate, aTime);
}
function updatePlanEndDate (aCtx, aIds, aDate,aTime) {
	updatePlanDate(aCtx,aIds,"End", aDate, aTime);
}
function deletePrescription(aCtx,aIds) {
	aCtx.manager.createNativeQuery("delete from Prescription where id in ("+aIds+")").executeUpdate() ;
}
function updatePlanDate (aCtx,aIds, aField, aDate,aTime) {
	var tm = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlTime(aTime);
	var dt = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aDate);
	var ids = aIds.split(",") ;
	for (var i = 0; i< ids.length ; i++) {
            var s = ids[i];
            
    aCtx.manager.createNativeQuery("update Prescription set plan"+aField+"Date =:dt,plan"+aField+"Time =:tm where id="+s)
		.setParameter("dt",dt)
		.setParameter("tm",tm)
		.executeUpdate() ;
    }
}