var map = new java.util.HashMap() ;

function printCriminalMessage(aCtx,aParams) {
	var dateBegin = aParams.get("dateBegin");
	var dateEnd=aParams.get("dateEnd") ;
   
    if (dateBegin==null && dateBegin.equals("")) throw "Неопределен период" ;
    if (dateEnd==null ||dateEnd.equals("")) dateEnd=dateBegin;
	var view = aParams.get("typeView1") ;
	var pigeonHole1Sql="", pigeonHoleSql="" ;
	var pHole = +aParams.get("pigeonHole") ;
    if (+pHole>0) {
    	pigeonHole1Sql= " and (ml.pigeonHole_id='"+pHole+"' or ml1.pigeonHole_id='"+pHole+"')" ;
    	pigeonHoleSql= " and ml.pigeonHole_id='"+pHole+"'" ;
    }
   	var phoneMessageTypeSql="",phoneMessageType = +aParams.get("phoneMessageType") ;
    if (phoneMessageType>0) {
    	phoneMessageTypeSql=" and pm.phoneMessageType_id='"+phoneMessageType+"'" ;
    }
    var phoneMessageSubTypeSql="", phoneMessageSubType = +aParams.get("phoneMessageSubType") ;
    if (phoneMessageSubType>0) {
    	phoneMessageSubTypeSql=" and pm.phoneMessageSubType_id='"+phoneMessageSubType+"'" ;
    }
    var departmentSql="", dep=+aParams.get("department") ;
	if (dep>0) {
		departmentSql= " and ml.id='"+dep+"'" ;
	}
	var typeDate1 = +aParams.get("typeDate1") ;
	var paramDate="",paramDateInfo="" ;
	if (typeDate1==1) {
		paramDate="m.dateStart" ;
		paramDateInfo="Дата поступления" ;
	} else if (typeDate1==3) {
		paramDate="pm.whenDateEventOccurred" ;
		paramDateInfo= "Дата, когда произошло событие" ;
	} else {
		paramDate="pm.phoneDate" ;
		paramDateInfo="Дата регистрации сообщения" ;
	}
	var sql="select pm.id, to_char(pm.phoneDate,'dd.mm.yyyy') as pmphoneDate "
	    +" ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'dd.mm.yyyy') as fiopat"
		+", case when p.address_addressId is not null "
		+"          then coalesce(a.fullname,a.name) || "
		+"               case when p.houseNumber is not null and p.houseNumber!='' then ' д.'||p.houseNumber else '' end" 
		+		" ||case when p.houseBuilding is not null and p.houseBuilding!='' then ' корп.'|| p.houseBuilding else '' end" 
		+		"||case when p.flatNumber is not null and p.flatNumber!='' then ' кв. '|| p.flatNumber else '' end"
		+"       when p.territoryRegistrationNonresident_id is not null"
		+"          then okt.name||' '||p.RegionRegistrationNonresident||' '||oq.name||' '||p.SettlementNonresident"
		+"               ||' '||ost.name||' '||p.StreetNonresident||"
		//+"               coalesce(' д.'||p.HouseNonresident,'') ||coalesce(' корп.'|| p.BuildingHousesNonresident,'') ||coalesce(' кв. '|| p.ApartmentNonresident,'')"
		+"               case when p.HouseNonresident is not null and p.HouseNonresident!='' then ' д.'||p.HouseNonresident else '' end" 
		+" ||case when p.BuildingHousesNonresident is not null and p.BuildingHousesNonresident!='' then ' корп.'|| p.BuildingHousesNonresident else '' end" 
		+"||case when p.ApartmentNonresident is not null and p.ApartmentNonresident!='' then ' кв. '|| p.ApartmentNonresident else '' end"
		+"   else '' "
		+"  end as address"
	    +" ,coalesce(vpme.name,pm.recieverFio) as reciever"
	    +" ,to_char(pm.whenDateEventOccurred,'dd.mm.yyyy')||' '||cast(pm.whenTimeEventOccurred as varchar(5)) as whenevent"
	    +" ,pm.place as pmplace,vpmo.name as vphoname"
    +" ,vpht.name||coalesce(' '||vpmst.name,'') as typeMessage"
    +" ,wp.lastname as wplastname"
    +" ,coalesce(vpmorg.name,pm.phone,pm.recieverOrganization) as organization"
    +" ,pm.diagnosis as pmdiagnosis"
    +" from PhoneMessage pm" 
    +" left join VocPhoneMessageType vpht on vpht.id=pm.phoneMessageType_id"
    +" left join VocPhoneMessageSubType vpmst on vpmst.id=pm.phoneMessageSubType_id"
    +" left join VocPhoneMessageOrganization vpmorg on vpmorg.id=pm.organization_id"
    +" left join VocPhoneMessageEmploye vpme on vpme.id=pm.recieverEmploye_id"
    +" left join VocPhoneMessageOutcome vpmo on vpmo.id=pm.outcome_id"
    +" left join WorkFunction wf on wf.id=pm.workFunction_id"
    +" left join Worker w on w.id=wf.worker_id"
    +" left join Patient wp on wp.id=w.person_id"
    +" left join medcase m on m.id=pm.medCase_id"
    +" left join Patient p on p.id=m.patient_id"
    +" left join MisLpu as ml on ml.id=m.department_id" 
    +" left join SecUser su on su.login=m.username"
    +" left join WorkFunction wf1 on wf1.secUser_id=su.id"
    +" left join Worker w1 on w1.id=wf1.worker_id"
    +" left join MisLpu ml1 on ml1.id=w1.lpu_id"  
	+" left join Address2 a on a.addressId=p.address_addressId"
	+" left join Omc_KodTer okt on okt.id=p.territoryRegistrationNonresident_id"
	+" left join Omc_Qnp oq on oq.id=p.TypeSettlementNonresident_id"
	+" left join Omc_StreetT ost on ost.id=p.TypeStreetNonresident_id"
    +" where pm.dtype='CriminalPhoneMessage'"
    +" and "+paramDate+" between to_date('"+dateBegin+"','dd.mm.yyyy')  and to_date('"+dateEnd+"','dd.mm.yyyy')"  
    +" and ( m.noActuality is null or m.noActuality='0')"
    +" "+pigeonHoleSql+" "+departmentSql+" "+phoneMessageTypeSql+" "+phoneMessageSubTypeSql
    +" order by "+paramDate ;
    var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
    var ret = new java.util.ArrayList() ;
	for (var i=0; i < list.size(); i++) {
		var obj = list.get(i) ;
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		par.set1(""+(i+1)) ;
		for (var j=2;j<=obj.length;j++) {
			eval("par.set"+(j)+"(obj[j-1]);") ;
		}
		ret.add(par) ;
	}
	map.put("list",ret) ;
	return map ;
}
function printHistology(aCtx,aParams) {
	var dateBegin = aParams.get("dateBegin");
	var dateEnd=aParams.get("dateEnd") ;
	var typePhatology=+aParams.get("typePhatology") ; ;
	var departmentSql="" ;
	var department = +aParams.get("department") ;
	var pathologySql="" ;
	if (department>0) {
		departmentSql=" and slo.department_id='"+department+"'"
	}
	if (typePhatology==1) {
		pathologySql=" and vhr.isWithPathology='1'" ;
	} else if (typePhatology==2) {
		pathologySql=" and (vhr.isWithPathology is null or vhr.isWithPathology='0')" ;
	}
    if (dateEnd==null || dateEnd.equals("")) dateEnd=dateBegin ;
	var sql ="select "
    +" cb.id as id,pat.lastname ||' '||pat.firstname ||' '|| pat.middlename as patmiddlename"
    +"     ,to_char(pat.birthday,'dd.mm.yyyy') as patbirthday"
    +"          	,to_char(cb.birthFinishDate,'dd.mm.yyyy')||' '||cast(cb.birthFinishTime as varchar(5)) as datetimebirthday"
    +"         	,preg.orderNumber as pregorderNumber,cons.name as consname"
    +"         	,pec.previousPregnancies as pecnotes,vms.name as vmsname,pec.pregnancyFeatures as pregpregnancyFeatures"
    +"         	,vof.name as ofname, list(distinct case "
    +"         	when vdrt.code='4' and vpd.code='1' and (mkb.code='O82.1' or mkb.code='O82.0') then 'кесарево'" 
    +"         	when vdrt.code='4' and vpd.code='1' and (mkb.code = 'O80.0' or mkb.code='O80.1') then 'естествен.'"
    +"         	when vdrt.code='4' and vpd.code='1' then mkb.code else null end) as childInfo"
    +"         	,case when cb.placentaHistologyOrder='1' then 'Направлена плацента на гистологию.'||coalesce(vhr.name,'') else '' end as histology"
    +"         	from ChildBirth cb "
    +"         	left join MedCase slo on slo.id=cb.medCase_id"
    +"         	left join MedCase sls on sls.id=slo.parent_id"
    +"          	left join Pregnancy preg on preg.id=sls.pregnancy_id" 
    +"          	left join Patient pat on pat.id=preg.patient_id"
    +"          	left join VocMarriageStatus vms on vms.id=pat.marriageStatus_id"
    +"         	left join Diagnosis d on d.medCase_id=slo.id"
    +"         	left join VocDiagnosisRegistrationType vdrt on vdrt.id=d.registrationType_id"
    +"         	left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id"
    +"         	left join VocIdc10 mkb on mkb.id=d.idc10_id"
    +"         	left join Omc_Frm vof on vof.id=sls.orderType_id"
    +"         	left join PregnanExchangeCard pec on pec.pregnancy_id=preg.id"
    +"         	left join MisLpu cons on cons.id=pec.consultation_id"
    +"         	left join VocHistologyResult vhr on vhr.id=cb.histology_id"
    +"     where" 
    +"     cb.birthFinishDate  between to_date('"+dateBegin+"','dd.mm.yyyy')" 
    +"     and to_date('"+dateEnd+"','dd.mm.yyyy') "+departmentSql+" "+pathologySql
    +"           	group by cb.id,cb.birthFinishDate,cb.birthFinishTime,"
    +"           	vof.name,cb.placentaHistologyOrder,vhr.isWithPathology,pat.lastname,pat.firstname,pat.middlename"
    +"     		,pat.birthday,preg.orderNumber ,cons.name "
    +"         	,pec.previousPregnancies,vms.name,pec.pregnancyFeatures,vhr.name"
    +"     order by cb.birthFinishDate";
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	var ret = new java.util.ArrayList() ;
	for (var i=0; i < list.size(); i++) {
		var obj = list.get(i) ;
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		par.set1(""+(i+1)) ;
		for (var j=2;j<=obj.length;j++) {
			eval("if (obj[j-1]!=null) {par.set"+(j)+"(obj[j-1]);} else {par.set"+(j)+"(\"\");}") ;
		}
		ret.add(par) ;
	}
	map.put("list",ret) ;
	return map ;
}
function executeSql(aName,aManager,aSql) {
	var listReestr = aManager.createNativeQuery(aSql).getResultList() ;
	var ret = new java.util.ArrayList() ;
	//aCntCols++ ;
	var j=1 ;
	for (var ind=0 ; ind< listReestr.size() ; ind++) {
		var obj = listReestr.get(ind) ;
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		par.setSn((j++)) ;
		for (var i=1;i<=obj.length;i++) {
			var i1=i-1 ;
			eval("par.set"+i+"(obj["+i1+"]!=null?obj["+i1+']:"");') ;
		}
		ret.add(par) ;
	}
	map.put(aName,ret) ;
}



function printDiagnosisJournal(aCtx,aParams) {
	var mkbCode ;
	var mkbLike ;
	var mkbName ;
	var dep ;
	var servStream ;
	var fldDate ;
	var dateBegin = aParams.get("dateBegin");
	var dateEnd ;
	var bedSubTypeSql ;
	var filterAdd ;
	var regType ;
	var bedSubTypeSql ;
	var filterSql ;
	var mkbNameGroup ;


    //if (dateBegin!=null && dateBegin!="")  {
    	 dateEnd = aParams.get("dateEnd") ;
    	if (dateEnd==null||dateEnd=="") {
    		dateEnd=dateBegin ;
    	}
    	
    	var stat = aParams.get("typeStatus") ;
    	var typeMKB = +aParams.get("typeMKB") ;
    	var typeDate = +aParams.get("typeDate") ;
    	 mkbCode = "mkb.code" ;
    	 mkbName = "mkb.name" ;
    	 mkbLike = "0" ;
    	
    		if (typeMKB==1) {mkbName="substring(mkb.code,1,1)";mkbCode="substring(mkb.code,1,1)" ;mkbLike="1";} 
    		else if (typeMKB==2) {mkbName="substring(mkb.code,1,2)";mkbCode="substring(mkb.code,1,2)" ;mkbLike="1";}
    		else if (typeMKB==3) {mkbName="substring(mkb.code,1,3)";mkbCode="substring(mkb.code,1,3)" ;mkbLike="1";}
    	//request.setAttribute("mkbName",mkbName) ;
    	mkbNameGroup=(mkbName=="mkb.name")?",mkb.name":"" ;
    	//request.setAttribute("mkbCode",mkbCode) ;
    	//request.setAttribute("mkbLike",mkbLike) ;
    	fldDate = "slo.dateFinish" ;
    	if (typeDate!=null) {
    		if (typeDate==1) {fldDate="slo.dateStart" ;} 
    		else if (typeDate==3) {fldDate="slo.transferDate" ;}
    	} 
    	//request.setAttribute("fldDate",fldDate) ;
    	var isStat = true ;
    	if (stat!=null && stat.equals("2")) {
    		isStat = false ;
    	}

    	 dep = +aParams.get("department") ;
    	if (dep>0) {
    		dep= " and slo.department_id="+dep ;
    		//request.setAttribute("department",dep) ;
    	} else {
    		dep= "";
    		//request.setAttribute("department","0") ;
    	}
    	 servStream = +aParams.get("serviceStream") ;
    	if (servStream>0) {
    		servStream= " and vss.id="+servStream ;
    		//request.setAttribute("serviceStream", servStream) ;
    	} else {
    		servStream= "";
    		//request.setAttribute("serviceStream", "0") ;
    	}
    	 regType = +aParams.get("registrationType") ;
    	if (regType>0) {
    		regType=" and diag.registrationType_id="+regType ;
    	} else {regType=""}
    	 priority = +aParams.get("priority") ;
    	if (priority>0) {
    		prioritySql=" and diag.priority_id="+priority ;
    	} else {
    		prioritySql ="" ;
    	}
    	 bedSubType = +aParams.get("bedSubType") ;
    	if (bedSubType>0) {
    		bedSubTypeSql=" and bf.bedSubType_id="+bedSubType ;
    	} else {
    		bedSubTypeSql="" ;
    	}
    	var filter = aParams.get("filterAdd") ;
    	var filterSql="" ;
    	if (filter!=null && filter!="") {
    		filter = filter.toUpperCase() ;
    		filterAdd=filter ;
    		var fs=filter.split("-") ;
    		if (fs.length>1) {
    			filterSql=" and mkb.code between '"+fs[0].trim()+"' and '"+fs[1].trim()+"'" ;
    		} else {
    			filterSql=" and mkb.code like '"+filter.trim()+"%'" ;
    		}
    		
    	}
	
	
	var sql="select "+mkbCode+"||':"+mkbLike+":"+dep+":"+servStream+":"+fldDate+":"+dateBegin+":"+dateEnd+":'||vpd.id||':'||vdrt.id||':"+bedSubTypeSql+":"+filterAdd+"' as id "
		+ " ,"+mkbCode+" as mkb,count(distinct slo.id) as cntSlo,vpd.name as vpdname,vdrt.name as vdrtname" 
		+ " ,"+mkbName+" as mkbname"
		+ " ,count(distinct sls.id) as cntSls"
		+ " ,count(distinct case when sls.dateFinish is not null and sls.dischargeTime is not null then sls.id else null end) as cntDischarge"
		+ " ,count(distinct case when vhr.code='11' then sls.id else null end) as cntDeath"
		+ " ,round(sum("
		+ " 	distinct "
		+ " 	  cast(case" 
		+ " 		when (sls.dateFinish is null or sls.dischargeTime is null) then 0" 
		+ " 		when (sls.dateFinish-sls.dateStart)=0 then 1 "
		+ " 		when bf.addCaseDuration='1' then ((sls.dateFinish-sls.dateStart)+1)" 
		+ " 		else (sls.dateFinish-sls.dateStart) end "
		+ " 		||'.'|| sls.id as decimal)"
		+ " 			)"
		+ " 	-sum(distinct" 
		+ " 	  cast('0.'|| case when sls.dateFinish is not null and sls.dischargeTime is not null then sls.id else 0 end as decimal))"
		+ " 	  ,0) as cntSlsDays"
		+ " ,"
		+ " case when count(distinct case" 
		+ " when sls.dateFinish is not null and sls.dischargeTime is not null then sls.id" 
		+ " else null end)>0 "
		+ " then"
		+ " 	round(("
		+ " 	sum("
		+ " 	distinct" 
		+ " 	  cast(case" 
		+ " 		when (sls.dateFinish is null or sls.dischargeTime is null) then 0" 
		+ " 		when (sls.dateFinish-sls.dateStart)=0 then 1" 
		+ " 		when bf.addCaseDuration='1' then ((sls.dateFinish-sls.dateStart)+1)" 
		+ " 		else (sls.dateFinish-sls.dateStart) end" 
		+ " 		||'.'|| sls.id as decimal)"
		+ " 			)"
		+ " 	-sum(distinct" 
		+ " 	  cast('0.'|| case when sls.dateFinish is not null and sls.dischargeTime is not null then sls.id else 0 end as decimal))"
		+ " 	 )"
		+ " 	/count(distinct case when sls.dateFinish is not null and sls.dischargeTime is not null then sls.id else null end),1)"
		+ " 	else 0 end as cntSrDays"
		+ " ,count(distinct case when so.id is null then sls.id else null end) as cntOper"
		+ " from Diagnosis diag"
		+ " left join VocIdc10 mkb on mkb.id=diag.idc10_id"
		+ " left join MedCase slo on slo.id=diag.medCase_id"
		+ " left join MedCase sls on sls.id=slo.parent_id"
		+ " left join MedCase aslo on aslo.parent_id=sls.id"
		+ " left join SurgicalOperation so on so.medcase_id=aslo.id"
		+ " left join VocHospitalizationResult vhr on vhr.id=sls.result_id"
		+ " left join BedFund bf on bf.id=slo.bedFund_id"
		+ " left join VocServiceStream vss on vss.id=slo.serviceStream_id"
		+ " left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id"
		+ " left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
		+ " where "+fldDate+" between to_date('"+dateBegin+"','dd.mm.yyyy')"
		+ " 	and to_date('"+dateEnd+"','dd.mm.yyyy') and slo.dtype='DepartmentMedCase'"
		+ " 	"+servStream+" "+dep+" "+prioritySql+" "+regType+" "+bedSubTypeSql+""
		+ " 	"+filterSql+""
		+ " group by "+mkbCode+",vpd.id,vpd.name,vdrt.id,vdrt.name "+mkbNameGroup+""
		+ " order by "+mkbCode+",vpd.id,vdrt.id" ;
	executeSql("list",aCtx.manager,sql) ;
	return map ;
}
function printJournalByDay(aCtx,aParams) {
	var ids = aParams.get("id") ;
	var id = ids.split(":") ;
	var dateBegin = id[3] ;
	var typeDate = +id[2] ;
	var typeHour = id[1] ;
	var typeEmergency = id[0] ;
	var typeNumber=+id[7] ;
	var numberInJournal =+id[6];
	var step = 1 ;
	if (numberInJournal<1) numberInJournal=1 ;
	if (typeNumber==1||typeNumber==2) {
		step=2 ;
		if (typeNumber==1) {
			if (numberInJournal%2!=0) numberInJournal=numberInJournal+1 ;
		} else {
			if (numberInJournal%2==0) numberInJournal=numberInJournal+1 ;
		}
	}
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
    	if (typeHour!=null && (+typeHour==1)) {
    		
			period = " and ((m."+dateI+"= to_date('"+date+"','dd.mm.yyyy') and m."+timeI+">= cast('07:00' as time) )"
					+" or (m."+dateI+"= to_date('"+date1+"','dd.mm.yyyy') and m."+timeI+"< cast('07:00' as time) )"
			+")" ;
    		hourInfo=" (7 часов)";
    	} else if (typeHour!=null && (+typeHour==2)) {
    		period = " and ((m."+dateI+"= to_date('"+date+"','dd.mm.yyyy') and m."+timeI+">= cast('08:00' as time) )"
    		+" or (m."+dateI+"= to_date('"+date1+"','dd.mm.yyyy') and m."+timeI+"< cast('08:00' as time) )"
    		+")" ;
    		hourInfo=" (8 часов)" ;
    	} else if (typeHour!=null && (+typeHour==3)) {
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
	var sql = "select" 
		    +" m.id as mid"
		    +" ,to_char(m.dateStart,'dd.mm.yyyy')||' '||cast(m.entranceTime as varchar(5))||case when m.emergency='1' then ' (Э)' else ' (П)' end as mdateStart"
		    +" ,'('||coalesce(pat.patientSync,'')||') '"
		    +" ||' '||pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as fio"
		    +" , ("
		    +" 	cast(to_char(m.dateStart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)"
		    +" 	+case when (cast(to_char(m.dateStart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(m.dateStart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(m.dateStart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end"
		    +" ) as age"
			+", case when pat.address_addressId is not null "
			+"          then coalesce(adr.fullname,adr.name) || "
			+"               case when pat.houseNumber is not null and pat.houseNumber!='' then ' д.'||pat.houseNumber else '' end" +
					" ||case when pat.houseBuilding is not null and pat.houseBuilding!='' then ' корп.'|| pat.houseBuilding else '' end" +
					"||case when pat.flatNumber is not null and pat.flatNumber!='' then ' кв. '|| pat.flatNumber else '' end"
			+"       when pat.territoryRegistrationNonresident_id is not null"
			+"          then okt.name||' '||pat.RegionRegistrationNonresident||' '||oq.name||' '||pat.SettlementNonresident"
			+"               ||' '||ost.name||' '||pat.StreetNonresident||"
			//+"               coalesce(' д.'||pat.HouseNonresident,'') ||coalesce(' корп.'|| pat.BuildingHousesNonresident,'') ||coalesce(' кв. '|| pat.ApartmentNonresident,'')"
			+"               case when pat.HouseNonresident is not null and pat.HouseNonresident!='' then ' д.'||pat.HouseNonresident else '' end" 
			+" ||case when pat.BuildingHousesNonresident is not null and pat.BuildingHousesNonresident!='' then ' корп.'|| pat.BuildingHousesNonresident else '' end" 
			+"||case when pat.ApartmentNonresident is not null and pat.ApartmentNonresident!='' then ' кв. '|| pat.ApartmentNonresident else '' end"
			+"   else '' "
			+"  end as address"
			+" ,coalesce(oml.name,of1.name,'') as omlname"
			+" ,case when m.deniedHospitalizating_id is null then ml.name else '' end as mlname"
		    +" ,sc.code as sccode"
		    +" , list(case when (vdrt.code='1' or vdrt.code='2') then coalesce(mkb.code||' '||diag.name,diag.name) else '' end) as diag"
			+" ,coalesce(to_char(m.dateFinish,'dd.mm.yyyy')||' '||cast(m.dischargeTime as varchar(5))"
			+" ||' '||coalesce(vho.name,'')||' '||coalesce(tml.name,''),'')  as finishresult"
		    +", case when m.relativeMessage='1' then 'Да' else '' end as relativemessage"
			+" ,case when m.deniedHospitalizating_id is not null then vdh.name ||coalesce('. '||list(case when trim(prot.journalText)='-' then null else prot.journalText end),'') else '' end as vdhname"
			+" ,case when m.deniedHospitalizating_id is not null then vh.name else '' end as vhname"
			
		    +" from MedCase as m  "
		    +" left join Diary as prot on prot.medCase_id=m.id" 
		    +" left join StatisticStub as sc on sc.medCase_id=m.id" 
		    +" left outer join Patient pat on m.patient_id = pat.id" 
		    +" left join MisLpu as ml on ml.id=m.department_id "
		    +" left join diagnosis diag on diag.medcase_id=m.id"
		    +" left join vocidc10 mkb on diag.idc10_id=mkb.id"
		    +" left join VocDiagnosisRegistrationType vdrt on diag.registrationType_id=vdrt.id"
		    +" left join MisLpu as oml on oml.id=m.orderLpu_id "
		    +" left join MisLpu as tml on tml.id=m.moveToAnotherLpu_id "
		    +" left join OMC_FRM of1 on m.orderType_id=of1.id"
		    +" left join VocServiceStream vss on vss.id=m.serviceStream_id"
		    +" left join Omc_Oksm ok on pat.nationality_id=ok.id"
			+" left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id"
			+" left join address2 adr on adr.addressId = pat.address_addressId"
			+" left join Omc_KodTer okt on okt.id=pat.territoryRegistrationNonresident_id"
			+" left join Omc_Qnp oq on oq.id=pat.TypeSettlementNonresident_id"
			+" left join Omc_StreetT ost on ost.id=pat.TypeStreetNonresident_id"
		    +" left join VocDeniedHospitalizating as vdh on vdh.id=m.deniedHospitalizating_id"
		    +" left join VocHospitalizationOutcome vho on vho.id=m.outcome_id"
		    +" left join VocHospitalization vh on vh.id=m.hospitalization_id"
		    +" where m.DTYPE='HospitalMedCase' "+period+""
		    //+" and m.deniedHospitalizating_id is null"
		    //+" and m.department_id='"+depId+"'"
		    +"  "+emer+pigeonHoleId+departmentId
		    +" group by m.id,m.dateStart,m.entranceTime,pat.lastname,pat.middlename,pat.firstname,pat.birthday"
		    +" ,m.datefinish,m.dischargeTime,pat.address_addressid,adr.fullname,adr.name,pat.housenumber"
		    +" ,pat.housebuilding,pat.flatnumber,pat.territoryregistrationnonresident_id,okt.name"
		    +" ,pat.regionregistrationnonresident,oq.name,pat.settlementnonresident,ost.name,pat.streetnonresident"
		    +" ,pat.housenonresident,pat.buildinghousesnonresident,pat.apartmentnonresident,oml.name"
		    +" ,of1.name,m.deniedhospitalizating_id,ml.name,sc.code,m.emergency,pat.id,pat.patientsync"
		    +" ,ok.voc_code,pvss.omccode,adr.kladr,vdh.name,vho.name,tml.name,m.relativeMessage,vh.name"
		    +" order by m.dateStart,m.entranceTime";
			
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	var ret = new java.util.ArrayList() ;
	for (var i=0; i < list.size(); i++) {
		var obj = list.get(i) ;
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		par.set1(""+(numberInJournal)) ;
		numberInJournal=numberInJournal+step ;
		for (var j=2;j<=obj.length;j++) {
			eval("par.set"+(j)+"(obj[j-1]);") ;
		}
		ret.add(par) ;
	}
	map.put("list",ret) ;
	return map ;
}


function formatDate(aDate) {
	  var dd = aDate.getDate()
	  if ( dd < 10 ) dd = '0' + dd;
	  var mm = aDate.getMonth()+1
	  if ( mm < 10 ) mm = '0' + mm;
	  var yy = aDate.getFullYear();
	  //if ( yy < 10 ) yy = '0' + yy;
	  return dd+'.'+mm+'.'+yy;
}
function getDate(aDateS,aDay) {
	
	var d = (""+aDateS).split(".") ;
	var dd=new Date(d[2],d[1]-1,+d[0]+aDay) ;
	return formatDate(dd) ;
}
function current_info(aCtx) {
    var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
    var FORMAT_3 = new java.text.SimpleDateFormat("HH:mm") ;	
	var current = new java.util.Date() ;
	var curDate = new java.sql.Date(current.getTime()) ;
	var curTime = new java.sql.Time(current.getTime()) ;	
	map.put("current_date",FORMAT_2.format(curDate)) ;
	map.put("current_time",FORMAT_3.format(curTime)) ;
	map.put("current_username",aCtx.sessionContext.callerPrincipal.name ) ;
}
function printReport007(aCtx,aParams) {
	current_info(aCtx) ;
	var date1 = aParams.get("dateBegin");
	map.put("dateInfo",date1) ;
	var department = +aParams.get("department") ;
	var typeHour=+aParams.get("typeHour") ;
	var date2 = getDate(date1,1) ;
	var timeSql="09:00"
	if (typeHour==1) {
		timeSql="07:00" ;
	} else if (typeHour==2) {
		timeSql="08:00" ;
	} else if (typeHour==4) {
		timeSql="00:00" ;
	}
	var sqlDep = "select ml2.printName as printNameMain,ml1.name as ml1name from MisLpu ml1 left join MisLpu ml2 on ml2.id=ml1.parent_id where ml1.id='"+department+"'" ;
	var listDep = aCtx.manager.createNativeQuery(sqlDep).getResultList() ;
	if (listDep.size()>0) {
		map.put("lpuMainInfo",listDep.get(0)[0]) ;
		map.put("titleInfo",listDep.get(0)[1]+' за '+date1) ;
		map.put("date1", date1);
	} else {
		throw "Не найдено отделение "+department+"!!!" ;
	}
	var sqlBType = "select distinct list(vbt.name) from mislpu ml" +
			" left join bedfund bf on bf.lpu_id=ml.id" +
			" left join vocbedtype vbt on vbt.id=bf.bedtype_id" +
			" where ml.id="+department;
	var listBedTypes = aCtx.manager.createNativeQuery(sqlBType).getResultList() ;
	
	
	var sql1 = "select bf.bedsubtype_id as bfbudsubtypeid,bf.bedtype_id as bfbedtype,vbst.name as vbstname,vbt.name as vbtname,lpu.name" 
		+"  from medcase slo"
		+"  left join patient pat on pat.id=slo.patient_id"
		+"  left join medcase sls on sls.id=slo.parent_id"
		+"  left join vochospitalizationoutcome vho on vho.id=sls.outcome_id"
		+"  left join vochospitalizationresult vhr on vhr.id=sls.result_id"
		+" left join bedfund bf on bf.id=slo.bedfund_id"
		+" left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id"
		+" left join vocbedtype vbt on vbt.id=bf.bedtype_id"
		+" left join vocservicestream vss on vss.id=bf.servicestream_id"
		+" left join mislpu lpu on lpu.id=slo.department_id"
		//+"  LEFT JOIN Address2 ad1 on ad1.addressId=pat.address_addressId" 
		//+"  LEFT JOIN Address2 ad2 on ad2.addressId=ad1.parent_addressId"
		+"  where "
		+"  slo.dtype='DepartmentMedCase' and slo.department_id='"+department+"'"
		+" and (to_date('"+date1+"','dd.mm.yyyy')>=slo.datestart or"
		+"			slo.datestart = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.entrancetime)"
		+"			and (slo.datefinish is null or slo.datefinish > to_date('"+date1+"','dd.mm.yyyy') or "
		+"			slo.datefinish = to_date('"+date1+"','dd.mm.yyyy') and slo.dischargetime>=cast('"+timeSql+"' as time) or"
		+"			slo.datefinish = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.dischargetime)"
		+"			and (slo.transferdate is null or slo.transferdate > to_date('"+date1+"','dd.mm.yyyy') or"
		+"			slo.transferdate = to_date('"+date1+"','dd.mm.yyyy') and slo.transfertime>=cast('"+timeSql+"' as time) or"
		+"			slo.transferdate = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.transfertime)"
		+" group by bf.bedsubtype_id,bf.bedtype_id,vbst.name,vbt.name,lpu.name" ;
	
	var sql = "select vss.name as vssname"
		+" ,count(distinct case when (slo.datestart = to_date('"+date1+"','dd.mm.yyyy') and cast('"+timeSql+":00' as time)>slo.entrancetime"
		+" 		or to_date('"+date1+"','dd.mm.yyyy')>slo.datestart)"
		+" 		and (slo.datefinish is null "
		+" 		or slo.datefinish > to_date('"+date1+"','dd.mm.yyyy')" 
		+" 		or slo.datefinish = to_date('"+date1+"','dd.mm.yyyy') and slo.dischargetime>=cast('"+timeSql+"' as time))"
		+" 		and (slo.transferdate is null "
		+" 		or slo.transferdate > to_date('"+date1+"','dd.mm.yyyy')" 
		+" 		or"
		+" 		slo.transferdate = to_date('"+date1+"','dd.mm.yyyy') and slo.transfertime>=cast('"+timeSql+"' as time))"
		+" 		 then slo.id else null end)"
		+" 		as cnt5CurrentFrom"
		+" 		,count(distinct case when slo.prevmedcase_id is null and (slo.datestart = to_date('"+date1+"','dd.mm.yyyy') and slo.entrancetime>=cast('"+timeSql+":00' as time)"
		+" 		or slo.datestart = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.entrancetime) then slo.id else null end)"
		+" 		as cnt6Entrance"
		+" 		,count(distinct case when slo.prevmedcase_id is null and vht.code='DAYTIMEHOSP' and(slo.datestart = to_date('"+date1+"','dd.mm.yyyy') and slo.entrancetime>=cast('"+timeSql+":00' as time)"
		+" 		or slo.datestart = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.entrancetime) then slo.id else null end)"
		+" 		as cnt7EntranceDayHosp"
		+" ,count(distinct case when slo.prevmedcase_id is null and (slo.datestart = to_date('"+date1+"','dd.mm.yyyy') and slo.entrancetime>=cast('"+timeSql+":00' as time)"
		+" 		or slo.datestart = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.entrancetime) "
		+" 		and (ad1.addressIsVillage='1')"
		+" 		then slo.id else null end)"
		+" 		as cnt8EntranceVillagers	"	
		+" 		,count(distinct case when slo.prevmedcase_id is null and(slo.datestart = to_date('"+date1+"','dd.mm.yyyy') and slo.entrancetime>=cast('"+timeSql+":00' as time)"
		+" 		or slo.datestart = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.entrancetime) "
		+" 		and ("
		+" 		cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)"
		+" 		+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end"
		+" 		)<18"
		+" 		then slo.id else null end)"
		+" 		as cnt9EntranceTo17"
		+" 		,count(distinct case when slo.prevmedcase_id is null and (slo.datestart = to_date('"+date1+"','dd.mm.yyyy') and slo.entrancetime>=cast('"+timeSql+":00' as time)"
		+" 		or slo.datestart = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.entrancetime) "
		+" 		and "
		+" 		60<=("
		+" 		cast(to_char(slo.datestart,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)"
		+" 		+case when (cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)) <0 then -1 when (cast(to_char(slo.datestart,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) and ((cast(to_char(slo.datestart, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)-1)<0)  then -1 else 0 end"
		+" 		)"
		+" 		then slo.id else null end)"
		+" 		as cnt10EntranceFrom60"
		+" 		,count(distinct case when slo.prevmedcase_id is not null and (slo.datestart = to_date('"+date1+"','dd.mm.yyyy') and slo.entrancetime>=cast('"+timeSql+":00' as time)"
		+" 		or slo.datestart = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.entrancetime) then slo.id else null end)"
		+" 		as cnt11TransferOutOtherDepartment"
		+" 		,count(distinct case when (slo.transferdate = to_date('"+date1+"','dd.mm.yyyy') and slo.transfertime>=cast('"+timeSql+"' as time)"
		+" 		or slo.transferdate = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.transfertime) then slo.id else null end)"
		+" 		as cnt12TransferInOtherDepartment"
		+" 		,count(distinct case when vhr.code!='11' and (slo.datefinish = to_date('"+date1+"','dd.mm.yyyy') and slo.dischargetime>=cast('"+timeSql+"' as time)"
		+" 		or slo.datefinish = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.dischargetime) then slo.id else null end)"
		+" 		as cnt13Finished"
		+" 		,count(distinct case when vho.code='4' and vhr.code!='11' and (slo.datefinish = to_date('"+date1+"','dd.mm.yyyy') and slo.dischargetime>=cast('"+timeSql+"' as time)"
		+" 		or slo.datefinish = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.dischargetime) then slo.id else null end)"
		+" 		as cnt14FinishedOtherHospital"
		+" 		,count(distinct case when vho.code='3' and vhr.code!='11' and (slo.datefinish = to_date('"+date1+"','dd.mm.yyyy') and slo.dischargetime>=cast('"+timeSql+"' as time)"
		+" 		or slo.datefinish = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.dischargetime) then slo.id else null end)"
		+" 		as cnt15FinishedHourHospital"
		+" 		,count(distinct case when vho.code='2' and vhr.code!='11' and (slo.datefinish = to_date('"+date1+"','dd.mm.yyyy') and slo.dischargetime>=cast('"+timeSql+"' as time)"
		+" 		or slo.datefinish = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.dischargetime) then slo.id else null end)"
		+" 		as cnt16FinishedDayHospital"
		+" 		,count(distinct case when vhr.code='11' and (slo.datefinish = to_date('"+date1+"','dd.mm.yyyy') and slo.dischargetime>=cast('"+timeSql+"' as time)"
		+" 		or slo.datefinish = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.dischargetime) then slo.id else null end)"
		+" 		as cnt17Death"
		+" 		,"
		+" 		count(distinct case when" 
		+" 				("
		+" 					slo.transferdate is null"
		+" 					or slo.transferdate > to_date('"+date2+"','dd.mm.yyyy')"
		+" 					or slo.transferdate = to_date('"+date2+"','dd.mm.yyyy') and slo.transfertime>=cast('"+timeSql+"' as time)"
		+" 				) and ("
		+" 					slo.datefinish is null or"
		+" 					slo.datefinish > to_date('"+date2+"','dd.mm.yyyy')"
		+" 					or slo.datefinish = to_date('"+date2+"','dd.mm.yyyy') and slo.dischargetime>=cast('"+timeSql+"' as time)"
		+" 				)"
		+" 			 then slo.id else null end"
		+" 		)"
		+" 		 as cnt18CurrentTo"
		+" 		,count(distinct case when sls.hotelServices='1' and" 
		+" 		 ("
		+" 					slo.transferdate is null"
		+" 					or slo.transferdate > to_date('"+date2+"','dd.mm.yyyy')"
		+" 					or slo.transferdate = to_date('"+date2+"','dd.mm.yyyy') and slo.transfertime>=cast('"+timeSql+"' as time)"
		+" 				) and ("
		+" 					slo.datefinish is null or"
		+" 					slo.datefinish > to_date('"+date2+"','dd.mm.yyyy')"
		+" 					or slo.datefinish = to_date('"+date2+"','dd.mm.yyyy') and slo.dischargetime>=cast('"+timeSql+"' as time)"
		+" 				) then slo.id else null end)"
		+" 		 as cnt19CurrentMother"
		+" 		,cast((select sum(bf1.amount) from BedFund bf1 where bf1.lpu_id=lpu.id and bf1.dateStart<=to_date('"+date2+"','dd.mm.yyyy')"
		+" 		and (bf1.dateFinish is null or bf1.dateFinish>=to_date('"+date2+"','dd.mm.yyyy')) ) as int) as cnt20BedFund"
		+"  from medcase slo"
		+"  left join patient pat on pat.id=slo.patient_id"
		+"  left join medcase sls on sls.id=slo.parent_id"
		+" left join mislpu lpu on lpu.id=slo.department_id"
		+"  left join vochospitalizationoutcome vho on vho.id=sls.outcome_id"
		+"  left join vochospitalizationresult vhr on vhr.id=sls.result_id"
		+" left join bedfund bf on bf.id=slo.bedfund_id"
		+" left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id"
		+" left join vocservicestream vss on vss.id=bf.servicestream_id"
		+" left join vochosptype vht on vht.id=sls.sourceHospType_id"
		+"  LEFT JOIN Address2 ad1 on ad1.addressId=pat.address_addressId" 
		+"  where "
		+"  slo.dtype='DepartmentMedCase'"
		+" and (to_date('"+date1+"','dd.mm.yyyy')>=slo.datestart or"
		+"			slo.datestart = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.entrancetime)"
		+"			and (slo.datefinish is null or slo.datefinish > to_date('"+date1+"','dd.mm.yyyy') or "
		+"			slo.datefinish = to_date('"+date1+"','dd.mm.yyyy') and slo.dischargetime>=cast('"+timeSql+"' as time) or"
		+"			slo.datefinish = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.dischargetime)"
		+"			and (slo.transferdate is null or slo.transferdate > to_date('"+date1+"','dd.mm.yyyy') or"
		+"			slo.transferdate = to_date('"+date1+"','dd.mm.yyyy') and slo.transfertime>=cast('"+timeSql+"' as time) or"
		+"			slo.transferdate = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.transfertime)"
		+"  and slo.department_id='"+department+"'"
		
		//+"   and (to_date('"+date1+"','dd.mm.yyyy')>=slo.datestart or"
		//+"  slo.datestart = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.entrancetime)"
		//+" and (slo.datefinish is null or slo.datefinish >= to_date('"+date1+"','dd.mm.yyyy') or"
		//+" slo.datefinish = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.dischargetime)"
		//+" and (slo.transferdate is null or slo.transferdate >= to_date('"+date1+"','dd.mm.yyyy') or"
		//+" slo.transferdate = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.transfertime)"
		+" and bf.bedType_id=";
	//throw ""+sql1;
	var sql2 = " and bf.bedsubtype_id=" ;
	var sqlGr = " group by lpu.id,vbst.name,bf.serviceStream_id,vss.name" ;
	var list1 = aCtx.manager.createNativeQuery(sql1).getResultList() ;
	//throw sql1 ;
	var BSTnameAll = "" ;
	var BTnameAll = "" ;
	var lpu = listDep.get(0)[1] ;
	var retBST = new java.util.ArrayList() ;
	
	if (list1.size()>0) {		
		for (var i=0; i < list1.size(); i++) {
			var ret = new java.util.ArrayList() ;
			var parBST = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			var objBST = list1.get(i) ;
			var BSTname = "";
			
				var BSTname = ""+ objBST[2];
				
			
			var BTname = ""
			
				var BTname = ""+ objBST[3];
				
			
			if (BSTnameAll.split(""+ objBST[2])<2) {
				if (BSTnameAll!="") BSTnameAll = BSTnameAll +"," ;
				BSTnameAll = BSTnameAll +BSTname ;}
			if (BTnameAll.split(""+ objBST[3])<2) {if (BTnameAll!="") BTnameAll = BTnameAll +"," ;BTnameAll = BTnameAll +BTname ;}
			lpu = ""+objBST[4] ;
			parBST.set1(BTname+" "+BSTname) ;
			//j=1 ;
			var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			var parSum = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			var list = aCtx.manager.createNativeQuery(sql+"'"+objBST[1]+"' "+sql2+"'"+objBST[0]+"' "+sqlGr).getResultList() ;
			//throw sql+"'"+objBST[0]+"' "+sqlGr ;
			for (var ii=0; ii < list.size(); ii++) {
				var obj = list.get(ii) ;
				var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
				//par.set1(""+(j++)) ;
				for (var jj=0;jj<obj.length;jj++) {
					eval("par.set"+(jj+1)+"('"+obj[jj]+"')");
					if (jj>0) {
						eval("var sum1=parSum.get"+(jj+1)+"() ;") ;
						sum1 = java.lang.Integer.valueOf(sum1!=null?sum1:"0").intValue()+java.lang.Integer.valueOf(obj[jj]).intValue() ;
						eval("parSum.set"+(jj+1)+"('"+sum1+"')");
					}
				}
				ret.add(par) ;
			}
			parBST.set2(ret) ;
			parBST.set3(parSum) ;
			retBST.add(parBST) ;
		}
	} else {
		var parBST = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		parBST.set2(new java.util.ArrayList());
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		for (var i=2;i<17;i++) {
			eval("par.set"+i+"('0')");
		}
		parBST.set3(par);
		if (listBedTypes.size()>0) {
			BTnameAll=listBedTypes.get(0);
		}

		retBST.add(parBST) ;
	}
	
	map.put("bedSubType",BSTnameAll) ;
	map.put("bedType",BTnameAll) ;
	map.put("lpu",lpu) ;
	map.put("listSwod",retBST) ;
	//throw ""+retBST.get(3).get1();
	// Список поступивших
	var asql1 = "select ss.code as sscode"
		+"     	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when slo.prevMedCase_id is not null then '('||pdep.name||')' else '' end as fio"
		+" from medcase slo"
		+" left join medcase sls on sls.id=slo.parent_id"
		+" left join statisticstub ss on ss.medcase_id=sls.id"
		+" left join patient pat on slo.patient_id=pat.id"
		+" left join medcase pslo on pslo.id=slo.prevmedcase_id"
		+" left join mislpu pdep on pdep.id=pslo.department_id"
		+" left join vochosptype vht on vht.id=sls.sourceHospType_id"
		+" where  slo.dtype='DepartmentMedCase' and slo.department_id='"+department+"' and (slo.datestart = to_date('"+date1+"','dd.mm.yyyy') and slo.entrancetime>=cast('"+timeSql+":00' as time)"
		+" or slo.datestart = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+":00' as time)>slo.entrancetime)"
		+" and slo.prevMedCase_id is null"
		//+" and (vht.code is null or vht.code!='ALLTIMEHOSP')"
		+" order by pat.lastname,pat.firstname,pat.middlename" ;
	//throw asql1 ;
	//Поступивших из круглосуточного стационара (переведенных)
	var asql2 = "select ss.code as sscode"
		+"     	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when slo.prevMedCase_id is not null then '('||pdep.name||')' else '' end as fio"
		+" from medcase slo"
		+" left join medcase sls on sls.id=slo.parent_id"
		+" left join statisticstub ss on ss.medcase_id=sls.id"
		+" left join patient pat on slo.patient_id=pat.id"
		+" left join medcase pslo on pslo.id=slo.prevmedcase_id"
		+" left join mislpu pdep on pdep.id=pslo.department_id"
		+" left join vochosptype vht on vht.id=sls.sourceHospType_id"
		+" where  slo.dtype='DepartmentMedCase' and slo.department_id='"+department+"' and (slo.datestart = to_date('"+date1+"','dd.mm.yyyy') and slo.entrancetime>=cast('"+timeSql+":00' as time)"
		+" or slo.datestart = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+":00' as time)>slo.entrancetime)"
		+" and slo.prevMedCase_id is not null"
		//+" and (vht.code='ALLTIMEHOSP')"
		+" order by pat.lastname,pat.firstname,pat.middlename" ;
	// Список выписанных
	var asql3 = "select"
		+"     	ss.code as sscode"
		+"     	,to_char(slo.dateStart,'dd/mm')||' '||pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when slo.prevmedcase_id is not null then ' ('||pdep.name||')' else '' end as fio"
		+" from medcase slo"
		+" left join medcase sls on sls.id=slo.parent_id"
		+" left join statisticstub ss on ss.medcase_id=sls.id"
		+" left join patient pat on slo.patient_id=pat.id"
		+" left join medcase pslo on pslo.id=slo.prevmedcase_id"
		+" left join mislpu pdep on pdep.id=pslo.department_id"
		+" left join vochosptype vht on vht.id=sls.sourceHospType_id"
		+" left join vochospitalizationoutcome vho on vho.id=sls.outcome_id"
		+" left join vochospitalizationresult vhr on vhr.id=sls.result_id"
		+" where  slo.dtype='DepartmentMedCase' and slo.department_id='"+department+"' and (slo.datefinish = to_date('"+date1+"','dd.mm.yyyy') and slo.dischargetime>=cast('"+timeSql+"' as time)"
		+" or slo.datefinish = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.dischargetime)"
		+" and vho.code!='2' and vho.code!='3' and vho.code!='4'"
		+" and vhr.code!='11'"
		+" order by pat.lastname,pat.firstname,pat.middlename" ;
	// Список переведенных в  другие отделения больницы
	var asql4 = "select ss.code as sscode"
		+"     	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when nslo.id is not null then ' ('||ndep.name||')' else '' end as fio"
		+" from medcase slo"
		+" left join medcase sls on sls.id=slo.parent_id"
		+" left join statisticstub ss on ss.medcase_id=sls.id"
		+" left join patient pat on slo.patient_id=pat.id"
		+" left join medcase pslo on pslo.id=slo.prevmedcase_id"
		+" left join medcase nslo on nslo.prevmedcase_id=slo.id"
		+" left join mislpu pdep on pdep.id=pslo.department_id"
		+" left join mislpu ndep on ndep.id=nslo.department_id"
		+" left join vochosptype vht on vht.id=sls.sourceHospType_id"
		+" left join vochospitalizationoutcome vho on vho.id=sls.outcome_id"
		+" left join vochospitalizationresult vhr on vhr.id=sls.result_id"
		+" where  slo.dtype='DepartmentMedCase' and slo.department_id='"+department+"' and (slo.transferdate = to_date('"+date1+"','dd.mm.yyyy') and slo.transfertime>=cast('"+timeSql+"' as time)"
		+" or slo.transferdate = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.transfertime)"
		+" order by pat.lastname,pat.firstname,pat.middlename" ;
	// Список переведенных в другие стационары
	var asql5 = "select ss.code as sscode"
		+"     	,pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when slo.prevmedcase_id is not null then ' ('||pdep.name||')' else '' end as fio"
		+" from medcase slo"
		+" left join medcase sls on sls.id=slo.parent_id"
		+" left join statisticstub ss on ss.medcase_id=sls.id"
		+" left join patient pat on slo.patient_id=pat.id"
		+" left join medcase pslo on pslo.id=slo.prevmedcase_id"
		+" left join mislpu pdep on pdep.id=pslo.department_id"
		+" left join vochosptype vht on vht.id=sls.sourceHospType_id"
		+" left join vochospitalizationoutcome vho on vho.id=sls.outcome_id"
		+" left join vochospitalizationresult vhr on vhr.id=sls.result_id"
		+" where  slo.dtype='DepartmentMedCase' and slo.department_id='"+department+"' and (slo.datefinish = to_date('"+date1+"','dd.mm.yyyy') and slo.dischargetime>=cast('"+timeSql+"' as time)"
		+" or slo.datefinish = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.dischargetime)"
		+" and (vho.code='2' or vho.code='3' or vho.code='4')"
		+" and vhr.code!='11'"
		+" order by pat.lastname,pat.firstname,pat.middlename" ;
	// Список умерших
	var asql6 = "select ss.code as sscode"
		+"     	,to_char(slo.dateStart,'dd/mm')||' '||pat.lastname||' '||substring(pat.firstname,1,1)||' '||coalesce(substring(pat.middlename,1,1),'')||' '||case when slo.prevmedcase_id is not null then ' ('||pdep.name||')' else '' end as fio"
		+" from medcase slo"
		+" left join medcase sls on sls.id=slo.parent_id"
		+" left join statisticstub ss on ss.medcase_id=sls.id"
		+" left join patient pat on slo.patient_id=pat.id"
		+" left join medcase pslo on pslo.id=slo.prevmedcase_id"
		+" left join mislpu pdep on pdep.id=pslo.department_id"
		+" left join vochosptype vht on vht.id=sls.sourceHospType_id"
		+" left join vochospitalizationoutcome vho on vho.id=sls.outcome_id"
		+" left join vochospitalizationresult vhr on vhr.id=sls.result_id"
		+" where  slo.dtype='DepartmentMedCase' and slo.department_id='"+department+"' and (slo.datefinish = to_date('"+date1+"','dd.mm.yyyy') and slo.dischargetime>=cast('"+timeSql+"' as time)"
		+" or slo.datefinish = to_date('"+date2+"','dd.mm.yyyy') and cast('"+timeSql+"' as time)>slo.dischargetime)"
		+" and vhr.code='11'"
		+" order by pat.lastname,pat.firstname,pat.middlename" ;
	var maxStr = 0 ;
	var list1,list2,list3,list4,list5,list6 ;
	for (var i=1;i<=6;i++) {
		eval("list"+i+" = aCtx.manager.createNativeQuery(asql"+i+").getResultList() ;") ;
		eval("if (list"+i+".size()>maxStr) {maxStr=list"+i+".size()}") ;
	}
	var retReestr = new java.util.ArrayList() ;
	for (var i=0;i<maxStr;i++) {
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		for (var j=1;j<=6;j++) {
			var str = "if (list"+j+".size()>=(i+1)) {par.set"+j+"(list"+j+".get(i)[1]) ;} else {par.set"+j+"('') ;}" ;
			eval(str) ;
		}
		retReestr.add(par) ;
	}
	map.put("listPat",retReestr) ;
	return map ;
}