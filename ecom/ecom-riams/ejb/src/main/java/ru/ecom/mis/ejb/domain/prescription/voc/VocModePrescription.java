package ru.ecom.mis.ejb.domain.prescription.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник режимов для листа назначений")
@Entity
@Table(schema="SQLUser")
public class VocModePrescription extends VocBaseEntity {

}
