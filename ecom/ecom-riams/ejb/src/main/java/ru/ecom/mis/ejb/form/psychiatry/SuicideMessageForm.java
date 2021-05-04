package ru.ecom.mis.ejb.form.psychiatry;


import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.psychiatry.SuicideMessage;
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
@EntityFormPersistance(clazz = SuicideMessage.class)
@Comment("Суицид")
@WebTrail(comment = "Суицид", nameProperties= "id",list="entityParentList-psych_suicideMessage.do",listComment="список по пациенту", view="entityParentView-psych_suicideMessage.do")
@Parent(property="patient", parentForm=PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Psychiatry/CareCard/SuicideMessage")
@Setter
public class SuicideMessageForm extends IdEntityForm {
	/** Дата суицида */
	@Comment("Дата суицида")
	@Persist @DoDateString @DateString @Required
	public String getSuicideDate() {return suicideDate;}

	/** Дата заполения извещения */
	@Comment("Дата заполения извещения")
	@Persist @DoDateString @DateString @Required
	public String getRegOtherLpuDate() {return regOtherLpuDate;}

	/** Время заполнения извещения */
	@Comment("Время заполнения извещения")
	@Persist @DoTimeString @TimeString
	public String getRegOtherLpuTime() {return regOtherLpuTime;}

	/** Дата регистрации */
	@Comment("Дата регистрации")
	@Persist @DoDateString @DateString @Required
	public String getRegDate() {return regDate;}

	/** Время регистрации */
	@Comment("Время регистрации")
	@Persist @DoTimeString @TimeString
	public String getRegTime() {return regTime;}

	/** Извещение заполнено в ЛПУ */
	@Comment("Извещение заполнено в ЛПУ")
	@Persist @Required
	public Long getRegOtherLpu() {return regOtherLpu;}

	/** Извещение заполнено в ЛПУ */
	private Long regOtherLpu;
	/** Время регистрации */
	private String regTime;
	/** Дата регистрации */
	private String regDate;
	/** Время заполнения извещения */
	private String regOtherLpuTime;
	/** Дата заполения извещения */
	private String regOtherLpuDate;
	/** Дата суицида */
	private String suicideDate;
	
	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {
		return createDate;
	}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist @DoDateString @DateString
	public String getEditDate() {
		return editDate;
	}

	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {
		return createUsername;
	}

	/** Пользователь, последний редактировавший запись */
	@Comment("Пользователь, последний редактировавший запись")
	@Persist
	public String getEditUsername() {
		return editUsername;
	}

	/** Время создания */
	@Comment("Время создания")
	@Persist @DoTimeString @TimeString
	public String getCreateTime() {
		return createTime;
	}

	/** Время редактрования */
	@Comment("Время редактрования")
	@Persist @DoTimeString @TimeString
	public String getEditTime() {
		return editTime;
	}

	/** Время редактрования */
	private String editTime;

	/** Время создания */
	private String createTime;

	/** Пользователь, последний редактировавший запись */
	private String editUsername;

	/** Пользователь, создавший запись */
	private String createUsername;

	/** Дата редактирования */
	private String editDate;

	/** Дата создания */
	private String createDate;
	
	/** Представитель */
	@Comment("Представитель")
	@Persist
	public String getKinsman() {
		return kinsman;
	}

	/** Представитель */
	private String kinsman;
	
	/** Телефон */
	@Comment("Телефон")
	@Persist
	public String getPhone() {
		return phone;
	}

	/** Телефон */
	private String phone;
	
	/** Пациент */
	@Comment("Пациент")
	@Persist @Required
	public Long getPatient() {
		return patient;
	}

	/** Пациент */
	private Long patient;
	
	/** Вид суицида */
	@Comment("Вид суицида")
	@Persist @Required
	public Long getType() {
		return type;
	}

	/** Вид суицида */
	private Long type;
	
	/** Другой вид суицида */
	@Comment("Другой вид суицида")
	@Persist
	public String getOtherType() {
		return otherType;
	}

	/** Другой вид суицида */
	private String otherType;
	
	/** Место */
	@Comment("Место")
	@Persist @Required
	public Long getPlace() {
		return place;
	}

	/** Другое место суицида */
	@Comment("Другое место суицида")
	@Persist
	public String getOtherPlace() {
		return otherPlace;
	}

	/** Другое место суицида */
	private String otherPlace;

	/** Место */
	private Long place;
	
	/** Присутствовали др. люди при суициде */
	@Comment("Присутствовали др. люди при суициде")
	@Persist @Required
	public Long getOtherPeople() {
		return otherPeople;
	}

	/** Присутствовали др. люди при суициде */
	private Long otherPeople;
	
	/** Опьянение */
	@Comment("Опьянение")
	@Persist @Required
	public Long getIntoxication() {
		return intoxication;
	}

	/** Опьянение */
	private Long intoxication;
	
	/** Направлен */
	@Comment("Направлен")
	@Persist
	public Long getOrderLpu() {
		return orderLpu;
	}

	/** Направлен */
	private Long orderLpu;
	
	/** Доставлен */
	@Comment("Доставлен")
	@Persist
	public Long getPostedLpu() {
		return postedLpu;
	}

	/** Доставлен */
	private Long postedLpu;
	/** Завершен */
	@Comment("Завершен")
	@Persist
	public Boolean getIsFinished() {
		return isFinished;
	}

	/** Завершен */
	private Boolean isFinished;

	/** Социальный статус */
	@Comment("Социальный статус")
	@Persist
	public Long getSocialStatus() {return socialStatus;}

	/** Социальный статус */
	private Long socialStatus;
	
	/** Мед.помощь оказано СМП */
	@Comment("Мед.помощь оказано СМП")
	@Persist
	public Long getHelpSMP() {return helpSMP;}

	/** Мед.помощь оказано СМП */
	private Long helpSMP;

}
