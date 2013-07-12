function onView(aForm, aVisit, aCtx) {
	if(aForm.parent!=0) {
		aForm.addDisabledField("parent") ;
	}
}
/**
 * Перед удалением
 */
function onPreDelete(aEntityId, aContext) {
	//throw "Запрет на удаление!!!"; 
}

function onCreate(aForm, aVisit, aCtx) {
	throw "Создавать визит можно только из направления" ;

}
function checkIntervalRegistration(aCtx,aWorkFunctionPlan,aDatePlan,aTimePlan,aId) {
	var check = true ;
	// Надо ли проверять ограничение по интервалу разрешенной регистрации
	if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Visit/EnableRegistraionInterval")){
		// Превышает ли регистрация разрешенный интервал
		var sql = "select to_char(wcd.calendarDate,'dd.mm.yyyy'),cast(wct.timeFrom as varchar(5)),case when wf.registrationInterval>0 then wf.registrationInterval when lpu1.registrationInterval>0 then lpu1.registrationInterval else lpu2.registrationInterval end from workCalendarTime wct left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id left join WorkCalendar wc on wc.id=wcd.workCalendar_id left join WorkFunction wf on wf.id=wc.workFunction_id left join worker w on wf.worker_id=w.id left join MisLpu lpu2 on lpu2.id=w.lpu_id left join MisLpu lpu1 on lpu1.id=wf.lpu_id where wct.id='"+
			aTimePlan+"' and wcd.id='"+aDatePlan+"' and wf.id='"+aWorkFunctionPlan+"'" ;
		var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
		if (list.size()>0) {
			var obj=list.get(0) ;
			var date = ""+obj[0] ;
			var time=""+obj[1] ;
			var interval = obj[2]!=null?+obj[2]:1440 ;
			var curDate = new java.util.Date() ;
			var calFrom = java.util.Calendar.getInstance() ;
			var calTo = java.util.Calendar.getInstance() ;
			var dateVisit = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(date,time) ;
			calFrom.setTime(dateVisit) ;
			calTo.setTime(dateVisit) ;
			calTo.set(java.util.Calendar.MINUTE,interval) ;
			calFrom.set(java.util.Calendar.MINUTE,-interval) ;
			
			if (calTo.getTime().getTime()<curDate.getTime()) {
				check=false ;
			} else {
				if (calFrom.getTime().getTime()>curDate.getTime()) {
					check=false ;
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
	return check ;
}
function onPreSave(aForm,aEntity, aCtx) {
	
	var stat=aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Visit/OnlyTheir") ;
	if(+aForm.workFunctionExecute==0) {
		//aForm.workFunctionExecute = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunctionList")
		//	.iterator().next().id ;
		var curWF = +aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunctionListByPoliclinic"
				,aForm.workFunctionPlan) ;
		var curGr = aCtx.serviceInvoke("WorkerService", "getGroupByWorkFunction"
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
				throw "У Вы можете оформлять только направленных к Вам пациентов!! "+exec ;
			}
		}
		
	}
	if (aForm.isPreRecord!=null && aForm.isPreRecord) {
	} else {

		if (!checkIntervalRegistration(aCtx,aForm.workFunctionPlan,aForm.datePlan,aForm.timePlan,aForm.id)) {
			throw "У Вас стоит ограничение на интервал разрешенной регистрации" ;
		}
	}
	
	var date = new java.util.Date();
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	//aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date();
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	//aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}

function onSave(aForm, aVisit, aCtx) {
	if(true==aVisit.getNoActuality()) {	
		aVisit.timePlan.medCase = null ;
		return ; // ничего не делаем, если не актуальный
	}
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
		}
	}

	//throw "spo="+spo.dateStart + " - "+aVisit.dateStart ;
	
}