function onPreDelete(aEntityId,aCtx) {
    //если пациент уже выписан, удалять форму нельзя
    var isDischarged= aCtx.manager.createNativeQuery("select case when hmc.datefinish is null then '1' else '0' end from medcase hmc" +
        " left join covidmark c on c.medcase_id=hmc.id where c.id="+aEntityId).getResultList();
    if (isDischarged.size()>0 && isDischarged.get(0)=='0' && !aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/DeleteAdmin"))
        throw "Нельзя удалять форму оценки тяжести заболевания уже выписанного пациента!";
    aCtx.manager.createNativeQuery("delete from CovidMarkSozn where covidMark_id="+aEntityId).executeUpdate() ;
}

function onCreate(aForm, aEntity, aCtx){
    var date = new java.util.Date() ;
    aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
    aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
    aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
    saveSozn(aForm, aEntity, aCtx);
}

//сохранение нарушений сознания
function saveSozn(aForm, aEntity, aCtx) {
    var soznString=aForm.getSoznString();
    if (soznString!='') {
        var sozns = soznString.split(',');
        for (var i=0; i<sozns.length; i++) {
            var vocSoznId = java.lang.Long.valueOf(sozns[i]);
            var sozn = new Packages.ru.ecom.mis.ejb.domain.medcase.CovidMarkSozn();
            sozn.setCovidMark(aEntity);
            var vocSozn = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.covidMarcVocs.VocSozn, vocSoznId);
            if (vocSozn != null) {
                sozn.setSozn(vocSozn);
                aCtx.manager.persist(sozn);
            }
        }
    }
}

function onSave(aForm, aEntity, aCtx){
    var date = new java.util.Date() ;
    aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
    aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
    aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
    aCtx.manager.createNativeQuery("delete from CovidMarkSozn where covidMark_id="+aEntity.id).executeUpdate() ;
    saveSozn(aForm, aEntity, aCtx);
}
function onPreSave(aForm,aEntity ,aCtx) {
    /*if (!aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/EditAfterOut")
        && aEntity.getMedCase().getDateFinish()!=null)
            throw "Пациент выписан. Нельзя редактировать форму оценки тяжести заболевания в закрытом случае!";*/
}