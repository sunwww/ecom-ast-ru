function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;

	
}

function onCreate(aForm, aEntity, aContext){
	var crits = aForm.criterions.split("#") ;
	var critsMap = new java.util.HashMap() ;
	if (crits.length>0 && aForm.criterions!=null && aForm.criterions !="") {
		//var id = aEntity.id ;
			
		for (var i=0; i<crits.length; i++) {
			var param = crits[i].split(":") ;
			var qec = new Packages.ru.ecom.mis.ejb.domain.expert.QualityEstimationCrit() ;
			var mark = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.expert.voc.VocQualityEstimationMark
					,java.lang.Long.valueOf(param[1])) ;
			qec.setMark(mark) ;
			qec.setCriterion(mark.criterion) ;
			qec.setEstimation(aEntity) ;
			aContext.manager.persist(qec) ;
			critsMap.put(+mark.criterion.id,(mark.isIgnore!=null&&mark.isIgnor.equals(java.lang.Boolean.TRUE))?null:mark.mark) ;
			//aContext.manager.createNativeQuery("insert into QualityEstimationCrit (estimation_id,mark_id) values ('"+id+"','"+param[1]+"')").executeUpdate() ;
		}
		
		var list1 = aContext.manager.createNativeQuery("select vqec.id as vqecid "
			+"\n ,list(''||vqecC.id)"
			+"\n "
			+"\n from VocQualityEstimationCrit vqec" 
			+"\n left join VocQualityEstimationMark vqem on vqem.criterion_id=vqec.id"
			+"\n left join VocQualityEstimationCrit vqecC on vqecC.parent_id=vqec.id"
			+"\n WHERE vqec.kind_id=1"
			+"\n and vqec.parent_id is not null"
			+"\n "
			+"\n group by vqec.id"
			+"\n having count(vqem.id)=0").getResultList() ;
		for (var i=0;i<list1.size();i++) {
			var obj = list1.get(i) ;
			var crit = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.expert.voc.VocQualityEstimationCrit
					,java.lang.Long.valueOf(+obj[0]));
			var qec = new Packages.ru.ecom.mis.ejb.domain.expert.QualityEstimationCrit() ;
			qec.setCriterion(crit) ;
			qec.setEstimation(aEntity) ;
			
			var listCrit=(""+obj[1]).split(",");
			var sumCr = 0 ;
			var cntCr = 0 ;
			for (var j=0;j<listCrit.length;j++) {
				var cr = listCrit[j] ;
				cr = +cr ;
				if (critsMap.containsKey(cr) ) {
					var crVal = critsMap.get(cr) ;
					sumCr=sumCr+(crVal!=null?+crVal:0);
					cntCr=cntCr+(crVal!=null?1:0);
				} else {
					cntCr=cntCr+1 ;
				}
			}
			if (cntCr>0) {
				if (sumCr>0) {
					//throw cntCr+" -- "+sumCr ;
					cntBD = new java.math.BigDecimal(cntCr) ;
					sumBD = new java.math.BigDecimal(sumCr) ;
					qec.setMarkTransient(sumBD.divide(cntBD)) ;
				} else {
					qec.setMarkTransient(new java.math.BigDecimal(0)) ;
				}
			}
			aContext.manager.persist(qec) ;
		}
	}
}
function onSave(aForm, aEntity, aContext){
	var id=aForm.id ;
	aContext.manager.createNativeQuery("delete from QualityEstimationCrit where estimation_id='"+id+"'").executeUpdate() ;
	onCreate(aForm, aEntity, aContext) ;
	
}
function onPreSave(aForm,aEntity , aCtx) {

}