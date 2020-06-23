package ru.ecom.mis.ejb.domain.birth;

import ru.ecom.mis.ejb.domain.birth.voc.*;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Оценка риска бактериальной инфекции у новорожденных
 * @author azviagin
 *
 */

@Comment("Оценка риска бактериальной инфекции у новорожденных")
@Entity
public class NewBornInfRiskEstimation extends Inspection {
	/** Длительность безводного периода */
	@Comment("Длительность безводного периода")
	@OneToOne
	public VocInfRiskWaterless getWaterlessDuration() {
		return theWaterlessDuration;
	}
	public void setWaterlessDuration(VocInfRiskWaterless aWaterlessDuration) {
		theWaterlessDuration = aWaterlessDuration;
	}
	private VocInfRiskWaterless theWaterlessDuration;
	
	/** Температура матери в родах */
	@Comment("Температура матери в родах")
	@OneToOne
	public VocInfRiskMotherTemperature getMotherTemperature() {
		return theMotherTemperature;
	}
	public void setMotherTemperature(VocInfRiskMotherTemperature aMotherTemperature) {
		theMotherTemperature = aMotherTemperature;
	}
	private VocInfRiskMotherTemperature theMotherTemperature;
	
	/** Характер амниотических вод */
	@Comment("Характер амниотических вод")
	@OneToOne
	public VocInfRiskWaterNature getWaterNature() {
		return theWaterNature;
	}
	public void setWaterNature(VocInfRiskWaterNature aWaterNature) {
		theWaterNature = aWaterNature;
	}
	private VocInfRiskWaterNature theWaterNature;
	
	/** Оценка по Апгар */
	@Comment("Оценка по Апгар")
	@OneToOne
	public VocInfRiskApgar getApgar() {
		return theApgar;
	}
	public void setApgar(VocInfRiskApgar aApgar) {
		theApgar = aApgar;
	}
	private VocInfRiskApgar theApgar;
	
	/** Хронические очаги инфекции или острые инфекции, перенесенные за месяц до родов или выявленные у матери в течение 1-х суток после родов  */
	@Comment("Хронические очаги инфекции или острые инфекции, перенесенные за месяц до родов или выявленные у матери в течение 1-х суток после родов")
	@OneToOne
	public VocInfRiskMotherDiseases getMotherInfectiousDiseases() {
		return theMotherInfectiousDiseases;
	}
	public void setMotherInfectiousDiseases(VocInfRiskMotherDiseases aMotherInfectiousDiseases) {
		theMotherInfectiousDiseases = aMotherInfectiousDiseases;
	}
	private VocInfRiskMotherDiseases theMotherInfectiousDiseases;
	
	/** Масса тела ребенка, гр. */
	@Comment("Масса тела ребенка, гр.")
	@OneToOne
	public VocInfRiskNewBornWeight getNewBornWeight() {
		return theNewBornWeight;
	}
	public void setNewBornWeight(VocInfRiskNewBornWeight aNewBornWeight) {
		theNewBornWeight = aNewBornWeight;
	}
	private VocInfRiskNewBornWeight theNewBornWeight;
	
	/** Общая оценка (балл) */
	@Comment("Общая оценка (балл)")
	@OneToOne
	public VocCommonMask getCommonMark() {
		return theCommonMark;
	}
	public void setCommonMark(VocCommonMask aCommonMark) {
		theCommonMark = aCommonMark;
	}
	private VocCommonMask theCommonMark;
	
	@Transient
	public String getTypeInformation() {
		return  "Оценка риска бактериальной инфекции у новорожденных" ;
	}
	@Transient
	public String getInformation() {
		return "Общая оценка (балл): " + (theCommonMark != null ? theCommonMark.getCode() : "") + " " + (theCommonMark != null ? theCommonMark.getName() : "");
	}
	

	
}
