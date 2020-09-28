package ru.ecom.expert2.form.voc.federal;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV020;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 * Профиль койки
 */
@EntityForm
@EntityFormPersistance(clazz = VocE2FondV020.class)
@Comment("Профиль койки")
@WebTrail(comment = "Профиль койки", nameProperties = "code", view = "entityView-e2_vocFondV020.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class VocE2FondV020Form extends IdEntityForm {

    /** Название */
    @Comment("Название")
    @Persist
    public String getName() {return theName;}
    public void setName(String aName) {theName = aName;}
    /** Название */
    private String theName ;

    /** Код */
    @Comment("Код")
    @Persist
    public String getCode() {return theCode;}
    public void setCode(String aCode) {theCode = aCode;}
    /** Код */
    private String theCode ;

    /** Дата начала действия */
    @Comment("Дата начала действия")
    @Persist @DateString
    @DoDateString
    public String getStartDate() {return theStartDate;}
    public void setStartDate(String aStartDate) {theStartDate = aStartDate;}
    /** Дата начала действия */
    private String theStartDate ;

    /** Дата окончания действия */
    @Comment("Дата окончания действия")
    @Persist @DateString @DoDateString
    public String getFinishDate() {return theFinishDate;}
    public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}
    /** Дата окончания действия */
    private String theFinishDate ;


    /** Услуга по профилю для стационар по умолчанию */
    @Comment("Услуга по профилю для стационар по умолчанию")
    @Persist
    public Long getDefaultStacMedService() {return theDefaultStacMedService;}
    public void setDefaultStacMedService(Long aDefaultStacMedService) {theDefaultStacMedService = aDefaultStacMedService;}
    /** Услуга по профилю для стационар по умолчанию */
    private Long theDefaultStacMedService ;
}
