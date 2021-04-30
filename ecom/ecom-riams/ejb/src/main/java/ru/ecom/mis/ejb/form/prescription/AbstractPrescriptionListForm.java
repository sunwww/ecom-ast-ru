package ru.ecom.mis.ejb.form.prescription;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.prescription.AbstractPrescriptionList;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
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
@EntityFormPersistance(clazz = AbstractPrescriptionList.class)
@Comment("Лист назначений")
@WebTrail(comment = "Лист назначений", nameProperties= "id", view="entitySubclassView-pres_list.do")
@Parent(property = "medCase", parentForm = MedCaseForm.class)
@Subclasses({ru.ecom.mis.ejb.form.prescription.PrescriptListForm.class, ru.ecom.mis.ejb.form.prescription.template.PrescriptListForm.class})
@EntityFormSecurityPrefix("/Policy/Mis/Prescription")
@Setter
public class AbstractPrescriptionListForm extends IdEntityForm {
	/** Название шаблона */
	@Comment("Название шаблона")
	@Persist 
	public String getName() {return name;}

	/** Комментарии */
	@Comment("Комментарии")
	@Persist
	public String getComments() {return comments;}

	/** Владелец */
	@Comment("Владелец")
	@Persist 
	public Long getOwner() {return owner;}

	/** Владелец (инфо) */
	@Comment("Владелец (инфо)")
	@Persist
	public String getOwnerInfo() {return ownerInfo;}

	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@Persist
	public Long getMedCase() {return medCase;	}

	/** Тип назначения */
	@Comment("Тип назначения")
	@Persist @Required
	public Long getPrescriptType() {return prescriptType;}

	/** Тип назначений инфо */
	@Comment("Тип назначений инфо")
	@Persist
	public String getPrescriptTypeInfo() {return prescriptTypeInfo;}

	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist @Required
	public Long getWorkFunction() {return workFunction;}

	/** Рабочая функция инфо */
	@Comment("Рабочая функция инфо")
	@Persist
	public String getWorkFunctionInfo() {return workFunctionInfo;}

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

	/** Список услуг по лаборатории */
	@Comment("Список услуг по лаборатории")
	public String getLabList() {return labList;}

	/** Список услуг по лаборатории */
	private String labList;
	
	/** Список лаб. назначений (полный, нужен только для edit.jsp) */
	@Comment("Список лаб. назначений (полный, нужен только для edit.jsp)")
	public String getAllDrugList() {
		return allDrugList;
	}

	/** Список лаб. назначений (полный, нужен только для edit.jsp) */
	private String allDrugList;
	
	/** Список лекарственных назначений */
	@Comment("Список лекарственных назначений")
	public String getDrugList() {
		return drugList;
	}

	/** Список лекарственных назначений */
	private String drugList;
	/** Лабораторные исследования */
	@Comment("Лабораторные исследования")
	public String getLabServicies() {
		return labServicies;
	}

	/** Лабораторные исследования */
	private String labServicies;
	
	/** Дата по лаб. исследованию */
	@Comment("Дата по лаб. исследованию")
	@DateString @DoDateString
	public String getLabDate() {return labDate;}
	/** Дата по лаб. исследованию */
	private String labDate;

	/** Функциональные исследования */
	@Comment("Функциональные исследования")
	public String getFuncServicies() {
		return funcServicies;
	}

	/** Кабинет для лабораторных исследования*/
	@Comment("Кабинет для лабораторных исследования")
	public String getLabCabinet() {
		return labCabinet;
	}

	/** Кабинет для лабораторных исследования */
	private String labCabinet;
	/** Функциональные исследования */
	private String funcServicies;
	
	/** Дата функционального исследования */
	@Comment("Дата функционального исследования")
	@DateString @DoDateString
	public String getFuncDate() {
		return funcDate;
	}

	/** Дата функционального исследования */
	private String funcDate;
	/** Кабинет для функ. исследования */
	@Comment("Кабинет для функ. исследования")
	public String getFuncCabinet() {
		return funcCabinet;
	}

	/** Кабинет для функ. исследования */
	private String funcCabinet;
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
	/** Рабочая функция инфо */
	private String workFunctionInfo;
	/** Рабочая функция */
	private Long workFunction;
	/** Тип назначений инфо */
	private String prescriptTypeInfo;
	/** Тип назначения */
	private Long prescriptType;
	/** Случай медицинского обслуживания */
	private Long medCase;	
	/** Владелец (инфо) */
	private String ownerInfo;
	/** Владелец */
	private Long owner;
	/** Комментарии */
	private String comments;
	/** Название шаблона */
	private String name;

}
