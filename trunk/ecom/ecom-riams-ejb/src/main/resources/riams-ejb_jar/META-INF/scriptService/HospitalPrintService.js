var map = new java.util.HashMap() ;
/** Печать служебной записки по госпитализациям без прикрепленных полисов */
function receivedWithoutPolicy(aCtx,aParams) {
	var dateBegin = aParams.get("dateBegin") ;
	var dateEnd = aParams.get("dateEnd") ;
	//var typeView = +aParams.get("typeView") ;
	var typeDuration = +aParams.get("typeDuration") ;
	var typePatient = +aParams.get("typePatient") ;
	var typePatientIs = +aParams.get("typePatientIs") ;
	var typeEmergency = +aParams.get("typeEmergency") ;
	var emerSql = "" ;
	if (typeEmergency==1) {
		emerSql = " and hmc.emergency='1'" ;
	} else if (typeEmergency==2) {
		emerSql = " and (hmc.emergency is null or hmc.emergency='0')" ;
	}
	var periodSql ;
	if (typePatientIs==1) {
		periodSql = "  hmc.dateFinish is null " ;
	} else {
		if (dateEnd==null||dateEnd=='') dateEnd=dateBegin ;
		periodSql = "  hmc.dateStart between to_date('"+dateBegin+"','dd.mm.yyyy') and to_date('"+dateEnd+"','dd.mm.yyyy')"
	}
	var patSql = "" ;
	if (typePatient==1) {
		patSql = " and ok.voc_code is not null and ok.voc_code!='643'" ;
	} else if (typePatient==2){
		patSql = " and (ok.voc_code is null or ok.voc_code='643') and adr.kladr not like '30%'"
	}
	var durSql = ""
	if (typeDuration==1) {
		durSql = " and (CURRENT_DATE-hmc.dateStart)>3 "
	} else if (typeDuration==2) {
		durSql = " and (CURRENT_DATE-hmc.dateStart)<=3 "
	}
	
	var sqlDep = "select dep.id,dep.name"
	+" from Medcase hmc" 
	+" left join StatisticStub ss on ss.id=hmc.statisticStub_id" 
	+" left join MisLpu dep on dep.id=hmc.department_id" 
	+" left join vocservicestream vss on vss.id=hmc.servicestream_id" 
	+" left join patient pat on pat.id=hmc.patient_id" 
	+" left join vocAdditionStatus vas on vas.id=pat.additionStatus_id" 
	+" left join medcase_medpolicy pol on pol.medCase_id=hmc.id"
	+" left join Omc_Oksm ok on pat.nationality_id=ok.id"
	+" left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id"
	+" left join VocHospType vht on vht.id=hmc.hospType_id"
	+" left join address2 adr on adr.addressId = pat.address_addressId"
	+" where "+periodSql
	+"      and hmc.DTYPE='HospitalMedCase'"
	+" and (vss.code = 'OBLIGATORYINSURANCE')" 
	+" and hmc.deniedHospitalizating_id is null"
	+" "+patSql+" "+emerSql 
	+" group by dep.id, dep.name"
	//+" having count(pol.medCase_id)=0 "+durSql
	+" order by dep.id,dep.name" ;
	var listDep = aCtx.manager.createNativeQuery(sqlDep).getResultList() ;
	var retDep = new java.util.ArrayList() ;
	var j=1 ;
	//throw ""+listDep.size() ;
	for (var i=0; i < listDep.size(); i++) {
		var objDep = listDep.get(i) ;
		var depId = +objDep[0];
		var sql = "select hmc.id as hospid, vss.name as vssname, hmc.dateStart as hospdateStart"
		    +", ss.code as statcard, pat.lastname||' '||pat.firstname||' '||pat.middlename || ' г.р. '|| pat.birthday as pbirthday"
		    +", case when (ok.voc_code is not null and ok.voc_code!='643') then 'ИНОСТ'"  
		    +"when pvss.omccode='И0' then 'ИНОГ' else '' end as typePatient"
		    +",case when hmc.emergency='1' then 'Э' else 'П' end as emer"
		    +",sum("
		    +"	  case" 
			+"		when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1" 
			+"		when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1)" 
			+"		else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)"
			+"	  end"
		    +"	) as sum2"
		    +",hmc.dateFinish as hospdateFinish"
			+" from Medcase hmc" 
			+" left join StatisticStub ss on ss.id=hmc.statisticStub_id" 
			+" left join MisLpu dep on dep.id=hmc.department_id" 
			+" left join vocservicestream vss on vss.id=hmc.servicestream_id" 
			+" left join patient pat on pat.id=hmc.patient_id" 
			+" left join vocAdditionStatus vas on vas.id=pat.additionStatus_id" 
			+" left join medcase_medpolicy pol on pol.medCase_id=hmc.id"
			+" left join Omc_Oksm ok on pat.nationality_id=ok.id"
			+" left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id"
			+" left join VocHospType vht on vht.id=hmc.hospType_id"
			+" left join address2 adr on adr.addressId = pat.address_addressId"
			+" where "+periodSql
			+"      and hmc.DTYPE='HospitalMedCase'"
			+" and hmc.department_id='"+depId+"' and (vss.code = 'OBLIGATORYINSURANCE')" 
			+" and hmc.deniedHospitalizating_id is null"
			+" "+patSql+" "+emerSql 
			+" group by hmc.id, dep.name, vss.name, hmc.dateStart, ss.code" 
			+"    , vas.name , pat.id , pat.lastname,pat.firstname,pat.middlename" 
			+"    , pat.birthday ,ok.voc_code,pvss.omccode,hmc.emergency"
			+"    ,hmc.dateFinish"
			+" having count(pol.medCase_id)=0 "+durSql
			+" order by vss.name,pat.lastname,pat.firstname,pat.middlename" ;
	
		var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
		if (list.size()>0) {
			var ret = new java.util.ArrayList() ;
			var parDep = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			var depname = ""+objDep[1] ;
			parDep.set1(depname) ;
			j=1 ;
			for (var ii=0; ii < list.size(); ii++) {
				var obj = list.get(ii) ;
			
				var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
				par.set1(""+(j++)) ;
				par.set2(obj[1]) ;
				par.set3(obj[2]) ;
				par.set4(obj[3]) ;
				par.set5(obj[4]) ;
				par.set6(obj[5]) ;
				par.set6(obj[6]) ;
				par.set6(obj[7]) ;
				ret.add(par) ;
			}
			parDep.set2(ret) ;
			retDep.add(parDep) ;
		}
	} 
	map.put("listDep",retDep) ;
	return map ;
		
}
function printCoveringLetterByDay(aCtx,aParams) {
	//var ids1 = aParams.get("id") ;
	//var spec = aParams.get("spec") ;
	var dateBegin = aParams.get("dateBegin") ;
	//var dateEnd = aParams.get("dateEnd") ;
	var disc = aParams.get("dischargeIs") ;
	var dateI = "dateStart" ;
	var timeI = "entranceTime" ;
	var dischInfo = "поступившим " ;
	map.put("dates",dateBegin) ;
	if (disc!=null && disc=="on") {
		dateI="dateFinish" ;
		timeI="dischargeTime" ;
		dischInfo="выбывшим " ;
	} 
	var emerIs = "" ; 
	map.put("dischInfo",dischInfo) ;
	var sql = "select" 
    +" m.id as mid"
    +" ,to_char(m.dateStart,'dd.mm.yyyy')||' '||cast(m.entranceTime as varchar(5)) as mdateStart"
    +" ,to_char(m.dateFinish,'dd.mm.yyyy')||' '||cast(m.dischargeTime as varchar(5)) as mdateFinish"
    +" ,cast(m.dischargeTime as varchar(5)) as mdischargeTime"
    +" ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as fio"
    +" ,to_char(pat.birthday,'dd.mm.yyyy') as birthday,sc.code as sccode,m.emergency as memergency"
    +" ,ml.name as mlname,cast(m.entranceTime as varchar(5)) as entranceTime"
    +" ,pat.id||' ('||coalesce(pat.patientSync,'         ')||')'"
    +" from MedCase as m  "
    +" left join StatisticStub as sc on sc.medCase_id=m.id" 
    +" left outer join Patient pat on m.patient_id = pat.id" 
    +" left join MisLpu as ml on ml.id=m.department_id "
    +" left join VocServiceStream vss on vss.id=m.serviceStream_id"
    +" left join Omc_Oksm ok on pat.nationality_id=ok.id"
    +" where m.DTYPE='HospitalMedCase' and m."+dateI+" = to_date('"+dateBegin+"','dd.mm.yyyy')"
    +" and m.deniedHospitalizating_id is null"
    +"  "+emerIs
    +" and (ok.voc_code is null or ok.voc_code='643')"
    +" order by m."+dateI+",pat.lastname,pat.firstname,pat.middlename";
	
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	var ret = new java.util.ArrayList() ;
	var j=1 ;
	for (var i=0; i < list.size(); i++) {
		var obj = list.get(i) ;
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		par.set1(""+(j++)) ;
		par.set2(obj[6]) ;
		par.set3(obj[4]) ;
		par.set4(obj[10]) ;
		ret.add(par) ;
	} 

	//throw ""+deniedlist.size() ;
	map.put("listDep",ret) ;
	return map ;
}

function printReestrByDay(aCtx,aParams) {
	var ids = aParams.get("id") ;
	var id = ids.split(":") ;
	var dateBegin = id[3] ;
	var typeDate = +id[2] ;
	var typeHour = id[1] ;
	var typeEmergency = id[0] ;
	var emer = "" ;
	var emerInfo = "все" ;
	if (+typeEmergency==1) {
		emer=" and m.emergency='1' " ;
		emerInfo="экстренно" ;
	} else if (typeEmergency!=null && typeEmergency.equals("2")) {
		emer=" and (m.emergency is null or m.emergency = '0')" ;
		emerInfo="планово" ;
	} 
	map.put("emerInfo",emerInfo) ;
	
	var dateI = "dateStart" ;
	var timeI = "entranceTime" ;
	
	var pigeonHole = id[4] ;
	var pigeonHoleName = "все" ;
	var pigeonHoleId = "" ;
	var pigeonHoleId1 = "" ;
	if (pigeonHole!=null
			&& +pigeonHole>0) {
		pigeonHoleId = " and ml.pigeonHole_id='"+pigeonHole+"' " ;
		pigeonHoleId1 = " and (ml1.pigeonHole_id='"+pigeonHole+"' or ml.pigeonHole_id='"+pigeonHole+"') " ;
		var list = aCtx.manager.createNativeQuery("select vph.name,vph.id from VocPigeonHole vph where vph.id="+pigeonHole).setMaxResults(1).getResultList() ;
		if (list.size()>0) {
			var ob = list.get(0) ;
			pigeonHoleName = ob[0] ;
			
		}
	}
	map.put("pigeonHoleName",pigeonHoleName) ;
	
	var department = id[5] ;
	var departmentName = "все" ;
	var departmentId = "" ;
    if (department!=null
    		&& +department>0) {
    	departmentId = " and ml.id='"+department+"' " ;
    	var list = aCtx.manager.createNativeQuery("select vph.name,vph.id from MisLpu vph where vph.id="+department).setMaxResults(1).getResultList() ;
		if (list.size()>0) {
			var ob = list.get(0) ;
			pigeonHoleName = ob[0] ;
			
		}
    }
    map.put("departmentName",departmentName) ;
    
    var dateI = null ; var period = "" ;
	var timeI = null ; var period1 = "" ;
	var dateInfo = "состоящим" ;
	if (typeDate==1) {
		//aRequest.setAttribute("dateIs"," and m.dateStart between to_date('"+form.getDateBegin()+"','dd.mm.yyyy') and to_date('"+form.getDateBegin()+"','dd.mm.yyyy') ") ;
		dateI = "dateStart" ; timeI = "entranceTime" ;
		dateInfo="поступившим" ;
	} else if (typeDate==2) {
		dateI = "dateFinish" ; timeI = "dischargeTime" ;
		dateInfo="выписанным" ;
	} else {
		dateI= null ; timeI = null ;
		period=" and m.dateFinish is null " ;
		period1=null ;
		dateInfo="состоящим" ;
	}
	
	var date = dateBegin ;
	var dat = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(date) ;
    var cal = java.util.Calendar.getInstance() ;
    cal.setTime(dat) ;
    cal.add(java.util.Calendar.DAY_OF_MONTH, 1) ;
    var format=new java.text.SimpleDateFormat("dd.MM.yyyy") ;
    var date1=format.format(cal.getTime()) ;
    if (dateI!=null) {
    	//aRequest.setAttribute("dateI", dateI) ;
    	if (typeHour!=null && typeHour.equals("1")) {
    		
			period = " and ((m."+dateI+"= to_date('"+date+"','dd.mm.yyyy') and m."+timeI+">= cast('08:00' as time) )"
					+" or (m."+dateI+"= to_date('"+date1+"','dd.mm.yyyy') and m."+timeI+"< cast('08:00' as time) )"
			+")" ;
    		hourInfo=" (8 часов)";
    	} else if (typeHour!=null && typeHour.equals("2")) {
			period = " and ((m."+dateI+"= to_date('"+date+"','dd.mm.yyyy') and m."+timeI+">= cast('09:00' as time) )"
					+" or (m."+dateI+"= to_date('"+date1+"','dd.mm.yyyy') and m."+timeI+"< cast('09:00' as time) )"
			+")" ;
    		hourInfo=" (9 часов)" ;
    	} else {
    		period = " and m."+dateI+"= to_date('"+date+"','dd.mm.yyyy')" ;
    		hourInfo="" ;
    	}
		period1=period ;
    }
	var sqlDep = "select" 
    +" ml.id as mlid,ml.name as mlname"
    +" from MedCase as m  "
    +" left join StatisticStub as sc on sc.medCase_id=m.id" 
    +" left outer join Patient pat on m.patient_id = pat.id" 
    +" left join MisLpu as ml on ml.id=m.department_id "
    +" left join VocServiceStream vss on vss.id=m.serviceStream_id"
    +" where m.DTYPE='HospitalMedCase' "+period+""
    +" and m.deniedHospitalizating_id is null"
    +"  "+emer+pigeonHoleId+departmentId+" group by ml.id,ml.name order by ml.name";
	
	var listDep = aCtx.manager.createNativeQuery(sqlDep).getResultList() ;
	var retDep = new java.util.ArrayList() ;
	var j=1 ;
	
	for (var i=0; i < listDep.size(); i++) {
		var parDep = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		var objDep = listDep.get(i) ;
		var depname = ""+objDep[1] ;
		var depId = +objDep[0];
		parDep.set1(depname) ;
		var sql = "select" 
		    +" m.id as mid"
		    +" ,to_char(m.dateStart,'dd.mm.yyyy')||' '||cast(m.entranceTime as varchar(5)) as mdateStart"
		    +" ,to_char(m.dateFinish,'dd.mm.yyyy')||' '||cast(m.dischargeTime as varchar(5)) as mdateFinish"
		    +" ,cast(m.dischargeTime as varchar(5)) as mdischargeTime"
		    +" ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as fio"
		    +" ,to_char(pat.birthday,'dd.mm.yyyy') as birthday,sc.code as sccode,m.emergency as memergency"
		    +" ,ml.name as mlname,cast(m.entranceTime as varchar(5)) as entranceTime"
		    +" ,pat.id||' ('||coalesce(pat.patientSync,'         ')||')'"
		    +", case when (ok.voc_code is not null and ok.voc_code!='643') then 'ИНОСТ'"  
		    +" when (pvss.omccode='И0' or adr.kladr is not null and adr.kladr not like '30%') then 'ИНОГ' else '' end as typePatient"
		    +" from MedCase as m  "
		    +" left join StatisticStub as sc on sc.medCase_id=m.id" 
		    +" left outer join Patient pat on m.patient_id = pat.id" 
		    +" left join MisLpu as ml on ml.id=m.department_id "
		    +" left join VocServiceStream vss on vss.id=m.serviceStream_id"
		    +" left join Omc_Oksm ok on pat.nationality_id=ok.id"
			+" left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id"
			+" left join address2 adr on adr.addressId = pat.address_addressId"
		    +" where m.DTYPE='HospitalMedCase' "+period+""
		    +" and m.deniedHospitalizating_id is null"
		    +" and m.department_id='"+depId+"'"
		    +"  "+emer+pigeonHoleId+departmentId+" order by ml.name,pat.lastname,pat.firstname,pat.middlename";
			
		var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
		var ret = new java.util.ArrayList() ;
		for (var ii=0; ii < list.size(); ii++) {
			var obj = list.get(ii) ;
		
			var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			par.set1(""+(j++)) ;
			par.set2(obj[6]) ;
			par.set3(obj[4]) ;
			par.set4(obj[10]) ;
			par.set5(obj[2]) ;
			par.set6(obj[11]) ;
			ret.add(par) ;
		}
		parDep.set2(ret) ;
		retDep.add(parDep) ;
	} 
	map.put("listDep",retDep) ;
	if (dateI!=null) {
		var sql = "select" 
	    +" m.id as mid"
	    +" ,to_char(m.dateStart,'dd.mm.yyyy')||' '||cast(m.entranceTime as varchar(5)) as mdateStart"
	    +" ,to_char(m.dateFinish,'dd.mm.yyyy')||' '||cast(m.dischargeTime as varchar(5)) as mdateFinish"
	    +" ,cast(m.dischargeTime as varchar(5)) as mdischargeTime"
	    +" ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as fio"
	    +" ,to_char(pat.birthday,'dd.mm.yyyy') as birthday,sc.code as sccode,m.emergency as memergency"
	    +" ,ml.name as mlname,cast(m.entranceTime as varchar(5)) as entranceTime"
	    +" ,pat.id||' ('||coalesce(pat.patientSync,'         ')||')'"
	    +" ,vdh.name "
	    +", case when (ok.voc_code is not null and ok.voc_code!='643') then 'ИНОСТ'"  
	    +" when (pvss.omccode='И0' or adr.kladr is not null and adr.kladr not like '30%') then 'ИНОГ' else '' end as typePatient"
	    +" from MedCase as m  "
	    +" left join StatisticStub as sc on sc.medCase_id=m.id" 
	    +" left outer join Patient pat on m.patient_id = pat.id" 
	    +" left join MisLpu as ml on ml.id=m.department_id "
	    +" left join VocServiceStream vss on vss.id=m.serviceStream_id"
	    +" left join VocDeniedHospitalizating as vdh on vdh.id=m.deniedHospitalizating_id"
	    +" left join Omc_Oksm ok on pat.nationality_id=ok.id"
		+" left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id"
		+" left join address2 adr on adr.addressId = pat.address_addressId"
		+" left join SecUser su on su.login=m.username"
		+" left join WorkFunction wf on wf.secUser_id=su.id"
		+" left join Worker w on w.id=wf.worker_id"
		+" left join MisLpu ml1 on ml1.id=w.lpu_id "
	    +" where m.DTYPE='HospitalMedCase' "+period+""
	    +" and m.deniedHospitalizating_id is not null"
	    +"  "+emer+pigeonHoleId1+departmentId+" order by m."+dateI+",ml.name,pat.lastname,pat.firstname,pat.middlename";
		
		var deniedlist = new java.util.ArrayList() ;
		var j=1 ;
		
		if (period1!=null) {
			var list1 = aCtx.manager.createNativeQuery(sql).getResultList() ;
		
			for (var i=0; i < list1.size(); i++) {
				var obj = list1.get(i) ;
				var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
				par.set1(""+(j++)) ;
				par.set2(obj[10]) ;
				par.set3(obj[4]) ;
				par.set4(obj[11]) ;
				par.set5(obj[2]) ;
				par.set6(obj[12]) ;
				deniedlist.add(par) ;
			}
		//throw ""+deniedlist.size() ;
		}
		map.put("listDenied",deniedlist) ;
	} else {
		map.put("listDenied",new java.util.ArrayList()) ;
	}
	return map ;
}
function printAddressSheetByHospital(aCtx, aParams) {
	var id = aParams.get("id") ;
	var medcase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase
			, new java.lang.Long(id)) ;
	var dep = medcase.lpu!=null?medcase.lpu.id:java.lang.Long(0) ;
	var depAddress = "" ;
	var depName = "" ;
	if (dep!=null && +dep>0) {
		var list = aCtx.manager.createNativeQuery("select l.name,a.fullname,l.houseNumber , l.houseBuilding from mislpu l left join address2 a on a.addressid=l.address_addressid where l.id="+dep).getResultList() ;
		if (list.size()>0) {
			var ob = list.get(0) ;
			depName = ob[0] ;
			if (ob[1]!=null &&ob[1]!="") {
				depAddress = ob[1] ;
				if (ob[2]!=null&&ob[2]!="") depAddress = depAddress+" д."+ob[2] ;
				if (ob[3]!=null&&ob[3]!="") depAddress = depAddress+" корп."+ob[3] ;
			}
		}
	}
	map.put("depAddress",depAddress) ;
	map.put("depName",depName) ;
	map.put("spec","") ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	var sn=0 ;
	var ret = new java.util.ArrayList() ;
	var kinsman = medcase.kinsman!=null?medcase.kinsman.kinsman:null ;
	var pat = medcase.patient ;
	var compilationDate = medcase.dateStart!=null?FORMAT_2.format(medcase.dateStart):"" ;
	var dischargeDate = medcase.dateFinish!=null?FORMAT_2.format(medcase.dateFinish):"" ;
	if (pat!=null) {
		sn++ ;
		var obj = saveInfoByPatient(pat,compilationDate,dischargeDate,sn,FORMAT_2) ;
		var par = new Packages.ru.ecom.mis.ejb.service.medcase.AddressSheetParentPrintForm()  ;
		par.setDoc1(obj) ;
		ret.add(par) ;
	}
	if (kinsman!=null) {
		sn++ ;
		var obj = saveInfoByPatient(kinsman,compilationDate,dischargeDate,sn,FORMAT_2) ;
		var par = new Packages.ru.ecom.mis.ejb.service.medcase.AddressSheetParentPrintForm()  ;
		par.setDoc1(obj) ;
		ret.add(par) ;
	}
	
	var par = new Packages.ru.ecom.mis.ejb.service.medcase.AddressSheetParentPrintForm()  ;
	par.setDoc1(obj) ;
	
	map.put("list",ret) ;
	return map ;
}

function printAddressSheetArrival(aCtx, aParams) {
	var ids1 = aParams.get("id") ;
	var spec = aParams.get("spec") ;
	var status = +aParams.get("status") ;
	var isPat = true ;
	var isKin = true ;
	if (status==2) isPat=false ; 
	if (status==3) isKin=false ; 
	var dateBegin = aParams.get("dateBegin") ;
	var dateEnd = aParams.get("dateEnd") ;
	map.put("dates",dateBegin) ;
	map.put("datepo",dateEnd) ;
	var specInfo = "" ;
	var dep = aParams.get("department") ;
	if (spec!=null && +spec>0) {
		var list = aCtx.manager.createNativeQuery("select wp.lastname||' '||substring(wp.firstname,0,2)||' '||substring(wp.middlename,0,2),wp.id  from workfunction wf left join worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id where wf.id="+spec)
		.getResultList();
		if (list.size()>0) {		
			specInfo =list.get(0)[0];
		}
		list.clear() ;
	}
	map.put("spec",specInfo) ;
	var depAddress = "" ;
	var depName = "" ;
	if (dep!=null && +dep>0) {
		var list = aCtx.manager.createNativeQuery("select l.name,a.fullname,l.houseNumber , l.houseBuilding from mislpu l left join address2 a on a.addressid=l.address_addressid where l.id="+dep).getResultList() ;
		if (list.size()>0) {
			var ob = list.get(0) ;
			depName = ob[0] ;
			if (ob[1]!=null &&ob[1]!="") {
				depAddress = ob[1] ;
				if (ob[2]!=null&&ob[2]!="") depAddress = depAddress+" д."+ob[2] ;
				if (ob[3]!=null&&ob[3]!="") depAddress = depAddress+" корп."+ob[3] ;
			}
		}
	}
	map.put("depAddress",depAddress) ;
	map.put("depName",depName) ;
	var pigeonHole = aParams.get("pigeonHole") ;
	var pigeonHoleName = "" ;
    if (pigeonHole!=null
    		&& +pigeonHole>0) {
    	var list = aCtx.manager.createNativeQuery("select vph.name from VocPigeonHole vph where vph.id="+dep).getResultList() ;
		if (list.size()>0) {
			var ob = list.get(0) ;
			pigeonHoleName = ob[0] ;
			
		}
    }
    map.put("pigeonHoleName",pigeonHoleName) ;
	var ids = ids1.split(",") ;
	var ret = new java.util.ArrayList() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	var sn=0 ;
	for (var i=0; i < ids.length; i++) {
		var id1=ids[i] ;
		var indlast = id1.lastIndexOf(":") ;
		var id = id1.substring(indlast+1) ;
		
		var medcase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase
		, new java.lang.Long(id)) ;
		if (medcase.deniedHospitalizating == null) {
			var kinsman = medcase.kinsman!=null?medcase.kinsman.kinsman:null ;
			var pat = medcase.patient ;
			var compilationDate = medcase.dateStart!=null?FORMAT_2.format(medcase.dateStart):"" ;
			var dischargeDate = medcase.dateFinish!=null?FORMAT_2.format(medcase.dateFinish):"" ;
			if (isPat && pat!=null) {
				sn++ ;
				ret.add(saveInfoByPatient(pat,compilationDate,dischargeDate,sn,FORMAT_2)) ;
			}
			if (isKin && kinsman!=null) {
				sn++ ;
				var obj = saveInfoByPatient(kinsman,compilationDate,dischargeDate,sn,FORMAT_2) ;
				ret.add(obj) ;
			}
		}
	}
	var ret1 = new java.util.ArrayList() ;
	
	for (var i=0; i < ret.size(); i++) {
		var par = new Packages.ru.ecom.mis.ejb.service.medcase.AddressSheetParentPrintForm()  ;
		par.setDoc1(ret.get(i)) ;
		i++ ;if (i<ret.size()) par.setDoc2(ret.get(i)) ;
		i++ ;if (i<ret.size()) par.setDoc3(ret.get(i)) ;
		i++ ;if (i<ret.size()) par.setDoc4(ret.get(i)) ;
		
		ret1.add(par) ;
		
	}
	
	map.put("list",ret1) ;
	//map.put("list1",ret) ;
	return map ;
	
}
function saveInfoByPatient(aPatient,aComplicationDate,aDischargeDate,aSn,aFormat2) {
	var obj = new Packages.ru.ecom.mis.ejb.service.medcase.AddressSheetPrintForm()  ;
	obj.setCompilationDate(aComplicationDate) ;
	obj.setDischargeDate(aDischargeDate) ;
	obj.setSn(aSn) ;
	obj.setAddress(aPatient.addressRegistration + ((aPatient.rayon!=null && aPatient.code!='ИО')?(" "+aPatient.rayon.name):"" ));
	obj.setLastname(aPatient.lastname) ;
	obj.setFirstname(aPatient.firstname);
	obj.setMiddlename(aPatient.middlename);
	obj.setBirthday(aPatient.birthday!=null?aFormat2.format(aPatient.birthday):"") ;
	obj.setNationality(aPatient.nationality!=null?aPatient.nationality.name:"") ;
	var birthPlace = aPatient.passportBirthPlace!=null?aPatient.passportBirthPlace.name:null ;
	if (birthPlace==null) {
		birthPlace = aPatient.birthPlace!=null?aPatient.birthPlace:"" ;
	}
		
	//obj.setBirthPlace(aPatient.birthPlace!=null?aPatient.birthPlace:"") ;
	obj.setBirthPlace(birthPlace) ;
	obj.setSex(aPatient.sex!=null? aPatient.sex.name:"") ;
	if (aPatient.passportType!=null && (
			aPatient.passportType.omcCode=='14'&&aPatient.passportCodeDivision!=null && aPatient.passportCodeDivision!=""
				|| aPatient.passportType.omcCode!='14'&& aPatient.passportWhomIssued!=""
		)
	) {
		obj.setDocument("вид "+aPatient.passportType.name
				
				+(aPatient.passportSeries!=null?" серия "+aPatient.passportSeries:"")
				
				+(aPatient.passportNumber!=null?" №"+aPatient.passportNumber:"")
				+" выдан "
				+(aPatient.passportWhomIssued!=null?aPatient.passportWhomIssued:
					"")
				
				+" "+(aPatient.passportDateIssued!=null?aFormat2.format(aPatient.passportDateIssued):"")
				
				+(aPatient.passportCodeDivision!=null?" код подразделения, выдавшего документ "+aPatient.passportCodeDivision:""));
	} else {
		obj.setDocument("документов не имеет") ;					
	}
	return obj ;

}

function printMedServicies(aCtx, aParams) {
	var ids1 = aParams.get("id") ;
	var ids = ids1.split(",") ;
	//infoServiceMedCase(aCtx,ids1[0]) ;
	//infoPrint(aCtx,ids1[0]) ;
	var ret = new java.lang.StringBuilder () ;
	//ret.append(ids) ;
	
	//throw ids ;
	var ii = 0 ;
	var ret = new java.util.ArrayList() ;
	map.put("medIds",ids1) ;
	var curDate = new java.util.Date() ;
	var curTime = new java.sql.Time(curDate.getTime()) ;
	for (var i=0; i < ids.length; i++) {
		var id1=ids[i] ;
		var indlast = id1.lastIndexOf(":") ;
		var id = id1.substring(indlast+1) ;
		
		var service = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.ServiceMedCase
		, new java.lang.Long(id)) ;
		
		var mapS= new Packages.ru.ecom.mis.ejb.form.medcase.ServiceMedCaseForm()  ;
		service.setPrintDate(curDate) ;
		service.setPrintTime(curTime) ;
		mapS.setId(service.id) ;
		mapS.setDateExecute(""+service.dateExecute) ;
		ret.add(mapS) ;
	}
	map.put("medServicies",ret) ;
	return map ;
}

function printSurOperation(aCtx,aParams) {
	var surOperation = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.SurgicalOperation
		, new java.lang.Long(aParams.get("id"))) ;
	var medCase = surOperation.medCase ;
	
	map.put("so",surOperation) ;
	map.put("medCase",medCase) ;
	map.put("pat",medCase.patient) ;
	var list=aCtx.manager.createNativeQuery("select list(' '||avwf.name||' '||awp.lastname||' '||awp.firstname||' '||awp.middlename) as anes,list(' '||vam.name||' (кол-во '||a.duration||')') as methodan from Anesthesia a"
		+" left join WorkFunction awf on awf.id=a.anesthesist_id"
		+" left join Worker aw on aw.id=awf.worker_id"
		+" left join Patient awp on awp.id=aw.person_id"
		+" left join VocWorkFunction avwf on avwf.id=awf.workFunction_id"
		+" left join VocAnesthesiaMethod vam on vam.id=a.method_id"
		+" where a.surgicalOperation_id=:id group by a.surgicalOperation_id"
	) .setParameter("id",surOperation.id).setMaxResults(1).getResultList() ;
	var list1=aCtx.manager.createNativeQuery("select "
		+" coalesce(ss1.code,ss.code) as ss1,ss.code as ss2"
		+" from MedCase d"
		+" left join MedCase h on h.id=d.parent_id and h.dtype='HospitalMedCase'"
		+" left join StatisticStub ss on ss.id=h.statisticStub_id"
		+" left join StatisticStub ss1 on ss1.id=d.statisticStub_id"
		+" where d.id=:id"
	).setParameter("id",medCase.id).setMaxResults(1).getResultList() ;
	var anes = list.size()>0?list.get(0):null ;
	map.put("surOper.anesthesist",anes!=null?(anes[0]!=null?anes[0]:""):"") ;
	map.put("surOper.method",anes!=null?(anes[1]!=null?anes[1]:""):"") ;
	var card1="" ;
	if (list1.size()>0) {
		var card = list1.get(0) ;
		
		if (card[0]!=null) {card1=card[0];} else {
			if (card[1]!=null) card1=card[1] ;
			}
	}
	
	map.put("surOper.statisticStub",
			card1
			) ;
	return map ;
}

function printPregHistoryByMC(aCtx, aParams) {
	//var pregHistory = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.birth.PregnancyHistory
	//	, new java.lang.Long(aParams.get("id"))) ;
	var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase
		, new java.lang.Long(aParams.get("id"))) ;
	map.put("confCert",null) ;
	//var medCase = pregHistory.medCase ;
	//map.put("confCert",pregHistory.confinementCertificate) ;
	map.put("pregnancy", medCase.pregnancy) ;
	map.put("pregCard", medCase.pregnancy!=null?medCase.pregnancy.pregnanExchangeCard:null) ;
	var pregInspect = null ;
	for (var i=0 ; i<medCase.inspections.size();i++) {
		var inspection = medCase.inspections.get(i) ;
		if (inspection instanceof Packages.ru.ecom.mis.ejb.domain.birth.PregnanInspection) {
			pregInspect = inspection ;
		}
	}
	map.put("pregInspect",pregInspect) ;
	recordPolicy(medCase.policies);
	recordPatient(medCase,aCtx);
	recordMedCaseDefaultInfo(medCase,aCtx) ;
	return map ;
	
}
function printPregHistory(aCtx, aParams) {
	var pregHistory = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.birth.PregnancyHistory
		, new java.lang.Long(aParams.get("id"))) ;
	//var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase
	//	, new java.lang.Long(aParams.get("id"))) ;
	var medCase = pregHistory.medCase ;
	map.put("confCert",pregHistory.confinementCertificate) ;
	map.put("pregnancy", medCase.pregnancy) ;
	map.put("pregCard", medCase.pregnancy!=null?medCase.pregnancy.pregnanExchangeCard:null) ;
	var pregInspect = null ;
	for (var i=0 ; i<medCase.inspections.size();i++) {
		var inspection = medCase.inspections.get(i) ;
		if (inspection instanceof Packages.ru.ecom.mis.ejb.domain.birth.PregnanInspection) {
			pregInspect = inspection ;
		}
	}
	map.put("pregInspect",pregInspect) ;
	recordPolicy(medCase.policies);
	recordPatient(medCase,aCtx);
	recordMedCaseDefaultInfo(medCase,aCtx) ;
	return map ;
	
}

function printNewBornHistory(aCtx, aParams){
	var newBornHistory = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.birth.NewBorn
		, new java.lang.Long(aParams.get("id"))) ;
	var pregInspect = null ;
	for (var i=0 ; i<medCase.inspections.size();i++) {
		var inspection = medCase.inspections.get(i) ;
		if (inspection instanceof Packages.ru.ecom.mis.ejb.domain.birth.PregnanInspection) {
			pregInspect = inspection ;
		}
	}
	map.put("pregInspect",pregInspect) ;
}

function recordPolicy(policies) {
	var rec="" ;
	for(var i=0; i<policies.size(); i++) {
		var casepol = policies.get(i);
		if (casepol!=null && casepol.policies!=null) {
			if (!rec.equals("")) rec = rec + "; " ;
			rec = rec+casepol.policies.text ;
		}
	}
	if (!rec.equals("")) {
		map.put("policyIs","");
	} else {
		map.put("policyIs","Полиса НЕТ");
	}
	map.put("policyInfo",rec);
}

function recordPatient(medCase,aCtx) {
	var patient = medCase.patient
	//1. Фамилия имя отчество
	map.put("patient",patient) ;
	
	map.put("pat.lastname",patient.lastname) ;
	map.put("pat.firstname",patient.firstname) ;
	map.put("pat.middlename",patient.middlename) ;
	//3. Дата рождения
	map.put("pat.birthday",patient.birthday);
	//2. Пол
	map.put("pat.sex",patient.sex) ;
	map.put("pat.rayon",patient.rayon) ;
	//3. Возраст (полных лет, для детей: до 1 года - месяцев, до 1 месяца - дней)
	getAge("pat.age",patient.birthday,medCase.dateStart,aCtx.manager) ;
	//4. Постоянное место жительства: город, село и адрес
	getAddress("pat.address",patient.address,patient) ;
	map.put("pat.addressReal",patient.addressReal) ;
	map.put("pat.addressReg",patient.addressRegistration) ;
	//Дом, корпус
	map.put("pat.addressHouse",patient.addressHouse) ;
	// Квартира
	map.put("pat.flatNumber",patient.flatNumber) ;
	//5. Место работы, профессия или должность
	map.put("pat.works",patient.works);
	var workDiv = "" ;
	if (medCase.patient.works!=null && medCase.patient.workPost!=null && !medCase.patient.workPost.equals("")) workDiv="," ;
	map.put("pat.workDiv",workDiv) ;
	map.put("pat.wPost",medCase.patient.workPost) ;
	//Документ, удостоверяющий личность
	map.put("pat.identityCard",patient.passportType);
	
	/*
	 final SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");
        StringBuilder sb = new StringBuilder();
        if (thePassportType!=null) sb.append(thePassportType.getName()).append(" ") ;
        addNotEmpty(sb, thePassportSeries) ;
        sb.append(" ") ;
        addNotEmpty(sb, thePassportNumber) ;
        if(thePassportDateIssue!=null) {
            sb.append(", выдан ") ;
        }
        addNotEmpty(sb, thePassportDateIssue!=null ? FORMAT.format(thePassportDateIssue) : "") ;
        sb.append(" ") ;
        addNotEmpty(sb, thePassportWhomIssued) 
	 */
	
	var cardInfo = patient.passportType!=null?patient.passportType.name:"" ;
	cardInfo=cardInfo+" "+(patient.passportSeries!=null?patient.passportSeries:"") ;
	cardInfo=cardInfo+" "+(patient.passportNumber!=null&&patient.passportNumber!=""?"№"+patient.passportNumber:"") ;
	if (patient.passportDateIssued!=null) {
		var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
		cardInfo=cardInfo+" выдан "+FORMAT_2.format(patient.passportDateIssued) ;
		
	}
	cardInfo=cardInfo+" "+(patient.passportWhomIssued!=null?""+patient.passportWhomIssued:"") ;
	map.put("pat.identityCardInfo",cardInfo);
	//Социальный статус
	map.put("pat.socialStatus",patient.socialStatus) ;
	
	//Образование
	map.put("pat.educationType",patient.educationType) ;
	//Национальность
	map.put("pat.ethnicity",patient.ethnicity) ;
	//12 СНИЛС
	if (patient.snils==("")) {
		map.put("pat.snils","______-______-______ ___") ;
	} else {
		map.put("pat.snils",patient.snils);
	}
	//13 Доставлен
	map.put("sls.orderType",medCase.orderType) ;
	map.put("sls.attendant",medCase.attendant) ;
	//Номер направления
	map.put("sls.orderNumber",medCase.orderNumber) ;
	//дата напраления
	map.put("sls.orderDate",medCase.orderDate) ;
	map.put("sls.hospType",medCase.hospType) ;
	//Номер наряда
	map.put("sls.supplyOrderNumber",medCase.supplyOrderNumber) ;
	
	if(medCase.temperatureCurves.size()>0) {
		var temper = medCase.temperatureCurves.get(0) ;
		var temperInfo = "";
		if (temper.weight>0) {
			temperInfo = temperInfo+"вес "+temper.weight ;
		} else {
			temperInfo = temperInfo+"вес ____" ;
		}
		if (temper.degree>0) {
			temperInfo = temperInfo+" темп. "+temper.degree ;
		} else {
			temperInfo = temperInfo+" темп. ____" ;
		}
		map.put("sls.temperature",temperInfo) ;
	} else {
		map.put("sls.temperature","") ;
	}
	//опьянение
	if(medCase.intoxication!=null){
		map.put("intoxication",medCase.intoxication.name) ;
	/*
	if(medCase.intoxication.code.equals("1"))	{map.put("intoxication","<text:span text:style-name=\"T23\">Алкогольного-1</text:span> Наркотического-2")  ;}
	else {
	if(medCase.intoxication.code.equals("2")) map.put("intoxication","Алкогольного-1 <text:span text:style-name=\"T23\">Наркотического-2</text:span>")  ;
	else map.put("intoxication","Алкогольного-1 Наркотического-2")  ;
	
	}
	*/
	}else map.put("intoxication","Алкогольного-1 Наркотического-2")  ;
	//госпиитализирован впервые повтороно
	if(medCase.hospitalization!=null){
	if(medCase.hospitalization.code=="1") {map.put("hosp","впервые") ;}
	else {map.put("hosp","повторно") ;}
	}
	else map.put("hosp","впервые повторно")  ;
	
	
	if (medCase.pediculosis!=null) {
		map.put("pediculInfo",medCase.pediculosis.name) ;
	} else {
		map.put("pediculInfo","") ;
	}
	
	//Вид оплаты
	//recordServiseStream("stream",medCase) ;
	
	//Вид травмы
	//recordTrauma("Trauma",medCase) ;

}

function recordAttendant(medCase,aCtx) {
	if (medCase.hotelServices!=null && medCase.hotelServices) {
		var kinsman = medCase.kinsman!=null?medCase.kinsman.kinsman:null ;
		if (kinsman!=null) {
			getAge("kinsman.age",kinsman.birthday,medCase.dateStart,aCtx.manager) ;
			//map.put("kinsman.age",) ;
			map.put("kinsman.sex",kinsman.sex!=null?kinsman.sex.name:"муж-1, жен-2") ;
		} else {
			map.put("kinsman.age","_________") ;
			map.put("kinsman.sex","муж-1, жен-2")
		}
	} else {
		map.put("kinsman.age","_________") ;
		map.put("kinsman.sex","муж-1, жен-2")
	}
	
}

function recordMedCaseDefaultInfo(medCase,aCtx) {
	map.put("medCase",medCase) ;
	map.put("sls.lpu", medCase.lpu) ;
	map.put("lpu.name",medCase.mainLpuInfo) ;
	map.put("sls.statCardNumber", medCase.statCardNumber) ;
	map.put("sls.admissionDate", medCase.dateStart) ;
	map.put("sls.admissionTime", medCase.entranceTime) ;
	//дата выписки
	map.put("sls.dischargeDate", medCase.dateFinish) ;
	//время выписки
	map.put("sls.dischargeTime", medCase.dischargeTime) ;
	//6. Кем направлен больной
	map.put("sls.whosRefer",medCase.orderLpu) ;
	//8. Диагноз направившего учреждения
	getDiagnos("sls.diagnosisOrder",medCase.diagnosOrder) ;
	//9. Диагноз при поступлении
	getDiagnos("sls.diagnosisAdmission",medCase.diagnosEntrance) ;
	//10. Диагноз клинический
	getDiagnos("sls.diagnosisClinical",medCase.diagnosClinical) ;
	//11б осложнение основного
	getDiagnos("sls.diagnosisComplication",medCase.diagnosComplication);
	//11в сопутствующий
	getDiagnos("sls.diagnosisConcominant",medCase.diagnosConcominant);

	map.put("sls.hospitalization",medCase.hospitalization) ;
	map.put("sls.kinsInfo",medCase.kinsman!=null?medCase.kinsman.info:"") ;
	var otds =aCtx.manager.createNativeQuery("select d.name as depname,to_char(dmc.dateStart,'DD.MM.YYYY') as dateStart,COALESCE(to_char(dmc.dateFinish,'DD.MM.YYYY'),to_char(dmc.transferDate,'DD.MM.YYYY'),'____.____.______г.') as dateFinish"
			+", case when dateFinish is not null then vwf.name||' '||p.lastname||' '|| p.firstname ||' '||p.middlename else '' end as worker"
			+", case when dateFinish is not null then d.name else '' end as dname,d.id as did"
			+", case when dateFinish is not null then wf.code else '' end as worker"
			+" from MedCase dmc "
			+" left join MisLpu d on d.id=dmc.department_id "
			+" left join WorkFunction wf on wf.id=dmc.ownerFunction_id "
			+" left join VocWorkFunction vwf on wf.workFunction_id=vwf.id "
			+" left join Worker w on w.id=wf.worker_id "
			+" left join Patient p on p.id=w.person_id "
			+" where dmc.parent_id='"+medCase.id+"' and dmc.DTYPE='DepartmentMedCase' order by dmc.dateStart,dmc.entranceTime ").getResultList();
		var otd = "";
		var lech = "" ;
		var lechCode = "" ;
		var lastotd = "";
		var lastotdId="" ;
		for (var i=0; i<otds.size(); i++) {
			var dep = otds.get([i]) ;
			if (otd!="") {otd += ", ";}
			otd += dep[0] +" "+" c "+dep[1]+" по "+dep[2] ;
			lech += dep[3] ;
			
			lastotd += dep[4] ;
			lastotdId = dep[5] ;
			lechCode=dep[6] ;
		}
		map.put("sls.departments",otd) ;
		map.put("sls.owner",lech) ;
		map.put("sls.ownercode",lechCode) ;
		map.put("sls.lastOtd",lastotd) ;
		recordZavOtd(aCtx,lastotdId,"dep.zav") ;
		slsId = medCase.id ;
		recordDiagnosis(aCtx,slsId,"1","2","diagnosis.order.main") ;
		recordDiagnosis(aCtx,slsId,"1","1","diagnosis.admission.main") ;
		recordDiagnosis(aCtx,slsId,"4","1","diagnosis.clinic.main") ;
		recordDiagnosis(aCtx,slsId,"4","3","diagnosis.clinic.related") ;
		recordDiagnosis(aCtx,slsId,"4","4","diagnosis.clinic.complication") ;
		recordDiagnosis(aCtx,slsId,"5","1","diagnosis.postmortem.main") ;
		recordDiagnosis(aCtx,slsId,"5","3","diagnosis.postmortem.related") ;
		recordDiagnosis(aCtx,slsId,"5","4","diagnosis.postmortem.complication") ;
		recordDisability(aCtx,slsId,"dis") ;

}
function recordZavOtd(aCtx,aLastOtdId,aField) {
	if (+aLastOtdId>0) {
		var sql = "select p.lastname||' '||p.firstname||' '||p.middlename,wf.id,p.lastname " 
			+" from workfunction wf " 
			+" left join worker w on w.id=wf.worker_id"
			+" left join patient p on p.id=w.person_id"
			+" where w.lpu_id='"+aLastOtdId+"' and wf.isAdministrator='1'" ;
		var list = aCtx.manager.createNativeQuery(sql).setMaxResults(1).getResultList() ;
		if (list.size()>0) {
			var obj=list.get(0) ;
			map.put(aField+".info",obj[0]);
			map.put(aField+".lastname",obj[2]);
		} else {
			map.put(aField+".info",null);
			map.put(aField+".lastname",null);
		}
	} else {
		map.put(aField+".info",null);
		map.put(aField+".lastname",null);
	}
}
function printConsentBySlo(aCtx,aParams) {
	var sloId=new java.lang.Long(aParams.get("id")) ;
	
	var parDep = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	var ret = new java.util.ArrayList() ;
	ret.add(parDep) ;
	var ret1 = new java.util.ArrayList() ;
	var params=[["consent",4],["direct",1],["rejection",2]] ;
	//throw +aParams.get("consent4") ;
	for (var i0=0;i0<params.length;i0++) {
		var par=params[i0] ;
		for (var i=1;i<=par[1];i++) {
			var param = +aParams.get(par[0]+i) ;
			if (param>0) {map.put(par[0]+i,ret)} else {map.put(par[0]+i,ret1)}
		}
	}
	var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase
			, sloId) ;
	var pat = medCase.patient ;
	map.put("pat",pat) ;
	getAge("patInfo.age",pat.birthday,medCase.parent.dateStart,aCtx.manager,0) ;
	map.put("medCase",medCase) ;
	map.put("dep.name",medCase.department!=null?medCase.department.name:"") ;
	var currentDate = new java.util.Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("current.date",FORMAT_2.format(currentDate)) ;
	map.put("sls.dateStart",medCase.parent.dateStart) ;
	//map.put("pat.age",) ;
	var username = aCtx.sessionContext.callerPrincipal.name ;
	var list = aCtx.manager.createNativeQuery("select p.lastname, p.firstname,p.middlename " 
			+ " from SecUser su " 
			+ " left join WorkFunction wf on wf.secuser_id=su.id " 
			+ " left join Worker w on w.id=wf.worker_id "
			+ " left join Patient p on p.id=w.person_id "
			+ " where login = :login and p.id is not null") 
		.setParameter("login", username).setMaxResults(1).getResultList() ;
	if (list.size()>0) {
		var obj=list.get(0) ;
		map.put("current.worker.lastname",obj[0]) ;
		map.put("current.worker.firstname",obj[1]) ;
		map.put("current.worker.middlename",obj[2]) ;
	} else {
		map.put("current.worker.lastname","") ;
		map.put("current.worker.firstname","") ;
		map.put("current.worker.middlename","") ;
	}
	var worker = medCase.ownerFunction!=null?medCase.ownerFunction.worker:null ;
	var wpat = worker!=null?worker.person:null ;
	map.put("slo.owner.lastname",wpat!=null?wpat.lastname:"") ;
	map.put("slo.owner.firstname",wpat!=null?wpat.firstname:"") ;
	map.put("slo.owner.middlename",wpat!=null?wpat.middlename:"") ;
	
	recordZavOtd(aCtx,medCase.department!=null?medCase.department.id:null,"administrator.dep") ;
	var list1=aCtx.manager.createNativeQuery("select "
			+" coalesce(ss1.code,ss.code),ss.code"
			+" from MedCase d"
			+" left join MedCase h on h.id=d.parent_id and h.dtype='HospitalMedCase'"
			+" left join StatisticStub ss on ss.id=h.statisticStub_id"
			+" left join StatisticStub ss1 on ss1.id=d.statisticStub_id"
			+" where d.id=:id"
		).setParameter("id",sloId).setMaxResults(1).getResultList() ;
	var card1="" ;
	if (list1.size()>0) {
		var card = list1.get(0) ;
		if (card[0]!=null) {card1=card[0];} else {if (card[1]!=null) card1=card[1] ;}
	}
	map.put("statcard",	card1) ;
	recordDiagnosis(aCtx,sloId,"4","1","diagnosis.main","DepartmentMedCase") ;
	recordDiagnosis(aCtx,sloId,"4","3","diagnosis.related","DepartmentMedCase") ;
	recordDiagnosis(aCtx,sloId,"4","4","diagnosis.complication","DepartmentMedCase") ;
	
	return map ;
}
function printStatCardInfo(aCtx, aParams) {
	var slsId=aParams.get("id") ;
	var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase
		, new java.lang.Long(slsId)) ;
	
	recordPolicy(medCase.policies) ;
	recordPatient(medCase,aCtx) ;
	recordMedCaseDefaultInfo(medCase,aCtx) ;
	recordAttendant(medCase,aCtx) ;
	
	
	var depDirect = "" ;
	var hospType = medCase.hospType ;
	if (hospType!=null) {
		if (hospType.code=="DAYTIMEHOSP") {
			depDirect = "(дневное)" ;
		}
	}
	
	map.put("sls.departmentDirection",medCase.department) ;
	map.put("sls.typeDirect",depDirect) ;
	map.put("sls.outcome",medCase.outcome) ;
	map.put("sls.result",medCase.result) ;
	//if (medCase.diagnosClinical.establishDate.equals("")){
	//map.put("diag.establishDate", "1") ;}
	//else {
	//map.put("diag.establishDate",medCase.diagnosClinical.establishDate);
	//}
	map.put("sls.daysCount", medCase.daysCount) ;
	//recordVocProba("sls.hosp",medCase.hospitalization,1,2);
	//7. Доставлен по экстренным показания
	toBeOrNotToBe("sls.dostavlen",medCase.emergency) ;
	// через_________часов после начала заболевания, получения травмы; госпитализирован в плановом порядке
	map.put("sls.preAdmissionTime",medCase.preAdmissionTime) ;
	//10. Диагноз клинический
	getDiagnos("sls.diagnosisClinical",medCase.diagnosClinical) ;
	//11. Диагноз заключительный клинический
	//11а основной
	//?getDiagnos("sls.diagnosisConcluding",medCase.diagnosConcluding);
	//11б осложнение основного
	//?getDiagnos("sls.diagnosisComplication",medCase.diagnosComplication);
	//11в сопутствующий
	//?getDiagnos("sls.diagnosisConcominant",medCase.diagnosConcominant);
	/*
	recordDiagnosis(aCtx,slsId,"1","2","diagnosis.order.main") ;
	recordDiagnosis(aCtx,slsId,"1","1","diagnosis.admission.main") ;
	recordDiagnosis(aCtx,slsId,"4","1","diagnosis.clinic.main") ;
	recordDiagnosis(aCtx,slsId,"4","3","diagnosis.clinic.related") ;
	recordDiagnosis(aCtx,slsId,"4","4","diagnosis.clinic.complication") ;
	recordDiagnosis(aCtx,slsId,"5","1","diagnosis.postmortem.main") ;
	recordDiagnosis(aCtx,slsId,"5","3","diagnosis.postmortem.related") ;
	recordDiagnosis(aCtx,slsId,"5","4","diagnosis.postmortem.complication") ;
	recordDisability(aCtx,slsId,"dis") ;
	*/
	recordSloBySls(aCtx,slsId,"listSlo") ;
	recordSurgicalOperationBySls(aCtx,slsId,"listOper") ;
	
	return map ;
}
function recordDisability(aContext,aSlsId,aField) {
	var sql="select dc.id,dd.id,dd.number,to_char(min(dr.dateFrom),'dd.mm.yyyy') as dateFrom,to_char(max(dr.dateTo),'dd.mm.yyyy') as dateTo,vddcr.name as vddcrname from DisabilityCase dc"
		+" 	left join Patient pat on pat.id=dc.patient_id"
		+" 	left join MedCase sls on sls.patient_id=pat.id"
		+" 	left join DisabilityDocument dd on dd.disabilityCase_id=dc.id"
		+" 	left join DisabilityRecord dr on dr.disabilityDocument_id=dd.id"
		+" 	left join VocDisabilityDocumentCloseReason vddcr on dd.closeReason_id=vddcr.id"
		+" 	where sls.id='"+aSlsId+"' and dd.anotherlpu_id is null"
		+" 	group by dc.id,dd.id,sls.dateStart,sls.dateFinish,dd.number,vddcr.name,dd.issueDate"
		+" 	having min(dr.dateFrom) between sls.dateStart and coalesce(sls.dateFinish,current_date)"
		+"  order by dd.issueDate"
	var list = aContext.manager.createNativeQuery(sql).getResultList() ;
	if (list.size()>0) {
		var obj=list.get(0) ;
		map.put(aField+".info","№"+obj[2]+" открыт с "+obj[3]+" по "+obj[4]+". Причина закрытия: "+obj[5]);
		map.put(aField+".age",null);
		map.put(aField+".sex",null);
	} else {
		map.put(aField+".info",null);
		map.put(aField+".age",null);
		map.put(aField+".sex",null);
	}
}

function recordDiagnosis(aCtx,aSlsId,aRegistrationType,aPriority,aField,aDtype) {
	if (aDtype==null || aDtype=='') aDtype='HospitalMedCase' ;
	var sql="select sls.id,list(case when vdrt.code='"+aRegistrationType+"' and vpd.code= '"+aPriority+"'  then mkb.code else null end) as diagCode"
		+" ,list(case when vdrt.code='"+aRegistrationType+"' and vpd.code='"+aPriority+"' then diag.name else null end) as diagText" 
		+" from MedCase sls" 
		+" left join Diagnosis diag on diag.medCase_id=sls.id"
		+" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id"
		+" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
		+" left join VocIdc10 mkb on mkb.id=diag.idc10_id"
		+" where sls.id='"+aSlsId+"' and sls.dtype='"+aDtype+"'"
		+" group by sls.id" ;
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	if (list.size()>0) {
		var obj = list.get(0) ;
		map.put(aField+".mkb",obj[1]!=null?obj[1]:"") ;
		map.put(aField+".text",obj[2]!=null?obj[2]:"") ;
	} else {
		map.put(aField+".mkb","") ;
		map.put(aField+".text","") ;
	}
}
function recordSloBySls(aCtx,aSlsId,aField) {
	var sql = "select slo.id as sloid0,dep.id as depid1,dep.name as depname2,vbt.name as vbtname3"
		+" ,coalesce(owf.code"
		+" ,ovwf.code||' '||owp.lastname||' '||substring(owp.firstname,1,1)||substring(owp.middlename,1,1)"
		+" ,owp.lastname||' '||substring(owp.firstname,1,1)||substring(owp.middlename,1,1)) as owner4"
		+" ,to_char(slo.dateStart,'dd.mm.yyyy') as startdate5,cast(slo.entranceTime as varchar(5)) as entrancetime6"
		+" ,coalesce(to_char(slo.dateFinish,'dd.mm.yyyy'),to_char(slo.transferDate,'dd.mm.yyyy')) as dischargedate7"
		+" ,coalesce(cast(slo.dischargeTime as varchar(5)),cast(slo.transferTime as varchar(5))) as dischargetime8"
		+" ,os.model as osmodel9"
		+" ,list(case when vdrt.code='4' and vpd.code='1'  then mkb.code else null end) as diagMain10"
		+" ,list(case when vdrt.code='4' and vpd.code='3' then mkb.code else null end) as diagSoput11" 
		+" ,list(case when vdrt.code='4' and vpd.code='4' then mkb.code else null end) as diagOsl12"
		+" ,vss.name as vssname13"
		+" from MedCase slo" 
		+" left join MisLpu dep on dep.id=slo.department_id"
		+" left join BedFund bf on bf.id=slo.bedFund_id"
		+" left join VocBedType vbt on vbt.id=bf.bedType_id"
		+" left join WorkFunction owf on owf.id=slo.ownerFunction_id"
		+" left join Worker ow on ow.id=owf.worker_id"
		+" left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id"
		+" left join Patient owp on owp.id=ow.person_id"
		+" left join Omc_Standart os on os.id=slo.omcStandart_id"
		+" left join VocServiceStream vss on vss.id=slo.serviceStream_id"
		+" left join Diagnosis diag on diag.medCase_id=slo.id"
		+" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id"
		+" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
		+" left join VocIdc10 mkb on mkb.id=diag.idc10_id"
		+" where slo.parent_id='"+aSlsId+"' and slo.dtype='DepartmentMedCase'"
		+" group by slo.id,slo.dateStart,slo.entranceTime,slo.dateFinish,slo.transferDate,slo.dischargeTime"
		+" ,slo.transferTime,os.model,vss.name,dep.id,dep.name,vbt.name"
		+" ,owf.code,ovwf.code"
		+" ,ovwf.name,owp.lastname,owp.firstname,owp.middlename"
		+" order by slo.dateStart,slo.entranceTime" ;
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	var ret = new java.util.ArrayList() ;
	
	if (list.size()>0) {
		for (var i=0;i<list.size();i++) {
			var wq = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			var obj=list.get(i) ;
			wq.setSn(i+1) ;
			for (var j=1;j<14;j++) {
				eval("wq.set"+j+"(obj["+(j)+"])") ;
			}
			ret.add(wq) ;
		}
	} else {
		for (var i=0;i<6;i++) {
			var parDep = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			ret.add(parDep) ;
		}
	}
	map.put(aField,ret) ;
}
function recordSurgicalOperationBySls(aCtx,aSlsId,aField) {
	var sql="select "
		+" so.id as soid0,case when so.base='1' then 'осн' else '' end as sobase1,case" 
		+" when so.operationdate=so.operationdateto or so.operationdateto is null" 
		+" 	then to_char(so.operationDate,'dd.mm.yyyy')||' '||coalesce(cast(so.operationTime as varchar(5)),'___:___')||' '||case when so.operationTimeTo is null then '' else '-'||coalesce(cast(so.operationTimeTo as varchar(5)),'___:___') end"
		+" else to_char(so.operationDate,'dd.mm.yyyy')||' '||coalesce(cast(so.operationTime as varchar(5)),'___:___')||'-'||to_char(so.operationDateTo,'dd.mm.yyyy')||' '||case when so.operationTimeTo is null then '' else ' '||coalesce(cast(so.operationTimeTo as varchar(5)),'___:___') end"
		+" end as operdate2"
		+" ,case when owf.code!=null and owf.code!='' then owf.code else case when ovwf.code!=null and ovwf.code!='' then ovwf.code else '' end||' '||owp.lastname||' '||substring(owp.firstname,1,1)||substring(owp.middlename,1,1) end as surgeon3"
		+" ,dep.id as depid4,dep.name as depname5,coalesce(vo.code,ms.code) as vocode6,coalesce(vo.name,ms.name) as voname7"
		+" ,list(vc.name) as vcname8,list(vc.code) as vccode9,list(vam.code) as vacode10"
		+" ,case when so.endoscopyuse='1' then 'X' else '' end as endoscopyuse11"
		+" ,case when so.laseruse='1' then 'X' else '' end as laseruse12"
		+" ,case when so.cryogenicuse='1' then 'X' else '' end as cryogenicuse13"
		+" ,vss.name as vssname14"
		+" from SurgicalOperation so"
		+" left join MedCase mc1 on mc1.id=so.medCase_id"
		+" left join WorkFunction owf on owf.id=so.surgeon_id"
		+" left join Worker ow on ow.id=owf.worker_id"
		+" left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id"
		+" left join Patient owp on owp.id=ow.person_id"
		+" left join MisLpu dep on dep.id=so.department_id"
		+" left join VocOperation vo on vo.id=so.operation_id"
		+" left join MedService ms on ms.id=so.medService_id"
		+" left join SurgicalOperation_VocComplication sovc on so.id = sovc.surgicaloperation_id"
		+" left join VocComplication vc on vc.id=sovc.complications_id"
		+" left join Anesthesia an on so.id = an.surgicaloperation_id"
		+" left join VocAnesthesiaMethod vam on vam.id=an.type_id"
		+" left join VocServiceStream vss on vss.id=so.serviceStream_id"
		+" where (mc1.id='"+aSlsId+"' or mc1.parent_id='"+aSlsId+"')"
		+" group by so.id,so.operationdate,so.operationdateto"
		+" ,so.operationtime,so.operationtimeto"
		+" ,owf.code,ovwf.code,owp.lastname,owp.firstname,owp.middlename,dep.id,dep.name,vo.code,vo.name"
		+" ,so.base,so.endoscopyuse, so.laseruse, so.cryogenicuse,vss.name,ms.code,ms.name" ;
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	var ret = new java.util.ArrayList() ;
	
	if (list.size()>0) {
		for (var i=0;i<list.size();i++) {
			var wq = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			var obj=list.get(i) ;
			wq.setSn(i+1) ;
			for (var j=1;j<15;j++) {
				eval("wq.set"+j+"(obj["+(j)+"])") ;
			}
			ret.add(wq) ;
		}
	} else {
		for (var i=0;i<5;i++) {
			var parDep = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			ret.add(parDep) ;
		}
	}
	map.put(aField,ret) ;

}
function printBilling(aCtx, aParams)
{
	var id = aParams.get("id") ;
	var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase
		, new java.lang.Long(id)) ;

	//Свединия по пациенту	
	recordPatient(medCase,aCtx) ;
	//Диагнозы
	recordMedCaseDefaultInfo(medCase,aCtx) ;
	map.put("sls.emergency", ((medCase.emergency!=null && medCase.emergency) ? "в экстренном порядке":"в плановом порядке")) ;
	//5. Даты: поступления, выбытия
	map.put("sls.Start",medCase.dateStart) ;
	map.put("sls.Finish",medCase.dateFinish) ;
	//выписной диагноз
	getDiagnos("sls.diagnosConcluding",medCase.diagnosConcluding);
	recordMultiText("sls.dischargeRecord",medCase.dischargeEpicrisis);
	//текущая дата
	var currentDate = new Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	
	return map ;
}

function getCode(aKey, aValue)
{
	if(aValue!=null) map.put("aKey",aValue.code) ;
}
function printProtocols(aCtx, aParams) {
	var ids1 = aParams.get("id") ;
	var ids = ids1.split(",") ;
	//infoSmo(aCtx,ids[0]) ;
	infoPrint(aCtx,ids[0]) ;
	var ret = new java.lang.StringBuilder () ;
	//ret.append(ids) ;
	//var list = new 
	//throw ids ;
	
	
	var ret = new java.util.ArrayList() ;
	var FORMAT_1 = new java.text.SimpleDateFormat("yyyy-MM-dd") ;
    var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
    var FORMAT_3 = new java.text.SimpleDateFormat("HH:mm") ;
	//var startDate =  FORMAT_2.parse(obj[0])) ;
	//var finishDate = FORMAT_1.format( FORMAT_2.parse(obj[1])) ;
	var current = new java.util.Date() ;
	var curDate = new java.sql.Date(current.getTime()) ;
	
	var curTime = new java.sql.Time(current.getTime()) ;
	for (var i=0; i < ids.length; i++) {
		var id1=ids[i] ;
		var indlast = id1.lastIndexOf("!") ;
		var id = id1.substring(indlast+1) ;
		//throw indlast+"--"+id1 +"----"+id;
		
		
		var protocol = aCtx.manager.find(Packages.ru.ecom.poly.ejb.domain.protocol.Protocol
		, new java.lang.Long(id)) ;
		protocol.setPrintDate(curDate) ;
		protocol.setPrintTime(curTime) ;
		
		var mapS= new Packages.ru.ecom.mis.ejb.form.medcase.VisitProtocolForm()  ;
		mapS.timeRegistration=FORMAT_3.format(protocol.timeRegistration);
		mapS.dateRegistration=FORMAT_2.format(protocol.dateRegistration);
		mapS.specialistInfo=protocol.specialistInfo ;
		mapS.setTicket(null) ;
		mapS.setTypeInfo("") ;
		var protType=protocol.type ;
		if (protType!=null) {
			mapS.typeInfo=protType.name ;
			//var protType = protType.isPrintAdministrator ;
			mapS.setTicket(protType.isPrintAdministrator==true?java.lang.Long.valueOf(0):null) ;
		}
		mapS.setInfo(protocol.medCase!=null?protocol.medCase.info:"");
		/*var n = '\n'  ;
		var items = protocol.record.split(n) ;
		//mapS.setId(service.id) ;
		//mapS.setDateExecute(""+service.dateExecute) ;
		var rec = new java.lang.StringBuilder() ;
		for (var j = 0; j < items.length; j++) {
			rec.append(items[j]) ;
			if (j < items.length-1) rec.append("<text:line-break/>") ;
			//rec.append("                                                      ");
		}
		*/
		mapS.setRecord(recordMultiValue(protocol.record));
		
		//mapS.typeInfo = protocol.medCase.info
		ret.add(mapS) ;
		
	}
	map.put("protocols",ret) ;
	
	return map ;
}
function printMedServies(aCtx, aParams) {
	var ids1 = aParams.get("id") ;
	var ids = ids1.split(",") ;
	infoSmo(aCtx,ids1[0]) ;
	infoPrint(aCtx,ids1[0]) ;
	var ret = new java.lang.StringBuilder () ;
	//ret.append(ids) ;
	
	//throw ids ;
	for (var i=0; i < ids.length; i++) {
		var id1=ids[i] ;
		var indlast = id1.lastIndexOf(":") ;
		var id = id1.substring(indlast+1) ;
		//ret.append("id=") ;
		//ret.append(id).append(",") ;
		
		var protocol = aCtx.manager.find(Packages.ru.ecom.poly.ejb.domain.protocol.Protocol
		, new java.lang.Long(id)) ;
		ret.append(protocol.dateRegistration).append(" ").append(protocol.timeRegistration).append("          ЗАКЛЮЧЕНИЕ") ;
		ret.append("</text:p><text:p>");
		ret.append(recordMultiValue(protocol.record));
		ret.append("</text:p><text:p>");
		ret.append("</text:p><text:p>").append(protocol.specialistInfo);
		ret.append("</text:p><text:p>");
		
		ret.append("</text:p><text:p> 	Подпись врача ______________________");
		ret.append("</text:p><text:p>");
		ret.append("</text:p><text:p>");
		ret.append("</text:p><text:p>");
		
	}
	
	//map.put("protocols","fdsfdsfsd") ;
	//throw ret.toString() ;
	map.put("protocol.text",ret.toString()) ;

	//map.put("protocols.text","gsdfgsd") ;
	return map ;
}
function infoSmo(aCtx,aParams) {
	map.put("smo.info","") ;
}
function infoPrint(aCtx,aParams) {
	map.put("print.info","") ;
}
function printProtocol (aCtx,aParams){
	var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase
		, new java.lang.Long(aParams.get("id"))) ;
	//var list = aCtx.manager.createQuery("from Protocol where medCase_id=:sls")
		//.setParameter("sls",medCase.id).getResultList();
	//var protocol = !list.isEmpty()?list.iterator().next().record:"";
	var protocol = aCtx.manager.find(Packages.ru.ecom.poly.ejb.domain.protocol.Protocol
		, new java.lang.Long(aParams.get("id"))) ;
	var current = new java.util.Date() ;
	var curDate = new java.sql.Date(current.getTime()) ;
	var curTime = new java.sql.Time(current.getTime()) ;
	protocol.setPrintDate(curDate) ;
	protocol.setPrintTime(curTime) ;	
	map.put("prot.date",protocol.dateRegistration);
	map.put("prot.time",protocol.timeRegistration);
	map.put("protocol",protocol);
	map.put("prot.spec",protocol.specialistInfo);
	map.put("medCase.info",protocol.medCase.info) ;
	recordMultiText("prot.rec", protocol.record) ;
	
	var protType=protocol.type ;
	if (protType!=null) {
		map.put("typeInfo",protType.name) ;
		//var protType = protType.isPrintAdministrator ;
		map.put("ticket",protType.isPrintAdministrator==true?java.lang.Long.valueOf(0):null) ;
	} else {
		map.put("ticket",null) ;
		map.put("typeInfo",null) ;
	}
	
	return map ;
}
	// получить возраст (полных лет, для детей: до 1 года - месяцев, до 1 месяца - дней)
function getAge(aKey,aBirthday,aDate,aManager,aType) {
	if (aType==null) aType=3 ;
	if (aDate!=null && aBirthday!=null) {
		map.put(aKey,Packages.ru.nuzmsh.util.date.AgeUtil.getAgeCache(aDate,aBirthday,aType)) ;	
	} else {
		map.put(aKey,"");
	}
}
function toBeOrNotToBe(aKey,aValue) {
	map.put(aKey,(aValue!=null && aValue==true)? "Да": "Нет") ;
}
function getAddress(aKey, aAddress,aPat) {
	if (aAddress!=null) {
		if (aAddress.addressIsCity!=null && aAddress.addressIsCity==true) {
			map.put(aKey+".CityOrVillage","город") ;
		} else {
			if (aAddress.addressIsVillage!=null && aAddress.addressIsVillage==true) {
				map.put(aKey+".CityOrVillage","село") ;
			} else {
				map.put(aKey+".CityOrVillage","город, село (нужное подчеркнуть)") ;
			}
		}
		//map.put(aKey+".info",aAddress.getAddressInfo(aPat.houseNumber, aPat.houseBuilding, aPat.flatNumber)) ;
	}else{
		map.put(aKey+".CityOrVillage","город, село (нужное подчеркнуть)") ;
	}
	map.put(aKey+".info",aPat.getAddressRegistration()) ;
	map.put(aKey+".real",aPat.getAddressReal()) ;
}
function getDiagnos(aKey,aDiag) {
	if (aDiag!=null) {
		map.put(aKey+".text",aDiag.name) ;
		map.put(aKey+".mkb","") ;
		if (aDiag.idc10!=null) map.put(aKey+".mkb","("+aDiag.idc10.code+")");
	} else {
		map.put(aKey+".text","") ;
		map.put(aKey+".mkb","");
	}
}

function recordVocProba(aKey, aValue, aMin, aMax) {
	for (i=aMin;i<=aMax;i++) {
		map.put(aKey+i+".k1","");
		map.put(aKey+i+".k2","");
	}
	if (aValue!=null) {
		var ind= aValue.id ;
		map.put(aKey+ind+".k1","<text:span text:style-name=\"T14\">");
		map.put(aKey+ind+".k2","</text:span>");
	}
} 

function recordMultiText(aKey, aValue) {
	var ret = new java.lang.StringBuilder () ;
	var val = aValue!=null?"" +aValue:"" ;
	var n = /\n/ ;
	var items = val.split(n);
	var list = new java.util.ArrayList() ;
	for (var i = 0; i < items.length; i++) {
		var prot = Packages.ru.ecom.poly.ejb.form.protocol.ProtocolForm() ;
		prot.setRecord(items[i]);
		list.add(prot);
	}
	map.put(aKey,list) ;
}
function recordMultiValue(aValue) {
	var ret = new java.lang.StringBuilder () ;
	var val = aValue!=null?"" +aValue:"" ;
	var n = /\n/ ;
	var items = val.split(n);
	for (var i = 0; i < items.length; i++) {
		ret.append(items[i]);
		ret.append("<text:line-break/>") ;
	}
	return ret.toString() ;
}
function recordBoolean(aKey) {
		map.put(aKey+".k1","<text:span text:style-name=\"T23\">") ;
		map.put(aKey+".k2","</text:span>");
}

