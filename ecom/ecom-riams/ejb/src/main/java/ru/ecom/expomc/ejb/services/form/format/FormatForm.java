package ru.ecom.expomc.ejb.services.form.format;

import javax.persistence.Id;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expomc.ejb.domain.format.Format;
import ru.ecom.expomc.ejb.services.form.impdoc.ImportDocumentForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Форма для формата
 */
@EntityForm
@EntityFormPersistance(clazz=Format.class)
@Comment("Формат импорта")
@Parent(property = "document", parentForm= ImportDocumentForm.class)
@WebTrail(comment = "Формат", nameProperties={"comment"}, view="entityParentEdit-exp_format.do")
@EntityFormSecurityPrefix("/Policy/Exp/Format")
public class FormatForm extends IdEntityForm {

    /** Идентификатор */
    @Id
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }

    /** Дата с которой начинает действовать формат */
    @DateString
    @Persist
    @Required
    public String getActualDateFrom() { return theActualDateFrom ; }
    public void setActualDateFrom(String aActualDateFrom) { theActualDateFrom = aActualDateFrom ; }

    /** Дата, до которой формат действует */
    @DateString
    @Persist
    public String getActualDateTo() { return theActualDateTo ; }
    public void setActualDateTo(String aActualDateTo) { theActualDateTo = aActualDateTo ; }

    /** Комментарий к формату */
    @Persist
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Отключен */
    @Persist
    public boolean isDisabled() { return theDisabled ; }
    public void setDisabled(boolean aDisabled) { theDisabled = aDisabled ; }

    /** Документ */
    @Persist
    public long getDocument() { return theDocument ; }
    public void setDocument(long aDocument) { theDocument = aDocument ; }

    /** Документ */
    private long theDocument ;
    /** Отключен */
    private boolean theDisabled ;
    /** Комментарий к формату */
    private String theComment ;
    /** Дата, до которой формат действует */
    private String theActualDateTo ;
    /** Дата с которой начинает действовать формат */
    private String theActualDateFrom ;
    /** Идентификатор */
    private long theId ;

}
