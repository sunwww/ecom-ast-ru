package ru.ecom.mis.ejb.domain.birth;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class ConfinedExchangeCard extends BaseEntity{
	
	/** Больница */
	@Comment("Больница")
	@OneToOne
	public MisLpu getHospital() {return hospital;}

	/** Особенности течения родов */
	@Comment("Особенности течения родов")
	@Column(length=1000)
	public String getBirthFeatures() {return birthFeatures;}

	/** Обезболивание */
	@Comment("Обезболивание")
	@Column(length=1000)
	public String getAnesthetization() {return anesthetization;}

	/** Течение послеродового периода */
	@Comment("Течение послеродового периода")
	@Column(length=1000)
	public String getPostNatalFeatures() {return postNatalFeatures;}

	/** Состояние матери при выписке */
	@Comment("Состояние матери при выписке")
	@Column(length=1000)
	public String getDischargeMotherCondition() {return dischargeMotherCondition;}

	/** Показания к патронажу */
	@Comment("Показания к патронажу")
	@Column(length=1000)
	public String getPatronageStatement() {return patronageStatement;}

	/** Особые замечания */
	@Comment("Особые замечания")
	@Column(length=1000)
	public String getNotes() {return notes;	}

	/** Данные новорожденных */
	@Comment("Данные новорожденных")
	@OneToMany(mappedBy="confinedExchangeCard")
	public List<NewBornInformation> getNewBorns() {return newBorns;}

	/** Беременность */
	@Comment("Беременность")
	@OneToOne
	public Pregnancy getPregnancy() {return pregnancy;}

	/** Беременность */
	private Pregnancy pregnancy;
	/** Количество дней от родов до выписки */
	private Integer birthDischargeDays;
	/** Состояние матери при выписке */
	private String dischargeMotherCondition;
	/** Особые замечания */
	private String notes;
	/** Дата заполнения */
	private Date fillingDate;
	/** Данные новорожденных */
	private List<NewBornInformation> newBorns;
	/** Больница */
	private MisLpu hospital;
	/** Показания к патронажу */
	private String patronageStatement;
	/** Нуждается в патронаже */
	private Boolean patronageNeeded;
	/** Обезболивание */
	private String anesthetization;
	/** Дата поступления */
	private Date hospitalizationDate;
	/** Дата родов */
	private Date birthDate;
	/** Особенности течения родов */
	private String birthFeatures;
	/** Оперативные пособия в родах */
	private String birthOperations;	
	/** Течение послеродового периода */
	private String postNatalFeatures;

}
