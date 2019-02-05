function onPreDelete(aEntityId,aCtx) {
    aCtx.manager.createNativeQuery("delete from oncologydirection where oncologycase_id="+aEntityId).executeUpdate() ;
    aCtx.manager.createNativeQuery("delete from oncologycontra where oncologycase_id="+aEntityId).executeUpdate() ;
    aCtx.manager.createNativeQuery("delete from oncologydiagnostic where oncologycase_id="+aEntityId).executeUpdate() ;
}
function onCreate(aForm, aEntity, aCtx){
    var format = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
    if (aForm.getDateBiops()!=null && aForm.getDateBiops()!='') aEntity.setDateBiops(new java.sql.Date(format.parse(aForm.getDateBiops()).getTime()));
    if (aForm.getDateCons()!=null && aForm.getDateCons()!='') aEntity.setDateCons(new java.sql.Date(format.parse(aForm.getDateCons()).getTime()));
    //гистология
    var histString=aForm.getHistString();
    if (histString!='') {
        var row = histString.split("!");
        for (var i = 0; i < row.length; i++) {
            var vals = row[i].split("#");
            var diagn = new Packages.ru.ecom.oncological.ejb.domain.OncologyDiagnostic();
            diagn.setVocOncologyDiagType(aCtx.manager.find(Packages.ru.ecom.oncological.ejb.domain.voc.VocOncologyDiagType, java.lang.Long.valueOf(vals[0])));
            if (vals[0] == '1') {
                diagn.setHistiology(aCtx.manager.find(Packages.ru.ecom.oncological.ejb.domain.voc.VocOncologyN007, java.lang.Long.valueOf(vals[1])));
                var res = aCtx.manager.createNativeQuery("select id from VocOncologyN008 where code='"+vals[2]+"'").getResultList();
                if (res.size()>0)
                    diagn.setResultHistiology(aCtx.manager.find(Packages.ru.ecom.oncological.ejb.domain.voc.VocOncologyN008, java.lang.Long.valueOf(res.get(0))));
            }
            else if (vals[0] == '2') {
                diagn.setMarkers(aCtx.manager.find(Packages.ru.ecom.oncological.ejb.domain.voc.VocOncologyN010, java.lang.Long.valueOf(vals[1])));
                var res = aCtx.manager.createNativeQuery("select id from VocOncologyN011 where code='"+vals[2]+"'").getResultList();
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
}
function onSave(aForm, aEntity, aCtx){
    aCtx.manager.createNativeQuery("delete from oncologycontra where oncologycase_id="+aEntity.id).executeUpdate() ;
    aCtx.manager.createNativeQuery("delete from oncologydiagnostic where oncologycase_id="+aEntity.id).executeUpdate() ;
    onCreate(aForm, aEntity, aCtx);
}