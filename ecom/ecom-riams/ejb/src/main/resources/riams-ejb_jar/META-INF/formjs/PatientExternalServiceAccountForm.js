/**
 * Created by vtsybulin on 23.05.2017.
 */
function onPreCreate(aForm, aCtx) {
    checkFields(aForm, aCtx);
    var pat = aForm.getPatient() ;
    var username = aCtx.getSessionContext().getCallerPrincipal().toString() ;
    var date = Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(new java.util.Date()) ;
    aForm.setDateStart(date);
    aForm.setUsername(username);
    aForm.setCreateDate(date);

    var list = aCtx.manager.createQuery("select id from PatientExternalServiceAccount where patient_id= :pat and dateTo is null"
    ).setParameter("pat",pat).getResultList() ;
    if (!list.isEmpty()&&list.size()>0) {
        throw "У пациента уже есть неотозванное согласие на передачу. Создание нового невозможно";
    }
  //  aCtx.manager.createNativeQuery("update patient set isTransferAgreementExist='1' where id="+pat).executeUpdate();

}

function onPreSave(aForm, aEntity, aCtx) {
    checkFields(aForm, aCtx);
    var date =Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(new java.util.Date()) ;
    if (aForm.getDisabled()!=null&&aForm.getDisabled()=="1") {
        aForm.setDateTo(date);
    }
    var username = aCtx.getSessionContext().getCallerPrincipal().toString() ;
    aForm.setEditUsername(username);
    aForm.setEditDate(date);
}
function checkFields(aForm, aCtx) {
    var phone = aForm.getPhoneNumber().trim();
    var email = aForm.getEmail().trim();
    var listSql = "",errorMessage="";

    if (aForm.getId()!=null&&+aForm.getId()>0) {
        listSql+=" id!="+aForm.getId()+" and ";
    }

    if (phone!=null&&phone!=""){
        phone = checkSetPhone(phone);
        aForm.setPhoneNumber(phone);
        listSql+=" phonenumber='"+phone+"'";
        errorMessage="телефоном";
    } else if (email!=null&&email!=""){
        email = setCheckEmail(email);
        aForm.setEmail(email);
        listSql+=" email='"+email+"'";
        errorMessage="адресом электронной почты";
    } else {
        throw "Необходимо указать либо номер мобильного телефона либо адрес электронной почты ";
    }
    var list = aCtx.manager.createNativeQuery("select id from PatientExternalServiceAccount where "+listSql+" and dateTo is null").getResultList();
    if (!list.isEmpty()) {
        throw "В системе уже существует учетная запись с указанным "+errorMessage;
    }
}
    function onCreate(aForm, aEntity, aCtx) {
    throw "Создание запрещено системой! обратитесь к разработчикам"; //Отключаем личный кабинет пациента

    }
    function onSave(aForm, aEntity, aCtx) {
        throw "Создание запрещено системой! обратитесь к разработчикам"; //Отключаем личный кабинет пациента
        //var username = aCtx.getSessionContext().getCallerPrincipal().toString() ;
        /**
         * TODO
         * При изменении номера телефона необходимо отвязать старый номер, на этого же пациента привязать новый номер. Вести журнал изменения номеров
         */
}

    function onPreDelete (aEntityId, aCtx) {
    throw "У вас стоит запрет на удаление согласия";
        //aCtx.manager.createQuery("update patient set isTransferAgreementExist='0' where id=(select patient_id from PatientExternalServiceAccount where id="+pat+" )").executeUpdate();

    }
    function checkSetPhone (aPhone) {
    aPhone = aPhone.trim();
        if (aPhone.startsWith("8")) {
            aPhone = "+7"+ aPhone.substring(1);
        } else if (aPhone.length==10) {
            aPhone="+7"+aPhone;
        } else if (!aPhone.startsWith("+")) {
            aPhone ="+"+aPhone;
        }

        aPhone = aPhone.split(" ").join("");
        aPhone = aPhone.split("-").join("");
        var regexpPhone = /^\+\d{11}$/;
        if (!regexpPhone.test(aPhone)) {
            throw "Неправильно указан номер телефона. Пример: +79170000000";
        }
    return aPhone;

    }
    function setCheckEmail (aEmail) {
        if (aEmail != null && aEmail.trim() != "") {
            aEmail = aEmail.trim();
            var regexpEmail = /^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$/;
            if (!regexpEmail.test(aEmail)) {
                throw "Неправильно заполнен адрес электронной почты";
            }
        }
        return aEmail;
    }