function onPreCreate(aForm, aCtx) {
    var date = new java.util.Date();
    aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date));
    aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
    var res = aCtx.manager.createNativeQuery("select case when code='PR203' then '1' else '0' end  from  VocQualityEstimationKind vqec where id=" + aForm.getKind()).getResultList();
    if (res.size()>0){
        if (res.get(0) == "1") {
          var list = aCtx.manager.createNativeQuery("select count(*)" +
                " from VocQualityEstimationCrit vqec" +
                " left join VocQualityEstimationMark vqem on vqem.criterion_id=vqec.id" +
                " left join vocqualityestimationcrit_diagnosis vqecrit_d on vqecrit_d.vqecrit_id=vqec.id" +
                " left join vocidc10 d on d.id=vqecrit_d.vocidc10_id " +
                " left join medcase mcase on mcase.id="+aForm.getMedcase() +
                " left join qualityestimationcard qecard on qecard.id=qecard.medcase_id" +
                " left join diagnosis ds on ds.medcase_id=mcase.id" +
                " left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id" +
                " left join patient pat on pat.id=mcase.patient_id" +
                " left join vocprioritydiagnosis prior on prior.id=ds.priority_id  where vqec.kind_id='5'" +
                " and (EXTRACT(YEAR from AGE(pat.birthday))>=18 and vqec.isgrownup=true or EXTRACT(YEAR from AGE(pat.birthday))<18 and vqec.ischild=true)" +
                " and ds.idc10_id=vqecrit_d.vocidc10_id  and reg.code='4' and prior.code='1'").getResultList();
            if (list.size() > 0)
                if (list.get(0) == "0")
                    throw("Не найдены критерии оценки качества по 203 приказу (диагноз этого СЛС не входит в перечень 203 приказа, либо критерии не соответствуют возрасту пациента), поэтому данный вид оценки качества в этом СЛС создать нельзя!");
            checkIfDischarge(aForm, aCtx);
            //проверка на имеется ли карта уже
           var ids= aCtx.manager.createNativeQuery("select id from qualityestimation where card_id=ANY(select id from qualityestimationcard  where medcase_id=" + aForm.getMedcase() + " and kind_id=5)").getResultList();
            if (ids.size() > 0) {
                throw("По этому случаю уже есть экспертная карта по 203 приказу (с черновиком от врача отделения):"+
                    "<a href='entityParentEdit-expert_qualityEstimation.do?id="+ids.get(0)+"&type=BranchManager' onclick=\"$('errorMessageContainer').style.display='none'\">Перейти к ней</a>");
            }
            //проверка на создание из-под СЛС, ане СЛО
            var ishmc = aCtx.manager.createNativeQuery("select case when dtype='HospitalMedCase' then '1' else '0' end from medcase where id="+aForm.getMedcase()).getResultList();
            if (ishmc.size()>0) {
                if (ishmc.get(0) == "1") {
                    var isallowed=aCtx.manager.createNativeQuery("select case when createinhmc=true then '1' else '0' end from vocqualityestimationkind where id="+ aForm.getKind()).getResultList();
                    if (isallowed.size()>0) {
                        if (isallowed.get(0) == "0") {
                            throw("Экспертную карту по 203 приказу можно создавать только для отделения (СЛО), не для всей госпитализации!");
                        }
                    }
                }
            }
        }
    }
}
function onSave(aForm, aEntity, aContext){
    checkIfDischarge(aForm, aContext);
}
function onCreate(aForm, aEntity, aContext) {
    checkIfDischarge(aForm, aContext);
}
//Milamesher проверка на выписан ли и не прошло ли больше допустимого времени #150
function checkIfDischarge(aForm, aCtx) {
    var resSlsFin = aCtx.manager.createNativeQuery("select case when hmc.datefinish<current_date-(select cast(keyvalue as int) from softconfig where key='Pr203DaysAfterDischarge')" +
        " and (select code from vocqualityestimationkind where id="+aForm.getKind()+")='PR203'" +
        " then '1' else '0' end" +
        " from medcase hmc" +
        " left join medcase slo on slo.parent_id=hmc.id" +
        " where slo.id=" + aForm.getMedcase()).getResultList();
    if (resSlsFin.size()>0) {
        if (resSlsFin.get(0) == "1" && !aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/EditAfterOut"))
            throw("Истёк срок с момента выписки пациента, во время которого можно создавать/редактировать экспертные карты по 203 приказу!");
    }
}