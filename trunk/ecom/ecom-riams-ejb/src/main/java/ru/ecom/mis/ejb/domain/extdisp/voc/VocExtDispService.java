package ru.ecom.mis.ejb.domain.extdisp.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
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
}
