package ru.ecom.mis.ejb.domain.equipment;

import ru.ecom.mis.ejb.domain.equipment.voc.VocOKOF;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author  vtsybulin
 */
@Entity
@Comment("Медицинское оборудование")
public class MedicalEquipment extends Equipment {
	
	/** Количество */
	@Comment("Количество")
	public Float getAmount() {return theAmount;}
	public void setAmount(Float aAmount) {theAmount = aAmount;}
	private Float theAmount;

	/** ИД в системе Парус */
	@Comment("ИД в системе Парус")
	public String getParusCode() {return theParusCode;}
	public void setParusCode(String aParusCode) {theParusCode = aParusCode;}
	private String theParusCode;

	/** Группа бухгалтерии */
	@Comment("Группа бухгалтерии")
	public String getGroupName() {return theGroupName;}
	public void setGroupName(String aGroupName) {theGroupName = aGroupName;}
	private String theGroupName;
	
	/** ОКОФ-текст */
	@Comment("ОКОФ-текст")
	public String getOkofText() {return theOkofText;}
	public void setOkofText(String aOkofText) {theOkofText = aOkofText;}
	private String theOkofText;

	/** Износ */
	@Comment("Износ")
	public BigDecimal getWearout() {return theWearout;}
	public void setWearout(BigDecimal aWearout) {theWearout = aWearout;}
	private BigDecimal theWearout;
	
	/** Остаточная стоимость */
	@Comment("Остаточная стоимость")
	public BigDecimal getResidualValue() {return theResidualValue;}
	public void setResidualValue(BigDecimal aResidualValue) {theResidualValue = aResidualValue;}
	private BigDecimal theResidualValue;
	
	/** Начальный износ */
	@Comment("Начальный износ")
	public BigDecimal getStartWearout() {return theStartWearout;}
	public void setStartWearout(BigDecimal aStartWearout) {theStartWearout = aStartWearout;}
	private BigDecimal theStartWearout;
	
	/** ОКОФ */
	@Comment("ОКОФ")
	@OneToOne
	public VocOKOF getOkof() {return theOkof;}
	public void setOkof(VocOKOF aOkof) {theOkof = aOkof;}
	private VocOKOF theOkof;

	/** Инвентарный номер */
	@Comment("Инвентарный номер")
	public String getInventoryNumber() {return theInventoryNumber;}
	public void setInventoryNumber(String aInventoryNumber) {theInventoryNumber = aInventoryNumber;}
	private String theInventoryNumber;

	/** В рамках какой програмы (мероприятия) было поставлено */
	@Comment("В рамках какой програмы (мероприятия) было поставлено")
	public String getProjectName() {return theProjectName;}
	public void setProjectName(String aProjectName) {theProjectName = aProjectName;}
	private String theProjectName;
	
	/** Источник финансирования */
	@Comment("Источник финансирования")
	@OneToOne
	public VocServiceStream getFundingStream() {return theFundingStream;}
	public void setFundingStream(VocServiceStream aFundingStream) {theFundingStream = aFundingStream;}
	private VocServiceStream theFundingStream;
	
	/** Цена */
	@Comment("Цена")
	public BigDecimal getPrice() {return thePrice;}
	public void setPrice(BigDecimal aPrice) {thePrice = aPrice;}
	private BigDecimal thePrice;
	
	/** Дата ввода в эксплуатацию */
	@Comment("Дата ввода в эксплуатацию")
	public Date getStartDate() {return theStartDate;}
	public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
	private Date theStartDate;
 
}
