var map = new java.util.HashMap() ;	

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
	
function printInfo(aCtx, aParams) {
	
	var ticket = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.ShortMedCase
		, new java.lang.Long(aParams.get("id"))) ;
	var mc = ticket.medcard;
    var prs = mc.person;
    var plc = null;
    record("pat",prs) ;
    var FORMAT = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
    record("bd",FORMAT.format(prs.birthday)) ;
    record("ticket",ticket) ;
    record("ticketd",ticket.dateFinish!=null?FORMAT.format(ticket.dateFinish):"") ;
    record("medcard",mc) ;
    record("idticket",""+ticket.id) ;
    recordVocProba("sex", prs.sex, 1, 2);
    recordVocProba("paym", ticket.serviceStream, 1, 5);
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
function recordBoCreateolean(aKey,aBool) {
	if (aBool!=null && aBool==true) {
		map.put(aKey+".k1","<text:span text:style-name=\"T13\">") ;
		map.put(aKey+".k2","</text:span>");
	} else {
		map.put(aKey+".k1","") ;
		map.put(aKey+".k2","");
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

