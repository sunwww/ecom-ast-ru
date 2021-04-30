package ru.ecom.expomc.ejb.services.form.check;

import java.io.Serializable;
import java.util.Collection;

/**
 */
public class ChecksTableFormRow implements Serializable {

    /** Описание формата DBF */
    public String getDbfInfo() { return dbfInfo ; }
    public void setDbfInfo(String aDbfInfo) { dbfInfo = aDbfInfo ; }

    /** Свойство */
    public String getProperty() { return property ; }
    public void setProperty(String aProperty) { property = aProperty ; }

    /** Комментарий */
    public String getComment() { return comment ; }
    public void setComment(String aComment) { comment = aComment ; }

    /** Название по формату */
    public String getFormatFieldName() { return formatFieldName ; }
    public void setFormatFieldName(String aFormatFieldName) { formatFieldName = aFormatFieldName ; }

    /** Связанный документ */
    public Long getLinkedDoc() { return linkedDoc ; }
    public void setLinkedDoc(Long aLinkedDoc) { linkedDoc = aLinkedDoc ; }

    /** Ons */
    public Collection<ChecksTableFormRowOn> getOns() { return ons ; }
    public void setOns(Collection<ChecksTableFormRowOn> aOns) { ons = aOns ; }

    /** Ons */
    private Collection<ChecksTableFormRowOn> ons ;
    /** Связанный документ */
    private Long linkedDoc ;

    /** Название по формату */
    private String formatFieldName ;
    /** Комментарий */
    private String comment ;
    /** Свойство */
    private String property ;
    /** Описание формата DBF */
    private String dbfInfo ;
}
