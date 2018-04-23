package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Потоки обслуживания для КСГ
 * @author Milamesher
 *
 */

@Comment("Потоки обслуживания для КСГ")
@Entity
@Table(schema="SQLUser")
public class VocSstreamE2Entry extends VocBaseEntity{
}