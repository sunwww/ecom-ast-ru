package ru.ecom.mis.ejb.domain.identification;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Кодирующее соответствие
  */
 @Comment("Кодирующее соответствие")
@Entity
@Table(schema="SQLUser")
public class EncodingAccordance extends BaseEntity{
 /**
  * Код элемента 1 кодирующей системы
  */
 @Comment("Код элемента 1 кодирующей системы")
 public String getCode1() {
  return theCode1;
 }
 public void setCode1(String aCode1) {
  theCode1 = aCode1;
 }
 /**
  * Код элемента 1 кодирующей системы
  */
 private String theCode1;
 /**
  * Код элемента 2 кодирующей системы
  */
 @Comment("Код элемента 2 кодирующей системы")
 public String getCode2() {
  return theCode2;
 }
 public void setCode2(String aCode2) {
  theCode2 = aCode2;
 }
 /**
  * Код элемента 2 кодирующей системы
  */
 private String theCode2;
 /**
  * Дата начала соответствия
  */
 @Comment("Дата начала соответствия")
 public Date getDateFrom() {
  return theDateFrom;
 }
 public void setDateFrom(Date aDateFrom) {
  theDateFrom = aDateFrom;
 }
 /**
  * Дата начала соответствия
  */
 private Date theDateFrom;
 /**
  * Дата окончания соответствия
  */
 @Comment("Дата окончания соответствия")
 public Date getDateTo() {
  return theDateTo;
 }
 public void setDateTo(Date aDateTo) {
  theDateTo = aDateTo;
 }
 /**
  * Дата окончания соответствия
  */
 private Date theDateTo;
 /**
  * 1 кодирующая система
  */
 @Comment("1 кодирующая система")
 public String getSystem1() {
  return theSystem1;
 }
 public void setSystem1(String aSystem1) {
  theSystem1 = aSystem1;
 }
 /**
  * 1 кодирующая система
  */
 private String theSystem1;
 /**
  * 2 кодирующая система
  */
 @Comment("2 кодирующая система")
 public String getSystem2() {
  return theSystem2;
 }
 public void setSystem2(String aSystem2) {
  theSystem2 = aSystem2;
 }
 /**
  * 2 кодирующая система
  */
 private String theSystem2;
}
