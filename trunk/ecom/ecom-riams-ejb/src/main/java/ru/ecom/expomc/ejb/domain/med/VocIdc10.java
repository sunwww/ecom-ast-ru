package ru.ecom.expomc.ejb.domain.med;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.voc.VocSexPermission;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("МКБ 10")
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(unique=false, properties={"code"}),
	@AIndex(unique=false, properties={"code","name"})
})
public class VocIdc10 extends VocIdCodeName {
	
	/** Разрешен по полу */
	@Comment("Разрешен по полу")
	@OneToOne
	public VocSexPermission getSexPermission() {
		return theSexPermission;
	}

	public void setSexPermission(VocSexPermission aSexPermission) {
		theSexPermission = aSexPermission;
	}

	/** Разрешен по полу */
	private VocSexPermission theSexPermission;
	
	/** Разрешен в ОМС */
	@Comment("Разрешен в ОМС")
	public Boolean getOmcPermission() {
		return theOmcPermission;
	}

	public void setOmcPermission(Boolean aOmcPermission) {
		theOmcPermission = aOmcPermission;
	}

	/** Разрешен в ОМС */
	private Boolean theOmcPermission;
	/** Класс МКБ */
	@Comment("Класс МКБ")
	@ManyToOne
	public VocIdc10Class getIdcClass() {
		return theIdcClass;
	}

	public void setIdcClass(VocIdc10Class aIdcClass) {
		theIdcClass = aIdcClass;
	}

	/** Класс МКБ */
	private VocIdc10Class theIdcClass;
	
	/** Начальный возраст */
	@Comment("Начальный возраст")
	public Double getAgeFrom() {
		return theAgeFrom;
	}

	public void setAgeFrom(Double aAgeFrom) {
		theAgeFrom = aAgeFrom;
	}

	/** Начальный возраст */
	private Double theAgeFrom;
	
	/** Конечный возраст */
	@Comment("Конечный возраст")
	public Double getAgeTo() {
		return theAgeTo;
	}

	public void setAgeTo(Double aAgeTo) {
		theAgeTo = aAgeTo;
	}

	/** Конечный возраст */
	private Double theAgeTo;
	
	/** Экстренность */
	@Comment("Экстренность")
	public Boolean getEmergency() {
		return theEmergency;
	}

	public void setEmergency(Boolean aEmergency) {
		theEmergency = aEmergency;
	}

	/** Экстренность */
	private Boolean theEmergency;
	
	/** Неактуаленный */
	@Comment("Неактуаленный")
	public Boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(Boolean aNoActuality) {theNoActuality = aNoActuality;}

	/** Неактуаленный */
	private Boolean theNoActuality;

}
