package ru.ecom.expomc.ejb.domain.impdoc;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.format.AbstractImportFormat;
import ru.ecom.expomc.ejb.domain.message.Message;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Дата актуальности
 * Changes:
 * IKO: 070312 *** Поддержка различных типов импорта (использование абстрактного типа импорта)
 */
@Entity
@Table(name="IMPTIME",schema="SQLUser")
public class ImportTime extends BaseEntity {

	/** Откуда импортировался файл */
	@Comment("Откуда импортировался файл")
	public String getOriginalFilename() {
		return theOriginalFilename;
	}

	public void setOriginalFilename(String aOriginalFilename) {
		theOriginalFilename = aOriginalFilename;
	}

    /** Комментарий */
    @Comment("Комментарий")
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Дата актуальности с */
    public Date getActualDateFrom() { return theActualDateFrom ; }
    public void setActualDateFrom(Date aActualDateFrom) { theActualDateFrom = aActualDateFrom ; }

    /** Дата актуальности по */
    public Date getActualDateTo() { return theActualDateTo ; }
    public void setActualDateTo(Date aActualDateTo) { theActualDateTo = aActualDateTo ; }

    /** Дата и время импорта */
    public java.util.Date getImportDate() { return theImportDate ; }
    public void setImportDate(java.util.Date aImportDate) { theImportDate = aImportDate ; }

    /** Документ импорта */
    @ManyToOne
    public ImportDocument getDocument() { return theDocument ; }
    public void setDocument(ImportDocument aDocument) { theDocument = aDocument ; }

    /** Формат, по которому импортировалось */
    @OneToOne
    // IKO 070312 ***
    //public Format getFormat() { return theFormat ; }
    //public void setFormat(Format aFormat) { theFormat = aFormat ; }

    public AbstractImportFormat getFormat() { return theFormat ; }
    public void setFormat(AbstractImportFormat aFormat) { theFormat = aFormat ; }
    // IKO 070312 ===


    /** Список сообщений */
    @OneToMany (cascade = CascadeType.ALL, mappedBy="importTime")
    public Collection<Message> getMessages() { return theMessages ; }
    public void setMessages(Collection<Message> aMessages) { theMessages = aMessages ; }

    /** Размер файла в байтах */
	@Comment("Размер файла в байтах")
	public Long getSizeInBytes() {
		return theSizeInBytes;
	}

	public void setSizeInBytes(Long aSizeInBytes) {
		theSizeInBytes = aSizeInBytes;
	}

	/** Размер файла в байтах */
	private Long theSizeInBytes;
    /** Список сообщений */
    private Collection<Message> theMessages ;
    /** Формат, по которому импортировалось */

    // IKO 070312 ***
//    private Format theFormat ;
    private AbstractImportFormat theFormat ;
    // IKO 070312 ===

    /** Документ импорта */


    private ImportDocument theDocument ;
    /** Дата актуальности по */
    private Date theActualDateTo ;
    /** Дата актуальности с */
    private Date theActualDateFrom ;
    /** Дата и время импорта */
    private java.util.Date theImportDate ;
    /** Комментарий */
    private String theComment ;
	/** Откуда импортировался файл */
	private String theOriginalFilename;

}
