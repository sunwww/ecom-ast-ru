package ru.ecom.mis.ejb.domain.medcase.voc;/**
 * Created by Milamesher on 16.09.2019.
 */

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/** Код обследования на ВИЧ */
@Comment("Код обследования на ВИЧ")
@Entity
@Table(schema="SQLUser")
public class VocHIVCodeResearch extends VocBaseEntity {
}