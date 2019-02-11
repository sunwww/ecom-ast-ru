package ru.ecom.address.ejb.domain.kladr;

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
public class Kladr extends NoLiveBaseEntity implements Serializable, IImportData {
    /** Архивная запись */
	@Comment("Архивная запись")
	public Boolean getArchive() {return theArchive;}
	public void setArchive(Boolean aArchive) {theArchive = aArchive;}
	/** Архивная запись */
	private Boolean theArchive;
	
	/** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }

    /** Время импорта */
    public long getTime() { return theTime ; }
    public void setTime(long aTime) { theTime = aTime ; }

    /** Название */
    @Column(length = 40)
    @Comment("Название")
    @AFormatFieldSuggest("NAME")
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Сокращение */
    @Column(length = 10)
    @Comment("Сокращение")
    @AFormatFieldSuggest("SOCR")
    public String getShortName() { return theShortName ; }
    public void setShortName(String aShortName) { theShortName = aShortName ; }

    /** Код */
    @Column(length = 17)
    @Comment("Код КЛАДР")
    @AFormatFieldSuggest("CODE")
    public String getKladrCode() { return theKladrCode ; }
    public void setKladrCode(String aKladrCode) { theKladrCode = aKladrCode ; }

    /** Почтовый индекс */
    @Column(length = 6)
    @Comment("Почтовый индекс")
    @AFormatFieldSuggest("INDEX")
    public String getPostIndex() { return thePostIndex ; }
    public void setPostIndex(String aPostIndex) { thePostIndex = aPostIndex ; }

    /** ГНИ */
    @Column(length = 4)
    @Comment("ГНИ")
    @AFormatFieldSuggest("GNINMB")
    public String getGninmb() { return theGninmb ; }
    public void setGninmb(String aGninmb) { theGninmb = aGninmb ; }

    /** УНО */
    @Column(length = 4)
    @Comment("УНО")
    @AFormatFieldSuggest("UNO")
    public String getUno() { return theUno ; }
    public void setUno(String aUno) { theUno = aUno ; }

    /** ОКАТД */
    @Column(length = 11)
    @Comment("ОКАТД")
    @AFormatFieldSuggest("OCATD")
    public String getOkatd() { return theOkatd ; }
    public void setOkatd(String aOkatd) { theOkatd = aOkatd ; }

    /** Статус */
    @Comment("Статус")
    @AFormatFieldSuggest("STATUS")
    @Column(length = 1)
    public String getAddressStatus() { return theAddressStatus ; }
    public void setAddressStatus(String aAddressStatus) { theAddressStatus = aAddressStatus ; }

    /** Статус */
    private String theAddressStatus ;
    /** ОКАТД */
    private String theOkatd ;
    /** УНО */
    private String theUno ;
    /** ГНИ */
    private String theGninmb ;
    /** Почтовый индекс */
    private String thePostIndex ;
    /** Код */
    private String theKladrCode ;
    /** Сокращение */
    private String theShortName ;
    /** Название */
    private String theName ;
    /** Идентификатор */
    private long theId ;
    /** Время импорта */
    private long theTime ;
}
