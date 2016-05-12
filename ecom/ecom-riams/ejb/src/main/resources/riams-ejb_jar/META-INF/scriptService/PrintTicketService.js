var map = new java.util.HashMap() ;	

function updateForNewTicket(aCtx,aParams) {
	aCtx.manager.createNativeQuery(sql).executeUpdate() ;
}

function printBakExp(aCtx, aParams) {
	
	var ticket = aCtx.manager.find(Packages.ru.ecom.poly.ejb.domain.Ticket
		, new java.lang.Long(aParams.get("id"))) ;
    var mc = ticket.medcard;
    var prs = mc.person;
    var plc = null;
    
    record("pat",prs) ;
    record("dateReg", ticket.date) ;
    return map ;
}

function back(aCtx, aParams) {
	return map ;
}
	
function printProtocol(aCtx, aParams) {
	//var list = aCtx.manager.createQuery("from Protocol where medCase_id=:sls")
		//.setParameter("sls",medCase.id).getResultList();
	//var protocol = !list.isEmpty()?list.iterator().next().record:"";
	var id =new java.lang.Long(aParams.get("id")) ; 
	var protocol = aCtx.manager.find(Packages.ru.ecom.poly.ejb.domain.protocol.Protocol
		, id) ;
	map.put("prot.date",protocol.dateRegistration);
	map.put("prot.time",protocol.timeRegistration);
	map.put("protocol",protocol);
	map.put("prot.spec",protocol.specialistInfo);
	//map.put("prot.rec",protocol.record) ;
	recordMultiText("prot.rec", protocol.record) ;
	map.put("prot.ticket",protocol.ticket) ;
	map.put("prot.idc10",protocol.ticket!=null && protocol.ticket.idc10!=null?protocol.ticket.idc10.code+". "+protocol.ticket.idc10.name:"") ;
	var nListDPT = new java.util.ArrayList() ;
	if (protocol.ticket!=null) {
		
		var listDPT = aCtx.manager.createQuery("from DrugPrescriptionByTicket where diary=:diary order by id")
		.setParameter("diary",protocol)
		.getResultList();
		var j=1 ;
		
		for (var i=0; i < listDPT.size(); i++) {
			var obj = listDPT.get(i) ;
			var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			par.set1(""+(j++)) ;
			if (obj.getDrug()!=null) {
				par.set2(obj.getNumberPrescript()!=null?obj.getNumberPrescript():"") ;
				par.set3(obj.getDrug().getName()!=null?obj.getDrug().getName():"--") ;
				par.set4(obj.amountUnit!=null?(+obj.amountString>0?obj.amountString:1)+" "+obj.amountUnit.code:"") ;
				par.set5(obj.frequencyUnit!=null?(+obj.frequengy>0?obj.frequengy+"":"")+" "+obj.frequencyUnit.code:"") ;
				par.set6(obj.durationUnit!=null?(+obj.duration>0?obj.duration:1)+" "+obj.durationUnit.name:"") ;
				par.set7(obj.method!=null?obj.method.name:"") ;
				nListDPT.add(par) ;
			} else {
				
			}
			
		}
		var medcard = protocol.ticket.medcard ;
		if (medcard!=null) {
			map.put("prot.medcard",medcard.number) ;
			var pat = medcard.person ;
				map.put("prot.patient",pat!=null?pat.fio:"") ;
			
		} else {
			map.put("prot.medcard","") ;
			map.put("prot.patient","") ;			
		}
	} else {
		map.put("prot.medcard","") ;
		map.put("prot.patient","") ;
	}
	if (nListDPT.size()>0) {
		map.put("drugInfo","Лекарственные назначения:") ;
	} else{
		map.put("drugInfo","") ;
	}
	map.put("drugs",nListDPT) ;
	return map ;
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
function getDefaultParameterByConfig(aParameter, aValueDefault, aCtx) {
	l = aCtx.manager.createNativeQuery("select sf.id,sf.keyvalue from SoftConfig sf where  sf.key='"+aParameter+"'").getResultList();
	if (l.isEmpty()) {
		return aValueDefault ;
	} else {
		return l.get(0)[1] ;
	}
}
function recordDisability(aContext,aPatId,aDateStart,aDateFinish,aField) {
	var sql="select dd.id,list(distinct to_char(dr.dateFrom,'yyyymmdd')) as datelist"
		+" , case when count(case when dr.dateto is null then dr.id else null end)=0 and dd.disabilityCase_id is not null then to_char(max(dr.dateFrom),'yyyymmdd') else '_____._____._________' end as maxdrdate"
		+", case when vdr.codef in ('01','02','04','06','07','10','11','12','13','14','15') then cast('заболевание' as varchar(50))"
		+" when vdr.codef='09' then cast('уход за больным членом семьи' as varchar(50))"
		+" when vdr.codef='03' then cast('в связи с карантином' as varchar(50))"
		+" when vdr.codef='08' then cast('на период санаторно-куротного лечения' as varchar(50))"
		+" when vdr.codef='05' then cast('по беременности и родам' as varchar(50)) else '' end as vreason"
		+" from DisabilityCase dc"
		+" 	left join DisabilityDocument dd on dd.disabilityCase_id=dc.id"
		+" 	left join DisabilityRecord dr on dr.disabilityDocument_id=dd.id"
		+" 	left join VocDisabilityDocumentCloseReason vddcr on dd.closeReason_id=vddcr.id"
		+" 	left join VocDisabilityReason vdr on dd.disabilityReason_id=vdr.id"
		+" 	where dc.patient_id='"+aPatId+"' and dd.anotherlpu_id is null"
		+" 	group by dc.id,dd.id,dd.number,vddcr.name,dd.issueDate,vdr.codef,dd.disabilityCase_id"
		+" 	having min(dr.dateFrom) between to_date('"+aDateStart+"','dd.mm.yyyy') and to_date('"+aDateFinish+"','dd.mm.yyyy')"
		+"  order by dd.issueDate"
	var list = aContext.manager.createNativeQuery(sql).getResultList() ;
	if (list.size()>0) {
		var obj=list.get(0) ;
		var disdoc = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.disability.DisabilityDocument,
				new java.lang.Long(obj[0])
		); 
		map.put(aField,disdoc) ;
		map.put(aField+"DateClose",obj[2]) ;
		map.put(aField+"Reason",obj[3]) ;
		var dats = (obj[1]!=null?""+obj[1]:"").split("") ;
		
		for (var i=0;i<6;i++) {
			
		}
	} else {
		map.put(aField,null) ;
		for (var i=0;i<6;i++) {
			map.put(aField+"Date"+(i+1),"") ;
		}
		map.put(aField+"DateClose","") ;
		map.put(aField+"Reason","") ;
		
	}
}
function printInfo(aCtx, aParams) {
	map.put("address_lpu",getDefaultParameterByConfig("address_lpu","___________________",aCtx)) ;
	map.put("name_lpu",getDefaultParameterByConfig("name_lpu","___________________",aCtx)) ;
	map.put("ogrn_lpu",getDefaultParameterByConfig("ogrn_lpu","___________________",aCtx)) ;
	map.put("okud_lpu",getDefaultParameterByConfig("okud_lpu","_________",aCtx)) ;
	map.put("okpo_lpu",getDefaultParameterByConfig("okpo_lpu","_________",aCtx)) ;

	var ticket = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.ShortMedCase
		, new java.lang.Long(aParams.get("id"))) ;
	var visit=ticket;
	var mc = ticket.medcard;
	var prs = mc.person;
	if (mc!=null) {
		map.put("mcnumber",mc.number) ;
	} else {
		map.put("mcnumber",prs.patientSync) ;
	}
    var plc = null;
    record("pat",prs) ;
    var listPriv =  aCtx.manager.createQuery("from Privilege where person=:pat and endDate is null order by beginDate desc")
	.setParameter("pat",prs).setMaxResults(1).getResultList() ;
	var priv = listPriv.size()>0?listPriv.get(0):null ;
	map.put("priv.info","") ;
	map.put("priv.obj",priv) ;
	map.put("priv.doc",priv!=null?priv.document:null) ;
	map.put("priv.code",priv!=null?(priv.privilegeCode!=null?priv.privilegeCode.code!=null?priv.privilegeCode.code:null:null):null) ;
	map.put("priv.endDate",priv!=null?(priv.endDate!=null?priv.endDate:null):null) ;

    var FORMAT = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
    var policy_list_sql = "select mp.id, case when (mp.DTYPE='MedPolicyOmc') then 'ОМС' when (mp.DTYPE='MedPolicyDmcForeign') then 'ДМС иногороднего' when (mp.DTYPE='MedPolicyDmc') then 'ДМС' else 'ОМС иногороднего' end"
       +" , ri.name as riname,coalesce('серия '||mp.series,'')||' '||coalesce(' №'||mp.polnumber,'')||coalesce(' RZ'||mp.commonNumber,'')"
       +" ,mp.actualDateFrom,mp.actualDateTo ,mp.commonNumber,vmo.name as vmoname"
       +" from MedPolicy as mp "
       +"  left join reg_ic as ri on ri.id=mp.company_id" 
       +" left join vocmedpolicyomc vmo on vmo.id=mp.type_id"
       +" where mp.patient_id="+prs.id+" and mp.actualDateFrom <=CURRENT_DATE and (mp.actualDateTo is null or mp.actualDateTo >=CURRENT_DATE)" ;
    var p_list=aCtx.manager.createNativeQuery(policy_list_sql).getResultList() ;
    if (p_list.size()>0) {
    	var p_obj=p_list.get(0) ;
    	map.put("policyInfoSN",p_obj[3]);
    	map.put("policyInfoC",p_obj[2]);
    } else {
    	map.put("policyInfoSN","") ;
    	map.put("policyInfoC","") ;
    }
    var medservice_list_sql = "select mc.id, mss.name, mss.code from medcase mc left join medservice mss on mss.id=mc.medservice_id " +
    		"where mc.dtype='ServiceMedCase' and mc.parent_id='"+ticket.id+"'";
    var ms_list = aCtx.manager.createNativeQuery(medservice_list_sql).getResultList();
    var medServiceName=["",""];
    var medServiceCode=["",""];
    if (ms_list.size()>0) {
    	for (var i=0;i<ms_list.size();i++) {
    		if (i==2) break;
    		var obj = ms_list.get(i);
    		medServiceName[i]=obj[1];
        	medServiceCode[i]=obj[2];
        }    	
    } 
    map.put("medService0Name",medServiceName[0]);
    map.put("medService0Code",medServiceCode[0]);
    map.put("medService1Name",medServiceName[1]);
    map.put("medService1Code",medServiceCode[1]);
    
    
    var diag_list_sql = "select vip.name as vname, idc.name as iname, idc.code as icode from diagnosis ds left join vocidc10 idc on idc.id=ds.idc10_id " +
    		"left join vocillnesprimary vip on vip.id=ds.illnesprimary_id where ds.medcase_id='"+ticket.id+"'";
    var diag_list = aCtx.manager.createNativeQuery(diag_list_sql).getResultList();
    var obj_diag = null;
    if (diag_list.size()>0) {
    	obj_diag=diag_list.get(0);
    	map.put("mainDiagChar",""+obj_diag[0]);
    	map.put("mainDiagName",""+obj_diag[1]);
    	map.put("mainDiagMKB",""+obj_diag[2]);
    } else {
    	map.put("mainDiagMKB","");
    	map.put("mainDiagName","");
    	map.put("mainDiagChar","");
    }
    var otherVisits = [];
	var otherVisits_list_sql = "select to_char(m2.datestart,'dd.MM.yyyy') from medcase m" +
			" left join medcase m1 on m1.id=m.parent_id" +
			" left join medcase m2 on m2.parent_id=m1.id" +
			" where m.id='"+ticket.getId()+"' and (m2.dtype='Visit' or m2.dtype='ShortMedCase') group by m2.datestart order by m2.datestart desc";
	var otherVisit_list = aCtx.manager.createNativeQuery(otherVisits_list_sql).getResultList();
	for (var i=0;i<14;i++) {
		otherVisits[i]="";
	}
	if (otherVisit_list.size()>0) {
		var j=0 ;
		for (var i=otherVisit_list.size();i>0;i--) {
			otherVisits[j++]=""+otherVisit_list.get(i-1);
		}
	}
	for (var i=0;i<14;i++) {
		map.put("otherVisit"+i,otherVisits[i]);
	}
	
	var invalidity_list_sql = "select vi.name" +
			" ,case when ( i.initial='1') then 'ВПЕРВЫЕ' else 'ПОВТОРНО' end as c1" +
			" ,case when ( i.childhoodinvalid='1') then 'ДА' else 'НЕТ' end as c2" +
			" from invalidity i " +
			" left join vocinvalidity vi on vi.id=i.group_id " +
			"where i.patient_id='"+visit.patient.getId()+"' order by i.id desc";
	var inv_primary="", inv_group="",inv_childhood="";
	var invalidity_list = aCtx.manager.createNativeQuery(invalidity_list_sql).getResultList();
	if (invalidity_list.size()>0) {
		var i_obj = invalidity_list.get(0);
		inv_group=i_obj[0];
		inv_primary=i_obj[1];
		inv_childhood=i_obj[2];
	}
	map.put("invalid_primary",inv_primary);
	map.put("invalid_group",inv_group);
	map.put("invalid_childhood",inv_childhood);
	
	recordDisability(aCtx,visit.patient.id,"01.01.2015","01.01.2015","dis") ;
    
    record("bd",FORMAT.format(prs.birthday)) ;
    record("ticket",ticket) ;
    record("ticketd",ticket.dateFinish!=null?FORMAT.format(ticket.dateFinish):"") ;
    record("medcard",mc) ;
    record("idticket",""+ticket.id) ;
    //recordVocProba("sex", prs.sex, 1, 2);
    //recordVocProba("paym", ticket.serviceStream, 1, 5);
    if (ticket.getWorkFunctionExecute()!=null) {
    	record("Doctor", ticket.getWorkFunctionExecute().getWorkerInfo());
    	ifVocIsNotNull(ticket.getWorkFunctionExecute().getWorkFunction(),"DoctorFunction") ;
    	if (ticket.workFunctionExecute.worker!=null && ticket.workFunctionExecute.worker.lpu!=null) {
    		record("LPUName",ticket.workFunctionExecute.worker.lpu.name) ;
    	} else {
    		record("LPUName","") ;
    	}
    } else {
    	record("Doctor","") ;
    	record("DoctorFunction","") ;
    	record("LPUName","") ;
    }
    // 14. Занятость
    var socStatus=prs.socialStatus!=null &&prs.socialStatus.code!=null?prs.socialStatus:null ;
    if (socStatus!=null) {
    	if (socStatus=="10") {
    		record("socStatus","работает") ;
    	} else if (socStatus=="05") {
    		record("socStatus","проходит военную службу или приравненную к ней службу") ;
    	} else if (socStatus=="20") {
    		record("socStatus","пенсионер(ка)") ;
    	} else if (socStatus=="80") {
    		record("socStatus","студент(ка)") ;
    	} else if (socStatus=="00" ||socStatus=="77"||socStatus=="70"||socStatus== "90") {
    		record("socStatus","не работет") ;
    	} else  {
    		record("socStatus","прочие") ;
    	}
    } else {
    	record("socStatus","прочие") ;
    }
    var vis1="" ; var vis2=""; var vis3="" ;
    var isPsych = aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MisLpu/Psychiatry") ;
    if (obj_diag!=null&&obj_diag[2]!=null && obj_diag[2]!="") {
    	var ct = ticket.visitResult!=null
		&&ticket.visitReason.codeTicket!=null
		&&ticket.visitReason.codeTicket!=""?ticket.visitReason.codeTicket:null ;
    	if (obj_diag[2].startsWith("Z")) {
    		vis1="с профилактическими и иными целями (Z00-Z99)" ;
    		vis2="с профилактической целью (Z00-Z99) " ;
    		
    		if (ct=="2.1") {
    			if (isPsych) {
        			if (obj_diag[2]=="Z00.0" || obj_diag[2]=="Z00.4") {
            			vis3="медицинские осмотры" ;
        			} else {
        				vis3="другие обстоятельства" ;
        			}
        		} else {
        			vis3="медицинские осмотры" ;
        		}
			} else if (ct=="2.2") {
				vis3="диспансеризация" ;
			} else if (ct=="2.3") {
				vis3="комплексное обследование" ;
			} else if (ct=="2.4") {
				vis3="паллиативная медицинская помощь" ;
			} else if (ct=="2.5") {
				vis3="патронаж" ;
			} else {
				vis3="другие обстоятельства" ;
			}
    	} else {
    		vis1="по заболеваниям (A00-T98)" ;
    		vis2="по заболеванию (A00-T98)" ;
    		if (ticket.emergency)  {
    			vis3="в неотложной форме" ;
    		} else if (ticket.workplaceType!=null &&ticket.workplaceType.code!=null
    				&&ticket.workplaceType.code=="HOMEACTIVE") {
    			vis3="активное посещение" ;
    		} else if (ct!=null) {
    			if (ct=="1.1") {
    				vis3="в неотложной форме" ;
    			} else if (ct=="1.2") {
    				vis3="активное посещение" ;
    			} else if (ct=="1.3") {
    				vis3="диспансенрное наблюдение" ;
    			}
    		}
    	}
    }
	record("visit1",vis1);
	record("visit2",vis2);
	record("visit3",vis3);
    return map ;
}

function ifIsNotNull(aObj,aKey, aValue) {
	if (aObj!=null) {
		map.put(aKey,aValue) ;
	} else {
		map.put(aKey,"") ;
	}
}
function ifVocIsNotNull(aObj,aKey) {
	if (aObj!=null) {
		map.put(aKey,aObj.getName()) ;
	} else {
		map.put(aKey,"") ;
	}
}

function record(aKey,aValue) {
	map.put(aKey,aValue) ;
}
function recordDate(aKey, aDate) {
	if (aDate!=null) {
		var calen = java.util.Calendar.getInstance() ;
		calen.setTime(aDate) ;
		map.put(aKey+".day",""+calen.get(java.util.Calendar.DAY_OF_MONTH)) ;
		var month = ""+(calen.get(java.util.Calendar.MONTH)+1) ;
		map.put(aKey+".month",month) ;
		map.put(aKey+".monthname","");
		if (month!=null && month.equals("1")) map.put(aKey+".monthname","ЯНВАРЯ");
		if (month!=null && month.equals("2")) map.put(aKey+".monthname","ФЕВРАЛЯ");
		if (month!=null && month.equals("3")) map.put(aKey+".monthname","МАРТА");
		if (month!=null && month.equals("4")) map.put(aKey+".monthname","АПРЕЛЯ");
		if (month!=null && month.equals("5")) map.put(aKey+".monthname","МАЯ");
		if (month!=null && month.equals("6")) map.put(aKey+".monthname","ИЮНЯ");
		if (month!=null && month.equals("7")) map.put(aKey+".monthname","ИЮЛЯ");
		if (month!=null && month.equals("8")) map.put(aKey+".monthname","АВГУСТА");
		if (month!=null && month.equals("9")) map.put(aKey+".monthname","СЕНТЯБРЯ");
		if (month!=null && month.equals("10")) map.put(aKey+".monthname","ОКТЯБРЯ");
		if (month!=null && month.equals("11")) map.put(aKey+".monthname","НОЯБРЯ");
		if (month!=null && month.equals("12")) map.put(aKey+".monthname","ДЕКАБРЯ");
		
		map.put(aKey+".year",""+calen.get(java.util.Calendar.YEAR)) ;
	} else {
		map.put(aKey+".day","") ;
		map.put(aKey+".month","") ;
		map.put(aKey+".year","") ;
	}
}

function recordArray(aKey,aValue,aMinVal,aMaxVal,aPrint) {
	
	for (var i=aMinVal;i<=aMaxVal;i++) {
		map.put(aKey+i,"");
	}
	if (aValue!=null)
	map.put(aKey+aValue.code,aPrint) ;
	}

function recordBlanks(aKey,aBlanks,aMax) {
	for (var i=0;i<=aBlanks.size()-1;i++) {
		var j=i+1;
		var bl=aBlanks.get(i);
		var info="Серия "+bl.series+" номер "+bl.number;
		if (bl.writingOutDate==null) {
			
		}else {
			var dat = Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(bl.writingOutDate);
			info=info+" дата выписки "+dat;
		}
		map.put(aKey+j,info);
	}
	for (var i=aBlanks.size()+1;i<=aMax;i++) {
		map.put(aKey+i,"");
	}
}

function recordTime(aKey, aTime) {
	if (aTime!=null) {
		map.put(aKey,aTime.toString()) ;
	} else {
		map.put(aKey,"") ;
	}
} 

