package ru.ecom.mis.ejb.domain.diet;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.diet.voc.VocMealTime;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Журнал контроля качества готовой пищи (УФ 6-лп)
 * @author oegorova
 *
 */

@Comment("Журнал контроля качества готовой пищи (УФ 6-лп)")
@Entity
@Table(schema="SQLUser")
public class MealQualityControl extends BaseEntity {
	
	/** Диета */
	@Comment("Диета")
	public Diet getDiet() {
		return theDiet;
	}
	public void setDiet(Diet aDiet) {
		theDiet = aDiet;
	}
	/** Диета */
	private Diet theDiet;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {
		return theLpu;
	}

	public void setLpu(MisLpu aLpu) {
		theLpu = aLpu;
	}

	/** ЛПУ */
	private MisLpu theLpu;
	
	/** Информация о снявшем пробу */
@Comment("Информация о снявшем пробу")
public String getTesterInfo() {
	return theTesterInfo;
}

public void setTesterInfo(String aTesterInfo) {
	theTesterInfo = aTesterInfo;
}

/** Информация о снявшем пробу */
private String theTesterInfo;
	
/** Информация о дежурном враче */
@Comment("Информация о дежурном враче")
public String getDutyDoctorInfo() {
	return theDutyDoctorInfo;
}

public void setDutyDoctorInfo(String aDutyDoctorInfo) {
	theDutyDoctorInfo = aDutyDoctorInfo;
}

/** Информация о дежурном враче */
private String theDutyDoctorInfo;
	
	/** Разрешение на выдачу */
	@Comment("Разрешение на выдачу")
	public Boolean getIssuePermission() {
		return theIssuePermission;
	}

	public void setIssuePermission(Boolean aIssuePermission) {
		theIssuePermission = aIssuePermission;
	}

	/** Разрешение на выдачу */
	private Boolean theIssuePermission;
	
	/** Оценка санитарии */
	@Comment("Оценка санитарии")
	public String getEstimateSanitation() {
		return theEstimateSanitation;
	}

	public void setEstimateSanitation(String aEstimateSanitation) {
		theEstimateSanitation = aEstimateSanitation;
	}

	/** Оценка санитарии */
	private String theEstimateSanitation;
	
	/** Оценка веса */
	@Comment("Оценка веса")
	public String getEstimateWeight() {
		return theEstimateWeight;
	}

	public void setEstimateWeight(String aEstimateWeight) {
		theEstimateWeight = aEstimateWeight;
	}

	/** Оценка веса */
	private String theEstimateWeight;
	
	/** Оценка приготовления */
	@Comment("Оценка приготовления")
	public String getEstimatePreparation() {
		return theEstimatePreparation;
	}

	public void setEstimatePreparation(String aEstimatePreparation) {
		theEstimatePreparation = aEstimatePreparation;
	}

	/** Оценка приготовления */
	private String theEstimatePreparation;
	
	/** Оценка блюда */
	@Comment("Оценка блюда")
	public String getEstimateDish() {
		return theEstimateDish;
	}

	public void setEstimateDish(String aEstimateDish) {
		theEstimateDish = aEstimateDish;
	}

	/** Оценка блюда */
	private String theEstimateDish;
	
	/** Оценка меню */
	@Comment("Оценка меню")
	public String getEstimateMenu() {
		return theEstimateMenu;
	}

	public void setEstimateMenu(String aEstimateMenu) {
		theEstimateMenu = aEstimateMenu;
	}

	/** Оценка меню */
	private String theEstimateMenu;
	
	/** Прием пищи */
	@Comment("Прием пищи")
	@OneToOne
	public VocMealTime getMealTime() {
		return theMealTime;
	}

	public void setMealTime(VocMealTime aMealTime) {
		theMealTime = aMealTime;
	}

	/** Прием пищи */
	private VocMealTime theMealTime;
	
	/** Дата контроля */
	@Comment("Дата контроля")
	public Date getControlDate() {
		return theControlDate;
	}

	public void setControlDate(Date aControlDate) {
		theControlDate = aControlDate;
	}

	/** Дата контроля */
	private Date theControlDate;

}
