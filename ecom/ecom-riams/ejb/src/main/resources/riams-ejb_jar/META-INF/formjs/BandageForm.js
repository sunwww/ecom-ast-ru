function onCreate(aForm, aBandage, aCtx) {
    if (aBandage.getMedCase().getPatient()!=null) {
        aBandage.patient = aBandage.getMedCase().getPatient() ;
    } else {
        if (aBandage.getMedCase().getParent()!=null && aBandage.getMedCase().getParent().getPatient()!=null)
            aBandage.patient = aBandage.getMedCase().getParent().getPatient() ;
    }
    if (+aForm.isAnesthesia>0) {
        var ch = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.voc.VocYesNo,aForm.isAnesthesia) ;
        if (ch!=null && ch.code.equals("1")) {
            if (+aForm.anaesthetist>0 && +aForm.anesthesia>0 && +aForm.anesthesiaType>0 && +aForm.anesthesiaDuration>0) {
                var anes = new Packages.ru.ecom.mis.ejb.domain.medcase.Anesthesia() ;
                var anesthetist = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction
                    ,aForm.anaesthetist) ;
                var anesthesia = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocAnesthesiaMethod
                    ,aForm.anesthesia) ;
                var anesthesiaType = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocAnesthesia
                    ,aForm.anesthesiaType) ;
                var anesthesiaService = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedService
                    ,aForm.anesthesiaService) ;
                anes.setMethod(anesthesia) ;
                anes.setType(anesthesiaType) ;
                anes.setMedService(anesthesiaService) ;
                anes.setAnesthesist(anesthetist) ;
                anes.setDuration(+aForm.anesthesiaDuration) ;
                anes.setManipulation(aBandage) ;
                var date = new java.util.Date() ;
                anes.setCreateDate(new java.sql.Date(date.getTime())) ;
                anes.setCreateTime(new java.sql.Time (date.getTime())) ;
                anes.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;

                aCtx.manager.persist(anes) ;


                aBandage.setAnesthesia(anesthesiaType);
            } else {
                throw "Не заполнены данные по анастезии" ;
            }
        }
    }
}

function onPreSave(aForm,aEntity, aCtx) {
    var date = new java.util.Date();
    aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
    //aForm.setEditTime(new java.sql.Time (date.getTime())) ;
    aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
    checkPeriod(aForm,aCtx) ;
}
function onPreCreate(aForm, aCtx) {
   // throw ""+aForm.the;
   //throw "precreate";
    var date = new java.util.Date();
    aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
    //aForm.setEditTime(new java.sql.Time (date.getTime())) ;
    aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
    checkPeriod(aForm,aCtx) ;
    if (+aForm.isAnesthesia>0) {
        var ch = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.voc.VocYesNo,aForm.isAnesthesia) ;
        if (ch!=null && ch.code.equals("1")) {
            if (+aForm.anaesthetist>0 && +aForm.anesthesia>0 && +aForm.anesthesiaType>0 && +aForm.anesthesiaDuration>0) {

            } else {
                throw "Необходимо заполнить все обязательные поля по анестезии!!!" ;
            }
        }
    } else {throw "Необходимо указать была анестезия или нет!!!" ;}

}
function checkPeriod(aForm,aCtx) {
    aManager = aCtx.manager ;
    var isCreateAnyTime = aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/SurOper/CreateAnyTime") ;
    if (!isCreateAnyTime) {
        Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.SecPolicy.checkPolicyCreateHour(aCtx.getSessionContext()
            , aForm.getStartDate(), aForm.getStartTime());
    }
    var theStartDate = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aForm.getStartDate()) ;
    var medCase = aManager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase
        ,aForm.medCase) ;
    if (medCase.dateStart.getTime()>theStartDate.getTime()) {
        throw "Дата перевязки "+Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(theStartDate)+" должна быть больше или равна началу СМО "
        +Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(medCase.dateStart) ;
    }

}
function onSave(aForm, aCtx) {

}
function getMedCaseType (aId, aCtx) {
    var list = aCtx.manager.createNativeQuery("select dtype from medcase where id="+aId).getResultList() ;
    return list.size()>0?""+list.get(0):"";
}
function onPreDelete(aEntityId, aCtx) {
    var b = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Bandage,new java.lang.Long(aEntityId));
    var dtype = getMedCaseType(b.getMedCase().getId(),aCtx);
    if (!aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/EditAfterOut")
        && (dtype=='DepartmentMedCase' || dtype=='HospitalMedCase')) {
        var parent=(dtype=='DepartmentMedCase')? b.getMedCase().getParent() : b.getMedCase() ;
        if (parent.getDateFinish()!=null) throw "Пациент выписан. Нельзя удалять перевязку в закрытом СЛС!";
    }
    aCtx.manager.createNativeQuery("delete from anesthesia where manipulation_id="+aEntityId).executeUpdate() ;
}