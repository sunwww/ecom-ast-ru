package ru.ecom.expomc.ejb.domain.impdoc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.format.AbstractImportFormat;

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
@Getter
@Setter
public class ImportTime extends BaseEntity {

    /**
     * Размер файла в байтах
     */
    private Long sizeInBytes;
    /**
     * Формат, по которому импортировалось
     */

    private AbstractImportFormat format;

    /**
     * Документ импорта
     */
    private ImportDocument document;
    /**
     * Дата актуальности по
     */
    private Date actualDateTo;
    /**
     * Дата актуальности с
     */
    private Date actualDateFrom;
    /**
     * Дата и время импорта
     */
    private java.util.Date importDate;
    /**
     * Комментарий
     */
    private String comment;
    /**
     * Откуда импортировался файл
     */
    private String originalFilename;


    /**
     * Документ импорта
     */
    @ManyToOne
    public ImportDocument getDocument() {
        return document;
    }

    /**
     * Формат, по которому импортировалось
     */
    @OneToOne
    public AbstractImportFormat getFormat() {
        return format;
    }

}
