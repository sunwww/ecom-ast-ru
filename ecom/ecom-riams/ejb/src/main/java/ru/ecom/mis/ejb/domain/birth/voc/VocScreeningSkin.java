package ru.ecom.mis.ejb.domain.birth.voc;/**
 * Created by Milamesher on 23.01.2019.
 */

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Comment("Кожные покровы")
@Entity
@Table(schema="SQLUser")
public class VocScreeningSkin extends VocBaseEntity {
}