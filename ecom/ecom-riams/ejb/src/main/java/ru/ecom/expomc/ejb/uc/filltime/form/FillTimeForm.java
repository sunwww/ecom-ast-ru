package ru.ecom.expomc.ejb.uc.filltime.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expomc.ejb.uc.filltime.domain.FillTime;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


@EntityForm
@EntityFormPersistance(clazz= FillTime.class)
@Comment("Заполнение")
@EntityFormSecurityPrefix("/Policy/Exp/FillTime")
@WebTrail(comment="Заполнение", nameProperties="name", view="entityView-exp_fillTime.do")
public class FillTimeForm extends IdEntityForm {
	
	
	/** Формат */
	@Comment("Формат")
	@Persist
	public Long getFormat() {
		return theFormat;
	}

	public void setFormat(Long aFormat) {
		theFormat = aFormat;
	}

	/** Формат */
	private Long theFormat;
	/** Название */
	@Comment("Название")
	@Persist
	public String getName() {
		return theName;
	}

	/** Строка SQL */
	@Comment("Строка SQL")
	@Persist
	public String getQueryString() {
		return theQueryString;
	}

	public void setQueryString(String aQueryString) {
		theQueryString = aQueryString;
	}

	/** Документ импорта */
	@Comment("Документ импорта")
	@Persist
	public Long getOutputDocument() {
		return theOutputDocument;
	}

	public void setOutputDocument(Long aOutputDocument) {
		theOutputDocument = aOutputDocument;
	}

	/** Документ импорта */
	private Long theOutputDocument;
	/** Строка SQL */
	private String theQueryString;
	public void setName(String aName) {
		theName = aName;
	}

	/** Инициализация перед каждой сущностью */
	@Comment("Инициализация перед каждой сущностью")
	@Persist
	public String getInitText() {
		return theInitText;
	}

	public void setInitText(String aInitText) {
		theInitText = aInitText;
	}

	/** Инициализация перед каждой сущностью */
	private String theInitText;
	
	/** Название */
	private String theName;	
}
