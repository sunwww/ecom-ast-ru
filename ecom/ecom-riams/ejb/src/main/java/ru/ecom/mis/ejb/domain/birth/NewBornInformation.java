package ru.ecom.mis.ejb.domain.birth;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Информация о новорожденном
 * @author azviagin
 *
 */
@Comment("Информация о новорожденном")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
		@AIndex(properties = { "confinedExchangeCard" }) 
	}
)
public class NewBornInformation extends BaseEntity{
	
	/** Обменная карта родильницы */
	@Comment("Обменная карта родильницы")
	@ManyToOne
	public ConfinedExchangeCard getConfinedExchangeCard() {return theConfinedExchangeCard;}
	public void setConfinedExchangeCard(ConfinedExchangeCard aConfinedExchangeCard) {theConfinedExchangeCard = aConfinedExchangeCard;}

	@Comment("Состояние при рождении")
	@Column(length=1000)
	public String getBirthCondition() {return theBirthCondition;}
	public void setBirthCondition(String aBirthCondition) {theBirthCondition = aBirthCondition;}

	/** Состояние при выписке */
	@Comment("Состояние при выписке")
	@Column(length=1000)
	public String getDischargeCondition() {return theDischargeCondition;}
	public void setDischargeCondition(String aDischargeCondition) {theDischargeCondition = aDischargeCondition;}

	/** Вес при рождении */
	@Comment("Вес при рождении")
	public BigDecimal getBirthWeight() {return theBirthWeight;}
	public void setBirthWeight(BigDecimal aBirthWeight) {theBirthWeight = aBirthWeight;}

	/** Вес при выписке */
	@Comment("Вес при выписке")
	public BigDecimal getDischargeWeight() {return theDischargeWeight;}
	public void setDischargeWeight(BigDecimal aDischargeWeight) {theDischargeWeight = aDischargeWeight;}

	/** Рост при рождении */
	@Comment("Рост при рождении")
	public BigDecimal getBirthHeight() {return theBirthHeight;}
	public void setBirthHeight(BigDecimal aBIrthHeight) {theBirthHeight = aBIrthHeight;}

	/** Особенности течения родов */
	@Comment("Особенности течения родов")
	@Column(length=1000)
	public String getBirthFeatures() {return theBirthFeatures;}
	public void setBirthFeatures(String aBirthFeatures) {theBirthFeatures = aBirthFeatures;}

	/** Особенности течения послеродового периода */
	@Comment("Особенности течения послеродового периода")
	@Column(length=1000)
	public String getPostNatalFeatures() {return thePostNatalFeatures;}
	public void setPostNatalFeatures(String aPostNatalFeatures) {thePostNatalFeatures = aPostNatalFeatures;}

	/** Противотуберкулезная вакцинация */
	@Comment("Противотуберкулезная вакцинация")
	public Boolean getVcgVaccination() {return theVcgVaccination;}
	public void setVcgVaccination(Boolean aVCGVaccination) {theVcgVaccination = aVCGVaccination;}

	/** Причины отказа в противотуберкулезной вакцинации */
	@Comment("Причины отказа в противотуберкулезной вакцинации")
	public String getVcgEstop() {return theVcgEstop;}
	public void setVcgEstop(String aVCGEstop) {theVcgEstop = aVCGEstop;}

	
	/** Другие мероприятия */
	@Comment("Другие мероприятия")
	@Column(length=1000)
	public String getOtherActions() {return theOtherActions;}
	public void setOtherActions(String aOtherActions) {theOtherActions = aOtherActions;
	}

	/** Другие мероприятия */
	private String theOtherActions;
	
	/** Особые замечания */
	@Comment("Особые замечания")
	@Column(length=1000)
	public String getNotes() {return theNotes;}
	public void setNotes(String aNotes) {theNotes = aNotes;}
	
	/** Дата заполнения */
	@Comment("Дата заполнения")
	public Date getFillingDate() {return theFillingDate;}
	public void setFillingDate(Date aFillingDate) {theFillingDate = aFillingDate;}

	/** Особые замечания */
	private String theNotes;
	/** Дата заполнения */
	private Date theFillingDate;
	/** Обменная карта родильницы */
	private ConfinedExchangeCard theConfinedExchangeCard;
	/** Состояние при рождении */
	private String theBirthCondition;
	/** Состояние при выписке */
	private String theDischargeCondition;
	/** Противотуберкулезная вакцинация */
	private Boolean theVcgVaccination;
	/** Рост при рождении */
	private BigDecimal theBirthHeight;
	/** Вес при рождении */
	private BigDecimal theBirthWeight;
	/** Вес при выписке */
	private BigDecimal theDischargeWeight;
	/** Особенности течения родов */
	private String theBirthFeatures;
	/** Особенности течения послеродового периода */
	private String thePostNatalFeatures;
	/** Причины отказа в противотуберкулезной вакцинации */
	private String theVcgEstop;
	
	/** Медикамент, которым проведена профилактика гонобленнореи (GonoblennorrheaProphylaxis) */
	@Comment("Медикамент, которым проведена профилактика гонобленнореи (GonoblennorrheaProphylaxis)")
	public String getGbpDrug() {
		return theGbpDrug;
	}

	public void setGbpDrug(String aGbpDrug) {
		theGbpDrug = aGbpDrug;
	}

	/** Медикамент, которым проведена профилактика гонобленнореи (GonoblennorrheaProphylaxis) */
	private String theGbpDrug;

}
