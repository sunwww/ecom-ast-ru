package ru.ecom.expomc.ejb.services.form.impdoc;

import javax.persistence.Id;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 * @author esinev 21.08.2006 0:15:05
 */
@EntityForm
@EntityFormPersistance(clazz= ImportTime.class)
@Comment("Список по дате")
@Parent(property = "document", parentForm= ImportDocumentForm.class)
@WebTrail(comment="Импортированные данные", nameProperties={"comment","importDate","actualDateFrom", "actualDateTo"}, view="entityParentEdit-exp_importtime.do")
@EntityFormSecurityPrefix("/Policy/Exp/Time")
public class ImportTimeForm extends IdEntityForm {

	
    /** Идентификатор */
    @Id
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }

    /** Комментарий */
    @Comment("Комментарий")
    @Persist
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Дата импорта */
    @DateString
    @Persist
    public String getImportDate() { return theImportDate ; }
    public void setImportDate(String aImportDate) { theImportDate = aImportDate ; }

    /** Дата актуальности с */
    @DateString
    @Persist
    public String getActualDateFrom() { return theActualDateFrom ; }
    public void setActualDateFrom(String aActualDateFrom) { theActualDateFrom = aActualDateFrom ; }

    /** Дата актуальности по */
    @DateString
    @Persist
    public String getActualDateTo() { return theActualDateTo ; }
    public void setActualDateTo(String aActualDateTo) { theActualDateTo = aActualDateTo ; }

    /** Документ */
    @Persist
    public long getDocument() { return theImportDocument ; }
    public void setDocument(long aImportDocument) { theImportDocument = aImportDocument ; }

    /** Файл */
	@Comment("Файл")
	@Persist
	public String getOriginalFilename() {
		return theOriginalFilename;
	}

	public void setOriginalFilename(String aOriginalFilename) {
		theOriginalFilename = aOriginalFilename;
	}
	
	/** Размер в байтах */
	@Comment("Размер в байтах")
	@Persist
	public long getSizeInBytes() {
		return theSizeInBytes;
	}

	public void setSizeInBytes(long aSizeInBytes) {
		theSizeInBytes = aSizeInBytes;
	}

	/** Размер в байтах */
	private long theSizeInBytes;

	/** Файл */
	private String theOriginalFilename;
    /** Документ */
    private long theImportDocument ;
    /** Дата актуальности по */
    private String theActualDateTo ;
    /** Дата актуальности с */
    private String theActualDateFrom ;
    /** Дата импорта */
    private String theImportDate ;
    /** Клас для сохранения */
    private String theEntityClassName ;
    /** Комментарий */
    private String theComment ;
    /** Ключ импорта */
    private String theKeyName ;
    /** Идентификатор */
    private long theId ;
}
