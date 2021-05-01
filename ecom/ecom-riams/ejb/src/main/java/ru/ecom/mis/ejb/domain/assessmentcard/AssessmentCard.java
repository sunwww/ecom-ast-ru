package ru.ecom.mis.ejb.domain.assessmentcard;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;


@Entity
@AIndexes({
		@AIndex(properties="patient")
		,@AIndex(properties="medcase")
})
@Table(schema="SQLUser")
@Getter
@Setter
public class AssessmentCard extends BaseEntity {
	/** Тип карты оценки */
	private Long template;
	
	/** Пациент */
	@Comment("Пациент")
	public Long getPatient() {return patient;}
	/** Пациент */
	private Long patient;
	
	/** Комментарий */
	@Comment("Комментарий")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getComment() {return comment;}
	/** Комментарий */
	private String comment;

	/** Сумма баллов по карте */
	private Long ballSum;
	/** Пользователь создавший запись */
	private String createUsername;
	/** Дата создания */
	private Date createDate;
	
	/** Рабочая функция врача */
	private Long workFunction;
	
	/** Дата приема */
	private Date startDate;

	/** СЛО/визит создания */
	@Comment("СЛО/визит создания")
	@OneToOne
	public MedCase getMedcase() {return medcase;}
	/** СЛО/визит создания */
	private MedCase medcase;
}
