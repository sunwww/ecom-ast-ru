function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateTime(new java.sql.Time (date.getTime())) ;
	aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
function onPreDelete(aId, aCtx) {
	aCtx.manager.createNativeQuery("delete from TransfusionMonitoring where transfusion_id='"+aId+"'").executeUpdate() ;
	aCtx.manager.createNativeQuery("delete from TransfusionReagent where transfusion_id='"+aId+"'").executeUpdate() ;
	//milamesher 03.04.2018
    aCtx.manager.createNativeQuery("delete from transfusioncomplication where transfusion_id='"+aId+"'").executeUpdate() ;
}

/**
 * При создании
 */
//milamesher 30.03.2018 no requirements so i need to check reagents #95
function checkReagentsAndChecks(aForm, aEntity) {
    if (!aEntity.getBloodPreparation().getCode().equals("PLAZMA") &&
		!aEntity.getBloodPreparation().getCode().equals("KRIO") &&
		!aEntity.getBloodPreparation().getCode().equals("TROMB") &&
        (aForm.getReagentForm1().getReagent()=='0' || aForm.getReagentForm1().getSeries()=='' ||  aForm.getReagentForm1().getExpirationDate()==''
            || aForm.getReagentForm2().getReagent()=='0' || aForm.getReagentForm2().getSeries()==''
            ||  aForm.getReagentForm2().getExpirationDate()=='' ))
        throw "Не вся информация по реактивам заполнена!";
    if (!aEntity.getBloodPreparation().getCode().equals("PLAZMA") && !aEntity.getBloodPreparation().getCode().equals("TROMB") && !aEntity.getBloodPreparation().getCode().equals("KRIO")
        && (aForm.getPatBloodGroupCheck()=='0'))
        throw "Не вся информация по контрольным проверкам заполнена! Группа крови реципиента обязательна для заполнения!";
}
function onCreate(aForm, aEntity, aContext) {
    checkReagentsAndChecks(aForm, aEntity);
	saveAdditionData(aForm,aEntity,aContext) ;
}

/** Перед сохранением */
function onPreSave(aForm,aEntity, aCtx) {
	var date = new java.util.Date() ;
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCtx) {
    checkReagentsAndChecks(aForm, aEntity);
	aCtx.manager.persist(aEntity) ;
	saveAdditionData(aForm,aEntity,aCtx) ;
}


function saveAdditionData(aForm,aEntity,aCtx) {
	var medCase = aEntity.medCase ; 
	if(medCase!=null && medCase.parent!=null &&medCase.parent.dischargeTime!=null) {
		throw "Нельзя добавить переливание в закрытый случай" ;
	}
	
	// Мониторинг 
	saveMonitoring(aEntity,aCtx.manager,aForm.getMonitorForm0(),'0') ;
	saveMonitoring(aEntity,aCtx.manager,aForm.getMonitorForm1(),'1') ;
	saveMonitoring(aEntity,aCtx.manager,aForm.getMonitorForm2(),'2') ;
	// Реактивы
	saveReagent(aEntity, aCtx.manager, aForm.getReagentForm1(), '1');
	saveReagent(aEntity, aCtx.manager, aForm.getReagentForm2(), '2');
	saveArray(aEntity, aCtx.manager,aForm.getComplications()
			,Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionReaction
			,[]
			,["var objNew=new Packages.ru.ecom.mis.ejb.domain.medcase.TransfusionComplication() ;"
			  ,"objNew.setTransfusion(aEntity)"
			  ,"objNew.setReaction(objS);"]
			,"from TransfusionComplication where transfusion_id='"+aEntity.getId()+"' and reaction_id"
			) ;
	
}

function saveReagent(aEntity,aManager,aReagentForm,aIdReagent) {
	saveArrayForm(aEntity,aManager,aReagentForm
			,["var reagent=aManager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionReagent,aForm.reagent);"
			  ,"var reagent=aManager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionReagent,aForm.reagent);"]
	,"Packages.ru.ecom.mis.ejb.domain.medcase.TransfusionReagent"
	,["objNew.setTransfusion(aEntity)"
	  ,"objNew.setNumberReagent("+aIdReagent+")"
	  ,"objNew.setReagent(reagent);"
	  ,"objNew.setSeries(aForm.series);"
	  ,"objNew.setExpirationDate(Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aForm.expirationDate));"
	  ]
	,"from TransfusionReagent where transfusion_id='"+aEntity.getId()+"' and numberReagent='"+aIdReagent+"'") ;
	
}
function saveMonitoring(aEntity,aManager,aMonitorForm,aIdMonitor) {
	saveArrayForm(aEntity,aManager,aMonitorForm
			,["var urineColor=aManager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocUrineColor,aForm.urineColor);"]
			,"Packages.ru.ecom.mis.ejb.domain.medcase.TransfusionMonitoring"
			,["objNew.setTransfusion(aEntity)"
			  ,"objNew.setHourAfterTransfusion("+aIdMonitor+")"
			  ,"objNew.setPulseRate(aForm.pulseRate);"
			  ,"objNew.setBloodPressureLower(aForm.bloodPressureLower);"
			  ,"objNew.setBloodPressureTop(aForm.bloodPressureTop);"
			  ,"objNew.setTemperature(aForm.temperature!=null&&!aForm.temperature.equals(\"\")?new java.math.BigDecimal(aForm.temperature.replaceAll(\",\",\".\")):null);"
			  ,"objNew.setUrineColor(urineColor);"
			  ,"objNew.setDiuresis(aForm.diuresis);"
			  ]
			,"from TransfusionMonitoring where transfusion_id='"+aEntity.getId()+"' and hourAfterTransfusion='"+aIdMonitor+"'") ;
	
}
function saveArrayForm(aEntity,aManager, aForm,aMainCmd,aObjNewClass, aAddCmd,
		 aTableSql) {
	
	for (var j=0;j<aMainCmd.length;j++) {
		eval(aMainCmd[j]) ;
	}
	
	var sql ="select id as cnt "+aTableSql ;
	var count = aManager.createNativeQuery(sql).setMaxResults(1).getResultList() ;
	var objNew ;
	if (count.isEmpty()|| (!count.isEmpty()&&(+count.get(0)<1))) {
		eval("objNew=new "+aObjNewClass+"();") ;
	} else {
		var idOld=java.lang.Long.valueOf(""+count.get(0));
		eval("objNew=aManager.find("+aObjNewClass+",idOld);") ;
	}
	for (var j=0;j<aAddCmd.length;j++) {
		eval(aAddCmd[j]) ;
	}
	aManager.persist(objNew) ;
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