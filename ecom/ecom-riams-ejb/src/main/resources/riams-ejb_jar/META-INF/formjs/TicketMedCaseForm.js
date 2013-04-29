function onPreCreate(aForm, aCtx) {
	var param = new java.util.HashMap() ;
				param.put("obj","Ticket") ;
				param.put("permission" ,"dateClosePeriod") ;
				param.put("date",
					Packages.ru.nuzmsh.util.format.DateFormat.formatToJDBC(aForm.dateStart)) ;
				param.put("id", aForm.id) ;
	var isClosedPeriod=aCtx.serviceInvoke("WorkerService", "checkPermission", param)+"";
	if (+isClosedPeriod==1) {
		var param1 = new java.util.HashMap() ;
		param1.put("obj","Ticket") ;
		param1.put("permission" ,"createDataInClosePeriod") ;
		param1.put("id", aForm.id) ;
		var isCreateClose = aCtx.serviceInvoke("WorkerService", "checkPermission", param1)+""; 
		//throw isDeleteClose ;
		if (+isCreateClose!=1) throw "У вас стоит запрет на создание данных в закрытом периоде";
	}
	var date = new java.util.Date() ;
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateTime(new java.sql.Time (date.getTime())) ;
	aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	//aCtx.getSessionContext().set("dCreate","123");
}
function onPreDelete(aId, aCtx) {
	var param = new java.util.HashMap() ;
				param.put("obj","Ticket") ;
				param.put("permission" ,"dateClosePeriod") ;
				param.put("id", aId) ;
	var isClosedPeriod=aCtx.serviceInvoke("WorkerService", "checkPermission", param)+"";
	if (+isClosedPeriod==1) {
		var param1 = new java.util.HashMap() ;
		param1.put("obj","Ticket") ;
		param1.put("permission" ,"deleteDataInClosePeriod") ;
		param1.put("id", aId) ;
		var isDeleteClose = aCtx.serviceInvoke("WorkerService", "checkPermission", param1)+""; 
		//throw isDeleteClose ;
		if (+isDeleteClose!=1) throw "У вас стоит запрет на удаление данных в закрытом периоде";
	}
	aCtx.manager.createNativeQuery("delete from RenderedService where ticket_id='"+aId+"'").executeUpdate() ;
}

/**
 * При создании
 */
function onCreate(aForm, aEntity, aContext) {
	saveAdditionData(aForm,aEntity,aCtx.manager) ;
}

/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCtx) {
	aCtx.manager.persist(aEntity) ;
	var date = new java.util.Date() ;
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	saveAdditionData(aForm,aEntity,aCtx.manager) ;
}
function saveAdditionData(aForm,aEntity,aManager) {
	// Сопутствующий диагноз
	var vocConcomType = Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseSaveInterceptor.getVocByCode(aManager,"VocPriorityDiagnosis","3") ;
	var addSql = " medCase_id="+aEntity.getId()+" and priority_id='"+vocConcomType.getId()+"'" ;
	saveArray(aManager,aForm.getConcomitantDiseases(),addSql, "Diagnosis", "medCase_id,priority_id"
			, "'"+aEntity.getId()+"','"+vocConcomType.getId()+"'" , "idc10_id" ) ;
	// Медицинские услуги
	saveArray(aManager,aForm.getMedServices(),"parent_id="+aEntity.getId()+" and dtype='ServiceMedCase' ","MedCase","parent_id,DTYPE,noActuality"
			,"'"+aEntity.getId()+"','ServiceMedCase','0'","medService_id") ;
	if(aEntity.parent==null) {
		var spo = new Packages.ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase() ;
		var workFunction = aEntity.getWorkFunctionExecute() ; 
		spo.setOwnerFunction(workFunction) ;
		spo.setDateStart(aEntity.getDateStart()) ;
		spo.setLpu(workFunction.worker.getLpu()) ;
		spo.setPatient(aEntity.getPatient()) ;
		spo.setStartFunction(workFunction) ;
		spo.setServiceStream(aEntity.getServiceStream()) ;
		spo.noActuality(false) ;
		aCtx.manager.persist(spo) ;
		aVisit.setParent(spo) ;
	}

	
}

function saveArray(aManager, aJsonString, aAddSql, aTableName
		, aFieldParent, aIdEntity, aFieldChildren) {
	//try {
		var obj = new Packages.org.json.JSONObject(aJsonString) ;
		var ar = obj.getJSONArray("childs");
		var ids = new Packages.java.lang.StringBuilder() ;
		var sql = new Packages.java.lang.StringBuilder () ;

		
		for (var i = 0; i < ar.length(); i++) {
			var child = ar.get(i);
			var jsonId = java.lang.String.valueOf(child.get("value"));
			if (jsonId!=null && jsonId!="" || jsonId=="0") {
				//System.out.println("    id="+jsonId) ;
				ids.append(",").append(jsonId) ;
				var sql = new java.lang.StringBuilder() ;
				sql.append("select count(*) as cnt from ").append(aTableName).append(" where ").append(aAddSql).append(" and ").append(aFieldChildren).append("='").append(jsonId).append("'") ;
				var count = aManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
				if (count.isEmpty()|| (!count.isEmpty()&&(+count.get(0)<1))) {
					sql = new java.lang.StringBuilder() ;
					
					sql.append("insert into ").append(aTableName).append(" ( ")
						.append(aFieldChildren).append(",").append(aFieldParent)
						.append(") values ('")
						.append(jsonId).append("',").append(aIdEntity).append(")") ;
					aManager.createNativeQuery(sql.toString()).executeUpdate() ;
					
				}
			}
		}
		if (ids.length()>0) {
			sql = new java.lang.StringBuilder() ;
			sql.append("delete from ").append(aTableName)
				.append(" where ").append(aAddSql).append(" and ").append(aFieldChildren)
				.append(" not in (").append(ids.substring(1)+") ") ;
			aManager.createNativeQuery(sql.toString()).executeUpdate();
		} else {
		}
	/*} catch (e) {
		var sql = new java.lang.StringBuilder() ;
		sql.append("delete from ").append(aTableName)
			.append(" where ").append(aAddSql);
		aManager.createNativeQuery(sql.toString()).executeUpdate() ;
		// TODO Auto-generated catch block
		
	}*/
}

