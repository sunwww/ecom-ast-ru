package ru.ecom.mis.ejb.domain.medcase;

import static javax.persistence.CascadeType.ALL;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.birth.Inspection;
import ru.ecom.mis.ejb.domain.birth.Pregnancy;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.hospital.DeathCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocHospitalization;
import ru.ecom.mis.ejb.domain.medcase.voc.VocIntoxication;
import ru.ecom.mis.ejb.domain.medcase.voc.VocKindHighCare;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedCaseDefect;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMethodHighCare;
import ru.ecom.mis.ejb.domain.patient.Kinsman;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.vaccination.Vaccination;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.poly.ejb.domain.PrescriptionBlank;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Случай медицинского обслуживания
 */
@Entity
@Table(schema="SQLUser")
@AIndexes({
    @AIndex(properties="parent"),
    @AIndex(properties="patient"),
    @AIndex(properties="dateStart")
    }) 
@EntityListeners(DeleteListener.class)
abstract public class MedCase extends BaseEntity {
	
    /** Только принят */
    public static final int STATUS_NULL = 0 ;
    /** ОТКАЗ */
    public static final int STATUS_REFUSAL = 1 ;
    /** СОГЛАСИЛСЯ */
    public static final int STATUS_AGREED = 2 ;	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {return theServiceStream;}
	public void setServiceStream(VocServiceStream aServiceStream) {theServiceStream = aServiceStream;}
	/** Пациент */
	@Comment("Пациент")
	@ManyToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aNewProperty) {thePatient = aNewProperty;}

	/**Дата начала */
	@Comment("Дата начала")
	public Date getDateStart() {return theDateStart;}
	public void setDateStart(Date aNewProperty) {theDateStart = aNewProperty;}
	
	/** Дочерние СМО */
	@Comment("Дочерние СМО")
	@OneToMany(mappedBy="parent", cascade=CascadeType.ALL)
	public List<MedCase> getChildMedCase() {return theChildMedCase;}
	public void setChildMedCase(List<MedCase> aChildMedCase) {theChildMedCase = aChildMedCase;}
	

	/**Родительский СМО */
	@Comment("Родительский СМО")
	@ManyToOne
	public MedCase getParent() {return theParent;}
	public void setParent(MedCase aNewProperty) {theParent = aNewProperty;}

	/** Тип СМО*/
	@Comment("Тип СМО")
	@OneToOne
	public VocMedCaseDefect getMedCaseDefect() {return theMedCaseDefect;}
	public void setMedCaseDefect(VocMedCaseDefect aNewProperty) {theMedCaseDefect = aNewProperty;}

	/** Недействительность */
	@Comment("Недействительность")
	public boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(boolean aNewProperty) {theNoActuality = aNewProperty;}

	/**ЛПУ - место исполнения  */
	@Comment("ЛПУ - место исполнения ")
	@OneToOne
	public MisLpu getLpu() {return theLpu;}
	public void setLpu(MisLpu aNewProperty) {theLpu = aNewProperty;}	
	
	/** Стат. талон*/
//	@Comment("Стат. талон")
//	@OneToOne
//	public StatisticStub getStatisticStub() {return theStatisticStub;}
//	public void setStatisticStub(StatisticStub a_Property) {theStatisticStub = a_Property;	}
	
	/** Опьянение */
	@Comment("Опьянение")
	@OneToOne
	public VocIntoxication getIntoxication() {	return theIntoxication;	}
	public void setIntoxication(VocIntoxication aIntoxication) {theIntoxication = aIntoxication;}
	
	/** Хирургические операции */
	@Comment("Хирургические операции")
	@OneToMany(mappedBy="medCase", cascade=CascadeType.ALL)
	public List<SurgicalOperation> getSurgicalOperations() {return theSurgicalOperations;	}
	public void setSurgicalOperations(List<SurgicalOperation> aSurgicalOperations) {theSurgicalOperations = aSurgicalOperations;}
	
	/** Оператор */
	@Comment("Оператор")
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}
	
	/** Экстренность */
	@Comment("Экстренность")
	public Boolean getEmergency() {return theEmergency;}
	public void setEmergency(Boolean aNewProperty) {theEmergency = aNewProperty;}
	
	/** Экстренность */
	private Boolean theEmergency;
//	/** Тип текущего стационара */
//	@Comment("Тип текущего стационара")
//	@OneToOne
//	public VocHospType getHospType() {
//		return theHospType;
//	}
//
//	public void setHospType(VocHospType aHospType) {
//		theHospType = aHospType;
//	}
//
//	/** Тип текущего стационара */
//	private VocHospType theHospType;
//	
//	/** Откуда поступил */
//	@Comment("Откуда поступил")
//	@OneToOne
//	public VocHospType getSourceHospType() {
//		return theSourceHospType;
//	}
//
//	public void setSourceHospType(VocHospType aSourceHospType) {
//		theSourceHospType = aSourceHospType;
//	}
//
//	/** Откуда поступил */
//	private VocHospType theSourceHospType;
//	
//	/** Куда переведен */
//	@Comment("Куда переведен")
//	@OneToOne
//	public VocHospType getTargetHospType() {
//		return theTargetHospType;
//	}
//
//	public void setTargetHospType(VocHospType aTargetHospType) {
//		theTargetHospType = aTargetHospType;
//	}
//
//	/** Куда переведен */
//	private VocHospType theTargetHospType;

	//private StatisticStub theStatisticStub;
	/**Вакцинации*/
	/*@Comment("Вакцинации")
	@OneToMany(mappedBy="medCase", cascade=CascadeType.ALL)
	public List<Vaccination> getVaccinations() {return theVaccinations;}
	public void setVaccinations(List<Vaccination> aNewProperty) {theVaccinations = aNewProperty;}
	*/
	/** Случай смерти */
	@Comment("Случай смерти")
	@OneToOne
	public DeathCase getDeathCase() {return theDeathCase;}
	public void setDeathCase(DeathCase aDeathCase) {theDeathCase = aDeathCase;	}
	
	/**Выписанные рецептурные бланки  */
    @OneToMany(mappedBy = "medCase", cascade = ALL)
    public List<PrescriptionBlank> getPrescriptionBlanks() {return thePrescriptionBlanks;}
    public void setPrescriptionBlanks(List<PrescriptionBlank> aPrescriptionBlanks) { thePrescriptionBlanks = aPrescriptionBlanks; }
  

	/** Представитель */
	@Comment("Представитель")
	@OneToOne
	public Kinsman getKinsman() {return theKinsman;}
	public void setKinsman(Kinsman aKinsman) {theKinsman = aKinsman;}
	@Comment("Госпитализация (впервые, повторно)")
	@OneToOne
	public VocHospitalization getHospitalization() {return theHospitalization;}
	public void setHospitalization(VocHospitalization aHospitalization) {theHospitalization = aHospitalization;}
	/** Госпитализация (впервые, повторно) */
	private VocHospitalization theHospitalization;	
	/** Представитель */
	private Kinsman theKinsman;

	@Transient
	public String getPatientInfo() {
		return thePatient!=null?thePatient.getFio():"" ;
	}
	
	@Transient
	public String getInfo() {return String.valueOf(getId()) ;}
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}

	/** Условная единица трудоемкости */
	@Comment("Условная единица трудоемкости")
	public BigDecimal getUet() {return theUet;}
	public void setUet(BigDecimal aUet) {theUet = aUet;}
	

	/** Распечатан? */
	@Comment("Распечатан?")
	public Long getIsPrint() {return theIsPrint;}
	public void setIsPrint(Long aIsPrint) {theIsPrint = aIsPrint;}

	/** Диагноз? */
	@Comment("Диагноз?")
	public Long getIsDiagnosisCreate() {return theIsDiagnosisCreate;}
	public void setIsDiagnosisCreate(Long aIsDiagnosisCreate) {theIsDiagnosisCreate = aIsDiagnosisCreate;}

	/** Создавать дневник */
	@Comment("Создавать дневник")
	public Long getIsDiaryCreate() {return theIsDiaryCreate;}
	public void setIsDiaryCreate(Long aIsDairyCreate) {theIsDiaryCreate = aIsDairyCreate;}
	
	@Transient
	public Boolean getIsPrintInfo() {
		return theIsPrint!=null && theIsPrint.intValue()==STATUS_AGREED ;
	}

	/** Дата печати */
	@Comment("Дата печати")
	public Date getPrintDate() {return thePrintDate;}
	public void setPrintDate(Date aPrintDate) {thePrintDate = aPrintDate;}
	
	/** Время печати */
	@Comment("Время печати")
	public Time getPrintTime() {return thePrintTime;}
	public void setPrintTime(Time aPrintTime) {thePrintTime = aPrintTime;}

	/** Беременность */
	@Comment("Беременность")
	@OneToOne
	public Pregnancy getPregnancy() {return thePregnancy;}
	public void setPregnancy(Pregnancy aPregnancy) {thePregnancy = aPregnancy;}

	/** Гостиничная услуга */
	@Comment("Гостиничная услуга")
	public Boolean getHotelServices() {return theHotelServices;}
	public void setHotelServices(Boolean aHotelServices) {theHotelServices = aHotelServices;}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}

	/** Пользователь последний, изменявший запись */
	@Comment("Пользователь последний, изменявший запись")
	public String getEditUsername() {
		return theEditUsername;
	}

	public void setEditUsername(String aEditUsername) {
		theEditUsername = aEditUsername;
	}

	/** Пользователь последний, изменявший запись */
	private String theEditUsername;
	/** Дата редактирования */
	private Date theEditDate;
	/** Гостиничная услуга */
	private Boolean theHotelServices;
	/** Беременность */
	private Pregnancy thePregnancy;
	/** Время печати */
	private Time thePrintTime;
	/** Дата печати */
	private Date thePrintDate;
	/** Создавать дневник */
	private Long theIsDiaryCreate;
	/** Диагноз? */
	private Long theIsDiagnosisCreate;
	/** Распечатан? */
	private Long theIsPrint;
	/** Условная единица трудоемкости */
	private BigDecimal theUet;
	/** Дата создания */
	private Date theCreateDate;
	/** Оператор */
	private String theUsername;
	/** Поток обслуживания */
	private VocServiceStream theServiceStream;
	/**Пациент */
	private Patient thePatient;
	/** Дата начала */
	private Date theDateStart;
	/** Дочерние СМО */
	private List<MedCase> theChildMedCase;
	/**Родительский СМО */
	private MedCase theParent;
	/**Тип СМО */
	private VocMedCaseDefect theMedCaseDefect;
	/**Недействительность */
	private boolean theNoActuality;

	/**ЛПУ - место исполнения */
	private MisLpu theLpu;
	/** Хирургические операции */
	private List<SurgicalOperation> theSurgicalOperations;
	/** Опьянение */
	private VocIntoxication theIntoxication;
    /** Вакцинации*/
	//private List<Vaccination> theVaccinations;	
    /** Случай смерти */
	private DeathCase theDeathCase;
    /**Выписанные рецептурные бланки */
    private List<PrescriptionBlank> thePrescriptionBlanks;
    
	/** Дата операции */
	@Comment("Дата операции")
	public Date getOperationDate() {
		return theOperationDate;
	}

	public void setOperationDate(Date aOperationDate) {
		theOperationDate = aOperationDate;
	}

	/** Дата операции */
	private Date theOperationDate;
	
	/** Осложнение при операции */
	@Comment("Осложнение при операции")
	public Long getComplication() {
		return theComplication;
	}

	public void setComplication(Long aComplication) {
		theComplication = aComplication;
	}

	/** Осложнение при операции */
	private Long theComplication;
	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartment() {return theDepartment;}
	public void setDepartment(MisLpu aDepartment) {theDepartment = aDepartment;}
	/** Отделение (текст) */
	@Comment("Отделение (текст)")
	@Transient
	public String getDepartmentInfo() {
		return theDepartment!=null? theDepartment.getName():"";
	}
	/** Отделение */
	private MisLpu theDepartment;
	
	/** Дата направления */
	@Comment("Дата направления")
	public Date getOrderDate() {return theOrderDate;}
	public void setOrderDate(Date aOrderDate) {theOrderDate = aOrderDate;}
	
	/** Рабочая функция начавшего случай */
	@Comment("Рабочая функция начавшего случай")
	@OneToOne
	public WorkFunction getStartFunction() {return theStartFunction;}
	public void setStartFunction(WorkFunction aStartFunction) {theStartFunction = aStartFunction;}

	/** Рабочая функция начавшего случай */
	private WorkFunction theStartFunction;
	/** Дата направления */
	private Date theOrderDate;
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}

	/** Время создания */
	private Time theCreateTime;
	
	/** Время редактирования */
	@Comment("Время редактирования")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}

	/** Время редактирования */
	private Time theEditTime;

	/** Дата окончания */
	@Comment("Дата окончания")
	public Date getDateFinish() {return theDateFinish;	}
	public void setDateFinish(Date aNewProperty) {theDateFinish = aNewProperty;}
	/** Дата окончания */
	private Date theDateFinish;
	
	/** Вид ВМП */
	@Comment("Вид ВМП")
	@OneToOne
	public VocKindHighCare getKindHighCare() {return theKindHighCare;}
	public void setKindHighCare(VocKindHighCare aKindHighCare) {theKindHighCare = aKindHighCare;}

	/** Метод ВМП */
	@Comment("Метод ВМП")
	@OneToOne
	public VocMethodHighCare getMethodHighCare() {return theMethodHighCare;}
	public void setMethodHighCare(VocMethodHighCare aMethodHighCare) {theMethodHighCare = aMethodHighCare;}

	/** Метод ВМП */
	private VocMethodHighCare theMethodHighCare;
	/** Вид ВМП */
	private VocKindHighCare theKindHighCare;
}
