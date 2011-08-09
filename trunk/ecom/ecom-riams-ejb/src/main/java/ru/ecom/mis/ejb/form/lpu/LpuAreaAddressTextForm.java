package ru.ecom.mis.ejb.form.lpu;

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
public class LpuAreaAddressTextForm extends IdEntityForm {
    /** Участок */
    @Persist
    public Long getArea() { return theArea ; }
    public void setArea(Long aArea) { theArea = aArea ; }

    /** Адрес */
    @Persist
    public String getAddressText() { return theAddressText ; }
    public void setAddressText(String aAddressText) { theAddressText = aAddressText ; }

    /** Улица */
    @Persist
    public String getStreetName() { return theStreetName ; }
    public void setStreetName(String aStreetName) { theStreetName = aStreetName ; }

    /** Строка адреса */
    @Comment("Строка адреса")
    @Persist
    public String getAddressString() { return theAddressString ; }
    public void setAddressString(String aAddressString) { theAddressString = aAddressString ; }

    /** Адрес */
    @Persist
    @Required
    public Long getAddress() { return theAddress ; }
    public void setAddress(Long aAddress) { theAddress = aAddress ; }

    /** Дом */
    public String getHouseNumber() { return theHouseNumber ; }
    public void setHouseNumber(String aHouseNumber) { theHouseNumber = aHouseNumber ; }

    /** Корпус */
    public String getHouseBuilding() { return theHouseBuilding ; }
    public void setHouseBuilding(String aHouseBuilding) { theHouseBuilding = aHouseBuilding ; }

    /** Квартира */
    public String getFlatNumber() { return theFlatNumber ; }
    public void setFlatNumber(String aFlatNumber) { theFlatNumber = aFlatNumber ; }


    /** Адрес */
    private Long theAddress ;
    /** Квартира */
    private String theFlatNumber ;
    /** Корпус */
    private String theHouseBuilding ;
    /** Дом */
    private String theHouseNumber ;

    /** Строка адреса */
    private String theAddressString ;
    /** Улица */
    private String theStreetName ;
    /** Адрес */
    private String theAddressText ;
    /** Участок */
    private Long theArea ;
}
