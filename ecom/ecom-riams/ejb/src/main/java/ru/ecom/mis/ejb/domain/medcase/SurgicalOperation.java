package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.*;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Comment("Хирургическая операция")
@AIndexes({
	@AIndex(properties="medCase"),
	@AIndex(properties="operationDate"),
	@AIndex(properties={"surgeon"}),
	@AIndex(properties={"operation"}),
	@AIndex(properties={"medService"}),
	@AIndex(properties="patient"),
	@AIndex(properties="serviceStream"),
	@AIndex(properties="department")
    }) 
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public class SurgicalOperation extends BaseEntity {

	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartment() {return department;}

	/** Операция */
	@Comment("Операция")
	@OneToOne @Deprecated
	public VocOperation getOperation() {return operation;}

	/** Анестизия */
	@Comment("Анестезия")
	@OneToOne
	public VocAnesthesia getAnesthesia() {return anesthesia;}

	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@ManyToOne
	public MedCase getMedCase() {return medCase;}

	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return patient;}


	/**Рабочая функция врача, проводившего операцию info */
	@Comment("Рабочая функция врача, проводившего операцию info")
	@Transient
	public String getSurgeonInfo(){
		return surgeon!=null?surgeon.getWorkFunctionInfo():"";
	}

	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@OneToOne
	public MisLpu getLpu() {return lpu;}

	/** Анестезиолог */
	@Comment("Анестезиолог")
	@OneToOne
	public WorkFunction getAnaesthetist() {return anaesthetist;}

	/** Операционная медсестра */
	@Comment("Операционная медсестра")
	@OneToOne
	public WorkFunction getOperatingNurse() {return operatingNurse;}

	/** Предоперационный эпикриз */
	@Comment("Предоперационный эпикриз")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getPreoperativeEpicrisis() {return preoperativeEpicrisis;}

	/** Описание операции */
	@Comment("Описание операции")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getOperationText() {return operationText;}

	
	/** МКБ до операции */
	@Comment("МКБ до операции")
	@OneToOne
	public VocIdc10 getIdc10Before() {return idc10Before;}

	@Transient
	public String getIdc10BeforeInfo() {return idc10Before!=null?idc10Before.getCode():"" ;}
	@Transient
	public String getIdc10AfterInfo() {return idc10After!=null?idc10After.getCode():"" ;}
	
	/** МКБ после операции */
	@Comment("МКБ после операции")
	@OneToOne
	public VocIdc10 getIdc10After() {return idc10After;}

	
	/** Гистологическое исследование */
	@Comment("Гистологическое исследование")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getHistologicalStudy() {return histologicalStudy;}

	
	/** Хирурги */
	@Comment("Хирурги")
	@ManyToMany
	public List<WorkFunction> getSurgeonFunctions() {return surgeonFunctions;}

	@Comment("Информации")
	@Transient
	public String getInformation() {
		return "Период: " + operationDate + " " + operationTime + " - " +
				operationDateTo + " " + operationTimeTo +
				"Операция: " + operation +
				"Хирург: " + getSurgeonInfo();
	}
		
		@Comment("Период")
		@Transient
		public String getPeriod() {
			return operationDate + " " + operationTime + " - " +
					operationDateTo + " " + operationTimeTo;
	}
		
		 /** Информация о пациенте */
	    @Comment("Информация о пациенте")
	    @Transient
	    public String getPatientInfo() {return patient!=null ? patient.getPatientInfo() : "";}
	
	 /** Экстренность */
    private Boolean emergency;

	/** Показания для операции */
	@Comment("Показания для операции")
	@OneToOne
	public VocHospitalAspect getAspect() {return aspect;}
	private VocHospitalAspect aspect;

	/** Малая */
	private Boolean minor;
	
	/** Анестезии */
	@Comment("Анестезии")
	@OneToMany(mappedBy="surgicalOperation", cascade=CascadeType.ALL)
	public List<Anesthesia> getAnesthesies() {return anesthesies;}

	/** Хирург */
	@Comment("Хирург")
	@OneToOne
	public WorkFunction getSurgeon() {return surgeon;}

	/** Профиль */
	@Comment("Профиль")
	@OneToOne
	public VocSurgicalProfile getProfile() {return profile;}

	/** Операция с использованием высоких медицинских технологий */
	@Comment("Операция с использованием высоких медицинских технологий")
	@OneToOne
	public VocOperationTechnology getTechnology() {return technology;}

	/** Исход операции */
	@Comment("Исход операции")
	@OneToOne
	public VocOperationOutcome getOutcome() {return outcome;}

	/** Метод операции */
	@Comment("Метод операции")
	@OneToOne
	public VocOperationMethod getMethod() {return method;}

	/** Метод операции */
	private VocOperationMethod method;
	/** Исход операции */
	private VocOperationOutcome outcome;

	/** Операция с использованием высоких медицинских технологий */
	private VocOperationTechnology technology;
	/** Профиль */
	private VocSurgicalProfile profile;

	/** Хирург */
	private WorkFunction surgeon;

	/** Номер в журнале */
	private String numberInJournal;

	/** Анестезии */
	private List<Anesthesia> anesthesies;
	
	    
	/** Кол-во  анастезии */
	private BigDecimal anesthesiaAmount;
	/** Хирурги */
	private List<WorkFunction> surgeonFunctions;
	/** Анестезиолог */
	private WorkFunction anaesthetist;
	/** Дата операции по */
	private Date operationDateTo;
	/** Предоперационный эпикриз */
	private String preoperativeEpicrisis;
	/** Описание операции */
	private String operationText;
	/** МКБ до операции */
	private VocIdc10 idc10Before;
	/** Операционная медсестра */
	private WorkFunction operatingNurse;
	/** МКБ после операции */
	private VocIdc10 idc10After;
	/** Гистологическое исследование */
	private String histologicalStudy;
	/** Время операции по */
	private Time operationTimeTo;

	/** Лечебное учреждение */
	private MisLpu lpu;
	/** Пациент */
	private Patient patient;
	/** Случай медицинского обслуживания */
	private MedCase medCase;
	/** Использование криогенной аппаратуры */
	private boolean cryogenicUse;
	/** Флаг использования лазерной аппаратуры */
	private boolean laserUse;
	/** Флаг использования эндоскопии */
	private boolean endoscopyUse;
	/** Флаг основной операции */
	private boolean base;
	/** Анестезия */
	private VocAnesthesia anesthesia;
	/** Операция */
	private VocOperation operation;
	/** Отделение */
	private MisLpu department;
	/** Время операции */
	private Time operationTime;
	/** Дата операции */
	private Date operationDate;
	
	/** Пользователь, последний изменявший запись */
	private String editUsername;
	/** Дата редактирования */
	private Date editDate;
	/** Пользователь, создавший запись */
	private String createUsername;
	/** Дата создания */
	private Date createDate;
	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {return serviceStream;}

	/** Поток обслуживания */
	private VocServiceStream serviceStream;
	
	/** Мед. услуга */
	@Comment("Мед. услуга")
	@OneToOne
	public MedService getMedService() {return medService;}

	/** Мед. услуга */
	private MedService medService;
	
	/** Аборта */
	@Comment("Аборта")
	@OneToOne
	public VocAbortion getAbortion() {return abortion;}

	/** Аборта */
	private VocAbortion abortion;
	
	/** Дата печати */
	private Date printDate;
	
	/** Время печати */
	private Time printTime;
	
	/** Пользователь, посл. распечат. документ */
	private String printUsername;

	/** На какой конечности была сделана операция */
	@Comment("На какой конечности была сделана операция")
	@OneToOne
	public VocLeftRight getLeftRight() {return leftRight;}
	private VocLeftRight leftRight ;

	/** Класс раны */
	@Comment("Класс раны")
	@OneToOne
	public VocClassWound getClassWound() {return classWound;}
	private VocClassWound classWound ;

	/** Препарат периоперационной антибиотикопрофилактики */
	@Comment("Препарат периоперационной антибиотикопрофилактики")
	@OneToOne
	public VocAntibioticDrug getAntibioticDrug() {return antibioticDrug;}
	private VocAntibioticDrug antibioticDrug ;

	/** Доза (мл) */
	private Double dose;

	/** Способы введения препаратов при периоперационной антибиотикопрофилактике */
	@Comment("Способы введения препаратов при периоперационной антибиотикопрофилактике")
	@OneToOne
	public VocMethodsDrugAdm getMethodsDrugAdm() {return methodsDrugAdm;}
	private VocMethodsDrugAdm methodsDrugAdm ;
	
	/** Время первой дозы */
	private Time firstDoseTime;

	/** Время повторной (при необходимости) дозы */
	private Time secondDoseTime;

	/** Препарат контраста */
	@Comment("Препарат контраста")
	@OneToOne
	public VocContrastDrug getContrastDrug() {return contrastDrug;}
	private VocContrastDrug contrastDrug ;

	/** Кол-во препарата контраста */
	@Comment("Кол-во препарата контраста")
	@OneToOne
	public VocContrastNumDrug getContrastNumDrug() {return contrastNumDrug;}
	private VocContrastNumDrug contrastNumDrug ;

	/** Вводился ли контраст?contrastDrug  */
	private Boolean isContrast;
}
