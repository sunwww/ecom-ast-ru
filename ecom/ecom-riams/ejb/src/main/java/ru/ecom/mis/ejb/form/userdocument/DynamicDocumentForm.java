package ru.ecom.mis.ejb.form.userdocument;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.userdocument.DynamicDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Динамический документ
 */
@EntityForm
@EntityFormPersistance(clazz = DynamicDocument.class)
@Comment("Динамический документ")
@WebTrail(comment = "Динамический документ", nameProperties = "id", view = "entityView-mis_dynamicDocument.do")
@EntityFormSecurityPrefix("/Policy/Mis/UserDocument")
public class DynamicDocumentForm extends IdEntityForm {
    /** Тип документа */
    @Comment("Тип документа")
    @Persist @Required
    public Long getType() {return theType;}
    public void setType(Long aType) {theType = aType;}
    private Long theType ;

    /** Содержимое документа */
    @Comment("Содержимое документа")
    @Persist
    public String getContent() {return theContent;}
    public void setContent(String aContent) {theContent = aContent;}
    private String theContent ;

    /** Случай мед. обслуживания */
    @Comment("Случай мед. обслуживания")
    @Persist @Required
    public Long getMedCase() {return theMedCase;}
    public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}
    private Long theMedCase ;

    /** Дата создания */
    @Comment("Дата создания")
    @Persist @DateString
    public String getCreateDate() {return theCreateDate;}
    public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
    private String theCreateDate;

    /** Пользователь, создавший запись */
    @Comment("Пользователь, создавший запись")
    @Persist
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    private String theCreateUsername;

    /** Время создания */
    @Comment("Время создания")
    @Persist @TimeString
    public String getCreateTime() {return theCreateTime;}
    public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
    private String theCreateTime ;

}
