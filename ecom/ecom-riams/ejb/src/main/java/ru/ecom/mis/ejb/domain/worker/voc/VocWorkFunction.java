package ru.ecom.mis.ejb.domain.worker.voc;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.worker.WorkFunctionService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Рабочая функция
 * @author azviagin
 *
 */
@Comment("Рабочая функция")
@Entity
@Table(schema="SQLUser")
public class VocWorkFunction extends VocBaseEntity{
	
	/** Должности */
	@Comment("Должности")
	@ManyToOne
	public VocPost getVocPost() {
		return theVocPost;
	}

	public void setVocPost(VocPost aVocPost) {
		theVocPost = aVocPost;
	}

	/** Должности */
	private VocPost theVocPost;

	/** Мед. услуги */
	@Comment("Мед. услуги")
	@OneToMany(mappedBy="vocWorkFunction", cascade=CascadeType.ALL)
	public List<WorkFunctionService> getWorkFunctionServices() {
		return theWorkFunctionServices;
	}

	public void setWorkFunctionServices(List<WorkFunctionService> aWorkFunctionServices) {
		theWorkFunctionServices = aWorkFunctionServices;
	}

	/** Мед. услуги */
	private List<WorkFunctionService> theWorkFunctionServices;
	
	/** Короткое название */
	@Comment("Короткое название")
	public String getShortName() {return theShortName;}
	public void setShortName(String aShortName) {theShortName = aShortName;}

	/** Короткое название */
	private String theShortName;
	
	/** Не заполняется диагноз */
	@Comment("Не заполняется диагноз")
	public Boolean getIsNoDiagnosis() {return theIsNoDiagnosis;}
	public void setIsNoDiagnosis(Boolean aIsNoDiagnosis) {theIsNoDiagnosis = aIsNoDiagnosis;}

	/** Не заполняется диагноз */
	private Boolean theIsNoDiagnosis;
	
	/** Функциональная диагностика */
	@Comment("Функциональная диагностика")
	public Boolean getIsFuncDiag() {return theIsFuncDiag;}
	public void setIsFuncDiag(Boolean aIsFuncDiag) {theIsFuncDiag = aIsFuncDiag;}

	/** Лаборатория */
	@Comment("Лаборатория")
	public Boolean getIsLab() {return theIsLab;}
	public void setIsLab(Boolean aIsLab) {theIsLab = aIsLab;}

	/** Лучевая диагностика */
	@Comment("Лучевая диагностика")
	public Boolean getIsRadiationDiagnosis() {return theIsRadiationDiagnosis;}
	public void setIsRadiationDiagnosis(Boolean aIsRadiationDiagnosis) {theIsRadiationDiagnosis = aIsRadiationDiagnosis;}

	/** Лучевая диагностика */
	private Boolean theIsRadiationDiagnosis;
	/** Лаборатория */
	private Boolean theIsLab;
	/** Функциональная диагностика */
	private Boolean theIsFuncDiag;
	
	/** Не включать в 039 форму */
	@Comment("Не включать в 039 форму")
	public Boolean getIsNo039() {return theIsNo039;}
	public void setIsNo039(Boolean aIsNo039) {theIsNo039 = aIsNo039;}

	/** Не включать в 039 форму */
	private Boolean theIsNo039;



	/** Короткое название ФСС*/
	@Comment("Короткое название ФСС")
	public String getFSSShortName() {return theFSSShortName;}
	public void setFSSShortName(String aFSSShortName) {theFSSShortName = aFSSShortName;}
	private String theFSSShortName;

	/** Код ФСС */
	@Comment("Код ФСС")
	public String getFSSCode() {return theFSSCode;}
	public void setFSSCode(String aFSSCode) {theFSSCode = aFSSCode;}
	private String theFSSCode;

	private String promedCode_polic;
	private String promedCode_stac;

	@Comment("Код поликлиники в промеде")
	public String getPromedCode_polic() {
		return promedCode_polic;
	}
	public void setPromedCode_polic(String promedCode_polic) {
		this.promedCode_polic = promedCode_polic;
	}

	@Comment("Код стационара в промеде")
	public String getPromedCode_stac() {
		return promedCode_stac;
	}
	public void setPromedCode_stac(String promedCode_stac) {
		this.promedCode_stac = promedCode_stac;
	}

	/** Не подавать по ОМС */
	@Comment("Не подавать по ОМС")
	public Boolean getIsNoOmc() {return theIsNoOmc;}
	public void setIsNoOmc(Boolean aIsNoOmc) {theIsNoOmc = aIsNoOmc;}
	/** Не подавать по ОМС */
	private Boolean theIsNoOmc=false ;
}
