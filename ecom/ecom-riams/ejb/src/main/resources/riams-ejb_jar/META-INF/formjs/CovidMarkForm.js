function onPreDelete(aEntityId,aCtx) {
    //если пациент уже выписан, удалять форму нельзя
    var isDischarged= aCtx.manager.createNativeQuery("select case when hmc.datefinish is null then '1' else '0' end from medcase hmc" +
        " left join covidmark c on c.medcase_id=hmc.id where c.id="+aEntityId).getResultList();
    if (isDischarged.size()>0 && isDischarged.get(0)=='0' && !aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/DeleteAdmin"))
        throw "Нельзя удалять форму оценки тяжести заболевания уже выписанного пациента!";
    aCtx.manager.createNativeQuery("delete from CovidMarkBadSost where covidMark_id="+aEntityId).executeUpdate() ;
}

function onCreate(aForm, aEntity, aCtx){
    //проверить на наличие в СЛС
    var isSingle= aCtx.manager.createNativeQuery("select id from covidmark where medcase_id=" + aForm.getMedCase()).getResultList();
    if (isSingle.size()>0)
        throw "Форма оценки тяжести уже создана в этой госпитализации!";
    var date = new java.util.Date() ;
    aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
    aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
    aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
    saveBadSost(aForm, aEntity, aCtx);
}

//сохранение нарушений сознания
function saveBadSost(aForm, aEntity, aCtx) {
    var badSostString=aForm.getBadSostString();
    if (badSostString!='') {
        var badSosts = badSostString.split(',');
        for (var i=0; i<badSosts.length; i++) {
            var vocBadSostId = java.lang.Long.valueOf(badSosts[i]);
            var badSost = new Packages.ru.ecom.mis.ejb.domain.medcase.CovidMarkBadSost();
            badSost.setCovidMark(aEntity);
            var vocBadSost = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.covidMarcVocs.VocBadSost, vocBadSostId);
            if (vocBadSost != null) {
                badSost.setBadSost(vocBadSost);
                aCtx.manager.persist(badSost);
            }
        }
    }
}

function onSave(aForm, aEntity, aCtx){
    var date = new java.util.Date() ;
    aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
    aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
    aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
    aCtx.manager.createNativeQuery("delete from CovidMarkBadSost where covidMark_id="+aEntity.id).executeUpdate() ;
    saveBadSost(aForm, aEntity, aCtx);
}
function onPreSave(aForm,aEntity ,aCtx) {
    /*if (!aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/EditAfterOut")
        && aEntity.getMedCase().getDateFinish()!=null)
            throw "Пациент выписан. Нельзя редактировать форму оценки тяжести заболевания в закрытом случае!";*/
}