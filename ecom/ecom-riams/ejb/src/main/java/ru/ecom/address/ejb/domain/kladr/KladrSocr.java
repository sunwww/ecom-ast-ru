package ru.ecom.address.ejb.domain.kladr;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Сокращения
 */
@Entity
@Table(schema="SQLUser")
@Comment("Сокращения КЛАДР")
public class KladrSocr extends BaseEntity implements Serializable {
     /** Идентификатор */
     @Id @GeneratedValue(strategy = GenerationType.AUTO)
     public long getId() { return theId ; }
     public void setId(long aId) { theId = aId ; }

    /** Название */
    @Comment("Название")
    @Column(length = 29)
    @AFormatFieldSuggest("SOCRNAME")
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Сокращение */
    @Comment("Сокращение")
    @Column(length = 10)
    @AFormatFieldSuggest("SCNAME")
    public String getShortName() { return theShortName ; }
    public void setShortName(String aShortName) { theShortName = aShortName ; }

    /** Уровень */
    @Comment("Уровень")
    @Column(length = 5, name="KLADR_LEVEL")
    @AFormatFieldSuggest("LEVEL")
    public String getLevel() { return theLevel ; }
    public void setLevel(String aLevel) { theLevel = aLevel ; }

    /** Уровень */
    private String theLevel ;
    /** Сокращение */
    private String theShortName ;
    /** Название */
    private String theName ;
     /** Идентификатор */
    private long theId ;

}
