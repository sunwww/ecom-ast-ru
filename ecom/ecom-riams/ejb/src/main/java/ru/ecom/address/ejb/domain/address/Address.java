package ru.ecom.address.ejb.domain.address;

import lombok.Getter;
import lombok.Setter;
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
@Setter
@Getter
public class Address extends BaseEntity {


    /**
     * Родитель
     */
    @OneToOne(fetch = FetchType.LAZY)
    @Comment("Родитель")
    public Address getParent() {
        return parent;
    }

    /**
     * Название
     */
    @Column(length = 40)
    @Comment("Название")
    public String getName() {
        return name;
    }

    /**
     * Код КЛАДР
     */
    @Column(length = 17)
    @Comment("Кладр")
    public String getKladr() {
        return kladr;
    }

    /**
     * Почтовый индекс
     */
    @Column(length = 6)
    @Comment("Почтовый индекc")
    public String getPostIndex() {
        return postIndex;
    }

    /**
     * Тип адреса
     */
    @OneToOne
    @Comment("Кладр")
    public AddressType getType() {
        return type;
    }
    @Transient
    public String getTypeName() {
        return type != null ? type.getShortName() : "";
    }

    @Transient
    public String getAddressInfo() {
    	return fullname==null ? name : fullname ;
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
	private Boolean addressIsCity;

	/** Село? */
	private Boolean addressIsVillage;

	/** Полный адрес */
	private String fullname;
    /** Тип адреса */
    private AddressType type;
    /**
     * Почтовый индекс
     */
    private String postIndex;
    /**
     * Код КЛАДР
     */
    private String kladr;
    /**
     * Название
     */
    private String name;
    /**
     * Родитель
     */
    private Address parent;
    /**
     * Домен
     */
    private int domen;

	/** ОКАТО */
	private String okato;
	
	/** Регион */
	@Comment("Регион")
	@OneToOne
	public Address getRegion() {return region;}

	/** Регион */
	private Address region;

	/** Текущий регион */
	private Boolean isCurrentRegion;

}
