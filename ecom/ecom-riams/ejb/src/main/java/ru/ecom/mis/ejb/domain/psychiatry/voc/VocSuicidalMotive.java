package ru.ecom.mis.ejb.domain.psychiatry.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник мотив суицидальной попытки")
@Entity
@Table(schema="SQLUser")
public class VocSuicidalMotive extends VocBaseEntity {

}
