package ru.ecom.diary.ejb.domain.protocol.template;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 21.12.2006
 * Time: 10:55:14
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(schema="SQLUser")
public class TemplateWord extends BaseEntity {

    /** Сокращение */
    @Column(unique = true)
    public String getReduction() { return theReduction ; }
    public void setReduction(String aReduction) { theReduction = aReduction ; }

    /** Расшифровка */
    public String getDecryption() { return theDecryption ; }
    public void setDecryption(String aDecryption) { theDecryption = aDecryption ; }

    /** Расшифровка */
    private String theDecryption ;

    /** Сокращение */
    private String theReduction ;

}
