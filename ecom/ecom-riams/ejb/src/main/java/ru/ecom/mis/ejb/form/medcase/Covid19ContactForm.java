package ru.ecom.mis.ejb.form.medcase;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.Covid19Contact;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz = Covid19Contact.class)
@Comment("Контактное лицо")
@WebTrail(comment = "Контактное лицо", nameProperties = "lastname", view = "entityParentView-smo_covid19Contact.do")
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Covid19")
@Parent(parentForm = Covid19Form.class,  property = "card")
public class Covid19ContactForm extends IdEntityForm {
    /** Карта коронавируса 19 */
    @Comment("Карта коронавируса 19")
    @Persist @Required
    public Long getCard() {return theCard;}
    public void setCard(Long aCard) {theCard = aCard;}
    private Long theCard ;

    /** Фамилия */
    @Comment("Фамилия")
    @Persist @Required
    @DoUpperCase
    public String getLastname() {return theLastname;}
    public void setLastname(String aLastname) {theLastname = aLastname;}
    private String theLastname ;

    /** Имя */
    @Comment("Имя")
    @Persist
    @DoUpperCase
    public String getFirstname() {return theFirstname;}
    public void setFirstname(String aFirstname) {theFirstname = aFirstname;}
    private String theFirstname ;

    /** Отчество */
    @Comment("Отчество")
    @Persist @DoUpperCase
    public String getMiddlename() {return theMiddlename;}
    public void setMiddlename(String aMiddlename) {theMiddlename = aMiddlename;}
    private String theMiddlename ;

    /** Дата рождения */
    @Comment("Дата рождения")
    @Persist
    @DateString @DoDateString
    public String getBirthDate() {return theBirthDate;}
    public void setBirthDate(String aBirthDate) {theBirthDate = aBirthDate;}
    private String theBirthDate ;

    /** Телефон */
    @Comment("Телефон")
    @Persist
    public String getPhone() {return thePhone;}
    public void setPhone(String aPhone) {thePhone = aPhone;}
    private String thePhone ;

    /** Адрес */
    @Comment("Адрес")
    @Persist
    public String getAddress() {return theAddress;}
    public void setAddress(String aAddress) {theAddress = aAddress;}
    private String theAddress ;
}
