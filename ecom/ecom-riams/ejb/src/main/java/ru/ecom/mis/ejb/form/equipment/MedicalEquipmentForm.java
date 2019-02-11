package ru.ecom.mis.ejb.form.equipment;

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
public class MedicalEquipmentForm extends EquipmentForm {

	/** Количество */
	@Comment("Количество")
	@Persist
	public Float getAmount() {return theAmount;}
	public void setAmount(Float aAmount) {theAmount = aAmount;}
	/** Количество */
	private Float theAmount;

	/** ИД в системе Парус */
	@Comment("ИД в системе Парус")
	@Persist
	public String getParusCode() {return theParusCode;}
	public void setParusCode(String aParusCode) {theParusCode = aParusCode;}
	/** ИД в системе Парус */
	private String theParusCode;
	
	/** Группа бухгалтерии */
	@Comment("Группа бухгалтерии")
	@Persist
	public String getGroupName() {return theGroupName;}
	public void setGroupName(String aGroupName) {theGroupName = aGroupName;}
	/** Группа бухгалтерии */
	private String theGroupName;
	
	/** ОКОФ-текст */
	@Comment("ОКОФ-текст")
	@Persist
	public String getOkofText() {return theOkofText;}
	public void setOkofText(String aOkofText) {theOkofText = aOkofText;}
	/** ОКОФ-текст */
	private String theOkofText;

	/** Износ */
	@Comment("Износ")
	@Persist
	public String getWearout() {return theWearout;}
	public void setWearout(String aWearout) {theWearout = aWearout;}
	/** Износ */
	private String theWearout;
	
	/** Остаточная стоимость */
	@Comment("Остаточная стоимость")
	@Persist
	public String getResidualValue() {return theResidualValue;}
	public void setResidualValue(String aResidualValue) {theResidualValue = aResidualValue;}
	/** Остаточная стоимость */
	private String theResidualValue;
	
	/** Начальный износ */
	@Comment("Начальный износ")
	@Persist
	public String getStartWearout() {return theStartWearout;}
	public void setStartWearout(String aStartWearout) {theStartWearout = aStartWearout;}
	/** Начальный износ */
	private String theStartWearout;
	
	
	/** ОКОФ */
	@Comment("ОКОФ")
	@Persist
	public Long getOkof() {return theOkof;}
	public void setOkof(Long aOkof) {theOkof = aOkof;}
	/** ОКОФ */
	private Long theOkof;

	/** Инвентарный номер */
	@Comment("Инвентарный номер")
	@Persist
	public String getInventoryNumber() {return theInventoryNumber;}
	public void setInventoryNumber(String aInventoryNumber) {theInventoryNumber = aInventoryNumber;}
	/** Инвентарный номер */
	private String theInventoryNumber;

	/** В рамках какой програмы (мероприятия) было поставлено */
	@Comment("В рамках какой програмы (мероприятия) было поставлено")
	@Persist
	public String getProjectName() {return theProjectName;}
	public void setProjectName(String aProjectName) {theProjectName = aProjectName;}
	/** В рамках какой програмы (мероприятия) было поставлено */
	private String theProjectName;
	
	/** Источник финансирования */
	@Comment("Источник финансирования")
	@Persist
	public Long getFundingStream() {return theFundingStream;}
	public void setFundingStream(Long aFundingStream) {theFundingStream = aFundingStream;}
	/** Источник финансирования */
	private Long theFundingStream;
	
	/** Цена */
	@Comment("Цена")
	@Persist
	public String getPrice() {return thePrice;}
	public void setPrice(String aPrice) {thePrice = aPrice;}
	/** Цена */
	private String thePrice;
	
	/** Дата ввода в эксплуатацию */
	@Comment("Дата ввода в эксплуатацию")
	@DateString @DoDateString
	public String getStartDate() {return theStartDate;}
	public void setStartDate(String aStartDate) {theStartDate = aStartDate;}
	/** Дата ввода в эксплуатацию */
	private String theStartDate;
	
}
