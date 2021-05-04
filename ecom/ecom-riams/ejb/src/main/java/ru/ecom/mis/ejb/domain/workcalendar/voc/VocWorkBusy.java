package ru.ecom.mis.ejb.domain.workcalendar.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник занятости
	 */
	@Comment("Справочник занятости")
@Entity
@Table(schema="SQLUser")
	@Getter
	@Setter
public class VocWorkBusy extends VocBaseEntity{
		/** Работает */
		@Comment("Работает")
		@AFormatFieldSuggest({"ISWORKING","WORKING"})
		public Boolean getIsWorking() {return isWorking;}

		/** Работает */
		private Boolean isWorking;
}
