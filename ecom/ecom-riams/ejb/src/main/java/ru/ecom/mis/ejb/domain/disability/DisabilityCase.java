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

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class DisabilityCase extends BaseEntity{
	
	/** Пациент */
	@Comment("Пациент")
	@ManyToOne
	public Patient getPatient() {return patient;}

	/** СМО */
	@Comment("СМО")
	@ManyToOne
	public MedCase getMedCase() {return medCase;}

	
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
	public List<DisabilityDocument> getDisabilityDocuments() {return disabilityDocuments;}

	/** Дочерние случаи нетрудоспособности */
	@Comment("Дочерние случаи нетрудоспособности")
	@OneToMany(mappedBy="parentDisabiblityCase", cascade=CascadeType.ALL)
	public List<DisabilityCase> getChildDisabiblityCases() {return childDisabiblityCases;}

	/** Родительский случай нетрудоспособности */
	@Comment("Родительский случай нетрудоспособности")
	@ManyToOne
	public DisabilityCase getParentDisabiblityCase() {return parentDisabiblityCase;}

	/** Лицо по уходу 1*/
	@Comment("Лицо по уходу 1")
	@OneToOne
	public Kinsman getNursingPerson1() {return nursingPerson1;}

	/** Лицо по уходу 2*/
	@Comment("Лицо по уходу 2")
	@OneToOne
	public Kinsman getNursingPerson2() {return nursingPerson2;}

	/**
	 * Поставлена на учет в ранние сроки беременности (до 12 недель)
	 */
	@Comment("Поставлена на учет в ранние сроки беременности (до 12 недель)")
	public Boolean getEarlyPregnancyRegistration() {return earlyPregnancyRegistration;}

	/** Состоит на учете в службе занятости */
	private Boolean placementService;

	/** Поставлена на учет в ранние сроки беременности (до 12 недель) */
	private Boolean earlyPregnancyRegistration;
	/** Лицо по уходу 2*/
	private Kinsman nursingPerson2;
	/** Лицо по уходу 1*/
	private Kinsman nursingPerson1;
	/** Пациент */
	private Patient patient;
	/** СМО */
	private MedCase medCase;
	/** Документы нетрудоспособности */
	private List<DisabilityDocument> disabilityDocuments;
	/** Дочерние случаи нетрудоспособности */
	private List<DisabilityCase> childDisabiblityCases;
	/** Родительский случай нетрудоспособности */
	private DisabilityCase parentDisabiblityCase;

	/** Дата редактирования */
	private Date editDate;
	/** Пользователь, редактировавший документ */
	private String editUsername;
	/** Дата создания */
	private Date createDate;
	/** Пользователь, создавший документ */
	private String createUsername;
	
	/** Возраст пациента */
	private Long agePatient;
	
	/** Длительность */
	private Long durationCase;
}
