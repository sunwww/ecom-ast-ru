package ru.ecom.mis.ejb.form.equipment;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.equipment.MedicalEquipment;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz= MedicalEquipment.class)
@Comment("Медицинское оборудование")
@WebTrail(comment = "Медицинское оборудование", nameProperties= "nameTypeEquip", view="entityView-mis_medicalEquipment.do")
@Parent(property="lpu", parentForm= MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Equipment/Equipment")
@Setter
public class MedicalEquipmentForm extends EquipmentForm {

	/** Количество */
	@Comment("Количество")
	@Persist
	public Float getAmount() {return amount;}
	/** Количество */
	private Float amount;

	/** ИД в системе Парус */
	@Comment("ИД в системе Парус")
	@Persist
	public String getParusCode() {return parusCode;}
	/** ИД в системе Парус */
	private String parusCode;
	
	/** Группа бухгалтерии */
	@Comment("Группа бухгалтерии")
	@Persist
	public String getGroupName() {return groupName;}
	/** Группа бухгалтерии */
	private String groupName;
	
	/** ОКОФ-текст */
	@Comment("ОКОФ-текст")
	@Persist
	public String getOkofText() {return okofText;}
	/** ОКОФ-текст */
	private String okofText;

	/** Износ */
	@Comment("Износ")
	@Persist
	public String getWearout() {return wearout;}
	/** Износ */
	private String wearout;
	
	/** Остаточная стоимость */
	@Comment("Остаточная стоимость")
	@Persist
	public String getResidualValue() {return residualValue;}
	/** Остаточная стоимость */
	private String residualValue;
	
	/** Начальный износ */
	@Comment("Начальный износ")
	@Persist
	public String getStartWearout() {return startWearout;}
	/** Начальный износ */
	private String startWearout;
	
	
	/** ОКОФ */
	@Comment("ОКОФ")
	@Persist
	public Long getOkof() {return okof;}
	/** ОКОФ */
	private Long okof;

	/** Инвентарный номер */
	@Comment("Инвентарный номер")
	@Persist
	public String getInventoryNumber() {return inventoryNumber;}
	/** Инвентарный номер */
	private String inventoryNumber;

	/** В рамках какой програмы (мероприятия) было поставлено */
	@Comment("В рамках какой програмы (мероприятия) было поставлено")
	@Persist
	public String getProjectName() {return projectName;}
	/** В рамках какой програмы (мероприятия) было поставлено */
	private String projectName;
	
	/** Источник финансирования */
	@Comment("Источник финансирования")
	@Persist
	public Long getFundingStream() {return fundingStream;}
	/** Источник финансирования */
	private Long fundingStream;
	
	/** Цена */
	@Comment("Цена")
	@Persist
	public String getPrice() {return price;}
	/** Цена */
	private String price;
	
	/** Дата ввода в эксплуатацию */
	@Comment("Дата ввода в эксплуатацию")
	@DateString @DoDateString
	public String getStartDate() {return startDate;}
	/** Дата ввода в эксплуатацию */
	private String startDate;
	
}
