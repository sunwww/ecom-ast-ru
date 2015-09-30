package ru.ecom.poly.ejb.domain;

import static javax.persistence.CascadeType.ALL;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcRoadTrafficInjury;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityReason;

import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAcuityDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAmbulance;
import ru.ecom.mis.ejb.domain.medcase.voc.VocHospitalization;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPrimaryDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocVisitOutcome;
import ru.ecom.mis.ejb.domain.medcase.voc.VocWorkMedservice;

import ru.ecom.mis.ejb.domain.patient.Kinsman;
import ru.ecom.mis.ejb.domain.patient.voc.VocWorkPlaceType;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.poly.ejb.domain.voc.VocDisabilityDocumentStatus;
import ru.ecom.poly.ejb.domain.voc.VocDispanseryRegistration;
import ru.ecom.poly.ejb.domain.voc.VocIllnesPrimary;
import ru.ecom.poly.ejb.domain.voc.VocMedUsluga;
import ru.ecom.poly.ejb.domain.voc.VocReason;
import ru.ecom.poly.ejb.domain.voc.VocSpecLabel;
import ru.ecom.poly.ejb.domain.voc.VocTrauma;
import ru.ecom.poly.ejb.domain.voc.VocVisitResult;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Талон амбулаторного пациента
 * @param <NursingPatientAge>
 */
@Entity
@AIndexes({
  // используется при поиске дyбликатов
  @AIndex(properties= {"date","workFunction","medcard"})
  ,  @AIndex(properties= {"medcard"})
  ,  @AIndex(properties= {"medcard","status"})
  ,@AIndex(properties={"date"})
})
@EntityListeners(DeleteListener.class)
@Table(schema="SQLUser")
public class Ticket extends BaseEntity {

    /** Открыт */
    public static final int STATUS_OPEN = 0 ;
    /** В производстве */
    public static final int STATUS_INPROCESS = 1 ;
    /** Закрыт */
    public static final int STATUS_CLOSED = 2 ;

    /** КСГ */
	@Comment("КСГ")
	@OneToOne
	public VocKsg getKsg() {return theKsg;}
	public void setKsg(VocKsg aKsg) {theKsg = aKsg;}

	/** КСГ */
	private VocKsg theKsg;
	/** Сопутствующие заболевания */
	@Comment("Сопутствующие заболевания")
	@ManyToMany
	public List<VocIdc10> getConcomitantDiseases() {return theConcomitantDiseases;}
	public void setConcomitantDiseases(List<VocIdc10> aConcomitantDiseases) {theConcomitantDiseases = aConcomitantDiseases;}

    
	/**  Медицинская карта */
    @ManyToOne
    public Medcard getMedcard() {return theMedcard;}
    public void setMedcard(Medcard aMedcard) {theMedcard = aMedcard;}

    /** Дата выдачи/создания(?) талона */
    public Date getDate() {return theDate;}
    public void setDate(Date aDate) {theDate = aDate;}

    /**
     * Вид оплаты
     */
    @ManyToOne
    public VocServiceStream getVocPaymentType() {return theVocPaymentType;}
    public void setVocPaymentType(VocServiceStream aVocPaymentType) {theVocPaymentType = aVocPaymentType;}

    /**
     * Место обслуживания
     */
    @ManyToOne
    public VocWorkPlaceType getVocServicePlace() {return theVocServicePlace;}
    public void setVocServicePlace(VocWorkPlaceType aVocServicePlace) {theVocServicePlace = aVocServicePlace;}

    /**
     * Цель посещения
     */
    @ManyToOne
    public VocReason getVocReason() {return theVocReason;}
    public void setVocReason(VocReason aVocReason) {theVocReason = aVocReason;}

    /** Результат обращения */
    @ManyToOne
    public VocVisitResult getVocVisitResult() {return theVocVisitResult;}
    public void setVocVisitResult(VocVisitResult aVocVisitResult) {theVocVisitResult = aVocVisitResult;}

    /**
     * @return Диагноз код МКБ *
     */
    @OneToOne
    public VocIdc10 getIdc10() {return theVocIdc10;}
    public void setIdc10(VocIdc10 idc10) {theVocIdc10 = idc10;}

    /**
     * @return Код мед. услуги (посещения, СМП, КЭС)
     */
    @ManyToOne
    public VocMedUsluga getVocMedUsluga() {return theVocMedUsluga;}
    public void setVocMedUsluga(VocMedUsluga aVocMedUsluga) {theVocMedUsluga = aVocMedUsluga;}

    /**
     * @return Характер заболевания *
     */
    @ManyToOne
    public VocAcuityDiagnosis getVocIllnesType() {return theVocIllnesType;}
    public void setVocIllnesType(VocAcuityDiagnosis aVocIllnesType) {theVocIllnesType = aVocIllnesType;}

    /**
     * @return Диспансерный учет *
     */
    @OneToOne
    public VocDispanseryRegistration getDispRegistration() {return theDispRegistration;}
    public void setDispRegistration(VocDispanseryRegistration aVocDispanseryRegistration) {theDispRegistration = aVocDispanseryRegistration;}

    /**
     * @return Травма *
     */
    @ManyToOne
    public VocTrauma getVocTrauma() {return theVocTrauma;}
    public void setVocTrauma(VocTrauma aVocTrauma) {theVocTrauma = aVocTrauma;}

    
    
    /** Статус документа нетрудоспособности*/
	@Comment("Статус документа нетрудоспособности")
	@OneToOne
	public VocDisabilityDocumentStatus getDisabilityDocumentStatus() {return theDisabilityDocumentStatus;}
	public void setDisabilityDocumentStatus(VocDisabilityDocumentStatus aDisabilityDocumentStatus) {theDisabilityDocumentStatus = aDisabilityDocumentStatus;}

	/** Статус документа нетрудоспособности */
	private VocDisabilityDocumentStatus theDisabilityDocumentStatus;


    /** Причина нетрудоспособности */
	@Comment("Причина нетрудоспособности")
	@OneToOne
	public VocDisabilityReason getDisabilityReason() {return theDisabilityReason;}
	public void setDisabilityReason(VocDisabilityReason aDisabilityReason) {theDisabilityReason = aDisabilityReason;}
	
	/** Выписанные рецептурные бланки */
    @OneToMany(mappedBy = "ticket", cascade = ALL)
    public List<PrescriptionBlank> getPrescriptionBlanks() {return thePrescriptionBlanks;}
    public void setPrescriptionBlanks(List<PrescriptionBlank> aPrescriptionBlanks) {thePrescriptionBlanks = aPrescriptionBlanks;}

    /** @return Специальная метка **/
    @OneToOne
    public VocSpecLabel getSpecialLabel() { return theLabel; }
    public void setSpecialLabel(VocSpecLabel aLabel) { theLabel = aLabel; }

    /** @return Время записи **/
    @Column(name="recordTime")
    public Time getTime() { return theTime; }
    public void setTime(Time aTime) { theTime = aTime; }

    @ManyToOne
    /** 27.1. Ранее зарегистрированный диагноз код МКБ10*/
    public VocIdc10 getPrevIdc10() {return thePreviousDiagnosisIdc;}
    public void setPrevIdc10(VocIdc10 idc10) {thePreviousDiagnosisIdc = idc10;}

    /** 27.1. Ранее зарегистрированный диагноз дата*/
    public Date getPrevIdc10Date(){return thePrevIdc10Date;}
    public void setPrevIdc10Date(Date aDate){thePrevIdc10Date = aDate;}

    /** Статус талона */
    public long getStatus() {return theStatus;}
    public void setStatus(long aStatus) {theStatus = aStatus;}

    /**
	 * Рабочая функция исполнения
	 */
	@Comment("Рабочая функция исполнения")
	@OneToOne
	public WorkFunction getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(WorkFunction aNewProperty) {theWorkFunction = aNewProperty;}
	

	@Comment("Справочник по ДТП")
	@OneToOne
	public OmcRoadTrafficInjury  getRoadTrafficInjury() {return theRoadTrafficInjury;}
	public void setRoadTrafficInjury(OmcRoadTrafficInjury aNewProperty) {theRoadTrafficInjury = aNewProperty;}
	
	/**
	 * Первичность 
	 */
	@Comment("Первичность")
	@OneToOne
	public VocPrimaryDiagnosis getPrimary() {return thePrimary;}
	public void setPrimary(VocPrimaryDiagnosis aNewProperty) {thePrimary = aNewProperty;}


    @Transient
    public String getPatientName() {
        return  "";
    }

    @Transient
    public String getStatusName() {
    	switch((int)theStatus) {
    	case STATUS_OPEN: return "Открыт" ; 
    	case STATUS_INPROCESS: return "В производстве" ; 
    	case STATUS_CLOSED: return "Закрыт" ;
    	default: 
    		throw new IllegalStateException("Неизвестный статус "+theStatus) ;
    	}
    }

    /**
     * Закрыть талон
     * @return
     */
    @Transient
    public Boolean getIsTicketClosed() {
    	return (int)theStatus==STATUS_CLOSED;
    }

    /** Информация о рабочей ф-ии леч врача */
	@Comment("Информация о рабочей ф-ии леч врача")
	@Transient
	public String getWorkFunctionInfo() {
		return theWorkFunction!=null?theWorkFunction.getWorkFunctionInfo():"";
	}

	@Transient
	@Comment("Информация по талону")
	public String getTicketInfo() {
		StringBuilder sb = new StringBuilder() ;
		sb.append("№").append(getId()).append(" ") ;
		sb.append(getDate()!=null?getDate():"нет даты") ;
		sb.append(" ") ;
		sb.append(getWorkFunctionInfo());
		return sb.toString();
	}
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getDateCreate() {return theDateCreate;}
	public void setDateCreate(Date aDateCreate) {theDateCreate = aDateCreate;}

	/** Пользователь */
	@Comment("Пользователь")
	public String getUsernameCreate() {return theUsernameCreate;}
	public void setUsernameCreate(String aUsername) {theUsernameCreate = aUsername;}

	/** Время создания */
	@Comment("Время создания")
	public Time getTimeCreate() {return theTimeCreate;}
	public void setTimeCreate(Time aTimeCreate) {theTimeCreate = aTimeCreate;}

	
	/** Представитель */
	@Comment("Представитель")
	@OneToOne
	public Kinsman getKinsman() {return theKinsman;}
	public void setKinsman(Kinsman aKinsman) {theKinsman = aKinsman;}
	
	@Comment("Обращение по поводу данного заболевания в текущем году (впервые, повторно)")
	@OneToOne
	public VocHospitalization getHospitalization() {return theHospitalization;}
	public void setHospitalization(VocHospitalization aHospitalization) {theHospitalization = aHospitalization;}
	
	/** Условная единица трудоемкости */
	@Comment("Условная единица трудоемкости")
	public BigDecimal getUet() {return theUet;}
	public void setUet(BigDecimal aUet) {theUet = aUet;}

	/** Направлен на стац. лечение */
	@Comment("Направлен на стац. лечение")
	public Boolean getDirectHospital() {return theDirectHospital;}
	public void setDirectHospital(Boolean aDirectHospital) {theDirectHospital = aDirectHospital;}

	/** Разговор с родственником */
	@Comment("Разговор с родственником")
	public Boolean getTalk() {return theTalk;}
	public void setTalk(Boolean aTalk) {theTalk = aTalk;}
	
	/** Неотложная помощь */
	@Comment("Неотложная помощь")
	public Boolean getEmergency() {return theEmergency;}
	public void setEmergency(Boolean aEmergency) {theEmergency = aEmergency;}
	
	@Transient
	public String getMedServices() {return "";}

	/** Неотложная помощь */
	private Boolean theEmergency;

	/** Разговор с родственником */
	private Boolean theTalk;
	/** Направлен на стац. лечение */
	private Boolean theDirectHospital;
	/** Условная единица трудоемкости */
	private BigDecimal theUet;
	private VocHospitalization theHospitalization;
	/** Представитель */
	private Kinsman theKinsman;
	/** Время создания */
	private Time theTimeCreate;
	/** Пользователь */
	private String theUsernameCreate;
	/** Дата создания */
	private Date theDateCreate;
	/** Первичность */
	private VocPrimaryDiagnosis thePrimary;
    /** Результат обращения */
    private VocVisitResult theVocVisitResult;
    /**Цель посещения */
    private VocReason theVocReason;
    /** Место обслуживания */
    private VocWorkPlaceType theVocServicePlace;
    /**Вид оплаты */
    private VocServiceStream theVocPaymentType;
    /**Дата выдачи/создания(?) талона */
    private Date theDate;
	/** Сопутствующие заболевания */
	private List<VocIdc10> theConcomitantDiseases;
    /** Медицинская карта */
    private Medcard theMedcard;
	/** Справочник по ДТП*/
	private OmcRoadTrafficInjury theRoadTrafficInjury;
	/** Рабочая функция исполнения*/
	private WorkFunction theWorkFunction;
	/** Причина нетрудоспособности */
	private VocDisabilityReason theDisabilityReason;
    /** Время записи **/
    private Time theTime;
    /** Специальная метка **/
    private VocSpecLabel theLabel;
    /** Выписанные рецептурные бланки */
    private List<PrescriptionBlank> thePrescriptionBlanks;
    /** Травма */
    private VocTrauma theVocTrauma;
    /** Диспансерный учет */
    private VocDispanseryRegistration theDispRegistration;
    /** Характер заболевания */
    private VocAcuityDiagnosis theVocIllnesType;
    /** Код мед. услуги (посещения, СМП, КЭС) */
    private VocMedUsluga theVocMedUsluga;
    /** Диагноз код МКБ */
    private VocIdc10 theVocIdc10;
    /** 27.1. Ранее зарегистрированный диагноз код МКБ10*/
    private VocIdc10 thePreviousDiagnosisIdc;
    /** 27.1. Ранее зарегистрированный диагноз дата*/
    private Date thePrevIdc10Date;
    /** Статус */
    private long theStatus;
    
    /** Характер заболевания */
	@Comment("Характер заболевания")
	@OneToOne
	public VocIllnesPrimary getIllnesPrimary() {return theIllnesPrimary;}
	public void setIllnesPrimary(VocIllnesPrimary aIllnesPrimary) {theIllnesPrimary = aIllnesPrimary;}

	/** Характер заболевания */
	private VocIllnesPrimary theIllnesPrimary;
	
	/** Тип мед. обслуживания */
	@Comment("Тип мед. обслуживания")
	@OneToOne
	public VocWorkMedservice getWorkMedservice() {return theWorkMedservice;}
	public void setWorkMedservice(VocWorkMedservice aWorkMedservice) {theWorkMedservice = aWorkMedservice;}

	/** Тип мед. обслуживания */
	private VocWorkMedservice theWorkMedservice;
	/** СПО */
	@Comment("СПО")
	@OneToOne
	public MedCase getParent() {
		return theParent;
	}

	public void setParent(MedCase aParent) {
		theParent = aParent;
	}

	/** СПО */
	private MedCase theParent;
	/** Бригада скорой помощи */
	@Comment("Бригада скорой помощи")
	@OneToOne
	public VocAmbulance getAmbulance() {return theAmbulance;}
	public void setAmbulance(VocAmbulance aAmbulance) {theAmbulance = aAmbulance;}

	/** Исход визита */
	@Comment("Исход визита")
	@OneToOne
	public VocVisitOutcome getVisitOutcome() {return theVisitOutcome;}
	public void setVisitOutcome(VocVisitOutcome aVisitOutcome) {theVisitOutcome = aVisitOutcome;}

	/** Исход визита */
	private VocVisitOutcome theVisitOutcome;
	/** Бригада скорой помощи */
	private VocAmbulance theAmbulance;

}
