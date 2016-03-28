function createNewEmergencySpec(aCtx,aLpu,aGroup) {
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
		+" where w.lpu_id="+aLpu
		+" group by wf.id,w.person_id,vwf.name,wp.lastname"
		+" having count(wfg1.lpu_id=wg.lpu_id)=0)";
	aCtx.manager.createNativeQuery(sql).executeUpdate() ;
	sql = "insert into workfunction (dtype,worker_id,group_id,workfunction_id,createusername,createdate,createtime)"
		+" ("
		+" select 'PersonalWorkFunction',"
		+" (select wn.id from worker wn where wn.lpu_id=(select gr.lpu_id from workfunction gr where gr.id="+aGroup+")"
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
		+" where w.lpu_id="+aLpu
		+" group by wf.id,vwf.name,wp.lastname,w.person_id"
		+" having count(wfg1.id="+aGroup+")=0)" ;
	aCtx.manager.createNativeQuery(sql).executeUpdate() ;
}

function deleteEmptySpo(aCtx,aParams) {
	var sql="delete from medcase spo where"
		+" spo.dtype='PolyclinicMedCase' and (select count(*) from MedCase v where v.parent_id=spo.id)=0"
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
	/*
	var lastname = aParams.get("lastname") ;
	var firstname = aParams.get("firstname") ;
	var middlename = aParams.get("middlename") ;
	var birthday = aParams.get("birthday") ;
	var policySeries = aParams.get("policySeries");
	var policyNumber = aParams.get("policyNumber") ;
	var rayon = aParams.get("rayon") ;
	var rayonName = aParams.get("rayonName") ; 
	var sql="" ;
	pretInfo = "" ;
	sql.append("select wct.id as wctid,wct.prePatient_id, to_char(wcd.calendarDate,'dd.mm.yyyy'), cast(wct.timeFrom as varchar(5)), vwf.name, wp.lastname as wplastname,wp.firstname as wpfirstname,wp.middlename as wpmiddlename ") ;
	sql.append(" ,coalesce(");
	//sql.append("pat.lastname||' '||pat.firstname||' '||pat.middlename||' '||to_char(p.birthday,'dd.mm.yyyy')||coalesce(' ('||pat.patientSync||')','')") ;
	sql.append(" p.lastname||' '||p.firstname||' '||p.middlename||' '||to_char(p.birthday,'dd.mm.yyyy') ||coalesce(' ('||p.patientSync||')','')") ;
	sql.append(",wct.prepatientInfo) as fio") ;
	sql.append(" from WorkCalendarTime wct left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id left join WorkCalendar wc on wc.id=wcd.workCalendar_id left join WorkFunction wf on wf.id=wc.workFunction_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id left join Worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id left join Patient p on p.id=wct.prePatient_id left join medpolicy mp on mp.patient_id=p.id where wcd.calendarDate>=CURRENT_DATE and (p.lastname like '").append(aLastname.toUpperCase()).append("%' ") ;
	preInfo.append(aLastname).append(" ") ;
	if (firstname!=null && firstname!=null) {
		sql=sql+" and p.firstname = '"+firstname.toUpperCase()+"' " ;
		preInfo=preInfo+firstname+" " ;
	} else {
		preInfo=preInfo+" " ;
	}
	if (middlename!=null && middlename!="") {
		sql=sql+" and p.middlename = '"+middlename.toUpperCase()+"' " ;
		preInfo=preInfo+middlename+" ";
	} else {
		preInfo=preInfo+" " ;
	}
	if (birthday!=null && birthday!="") {
		sql=sql+" and p.birthday=to_date('"+birthday+"','dd.mm.yyyy') " ;
		preInfo=preInfo+birthday+" ";
	} else {
		preInfo=preInfo+" " ;
	}
	if (policySeries!=null && policySeries!="") {
		sql=sql+" and mp.series='"+policySeries.toUpperCase()+"' " ;
		preInfo=preInfo+policySeries.toUpperCase()+" ";
	} else {
		preInfo=preInfo+" " ;
	}
	if (policyNumber!=null && !policyNumber.equals("")) {
		sql=sql+" and mp.polNumber='"+policyNumber.toUpperCase()+"' " ;
		preInfo=preInfo+policyNumber.toUpperCase()+" ";
	} else {
		preInfo=preInfo+" " ;
	}
	if (rayon!=null && rayon>Long.valueOf(0)) {
		sql=sql+" and p.rayon_id='"+rayon+"' " ;
		preInfo=preInfo+rayonName;
	} else {
		preInfo=preInfo+"" ;
	}
	//System.out.println(preInfo) ;
	sql=sql+"or wct.prePatientInfo = '").append(preInfo).append("') " ;
	*/
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
	var visit = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit
		, java.lang.Long.valueOf(aVisitId)) ;
	if (visit.timeExecute==null && visit.timePlan!=null) visit.timeExecute = visit.timePlan.timeFrom ;
	if (visit.dateStart==null && visit.datePlan!=null) visit.dateStart = visit.datePlan.calendarDate ;
	var list = aContext.manager.createQuery("from VocVisitResult where omcCode='-1'").getResultList() ;
	if (list.size()>0) {
		visit.visitResult = list.get(0) ;
		visit.noActuality = true ;
	} else {
		visit.noActuality = true ;
	}
	if (visit.timePlan!=null) {
		visit.timePlan.medCase = null ;
		visit.timePlan.prescription = null;
	}
	var startWF= aContext.serviceInvoke("WorkerService", "findLogginedWorkFunctionList")
	.iterator().next() ;
	/*
	if(visit.startWorker==null) {
		visit.startWorker = aContext.serviceInvoke("WorkerService", "findLogginedWorker") ;
	}*/
	// FIXME определять функцию правильно
	if(visit.workFunctionExecute==null) {
		visit.workFunctionExecute = startWF ;
	}
	return visit.getId();
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
function createNewVisitByDeniedDiary(aContext,aVocWorkFunctions,aVocWorkFunction,aFilterMkb,aBeginDate,aFinishDate,aDepartmentPolyclinic) {
	var username = aContext.getSessionContext().getCallerPrincipal().toString() ;
	aDepartmentPolyclinic=256;
	filterMkbSql = "" ;
	if (aFilterMkb!=null && aFilterMkb!="") {
		aFilterMkb = aFilterMkb.toUpperCase() ;
		
		var fs1=aFilterMkb.split(",") ;
		var filtOr = new java.lang.StringBuilder() ;
		
		for (var i=0;i<fs1.length;i++) {
			var filt1 = fs1[i].trim() ;
			var fs=filt1.split("-") ;
			if (filt1.length()>0) {
    			if (filtOr.length()>0) filtOr.append(" or ") ;
	    		if (fs.length>1) {
	    			filtOr.append(" ").append(aFldId).append(" between '"+fs[0].trim()+"' and '"+fs[1].trim()+"'") ;
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
	
	
	// Создание мед.карты
	sql = "insert into Medcard (number,dateregistration,registrator,person_id)"
		+" select p.patientSync,sls.datestart,'admin',p.id as patid"
		+"  from MedCase sls"
		+" left join patient p on p.id=sls.patient_id"
		+" left join medcard mp on mp.person_id=p.id"
		+" left join diary d on d.medcase_id=sls.id"
		+" left join workfunction wf on wf.id=d.specialist_id"
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
		+" and vwf.id in ("+aVocWorkFunctions+") and sls.medicalAid='1'"
		+" and diag.id is not null and mp.id is null "+filterMkbSql
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
		+"  left join diary d on d.medcase_id=sls.id"
		+" 	left join workfunction wf on wf.id=d.specialist_id"
		+" 	left join worker w on w.id=wf.worker_id"
		+" 	left join vocworkfunction vwf on vwf.id=wf.workFunction_id"
		+" 	left join diagnosis diag on diag.medcase_id=sls.id and diag.registrationType_id in (1,4)"
		+" 	left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id"
		+" 	left join workfunction dwf on dwf.id=diag.medicalWorker_id"
		+" 	left join worker dw on dw.id=dwf.worker_id"
		+" 	left join patient dwp on dwp.id=dw.person_id"
		+" 	left join worker wN on Wn.person_id=dw.person_id and wN.lpu_id="+aDepartmentPolyclinic
		+" 	left join workfunction wfN on wfN.worker_id=wN.id"
		+" 	left join vocworkfunction dvwf on dvwf.id=dwf.workFunction_id"
		+" 	left join vocidc10 mkb on mkb.id=diag.idc10_id"
		+" 	where sls.dtype='HospitalMedCase' and sls.dateStart between to_date('"+aBeginDate+"','dd.mm.yyyy') and to_date('"+aFinishDate+"','dd.mm.yyyy')"
		+" 	and sls.deniedHospitalizating_id is not null"
		+" and vwf.id in ("+aVocWorkFunctions+") and sls.medicalAid='1'"
		+" and diag.id is not null and mp.id is not null "+filterMkbSql
		+"  and (select count(*) from medcase t where t.patient_id=sls.patient_id and t.workFunctionExecute_id=wfN.id and t.datestart=sls.datestart and t.dtype='ShortMedCase')=0"
		+" 	and wfN.id is not null and wfN.workFunction_id="+aVocWorkFunction
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
		var sql = "select id from medcase where parent_id='"+idspo+"' and dtype='ShortMedCase' and workFunctionExecute_id='"+obj[4]+"'"
		+" and patient_id='"+obj[6]+"' and dateStart=to_date('"+obj[2]+"','dd.mm.yyyy')" ;
		var listvis = aContext.manager.createNativeQuery(sql).setMaxResults(1).getResultList() ;
		var idvis=0 ;
		if (listvis.size()>0) {idvis=listvis.get(0) ;}else {throw 'Проблема с определением визита по отказу №'+obj  ;}
		
		// создание диагноза
		sql = "insert into diagnosis (patient_id,priority_id,medcase_id,idc10_id,name,illnesPrimary_id,medicalWorker_id) select patient_id,priority_id,'"+idvis+"',idc10_id,name,illnesPrimary_id,'"+obj[4]+"' from diagnosis where id="+obj[8] ;
		aContext.manager.createNativeQuery(sql).executeUpdate() ;
	}
}
function createNewVisitByDenied(aContext,aDepartment,aBeginDate,aFinishDate,aDepartmentPolyclinic) {
	var username = aContext.getSessionContext().getCallerPrincipal().toString() ;
	aDepartmentPolyclinic=256;
	// Создание мед.карты
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
		+" 	left join workfunction wf on wf.id=sls.ownerFunction_id"
		+" 	left join worker w on w.id=wf.worker_id"
		+" 	left join vocworkfunction vwf on vwf.id=wf.workFunction_id"
		+" 	left join diagnosis diag on diag.medcase_id=sls.id and diag.registrationType_id in (1,4)"
		+" 	left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id"
		+" 	left join workfunction dwf on dwf.id=diag.medicalWorker_id"
		+" 	left join worker dw on dw.id=dwf.worker_id"
		+" 	left join patient dwp on dwp.id=dw.person_id"
		+" 	left join worker wN on Wn.person_id=dw.person_id and wN.lpu_id="+aDepartmentPolyclinic
		+" 	left join workfunction wfN on wfN.worker_id=wN.id"
		+" 	left join vocworkfunction dvwf on dvwf.id=dwf.workFunction_id"
		+" 	left join vocidc10 mkb on mkb.id=diag.idc10_id"
		+" 	where sls.dtype='HospitalMedCase' and sls.dateStart between to_date('"+aBeginDate+"','dd.mm.yyyy') and to_date('"+aFinishDate+"','dd.mm.yyyy')"
		+" 	and sls.deniedHospitalizating_id is not null"
		+" 	and sls.department_id='"+aDepartment+"' and sls.medicalAid='1'"
		+" 	and diag.id is not null and (select count(*) from medcase t where t.patient_id=sls.patient_id and t.workFunctionExecute_id=wfN.id and t.datestart=sls.datestart and t.dtype='ShortMedCase')=0"
		+" 	and wfN.id is not null"
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
		var sql = "select id from medcase where parent_id='"+idspo+"' and dtype='ShortMedCase' and workFunctionExecute_id='"+obj[4]+"'"
		+" and patient_id='"+obj[6]+"' and dateStart=to_date('"+obj[2]+"','dd.mm.yyyy')" ;
		var listvis = aContext.manager.createNativeQuery(sql).setMaxResults(1).getResultList() ;
		var idvis=0 ;
		if (listvis.size()>0) {idvis=listvis.get(0) ;}else {throw 'Проблема с определением визита по отказу №'+obj  ;}
		
		// создание диагноза
		sql = "insert into diagnosis (patient_id,priority_id,medcase_id,idc10_id,name,illnesPrimary_id,medicalWorker_id) select patient_id,priority_id,'"+idvis+"',idc10_id,name,illnesPrimary_id,'"+obj[4]+"' from diagnosis where id="+obj[8] ;
		aContext.manager.createNativeQuery(sql).executeUpdate() ;
	}
}
/**
 * Закрыть СПО
 */
function closeSpoByCurrentDate(aContext,aSpoId) {
	var listOpenVis = aContext.manager.createNativeQuery("select vis.id as visid"
			+" ,vis.dateStart as mkbid"
			+" from MedCase vis"
			+" where vis.parent_id="+aSpoId
			+" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase')"
			+" and vis.dateStart is null"
			).setMaxResults(1).getResultList() ;
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
				+" and (vpd.code='1' or vpd.id is null) and (vis.noActuality='0' or vis.noActuality='1')"
				+" group by vis.id, vis.dateStart,vis.workfunctionexecute_id, vis.timeExecute,vwf.name, pat.lastname,  pat.firstname,  pat.middlename"
				+" ,vr.name ,vss.name,vvr.name,vpd.code,vpd.id,mkb.id"
				+" order by vis.dateStart, vis.timeExecute").setMaxResults(1).getResultList() ;		
		var visFirst = listVisFirst.get(0)[0];
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
			var mkb = listMkb.size()>0?listMkb.get(0)[1]:null;

			
		}
		var dateStart = listVisFirst.get(0)[2];
		var dateFinish = listVisLast.get(0)[2];
		var startWF = listVisFirst.get(0)[3];
		var finishWF = listVisLast.get(0)[3];
		aContext.manager.createNativeQuery("update medcase set dateFinish=CURRENT_DATE,dateStart=to_date('"+dateStart
				+"','dd.mm.yyyy'),finishFunction_id='"+finishWF+"',startFunction_id='"+startWF
				+"'"+(mkb!=null?(",idc10_id='"+mkb+"'"):"")+" where id="+aSpoId).executeUpdate() ;
	} else {
		if(listVisLast.size()==0) throw "Нет ни одного присоединенного визита к СПО с основным диагнозом!!!" ;
	}
	return aSpoId;
}
function closeSpo(aContext, aSpoId) {
	var listOpenVis = aContext.manager.createNativeQuery("select vis.id as visid"
			+" ,vis.dateStart as mkbid"
			+" from MedCase vis"
			+" where vis.parent_id="+aSpoId
			+" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase')"
			+" and vis.dateStart is null"
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
			var mkb = listMkb.size()>0?listMkb.get(0)[1]:null;

			
		}
		var dateStart = listVisFirst.get(0)[2];
		var dateFinish = listVisLast.get(0)[2];
		var startWF = listVisFirst.get(0)[3];
		var finishWF = listVisLast.get(0)[3];
		aContext.manager.createNativeQuery("update medcase set dateFinish=to_date('"+dateFinish
				+"','dd.mm.yyyy'),dateStart=to_date('"+dateStart
				+"','dd.mm.yyyy'),finishFunction_id='"+finishWF+"',startFunction_id='"+startWF
				+"'"+(mkb!=null?(",idc10_id='"+mkb+"'"):"")+" where id="+aSpoId).executeUpdate() ;
	} else {
		if(listVisLast.size()==0) throw "Нет ни одного присоединенного визита к СПО с основным диагнозом!!!" ;
	}
	return aSpoId;
}
function closeSpoWithoutDiagnosis(aContext, aSpoId, aMkbId, aDateFinish) {
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
	
	listVisLastSql = listVisLastSql +" as dateStart, vis.workFunctionExecute_id"
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
			+" and (vis.noActuality='0' or vis.noActuality='1')"
			+" group by vis.id, vis.dateStart,vis.workfunctionexecute_id, vis.timeExecute,vwf.name, pat.lastname,  pat.firstname,  pat.middlename"
			+" ,vr.name ,vss.name,vvr.name,vpd.code,vpd.id,mkb.id"
			+" order by vis.dateStart desc, vis.timeExecute desc" ;
	var listVisLast = aContext.manager.createNativeQuery(listVisLastSql).setMaxResults(1).getResultList() ;
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
				+" and (vis.noActuality='0' or vis.noActuality='1')"
				+" group by vis.id, vis.dateStart,vis.workfunctionexecute_id, vis.timeExecute,vwf.name, pat.lastname,  pat.firstname,  pat.middlename"
				+" ,vr.name ,vss.name,vvr.name,vpd.code,vpd.id,mkb.id"
				+" order by vis.dateStart, vis.timeExecute").setMaxResults(1).getResultList() ;		
		var visFirst = listVisFirst.get(0)[0];
		//var visFirstO = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase
		//		, java.lang.Long.valueOf(visFirst)) ;
		var visLast = listVisLast.get(0)[0];
		var mkb = aMkbId!=null?aMkbId: listVisLast.get(0)[1];
		var dateStart = listVisFirst.get(0)[2];
		var dateFinish = listVisLast.get(0)[2];
		var startWF = listVisFirst.get(0)[3];
		var finishWF = listVisLast.get(0)[3];
		aContext.manager.createNativeQuery("update medcase set dateFinish=to_date('"+dateFinish
				+"','dd.mm.yyyy'),dateStart=to_date('"+dateStart
				+"','dd.mm.yyyy'),finishFunction_id='"+finishWF+"',startFunction_id='"+startWF
				+"',idc10_id='"+mkb+"' where id="+aSpoId).executeUpdate() ;
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

