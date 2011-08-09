package ru.ecom.mis.ejb.domain.workcalendar.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник занятости
	 */
	@Comment("Справочник занятости")
@Entity
@Table(schema="SQLUser")
public class VocWorkBusy extends VocBaseEntity{
		/** Работает */
		@Comment("Работает")
		@AFormatFieldSuggest({"ISWORKING","WORKING"})
		public Boolean getIsWorking() {return theIsWorking;}
		public void setIsWorking(Boolean aIsWorking) {theIsWorking = aIsWorking;}

		/** Работает */
		private Boolean theIsWorking;
}
