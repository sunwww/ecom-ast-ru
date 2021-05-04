package ru.ecom.mis.ejb.domain.extdisp;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDisp;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispAgeGroup;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispHealthGroup;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispSocialGroup;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.Kinsman;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/** Карта учета дополнительной диспансеризации (профосмотров) (УФ N 131/у) */
@Comment("Карта учета дополнительной диспансеризации (профосмотров) (УФ N 131/у)")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="patient"),
	@AIndex(properties="finishDate")
    }) 
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public class ExtDispCard extends BaseEntity{
	
	/** Источник финансирования */
	@Comment("Источник финансирования")
	@OneToOne
	public VocServiceStream  getServiceStream() {return serviceStream;}
	/** Источник финансирования */
	private VocServiceStream  serviceStream;

	/** Не подавать на оплату по ОМС */
	private Boolean isNoOmc;
	
	/** Дата экспорта в федеральную систему */
	private Date exportDate;

	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return patient;}
	/** Пациент */
	private Patient patient;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {return lpu;}
	/** ЛПУ */
	private MisLpu lpu;
	
	@OneToMany(mappedBy="card", cascade=CascadeType.ALL)
	public List<ExtDispService> getServices() {return services;}
	/** Услуги */
	private List<ExtDispService> services;
	
	/** Госпитализирован */
	private Boolean hospitalized;
	
	/** Социальная группа */
	@Comment("Социальная группа")
	@OneToOne
	public VocExtDispSocialGroup getSocialGroup() {return socialGroup;}
	/** Социальная группа */
	private VocExtDispSocialGroup socialGroup;
	
	/** Тип дополнительной диспансеризации */
	@Comment("Тип дополнительной диспансеризации")
	@OneToOne
	public VocExtDisp getDispType() {return dispType;}
	/** Тип дополнительной диспансеризации */
	private VocExtDisp dispType;
	
	/** На выезде */
	private Boolean onDeparture;
	
	/** Дата начала */
	private Date startDate;
	
	/** Дата окончания */
	private Date finishDate;
	
	/** Группа здоровья дополнительной диспансеризации */
	@Comment("Группа здоровья дополнительной диспансеризации")
	@OneToOne
	public VocExtDispHealthGroup getHealthGroup() {return healthGroup;}
	/** Группа здоровья дополнительной диспансеризации */
	private VocExtDispHealthGroup healthGroup;
	
	/** Взят на диспансерное наблюдение */
	private Boolean isObservation;
	
	/** Назначено лечение */
	private Boolean isTreatment;
	
	/** Назначена дополнительное диагностическое исследование */
	private Boolean isDiagnostics;
	
	/** Дано направление  для  получения  специализированной,  в  том  числе ВМП */
	private Boolean isSpecializedCare;
	
	/** Дано направление на санаторно-курортное лечение */
	private Boolean isSanatorium;
	
	@OneToMany(mappedBy="card", cascade=CascadeType.ALL)
	public List<ExtDispRisk> getRisks() {return risks;}
	/** Риски здоровью */
	private List<ExtDispRisk> risks;
	
	/** Принадлежность к коренным малочисленным народам Севера, Сибири и Дальнего Востока РФ */
	private Boolean isSmallNation;
	
	/** МКБ основного диагноза */
	@Comment("МКБ основного диагноза")
	@OneToOne
	public VocIdc10 getIdcMain() {return idcMain;}
	/** МКБ основного диагноза */
	private VocIdc10 idcMain;
	
	/** Направлен на след. этап */
	private Boolean isServiceIndication;

	/** Возрастная категория */
	@Comment("Возрастная категория")
	@OneToOne
	public VocExtDispAgeGroup getAgeGroup() {return ageGroup;}
	/** Возрастная категория */
	private VocExtDispAgeGroup ageGroup;
	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunction() {return workFunction;}

	/** Представитель */
	@Comment("Представитель")
	@OneToOne
	public Kinsman getKinsman() {return kinsman;}

	/** Представитель */
	private Kinsman kinsman;
	/** Рабочая функция */
	private WorkFunction workFunction;
	
	/** К оплате не принято */
	private Boolean notPaid;

	/** Пользователь редактировавший услуги */
	private String editUsernameRender;
	/** Время редактирование услуги */
	private Time editTimeRender;
	/** Дата редактирования услуги */
	private Date editDateRender;
	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private Time editTime;
	/** Время создания */
	private Time createTime;
	/** Дата редактирования */
	private Date editDate;
	/** Дата создания */
	private Date createDate;

	
	/** Основной диагноз установлен впервые */
	private Boolean isDiagnosisSetFirstTime ;

	/** Дата следующего визита */
	private Date nextDispDate ;
}
