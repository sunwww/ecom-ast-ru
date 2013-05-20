package ru.ecom.mis.ejb.domain.expert.voc;

import javax.persistence.Entity;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Обоснование направления на ВК")
public class VocExpertOrderConclusion extends VocBaseEntity{

}
