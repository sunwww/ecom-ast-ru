package ru.ecom.mis.ejb.uc.privilege.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Процент оплаты")
@Table(schema="SQLUser")
public class VocPayPercent extends VocIdName {
}
