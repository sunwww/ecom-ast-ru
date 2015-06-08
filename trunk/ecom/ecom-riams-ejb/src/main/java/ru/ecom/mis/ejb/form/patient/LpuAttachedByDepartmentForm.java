package ru.ecom.mis.ejb.form.patient;

import javax.persistence.OneToOne;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.ecom.mis.ejb.domain.patient.LpuAttachedByDepartment;
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

/**
 * Прикрепление по ведомству
 */
@EntityForm
@EntityFormPersistance(clazz = LpuAttachedByDepartment.class)
@Comment("Специальное прикрепление")
@WebTrail(comment = "Специальное прикрепление", nameProperties = "info", view = "entityView-mis_lpuAttachedByDepartment.do")
@Parent(property = "patient", parentForm =PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Patient/AttachedByDepartment")
public class LpuAttachedByDepartmentForm extends IdEntityForm {

	/** Не актуально */
	@Comment("Не актуально")
	@Persist
	public Boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(Boolean aNoActuality) {theNoActuality = aNoActuality;}
	/** Не актуально */
	private Boolean theNoActuality;
	
	/** Результат последней проверки ФОМС */
	@Comment("Результат последней проверки ФОМС")
	@Persist
	public String getCheckResult() {return theCheckResult;}
	public void setCheckResult(String aCheckResult) {theCheckResult = aCheckResult;}
	/** Результат последней проверки ФОМС */
	private String theCheckResult;
	
	/** ЛПУ открепления */
	@Comment("ЛПУ открепления")
	@Persist
	public String getLpuTo() {
		return theLpuTo;
	}

	public void setLpuTo(String aLpuTo) {
		theLpuTo = aLpuTo;
	}

	/** ЛПУ открепления */
	private String theLpuTo;
	
	/** Полное имя ЛПУ */
	@Comment("Полное имя ЛПУ")
	@Persist
	public String getLpuFullname() {
		return theLpuFullname;
	}

	public void setLpuFullname(String aLpuFullname) {
		theLpuFullname = aLpuFullname;
	}

	/** ПОлное имя ЛПУ */
	private String theLpuFullname;
	
	public String getInfo() {
		return theLpuFullname ;
	}
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist @Required
	public Long getLpu() {
		return theLpu;
	}

	public void setLpu(Long aLpu) {
		theLpu = aLpu;
	}

	/** Участок */
	@Comment("Участок")
	@Persist @Required
	public Long getArea() {return theArea;}
	public void setArea(Long aArea) {theArea = aArea;}

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	/** Пациент */
	private Long thePatient;
	/** Участок */
	private Long theArea;
	/** ЛПУ */
	private Long theLpu;
	
	/** Прикреплен с */
	@Comment("Прикреплен с")
	@Persist @Required @DateString @DoDateString 
	public String getDateFrom() {return theDateFrom;}
	public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}

	/** Откреплен с */
	@Comment("Откреплен с")
	@Persist @DateString @DoDateString
	public String getDateTo() {return theDateTo;}
	public void setDateTo(String aDateTo) {theDateTo = aDateTo;}

	/** Откреплен с */
	private String theDateTo;
	/** Прикреплен с */
	private String theDateFrom;
	
	/** Тип прикрепления */
	@Comment("Тип прикрепления")
	@Persist @Required
	public Long getAttachedType() {return theAttachedType;}
	public void setAttachedType(Long aAttachedType) {theAttachedType = aAttachedType;}

	/** Тип прикрепления */
	private Long theAttachedType;
	
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
	
	/** Период дефекта */
	@Comment("Дата импорта")
	@Persist
	public String getDefectPeriod() {return theDefectPeriod;}
	public void setDefectPeriod(String aDefectPeriod) {theDefectPeriod = aDefectPeriod;}

	/** Текст дефекта */
	@Comment("Текст дефекта")
	@Persist
	public String getDefectText() {return theDefectText;}
	public void setDefectText(String aDefectText) {theDefectText = aDefectText;}

	/** Текст дефекта */
	private String theDefectText;
	/** Дата импорта ( стар. Период дефекта ) */
	private String theDefectPeriod;
	
	/** Страховая компания */
	@Comment("Страховая компания")
	@Persist @Required
	public Long getCompany() {return theCompany;}
	public void setCompany(Long aCompany) {theCompany = aCompany;}

	/** Страховая компания */
	private Long theCompany;
	
	/** Подача производилась по неактуальному полису */
	@Comment("Подача производилась по неактуальному полису")
	@Persist
	public Boolean getNoActualPolicy() {return theNoActualPolicy;}
	public void setNoActualPolicy(Boolean aNoActualPolicy) {theNoActualPolicy = aNoActualPolicy;}

	/** Подача производилась по неактуальному полису */
	private Boolean theNoActualPolicy;
}