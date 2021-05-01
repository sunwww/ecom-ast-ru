package ru.ecom.address.ejb.domain.kladr;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Сокращения
 */
@Entity
@Table(schema="SQLUser")
@Comment("Сокращения КЛАДР")
@Setter
@Getter
public class KladrSocr extends BaseEntity implements Serializable {
     /** Идентификатор */
     @Id @GeneratedValue(strategy = GenerationType.AUTO)
     public long getId() { return id ; }

    /** Название */
    @Comment("Название")
    @Column(length = 29)
    @AFormatFieldSuggest("SOCRNAME")
    public String getName() { return name ; }

    /** Сокращение */
    @Comment("Сокращение")
    @Column(length = 10)
    @AFormatFieldSuggest("SCNAME")
    public String getShortName() { return shortName ; }

    /** Уровень */
    @Comment("Уровень")
    @Column(length = 5, name="KLADR_LEVEL")
    @AFormatFieldSuggest("LEVEL")
    public String getLevel() { return level ; }

    /** Уровень */
    private String level ;
    /** Сокращение */
    private String shortName ;
    /** Название */
    private String name ;
     /** Идентификатор */
    private long id ;

}
