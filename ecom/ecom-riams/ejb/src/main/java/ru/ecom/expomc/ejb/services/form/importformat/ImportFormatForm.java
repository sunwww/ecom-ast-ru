/**
    Форма редактирования формата свойств импорта
    IKO 0700308 +++
 */

package ru.ecom.expomc.ejb.services.form.importformat;

import javax.persistence.Column;

import lombok.Setter;
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
@Setter
public class ImportFormatForm extends IdEntityForm {

    /** Дата с которой начинает действовать формат */
    @Persist
    @Required
    @DateString
    public String getActualDateFrom() { return actualDateFrom ; }

    /** Дата, до которой формат действует */
    @Persist
    @DateString
    public String getActualDateTo() { return actualDateTo ; }

    /** Комментарий к формату */
    @Persist
    public String getComment() { return comment ; }

    /** Отключен */
    @Persist
    public boolean isDisabled() { return disabled ; }

    /** Документ */
    @Persist
    public long getDocument() { return document ; }

    /** XML - конфигурации импорта */
    @Persist
    @Column(length = 15000)
    public String getConfig() { return config ; }

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

    /** XML - конфигурации импорта */
    private String config ;
    
    /** Служебный формат импорта */
	@Comment("Служебный формат импорта")
	@Persist
	public Boolean getSystemFormat() {return systemFormat;}
	/** Служебный формат импорта */
	private Boolean systemFormat;
}
