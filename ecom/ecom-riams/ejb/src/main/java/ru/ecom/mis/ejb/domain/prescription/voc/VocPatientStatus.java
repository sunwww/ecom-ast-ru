package ru.ecom.mis.ejb.domain.prescription.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Comment("Статус пациентки")
@Table(schema = "SQLUser")
public class VocPatientStatus extends VocBaseEntity {
}