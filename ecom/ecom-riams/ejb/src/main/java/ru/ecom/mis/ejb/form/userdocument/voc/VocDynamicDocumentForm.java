package ru.ecom.mis.ejb.form.userdocument.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.userdocument.voc.VocDynamicDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Справочник видов динамических документов
 */
@EntityForm
@EntityFormPersistance(clazz = VocDynamicDocument.class)
@Comment("Справочник видов динамических документов")
@WebTrail(comment = "Справочник видов динамических документов", nameProperties = "id", view = "entityView-voc_dynamicDocument.do")
@EntityFormSecurityPrefix("/Policy/Mis/UserDocument")
public class VocDynamicDocumentForm extends IdEntityForm {
    /** Содержимое формы */
    @Comment("Содержимое формы")
    @Persist @Required
    public String getContent() {return theContent;}
    public void setContent(String aContent) {theContent = aContent;}
    private String theContent ;

    /** Название */
    @Comment("Наименование")
    @Persist @Required
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }
    private String theName ;

    /** код */
    @Comment("код")
    @Persist @Required
    public String getCode() {
        return theCode;
    }
    public void setCode(String aCode) {
        theCode = aCode;
    }
    private String theCode;
}
