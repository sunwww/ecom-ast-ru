var critsMap = new java.util.HashMap() ;
function onPreCreate(aForm, aCtx) {
    var date = new java.util.Date() ;
    aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
    aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}

function onCreate(aForm, aEntity, aContext){
///set comment
    aEntity.setIsDraft(true);
    var crits = aForm.criterions.split("#") ;
    if (crits.length>0 && aForm.criterions!=null && aForm.criterions !="") {
        for (var i=0; i<crits.length; i++) {
            var param = crits[i].split(":") ;
            var qec = new Packages.ru.ecom.mis.ejb.domain.expert.QualityEstimationCrit() ;
            var mark = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.expert.voc.VocQualityEstimationMark,java.lang.Long.valueOf(param[1]));
            var crit = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.expert.voc.VocQualityEstimationCrit,java.lang.Long.valueOf(param[0]));
            if (param[3]!=null) {
                qec.setComment(param[3]);
            }
            qec.setMark(mark) ;
            qec.setEstimation(aEntity) ;
            qec.setCriterion(crit) ;
            aContext.manager.persist(qec) ;
            if (param.length>2&&(""+param[2]!="")) { //Проставляем дефекты
                var defects = (""+param[2]).split(",");

                for (var j=0;j<defects.length;j++) {
                    var def = new Packages.ru.ecom.mis.ejb.domain.expert.QualityEstimationCritDefect();
                    def.setCriterion(qec.id);
                    def.setDefect(+defects[j]);
                    aContext.manager.persist(def) ;
                }
            }
            critsMap.put(+mark.criterion.id,(mark.isIgnore!=null&&mark.isIgnore.equals(java.lang.Boolean.TRUE))?null:mark.mark) ;
        }

        var list1 = aContext.manager.createNativeQuery("select vqec.id as vqecid "
            +"\n ,list(''||vqecC.id)"+"\n "
            +"\n from VocQualityEstimationCrit vqec"
            +"\n left join VocQualityEstimationMark vqem on vqem.criterion_id=vqec.id"
            +"\n left join VocQualityEstimationCrit vqecC on vqecC.parent_id=vqec.id"
            +"\n WHERE vqec.kind_id=1"+"\n and vqec.parent_id is not null"
            +"\n "+"\n group by vqec.id"+"\n having count(vqem.id)=0").getResultList() ;
        saveParentCriterion(list1,aEntity,aContext) ;
        list1 = aContext.manager.createNativeQuery("select vqec.id as vqecid "
            +"\n ,list(''||vqecC.id)"+"\n "
            +"\n from VocQualityEstimationCrit vqec"
            +"\n left join VocQualityEstimationMark vqem on vqem.criterion_id=vqec.id"
            +"\n left join VocQualityEstimationCrit vqecC on vqecC.parent_id=vqec.id"
            +"\n WHERE vqec.kind_id=1"+"\n and vqec.parent_id is null"
            +"\n "+"\n group by vqec.id"+"\n having count(vqem.id)=0").getResultList() ;
        saveParentCriterion(list1,aEntity,aContext) ;
    } else {
        throw "-----------";
    }
}
function saveParentCriterion(aList,aEntity,aContext) {
    for (var i=0;i<aList.size();i++) {
        var obj = aList.get(i) ;
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
                sumBD = sumBD.divide(cntBD,2,java.math.BigDecimal.ROUND_HALF_UP) ;
                qec.setMarkTransient(sumBD) ;
                critsMap.put(+crit.id,sumBD.doubleValue()) ;
            } else {
                qec.setMarkTransient(new java.math.BigDecimal(0)) ;
                critsMap.put(+crit.id,null) ;
            }
        }
        aContext.manager.persist(qec) ;
    }

}
function onSave(aForm, aEntity, aContext){
    var id=aForm.id ;
    aContext.manager.createNativeQuery("delete from QualityEstimationCritDefect qecd where criterion in (select id from QualityEstimationCrit qec where qec.estimation_id='"+id+"')").executeUpdate() ;
    aContext.manager.createNativeQuery("delete from QualityEstimationCrit where estimation_id='"+id+"'").executeUpdate() ;
    onCreate(aForm, aEntity, aContext) ;
}
function onPreSave(aForm,aEntity , aCtx) {
}