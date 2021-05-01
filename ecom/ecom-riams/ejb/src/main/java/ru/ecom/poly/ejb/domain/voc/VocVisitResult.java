package ru.ecom.poly.ejb.domain.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;


//Справочник Результатов обращений
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocVisitResult extends VocIdNameOmcCode {

	/** Код исхода федеральный по поликлинике */
	private String codefIshod ;
	/** Код фед. ск.помощи */
	private String codefamb;
	/** Код результата федеральный по поликлинике */
	private String codefpl;
	/**Код в промеде1**/
	private String promedCode1;
}