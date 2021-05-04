package ru.ecom.mis.ejb.form.patient;

import lombok.Setter;
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
@Setter
public class MedPolicyForm extends IdEntityForm {

    /** Новый тип полиса) */
	@Comment("Новый тип полиса)")
	public Long getChangePolicyType() {return changePolicyType;}
	/** Новый тип полиса) */
	private Long changePolicyType;

	/** Серия */
    @Comment("Серия")
    @Persist @DoUpperCase
    public String getSeries() { return series ; }

    /** Страховая компания */
    @Comment("Страховая компания")
    @Persist
    public Long getCompany() { return company ; }

    /** Номер */
    @Comment("Номер")
    @Persist @DoUpperCase
    @Required
    public String getPolNumber() { return number ; }
	public void setPolNumber(String number) {
    	this.number = number;
	}

    /** Дата действия с */
    @Comment("Дата действия с")
    @Persist
    @Required
    @DateString
    public String getActualDateFrom() { return actualDateFrom ; }

    /** Дата действия по */
    @Comment("Дата действия по")
    @Persist
    @DateString
    public String getActualDateTo() { return actualDateTo ; }

    /** Пациент */
    @Comment("Пациент")
    @Persist
    public Long getPatient() { return patient ; }

    /** Текст */
    @Comment("Текст")
    @Persist
    public String getText() { return text ; }

    /** Фамилия */
	@Comment("Фамилия")
	@Persist @DoUpperCase
	public String getLastname() {return lastname;	}

	/** Имя */
	@Comment("Имя")
	@Persist @DoUpperCase
	public String getFirstname() {return firstname;	}

	/** Отчество */
	@Comment("Отчество")
	@Persist @DoUpperCase
	public String getMiddlename() {return middlename;	}

	/** Отчество */
	private String middlename;
	
	/** Область нахождения СМО */
	@Comment("Область нахождения СМО")
	@Persist
	public Long getInsuranceCompanyArea() {
		return insuranceCompanyArea;
	}

	/** Единый номер застрахованного */
	@Comment("Единый номер застрахованного")
	@Persist @MinLength(16) @MaxLength(16)
	public String getCommonNumber() {return commonNumber;}

	/** Единый номер застрахованного */
	private String commonNumber;
	/** Область нахождения СМО */
	private Long insuranceCompanyArea;
	/** Имя */
	private String firstname;
	/** Фамилия */
	private String lastname;   
	/** Текст */
    private String text ;
    /** Пациент */
    private Long patient ;
    /** Дата действия по */
    private String actualDateTo ;
    /** Дата действия с */
    private String actualDateFrom ;
    /** Номер */
    private String number ;
    /** Серия */
    private String series ;
    /** Страховая компания */
    private Long company ;
	/** Тип подтверждения по полису */
	@Comment("Тип подтверждения по полису")
	@Persist
	public Long getConfirmationType() {return confirmationType;}

	/** Тип подтверждения по полису */
	private Long confirmationType;
	
	/** Дата подтверждения */
	@Comment("Дата подтверждения")
	@Persist @DateString @DoDateString
	public String getConfirmationDate() {return confirmationDate;}
	/** Дата подтверждения */
	private String confirmationDate;
	
	/** Дата рождения */
	@Comment("Дата рождения")
	@Persist @DateString @DoDateString
	public String getBirthday() {
		return birthday;
	}

	/** Дата рождения */
	private String birthday;
}
