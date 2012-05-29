package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Тип подтверждения полиса")
@Entity
@Table(schema="SQLUser")
public class VocPolicyConfirmationType extends VocBaseEntity {

}
