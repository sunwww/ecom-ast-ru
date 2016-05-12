package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Определение резус-фактор")
@Table(schema="SQLUser")
public class VocTransfusionDefinitionRhesus extends VocBaseEntity{

}
