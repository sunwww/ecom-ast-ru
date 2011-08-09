/**
 *
 * @author ikouzmin 08.03.2007 18:12:39
 */

package ru.ecom.expomc.ejb.domain.format;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


// ALTER TABLE EFFORMAT ALTER COLUMN config varchar(150000)

@Entity
//@Table(name="EFIMPORTFORMAT")
@SuppressWarnings("serial")
@DiscriminatorValue("1")
@Table(schema="SQLUser")
public class ImportFormat  extends AbstractImportFormat  {

//    /** Дата с которой начинает действовать формат */
//    public Date getActualDateFrom() { return theActualDateFrom ; }
//    public void setActualDateFrom(Date aActualDateFrom) { theActualDateFrom = aActualDateFrom ; }
//
//    /** Дата, до которой формат действует */
//    public Date getActualDateTo() { return theActualDateTo ; }
//    public void setActualDateTo(Date aActualDateTo) { theActualDateTo = aActualDateTo ; }
//
//    /** Комментарий к формату */
//    public String getComment() { return theComment ; }
//    public void setComment(String aComment) { theComment = aComment ; }
//
//    /** Отключен */
//    public boolean getDisabled() { return theDisabled ; }
//    public void setDisabled(boolean aDisabled) { theDisabled = aDisabled ; }
//
//    /** Документ импорта */
//    @ManyToOne
//    public ImportDocument getDocument() { return theDocument ; }
//    public void setDocument(ImportDocument aDocument) { theDocument = aDocument ; }

    /** XML - конфигурации импорта */
    @Column(length = 150000)
    public String getConfig() { return theConfig ; }
    public void setConfig(String aConfig) { theConfig = aConfig ; }


//      /** Документ импорта */
//    private ImportDocument theDocument ;
//    /** Отключен */
//    private boolean theDisabled ;
//    /** Комментарий к формату */
//    private String theComment ;
//    /** Дата, до которой формат действует */
//    private Date theActualDateTo ;
//    /** Дата с которой начинает действовать формат */
//    private Date theActualDateFrom ;
//

    /** XML - конфигурации импорта */
    private String theConfig ;
}
