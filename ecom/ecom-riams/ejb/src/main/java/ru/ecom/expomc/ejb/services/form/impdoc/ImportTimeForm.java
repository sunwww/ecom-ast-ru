package ru.ecom.expomc.ejb.services.form.impdoc;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.DateString;

import javax.persistence.Id;

/**
 * @author esinev 21.08.2006 0:15:05
 */
@EntityForm
@EntityFormPersistance(clazz= ImportTime.class)
@Comment("Список по дате")
@Parent(property = "document", parentForm= ImportDocumentForm.class)
@WebTrail(comment="Импортированные данные", nameProperties={"comment","importDate","actualDateFrom", "actualDateTo"}, view="entityParentEdit-exp_importtime.do")
@EntityFormSecurityPrefix("/Policy/Exp/Time")
@Setter
public class ImportTimeForm extends IdEntityForm {

	
    /** Идентификатор */
    @Id
    public long getId() { return id ; }

    /** Комментарий */
    @Comment("Комментарий")
    @Persist
    public String getComment() { return comment ; }

    /** Дата импорта */
    @DateString
    @Persist
    public String getImportDate() { return importDate ; }

    /** Дата актуальности с */
    @DateString
    @Persist
    public String getActualDateFrom() { return actualDateFrom ; }

    /** Дата актуальности по */
    @DateString
    @Persist
    public String getActualDateTo() { return actualDateTo ; }

    /** Документ */
    @Persist
    public long getDocument() { return importDocument ; }

    /** Файл */
	@Comment("Файл")
	@Persist
	public String getOriginalFilename() {
		return originalFilename;
	}

	/** Размер в байтах */
	@Comment("Размер в байтах")
	@Persist
	public long getSizeInBytes() {
		return sizeInBytes;
	}

	/** Размер в байтах */
	private long sizeInBytes;

	/** Файл */
	private String originalFilename;
    /** Документ */
    private long importDocument ;
    /** Дата актуальности по */
    private String actualDateTo ;
    /** Дата актуальности с */
    private String actualDateFrom ;
    /** Дата импорта */
    private String importDate ;
    /** Клас для сохранения */
    private String entityClassName ;
    /** Комментарий */
    private String comment ;
    /** Ключ импорта */
    private String keyName ;
    /** Идентификатор */
    private long id ;
}
