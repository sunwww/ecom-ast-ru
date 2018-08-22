package ru.ecom.mis.ejb.domain.birth.voc;/**
 * Created by Milamesher on 22.08.2018.
 */

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Comment("Паритет")
@Table(schema="SQLUser")
public class VocParitet extends VocBaseEntity {
}