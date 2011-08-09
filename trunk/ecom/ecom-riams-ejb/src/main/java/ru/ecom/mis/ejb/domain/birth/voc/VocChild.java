package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник ребенок: одноплодный, 1 из многоплодных, 2 из многоплодных, 3..4...5")
@Entity
public class VocChild extends VocBaseEntity {

}
