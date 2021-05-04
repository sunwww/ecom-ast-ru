package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import java.sql.Date;

@Entity
@Comment("Показания к переливанию")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocTransfusionReason extends VocBaseEntity {

	/** Код среды */
	private String codePreparation;

	/** Дата окончания актуальности */
	private Date dateFinish;
}
