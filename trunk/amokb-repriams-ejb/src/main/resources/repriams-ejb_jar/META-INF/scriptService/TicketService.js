function addTalk(aContext, aTicketId) {
	var ticket = aContext.manager.find(Packages.ru.ecom.poly.ejb.domain.Ticket
		, java.lang.Long.valueOf(aTicketId)) ;
	var profs = aContext.manager.createQuery("from VocReason where code='PROFYLACTIC'").getResultList() ;
	var prof = profs.size() ? profs.get(0) : null ;
	var ticketCopy = new Packages.ru.ecom.poly.ejb.domain.Ticket() ;
	ticketCopy.medcard = ticket.medcard ;
	ticketCopy.vocIllnesType = ticket.vocIllnesType ;
	ticketCopy.vocPaymentType = ticket.vocPaymentType ;
	ticketCopy.vocReason = prof ;
	ticketCopy.vocServicePlace = ticket.vocServicePlace ;
	ticketCopy.vocVisitResult = ticket.vocVisitResult ;
	ticketCopy.workFunction = ticket.workFunction ;
	ticketCopy.idc10 = ticket.idc10 ;
	ticketCopy.primary = ticket.primary ;
	ticketCopy.hospitalization = ticket.hospitalization;
	ticketCopy.date = ticket.date ;
	ticketCopy.time = ticket.time ;
	ticketCopy.talk = java.lang.Boolean.TRUE ;
	ticketCopy.status = Packages.ru.ecom.poly.ejb.domain.Ticket.STATUS_CLOSED
	aContext.manager.persist(ticketCopy);
	return ticketCopy.getId();
	
}
function journalRegisterVisit(aCtx,aParams) {
	var ret = new java.util.ArrayList() ;
	var obj = aParams.split(":") ;
	var FORMAT_1 = new java.text.SimpleDateFormat("yyyy-MM-dd") ;
    var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
    var FORMAT_3 = new java.text.SimpleDateFormat("hh:mm") ;
	var startDate = FORMAT_1.format( FORMAT_2.parse(obj[0])) ;
	var finishDate = FORMAT_1.format( FORMAT_2.parse(obj[1])) ;
	var spec = obj[2] ;
	var rayon = obj[3] ;
	var primary = obj[4] ;
	var sn = +obj[5] ;
	var func = obj[7] ;
	var order = obj[6]!=null?obj[6]:"date,time" ;
	
	
	
	var sql = "where t.date between '"+startDate+"' and '"+finishDate+"'" ;
	
	if (func!=null && (+func>0)) {
		sql = sql + " and wf.workFunction_id='"+func+"'"
	}
	if (spec!=null && (+spec>0)) {
		sql = sql + " and t.workFunction_id='"+spec+"'"
	}
		
	if (primary!=null && (+primary>0)) {
		sql = sql+" and t.hospitalization_id='"+primary+"'" ;
	}
	if (rayon!=null && (+rayon>0)) {
		sql = sql+" and p.rayon_id='"+rayon+"'" ;
	}
	sql = "select t.id ,to_char(t.date,'dd.MM.yyyy')"+
	+", vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as spec"
	+", p.lastname||' '||p.firstname||' '||p.middlename as fio"
	+", vs.name as vsname,to_char(p.birthday,'dd.mm.yyyy')"
	+", $$ByPatient^ZAddressLib(p.id),vr.name as vrname"
	+", m.number"
	+", (select list(mp.series||' '||mp.polNumber||' '||ri.name||' ('||to_char(mp.actualDateFrom,'dd.MM.yyyy')||'-'||to_char(mp.actualDateTo,'dd.MM.yyyy')||')') from medpolicy mp left join reg_ic ri on ri.id=mp.company_id where mp.patient_id=p.id "
		+" and t.date between mp.actualDateFrom and COALESCE(mp.actualDateTo,CURRENT_DATE))"
	+" ,mkb.code as mkbcode,vh.code as vhcode,t.directHospital"
	+" from Ticket t "
	+" left join medcard m on m.id=t.medcard_id left join patient p on p.id=m.person_id left join vocrayon vr on vr.id=p.rayon_id "
	+" left join workfunction wf on wf.id=t.workfunction_id "
	+" left join worker w on w.id=wf.worker_id"
	+" left join vocworkfunction vwf on vwf.id=wf.workfunction_id"
	+" left join patient wp on wp.id=w.person_id"
	+" left join vocsex vs on vs.id=p.sex_id"
	+" left join vocidc10 mkb on mkb.id=t.idc10_id"
	+" left join vochospitalization vh on vh.id=t.hospitalization_id "+sql +" and t.status=2";
	
	//throw sql ;
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	var ids = "" ;
	if (sn<1) sn=1 ;
	//throw list.size() ;
	if (+list.size()>0) {
		for (var i=0 ; i< list.size() ; i++) {
			ret.add(ticketMap(list.get(i),sn,FORMAT_1,FORMAT_2,FORMAT_3,aCtx.manager)) ;
			sn=sn+1 ;
		}
		/
	} else {
	}
	
	return ret ;
}
function journalRegisterGroupByDirectHospital() {
	var ret = new java.util.ArrayList() ;
	var obj = aParams.split(":") ;
	var FORMAT_1 = new java.text.SimpleDateFormat("yyyy-MM-dd") ;
    var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
    var FORMAT_3 = new java.text.SimpleDateFormat("hh:mm") ;
	var startDate = FORMAT_1.format( FORMAT_2.parse(obj[0])) ;
	var finishDate = FORMAT_1.format( FORMAT_2.parse(obj[1])) ;
	var spec = obj[2] ;
	var rayon = obj[3] ;
	var primary = obj[4] ;
	var sn = +obj[5] ;
	var func = obj[7] ;
	var order = obj[6]!=null?obj[6]:"date,time" ;
	
	
	
	var sql = "where t.date between '"+startDate+"' and '"+finishDate+"'" ;
	
	if (func!=null && (+func>0)) {
		sql = " left join workfunction wf on wf.id=t.workfunction_id "+sql + " and wf.workFunction_id='"+func+"'"
	}
	if (spec!=null && (+spec>0)) {
		sql = sql + " and t.workFunction_id='"+spec+"'"
	}
		
	if (primary!=null && (+primary>0)) {
		sql = sql+" and t.hospitalization_id='"+primary+"'" ;
	}
	if (rayon!=null && (+rayon>0)) {
		sql = " left join medcard m on m.id=t.medcard_id left join patient p on p.id = m.person_id "+sql+" and p.rayon_id='"+rayon+"'" ;
	}
	sql = "select t.directHospital,count(*)"
	+" from Medcase t "
	+sql +" and t.status=2";
	+" group by t.directHospital"
	
	//throw sql ;
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	if (+list.size()>0) {
		for (var i=0 ; i< list.size() ; i++) {
			var obj = list.get(i) ;
			var map = new java.util.HashMap() ;
			//var pat = aTic.medcard!=null?aTic.medcard.person:"" ;
			map.put("id", obj[0]) ;
			map.put("count",obj[1]) ;
			ret.add(map) ;
			
		}
		/
	}
	return ret ;
}
function ticketMap(aTic,aSn,aFormatSql,aFormat,aFormatTime,aManager) {
	var map = new java.util.HashMap() ;
	//var pat = aTic.medcard!=null?aTic.medcard.person:"" ;
	map.put("id", aTic[0]) ;
	map.put("sn",""+aSn) ;
	map.put("date",aTic[1]) ;
	map.put("spec",aTic[2]) ;
	map.put("fio",aTic[3]) ;
	map.put("sex",aTic[4]) ;
	map.put("birthday",aTic[5]);
	map.put("address",aTic[6]) ;
	map.put("rayon",aTic[7]) ;
	map.put("medcard",aTic[8]) ;
	map.put("policy",aTic[9]) ;	
	map.put("mkb",aTic[10]) ;
	map.put("primary",aTic[11]) ;
	map.put("directHospital",+aTic[12]>0?"Да":"") ;
	/*
	var pol ="";
	
	if (pat!=null) {
		var sqlDate = aFormatSql.format(aTic.date) ;
		var sql = "from MedPolicy where patient_id="
		+pat.id+" and cast(cast('"+sqlDate+"' as date) as int)<=cast(actualDateTo as int) and cast(cast('"
		+sqlDate+"' as date) as int) >= cast(actualDateFrom as int)" ;
		//throw sql ;
		var policies  = aManager.createQuery(sql).getResultList() ;
		pol = policies.size()>0 ?policies.get(0).text:"" ;
	}*/
	
	
	

	

	return map ;
}