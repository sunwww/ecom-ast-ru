package ru.ecom.mis.ejb.form.lpu;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ADynamicParentSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ADynamicSecurityInterceptor;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressText;
import ru.ecom.mis.ejb.form.lpu.interceptors.LpuAreaAddressTextDynamicSecurity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz= LpuAreaAddressText.class)
@WebTrail(comment = "Адрес участка", nameProperties= "addressText", view="entityView-mis_lpuAreaAddressText.do")
@Parent(property="area", parentForm=LpuAreaForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/LpuAreaAddressText")
@ADynamicSecurityInterceptor(LpuAreaAddressTextDynamicSecurity.class)
@ADynamicParentSecurityInterceptor(LpuAreaAddressTextDynamicSecurity.class)
@Setter
public class LpuAreaAddressTextForm extends IdEntityForm {
    /** Участок */
    @Persist
    public Long getArea() { return area ; }

    /** Адрес */
    @Persist
    public String getAddressText() { return addressText ; }

    /** Улица */
    @Persist
    public String getStreetName() { return streetName ; }

    /** Строка адреса */
    @Comment("Строка адреса")
    @Persist
    public String getAddressString() { return addressString ; }

    /** Адрес */
    @Persist
    @Required
    public Long getAddress() { return address ; }

    /** Дом */
    public String getHouseNumber() { return houseNumber ; }

    /** Корпус */
    public String getHouseBuilding() { return houseBuilding ; }

    /** Квартира */
    public String getFlatNumber() { return flatNumber ; }

    /** Адрес */
    private Long address ;
    /** Квартира */
    private String flatNumber ;
    /** Корпус */
    private String houseBuilding ;
    /** Дом */
    private String houseNumber ;

    /** Строка адреса */
    private String addressString ;
    /** Улица */
    private String streetName ;
    /** Адрес */
    private String addressText ;
    /** Участок */
    private Long area ;
}
