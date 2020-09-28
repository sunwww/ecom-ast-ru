package ru.ecom.mis.ejb.domain.medcase.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Comment("Классификатор групп состояния здоровья взрослого населения")
@Entity
@Table(schema="SQLUser")
public class VocHealthGroup extends VocBaseEntity {
}
