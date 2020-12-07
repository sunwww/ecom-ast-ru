var map = new java.util.HashMap() ;
/**Печать произвольных реквизитов для ЛПУ по умолчанию*/
function printDefaultLpuRequisites(aCtx, aFldName) {
    var lpu =aCtx.manager.createNativeQuery( "select keyvalue from softconfig  where key = 'DEFAULT_LPU' ").getResultList();
    if (lpu.size()>0) {
        printLpuRequisites(aCtx,+lpu.get(0),aFldName);
    }
}
/** Печать произвольных реквизитов по ЛПУ*/
function printLpuRequisites(aCtx, aLpuId, aFldName) {
    var map = new java.util.HashMap() ;
    var sql = "select code, value, name from MisLpuRequisite where lpu_id="+aLpuId;
    var list = aCtx.manager.createNativeQuery(sql).getResultList();
    for (var i=0;i<list.size();i++) {
        var obj = list.get(i);
        map.put(aFldName+"_"+obj[0],""+obj[1]);
        //throw ""+aFldName+"_"+obj[0]+"<>"+map.get(aFldName+"_"+obj[0]);
        map.put(aFldName+"_"+obj[0]+"Name",""+obj[2]);
    }
}

function printInfoPatientByMedcase(aCtx, aParams) {
	var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase
			, new java.lang.Long(aParams.get("id"))) ;
	return printInfoByPatient(medCase,aCtx) ;
}
function getDefaultParameterByConfig(aParameter, aValueDefault, aCtx) {
	l = aCtx.manager.createNativeQuery("select sf.id,sf.keyvalue from SoftConfig sf where  sf.key='"+aParameter+"'").getResultList();
	if (l.isEmpty()) {
		return aValueDefault ;
	} else {
		return l.get(0)[1] ;
	}
}

function printInfoByPatient(aPatient,aCtx) {
	var map = new java.util.HashMap() ;
	
	var polList = aCtx.manager.createQuery("from MedPolicy where patient_id="+aPatient.id+" and actualDateTo is null order by actualDateFrom").getResultList() ; 
	var policy = null ;
    printDefaultLpuRequisites(aCtx,"DefaultLpu");
	map.put("address_lpu",getDefaultParameterByConfig("address_lpu","___________________",aCtx)) ;
	map.put("name_lpu",getDefaultParameterByConfig("name_lpu","___________________",aCtx)) ;
	map.put("ogrn_lpu",getDefaultParameterByConfig("ogrn_lpu","___________________",aCtx)) ;
	map.put("okud_lpu",getDefaultParameterByConfig("okud_lpu","_________",aCtx)) ;
	map.put("okpo_lpu",getDefaultParameterByConfig("okpo_lpu","_________",aCtx)) ;
	policy = polList.size()>0?polList.get(polList.size()-1):"" ;
	map.put("pat", aPatient) ;
	
	var currentDate = new Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	var psychDate = null ;
	var psa=aCtx.manager.createNativeQuery("select pcc.id,to_char(pcc.firstPsychiatricVisitDate,'dd.mm.yyyy')"+
			" from PsychiatricCareCard pcc where pcc.patient_id="+aPatient.id).getResultList() ;
	if (psa.isEmpty()) {
		map.put("currentDate",FORMAT_2.format(currentDate)) ;
	} else {
		psychDate = psa.get(0)[1] ;
	}
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	map.put("psychDate",psychDate) ;
	var cal = java.util.Calendar.getInstance() ;
	cal.setTime(currentDate) ;
	cal.add(java.util.Calendar.DATE,14) ;
	map.put("dateTo",FORMAT_2.format(cal.getTime())) ;
	map.put("policyText", policy!=null? policy.text : "") ;
	var medcardL = aCtx.manager.createQuery("from Medcard where person_id="+aPatient.id).getResultList() ;
	var medcard = medcardL.size()>0?medcardL.get(0):null ;
	map.put("card.code",medcard!=null ? medcard.number : aPatient.patientSync) ;
    map.put("card.cardIndex","") ;
	var listInv = aCtx.manager.createQuery("from Invalidity where patient_id=:pat and (nextRevisionDate>=CURRENT_DATE or withoutExam='1') order by dateFrom desc,revisionDate desc,lastRevisionDate desc")
	.setParameter("pat",aPatient.id).setMaxResults(1).getResultList() ;
	var inv=listInv.size()>0?listInv.get(0):null;
	var groupInv = inv!=null?inv.group:null ;
	map.put("groupInv",groupInv) ;
	var listPriv =  aCtx.manager.createQuery("from Privilege where person=:pat and endDate is null order by beginDate desc")
		.setParameter("pat",aPatient).setMaxResults(1).getResultList() ;
	var priv = listPriv.size()>0?listPriv.get(0):null ;
	map.put("priv.info","") ;
	map.put("priv.obj",priv) ;
	map.put("priv.doc",priv!=null?priv.document:null) ;
	map.put("priv.code",priv!=null?priv.privilegeCode:null) ;
	var listPolicy = aCtx.manager.createQuery("from MedPolicy where patient=:pat and (actualDateTo is null or actualDateTo>=current_date)")
		.setParameter("pat",aPatient).setMaxResults(1).getResultList() ;
	if (listPolicy.size()>0) {
		map.put("strax",listPolicy.get(0)) ;
	} else {
		map.put("strax",null) ;
	}
	var date = new java.util.Date() ;
	var cal = java.util.Calendar.getInstance() ;
	cal.setTime(date) ;
	cal.add(java.util.Calendar.DAY_OF_MONTH, 12) ;
	var toDate = cal.getTime() ;
	map.put("date", date) ;
	map.put("todate", toDate) ;
	
	var area = aPatient.getLpuArea() ;
	var areaText = "" ;
	if(area!=null) {
		areaText = area.getName() + ",  "+area.getComment()  ;
	}
	map.put("areaText", areaText) ;
	var ddList = new java.util.ArrayList() ;
	if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MisLpu/Psychiatry")) {
		
		var sqlD = "select to_char(lpcc.startdate,'dd.mm.yyyy') as p1ocode";
		sqlD =sqlD+"  , to_char(coalesce(lpcc.finishdate,lpcc.transferdate),'dd.mm.yyyy')  as p2ocode";
		sqlD =sqlD +" ,(select mkb.code||'#'||mkb.name from diagnosis d left";
		sqlD =sqlD +" join vocidc10 mkb on mkb.id=d.idc10_id";
		sqlD =sqlD +" left join vocprioritydiagnosis vpd on vpd.id=d.priority_id";
		sqlD =sqlD +" where d.patient_id=pcc.patient_id and d.medcase_id is ";
		sqlD =sqlD +" null and vpd.code='1'";
		sqlD =sqlD +" and d.establishDate=(select max(d1.establishDate) from";
		sqlD =sqlD +" diagnosis d1";
		sqlD =sqlD +" left join vocprioritydiagnosis vpd1 on vpd1.id=d1.priority_id";
		sqlD =sqlD +" where d1.patient_id=pcc.patient_id and d1.medcase_id";
		sqlD =sqlD +"  is null and vpd1.code='1'";
		sqlD =sqlD +"   and case when coalesce(lpcc.finishdate,lpcc.transferdate) is not null and d1.establishDate<coalesce(lpcc.finishdate,lpcc.transferdate) then '1' when coalesce(lpcc.finishdate,lpcc.transferdate) is null and d1.establishDate<=current_date then '1' else null end ='1')) as mkbcode";

		sqlD =sqlD +"   from psychiaticObservation po";
		sqlD =sqlD +"  left join LpuAreaPsychCareCard lpcc on lpcc.id=po.lpuAreaPsychCareCard_id";
		sqlD =sqlD +"   left join VocpsychambulatoryCare vpac on";
		sqlD =sqlD +"  vpac.id=po.ambulatoryCare_id";

		sqlD =sqlD +" left join PsychiatricCareCard pcc on pcc.id=po.careCard_id";
		sqlD =sqlD +" where pcc.patient_id='"+aPatient.id+"' and vpac.code='Д' and po.lpuAreaPsychCareCard_id is not null";
		sqlD =sqlD +" order by po.startdate" ;
			
		//throw sqlD ;
		
		var listD = aCtx.manager.createNativeQuery(sqlD).getResultList() ;
		//throw "listD"+listD.size() ;
		var d1=null ; var d2=null ;
		var iF=null;
		for (var i=0;i<listD.size();i++) {
			var objD = listD.get(i) ;
			
			if ((d2!=null && d1!=null &&(""+d2)!=(""+objD[0])
					)||
					((listD.size()-1)==i)) {
				if (listD.size()-1==i) {
					if (d2!=null && d1!=null &&(""+d2)!=(""+objD[0])) {
						var dd1 = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
						//throw ""+d2 ;
						dd1.set1(d1) ;
						dd1.set2(d2) ;
						dd1.set3(d3!=null?d3.split("#")[0]:null) ;
						dd1.set4(d3!=null?d3.split("#")[1]:null) ;
						ddList.add(dd1) ;
						d1=objD[0];d2=null;
					}
				}
				var dd = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
				
				if (listD.size()-1==i) {
					dd.set1(d1!=null?d1:objD[0]) ;
					
					dd.set2(objD[1]) ;
				} else {
					dd.set1(d1!=null?d1:objD[0]) ;
					dd.set2(d2!=null?d2:objD[1]) ;
				}
				dd.set3(objD[2]!=null?objD[2].split("#")[0]:null) ;
				dd.set4(objD[2]!=null?objD[2].split("#")[1]:null) ;
				ddList.add(dd) ;
				d1=objD[0] ; d2 = objD[1] ; d3 = objD[2] ;
			} else {
				if (d1==null) d1=objD[0] ;
				d2 = objD[1] ; d3 = objD[2] ;
			}
		}	
	} 
	map.put("ddates",null) ;
	map.put("ddiag",null) ;
	map.put("ddiagn",null) ;
	map.put("ddlist",ddList) ;
	return map ;
}
function printInfo(aCtx, aParams) {
	
	var patient = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient
		, new java.lang.Long(aParams.get("id"))) ;
	return printInfoByPatient(patient,aCtx) ;
}
function printAgreement(aCtx, aParams) {
	return printInfo(aCtx, aParams);
}

function printCovid(aCtx, aParams) {
	var covidCard = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Covid19, new java.lang.Long(aParams.get(("id"))));
	map.put("card", covidCard);
	map.put("pat", covidCard.patient);
	map.put("epidNumber", covidCard.epidNumber);
	var labName = covidCard.labOrganization;
	if (!labName) labName='';
	else labName = ' '+labName;

	var exportDoubleDate = covidCard.exportDoubleDate;
	if (!exportDoubleDate) exportDoubleDate='';
	map.put("exportDoubleDate",exportDoubleDate);

	map.put("labName",labName);
	var hosp = covidCard.medCase;
	map.put("hosp", hosp);

	var list = aCtx.manager.createNativeQuery("select to_char((mc.datestart+1),'dd.mm.yyyy') as dsn1,st.code as stc" +
        ", case when mc.datefinish is null then '' else to_char((mc.datefinish+1),'dd.mm.yyyy') end from medcase mc" +
		" left join statisticstub st on st.id=mc.statisticstub_id where mc.id="+hosp.id).getResultList();
	if (!list.isEmpty()) {
		var obj = list.get(0);
		map.put("dateStartNext",""+obj[0]);
		map.put("hist",""+obj[1]);
        map.put("dateFinNext",""+obj[2]);
	}

	map.put("isLabConfirmed",(covidCard.labResult=="1" ? "да":"нет")+
		(covidCard.getCovidResearchDate()!=null
			? " №" + (covidCard.labResultNumber!=null ? covidCard.labResultNumber : "")+" от "+Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(covidCard.getCovidResearchDate())
			: ""));
	var age = Packages.ru.nuzmsh.util.date.AgeUtil.calcAgeYear(covidCard.patient.birthday,hosp.dateStart) ;
	map.put("age",age+"");
	map.put("contactList",aCtx.manager.createNamedQuery("Covid19Contact.getAllByPatient")
		.setParameter("patient",covidCard.getPatient()).getResultList());
	return map;
}

//Печать реестра
function printCovidReestr(aCtx,aParams) {
	var dateBegin = ''+aParams.get("dateBegin");
	var dateEnd = ''+aParams.get("dateEnd");
    if (!dateEnd) dateEnd=dateBegin;
    var type = ''+aParams.get("type");
    var dateSearch='', sqlAdd='', field2_9='', field6='';
    if (type==1) {
        dateSearch = ' sls.dateStart ';
        sqlAdd = ' and c.exportFirstDate is not null and c.exportDoubleDate is null ';
        field2_9 = ' sls.datestart+1 ';
        field6='Первичное';
    }
    else if (type==2) {
        dateSearch = ' c.exportDoubleDate ';
        sqlAdd = ' and c.exportDoubleDate is not null and c.exportDischargeDate is null ';
        field2_9 = ' c.exportDoubleDate ';
		field6='Повторное';
    }
    else if (type==3) {
        dateSearch = ' c.exportDischargeDate ';
        sqlAdd = ' and c.exportDischargeDate is not null ';
        field2_9 = ' c.exportDischargeDate ';
		field6='Выписное';
    }
	var reestrSql = " select to_char(" + field2_9 + ",'dd.mm.yyyy') as f0_datestart" +
		" ,st.code as f1_hist" +
		" ,pat.lastname||' '||pat.firstname||' '||pat.middlename as f2_patfio" +
		" ,to_char(pat.birthday,'dd.mm.yyyy') as f3_bday" +
		" ,idc.code as f4_mkb" +
		" ,c.epidnumber as f5_epid" +
		" ,  c.epidnumber as f6_enumb" +
		" ,vhr.name as f7_vhrName" +
		"    from Covid19 c" +
		"    left join Patient pat on pat.id=c.patient_id" +
		"    left join medcase sls on sls.id=c.medcase_id" +
		"    left join mislpu dep on dep.id=sls.department_id" +
		"    left join vochospitalizationresult vhr on vhr.id=c.hospresult_id" +
		"    left join statisticstub st on st.medcase_id=sls.id" +
		"    left join vocidc10 idc on idc.id=c.mkb_id" +
		"    where " + dateSearch + " between to_date('" + dateBegin + "','dd.mm.yyyy')" +
		" 	 and to_date('" + dateEnd + "','dd.mm.yyyy')" +
		" 	 and (c.noActual is null or c.noActual='0')" +
        sqlAdd +
		"    order by pat.lastname||' '||pat.firstname||' '||pat.middlename";
	var resultCardList = aCtx.manager.createNativeQuery(reestrSql).getResultList();
	var cards = new java.util.ArrayList();
	if(!resultCardList.isEmpty()) {
		for (var i=0; i<resultCardList.size();i++){
			var p = resultCardList.get(i);
			var pp = new java.util.ArrayList();
			var f0_datestart = p[0];
			var f1_hist = p[1];
			var f2_patfio = p[2];
			var f3_bday = p[3];
			var f4_mkb = p[4];
			var f5_epid = p[5];
			var f6_enumb = p[6];
			var f7_resHosp = p[7];

			pp.add(((i+1)+'').replace('.0',''));//0
			pp.add(f0_datestart);
			pp.add(f1_hist);
			pp.add(f2_patfio);
			pp.add(f3_bday);
			pp.add(f4_mkb);
			pp.add(f5_epid);
			pp.add(f6_enumb);
			pp.add(f7_resHosp);
			pp.add(field6);

			cards.add(pp);
		}
	}
	map.put("cards", cards);
	return map;
}

//Печать журнала
function printCovidJournal(aCtx,aParams) {
	var dateBegin = ''+aParams.get("dateBegin");
	var jrnlSql = "select st.code as f1_stCode,to_char(c.exportFirstDate,'dd.mm.yyyy')||' '||to_char(c.exportFirstTime,'HH24:MI') as f2_df" +
		" ,pat.lastname||' '||pat.firstname||' '||pat.middlename as f3_patfio" +
		" ,to_char(pat.birthday,'dd.mm.yyyy') as f4_bday" +
		" , case when pat.address_addressId is not null" +
		"  then coalesce(adr.fullname,adr.name)" +
		"  ||case when pat.houseNumber is not null and pat.houseNumber!='' then ' д.'||pat.houseNumber else '' end" +
		"  ||case when pat.houseBuilding is not null and pat.houseBuilding!='' then ' корп.'|| pat.houseBuilding else '' end" +
		" ||case when pat.flatNumber is not null and pat.flatNumber!='' then ' кв. '|| pat.flatNumber else '' end" +
		" when pat.territoryRegistrationNonresident_id is not null" +
		" then okt.name||' '||pat.RegionRegistrationNonresident||' '||oq.name||' '||pat.SettlementNonresident" +
		" ||' '||ost.name||' '||pat.StreetNonresident" +
		"  ||case when pat.HouseNonresident is not null and pat.HouseNonresident!='' then ' д.'||pat.HouseNonresident else '' end" +
		" ||case when pat.BuildingHousesNonresident is not null and pat.BuildingHousesNonresident!='' then ' корп.'|| pat.BuildingHousesNonresident else '' end" +
		" ||case when pat.ApartmentNonresident is not null and pat.ApartmentNonresident!='' then ' кв. '|| pat.ApartmentNonresident else '' end" +
		" else pat.foreignRegistrationAddress end as f5_address" +
		" ,c.workplace as f6_job" +
		" ,to_char(c.SymptomsDate,'dd.mm.yyyy') as f7_startIll" +
		" ,idc.code||' '||to_char(sls.dateStart,'dd.mm.yyyy') as f8_mkb" +
		" ,to_char(sls.datestart,'dd.mm.yyyy') as f9_dateStart" +
		" ,to_char(sls.datestart,'dd.mm.yyyy') as f10_firstAsk" +
		" ,case when idc3.id is not null and c3.exportdoubledate is not null then idc3.code||' '||to_char(c3.exportdoubledate,'dd.mm.yyyy') else " +
		" case when idc2.id is not null and c2.exportdoubledate is not null then idc2.code||' '||to_char(c2.exportdoubledate,'dd.mm.yyyy') else '' end end as f11_ds" +
		" ,case when c3.covidresearchdate is not null then to_char(c3.covidresearchdate,'dd.mm.yyyy')||' '||" +
		" (case when c3.labresultnumber is not null then cast('№' as varchar)||c3.labresultnumber else '' end)||' '||" +
		" (case when c3.labResult='1' then cast('Положительно' as varchar) else case when c3.labResult='2' then cast('Отрицательно' as varchar) else '' end end) else " +
		" case when c2.covidresearchdate is not null then to_char(c2.covidresearchdate,'dd.mm.yyyy')||' '||" +
		" (case when c2.labresultnumber is not null then cast('№' as varchar)||c2.labresultnumber else '' end)||' '||" +
		" (case when c2.labResult='1' then cast('Положительно' as varchar) else case when c2.labResult='2' then cast('Отрицательно' as varchar) else '' end end)" +
		" else '' end end as f12_research" +
		" ,case when sls.datefinish is not null then cast('Дата выписки: ' as varchar)||to_char(sls.datefinish,'dd.mm.yyyy') else '' end as f13_dateFin" +
		" ,case when c3.id is not null then cast('Дата передачи КЭС: ' as varchar)||to_char(c3.exportDischargeDate,'dd.mm.yyyy') else '' end as f14_dateFinExp" +
		" from Covid19 c" +
		" left join Patient pat on pat.id=c.patient_id" +
		" left join medcase sls on sls.id=c.medcase_id" +
		" left join mislpu dep on dep.id=sls.department_id" +
		" left join vochospitalizationresult vhr on vhr.id=c.hospresult_id" +
		" left join statisticstub st on st.medcase_id=sls.id" +
		" left join vocidc10 idc on idc.id=c.mkb_id" +
		" left join Address2 adr on adr.addressid = pat.address_addressid" +
		" left join Omc_KodTer okt on okt.id=pat.territoryRegistrationNonresident_id" +
		" left join Omc_Qnp oq on oq.id=pat.TypeSettlementNonresident_id" +
		" left join Omc_StreetT ost on ost.id=pat.TypeStreetNonresident_id" +
		" left join Covid19 c2 on c2.medcase_id=c.medcase_id and c2.exportDoubleDate is not null" +
		" left join Covid19 c3 on c3.medcase_id=c.medcase_id and c3.exportDischargeDate is not null" +
		" left join vocidc10 idc3 on idc3.id=c3.mkb_id" +
		" left join vocidc10 idc2 on idc2.id=c2.mkb_id" +
		" where c.exportFirstDate=to_date('" + dateBegin + "','dd.mm.yyyy')" +
		" and c.exportDoubleDate is null " +
		" and c.id=(select min(id) from covid19 where medcase_id=sls.id)" +
		" and (c2.id is null or c2.id=(select max(id) from covid19 where medcase_id=sls.id))" +
		" and (c3.id is null or c3.id=(select max(id) from covid19 where medcase_id=sls.id))" +
		" order by pat.lastname||' '||pat.firstname||' '||pat.middlename";
	var resultCardList = aCtx.manager.createNativeQuery(jrnlSql).getResultList();
	var cards = new java.util.ArrayList();
	if(!resultCardList.isEmpty()) {
		for (var i=0; i<resultCardList.size();i++){
			var p = resultCardList.get(i);
			var pp = new java.util.ArrayList();
			var f1_stCode = p[0];
			var f2_df = p[1];
			var f3_patfio = p[2];
			var f4_bday = p[3];
			var f5_address = p[4];
			var f6_job = p[5];
			var f7_startIll = p[6];
			var f8_mkb = p[7];
			var f9_dateStart = p[8];
			var f10_firstAsk = p[9];
			var f11_ds = p[10];
			var f12_research = p[11];
			var f13_dateFin = p[12];
			var f14_dateFinExp = p[13];

			pp.add(((i+1)+'').replace('.0','') + '/' + f1_stCode);
			pp.add(f2_df);
			pp.add(f3_patfio);
			pp.add(f4_bday);
			pp.add(f5_address);
			pp.add(f6_job);
			pp.add(f7_startIll);
			pp.add(f8_mkb);
			pp.add(f9_dateStart);
			pp.add(f10_firstAsk);
			pp.add(f11_ds);
			pp.add(f12_research);
			pp.add(f13_dateFin);
			pp.add(f14_dateFinExp);

			cards.add(pp);
		}
	}
	map.put("cards", cards);
	return map;
}