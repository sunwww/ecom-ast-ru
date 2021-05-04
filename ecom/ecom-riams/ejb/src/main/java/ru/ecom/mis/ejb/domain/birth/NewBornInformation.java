package ru.ecom.mis.ejb.domain.birth;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class NewBornInformation extends BaseEntity{
	
	/** Обменная карта родильницы */
	@Comment("Обменная карта родильницы")
	@ManyToOne
	public ConfinedExchangeCard getConfinedExchangeCard() {return confinedExchangeCard;}

	@Comment("Состояние при рождении")
	@Column(length=1000)
	public String getBirthCondition() {return birthCondition;}

	/** Состояние при выписке */
	@Comment("Состояние при выписке")
	@Column(length=1000)
	public String getDischargeCondition() {return dischargeCondition;}

	/** Особенности течения родов */
	@Comment("Особенности течения родов")
	@Column(length=1000)
	public String getBirthFeatures() {return birthFeatures;}

	/** Особенности течения послеродового периода */
	@Comment("Особенности течения послеродового периода")
	@Column(length=1000)
	public String getPostNatalFeatures() {return postNatalFeatures;}

	/** Другие мероприятия */
	@Comment("Другие мероприятия")
	@Column(length=1000)
	public String getOtherActions() {return otherActions;}

	/** Другие мероприятия */
	private String otherActions;
	
	/** Особые замечания */
	@Comment("Особые замечания")
	@Column(length=1000)
	public String getNotes() {return notes;}

	/** Особые замечания */
	private String notes;
	/** Дата заполнения */
	private Date fillingDate;
	/** Обменная карта родильницы */
	private ConfinedExchangeCard confinedExchangeCard;
	/** Состояние при рождении */
	private String birthCondition;
	/** Состояние при выписке */
	private String dischargeCondition;
	/** Противотуберкулезная вакцинация */
	private Boolean vcgVaccination;
	/** Рост при рождении */
	private BigDecimal birthHeight;
	/** Вес при рождении */
	private BigDecimal birthWeight;
	/** Вес при выписке */
	private BigDecimal dischargeWeight;
	/** Особенности течения родов */
	private String birthFeatures;
	/** Особенности течения послеродового периода */
	private String postNatalFeatures;
	/** Причины отказа в противотуберкулезной вакцинации */
	private String vcgEstop;
	/** Медикамент, которым проведена профилактика гонобленнореи (GonoblennorrheaProphylaxis) */
	private String gbpDrug;

}
