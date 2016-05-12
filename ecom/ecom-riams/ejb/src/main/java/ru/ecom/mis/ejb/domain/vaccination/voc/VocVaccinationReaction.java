package ru.ecom.mis.ejb.domain.vaccination.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Фаза вакцинации")
@Table(schema="SQLUser")
public class VocVaccinationReaction extends VocIdName {

}
