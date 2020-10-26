package ru.ecom.expomc.ejb.domain.impdoc;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.format.AbstractImportFormat;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Дата актуальности
 * Changes:
 * IKO: 070312 *** Поддержка различных типов импорта (использование абстрактного типа импорта)
 */
@Entity
@Table(name = "IMPTIME", schema = "SQLUser")
public class ImportTime extends BaseEntity {

    /**
     * Размер файла в байтах
     */
    private Long theSizeInBytes;
    /**
     * Формат, по которому импортировалось
     */

    private AbstractImportFormat theFormat;
    /**
     * Документ импорта
     */


    private ImportDocument theDocument;
    /**
     * Дата актуальности по
     */
    private Date theActualDateTo;
    /**
     * Дата актуальности с
     */
    private Date theActualDateFrom;
    /**
     * Дата и время импорта
     */
    private java.util.Date theImportDate;
    /**
     * Комментарий
     */
    private String theComment;
    /**
     * Откуда импортировался файл
     */
    private String theOriginalFilename;

    /**
     * Откуда импортировался файл
     */
    @Comment("Откуда импортировался файл")
    public String getOriginalFilename() {
        return theOriginalFilename;
    }

    public void setOriginalFilename(String aOriginalFilename) {
        theOriginalFilename = aOriginalFilename;
    }

    /**
     * Комментарий
     */
    @Comment("Комментарий")
    public String getComment() {
        return theComment;
    }

    public void setComment(String aComment) {
        theComment = aComment;
    }

    /**
     * Дата актуальности с
     */
    public Date getActualDateFrom() {
        return theActualDateFrom;
    }

    public void setActualDateFrom(Date aActualDateFrom) {
        theActualDateFrom = aActualDateFrom;
    }

    /**
     * Дата актуальности по
     */
    public Date getActualDateTo() {
        return theActualDateTo;
    }

    public void setActualDateTo(Date aActualDateTo) {
        theActualDateTo = aActualDateTo;
    }

    /**
     * Дата и время импорта
     */
    public java.util.Date getImportDate() {
        return theImportDate;
    }

    public void setImportDate(java.util.Date aImportDate) {
        theImportDate = aImportDate;
    }

    /**
     * Документ импорта
     */
    @ManyToOne
    public ImportDocument getDocument() {
        return theDocument;
    }

    public void setDocument(ImportDocument aDocument) {
        theDocument = aDocument;
    }

    /**
     * Формат, по которому импортировалось
     */
    @OneToOne
    public AbstractImportFormat getFormat() {
        return theFormat;
    }

    public void setFormat(AbstractImportFormat aFormat) {
        theFormat = aFormat;
    }

    /**
     * Размер файла в байтах
     */
    @Comment("Размер файла в байтах")
    public Long getSizeInBytes() {
        return theSizeInBytes;
    }

    public void setSizeInBytes(Long aSizeInBytes) {
        theSizeInBytes = aSizeInBytes;
    }

}
