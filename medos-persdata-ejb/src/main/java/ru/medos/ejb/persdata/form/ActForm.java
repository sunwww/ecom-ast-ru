package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.Act;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.MaxDateCurrent;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = Act.class)
@Comment("Акт")
@WebTrail(comment = "Акт", nameProperties= "id", view="entitySubclassView-pd_act.do")
@Subclasses(value = { CopiesTransferActForm.class, CardTransferActForm.class
		, ExternalCarrierDestructionActForm.class, CopiesDestructionActForm.class,
		})
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
	/**
	 * Дата начала актуальности
	 */
	@Comment("Дата начала актуальности")
	@Persist @Required @MaxDateCurrent
	@DateString @DoDateString
	public String getUrgencyStartDate() {
		return theUrgencyStartDate;
	}
	public void setUrgencyStartDate(String aUrgencyStartDate) {
		theUrgencyStartDate = aUrgencyStartDate;
	}
	/**
	 * Дата начала актуальности
	 */
	private String theUrgencyStartDate;
	/** Файл выгрузки */
	@Comment("Файл выгрузки")
	@Persist
	public String getFilenameExport() {return theFilenameExport;}
	public void setFilenameExport(String aFilenameExport) {theFilenameExport = aFilenameExport;}

	
	/** Filename info */
	@Comment("Filename info")
	public String getFilenameInfo() {return theFilenameInfo;}
	public void setFilenameInfo(String aFilenameInfo) {theFilenameInfo = aFilenameInfo;}

	/** Filename info */
	private String theFilenameInfo;
	/** Файл выгрузки */
	private String theFilenameExport;
}
