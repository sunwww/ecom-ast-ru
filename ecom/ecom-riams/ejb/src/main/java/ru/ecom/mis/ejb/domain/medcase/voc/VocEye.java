package ru.ecom.mis.ejb.domain.medcase.voc;/**
 * Created by Milamesher on 31.10.2019.
 */

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Справочник глаз (левый, правый, оба)
 * @author Milamesher
 *
 */
@Comment("Справочник глаз")
@Entity
@Table(schema="SQLUser")
public class VocEye extends VocBaseEntity {
}