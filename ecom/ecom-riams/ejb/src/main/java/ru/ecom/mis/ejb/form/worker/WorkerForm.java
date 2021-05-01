package ru.ecom.mis.ejb.form.worker;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
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
 * Сотрудник
 */
@EntityForm
@EntityFormPersistance(clazz= Worker.class)
@Comment("Сотрудник")
@WebTrail(comment = "Сотрудник", nameProperties= "workerInfo", view="entityView-mis_worker.do", list="entityParentList-mis_worker.do")
@Parent(property="lpu", parentForm= MisLpuForm.class,orderBy="person.lastname,person.firstname,person.middlename")
@EntityFormSecurityPrefix("/Policy/Mis/Worker/Worker")
@Setter
public class WorkerForm extends IdEntityForm {
    

	/** Персона */
	@Comment("Персона")
	@Persist @Required
	public Long getPerson() {
		return person;
	}

	/** Персона */
	private Long person;

    
	
	/** ЛПУ */
    @Comment("ЛПУ")
	@Persist @Required
    public Long getLpu() { return lpu ; }
    /** ЛПУ */
    private Long lpu ;



    /** Информаия о сотруднике */
	@Comment("Информаия о сотруднике")
	@Persist
	public String getWorkerInfo() {
		return workerInfo;
	}

	/** Информаия о сотруднике */
	private String workerInfo;





    /** ФИО и должность специалиста */
	@Comment("ФИО и должность специалиста")
	@Persist
	public String getDoctorInfo() {
		return doctorInfo;
	}

	/** ФИО и должность специалиста */
	private String doctorInfo;
    



    /** Должность */
	@Comment("Должность")
	@Persist
	public String getPost() {
		return post;
	}

	/** Должность */
	private String post;


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