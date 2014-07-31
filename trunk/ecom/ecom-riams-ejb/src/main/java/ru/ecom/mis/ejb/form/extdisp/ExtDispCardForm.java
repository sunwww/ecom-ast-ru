package ru.ecom.mis.ejb.form.extdisp;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.OneToOne;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispCard;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispSocialGroup;
import ru.ecom.mis.ejb.domain.patient.Kinsman;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.form.extdisp.interceptor.ExtDispCardViewInterceptor;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = ExtDispCard.class)
@Comment("Карта учета дополнительной диспансеризации (профосмотров) (УФ N 131/у)")
@WebTrail(comment = "Карта учета дополнительной диспансеризации (профосмотров) (УФ N 131/у)"
, nameProperties= "id", list="entityParentList-extDisp_card.do", view="entityParentView-extDisp_card.do")
@Parent(property="patient", parentForm=PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card")
@AViewInterceptors(
        @AEntityFormInterceptor(ExtDispCardViewInterceptor.class)
)
public class ExtDispCardForm extends IdEntityForm{
	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}
	/** Пациент */
	private Long thePatient;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist @Required
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}
	/** ЛПУ  */
	private Long theLpu;
	
	/** Госпитализирован */
	@Comment("Госпитализирован")
	@Persist
	public Boolean getHospitalized() {return theHospitalized;}
	public void setHospitalized(Boolean aHospitalized) {theHospitalized = aHospitalized;}
	/** Госпитализирован */
	private Boolean theHospitalized;
	
	/** Социальная группа */
	@Comment("Социальная группа")
	@Persist @Required
	public Long getSocialGroup() {return theSocialGroup;}
	public void setSocialGroup(Long aSocialGroup) {theSocialGroup = aSocialGroup;}
	/** Социальная группа */
	private Long theSocialGroup;
	
	/** Тип дополнительной диспансеризации */
	@Comment("Тип дополнительной диспансеризации")
	@Persist @Required
	public Long getDispType() {return theDispType;}
	public void setDispType(Long aDispType) {theDispType = aDispType;}
	/** Тип дополнительной диспансеризации */
	private Long theDispType;
	
	/** На выезде */
	@Comment("На выезде")
	@Persist
	public Boolean getOnDeparture() {return theOnDeparture;}
	public void setOnDeparture(Boolean aOnDeparture) {theOnDeparture = aOnDeparture;}
	/** На выезде */
	private Boolean theOnDeparture;
	
	/** Дата начала */
	@Comment("Дата начала")
	@Persist @Required
	@DateString @DoDateString
	public String getStartDate() {return theStartDate;}
	public void setStartDate(String aStartDate) {theStartDate = aStartDate;}
	/** Дата начала */
	private String theStartDate;
	
	/** Дата окончания */
	@Comment("Дата окончания")
	@Persist @Required
	@DateString @DoDateString
	public String getFinishDate() {return theFinishDate;}
	public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}
	/** Дата окончания */
	private String theFinishDate;
	
	/** Группа здоровья дополнительной диспансеризации */
	@Comment("Группа здоровья дополнительной диспансеризации")
	@Persist @Required
	public Long getHealthGroup() {return theHealthGroup;}
	public void setHealthGroup(Long aHealthGroup) {theHealthGroup = aHealthGroup;}
	/** Группа здоровья дополнительной диспансеризации */
	private Long theHealthGroup;
	
	/** Взят на диспансерное наблюдение */
	@Comment("Взят на диспансерное наблюдение")
	@Persist
	public Boolean getIsObservation() {return theIsObservation;}
	public void setIsObservation(Boolean aIsObservation) {theIsObservation = aIsObservation;}
	/** Взят на диспансерное наблюдение */
	private Boolean theIsObservation;
	
	/** Назначено лечение */
	@Comment("Назначено лечение")
	@Persist
	public Boolean getIsTreatment() {return theIsTreatment;}
	public void setIsTreatment(Boolean aIsTreatment) {theIsTreatment = aIsTreatment;}
	/** Назначено лечение */
	private Boolean theIsTreatment;
	
	/** Назначена дополнительное диагностическое исследование */
	@Comment("Назначена дополнительное диагностическое исследование")
	@Persist
	public Boolean getIsDiagnostics() {return theIsDiagnostics;}
	public void setIsDiagnostics(Boolean aIsDiagnostics) {theIsDiagnostics = aIsDiagnostics;}
	/** Назначена дополнительное диагностическое исследование */
	private Boolean theIsDiagnostics;
	
	/** Дано направление  для  получения  специализированной,  в  том  числе ВМП */
	@Comment("Дано направление  для  получения  специализированной,  в  том  числе ВМП")
	@Persist
	public Boolean getIsSpecializedCare() {return theIsSpecializedCare;}
	public void setIsSpecializedCare(Boolean aIsSpecializedCare) {theIsSpecializedCare = aIsSpecializedCare;}
	/** Дано направление  для  получения  специализированной,  в  том  числе ВМП */
	private Boolean theIsSpecializedCare;
	
	/** Дано направление на санаторно-курортное лечение */
	@Comment("Дано направление на санаторно-курортное лечение")
	@Persist
	public Boolean getIsSanatorium() {return theIsSanatorium;}
	public void setIsSanatorium(Boolean aIsSanatorium) {theIsSanatorium = aIsSanatorium;}
	/** Дано направление на санаторно-курортное лечение */
	private Boolean theIsSanatorium;
	
	/** Принадлежность к коренным малочисленным народам Севера, Сибири и Дальнего Востока РФ */
	@Comment("Принадлежность к коренным малочисленным народам Севера, Сибири и Дальнего Востока РФ")
	@Persist
	public Boolean getIsSmallNation() {return theIsSmallNation;}
	public void setIsSmallNation(Boolean aIsSmallNation) {theIsSmallNation = aIsSmallNation;}
	/** Принадлежность к коренным малочисленным народам Севера, Сибири и Дальнего Востока РФ */
	private Boolean theIsSmallNation;
	
	/** МКБ основного диагноза */
	@Comment("МКБ основного диагноза")
	@Persist @Required
	public Long getIdcMain() {return theIdcMain;}
	public void setIdcMain(Long aIdcMain) {theIdcMain = aIdcMain;}
	/** МКБ основного диагноза */
	private Long theIdcMain;
	
	/** Риски */
	@Comment("Риски")
	public String getRisks() {return theRisks;}
	public void setRisks(String aRisks) {theRisks = aRisks;}

	/** Риски */
	private String theRisks;
	
	/** Направлен на след. этап */
	@Comment("Направлен на след. этап")
	@Persist
	public Boolean getIsServiceIndication() {return theIsServiceIndication;}
	public void setIsServiceIndication(Boolean aIsServiceIndication) {theIsServiceIndication = aIsServiceIndication;}

	/** Направлен на след. этап */
	private Boolean theIsServiceIndication;
	
	/** Возрастная категория */
	@Comment("Возрастная категория")
	@Persist @Required
	public Long getAgeGroup() {return theAgeGroup;}
	public void setAgeGroup(Long aAgeGroup) {theAgeGroup = aAgeGroup;}

	/** Возрастная категория */
	private Long theAgeGroup;
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist @Required
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Представитель */
	@Comment("Представитель")
	@Persist
	public Long getKinsman() {return theKinsman;}
	public void setKinsman(Long aKinsman) {theKinsman = aKinsman;}

	/** Представитель */
	private Long theKinsman;
	/** Рабочая функция */
	private Long theWorkFunction;
	
	/** Возраст вычисляемый */
	@Comment("Возраст вычисляемый")
	public String getAge() {return theAge;}
	public void setAge(String aAge) {theAge = aAge;}

	/** Возраст вычисляемый */
	private String theAge;
	/** К оплате не принято */
	@Comment("К оплате не принято")
	@Persist
	public Boolean getNotPaid() {return theNotPaid;}
	public void setNotPaid(Boolean aNotPaid) {theNotPaid = aNotPaid;}

	/** К оплате не принято */
	private Boolean theNotPaid;
	
	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return theEditTime;}
	public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Дата редактирования услуги */
	@Comment("Дата редактирования услуги")
	@Persist @DateString @DoDateString
	public String getEditDateRender() {return theEditDateRender;}
	public void setEditDateRender(String aEditDateRender) {theEditDateRender = aEditDateRender;}

	/** Время редактирование услуги */
	@Comment("Время редактирование услуги")
	@Persist @TimeString @DoTimeString
	public String getEditTimeRender() {return theEditTimeRender;}
	public void setEditTimeRender(String aEditTimeRender) {theEditTimeRender = aEditTimeRender;}

	/** Пользователь редактировавший услуги */
	@Comment("Пользователь редактировавший услуги")
	@Persist
	public String getEditUsernameRender() {return theEditUsernameRender;}
	public void setEditUsernameRender(String aEditUsernameRender) {theEditUsernameRender = aEditUsernameRender;}

	/** Пользователь редактировавший услуги */
	private String theEditUsernameRender;
	/** Время редактирование услуги */
	private String theEditTimeRender;
	/** Дата редактирования услуги */
	private String theEditDateRender;
	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private String theEditTime;
	/** Время создания */
	private String theCreateTime;
	/** Дата редактирования */
	private String theEditDate;
	/** Дата создания */
	private String theCreateDate;
}
