package ru.ecom.mis.ejb.form.extdisp;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispCard;
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
@Comment("Карта осмотра")
@WebTrail(comment = "Карта осмотра"
, nameProperties= "id", list="entityParentList-extDispCom_card.do", view="entityParentView-extDispCom_card.do")
@Parent(property="patient", parentForm=PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card")
/*@AViewInterceptors(
        @AEntityFormInterceptor(ExtDispCardViewInterceptor.class)
)*/
@Setter
public class ExtDispComCardForm extends IdEntityForm{
	
	/** Источник финансирования */
	@Comment("Источник финансирования")
	@Persist
	public Long  getServiceStream() {return serviceStream;}
	/** Источник финансирования */
	private Long  serviceStream;
	
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

	/** Социальная группа */
	@Comment("Социальная группа")
	
	public Long getSocialGroup() {return socialGroup;}
	/** Социальная группа */
	private Long socialGroup;
	
	/** Тип дополнительной диспансеризации */
	@Comment("Тип дополнительной диспансеризации")
	@Persist @Required
	public Long getDispType() {return dispType;}
	/** Тип дополнительной диспансеризации */
	private Long dispType;
	
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
	
	public Long getHealthGroup() {return healthGroup;}
	/** Группа здоровья дополнительной диспансеризации */
	private Long healthGroup;
	
	/** МКБ основного диагноза */
	@Comment("МКБ основного диагноза")
	@Persist
	public Long getIdcMain() {return idcMain;}
	/** МКБ основного диагноза */
	private Long idcMain;
	
	
	/** Возрастная категория */
	@Comment("Возрастная категория")
	@Persist
	public Long getAgeGroup() {return ageGroup;}

	/** Возрастная категория */
	private Long ageGroup;
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist @Required
	public Long getWorkFunction() {return workFunction;}
	/** Рабочая функция */
	private Long workFunction;
	
	/** Возраст вычисляемый */
	@Comment("Возраст вычисляемый")
	public String getAge() {return age;}

	/** Возраст вычисляемый */
	private String age;

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
}
