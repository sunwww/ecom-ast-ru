package ru.ecom.mis.ejb.form.userdocument;

import lombok.Setter;
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
@Setter
public class DynamicDocumentForm extends IdEntityForm {
    /** Тип документа */
    @Comment("Тип документа")
    @Persist @Required
    public Long getType() {return type;}
    private Long type ;

    /** Содержимое документа */
    @Comment("Содержимое документа")
    @Persist
    public String getContent() {return content;}
    private String content ;

    /** Случай мед. обслуживания */
    @Comment("Случай мед. обслуживания")
    @Persist @Required
    public Long getMedCase() {return medCase;}
    private Long medCase ;

    /** Дата создания */
    @Comment("Дата создания")
    @Persist @DateString
    public String getCreateDate() {return createDate;}
    private String createDate;

    /** Пользователь, создавший запись */
    @Comment("Пользователь, создавший запись")
    @Persist
    public String getCreateUsername() {return createUsername;}
    private String createUsername;

    /** Время создания */
    @Comment("Время создания")
    @Persist @TimeString
    public String getCreateTime() {return createTime;}
    private String createTime ;

}
