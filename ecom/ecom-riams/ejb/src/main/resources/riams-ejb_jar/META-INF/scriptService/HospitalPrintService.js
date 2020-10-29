/*Печать реестра операций по операционной*/
function printOperationsByCabinet(aCtx, aParams) {
	var operRoom = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.lpu.OperatingRoom, new java.lang.Long(aParams.get("id"))) ;
	var operations = aParams.get("sqlInfo");
	var wqs =new Packages.ru.ecom.ejb.services.query.WebQueryServiceBean();
	var date = aParams.get("date");
	map.put("roomName",operRoom.groupName);
	map.put("operDate",date);
	map.put("departmentName",operRoom.lpu.name);
	var boss ="";
	if (operRoom.director!=null) {
		var bossPat  = operRoom.director.worker.person;
		boss = bossPat.lastname+" "+bossPat.firstname.substring(0,1)+". "+bossPat.middlename.substring(0,1);
	} else {
		boss = "_______________";
	}
	map.put("departmentAdmin",boss);
	map.put("operations",wqs.executeNativeSql(operations,1, aCtx.manager));
	return map;
}

/**Печать произвольных реквизитов для ЛПУ по умолчанию*/
function printDefaultLpuRequisites(aCtx, aFldName) {
    var lpu =aCtx.manager.createNativeQuery( "select keyvalue from softconfig  where key = 'DEFAULT_LPU' ").getResultList();
    if (lpu.size()>0) {
        printLpuRequisites(aCtx,+lpu.get(0),aFldName);
    }
}
/** Печать произвольных реквизитов по ЛПУ*/
function printLpuRequisites(aCtx, aLpuId, aFldName) {
    var sql = "select code, value, name from MisLpuRequisite where lpu_id="+aLpuId;
    var list = aCtx.manager.createNativeQuery(sql).getResultList();
    for (var i=0;i<list.size();i++) {
        var obj = list.get(i);
        map.put(aFldName+"_"+obj[0],""+obj[1]);
        //throw ""+aFldName+"_"+obj[0]+"<>"+map.get(aFldName+"_"+obj[0]);
        map.put(aFldName+"_"+obj[0]+"Name",""+obj[2]);
    }
}


var map = new java.util.HashMap() ;
/* Печать протокола КИЛИ */
function printKiliProtocol (aCtx, aParams) {
	//var id = new java.lang.Long(aParams.get("id"));
	var protocolNumber = new java.lang.String(aParams.get("protocolNumber"));
	var protocolDate = new java.lang.String(aParams.get("protocolDate"));
	var profileName = "";
	map.put("protocolNumber", protocolNumber);
	map.put("protocolDate", protocolDate);
	
	var patList = "select pat.lastname||' '||pat.firstname||' '||pat.middlename as f0_patFio, pat.birthday as f1_birthday, case when mlp.isNoOmc='1' then depPrev.name else mlp.name end as f2_mlpname, sls.datestart as f3_dateStart, "+
	"sls.datefinish as f4_dateFinish, pk.id as f5_pkid, stt.code as f6_sscode, pat.id as f7_patid " +
	",vwf.name as f8_vwfname, case when  mlp.isnoomc='1' then  wpat2.lastname||' '||wpat2.firstname||' '||wpat2.middlename else wpat.lastname||' '||wpat.firstname||' '||wpat.middlename end as f9_docFio" +
	",list(case when vpd.code='1' and vdrt.code='3' then mkb.code|| ' '|| dia.name end )as f10_diagOSN" +
	",list(case when vpd.code='3' and vdrt.code='3' then mkb.code|| ' '||dia.name end) as f11_diagSOP" +
	",list(case when vpd.code='4' and vdrt.code='3' then mkb.code|| ' '||dia.name end) as f12_diagOSL" +
	",list(case when vpd.code='1' and vdrt.code='5' then mkb.code|| ' '||dia.name end) as f13_diagOSLPat" +
	",dth.reasoncomplicationtext as f15_, dth.reasonconcomitanttext as f16_, case when dth.commentcategory is null or dth.commentcategory ='' then 'Имеется совпадение диагнозов' else dth.commentcategory end as f17_com" +
	", pk.protocolNumber as f18_pkNumber, pk.protocolDate as f19_pkDate, case when mlp.isnoomc='1' then depPrev.kiliprofile_id else mlp.kiliprofile_id end as f20_profileId  "+
	", case when mlp.isnoomc='1' then vkp.name else vkp2.name end as f21_profileName, vkc.name as f22_kiliConclusion "+
	",case when dth.isneonatologic=true then pk.protocolComment else '' end as protCom23 " +
	", case when dth.isneonatologic=true then dth.commentreason else '' end as dReas24 " +
	", case when dth.isneonatologic=true then dth.newbornhistory else '' end as nBHist25 " +
	" from protocolKili pk "+
	" left join deathcase dth on dth.id = pk.deathcase_id "+
	" left join patient pat on pat.id = dth.patient_id "+
	" left join medcase sls on dth.medcase_id = sls.id " +
	" left join medcase slo on slo.parent_id=sls.id and slo.datefinish is not null" +
	" left join medcase sloPrev on sloPrev.id=slo.prevmedcase_id "+
	" left join mislpu depPrev on depPrev.id=sloPrev.department_id "+ 
	" left join workfunction wf on wf.id=slo.ownerfunction_id" +
	" left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
	" left join worker w on w.id=wf.worker_id" +
	" left join patient wpat on wpat.id=w.person_id"+
	" left join mislpu mlp on slo.department_id = mlp.id "+
	" left join statisticstub stt on sls.statisticstub_id = stt.id " +
	" left join diagnosis dia on sls.id = dia.medcase_id" +
	" left join vocidc10 mkb on mkb.id=dia.idc10_id" +
	" left join vocdiagnosisregistrationtype vdrt on vdrt.id=dia.registrationtype_id" +
	" left join vocprioritydiagnosis vpd on vpd.id=dia.priority_id "+
	" left join workfunction wf2 on wf2.id=sloPrev.ownerfunction_id "+ 
	" left join vocworkfunction vwf2 on vwf2.id=wf2.workfunction_id "+
	" left join worker w2 on w2.id=wf2.worker_id "+
	" left join vockiliprofile vkp on vkp.id = depPrev.kiliprofile_id "+
	" left join vockiliprofile vkp2 on vkp2.id = mlp.kiliprofile_id "+
	" left join patient wpat2 on wpat2.id=w2.person_id "+ 
	" left join vockiliconclusion vkc on vkc.id = pk.conclusion_id "+
	" where pk.protocolnumber = '"+protocolNumber+"' and pk.protocolDate = to_date('"+protocolDate+"','dd.MM.yyyy') " +
	" group by pat.lastname||' '||pat.firstname||' '||pat.middlename, pat.birthday, mlp.name, sls.datestart,"+
	" sls.datefinish, pk.id, stt.code, pat.id , dth.reasoncomplicationtext, dth.reasonconcomitanttext,vwf.name, wpat.lastname, wpat.firstname, wpat.middlename, dth.commentcategory, mlp.isnoomc,depPrev.name,f9_docFio, depPrev.kiliprofile_id, mlp.kiliprofile_id, vkp.name, vkp2.name, vkc.name,dth.isneonatologic,dth.commentreason,dth.newbornhistory" +
	" order by f9_docFio";
	//throw ""+patList;
	var resultPatList = aCtx.manager.createNativeQuery(patList).getResultList();
	var showPat = new java.util.ArrayList();
	if(!resultPatList.isEmpty()) {
		for (var i=0; i<resultPatList.size();i++){
			var p = resultPatList.get(i);
			var pp = new java.util.ArrayList();
			var fio = p[0];//0
			var bth = p[1];//1
			var dept = p[2];//3
			var srtdate = p[3];//4
			var fnsdate = p[4];//5
			var pkId = p[5];
			var stat = p[6];//2
			var patId = p[7];
			var docTitul = p[8];//12
			var docFio = p[9];//13
			var diagOSN = p[10];//6
			var diagOSL = p[11];//7
			var diagSOP = p[12];//8
			var diagOSLPat = p[13];//9
			var OSLPat = p[14];//10
			var SOPPat = p[15];//11
			var comment = p[16];
			var profileName = p[20];
			var conclusion = p[21];
			var rashojdenie = p[16];

            var protCom = (p[22]=="")? '':'Примечание: '+p[22];
            var dReas = (p[23]=='')? '':'Причина смерти: '+p[23];
            var nBHist = (p[24]==null || p[24]=='' || p[24]=='undefined')? '': p[24];

			pp.add(fio);//0
			pp.add(bth);//1
			pp.add(stat);//2
			pp.add(dept);//3
			pp.add(srtdate);//4
			pp.add(fnsdate);//5
			pp.add(diagOSN);//6
			pp.add(diagOSL);//7
			pp.add(diagSOP);//8
			pp.add(diagOSLPat);//9
			pp.add(OSLPat);//10
			pp.add(SOPPat);//11
			pp.add(docTitul);//12
			pp.add(docFio);//13
	//		var conclusionQ = "select vkc.name from protocolkili pk left join vocKiliConclusion vkc on vkc.id = pk.conclusion_id where pk.id =" +pkId;
	//		var concResult = aCtx.manager.createNativeQuery(conclusionQ).getSingleResult();
			
			//pp.add(concResult);//14
			
			var getDefects = "select vkd.name, case when pkd.isdefectfound='1' then ' ' else ' Нет' end" +
										", coalesce(pkd.defecttext,'') "+ 
							 " from protocolkilidefect pkd" +
							 " left join vockilidefect vkd on vkd.id=pkd.defect_id " +
							 " where pkd.protocol_id = " + pkId +
							 " ORDER BY vkd.name";
			var resultDef = aCtx.manager.createNativeQuery(getDefects).getResultList();
			//pp.add(resultDef); //14
			var showDefects = new java.util.ArrayList();
			if(!resultDef.isEmpty()) {
				for (var j=0; j<resultDef.size();j++){
					var pp1 = new java.util.ArrayList();
					
					var p1 = resultDef.get(j);
					//var pp1 = new java.util.ArrayList();
					var id = ""+p1[0];
					var name = ""+p1[1]; 
					var isFound = ""+p1[2];
					
					//var text = p1[3];
					pp1.add(id); //14
					pp1.add(name); //15
					pp1.add(isFound); //16
					//pp1.add(text);
					showDefects.add(pp1);
					}
			}
			pp.add(showDefects); //14
			pp.add(comment); //15
			pp.add(conclusion); //16
			pp.add(rashojdenie); //17
			//pp.add(showDefects);//6

            pp.add(protCom); //18
            pp.add(dReas); //19
            pp.add(nBHist); //20

			showPat.add(pp);
		//	throw ""+pp.get(16);
			}
	}
	map.put("showPat", showPat); 
	map.put("profileName", profileName);
	
	
	return map;
}

function printProtocolTemplate (aCtx, aParams) {
	var protocolId = new java.lang.Long(aParams.get("id"));
	if (protocolId==null||protocolId==0) {return;}
	var prot = aCtx.manager.find(Packages.ru.ecom.poly.ejb.domain.protocol.Protocol, protocolId) ;
	
	var medCase = prot.medCase;
	var sql = "select p.id, coalesce(p.code,'_id'||p.id) as f1_code ,p.name as f2_name" 
	+" ,case when p.type='2' then coalesce(uv.name, '')"
	+" when p.type='7' then coalesce(uv.name, '')||' '||coalesce(fir.valuetext,'') when p.type='6' then fir.valuetext when p.type='3' then fir.valuetext "
	+" when p.type='1' then ''||round(fir.valueBD,0) " 
	+" when p.type='4' then replace(case when fir.valuetext is null or fir.valuetext='' then ''||round(fir.valueBD,cast(p.cntdecimal as int)) else fir.valuetext end,'.',',')"
	+" end as f3_value" 
	+" from diary d" 
	+" left join forminputprotocol fir on fir.docprotocol_id=d.id" 
	+" left join parameter p on p.id=fir.parameter_id" 
	+" left join uservalue uv on uv.id=fir.valuevoc_id" 
	+" where d.id ="+protocolId;
	var result = aCtx.manager.createNativeQuery(sql).getResultList();
	if (!result.isEmpty()) {
		for (var i=0;i<result.size();i++) {
			var param = result.get(i);
			map.put(''+param[1],''+param[3]);
			map.put(''+param[1]+'Name',param[2]);
		}
	}
	printProtocol(aCtx, aParams);
	recordPatient(medCase,aCtx);
	recordMedCaseDefaultInfo(medCase,aCtx) ;
	map.remove("medCase.info");
	
	return map;
}
function printCheckList (aCtx, aParams) {
	var id = new java.lang.Long(aParams.get("id"));
	var resultSql = "select p.id, to_char(p.planstartdate,'dd.MM.yyyy') as f1_surDate" +
			" ,wf.code as f2_surCode" +
			" ,ms.code||' ' ||ms.name as f3_surName" +
			" ,pat.patientinfo as f4_patInfo" +
			" ,ss.code as f5_statNumber" +
			" from prescription p " +
			" left join medservice ms on ms.id=p.medservice_id" +
			" left join prescriptionlist pl on pl.id=p.prescriptionlist_id" +
			" left join medcase dep on dep.id=pl.medcase_id" +
			" left join medcase sls on sls.id=dep.parent_id" +
			" left join statisticstub ss on ss.medcase_id=coalesce(sls.id, dep.id)" +
			" left join patient pat on pat.id=dep.patient_id" +
			" left join workfunction wf on wf.id=p.prescriptcabinet_id" +
			" where p.id = " + id ;
	var result = aCtx.manager.createNativeQuery(resultSql).getResultList();
	if (!result.isEmpty()) {
		var rec = result.get(0);
		map.put("patientInfo", rec[4]);
		map.put("statNumber", rec[5]);
		map.put("operCode", rec[2]);
		map.put("operDate", rec[1]);
		map.put("operName", rec[3]);
		return map;
		
	}
	return null;
			
}
function unNull (aStr) {
	return aStr!=null ? ""+aStr : "";
}

//печать листа назначений наркотиков
function printDrugPrescriptList(aCtx, aParams) {
	var username = aCtx.sessionContext.callerPrincipal.name ;
	var id = new java.lang.Long(aParams.get("id"));
	var list = aCtx.manager.createQuery("from DrugPrescription where prescriptionList_id=:id and createUsername=:username")
		.setParameter("id",id).setParameter("username",username).getResultList();
	map.put("presList",list);
	map.put("presListSize",+list.size());
	var prescriptionList = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.PrescriptList,id);
	var patient = prescriptionList.medCase.patient;
	map.put("pat",patient);
	return map;

}

function printPrescriptList(aCtx, aParams) {
    var mapTmp = new java.util.HashMap() ;
	var id = new java.lang.Long(aParams.get("id"));
	var sscode="";
	var lastname="";
	var firstname="";
	var middlename="";
//	var birthday="";
	var doctor="";
	var date = "";
	var prescriptions = new java.util.ArrayList();
	
		var presSql = "select p.id, p.dtype" +
				" ,to_char(p.planstartdate,'dd.MM.yyyy') as datestart" +
				" ,to_char(p.planstarttime,'HH24:MI') as timestart" +
				" ,to_char(p.canceldate,'dd.MM.yyyy') as canceldate" +
				" ,case when p.dtype='ServicePrescription' then ms.name" +
					" when p.dtype='DietPrescription' then 'Диета: '|| diet.name" +
					" when p.dtype='DrugPrescription' then dr.name " +
					" when p.dtype='ModePrescription' then 'Режим: '|| vmp.name " +
					" else '' end as f5_presName" +
				" ,'Частота: '||p.frequency ||' '||coalesce(vfu.name,'') as f6_vfuname" +
				" ,p.orderTime ||' '||coalesce(vpot.name,'') as f7_vpotname" +
				" ,'Дозировка: '||p.amount ||' '||coalesce(vdau.name,'') as f8_vdauname" +
				" ,'Продолжительность: '||p.duration ||' '||coalesce(vdu.name,'') as f9_vduname" +
				" ,p.comments as f10_comments" +
				" ,vdm.name as f11_drugMethod" +
				" from prescription p" +
				" left join medservice ms on ms.id=p.medservice_id" +
				" left join vocdrug as dr on dr.id=p.vocDrug_id" +
				" left join vocdrugmethod as vdm on vdm.id=p.method_id" +
				" left join vocfrequencyunit as vfu on vfu.id=p.frequencyunit_id" +
				" left join vocPrescriptOrderType as vpot on vpot.id=p.orderType_id" +
				" left join vocDrugAmountUnit as vdau on vdau.id=p.amountUnit_id" +
				" left join vocDurationUnit as vdu on vdu.id=p.durationUnit_id" +
				" left join diet diet on diet.id=p.diet_id" +
				" left join vocmodeprescription vmp on vmp.id=p.modeprescription_id" +
				" where p.prescriptionlist_id="+id+" order by p.planstartdate ";
		var pres = aCtx.manager.createNativeQuery(presSql).getResultList();
		if (!pres.isEmpty()) {
			for (var i=0;i<pres.size();i++) {
				
				var comments = new java.util.ArrayList();
				var p = pres.get(i);
				var pp = new java.util.ArrayList();
				var dtype = p[1];
				var name = unNull(p[5]);
				var startDate = unNull(p[2]);
				if (p[3]!=null) {startDate +=" "+p[3];}
				var cancelDate = unNull(p[4]);
				if (p[10]!=null) {
					comments.add(unNull(p[10]));
				}
			/*	if (dtype.equals("ServicePrescription")) {
					
				} else if (dtype.equals("DietPrescription")) {
					
				} else */
					if (dtype.equals("DrugPrescription")) {
					for (var j=6;j<10;j++) {
						if (p[j]!=null) {
							comments.add(unNull(p[j]));
						}
					}
				}/* else if (dtype.equals("ModePrescription")) {
					
				}*/
				
				pp.add(name);
				pp.add(startDate);
				pp.add(cancelDate);
				pp.add(comments);
				pp.add(p[11]); //method
				pp.add(p[9]); //duration
				pp.add(p[6]); //duration
				prescriptions.add(pp);
				
			}
		}
		var sql = "select ss.code, pat.lastname, pat.firstname, pat.middlename, to_char(pat.birthday,'dd.MM.yyyy')as birthDay" +
			" ,vwf.name ||' '|| wp.lastname ||' '|| wp.firstname ||' '|| wp.middlename as doctor, to_char(current_date,'dd.MM.yyyy') as curDate" +
			" from prescriptionlist pl" +
			" left join medcase dep on dep.id=pl.medcase_id" +
			" left join medcase sls on sls.id=dep.parent_id" +
			" left join statisticstub ss on ss.medcase_id=coalesce(sls.id, dep.id)" +
			" left join patient pat on pat.id=dep.patient_id" +
			" left join workfunction wf on wf.id=pl.workfunction_id" +
			" left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
			" left join worker w on w.id=wf.worker_id" +
			" left join patient wp on wp.id=w.person_id" +
			" where pl.id="+id;
			var arr  = aCtx.manager.createNativeQuery(sql).getResultList();
	if (!arr.isEmpty()) {
		var data = arr.get(0);
		sscode = ""+data[0];
		lastname = ""+data[1];
		firstname = ""+data[2];
		middlename = ""+data[3];
	//	birthday = ""+data[4];
		doctor = ""+data[5];
		date =""+data[6];
		
	}
	//map.put("comments",comments);
    mapTmp.put ("sql", sql);
    mapTmp.put("cardNumber", sscode);
    mapTmp.put("pat.lastname", lastname);
    mapTmp.put("pat.firstname", firstname);
    mapTmp.put("pat.middlename", middlename);
    mapTmp.put("doctorInfo", doctor);
    mapTmp.put("currentDate", date);
    mapTmp.put ("listNumber", id);
    mapTmp.put("prescriptions",prescriptions);
    if (aParams.get("inner")!='inner') map=mapTmp; else return mapTmp;
	return map;
}
function printPrescriptListTotal(aCtx,aParams) {
    var id = new java.lang.Long(aParams.get("id"));
    var sscode="";
    var lastname="";
    var firstname="";
    var middlename="";
    var birthday="";
    var currentDate="";
    var sql = "select ss.code, pat.lastname, pat.firstname, pat.middlename, to_char(pat.birthday,'dd.MM.yyyy')as birthDay " +
        ", to_char(current_date,'dd.MM.yyyy') as curDate from prescriptionlist pl " +
        "left join medcase dep on dep.id=pl.medcase_id " +
        "left join medcase sls on sls.id=dep.parent_id " +
        "left join statisticstub ss on ss.medcase_id=coalesce(sls.id, dep.id) " +
        "left join patient pat on pat.id=dep.patient_id " +
        "where sls.id="+id;
    var arr  = aCtx.manager.createNativeQuery(sql).getResultList();
    if (!arr.isEmpty()) {
        var data = arr.get(0);
        sscode = ""+data[0];
        lastname = ""+data[1];
        firstname = ""+data[2];
        middlename = ""+data[3];
        birthday = ""+data[4];
        currentDate = ""+data[5];
    }
    var lnSql = "select pl.id,dep.name " +
        "from prescriptionlist pl " +
        "left join medcase mc on mc.id=pl.medcase_id " +
        "left join mislpu dep on dep.id=mc.department_id " +
        "where pl.medcase_id in (select id from medcase where parent_id="+id+") or medcase_id="+id;
    var lns = aCtx.manager.createNativeQuery(lnSql).getResultList();
	var totalPrescs=new java.util.ArrayList();
	for (var i = 0; i < lns.size(); i++) {
		var listNumber = unNull(lns.get(i)[0]);
		if (listNumber != '') {
			var pars = new java.util.TreeMap();
			pars.put("id",listNumber);
			pars.put("inner",'inner');
			var prescriptions = printPrescriptList(aCtx,pars);
			totalPrescs.addAll(prescriptions.get('prescriptions'));
		}
	}
    map.put("cardNumber", sscode);
    map.put("pat.lastname", lastname);
    map.put("pat.firstname", firstname);
    map.put("pat.middlename", middlename);
    map.put("pat.birthday", birthday);
    map.put("currentDate", currentDate);
    map.put("prescriptions",totalPrescs);
    return map;
}
function printBloodTransfusionInfo(aCtx,aParams) {
	var id = new java.lang.Long(aParams.get("id")) ;
	var trans = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.BloodTransfusion,id);
	var medCase = trans.medCase ;
	var patient = medCase.patient ;
	map.put("trans",trans) ;
	map.put("medCase",medCase) ;
	map.put("pat",patient) ;
	map.put("statCard",medCase.parent.statisticStub.code) ;
	//Биологический тест
	//lastrelease milamesher 15.05.2020 #95
	var biolTest = new java.lang.StringBuilder() ;
	if (trans.getBloodBioProbProcedure()!=null)
		biolTest.append(trans.getBloodBioProbProcedure().getName()).append(" ");
	if (trans.getIsIllPatientsBT()!=null&&trans.getIsIllPatientsBT().booleanValue()==true) {
		biolTest.append(trans.getSerumColorBT()!=null?trans.getSerumColorBT().getName():"_________") ;
	} else {
		biolTest.append(" PS: ").append(trans.getPulseRateBT()) ;
		biolTest.append(", AD: ").append(trans.getBloodPressureTopBT()).append("/").append(trans.getBloodPressureLowerBT()) ;
		biolTest.append(", PS: ").append(trans.getRespiratoryRateBT()!=null?trans.getRespiratoryRateBT():"____") ;
		biolTest.append(", t: ").append(trans.getTemperatureBT()!=null?trans.getTemperatureBT():"_______") ;
		if (trans.getStateBT()!=null) {
			if (trans.getStateBT().getCode()!=null&&trans.getStateBT().getCode().equals("2")) {
				biolTest.append(". Состояние не изменилось.") ;
			} else {
				biolTest.append(". Состояние изменилось. Жалобы:").append(trans.getLamentBT()).append(".") ;
			}
		} else {
			biolTest.append(". Состояние: ____________________________. Жалобы _____________________________") ;
		}
	}
	if (trans.getIsBreakBT()!=null&&trans.getIsBreakBT().booleanValue()==true) biolTest.append(" Переливание прекращено.") ;
	
	map.put("biologicTest",biolTest.toString()) ;
	//Индивидуальный тест
	var personalTest = new java.lang.StringBuilder() ;
	if (trans.methodPT1!=null) {
		personalTest.append(trans.getMethodPT1().getName()!=null?trans.getMethodPT1().getName():"") ;
		personalTest.append(" ") ;
		if (trans.reagentPT1!=null && !trans.reagentPT1.equals("")) {
			personalTest.append("реактив ").append(trans.reagentPT1)
			.append(" сер. ").append(trans.reagentSeriesPT1!=null?trans.reagentSeriesPT1:"__________")
			.append(" годен до ").append(trans.reagentExpDatePT11!=null?Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(trans.reagentExpDatePT11):"_____._____.__________") ;
		}
		if (trans.resultGoodPT1!=null &&trans.resultGoodPT1.code!=null) {
			if (trans.resultGoodPT1.code.equals("1")) {
				personalTest.append(" - совместима. ")
			} else {
				personalTest.append(" - несовместима. ")
			}
		} else {
			personalTest.append(" - совместима _____.  ")
		}
	}
	if (trans.methodPT2!=null) {
		personalTest.append(trans.getMethodPT2().getName()!=null?trans.getMethodPT2().getName():"") ;
		personalTest.append(" ") ;
		if (trans.reagentPT2!=null && !trans.reagentPT2.equals("")) {
			personalTest.append("реактив ").append(trans.reagentPT2)
				.append(" сер. ").append(trans.reagentSeriesPT2!=null?trans.reagentSeriesPT2:"__________")
				.append(" годен до ").append(trans.reagentExpDatePT2!=null?Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(trans.reagentExpDatePT2):"_____._____.__________") ;
		}
		if (trans.resultGoodPT2!=null &&trans.resultGoodPT2.code!=null) {
			if (trans.resultGoodPT2.code.equals("1")) {
				personalTest.append(" - совместима. ")
			} else {
				personalTest.append(" - несовместима. ")
			}
		} else {
			personalTest.append(" - совместима _____.  ")
		}
	}
	map.put("personalTest",personalTest.toString()) ;
	//Исследования антител
	var antibodies = new java.lang.StringBuilder() ;
	if (trans.getResearchAntibodies()!=null) {
		antibodies.append(trans.getResearchAntibodies().name!=null?trans.getResearchAntibodies().name:"") ;
		
		if (trans.getResearchAntibodies().getCode()!=null&&trans.getResearchAntibodies().getCode().equals("1")) {
			antibodies.append(" Выявленные антитела: ").append(trans.getAntibodiesList()) ;
		} 
	} else {
		antibodies.append("________________________. Выявленные антитела:____________________") ;
	}
	map.put("antibodies",antibodies.toString()) ;
	//Макроскопическая оценка
	//map.put("macroBall","") ;
	if (trans.getMacroBall().getCode()!=null&&trans.getMacroBall().getCode().equals("1")) {
		map.put("macroBall"," пригодна к использованию.") ;
	} else {
		map.put("macroBall"," не пригодна к использованию.") ;
	}	
	//Акушерский анамнез
	var pregDescription = new java.lang.StringBuilder() ;
	if (trans.getCountPregnancy()!=null&&+trans.getCountPregnancy()>0) {
		pregDescription.append(trans.getCountPregnancy()) ;
		pregDescription.append(". Особенности течения: ") ;
		if (trans.getPregnancyHang()!=null) {
			pregDescription.append(trans.getPregnancyHang().name) ;
		} else {
			pregDescription.append("-") ;
		}
	} else {
		pregDescription.append("-") ;
	}
	map.put("pregDescription",pregDescription.toString()) ;
	//Осложнения
	var list = aCtx.manager.createNativeQuery(new java.lang.StringBuilder().append(" select list(vtr.name) from TransfusionComplication tc left join VocTransfusionReaction vtr on vtr.id=tc.reaction_id where tc.transfusion_id='").append(id).append("' group by tc.transfusion_id").toString()).getResultList();
	if (list.size()>0) {
		map.put("complication",new java.lang.StringBuilder().append(list.get(0)).toString()) ;
	} else {
		map.put("complication","-") ;
	}
	//Были ли реакции на переливание ранее
	if (trans.getTransfusionReactionLast()!=null&&trans.getTransfusionReactionLast().getCode()!=null&&trans.getTransfusionReactionLast().getCode().equals("1")) {
		map.put("reactionLast"," были.") ;
	} else {
		map.put("reactionLast"," не были.") ;
	}	
	//Реактивы
	var listR = aCtx.manager.createNativeQuery("select tr.id,vtr.name||' сер. '||tr.series||' годен до '||to_char(tr.expirationDate,'dd.mm.yyyy') from TransfusionReagent tr left join VocTransfusionReagent vtr on vtr.id=tr.reagent_id where tr.transfusion_id='"+id+"' order by tr.numberReagent").getResultList();
	if (listR.size()>0) {
		var reagent=new java.lang.StringBuilder() ;
		for (i=0;i<listR.size();i++) {
			if (i>0) {reagent.append("; ") ;} 
			reagent.append(i+1) ; reagent.append(". ") ;
			reagent.append(""+listR.get(i)[1]) ;
		}
		map.put("reagent",reagent.toString()) ;
	} else {
		map.put("reagent","-") ;
	}
	//Наблюдения после переливания
	for (var i=0;i<3;i++) {
		var listM =  aCtx.manager.createQuery("from TransfusionMonitoring where transfusion_id='"+id+"' and hourAfterTransfusion='"+i+"'").getResultList() ;
		if (listM.size()>0) {
			map.put("monitor"+i,listM.get(0)) ;
		} else {
			map.put("monitor"+i,new Packages.ru.ecom.mis.ejb.domain.medcase.TransfusionMonitoring()) ;
		}
	}
	return map ;
}
function printDirectVK(aCtx,aParams) {
	var id = new java.lang.Long(aParams.get("id")) ;
	var doc = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.expert.ClinicExpertCard,id);
	map.put("directVk",doc) ;
	map.put("sls",doc.medCase.parent) ;
	map.put("patient",doc.medCase.patient) ;
	map.put("direct.countDaysDisability",recordDuration(doc.disabilityDate,doc.orderDate,1)) ;
	map.put("direct.preDays",recordDuration(doc.orderDate,doc.preFinishDate,0)) ;
	
	//map.put("patInfo.age","") ;
	recordAge("patInfo.age",doc.medCase.patient.birthday,doc.orderDate,1,1,1) ;
	recordZavOtd(aCtx,doc.lpu!=null?doc.lpu.id:null,"direct.administrator") ;
	recordOwnerFunction(aCtx,doc.orderFunction!=null?doc.orderFunction.id:null,"direct.doctor") ;
	return map ;
}
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
	var typeEmergency = +id[0] ;
	var typeHour = id[1] ;
	var typeDate = +id[2] ;
	var typePatient = +id[3] ;
	var typeDepartment = +id[4] ;
	var dateBegin = id[5] ;
	var pigeonHole = id[6] ;
	var department = id[7] ;
	var serviceStream = id[8] ;
	var emer = "" ;
	var emerInfo = "все" ;
	var departmentFldIdSql="m.department_id",departmentFldNameSql="ml.name",departmentFldAddSql="" ;
	if (typeDepartment==2) {
		departmentFldIdSql="coalesce(slo.department_id,m.department_id)" ;
		departmentFldNameSql="coalesce(sloml.name,ml.name)" ;
		departmentFldAddSql=" and (slo.id is null or slo.transferDate is null) " ;
	}
	if (+typeEmergency==1) {
		emer=" and m.emergency='1' " ;
		emerInfo="экстренно" ;
	} else if (typeEmergency!=null && +typeEmergency==2) {
		emer=" and (m.emergency is null or m.emergency = '0')" ;
		emerInfo="планово" ;
	} 
	map.put("emerInfo",emerInfo) ;
	var addPat="",infoTypePat="" ;
	if (typePatient==3) {
		addPat= " and (ok.id is not null and ok.voc_code!='643') " ;
		infoTypePat= " иностранцам " ;
	} else if (typePatient==2){
		addPat=" and (adr.addressId is not null and adr.kladr not like '30%')   and (ok.id is null or ok.voc_code='643')" ;
		infoTypePat= " проживающим в других регионах" ;
	} else if (typePatient==1){
		addPat= " and (adr.addressId is not null and adr.kladr like '30%')   and (ok.id is null or ok.voc_code='643')" ;
		infoTypePat= " региональным пациентам" ;
	}
	var dateI = "dateStart" ;
	var timeI = "entranceTime" ;
	
	
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
	
	
	var departmentName = "все" ;
	var departmentId = "" ;
	if (department!=null
			&& +department>0) {
		departmentId = " and "+departmentFldIdSql+"='"+department+"' " ;
		var list = aCtx.manager.createNativeQuery("select vph.name,vph.id from MisLpu vph where vph.id="+department).setMaxResults(1).getResultList() ;
		if (list.size()>0) {
			var ob = list.get(0) ;
			departmentName = ob[0] ;
			
		}
	}
	map.put("departmentName",departmentName) ;
	
	var serviceStreamName = "все" ;
	var serviceStreamId = "" ;
    if (serviceStream!=null
    		&& +serviceStream>0) {
    	serviceStreamId = " and m.serviceStream_id='"+serviceStream+"' " ;
    	var list = aCtx.manager.createNativeQuery("select vph.name,vph.id from VocServiceStream vph where vph.id="+serviceStream).setMaxResults(1).getResultList() ;
		if (list.size()>0) {
			var ob = list.get(0) ;
			serviceStreamName = ob[0] ;
			
		}
    }
    map.put("serviceStreamName",serviceStreamName) ;
    
    var dateI = null ; var period = "" ;
	var timeI = null ; var period1 = "" ;
	var dateInfo = "состоящим" ;
	if (typeDate==1) {
		//aRequest.setAttribute("dateIs"," and m.dateStart between to_date('"+form.getDateBegin()+"','dd.mm.yyyy') and to_date('"+form.getDateBegin()+"','dd.mm.yyyy') ") ;
		dateI = "dateStart" ; timeI = "entranceTime" ;
		dateInfo="поступившим" ;
		groupBy = "" ;
	} else if (typeDate==2) {
		dateI = "dateFinish" ; timeI = "dischargeTime" ;
		dateInfo="выписанным" ;
	} else {
		dateI= null ; timeI = null ;
		period=" and m.dateFinish is null " ;
		period1=null ;
		dateInfo="состоящим" ;
	}
	if (typeDepartment==2) {
		dateI= null ;period1= null ;
	}
	var date = dateBegin ;
	var dat = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(date) ;
    var cal = java.util.Calendar.getInstance() ;
    cal.setTime(dat) ;
    cal.add(java.util.Calendar.DAY_OF_MONTH, 1) ;
    var format=new java.text.SimpleDateFormat("dd.MM.yyyy") ;
    var date1=format.format(cal.getTime()) ;
    
    var timeSql = null, timeInfo ="",dateInfo="";
    if (typeHour==1) {
		timeSql= "07:00" ;timeInfo="(7 часов)" ;
    } else if (typeHour==2) {
    	timeSql= "08:00" ;timeInfo="(8 часов)" ;
	} else if (typeHour==3) {
		timeSql= "09:00" ;timeInfo="(9 часов)" ;
	} 
    if (typeDate==1) {
		//aRequest.setAttribute("dateIs"," and m.dateStart between to_date('"+form.getDateBegin()+"','dd.mm.yyyy') and to_date('"+form.getDateBegin()+"','dd.mm.yyyy') ") ;
		dateI = "dateStart" ; timeI = "entranceTime" ;
		dateInfo="поступившим" ;
	} else if (typeDate==2) {
		dateI = "dateFinish" ; timeI = "dischargeTime" ;
		dateInfo="выписанным" ;
	} else {
		
		var periodF = "" ;
		periodF=periodF+" and (m.dateFinish is null ";
		periodF=periodF+" or " ;
		periodF=periodF+Packages.ru.nuzmsh.util.query.ReportParamUtil.getDateFrom(false, "m.dateFinish", "m.dischargeTime", timeSql!=null?date1:date, timeSql) ;
		periodF=periodF+" and " ;
		periodF=periodF+Packages.ru.nuzmsh.util.query.ReportParamUtil.getDateTo(false, "m.dateStart", "m.entranceTime", timeSql!=null?date:date1, timeSql) ;
		periodF=periodF+") " ;
	
		period=periodF ;
		period1=null ;
		dateInfo="состоящим" ;
	}
    if (dateI!=null) {
    	
    	period = Packages.ru.nuzmsh.util.query.ReportParamUtil.getPeriodByDate(true,false, "m."+dateI, "m."+timeI, date, date1, timeSql) ;
    	//aRequest.setAttribute("period",period) ;
    	//hourInfo=timeInfo ;
		period1=period ;
    }
    
    /*
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
    }*/
	var sqlDep = "select" 
    +" "+departmentFldIdSql+" as mlid,"+departmentFldNameSql+" as mlname"
    +" from MedCase as m  "
    +" left join StatisticStub as sc on sc.medCase_id=m.id" 
    +" left join Patient pat on m.patient_id = pat.id" 
    +" left join Omc_Oksm ok on pat.nationality_id=ok.id"
	+" left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id"
	+" left join address2 adr on adr.addressId = pat.address_addressId"
    +" left join MisLpu as ml on ml.id=m.department_id "
    +" left join VocServiceStream vss on vss.id=m.serviceStream_id"
    +" left join MedCase slo on slo.parent_id=m.id"
    +" left join MisLpu as sloml on sloml.id=slo.department_id"
    +" where m.DTYPE='HospitalMedCase' "+period+" "+departmentId+addPat
    +" and m.deniedHospitalizating_id is null"
    +"  "+emer+serviceStreamId+pigeonHoleId+departmentFldAddSql+" group by "+departmentFldIdSql+","+departmentFldNameSql+" order by "+departmentFldNameSql;
	
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
		    +" ,"+departmentFldNameSql+" as mlname,cast(m.entranceTime as varchar(5)) as entranceTime"
		    +" ,pat.id||' ('||coalesce(pat.patientSync,'         ')||')'"
		    +", case when (ok.voc_code is not null and ok.voc_code!='643') then 'ИНОСТ'"  
		    +" when (pvss.omccode='И0' or adr.kladr is not null and adr.kladr not like '30%') then 'ИНОГ' else '' end as typePatient"
		    +" , vss.name as vssname"
		    +" from MedCase as m  "
		    +" left join StatisticStub as sc on sc.medCase_id=m.id" 
		    +" left outer join Patient pat on m.patient_id = pat.id" 
		    +" left join MisLpu as ml on ml.id=m.department_id "
		    +" left join Omc_Oksm ok on pat.nationality_id=ok.id"
			+" left join VocSocialStatus pvss on pvss.id=pat.socialStatus_id"
			+" left join address2 adr on adr.addressId = pat.address_addressId"
		    +" left join MedCase slo on slo.parent_id=m.id"
		    +" left join MisLpu as sloml on sloml.id=slo.department_id"
		    +" left join VocServiceStream vss on vss.id=m.serviceStream_id"
		    +" where m.DTYPE='HospitalMedCase' "+period+" and "+departmentFldIdSql+"='"+depId+"'"
		    +" and m.deniedHospitalizating_id is null"
		    
		    +"  "+emer+pigeonHoleId+serviceStreamId+addPat+departmentFldAddSql
		    +" group by pat.lastname,pat.firstname,pat.middlename,m.id ,m.dateStart,m.entranceTime,m.dateFinish,m.dischargeTime " 
		    +" ,pat.birthday,sc.code,m.emergency ,"+departmentFldNameSql+" ,m.entranceTime ,pat.id,pat.patientSync, ok.voc_code ,pvss.omccode,adr.kladr, vss.name "
		    +" order by pat.lastname,pat.firstname,pat.middlename";
			
		var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
		var ret = new java.util.ArrayList() ;
		for (var ii=0; ii < list.size(); ii++) {
			var obj = list.get(ii) ;
		
			var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			par.set1(""+(j++)) ;
			par.set2(obj[6]) ;
			par.set3(obj[4]) ;
			par.set4(obj[10]) ;
			par.set5(obj[1]) ;
			par.set6(obj[11]) ;
			par.set7(obj[12]) ;
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
	    +" where m.DTYPE='HospitalMedCase' "+period+" "+departmentId
	    +" and m.deniedHospitalizating_id is not null"
	    +"  "+emer+pigeonHoleId1+serviceStreamId+addPat+" order by pat.lastname,pat.firstname,pat.middlename";
		
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
	var isRolePrintNewBorn = aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Journal/AddressListPrintNewBorn") ;
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
				var age = (new java.util.Date().getTime() - pat.birthday.getTime())/(3600000*24*365) ;
				if (age>1||isRolePrintNewBorn) {
					sn++ ;
					ret.add(saveInfoByPatient(pat,compilationDate,dischargeDate,sn,FORMAT_2)) ;
				}
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
	var current = new java.util.Date() ;
	var curDate = new java.sql.Date(current.getTime()) ;
	var curTime = new java.sql.Time(current.getTime()) ;
	var username = aCtx.sessionContext.callerPrincipal.name ;
	
	surOperation.setPrintDate(curDate) ;
	surOperation.setPrintTime(curTime) ;
	surOperation.setPrintUsername(username) ;
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
	var list2=aCtx.manager.createNativeQuery("select list(' '||avwf.name||' '||awp.lastname||' '||awp.firstname||' '||awp.middlename) as asist from SurgicalOperation_WorkFunction sowf"
			+" left join WorkFunction awf on awf.id=sowf.surgeonfunctions_id"
			+" left join Worker aw on aw.id=awf.worker_id"
			+" left join Patient awp on awp.id=aw.person_id"
			+" left join VocWorkFunction avwf on avwf.id=awf.workFunction_id"
			+" where sowf.surgicalOperation_id=:id group by sowf.surgicalOperation_id"
		) .setParameter("id",surOperation.id).setMaxResults(1).getResultList() ;
	var asist=list2.size()>0?list2.get(0):"" ;
	map.put("surOper.surgeonsInfo",asist) ;
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
	map.put("ant",getAntibioFromSurgicalOperation(aCtx, java.lang.Long(aParams.get("id"))));
	map.put("surOper.statisticStub",card1) ;
	return map ;
}

function getAntibioFromSurgicalOperation(aCtx, aOper) {
	var list = aCtx.manager.createNativeQuery("select vab.name||' '||round(cast (so.dose as numeric),2)" +
		" ||' '||vmd.name||cast(' в 1). ' as varchar(7))||so.firstdosetime||cast(' 2). ' as varchar(5))" +
        " ||case when so.seconddosetime is not null then cast(so.seconddosetime as varchar) else '-' end as ant" +
        " from SurgicalOperation so" +
        " left join vocmethodsdrugadm vmd on vmd.id=so.methodsdrugadm_id" +
        " left join vocantibioticdrug vab on vab.id=so.antibioticdrug_id" +
        " where so.id=" + aOper).setMaxResults(1).getResultList();
	return list.size()>0 && list.get(0)!=null? 'Антибиотикопрофилактика: ' + list.get(0) : '';
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
	var inspections = aCtx.manager.createQuery("from PregnanInspection where medCase_id="+medCase.id).getResultList() ;
	for (var i=0 ; i<inspections.size();i++) {
		var inspection = medCase.inspections.get(i) ;
		if (inspection instanceof Packages.ru.ecom.mis.ejb.domain.birth.PregnanInspection) {
			pregInspect = inspection ;
		}
	}
	map.put("pregInspect",pregInspect) ;
	recordPolicy(aCtx.manager.createQuery("from MedCaseMedPolicy where medCase=:mc").setParameter("mc", medCase).getResultList());
	recordPatient(medCase,aCtx);
	recordMedCaseDefaultInfo(medCase,aCtx) ;
	recordChildBirth(medCase, aCtx);
	var daysCount = Packages.ru.ecom.ejb.util.DurationUtil.getDurationMedCase(medCase.getDateStart(), medCase.getDateFinish(),0) ;
	map.put("sls.daysCount",daysCount) ;
	//map.put("err","");
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
	recordPolicy(aCtx.manager.createQuery("from MedCaseMedPolicy where medCase=:mc").setParameter("mc", medCase).getResultList());
	recordPatient(medCase,aCtx);
	recordMedCaseDefaultInfo(medCase,aCtx) ;
	return map ;
	
}

function recordChildBirth(medCase, aCtx) { //Случай рождения по СЛС
	var childBirthId = aCtx.manager.createNativeQuery("select id from ChildBirth where medCase_id in (select id from medcase where id = :mc or parent_id=:mc)").setParameter("mc", medCase).getResultList().get(0) ;
 
 if (childBirthId!=null) {
	 var childBirth = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.birth.ChildBirth, new java.lang.Long(+childBirthId));
 //if (childBirths.size()>0) {
	 map.put("childBirth", childBirth);
	 recordNewBorns(childBirth,aCtx);
	 
//}
 }
 return map;
}

function recordNewBorns(childBirth, aCtx) { //Рождение ребенка по случаю родов
	var newBorns = aCtx.manager.createQuery("from NewBorn where childBirth=:mc").setParameter("mc", childBirth).getResultList() ;
	if (newBorns.size()>0) {
		map.put("newBorns", newBorns);
		//for (var i=0; i<newBorns.size();i++) {
			//var newBorn = newBorns.get(i);
		//}
	}
	return map;
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
		map.put("policyInfo",rec);
		map.put("policyIs","");
	} else {
		map.put("policyInfo","");
		map.put("policyIs","Полиса НЕТ");
	}
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
	if (medCase.patient.works!=null&&!medCase.patient.works.equals("") && medCase.patient.workPost!=null && !medCase.patient.workPost.equals("")) workDiv="," ;
	map.put("pat.workDiv",workDiv) ;
	map.put("pat.wPost",medCase.patient.workPost) ;
	//Документ, удостоверяющий личность
	map.put("pat.identityCard",patient.passportType);

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
	var temperList =aCtx.manager.createQuery("from TemperatureCurve where medCase_id=:medCase").setParameter("medCase", medCase.id).getResultList()
	if(temperList.size()>0) {
		var temper= temperList.get(0) ;
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
	}else map.put("intoxication","Алкогольного-1 Наркотического-2")  ;
	//госпитализирован впервые повтороно
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
	if (medCase.roomNumber!=null) {
		var txt = medCase.roomNumber.name;
		if (medCase.roomNumber.parent!=null) {
			txt += " ("+medCase.roomNumber.parent.name+")";
		}
		map.put("sls.roomNumber",txt);
	} else {
		map.put("sls.roomNumber","___");
	}
	//map.put("sls.daysCount",medCase.getDaysCount()!=null?medCase.getDaysCount():"VFKJ");
//	var depMC = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase, new java.lang.Long(medCase.id)) ;
	//throw "DAYS = "+depMC.getDaysCount();
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
	//getDiagnos("sls.diagnosisOrder",medCase.diagnosOrder) ;
	//9. Диагноз при поступлении
	//getDiagnos("sls.diagnosisAdmission",medCase.diagnosEntrance) ;
	//10. Диагноз клинический
	//getDiagnos("sls.diagnosisClinical",medCase.diagnosClinical) ;
	//11б осложнение основного
	//getDiagnos("sls.diagnosisComplication",medCase.diagnosComplication);
	//11в сопутствующий
	//getDiagnos("sls.diagnosisConcominant",medCase.diagnosConcominant);

	map.put("sls.hospitalization",medCase.hospitalization) ;
	map.put("sls.kinsInfo",medCase.kinsman!=null?medCase.kinsman.info:"") ;
	var otds =aCtx.manager.createNativeQuery("select d.name as depname,to_char(dmc.dateStart,'DD.MM.YYYY') as dateStart,COALESCE(to_char(dmc.dateFinish,'DD.MM.YYYY'),to_char(dmc.transferDate,'DD.MM.YYYY'),'____.____.______г.') as dateFinish"
			+", coalesce(vwfd.code||' ','')||vwf.name||' '||p.lastname||' '|| p.firstname ||' '||p.middlename as worker"
			+",d.name as dname,d.id as did"
			+", coalesce(wf.code,'') as worker"
			+", case when d.IsNoOmc='1' then '1' else null end as IsNoOmc"
			+" from MedCase dmc "
			+" left join MisLpu d on d.id=dmc.department_id "
			+" left join WorkFunction wf on wf.id=dmc.ownerFunction_id "
			+" left join VocWorkFunction vwf on wf.workFunction_id=vwf.id "
			+" left join VocAcademicDegree vwfd on wf.degrees_id=vwfd.id "
			+" left join Worker w on w.id=wf.worker_id "
			+" left join Patient p on p.id=w.person_id "
			+" where dmc.parent_id='"+medCase.id+"' and dmc.DTYPE='DepartmentMedCase' order by dmc.dateStart,dmc.entranceTime ").getResultList();
		var otd = "";
		var lech = "" ;
		var lechCode = "" ;
		var lastotd = "";
		var lastotdId="" ;
		var result = medCase.result;
		for (var i=0; i<otds.size(); i++) {
			var dep = otds.get([i]) ;
			if (otd!="") {otd += ", ";}
			otd += dep[0] +" "+" c "+dep[1]+" по "+dep[2] ;
			
			if (result!=null && (result.code=="11" || result.code=="15")) {
				if (dep[7]==null) {
					lech = dep[3] ;
					lastotd = dep[4];
					lastotdId = dep[5] ;
					lechCode=dep[6] ;
				}
			} else{
				lech = dep[3] ;
				lastotd = dep[4];
				lastotdId = dep[5] ;
				lechCode=dep[6] ;
			}
		}
		map.put("sls.departments",otd) ;
		map.put("sls.owner",lech) ;
		map.put("sls.ownercode",lechCode) ;
		map.put("sls.lastOtd",lastotd) ;
		recordZavOtd(aCtx,lastotdId,"dep.zav") ;
		slsId = medCase.id ;
		recordDiagnosis(aCtx,slsId,"2","0","diagnosis.order.main") ;
		recordDiagnosis(aCtx,slsId,"1","1","diagnosis.admission.main") ;
		if (medCase.deniedHospitalizating==null) {
			recordDiagnosis(aCtx,slsId,"3","1","diagnosis.clinic.main") ;
			recordDiagnosis(aCtx,slsId,"3","3","diagnosis.clinic.related") ;
			recordDiagnosis(aCtx,slsId,"3","4","diagnosis.clinic.complication") ;
			map.put("ambtype","СТАЦИОНАРНОГО") ;
		} else {
            //milamesher 1105 если есть клинический, беру его, нет - беру направительный
			recordDiagnosis(aCtx,slsId,"4","1","diagnosis.clinic.main") ;
			if (map.get("diagnosis.clinic.main.mkb")==null || map.get("diagnosis.clinic.main.mkb").equals(""))
                recordDiagnosis(aCtx, slsId, "1", "1", "diagnosis.clinic.main");
			recordDiagnosis(aCtx,slsId,"1","3","diagnosis.clinic.related") ;
			recordDiagnosis(aCtx,slsId,"1","4","diagnosis.clinic.complication") ;
			map.put("ambtype","АМБУЛАТОРНОГО") ;
		}
		recordDiagnosis(aCtx,slsId,"5","1","diagnosis.postmortem.main") ;
		recordDiagnosis(aCtx,slsId,"5","3","diagnosis.postmortem.related") ;
		recordDiagnosis(aCtx,slsId,"4","1","diagnosis.clinical.main");
		recordDiagnosis(aCtx,slsId,"5","4","diagnosis.postmortem.complication") ;
		recordDisability(aCtx,slsId,"dis") ;
		var dc =aCtx.manager.createQuery("from DeathCase where medCase_id="+medCase.id).getResultList() ;
		if (dc.size()>0) {
			map.put("dc.deathCase",dc.get(0)) ;
		}
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
function recordOwnerFunction(aCtx,aOwnerFunctiondId,aField) {
	if (+aOwnerFunctiondId>0) {
		var sql = "select p.lastname||' '||p.firstname||' '||p.middlename,wf.id,p.lastname " 
			+" from workfunction wf " 
			+" left join worker w on w.id=wf.worker_id"
			+" left join patient p on p.id=w.person_id"
			+" where wf.id='"+aOwnerFunctiondId+"'" ;
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
	var params=[["consent",10],["direct",3],["rejection",5], ["hystology",2]] ;
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
	recordPolicy(aCtx.manager.createQuery("from MedCaseMedPolicy where medCase=:mc").setParameter("mc", medCase.parent).getResultList()) ;
	return map ;
}
function checkAllDiagnosis (aCtx, aSlsId) {
	if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/DontPrintWithoutDiagnosisInSlo")){
		var sql = "select sls.id,slo.id from medcase sls "
		 +" left join medcase slo on sls.id=slo.parent_id and slo.dtype='DepartmentMedCase'"
		 +" left join mislpu ml on ml.id=slo.department_id"
		 +" left join diagnosis d on slo.id = d.medcase_id"
		 +" left join vocdiagnosisregistrationtype vdrt on vdrt.id=d.registrationtype_id"
		 +" where sls.id='"+aSlsId+"' and (ml.isnoomc is null or ml.isnoomc='0') "
		 +" group by sls.id,slo.id	"
		 +" having count(case when (vdrt.code='3' or vdrt.code='4') and d.idc10_id is not null then 1 else null end)=0  "
		var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
		if (list.size()>0) {throw "Не полностью заполнены данные по диагнозам в отделениях!!!" ;}	
	}
}
function printStatCardInfo(aCtx, aParams) {
	var slsId=aParams.get("id") ;
	var check=aParams.get("check") ;
	var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase
		, new java.lang.Long(slsId)) ;
	if (+check>0) checkAllDiagnosis (aCtx, slsId) ;
	if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Discharge/NoPrintDischargeWithoutDischargeTime")
	 	&& null==medCase.dischargeTime) {
		throw ("Необходимо заполнить время выписки");
	}
	recordPolicy(aCtx.manager.createQuery("from MedCaseMedPolicy where medCase=:mc").setParameter("mc", medCase).getResultList()) ;
	recordPatient(medCase,aCtx) ;
	recordMedCaseDefaultInfo(medCase,aCtx) ;
	recordAttendant(medCase,aCtx) ;
	
	
	var depDirect = "" ;
	var hospType = medCase.hospType ;
	var daysCount = Packages.ru.ecom.ejb.util.DurationUtil.getDurationMedCase(medCase.getDateStart(), medCase.getDateFinish(),0) ;
	if (hospType!=null) {
		if (hospType.code=="DAYTIMEHOSP") {
			depDirect = "(дневное)" ;
			daysCount = Packages.ru.ecom.ejb.util.DurationUtil.getDurationMedCase(medCase.getDateStart(), medCase.getDateFinish(),0,1) ;
			
		}
	}
	map.put("sls.daysCount",daysCount) ;
	map.put("sls.departmentDirection",medCase.department) ;
	map.put("sls.typeDirect",depDirect) ;
	map.put("sls.outcome",medCase.outcome) ;
	map.put("sls.result",medCase.result) ;
	//if (medCase.diagnosClinical.establishDate.equals("")){
	//map.put("diag.establishDate", "1") ;}
	//else {
	//map.put("diag.establishDate",medCase.diagnosClinical.establishDate);
	//}
	
	//recordVocProba("sls.hosp",medCase.hospitalization,1,2);
	//7. Доставлен по экстренным показания
	toBeOrNotToBe("sls.dostavlen",medCase.emergency) ;
	// через_________часов после начала заболевания, получения травмы; госпитализирован в плановом порядке
	map.put("sls.preAdmissionTime",medCase.preAdmissionTime) ;
	//10. Диагноз клинический
	getDiagnos("sls.diagnosisClinical",medCase.diagnosClinical) ;
	recordSloBySls(aCtx,slsId,"listSlo") ;
	recordSurgicalOperationBySls(aCtx,slsId,"listOper") ;
    printDefaultLpuRequisites(aCtx,"DefaultLpu");

    return map ;
}
function recordDisability(aContext,aSlsId,aField) {
	var sql="select dc.id,dd.id,dd.number,to_char(min(dr.dateFrom),'dd.mm.yyyy') as dateFrom,to_char(max(dr.dateTo),'dd.mm.yyyy') as dateTo,vddcr.name as vddcrname"
		+" ,vddt.name as vddtname from DisabilityCase dc"
		+" 	left join Patient pat on pat.id=dc.patient_id"
		+" 	left join MedCase sls on sls.patient_id=pat.id"
		+" 	left join DisabilityDocument dd on dd.disabilityCase_id=dc.id"
		+" 	left join DisabilityRecord dr on dr.disabilityDocument_id=dd.id"
		+" 	left join VocDisabilityDocumentCloseReason vddcr on dd.closeReason_id=vddcr.id"
		+" 	left join VocDisabilityDocumentType vddt on dd.documentType_id=vddt.id"
		+" 	where sls.id='"+aSlsId+"' and dd.anotherlpu_id is null"
		+" 	group by dc.id,dd.id,sls.dateStart,sls.dateFinish,dd.number,vddcr.name,dd.issueDate,vddt.name"
		+" 	having min(dr.dateFrom) between sls.dateStart and coalesce(sls.dateFinish,current_date)"
		+"  order by dd.issueDate"
	var list = aContext.manager.createNativeQuery(sql).getResultList() ;
	if (list.size()>0) {
		var obj=list.get(0) ;
		map.put(aField+".info","№"+obj[2]+" открыт с "+obj[3]+" по "+obj[4]+". Причина закрытия: "+obj[5]);
		map.put(aField+".age",null);
		map.put(aField+".sex",null);
		map.put(aField+".type",obj[6]);
		var ddinfo="" ;
		for (var i=0;i<list.size();i++){
			var obj1=list.get(i) ;
			ddinfo=ddinfo+obj1[6]+" №"+obj1[2]+" открыт с "+obj1[3]+" по "+obj1[4]+". Причина закрытия: "+obj1[5];//aField+".info",
			if (i+1<list.size()) ddinfo=ddinfo+", ";
		}
		map.put(aField+".all",ddinfo);
	} else {
		map.put(aField+".info",null);
		map.put(aField+".age",null);
		map.put(aField+".sex",null);
		map.put(aField+".all",null);
		map.put(aField+".type",null);
	}
}

function recordDiagnosis(aCtx,aSlsId,aRegistrationType,aPriority,aField,aDtype) {
	if (aDtype==null || aDtype=='') aDtype='HospitalMedCase' ;
	var prioritySql="" ;
	if (+aPriority>0) {prioritySql=" and vpd.code='"+aPriority+"' "}
	
	var sql="select sls.id,list(case when vdrt.code='"+aRegistrationType+"' "+prioritySql+"  then mkb.code else null end) as diagCode"
		+" ,list(case when vdrt.code='"+aRegistrationType+"' "+prioritySql+" then diag.name else null end) as diagText" 
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
		+" left join SurgComplication sc on so.id=sc.surgicaloperation_id"
		+" left join VocComplication vc on vc.id=sc.complication_id"
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
	var ret = new java.util.ArrayList() ;
	var ret1 = new java.util.ArrayList() ;
	
	var wq = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	ret1.add(wq) ;
	if (medCase.result!=null&& (medCase.result.code=="11" || medCase.result.code=="15")) {
		map.put("title.death",ret1) ;
		map.put("title.normal",ret) ;
	} else {
		map.put("title.death",ret) ;
		map.put("title.normal",ret1) ;
	}
	//Свединия по пациенту	
	recordPatient(medCase,aCtx) ;
	//Диагнозы
	recordMedCaseDefaultInfo(medCase,aCtx) ;
	map.put("sls.emergency", ((medCase.emergency!=null && medCase.emergency) ? "в экстренном порядке":"в плановом порядке")) ;
	
	//5. Даты: поступления, выбытия
	map.put("sls.Start",medCase.dateStart) ;
	map.put("sls.Finish",medCase.dateFinish) ;
	//выписной диагноз
	if (medCase.deniedHospitalizating==null) {
		//getDiagnos("sls.diagnosConcluding",medCase.diagnosConcluding);
		recordMultiText("sls.dischargeRecord",Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.HospitalMedCaseViewInterceptor.getDischargeEpicrisis(medCase.getId(), aCtx.manager));
	} else {
		var list_prot = aCtx.manager.createNativeQuery("select upper(to_char(d.dateregistration,'dd.mm.yyyyy')||' '||cast(d.timeregistration as varchar(5)) ||' '||vwf.name||' '||pat.lastname),d.record from diary d left join WorkFunction wf on wf.id=d.specialist_id left join worker w on w.id=wf.worker_id left join patient pat on pat.id=w.person_id left join vocworkfunction vwf on vwf.id=wf.workfunction_id left join medcase m on m.id=d.medcase_id where m.id="+medCase.id+" or (m.patient_id="+medCase.patient.id+" and m.dateStart-to_date('"+medCase.dateStart+"','yyyy-mm-dd') between 0 and 1) order by d.dateregistration,d.timeregistration").getResultList() ;
		
		var text = "" ;
		for (var i = 0; i< list_prot.size();i++) {
			text += list_prot.get(i)[0] ;
			text += '\n' ;
			text += list_prot.get(i)[1] ;
			text += '\n\n' ;
		}
		recordMultiText("sls.dischargeRecord",text);
	}
	//текущая дата
	var currentDate = new Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	//Milamesher #137 вывод кардиоскринингов
	var listDatesScreening=aCtx.manager.createNativeQuery("select \n" +
        "case when (scrI.createdate is not null and scrII.createdate is not null) then\n" +
        "cast('Кардиоскрининг был проведён ' as varchar(36))||to_char(scrI.createdate,'dd.mm.yyyy')||' (I этап) и '||to_char(scrII.createdate,'dd.mm.yyyy')||' (II этап). '||coalesce(scrII.conclusion,'')\n" +
        "else case when scrI.createdate is not null then\n" +
        "cast('Кардиоскрининг был проведён ' as varchar(36))||to_char(scrI.createdate,'dd.mm.yyyy')||' (I этап)'\n" +
        "else case when scrII.createdate is not null then\n" +
        "cast('Кардиоскрининг был проведён ' as varchar(36))||to_char(scrII.createdate,'dd.mm.yyyy')||' (II этап). '||coalesce(scrII.conclusion,'')\n" +
        "end end end as s\n" +
        "from medcase sls\n" +
        "left join mislpu lpu on lpu.id=sls.department_id\n" +
        "left join medcase slo on slo.parent_id=sls.id\n" +
        "left join medcase allslo on allslo.parent_id=sls.id\n" +
        "left join mislpu lpuslo on lpuslo.id=slo.department_id\n" +
        "left join screeningcardiac scrI on scrI.medcase_id=slo.id and scrI.dtype='ScreeningCardiacFirst'\n" +
        "left join screeningcardiac scrII on scrII.medcase_id=slo.id and scrII.dtype='ScreeningCardiacSecond'\n" +
        "where lpu.IsCreateCardiacScreening=true and lpuslo.IsCreateCardiacScreening=true\n" +
        "and sls.dtype='HospitalMedCase' and slo.dtype='DepartmentMedCase' and allslo.dtype='DepartmentMedCase'\n" +
        "group by sls.id,scrI.createdate,scrII.createdate,scrii.conclusion\n" +
        "having count(distinct allslo.id)=1 and sls.id="+id).getResultList();
    if (listDatesScreening.size()>0) map.put("cardiascreen",(listDatesScreening.get(0)!=null)? "\n"+listDatesScreening.get(0):"") ;
    else  map.put("cardiascreen","") ;
	return map ;
}

function getCode(aKey, aValue)
{
	if(aValue!=null) map.put("aKey",aValue.code) ;
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
	var protocol = aCtx.manager.find(Packages.ru.ecom.poly.ejb.domain.protocol.Protocol
		, new java.lang.Long(aParams.get("id"))) ;
	var medCase = protocol.medCase ;
	var current = new java.util.Date() ;
	var curDate = new java.sql.Date(current.getTime()) ;
	var curTime = new java.sql.Time(current.getTime()) ;
	protocol.setPrintDate(curDate) ;
	protocol.setPrintTime(curTime) ;	
	map.put("prot.date",protocol.dateRegistration);
	map.put("prot.time",protocol.timeRegistration);
	//Возраст (полных лет, для детей: до 1 года - месяцев, до 1 месяца - дней)
	if (medCase.patient!=null&&protocol.dateRegistration!=null) {
		getAge("prot.age",medCase.patient.birthday,protocol.dateRegistration,aCtx.manager) ;
	} else {
		map.put("prot.age","") ;
	}
	map.put("protocol",protocol);
	map.put("prot.spec",protocol.specialistInfo);
	map.put("medCase.info",protocol.medCase.info) ;
	recordMultiText("prot.rec", protocol.record) ;
	map.put("prot.title",recordMultiValue(protocol.title));
	map.put("drugs",new java.util.ArrayList()) ;
	var protType=protocol.type ;
	if (protType!=null) {
		map.put("typeInfo",protType.name) ;
		//var protType = protType.isPrintAdministrator ;
		map.put("ticket",protType.isPrintAdministrator==true?java.lang.Long.valueOf(0):null) ;
	} else {
		map.put("ticket",null) ;
		map.put("typeInfo",null) ;
	}
	recordDiagnosis(aCtx,medCase.id,"4","1","diag_cl") ;
	recordDiagnosis(aCtx,medCase.id,"3","1","diag") ;
	recordDiagnosis(aCtx,medCase.id,"1","1","diag_order") ;
	return map ;
}
function printEdkcProtocol (aCtx,aParams){
    var protocol = aCtx.manager.find(Packages.ru.ecom.poly.ejb.domain.protocol.Protocol
        , new java.lang.Long(aParams.get("id"))) ;
    var obsSheet = protocol.obsSheet ;
    var current = new java.util.Date() ;
    var curDate = new java.sql.Date(current.getTime()) ;
    var curTime = new java.sql.Time(current.getTime()) ;
    protocol.setPrintDate(curDate) ;
    protocol.setPrintTime(curTime) ;
    map.put("prot.date",protocol.dateRegistration);
    map.put("prot.time",protocol.timeRegistration);
    //Возраст (полных лет, для детей: до 1 года - месяцев, до 1 месяца - дней)
    if (obsSheet.patient!=null&&protocol.dateRegistration!=null) {
        getAge("prot.age",obsSheet.patient.birthday,protocol.dateRegistration,aCtx.manager) ;
    } else {
        map.put("prot.age","") ;
    }
    map.put("protocol",protocol);
    map.put("prot.spec",protocol.specialistInfo);
    map.put("medCase.info","") ;
    recordMultiText("prot.rec", protocol.record) ;
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

function recordAge(aKey,aDateBegin,aDateEnd,aIsYear,aIsMonth,aIsDay) {
	if (aDateBegin!=null && aDateEnd!=null) {
		var calenB = java.util.Calendar.getInstance() ;
		calenB.setTime(aDateBegin) ;
		var calenE = java.util.Calendar.getInstance() ;
		calenE.setTime(aDateEnd) ;
		if (aIsDay!=null && aIsDay==1) {
			map.put(aKey+".cntday",""+(calenE.get(java.util.Calendar.DAY_OF_MONTH)-calenB.get(java.util.Calendar.DAY_OF_MONTH)+1)) ;
		}
		if (aIsMonth!=null && aIsMonth==1) {
			map.put(aKey+".cntmonth",""+(calenE.get(java.util.Calendar.MONTH)-calenB.get(java.util.Calendar.MONTH))) ;
		}
		if (aIsYear!=null && aIsYear==1) {
			map.put(aKey+".cntyear",""+(calenE.get(java.util.Calendar.YEAR)-calenB.get(java.util.Calendar.YEAR))) ;
		}
	} else {
		map.put(aKey+".cntday","");
		map.put(aKey+".cntmonth","");
		map.put(aKey+".cntyear","");
	}
}
function recordDuration(aDateBegin,aDateEnd,aAddCount) {
	try {
		var calenB = java.util.Calendar.getInstance() ;
		calenB.setTime(aDateBegin) ;
		
		var calenE = java.util.Calendar.getInstance() ;
		calenE.setTime(aDateEnd) ;
		return ""+(( (calenE.getTime().getTime()-calenB.getTime().getTime())/(1000*60*60*24))+aAddCount) ;
	} catch(e) {
		return "" ;
	}
}

//Получить критерии для запроса
function getCritList(aCtx,estimationKind) {
	return aCtx.manager.createNativeQuery("select id,code,name from vocqualityestimationcrit where kind_id="+estimationKind+" and parent_id is null order by code").getResultList();
}

function getSqlAdd(typeMarks,department,workFunction,expert) {
    var sqlAdd="";
    if (typeMarks!=null && typeMarks!="") {
        if (typeMarks=="1") {
            sqlAdd+=" and qe.experttype='Expert'";
        } else if (typeMarks=="2") {
            sqlAdd+=" and qe.experttype='BranchManager'";
        }
    }
    if (department!=null && department!="") {
        sqlAdd=sqlAdd+" and wml.id='"+department+"'";
    }
    if (workFunction!=null && workFunction!="") {
        sqlAdd=sqlAdd+" and qec.doctorcase_id='"+workFunction+"'";
    }
    if (expert!=null && expert!="") {
        sqlAdd=sqlAdd+" and qe.expert_id='"+expert+"'";
    }
    return sqlAdd;
}

//реестр по пациентам
function getQuarterlyReportPatientsList(aCtx,estimationKind,department,workFunction,expert,dateBegin,dateEnd,typeMarks,typeOrder) {
    var critList = getCritList(aCtx,estimationKind);
    var critSql="";
    for (var i=0;i<critList.size();i++) {
        critSql+=",max(case when vqec.id = '";
        critSql+=critList.get(i)[0];
        critSql+="' then (vqem.mark||' '||(select list(vqecd.name) from qualityestimationcritdefect qecd left join vocqualityestimationcritdefect vqecd on vqecd.id=qecd.defect where qecd.criterion=qecr.id ))  else ''||0 end) as f";
        critSql+=i+5;
        critSql+="_def1";
    }

   var sqlAdd=getSqlAdd(typeMarks,department,workFunction,expert);

    var orderBySql="";
    if (typeOrder!=null && typeOrder!="") {
        if (typeOrder=="1") {
            orderBySql+=" pat.patientinfo";
        } else if (typeOrder=="2") {
            orderBySql+=" qec.createdate";
        }
    }
    var sql="select qec.id" +
        " ,to_char(qec.createdate,'dd.MM.yyyy') as f1_createDate" +
        " ,vwf.name ||' '||wpat.lastname ||' ' || wpat.firstname||' '||wpat.middlename ||' '|| \twml.name as f2_dep_doctor" +
        " ,pat.patientinfo ||' (№'||coalesce(ss.code,pat.patientSync)||')' as f3_patient" +
        " ,mkb.code as f4_diagnosis" +
        critSql +
        " ,round(cast(sum (vqem.mark)/count(vqec.id) as numeric),2) as f5_average" +
        " from qualityestimationcard qec" +
        " left join vocidc10 mkb on mkb.id=qec.idc10_id" +
        " left join workfunction wf on wf.id=qec.doctorcase_id" +
        " left join worker w on w.id=wf.worker_id" +
        " left join mislpu wml on wml.id=w.lpu_id" +
        " left join patient wpat on wpat.id=w.person_id" +
        " left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
        " left join qualityestimation qe on qe.card_id=qec.id" +
        " left join qualityestimationcrit qecr on qecr.estimation_id=qe.id" +
        " left join vocqualityestimationmark vqem on vqem.id=qecr.mark_id" +
        " left join vocqualityestimationcrit vqec on vqec.kind_id = qec.kind_id and vqec.id=qecr.criterion_id  " +
        " left join medcase sls on sls.id=qec.medcase_id" +
        " left join medcase sls2 on sls2.id=sls.parent_id" +
        " left join patient pat on pat.id=sls.patient_id" +
        " left join statisticstub ss on ss.medcase_id=coalesce(sls2.id,sls.id)" +
        " where  qec.createDate between to_date('"+dateBegin+"','dd.MM.yyyy') and to_date('"+dateEnd+"','dd.MM.yyyy')" +
        " and qec.kind_id=" + estimationKind +
        sqlAdd  +
        " group by qec.id,qec.createdate ,wml.name ,vwf.name,wpat.lastname, wpat.firstname,wpat.middlename ,ss.code,pat.patientinfo ,mkb.code,pat.patientSync" +
        " order by " + orderBySql;
    var list=aCtx.manager.createNativeQuery(sql).getResultList() ;
    var patientsList = new java.util.ArrayList() ;
    for (var i=0;i<list.size();i++) {
        var o = list.get(i);
        var oo = new java.util.ArrayList();
        var f1_createDate = unNull(o[1]);
        var f2_dep_doctor = unNull(o[2]);
        var f3_patient = unNull(o[3]);
        var f4_diagnosis = unNull(o[4]);
        var f5_def1 = unNull(o[5]);
        var f6_def1 = unNull(o[6]);
        var f7_def1 = unNull(o[7]);
        var f8_def1 = unNull(o[8]);
        var f9_def1 = unNull(o[9]);
        var f10_def1 = unNull(o[10]);
        var f11_def1 = unNull(o[11]);
        var f12_def1 = unNull(o[12]);
        var f13_def1 = unNull(o[13]);
        var f14_def1 = unNull(o[14]);
        var f5_average = unNull(o[15]);

        oo.add(i+1+'.');
        oo.add(f1_createDate);
        oo.add(f2_dep_doctor);
        oo.add(f3_patient);
        oo.add(f4_diagnosis);
        oo.add(f5_def1);
        oo.add(f6_def1);
        oo.add(f7_def1);
        oo.add(f8_def1);
        oo.add(f9_def1);
        oo.add(f10_def1);
        oo.add(f11_def1);
        oo.add(f12_def1);
        oo.add(f13_def1);
        oo.add(f14_def1);
        oo.add(f5_average);
        patientsList.add(oo);
    }
    return patientsList;
}
//реестр по лечащему врачу (typeReport 2), по отделению (3), по эксперту (4)
function getQuarterlyReportTypeReport235List(aCtx,estimationKind,department,workFunction,expert,dateBegin,dateEnd,typeMarks,typeOrder,typeReport) {
    var critSql="";
    var critList = getCritList(aCtx,estimationKind);
    for (var i=0;i<critList.size();i++) {
        critSql+=",round(cast(avg(case when vqec.id = '";
        critSql+=critList.get(i)[0];
        critSql+="' then vqem.mark else null end) as numeric),2) as f";
        critSql+=(i+5);
        critSql+="_def1";
    }

    var groupBy="";
    var nameFldId="";
    var nameFld="";
    var orderBySql="";
    if (typeReport=="2") { //Группировка по врачу
        groupBy="wf.id,wml.name ,vwf.name,wpat.lastname, wpat.firstname,wpat.middlename";
        nameFldId="wf.id";
        nameFld="vwf.name ||' '||wpat.lastname ||' ' || wpat.firstname||' '||wpat.middlename ||' '|| 	wml.name";
        orderBySql="wpat.lastname, wpat.firstname,wpat.middlename";
    } else if (typeReport=="3") {  //Группировка по отделению
        groupBy="wml.id,wml.name ";
        nameFldId="wml.id";
        nameFld="wml.name";
        orderBySql="wml.name";
    } else if (typeReport=="5") { //Группировка по эксперту
        groupBy="wfExp.id, vwfExp.name, patExp.lastname, patExp.firstname, patExp.middlename";
        nameFldId=" wfExp.id";
        nameFld="vwfExp.name||' '||patExp.lastname||' '|| patExp.firstname ||' '|| patExp.middlename";
        orderBySql="patExp.lastname, patExp.firstname, patExp.middlename";
    }

    var sqlAdd=getSqlAdd(typeMarks,department,workFunction,expert);

    var sql="select " + nameFldId + " , " + nameFld + " as f2_dep_doctor" +
		" ,count(distinct qe.id) as f3_cntExp " +
		critSql +
		" ,round(cast(sum (vqem.mark)/count(vqec.id) as numeric),2) as f5_average" +
        " from qualityestimationcard qec" +
        " left join vocidc10 mkb on mkb.id=qec.idc10_id" +
        " left join workfunction wf on wf.id=qec.doctorcase_id" +
        " left join worker w on w.id=wf.worker_id" +
        " left join mislpu wml on wml.id=w.lpu_id" +
        " left join patient wpat on wpat.id=w.person_id" +
        " left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
        " left join qualityestimation qe on qe.card_id=qec.id" +
        " left join workfunction wfExp on wfExp.id=qe.expert_id" +
        " left join worker wExp on wExp.id=wfExp.worker_id" +
        " left join vocworkfunction vwfExp on vwfExp.id=wfExp.workfunction_id" +
        " left join patient patExp on patExp.id=wExp.person_id" +
        " left join qualityestimationcrit qecr on qecr.estimation_id=qe.id" +
        " left join vocqualityestimationmark vqem on vqem.id=qecr.mark_id" +
        " left join vocqualityestimationcrit vqec on vqec.kind_id = qec.kind_id and vqec.id=qecr.criterion_id  " +
        " left join medcase sls on sls.id=qec.medcase_id" +
        " left join medcase sls2 on sls2.id=sls.parent_id" +
        " left join patient pat on pat.id=sls.patient_id" +
        " left join statisticstub ss on ss.medcase_id=coalesce(sls2.id,sls.id)" +
		" where  qec.createDate between to_date('" + dateBegin + "','dd.MM.yyyy') and to_date('" + dateEnd + "','dd.MM.yyyy')" +
		" and qec.kind_id='" + estimationKind + "'" +
		sqlAdd +
		" group by " + groupBy +
		" order by " + orderBySql;
    var list=aCtx.manager.createNativeQuery(sql).getResultList() ;
    var resList = new java.util.ArrayList() ;
    for (var i=0;i<list.size();i++) {
        var o = list.get(i);
        var rr = new java.util.ArrayList();
        var f1 = unNull(o[1]);
        var f2 = unNull(o[2]);
        var f3 = unNull(o[3]);
        var f4 = unNull(o[4]);
        var f5_def1 = unNull(o[5]);
        var f6_def1 = unNull(o[6]);
        var f7_def1 = unNull(o[7]);
        var f8_def1 = unNull(o[8]);
        var f9_def1 = unNull(o[9]);
        var f10_def1 = unNull(o[10]);
        var f11_def1 = unNull(o[11]);
        var f12_def1 = unNull(o[12]);
        var f13_def1 = unNull(o[13]);
        var f14_def1 = unNull(o[14]);
        var f5_average = unNull(o[15]);

        rr.add(i+1+'.');
        rr.add(f1);
        rr.add(f2);
        rr.add(f3);
        rr.add(f4);
        rr.add(f5_def1);
        rr.add(f6_def1);
        rr.add(f7_def1);
        rr.add(f8_def1);
        rr.add(f9_def1);
        rr.add(f10_def1);
        rr.add(f11_def1);
        rr.add(f12_def1);
        rr.add(f13_def1);
        rr.add(f14_def1);
        rr.add(f5_average);
        resList.add(rr);
    }
    return resList;
}

//Получить кол-во проведённых экспертиз и без дефектов
function getCountCards(aCtx,estimationKind,department,workFunction,expert,dateBegin,dateEnd,typeMarks) {
    var sqlAdd=getSqlAdd(typeMarks,department,workFunction,expert);
	var sql="select count(distinct qe.id) as f1_cntExp" +
        " , count(distinct qe.id) - count(distinct case when vqem.mark='1' then null else qe.id end) as f4_cntExp" +
        " from qualityestimationcard qec" +
        " left join vocidc10 mkb on mkb.id=qec.idc10_id" +
        " left join workfunction wf on wf.id=qec.doctorcase_id" +
        " left join worker w on w.id=wf.worker_id" +
        " left join mislpu wml on wml.id=w.lpu_id" +
        " left join patient wpat on wpat.id=w.person_id" +
        " left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
        " left join qualityestimation qe on qe.card_id=qec.id" +
        " left join qualityestimationcrit qecr on qecr.estimation_id=qe.id" +
        " left join vocqualityestimationmark vqem on vqem.id=qecr.mark_id" +
        " left join vocqualityestimationcrit vqec on vqec.kind_id = qec.kind_id and vqec.id=qecr.criterion_id  " +
        " left join medcase sls on sls.id=qec.medcase_id" +
        " left join medcase sls2 on sls2.id=sls.parent_id" +
        " left join patient pat on pat.id=sls.patient_id" +
        " left join statisticstub ss on ss.medcase_id=coalesce(sls2.id,sls.id)" +
        " where  qec.createDate between to_date('"+dateBegin+"','dd.MM.yyyy') and to_date('"+dateEnd+"','dd.MM.yyyy')" +
        " and qec.kind_id='"+estimationKind+"'" +
        sqlAdd;
    var list=aCtx.manager.createNativeQuery(sql).getResultList() ;
    var res = "";
    for (var i=0;i<list.size();i++) {
        var o = list.get(i);
        var f1 = unNull(o[0]);
        var f2 = unNull(o[1]);
        res=f1+"#"+f2;
    }
    return res;
}

//Получить кол-во проведённых экспертиз и без дефектов
function getQuarterlyReportTypeReportIndicators(aCtx,estimationKind,department,workFunction,expert,dateBegin,dateEnd,typeMarks,total,nodef) {
    var sqlAdd=getSqlAdd(typeMarks,department,workFunction,expert);
    var sql="select vqec.name as f3_name_crit" +
        " ,count(distinct case when vqem.mark='1' then null else qec.id end) as f4_cntExp" +
		" ,replace(selectalldefectspercent(cast(vqec.id as integer),"+total+",cast(wml.id as integer),'"+dateBegin+"','"+dateEnd+"',"+estimationKind+"),'.00','') as f5_cntExp" +
        " ,replace(cast (round(cast(avg (vqem.mark) as numeric),2) as varchar),'.00','') as f6_average" +
        " ,replace(count(distinct case when vqem.mark='1' then null else qec.id end)" +
        " ||case when count(distinct case when vqem.mark='1' then null else qec.id end)>0 then" +
        " ' ('||(round(cast(100.0*count(distinct case when vqem.mark='1' then null else qec.id end)/'"+total+"' as numeric),2))||'%)'  else '' end,'.00','') as allpers" +
        " from qualityestimationcard qec" +
        " left join vocidc10 mkb on mkb.id=qec.idc10_id" +
        " left join workfunction wf on wf.id=qec.doctorcase_id" +
        " left join worker w on w.id=wf.worker_id" +
        " left join mislpu wml on wml.id=w.lpu_id" +
        " left join patient wpat on wpat.id=w.person_id" +
        " left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
        " left join qualityestimation qe on qe.card_id=qec.id" +
        " left join qualityestimationcrit qecr on qecr.estimation_id=qe.id" +
        " left join vocqualityestimationmark vqem on vqem.id=qecr.mark_id" +
        " left join vocqualityestimationcrit vqec on vqec.kind_id = qec.kind_id and vqec.id=qecr.criterion_id  " +
        " left join medcase sls on sls.id=qec.medcase_id" +
        " left join medcase sls2 on sls2.id=sls.parent_id" +
        " left join patient pat on pat.id=sls.patient_id" +
        " left join statisticstub ss on ss.medcase_id=coalesce(sls2.id,sls.id)" +
        " where  qec.createDate between to_date('"+dateBegin+"','dd.MM.yyyy') and to_date('"+dateEnd+"','dd.MM.yyyy')" +
        " and qec.kind_id='"+estimationKind+"'" +
        sqlAdd +
		" group by vqec.id ,vqec.code,vqec.name,wml.id" +
        " order by vqec.code";
    var list=aCtx.manager.createNativeQuery(sql).getResultList() ;
    var resList = new java.util.ArrayList() ;
    for (var i=0;i<list.size();i++) {
        var o = list.get(i);
        var rr = new java.util.ArrayList();
        var f0 = unNull(o[0]);
        var f1 = unNull(o[1]);
        var f2 = unNull(o[2]);
        var f3 = unNull(o[3]);
        var f4 = unNull(o[4]);

        rr.add(i+1+'.');
        rr.add(f0);
        rr.add(f1);
        rr.add(f2);
        rr.add(f3);
        rr.add(f4);
        resList.add(rr);
    }
    return resList;
}

//получить квартал, считается по дате начала
function getQuarter(dateBegin) {
	var q='';
	var month=+dateBegin.substring(3,5);
	if (month<=3)
		q='I'; //01 02 03
    else if (month>3 && month<=6)
        q='II'; //04 05 06
    else if (month>6 && month<=9)
        q='III'; //07 08 09
    else if (month>9 && month<=12)
        q='IV'; //10 11 12
	return q;

}
//получить год
function getYear(dateBegin) {
	return +dateBegin.substring(6);
}
//получить общее количество дефектов
function getTotalDefects(indicList) {
    var sum=0;
    for (var i=0; i<indicList.size(); i++) {
        sum+=+indicList.get(i).get(2);
    }
    return (''+sum).replace('.0','');
}
//получить процент показателя качества
function getDefectPercent(list,pos) {
    var sum=0;
    for (var i=0; i<list.size(); i++) {
        sum+=+list.get(i).get(pos);
    }
    return (sum/list.size()).toFixed(2);
}
function printQuarterlyReport(aCtx, aParams) {
    var estimationKind = aParams.get("estimationKind") ;
    var department = aParams.get("department") ;
    var workFunction = aParams.get("workFunction") ;
    var expert = aParams.get("expert") ;
    var dateBegin = aParams.get("dateBegin") ;
    var dateEnd = aParams.get("dateEnd") ;
    var typeMarks = aParams.get("typeMarks") ;
    var typeOrder = aParams.get("typeOrder") ;
    var depname = aParams.get("departmentName") ;

    map.put('depname',depname.replace('.    ',''));
    map.put('dateBegin',dateBegin);
    map.put('dateEnd',dateEnd);
    map.put('qy',getQuarter(dateBegin)+' квартал ' + getYear(dateBegin) + ' г.');

	map.put('patientsList',getQuarterlyReportPatientsList(aCtx,estimationKind,department,workFunction,expert,dateBegin,dateEnd,typeMarks,typeOrder));
	var doctorsList = getQuarterlyReportTypeReport235List(aCtx,estimationKind,department,workFunction,expert,dateBegin,dateEnd,typeMarks,typeOrder,2);
    map.put('doctorsList', doctorsList);
    for (var i=1; i<12; i++) {
        map.put('dd'+i, getDefectPercent(doctorsList,i+2));
	}

    map.put('depList',getQuarterlyReportTypeReport235List(aCtx,estimationKind,department,workFunction,expert,dateBegin,dateEnd,typeMarks,typeOrder,3));
    map.put('expList',getQuarterlyReportTypeReport235List(aCtx,estimationKind,department,workFunction,expert,dateBegin,dateEnd,typeMarks,typeOrder,5));

    var cnts=getCountCards(aCtx,estimationKind,department,workFunction,expert,dateBegin,dateEnd,typeMarks);
    var total = cnts.split('#')[0];
    var nodef = cnts.split('#')[1];
    map.put('total',total);
    map.put('nodef',nodef);
    /*var def = total-nodef;
    def=''+def;
    map.put('def',def.replace('.0',''));*/


    var indicList = getQuarterlyReportTypeReportIndicators(aCtx,estimationKind,department,workFunction,expert,dateBegin,dateEnd,typeMarks,total,nodef);
	map.put('indicList',indicList);
    map.put('iper',getDefectPercent(indicList,4));
    map.put('def',getTotalDefects(indicList));
	return map;
}
function printAnestResPatient(aCtx, aParams) {
    var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase
        , new java.lang.Long(aParams.get("id"))) ;
    var sql = "select st.code as stcode,coalesce(idc.code||' '||ds.name,'') as mkb,coalesce(dep.name,'')" +
        " from medcase mc" +
        " left join medcase hmc on hmc.id=mc.parent_id" +
        " left join medcase pastmc on pastmc.transferdepartment_id=mc.department_id and pastmc.parent_id=hmc.id" +
        " left join mislpu dep on dep.id=pastmc.department_id" +
        " left join statisticstub st on st.id=hmc.statisticstub_id" +
        " left join diagnosis ds on ds.medcase_id=mc.id" +
        " left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id" +
        " left join vocprioritydiagnosis prior on prior.id=ds.priority_id" +
        " left join vocidc10 idc on idc.id=ds.idc10_id and reg.code='4' and prior.code='1' " +
        " where hmc.dtype='HospitalMedCase' and mc.dtype='DepartmentMedCase'" +
        " and mc.id="+medCase.id;
    var arr  = aCtx.manager.createNativeQuery(sql).getResultList();
    if (!arr.isEmpty()) {
        var data = arr.get(0);
        map.put("stat",""+data[0]);
        map.put("ds",""+data[1]);
        map.put("dep",""+data[2]);
    }
    return map;
}

//печать направления на микробиологическое исследование
function printMiсrobio(aCtx, aParams) {
	var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase
		, new java.lang.Long(aParams.get("id"))) ;
	var sql = "select pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as patfio" +
		" ,to_char(current_date,'dd.mm.yyyy г. ')||to_char(current_timestamp,'HH24 час. MM мин') as dt" +
		" ,adr.fullname as adr" +
		" ,(select vwf.name||' '||p.lastname||' '||p.firstname||' '||p.middlename " +
		" from SecUser su" +
		" left join WorkFunction wf on wf.secuser_id=su.id" +
		" left join VocWorkFunction vwf on vwf.id = wf.workFunction_id" +
		" left join Worker w on w.id=wf.worker_id" +
		" left join Patient p on p.id=w.person_id" +
		" where login = 'kmeshkov' and p.id is not null" +
		" ) as doc" +
		" from patient pat" +
		" left join medcase slo on slo.patient_id =pat.id " +
		" left join address2 adr on adr.addressId = pat.address_addressId" +
		" where slo.id="+medCase.id;
	var arr  = aCtx.manager.createNativeQuery(sql).getResultList();
	if (!arr.isEmpty()) {
		var data = arr.get(0);
		map.put("fio",""+data[0]);
		map.put("dt",""+data[1]);
		map.put("adr",""+data[2]);
		map.put("doc",""+data[3]);
	}
	return map;
}

//Получить информацию по пациенту (patId) для печати в направлении
function getPatientHIVDirectionInfo(aCtx,patId) {
	return aCtx.manager.createNativeQuery("select pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as patfio" +
        " ,vsex.name as sex" +
        " ,to_char(pat.birthday,'dd.mm.yyyy') as birthday" +
        " , case when pat.address_addressId is not null" +
        "      then coalesce(adr.fullname,adr.name)" +
        "           ||case when pat.houseNumber is not null and pat.houseNumber!='' then ' д.'||pat.houseNumber else '' end" +
        "           ||case when pat.houseBuilding is not null and pat.houseBuilding!='' then ' корп.'|| pat.houseBuilding else '' end" +
        "       ||case when pat.flatNumber is not null and pat.flatNumber!='' then ' кв. '|| pat.flatNumber else '' end" +
        "   when pat.territoryRegistrationNonresident_id is not null" +
        "  then okt.name||' '||pat.RegionRegistrationNonresident||' '||oq.name||' '||pat.SettlementNonresident" +
        "       ||' '||ost.name||' '||pat.StreetNonresident" +
        "           ||case when pat.HouseNonresident is not null and pat.HouseNonresident!='' then ' д.'||pat.HouseNonresident else '' end" +
        "       ||case when pat.BuildingHousesNonresident is not null and pat.BuildingHousesNonresident!='' then ' корп.'|| pat.BuildingHousesNonresident else '' end" +
        "       ||case when pat.ApartmentNonresident is not null and pat.ApartmentNonresident!='' then ' кв. '|| pat.ApartmentNonresident else '' end" +
        "   else  pat.foreignRegistrationAddress end as address" +

        " from Patient pat" +
        " left join vocsex vsex on vsex.id=pat.sex_id" +
        " left join Address2 adr on adr.addressid = pat.address_addressid" +
        " left join Omc_KodTer okt on okt.id=pat.territoryRegistrationNonresident_id" +
        " left join Omc_Qnp oq on oq.id=pat.TypeSettlementNonresident_id" +
        " left join Omc_StreetT ost on ost.id=pat.TypeStreetNonresident_id" +
        " where pat.id="+patId).getResultList() ;
}


//Печать направления на исследование на СПИД
function printDirectionHIV(aCtx, aParams) {
    var ret = new java.util.ArrayList() ;
	var info = new java.lang.String(aParams.get("info"));
	var infoMas=info.split('!');
    for (var i=0; i<infoMas.length; i++) {
        var row = infoMas[i].split('-');
        if (row[2]) {
            var list = getPatientHIVDirectionInfo(aCtx, row[0]);
            if (!list.isEmpty()) {
                var obj = list.get(0);
                var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult();
                par.set1("" + (i + 1));  //#
                par.set2(obj[0]);	//фио
                par.set3(obj[1]);	//пол
                par.set4(obj[2]);	//дата рождения
                par.set5(obj[3]);	//дом. адрес
                par.set6(row[1]);	//код контингента
                par.set7(row[2]);	//дата забора крови
                par.set8(row[3]);	//рег. номер
                ret.add(par);
			}
        }
    }
    map.put("listPat",ret) ;
    return map;
}

//todo - гавно, переделать
function printDirectionCovid(aCtx, aParams) {
    var ret = new java.util.ArrayList() ;
	var info = new java.lang.String(aParams.get("info"));
	var infoMas=info.split('!');
    for (var i=0; i<infoMas.length; i++) {
        var row = infoMas[i].split('--');
                var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult();
                par.set1("" + (i + 1));  //#
                par.set2(row[0]);	//фио
                par.set3(row[1]);	//дата начала
                par.set4(row[2]);	//возраст
                par.set5(row[3]);	//хз
                par.set6(row[4]);	//хз
                par.set7(row[5]);	//хз
                par.set8(row[6]);	//хз
                ret.add(par);
    }
    map.put("listPat",ret) ;
    return map;
}


//Печать cогласия на операцию переливания компонентов крови
function printTransfusionAgreement(aCtx, aParams) {
	var patId = new java.lang.Long(aParams.get("patId"));
	if (patId==null||patId==0) {return;}
	var patient = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient, patId) ;
	if (patient!=null)
		map.put("fio",patient.lastname + ' ' + patient.firstname + ' ' + patient.middlename) ;
	var arr  = aCtx.manager.createNativeQuery("select to_char(current_date,'dd.MM.yyyy') as curDate").getResultList();
	if (!arr.isEmpty())
		map.put("currentDate",arr.get(0));
	return map;
}

//Журнал COVID для Иванова сразу по всем инфекционным отделениям
function printCovidAllDepartments(aCtx, aParams) {
	var dateBegin = aParams.get("dateBegin"), dateEnd = aParams.get("dateEnd");
	if (!dateEnd) dateEnd=dateBegin;
	var dateSql = dateBegin!=null && typeof dateBegin !== 'undefined' && dateBegin!='' ?
		" and sls.dateStart between to_date('"+dateBegin+"','dd.mm.yyyy')  and to_date('"+dateEnd+"','dd.mm.yyyy')":
		" and m.transferDate is null and (m.dateFinish is null or m.dateFinish=current_date and m.dischargetime>CURRENT_TIME)";

	var patListSql = " select dep.name" +
		" ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as patfio" +
		" ,sex.name as sname" +
		" ,to_char(pat.birthday,'dd.mm.yyyy') as birthday" +
		" , case when pat.address_addressId is not null" +
		" then coalesce(adr.fullname,adr.name)" +
		" ||case when pat.houseNumber is not null and pat.houseNumber!='' then ' д.'||pat.houseNumber else '' end" +
		" ||case when pat.houseBuilding is not null and pat.houseBuilding!='' then ' корп.'|| pat.houseBuilding else '' end" +
		" ||case when pat.flatNumber is not null and pat.flatNumber!='' then ' кв. '|| pat.flatNumber else '' end" +
		" when pat.territoryRegistrationNonresident_id is not null" +
		" then okt.name||' '||pat.RegionRegistrationNonresident||' '||oq.name||' '||pat.SettlementNonresident" +
		" ||' '||ost.name||' '||pat.StreetNonresident" +
		" ||case when pat.HouseNonresident is not null and pat.HouseNonresident!='' then ' д.'||pat.HouseNonresident else '' end" +
		" ||case when pat.BuildingHousesNonresident is not null and pat.BuildingHousesNonresident!='' then ' корп.'|| pat.BuildingHousesNonresident else '' end" +
		" ||case when pat.ApartmentNonresident is not null and pat.ApartmentNonresident!='' then ' кв. '|| pat.ApartmentNonresident else '' end" +
		" else pat.foreignRegistrationAddress end as address" +
		" , st.code as stCode" +
		" ,to_char(sls.dateStart,'dd.mm.yyyy') as dSt" +
		" from medCase m" +
		" left join MedCase as sls on sls.id = m.parent_id" +
		" left join Mislpu dep on dep.id=m.department_id" +
		" left join Patient pat on m.patient_id = pat.id" +
		" left join vocsex sex on pat.sex_id = sex.id" +
		" left join Address2 adr on adr.addressid = pat.address_addressid" +
		" left join Omc_KodTer okt on okt.id=pat.territoryRegistrationNonresident_id" +
		" left join Omc_Qnp oq on oq.id=pat.TypeSettlementNonresident_id" +
		" left join Omc_StreetT ost on ost.id=pat.TypeStreetNonresident_id" +
		" left join StatisticStub st on st.medcase_id=sls.id" +
		" where m.DTYPE='DepartmentMedCase' and dep.isForCovid=true" +
		dateSql +
		" group by dep.name,pat.lastname,pat.firstname" +
		" ,pat.middlename,pat.birthday,sex.name" +
		" , pat.address_addressId ,adr.fullname,adr.name" +
		" , pat.houseNumber , pat.houseBuilding ,pat.flatNumber" +
		" , pat.territoryRegistrationNonresident_id , okt.name,pat.RegionRegistrationNonresident,oq.name,pat.SettlementNonresident" +
		" ,ost.name,pat.StreetNonresident" +
		" , pat.HouseNonresident , pat.BuildingHousesNonresident,pat.ApartmentNonresident" +
		" , pat.foreignRegistrationAddress,sls.id,st.code,sls.dateStart" +
		" order by dep.name,pat.lastname,pat.firstname,pat.middlename";
	var resultPatList = aCtx.manager.createNativeQuery(patListSql).getResultList();
	var patients = new java.util.ArrayList();
	if(!resultPatList.isEmpty()) {
		for (var i=0; i<resultPatList.size();i++){
			var p = resultPatList.get(i);
			var pp = new java.util.ArrayList();
			var dept = p[0];
			var fio = p[1];
			var sex = p[2];
			var bth = p[3];
			var adr = p[4];
			var hist = p[5];
			var datest = p[6];

			pp.add(((i+1)+'').replace('.0',''));//0
			pp.add(dept);
			pp.add(fio);
			pp.add(sex);
			pp.add(bth);
			pp.add(adr);
			pp.add(hist);
			pp.add(datest);

			patients.add(pp);
		}
	}
	map.put("patients", patients);
	return map;
}