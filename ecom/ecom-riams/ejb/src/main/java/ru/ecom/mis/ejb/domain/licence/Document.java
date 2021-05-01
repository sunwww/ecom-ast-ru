package ru.ecom.mis.ejb.domain.licence;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Документ
 */

@Entity
@Comment("Документ")
@Table(schema="SQLUser")
@Getter
@Setter
public class Document extends BaseEntity {
	/** Кем выдан */
	private String whomIssued;
	/** Дата выдачи */
	private Date dateIssued;
    /** Серия документа */
    private String seriaDoc ;
    /** Номер документа */
    private String numberDoc ;
	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return medCase;}
	/** СМО */
	private MedCase medCase;
	/** Пользователь, последний редактировавший запись */
	private String editUsername;
	/** Дата редактирования */
	private Date editDate;
	/** Пользователь, создавший запись */
	private String createUsername;
	/** Дата создания */
	private Date createDate;
	/** Время создания */
	private Time createTime;

}