package ru.ecom.expert2.form.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2Entry;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.ecom.expert2.form.E2EntryListForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

import javax.persistence.Entity;
import java.sql.Date;

/**
 * Справочник профилей медицинской помощи
 */
@EntityForm
@EntityFormPersistance(clazz = VocE2MedHelpProfile.class)
@Comment("Справочник профилей медицинской помощи")
@WebTrail(comment = "Справочник профилей медицинской помощи", nameProperties = "id", view = "entityView-e2_vocMedHelpProfile.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class VocE2MedHelpProfileForm extends IdEntityForm {
    /** Дата начала действия */
    @Comment("Дата начала действия")
    @Persist @DateString @DoDateString
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

    /** Архивная запись */
    @Comment("Архивная запись")
    @Persist
    public Boolean getNoActuality() {return theNoActuality;}
    public void setNoActuality(Boolean aNoActuality) {theNoActuality = aNoActuality;}
    /** Архивная запись */
    private Boolean theNoActuality ;

    /** Профиль ФОМС */
    @Comment("Профиль ФОМС")
    @Persist
    public String getProfileK() {return theProfileK;}
    public void setProfileK(String aProfileK) {theProfileK = aProfileK;}
    /** Профиль ФОМС */
    private String theProfileK ;

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

  /** Профиль койки  */
  @Comment("Профиль койки ")
  public Long getNewBedType() {return theNewBedType;}
  public void setNewBedType(Long aNewBedType) {theNewBedType = aNewBedType;}
  /** Профиль койки  */
  private Long theNewBedType ;

  /** Тип койки */
  @Comment("Тип койки")
  public Long getNewBedSubType() {return theNewBedSubType;}
  public void setNewBedSubType(Long aNewBedSubType) {theNewBedSubType = aNewBedSubType;}
  /** Тип койки */
  private Long theNewBedSubType ;


    /** Мед. специальность по профилю */
    @Comment("Мед. специальность по профилю")
    @Persist
    public Long getMedSpec() {return theMedSpec;}
    public void setMedSpec(Long aMedSpec) {theMedSpec = aMedSpec;}
    /** Мед. специальность по профилю */
    private Long theMedSpec ;
}
