package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Врачебная должность по ОМС
 */
@Comment("Врачебная должность по ОМС")
@Entity
@Table(schema="SQLUser")
public class VocOmcDoctorPost extends VocIdName {

}
