package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.MedPolicy;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.MaxLength;
import ru.nuzmsh.forms.validator.validators.MinLength;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Полис ОМС
 */
@EntityForm
@EntityFormPersistance(clazz = MedPolicy.class)
@Comment("Медицинский полис")
@WebTrail(comment = "Медицинский полис", nameProperties = "text", view = "entitySubclassView-mis_medPolicy.do")
@Parent(property = "patient", parentForm =PatientForm.class)
@Subclasses({MedPolicyOmcForm.class, MedPolicyDmcForm.class, MedPolicyOmcForeignForm.class, MedPolicyDmcForeignForm.class})
@EntityFormSecurityPrefix("/Policy/Mis/MedPolicy")
public class MedPolicyForm extends IdEntityForm {

    /** Новый тип полиса) */
	@Comment("Новый тип полиса)")
	public Long getChangePolicyType() {return theChangePolicyType;}
	public void setChangePolicyType(Long aChangePolicyType) {theChangePolicyType = aChangePolicyType;}
	/** Новый тип полиса) */
	private Long theChangePolicyType;

	/** Серия */
    @Comment("Серия")
    @Persist @DoUpperCase
    public String getSeries() { return theSeries ; }
    public void setSeries(String aSeries) { theSeries = aSeries ; }

    /** Страховая компания */
    @Comment("Страховая компания")
    @Persist
    public Long getCompany() { return theCompany ; }
    public void setCompany(Long aCompany) { theCompany = aCompany ; }

    /** Номер */
    @Comment("Номер")
    @Persist @DoUpperCase
    @Required
    public String getPolNumber() { return theNumber ; }
    public void setPolNumber(String aNumber) { theNumber = aNumber ; }

    /** Дата действия с */
    @Comment("Дата действия с")
    @Persist
    @Required
    @DateString
    public String getActualDateFrom() { return theActualDateFrom ; }
    public void setActualDateFrom(String aActualDateFrom) { theActualDateFrom = aActualDateFrom ; }

    /** Дата действия по */
    @Comment("Дата действия по")
    @Persist
    @DateString
    public String getActualDateTo() { return theActualDateTo ; }
    public void setActualDateTo(String aActualDateTo) { theActualDateTo = aActualDateTo ; }

    /** Пациент */
    @Comment("Пациент")
    @Persist
    public Long getPatient() { return thePatient ; }
    public void setPatient(Long aPatient) { thePatient = aPatient ; }

    /** Текст */
    @Comment("Текст")
    @Persist
    public String getText() { return theText ; }
    public void setText(String aText) { theText = aText ; }

    /** Фамилия */
	@Comment("Фамилия")
	@Persist @DoUpperCase
	public String getLastname() {return theLastname;	}
	public void setLastname(String aLastname) {theLastname = aLastname;	}

	/** Имя */
	@Comment("Имя")
	@Persist @DoUpperCase
	public String getFirstname() {return theFirstname;	}
	public void setFirstname(String aFirstname) {theFirstname = aFirstname;	}

	/** Отчество */
	@Comment("Отчество")
	@Persist @DoUpperCase
	public String getMiddlename() {return theMiddlename;	}
	public void setMiddlename(String aMiddlename) {theMiddlename = aMiddlename;}

	/** Отчество */
	private String theMiddlename;
	
	/** Область нахождения СМО */
	@Comment("Область нахождения СМО")
	@Persist
	public Long getInsuranceCompanyArea() {
		return theInsuranceCompanyArea;
	}

	public void setInsuranceCompanyArea(Long aInsuranceCompanyArea) {
		theInsuranceCompanyArea = aInsuranceCompanyArea;
	}

	/** Единый номер застрахованного */
	@Comment("Единый номер застрахованного")
	@Persist @MinLength(16) @MaxLength(16)
	public String getCommonNumber() {return theCommonNumber;}
	public void setCommonNumber(String aCommonNumber) {theCommonNumber = aCommonNumber;}

	/** Единый номер застрахованного */
	private String theCommonNumber;
	/** Область нахождения СМО */
	private Long theInsuranceCompanyArea;
	/** Имя */
	private String theFirstname;
	/** Фамилия */
	private String theLastname;   
	/** Текст */
    private String theText ;
    /** Пациент */
    private Long thePatient ;
    /** Дата действия по */
    private String theActualDateTo ;
    /** Дата действия с */
    private String theActualDateFrom ;
    /** Номер */
    private String theNumber ;
    /** Серия */
    private String theSeries ;
    /** Страховая компания */
    private Long theCompany ;
	/** Тип подтверждения по полису */
	@Comment("Тип подтверждения по полису")
	@Persist
	public Long getConfirmationType() {return theConfirmationType;}
	public void setConfirmationType(Long aConfirmationType) {theConfirmationType = aConfirmationType;}

	/** Тип подтверждения по полису */
	private Long theConfirmationType;
	
	/** Дата подтверждения */
	@Comment("Дата подтверждения")
	@Persist @DateString @DoDateString
	public String getConfirmationDate() {return theConfirmationDate;}
	public void setConfirmationDate(String aConfirmationDate) {theConfirmationDate = aConfirmationDate;}
	/** Дата подтверждения */
	private String theConfirmationDate;
	
	/** Дата рождения */
	@Comment("Дата рождения")
	@Persist @DateString @DoDateString
	public String getBirthday() {
		return theBirthday;
	}

	public void setBirthday(String aBirthday) {
		theBirthday = aBirthday;
	}

	/** Дата рождения */
	private String theBirthday;
}
