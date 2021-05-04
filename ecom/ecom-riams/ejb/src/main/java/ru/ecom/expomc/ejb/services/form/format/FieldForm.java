package ru.ecom.expomc.ejb.services.form.format;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.util.FormAfterLoadInterceptor;
import ru.ecom.expomc.ejb.domain.format.Field;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

import javax.persistence.Id;

/**
 * Описание поля
 */
@EntityForm
@EntityFormPersistance(clazz= Field.class)
@Comment("Описание поля импорта")
@Parent(property = "format", parentForm=FormatForm.class)
@FormAfterLoadInterceptor(FieldAfterLoadInterceptor.class)
@WebTrail(comment = "Поле", nameProperties={"name","comment"}, view="entityParentEdit-exp_field.do")
@EntityFormSecurityPrefix("/Policy/Exp/Field")
@Setter
public class FieldForm extends IdEntityForm {

    /** Идентификатор */
    @Id
    public long getId() { return id ; }

    /** Название поля */
    @Persist
    @Required
    public String getName() { return name ; }

    /** Комментарий */
    @Persist
    public String getComment() { return comment ; }

    /** DBF: тип поля */
    @Persist
    @Required
    public int getDbfType() { return dbfType ; }

    /** DBF: размер поля */
    @Persist
    public int getDbfSize() { return dbfSize ; }

    /** DBF: количество знаков после запятой */
    @Persist
    public int getDbfDecimal() { return dbfDecimal ; }

    /** Формат */
    @Persist
    public long getFormat() { return format ; }

    /** Поле для сохранения */
    @Persist
    public String getProperty() { return property ; }

    /** Порядковый номер */
    @Persist
    @Required
    public int getSerialNumber() { return serialNumber ; }

    /** Обязательное поле */
    @Persist
    public boolean getRequired() { return required ; }

    /** Подробное описание */
    @Persist
    public String getDescription() { return description ; }

    /** Cвязанный документ */
    @Persist
    public Long getLinkedDocument() { return linkedDocument ; }

    /** Поле с кодом у связанного документа */
    @Persist
    public String getLinkedDocumentCodeField() { return linkedDocumentCodeField ; }

    /** Идентификатор документа для связанных свойств */
    public long getDocument() { return document ; }

    /** Описание DBF */
    public String getDbfInfo() { return dbfInfo ; }

    /** Значение по-умолчанию */
	@Comment("Значение по-умолчанию")
	@Persist
	public String getDefaultValue() {
		return defaultValue;
	}

	/** Значение по-умолчанию */
	private String defaultValue;
    /** Описание DBF */
    private String dbfInfo ;

    /** Идентификатор документа для связанных свойств */
    private long document ;
    /** Поле с кодом у связанного документа */
    private String linkedDocumentCodeField ;
    /** Cвязанный документ */
    private Long linkedDocument ;
    /** Подробное описание */
    private String description ;
    /** Обязательное поле */
    private boolean required ;
    /** Порядковый номер */
    private int serialNumber ;
    /** Поле для сохранения */
    private String property ;
    /** Формат */
    private long format ;
    /** DBF: количество знаков после запятой */
    private int dbfDecimal ;
    /** DBF: размер поля */
    private int dbfSize ;
    /** DBF: тип поля */
    private int dbfType ;
    /** Комментарий */
    private String comment ;
    /** Название поля */
    private String name ;
    /** Идентификатор */
    private long id ;

}
