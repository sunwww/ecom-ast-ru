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
}
