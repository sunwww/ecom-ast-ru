package ru.ecom.mis.ejb.uc.privilege.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Форма выпуска лекарственного препарата
 * @author esinev
 *
 */
@Entity
@Comment("Форма выпуска лекарственного препарата")
@Table(schema="SQLUser")
public class VocDrugForm extends VocIdNameOmcCode {
}
