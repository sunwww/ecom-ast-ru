/**
 * При создании
 */
function onCreate(aForm, aEntity, aContext) {
	saveAdditionData(aForm,aEntity,aContext) ;
}

/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCtx) {
	aCtx.manager.persist(aEntity) ;
	/*
	 var date = new java.util.Date() ;
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	*/
	saveAdditionData(aForm,aEntity,aCtx) ;
	
}
function saveAdditionData(aForm,aEntity,aCtx) {

	
	// Медицинские услуги
	saveArray(aEntity, aCtx.manager,aForm.getRisks()
			,Packages.ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispRisk
			,[]
			,["var objNew=new Packages.ru.ecom.mis.ejb.domain.extdisp.ExtDispRisk() ;"
			  ,"objNew.setCard(aEntity)"
			  ,"objNew.setDispRisk(objS);"]
			,"from ExtDispRisk where card_id='"+aEntity.getId()+"' and dispRisk_id"
			) ;

	
		
	

	
}

function saveArray(aEntity,aManager, aIdString,aClazz,aMainCmd, aAddCmd,
		 aTableSql) {
	//var obj = new Packages.org.json.JSONObject(aJsonString) ;
	//var ar = obj.getJSONArray("childs");
	if (aIdString!=null && aIdString!="" && aIdString!="null") {
		var ids = aIdString.split(",") ;
		
		for (var j=0;j<aMainCmd.length;j++) {
			eval(aMainCmd[j]) ;
		}
		for (var i = 0; i < ids.length; i++) {
			var jsId = ids[i];
			if (jsId!=null && jsId!="" || jsId=="0") {
				//System.out.println("    id="+jsonId) ;
				
				var jsonId=java.lang.Long.valueOf(jsId) ;
				
				var sql ="select count(*) as cnt "+aTableSql+"='"+jsonId+"'" ;
				var count = aManager.createNativeQuery(sql).setMaxResults(1).getResultList() ;
				if (count.isEmpty()|| (!count.isEmpty()&&(+count.get(0)<1))) {
					
					var objS = aManager.find(aClazz,jsonId) ;
					
					for (var j=0;j<aAddCmd.length;j++) {
						eval(aAddCmd[j]) ;
						//if (j>3)throw ""+aAddCmd[j] ;
					}
					//throw ""+objS ;
					aManager.persist(objNew) ;
					
				}
			}
		}
		
		sql = "delete "+aTableSql+" not in ("+aIdString+") " ;
		aManager.createNativeQuery(sql).executeUpdate();
	} else {
		sql = "delete "+aTableSql+" !='0' " ;
		aManager.createNativeQuery(sql).executeUpdate();
	}
	
}