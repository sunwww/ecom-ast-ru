package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник реактив, используемых при определение показателей")
@Entity
@Table(schema="SQLUser")
public class VocTransfusionReagent extends VocBaseEntity{

}
