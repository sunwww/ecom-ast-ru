package ru.ecom.poly.ejb.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcRoadTrafficInjury;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityReason;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.*;
import ru.ecom.mis.ejb.domain.patient.Kinsman;
import ru.ecom.mis.ejb.domain.patient.voc.VocWorkPlaceType;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.poly.ejb.domain.voc.*;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Талон амбулаторного пациента
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
@Setter
@Getter
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
	public VocKsg getKsg() {return ksg;}

	/** КСГ */
	private VocKsg ksg;
	/** Сопутствующие заболевания */
	@Comment("Сопутствующие заболевания")
	@ManyToMany
	public List<VocIdc10> getConcomitantDiseases() {return concomitantDiseases;}

    
	/**  Медицинская карта */
    @ManyToOne
    public Medcard getMedcard() {return medcard;}

    /**
     * Вид оплаты
     */
    @ManyToOne
    public VocServiceStream getVocPaymentType() {return vocPaymentType;}

    /**
     * Место обслуживания
     */
    @ManyToOne
    public VocWorkPlaceType getVocServicePlace() {return vocServicePlace;}

    /**
     * Цель посещения
     */
    @ManyToOne
    public VocReason getVocReason() {return vocReason;}

    /** Результат обращения */
    @ManyToOne
    public VocVisitResult getVocVisitResult() {return vocVisitResult;}

    /**
     * @return Диагноз код МКБ *
     */
    @OneToOne
    public VocIdc10 getIdc10() {return idc10;}

    /**
     * @return Код мед. услуги (посещения, СМП, КЭС)
     */
    @ManyToOne
    public VocMedUsluga getVocMedUsluga() {return vocMedUsluga;}

    /**
     * @return Характер заболевания *
     */
    @ManyToOne
    public VocAcuityDiagnosis getVocIllnesType() {return vocIllnesType;}

    /**
     * @return Диспансерный учет *
     */
    @OneToOne
    public VocDispanseryRegistration getDispRegistration() {return dispRegistration;}

    /**
     * @return Травма *
     */
    @ManyToOne
    public VocTrauma getVocTrauma() {return vocTrauma;}

    /** Статус документа нетрудоспособности*/
	@Comment("Статус документа нетрудоспособности")
	@OneToOne
	public VocDisabilityDocumentStatus getDisabilityDocumentStatus() {return disabilityDocumentStatus;}

	/** Статус документа нетрудоспособности */
	private VocDisabilityDocumentStatus disabilityDocumentStatus;


    /** Причина нетрудоспособности */
	@Comment("Причина нетрудоспособности")
	@OneToOne
	public VocDisabilityReason getDisabilityReason() {return disabilityReason;}

    /** @return Специальная метка **/
    @OneToOne
    public VocSpecLabel getSpecialLabel() { return label; }
    public void setSpecialLabel(VocSpecLabel aLabel) { label = aLabel; }

    /** @return Время записи **/
    @Column(name="recordTime")
    public Time getTime() { return time; }
    public void setTime(Time aTime) { time = aTime; }

    @ManyToOne
    /** 27.1. Ранее зарегистрированный диагноз код МКБ10*/
    public VocIdc10 getPrevIdc10() {return previousDiagnosisIdc;}
    public void setPrevIdc10(VocIdc10 idc10) {previousDiagnosisIdc = idc10;}

    /** 27.1. Ранее зарегистрированный диагноз дата*/
    public Date getPrevIdc10Date(){return prevIdc10Date;}

    /**
	 * Рабочая функция исполнения
	 */
	@Comment("Рабочая функция исполнения")
	@OneToOne
	public WorkFunction getWorkFunction() {return workFunction;}


	@Comment("Справочник по ДТП")
	@OneToOne
	public OmcRoadTrafficInjury  getRoadTrafficInjury() {return roadTrafficInjury;}

	/**
	 * Первичность 
	 */
	@Comment("Первичность")
	@OneToOne
	public VocPrimaryDiagnosis getPrimary() {return primary;}

    @Transient
    public String getPatientName() {
        return  "";
    }

    @Transient
    public String getStatusName() {
    	switch((int)status) {
    	case STATUS_OPEN: return "Открыт" ; 
    	case STATUS_INPROCESS: return "В производстве" ; 
    	case STATUS_CLOSED: return "Закрыт" ;
    	default: 
    		throw new IllegalStateException("Неизвестный статус "+status) ;
    	}
    }

    /**
     * Закрыть талон
     * @return
     */
    @Transient
    public Boolean getIsTicketClosed() {
    	return (int)status==STATUS_CLOSED;
    }

    /** Информация о рабочей ф-ии леч врача */
	@Comment("Информация о рабочей ф-ии леч врача")
	@Transient
	public String getWorkFunctionInfo() {
		return workFunction!=null?workFunction.getWorkFunctionInfo():"";
	}

	@Transient
	@Comment("Информация по талону")
	public String getTicketInfo() {
		return "№" + getId() + " " +
				(getDate() != null ? getDate() : "нет даты") +
				" " + getWorkFunctionInfo();
	}

	/** Представитель */
	@Comment("Представитель")
	@OneToOne
	public Kinsman getKinsman() {return kinsman;}

	@Comment("Обращение по поводу данного заболевания в текущем году (впервые, повторно)")
	@OneToOne
	public VocHospitalization getHospitalization() {return hospitalization;}


	@Transient
	public String getMedServices() {return "";}

	/** Неотложная помощь */
	private Boolean emergency;

	/** Разговор с родственником */
	private Boolean talk;
	/** Направлен на стац. лечение */
	private Boolean directHospital;
	/** Условная единица трудоемкости */
	private BigDecimal uet;
	private VocHospitalization hospitalization;
	/** Представитель */
	private Kinsman kinsman;
	/** Время создания */
	private Time timeCreate;
	/** Пользователь */
	private String usernameCreate;
	/** Дата создания */
	private Date dateCreate;
	/** Первичность */
	private VocPrimaryDiagnosis primary;
    /** Результат обращения */
    private VocVisitResult vocVisitResult;
    /**Цель посещения */
    private VocReason vocReason;
    /** Место обслуживания */
    private VocWorkPlaceType vocServicePlace;
    /**Вид оплаты */
    private VocServiceStream vocPaymentType;
    /**Дата выдачи/создания(?) талона */
    private Date date;
	/** Сопутствующие заболевания */
	private List<VocIdc10> concomitantDiseases;
    /** Медицинская карта */
    private Medcard medcard;
	/** Справочник по ДТП*/
	private OmcRoadTrafficInjury roadTrafficInjury;
	/** Рабочая функция исполнения*/
	private WorkFunction workFunction;
	/** Причина нетрудоспособности */
	private VocDisabilityReason disabilityReason;
    /** Время записи **/
    private Time time;
    /** Специальная метка **/
    private VocSpecLabel label;
    /** Травма */
    private VocTrauma vocTrauma;
    /** Диспансерный учет */
    private VocDispanseryRegistration dispRegistration;
    /** Характер заболевания */
    private VocAcuityDiagnosis vocIllnesType;
    /** Код мед. услуги (посещения, СМП, КЭС) */
    private VocMedUsluga vocMedUsluga;
    /** Диагноз код МКБ */
    private VocIdc10 idc10;
    /** 27.1. Ранее зарегистрированный диагноз код МКБ10*/
    private VocIdc10 previousDiagnosisIdc;
    /** 27.1. Ранее зарегистрированный диагноз дата*/
    private Date prevIdc10Date;
    /** Статус */
    private long status;
    
    /** Характер заболевания */
	@Comment("Характер заболевания")
	@OneToOne
	public VocIllnesPrimary getIllnesPrimary() {return illnesPrimary;}

	/** Характер заболевания */
	private VocIllnesPrimary illnesPrimary;

	/** СПО */
	@Comment("СПО")
	@OneToOne
	public MedCase getParent() {
		return parent;
	}

	/** СПО */
	private MedCase parent;
	/** Бригада скорой помощи */
	@Comment("Бригада скорой помощи")
	@OneToOne
	public VocAmbulance getAmbulance() {return ambulance;}

	/** Исход визита */
	@Comment("Исход визита")
	@OneToOne
	public VocVisitOutcome getVisitOutcome() {return visitOutcome;}

	/** Исход визита */
	private VocVisitOutcome visitOutcome;
	/** Бригада скорой помощи */
	private VocAmbulance ambulance;

}
