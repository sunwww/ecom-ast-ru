package ru.ecom.mis.ejb.domain.birth.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Milamesher on 10.12.2018.
 * Классификация Робсона
 */
@Comment("Справочник классификация Робсона")
@Entity
@Table(schema="SQLUser")
public class VocRobsonClass extends VocBaseEntity {
}