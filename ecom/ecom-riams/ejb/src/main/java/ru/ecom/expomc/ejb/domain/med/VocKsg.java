package ru.ecom.expomc.ejb.domain.med;

import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *  КСГ 
 */
@Entity
@Comment("КСГ")
@Table(schema="SQLUser")
public class VocKsg extends VocIdCodeName {

	/** Группа КСГ */
	@Comment("Группа КСГ")
	@OneToOne
	public VocKsgGroup getGroup() {return theGroup;}
	public void setGroup(VocKsgGroup aGroup) {theGroup = aGroup;}
	private VocKsgGroup theGroup ;

	/** Год КСГ */
	@Comment("Год КСГ")
	public Integer getYear() {return theYear;}
	public void setYear(Integer aYear) {theYear = aYear;}
	private Integer theYear ;

	/** Сверхдлительный КСГ (45 дней)	*/
	@Comment("Длительный срок лечения КСГ")
	public Boolean getLongKsg() {return theLongKsg;}
	public void setLongKsg(Boolean aLongKsg) {theLongKsg = aLongKsg;}
	private Boolean theLongKsg ;

	/** Является операцией */
	@Comment("Является операцией")
	public Boolean getIsOperation() {return theIsOperation;}
	public void setIsOperation(Boolean aIsOperation) {theIsOperation = aIsOperation;}
	private Boolean theIsOperation ;

	/** Оплачивать в полном объеме */
	@Comment("Оплачивать в полном объеме")
	public Boolean getIsFullPayment() {return theIsFullPayment;}
	public void setIsFullPayment(Boolean aIsFullPayment) {theIsFullPayment = aIsFullPayment;}
	private Boolean theIsFullPayment ;

	/** Коэффициент затрат */
	@Comment("Коэффициент затрат")
	public Double getKZ() {return theKZ;}
	public void setKZ(Double aKZ) {theKZ = aKZ;}
	private Double theKZ ;

	/** Профиль помощи */
	@Comment("Профиль помощи")
	public String getProfile() {return theProfile;}
	public void setProfile(String aProfile) {theProfile = aProfile;}
	private String theProfile ;

	/** Тип коек */
	@Comment("Тип коек")
	@OneToOne
	public VocBedSubType getBedSubType() {return theBedSubType;}
	public void setBedSubType(VocBedSubType aBedSubType) {theBedSubType = aBedSubType;}
	private VocBedSubType theBedSubType ;

	/** Не учитывать КУСмо */
	@Comment("Не учитывать КУСмо")
	public Boolean getDoNotUseCusmo() {return theDoNotUseCusmo;}
	public void setDoNotUseCusmo(Boolean aDoNotUseCusmo) {theDoNotUseCusmo = aDoNotUseCusmo;}
	private Boolean theDoNotUseCusmo = false;

	/** Covid-19 КСГ */
	@Comment("Covid-19 КСГ")
	public Boolean getIsCovid19() {return theIsCovid19;}
	public void setIsCovid19(Boolean aIsCovid19) {theIsCovid19 = aIsCovid19;}
	private Boolean theIsCovid19 ;


}
