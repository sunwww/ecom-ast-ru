/**
* Поиск
*/
function findListCard(aContext, aId, aCnt, aNext) {
	var list ;
	var ret = new java.util.ArrayList() ;
	var sql ="select card.id,vek.name,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename,d.name,p.lastname||' '||p.firstname||' '||p.middlename"
	+" ,case when md.DTYPE='HospitalMedCase' then 'СЛС №'||ss1.code  when md.DTYPE='DepartmentMedCase' then 'СЛО '||md.id||' СЛС №'||ss2.code  when md.DTYPE='PolyclinicMedCase' then 'СПО №'||md.id else 'СМО №'||md.id end "
	+" ,mkb.code,card.createDate,card.createUsername "
	+" ,(select +round(avg(vqem.mark),2) from QualityEstimationCrit qecB left join vocqualityEstimationMark vqem on vqem.id=qecB.mark_id where qecB.estimation_id=qeB.id), (select +round(avg(vqem.mark),2) from QualityEstimationCrit qecB  left join vocqualityEstimationMark vqem on vqem.id=qecB.mark_id where qecB.estimation_id=qeE.id), (select +round(avg(vqem.mark),2) from QualityEstimationCrit qecB  left join vocqualityEstimationMark vqem on vqem.id=qecB.mark_id where qecB.estimation_id=qeC.id)"
	+" from QualityEstimationCard card"
	+" left join VocQualityEstimationKind vek on vek.id=card.kind_id"
	+" left join WorkFunction wf on wf.id=card.doctorCase_id"
	+" left join Worker w on w.id=wf.worker_id"
	+" left join Patient wp on wp.id=w.person_id"
	+" left join Patient p on p.id=card.patient_id"
	+" left join VocIdc10 mkb on mkb.id=card.idc10_id"
	+" left join MisLpu d on d.id=card.department_id"
	+" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id"
	+" left join MedCase md on md.id=card.medcase_id"
	+" left join MedCase mh on mh.id=md.parent_id"
	+" left join StatisticStub ss1 on ss1.id=md.statisticStub_id"
	+" left join StatisticStub ss2 on ss2.id=mh.statisticStub_id"
	+" left join qualityestimation qeB on card.id=qeB.card_id and qeB.expertType='BranchManager'  left join qualityestimation qeE on card.id=qeE.card_id and qeE.expertType='Expert'  left join qualityestimation qeC on card.id=qeC.card_id and qeC.expertType='Coeur'"
	
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
