package ru.ecom.poly.web.action.ticket;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

public class JournalBySpecialistForm extends BaseValidatorForm {
	/** Специалист */
	@Comment("Специалист")
	public Long getSpecialist() {return theSpecialist;}
	public void setSpecialist(Long aSpecialist) {	theSpecialist = aSpecialist;}

	/** Первичность по данному заболеванию в данном году */
	@Comment("Первичность по данному заболеванию в данном году")
	public Long getPrimaryInYear() {return thePrimaryInYear;}
	public void setPrimaryInYear(Long aPrimaryInYear) {thePrimaryInYear = aPrimaryInYear;}

	/** Район */
	@Comment("Район")
	public Long getRayon() {return theRayon;}
	public void setRayon(Long aRayon) {theRayon = aRayon;}

	/** Дата начала периода */
	@Comment("Дата начала периода")
	@DateString @DoDateString @Required
	public String getBeginDate() {return theBeginDate;}
	public void setBeginDate(String aBeginDate) {theBeginDate = aBeginDate;}

	/** Дата окончания периода */
	@Comment("Дата окончания периода")
	@DateString @DoDateString @Required
	public String getFinishDate() {return theFinishDate;}
	public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}

	/** Номер в журнале */
	@Comment("Номер в журнале")
	public Long getNumberInJournal() {return theNumberInJournal;}
	public void setNumberInJournal(Long aNumberInJournal) {theNumberInJournal = aNumberInJournal;}

	/** Сортировка по специалисту */
	@Comment("Сортировка по специалисту")
	public Boolean getOrderBySpecialist() {return theOrderBySpecialist;}
	public void setOrderBySpecialist(Boolean aOrderBySpecialist) {theOrderBySpecialist = aOrderBySpecialist;}

	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** ЛПУ */
	@Comment("ЛПУ")
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	public Long getServiceStream() {
		return theServiceStream;
	}

	public void setServiceStream(Long aServiceStream) {
		theServiceStream = aServiceStream;
	}

	/** Поток обслуживания */
	private Long theServiceStream;
	/** ЛПУ */
	private Long theLpu;
	/** Рабочая функция */
	private Long theWorkFunction;
	/** Сортировка по специалисту */
	private Boolean theOrderBySpecialist;
	/** Номер в журнале */
	private Long theNumberInJournal;
	/** Дата окончания периода */
	private String theFinishDate;
	/** Дата начала периода */
	private String theBeginDate;
	/** Район */
	private Long theRayon;
	/** Первичность по данному заболеванию в данном году */
	private Long thePrimaryInYear;
	/** Специалист */
	private Long theSpecialist;

}
