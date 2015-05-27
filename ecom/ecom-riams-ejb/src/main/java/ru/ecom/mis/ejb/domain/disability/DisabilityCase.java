package ru.ecom.mis.ejb.domain.disability;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.util.DurationUtil;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.patient.Kinsman;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Случай нетрудоспособности
 * @author azviagin,stkacheva
 *
 */
@Comment("Случай нетрудоспособности")
@Entity
@AIndexes({
	@AIndex(unique = false, properties= {"patient"})
})
@Table(schema="SQLUser")
public class DisabilityCase extends BaseEntity{
	
	/** Пациент */
	@Comment("Пациент")
	@ManyToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}
	
	/** СМО */
	@Comment("СМО")
	@ManyToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	
	/** Дата начала */
	@Comment("Дата начала")
	@Transient
	public Date getDateFrom() {
		return getDisabilityDocuments().isEmpty() 
		? null 
		: getDisabilityDocuments().get(0).getDateFrom(); 
	}

	/** Дата окончания */
	@Comment("Дата окончания")
	@Transient
	public Date getDateTo() {
		return getDisabilityDocuments().isEmpty() 
			? null 
			: getDisabilityDocuments().get(getDisabilityDocuments().size()-1).getDateTo(); 
	}

	
	/** Продолжительность */
	@Comment("Продолжительность")
	@Transient
	public String getDuration() {
		return DurationUtil.getDurationMedCase(getDateFrom(), getDateTo(),1,1);
	}
	
	/** Документы нетрудоспособности */
	@Comment("Документы нетрудоспособности")
	@OneToMany(mappedBy="disabilityCase", cascade=CascadeType.ALL)
	// @OrderBy("dateFrom") // FIXME нужно свойство dateFrom в DisabilityDocument
	public List<DisabilityDocument> getDisabilityDocuments() {return theDisabilityDocuments;}
	public void setDisabilityDocuments(List<DisabilityDocument> aDisabilityDocuments) {theDisabilityDocuments = aDisabilityDocuments;}
	
	/** Дочерние случаи нетрудоспособности */
	@Comment("Дочерние случаи нетрудоспособности")
	@OneToMany(mappedBy="parentDisabiblityCase", cascade=CascadeType.ALL)
	public List<DisabilityCase> getChildDisabiblityCases() {return theChildDisabiblityCases;}
	public void setChildDisabiblityCases(List<DisabilityCase> aChildDisabiblityCases) {theChildDisabiblityCases = aChildDisabiblityCases;}
	
	/** Родительский случай нетрудоспособности */
	@Comment("Родительский случай нетрудоспособности")
	@ManyToOne
	public DisabilityCase getParentDisabiblityCase() {return theParentDisabiblityCase;}
	public void setParentDisabiblityCase(DisabilityCase aParentDisabiblityCase) {theParentDisabiblityCase = aParentDisabiblityCase;}

	/** Лицо по уходу 1*/
	@Comment("Лицо по уходу 1")
	@OneToOne
	public Kinsman getNursingPerson1() {return theNursingPerson1;}
	public void setNursingPerson1(Kinsman aNursingPerson1) {theNursingPerson1 = aNursingPerson1;}

	/** Лицо по уходу 2*/
	@Comment("Лицо по уходу 2")
	@OneToOne
	public Kinsman getNursingPerson2() {return theNursingPerson2;}
	public void setNursingPerson2(Kinsman aNursingPerson2) {theNursingPerson2 = aNursingPerson2;}
	

	/**
	 * Поставлена на учет в ранние сроки беременности (до 12 недель)
	 */
	@Comment("Поставлена на учет в ранние сроки беременности (до 12 недель)")
	public Boolean getEarlyPregnancyRegistration() {return theEarlyPregnancyRegistration;}
	public void setEarlyPregnancyRegistration(Boolean aEarlyPregnancyRegistration) {theEarlyPregnancyRegistration = aEarlyPregnancyRegistration;}
	
	/** Состоит на учете в службе занятости */
	@Comment("Состоит на учете в службе занятости")
	public Boolean getPlacementService() {return thePlacementService;}
	public void setPlacementService(Boolean aPlacementService) {thePlacementService = aPlacementService;}
	/** Состоит на учете в службе занятости*/
	private Boolean thePlacementService;

	/** Поставлена на учет в ранние сроки беременности (до 12 недель) */
	private Boolean theEarlyPregnancyRegistration;
	/** Лицо по уходу 2*/
	private Kinsman theNursingPerson2;
	/** Лицо по уходу 1*/
	private Kinsman theNursingPerson1;
	/** Пациент */
	private Patient thePatient;
	/** СМО */
	private MedCase theMedCase;
	/** Документы нетрудоспособности */
	private List<DisabilityDocument> theDisabilityDocuments;
	/** Дочерние случаи нетрудоспособности */
	private List<DisabilityCase> theChildDisabiblityCases;
	/** Родительский случай нетрудоспособности */
	private DisabilityCase theParentDisabiblityCase;
	/** Пользователь, создавший документ */
	@Comment("Пользователь, создавший документ")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aUsernameCreate) {theCreateUsername = aUsernameCreate;}

	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aDateCreate) {theCreateDate = aDateCreate;}

	/** Пользователь, редактировавший документ */
	@Comment("Пользователь, редактировавший документ")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aUsernameEdit) {theEditUsername = aUsernameEdit;}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aDateEdit) {theEditDate = aDateEdit;}

	/** Дата редактирования */
	private Date theEditDate;
	/** Пользователь, редактировавший документ */
	private String theEditUsername;
	/** Дата создания */
	private Date theCreateDate;
	/** Пользователь, создавший документ */
	private String theCreateUsername;
	
	/** Возраст пациента */
	@Comment("Возраст пациента")
	public Long getAgePatient() {
		return theAgePatient;
	}

	public void setAgePatient(Long aAgePatient) {
		theAgePatient = aAgePatient;
	}

	/** Возраст пациента */
	private Long theAgePatient;
	
	/** Длительность */
	@Comment("Длительность")
	public Long getDurationCase() {
		return theDurationCase;
	}

	public void setDurationCase(Long aDurationCase) {
		theDurationCase = aDurationCase;
	}

	/** Длительность */
	private Long theDurationCase;
}
