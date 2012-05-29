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
function updateWorkFunctionByMedService(aCtx,aIds, aWorkFunction, aLpu, aAction) {
	if (aAction=="delete") deleteWorkFunctionByMedService(aCtx,aIds, aWorkFunction, aLpu) ;
	if (aAction=="add") addWorkFunctionByMedService(aCtx,aIds, aWorkFunction, aLpu) ;
	return ;
}
function deleteWorkFunctionByMedService(aCtx,aIds, aWorkFunction, aLpu) {
	var ids = aIds.split(",") ; 
	var add="" ;
	if (+aLpu>0) add=" and lpu_id='"+aLpu+"'" ;
	for (var i = 0; i< ids.length ; i++) {
		var s = ids[i];
	    aCtx.manager.createNativeQuery("delete from WorkFunctionService where medService_id ='"+s+"' and vocWorkFunction_id='"+aWorkFunction+"'"+add) 
			.executeUpdate() ;
    }
	return ;
}
function addWorkFunctionByMedService(aCtx,aIds, aWorkFunction,aLpu) {
	if (+aWorkFunction<1) return  ;
	var ids = aIds.split(",") ; 
	var vwf = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction,java.lang.Long.valueOf(aWorkFunction)) ;
	var lpu = null ;
	if (+aLpu>0) lpu = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.lpu.MisLpu,java.lang.Long.valueOf(aLpu)) ;
	for (var i = 0; i< ids.length ; i++) {
      var s = ids[i];
      //throw "select count(*) from WorkFunctionService where medService_id='"+s+"' and vocWorkFunction_id='"+aWorkFunction+"'" ;
      var cnt=aCtx.manager.createNativeQuery("select count(*) from WorkFunctionService where medService_id='"+s+"' and vocWorkFunction_id='"+aWorkFunction+"'").getSingleResult() ;
      if (+cnt==0) {
      	//var cnt1=aCtx.manager.createNativeQuery("select count(*) from MedService where id='"+s+"' and vocMedService_id is not null").getSingleResult() ;
      	//if (+cnt1>0) {
    	  var ms = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedService,java.lang.Long.valueOf(s)) ;
    	  if (ms.vocMedService!=null) {
    		  var wfs = new Packages.ru.ecom.mis.ejb.domain.worker.WorkFunctionService() ;
      		
      		
      		wfs.setMedService(ms) ;
      		wfs.setVocWorkFunction(vwf) ;
      		wfs.setLpu(lpu) ;
	      //aCtx.manager.createNativeQuery("insert into WorkFunctionService set medService_id ='"+s+"' , vocWorkFunction_id='"+aWorkFunction+"'") 
			//.executeUpdate() ;
      		aCtx.manager.persist(wfs) ;
    	  }
		//}
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