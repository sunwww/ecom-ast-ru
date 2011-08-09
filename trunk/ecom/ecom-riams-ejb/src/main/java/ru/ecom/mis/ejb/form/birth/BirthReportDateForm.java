package ru.ecom.mis.ejb.form.birth;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.RowPersist;
import ru.ecom.ejb.services.entityform.annotation.RowPersistMatch;
import ru.ecom.ejb.services.entityform.annotation.RowPersistProperty;
import ru.ecom.mis.ejb.domain.birth.BirthReportDate;
import ru.ecom.mis.ejb.domain.birth.BirthReportRow;
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
 * Строка отчета о рождаемости
 * 
 * @author oegorova
 * 
 */
@EntityForm
@EntityFormPersistance(clazz = BirthReportDate.class)
@WebTrail(
			comment = "Сведения о числе родов и родившихся за день"
	, nameProperties = "reportDate"
		, view = "entityView-mis_birthReportDate.do"
		, list = "entityParentList-mis_birthReportDate.do"	
			)
@Parent(property = "lpu", parentForm = MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Report/Birth")
@Comment("Отчет о рождаемости")
@RowPersist(rowEntityClass = BirthReportRow.class, parentProperty = "birthReportDate", defaultProperty = "birthAmount")
public class BirthReportDateForm extends IdEntityForm {

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
	@DateString
	@DoDateString
	@Persist
	public String getReportDate() {
		return theReportDate;
	}

	public void setReportDate(String aReportDate) {
		theReportDate = aReportDate;
	}

	/** Дата отчета */
	private String theReportDate;

	/** 3-е и последующие роды мертвыми */
	@Comment("3-е и последующие роды мертвыми ")
	@RowPersistProperty(matches = {
			@RowPersistMatch(property = "vocBirthOrder", matchProperty = "code", matchValue = "OTHER_DEAD") })
	public int getOtherDeadBirth() {
		return theOtherDeadBirth;
	}

	public void setOtherDeadBirth(int aOtherDeadBoysBirth) {
		theOtherDeadBirth = aOtherDeadBoysBirth;
	}

	/** 3-е и последующие роды мертвыми */
	private int theOtherDeadBirth;

	/** 3-е и последующие роды живыми */
	@Comment("3-е и последующие роды живыми ")
	@RowPersistProperty(matches = {
			@RowPersistMatch(property = "vocBirthOrder", matchProperty = "code", matchValue = "OTHER_LIVE")})
	public int getOtherLiveBirth() {
		return theOtherLiveBirth;
	}

	public void setOtherLiveBirth(int aOtherLiveBoysBirth) {
		theOtherLiveBirth = aOtherLiveBoysBirth;
	}

	/** 3-е и последующие роды живыми */
	private int theOtherLiveBirth;

	/** 2-роды мертвыми */
	@Comment("2-роды мертвыми ")
	@RowPersistProperty(matches = {
			@RowPersistMatch(property = "vocBirthOrder", matchProperty = "code", matchValue = "2_DEAD")})
	public int getSecondDeadBirth() {
		return theSecondDeadBirth;
	}

	public void setSecondDeadBirth(int aSecondDeadBoysBirth) {
		theSecondDeadBirth = aSecondDeadBoysBirth;
	}

	/** 2-роды мертвыми */
	private int theSecondDeadBirth;

	/** 2-е роды живыми */
	@Comment("2-е роды живыми ")
	@RowPersistProperty(matches = {
			@RowPersistMatch(property = "vocBirthOrder", matchProperty = "code", matchValue = "2_LIVE") })
	public int getSecondLiveBirth() {
		return theSecondLiveBirth;
	}

	public void setSecondLiveBirth(int aSecondLiveBoysBirth) {
		theSecondLiveBirth = aSecondLiveBoysBirth;
	}

	/** 2-е роды живыми */
	private int theSecondLiveBirth;

	/** 1-е роды мертвыми */
	@Comment("1-е роды мертвыми ")
	@RowPersistProperty(matches = {
			@RowPersistMatch(property = "vocBirthOrder", matchProperty = "code", matchValue = "1_DEAD") })
	public int getFirstDeadBirth() {
		return theFirstDeadBirth;
	}

	public void setFirstDeadBirth(int aFirstDeadBoysBirth) {
		theFirstDeadBirth = aFirstDeadBoysBirth;
	}

	/** 1-е роды мертвыми */
	private int theFirstDeadBirth;

	/** 1-е роды живыми */
	@Comment("1-е роды живыми ")
	@RowPersistProperty(matches = {
			@RowPersistMatch(property = "vocBirthOrder", matchProperty = "code", matchValue = "1_LIVE") })
	public int getFirstLiveBirth() {
		return theFirstLiveBirth;
	}

	public void setFirstLiveBirth(int aFirstLiveBoysBirth) {
		theFirstLiveBirth = aFirstLiveBoysBirth;
	}

	/** 1-е роды живыми */
	private int theFirstLiveBirth;

	/** Родилось мертвых девочек */
	@Comment("Родилось мертвых девочек")
	@RowPersistProperty(matches = {
			@RowPersistMatch(property = "vocBirthOrder", matchProperty = "code", matchValue = "DEAD_BORN"),
			@RowPersistMatch(property = "sex", matchProperty = "code", matchValue = "2") })
	public int getDeadBornGirls() {
		return theDeadBornGirls;
	}

	public void setDeadBornGirls(int aDeadBornGirls) {
		theDeadBornGirls = aDeadBornGirls;
	}

	/** Родилось мертвых девочек */
	private int theDeadBornGirls;

	/** Родилось живых девочек */
	@Comment("Родилось живых девочек")
	@RowPersistProperty(matches = {
			@RowPersistMatch(property = "vocBirthOrder", matchProperty = "code", matchValue = "LIVE_BORN"),
			@RowPersistMatch(property = "sex", matchProperty = "code", matchValue = "2") })
	public int getLiveBornGirls() {
		return theLiveBornGirls;
	}

	public void setLiveBornGirls(int aLiveBornGirls) {
		theLiveBornGirls = aLiveBornGirls;
	}

	/** Родилось живых девочек */
	private int theLiveBornGirls;

	/** Родилось мертвых мальчиков */
	@Comment("Родилось мертвых мальчиков")
	@RowPersistProperty(matches = {
			@RowPersistMatch(property = "vocBirthOrder", matchProperty = "code", matchValue = "DEAD_BORN"),
			@RowPersistMatch(property = "sex", matchProperty = "code", matchValue = "1") })
	public int getDeadBornBoys() {
		return theDeadBornBoys;
	}

	public void setDeadBornBoys(int aDeadBotnBoys) {
		theDeadBornBoys = aDeadBotnBoys;
	}

	/** Родилось мертвых мальчиков */
	private int theDeadBornBoys;

	/** Родилось живых мальчиков */
	@Comment("Родилось живых мальчиков")
	@RowPersistProperty(matches = {
			@RowPersistMatch(property = "vocBirthOrder", matchProperty = "code", matchValue = "LIVE_BORN"),
			@RowPersistMatch(property = "sex", matchProperty = "code", matchValue = "1") })
	public int getLiveBornBoys() {
		return theLiveBornBoys;
	}

	public void setLiveBornBoys(int aLiveBornBoys) {
		theLiveBornBoys = aLiveBornBoys;
	}

	/** Родилось живых мальчиков */
	private int theLiveBornBoys;
	
	/** Родов двойни */
	@Comment("Родов двойни")
	@RowPersistProperty(matches = {
			@RowPersistMatch(property = "vocBirthOrder", matchProperty = "code", matchValue = "TWINS_BIRTH")
	})
	public int getTwinsBirth() {
		return theTwinsBirth;
	}

	public void setTwinsBirth(int aTwinsBirth) {
		theTwinsBirth = aTwinsBirth;
	}

	/** Родов тройни */
	@Comment("Родов тройни")
	@RowPersistProperty(matches = {
			@RowPersistMatch(property = "vocBirthOrder", matchProperty = "code", matchValue = "TRIPLETS_BIRTH")
	})
	public int getTripletsBirth() {
		return theTripletsBirth;
	}

	public void setTripletsBirth(int aTripletsBirth) {
		theTripletsBirth = aTripletsBirth;
	}

	/** Родов тройни */
	private int theTripletsBirth;
	/** Родов двойни */
	private int theTwinsBirth;

}
