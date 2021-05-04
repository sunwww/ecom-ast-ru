package ru.ecom.mis.ejb.domain.extdisp.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

	/**
	 * Справочник услуг дополнительной диспансеризации
	 */
	@Comment("Справочник услуг дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "WorkFunctionCode" }) })
	@Getter
	@Setter
public class VocExtDispService extends VocBaseEntity{
		/** Визит		 */
		private Boolean isVisit;
		
		/** Код для экспорта (orph.rosminzdrav.ru) */
		private String orphCode;
		
		/** Специальность врача */
		private String workFunctionCode;
		
		/** Код главной услуги */
		private String mainCode;
		/** Код ФОМС */
		private String omcCode ;
}
