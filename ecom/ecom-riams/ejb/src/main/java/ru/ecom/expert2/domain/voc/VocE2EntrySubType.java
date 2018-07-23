package ru.ecom.expert2.domain.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV006;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class VocE2EntrySubType extends VocBaseEntity {
    /** Код для определения тарифа */
    @Comment("Код для определения тарифа")
    public String getTariffCode() {return theTariffCode;}
    public void setTariffCode(String aTariffCode) {theTariffCode = aTariffCode;}
    /** Код для определения тарифа */
    private String theTariffCode ;

    /** Условия оказания мед. помощи для подачи */
    @Comment("Условия оказания мед. помощи для подачи")
    @OneToOne
    public VocE2FondV006 getUslOk() {return theUslOk;}
    public void setUslOk(VocE2FondV006 aUslOk) {theUslOk = aUslOk;}
    /** Условия оказания мед. помощи для подачи */
    private VocE2FondV006 theUslOk ;

    /** В архиве */
    @Comment("В архиве")
    public Boolean getIsArchival() {return theIsArchival;}
    public void setIsArchival(Boolean aIsArchival) {theIsArchival = aIsArchival;}
    /** В архиве */
    private Boolean theIsArchival ;

  /** Посещение в консультативной поликлинике */
  @Comment("Посещение в консультативной поликлинике")
  public Boolean getIsConsultation() {return theIsConsultation;}
  public void setIsConsultation(Boolean aIsConsultation) {theIsConsultation = aIsConsultation;}
  /** Посещение в консультативной поликлинике */
  private Boolean theIsConsultation ;
  
  /** Вид случая */
  @Comment("Вид случая")
  @OneToOne
  public VocE2VidSluch getVidSluch() {return theVidSluch;}
  public void setVidSluch(VocE2VidSluch aVidSluch) {theVidSluch = aVidSluch;}
  /** Вид случая */
  private VocE2VidSluch theVidSluch ;
}
