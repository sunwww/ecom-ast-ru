package ru.ecom.mis.ejb.domain.licence.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/**
  Юридические лица
  */
@Entity
@Comment("Юридические лица")
@Table(schema="SQLUser")
public class VocUrMember extends VocIdName {
}
