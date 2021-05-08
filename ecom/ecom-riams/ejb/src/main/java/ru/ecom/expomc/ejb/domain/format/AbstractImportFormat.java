/**
 * @author ikouzmin 11.03.2007 22:47:28
 */
package ru.ecom.expomc.ejb.domain.format;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;

import javax.persistence.*;
import java.sql.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "EFFORMAT", schema = "SQLUser")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "formatProvider",
        discriminatorType = DiscriminatorType.STRING
)
@DiscriminatorValue("-")
@Getter
@Setter
public class AbstractImportFormat extends BaseEntity {
    /** Дата с которой начинает действовать формат */
    private Date actualDateFrom;

    /** Дата, до которой формат действует */
    private Date actualDateTo;

    /** Комментарий к формату */
    private String comment;

    /** Отключен */
    private boolean disabled;

    /** Документ импорта */
    @ManyToOne
    public ImportDocument getDocument() {
        return document;
    }

    private ImportDocument document;

    /** Системный формат импорта */
    private Boolean systemFormat;
}
