package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник остроты диагноза (острое, хроническое)
 */
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocAcuityDiagnosis extends VocBaseEntity {

	/** Код ОМС */
	private String omcCode;
	
}
