package ru.ecom.mis.ejb.domain.query;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Запрос
 */
@Entity
@Comment("Запрос")
@Table(schema="SQLUser")
public class StoredQuery extends BaseEntity {
 
    /** Ключ */
    public String getKey() { return theKey ; }
    public void setKey(String aKey) { theKey = aKey ; }

    /** Ключ */
    private String theKey ;
    /** Документ */
    @OneToOne
    public ImportDocument getDocument() { return theDocument ; }
    public void setDocument(ImportDocument aDocument) { theDocument = aDocument ; }

    /** Колонки */
    @OneToMany(mappedBy="storedQuery", cascade=CascadeType.ALL)
    public List<QueryColumn> getColumns() { return theColumns ; }
    public void setColumns(List<QueryColumn> aColumns) { theColumns = aColumns ; }

    /** Колонки */
    private List<QueryColumn> theColumns ;
    /** Документ */
    private ImportDocument theDocument ;


}
