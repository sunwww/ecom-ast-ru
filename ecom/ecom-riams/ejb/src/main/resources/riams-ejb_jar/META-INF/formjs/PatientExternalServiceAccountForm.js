/**
 * Created by vtsybulin on 23.05.2017.
 */
function onPreCreate(aForm, aCtx) {
    checkFields(aForm, aCtx);
    var pat = aForm.getPatient() ;
    var username = aCtx.getSessionContext().getCallerPrincipal().toString() ;
    var date = new java.util.Date() ;
    aForm.setDateStart(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date));
    aForm.setUsername(username);
    aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date));

    var list = aCtx.manager.createQuery("select id from PatientExternalServiceAccount where patient_id= :pat and dateTo is null"
    ).setParameter("pat",pat).getResultList() ;
    if (!list.isEmpty()&&list.size()>0) {
        throw "У пациента уже есть неотозванное согласие на передачу. Создание нового невозможно";
    }
    aCtx.manager.createNativeQuery("update patient set isTransferAgreementExist='1' where id="+pat).executeUpdate();

}

function onPreSave(aForm, aEntity, aCtx) {
    checkFields(aForm, aCtx);
    if (aForm.getDisabled()!=null&&aForm.getDisabled()=="1") {
        var date = new java.util.Date() ;
        aForm.setDateTo(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date));
        var username = aCtx.getSessionContext().getCallerPrincipal().toString() ;
        aForm.setEditUsername(username);
        aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date));
    }
}
function checkFields(aForm, aCtx) {
    if ((aForm.getPhoneNumber==null ||aForm.getPhoneNumber().trim()=="")&&(aForm.getEmail()==null||aForm.getEmail().trim()=="")) {
        throw "Необходимо указать либо номер мобильного телефона либо адрес электронной почты ";

    }
}
    function onCreate(aForm, aEntity, aCtx) {
        var bean = new Packages.ru.ecom.diary.ejb.service.template.TemplateProtocolServiceBean();
        bean.registerPatientExternalResource(aEntity.getId(), aCtx.manager);
        if (aEntity.getExportAllHistory()) {
            if (aEntity.getExternalCode()!=null){
                bean.sendPatientMedicalHistoryToExternalResource(aEntity.getId(), aCtx.manager);
            } else {
                throw "Нет кода пациента, выгрузка невозможна";
            }
        }
        //aCtx.manager.createQuery("update ")

    }
    function onSave(aForm, aEntity, aCtx) {
    onCreate(aForm, aEntity, aCtx);
    }

    function onPreDelete (aEntityId, aCtx) {
    throw "У вас стоит запрет на удаление согласия";
        //aCtx.manager.createQuery("update patient set isTransferAgreementExist='0' where id=(select patient_id from PatientExternalServiceAccount where id="+pat+" )").executeUpdate();

    }