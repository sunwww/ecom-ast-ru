package ru.ecom.expert2.domain.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * Справочник коэффициентов сложности лечения пациента (КСЛП)
 */
@Entity
public class VocE2CoefficientPatientDifficulty extends VocBaseEntity {

  /** Значение коэффицинта */
  @Comment("Значение коэффицинта")
  @Column( precision = 15, scale = 5)
  public BigDecimal getValue() {return theValue;}
  public void setValue(BigDecimal aValue) {theValue = aValue;}
  /** Значение коэффицинта */
  private BigDecimal theValue ;

}
