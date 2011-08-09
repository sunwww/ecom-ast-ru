package ru.amokb.asset.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник разъемов процессора
	 */
	@Comment("Справочник разъемов процессора")
@Entity
@Table(schema="SQLUser")
public class VocCpuSocket extends VocBaseEntity{
		/** Процессор */
		@Comment("Процессор")
		public String getCPU() {return theCPU;}
		public void setCPU(String aCPU) {theCPU = aCPU;}

		/** Процессор */
		private String theCPU;
}
