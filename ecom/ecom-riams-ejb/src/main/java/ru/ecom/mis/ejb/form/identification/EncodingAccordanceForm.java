package ru.ecom.mis.ejb.form.identification;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.identification.EncodingAccordance;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = EncodingAccordance.class)
@Comment("Кодирующая система")
@WebTrail(comment = "Оценочные баллы", nameProperties = "id", view = "entityView-ident_encodingAccordance.do")
@EntityFormSecurityPrefix("/Policy/Mis/EncodingAccordance")
public class EncodingAccordanceForm extends IdEntityForm {
	/**
	  * Код элемента 1 кодирующей системы
	  */
	 @Comment("Код элемента 1 кодирующей системы")
	 @Persist
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
	 @Persist
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
	 @Persist
	 public String getDateFrom() {
	  return theDateFrom;
	 }
	 public void setDateFrom(String aDateFrom) {
	  theDateFrom = aDateFrom;
	 }
	 /**
	  * Дата начала соответствия
	  */
	 private String theDateFrom;
	 /**
	  * Дата окончания соответствия
	  */
	 @Comment("Дата окончания соответствия")
	 @Persist
	 public String getDateTo() {
	  return theDateTo;
	 }
	 public void setDateTo(String aDateTo) {
	  theDateTo = aDateTo;
	 }
	 /**
	  * Дата окончания соответствия
	  */
	 private String theDateTo;
	 /**
	  * 1 кодирующая система
	  */
	 @Comment("1 кодирующая система")
	 @Persist
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
	 @Persist
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
