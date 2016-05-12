/**
    Форма редактирования формата свойств импорта
    IKO 0700308 +++
 */

package ru.ecom.expomc.ejb.services.form.importformat;

import javax.persistence.Column;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expomc.ejb.domain.format.ImportFormat;
import ru.ecom.expomc.ejb.services.form.impdoc.ImportDocumentForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@Comment("Формат импорта")
@EntityFormPersistance(clazz= ImportFormat.class)
@Parent(property = "document", parentForm= ImportDocumentForm.class)
@WebTrail(comment = "Формат", nameProperties={"comment"}, view="entityParentEdit-exp_importformat.do")
@EntityFormSecurityPrefix("/Policy/Exp/ImportFormat")

public class ImportFormatForm extends IdEntityForm {

    /** Дата с которой начинает действовать формат */
    @Persist
    @Required
    @DateString
    public String getActualDateFrom() { return theActualDateFrom ; }
    public void setActualDateFrom(String aActualDateFrom) { theActualDateFrom = aActualDateFrom ; }

    /** Дата, до которой формат действует */
    @Persist
    @DateString
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

    /** XML - конфигурации импорта */
    @Persist
    @Column(length = 15000)
    public String getConfig() { return theConfig ; }
    public void setConfig(String aConfig) { theConfig = aConfig ; }

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

    /** XML - конфигурации импорта */
    private String theConfig ;
    
    /** Служебный формат импорта */
	@Comment("Служебный формат импорта")
	@Persist
	public Boolean getSystemFormat() {return theSystemFormat;}
	public void setSystemFormat(Boolean aSystemFormat) {theSystemFormat = aSystemFormat;}
	/** Служебный формат импорта */
	private Boolean theSystemFormat;
}
