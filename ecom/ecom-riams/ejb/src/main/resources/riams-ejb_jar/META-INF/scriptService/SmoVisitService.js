/**
 * Создание должностей в экстренных пунктах для формирования визитов НМП.
 * Не использовать, неактуально
 */
function createNewEmergencySpec(aCtx,aLpu,aGroup) {
	throw "Не используется";
	/*
	var sql="insert into worker (person_id,lpu_id,createusername,createdate,createtime)"
		+" (select w.person_id"
		+" ,(select gr.lpu_id from workfunction gr where gr.id="+aGroup+")"
		+" ,'autogerenerate',current_date,current_time"
		+"  from workfunction wf"
		+" left join worker w on w.id=wf.worker_id"
		+" left join vocworkfunction vwf on vwf.id=wf.workfunction_id"
		+" left join patient wp on wp.id=w.person_id"
		+" left join worker wg on wg.person_id=w.person_id"
		+" left join workfunction wfg on wfg.worker_id=wg.id"
		+" left join workfunction wfg1 on wfg1.id=wfg.group_id  and wfg1.id="+aGroup
		+" where w.lpu_id="+aLpu+" and vwf.vocPost_id=(select vwfg.vocPost_id from workfunction gr  left join vocworkfunction vwfg on vwfg.id=gr.workFunction_id where gr.id="+aGroup+")"
		+" and (select count(*) from worker ww where ww.lpu_id=(select gr.lpu_id from workfunction gr where gr.id="+aGroup+") and ww.person_id=w.person_id)=0"
		+" and (wf.archival='0' or wf.archival is null)"
		+" group by wf.id,w.person_id,vwf.name,wp.lastname"
		+" )";
	aCtx.manager.createNativeQuery(sql).executeUpdate() ;
	sql = "insert into workfunction (dtype,worker_id,group_id,workfunction_id,createusername,createdate,createtime)"
		+" ("
		+" select 'PersonalWorkFunction',"
		+" (select max(wn.id) from worker wn where wn.lpu_id=(select gr.lpu_id from workfunction gr where gr.id="+aGroup+")"
		+"  and wn.person_id=w.person_id)"
		+" ,(select gr.id from workfunction gr where gr.id="+aGroup+")"
		+" ,(select gr.workfunction_id from workfunction gr where gr.id="+aGroup+"),'autogerenerate',current_date,current_time"
		+"  from workfunction wf"
		+" left join worker w on w.id=wf.worker_id"
		+" left join vocworkfunction vwf on vwf.id=wf.workfunction_id"
		+" left join patient wp on wp.id=w.person_id"
		+" left join worker wg on wg.person_id=w.person_id"
		+" left join workfunction wfg on wfg.worker_id=wg.id"
		+" left join workfunction wfg1 on wfg1.id=wfg.group_id"
		+" where w.lpu_id="+aLpu+" and vwf.vocPost_id=(select vwfg.vocPost_id from workfunction gr left join vocworkfunction vwfg on vwfg.id=gr.workFunction_id where gr.id="+aGroup+")"
		+" and (select count(www.id) from  worker www where www.lpu_id=(select gr.lpu_id from workfunction gr where gr.id="+aGroup+" ) and www.person_id=w.person_id)>0 "
		+" and (select count(wfw.id) from  workfunction wfw left join worker www on www.id=wfw.worker_id "
		+" where wfw.group_id="+aGroup+" and www.person_id=w.person_id)=0  "
		+" and (wf.archival='0' or wf.archival is null)"
		+" group by wf.id,vwf.name,wp.lastname,w.person_id"
		//+" having count(wfg1.id="+aGroup+")=0)" 
		
		+")";
	aCtx.manager.createNativeQuery(sql).executeUpdate() ;
	*/
}

function deleteEmptySpo(aCtx,aParams) {
	var sql="delete from medcase spo where"
		+" spo.dtype='PolyclinicMedCase' and (select count(*) from MedCase v where v.parent_id=spo.id)=0";
		aCtx.manager.createNativeQuery(sql).executeUpdate() ;
}


function printDirectionByTime(aCtx,aParams) {
	var map = new java.util.HashMap() ;
	var currentDate = new java.util.Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	//var FORMAT_time = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentTime",new java.sql.Time (currentDate.getTime())) ;
	var wctId = aParams.get("wct") ;
	var sql="" ;
	sql=sql+"select wct.id as wctid,to_char(wcd.calendarDate,'dd.mm.yyyy') as wcdcalendardate, cast(wct.timeFrom as varchar(5)) as wcttimeFrom, vwf.name as vwfname, wp.lastname ||' '||wp.firstname||' '||wp.middlename as wpmiddlename " ;
	sql=sql+" , coalesce(p.lastname ||' '||substring(p.firstname,1,1)||' '||substring(p.middlename,1,1),p1.lastname ||' '||substring(p1.firstname,1,1)||' '||substring(p1.middlename,1,1)) as fio ";
	sql=sql+" , coalesce(p.patientSync,p1.patientSync) as sync, case when wct.medCase_id is null then '(Пред. запись)' else '' end as pred,wct.prePatientInfo ";
	sql=sql+" , case when m.dateStart is null then '' else '+' end as priem";
	sql=sql+", 'Каб.: '||list(wpl.name)||'.' as wpname" ;
	sql=sql+" from WorkCalendarTime wct" ; 
	sql=sql+" left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id " 
			+" left join Medcase m on m.id=wct.medCase_id "
			+" left join WorkCalendar wc on wc.id=wcd.workCalendar_id " 
			+" left join WorkFunction wf on wf.id=wc.workFunction_id " 
			+" left join WorkPlace_WorkFunction wpf on wpf.workFunctions_id=wf.id" 
			+" left join WorkPlace wpl on wpl.id=wpf.workPlace_id and wpl.dtype='ConsultingRoom'" 
			+" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id " 
			+" left join Worker w on w.id=wf.worker_id " 
			+" left join patient wp on wp.id=w.person_id " 
			+" left join Patient p on p.id=wct.prePatient_id " 
			+" left join Patient p1 on p1.id=m.patient_id " 
			+" left join medpolicy mp on mp.patient_id=p.id where wcd.calendarDate>=CURRENT_DATE " ;
	sql=sql+" and wct.id = '"+wctId+"' " ;
	sql=sql+" group by wct.id,wct.prePatient_id,m.dateStart, wcd.calendarDate, wct.timeFrom, vwf.name, wp.lastname,wp.middlename,wp.firstname ,p.id,p.patientSync,p.lastname,p.firstname,p.middlename,p.birthday,wct.prepatientInfo,wct.medcase_id,p1.patientSync,p1.lastname,p1.firstname,p1.middlename" ;
	sql=sql+" order by wcd.calendarDate,wct.timeFrom" ;
	//throw sql ;
	var ret = new java.util.ArrayList() ;
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	if (list.size()>0) {
		var obj=list.get(0) ;
		map.put("pat.fio",obj[5]!=null?obj[5]:obj[8]) ;
		map.put("pat.sync",obj[6]) ;
	}
	for (var ind=0 ; ind< list.size() ; ind++) {
		var obj = list.get(ind) ;
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		par.setSn(ind+1) ;
		for (var i=1;i<=10;i++) {
			//var i1=i-2 ;
			eval("par.set"+i+"(obj["+i+"]!=null?obj["+i+']:"");') ;
			
		}
		ret.add(par) ;
	}
	map.put("listTime",ret) ;
	return map ;
}
function printDirectionByPatient(aCtx,aParams) {
	var map = new java.util.HashMap() ;
	var patientId = aParams.get("patientId") ;
	var currentDate = new java.util.Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	//var FORMAT_time = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentTime",new java.sql.Time (currentDate.getTime())) ;	
	
	var sql="" ;
	sql=sql+"select wct.id as wctid,to_char(wcd.calendarDate,'dd.mm.yyyy') as wcdcalendardate, cast(wct.timeFrom as varchar(5)) as wcttimeFrom, vwf.name as vwfname, wp.lastname ||' '||wp.firstname||' '||wp.middlename as wpmiddlename " ;
	sql=sql+" , coalesce(p.lastname ||' '||substring(p.firstname,1,1)||' '||substring(p.middlename,1,1),p1.lastname ||' '||substring(p1.firstname,1,1)||' '||substring(p1.middlename,1,1)) as fio ";
	sql=sql+" , coalesce(p.patientSync,p1.patientSync) as sync, case when wct.medCase_id is null then '(Пред. запись)' else '' end as pred ";
	sql=sql+" , case when m.dateStart is null then '' else '+' end as priem";
	sql=sql+" , 'Каб.: '||list(wpl.name)||'.' as wpname";
	sql=sql+" from WorkCalendarTime wct" ; 
	sql=sql+" left join Medcase m on m.id=wct.medCase_id";
	sql=sql+" left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id";
	sql=sql+" left join WorkCalendar wc on wc.id=wcd.workCalendar_id";
	sql=sql+" left join WorkFunction wf on wf.id=wc.workFunction_id";
	sql=sql+" left join WorkPlace_WorkFunction wpf on wpf.workFunctions_id=wf.id" ;
	sql=sql+" left join WorkPlace wpl on wpl.id=wpf.workPlace_id and wpl.dtype='ConsultingRoom'" ;
	sql=sql+" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id" +
			" left join Worker w on w.id=wf.worker_id" +
			" left join patient wp on wp.id=w.person_id" +
			" left join Patient p on p.id=wct.prePatient_id" +
			" left join Patient p1 on p1.id=m.patient_id" +
			" left join medpolicy mp on mp.patient_id=p.id where wcd.calendarDate>=CURRENT_DATE " ;
	sql=sql+" and (wct.prePatient_id = '"+patientId+"' or m.patient_id='"+patientId+"') " ;
	sql=sql+" group by wct.id,wct.prePatient_id, wcd.calendarDate, wct.timeFrom, vwf.name, wp.lastname,wp.middlename,wp.firstname ,p.id,p.patientSync,p.lastname,p.firstname,p.middlename,p.birthday,wct.prepatientInfo,wct.medCase_id,m.dateStart,p1.patientSync,p1.lastname,p1.firstname,p1.middlename" ;
	sql=sql+" order by wcd.calendarDate,wct.timeFrom" ;
	
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	if (list.size()>0) {
		var obj=list.get(0) ;
		map.put("pat.fio",obj[5]) ;
		map.put("pat.sync",obj[6]) ;
	}
	var ret = new java.util.ArrayList() ;
	for (var ind=0 ; ind< list.size() ; ind++) {
		var obj = list.get(ind) ;
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		par.setSn(ind+1) ;
		for (var i=1;i<=9;i++) {
			//var i1=i-2 ;
			eval("par.set"+i+"(obj["+i+"]!=null?obj["+i+']:"");') ;
			
		}
		ret.add(par) ;
	}
	map.put("listTime",ret) ;
	return map ;
}
/**
* Не явка пациента на прием
*/
function visitNoPatient(aContext, aVisitId) {
	checkIfLabAlreadyTransfered(aContext, aVisitId);
	var startWF= aContext.serviceInvoke("WorkerService", "findLogginedWorkFunctionList")
		.iterator().next();
	var username = aContext.getSessionContext().getCallerPrincipal().toString();
	var visit = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit
		, java.lang.Long.valueOf(aVisitId));
	if (visit.timeExecute==null && visit.timePlan!=null) visit.timeExecute = visit.timePlan.timeFrom;
	if (visit.dateStart==null && visit.datePlan!=null) visit.dateStart = visit.datePlan.calendarDate;
	var list = aContext.manager.createQuery("from VocVisitResult where omcCode='-1'").getResultList();
	if (!list.isEmpty()) {
		visit.visitResult = list.get(0);
	}
	visit.noActuality = true;

	if (visit.timePlan!=null) { //не освобождаем время в записи. Раз не пришел, время прошло и записать нового пациента нельзя. *служебка 05-11-19 *upd получается у меня склероз, сделал какую-то херь сам по себе, вернул всё как было
            visit.timePlan.medCase = null;
		if (visit.timePlan.prescription!=null) {
			cancelPrescriptionByVisit(aContext, visit);
			visit.timePlan.prescription = null;
		}
          visit.timePlan = null;
	}

	// FIXME определять функцию правильно
	if (visit.workFunctionExecute == null) {
		visit.workFunctionExecute = startWF;
	}
    freeCaosIfNoVisit(aContext, aVisitId);
	return visit.getId();
}

/**
 * Убираем отметку о выполнении в CAOS при неявке на приём
 */
function freeCaosIfNoVisit(aContext, aVisitId) {
    aContext.manager.createNativeQuery("update contractaccountoperationbyservice set serviceid=null, servicetype= null, medcase_id=null where medcase_id="+aVisitId).executeUpdate();
    aContext.manager.createNativeQuery("update contractaccountoperationbyservice set serviceid=null, servicetype= null, medcase_id=null where medcase_id = " +
		" any(select id from medcase where parent_id="+aVisitId + ")").executeUpdate();
}

/**
 * Отменяем назначение при неявке пациента
 */

function cancelPrescriptionByVisit (aContext, aVisit) {
	var cancelWfId= aContext.serviceInvoke("WorkerService", "findLogginedWorkFunctionList").iterator().next().id ;
	var username = aContext.getSessionContext().getCallerPrincipal().toString() ;
	if (aVisit.timePlan.prescription==null) return;
	aContext.manager.createNativeQuery("update prescription set calendartime_id = null " +
			" ,cancelreasontext='Оформлена неявка на прием', cancelusername = '"+username+"'" +
			" ,cancelspecial_id="+cancelWfId +
			" ,canceldate = current_date, canceltime = current_time"+
			" ,planstarttime = '" +aVisit.timePlan.timeFrom +"'"+
			" where calendartime_id = "+aVisit.timePlan.id).executeUpdate();
}

/**
 * Milamesher 17072018 если есть лаб. назначения, которые переданы в лабораторию, ставить неявку на приём нельзя
 */

function checkIfLabAlreadyTransfered(aContext, aVisitId) {
    var list = aContext.manager.createNativeQuery(
        " select pr.id from prescriptionlist pl " +
        " left join prescription pr on pr.prescriptionlist_id=pl.id" +
        " where pl.medcase_id=" + aVisitId + " and pr.transferdate is not null ").getResultList() ;
    if (!list.isEmpty()) {
		throw "Лабораторные направления, сделанные в этом визите, уже переданы в лабораторию. Поставить неявку на приём нельзя.";
    }
}

/**
 * Закрыть СПО по визиту
 */
function closeSpoByVisit(aContext, aVisitId) {
	var visit = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase
			, java.lang.Long.valueOf(aVisitId)) ;
	if(visit.getDateStart()==null) throw "У визита нет даты приема" ;	

	var spo = visit.getParent() ;
	if(spo==null) throw "Визит не присоединен к СПО" ;
	if(spo.getDateFinish()!=null) throw "СПО уже закрыто" ;
	
	// FIXME диагноз
	return closeSpo(aContext, spo.id) ;
}
function trim(aStr) {
	return aStr.replace(/^\s+|\s+$/g,'');
}

/** Формирование визитов к врачам стационарных отделения
 *  формируются визиты к тем специалистам, оформившие дневник + установившие диагноз в приемном отделении.
 * */
function createNewVisitByDeniedDiary(aContext,aVocWorkFunctions,aVocWorkFunction,aFilterMkb,aBeginDate,aFinishDate,aDepartmentPolyclinic) {
	var username = aContext.getSessionContext().getCallerPrincipal().toString() ;
//	aDepartmentPolyclinic=256;
	var filterMkbSql = "" ;
	var manager = aContext.manager;
	if (aFilterMkb!=null && aFilterMkb!="") {
		aFilterMkb = aFilterMkb.toUpperCase() ;
		
		var fs1=aFilterMkb.split(",") ;
		var filtOr = new java.lang.StringBuilder() ;
		
		for (var i=0;i<fs1.length;i++) {
			var filt1 = trim(fs1[i]) ;
			var fs=filt1.split("-") ;
			if (filt1.length()>0) {
    			if (filtOr.length()>0) filtOr.append(" or ") ;
	    		if (fs.length>1) {
	    			filtOr.append(" ").append(aFldId).append(" between '"+trim(fs[0])+"' and '"+trim(fs[1])+"'") ;
	    		} else {
	    			filtOr.append(" substring(").append(aFldId).append(",1,"+filt1.length()+") = '"+filt1+"'") ;
	    		}
			}
		}
		
		if (filtOr.length()>0) {
			filtOr.insert(0, " and (").append(")") ;
    		filterMkbSql = filtOr.toString() ;
		}
	}
	// Создание мед.карты //Исключаем создание дублей
	sql = "insert into Medcard (number,dateregistration,registrator,person_id)"
		+" select p.patientSync,min(sls.datestart),'"+username+"',p.id as patid"
		+"  from MedCase sls"
		+" left join patient p on p.id=sls.patient_id"
		+" left join medcard mp on mp.person_id=p.id"
		+" left join diary d on d.medcase_id=sls.id"
		+" left join workfunction wf on wf.id=d.specialist_id"
		+" left join vocworkfunction vwf on vwf.id=wf.workFunction_id"
		+" left join diagnosis diag on diag.medcase_id=sls.id and diag.registrationType_id in (1,4)"
		+" left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id"
		+" left join workfunction dwf on dwf.id=diag.medicalWorker_id"
		+" left join vocidc10 mkb on mkb.id=diag.idc10_id"
		+" where sls.dtype='HospitalMedCase' and sls.dateStart between to_date('"+aBeginDate+"','dd.mm.yyyy') and to_date('"+aFinishDate+"','dd.mm.yyyy')"
		+" and sls.deniedHospitalizating_id is not null";
	if (aVocWorkFunctions!=null&&aVocWorkFunctions!='') {
        sql+=" and vwf.id in ("+aVocWorkFunctions+") ";
	} else {
        sql+=" and vwf.id is not null ";
	}
	sql+=" and sls.medicalAid='1'"
		+" and diag.id is not null and mp.id is null "+filterMkbSql
		+" group by p.patientSync,p.id";
	manager.createNativeQuery(sql).executeUpdate() ;
	// Список талонов
	sql = "select coalesce(sls.serviceStream_id,1) as serviceStream,case when sls.emergency='1' then '1' else '0' end as emergency"
		+" ,to_char(sls.datestart,'dd.mm.yyyy') as dateStart,sls.entranceTime,wf.id as wfNid"
		+" ,max(mp.id) as medcard,sls.patient_id,coalesce(sls.hospitalization_id,'1')"
		+" ,max(diag.id) as diagid" //Берем самый последний установленный специалистом диагноз
		+" 	 from MedCase sls"
		+" 	left join patient p on p.id=sls.patient_id"
		+" 	left join medcard mp on mp.person_id=p.id"
		+"  left join diary d on d.medcase_id=sls.id"
		+" 	left join workfunction wf on wf.id=d.specialist_id"
		+" 	left join vocworkfunction vwf on vwf.id=wf.workFunction_id"
		+" 	left join diagnosis diag on diag.medcase_id=sls.id and diag.registrationType_id in (1,4)"
		+" 	left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id"
		+" 	left join workfunction dwf on dwf.id=diag.medicalWorker_id"
		+" 	left join vocidc10 mkb on mkb.id=diag.idc10_id"
		+" 	where sls.dtype='HospitalMedCase' and sls.dateStart between to_date('"+aBeginDate+"','dd.mm.yyyy') and to_date('"+aFinishDate+"','dd.mm.yyyy')"
		+" 	and sls.deniedHospitalizating_id is not null";
    if (aVocWorkFunctions!=null&&aVocWorkFunctions!='') {
        sql+=" and vwf.id in ("+aVocWorkFunctions+") ";
    } else {
        sql+=" and vwf.id is not null ";
    }
    sql+=" and sls.medicalAid='1'"
		+" and diag.id is not null and mp.id is not null "+filterMkbSql
		+" and wf.id=dwf.id"
		+"  and (select count(*) from medcase t where t.patient_id=sls.patient_id and t.workFunctionExecute_id=wf.id and t.datestart=sls.datestart and t.dtype='ShortMedCase')=0"
		+" group by sls.serviceStream_id,sls.emergency,sls.datestart,sls.entranceTime,wf.id,sls.patient_id,sls.hospitalization_id,p.lastname,p.firstname,p.middlename"
		+" 	order by sls.dateStart,p.lastname,p.firstname,p.middlename" ;
	var list = manager.createNativeQuery(sql).getResultList() ;
	var visitResult = "3" ;
	var visitReason = "2" ;
	var workPlaceType="1" ;
	// Обработка отказов и создание новых талонов
	for (var i=0;i<list.size();i++) {
		var obj = list.get(i) ;
		// создание спо
		sql = "insert into MedCase (dtype,createtime,createdate,username"
			+",serviceStream_id,emergency,dateStart,dateFinish,startFunction_id,finishFunction_id,ownerFunction_id"
			+" ,medcard_id,patient_id,noActuality"
			+" ) values ("
			+" 'PolyclinicMedCase',CURRENT_TIME,CURRENT_DATE,'"+username+"'"
			+",'"+obj[0]+"','"+obj[1]+"',to_date('"+obj[2]+"','dd.mm.yyyy'),to_date('"+obj[2]+"','dd.mm.yyyy'),'"+obj[4]+"'"
			+",'"+obj[4]+"','"+obj[4]+"','"
			+obj[5]+"','"+obj[6]+"','0'"
			+")" ;
		manager.createNativeQuery(sql).executeUpdate() ;
		var sql = "select id from medcase where dtype='PolyclinicMedCase' and startFunction_id='"+obj[4]+"'"
		+" and patient_id='"+obj[6]+"' and dateStart=to_date('"+obj[2]+"','dd.mm.yyyy')" ;
		var listspo = manager.createNativeQuery(sql).setMaxResults(1).getResultList() ;
		var idspo=0 ;
		if (!listspo.isEmpty()) {idspo=listspo.get(0) ;}else {throw 'Проблема с определением СПО по отказу №'+obj  ;}
		// создание визита
		sql = "insert into MedCase (parent_id,dtype,createtime,createdate,username"
			+",serviceStream_id,emergency,dateStart,timeExecute,workfunctionExecute_id"
			+" ,medcard_id,patient_id,hospitalization_id"
			+",visitResult_id,visitReason_id,workPlaceType_id,noActuality"
			+" ) values ("
			+" '"+idspo+"','ShortMedCase',CURRENT_TIME,CURRENT_DATE,'"+username+"'"
			+",'"+obj[0]+"','"+obj[1]+"',to_date('"+obj[2]+"','dd.mm.yyyy'),'"+obj[3]+"','"+obj[4]+"'"
			+",'"+obj[5]+"','"+obj[6]+"','"+obj[7]+"'"
			+",'"+visitResult+"','"+visitReason+"','"+workPlaceType+"','0'"
			+")" ;
		manager.createNativeQuery(sql).executeUpdate() ;
		var sql = "select id from medcase where parent_id='"+idspo+"' and dtype='ShortMedCase' and workFunctionExecute_id='"+obj[4]+"'"
		+" and patient_id='"+obj[6]+"' and dateStart=to_date('"+obj[2]+"','dd.mm.yyyy')" ;
		var listvis = manager.createNativeQuery(sql).setMaxResults(1).getResultList() ;
		var idvis=0 ;
		if (!listvis.isEmpty()) {idvis=listvis.get(0) ;}else {throw 'Проблема с определением визита по отказу №'+obj  ;}
		
		// создание диагноза
		sql = "insert into diagnosis (patient_id,priority_id,medcase_id,idc10_id,name,illnesPrimary_id,medicalWorker_id) select patient_id,priority_id,'"+idvis+"',idc10_id,name,illnesPrimary_id,'"+obj[4]+"' from diagnosis where id="+obj[8] ;
		manager.createNativeQuery(sql).executeUpdate() ;
	}
}

//@deprecated
function createNewVisitByDenied(aContext,aDepartment,aBeginDate,aFinishDate,aDepartmentPolyclinic) {
	if (1==1) throw "Невозможно!";

	var username = aContext.getSessionContext().getCallerPrincipal().toString() ;
	aDepartmentPolyclinic=256;
	//--createNewEmergencySpec(aContext,aDepartment,aGroup);
	var sql ;
	if (+aDepartment>0) {
		sql = "select id,emergencyCabinet from mislpu where id="+aDepartment ;
	} else {
		sql = "select id,emergencyCabinet from mislpu where emergencyCabinet>0" ;
	}
	var lCab = aContext.manager.createNativeQuery(sql).getResultList() ;
	// Создание мед.карты
	for (var ind=0;ind<lCab.size();ind++) { 
		aDepartment = +lCab.get(ind)[0] ;
		group = +lCab.get(ind)[1] ;
		//throw aDepartment+" -- "+group ;
		if (group>0) createNewEmergencySpec(aContext,aDepartment,group);
		sql = "insert into Medcard (number,dateregistration,registrator,person_id)"
			+" select p.patientSync,sls.datestart,'admin',p.id as patid"
			+"  from MedCase sls"
			+" left join patient p on p.id=sls.patient_id"
			+" left join medcard mp on mp.person_id=p.id"
			+" left join workfunction wf on wf.id=sls.ownerFunction_id"
			+" left join worker w on w.id=wf.worker_id"
			+" left join vocworkfunction vwf on vwf.id=wf.workFunction_id"
			+" left join diagnosis diag on diag.medcase_id=sls.id and diag.registrationType_id in (1,4)"
			+" left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id"
			+" left join workfunction dwf on dwf.id=diag.medicalWorker_id"
			+" left join worker dw on dw.id=dwf.worker_id"
			+" left join patient dwp on dwp.id=dw.person_id"
			+" left join worker wN on Wn.person_id=dw.person_id and wN.lpu_id="+aDepartmentPolyclinic
			+" left join workfunction wfN on wfN.worker_id=wN.id"
			+" left join vocworkfunction dvwf on dvwf.id=dwf.workFunction_id"
			+" left join vocidc10 mkb on mkb.id=diag.idc10_id"
			+" where sls.dtype='HospitalMedCase' and sls.dateStart between to_date('"+aBeginDate+"','dd.mm.yyyy') and to_date('"+aFinishDate+"','dd.mm.yyyy')"
			+" and sls.deniedHospitalizating_id is not null"
			+" and sls.department_id='"+aDepartment+"' and sls.medicalAid='1'"
			+" and diag.id is not null and mp.id is null"
			+" order by sls.dateStart,p.lastname,p.firstname,p.middlename" ;
		aContext.manager.createNativeQuery(sql).executeUpdate() ;
		// Список талонов
		sql = "select sls.serviceStream_id,case when sls.emergency='1' then '1' else '0' end as emergency"
			+" ,to_char(sls.datestart,'dd.mm.yyyy') as dateStart,sls.entranceTime,wfN.id as wfNid"
			+" ,mp.id as medcard,sls.patient_id,coalesce(sls.hospitalization_id,'1')"
			+" ,diag.id as diagid"
			+" 	 from MedCase sls"
			+" 	left join patient p on p.id=sls.patient_id"
			+" 	left join medcard mp on mp.person_id=p.id"
		//	+" 	left join workfunction wf on wf.id=sls.ownerFunction_id"
		//	+" 	left join worker w on w.id=wf.worker_id"
		//	+" 	left join vocworkfunction vwf on vwf.id=wf.workFunction_id"
			+" 	left join diagnosis diag on diag.medcase_id=sls.id and diag.registrationType_id in (1,4)"
			+" 	left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id"
			+" 	left join workfunction dwf on dwf.id=diag.medicalWorker_id"
			+" 	left join worker dw on dw.id=dwf.worker_id"
			+" 	left join patient dwp on dwp.id=dw.person_id"
			+" 	left join worker wN on Wn.person_id=dw.person_id and wN.lpu_id="+aDepartmentPolyclinic
			+" 	left join workfunction wfN on wfN.worker_id=wN.id"
			+" 	left join vocworkfunction dvwf on dvwf.id=dwf.workFunction_id"
			+" 	left join vocworkfunction vwfN on vwfN.id=wfN.workFunction_id"
			+" 	left join vocidc10 mkb on mkb.id=diag.idc10_id"
			+" 	where sls.dtype='HospitalMedCase' and sls.dateStart between to_date('"+aBeginDate+"','dd.mm.yyyy') and to_date('"+aFinishDate+"','dd.mm.yyyy')"
			+" 	and sls.deniedHospitalizating_id is not null"
			+" 	and sls.department_id='"+aDepartment+"' and sls.medicalAid='1'"
			+" 	and diag.id is not null and (select count(*) from medcase t where t.patient_id=sls.patient_id and t.workFunctionExecute_id=wfN.id and t.datestart=sls.datestart and t.dtype='ShortMedCase')=0"
			+" 	and wfN.id is not null and dvwf.code=vwfN.code"
			+" 	order by sls.dateStart,p.lastname,p.firstname,p.middlename" ;
			var list = aContext.manager.createNativeQuery(sql).getResultList() ;
		var visitResult = "3" ;
		var visitReason = "2" ;
		var workPlaceType="1" ;
		// Обработка отказов и создание новых талонов
		for (var i=0;i<list.size();i++) {
			var obj = list.get(i) ;
			// создание спо
			sql = "insert into MedCase (dtype,createtime,createdate,username"
				+",serviceStream_id,emergency,dateStart,dateFinish,startFunction_id,finishFunction_id,ownerFunction_id"
				+" ,medcard_id,patient_id,noActuality"
				+" ) values ("
				+" 'PolyclinicMedCase',CURRENT_TIME,CURRENT_DATE,'"+username+"'"
				+",'"+obj[0]+"','"+obj[1]+"',to_date('"+obj[2]+"','dd.mm.yyyy'),to_date('"+obj[2]+"','dd.mm.yyyy'),'"+obj[4]+"'"
				+",'"+obj[4]+"','"+obj[4]+"','"
				+obj[5]+"','"+obj[6]+"','0'"
				+")" ;
			aContext.manager.createNativeQuery(sql).executeUpdate() ;
			var sql = "select id from medcase where dtype='PolyclinicMedCase' and startFunction_id='"+obj[4]+"'"
				+" and patient_id='"+obj[6]+"' and dateStart=to_date('"+obj[2]+"','dd.mm.yyyy')" ;
			var listspo = aContext.manager.createNativeQuery(sql).setMaxResults(1).getResultList() ;
			var idspo=0 ;
			if (listspo.size()>0) {idspo=listspo.get(0) ;}else {throw 'Проблема с определением СПО по отказу №'+obj  ;}
			//throw ''+idspo ;
			// создание визита
			sql = "insert into MedCase (parent_id,dtype,createtime,createdate,username"
				+",serviceStream_id,emergency,dateStart,timeExecute,workfunctionExecute_id"
				+" ,medcard_id,patient_id,hospitalization_id"
				+",visitResult_id,visitReason_id,workPlaceType_id,noActuality"
				+" ) values ("
				+" '"+idspo+"','ShortMedCase',CURRENT_TIME,CURRENT_DATE,'"+username+"'"
				+",'"+obj[0]+"','"+obj[1]+"',to_date('"+obj[2]+"','dd.mm.yyyy'),'"+obj[3]+"','"+obj[4]+"'"
				+",'"+obj[5]+"','"+obj[6]+"','"+obj[7]+"'"
				+",'"+visitResult+"','"+visitReason+"','"+workPlaceType+"','0'"
				+")" ;
			aContext.manager.createNativeQuery(sql).executeUpdate() ;
			sql = "select id from medcase where parent_id='"+idspo+"' and dtype='ShortMedCase' and workFunctionExecute_id='"+obj[4]+"'"
			+" and patient_id='"+obj[6]+"' and dateStart=to_date('"+obj[2]+"','dd.mm.yyyy')" ;
			var listvis = aContext.manager.createNativeQuery(sql).setMaxResults(1).getResultList() ;
			var idvis=0 ;
			if (listvis.size()>0) {idvis=listvis.get(0) ;}else {throw 'Проблема с определением визита по отказу №'+obj  ;}
			
			// создание диагноза
			sql = "insert into diagnosis (patient_id,priority_id,medcase_id,idc10_id,name,illnesPrimary_id,medicalWorker_id) select patient_id,priority_id,'"+idvis+"',idc10_id,name,illnesPrimary_id,'"+obj[4]+"' from diagnosis where id="+obj[8] ;
			aContext.manager.createNativeQuery(sql).executeUpdate() ;
		}
	}
}
/**
 * Закрыть СПО
 */
function closeSpoByCurrentDate(aContext,aSpoId) {
    checkIfExistingActRVKClosed(aSpoId,aContext);
	var listOpenVis = aContext.manager.createNativeQuery("select vis.id as visid"
			+" ,vis.dateStart as mkbid"
			+" from MedCase vis"
			+" where vis.parent_id="+aSpoId
			+" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase') and (vis.noactuality is null or vis.noactuality='0')"
			+" and vis.dateStart is null"
			).setMaxResults(1).getResultList() ; //активные направления
	var listVisLast = aContext.manager.createNativeQuery("select vis.id as visid"
			+" ,mkb.id as mkbid, to_char(vis.dateStart,'dd.mm.yyyy') as dateStart, vis.workFunctionExecute_id "
			+" from MedCase vis"
			+"     left join WorkFunction wf on vis.workFunctionExecute_id = wf.id"
			+"     left join VocWorkFunction vwf on vwf.id=wf.workFunction_id"
			+"     left join Worker w on wf.worker_id = w.id"
			+"     left join Patient pat on w.person_id = pat.id"
			+" left join VocReason vr on vis.visitReason_id = vr.id"
			+" left join VocServiceStream vss on vss.id=vis.serviceStream_id"
			+" left join Diagnosis diag on diag.medcase_id=vis.id"
			+" left join VocIdc10 mkb on mkb.id=diag.idc10_id"
			+" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
			+" left join VocVisitResult vvr on vvr.id=vis.visitResult_id"
			+" where vis.parent_id="+aSpoId
			+" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase')"
			+" and vis.dateStart is not null"
			+" and (vpd.code='1' or vpd.id is null) and (vis.noActuality='0' or vis.noActuality='1')"
			+" group by vis.id, vis.dateStart,vis.workfunctionexecute_id, vis.timeExecute,vwf.name, pat.lastname,  pat.firstname,  pat.middlename"
			+" ,vr.name ,vss.name,vvr.name,vpd.code,vpd.id,mkb.id"
			+" order by vis.dateStart desc, vis.timeExecute desc").setMaxResults(1).getResultList() ;
	if (listOpenVis.isEmpty() && !listVisLast.isEmpty()) { //нет направлений и есть визиты
		var listVisFirst = aContext.manager.createNativeQuery("select vis.id as visid"
				+" ,mkb.id as mkbid, to_char(vis.dateStart,'dd.mm.yyyy') as dateStart, vis.workFunctionExecute_id"
				+" from MedCase vis"
				+"     left join WorkFunction wf on vis.workFunctionExecute_id = wf.id"
				+"     left join VocWorkFunction vwf on vwf.id=wf.workFunction_id"
				+"     left join Worker w on wf.worker_id = w.id"
				+"     left join Patient pat on w.person_id = pat.id"
				+" left join VocReason vr on vis.visitReason_id = vr.id"
				+" left join VocServiceStream vss on vss.id=vis.serviceStream_id"
				+" left join Diagnosis diag on diag.medcase_id=vis.id"
				+" left join VocIdc10 mkb on mkb.id=diag.idc10_id"
				+" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
				+" left join VocVisitResult vvr on vvr.id=vis.visitResult_id"
				+" where vis.parent_id="+aSpoId
				+" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase')"
				+" and vis.dateStart is not null"
				+" and (vpd.code='1' or vpd.id is null) and (vis.noActuality='0' or vis.noActuality='1')"
				+" group by vis.id, vis.dateStart,vis.workfunctionexecute_id, vis.timeExecute,vwf.name, pat.lastname,  pat.firstname,  pat.middlename"
				+" ,vr.name ,vss.name,vvr.name,vpd.code,vpd.id,mkb.id"
				+" order by vis.dateStart, vis.timeExecute").setMaxResults(1).getResultList() ;		
	//	var visFirst = listVisFirst.get(0)[0];
		var visLast = listVisLast.get(0)[0];
		var mkb = listVisLast.get(0)[1];
		if (mkb==null) {
			var listMkb = aContext.manager.createNativeQuery("select vis.id as visid"
					+" ,mkb.id as mkbid, to_char(vis.dateStart,'dd.mm.yyyy') as dateStart, vis.workFunctionExecute_id"
					+" from MedCase vis"
					+"     left join WorkFunction wf on vis.workFunctionExecute_id = wf.id"
					+"     left join VocWorkFunction vwf on vwf.id=wf.workFunction_id"
					+"     left join Worker w on wf.worker_id = w.id"
					+"     left join Patient pat on w.person_id = pat.id"
					+" left join VocReason vr on vis.visitReason_id = vr.id"
					+" left join VocServiceStream vss on vss.id=vis.serviceStream_id"
					+" left join Diagnosis diag on diag.medcase_id=vis.id"
					+" left join VocIdc10 mkb on mkb.id=diag.idc10_id"
					+" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
					+" left join VocVisitResult vvr on vvr.id=vis.visitResult_id"
					+" where vis.parent_id="+aSpoId
					+" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase')"
					+" and vis.dateStart is not null"
					+" and (vpd.code='1' or vpd.id is null) and (vis.noActuality='0' or vis.noActuality is null) and mkb.id is not null"
					+" group by vis.id, vis.dateStart,vis.workfunctionexecute_id, vis.timeExecute,vwf.name, pat.lastname,  pat.firstname,  pat.middlename"
					+" ,vr.name ,vss.name,vvr.name,vpd.code,vpd.id,mkb.id"
					+" order by vis.dateStart desc, vis.timeExecute desc").setMaxResults(1).getResultList() ;
			 mkb = listMkb.size()>0?listMkb.get(0)[1]:null;
		}
		var dateStart = listVisFirst.get(0)[2];
	//	var dateFinish = listVisLast.get(0)[2];
		var startWF = listVisFirst.get(0)[3];
		var finishWF = listVisLast.get(0)[3];
		aContext.manager.createNativeQuery("update medcase set dateFinish=CURRENT_DATE,dateStart=to_date('"+dateStart
				+"','dd.mm.yyyy'),finishFunction_id='"+finishWF+"',startFunction_id='"+startWF
				+"'"+(mkb!=null?(",idc10_id='"+mkb+"'"):"")+" where id="+aSpoId).executeUpdate() ;
        setSpoSstreamLikeLastVisit(aContext,aSpoId,visLast);
	} else {
		if(listVisLast.size()==0) throw "Нет ни одного присоединенного визита к СПО с основным диагнозом!!!" ;
	}
	return aSpoId;
}
function closeSpo(aContext, aSpoId) {
    checkIfExistingActRVKClosed(aSpoId,aContext);
	var listOpenVis = aContext.manager.createNativeQuery("select vis.id as visid"
			+" ,vis.dateStart as mkbid"
			+" from MedCase vis"
			+" where vis.parent_id="+aSpoId
			+" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase')"
			+" and vis.dateStart is null"
			+ "  and (vis.noactuality=null or vis.noactuality='0')" //только актуальные визиты надо проверять
	).setMaxResults(1).getResultList() ;
	var listVisLast = aContext.manager.createNativeQuery("select vis.id as visid"
			+" ,mkb.id as mkbid, to_char(vis.dateStart,'dd.mm.yyyy') as dateStart, vis.workFunctionExecute_id"
			+" from MedCase vis"
			+"     left join WorkFunction wf on vis.workFunctionExecute_id = wf.id"
			+"     left join VocWorkFunction vwf on vwf.id=wf.workFunction_id"
			+"     left join Worker w on wf.worker_id = w.id"
			+"     left join Patient pat on w.person_id = pat.id"
			+" left join VocReason vr on vis.visitReason_id = vr.id"
			+" left join VocServiceStream vss on vss.id=vis.serviceStream_id"
			+" left join Diagnosis diag on diag.medcase_id=vis.id"
			+" left join VocIdc10 mkb on mkb.id=diag.idc10_id"
			+" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
			+" left join VocVisitResult vvr on vvr.id=vis.visitResult_id"
			+" where vis.parent_id="+aSpoId
			+" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase')"
			+" and vis.dateStart is not null"
			+" and (vpd.code='1' or vpd.id is null)"
			+" group by vis.id, vis.dateStart,vis.workfunctionexecute_id, vis.timeExecute,vwf.name, pat.lastname,  pat.firstname,  pat.middlename"
			+" ,vr.name ,vss.name,vvr.name,vpd.code,vpd.id,mkb.id"
			+" order by vis.dateStart desc, vis.timeExecute desc").setMaxResults(1).getResultList() ;
	if (listOpenVis.size()==0&&listVisLast.size()>0) {
		var listVisFirst = aContext.manager.createNativeQuery("select vis.id as visid"
				+" ,mkb.id as mkbid, to_char(vis.dateStart,'dd.mm.yyyy') as dateStart, vis.workFunctionExecute_id"
				+" from MedCase vis"
				+"     left join WorkFunction wf on vis.workFunctionExecute_id = wf.id"
				+"     left join VocWorkFunction vwf on vwf.id=wf.workFunction_id"
				+"     left join Worker w on wf.worker_id = w.id"
				+"     left join Patient pat on w.person_id = pat.id"
				+" left join VocReason vr on vis.visitReason_id = vr.id"
				+" left join VocServiceStream vss on vss.id=vis.serviceStream_id"
				+" left join Diagnosis diag on diag.medcase_id=vis.id"
				+" left join VocIdc10 mkb on mkb.id=diag.idc10_id"
				+" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
				+" left join VocVisitResult vvr on vvr.id=vis.visitResult_id"
				+" where vis.parent_id="+aSpoId
				+" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase')"
				+" and vis.dateStart is not null"
				+" and (vpd.code='1' or vpd.id is null) "
				+" group by vis.id, vis.dateStart,vis.workfunctionexecute_id, vis.timeExecute,vwf.name, pat.lastname,  pat.firstname,  pat.middlename"
				+" ,vr.name ,vss.name,vvr.name,vpd.code,vpd.id,mkb.id"
				+" order by vis.dateStart, vis.timeExecute").setMaxResults(1).getResultList() ;		
		var visFirst = listVisFirst.get(0)[0];
		//var visFirstO = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase
		//		, java.lang.Long.valueOf(visFirst)) ;
		var visLast = listVisLast.get(0)[0];
		var mkb = listVisLast.get(0)[1];
		if (mkb==null) {
			var listMkb = aContext.manager.createNativeQuery("select vis.id as visid"
					+" ,mkb.id as mkbid, to_char(vis.dateStart,'dd.mm.yyyy') as dateStart, vis.workFunctionExecute_id"
					+" from MedCase vis"
					+"     left join WorkFunction wf on vis.workFunctionExecute_id = wf.id"
					+"     left join VocWorkFunction vwf on vwf.id=wf.workFunction_id"
					+"     left join Worker w on wf.worker_id = w.id"
					+"     left join Patient pat on w.person_id = pat.id"
					+" left join VocReason vr on vis.visitReason_id = vr.id"
					+" left join VocServiceStream vss on vss.id=vis.serviceStream_id"
					+" left join Diagnosis diag on diag.medcase_id=vis.id"
					+" left join VocIdc10 mkb on mkb.id=diag.idc10_id"
					+" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
					+" left join VocVisitResult vvr on vvr.id=vis.visitResult_id"
					+" where vis.parent_id="+aSpoId
					+" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase')"
					+" and vis.dateStart is not null"
					+" and (vpd.code='1' or vpd.id is null) and (vis.noActuality='0' or vis.noActuality is null) and mkb.id is not null"
					+" group by vis.id, vis.dateStart,vis.workfunctionexecute_id, vis.timeExecute,vwf.name, pat.lastname,  pat.firstname,  pat.middlename"
					+" ,vr.name ,vss.name,vvr.name,vpd.code,vpd.id,mkb.id"
					+" order by vis.dateStart desc, vis.timeExecute desc").setMaxResults(1).getResultList() ;
			mkb = listMkb.size()>0?listMkb.get(0)[1]:null;

			
		}
		var dateStart = listVisFirst.get(0)[2];
		var dateFinish = listVisLast.get(0)[2];
		var startWF = listVisFirst.get(0)[3];
		var finishWF = listVisLast.get(0)[3];
		aContext.manager.createNativeQuery("update medcase set dateFinish=to_date('"+dateFinish
				+"','dd.mm.yyyy'),dateStart=to_date('"+dateStart
				+"','dd.mm.yyyy'),finishFunction_id='"+finishWF+"',startFunction_id='"+startWF
				+"'"+(mkb!=null?(",idc10_id='"+mkb+"'"):"")+" where id="+aSpoId).executeUpdate() ;
        setSpoSstreamLikeLastVisit(aContext,aSpoId,visLast);
	} else {
		if(listVisLast.size()==0) throw "Нет ни одного присоединенного визита к СПО с основным диагнозом!!!" ;
		if (listOpenVis.size()>0) throw "Есть актуальные направления! <a href='entityParentView-smo_visit.do?id="+listOpenVis.get(0)[0]+"'>Перейти к нему</a>"
	}
	return aSpoId;
}
function closeSpoWithoutDiagnosis(aContext, aSpoId, aMkbId, aDateFinish) {
	return closeSpoWithoutDiagnosis(aContext, aSpoId, aMkbId, aDateFinish, null); 
}
function closeSpoWithoutDiagnosis(aContext, aSpoId, aMkbId, aDateFinish, aWorkFunctionClose) {
    checkIfExistingActRVKClosed(aSpoId,aContext);
	var listOpenVis = aContext.manager.createNativeQuery("select vis.id as visid"
			+" ,vis.dateStart as mkbid"
			+" from MedCase vis"
			+" where vis.parent_id="+aSpoId
			+" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase')"
			+" and vis.dateStart is null"
			).setMaxResults(1).getResultList() ;
	var listVisLastSql="select vis.id as visid"
			+" ,mkb.id as mkbid, " ;
	if (aDateFinish!=null && aDateFinish!='') {
		listVisLastSql = listVisLastSql +"case when vis.dateStart>"+aDateFinish+" then to_char(vis.dateStart,'dd.mm.yyyy') else to_char("+aDateFinish+",'dd.mm.yyyy') end " ;
	} else {
		listVisLastSql = listVisLastSql +"to_char(vis.dateStart,'dd.mm.yyyy')" ;
	}
	
	listVisLastSql = listVisLastSql +" as dateStart" ;
	if (aWorkFunctionClose!=null&&+aWorkFunctionClose>0) {
	
		listVisLastSql = listVisLastSql +", case when vis.dateStart>"+aDateFinish+" then  vis.workFunctionExecute_id else "+aWorkFunctionClose+" end "
	} else {
		listVisLastSql = listVisLastSql +", vis.workFunctionExecute_id";
	}
	
	
	listVisLastSql += " from MedCase vis"
			+"     left join WorkFunction wf on vis.workFunctionExecute_id = wf.id"
			+"     left join VocWorkFunction vwf on vwf.id=wf.workFunction_id"
			+"     left join Worker w on wf.worker_id = w.id"
			+"     left join Patient pat on w.person_id = pat.id"
			+" left join VocReason vr on vis.visitReason_id = vr.id"
			+" left join VocServiceStream vss on vss.id=vis.serviceStream_id"
			+" left join Diagnosis diag on diag.medcase_id=vis.id"
			+" left join VocIdc10 mkb on mkb.id=diag.idc10_id"
			+" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
			+" left join VocVisitResult vvr on vvr.id=vis.visitResult_id"
			+" where vis.parent_id="+aSpoId
			+" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase')"
			+" and vis.dateStart is not null"
			+" and (vis.noActuality='0' or vis.noActuality='1')"
			+" group by vis.id, vis.dateStart,vis.workfunctionexecute_id, vis.timeExecute,vwf.name, pat.lastname,  pat.firstname,  pat.middlename"
			+" ,vr.name ,vss.name,vvr.name,vpd.code,vpd.id,mkb.id"
			+" order by vis.dateStart desc, vis.timeExecute desc" ;
	var listVisLast = aContext.manager.createNativeQuery(listVisLastSql).setMaxResults(1).getResultList() ;
	if (listOpenVis.size()==0&&listVisLast.size()>0) {
		var listVisFirstSql = "select vis.id as visid"
				+" ,mkb.id as mkbid,";
		if (aDateFinish!=null && aDateFinish!='') {
			listVisFirstSql = listVisFirstSql +"case when vis.dateStart<"+aDateFinish+" then to_char(vis.dateStart,'dd.mm.yyyy') else to_char("+aDateFinish+",'dd.mm.yyyy') end as dateStart" ;
		} else {
			listVisFirstSql = listVisFirstSql +"to_char(vis.dateStart,'dd.mm.yyyy') as dateStart" ;
		}
		
		if (aWorkFunctionClose!=null&&+aWorkFunctionClose>0) {
			
			listVisFirstSql = listVisFirstSql +", case when vis.dateStart<"+aDateFinish+" then  vis.workFunctionExecute_id else "+aWorkFunctionClose+" end "
		} else {
			listVisFirstSql = listVisFirstSql +", vis.workFunctionExecute_id";
		}
		
		listVisFirstSql +=" from MedCase vis"
				+"     left join WorkFunction wf on vis.workFunctionExecute_id = wf.id"
				+"     left join VocWorkFunction vwf on vwf.id=wf.workFunction_id"
				+"     left join Worker w on wf.worker_id = w.id"
				+"     left join Patient pat on w.person_id = pat.id"
				+" left join VocReason vr on vis.visitReason_id = vr.id"
				+" left join VocServiceStream vss on vss.id=vis.serviceStream_id"
				+" left join Diagnosis diag on diag.medcase_id=vis.id"
				+" left join VocIdc10 mkb on mkb.id=diag.idc10_id"
				+" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
				+" left join VocVisitResult vvr on vvr.id=vis.visitResult_id"
				+" where vis.parent_id="+aSpoId
				+" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase')"
				+" and vis.dateStart is not null"
				+" and (vis.noActuality='0' or vis.noActuality='1')"
				+" group by vis.id, vis.dateStart,vis.workfunctionexecute_id, vis.timeExecute,vwf.name, pat.lastname,  pat.firstname,  pat.middlename"
				+" ,vr.name ,vss.name,vvr.name,vpd.code,vpd.id,mkb.id"
				+" order by vis.dateStart, vis.timeExecute";
		var listVisFirst = aContext.manager.createNativeQuery(listVisFirstSql).setMaxResults(1).getResultList() ;		
		var visFirst = listVisFirst.get(0)[0];
		//var visFirstO = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase
		//		, java.lang.Long.valueOf(visFirst)) ;
		var visLast = listVisLast.get(0)[0];
		var mkb = aMkbId!=null?aMkbId: listVisLast.get(0)[1];
		var dateStart = listVisFirst.get(0)[2];
		var dateFinish = listVisLast.get(0)[2];
		var startWF = listVisFirst.get(0)[3];
		var finishWF = listVisLast.get(0)[3];
		//throw "lastVisitID = "+visLast+": "+finishWF;
		aContext.manager.createNativeQuery("update medcase set dateFinish=to_date('"+dateFinish
				+"','dd.mm.yyyy'),dateStart=to_date('"+dateStart
				+"','dd.mm.yyyy'),finishFunction_id='"+finishWF+"',startFunction_id='"+startWF
				+"',idc10_id='"+mkb+"' where id="+aSpoId).executeUpdate() ;
        setSpoSstreamLikeLastVisit(aContext,aSpoId,visLast);
	} else {
		if(listVisLast.size()==0) throw "Нет ни одного присоединенного визита к СПО с основным диагнозом!!!" ;
	}
	return aSpoId;
}
/**
 * Открыть СПО
 */
function reopenSpo(aContext, aSpoId) {
	var spo = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase
		, java.lang.Long.valueOf(aSpoId)) ;
	spo.setDateFinish(null) ;
	spo.setFinishFunction(null) ;
	aContext.manager.persist(spo) ;
	return spo.getId();
}

function findSpoIdByVisit(aContext, aVisitId) {
	var visit = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit
		, java.lang.Long.valueOf(aVisitId)) ;
	if(!visit) throw "Не найден Визит с ид "+aVisitId ;
	if(!visit.parent) throw "У визита нет СПО" ;
	return visit.parent.id ;	
}

/**
 * Проставить поток обслуживания в СПО, как в последнем визите
 */
function setSpoSstreamLikeLastVisit(aContext, aSpoId, aVisLastId) {
    aContext.manager.createNativeQuery("update medcase set  servicestream_id=(select servicestream_id from medcase where id="+aVisLastId+") where id="+aSpoId).executeUpdate() ;
}

/**
 * Проверка на наличие открытого акта РВК. Если есть - выписку запретить #183
 * @param aForm форма
 * @param aCtx контекст
 */
function checkIfExistingActRVKClosed(sSpoId,aCtx) {
	var sql="select id from actrvk where datefinish is null and medcase_id=ANY(select id from medcase where parent_id=" + sSpoId + ")";
	var list = aCtx.manager.createNativeQuery(sql).getResultList();
	if (!list.isEmpty())
		throw ("<a href='entityEdit-rvk_aktVisit.do?id=" + list.get(0)+"'>Акт РВК</a> необходимо закрыть до закрытия СПО!");
}