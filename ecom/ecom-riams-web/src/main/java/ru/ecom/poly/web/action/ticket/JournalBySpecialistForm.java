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

	/** Место обслуживания */
	@Comment("Место обслуживания")
	public Long getWorkPlaceType() {return theWorkPlaceType;}
	public void setWorkPlaceType(Long aWorkPlaceType) {theWorkPlaceType = aWorkPlaceType;}
	
	/** Внешний направитель */
	@Comment("Внешний направитель")
	public Long getOrderLpu() {return theOrderLpu;}
	public void setOrderLpu(Long aOrderLpu) {theOrderLpu = aOrderLpu;}

	/** Направитель */
	@Comment("Направитель")
	public Long getOrderWorkFunction() {return theOrderWorkFunction;}
	public void setOrderWorkFunction(Long aOrderWorkFunction) {theOrderWorkFunction = aOrderWorkFunction;}

	/** Пациент */
	@Comment("Пациент")
	public Long getPatient() {
		return thePatient;
	}

	public void setPatient(Long aPatient) {
		thePatient = aPatient;
	}

	/** Пациент */
	private Long thePatient;
	/** Направитель */
	private Long theOrderWorkFunction;
	/** Внешний направитель */
	private Long theOrderLpu;

	/** Место обслуживания */
	private Long theWorkPlaceType;
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
	
	/** Направитель */
	@Comment("Направитель")
	public Long getOrderFunction() {return theOrderFunction;}
	public void setOrderFunction(Long aOrderFunction) {theOrderFunction = aOrderFunction;}


	/** Направитель */
	private Long theOrderFunction;
	/** Дефекты */
	@Comment("Дефекты")
	public Long getDefect() {return theDefect;}
	public void setDefect(Long aDefect) {theDefect = aDefect;}

	/** Дефекты */
	private Long theDefect;
	
	/** Социальный статус */
	@Comment("Социальный статус")
	public Long getSocialStatus() {return theSocialStatus;}
	public void setSocialStatus(Long aSocialStatus) {theSocialStatus = aSocialStatus;}

	/** Социальный статус */
	private Long theSocialStatus;
	
	/** Отделение */
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}

	/** Отделение */
	private Long theDepartment;
	
	/** Гражданство */
	@Comment("Гражданство")
	public Long getNationality() {
		return theNationality;
	}

	public void setNationality(Long aNationality) {
		theNationality = aNationality;
	}

	/** Гражданство */
	private Long theNationality;
	
	/** Мед.карта */
	@Comment("Мед.карта")
	public String getMedcard() {
		return theMedcard;
	}

	public void setMedcard(String aMedcard) {
		theMedcard = aMedcard;
	}

	/** Мед.карта */
	private String theMedcard;
}
