package ru.ecom.mis.ejb.form.patient;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.LpuAttachedByDepartment;
import ru.nuzmsh.commons.formpersistence.annotation.*;
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
@Setter
public class LpuAttachedByDepartmentForm extends IdEntityForm {

	/** Не актуально */
	@Comment("Не актуально")
	@Persist
	public Boolean getNoActuality() {return noActuality;}
	/** Не актуально */
	private Boolean noActuality;
	
	/** Результат последней проверки ФОМС */
	@Comment("Результат последней проверки ФОМС")
	@Persist
	public String getCheckResult() {return checkResult;}
	/** Результат последней проверки ФОМС */
	private String checkResult;
	
	/** ЛПУ открепления */
	@Comment("ЛПУ открепления")
	@Persist
	public String getLpuTo() {
		return lpuTo;
	}
	/** ЛПУ открепления */
	private String lpuTo;
	
	/** Полное имя ЛПУ */
	@Comment("Полное имя ЛПУ")
	@Persist
	public String getLpuFullname() {
		return lpuFullname;
	}

	/** ПОлное имя ЛПУ */
	private String lpuFullname;
	
	public String getInfo() {
		return lpuFullname ;
	}
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist @Required
	public Long getLpu() {
		return lpu;
	}

	/** Участок */
	@Comment("Участок")
	@Persist @Required
	public Long getArea() {return area;}

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return patient;}

	/** Пациент */
	private Long patient;
	/** Участок */
	private Long area;
	/** ЛПУ */
	private Long lpu;
	
	/** Прикреплен с */
	@Comment("Прикреплен с")
	@Persist @Required @DateString @DoDateString 
	public String getDateFrom() {return dateFrom;}

	/** Откреплен с */
	@Comment("Откреплен с")
	@Persist @DateString @DoDateString
	public String getDateTo() {return dateTo;}

	/** Откреплен с */
	private String dateTo;
	/** Прикреплен с */
	private String dateFrom;
	
	/** Тип прикрепления */
	@Comment("Тип прикрепления")
	@Persist @Required
	public Long getAttachedType() {return attachedType;}

	/** Тип прикрепления */
	private Long attachedType;
	
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
	
	/** Дата импорта результатов ФОМС */
	@Comment("Дата импорта результатов ФОМС")
	@Persist
	public String getDefectPeriod() {return defectPeriod;}
	/** Дата импорта результатов ФОМС */
	private String defectPeriod;
	
	/** Текст дефекта */
	@Comment("Текст дефекта")
	@Persist
	public String getDefectText() {return defectText;}
	/** Текст дефекта */
	private String defectText;

	
	/** Страховая компания */
	@Comment("Страховая компания")
	@Persist @Required
	public Long getCompany() {return company;}

	/** Страховая компания */
	private Long company;
	
	/** Подача производилась по неактуальному полису */
	@Comment("Подача производилась по неактуальному полису")
	@Persist
	public Boolean getNoActualPolicy() {return noActualPolicy;}

	/** Подача производилась по неактуальному полису */
	private Boolean noActualPolicy;

	/** Новый адрес */
	@Comment("Новый адрес")
	@Persist
	public Boolean getNewAddress() {return newAddress;}
	/** Новый адрес */
	private Boolean newAddress;
	
	/** Дата последней выгрузки в ФОМС */
	@Comment("Дата последней выгрузки в ФОМС")
	@Persist @DateString @DoDateString
	public String getExportDate() {return exportDate;}
	/** Дата последней выгрузки в ФОМС */
	private String exportDate;
}