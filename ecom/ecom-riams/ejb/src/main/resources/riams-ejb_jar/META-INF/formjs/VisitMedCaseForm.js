function onView(aForm, aVisit, aCtx) {
	if(aForm.parent!=0) {
		aForm.addDisabledField("parent") ;
	}
}
/**
 * Перед удалением
 */
function onPreDelete(aEntityId, aContext) {
	var manager = aContext.manager;
	var visit = manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit, new java.lang.Long(aEntityId)) ;
	if(visit.getNoActuality()!=null && visit.getNoActuality()==true) {
		//throw "fdsfsd";
		manager.createNativeQuery("delete from Diagnosis where medcase_id="+aEntityId).executeUpdate() ;
		manager.createNativeQuery("delete from medcase where parent_id="+aEntityId).executeUpdate() ;
		manager.createNativeQuery("delete from diary where medcase_id="+aEntityId).executeUpdate() ;
		var  l=manager.createNativeQuery("select id,parent_id from MedCase where parent_id=(select parent_id from medcase where id="+aEntityId+") and parent_id is not null").getResultList() ;
		if (l.size()==1) {
			manager.createNativeQuery("update medcase set parent_id=null where id="+aEntityId).executeUpdate() ;
			manager.createNativeQuery("delete from medcase where id="+l.get(0)[1]).executeUpdate() ;
		}

	} else {
		var  l=manager.createNativeQuery("select id,parent_id from MedCase where parent_id="+aEntityId).getResultList() ;
		if (l.size()>0) {
			throw "Невозможно удалить визит, т.к. у визиту прекреплены услуги! Для удаления необходимо отметить визит как недействительный";
		}
	}
	l = manager.createNativeQuery("select id from prescriptionList where medcase_id="+aEntityId).getResultList(); //Удаляем листы назначений
	if (l.size()>0) {
		manager.createNativeQuery("delete from prescription where prescriptionlist_id="+l.get(0)).executeUpdate();
		manager.createNativeQuery("delete from prescriptionList where id="+l.get(0)).executeUpdate();
	}
}

function onCreate(aForm, aVisit, aCtx) {
	throw "Создавать визит можно только из направления" ;

}
function checkIntervalRegistration(aCtx,aWorkFunctionPlan,aDatePlan,aTimePlan,aId) {
	var check = true ;var message="";
	var interval = 0 ;
	// Надо ли проверять ограничение по интервалу разрешенной регистрации
	if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Visit/EnableRegistraionInterval")){
		// Превышает ли регистрация разрешенный интервал
		var sql = "select to_char(wcd.calendarDate,'dd.mm.yyyy'),cast(wct.timeFrom as varchar(5)),case when wf.registrationInterval>0 then wf.registrationInterval when lpu1.registrationInterval>0 then lpu1.registrationInterval else lpu2.registrationInterval end from workCalendarTime wct left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id left join WorkCalendar wc on wc.id=wcd.workCalendar_id left join WorkFunction wf on wf.id=wc.workFunction_id left join worker w on wf.worker_id=w.id left join MisLpu lpu2 on lpu2.id=w.lpu_id left join MisLpu lpu1 on lpu1.id=wf.lpu_id where wct.id='"+
			aTimePlan+"' and wcd.id='"+aDatePlan+"' and wf.id='"+aWorkFunctionPlan+"'" ;
		//throw sql ;
		var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
		if (list.size()>0) {
			var obj=list.get(0) ;
			var date = ""+obj[0] ;
			var time=""+obj[1] ;
			interval = obj[2]!=null?+obj[2]:1440 ;
			if (+interval<1) {
				//check=true;
			} else {
				var curDate = java.util.Calendar.getInstance() ;
				var calFrom = java.util.Calendar.getInstance() ;
				var calTo = java.util.Calendar.getInstance() ;
				var dateVisit = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(date,time) ;
				calFrom.setTime(dateVisit) ;
				calTo.setTime(dateVisit) ;
				calTo.add(java.util.Calendar.MINUTE,interval) ;
				calFrom.add(java.util.Calendar.MINUTE,-interval) ;
				if (!aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Visit/EnableRegistraionIntervalNoBefore")){
					if (curDate.before(calFrom)) {
						check=false ;
						message="У Вас стоит ограничение "+interval+" мин. для регистрации посещения до начала приема" ;
					}
				}
				if (!aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Visit/EnableRegistraionIntervalNoAfter")){
					if (curDate.after(calTo)) {
						check=false ;
						message="У Вас стоит ограничение "+interval+" мин. разрешенной регистрации после начала приема" ;
					}
				}
			}
			// Есть ли разрешение регистрировать в запрещенном периоде
			if (!check) {
				var param1 = new java.util.HashMap() ;
				param1.put("obj","Visit") ;
				param1.put("permission" ,"editDataClosePeriod") ;
				param1.put("id", aId) ;
				var isCreateClose = aCtx.serviceInvoke("WorkerService", "checkPermission", param1)+""; 
				if (+isCreateClose!=1) { check=false ;}
			}
		}
	} 
	if (!check) {
		throw message ;
	}
	return check ;
}
function onPreSave(aForm,aEntity, aCtx) {
	var stat=!aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Visit/OnlyTheir") ;
	if(+aForm.workFunctionExecute==0) {
		//aForm.workFunctionExecute = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunctionList")
		//	.iterator().next().id ;
		var curWF = +aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunctionListByPoliclinic"
				,aForm.workFunctionPlan) ;
		var curGr = +aCtx.serviceInvoke("WorkerService", "getGroupByWorkFunction"
				,curWF) ;
		aForm.workFunctionExecute = java.lang.Long.valueOf(curWF);
		var planWF = +aForm.workFunctionPlan ;
		
		if (stat && curWF!=planWF && curGr!=planWF) {
			throw "У Вы можете оформлять только направленных к Вам пациентов!!" ;
		}
	} else {
		if (stat && aForm.workFunctionExecute!=aForm.workFunctionPlan) {
			var exec = java.lang.Long.valueOf(aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunctionListByPoliclinic"
					,aForm.workFunctionPlan));
			var curGr = aCtx.serviceInvoke("WorkerService", "getGroupByWorkFunction"
					,exec) ;
			var planWF = +aForm.workFunctionPlan ;
			if (+exec==planWF || curGr==planWF) {
				aForm.workFunctionExecute = exec ;
			} else {
			//	throw "У Вы можете оформлять только направленных к Вам пациентов!! "+exec ;
			}
		}
	}
	
	if ((aForm.isPreRecord!=null && aForm.isPreRecord.equals(java.lang.Boolean.TRUE)) || aEntity.dateStart!=null) {
	} else {
		//throw ""+aForm.isPreRecord;
		if (!checkIntervalRegistration(aCtx,aForm.workFunctionPlan,aForm.datePlan,aForm.timePlan,aForm.id)) {
			
		}
	}
	
	var date = new java.util.Date() ;
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditTime(Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(new java.sql.Time (date.getTime()))) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
    checkHospitBetween2VisitsAt1Spo(aForm,aEntity, aCtx);
}

//Milamesher #108 проверка на наличие госпитализации между визитами в СПО
function checkHospitBetween2VisitsAt1Spo(aForm,aEntity, aCtx) {
	if (aEntity.dateStart!=null && aEntity.dateStart!='' && aForm.parent!=null && aForm.parent!='' && aEntity.patient!=null) {
        var spo = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase, new java.lang.Long(aForm.parent));
        if (spo!=null && spo.dateStart!=null) {
        	var spoDateStart=spo.dateStart;
        	var dateNow=aEntity.dateStart;
        	var patId=aEntity.patient.id;
            var list = aCtx.manager.createNativeQuery("select hmc.id from medcase hmc\n" +
                "left join patient pat on pat.id=hmc.patient_id\n" +
                "where hmc.datefinish is not null and hmc.datestart between to_date('"+spoDateStart+"','yyyy-MM-dd') and to_date('"+dateNow+"','yyyy-MM-dd')\n" +
                "and hmc.deniedHospitalizating_id is null and hmc.dtype='HospitalMedCase' and pat.id="+patId).getResultList() ;
            if (!list.isEmpty()) throw "С момента начала выбранного СПО у пациента была госпитализация, необходимо открыть новый СПО!";
		}
	}
}

function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date();
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	//aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}

function onSave(aForm, aVisit, aCtx) {
	if(true==aVisit.getNoActuality()&&aVisit.timePlan!=null) {
		aVisit.timePlan.medCase = null ;
		return ; // ничего не делаем, если не актуальный
	}
	var canAddToCloseSPO=aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Visit/CanAttachToCloseSPO") ;
	if (aForm.isPreRecord!=null && aForm.isPreRecord==true) {
		aVisit.dateStart=null ;
		aVisit.timeExecute=null ;
	} else {
		if(aVisit.dateStart==null) throw "Нет даты визита" ;
		if(aVisit.timeExecute==null) throw "Нет времени визита" ;
		// создание нового СПО
		if(aVisit.parent==null) {
			var spo = new Packages.ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase() ;
			aCtx.manager.persist(spo) ;
			var workFunction = aVisit.getWorkFunctionExecute() ; 
			spo.setOwnerFunction(workFunction) ;
			spo.setDateStart(aVisit.getDateStart()) ;
			spo.setLpu(workFunction.worker.getLpu()) ;
			spo.setPatient(aVisit.getPatient()) ;
			spo.setStartFunction(workFunction) ;
			spo.setServiceStream(aVisit.getServiceStream()) ;
			aVisit.setParent(spo) ;
		} else if (aVisit.parent.dateFinish!=null &&canAddToCloseSPO) { //Добавляем визит в закрытый СПО
			//throw "good";
			var spo = aVisit.parent;
			if (aVisit.dateStart<spo.dateStart) {
				spo.setDateStart(aVisit.getDateStart());
				spo.setStartFunction(aVisit.getWorkFunctionExecute()) ;
			} else if (aVisit.dateStart>spo.dateFinish) {
				spo.setDateFinish(aVisit.getDateStart());
				spo.setFinishFunction(aVisit.getWorkFunctionExecute()) ;
			}
			
		}
		//throw "not good = "+aVisit.parent.dateFinish +"<>"+canAddToCloseSPO;
	}
	// Медицинские услуги
	saveArray(aVisit, aCtx.manager,aForm.getMedServices()
			,Packages.ru.ecom.mis.ejb.domain.medcase.MedService
			,[]
			,["var objNew=new Packages.ru.ecom.mis.ejb.domain.medcase.ServiceMedCase() ;"
			  ,"objNew.setParent(aEntity)"
			  ,"objNew.setPatient(aEntity.patient)"
			  ,"objNew.setDateStart(aEntity.dateStart)"
			  ,"objNew.setTimeExecute(aEntity.timeExecute)"
			  ,"objNew.setWorkFunctionExecute(aEntity.workFunctionExecute)"
			//  ,"objNew.setMedseviceAmount(+str[j)"
			  ,"objNew.setNoActuality(false);objNew.setMedService(objS);"]
			,"from MedCase where parent_id='"+aVisit.getId()+"' and dtype='ServiceMedCase' and medService_id"
		//	, aForm.getMedserviceAmounts()
			) ;
	//throw "spo="+spo.dateStart + " - "+aVisit.dateStart ;
}

function saveArray(aEntity,aManager, aJsonString, aClazz,aMainCmd, aAddCmd,
		 aTableSql
		 //, aStringAmount
		 ) {
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
	if (ids.length()>0) {
		sql = "delete "+aTableSql+" not in ("+ids.substring(1)+") " ;
		aManager.createNativeQuery(sql).executeUpdate();
	} else {
		sql = "delete "+aTableSql+" !='0' " ;
		aManager.createNativeQuery(sql).executeUpdate();
	}
	
}