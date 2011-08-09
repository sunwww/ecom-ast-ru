package ru.ecom.expomc.ejb.uc.filltime.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.format.Format;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Заполнение Time Supported")
@Table(schema="SQLUser")
public class FillTime extends BaseEntity {

	/** Формат (для удобства) */
	@Comment("Формат (для удобства)")
	@OneToOne
	public Format getFormat() {
		return theFormat;
	}

	public void setFormat(Format aFormat) {
		theFormat = aFormat;
	}

	/** Название */
	@Comment("Название")
	public String getName() {
		return theName;
	}

	/** Строка SQL */
	@Comment("Строка SQL")
	public String getQueryString() {
		return theQueryString;
	}

	public void setQueryString(String aQueryString) {
		theQueryString = aQueryString;
	}

	/** Документ импорта */
	@Comment("Документ импорта")
	@OneToOne
	public ImportDocument getOutputDocument() {
		return theOutputDocument;
	}

	public void setOutputDocument(ImportDocument aOutputDocument) {
		theOutputDocument = aOutputDocument;
	}

	/** Свойства заполнения */
	@Comment("Свойства заполнения")
	@OneToMany(mappedBy="fillTime", cascade = CascadeType.ALL)
	public List<FillTimeProperty> getProperties() {
		return theProperties;
	}

	public void setProperties(List<FillTimeProperty> aProperties) {
		theProperties = aProperties;
	}

	/** Инициализация перед каждой сущностью */
	@Comment("Инициализация перед каждой сущностью")
	@Column(length=16000, columnDefinition="varchar(16000)")
	public String getInitText() {
		return theInitText;
	}

	public void setInitText(String aInitText) {
		theInitText = aInitText;
	}

	/** Инициализация перед каждой сущностью */
	private String theInitText;
	
	/** Свойства заполнения */
	private List<FillTimeProperty> theProperties;
	/** Документ импорта */
	private ImportDocument theOutputDocument;
	/** Строка SQL */
	private String theQueryString;
	public void setName(String aName) {
		theName = aName;
	}

	/** Название */
	private String theName;
	/** Формат (для удобвства) */
	private Format theFormat;
}
