package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * социальный статус
 *      видимо, тоже самое что и социальная группа
 *      заполняеца из справочника SGROUP.DBF
 */
@Entity
@Comment("Социальный статус")
@Table(schema="SQLUser")
public class VocSocialStatus extends VocIdNameOmcCode {
}
