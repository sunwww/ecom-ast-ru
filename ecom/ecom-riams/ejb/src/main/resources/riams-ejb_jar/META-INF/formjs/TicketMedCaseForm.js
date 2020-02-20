function onPreCreate(aForm, aCtx) {
	
	//Проверка на создание талона позже даты смерти пациента 
	var pat = aCtx.manager.createQuery(" from Patient where id = :pat").setParameter("pat", aForm.getPatient()).getResultList().get(0);
	if (pat.getDeathDate()!=null&&(aForm.getNoActuality()==null
			||aForm.getNoActuality().equals(java.lang.Boolean.FALSE))) {
		var dateStart = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.getDateStart());	
		var deathDate = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(pat.getDeathDate(),"yyyy-MM-dd");
		if (dateStart.getTime() > deathDate.getTime()) {
			throw "Невозможно создать посещение. На дату приема пациент уже умер ("
				+Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(deathDate)+")";
		} 
	}
	var param = new java.util.HashMap() ;
				param.put("obj","ShortMedCase") ;
				param.put("permission" ,"dateClosePeriod") ;
				param.put("date",
					aForm.dateStart) ;
				param.put("id", 0) ;
	var isClosedPeriod=aCtx.serviceInvoke("WorkerService", "checkPermission", param)+"";
	if (+isClosedPeriod==1) {
		var param1 = new java.util.HashMap() ;
		param1.put("obj","ShortMedCase") ;
		param1.put("permission" ,"createDataInClosePeriod") ;
		param1.put("id", 0) ;
		param1.put("date",
				aForm.dateStart) ;
		var isCreateClose = aCtx.serviceInvoke("WorkerService", "checkPermission", param1)+""; 
		//throw isDeleteClose ;
		if (+isCreateClose!=1) throw "У вас стоит запрет на создание данных в закрытом периоде";
	}
/*	if (!aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Visit/EnableCreateTicketInHoliday")){
		var startDate = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.dateStart);
		var calHoliday = java.util.Calendar.getInstance();
		calHoliday.setTime(startDate);
		if (calHoliday.get(java.util.Calendar.DAY_OF_WEEK)==1) {
			throw "У вас стоит запрет на создание талонов в воскресенье ("+aForm.dateStart+")";
		}	
	}*/
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateTime(new java.sql.Time (date.getTime())) ;
	aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	//aCtx.getSessionContext().set("dCreate","123");
	if (+aForm.ambulance>0 && (aForm.ambulanceCard==null ||aForm.ambulanceCard=="")) {
		throw "Необходимо указать номер карты скорой помощи!!!" ;
	}
}
function onPreDelete(aId, aCtx) {
	var param = new java.util.HashMap() ;
				param.put("obj","ShortMedCase") ;
				param.put("permission" ,"dateClosePeriod") ;
				param.put("id", aId) ;
	var isClosedPeriod=aCtx.serviceInvoke("WorkerService", "checkPermission", param)+"";
	if (+isClosedPeriod==1) {
		var param1 = new java.util.HashMap() ;
		param1.put("obj","ShortMedCase") ;
		param1.put("permission" ,"deleteDataInClosePeriod") ;
		param1.put("id", aId) ;
		var isDeleteClose = aCtx.serviceInvoke("WorkerService", "checkPermission", param1)+""; 
		//throw isDeleteClose ;
		if (+isDeleteClose==1) {}else{throw "У вас стоит запрет на удаление данных в закрытом периоде";}
	}
	aCtx.manager.createNativeQuery("delete from Diagnosis where medcase_id="+aId).executeUpdate() ;
	aCtx.manager.createNativeQuery("delete from Medcase where parent_id="+aId).executeUpdate() ;
	//aCtx.manager.createNativeQuery("delete from RenderedService where ticket_id='"+aId+"'").executeUpdate() ;
	aCtx.manager.createNativeQuery("delete from ambulancecard where medcase_id='"+aId+"'").executeUpdate() ;
	var  l=aCtx.manager.createNativeQuery("select id,parent_id from MedCase where parent_id=(select parent_id from medcase where id="+aId+") and parent_id is not null").getResultList() ;
	if (l.size()==1) {
		aCtx.manager.createNativeQuery("update medcase set parent_id=null where id="+aId).executeUpdate() ;
		aCtx.manager.createNativeQuery("delete from medcase where id="+l.get(0)[1]).executeUpdate() ;
	}
	
}


/**
 * При создании
 */
function onCreate(aForm, aEntity, aContext) {
	saveAdditionData(aForm,aEntity,aContext) ;
}

/** Перед сохранением */
function onPreSave(aForm,aEntity, aCtx) {
	var pat = aCtx.manager.createQuery(" from Patient where id = :pat").setParameter("pat", aForm.getPatient()).getResultList().get(0);
	if (pat.getDeathDate()!=null&&(aForm.getNoActuality()==null
			||aForm.getNoActuality().equals(java.lang.Boolean.FALSE))) {
		var dateStart = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.getDateStart());	
		var deathDate = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(pat.getDeathDate(),"yyyy-MM-dd");
		if (dateStart.getTime() > deathDate.getTime()) {
			throw "Невозможно создать посещение. На дату приема пациент уже умер ("
				+Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(deathDate)+")";
		} 
	}
	
	var param = new java.util.HashMap() ;
				param.put("obj","ShortMedCase") ;
				param.put("permission" ,"dateClosePeriod") ;
				param.put("date",
					aForm.dateStart) ;
				param.put("id", aForm.id) ;
	var isClosedPeriod=aCtx.serviceInvoke("WorkerService", "checkPermission", param)+"";
	//throw +isClosedPeriod +" date="+aForm.dateStart;
	if (+isClosedPeriod==1) {
		var param1 = new java.util.HashMap() ;
		param1.put("obj","ShortMedCase") ;
		param1.put("permission" ,"editDataClosePeriod") ;
		param1.put("id", aForm.id) ;
		param1.put("date",
				aForm.dateStart) ;
		var isCreateClose = aCtx.serviceInvoke("WorkerService", "checkPermission", param1)+""; 
		//throw isDeleteClose ;
		if (+isCreateClose!=1) throw "У вас стоит запрет на создание данных в закрытом периоде";
	}
	var date = new java.util.Date() ;
	
	if (+aForm.ambulance>0 && (aForm.ambulanceCard==null ||aForm.ambulanceCard=="")) {
		throw "Необходимо указать номер карты скорой помощи!!!" ;
	}
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	//aCtx.getSessionContext().set("dCreate","123");
	if (+aEntity.workFunctionExecute.id!=+aForm.workFunctionExecute) {
		var txtD ;
		txtD="update Diary set specialist_id='"+aForm.workFunctionExecute+"' where medCase_id='"
		+aForm.id+"' and username='"+aCtx.getSessionContext().getCallerPrincipal().toString()+"'" ;
		//throw txtD ;
		aCtx.manager.createNativeQuery(txtD).executeUpdate();
	}

}


/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCtx) {
	aCtx.manager.persist(aEntity) ;
	var date = new java.util.Date() ;
	saveAdditionData(aForm,aEntity,aCtx) ;
	
}
function saveAdditionData(aForm,aEntity,aCtx) {
	if (+aForm.categoryChild>0) {
		var sql = new java.lang.StringBuilder() ;
		sql.append("update Patient set categoryChild_id='").append(aForm.categoryChild)
			.append("' where id='").append(aForm.patient).append("'") ;
		aCtx.manager.createNativeQuery(sql.toString()).executeUpdate();
	}
	var spo = null;
	if (+aForm.id>0) aCtx.manager.createNativeQuery("delete from AmbulanceCard where medCase_id="+aForm.id).executeUpdate() ;
	if (+aForm.ambulance>0 && (aForm.ambulanceCard!=null &&aForm.ambulanceCard!="")) {
		var ambCard = new Packages.ru.ecom.mis.ejb.domain.ambulance.AmbulanceCard() ;
		ambCard.setMedCase(aEntity) ;
		ambCard.setNumberCard(aForm.ambulanceCard) ;
		ambCard.setCallReceiveTime(Packages.ru.nuzmsh.util.format.DateFormat.parseSqlTime(aForm.getCallReceiveTime()));
		ambCard.setArrivalTime(Packages.ru.nuzmsh.util.format.DateFormat.parseSqlTime(aForm.getArrivalTime()));
		aCtx.manager.persist(ambCard) ;
	}
	if(aEntity.parent==null) {
		spo = new Packages.ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase() ;
		var workFunction = aEntity.getWorkFunctionExecute() ; 
		spo.setOwnerFunction(workFunction) ;
		spo.setDateStart(aEntity.getDateStart()) ;
		spo.setLpu(workFunction.worker.getLpu()) ;
		spo.setPatient(aEntity.getPatient()) ;
		spo.setStartFunction(workFunction) ;
		spo.setServiceStream(aEntity.getServiceStream()) ;
		spo.setNoActuality(false) ;
		if ((aForm.isCloseSpo!=null && aForm.isCloseSpo==true)||(aForm.emergency!=null && aForm.emergency==true)
				||(aForm.ambulance!=null && aForm.ambulance>0)) {
			
			spo.setDateFinish(aEntity.getDateStart()) ;
			spo.setFinishFunction(workFunction) ;
		}
	//	spo.setIsDiagnosticSpo(aForm.getIsDiagnosticSpo()); //признак КДО
		aCtx.manager.persist(spo) ;
		aEntity.setParent(spo) ;
		aCtx.manager.persist(aEntity) ;
	} else {
		spo=aEntity.parent;
     //   spo.setIsDiagnosticSpo(aForm.getIsDiagnosticSpo()); //признак КДО
        aCtx.manager.persist(spo) ;
		if (((aForm.isCloseSpo!=null && aForm.isCloseSpo)||(aForm.emergency!=null && aForm.emergency)) && aEntity.parent!=null) {
			//throw "Закрыть СПО: "+(aForm.isCloseSpo!=null && aForm.isCloseSpo && aEntity.parent!=null) ;
			try {
				
				aCtx.serviceInvoke("SmoVisitService", "closeSpoWithoutDiagnosis",aEntity.parent.id,aForm.getConcludingMkb(),"to_date('"+aForm.dateStart+"','dd.mm.yyyy')",aForm.workFunctionExecute) ;
			} catch(e) {
				throw "e="+e;
				e.printStackTrace();
			}
			
		}	
	}
	
	
	// Сопутствующий диагноз
	saveArray(aEntity,aCtx.manager,aForm.getConcomitantDiseases()
			,Packages.ru.ecom.expomc.ejb.domain.med.VocIdc10
			,["var vocConcomType = Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseSaveInterceptor.getVocByCode(aManager,\"VocPriorityDiagnosis\",\"3\") ;"
			  ,"aTableSql=aTableSql+\"and priority_id='\"+vocConcomType.getId()+\"' and idc10_id\";"
			  ]
			,["var objNew=new Packages.ru.ecom.mis.ejb.domain.medcase.Diagnosis() ;"
			  ,"objNew.setMedCase(aEntity)"
			  ,"objNew.setPatient(aEntity.patient)"
			  ,"objNew.setEstablishDate(aEntity.dateStart)"
			  ,"objNew.setIdc10(objS);"
			  ,"objNew.setPriority(vocConcomType);"]
			,"from Diagnosis where medCase_id='"+aEntity.getId()+"' ") ;
	/*
	// Медицинские услуги
	saveArray(aEntity, aCtx.manager,aForm.getMedServices()
			,Packages.ru.ecom.mis.ejb.domain.medcase.MedService
			,[]
			,["var objNew=new Packages.ru.ecom.mis.ejb.domain.medcase.ServiceMedCase() ;"
			  ,"objNew.setParent(aEntity)"
			  ,"objNew.setPatient(aEntity.patient)"
			  ,"objNew.setDateStart(aEntity.dateStart)"
			  ,"objNew.setNoActuality(false);objNew.setMedService(objS);"]
			,"from MedCase where parent_id='"+aEntity.getId()+"' and dtype='ServiceMedCase' and medService_id"
			) ;
*/
	saveServices(aForm,aEntity,aCtx) ;
	
		
	

	
}
function saveServices(aForm,aEntity,aCtx) {
	aCtx.manager.createNativeQuery("delete from medcase where parent_id="+aEntity.id+" and dtype='ServiceMedCase'").executeUpdate();
	
	if (aForm.getMedServices()!=null&&aForm.getMedServices()!='') {
		var otherServs = aForm.medServices.split("##");
		if (otherServs.length>0) {
			for (var i=0;i<otherServs.length;i++) {
				var serv = otherServs[i].split("@") ;
				var servMC = new Packages.ru.ecom.mis.ejb.domain.medcase.ServiceMedCase();
				var servObj = aCtx.manager.find(  Packages.ru.ecom.mis.ejb.domain.medcase.MedService,java.lang.Long.valueOf(serv[0])) ;
				servMC.setUet(+serv[1]>0?new java.math.BigDecimal(serv[1]):null);
				servMC.setOrderNumber(serv[2]);
				var am=java.lang.Long.valueOf(serv[3]) ;
				servMC.setMedServiceAmount(am!=null?am.intValue():null);
				servMC.setMedService(servObj);
				servMC.setPatient(aEntity.getPatient());
				servMC.setWorkFunctionExecute(aEntity.getWorkFunctionExecute());
				servMC.setUsername(aEntity.getUsername());
				servMC.setCreateDate(aEntity.getCreateDate());
				servMC.setNoActuality(aEntity.getNoActuality());
				servMC.setServiceStream(aEntity.getServiceStream());
				servMC.setCreateTime(aEntity.getCreateTime());
				servMC.setParent(aEntity);
				servMC.setDateStart(aEntity.dateStart);
				aCtx.manager.persist(servMC);
			}
		}
	}
}
function saveArray(aEntity,aManager, aJsonString,aClazz,aMainCmd, aAddCmd,
		 aTableSql) {
	
	var obj = new Packages.org.json.JSONObject(aJsonString) ;
	var ar = obj.getJSONArray("childs");
	var ids = new Packages.java.lang.StringBuilder() ;
	
	for (var j=0;j<aMainCmd.length;j++) {
		eval(aMainCmd[j]) ;
	}
	
	
	
	for (var i = 0; i < ar.length(); i++) {
		
		var child = ar.get(i);
		var jsId = java.lang.String.valueOf(child.get("value"));
		if (jsId!=null && jsId!="" || jsId=="0") {
			//System.out.println("    id="+jsonId) ;
			
			var jsonId=java.lang.Long.valueOf(jsId) ;
			ids.append(",").append(jsonId) ;
			var sql ="select count(*) as cnt "+aTableSql+"='"+jsonId+"'" ;
			var count = aManager.createNativeQuery(sql).setMaxResults(1).getResultList() ;
			if (count.isEmpty()|| (!count.isEmpty()&&(+count.get(0)<1))) {
				//throw ""+jsId;
				var objS = aManager.find(aClazz,jsonId) ;
				//throw ""+aAddCmd.length;
				for (var j=0;j<aAddCmd.length;j++) {
					eval(aAddCmd[j]) ;
					//if (j>3)throw ""+aAddCmd[j] ;
				}
				//throw ""+objS ;
				aManager.persist(objNew) ;
				
			}
		}
	}
	if (ids.length()>0) {
		sql = "delete "+aTableSql+" not in ("+ids.substring(1)+") " ;
		aManager.createNativeQuery(sql).executeUpdate();
	} else {
		sql = "delete "+aTableSql+" !='0' " ;
		aManager.createNativeQuery(sql).executeUpdate();
	}
	
}
function saveArray_back(aManager, aJsonString, aAddSql, aTableName
		, aFieldParent, aIdEntity, aFieldChildren) {
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
			sql = new java.lang.StringBuilder() ;
			sql.append("delete from ").append(aTableName)
				.append(" where ").append(aAddSql).append("") ;
			aManager.createNativeQuery(sql.toString()).executeUpdate();
		}

}

