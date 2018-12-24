package ru.ecom.mis.ejb.domain.assessmentcard;

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
public class AssessmentCard extends BaseEntity {
	/** Тип карты оценки */
	@Comment("Тип карты оценки")
	public Long getTemplate() {return theTemplate;}
	public void setTemplate(Long aTemplate) {theTemplate = aTemplate;}
	/** Тип карты оценки */
	private Long theTemplate;
	
	/** Пациент */
	@Comment("Пациент")
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}
	/** Пациент */
	private Long thePatient;
	
	/** Комментарий */
	@Comment("Комментарий")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}
	/** Комментарий */
	private String theComment;

	/** Сумма баллов по карте */
	@Comment("Сумма баллов по карте")
	public Long getBallSum() {return theBallSum;}
	public void setBallSum(Long aBallSum) {theBallSum = aBallSum;}
	/** Сумма баллов по карте */
	private Long theBallSum;
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}

	/** Пользователь создавший запись */
	@Comment("Пользователь создавший запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Пользователь создавший запись */
	private String theCreateUsername;
	/** Дата создания */
	private Date theCreateDate;
	
	/** Рабочая функция врача */
	@Comment("Рабочая функция врача")
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}
	/** Рабочая функция врача */
	private Long theWorkFunction;
	
	/** Дата приема */
	@Comment("Дата приема")
	public Date getStartDate() {return theStartDate;}
	public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
	/** Дата приема */
	private Date theStartDate;

	/** СЛО/визит создания */
	@Comment("СЛО/визит создания")
	@OneToOne
	public MedCase getMedcase() {return theMedcase;}
	public void setMedcase(MedCase aMedcase) {theMedcase = aMedcase;}
	/** СЛО/визит создания */
	private MedCase theMedcase;
}
