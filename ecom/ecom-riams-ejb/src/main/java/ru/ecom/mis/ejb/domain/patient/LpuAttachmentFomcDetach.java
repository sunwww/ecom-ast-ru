package ru.ecom.mis.ejb.domain.patient;
/**
 * Импорт открепленного населения из фонда
 * 
 * @author VTsybulin 28.11.2014
 */
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

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
