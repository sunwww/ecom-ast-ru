package ru.ecom.mis.ejb.domain.contract.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник вид помощи по гарантийному письму")
@Entity
@Table(schema="SQLUser")
public class VocGuaranteeKindHelp extends VocBaseEntity {

}
