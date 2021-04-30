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
  return intervals;
 }
 public void setIntervals(GuaranteeInterval aIntervals) {
  intervals = aIntervals;
 }
 /**
  * Интервалы гарантийных документов
  */
 private GuaranteeInterval intervals;
 /**
  * Название
  */
 @Comment("Название")
 public String getName() {
  return name;
 }
 public void setName(String aName) {
  name = aName;
 }
 /**
  * Название
  */
 private String name;
}
