package ru.ecom.expomc.ejb.services.form.format;

import javax.persistence.Id;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.util.FormAfterLoadInterceptor;
import ru.ecom.expomc.ejb.domain.format.Field;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.validators.Required;

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
public class FieldForm extends IdEntityForm {

    /** Идентификатор */
    @Id
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }

    /** Название поля */
    @Persist
    @Required
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Комментарий */
    @Persist
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** DBF: тип поля */
    @Persist
    @Required
    public int getDbfType() { return theDbfType ; }
    public void setDbfType(int aDbfType) { theDbfType = aDbfType ; }

    /** DBF: размер поля */
    @Persist
    public int getDbfSize() { return theDbfSize ; }
    public void setDbfSize(int aDbfSize) { theDbfSize = aDbfSize ; }

    /** DBF: количество знаков после запятой */
    @Persist
    public int getDbfDecimal() { return theDbfDecimal ; }
    public void setDbfDecimal(int aDbfDecimal) { theDbfDecimal = aDbfDecimal ; }

    /** Формат */
    @Persist
    public long getFormat() { return theFormat ; }
    public void setFormat(long aFormat) { theFormat = aFormat ; }

    /** Поле для сохранения */
    @Persist
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }

    /** Порядковый номер */
    @Persist
    @Required
    public int getSerialNumber() { return theSerialNumber ; }
    public void setSerialNumber(int aSerialNumber) { theSerialNumber = aSerialNumber ; }

    /** Обязательное поле */
    @Persist
    public boolean getRequired() { return theRequired ; }
    public void setRequired(boolean aRequired) { theRequired = aRequired ; }

    /** Подробное описание */
    @Persist
    public String getDescription() { return theDescription ; }
    public void setDescription(String aDescription) { theDescription = aDescription ; }

    /** Cвязанный документ */
    @Persist
    public Long getLinkedDocument() { return theLinkedDocument ; }
    public void setLinkedDocument(Long aLinkedDocument) { theLinkedDocument = aLinkedDocument ; }

    /** Поле с кодом у связанного документа */
    @Persist
    public String getLinkedDocumentCodeField() { return theLinkedDocumentCodeField ; }
    public void setLinkedDocumentCodeField(String aLinkedDocumentCodeField) { theLinkedDocumentCodeField = aLinkedDocumentCodeField ; }

    /** Идентификатор документа для связанных свойств */
    public long getDocument() { return theDocument ; }
    public void setDocument(long aDocument) { theDocument = aDocument ; }

    /** Описание DBF */
    public String getDbfInfo() { return theDbfInfo ; }
    public void setDbfInfo(String aDbfInfo) { theDbfInfo = aDbfInfo ; }

    /** Значение по-умолчанию */
	@Comment("Значение по-умолчанию")
	@Persist
	public String getDefaultValue() {
		return theDefaultValue;
	}

	public void setDefaultValue(String aDefaultValue) {
		theDefaultValue = aDefaultValue;
	}

	/** Значение по-умолчанию */
	private String theDefaultValue;
    /** Описание DBF */
    private String theDbfInfo ;

    /** Идентификатор документа для связанных свойств */
    private long theDocument ;
    /** Поле с кодом у связанного документа */
    private String theLinkedDocumentCodeField ;
    /** Cвязанный документ */
    private Long theLinkedDocument ;
    /** Подробное описание */
    private String theDescription ;
    /** Обязательное поле */
    private boolean theRequired ;
    /** Порядковый номер */
    private int theSerialNumber ;
    /** Поле для сохранения */
    private String theProperty ;
    /** Формат */
    private long theFormat ;
    /** DBF: количество знаков после запятой */
    private int theDbfDecimal ;
    /** DBF: размер поля */
    private int theDbfSize ;
    /** DBF: тип поля */
    private int theDbfType ;
    /** Комментарий */
    private String theComment ;
    /** Название поля */
    private String theName ;
    /** Идентификатор */
    private long theId ;

}
