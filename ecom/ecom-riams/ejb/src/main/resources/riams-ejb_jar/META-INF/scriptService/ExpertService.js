function deleteExtDispServices(aCtx, aCard, aType) {
	//throw ""+aParams;
	var sql = "delete from ExtDispService where dtype='"+aType+"' and card_id="+aCard;
	aCtx.manager.createNativeQuery(sql).executeUpdate();
}


function createExtDispExamService(aCtx,aParams){
//	throw ""+aParams ;
	var param=aParams.split(":") ;
	
	var card = param[0] ;
	var cardO=aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.extdisp.ExtDispCard,java.lang.Long.valueOf(card)) ;
	var serviceType=+param[2] ;
	var serviceDate = param[3] ;
	var isPathology = param[4] ;
	var deniedService = param[5] ;
	if (isPathology=="checked"||isPathology=="true"||isPathology=="on"||isPathology=="1") {isPathology=1 ;} else {isPathology=0;}
	if (deniedService=="checked"||deniedService=="true"||deniedService=="on"||deniedService=="1") {deniedService=1 ;} else {deniedService=0;}
		var serviceO = new Packages.ru.ecom.mis.ejb.domain.extdisp.ExtDispExam();
		var serviceTypeO=aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispService,java.lang.Long.valueOf(serviceType)) ;
		try {
			serviceO.serviceDate = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(serviceDate);
		} catch(e) {
			serviceO.serviceDate=null ;
		}
		serviceO.card = cardO;
		serviceO.serviceType = serviceTypeO;
		serviceO.isPathology = isPathology>0 ;
		serviceO.deniedService=deniedService>0;
			aCtx.manager.persist(serviceO) ;
}
function createExtDispVisitService(aCtx,aParams){
	
	var param=aParams.split(":") ;
	//throw "PARAMS="+param[6]; 
	var card = param[0] ;
	var cardO=aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.extdisp.ExtDispCard,java.lang.Long.valueOf(card)) ;
	//var id = param[1] ;
	var serviceType=+param[2] ;
	var serviceDate = param[3] ;
	var recommendation = param[4] ;
	var isEtdccSuspicion = param[5] ;
	var workFunction = param[6] ;
	var Idc10=param[7];
	var createVisit = (param.length>9&&+param[9]===1)?1:0;
	if (isEtdccSuspicion=="checked"||isEtdccSuspicion=="true"||isEtdccSuspicion=="on"||isEtdccSuspicion=="1") {isEtdccSuspicion=1 ;} else {isEtdccSuspicion=0;}
		var serviceO = new Packages.ru.ecom.mis.ejb.domain.extdisp.ExtDispVisit();
		var serviceTypeO=aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispService,java.lang.Long.valueOf(serviceType)) ;
		try {
			serviceO.serviceDate = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(serviceDate);
		} catch(e) {
			serviceO.serviceDate=null ;
		}
		serviceO.card = cardO;
		serviceO.serviceType = serviceTypeO;
		serviceO.recommendation = recommendation ;
		serviceO.isEtdccSuspicion = isEtdccSuspicion>0 ;
        serviceO.deniedService=false;
		if (Idc10!=null && Idc10!="" && Idc10!="null") {
            var Idc10O=aCtx.manager.find(Packages.ru.ecom.expomc.ejb.domain.med.VocIdc10,java.lang.Long.valueOf(Idc10)) ;
            serviceO.idc10=Idc10O;
		}
		if (workFunction!=null && workFunction!="" && workFunction!="null") {
            var workFunctionO=aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction,java.lang.Long.valueOf(workFunction)) ;
            serviceO.workFunction=workFunctionO;
		}
			aCtx.manager.persist(serviceO) ;
			
			//Время создавать визиты
			if (createVisit==1 && workFunction!=null && workFunction!="" && workFunction!="null"){
					var spo = null;
					var smc= null;
					var pat = cardO.getPatient();
					var sql="select id from medcase where dtype='PolyclinicMedCase' and patient_id="+cardO.patient.id+" and dateStart = to_date('"+cardO.startDate+"','yyyy-MM-dd')" +
							"and ownerFunction_id="+cardO.workFunction.id+" and (noActuality is null or noActuality='0')";
					
					var spoId = aCtx.manager.createNativeQuery(sql).getResultList();
					if (spoId.size()>0) {spoId=spoId.get(0);}
				
					if (+spoId>0){
						spo=aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase,java.lang.Long.valueOf(spoId)) ;
					} else {
						spo = new Packages.ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase();
						spo.setDateStart(cardO.getStartDate());
						spo.setDateFinish(cardO.getFinishDate())
						spo.setLpu(cardO.getLpu());
						spo.setServiceStream(cardO.getServiceStream());
						spo.setPatient(pat);
						spo.setIdc10(cardO.getIdcMain());
						spo.setOwnerFunction(cardO.getWorkFunction());
						spo.setStartFunction(cardO.getWorkFunction());
						spo.setFinishFunction(cardO.getWorkFunction());
						aCtx.manager.persist(spo);
					}
					var username = aCtx.getSessionContext().getCallerPrincipal().toString() ;
					
					var smc = new Packages.ru.ecom.mis.ejb.domain.medcase.ShortMedCase();
					smc.setParent(spo);
					var visitResult=aCtx.manager.createQuery(" from VocVisitResult where omcCode=:par").setParameter("par","3").getSingleResult() ; //Дин. наблюдение
					var visitReason=aCtx.manager.createQuery(" from VocReason where omcCode=:par").setParameter("par","2").getSingleResult() ; //Профосмотр
					var visitWorkPlace=aCtx.manager.createQuery(" from VocWorkPlaceType where omcCode=:par").setParameter("par","1").getSingleResult() ; //Поликлиника
					var visitIllnesPrimary=aCtx.manager.createQuery(" from VocIllnesPrimary where code=:par").setParameter("par","1").getSingleResult() ; //Поликлиника
					var visitMedcard = aCtx.manager.createQuery(" from Medcard where person=:pat").setParameter("pat",cardO.patient).getResultList(); //Поликлиника
					if (!visitMedcard.isEmpty()) {
						visitMedcard = visitMedcard.get(0);
					} else {
						visitMedcard = new Packages.ru.ecom.poly.ejb.domain.Medcard();
						visitMedcard.setPerson(pat);
						var cardNumber = pat.patientSync!=null&&pat.patientSync!=""?pat.patientSync:""+pat.id;
						var cardNameListSize  =aCtx.manager.createQuery("select id from Medcard where number='"+cardNumber+"'").getResultList().size();
						if (cardNameListSize>0){
							while (cardNameListSize>0){
								cardNumber="0"+cardNumber;
								cardNameListSize  =aCtx.manager.createQuery("select id from Medcard where number='"+cardNumber+"'").getResultList().size();		
							}
						}
						visitMedcard.setNumber(cardNumber);
						visitMedcard.setRegistrator(username);
						visitMedcard.setDateRegistration(new java.sql.Date(new java.util.Date().getTime()));
						aCtx.manager.persist(visitMedcard);
					}
					smc.setDateStart(Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(serviceDate));
					smc.setUsername(username);
					smc.setServiceStream(cardO.getServiceStream());
					smc.setPatient(pat);
					smc.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
					
					smc.setWorkFunctionExecute(serviceO.workFunction);
					smc.setVisitResult(visitResult);
					smc.setVisitReason(visitReason);
					smc.setWorkPlaceType(visitWorkPlace);
					smc.setMedcard(visitMedcard);
					aCtx.manager.persist(smc);
					if (serviceO.idc10!=null||cardO.idcMain!=null){
						var vocConcomType = Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseSaveInterceptor.getVocByCode(aCtx.manager,"VocPriorityDiagnosis","1") ;
						var diag=new Packages.ru.ecom.mis.ejb.domain.medcase.Diagnosis() ;
						  diag.setMedCase(smc);
						  diag.setPatient(pat);
						  diag.setEstablishDate(smc.dateStart);
						  diag.setIdc10(serviceO.idc10!=null?serviceO.idc10:cardO.idcMain);
						  diag.setPriority(vocConcomType);
						  diag.setName(serviceO.idc10!=null?serviceO.idc10.name:cardO.idcMain.name);
						  diag.setIllnesPrimary(visitIllnesPrimary);
						  aCtx.manager.persist(diag);
					}
				}
}
function getCountDispServices (aCtx, aCardId) {
	return ""+aCtx.manager.createNativeQuery("select count(id) from extDispService where card_id="+aCardId).getSingleResult();
}
/**
* Поиск
*/
function findListCard(aContext, aId, aCnt, aNext) {
	var list ;
	var ret = new java.util.ArrayList() ;
	var sql ="select card.id as cardid,vek.name as vekname"
+" ,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor"
+" ,d.name as dname,p.lastname||' '||p.firstname||' '||p.middlename as pat"
+"  ,case when md.DTYPE='HospitalMedCase' then 'СЛС №'||ss1.code  "
+"  	when md.DTYPE='DepartmentMedCase' then 'СЛО '||md.id||' СЛС №'||ss2.code"  
+"  	when md.DTYPE='PolyclinicMedCase' then 'СПО №'||md.id "
+"  	else 'СМО №'||md.id end as dtype "
+" ,mkb.code as mkbcode,card.createDate,card.createUsername" 
+"  ,(select round(cast(avg(coalesce(vqem.mark,qecB.markTransient)) as numeric),2) from QualityEstimationCrit qecB left join vocqualityEstimationMark vqem on vqem.id=qecB.mark_id left join vocqualityEstimationCrit vqec on vqec.id=qecB.criterion_id where qecB.estimation_id=qeB.id and vqec.parent_id is null) as mark1"
+"  , (select round(cast(avg(coalesce(vqem.mark,qecB.markTransient)) as numeric),2) from QualityEstimationCrit qecB  left join vocqualityEstimationMark vqem on vqem.id=qecB.mark_id left join vocqualityEstimationCrit vqec on vqec.id=qecB.criterion_id where qecB.estimation_id=qeE.id and vqec.parent_id is null) as mark2"
+"  , (select round(cast(avg(coalesce(vqem.mark,qecB.markTransient)) as numeric),2) from QualityEstimationCrit qecB  left join vocqualityEstimationMark vqem on vqem.id=qecB.mark_id left join vocqualityEstimationCrit vqec on vqec.id=qecB.criterion_id where qecB.estimation_id=qeC.id and vqec.parent_id is null) as mark3"
+"  from QualityEstimationCard card"
+"  left join VocQualityEstimationKind vek on vek.id=card.kind_id"
+"  left join WorkFunction wf on wf.id=card.doctorCase_id"
+"  left join Worker w on w.id=wf.worker_id"
+"  left join Patient wp on wp.id=w.person_id"
+"  left join Patient p on p.id=card.patient_id"
+"  left join VocIdc10 mkb on mkb.id=card.idc10_id"
+"  left join MisLpu d on d.id=card.department_id"
+"  left join VocWorkFunction vwf on vwf.id=wf.workFunction_id"
+"  left join MedCase md on md.id=card.medcase_id"
+"  left join MedCase mh on mh.id=md.parent_id"
+"  left join StatisticStub ss1 on ss1.id=md.statisticStub_id"
+"  left join StatisticStub ss2 on ss2.id=mh.statisticStub_id"
+"  left join qualityestimation qeB on card.id=qeB.card_id and qeB.expertType='BranchManager'"  
+"  left join qualityestimation qeE on card.id=qeE.card_id and qeE.expertType='Expert'"  
+" left join qualityestimation qeC on card.id=qeC.card_id and qeC.expertType='Coeur' "
	
	//if (aCnt==0) aCnt=10 ;
	if (aNext < 0) {
		if (aCnt<0) aCnt = aCnt*(-1) ;
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createNativeQuery(sql+" order by card.id desc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createNativeQuery(sql+" where card.id < "+aId+" order by card.id desc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListCard(aContext,"",aCnt,1) ;
			}
			
		}
		
		for (var i=list.size()-1 ; i>=0 ; i--) {
			var temp = list.get(i) ;
			ret.add(cardMap(temp)) ;
			
		}
	} else {
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createNativeQuery(sql+" order by card.id asc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createNativeQuery(sql+" where card.id > "+aId+" order by card.id asc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListCard(aContext,"",aCnt,-1) ;
			}
		}
		for (var i=0 ; i< list.size() ; i++) {
			var temp = list.get(i) ;
			ret.add(cardMap(temp)) ;
		}
	}
	return ret ;
}
function cardMap(aCard) {
	var map = new java.util.HashMap() ;
	//var id=new java.lang.Long(aCard.id)
	//var list = aManager.createNativeQuery("select (select avg(vqem.mark) from QualityEstimationCrit qecB left join vocqualityEstimationMark vqem on vqem.id=qecB.mark_id where qecB.estimation_id=qeB.id), (select avg(vqem.mark) from QualityEstimationCrit qecB  left join vocqualityEstimationMark vqem on vqem.id=qecB.mark_id where qecB.estimation_id=qeE.id), (select avg(vqem.mark) from QualityEstimationCrit qecB  left join vocqualityEstimationMark vqem on vqem.id=qecB.mark_id where qecB.estimation_id=qeC.id)  from qualityestimationcard card  left join qualityestimation qeB on card.id=qeB.card_id and qeB.expertType='BranchManager'  left join qualityestimation qeE on card.id=qeE.card_id and qeE.expertType='Expert'  left join qualityestimation qeC on card.id=qeC.card_id and qeC.expertType='Coeur' where card.id='"+id+"'").getResultList() ;
	//var row=null ;
	//if (list.size()>0) row = list.get(0) ;
	map.put("id", aCard[0]) ;
	map.put("doctorCase", aCard[2]) ;
	map.put("department", aCard[3]) ;
	map.put("mkb", aCard[6]) ;
	map.put("kind", aCard[1]) ;
	map.put("patient", aCard[4]) ;
	map.put("numberMedCard", aCard[5]) ;
	map.put("zavMark", aCard[9]) ;
	map.put("expertMark", aCard[10]) ;
	map.put("kepMark", aCard[11]) ;
	map.put("createDate", aCard[7]) ;
	map.put("createUsername", aCard[8]) ;
	return map ;
}