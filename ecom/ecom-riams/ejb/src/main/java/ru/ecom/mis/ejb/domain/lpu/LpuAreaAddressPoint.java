package ru.ecom.mis.ejb.domain.lpu;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;

/**
 * @author  azviagin
 */
@Entity
@AIndexes({
	@AIndex(properties= {"address","houseNumber","houseBuilding"}),
    @AIndex(properties= {"address","houseNumber","houseBuilding","flat"}),
    @AIndex(properties= {"address","houseNumber", "flat"}),
    @AIndex(properties= {"address","houseNumber"}),
    @AIndex(properties= "address")
})
@Table(schema="SQLUser")
@Getter
@Setter
public class LpuAreaAddressPoint extends BaseEntity {
 

    /** Адрес */
    @OneToOne
    public Address getAddress() { return address ; }

    /** Адрес участка */
    @ManyToOne
    public LpuAreaAddressText getLpuAreaAddressText() { return lpuAreaAddressText ; }

    /** Адрес участка */
    private LpuAreaAddressText lpuAreaAddressText ;

	/** Квартира */
	private String flat;
    /** Корпус */
    private String houseBuilding ;
    /** Номер дома */
    private String houseNumber ;
    /** Адрес */
    private Address address ;
}
