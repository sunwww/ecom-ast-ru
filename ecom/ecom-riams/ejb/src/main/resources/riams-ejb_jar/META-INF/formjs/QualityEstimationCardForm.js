function onPreCreate(aForm, aCtx) {
    var date = new java.util.Date();
    aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date));
    aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString());

    var res = aCtx.manager.createNativeQuery("select case when code='PR203' then '1' else '0' end  from  VocQualityEstimationKind vqec where id=" + aForm.getKind()).getResultList();
    if (res.size()>0){
        if (res.get(0) == "1") {
          var list = aCtx.manager.createNativeQuery("select count(*) \n" +
                "from VocQualityEstimationCrit vqec \n" +
                "left join VocQualityEstimationMark vqem on vqem.criterion_id=vqec.id \n" +
                "left join vocqualityestimationcrit_diagnosis vqecrit_d on vqecrit_d.vqecrit_id=vqec.id\n" +
                " left join vocidc10 d on d.id=vqecrit_d.vocidc10_id  \n" +
                "  left join medcase mcase on mcase.id="+aForm.getMedcase()+"\n" +
                " left join qualityestimationcard qecard on qecard.id=qecard.medcase_id\n" +
                "   left join diagnosis ds on ds.medcase_id=mcase.id  \n" +
                "   left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id  \n" +
              "  left join patient pat on pat.id=mcase.patient_id " +
                "   left join vocprioritydiagnosis prior on prior.id=ds.priority_id  where vqec.kind_id='5'\n" +
              "  and (EXTRACT(YEAR from AGE(pat.birthday))>=18 and vqec.isgrownup=true or EXTRACT(YEAR from AGE(pat.birthday))<18 and vqec.ischild=true)\n" +
                "and ds.idc10_id=vqecrit_d.vocidc10_id  and reg.code='4' and prior.code='1' \n").getResultList();
            if (list.size() > 0)
                if (list.get(0) == "0")
                    throw("Не найдены критерии оценки качества по 203 приказу (диагноз этого СЛС не входит в перечень 203 приказа, либо критерии не соответствуют возрасту пациента), поэтому данный вид оценки качества в этом СЛС создать нельзя!");
            else {
                //проверка на имеется ли карта уже

                }
           var ids= aCtx.manager.createNativeQuery("select id from qualityestimation where card_id=ANY(select id from qualityestimationcard  where medcase_id=" + aForm.getMedcase() + " and kind_id=5)").getResultList();
            if (ids.size() > 0) {
                throw("По этому случаю уже есть экспертная карта по 203 приказу (с черновиком от врача отделения):"+
                    "<a href='entityParentEdit-expert_qualityEstimation.do?id="+ids.get(0)+"&type=BranchManager'>Перейти к ней</a>");
            }
        }
    }
}