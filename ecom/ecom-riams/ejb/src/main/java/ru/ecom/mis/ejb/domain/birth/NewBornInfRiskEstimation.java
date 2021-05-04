package ru.ecom.mis.ejb.domain.birth;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class NewBornInfRiskEstimation extends Inspection {
	/** Длительность безводного периода */
	@Comment("Длительность безводного периода")
	@OneToOne
	public VocInfRiskWaterless getWaterlessDuration() {
		return waterlessDuration;
	}
	private VocInfRiskWaterless waterlessDuration;
	
	/** Температура матери в родах */
	@Comment("Температура матери в родах")
	@OneToOne
	public VocInfRiskMotherTemperature getMotherTemperature() {
		return motherTemperature;
	}
	private VocInfRiskMotherTemperature motherTemperature;
	
	/** Характер амниотических вод */
	@Comment("Характер амниотических вод")
	@OneToOne
	public VocInfRiskWaterNature getWaterNature() {
		return waterNature;
	}
	private VocInfRiskWaterNature waterNature;
	
	/** Оценка по Апгар */
	@Comment("Оценка по Апгар")
	@OneToOne
	public VocInfRiskApgar getApgar() {
		return apgar;
	}
	private VocInfRiskApgar apgar;
	
	/** Хронические очаги инфекции или острые инфекции, перенесенные за месяц до родов или выявленные у матери в течение 1-х суток после родов  */
	@Comment("Хронические очаги инфекции или острые инфекции, перенесенные за месяц до родов или выявленные у матери в течение 1-х суток после родов")
	@OneToOne
	public VocInfRiskMotherDiseases getMotherInfectiousDiseases() {
		return motherInfectiousDiseases;
	}
	private VocInfRiskMotherDiseases motherInfectiousDiseases;
	
	/** Масса тела ребенка, гр. */
	@Comment("Масса тела ребенка, гр.")
	@OneToOne
	public VocInfRiskNewBornWeight getNewBornWeight() {
		return newBornWeight;
	}
	private VocInfRiskNewBornWeight newBornWeight;
	
	/** Общая оценка (балл) */
	@Comment("Общая оценка (балл)")
	@OneToOne
	public VocCommonMask getCommonMark() {
		return commonMark;
	}
	private VocCommonMask commonMark;
	
	@Transient
	public String getTypeInformation() {
		return  "Оценка риска бактериальной инфекции у новорожденных" ;
	}
	@Transient
	public String getInformation() {
		return "Общая оценка (балл): " + (commonMark != null ? commonMark.getCode() : "") + " " + (commonMark != null ? commonMark.getName() : "");
	}
	

	
}
