package ru.ecom.mis.ejb.domain.contract;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Группа гарантийных документов по договору
  */
 @Comment("Группа гарантийных документов по договору")
@Entity
@Table(schema="SQLUser")
public class ContractGuaranteeGroup extends BaseEntity{
 /**
  * Интервалы гарантийных документов
  */
 @Comment("Интервалы гарантийных документов")
 @OneToOne
 public GuaranteeInterval getIntervals() {
  return theIntervals;
 }
 public void setIntervals(GuaranteeInterval aIntervals) {
  theIntervals = aIntervals;
 }
 /**
  * Интервалы гарантийных документов
  */
 private GuaranteeInterval theIntervals;
 /**
  * Название
  */
 @Comment("Название")
 public String getName() {
  return theName;
 }
 public void setName(String aName) {
  theName = aName;
 }
 /**
  * Название
  */
 private String theName;
}
