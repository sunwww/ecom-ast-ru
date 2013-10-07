package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.Act;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = Act.class)
@Comment("Акт")
@WebTrail(comment = "Акт", nameProperties= "id", list="entityParentList-personaldata_act.do", view="entityParentView-personaldata_act.do")
@Subclasses(value = { CopiesTransferActForm.class, CardTransferActForm.class
		, ExternalCarrierDestructionActForm.class })
@EntityFormSecurityPrefix("/Policy/PersData/Act")
public class ActForm extends JournalDataForm{
	/**
	 * Номер акта
	 */
	@Comment("Номер акта")
	@Persist
	public String getActNumber() {return theActNumber;}
	public void setActNumber(String aActNumber) {theActNumber = aActNumber;}
	
	/** Количество копий */
	@Comment("Количество копий")
	@Persist
	public Long getCopiesAmount() {return theCopiesAmount;}
	public void setCopiesAmount(Long aCopiesAmount) {theCopiesAmount = aCopiesAmount;}

	/** Количество копий */
	private Long theCopiesAmount;
	/**
	 * Номер акта
	 */
	private String theActNumber;
}
