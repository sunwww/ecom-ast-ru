package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Entity
/**Таблица соответствия услуги в доп. диспансеризации и услуги по справочнику V001*/
@Deprecated
public class E2ExtDispMedServiceLink extends BaseEntity {
   /** Код VocExtDispService */
   @Comment("Код VocExtDispService")
   public String getInternalCode() {return theInternalCode;}
   public void setInternalCode(String aInternalCode) {theInternalCode = aInternalCode;}
   /** Код VocExtDispService */
   private String theInternalCode ;

   /** Код медицинской услуги */
   @Comment("Код медицинской услуги")
   public String getMedServiceCode() {return theMedServiceCode;}
   public void setMedServiceCode(String aMedServiceCode) {theMedServiceCode = aMedServiceCode;}
   /** Код медицинской услуги */
   private String theMedServiceCode ;

   /** Не актуально */
   @Comment("Не актуально")
   public Boolean getNoActual() {return theNoActual;}
   public void setNoActual(Boolean aNoActual) {theNoActual = aNoActual;}
   /** Не актуально */
   private Boolean theNoActual =false;
}
