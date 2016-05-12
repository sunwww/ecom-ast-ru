package ru.ecom.mis.ejb.domain.vaccination.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Тип медотвода вакцинаций
 * @author azviagin
 *
 */
@Entity
@Comment("Тип медотвода вакцинаций")
@Table(schema="SQLUser")
public class VocVaccinationEstopType extends VocIdName {

}
