package ru.ecom.mis.ejb.domain.patient;
/**
 * Импорт дефектов прикрепленного населения из фонда 
 * 
 * @author VTsybulin 28.11.2014
 */

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Comment("Дефекты прикрепленного населения -из фонда")
@Entity
@Table(schema="SQLUser")
public class LpuAttachmentFomcDefect extends LpuAttachmentFomc {

	/** Код дефекта */
	@Comment("Код дефекта")
	public String getRefreason() {
		return theRefreason;
	}

	public void setRefreason(String aRefreason) {
		theRefreason = aRefreason;
	}

	/** Код дефекта */
	private String theRefreason;
	
	/** Тип прикрепления (Прикреплен/откреплен)*/
	@Comment("Тип прикрепления")
	public String getAttachType() {
		return theAttachType;
	}

	public void setAttachType(String aAttachType) {
		theAttachType = aAttachType;
	}

	/** Тип прикрепления */
	private String theAttachType;


	
	

}
