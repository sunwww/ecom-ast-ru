/**
 *
 * @author ikouzmin 11.03.2007 22:47:28
 */
package ru.ecom.expomc.ejb.domain.format;

import java.sql.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;

@SuppressWarnings("serial")
@Entity
@Table(name="EFFORMAT",schema="SQLUser")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
name="formatProvider",
discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue("-")
public class AbstractImportFormat extends BaseEntity {
    /** Дата с которой начинает действовать формат */
    public Date getActualDateFrom() { return theActualDateFrom ; }
    public void setActualDateFrom(Date aActualDateFrom) { theActualDateFrom = aActualDateFrom ; }

    /** Дата, до которой формат действует */
    public Date getActualDateTo() { return theActualDateTo ; }
    public void setActualDateTo(Date aActualDateTo) { theActualDateTo = aActualDateTo ; }

    /** Комментарий к формату */
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Отключен */
    public boolean isDisabled() { return theDisabled ; }
    public void setDisabled(boolean aDisabled) { theDisabled = aDisabled ; }

    /** Документ импорта */
    @ManyToOne
    public ImportDocument getDocument() { return theDocument ; }
    public void setDocument(ImportDocument aDocument) { theDocument = aDocument ; }

    /** Документ импорта */
    private ImportDocument theDocument ;
    /** Отключен */
    private boolean theDisabled ;

    /** Комментарий к формату */
    private String theComment ;
    /** Дата, до которой формат действует */
    private Date theActualDateTo ;
    /** Дата с которой начинает действовать формат */
    private Date theActualDateFrom ;
}
