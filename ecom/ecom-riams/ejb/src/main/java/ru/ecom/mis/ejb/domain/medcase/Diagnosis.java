package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcRoadTrafficInjury;
import ru.ecom.mis.ejb.domain.medcase.voc.*;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.poly.ejb.domain.voc.VocIllnesPrimary;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;


/**
 * Диагноз
 */
@Entity
@AIndexes({
	@AIndex(properties="medCase")
	,@AIndex(properties={"medCase","idc10"})
	,@AIndex(properties={"patient","idc10"})
	,@AIndex(properties={"patient","idc10","establishDate","priority"})
	,@AIndex(properties={"patient","idc10","establishDate"})
    })
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public class Diagnosis extends BaseEntity {

	/** Фоновое заболевание */
	@Comment("Фоновое заболевание")
	@OneToOne
	public VocIdc10 getBackgroundDisease() {return backgroundDisease;}
	private VocIdc10 backgroundDisease;

	/** Выявлен атеросклероз БЦА */
	@Comment("Выявлен атеросклероз БЦА")
	public Boolean getIsFoundAtherosclerosis() {return isFoundAtherosclerosis;}
	private Boolean isFoundAtherosclerosis;
	
	/**
	 * Наименование
	 */
	@Comment("Наименование")
	@Column(length=500)
	public String getName() {return name;}

	/**
	 * Наименование
	 */
	private String name;

	/**
	 * Приоритет
	 */
	@Comment("Приоритет")
	@OneToOne
	public VocPriorityDiagnosis getPriority() {return priority;}

	@Transient
	public String getPriorityDiagnosisText() {return priority!=null ? priority.getName() : "" ;}
	
	

	/**
	 * Приоритет
	 */
	private VocPriorityDiagnosis priority;

	/**
	 * Острота
	 */
	@Comment("Острота")
	@OneToOne
	public VocAcuityDiagnosis getAcuity() {
		return acuity;
	}


	/**
	 * Острота
	 */
	private VocAcuityDiagnosis acuity;

	/**
	 * Первичность 
	 */
	@Comment("Первичность")
	@OneToOne
	public VocPrimaryDiagnosis getPrimary() {
		return primary;
	}

	/**
	 * Первичность
	 */
	private VocPrimaryDiagnosis primary;

	/**
	 * Дата уточнения диагноза
	 */
	private Date accurationDate;

	/**
	 * Предыдущий диагноз
	 */
	@Comment("Предыдущий диагноз")
	@OneToOne
	public Diagnosis getDiagnosisPrior() {
		return diagnosisPrior;
	}

	/**
	 * Предыдущий диагноз
	 */
	private Diagnosis diagnosisPrior;

		/**
	 * МКБ10 причины травмы
	 */
	@Comment("МКБ10 причины травмы")
	@OneToOne
	public VocIdc10 getIdc10Reason() {
		return idc10Reason;
	}

	/**
	 * МКБ10 причины травмы
	 */
	private VocIdc10 idc10Reason;

	/**
	 * МКБ10
	 */
	@Comment("МКБ10")
	@OneToOne
	public VocIdc10 getIdc10() {
		return idc10;
	}

	/**
	 * МКБ10
	 */
	private VocIdc10 idc10;

	
	@Transient
	public String getIdc10Text() {
		return idc10!=null ? idc10.getCode() + " "+idc10.getName() : "";
	}
	
	@Transient
	public String getIdc10CodeText() {
		return idc10!=null ? idc10.getCode():"";
	}

	/**
	 * Выявлен при профосмотре
	 */
	private Boolean prophylacticExamination;

	/**
	 * Тип травмы
	 */
	@Comment("Тип травмы")
	@OneToOne
	public VocTraumaType getTraumaType() {
		return traumaType;
	}

	/**
	 * Тип травмы
	 */
	private VocTraumaType traumaType;

	/**
	 * Дата установления
	 */
	private Date establishDate;

	/**
	 * Заменен диагнозом
	 */
	@Comment("Заменен диагнозом")
	@OneToOne
	public Diagnosis getDiagnosisNext() {
		return diagnosisNext;
	}

	/**
	 * Заменен диагнозом
	 */
	private Diagnosis diagnosisNext;

	/** СМО */
	@Comment("СМО")
	@ManyToOne
	public MedCase getMedCase() {
		return medCase;
	}
	/** СМО */
	private MedCase medCase;
	
	/** Клинико-статистическая группа */
	@Comment("Клинико-статистическая группа")
	@OneToOne
	public VocKsg getClinicStatisticGroup() {
		return clinicStatisticGroup;
	}

	@Transient
	public String getClinicStatisticGroupText() {
		return clinicStatisticGroup!=null ? clinicStatisticGroup.getCode() + " "+clinicStatisticGroup.getName() : "";
	}
	
	/** Клинико-статистическая группа */
	private VocKsg clinicStatisticGroup;
	
	/** Тип регистрации диагноза */
	@Comment("Тип регистрации диагноза")
	@OneToOne
	public VocDiagnosisRegistrationType getRegistrationType() {
		return registrationType;
	}

	/** Тип регистрации диагноза */
	private VocDiagnosisRegistrationType registrationType;

	/**
	 * Справочник по ДТП
	 */
	@Comment("Справочник по ДТП")
	@OneToOne
	public OmcRoadTrafficInjury getRoadTrafficInjury() {
		return roadTrafficInjury;}
	
	private OmcRoadTrafficInjury roadTrafficInjury;
	
	/** Пользователь, создавший диагноз */
	private String username;
	
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return patient;}

	/** Медицинский специалист */
	@Comment("Медицинский специалист")
	@OneToOne
	public WorkFunction getMedicalWorker() {return medicalWorker;	}

	/** Медицинский специалист (инфо) */
	@Comment("Медицинский специалист (инфо)")
	@Transient
	public String getWorkerInfo() {
		return medicalWorker!=null ? medicalWorker.getWorkFunctionInfo() : "";
	}
	
	@Comment("Тип регистрации инфо")
	@Transient
	public String getRegistrationTypeInfo(){
		return registrationType!=null ? registrationType.getName()  : "" ;
	}
	@Comment("Первичность инфо")
	@Transient
	public String getPrimaryInfo() {
		return primary!=null?primary.getName():"" ;
	}
	/** Диагноз информация */
	@Comment("Диагноз информация")
	@Transient
	public String getDiagnosisInfo() {
		StringBuilder buf = new StringBuilder() ;
		
		if (registrationType!=null) {
			buf.append("Тип: ");
			buf.append(registrationType.getName());
			buf.append(". ");
		}
		if (idc10!=null) {
			buf.append("МКБ: ");
			buf.append(idc10.getName());
			buf.append(".");
		}
		return buf.toString();
	}

	/** Медицинский специалист */
	private WorkFunction medicalWorker;
	/** Пациент */
	private Patient patient;
	/** Дата объявления недействительным */
	private Date disableDate;
	
    /** Характер заболевания */
	@Comment("Характер заболевания")
	@OneToOne
	public VocIllnesPrimary getIllnesPrimary() {
		return illnesPrimary;
	}

	/** Характер заболевания */
	private VocIllnesPrimary illnesPrimary;

	/** Пользователь, последний изменявший запись */
	private String editUsername;
	/** Дата редактирования */
	private Date editDate;
	/** Пользователь, создавший запись */
	private String createUsername;
	/** Дата создания */
	private Date createDate;
	
	/** Дополнительный код */
	private String mkbAdc;
}
