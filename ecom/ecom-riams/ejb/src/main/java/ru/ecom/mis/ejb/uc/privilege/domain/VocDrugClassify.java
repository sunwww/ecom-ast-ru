package ru.ecom.mis.ejb.uc.privilege.domain;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;
import ru.ecom.mis.ejb.uc.privilege.domain.voc.VocDrugUnlicensedName;
import ru.ecom.mis.ejb.uc.privilege.domain.voc.VocDrugVendor;
import ru.ecom.mis.ejb.uc.privilege.domain.voc.VocLicensedName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/**
 * Лекарственное средство
 */
@Entity
@Comment("Лекарственное средство")
@Table(schema="SQLUser")
public class VocDrugClassify extends VocIdNameOmcCode {
	
	/** Позиции классификаторов */
	@Comment("Позиции классификаторов")
	@OneToMany(mappedBy="drug", cascade=CascadeType.ALL)
	public List<DrugClassificatorPosition> getDrugClassificatorPositions() {
		return theDrugClassificatorPositions;
	}

	public void setDrugClassificatorPositions(List<DrugClassificatorPosition> aDrugClassificatorPositions) {
		theDrugClassificatorPositions = aDrugClassificatorPositions;
	}

	/** Позиции классификаторов */
	private List<DrugClassificatorPosition> theDrugClassificatorPositions;
	
	/** Производитель */
	@Comment("Производитель")
	@OneToOne
	public VocDrugVendor getDrugVendor() {
		return theDrugVendor;
	}

	public void setDrugVendor(VocDrugVendor aDrugVendor) {
		theDrugVendor = aDrugVendor;
	}

	/** Производитель */
	private VocDrugVendor theDrugVendor;
	
	/** Количество доз в упаковке */
	@Comment("Количество доз в упаковке")
	public Integer getPackingAmount() {
		return thePackingAmount;
	}

	public void setPackingAmount(Integer aPackingAmount) {
		thePackingAmount = aPackingAmount;
	}

	/** Количество доз в упаковке */
	private Integer thePackingAmount;
	
	/** Дозировка */
	@Comment("Дозировка")
	public String getDozage() {
		return theDozage;
	}

	public void setDozage(String aDozage) {
		theDozage = aDozage;
	}

	/** Дозировка */
	private String theDozage;
	
	/** Лекарственная форма */
	@Comment("Лекарственная форма")
	@OneToOne
	public VocDrugForm getDrugForm() {
		return theDrugForm;
	}

	public void setDrugForm(VocDrugForm aDrugForm) {
		theDrugForm = aDrugForm;
	}

	/** Лекарственная форма */
	private VocDrugForm theDrugForm;
	
	/** Патентованное наименование */
	@Comment("Патентованное наименование")
	@OneToOne
	public VocLicensedName getLicensedName() {
		return theLicensedName;
	}

	public void setLicensedName(VocLicensedName aLicensedName) {
		theLicensedName = aLicensedName;
	}

	/** Патентованное наименование */
	private VocLicensedName theLicensedName;
	
	/** Международное непатентованное наименование */
	@Comment("Международное непатентованное наименование")
	@OneToOne
	public VocDrugUnlicensedName getDrugUnlicensedName() {
		return theDrugUnlicensedName;
	}

	public void setDrugUnlicensedName(VocDrugUnlicensedName aDrugUnlicensedName) {
		theDrugUnlicensedName = aDrugUnlicensedName;
	}

	/** Пользователь */
	@Comment("Пользователь")
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}

	/** Дата создания */
	private Date theCreateDate;
	/** Пользователь */
	private String theUsername;
	/** Международное непатентованное наименование */
	private VocDrugUnlicensedName theDrugUnlicensedName;
	
	/** Импорт наименование фирмы */
	@Comment("Импорт наименование фирмы")
	public String getImpVendor() {return theImpVendor;}
	public void setImpVendor(String aImpVendor) {theImpVendor = aImpVendor;}

	/** Импорт наименование международное */
	@Comment("Импорт наименование международное")
	public String getImpUnlicName() {return theImpUnlicName;}
	public void setImpUnlicName(String aImpUnlicName) {theImpUnlicName = aImpUnlicName;}

	/** Торговое наименование */
	@Comment("Торговое наименование")
	public String getImpLicName() {return theImpLicName;}
	public void setImpLicName(String aImpLicName) {theImpLicName = aImpLicName;}

	/** Форма выпуска */
	@Comment("Форма выпуска")
	public String getImpForm() {return theImpForm;}
	public void setImpForm(String aImpForm) {theImpForm = aImpForm;}

	/** Регистрац. номер */
	@Comment("Регистрац. номер")
	public String getImpRegNumber() {return theImpRegNumber;}
	public void setImpRegNumber(String aImpRegNumber) {theImpRegNumber = aImpRegNumber;}

	/** Дата регистрации */
	@Comment("Дата регистрации")
	public Date getRegistDate() {return theRegistDate;}
	public void setRegistDate(Date aRegistDate) {theRegistDate = aRegistDate;}

	/** Дата регистрации */
	private Date theRegistDate;
	
	/** Дата окончания регистрации */
	@Comment("Дата окончания регистрации")
	public Date getRegistDateTo() {return theRegistDateTo;}
	public void setRegistDateTo(Date aRegistDateTo) {theRegistDateTo = aRegistDateTo;}

	/** Дата аннулирования */
	@Comment("Дата аннулирования")
	public Date getRegistDateCancel() {return theRegistDateCancel;}
	public void setRegistDateCancel(Date aRegistDateCancel) {theRegistDateCancel = aRegistDateCancel;}

	/** Нормативная база */
	@Comment("Нормативная база")
	public String getNormData() {return theNormData;}
	public void setNormData(String aNormData) {theNormData = aNormData;}

	/** Нормативная база */
	private String theNormData;
	/** Дата аннулирования */
	private Date theRegistDateCancel;
	/** Дата окончания регистрации */
	private Date theRegistDateTo;
	/** Регистрац. номер */
	private String theImpRegNumber;
	/** Форма выпуска */
	private String theImpForm;
	/** Торговое наименование */
	private String theImpLicName;
	/** Импорт наименование международное */
	private String theImpUnlicName;
	/** Импорт наименование фирмы */
	private String theImpVendor;
	/** Группа */
	@Comment("Группа")
	public String getImpGroup() {return theImpGroup;}
	public void setImpGroup(String aImpGroup) {theImpGroup = aImpGroup;}

	/** Группа */
	private String theImpGroup;
}
