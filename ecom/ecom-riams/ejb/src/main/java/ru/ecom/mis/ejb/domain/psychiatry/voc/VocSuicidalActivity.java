package ru.ecom.mis.ejb.domain.psychiatry.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник характер суицидальной активности")
@Entity
@Table(schema="SQLUser")
public class VocSuicidalActivity extends VocBaseEntity {

}
