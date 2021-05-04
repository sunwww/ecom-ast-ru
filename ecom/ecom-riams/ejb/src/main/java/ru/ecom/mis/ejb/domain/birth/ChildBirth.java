package ru.ecom.mis.ejb.domain.birth;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.birth.voc.*;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.patient.voc.VocColorIdentityPatient;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

/**
 * Роды
 * @author azviagin
 *
 */

@Comment("Роды")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
		@AIndex(properties = { "medCase" }) 
		,@AIndex(properties = { "pregnancy" }) 
	}
)
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public class ChildBirth extends BaseEntity{
	/** Преждевременность отхождения вод */
	@Comment("Преждевременность отхождения вод")
	@OneToOne
	public VocBirthWatesPrematurity getWatersPrematurity() {return watersPrematurity;}
	/** Отделение плаценты */
	@Comment("Отделение плаценты")
	@OneToOne
	public VocPlacentaSeparation getPlacentaSeparation() {return placentaSeparation;}


	/** Целостность плаценты */
	@Comment("Целостность плаценты")
	@OneToOne
	public VocPlacentaIntegrity getPlacentaIntegrity() {	return placentaIntegrity;}

	/** Целостность оболочек */
	@OneToOne
	@Comment("Целостность оболочек")
	public VocFetalMembranesIntegrity getMembranesIntegrity() {return membranesIntegrity;}

	/** Место разрыва оболочек */
	@Comment("Место разрыва оболочек")
	@OneToOne
	public VocMembranesBreakPlace getMembranesBreakPlace() {return membranesBreakPlace;}
	/** Кто исследовал плаценту */
	@Comment("Кто исследовал плаценту")
	@OneToOne
	public WorkFunction getPlacentaInspector() {return placentaInspector;}

	/** Повышение температуры без диагноза */
	@Comment("Повышение температуры без диагноза")
	@OneToOne
	public VocFeverFeature getFeverWithoutDiagnosis() {return feverWithoutDiagnosis;}

	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return medCase;}

	/** Гистология плаценты */
	@Comment("Гистология плаценты")
	@OneToOne
	public VocHistologyResult getHistology() {return histology;}

	/** Гистология плаценты */
	private VocHistologyResult histology;
	/** Время начала потуг */
	private Time travailStartTime;
	/** Дата начала потуг */
	private Date travailStartDate;
	/** Качество верхних вод */
	private String upperWatersQuality;
	/** Качество нижних вод */
	private String downWatersQuality;
	/** Количество верхних вод (мл) */
	private Integer upperWatersAmount;
	/** Количество нижних вод (мл) */
	private Integer downWatersAmount;
	/** Преждевременность отхождения вод */
	private VocBirthWatesPrematurity watersPrematurity;
	/** Время отхождения вод */
	private Time watersTime;
	/** Дата отхождения вод */
	private Date watersDate;
	/** Время начала схваток */
	private Time pangsStartTime;
	/** Дата начала схваток */
	private Date pangsStartDate;
	/** Отделение плаценты */
	private VocPlacentaSeparation placentaSeparation;
	/** Размеры плаценты */
	private String placentaSize;
	/** Целостность плаценты */
	private VocPlacentaIntegrity placentaIntegrity;
	/** Особенности плаценты */
	private String placentaFeatures;
	/** Масса плаценты (гр) */
	private Integer placentaWeight;
	/** Целостность оболочек */
	private VocFetalMembranesIntegrity membranesIntegrity;
	/** Место разрыва оболочек */
	private VocMembranesBreakPlace membranesBreakPlace;
	/** Объем кровопотери (мл) */
	private Integer hemorrhageVolume;
	/** Особенности лечения */
	private String treatmentFeatures;
	/** Направление плаценты на гистологию */
	private Boolean placentaHistologyOrder;
	/** СМО */
	private MedCase medCase;
	/** Повышение температуры без диагноза */
	private VocFeverFeature feverWithoutDiagnosis;
	/** Продолжительность 1 периода (час) */
	private BigDecimal period1Duration;
	/** Продолжительность 2 периода (час) */
	private BigDecimal period2Duration;
	/** Продолжительность 3 периода (час) */
	private BigDecimal period3Duration;
	/** Дата окончания родов */
	private Date birthFinishDate;
	/** Время окончания родов */
	private Time birthFinishTime;
	/** Кто исследовал плаценту */
	private WorkFunction placentaInspector;
	/** Состояние матери при выписке */
	private String dischangeMotherCondition;
	/** Неправильный постнатальный период */
	private Boolean wrongPostNatalPeriod;
	/** Трещины сосков */
	private Boolean mammillaChaps;
	/** Отек наружных половых органов */
	private String vulvaEdema;
	/** Отек промежности */
	private String perineumEdema;
	/** Геморрой */
	private String haemorrhoids;
	/** Послеродовая болезнь */
	private String postNatalDisease;
	/** Болезнь, не зависящая от родов */
	private String notPostNatalDisease;
	/** Беременность */
	@Comment("Беременность")
	@OneToOne
	public Pregnancy getPregnancy() {return pregnancy;}

	/** Беременность */
	private Pregnancy pregnancy;
	
	@Transient
	public MedCase getSls(){
		return medCase!=null?medCase.getParent():null ;
	}
		
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
	/** Вес */
	private Integer weight;
	/** Рост */
	private Integer height;

	/** Роды тип (кесарево, самостоятельные) */
	@Comment("Роды тип (кесарево, самостоятельные)")
	@OneToOne
	public VocChildBirth getChildBirthType() {return childBirthType;}

	/** Роды тип (кесарево, самостоятельные) */
	private VocChildBirth childBirthType;
	
	/** Где произошли роды */
	@Comment("Где произошли роды")
	@OneToOne
	public VocWhereBirthOccurred getWhereBirthOccurred() {return whereBirthOccurred;}

	/** Другое место родов */
	private String whereBirthOccurredOther;
	/** Где произошли роды */
	private VocWhereBirthOccurred whereBirthOccurred;
	/** Срок беременности (нед) */
	private BigDecimal durationPregnancy;
	/** Послед выделился через (мин) */
	private Long placentaMinute;
	/** Длина пуповины */
	private BigDecimal cordLength;
	
	/** Ребенка принял */
	@Comment("Ребенка принял")
	@OneToOne
	public WorkFunction getChildTook() {return childTook;}

	/** Ребенка принял */
	private WorkFunction childTook;
	
	/** Медикаментозное обезболивание */
	@Comment("Медикаментозное обезболивание")
	@OneToOne
	public VocChildAnesthesiaMedication getAnesthesiaMedication() {return anesthesiaMedication;}

	/** Медикаментозное обезболивание */
	private VocChildAnesthesiaMedication anesthesiaMedication;
	
	/** Эффект от медикаментозного обезболивания */
	@Comment("Эффект от медикаментозного обезболивания")
	@OneToOne
	public VocChildAnesthesiaMedicationEffect getAnesthesiaMedicationEffect() {return anesthesiaMedicationEffect;}

	/** Эффект от медикаментозного обезболивания */
	private VocChildAnesthesiaMedicationEffect anesthesiaMedicationEffect;
	
	/** Дата полного открытия */
	private Date fullOpenDate;
	/** Время полного открытия */
	private Time fullOpenTime;
	
	/** Показания */
	@Comment("Показания")
	@OneToOne
	public VocChildEmergency getEmergency() {return emergency;}

	/** Показания */
	private VocChildEmergency emergency;

	/** Паритет родов */
	@Comment("Паритет родов")
	@OneToOne
	public VocParitet getParitet() {return paritet;}

	/** Паритет родов */
	private VocParitet paritet;

	/** ЭКО? */
	private Boolean isECO;

	/** Состояла на учёте в ЖК? */
	private Boolean isRegisteredWithWomenConsultation;

	/** Паритет беременностей */
	@Comment("Паритет беременностей")
	@OneToOne
	public VocParitet getParitetPregn() {return paritetPregn;}

	/** Паритет беременностей */
	private VocParitet paritetPregn;

	/** Длительность безводного периода (часы)*/
	private Long waterlessDurationHour;

	/** Длительность безводного периода (минуты)*/
	private Long waterlessDurationMin;


	/** Диабет (браслет)*/
	@Comment("Диабет (браслет)")
	@OneToOne
	public VocColorIdentityPatient getDiabetIdentity() {return diabetIdentity;}
	/** Диабет (браслет)*/
	private VocColorIdentityPatient diabetIdentity;

	/** Женская консультация */
	@Comment("Женская консультация")
	@OneToOne
	public VocWomenConsult getWomenConsult() {return womenConsult;}

	/** Женская консультация */
	private VocWomenConsult womenConsult;
}