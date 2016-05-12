
function findLogginedWorker(aCtx) {
    return aCtx.serviceInvoke("WorkerService", "findLogginedWorker") ;
}
function findLogginedWorkFunction(aCtx) {
	return aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction") ;
}

function checks(aCtx,aVisit) {
	if(+aVisit.workFunctionPlan==0) throw "Нет места направления" ;
	if(+aVisit.datePlan==0) throw "Нет даты направления" ;
	if(+aVisit.timePlan==0) throw "Нет времени направления" ;
	var list = aCtx.manager.createNativeQuery("select vwf.id from workfunction wf left join vocworkfunction vwf on vwf.id=wf.workfunction_id where wf.id='"+(+aVisit.workFunctionPlan)+"' and vwf.isnodiagnosis='1'")
	.getResultList() ;
	var list1 = aCtx.manager.createNativeQuery("select vss.id from vocservicestream vss where vss.id='"+(+aVisit.serviceStream)+"' and vss.code='CHARGED'")
			.getResultList() ;
	if (list.size()>0
			|| aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Visit/CheckChargedPatient") && list1.size()>0
	) {
		var obj = new Packages.org.json.JSONObject(aVisit.getMedServices()) ;
		var ar = obj.getJSONArray("childs");
		//throw ar.length() ;
		if (ar.length()==0||
				(ar.length()==1 && (+java.lang.String.valueOf(ar.get(0).get("value")))==0
						)) {
			throw "Необходимо указать услугу, на которую направляется пациент!!!" ;
		}
	}
	list = aCtx.manager.createNativeQuery("select wct.medCase_id,pat.lastname||' '||pat.firstname||' '||coalesce(pat.middlename,'') from WorkCalendarTime wct left join MedCase mc on mc.id=wct.medCase_id left join Patient pat on pat.id=mc.patient_id where wct.id='"
			+aVisit.timePlan+"' and mc.id!='"+(+aVisit.id)+"'")
			.getResultList() ;
	if (list.size()>0) {
		var obj = list.get(0) ;
		if (obj[0]!=null) throw "Пациент "+obj[1]+" уже направлен на это время";
	}
	
	
}

function createOrSave(aForm, aVisit, aCtx) {
	aVisit.timePlan.medCase = aVisit ;
	//aVisit.orderWorker = findLogginedWorker(aCtx) ;
	var workFunc = findLogginedWorkFunction(aCtx) ;
	aVisit.orderWorkFunction =  workFunc;
	if(aVisit.parent!=null&&aVisit.parent.dateFinish!=null) {
		aVisit.parent=null ;}
	if ((aForm.emergency==null || !aForm.emergency)&&
			aVisit.workFunctionPlan.emergency!=null &&aVisit.workFunctionPlan.emergency) {
		aVisit.setEmergency(true) ;
	}
	// Медицинские услуги
	saveArray(aVisit, aCtx.manager,aForm.getMedServices()
			,Packages.ru.ecom.mis.ejb.domain.medcase.MedService
			,[]
			,["var objNew=new Packages.ru.ecom.mis.ejb.domain.medcase.ServiceMedCase() ;"
			  ,"objNew.setParent(aEntity)"
			  ,"objNew.setPatient(aEntity.patient)"
			  ,"objNew.setDateStart(aEntity.dateStart)"
			  ,"objNew.setNoActuality(false);objNew.setMedService(objS);"]
			,"from MedCase where parent_id='"+aVisit.getId()+"' and dtype='ServiceMedCase' and medService_id"
			) ;
	aCtx.manager.persist(aVisit) ;
}

function onCreate(aForm, aVisit, aCtx) {
	//checks(aVisit) ;
	if(aVisit.timePlan.medCase!=null) throw "На это время уже есть направление: "+ aVisit.timePlan.medCase.id;
	if(aVisit.parent!=null&&aVisit.parent.dateFinish!=null) aVisit.dateFinish=null ;

	createOrSave(aForm, aVisit, aCtx) ;
}

function onPreCreate(aForm, aCtx) {
	checks(aCtx,aForm) ;
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateTime(Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(new java.sql.Time (date.getTime()))) ;
	aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	var wf = +aForm.workFunctionPlan ;
	var wfc = aCtx.manager.createNativeQuery("select case when isNoDirectSelf='1' then isNoDirectSelf else null end from workfunction where id='"+wf+"'").getResultList() ;
	if (wfc.size()>0 && wfc.get(0)!=null) {
		wfc = aCtx.manager.createNativeQuery("select case when wf.id='"+wf+"' then wf.id  when wf.group_id='"+wf+"' then wf.id else null end from secuser su left join workfunction wf on wf.secuser_id=su.id where su.login='"+aForm.username+"'").getResultList() ;
		if (wfc.size()>0 && wfc.get(0)!=null) {
			throw "У Вас стоит ограничение на направление к самому себе!!!" ;
		}
	}
}


function onPreSave(aForm, aVisit, aCtx) {
    // освобождение предыдущего времени
	var date = new java.util.Date() ;
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditTime(Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(new java.sql.Time (date.getTime()))) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	
	checks(aCtx,aForm) ;
	var visit = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit, new java.lang.Long(aForm.id))  ;
	if (visit.timePlan!=null) visit.timePlan.medCase = null ;
	

}

function onSave(aForm, aVisit, aCtx) {
	//checks(aVisit) ;
	createOrSave(aForm, aVisit, aCtx) ;
	
}
function onPreDelete(aId,aCtx) {
	aCtx.manager
		.createNativeQuery("update WorkCalendarTime set medCase_id=null where medCase_id=:medCase")
		.setParameter("medCase",aId).executeUpdate() ;
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