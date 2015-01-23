package ru.ecom.mis.ejb.domain.extdisp.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник услуг дополнительной диспансеризации
	 */
	@Comment("Справочник услуг дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
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
		
		/** Код специальности врача */
		@Comment("Код специальности врача")
		@OneToOne
		public VocWorkFunction getWorkFunction() {
			return theWorkFunction;
		}

		public void setWorkFunction(VocWorkFunction aWorkFunction) {
			theWorkFunction = aWorkFunction;
		}

		/** Код специальности врача */
		private VocWorkFunction theWorkFunction;
}
