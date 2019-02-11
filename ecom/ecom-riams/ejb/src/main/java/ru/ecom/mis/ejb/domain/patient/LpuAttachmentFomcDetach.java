package ru.ecom.mis.ejb.domain.patient;
/**
 * Импорт открепленного населения из фонда
 * 
 * @author VTsybulin 28.11.2014
 */

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Comment("Открепленное население -из фонда")
@Entity
@Table(schema="SQLUser")
public class LpuAttachmentFomcDetach extends LpuAttachmentFomc {

/** ФИО пациента */
@Comment("ФИО пациента")
@Transient
	public String getFullname() {
		return this.getLastname()+" "+ this.getFirstname() +" "+this.getMiddlename();
	}

}
