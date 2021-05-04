package ru.ecom.expomc.ejb.domain.registry;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 *  Страховая компания
 */
@Entity
@Table(name="REG_IC",schema="SQLUser")
@Comment("Страховая компания")
@Setter
@Getter
public class RegInsuranceCompany extends VocIdNameOmcCode {

	/** ОГРН */
	private String ogrn;
	/** Региональная компания */
	private Boolean isRegion;
	/** Код СМО */
	private String smocode;

	/** ОКАТО */
	private String okato;

	/**Код в промеде**/
	private String promedCode;
}
