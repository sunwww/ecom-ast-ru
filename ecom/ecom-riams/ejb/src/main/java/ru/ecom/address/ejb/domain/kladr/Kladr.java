package ru.ecom.address.ejb.domain.kladr;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.NoLiveBaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.io.Serializable;


/**
 * КЛАДР
 */
@Entity
@Comment("КЛАДР")
@Table(schema="SQLUser")
@AIndexes(@AIndex( properties = "KladrCode"))
@NamedQueries({
@NamedQuery(name = "kladr.findByKladrcode"
        , query = "from Kladr where kladrcode=:code"
)
})
@Setter
@Getter
public class Kladr extends NoLiveBaseEntity implements Serializable, IImportData {
	/** Архивная запись */
	private Boolean archive;
	
	/** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return id ; }

    /** Время импорта */
    public long getTime() { return time ; }

    /** Название */
    @Column(length = 40)
    @Comment("Название")
    @AFormatFieldSuggest("NAME")
    public String getName() { return name ; }

    /** Сокращение */
    @Column(length = 10)
    @Comment("Сокращение")
    @AFormatFieldSuggest("SOCR")
    public String getShortName() { return shortName ; }

    /** Код */
    @Column(length = 17)
    @Comment("Код КЛАДР")
    @AFormatFieldSuggest("CODE")
    public String getKladrCode() { return kladrCode ; }

    /** Почтовый индекс */
    @Column(length = 6)
    @Comment("Почтовый индекс")
    @AFormatFieldSuggest("INDEX")
    public String getPostIndex() { return postIndex ; }

    /** ГНИ */
    @Column(length = 4)
    @Comment("ГНИ")
    @AFormatFieldSuggest("GNINMB")
    public String getGninmb() { return gninmb ; }

    /** УНО */
    @Column(length = 4)
    @Comment("УНО")
    @AFormatFieldSuggest("UNO")
    public String getUno() { return uno ; }

    /** ОКАТД */
    @Column(length = 11)
    @Comment("ОКАТД")
    @AFormatFieldSuggest("OCATD")
    public String getOkatd() { return okatd ; }

    /** Статус */
    @Comment("Статус")
    @AFormatFieldSuggest("STATUS")
    @Column(length = 1)
    public String getAddressStatus() { return addressStatus ; }

    /** Статус */
    private String addressStatus ;
    /** ОКАТД */
    private String okatd ;
    /** УНО */
    private String uno ;
    /** ГНИ */
    private String gninmb ;
    /** Почтовый индекс */
    private String postIndex ;
    /** Код */
    private String kladrCode ;
    /** Сокращение */
    private String shortName ;
    /** Название */
    private String name ;
    /** Идентификатор */
    private long id ;
    /** Время импорта */
    private long time ;
}
