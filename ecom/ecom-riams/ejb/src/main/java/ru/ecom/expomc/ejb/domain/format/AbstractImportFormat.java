/**
 *
 * @author ikouzmin 11.03.2007 22:47:28
 */
package ru.ecom.expomc.ejb.domain.format;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;

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
    private Date theActualDateFrom ;

    /** Дата, до которой формат действует */
    public Date getActualDateTo() { return theActualDateTo ; }
    public void setActualDateTo(Date aActualDateTo) { theActualDateTo = aActualDateTo ; }
    private Date theActualDateTo ;

    /** Комментарий к формату */
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }
    private String theComment ;

    /** Отключен */
    public boolean isDisabled() { return theDisabled ; }
    public void setDisabled(boolean aDisabled) { theDisabled = aDisabled ; }
    private boolean theDisabled ;

    /** Документ импорта */
    @ManyToOne
    public ImportDocument getDocument() { return theDocument ; }
    public void setDocument(ImportDocument aDocument) { theDocument = aDocument ; }
    private ImportDocument theDocument ;

    /** Системный формат импорта */
	@Comment("Системный формат импорта")
	public Boolean getSystemFormat() {return theSystemFormat;}
	public void setSystemFormat(Boolean aSystemFormat) {theSystemFormat = aSystemFormat;}
	private Boolean theSystemFormat;
}
