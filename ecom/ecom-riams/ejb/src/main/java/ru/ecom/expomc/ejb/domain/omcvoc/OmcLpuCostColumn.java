package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Колонка цены таблицы тарифов OmcTariff по ЛПУ
 * @author azviagin
 *
 */

@Comment("Колонка цены таблицы тарифов OmcTariff по ЛПУ")
@Entity
@Table(schema="SQLUser")
public class OmcLpuCostColumn extends BaseEntity{
	
	/** Название колонки  */
	@Comment("Название колонки ")
	public String getColumnName() {
		return theColumnName;
	}

	public void setColumnName(String aColumnName) {
		theColumnName = aColumnName;
	}

	/** Название колонки  */
	private String theColumnName;
	
	/** ОМС код ЛПУ */
	@Comment("ОМС код ЛПУ")
	public String getLpuCode() {
		return theLpuCode;
	}

	public void setLpuCode(String aLpuCode) {
		theLpuCode = aLpuCode;
	}

	/** ОМС код ЛПУ */
	private String theLpuCode;

}
