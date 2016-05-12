package ru.ecom.mis.ejb.domain.lpu;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;

/**
 * @author  azviagin
 */
@Entity
@AIndexes({
	@AIndex(unique = false, properties= {"address","houseNumber","houseBuilding"}),
    @AIndex(unique = false, properties= {"address","houseNumber","houseBuilding","flat"}),
    @AIndex(unique = false, properties= {"address","houseNumber", "flat"}),
    @AIndex(unique = false, properties= {"address","houseNumber"}),
    @AIndex(unique = false, properties= "address")
})
@Table(schema="SQLUser")
public class LpuAreaAddressPoint extends BaseEntity {
 

    /** Адрес */
    @OneToOne
    public Address getAddress() { return theAddress ; }
    public void setAddress(Address aAddress) { theAddress = aAddress ; }

    /** Участок */
//    @ManyToOne
//    public LpuArea getLpuArea() { return theLpuArea ; }
//    public void setLpuArea(LpuArea aLpuArea) { theLpuArea = aLpuArea ; }

    /** Адрес участка */
    @ManyToOne
    public LpuAreaAddressText getLpuAreaAddressText() { return theLpuAreaAddressText ; }
    public void setLpuAreaAddressText(LpuAreaAddressText aLpuAreaAddressText) { theLpuAreaAddressText = aLpuAreaAddressText ; }

    /** Адрес участка */
    private LpuAreaAddressText theLpuAreaAddressText ;
    /** Номер дома */
    public String getHouseNumber() { return theHouseNumber ; }
    public void setHouseNumber(String aHouseNumber) { theHouseNumber = aHouseNumber ; }

    /** Корпус */
    public String getHouseBuilding() { return theHouseBuilding ; }
    public void setHouseBuilding(String aHouseBuilding) { theHouseBuilding = aHouseBuilding ; }

    /** Квартира */
	public String getFlat() {
		return theFlat;
	}

	public void setFlat(String aFlat) {
		theFlat = aFlat;
	}

	/** Квартира */
	private String theFlat;
    /** Корпус */
    private String theHouseBuilding ;
    /** Номер дома */
    private String theHouseNumber ;
    /** Адрес */
    private Address theAddress ;

//    /** Участок */
//    private LpuArea theLpuArea ;
}
