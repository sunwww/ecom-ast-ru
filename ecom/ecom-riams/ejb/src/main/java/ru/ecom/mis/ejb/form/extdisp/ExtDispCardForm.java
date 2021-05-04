package ru.ecom.mis.ejb.form.extdisp;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispCard;
import ru.ecom.mis.ejb.form.extdisp.interceptor.ExtDispCardViewInterceptor;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
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
@Setter
public class ExtDispCardForm extends IdEntityForm{
	
	/** Не подавать на оплату по ОМС */
	@Comment("Не подавать на оплату по ОМС")
	@Persist
	public Boolean getIsNoOmc() {return isNoOmc;}
	/** Не подавать на оплату по ОМС */
	private Boolean isNoOmc;
	
	/** Дата выгрузки в федеральную систему */
	@Comment("Дата выгрузки в федеральную систему")
	@Persist
	public String getExportDate() {return exportDate;}
	/** Дата выгрузки в федеральную систему */
	private String exportDate;

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return patient;}
	/** Пациент */
	private Long patient;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist @Required
	public Long getLpu() {return lpu;}
	/** ЛПУ  */
	private Long lpu;
	
	/** Госпитализирован */
	@Comment("Госпитализирован")
	@Persist
	public Boolean getHospitalized() {return hospitalized;}
	/** Госпитализирован */
	private Boolean hospitalized;
	
	/** Социальная группа */
	@Comment("Социальная группа")
	@Persist @Required
	public Long getSocialGroup() {return socialGroup;}
	/** Социальная группа */
	private Long socialGroup;
	
	/** Тип дополнительной диспансеризации */
	@Comment("Тип дополнительной диспансеризации")
	@Persist @Required
	public Long getDispType() {return dispType;}
	/** Тип дополнительной диспансеризации */
	private Long dispType;
	
	/** На выезде */
	@Comment("На выезде")
	@Persist
	public Boolean getOnDeparture() {return onDeparture;}
	/** На выезде */
	private Boolean onDeparture;
	
	/** Дата начала */
	@Comment("Дата начала")
	@Persist @Required
	@DateString @DoDateString
	public String getStartDate() {return startDate;}
	/** Дата начала */
	private String startDate;
	
	/** Дата окончания */
	@Comment("Дата окончания")
	@Persist @Required
	@DateString @DoDateString
	public String getFinishDate() {return finishDate;}
	/** Дата окончания */
	private String finishDate;
	
	/** Группа здоровья дополнительной диспансеризации */
	@Comment("Группа здоровья дополнительной диспансеризации")
	@Persist @Required
	public Long getHealthGroup() {return healthGroup;}
	/** Группа здоровья дополнительной диспансеризации */
	private Long healthGroup;
	
	/** Взят на диспансерное наблюдение */
	@Comment("Взят на диспансерное наблюдение")
	@Persist
	public Boolean getIsObservation() {return isObservation;}
	/** Взят на диспансерное наблюдение */
	private Boolean isObservation;
	
	/** Назначено лечение */
	@Comment("Назначено лечение")
	@Persist
	public Boolean getIsTreatment() {return isTreatment;}
	/** Назначено лечение */
	private Boolean isTreatment;
	
	/** Назначена дополнительное диагностическое исследование */
	@Comment("Назначена дополнительное диагностическое исследование")
	@Persist
	public Boolean getIsDiagnostics() {return isDiagnostics;}
	/** Назначена дополнительное диагностическое исследование */
	private Boolean isDiagnostics;
	
	/** Дано направление  для  получения  специализированной,  в  том  числе ВМП */
	@Comment("Дано направление  для  получения  специализированной,  в  том  числе ВМП")
	@Persist
	public Boolean getIsSpecializedCare() {return isSpecializedCare;}
	/** Дано направление  для  получения  специализированной,  в  том  числе ВМП */
	private Boolean isSpecializedCare;
	
	/** Дано направление на санаторно-курортное лечение */
	@Comment("Дано направление на санаторно-курортное лечение")
	@Persist
	public Boolean getIsSanatorium() {return isSanatorium;}
	/** Дано направление на санаторно-курортное лечение */
	private Boolean isSanatorium;
	
	/** Принадлежность к коренным малочисленным народам Севера, Сибири и Дальнего Востока РФ */
	@Comment("Принадлежность к коренным малочисленным народам Севера, Сибири и Дальнего Востока РФ")
	@Persist
	public Boolean getIsSmallNation() {return isSmallNation;}
	/** Принадлежность к коренным малочисленным народам Севера, Сибири и Дальнего Востока РФ */
	private Boolean isSmallNation;
	
	/** МКБ основного диагноза */
	@Comment("МКБ основного диагноза")
	@Persist @Required
	public Long getIdcMain() {return idcMain;}
	/** МКБ основного диагноза */
	private Long idcMain;
	
	/** Риски */
	@Comment("Риски")
	public String getRisks() {return risks;}

	/** Риски */
	private String risks;
	
	/** Направлен на след. этап */
	@Comment("Направлен на след. этап")
	@Persist
	public Boolean getIsServiceIndication() {return isServiceIndication;}

	/** Направлен на след. этап */
	private Boolean isServiceIndication;
	
	/** Возрастная категория */
	@Comment("Возрастная категория")
	@Persist @Required
	public Long getAgeGroup() {return ageGroup;}

	/** Возрастная категория */
	private Long ageGroup;
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist @Required
	public Long getWorkFunction() {return workFunction;}

	/** Представитель */
	@Comment("Представитель")
	@Persist
	public Long getKinsman() {return kinsman;}

	/** Представитель */
	private Long kinsman;
	/** Рабочая функция */
	private Long workFunction;
	
	/** Возраст вычисляемый */
	@Comment("Возраст вычисляемый")
	public String getAge() {return age;}

	/** Возраст вычисляемый */
	private String age;
	/** К оплате не принято */
	@Comment("К оплате не принято")
	@Persist
	public Boolean getNotPaid() {return notPaid;}

	/** К оплате не принято */
	private Boolean notPaid;
	
	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return createDate;}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return editDate;}

	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return createTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return editTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return createUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Дата редактирования услуги */
	@Comment("Дата редактирования услуги")
	@Persist @DateString @DoDateString
	public String getEditDateRender() {return editDateRender;}

	/** Время редактирование услуги */
	@Comment("Время редактирование услуги")
	@Persist @TimeString @DoTimeString
	public String getEditTimeRender() {return editTimeRender;}

	/** Пользователь редактировавший услуги */
	@Comment("Пользователь редактировавший услуги")
	@Persist
	public String getEditUsernameRender() {return editUsernameRender;}

	/** Пользователь редактировавший услуги */
	private String editUsernameRender;
	/** Время редактирование услуги */
	private String editTimeRender;
	/** Дата редактирования услуги */
	private String editDateRender;
	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private String editTime;
	/** Время создания */
	private String createTime;
	/** Дата редактирования */
	private String editDate;
	/** Дата создания */
	private String createDate;
	
	
	/** Основной диагноз установлен впервые */
	@Comment("Основной диагноз установлен впервые")
	@Persist
	public Boolean getIsDiagnosisSetFirstTime(){return isDiagnosisSetFirstTime;}
	private Boolean isDiagnosisSetFirstTime ;

	/** Дата следующего визита */
	@Comment("Дата следующего визита")
	@Persist
	@DateString @DoDateString
	public String getNextDispDate() {return nextDispDate;}
	/** Дата следующего визита */
	private String nextDispDate ;
}
