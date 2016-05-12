package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Какой частью тела родился (головкой, ягодицами, ножками)")
@Table(schema="SQLUser")
public class VocPartBodyBorn extends VocBaseEntity{

}
