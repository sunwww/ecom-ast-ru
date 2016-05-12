package ru.ecom.mis.ejb.form.mortality;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.RowPersist;
import ru.ecom.ejb.services.entityform.annotation.RowPersistMatch;
import ru.ecom.ejb.services.entityform.annotation.RowPersistProperty;
import ru.ecom.mis.ejb.domain.mortality.MortalityReportDate;
import ru.ecom.mis.ejb.domain.mortality.MortalityReportRow;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 * Строка отчета по смертности
 * @author oegorova
 *
 */
@EntityForm
@EntityFormPersistance(clazz= MortalityReportDate.class)
@WebTrail(comment = "Сведения о летальности", nameProperties= "reportDate", view="entityView-mis_mortalityReport.do"
	, list = "entityParentList-mis_mortalityReportDate.do"	
	
)
@Parent(property="lpu", parentForm= MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Report/Mortality")
@Comment("Отчет по смертности")
@RowPersist(
			rowEntityClass = MortalityReportRow.class
		 ,  parentProperty = "mortalityReportDate"
		 , defaultProperty = "amount"	  
)
public class MortalityReportDateForm extends IdEntityForm{

	/** Редакция завершена */
	@Comment("Редакция завершена")
	@Persist
	public boolean getEditComplete() {
		return theEditComplete;
	}

	public void setEditComplete(boolean aEditComplete) {
		theEditComplete = aEditComplete;
	}

	/** Редакция завершена */
	private boolean theEditComplete;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {
		return theLpu;
	}

	public void setLpu(Long aLpu) {
		theLpu = aLpu;
	}

	/** ЛПУ */
	private Long theLpu;
	
	/** Дата отчета */
	@Comment("Дата отчета")
	@DateString @DoDateString
	@Persist
	public String getReportDate() {
		return theReportDate;
	}

	public void setReportDate(String aReportDate) {
		theReportDate = aReportDate;
	}

	/** Дата отчета */
	private String theReportDate;
	/** Женщины в возрасте 65 и старше */
	@Comment("Женщины в возрасте 65 и старше")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="65")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="2")}	
	)
	public int getWomenAge65() {
		return theWomenAge65;
	}

	public void setWomenAge65(int aWomenAge65) {
		theWomenAge65 = aWomenAge65;
	}

	/** Женщины в возрасте 65 и старше */
	private int theWomenAge65;
	
	/** Мужчины в возрасте 65 и старше */
	@Comment("Мужчины в возрасте 65 и старше")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="65")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="1")}	
	)
	public int getMenAge65() {
		return theMenAge65;
	}

	public void setMenAge65(int aMenAge65) {
		theMenAge65 = aMenAge65;
	}

	/** Мужчины в возрасте 65 и старше */
	private int theMenAge65;
	
	/** Женщины в возрасте 55-64 года */
	@Comment("Женщины в возрасте 55-64 года")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="55")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="2")}	
	)
	public int getWomenAge55() {
		return theWomenAge55;
	}

	public void setWomenAge55(int aWomenAge55) {
		theWomenAge55 = aWomenAge55;
	}

	/** Женщины в возрасте 55-64 года */
	private int theWomenAge55;
	
	/** Мужчины в возрасте 55-64 года */
	@Comment("Мужчины в возрасте 55-64 года")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="55")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="1")}	
	)
	public int getMenAge55() {
		return theMenAge55;
	}

	public void setMenAge55(int aMenAge55) {
		theMenAge55 = aMenAge55;
	}

	/** Мужчины в возрасте 55-64 года */
	private int theMenAge55;
	
	/** Женщины в возрасте 45-54 года */
	@Comment("Женщины в возрасте 45-54 года")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="45")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="2")}	
	)
	public int getWomenAge45() {
		return theWomenAge45;
	}

	public void setWomenAge45(int aWomenAge45) {
		theWomenAge45 = aWomenAge45;
	}

	/** Женщины в возрасте 45-54 года */
	private int theWomenAge45;
	
	/** Мужчины в возрасте 45-54 года */
	@Comment("Мужчины в возрасте 45-54 года")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="45")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="1")}	
	)
	public int getMenAge45() {
		return theMenAge45;
	}

	public void setMenAge45(int aMenAge45) {
		theMenAge45 = aMenAge45;
	}

	/** Мужчины в возрасте 45-54 года */
	private int theMenAge45;
	
	/** Женщины в возрасте 35-44 года */
	@Comment("Женщины в возрасте 35-44 года")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="35")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="2")}	
	)
	public int getWomenAge35() {
		return theWomenAge35;
	}

	public void setWomenAge35(int aWomenAge35) {
		theWomenAge35 = aWomenAge35;
	}

	/** Женщины в возрасте 35-44 года */
	private int theWomenAge35;
	
	/** Мужчины в возрасте 35-44 года */
	@Comment("Мужчины в возрасте 35-44 года")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="35")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="1")}	
	)
	public int getMenAge35() {
		return theMenAge35;
	}

	public void setMenAge35(int aMenAge35) {
		theMenAge35 = aMenAge35;
	}

	/** Мужчины в возрасте 35-44 года */
	private int theMenAge35;
	
	/** Женщины в возрасте 25-34 года */
	@Comment("Женщины в возрасте 25-34 года")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="25")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="2")}	
	)
	public int getWomenAge25() {
		return theWomenAge25;
	}

	public void setWomenAge25(int aWomenAge25) {
		theWomenAge25 = aWomenAge25;
	}

	/** Женщины в возрасте 25-34 года */
	private int theWomenAge25;
	
	/** Мужчины в возрасте 25-34 года */
	@Comment("Мужчины в возрасте 25-34 года")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="25")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="1")}	
	)
	public int getMenAge25() {
		return theMenAge25;
	}

	public void setMenAge25(int aMenAge25) {
		theMenAge25 = aMenAge25;
	}

	/** Мужчины в возрасте 25-34 года */
	private int theMenAge25;
	
	/** Женщины в возрасте 18-24 года */
	@Comment("Женщины в возрасте 18-24 года")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="18")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="2")}	
	)
	public int getWomenAge18() {
		return theWomenAge18;
	}

	public void setWomenAge18(int aWomenAge18) {
		theWomenAge18 = aWomenAge18;
	}

	/** Женщины в возрасте 18-24 года */
	private int theWomenAge18;
	
	/** Мужчины в возрасте 18-24 лет */
	@Comment("Мужчины в возрасте 18-24 лет")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="18")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="1")}	
	)
	public int getMenAge18() {
		return theMenAge18;
	}

	public void setMenAge18(int aMenAge18) {
		theMenAge18 = aMenAge18;
	}

	/** Мужчины в возрасте 18-24 лет */
	private int theMenAge18;
	
	/** Женщины в возрасте 15-17 лет */
	@Comment("Женщины в возрасте 15-17 лет")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="15")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="2")}	
	)
	public int getWomenAge15() {
		return theWomenAge15;
	}

	public void setWomenAge15(int aWomenAge15) {
		theWomenAge15 = aWomenAge15;
	}

	/** Женщины в возрасте 15-17 лет */
	private int theWomenAge15;
	
	/** Мужчины в возрасте 15-17 лет */
	@Comment("Мужчины в возрасте 15-17 лет")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="15")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="1")}	
	)
	public int getMenAge15() {
		return theMenAge15;
	}

	public void setMenAge15(int aMenAge15) {
		theMenAge15 = aMenAge15;
	}

	/** Мужчины в возрасте 15-17 лет */
	private int theMenAge15;
	
	/** Женщины в возрасте 10-14 лет */
	@Comment("Женщины в возрасте 10-14 лет")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="10")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="2")}	
	)
	public int getWomenAge10() {
		return theWomenAge10;
	}

	public void setWomenAge10(int aWomenAge10) {
		theWomenAge10 = aWomenAge10;
	}

	/** Женщины в возрасте 10-14 лет */
	private int theWomenAge10;
	

	
	/** Мужчины в возрасте 10-14 лет */
	@Comment("Мужчины в возрасте 10-14 лет")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="10")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="1")}	
	)
	public int getMenAge10() {
		return theMenAge10;
	}

	public void setMenAge10(int aMenAge10) {
		theMenAge10 = aMenAge10;
	}

	/** Мужчины в возрасте 10-14 лет */
	private int theMenAge10; 
	
	
	/** Женщины в возрасте 5-9 лет */
	@Comment("Женщины в возрасте 5-9 лет")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="5")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="2")}	
	)
	public int getWomenAge5() {
		return theWomenAge5;
	}

	public void setWomenAge5(int aWomenAge5) {
		theWomenAge5 = aWomenAge5;
	}

	/** Женщины в возрасте 5-9 лет */
	private int theWomenAge5;
	
	/** Мужчины в возрасте 5-9 лест */
	@Comment("Мужчины в возрасте 5-9 лест")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="5")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="1")}	
	)
	public int getMenAge5() {
		return theMenAge5;
	}

	public void setMenAge5(int aMenAge5) {
		theMenAge5 = aMenAge5;
	}

	/** Мужчины в возрасте 5-9 лест */
	private int theMenAge5;
	

	
	/** Женщины в возрасте 1-4 года */
	@Comment("Женщины в возрасте 1-4 года")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="1")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="2")}	
	)
	public int getWomenAge1() {
		return theWomenAge1;
	}

	public void setWomenAge1(int aWomenAge1) {
		theWomenAge1 = aWomenAge1;
	}

	/** Женщины в возрасте 1-4 года */
	private int theWomenAge1;
	
	/** Мужчины в возрасте 1-4 года */
	@Comment("Мужчины в возрасте 1-4 года")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="1")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="1")}	
	)
	public int getMenAge1() {
		return theMenAge1;
	}

	public void setMenAge1(int aMenAge1) {
		theMenAge1 = aMenAge1;
	}

	/** Мужчины в возрасте 1-4 года */
	private int theMenAge1;
	
	/** Женщины в возрасте до 1 года */
	@Comment("Женщины в возрасте до 1 года")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="0")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="2")}	
	)
	public int getWomenAge0() {
		return theWomenAge0;
	}

	public void setWomenAge0(int aWomenAge0) {
		theWomenAge0 = aWomenAge0;
	}

	/** Женщины в возрасте до 1 года */
	private int theWomenAge0;
	
	/** Мужчины в возрасте до 1 года */
	@Comment("Мужчины в возрасте до 1 года")
	@RowPersistProperty(  
		matches = {@RowPersistMatch ( property="agePeriod", matchProperty="code", matchValue="0")
				, @RowPersistMatch ( property="sex", matchProperty="code", matchValue="1")}	
	)
	public int getMenAge0() {
		return theMenAge0;
	}

	public void setMenAge0(int aMenAge0) {
		theMenAge0 = aMenAge0;
	}

	/** Мужчины в возрасте до 1 года */
	private int theMenAge0;

}
