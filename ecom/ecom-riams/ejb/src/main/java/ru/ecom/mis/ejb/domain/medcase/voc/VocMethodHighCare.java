package ru.ecom.mis.ejb.domain.medcase.voc;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Методы ВМП
 */
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocMethodHighCare extends VocBaseEntity {
	/** Список диагнозов */
	private String diagnosis;
	/** Код вида ВМП */
	private String kindHighCare;
	/** Дата начала */
	private Date dateFrom;
	/** Дата окончания */
	private Date dateTo;
	/** Модель пациента */
	private String patientModel ;
	/** Цена */
	private BigDecimal cost ;
}
