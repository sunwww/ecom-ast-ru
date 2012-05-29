package ru.ecom.mis.ejb.domain.patient;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcKodTer;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.ecom.mis.ejb.domain.patient.voc.VocPolicyConfirmationType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Медицинский полис
 */
@Entity
@AIndexes({
		/*@AIndex(name="Index1", unique = false, properties={"series","polNumber","company"})
		,@AIndex(name ="Index2", properties={"patient","polNumber"})
		, @AIndex(name="Index3", properties={"patient","series","polNumber"})
		,@AIndex(name="Index5", properties={"patient"})		
		,@AIndex(name="Index4", properties={"patient","actualDateFrom","actualDateTo"})
		*/
	@AIndex(properties={"series"})
	,@AIndex(properties={"polNumber"})
	,@AIndex(properties={"patient"})		
	,@AIndex(properties={"actualDateTo"})
	,@AIndex(properties={"actualDateFrom"})
	,@AIndex(properties={"commonNumber"})
	,@AIndex(properties={"company"})
	,@AIndex(properties={"insuranceCompanyArea"})
})
@Table(schema="SQLUser")
abstract public class MedPolicy extends BaseEntity {
 
    /** Страховая компания */
    @OneToOne
    public RegInsuranceCompany getCompany() { return theCompany ; }
    public void setCompany(RegInsuranceCompany aCompany) { theCompany = aCompany ; }

    /** Серия полиса */
    public String getSeries() { return theSeries ; }
    public void setSeries(String aSeries) { theSeries = aSeries ; }

    /** Номер полиса */
    public String getPolNumber() { return theNumber ; }
    public void setPolNumber(String aNumber) { theNumber = aNumber ; }

    /** Дата действия с */
    public Date getActualDateFrom() { return theActualDateFrom ; }
    public void setActualDateFrom(Date aActualDateFrom) { theActualDateFrom = aActualDateFrom ; }

    /** Дата действия по */
    public Date getActualDateTo() { return theActualDateTo ; }
    public void setActualDateTo(Date aActualDateTo) { theActualDateTo = aActualDateTo ; }

    /** Пациент */
    @ManyToOne
    public Patient getPatient() { return thePatient ; }
    public void setPatient(Patient aPatient) { thePatient = aPatient ; }

    /** Текст */
    @Transient
    public String getText() { return "Не переопределен метод getText() у класса "+getClass().getName() ; }
    public void setText(String aText) { }

    
    /** Фамилия */
	@Comment("Фамилия")
	public String getLastname() {return theLastname;	}
	public void setLastname(String aLastname) {theLastname = aLastname;	}

	/** Имя */
	@Comment("Имя")
	public String getFirstname() {return theFirstname;	}
	public void setFirstname(String aFirstname) {theFirstname = aFirstname;	}

	/** Отчество */
	@Comment("Отчество")
	public String getMiddlename() {return theMiddlename;	}
	public void setMiddlename(String aMiddlename) {theMiddlename = aMiddlename;}
	
	/** Единый номер застрахованного */
	@Comment("Единый номер застрахованного")
	public String getCommonNumber() {return theCommonNumber;}
	public void setCommonNumber(String aCommonNumber) {theCommonNumber = aCommonNumber;}

	/** Единый номер застрахованного */
	private String theCommonNumber;

	/** Отчество */
	private String theMiddlename;
	/** Имя */
	private String theFirstname;
	/** Фамилия */
	private String theLastname;
    /** Пациент */
    private Patient thePatient ;
    /** Дата действия по */
    private Date theActualDateTo ;
    /** Дата действия с */
    private Date theActualDateFrom ;
    /** Номер полиса */
    private String theNumber ;
    /** Серия полиса */
    private String theSeries ;

    /** Страховая компания */
    private RegInsuranceCompany theCompany ;
    
    /** Область нахождения СМО */
	@Comment("Область нахождения СМО")
	@OneToOne
	public OmcKodTer getInsuranceCompanyArea() {
		return theInsuranceCompanyArea;
	}

	public void setInsuranceCompanyArea(OmcKodTer aInsuranceCompanyArea) {
		theInsuranceCompanyArea = aInsuranceCompanyArea;
	}

	/** Область нахождения СМО */
	private OmcKodTer theInsuranceCompanyArea;
	
	/** Дата подтверждения */
	@Comment("Дата подтверждения")
	public Date getConfirmationDate() {
		return theConfirmationDate;
	}

	public void setConfirmationDate(Date aConfirmationDate) {
		theConfirmationDate = aConfirmationDate;
	}
	/** Тип подтверждения по полису */
	@Comment("Тип подтверждения по полису")
	@OneToOne
	public VocPolicyConfirmationType getConfirmationType() {
		return theConfirmationType;
	}

	public void setConfirmationType(VocPolicyConfirmationType aConfirmationType) {
		theConfirmationType = aConfirmationType;
	}

	/** Тип подтверждения по полису */
	private VocPolicyConfirmationType theConfirmationType;
	/** Дата подтверждения */
	private Date theConfirmationDate;
	
	/** Дата рождения */
	@Comment("Дата рождения")
	public Date getBirthday() {
		return theBirthday;
	}

	public void setBirthday(Date aBirthday) {
		theBirthday = aBirthday;
	}

	/** Дата рождения */
	private Date theBirthday;

}
