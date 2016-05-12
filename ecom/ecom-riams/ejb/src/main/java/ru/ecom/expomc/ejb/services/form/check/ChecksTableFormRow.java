package ru.ecom.expomc.ejb.services.form.check;

import java.io.Serializable;
import java.util.Collection;

/**
 */
public class ChecksTableFormRow implements Serializable {

    /** Описание формата DBF */
    public String getDbfInfo() { return theDbfInfo ; }
    public void setDbfInfo(String aDbfInfo) { theDbfInfo = aDbfInfo ; }

    /** Свойство */
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }

    /** Комментарий */
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Название по формату */
    public String getFormatFieldName() { return theFormatFieldName ; }
    public void setFormatFieldName(String aFormatFieldName) { theFormatFieldName = aFormatFieldName ; }

    /** Связанный документ */
    public Long getLinkedDoc() { return theLinkedDoc ; }
    public void setLinkedDoc(Long aLinkedDoc) { theLinkedDoc = aLinkedDoc ; }

    /** Ons */
    public Collection<ChecksTableFormRowOn> getOns() { return theOns ; }
    public void setOns(Collection<ChecksTableFormRowOn> aOns) { theOns = aOns ; }

    /** Ons */
    private Collection<ChecksTableFormRowOn> theOns ;
    /** Связанный документ */
    private Long theLinkedDoc ;

    /** Название по формату */
    private String theFormatFieldName ;
    /** Комментарий */
    private String theComment ;
    /** Свойство */
    private String theProperty ;
    /** Описание формата DBF */
    private String theDbfInfo ;
}
