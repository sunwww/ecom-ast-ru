package ru.ecom.mis.ejb.form.userdocument.voc;

import lombok.Setter;
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
@Setter
public class VocDynamicDocumentForm extends IdEntityForm {
    /** Содержимое формы */
    @Comment("Содержимое формы")
    @Persist @Required
    public String getContent() {return content;}
    private String content ;

    /** Название */
    @Comment("Наименование")
    @Persist @Required
    public String getName() { return name ; }
    private String name ;

    /** код */
    @Comment("код")
    @Persist @Required
    public String getCode() {
        return code;
    }
    private String code;
}
