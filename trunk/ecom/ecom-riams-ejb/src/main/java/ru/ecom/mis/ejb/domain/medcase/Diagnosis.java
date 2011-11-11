package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.expomc.ejb.domain.med.VocDiagnosis;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcRoadTrafficInjury;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAcuityDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPrimaryDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTraumaType;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.poly.ejb.domain.voc.VocIllnesPrimary;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/**
 * Диагноз
 */
@Entity
@AIndexes({
	@AIndex(properties="medCase"),
	@AIndex(properties="patient"),
	@AIndex(properties={"patient","idc10"}),
	@AIndex(properties={"patient","idc10","establishDate","priority"}),
	@AIndex(properties={"patient","idc10","establishDate"})
    }) 
@Table(schema="SQLUser")
public class Diagnosis extends BaseEntity {

	/**
	 * Наименование
	 */
	@Comment("Наименование")
	@Column(length=500)
	public String getName() {return theName;}

	/**
	 * Наименование
	 */
	public void setName(String aNewProperty) {theName = aNewProperty;}

	/**
	 * Наименование
	 */
	private String theName;

	/**
	 * Приоритет
	 */
	@Comment("Приоритет")
	@OneToOne
	public VocPriorityDiagnosis getPriority() {return thePriority;}

	@Transient
	public String getPriorityDiagnosisText() {return thePriority!=null ? thePriority.getName() : "" ;}
	
	
	/**
	 * Приоритет
	 */
	public void setPriority(VocPriorityDiagnosis aNewProperty) {
		thePriority = aNewProperty;
	}

	/**
	 * Приоритет
	 */
	private VocPriorityDiagnosis thePriority;

	/**
	 * Острота
	 */
	@Comment("Острота")
	@OneToOne
	public VocAcuityDiagnosis getAcuity() {
		return theAcuity;
	}

	/**
	 * Острота
	 */
	public void setAcuity(VocAcuityDiagnosis aNewProperty) {
		theAcuity = aNewProperty;
	}

	/**
	 * Острота
	 */
	private VocAcuityDiagnosis theAcuity;

	/**
	 * Первичность 
	 */
	@Comment("Первичность")
	@OneToOne
	public VocPrimaryDiagnosis getPrimary() {
		return thePrimary;
	}

	/**
	 * Первичность
	 */
	public void setPrimary(VocPrimaryDiagnosis aNewProperty) {
		thePrimary = aNewProperty;
	}

	/**
	 * Первичность
	 */
	private VocPrimaryDiagnosis thePrimary;

	/**
	 * Дата уточнения диагноза
	 */
	@Comment("Дата уточнения диагноза")
	public Date getAccurationDate() {
		return theAccurationDate;
	}

	/**
	 * Дата уточнения диагноза
	 */
	public void setAccurationDate(Date aNewProperty) {
		theAccurationDate = aNewProperty;
	}

	/**
	 * Дата уточнения диагноза
	 */
	private Date theAccurationDate;

	/**
	 * Предыдущий диагноз
	 */
	@Comment("Предыдущий диагноз")
	@OneToOne
	public Diagnosis getDiagnosisPrior() {
		return theDiagnosisPrior;
	}

	/**
	 * Предыдущий диагноз
	 */
	public void setDiagnosisPrior(Diagnosis aNewProperty) {
		theDiagnosisPrior = aNewProperty;
	}

	/**
	 * Предыдущий диагноз
	 */
	private Diagnosis theDiagnosisPrior;

		/**
	 * МКБ10 причины травмы
	 */
	@Comment("МКБ10 причины травмы")
	@OneToOne
	public VocIdc10 getIdc10Reason() {
		return theIdc10Reason;
	}

	/**
	 * МКБ10 причины травмы
	 */
	public void setIdc10Reason(VocIdc10 aNewProperty) {
		theIdc10Reason = aNewProperty;
	}

	/**
	 * МКБ10 причины травмы
	 */
	private VocIdc10 theIdc10Reason;

	/**
	 * МКБ10
	 */
	@Comment("МКБ10")
	@OneToOne
	public VocIdc10 getIdc10() {
		return theIdc10;
	}

	/**
	 * МКБ10
	 */
	public void setIdc10(VocIdc10 aNewProperty) {
		theIdc10 = aNewProperty;
	}

	/**
	 * МКБ10
	 */
	private VocIdc10 theIdc10;

	
	@Transient
	public String getIdc10Text() {
		return theIdc10!=null ? theIdc10.getCode() + " "+theIdc10.getName() : "";
	}
	
	@Transient
	public String getIdc10CodeText() {
		return theIdc10!=null ? theIdc10.getCode():"";
	}
	/**
	 * Выявлен при профосмотре
	 */
	@Comment("Выявлен при профосмотре")
	public Boolean getProphylacticExamination() {
		return theProphylacticExamination;
	}

	/**
	 * Выявлен при профосмотре
	 */
	public void setProphylacticExamination(Boolean aNewProperty) {
		theProphylacticExamination = aNewProperty;
	}

	/**
	 * Выявлен при профосмотре
	 */
	private Boolean theProphylacticExamination;

	/**
	 * Тип травмы
	 */
	@Comment("Тип травмы")
	@OneToOne
	public VocTraumaType getTraumaType() {
		return theTraumaType;
	}

	/**
	 * Тип травмы
	 */
	public void setTraumaType(VocTraumaType aNewProperty) {
		theTraumaType = aNewProperty;
	}

	/**
	 * Тип травмы
	 */
	private VocTraumaType theTraumaType;

	/**
	 * Дата установления
	 */
	@Comment("Дата установления")
	public Date getEstablishDate() {
		return theEstablishDate;
	}

	/**
	 * Дата установления
	 */
	public void setEstablishDate(Date aNewProperty) {
		theEstablishDate = aNewProperty;
	}

	/**
	 * Дата установления
	 */
	private Date theEstablishDate;

	/**
	 * Заменен диагнозом
	 */
	@Comment("Заменен диагнозом")
	@OneToOne
	public Diagnosis getDiagnosisNext() {
		return theDiagnosisNext;
	}

	/**
	 * Заменен диагнозом
	 */
	public void setDiagnosisNext(Diagnosis aNewProperty) {
		theDiagnosisNext = aNewProperty;
	}

	/**
	 * Заменен диагнозом
	 */
	private Diagnosis theDiagnosisNext;

	/** СМО */
	@Comment("СМО")
	@ManyToOne
	public MedCase getMedCase() {
		return theMedCase;
	}

	public void setMedCase(MedCase aMedCase) {
		theMedCase = aMedCase;
	}

	/** СМО */
	private MedCase theMedCase;
	
	/** Клинико-статистическая группа */
	@Comment("Клинико-статистическая группа")
	@OneToOne
	public VocKsg getClinicStatisticGroup() {
		return theClinicStatisticGroup;
	}

	@Transient
	public String getClinicStatisticGroupText() {
		return theClinicStatisticGroup!=null ? theClinicStatisticGroup.getCode() + " "+theClinicStatisticGroup.getName() : "";
	}
	
	public void setClinicStatisticGroup(VocKsg aClinicStatisticGroup) {
		theClinicStatisticGroup = aClinicStatisticGroup;
	}

	/** Клинико-статистическая группа */
	private VocKsg theClinicStatisticGroup;
	
	/** Тип регистрации диагноза */
	@Comment("Тип регистрации диагноза")
	@OneToOne
	public VocDiagnosisRegistrationType getRegistrationType() {
		return theRegistrationType;
	}

	public void setRegistrationType(VocDiagnosisRegistrationType aRegistrationType) {
		theRegistrationType = aRegistrationType;
	}

	/** Тип регистрации диагноза */
	private VocDiagnosisRegistrationType theRegistrationType;

	/**
	 * Справочник по ДТП
	 */
	@Comment("Справочник по ДТП")
	@OneToOne
	public OmcRoadTrafficInjury  getRoadTrafficInjury() {
		return theRoadTrafficInjury;}
	
	public void setRoadTrafficInjury(OmcRoadTrafficInjury aNewProperty) {
		theRoadTrafficInjury = aNewProperty;}
	
	private OmcRoadTrafficInjury theRoadTrafficInjury;
	
	/** Пользователь, создавший диагноз */
	@Comment("Пользователь, создавший диагноз")
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Пользователь, создавший диагноз */
	private String theUsername;
	
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}

	/** Дата объявления недействительным */
	@Comment("Дата объявления недействительным")
	public Date getDisableDate() {return theDisableDate;}
	public void setDisableDate(Date aDisableDate) {theDisableDate = aDisableDate;	}
	
	/** Медицинский специалист */
	@Comment("Медицинский специалист")
	@OneToOne
	public WorkFunction getMedicalWorker() {return theMedicalWorker;	}
	public void setMedicalWorker(WorkFunction aMedicalWorker) {theMedicalWorker = aMedicalWorker;}

	/** Медицинский специалист (инфо) */
	@Comment("Медицинский специалист (инфо)")
	@Transient
	public String getWorkerInfo() {
		return theMedicalWorker!=null ? theMedicalWorker.getWorkFunctionInfo() : "";
	}
	
	@Comment("Тип регистрации инфо")
	@Transient
	public String getRegistrationTypeInfo(){
		return theRegistrationType!=null ? theRegistrationType.getName()  : "" ;
	}
	@Comment("Первичность инфо")
	@Transient
	public String getPrimaryInfo() {
		return thePrimary!=null?thePrimary.getName():"" ;
	}
	/** Диагноз информация */
	@Comment("Диагноз информация")
	@Transient
	public String getDiagnosisInfo() {
		StringBuffer buf = new StringBuffer() ;
		
		if (theRegistrationType!=null) {
			buf.append("Тип: ");
			buf.append(theRegistrationType.getName());
			buf.append(". ");
		}
		if (theIdc10!=null) {
			buf.append("МКБ: ");
			buf.append(theIdc10.getName());
			buf.append(".");
		}
		return buf.toString();
	}
	
	/** Диагноз текст */
	@Comment("Диагноз текст")
	@OneToOne
	public VocDiagnosis getDocDiagnosis() {return theDocDiagnosis;}
	public void setDocDiagnosis(VocDiagnosis aDocDiagnosis) {theDocDiagnosis = aDocDiagnosis;}

	/** Диагноз текст */
	private VocDiagnosis theDocDiagnosis;

	/** Медицинский специалист */
	private WorkFunction theMedicalWorker;
	/** Пациент */
	private Patient thePatient;
	/** Дата объявления недействительным */
	private Date theDisableDate;
	
    /** Характер заболевания */
	@Comment("Характер заболевания")
	@OneToOne
	public VocIllnesPrimary getIllnesPrimary() {
		return theIllnesPrimary;
	}

	public void setIllnesPrimary(VocIllnesPrimary aIllnesPrimary) {
		theIllnesPrimary = aIllnesPrimary;
	}

	/** Характер заболевания */
	private VocIllnesPrimary theIllnesPrimary;
}
