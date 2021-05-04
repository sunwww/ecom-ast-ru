package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Код профиля отделения для стационара или специалиста поликлиники
 */
@Entity
@Comment("Код профиля отделения для стационара или специалиста поликлиники")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocOmcDepType extends VocIdName {
	/** Код федеральный */
	private String codef;
}
