package ru.ecom.mis.ejb.domain.birth;

/**
 * Осмотр беременной
 */

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.birth.voc.*;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Entity
@Comment("Осмотр беременной")
@Getter
@Setter
public class PregnanInspection extends Inspection {
	
	/** Положение плода */
	@Comment("Положение плода")
	@OneToOne
	public VocFetusLocation getFetusLocation() {return fetusLocation;}

	/** Предлежащая часть */
	@Comment("Предлежащая часть")
	@OneToOne
	public VocPreviusPart getPreviusPart() {return previusPart;}

	/** Родовая деятельность */
	@Comment("Родовая деятельность")
	@OneToOne
	public VocPregnancyActivity getPregnancyActivity() {return pregnancyActivity;}

	/** Влагалищные выделения */
	@Comment("Влагалищные выделения")
	@OneToOne
	public VocVaginalDischarge getVaginalDischarge() {return vaginalDischarge;}

	/** Место сердцебиения плода */
	@Comment("Место сердцебиения плода")
	@OneToOne
	public VocFetusPalpitationPlace getFetusPalpitationPlace() {return fetusPalpitationPlace;}

	/** Характер сердцебиения плода */
	@Comment("Характер сердцебиения плода")
	@OneToOne
	public VocFetusPalpitationNature getFetusPalpitationNature() {return fetusPalpitationNature;}

	@Transient
	public String getInformation() {
		// Размеры таза
		return "Размеры таза: D Sp" + pelvisDSp +
				" D Cr " + pelvisDCr +
				" D Tr " + pelvisDTr +
				"C ext " + pelvisCExt +
				"C diag " + pelvisCDiag +
				"C later " + pelvisCLater +
				//Ромб Михаэлиса
				". Ромб Михаэлиса прод." + rhombLongitudinal + " попер. " + rhombTransversal +
				//Индекс Соловьева
				". Индекс Соловьева " + solovievIndex;
	}
	@Transient
	public String getTypeInformation() {
		return  "Осмотр беременной";
	}	
	@Transient
	public String getFetusInfo() {
		StringBuilder ret = new StringBuilder() ;
		if(getFetusPalpitationPlace()!=null) {
            ret.append(getFetusPalpitationPlace().getName()) ;
            ret.append(",");
		ret.append(getFetusPalpitationRate()) ;
		
		ret.append(" уд.");
		ret.append(",");
		if(getFetusPalpitationNature()!=null) {
			ret.append(getFetusPalpitationNature().getName());
			}
		}
		return ret.toString() ;
	}
	
	/** Место сердцебиения плода */
	private VocFetusPalpitationPlace fetusPalpitationPlace;
	/** Частота сердцебиения плода */
	private Integer fetusPalpitationRate;
	/** Характер сердцебиения плода */
	private VocFetusPalpitationNature fetusPalpitationNature;
	/** Продольный размер ромба Михаэлиса */
	private Integer rhombLongitudinal;
	/** Поперечный размер ромба Михаэлиса */
	private Integer rhombTransversal;
	/** Индекс Соловьева */
	private BigDecimal solovievIndex;
	/** Окружность живота */
	private BigDecimal abdomenCircle;
	/** Высота матки над лоном */
	private BigDecimal uterusHeight;
	/** Положение плода */
	private VocFetusLocation fetusLocation;
	/** Предлежащая часть */
	private VocPreviusPart previusPart;
	/** Высота стояния предлежащей части */
	private BigDecimal previusPartHeight;
	/** Влагалищные выделения */
	private VocVaginalDischarge vaginalDischarge;
	/** Родовая деятельность */
	private VocPregnancyActivity pregnancyActivity;
	/** Размер таза DSp */
	private Integer pelvisDSp;
	/** Размер таза DCr */
	private Integer pelvisDCr;
	/** Размер таза DTr */
	private Integer pelvisDTr;
	/** Размера таза CExt */
	private Integer pelvisCExt;
	/** Размер таза СDiag */
	private Integer pelvisCDiag;
	/** Размер таза CLater */
	private Integer pelvisCLater;

}
