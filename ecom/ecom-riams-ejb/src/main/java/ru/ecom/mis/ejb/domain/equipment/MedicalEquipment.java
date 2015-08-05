package ru.ecom.mis.ejb.domain.equipment;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.equipment.voc.VocCreater;
import ru.ecom.mis.ejb.domain.equipment.voc.VocMarka;
import ru.ecom.mis.ejb.domain.equipment.voc.VocOKOF;
import ru.ecom.mis.ejb.domain.equipment.voc.VocProvider;
import ru.ecom.mis.ejb.domain.equipment.voc.VocTypeEquip;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * @author  vtsybulin
 */
@Entity
@Comment("Медицинское оборудование")
@Table(schema="SQLUser")
public class MedicalEquipment extends Equipment {

	/** Группа бухгалтерии */
	@Comment("Группа бухгалтерии")
	public String getGroupName() {return theGroupName;}
	public void setGroupName(String aGroupName) {theGroupName = aGroupName;}
	/** Группа бухгалтерии */
	private String theGroupName;
	
	/** ОКОФ-текст */
	@Comment("ОКОФ-текст")
	public String getOkofText() {return theOkofText;}
	public void setOkofText(String aOkofText) {theOkofText = aOkofText;}
	/** ОКОФ-текст */
	private String theOkofText;

	/** Износ */
	@Comment("Износ")
	public BigDecimal getWearout() {return theWearout;}
	public void setWearout(BigDecimal aWearout) {theWearout = aWearout;}
	/** Износ */
	private BigDecimal theWearout;
	
	/** Остаточная стоимость */
	@Comment("Остаточная стоимость")
	public BigDecimal getResidualValue() {return theResidualValue;}
	public void setResidualValue(BigDecimal aResidualValue) {theResidualValue = aResidualValue;}
	/** Остаточная стоимость */
	private BigDecimal theResidualValue;
	
	/** Начальный износ */
	@Comment("Начальный износ")
	public BigDecimal getStartWearout() {return theStartWearout;}
	public void setStartWearout(BigDecimal aStartWearout) {theStartWearout = aStartWearout;}
	/** Начальный износ */
	private BigDecimal theStartWearout;
	
	
	/** ОКОФ */
	@Comment("ОКОФ")
	@OneToOne
	public VocOKOF getOkof() {return theOkof;}
	public void setOkof(VocOKOF aOkof) {theOkof = aOkof;}
	/** ОКОФ */
	private VocOKOF theOkof;

	/** Инвентарный номер */
	@Comment("Инвентарный номер")
	public String getInventoryNumber() {return theInventoryNumber;}
	public void setInventoryNumber(String aInventoryNumber) {theInventoryNumber = aInventoryNumber;}
	/** Инвентарный номер */
	private String theInventoryNumber;

	/** В рамках какой програмы (мероприятия) было поставлено */
	@Comment("В рамках какой програмы (мероприятия) было поставлено")
	public String getProjectName() {return theProjectName;}
	public void setProjectName(String aProjectName) {theProjectName = aProjectName;}
	/** В рамках какой програмы (мероприятия) было поставлено */
	private String theProjectName;
	
	/** Источник финансирования */
	@Comment("Источник финансирования")
	@OneToOne
	public VocServiceStream getFundingStream() {return theFundingStream;}
	public void setFundingStream(VocServiceStream aFundingStream) {theFundingStream = aFundingStream;}
	/** Источник финансирования */
	private VocServiceStream theFundingStream;
	
	/** Цена */
	@Comment("Цена")
	public BigDecimal getPrice() {return thePrice;}
	public void setPrice(BigDecimal aPrice) {thePrice = aPrice;}
	/** Цена */
	private BigDecimal thePrice;
	
	/** Дата ввода в эксплуатацию */
	@Comment("Дата ввода в эксплуатацию")
	public Date getStartDate() {return theStartDate;}
	public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
	/** Дата ввода в эксплуатацию */
	private Date theStartDate;
 
}
