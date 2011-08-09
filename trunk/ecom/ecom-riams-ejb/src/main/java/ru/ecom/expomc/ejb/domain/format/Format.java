package ru.ecom.expomc.ejb.domain.format;

import static javax.persistence.CascadeType.ALL;

import java.util.Collection;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Формат файлов проверки, например LPUFOND.DBF
 * Changes:
 * IKO 070311 *** Изменение схемы наследования
 */
@SuppressWarnings("serial")
@Entity
// IKO 070311 *** Вынос реализации в AbstractImportFormat
//@Table(name="EFFORMAT")
@DiscriminatorValue("0")
@Table(schema="SQLUser")
//public class Format extends BaseEntity {
public class Format extends AbstractImportFormat {

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

// IKO 070311 ===

    /** Поля  */
    @OneToMany(cascade = ALL, mappedBy = "format" )
    @OrderBy("serialNumber")
    public Collection<Field> getFields() { return theFields ; }
    public void setFields(Collection<Field> aFields) { theFields = aFields ; }

// IKO 070311 ---
//    /** Отключен */
//    public boolean getDisabled() { return theDisabled ; }
//    public void setDisabled(boolean aDisabled) { theDisabled = aDisabled ; }
//
//    /** Документ импорта */
//    @ManyToOne
//    public ImportDocument getDocument() { return theDocument ; }
//    public void setDocument(ImportDocument aDocument) { theDocument = aDocument ; }
//
//    /** Документ импорта */
//    private ImportDocument theDocument ;
//    /** Отключен */
//    private boolean theDisabled ;

 // IKO 070311 ===

    /** Поля  */
    private Collection<Field> theFields ;


// IKO 070311 ---
//    /** Комментарий к формату */
//    private String theComment ;
//    /** Дата, до которой формат действует */
//    private Date theActualDateTo ;
//    /** Дата с которой начинает действовать формат */
//    private Date theActualDateFrom ;
// IKO 070311 ===
}
