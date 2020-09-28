function onPreDelete(aEntityId,aCtx) {
    //если пациент уже выписан, удалять форму нельзя
    var isDischarged= aCtx.manager.createNativeQuery("select case when (hmc.datefinish is null or hmc.dtype<>'HospitalMedCase') then '1' else '0' end from medcase hmc" +
        " left join oncologycase c on c.medcase_id=hmc.id where c.id="+aEntityId).getResultList();
    if (isDischarged.size()>0 && isDischarged.get(0)=='0' && !aCtx.getSessionContext().isCallerInRole("/Policy/Mis/Oncology/Case/DeleteAnyway"))
        throw "Нельзя удалять онкологическую форму уже выписанного пациента!";
    aCtx.manager.createNativeQuery("delete from oncologydirection where oncologycase_id="+aEntityId).executeUpdate() ;
    aCtx.manager.createNativeQuery("delete from oncologycontra where oncologycase_id="+aEntityId).executeUpdate() ;
    aCtx.manager.createNativeQuery("delete from oncologydiagnostic where oncologycase_id="+aEntityId).executeUpdate() ;
}
function onCreate(aForm, aEntity, aCtx){
    var format = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
    if (aForm.getDateBiops()!=null && aForm.getDateBiops()!='') aEntity.setDateBiops(new java.sql.Date(format.parse(aForm.getDateBiops()).getTime()));
    else aEntity.setDateBiops(null);
    if (aForm.getDateCons()!=null && aForm.getDateCons()!='') aEntity.setDateCons(new java.sql.Date(format.parse(aForm.getDateCons()).getTime()));
    else aEntity.setDateCons(null);
    //гистология
    var histString=aForm.getHistString();
    if (histString!='') {
        var row = histString.split("!");
        for (var i = 0; i < row.length; i++) {
            var vals = row[i].split("#");
            var diagn = new Packages.ru.ecom.oncological.ejb.domain.OncologyDiagnostic();
            diagn.setVocOncologyDiagType(aCtx.manager.find(Packages.ru.ecom.oncological.ejb.domain.voc.VocOncologyDiagType, java.lang.Long.valueOf(vals[0])));
            if (vals[0] == '1') {
                //получаем тип гистологии из ненормализованной таблицы
                var histRes = aCtx.manager.createNativeQuery("select id from VocOncologyN007 where (finishdate is null or finishdate>=current_date) and code=(select histology from VocOncologyN008 where (finishdate is null or finishdate>=current_date) and code='"+vals[2]+"')").getResultList();
                if (histRes.size()>0)
                    diagn.setHistiology(aCtx.manager.find(Packages.ru.ecom.oncological.ejb.domain.voc.VocOncologyN007, java.lang.Long.valueOf(histRes.get(0))));
                var res = aCtx.manager.createNativeQuery("select id from VocOncologyN008 where (finishdate is null or finishdate>=current_date) and  code='"+vals[2]+"'").getResultList();
                if (res.size()>0)
                    diagn.setResultHistiology(aCtx.manager.find(Packages.ru.ecom.oncological.ejb.domain.voc.VocOncologyN008, java.lang.Long.valueOf(res.get(0))));
            }
            else if (vals[0] == '2') {
                diagn.setMarkers(aCtx.manager.find(Packages.ru.ecom.oncological.ejb.domain.voc.VocOncologyN010, java.lang.Long.valueOf(vals[1])));
                var res = aCtx.manager.createNativeQuery("select id from VocOncologyN011 where (finishdate is null or finishdate>=current_date) and  code='"+vals[2]+"'").getResultList();
                if (res.size()>0)
                    diagn.setValueMarkers(aCtx.manager.find(Packages.ru.ecom.oncological.ejb.domain.voc.VocOncologyN011, java.lang.Long.valueOf(res.get(0))));
            }
            diagn.setOncologyCase(aEntity);
            aCtx.manager.persist(diagn);
        }
    }
    //прот-я и отказы
    var contraString=aForm.getContraString();
    if (contraString!='') {
        row = contraString.split("!");
        for (var i = 0; i < row.length; i++) {
            var vals = row[i].split("#");
            var contra = new Packages.ru.ecom.oncological.ejb.domain.OncologyContra();
            contra.setContraindicationAndRejection(aCtx.manager.find(Packages.ru.ecom.oncological.ejb.domain.voc.VocOncologyN001, java.lang.Long.valueOf(vals[0])));
            var date = format.parse(vals[1]);
            contra.setDate(new java.sql.Date(date.getTime()));
            contra.setOncologyCase(aEntity);
            aCtx.manager.persist(contra);
        }
    }
    //vocOncologyReasonTreat - повод обращения
    var rt = aForm.getVocOncologyReasonTreat();
    if (rt!=null) {
        var res = aCtx.manager.createNativeQuery("select id from vocOncologyReasonTreat where (finishdate is null or finishdate>=current_date) and code='"+rt+"'").getResultList();
        if (res.size()>0)
            aEntity.setVocOncologyReasonTreat(aCtx.manager.find(Packages.ru.ecom.oncological.ejb.domain.voc.VocOncologyReasonTreat, java.lang.Long.valueOf(res.get(0))));
    }
    //consilium
    var consilium = aForm.getConsilium();
    if (consilium!=null) {
        var res = aCtx.manager.createNativeQuery("select id from voconcologyconsilium where (finishdate is null or finishdate>=current_date) and  code='"+consilium+"'").getResultList();
        if (res.size()>0)
            aEntity.setConsilium(aCtx.manager.find(Packages.ru.ecom.oncological.ejb.domain.voc.VocOncologyConsilium, java.lang.Long.valueOf(res.get(0))));
    }
    //медикаменты
    var allMeds=aForm.getAllMeds();
    if (allMeds!='') {
        var obj = new Packages.org.json.JSONObject(allMeds) ;
        var meds = obj.getJSONArray("list");
        for (var i=0; i<meds.length(); i++) {
            var child = meds.get(i);
            var vocDrugId = java.lang.Long.valueOf(child.get("med"));
            var drug = new Packages.ru.ecom.oncological.ejb.domain.OncologyDrug();
            drug.setOncologyCase(aEntity);
            var vocDrug = aCtx.manager.find(Packages.ru.ecom.oncological.ejb.domain.voc.VocOncologyN020, vocDrugId);
            if (vocDrug != null) {
                drug.setDrug(vocDrug);
                aCtx.manager.persist(drug);
                for (var j = 0; j < meds.length(); j++) {
                    var childj = meds.get(j);
                    if (i == j) {
                        var drugDate = new Packages.ru.ecom.oncological.ejb.domain.OncologyDrugDate();
                        drugDate.setOncologyDrug(drug);
                        drugDate.setDate(new java.sql.Date(format.parse(java.lang.String.valueOf(childj.get("date"))).getTime()));
                        aCtx.manager.persist(drugDate);
                    }
                }
            }
        }
    }
}
function onSave(aForm, aEntity, aCtx){
    aCtx.manager.createNativeQuery("delete from oncologycontra where oncologycase_id="+aEntity.id).executeUpdate() ;
    aCtx.manager.createNativeQuery("delete from oncologydiagnostic where oncologycase_id="+aEntity.id).executeUpdate() ;
    aCtx.manager.createNativeQuery("delete from oncologydrugdate where oncologydrug_id=ANY(select id from oncologydrug where oncologycase_id="+aEntity.id+")").executeUpdate() ;
    aCtx.manager.createNativeQuery("delete from oncologydrug where oncologycase_id="+aEntity.id).executeUpdate() ;
   onCreate(aForm, aEntity, aCtx);
}
function getMedCaseType (aId, aCtx) {
    var list = aCtx.manager.createNativeQuery("select dtype from medcase where id="+aId).getResultList() ;
    return list.size()>0?""+list.get(0):"";
}
function onPreDelete(aEntityId, aCtx) {
    var zno = aCtx.manager.find(Packages.ru.ecom.oncological.ejb.domain.OncologyCase,new java.lang.Long(aEntityId));
    var dtype = getMedCaseType(zno.getMedCase().getId(),aCtx);
    if (!aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/EditAfterOut")
        && (dtype=='DepartmentMedCase' || dtype=='HospitalMedCase')) {
        var parent=(dtype=='DepartmentMedCase')? zno.getMedCase().getParent() : zno.getMedCase() ;
        if (parent.getDateFinish()!=null) throw "Пациент выписан. Нельзя удалять онкологический случай в закрытом СЛС!";
    }
}