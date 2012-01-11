package ru.ecom.mis.ejb.domain.worker;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.worker.voc.VocAcademicDegree;
import ru.ecom.mis.ejb.domain.worker.voc.VocAcademicStatus;
import ru.ecom.mis.ejb.domain.worker.voc.VocCertificateIssueBase;
import ru.ecom.mis.ejb.domain.worker.voc.VocCategory;
import ru.ecom.mis.ejb.domain.worker.voc.VocInstitut;
import ru.ecom.mis.ejb.domain.worker.voc.VocSpec;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Квалификация
 */
@Entity
@Comment("Квалификация")
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties={"person"})
})
public class Qualification extends BaseEntity {
	
	/** Обучение */
	@Comment("Обучение")
	@OneToOne
	public Education getEducation() {
		return theEducation;
	}
	public void setEducation(Education aEducation) {
		theEducation = aEducation;
	}
	/** Обучение */
	private Education theEducation;
	 	
    /** Основание выдачи сертификата*/
    @Comment("Основание выдачи сертификата")
    @OneToOne
	public VocCertificateIssueBase getCertificateIssueBase() { return theCertificateIssueBase ; }
    public void setCertificateIssueBase(VocCertificateIssueBase aBase) { theCertificateIssueBase = aBase ; }
    /** Основание выдачи сертификата*/
    private VocCertificateIssueBase theCertificateIssueBase ;
    
    /** Номер сертификата */
    @Comment("Номер сертификата")
    public String getCertificateNumber() { return theCertificateNumber ; }
    public void setCertificateNumber(String aNumberCertif) { theCertificateNumber = aNumberCertif ; }
    /** Номер сертификата */
    private String theCertificateNumber ;
    
    /** Дата выдачи сертификата */
    @Comment("Дата выдачи сертификата")
    public Date getCertificateIssueDate() { return theCertificateIssueDate ; }
    public void setCertificateIssueDate(Date aDateGetCertif) { theCertificateIssueDate = aDateGetCertif ; }
    /** Дата выдачи сертификата */
    private Date theCertificateIssueDate ;
    
    /** Организация */
    @Comment("Организация")
    @OneToOne
    public VocInstitut getInstitut() { return theInstitut ; }
    public void setInstitut(VocInstitut aInstitut) { theInstitut = aInstitut ; }

    /** Организация */
    private VocInstitut theInstitut ;
    
    /** Специальность */
    @Comment("Специальность")
    @OneToOne
    public VocSpec getSpec() { return theSpec ; }
    public void setSpec(VocSpec aSpec) { theSpec = aSpec ; }
    /** Специальность */
    private VocSpec theSpec ;
    
    /** Присвоенное звание */
    @Comment("Присвоенное звание")
    @OneToOne
    public VocAcademicStatus getAcademicStatus() { return theAcademicStatus ; }
    public void setAcademicStatus(VocAcademicStatus aAcademicStatus) { theAcademicStatus = aAcademicStatus ; }
    /** Присвоенное звание */
    private VocAcademicStatus theAcademicStatus ;
    
    /** Присвоенная степень */
    @Comment("Присвоенная степень")
    @OneToOne
    public VocAcademicDegree getAcademicDegree() { return theAcademicDegree ; }
    public void setAcademicDegree(VocAcademicDegree aAcademicDegree) { theAcademicDegree = aAcademicDegree ; }
    /** Присвоенная степень */
    private VocAcademicDegree theAcademicDegree ;
    
    /** Присвоенная категория */
    @Comment("Присвоенная категория")
    @OneToOne
    public VocCategory getCategory() { return theCategory ; }
    public void setCategory(VocCategory aCategory) { theCategory = aCategory ; }
    /** Присвоенная категория */
    private VocCategory theCategory ;
    
    /** Дата присвоения */
    @Comment("Дата присвоения")
    public Date getAwardingDate() { return theAwardingDate ; }
    public void setAwardingDate(Date aAwardingDate) { theAwardingDate = aAwardingDate ; }
    /** Дата присвоения */
    private Date theAwardingDate ;
    
   /** Персона */
    @Comment("Персона")
    @ManyToOne
    public Patient getPerson() { return thePerson ; }
    public void setPerson(Patient aPerson) { thePerson = aPerson ; }
    /** Персона */
    private Patient thePerson ;

    /** Категория */
	@Comment("Категория")
	@Transient
	public String getCategoryText() {
		return theCategory!=null? theCategory.getName():"";
	}
	/** Степень (текст) */
	@Comment("Степень (текст)")
	@Transient
	public String getAcademicDegreeText() {
		return theAcademicDegree!=null?theAcademicDegree.getName():"";
	}
	
	/** Звание (текст) */
	@Comment("Звание (текст)")
	@Transient
	public String getAcademicStatusText() {
		return theAcademicStatus!=null ? theAcademicStatus.getName():"";
	}
}
