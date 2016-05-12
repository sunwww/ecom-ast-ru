package ru.ecom.diary.ejb.domain.protocol.template;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;

/**
 * User: STkacheva
 * Date: 15.12.2006
 */
@Entity
@Table(schema="SQLUser")
public class TemplatePropertyProt extends BaseEntity {

    /** Свойство */
    public PropertyProt getProperty() { return theProperty ; }
    public void setProperty(PropertyProt aProperty) { theProperty = aProperty ; }
    /** Строка */
    public long getTd() { return theTd ; }
    public void setTd(long aTd) { theTd = aTd ; }
    /** Столбец */
    public long getTr() { return theTr ; }
    public void setTr(long aTr) { theTr = aTr ; }
    /** Заголовок */
    public String getTitle() { return theTitle ; }
    public void setTitle(String aTitle) { theTitle = aTitle ; }

    /** Заголовок */
    private String theTitle ;
    /** Столбец */
    private long theTr ;
    /** Строка */
    private long theTd ;
    /** Свойство */
    private PropertyProt theProperty ;
}
