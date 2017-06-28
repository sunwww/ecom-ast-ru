/**
 * Перед созданием
 * 
 */
function onPreCreate(aForm, aCtx) {
	var date = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.getFinishDate());
	var pat = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,aForm.getPatient());
	if (pat.getDeathDate()!=null) {
		var deathDate = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(pat.getDeathDate(),"yyyy-MM-dd");
		if (date.getTime() > deathDate.getTime()) {
			throw "Невозможно создать карту диспансеризации с датой окончания позже даты смерти пациента: "
				+Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(deathDate);
		} 
}
}
/**
 * При создании
 */
function onCreate(aForm, aEntity, aCtx) {
	saveAdditionData(aForm,aEntity,aCtx) ;
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}

/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCtx) {
	aCtx.manager.persist(aEntity) ;
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
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

/**
 * Перед удалением
 */
function onPreDelete(aEntityId, aContext) {
	
	var sql ="delete FROM extdispappointment e using extdispcard ed where ed.id = e.dispcard_id and ed.id = '"+aEntityId+"'";
	aContext.manager.createNativeQuery(sql).executeUpdate();
}

/**
 * При удалении
 */
function onDelete(aEntityId, aContext) {
	
	
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
			var jsId = ids[i].trim();
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
		//throw sql ;
		aManager.createNativeQuery(sql).executeUpdate();
	} else {
		sql = "delete "+aTableSql+" !='0' " ;
		aManager.createNativeQuery(sql).executeUpdate();
	}
	
}