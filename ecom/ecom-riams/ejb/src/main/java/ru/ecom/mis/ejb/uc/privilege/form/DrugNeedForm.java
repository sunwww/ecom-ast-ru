package ru.ecom.mis.ejb.uc.privilege.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.uc.privilege.domain.DrugNeed;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz= DrugNeed.class)
@WebTrail(comment = "Лекарственное средство", nameProperties= {"info"}, view="entityParentView-mis_drugNeed.do")
@Parent(property="privilege", parentForm=PrivilegeForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Person/Privilege/DrugNeed")
public class DrugNeedForm  extends IdEntityForm{
	
	/** Информация */
	@Comment("Информация")
	@Persist
	public String getInfo() {
		return theInfo;
	}

	public void setInfo(String aInfo) {
		theInfo = aInfo;
	}

	/** Информация */
	private String theInfo;
	/** Льгота */
	@Comment("Льгота")
	@Persist
	public Long getPrivilege() {
		return thePrivilege;
	}

	public void setPrivilege(Long aPrivilege) {
		thePrivilege = aPrivilege;
	}

	/** Среднемесячная доза в мг */
	@Comment("Среднемесячная доза в мг")
	@Persist
	public String getMiddleMonthDoze() {
		return theMiddleMonthDoze;
	}

	public void setMiddleMonthDoze(String aMiddleMonthDoze) {
		theMiddleMonthDoze = aMiddleMonthDoze;
	}

	/** Лекарственный препарат */
	@Comment("Лекарственный препарат")
	@Persist
	public Long getDrugClassify() {
		return theDrugClassify;
	}

	public void setDrugClassify(Long aDrugClassify) {
		theDrugClassify = aDrugClassify;
	}

	/** Торговое наименование лекарственного препарата */
	@Comment("Торговое наименование лекарственного препарата")
	@Persist
	public String getTradename() {
		return theTradename;
	}

	public void setTradename(String aTradename) {
		theTradename = aTradename;
	}

	/** Форма выпуска */
	@Comment("Форма выпуска")
	@Persist
	public Long getDrugForm() {
		return theDrugForm;
	}

	public void setDrugForm(Long aDrugForm) {
		theDrugForm = aDrugForm;
	}
	/** Код врача */
	@Comment("Код врача")
	@Persist
	public Long getDloDoctor() {
		return theDloDoctor;
	}

	public void setDloDoctor(Long aDloDoctor) {
		theDloDoctor = aDloDoctor;
	}

        /**
         * Дата назначения
         */
	@Comment("Дата назначения")
	@Persist
	@DateString @DoDateString
        public String getIssuedDate() { return theIssuedDate ; }
        public void setIssuedDate(String aDate) { theIssuedDate = aDate ; }
        private String theIssuedDate ;

        /** Дата отмены */
	@Comment("Дата отмены")
	@Persist
	@DateString @DoDateString
        public String getCancelDate() { return theCancelDate ; }
        public void setCancelDate(String aDate) { theCancelDate = aDate ; }
        private String theCancelDate ;

	/** Код врача */
	private Long theDloDoctor;
	/** Форма выпуска */
	private Long theDrugForm;
	/** Торговое наименование лекарственного препарата */
	private String theTradename;
	/** Лекарственный препарат */
	private Long theDrugClassify;

	/** Среднемесячная доза в мг */
	private String theMiddleMonthDoze;
	
	/** Льгота */
	private Long thePrivilege;

}
