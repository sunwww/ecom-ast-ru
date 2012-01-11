package ru.ecom.mis.ejb.domain.birth;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Обменная карта беременности (сведения о родильнице)
 * @author azviagin
 *
 */
@Comment("Обменная карта беременности (сведения о родильнице)")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
		@AIndex(properties = { "pregnancy" }) 
	}
)
public class ConfinedExchangeCard extends BaseEntity{
	
	/** Больница */
	@Comment("Больница")
	@OneToOne
	public MisLpu getHospital() {return theHospital;}
	public void setHospital(MisLpu aHospital) {theHospital = aHospital;}

	
	/** Дата поступления */
	@Comment("Дата поступления")
	public Date getHospitalizationDate() {return theHospitalizationDate;}
	public void setHospitalizationDate(Date aHospitalizationDate) {theHospitalizationDate = aHospitalizationDate;}
		
	
	/** Дата родов */
	@Comment("Дата родов")
	public Date getBirthDate() {return theBirthDate;	}
	public void setBirthDate(Date aBirthDate) {theBirthDate = aBirthDate;}

	
	/** Особенности течения родов */
	@Comment("Особенности течения родов")
	@Column(length=1000)
	public String getBirthFeatures() {return theBirthFeatures;}
	public void setBirthFeatures(String aBirthFeatures) {theBirthFeatures = aBirthFeatures;}
	
	/** Оперативные пособия в родах */
	@Comment("Оперативные пособия в родах")
	public String getBirthOperations() {return theBirthOperations;}
	public void setBirthOperations(String aBirthOperations) {theBirthOperations = aBirthOperations;}

	/** Обезболивание */
	@Comment("Обезболивание")
	@Column(length=1000)
	public String getAnesthetization() {return theAnesthetization;}
	public void setAnesthetization(String aAnesthetization) {theAnesthetization = aAnesthetization;}
	
	/** Течение послеродового периода */
	@Comment("Течение послеродового периода")
	@Column(length=1000)
	public String getPostNatalFeatures() {return thePostNatalFeatures;}
	public void setPostNatalFeatures(String aPostNatalFeatures) {thePostNatalFeatures = aPostNatalFeatures;}
	
	/** Количество дней от родов до выписки */
	@Comment("Количество дней от родов до выписки")
	public Integer getBirthDischargeDays() {return theBirthDischargeDays;}
	public void setBirthDischargeDays(Integer aBirthDischargeDays) {	theBirthDischargeDays = aBirthDischargeDays;}

	/** Состояние матери при выписке */
	@Comment("Состояние матери при выписке")
	@Column(length=1000)
	public String getDischargeMotherCondition() {return theDischargeMotherCondition;}
	public void setDischargeMotherCondition(String aDischargeMotherCondition) {theDischargeMotherCondition = aDischargeMotherCondition;}

	/** Нуждается в патронаже */
	@Comment("Нуждается в патронаже")
	public Boolean getPatronageNeeded() {return thePatronageNeeded;}
	public void setPatronageNeeded(Boolean aPatronageNeeded) {thePatronageNeeded = aPatronageNeeded;}

	/** Показания к патронажу */
	@Comment("Показания к патронажу")
	@Column(length=1000)
	public String getPatronageStatement() {return thePatronageStatement;}
	public void setPatronageStatement(String aPatronageStatement) {thePatronageStatement = aPatronageStatement;}

	
	/** Особые замечания */
	@Comment("Особые замечания")
	@Column(length=1000)
	public String getNotes() {return theNotes;	}
	public void setNotes(String aNotes) {theNotes = aNotes;}

	
	/** Дата заполнения */
	@Comment("Дата заполнения")
	public Date getFillingDate() {return theFillingDate;}
	public void setFillingDate(Date aFillingDate) {theFillingDate = aFillingDate;	}

	
	/** Данные новорожденных */
	@Comment("Данные новорожденных")
	@OneToMany(mappedBy="confinedExchangeCard")
	public List<NewBornInformation> getNewBorns() {return theNewBorns;}
	public void setNewBorns(List<NewBornInformation> aNewBorns) {theNewBorns = aNewBorns;}

	/** Беременность */
	@Comment("Беременность")
	@OneToOne
	public Pregnancy getPregnancy() {return thePregnancy;}
	public void setPregnancy(Pregnancy aPregnancy) {thePregnancy = aPregnancy;}

	/** Беременность */
	private Pregnancy thePregnancy;
	/** Количество дней от родов до выписки */
	private Integer theBirthDischargeDays;
	/** Состояние матери при выписке */
	private String theDischargeMotherCondition;
	/** Особые замечания */
	private String theNotes;
	/** Дата заполнения */
	private Date theFillingDate;
	/** Данные новорожденных */
	private List<NewBornInformation> theNewBorns;
	/** Больница */
	private MisLpu theHospital;
	/** Показания к патронажу */
	private String thePatronageStatement;
	/** Нуждается в патронаже */
	private Boolean thePatronageNeeded;
	/** Обезболивание */
	private String theAnesthetization;
	/** Дата поступления */
	private Date theHospitalizationDate;
	/** Дата родов */
	private Date theBirthDate;
	/** Особенности течения родов */
	private String theBirthFeatures;
	/** Оперативные пособия в родах */
	private String theBirthOperations;	
	/** Течение послеродового периода */
	private String thePostNatalFeatures;

}
