package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(name = "OMC_STANDART",schema="SQLUser")
@AIndexes
(
		{	@AIndex(unique = false, properties = "model")
	, @AIndex(unique = false, properties = "idcList")
		}
)
public class OmcStandart extends VocBaseEntity {
	/** Idc list */
	@Comment("Idc list")
	@AFormatFieldSuggest("IdcList")
	public String getIdcList() {
		return theIdcList;
	}

	public void setIdcList(String aIdcList) {
		theIdcList = aIdcList;
	}
	
	/** Фаза */
	@Comment("Фаза")
	@AFormatFieldSuggest("phase")
	public String getPhase() {
		return thePhase;
	}

	public void setPhase(String aPhase) {
		thePhase = aPhase;
	}

	/** stage */
	@Comment("stage")
	@AFormatFieldSuggest("stage")
	public String getStage() {
		return theStage;
	}

	public void setStage(String aStage) {
		theStage = aStage;
	}

	/** complicatioC */
	@Comment("complicatioC")
	@AFormatFieldSuggest("complicatio")
	public String getComplication() {
		return theComplication;
	}

	public void setComplication(String aComplication) {
		theComplication = aComplication;
	}

	/** payer */
	@Comment("payer")
	@AFormatFieldSuggest("payer")
	public String getPayer() {
		return thePayer;
	}

	public void setPayer(String aPayer) {
		thePayer = aPayer;
	}

	/** model */
	@Comment("model")
	@AFormatFieldSuggest("model")
	public String getModel() {
		return theModel;
	}

	public void setModel(String aModel) {
		theModel = aModel;
	}
	
	/** Не действует */
	@Comment("Не действует")
	public Boolean getDeprecated() {return theDeprecated;}
	public void setDeprecated(Boolean aDeprecated) {theDeprecated = aDeprecated;}

	/** Не действует */
	private Boolean theDeprecated;

	/** model */
	private String theModel;
	/** payer */
	private String thePayer;
	/** complicatioC */
	private String theComplication;
	/** stage */
	private String theStage;
	/** Фаза */
	private String thePhase;

	/** Idc list */
	private String theIdcList;

}
