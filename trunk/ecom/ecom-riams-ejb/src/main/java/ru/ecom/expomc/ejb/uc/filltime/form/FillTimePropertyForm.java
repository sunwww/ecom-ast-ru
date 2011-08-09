package ru.ecom.expomc.ejb.uc.filltime.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.expomc.ejb.uc.filltime.domain.FillTimeProperty;
import ru.ecom.expomc.ejb.uc.filltime.form.interceptors.FillTimePropertyCreateInterceptor;
import ru.ecom.expomc.ejb.uc.filltime.form.interceptors.FillTimePropertyViewInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz= FillTimeProperty.class)
@Comment("Свойство заполнения")
@Parent(property = "fillTime", parentForm=FillTimeForm.class)
@WebTrail(comment="Свойство", nameProperties="property", view ="exp_fillTimePropertyEdit.do")
@EntityFormSecurityPrefix("/Policy/Exp/FillTimeProperty")

@AViewInterceptors(
        @AEntityFormInterceptor(FillTimePropertyViewInterceptor.class)
)
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(FillTimePropertyCreateInterceptor.class)
)

public class FillTimePropertyForm extends IdEntityForm {
	
	/** Свойство */
	@Comment("Свойство")
	@Persist
	public String getProperty() {
		return theProperty;
	}

	public void setProperty(String aProperty) {
		theProperty = aProperty;
	}

	/** Преобразователь */
	@Comment("Преобразователь")
	@Persist
	public String getTransformText() {
		return theTranformText;
	}

	public void setTransformText(String aTranformText) {
		theTranformText = aTranformText;
	}

	/** Заполнение */
	@Comment("Заполнение")
	@Persist
	public Long getFillTime() {
		return theFillTime;
	}

	public void setFillTime(Long aFillTime) {
		theFillTime = aFillTime;
	}

	/** Идентификатор ImportDocument */
	@Comment("Идентификатор ImportDocument")
	public Long getDocumentId() {
		return theDocumentId;
	}

	public void setDocumentId(Long aDocumentId) {
		theDocumentId = aDocumentId;
	}

	/** Идентификатор ImportDocument */
	private Long theDocumentId;
	/** Заполнение */
	private Long theFillTime;
	/** Преобразователь */
	private String theTranformText;
	/** Свойство */
	private String theProperty;
}
