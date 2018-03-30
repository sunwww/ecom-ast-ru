package ru.ecom.expert2.form.voc.federal;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV010;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV015;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Классификатор медицинских специальностей
 */
@EntityForm
@EntityFormPersistance(clazz = VocE2FondV015.class)
@Comment("Коэффициент")
@WebTrail(comment = "Коэффициент", nameProperties = "id", view = "entityView-e2_vocFondV015.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class VocE2FondV015Form extends IdEntityForm {

    /** Название */
    @Comment("Название")
    @Persist @Required
    public String getName() {return theName;}
    public void setName(String aName) {theName = aName;}
    /** Название */
    private String theName ;

    /** Код */
    @Comment("Код")
    @Persist @Required
    public String getCode() {return theCode;}
    public void setCode(String aCode) {theCode = aCode;}
    /** Код */
    private String theCode ;

    /** Дата начала действия */
    @Comment("Дата начала действия")
    @Persist
    @DoDateString @DateString
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
    /** Родитель */
    @Comment("Родитель")
    @Persist
    public String getParent() {return theParent;}
    public void setParent(String aParent) {theParent = aParent;}
    /** Родитель */
    private String theParent ;

    /** ОКСО */
    @Comment("ОКСО")
    @Persist
    public String getOkso() {return theOkso;}
    public void setOkso(String aOkso) {theOkso = aOkso;}
    /** ОКСО */
    private String theOkso ;

    /** Профиль мед. помощи для подачи по стационару */
    @Comment("Профиль мед. помощи для подачи по стационару")
    @Persist @Required
    public Long getStacProfile() {return theStacProfile;}
    public void setStacProfile(Long aStacProfile) {theStacProfile = aStacProfile;}
    /** Профиль мед. помощи для подачи по стационару */
    private Long theStacProfile ;

    /** Профиль мед. помощи для подачи по поликлинике */
    @Comment("Профиль мед. помощи для подачи по поликлинике")
    @Persist @Required
    public Long getPolicProfile() {return thePolicProfile;}
    public void setPolicProfile(Long aPolicProfile) {thePolicProfile = aPolicProfile;}
    /** Профиль мед. помощи для подачи по поликлинике */
    private Long thePolicProfile ;
}
