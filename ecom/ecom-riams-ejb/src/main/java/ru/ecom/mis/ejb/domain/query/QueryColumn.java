package ru.ecom.mis.ejb.domain.query;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Колонка
 */
@Entity
@Comment("Колонка")
@Table(schema="SQLUser")
public class QueryColumn extends BaseEntity {
  
    /** Запрос */
    @ManyToOne
    public StoredQuery getStoredQuery() { return theStoredQuery ; }
    public void setStoredQuery(StoredQuery aStoredQuery) { theStoredQuery = aStoredQuery ; }

    /** Запрос */
    private StoredQuery theStoredQuery ;
    /** Свойство */
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }

    /** Свойство */
    private String theProperty ;


}
