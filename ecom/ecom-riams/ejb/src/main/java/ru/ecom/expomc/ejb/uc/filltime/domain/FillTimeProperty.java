package ru.ecom.expomc.ejb.uc.filltime.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Свойство заполнения Time Supported")
@NamedQueries(
			@NamedQuery(name="FillTimeProperty.byPropertyName"
					, query="from FillTimeProperty where fillTime = :fillTime and property = :property")
		)
@Table(schema="SQLUser")
public class FillTimeProperty extends BaseEntity {

	/** Свойство */
	@Comment("Свойство")
	public String getProperty() {
		return theProperty;
	}

	public void setProperty(String aProperty) {
		theProperty = aProperty;
	}

	/** Преобразователь */
	@Comment("Преобразователь")
	@Column(length=16000)
	public String getTransformText() {
		return theTranformText;
	}

	public void setTransformText(String aTranformText) {
		theTranformText = aTranformText;
	}

	/** Заполнение */
	@Comment("Заполнение")
	@ManyToOne
	public FillTime getFillTime() {
		return theFillTime;
	}

	public void setFillTime(FillTime aFillTime) {
		theFillTime = aFillTime;
	}

	/** Заполнение */
	private FillTime theFillTime;
	/** Преобразователь */
	private String theTranformText;
	/** Свойство */
	private String theProperty;
}
