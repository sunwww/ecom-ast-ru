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
}
