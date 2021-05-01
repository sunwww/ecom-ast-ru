package ru.ecom.mis.ejb.uc.privilege.domain;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VocDrugClassify extends VocIdNameOmcCode {
	
	/** Позиции классификаторов */
	@Comment("Позиции классификаторов")
	@OneToMany(mappedBy="drug", cascade=CascadeType.ALL)
	public List<DrugClassificatorPosition> getDrugClassificatorPositions() {
		return drugClassificatorPositions;
	}


	/** Позиции классификаторов */
	private List<DrugClassificatorPosition> drugClassificatorPositions;
	
	/** Производитель */
	@Comment("Производитель")
	@OneToOne
	public VocDrugVendor getDrugVendor() {
		return drugVendor;
	}

	/** Производитель */
	private VocDrugVendor drugVendor;
	
	/** Количество доз в упаковке */
	private Integer packingAmount;
	
	/** Дозировка */
	private String dozage;
	
	/** Лекарственная форма */
	@Comment("Лекарственная форма")
	@OneToOne
	public VocDrugForm getDrugForm() {
		return drugForm;
	}

	/** Лекарственная форма */
	private VocDrugForm drugForm;
	
	/** Патентованное наименование */
	@Comment("Патентованное наименование")
	@OneToOne
	public VocLicensedName getLicensedName() {
		return licensedName;
	}

	/** Патентованное наименование */
	private VocLicensedName licensedName;
	
	/** Международное непатентованное наименование */
	@Comment("Международное непатентованное наименование")
	@OneToOne
	public VocDrugUnlicensedName getDrugUnlicensedName() {
		return drugUnlicensedName;
	}


	/** Дата создания */
	private Date createDate;
	/** Пользователь */
	private String username;
	/** Международное непатентованное наименование */
	private VocDrugUnlicensedName drugUnlicensedName;

	/** Дата регистрации */
	private Date registDate;

	/** Нормативная база */
	private String normData;
	/** Дата аннулирования */
	private Date registDateCancel;
	/** Дата окончания регистрации */
	private Date registDateTo;
	/** Регистрац. номер */
	private String impRegNumber;
	/** Форма выпуска */
	private String impForm;
	/** Торговое наименование */
	private String impLicName;
	/** Импорт наименование международное */
	private String impUnlicName;
	/** Импорт наименование фирмы */
	private String impVendor;
	/** Группа */
	private String impGroup;
}
