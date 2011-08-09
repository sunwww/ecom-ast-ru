package ru.ecom.diary.ejb.domain.protocol.template;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 19.01.2007
 * Time: 11:37:31
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(schema="SQLUser")
public class TemplateClassif extends BaseEntity {

    /** Название классификатора */
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Класс, к которому принадлежит классификатор */
    public String getClazz() { return theClazz ; }
    public void setClazz(String aClazz) { theClazz = aClazz ; }

    /** Свойство для отображения */
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }

    /** Свойство для отображения */
    private String theProperty ;

    /** Класс, к которому принадлежит классификатор */
    private String theClazz ;

    /** Название классификатора */
    private String theName ;

}
