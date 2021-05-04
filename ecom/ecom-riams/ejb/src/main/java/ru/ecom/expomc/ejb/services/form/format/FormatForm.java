package ru.ecom.expomc.ejb.services.form.format;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expomc.ejb.domain.format.Format;
import ru.ecom.expomc.ejb.services.form.impdoc.ImportDocumentForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

import javax.persistence.Id;

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
    public long getId() { return id ; }
    public void setId(long aId) { id = aId ; }

    /** Дата с которой начинает действовать формат */
    @DateString
    @Persist
    @Required
    public String getActualDateFrom() { return actualDateFrom ; }
    public void setActualDateFrom(String aActualDateFrom) { actualDateFrom = aActualDateFrom ; }

    /** Дата, до которой формат действует */
    @DateString
    @Persist
    public String getActualDateTo() { return actualDateTo ; }
    public void setActualDateTo(String aActualDateTo) { actualDateTo = aActualDateTo ; }

    /** Комментарий к формату */
    @Persist
    public String getComment() { return comment ; }
    public void setComment(String aComment) { comment = aComment ; }

    /** Отключен */
    @Persist
    public boolean isDisabled() { return disabled ; }
    public void setDisabled(boolean aDisabled) { disabled = aDisabled ; }

    /** Документ */
    @Persist
    public long getDocument() { return document ; }
    public void setDocument(long aDocument) { document = aDocument ; }

    /** Документ */
    private long document ;
    /** Отключен */
    private boolean disabled ;
    /** Комментарий к формату */
    private String comment ;
    /** Дата, до которой формат действует */
    private String actualDateTo ;
    /** Дата с которой начинает действовать формат */
    private String actualDateFrom ;
    /** Идентификатор */
    private long id ;

}
