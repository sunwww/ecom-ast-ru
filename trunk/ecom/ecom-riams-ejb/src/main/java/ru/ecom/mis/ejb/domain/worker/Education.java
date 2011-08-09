package ru.ecom.mis.ejb.domain.worker;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.worker.voc.VocEducationType;
import ru.ecom.mis.ejb.domain.worker.voc.VocInstitut;
import ru.ecom.mis.ejb.domain.worker.voc.VocSpec;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Образование
 */
@Entity
@Comment("Образование")
@Table(schema="SQLUser")
public class Education extends BaseEntity {



	/** Институт */
    @OneToOne
    @Comment("Институт")
    public VocInstitut getInstitut() { return theInstitut ; }
    public void setInstitut(VocInstitut aInstitut) { theInstitut = aInstitut ; }
    private VocInstitut theInstitut;
    
    /** Факультет */
	@Comment("Факультет")
	public String getFaculty() {
		return theFaculty;
	}
	public void setFaculty(String aFaculty) {
		theFaculty = aFaculty;
	}
	/** Факультет */
	private String theFaculty;
	
	/** Тип обучения */
	@Comment("Тип обучения")
	@OneToOne
	public VocEducationType getType() {
		return theType;
	}
	public void setType(VocEducationType aType) {
		theType = aType;
	}
	/** Тип обучения */
	private VocEducationType theType;
	
	/** Квалификация */
	@Comment("Квалификация")
	@OneToOne
	public Qualification getQualification() {
		return theQualification;
	}
	public void setQualification(Qualification aQualification) {
		theQualification = aQualification;
	}
	/** Квалификация */
	private Qualification theQualification;
	
    /** Дата начала обучения */
    @Comment("Дата начала обучения")
    public Date getDateStart() { return theDateStart ; }
    public void setDateStart(Date aDateStart) { theDateStart = aDateStart ; }
    /** Дата начала обучения */
    private Date theDateStart ;
    
    /** Дата окончания обучения */
    @Comment("Дата окончания обучения")
    public Date getDateFinish() { return theDateFinish ; }
    public void setDateFinish(Date aDateFinish) { theDateFinish = aDateFinish ; }
    /** Дата окончания обучения */
    private Date theDateFinish ;
    
    /** Номер диплома */
    @Comment("Номер диплома")
    public String getNumberDiplom() { return theNumberDiplom ; }
    public void setNumberDiplom(String aNumberDiplom) { theNumberDiplom = aNumberDiplom ; }
    /** Номер диплома */
    private String theNumberDiplom ;
    
    /** Специальность по диплому */
    @OneToOne
    @Comment("Специальность по диплому")
    public VocSpec getSpec() { return theSpec ; }
    public void setSpec(VocSpec aSpec) { theSpec = aSpec ; }
    /** Специальность по диплому */
    private VocSpec theSpec ;
    
    /** Персона */
    @Comment("Персона")
    @ManyToOne
    public Patient getPerson() { return thePerson ; }
    public void setPerson(Patient aPerson) { thePerson = aPerson ; }
    /** Персона */
    private Patient thePerson ;


    // [start] Transient
    /** НАзвание института (text) */
	@Comment("НАзвание института (text)")
	@Transient
	public String getInstitutText() {
		return theInstitut!=null ? theInstitut.getName() : "";
	}

	/** Название сцециальности (text) */
	@Comment("Название сцециальности (text)")
	@Transient
	public String getSpecText() {
		return theSpec!=null? theSpec.getName() : "";
	}


}
