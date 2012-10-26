package ru.ecom.mis.ejb.domain.birth;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.mis.ejb.domain.birth.voc.VocBirthWatesPrematurity;
import ru.ecom.mis.ejb.domain.birth.voc.VocFetalMembranesIntegrity;
import ru.ecom.mis.ejb.domain.birth.voc.VocFeverFeature;
import ru.ecom.mis.ejb.domain.birth.voc.VocHistologyResult;
import ru.ecom.mis.ejb.domain.birth.voc.VocMembranesBreakPlace;
import ru.ecom.mis.ejb.domain.birth.voc.VocPlacentaIntegrity;
import ru.ecom.mis.ejb.domain.birth.voc.VocPlacentaSeparation;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

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
public class ChildBirth extends BaseEntity{
	
	/** Время начала потуг */
	@Comment("Время начала потуг")
	public Time getTravailStartTime() {return theTravailStartTime;}
	public void setTravailStartTime(Time aTravailStartTime) {theTravailStartTime = aTravailStartTime;}

	/** Дата начала потуг */
	@Comment("Дата начала потуг")
	public Date getTravailStartDate() {return theTravailStartDate;}
	public void setTravailStartDate(Date aTravailStartDate) {theTravailStartDate = aTravailStartDate;}

	/** Качество верхних вод */
	@Comment("Качество верхних вод")
	public String getUpperWatersQuality() {return theUpperWatersQuality;}
	public void setUpperWatersQuality(String aUpperWatersQuality) {theUpperWatersQuality = aUpperWatersQuality;}

	/** Качество нижних вод */
	@Comment("Качество нижних вод")
	public String getDownWatersQuality() {return theDownWatersQuality;}
	public void setDownWatersQuality(String aDownWatersQuality) {theDownWatersQuality = aDownWatersQuality;}

	/** Количество верхних вод (мл) */
	@Comment("Количество верхних вод (мл)")
	public Integer getUpperWatersAmount() {return theUpperWatersAmount;}
	public void setUpperWatersAmount(Integer aUpperWatersAmount) {theUpperWatersAmount = aUpperWatersAmount;}

	/** Количество нижних вод (мл) */
	@Comment("Количество нижних вод (мл)")
	public Integer getDownWatersAmount() {return theDownWatersAmount;}
	public void setDownWatersAmount(Integer aDownWatersAmount) {theDownWatersAmount = aDownWatersAmount;}

	/** Преждевременность отхождения вод */
	@Comment("Преждевременность отхождения вод")
	@OneToOne
	public VocBirthWatesPrematurity getWatersPrematurity() {return theWatersPrematurity;}
	public void setWatersPrematurity(VocBirthWatesPrematurity aWatersPrematurity) {theWatersPrematurity = aWatersPrematurity;}

	/** Время отхождения вод */
	@Comment("Время отхождения вод")
	public Time getWatersTime() {return theWatersTime;}
	public void setWatersTime(Time aWatersTime) {theWatersTime = aWatersTime;}

	/** Дата отхождения вод */
	@Comment("Дата отхождения вод")
	public Date getWatersDate() {return theWatersDate;}
	public void setWatersDate(Date aWatersDate) {theWatersDate = aWatersDate;}

	/** Время начала схваток */
	@Comment("Время начала схваток")
	public Time getPangsStartTime() {return thePangsStartTime;}
	public void setPangsStartTime(Time aPangsStartTime) {thePangsStartTime = aPangsStartTime;}

	/** Дата начала схваток */
	@Comment("Дата начала схваток")
	public Date getPangsStartDate() {return thePangsStartDate;}
	public void setPangsStartDate(Date aPangsStartDate) {thePangsStartDate = aPangsStartDate;}

	/** Отделение плаценты */
	@Comment("Отделение плаценты")
	@OneToOne
	public VocPlacentaSeparation getPlacentaSeparation() {return thePlacentaSeparation;}
	public void setPlacentaSeparation(VocPlacentaSeparation aPlacentaSeparation) {thePlacentaSeparation = aPlacentaSeparation;}

	/** Размеры плаценты */
	@Comment("Размеры плаценты")
	public String getPlacentaSize() {return thePlacentaSize;}
	public void setPlacentaSize(String aPlacentaSize) {thePlacentaSize = aPlacentaSize;}
	
	/** Целостность плаценты */
	@Comment("Целостность плаценты")
	@OneToOne
	public VocPlacentaIntegrity getPlacentaIntegrity() {	return thePlacentaIntegrity;}
	public void setPlacentaIntegrity(VocPlacentaIntegrity aPlacentaIntegrity) {thePlacentaIntegrity = aPlacentaIntegrity;}
	
	/** Особенности плаценты */
	@Comment("Особенности плаценты")
	public String getPlacentaFeatures() {return thePlacentaFeatures;}
	public void setPlacentaFeatures(String aPlacentaFeatures) {thePlacentaFeatures = aPlacentaFeatures;}
	
	/** Масса плаценты (гр) */
	@Comment("Масса плаценты (гр)")
	public Integer getPlacentaWeight() {return thePlacentaWeight;}
	public void setPlacentaWeight(Integer aPlacentaWeight) {thePlacentaWeight = aPlacentaWeight;}
	
	/** Целостность оболочек */
	@OneToOne
	@Comment("Целостность оболочек")
	public VocFetalMembranesIntegrity getMembranesIntegrity() {return theMembranesIntegrity;}
	public void setMembranesIntegrity(VocFetalMembranesIntegrity aMembranesIntegrity) {theMembranesIntegrity = aMembranesIntegrity;}
	
	/** Место разрыва оболочек */
	@Comment("Место разрыва оболочек")
	@OneToOne
	public VocMembranesBreakPlace getMembranesBreakPlace() {return theMembranesBreakPlace;}
	public void setMembranesBreakPlace(VocMembranesBreakPlace aMembranesBreakPlace) {theMembranesBreakPlace = aMembranesBreakPlace;}
	
	/** Объем кровопотери (мл) */
	@Comment("Объем кровопотери (мл)")
	public Integer getHemorrhageVolume() {return theHemorrhageVolume;}
	public void setHemorrhageVolume(Integer aHemorrhageVolume) {theHemorrhageVolume = aHemorrhageVolume;}

	/** Продолжительность 1 периода (час) */
	@Comment("Продолжительность 1 периода (час)")
	public BigDecimal getPeriod1Duration() {return thePeriod1Duration;}
	public void setPeriod1Duration(BigDecimal aPeriod1Duration) {thePeriod1Duration = aPeriod1Duration;}
	
	/** Продолжительность 2 периода (час) */
	@Comment("Продолжительность 2 периода (час)")
	public BigDecimal getPeriod2Duration() {return thePeriod2Duration;}
	public void setPeriod2Duration(BigDecimal aPeriod2Duration) {thePeriod2Duration = aPeriod2Duration;}

	
	/** Продолжительность 3 периода (час) */
	@Comment("Продолжительность 3 периода (час)")
	public BigDecimal getPeriod3Duration() {return thePeriod3Duration;}
	public void setPeriod3Duration(BigDecimal aPeriod3Duration) {thePeriod3Duration = aPeriod3Duration;}
	
	/** Дата окончания родов */
	@Comment("Дата окончания родов")
	public Date getBirthFinishDate() {return theBirthFinishDate;}
	public void setBirthFinishDate(Date aBirthFinishDate) {theBirthFinishDate = aBirthFinishDate;}
	
	/** Время окончания родов */
	@Comment("Время окончания родов")
	public Time getBirthFinishTime() {return theBirthFinishTime;}
	public void setBirthFinishTime(Time aBirthFinishTime) {theBirthFinishTime = aBirthFinishTime;}
	
	/** Кто исследовал плаценту */
	@Comment("Кто исследовал плаценту")
	@OneToOne
	public WorkFunction getPlacentaInspector() {return thePlacentaInspector;}
	public void setPlacentaInspector(WorkFunction aPlacentaInspector) {thePlacentaInspector = aPlacentaInspector;}
	
	/** Состояние матери при выписке */
	@Comment("Состояние матери при выписке")
	public String getDischangeMotherCondition() {return theDischangeMotherCondition;}
	public void setDischangeMotherCondition(String aDischangeMotherCondition) {theDischangeMotherCondition = aDischangeMotherCondition;}
	
	/** Неправильный постнатальный период */
	@Comment("Неправильный постнатальный период")
	public Boolean getWrongPostNatalPeriod() {return theWrongPostNatalPeriod;}
	public void setWrongPostNatalPeriod(Boolean aWrongPostNatalPeriod) {theWrongPostNatalPeriod = aWrongPostNatalPeriod;}
	
	/** Трещины сосков */
	@Comment("Трещины сосков")
	public Boolean getMammillaChaps() {return theMammillaChaps;}
	public void setMammillaChaps(Boolean aMammillaChaps) {theMammillaChaps = aMammillaChaps;}
	
	/** Отек наружных половых органов */
	@Comment("Отек наружных половых органов")
	public String getVulvaEdema() {return theVulvaEdema;}
	public void setVulvaEdema(String aVulvaEdema) {theVulvaEdema = aVulvaEdema;}
	
	/** Отек промежности */
	@Comment("Отек промежности")
	public String getPerineumEdema() {return thePerineumEdema;}
	public void setPerineumEdema(String aPerineumEdema) {thePerineumEdema = aPerineumEdema;}
	
	/** Геморрой */
	@Comment("Геморрой")
	public String getHaemorrhoids() {return theHaemorrhoids;}
	public void setHaemorrhoids(String aHaemorrhoids) {theHaemorrhoids = aHaemorrhoids;}
	
	/** Послеродовая болезнь */
	@Comment("Послеродовая болезнь")
	public String getPostNatalDisease() {return thePostNatalDisease;}
	public void setPostNatalDisease(String aPostNatalDisease) {thePostNatalDisease = aPostNatalDisease;}
	
	/** Болезнь, не зависящая от родов */
	@Comment("Болезнь, не зависящая от родов")
	public String getNotPostNatalDisease() {return theNotPostNatalDisease;}
	public void setNotPostNatalDisease(String aNotPostNatalDisease) {theNotPostNatalDisease = aNotPostNatalDisease;}
	
	/** Повышение температуры без диагноза */
	@Comment("Повышение температуры без диагноза")
	@OneToOne
	public VocFeverFeature getFeverWithoutDiagnosis() {return theFeverWithoutDiagnosis;}
	public void setFeverWithoutDiagnosis(VocFeverFeature aFeverWithoutDiagnosis) {theFeverWithoutDiagnosis = aFeverWithoutDiagnosis;}
	
	/** Особенности лечения */
	@Comment("Особенности лечения")
	public String getTreatmentFeatures() {return theTreatmentFeatures;}
	public void setTreatmentFeatures(String aTreatmentFeatures) {theTreatmentFeatures = aTreatmentFeatures;}
	
	/** Направление плаценты на гистологию */
	@Comment("Направление плаценты на гистологию")
	public Boolean getPlacentaHistologyOrder() {return thePlacentaHistologyOrder;}
	public void setPlacentaHistologyOrder(Boolean aPlacentaHistologyOrder) {thePlacentaHistologyOrder = aPlacentaHistologyOrder;}

	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}
	
	/** Новорожденные */
	@Comment("Новорожденные")
	@OneToMany(mappedBy="childBirth")
	public List<NewBorn> getNewBorns() {return theNewBorns;}
	public void setNewBorns(List<NewBorn> aNewBorns) {theNewBorns = aNewBorns;}

	/** Гистология плаценты */
	@Comment("Гистология плаценты")
	@OneToOne
	public VocHistologyResult getHistology() {return theHistology;}
	public void setHistology(VocHistologyResult aHistology) {theHistology = aHistology;}

	/** Гистология плаценты */
	private VocHistologyResult theHistology;
	/** Время начала потуг */
	private Time theTravailStartTime;
	/** Дата начала потуг */
	private Date theTravailStartDate;
	/** Качество верхних вод */
	private String theUpperWatersQuality;
	/** Качество нижних вод */
	private String theDownWatersQuality;
	/** Количество верхних вод (мл) */
	private Integer theUpperWatersAmount;
	/** Количество нижних вод (мл) */
	private Integer theDownWatersAmount;
	/** Преждевременность отхождения вод */
	private VocBirthWatesPrematurity theWatersPrematurity;
	/** Время отхождения вод */
	private Time theWatersTime;
	/** Дата отхождения вод */
	private Date theWatersDate;
	/** Время начала схваток */
	private Time thePangsStartTime;
	/** Дата начала схваток */
	private Date thePangsStartDate;
	/** Отделение плаценты */
	private VocPlacentaSeparation thePlacentaSeparation;
	/** Размеры плаценты */
	private String thePlacentaSize;
	/** Целостность плаценты */
	private VocPlacentaIntegrity thePlacentaIntegrity;
	/** Особенности плаценты */
	private String thePlacentaFeatures;
	/** Масса плаценты (гр) */
	private Integer thePlacentaWeight;
	/** Целостность оболочек */
	private VocFetalMembranesIntegrity theMembranesIntegrity;
	/** Место разрыва оболочек */
	private VocMembranesBreakPlace theMembranesBreakPlace;
	/** Объем кровопотери (мл) */
	private Integer theHemorrhageVolume;
	/** Особенности лечения */
	private String theTreatmentFeatures;
	/** Направление плаценты на гистологию */
	private Boolean thePlacentaHistologyOrder;
	/** СМО */
	private MedCase theMedCase;
	/** Новорожденные */
	private List<NewBorn> theNewBorns;
	/** Повышение температуры без диагноза */
	private VocFeverFeature theFeverWithoutDiagnosis;
	/** Продолжительность 1 периода (час) */
	private BigDecimal thePeriod1Duration;
	/** Продолжительность 2 периода (час) */
	private BigDecimal thePeriod2Duration;
	/** Продолжительность 3 периода (час) */
	private BigDecimal thePeriod3Duration;
	/** Дата окончания родов */
	private Date theBirthFinishDate;
	/** Время окончания родов */
	private Time theBirthFinishTime;
	/** Кто исследовал плаценту */
	private WorkFunction thePlacentaInspector;
	/** Состояние матери при выписке */
	private String theDischangeMotherCondition;
	/** Неправильный постнатальный период */
	private Boolean theWrongPostNatalPeriod;
	/** Трещины сосков */
	private Boolean theMammillaChaps;
	/** Отек наружных половых органов */
	private String theVulvaEdema;
	/** Отек промежности */
	private String thePerineumEdema;
	/** Геморрой */
	private String theHaemorrhoids;
	/** Послеродовая болезнь */
	private String thePostNatalDisease;
	/** Болезнь, не зависящая от родов */
	private String theNotPostNatalDisease;
	/** Беременность */
	@Comment("Беременность")
	@OneToOne
	public Pregnancy getPregnancy() {return thePregnancy;}
	public void setPregnancy(Pregnancy aPregnancy) {thePregnancy = aPregnancy;}

	/** Беременность */
	private Pregnancy thePregnancy;
	
	@Transient
	public MedCase getSls(){
		return theMedCase!=null?theMedCase.getParent():null ;
	}
		
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private Time theEditTime;
	/** Время создания */
	private Time theCreateTime;
	/** Дата редактирования */
	private Date theEditDate;
	/** Дата создания */
	private Date theCreateDate;

}