package ru.ecom.address.ejb.domain.address;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;

import javax.persistence.*;


/**
 * Адрес
 */
@Entity
@Comment("Адрес")
@Table(name = "Address2",schema="SQLUser")
@AIndexes({
        	@AIndex(unique = false, properties = "parent"),
        	@AIndex(unique = false, properties = {"parent","domen"}),
        	@AIndex(unique = false, properties = {"name","domen"}),
        	@AIndex(unique = false, properties = "kladr"),
        	@AIndex(unique = false, properties = "domen")
                })
@AttributeOverride(name="id", column=@Column(name = "AddressId"))                
public class Address extends BaseEntity {

    /**
     * Домен (Ошибка: domain)
     */
    @Comment("Домен")
    public int getDomen() {
        return theDomen;
    }

    public void setDomen(int aDomen) {
        theDomen = aDomen;
    }

    /**
     * Родитель
     */
    @OneToOne(fetch = FetchType.LAZY)
    @Comment("Родитель")
    public Address getParent() {
        return theParent;
    }

    public void setParent(Address aParent) {
        theParent = aParent;
    }

    /**
     * Название
     */
    @Column(length = 40)
    @Comment("Название")
    public String getName() {
        return theName;
    }

    public void setName(String aName) {
        theName = aName;
    }

    /**
     * Код КЛАДР
     */
    @Column(length = 17)
    @Comment("Кладр")
    public String getKladr() {
        return theKladr;
    }

    public void setKladr(String aKladr) {
        theKladr = aKladr;
    }

    /**
     * Почтовый индекс
     */
    @Column(length = 6)
    @Comment("Почтовый индекc")
    public String getPostIndex() {
        return thePostIndex;
    }

    public void setPostIndex(String aPostIndex) {
        thePostIndex = aPostIndex;
    }

    /**
     * Тип адреса
     */
    @OneToOne
    @Comment("Кладр")
    public AddressType getType() {
        return theType;
    }
    public void setType(AddressType aType) {
        theType = aType;
    }
    @Transient
    public String getTypeName() {
        return theType != null ? theType.getShortName() : "";
    }

    @Transient
    public String getAddressInfo() {
    	return theFullname==null ? theName : theFullname ;
    }

    @Transient
    public String getAddressInfo(String aHouse, String aBuilding, String aFlat) {
        StringBuilder sb = new StringBuilder(getAddressInfo());
        if(!StringUtil.isNullOrEmpty(aHouse)) {
            sb.append(", д. ") ;
            sb.append(aHouse) ;
        }
        if(!StringUtil.isNullOrEmpty(aBuilding)) {
            sb.append(", корпус ") ;
            sb.append(aBuilding) ;
        }
        if(!StringUtil.isNullOrEmpty(aFlat)) {
            sb.append(", кв. ") ;
            sb.append(aFlat) ;
        }
        return sb.toString() ;
    }
    /** Город? */
	@Comment("Город?")
	public Boolean getAddressIsCity() {return theAddressIsCity;}
	public void setAddressIsCity(Boolean aAddressIsCity) {theAddressIsCity = aAddressIsCity;}

	/** Город? */
	private Boolean theAddressIsCity;

    /** Село? */
	@Comment("Село?")
	public Boolean getAddressIsVillage() {return theAddressIsVillage;}
	public void setAddressIsVillage(Boolean aAddressIsVillage) {theAddressIsVillage = aAddressIsVillage;}

	/** Село? */
	private Boolean theAddressIsVillage;

    /** Полный адрес */
	@Comment("Полный адрес")
	public String getFullname() {return theFullname;}
	public void setFullname(String aFullname) {theFullname = aFullname;}

	/** Полный адрес */
	private String theFullname;
    /** Тип адреса */
    private AddressType theType;
    /**
     * Почтовый индекс
     */
    private String thePostIndex;
    /**
     * Код КЛАДР
     */
    private String theKladr;
    /**
     * Название
     */
    private String theName;
    /**
     * Родитель
     */
    private Address theParent;
    /**
     * Домен
     */
    private int theDomen;

    /** ОКАТО */
	@Comment("ОКАТО")
	public String getOkato() {return theOkato;}
	public void setOkato(String aOkato) {theOkato = aOkato;}

	/** ОКАТО */
	private String theOkato;
	
	/** Регион */
	@Comment("Регион")
	@OneToOne
	public Address getRegion() {return theRegion;}
	public void setRegion(Address aRegion) {theRegion = aRegion;}

	/** Регион */
	private Address theRegion;
	
	/** Текущий регион */
	@Comment("Текущий регион")
	public Boolean getIsCurrentRegion() {return theIsCurrentRegion;}
	public void setIsCurrentRegion(Boolean aIsCurrentRegion) {theIsCurrentRegion = aIsCurrentRegion;}

	/** Текущий регион */
	private Boolean theIsCurrentRegion;

}
