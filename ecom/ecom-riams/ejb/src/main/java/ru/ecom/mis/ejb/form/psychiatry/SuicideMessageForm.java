package ru.ecom.mis.ejb.form.psychiatry;


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
public class SuicideMessageForm extends IdEntityForm {
	/** Дата суицида */
	@Comment("Дата суицида")
	@Persist @DoDateString @DateString @Required
	public String getSuicideDate() {return theSuicideDate;}
	public void setSuicideDate(String aSuicideDate) {theSuicideDate = aSuicideDate;}

	/** Дата заполения извещения */
	@Comment("Дата заполения извещения")
	@Persist @DoDateString @DateString @Required
	public String getRegOtherLpuDate() {return theRegOtherLpuDate;}
	public void setRegOtherLpuDate(String aRegOtherLpuDate) {theRegOtherLpuDate = aRegOtherLpuDate;}

	/** Время заполнения извещения */
	@Comment("Время заполнения извещения")
	@Persist @DoTimeString @TimeString
	public String getRegOtherLpuTime() {return theRegOtherLpuTime;}
	public void setRegOtherLpuTime(String aRegOtherLpuTime) {theRegOtherLpuTime = aRegOtherLpuTime;}

	/** Дата регистрации */
	@Comment("Дата регистрации")
	@Persist @DoDateString @DateString @Required
	public String getRegDate() {return theRegDate;}
	public void setRegDate(String aRegDate) {theRegDate = aRegDate;}

	/** Время регистрации */
	@Comment("Время регистрации")
	@Persist @DoTimeString @TimeString
	public String getRegTime() {return theRegTime;}
	public void setRegTime(String aRegTime) {theRegTime = aRegTime;}

	/** Извещение заполнено в ЛПУ */
	@Comment("Извещение заполнено в ЛПУ")
	@Persist @Required
	public Long getRegOtherLpu() {return theRegOtherLpu;}
	public void setRegOtherLpu(Long aRegOtherLpu) {theRegOtherLpu = aRegOtherLpu;}

	/** Извещение заполнено в ЛПУ */
	private Long theRegOtherLpu;
	/** Время регистрации */
	private String theRegTime;
	/** Дата регистрации */
	private String theRegDate;
	/** Время заполнения извещения */
	private String theRegOtherLpuTime;
	/** Дата заполения извещения */
	private String theRegOtherLpuDate;
	/** Дата суицида */
	private String theSuicideDate;
	
	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {
		return theCreateDate;
	}

	public void setCreateDate(String aCreateDate) {
		theCreateDate = aCreateDate;
	}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist @DoDateString @DateString
	public String getEditDate() {
		return theEditDate;
	}

	public void setEditDate(String aEditDate) {
		theEditDate = aEditDate;
	}

	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {
		return theCreateUsername;
	}

	public void setCreateUsername(String aCreateUsername) {
		theCreateUsername = aCreateUsername;
	}

	/** Пользователь, последний редактировавший запись */
	@Comment("Пользователь, последний редактировавший запись")
	@Persist
	public String getEditUsername() {
		return theEditUsername;
	}

	public void setEditUsername(String aEditUsername) {
		theEditUsername = aEditUsername;
	}

	/** Время создания */
	@Comment("Время создания")
	@Persist @DoTimeString @TimeString
	public String getCreateTime() {
		return theCreateTime;
	}

	public void setCreateTime(String aCreateTime) {
		theCreateTime = aCreateTime;
	}

	/** Время редактрования */
	@Comment("Время редактрования")
	@Persist @DoTimeString @TimeString
	public String getEditTime() {
		return theEditTime;
	}

	public void setEditTime(String aEditTime) {
		theEditTime = aEditTime;
	}

	/** Время редактрования */
	private String theEditTime;

	/** Время создания */
	private String theCreateTime;

	/** Пользователь, последний редактировавший запись */
	private String theEditUsername;

	/** Пользователь, создавший запись */
	private String theCreateUsername;

	/** Дата редактирования */
	private String theEditDate;

	/** Дата создания */
	private String theCreateDate;
	
	/** Представитель */
	@Comment("Представитель")
	@Persist
	public String getKinsman() {
		return theKinsman;
	}

	public void setKinsman(String aKinsman) {
		theKinsman = aKinsman;
	}

	/** Представитель */
	private String theKinsman;
	
	/** Телефон */
	@Comment("Телефон")
	@Persist
	public String getPhone() {
		return thePhone;
	}

	public void setPhone(String aPhone) {
		thePhone = aPhone;
	}

	/** Телефон */
	private String thePhone;
	
	/** Пациент */
	@Comment("Пациент")
	@Persist @Required
	public Long getPatient() {
		return thePatient;
	}

	public void setPatient(Long aPatient) {
		thePatient = aPatient;
	}

	/** Пациент */
	private Long thePatient;
	
	/** Вид суицида */
	@Comment("Вид суицида")
	@Persist @Required
	public Long getType() {
		return theType;
	}

	public void setType(Long aType) {
		theType = aType;
	}

	/** Вид суицида */
	private Long theType;
	
	/** Другой вид суицида */
	@Comment("Другой вид суицида")
	@Persist
	public String getOtherType() {
		return theOtherType;
	}

	public void setOtherType(String aOtherType) {
		theOtherType = aOtherType;
	}

	/** Другой вид суицида */
	private String theOtherType;
	
	/** Место */
	@Comment("Место")
	@Persist @Required
	public Long getPlace() {
		return thePlace;
	}

	public void setPlace(Long aPlace) {
		thePlace = aPlace;
	}
	
	/** Другое место суицида */
	@Comment("Другое место суицида")
	@Persist
	public String getOtherPlace() {
		return theOtherPlace;
	}

	public void setOtherPlace(String aOtherPlace) {
		theOtherPlace = aOtherPlace;
	}

	/** Другое место суицида */
	private String theOtherPlace;

	/** Место */
	private Long thePlace;
	
	/** Присутствовали др. люди при суициде */
	@Comment("Присутствовали др. люди при суициде")
	@Persist @Required
	public Long getOtherPeople() {
		return theOtherPeople;
	}

	public void setOtherPeople(Long aOtherPeople) {
		theOtherPeople = aOtherPeople;
	}

	/** Присутствовали др. люди при суициде */
	private Long theOtherPeople;
	
	/** Опьянение */
	@Comment("Опьянение")
	@Persist @Required
	public Long getIntoxication() {
		return theIntoxication;
	}

	public void setIntoxication(Long aIntoxication) {
		theIntoxication = aIntoxication;
	}

	/** Опьянение */
	private Long theIntoxication;
	
	/** Направлен */
	@Comment("Направлен")
	@Persist
	public Long getOrderLpu() {
		return theOrderLpu;
	}

	public void setOrderLpu(Long aOrderLpu) {
		theOrderLpu = aOrderLpu;
	}

	/** Направлен */
	private Long theOrderLpu;
	
	/** Доставлен */
	@Comment("Доставлен")
	@Persist
	public Long getPostedLpu() {
		return thePostedLpu;
	}

	public void setPostedLpu(Long aPostedLpu) {
		thePostedLpu = aPostedLpu;
	}

	/** Доставлен */
	private Long thePostedLpu;
	/** Завершен */
	@Comment("Завершен")
	@Persist
	public Boolean getIsFinished() {
		return theIsFinished;
	}

	public void setIsFinished(Boolean aIsFinished) {
		theIsFinished = aIsFinished;
	}

	/** Завершен */
	private Boolean theIsFinished;

	/** Социальный статус */
	@Comment("Социальный статус")
	@Persist
	public Long getSocialStatus() {return theSocialStatus;}
	public void setSocialStatus(Long aSocialStatus) {theSocialStatus = aSocialStatus;}

	/** Социальный статус */
	private Long theSocialStatus;
	
	/** Мед.помощь оказано СМП */
	@Comment("Мед.помощь оказано СМП")
	@Persist
	public Long getHelpSMP() {return theHelpSMP;}
	public void setHelpSMP(Long aHelpSMP) {theHelpSMP = aHelpSMP;}

	/** Мед.помощь оказано СМП */
	private Long theHelpSMP;

}
