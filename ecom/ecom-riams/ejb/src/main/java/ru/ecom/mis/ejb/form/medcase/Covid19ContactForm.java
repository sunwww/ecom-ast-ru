package ru.ecom.mis.ejb.form.medcase;

import lombok.Setter;
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
@Setter
public class Covid19ContactForm extends IdEntityForm {
    /** Карта коронавируса 19 */
    @Comment("Карта коронавируса 19")
    @Persist @Required
    public Long getCard() {return card;}
    private Long card ;

    /** Фамилия */
    @Comment("Фамилия")
    @Persist @Required
    @DoUpperCase
    public String getLastname() {return lastname;}
    private String lastname ;

    /** Имя */
    @Comment("Имя")
    @Persist
    @DoUpperCase
    public String getFirstname() {return firstname;}
    private String firstname ;

    /** Отчество */
    @Comment("Отчество")
    @Persist @DoUpperCase
    public String getMiddlename() {return middlename;}
    private String middlename ;

    /** Дата рождения */
    @Comment("Дата рождения")
    @Persist
    @DateString @DoDateString
    public String getBirthDate() {return birthDate;}
    private String birthDate ;

    /** Телефон */
    @Comment("Телефон")
    @Persist
    public String getPhone() {return phone;}
    private String phone ;

    /** Адрес */
    @Comment("Адрес")
    @Persist
    public String getAddress() {return address;}
    private String address ;
}
