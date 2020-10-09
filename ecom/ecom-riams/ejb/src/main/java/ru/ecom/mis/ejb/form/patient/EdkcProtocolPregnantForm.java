package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.*;
import ru.ecom.poly.ejb.form.interceptors.EdkcProtocolCreateInterceptor;
import ru.ecom.poly.ejb.form.interceptors.EdkcProtocolPreCreateInterceptor;
import ru.ecom.poly.ejb.form.interceptors.EdkcProtocolSaveInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Created by Milamesher on 07.10.2020.
 */
@Comment("Протокол ЕДКЦ беременной")
@EntityForm
@EntityFormPersistance(clazz = ru.ecom.poly.ejb.domain.protocol.Protocol.class)
@WebTrail(comment = "Протокол ЕДКЦ беременной", nameProperties = "id"
        , view = "entityParentView-edkcProtocolPregnant.do"
        ,list = "entityParentList-edkcProtocolPregnant.do"
)
@Parent(property = "obsSheet", parentForm = ObservationSheetPregnantForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheetPregnant")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(EdkcProtocolPreCreateInterceptor.class)
)
@ACreateInterceptors(
        @AEntityFormInterceptor(EdkcProtocolCreateInterceptor.class)
)
@ASaveInterceptors(
        @AEntityFormInterceptor(EdkcProtocolSaveInterceptor.class)
)
public class EdkcProtocolPregnantForm extends EdkcProtocolForm{
}
