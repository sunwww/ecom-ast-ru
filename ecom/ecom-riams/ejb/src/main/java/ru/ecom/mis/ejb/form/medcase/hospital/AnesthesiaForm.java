package ru.ecom.mis.ejb.form.medcase.hospital;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.Anesthesia;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.IntegerString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@Comment("Анестезия")
@EntityForm
@EntityFormPersistance(clazz= Anesthesia.class)
@WebTrail(comment = "Анестезия", nameProperties= "id", view="entityParentView-stac_anesthesia.do")
@Parent(property="surgicalOperation", parentForm= SurgicalOperationForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/SurOper/Anesthesia")
@Setter
public class AnesthesiaForm extends IdEntityForm {

	/** Метод */
	@Comment("Метод")
	@Persist @Required
	public Long getMethod() {
		return method;
	}

	/** Вид анестезии */
	@Comment("Вид анестезии")
	@Persist
	public Long getType() {return type;}

	/** Вид анестезии */
	private Long type;
	/** Метод */
	private Long method;
	
	/** Длительность в минутах */
	@Comment("Длительность в минутах")
	@Persist @Required @IntegerString
	public Integer getDuration() {
		return duration;
	}

	/** Длительность в минутах */
	private Integer duration;
	
	/** Описание */
	@Comment("Описание")
	@Persist 
	public String getDescription() {
		return description;
	}

	/** Описание */
	private String description;
	
	/** Хирургическая операция */
	@Comment("Хирургическая операция")
	@Persist @Required
	public Long getSurgicalOperation() {
		return surgicalOperation;
	}

	/** Хирургическая операция */
	private Long surgicalOperation;
	
	/** Анестезиолог */
	@Comment("Анестезист")
	@Persist @Required
	public Long getAnesthesist() {
		return anesthesist;
	}

	/** Анестезиолог */
	private Long anesthesist;
	
	/** Анестезист инфо */
	@Comment("Анестезист инфо")
	@Persist
	public String getAnesthesistInfo() {
		return anesthesistInfo;
	}

	/** Анестезист инфо */
	private String anesthesistInfo;
	
	/** Метод инфо */
	@Comment("Метод инфо")
	@Persist
	public String getMethodInfo() {
		return methodInfo;
	}

	/** Метод инфо */
	private String methodInfo;
	
	/** Дата создания */
	@Comment("Дата создания")
	@Persist
	public String getCreateDate() {
		return createDate;
	}

	/** Пользователь создавший запись */
	@Comment("Пользователь создавший запись")
	@Persist
	public String getCreateUsername() {
		return createUsername;
	}

	/** Пользователь создавший запись */
	private String createUsername;
	/** Дата создания */
	private String createDate;
	
	/** Мед.услуга */
	@Comment("Мед.услуга")
	@Persist
	public Long getMedService() {
		return medService;
	}

	/** Мед.услуга */
	private Long medService;
	
	/** Время создания */
	@Comment("Время создания")
	@Persist @DoTimeString @TimeString
	public String getCreateTime() {return createTime;}

	/** Время создания */
	private String createTime;

}
