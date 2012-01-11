package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Место рождения")
@Entity
@Table(schema="SQLUser")
public class VocPassportBirthPlace extends VocIdName {
}
