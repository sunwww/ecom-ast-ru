package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class VocClassificationMedService extends VocBaseEntity{
/** Тип мед услуги */
@Comment("Тип мед услуги")
public String getType() {return theType;}
public void setType(String aType) {	theType = aType;}
/** Тип кода */
@Comment("Тип кода")
public String getTypeCode() {return theTypeCode;}
public void setTypeCode(String aTypeCode) {	theTypeCode = aTypeCode;}

/** Тип мед услуги */
private String theType;
/** Тип кода */
private String theTypeCode;
}
