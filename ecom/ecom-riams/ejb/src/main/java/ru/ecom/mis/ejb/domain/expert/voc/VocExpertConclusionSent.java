package ru.ecom.mis.ejb.domain.expert.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@Comment("Справочник заключений по КЭР. Направляется на...")
@Getter
@Setter
public class VocExpertConclusionSent extends VocBaseEntity {
	/** Обоснование  */
	@Comment("Обоснование ")
	@OneToOne
	public VocExpertConclusion getConclusion() {return conclusion;}
	/** Обоснование  */
	private VocExpertConclusion conclusion;
	/** Неактуальный */
	private Boolean noActuality;
	/** Доп. данные */
	private String additionData;
	/** Код обоснования */
	private String conclusionCode;
}
