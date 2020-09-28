package ru.ecom.mis.ejb.domain.birth.voc;/**
 * Created by Milamesher on 26.03.2019.
 */

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Comment("Подгруппа класиификации Робсона")
@Entity
@Table(schema="SQLUser")
public class VocSubRobson extends VocBaseEntity {
}