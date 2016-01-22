package ru.ecom.mis.ejb.domain.claim;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник типов заявки")
@Entity
@Table(schema="SQLUser")
public class VocClaimType extends VocBaseEntity{

}
