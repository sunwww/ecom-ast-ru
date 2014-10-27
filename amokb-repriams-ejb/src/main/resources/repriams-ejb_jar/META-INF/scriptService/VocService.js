function updateStartDateByMedService(aCtx, aId, aIds, aDate) {
	updateDateByMedService(aCtx, aId, aIds ,"start", aDate);
	return ;
}
function updateEndDateByMedService(aCtx, aId,aIds, aDate) {
	updateDateByMedService(aCtx, aId ,aIds,"finish", aDate);
	return ;
}
function deleteByMedService(aCtx,aIds) {
	aCtx.manager.createNativeQuery("delete from MedService where id in ("+aIds+")").executeUpdate() ;
	return ;
}
function updateWorkFunctionByMedService(aCtx,aIds, aWorkFunction, aAction) {
	if (aAction=="delete") deleteWorkFunctionByMedService(aCtx,aIds, aWorkFunction) ;
	if (aAction=="add") addWorkFunctionByMedService(aCtx,aIds, aWorkFunction) ;
	return ;
}
function deleteWorkFunctionByMedService(aCtx,aIds, aWorkFunction) {
	var ids = aIds.split(",") ; ;
	for (var i = 0; i< ids.length ; i++) {
		var s = ids[i];
	    aCtx.manager.createNativeQuery("delete from WorkFunctionService where medService_id ='"+s+"' and vocWorkFunction_id='"+aWorkFunction+"'") 
			.executeUpdate() ;
    }
	return ;
}
function addWorkFunctionByMedService(aCtx,aIds, aWorkFunction) {
	var ids = aIds.split(",") ; ;
	for (var i = 0; i< ids.length ; i++) {
      var s = ids[i];
      var cnt=aCtx.manager.createNativeQuery("select count(*) from WorkFunctionService where medService_id='"+s+"' and vocWorkFunction_id='"+aWorkFunction+"'").getSingleResult() ;
      if (+cnt==0) {
      	var cnt1=aCtx.manager.createNativeQuery("select count(*) from MedService where id='"+s+"' and vocMedService_id is not null").getSingleResult() ;
      	if (+cnt1>0) {
	      aCtx.manager.createNativeQuery("insert into WorkFunctionService set medService_id ='"+s+"' , vocWorkFunction_id='"+aWorkFunction+"'") 
			.executeUpdate() ;
		}
	  }
    }
	return ;
}
function updateDateByMedService (aCtx,aId,aIds, aField, aDate) {
	var dt = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aDate);
	var znak ;
	if (aField=="start") {znak="<";} else {znak=">"} ;
	var prov = aCtx.manager.createNativeQuery("select count(*) from MedService where id="+aId+" and ("+aField+"Date "
		+znak+"= :dt or "+aField+"Date is null)")
		.setParameter("dt",dt)
		.getSingleResult() ;
	if (+prov==0) {
		if (aField=="start") {throw "Дата начала родительской категории должна быть меньше или равна изменяемой даты";}
		else {throw "Дата окончания родительской категории должна быть больше или равна изменяемой даты";}
	}
	var ids = aIds.split(",") ; ;
	for (var i = 0; i< ids.length ; i++) {
            var s = ids[i];
            
      aCtx.manager.createNativeQuery("update MedService set "+aField+"Date =:dt where id="+s)
		.setParameter("dt",dt)
		.executeUpdate() ;
    }
	return ;
}