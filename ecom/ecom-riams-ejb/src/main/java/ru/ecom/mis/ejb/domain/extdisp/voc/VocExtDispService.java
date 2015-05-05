package ru.ecom.mis.ejb.domain.extdisp.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник услуг дополнительной диспансеризации
	 */
	@Comment("Справочник услуг дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "WorkFunctionCode" }) })
public class VocExtDispService extends VocBaseEntity{
		/** Визит */
		@Comment("Визит")
		public Boolean getIsVisit() {return theIsVisit;}
		public void setIsVisit(Boolean aIsVisit) {theIsVisit = aIsVisit;}
		/** Визит		 */
		private Boolean theIsVisit;
		
		/** Код для экспорта (orph.rosminzdrav.ru) */
		@Comment("Код для экспорта (orph.rosminzdrav.ru)")
		public String getOrphCode() {
			return theOrphCode;
		}

		public void setOrphCode(String aOrphCode) {
			theOrphCode = aOrphCode;
		}

		/** Код для экспорта (orph.rosminzdrav.ru) */
		private String theOrphCode;
		
		
		/** Специальность врача */
		@Comment("Специальность врача")
		public String getWorkFunctionCode() {
			return theWorkFunctionCode;
		}

		public void setWorkFunctionCode(String aWorkFunctionCode) {
			theWorkFunctionCode = aWorkFunctionCode;
		}

		/** Специальность врача */
		private String theWorkFunctionCode;
		
		/** Код главной услуги */
		@Comment("Код главной услуги")
		public String getMainCode() {return theMainCode;}
		public void setMainCode(String aMainCode) {theMainCode = aMainCode;}
		/** Код главной услуги */
		private String theMainCode;
}
