package ru.ecom.address.ejb.domain.address;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;


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

//    /**
//     * Идентификатор
//     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "AddressId")
//    @Comment("Идентификатор")
//    public long getId() {
//        return theId;
//    }
//
//    public void setId(long aId) {
//        theId = aId;
//    }

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
    /*
    @Transient
    @Comment("Родитель (идентификатор)")
    public Long getParentId() {
    	return theParent!=null ? theParent.getId() : null ;
    }
*/
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
    /*
    @Transient
    @Comment("Тип (идентификатор")
    public Long getTypeId() {
    	return theType!=null ? theType.getId() : null ;
    }
    

    @Transient
    public String getTypeName() {
        return theType != null ? theType.getShortName() : "";
    }*/
    @Transient
    public String getAddressInfo() {
        /*StringBuilder sb = new StringBuilder();
        Address a = this ;
        while(a!=null) {

            sb.insert(0, a.getName()) ;
            sb.insert(0, " ") ;
            sb.insert(0, a.getType()!=null ? a.getType().getShortName() : "") ;

            long oldId = a.getId() ;
            a = a.getParent() ;
            if(a!=null && a.getId()==oldId) {
                a = null ;
            }
            if(a!=null) sb.insert(0, ", ") ;
        }
        return sb.toString() ;*/
    	return theFullname==null?theName:theFullname ;
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
    /*
    //Край (область) регион
    @Transient
    public String getAddressRegion() {
    	Address adr = this ;
    	while (adr!=null && adr.getDomen()>1) {
    		if (adr.getDomen()==2) return adr.getName() ;
    		adr = adr.getParent() ;
    	}
    	return "" ;
    }\
    */
    /*
    //Город или село инфо
    @Transient
    public String getAddressCity() {
    	Address adr = this ;
    	while (adr!=null && adr.getDomen()>3) {
    		if (adr.getDomen()==4 || adr.getDomen()==5) return adr.getName() ;
    		adr = adr.getParent() ;
    	}
    	return "" ;
    }
    */
    /*
    //Район области
    @Transient
    public String getAddressArea() {
    	Address adr = this ;
    	while (adr!=null && adr.getDomen()>2) {
    		
    		if (adr.getDomen()==3) return adr.getName() ;
    		adr = adr.getParent() ;
    	}
    	return "" ;
    }
    */
    //Город?
    @Transient
    public Boolean getAddressIsCity() {
    	Address adr = this ;
    	while (adr!=null && adr.getDomen()>3) {
    		
    		if (adr.getDomen()==4) return true ;
    		adr = adr.getParent() ;
    	}
    	return false ;    	//return theDomen==4?true:false ;
    }
    //Село?
    @Transient
    public Boolean getAddressIsVillage() {
    	Address adr = this ;
    	while (adr!=null && adr.getDomen()>4) {
    		
    		if (adr.getDomen()==5) return true ;
    		adr = adr.getParent() ;
    	}
    	return false ;//return theDomen==5?true:false ;
    }
    /*
    // Улица
    @Transient
    public String getAddressStreet() {
    	Address adr = this ;
    	while (adr!=null && adr.getDomen()>5) {
    		if (adr.getDomen()==6) return adr.getName() ;
    		adr = adr.getParent() ;
    	}
    	return "" ;
    }
    */
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
//    /**
//     * Идентификатор
//     */
//    private long theId;
    /** ОКАТО */
	@Comment("ОКАТО")
	public String getOkato() {return theOkato;}
	public void setOkato(String aOkato) {theOkato = aOkato;}

	/** ОКАТО */
	private String theOkato;

}
