package ru.ecom.mis.ejb.domain.birth.voc;/**
 * Created by Milamesher on 23.12.2019.
 */

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Comment("Нозология в акушерстве")
@Table(schema="SQLUser")
public class VocBirthNosology extends VocBaseEntity {
}