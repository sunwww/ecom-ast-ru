
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
	if(aVisit.parent!=null && aVisit.parent.dateFinish!=null) {
		aVisit.parent=null ;
	}
	if (aForm.emergency==null || aForm.emergency==false) {
		aVisit.setEmergency(aVisit.workFunctionPlan.emergency) ;
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
	setNotPaid(aForm, aVisit, aCtx)
}

function checkCountDays(aForm,aCtx, aVisit)
{
	var countDays = aForm.countDays;
	
		var wctId = aCtx.manager.createNativeQuery("select wct.id,wcd.calendardate from workcalendartime wct1"
		+" left join workcalendarday wcd1 on wct1.workcalendarday_id = wcd1.id"
		+" left join workcalendarday wcd on wcd.workcalendar_id = wcd1.workcalendar_id"
		+" left join workcalendartime wct on wct.workcalendarday_id=wcd.id  and wct.timefrom=wct1.timefrom"
		+" where wct1.id='"+aForm.timePlan+"' "
		+" and wcd.calendardate BETWEEN  wcd1.calendardate+1 and  wcd1.calendardate+"+(countDays-1)
		+" and wct.medcase_id is null"
		+" and wct.prescription is null"
		+" and wct.prepatient_id is null"
		+" and wct.prepatientinfo is null").getResultList();
		
		for(var i=0;i<wctId.size();i++)
		{
			var wct= aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime, new java.lang.Long(wctId.get(i)[0]))  ;
		var visit= new Packages.ru.ecom.mis.ejb.domain.medcase.Visit();
		visit.setCreateDate(aVisit.getCreateDate());
		visit.setTimePlan(wct);
		visit.setDatePlan(wct.getWorkCalendarDay());
		visit.setEmergency(aVisit.getEmergency());
		visit.setVisitReason(aVisit.getVisitReason());
		visit.setKinsman(aVisit.getKinsman());
		visit.setParent(aVisit.getParent());
		visit.setPatient(aVisit.getPatient());
		visit.setWorkFunctionPlan(aVisit.getWorkFunctionPlan());
		visit.setWorkPlaceType(aVisit.getWorkPlaceType());
		aCtx.manager.persist(visit);
		wct.setMedCase(visit);
		aCtx.manager.persist(wct);
		saveArray(visit, aCtx.manager,aForm.getMedServices()
				,Packages.ru.ecom.mis.ejb.domain.medcase.MedService
				,[]
				,["var objNew=new Packages.ru.ecom.mis.ejb.domain.medcase.ServiceMedCase() ;"
				  ,"objNew.setParent(aEntity)"
				  ,"objNew.setPatient(aEntity.patient)"
				  ,"objNew.setDateStart(aEntity.dateStart)"
				  ,"objNew.setNoActuality(false);objNew.setMedService(objS);"]
				,"from MedCase where parent_id='"+visit.getId()+"' and dtype='ServiceMedCase' and medService_id"
				) ;
		
		
		}
	
}
function onCreate(aForm, aVisit, aCtx) {
	if (aForm.countDays != null && +aForm.countDays>0){
		checkCountDays(aForm, aCtx, aVisit);
		}
	
	//checks(aVisit) ;
	if(aVisit.timePlan.medCase!=null) throw "На это время уже есть направление: "+ aVisit.timePlan.medCase.id;
	if(aVisit.parent!=null&&aVisit.parent.dateFinish!=null) aVisit.dateFinish=null ;

	//---
	createOrSave(aForm, aVisit, aCtx) ;
}
function setNotPaid(aForm, aEntity, aCtx) {
		if (aEntity.serviceStream.isPaidConfirmation&&aEntity.isPaid==null) {
			aEntity.setIsPaid(false);

	}
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
    var visit = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit, new java.lang.Long(aForm.id))  ;
	if (visit.getDateStart()!=null) { //Запрет на редактирование направления если пациент принят врачом
        throw "Пациент принят врачом, редактирование направления невозможно!";
    }
    // освобождение предыдущего времени
	var date = new java.util.Date() ;
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditTime(Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(new java.sql.Time (date.getTime()))) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	checks(aCtx,aForm) ;
	if (visit.timePlan!=null) visit.timePlan.medCase = null ;
}

function onSave(aForm, aVisit, aCtx) {
	//checks(aVisit) ;
	createOrSave(aForm, aVisit, aCtx) ;
	
}
function onPreDelete(aId,aCtx) {
	var manager = aCtx.manager;
	var list = manager.createNativeQuery("select id from medcase where id="+aId+" and datestart is not null").getResultList();
	if (list.size()>0) {
		throw "Визит оформлен, удаление направления невозможно!";
	}
	manager.createNativeQuery("delete from medcase where parent_id="+aId).executeUpdate();
	manager.createNativeQuery("update WorkCalendarTime set medCase_id=null where medCase_id=:medCase")
		.setParameter("medCase",aId).executeUpdate() ;
}
function saveArray(aEntity,aManager, aJsonString,aClazz,aMainCmd, aAddCmd,
		 aTableSql) {
	if (aJsonString==null||aJsonString=='') {return;}
	var obj = new Packages.org.json.JSONObject(aJsonString) ;
	var ar = obj.getJSONArray("childs");
	var ids = new Packages.java.lang.StringBuilder() ;
	
	for (var j=0;j<aMainCmd.length;j++) {
		eval(aMainCmd[j]) ;
	}


    //Проверяем -  есть ли открытый СЛС.
    var sql = "select pl.id as listId, coalesce(slo.id,sls.id) as medcaseId" +
        " from medcase sls" +
        " left join medcase slo on slo.parent_id = sls.id and slo.dtype='DepartmentMedCase' and slo.transferdate is null" +
        " left join prescriptionlist pl on pl.medcase_id = coalesce(slo.id, sls.id)" +
        " left join vocdeniedhospitalizating vdh on vdh.id = sls.deniedhospitalizating_id" +
        " where sls.dtype='HospitalMedCase' and sls.patient_id = " + aEntity.patient.id +
        " and sls.datefinish is null and (sls.deniedhospitalizating_id is null or vdh.code = 'IN_PIGEON_HOLE')" ;
    var list = aManager.createNativeQuery(sql).getResultList();
    var hasSls = !list.isEmpty();
    var presList =null;
    if (hasSls) {
    	var sls =list.get(0);
		if (sls[0]) {
			presList = aManager.find(Packages.ru.ecom.mis.ejb.domain.prescription.PrescriptList, java.lang.Long.valueOf(+sls[0]));
		} else {
			presList = new Packages.ru.ecom.mis.ejb.domain.prescription.PrescriptList();
			presList.setMedCase(aManager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase, java.lang.Long.valueOf(+sls[1])));
		}
	}

    sql = "delete "+aTableSql+" !='0' " ;
    aManager.createNativeQuery(sql).executeUpdate();
	for (var i = 0; i < ar.length(); i++) {
		var child = ar.get(i);
		var jsId = java.lang.String.valueOf(child.get("value"));
		if (jsId!=null && jsId!="" && jsId!="0") {
			var jsonId=java.lang.Long.valueOf(jsId) ;
			ids.append(",").append(jsonId) ;
			var sql ="select count(*) as cnt "+aTableSql+"='"+jsonId+"'" ;
			var count = aManager.createNativeQuery(sql).setMaxResults(1).getResultList() ;
			if (count.isEmpty()|| (!count.isEmpty() && +count.get(0)<1)) {
				var objS = aManager.find(aClazz,jsonId) ;
				for (var j=0;j<aAddCmd.length;j++) {
					eval(aAddCmd[j]) ;
				}
                if (child.get("cmnt")!='')
                	objNew.setServiceComment(java.lang.String.valueOf(child.get("cmnt")));
				aManager.persist(objNew) ;
				if (hasSls) {
                    var pres = new Packages.ru.ecom.mis.ejb.domain.prescription.ServicePrescription();
                    pres.setPrescriptionList(presList);
                    pres.setPlanStartDate(aEntity.timePlan.workCalendarDay.calendarDate);
                    pres.setPrescriptSpecial(aEntity.orderWorkFunction);
                    pres.setMedService(objS);
                    pres.setCreateUsername(aEntity.username);
                    pres.setPrescriptCabinet(aEntity.workFunctionPlan);
                    pres.setCalendarTime(aEntity.timePlan);
                    aManager.persist(pres);
                }
            }
		}
	}
	/*if (ids.length()>0) {
		sql = "delete "+aTableSql+" not in ("+ids.substring(1)+") " ;
		aManager.createNativeQuery(sql).executeUpdate();
	} else {
		sql = "delete "+aTableSql+" !='0' " ;
		aManager.createNativeQuery(sql).executeUpdate();
	}*/
	
}