package ru.ecom.mis.ejb.domain.birth;

/**
 * Осмотр беременной
 */

import ru.ecom.mis.ejb.domain.birth.voc.*;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Entity
@Comment("Осмотр беременной")
public class PregnanInspection extends Inspection {
	
	/** Размер таза DSp */
	@Comment("Размер таза DSp")
	public Integer getPelvisDSp() {return thePelvisDSp;}
	public void setPelvisDSp(Integer aPelvisDSp) {thePelvisDSp = aPelvisDSp;	}

	/** Размер таза DCr */
	@Comment("Размер таза DCr")
	public Integer getPelvisDCr() {return thePelvisDCr;}
	public void setPelvisDCr(Integer aPelvisDCr) {thePelvisDCr = aPelvisDCr;}

	/** Размер таза DTr */
	@Comment("Размер таза DTr")
	public Integer getPelvisDTr() {return thePelvisDTr;}
	public void setPelvisDTr(Integer aPelvisDTr) {thePelvisDTr = aPelvisDTr;}

	/** Размера таза CExt */
	@Comment("Размера таза CExt")
	public Integer getPelvisCExt() {return thePelvisCExt;}
	public void setPelvisCExt(Integer aPelvisCExt) {thePelvisCExt = aPelvisCExt;}
	
	/** Размер таза СDiag */
	@Comment("Размер таза СDiag")
	public Integer getPelvisCDiag() {return thePelvisCDiag;}
	public void setPelvisCDiag(Integer aPelvisCDiag) {thePelvisCDiag = aPelvisCDiag;}
	
	/** Размер таза CLater */
	@Comment("Размер таза CLater")
	public Integer getPelvisCLater() {return thePelvisCLater;}
	public void setPelvisCLater(Integer aPelvisCLater) {thePelvisCLater = aPelvisCLater;}

	/** Продольный размер ромба Михаэлиса */
	@Comment("Продольный размер ромба Михаэлиса")
	public Integer getRhombLongitudinal() {return theRhombLongitudinal;}
	public void setRhombLongitudinal(Integer aRhombLongitudinal) {theRhombLongitudinal = aRhombLongitudinal;}

	/** Поперечный размер ромба Михаэлиса */
	@Comment("Поперечный размер ромба Михаэлиса")
	public Integer getRhombTransversal() {return theRhombTransversal;}
	public void setRhombTransversal(Integer aRhombTransversal) {theRhombTransversal = aRhombTransversal;}

	/** Индекс Соловьева */
	@Comment("Индекс Соловьева")
	public BigDecimal getSolovievIndex() {return theSolovievIndex;}
	public void setSolovievIndex(BigDecimal aSolovievIndex) {theSolovievIndex = aSolovievIndex;}

	/** Окружность живота */
	@Comment("Окружность живота")
	public BigDecimal getAbdomenCircle() {return theAbdomenCircle;}
	public void setAbdomenCircle(BigDecimal aAbdomenCircle) {theAbdomenCircle = aAbdomenCircle;}

	/** Высота матки над лоном */
	@Comment("Высота матки над лоном")
	public BigDecimal getUterusHeight() {return theUterusHeight;}
	public void setUterusHeight(BigDecimal aUterusHeight) {theUterusHeight = aUterusHeight;}

	/** Положение плода */
	@Comment("Положение плода")
	@OneToOne
	public VocFetusLocation getFetusLocation() {return theFetusLocation;}
	public void setFetusLocation(VocFetusLocation aFetusLocation) {theFetusLocation = aFetusLocation;}

	/** Предлежащая часть */
	@Comment("Предлежащая часть")
	@OneToOne
	public VocPreviusPart getPreviusPart() {return thePreviusPart;}
	public void setPreviusPart(VocPreviusPart aPreviusPart) {thePreviusPart = aPreviusPart;}

	/** Высота стояния предлежащей части */
	@Comment("Высота стояния предлежащей части")
	public BigDecimal getPreviusPartHeight() {return thePreviusPartHeight;}
	public void setPreviusPartHeight(BigDecimal aPreviusPartHeight) {thePreviusPartHeight = aPreviusPartHeight;}

	/** Родовая деятельность */
	@Comment("Родовая деятельность")
	@OneToOne
	public VocPregnancyActivity getPregnancyActivity() {return thePregnancyActivity;}
	public void setPregnancyActivity(VocPregnancyActivity aPregnancyActivity) {thePregnancyActivity = aPregnancyActivity;}
	
	/** Влагалищные выделения */
	@Comment("Влагалищные выделения")
	@OneToOne
	public VocVaginalDischarge getVaginalDischarge() {return theVaginalDischarge;}
	public void setVaginalDischarge(VocVaginalDischarge aVaginalDischarge) {theVaginalDischarge = aVaginalDischarge;}

	/** Место сердцебиения плода */
	@Comment("Место сердцебиения плода")
	@OneToOne
	public VocFetusPalpitationPlace getFetusPalpitationPlace() {return theFetusPalpitationPlace;}
	public void setFetusPalpitationPlace(VocFetusPalpitationPlace aFetusPalpitationPlace) {theFetusPalpitationPlace = aFetusPalpitationPlace;	}
	
	/** Частота сердцебиения плода */
	@Comment("Частота сердцебиения плода")
	public Integer getFetusPalpitationRate() {return theFetusPalpitationRate;}
	public void setFetusPalpitationRate(Integer aFetusPalpitationRate) {theFetusPalpitationRate = aFetusPalpitationRate;}

	
	/** Характер сердцебиения плода */
	@Comment("Характер сердцебиения плода")
	@OneToOne
	public VocFetusPalpitationNature getFetusPalpitationNature() {return theFetusPalpitationNature;}
	public void setFetusPalpitationNature(VocFetusPalpitationNature aFetusPalpitationNature) {theFetusPalpitationNature = aFetusPalpitationNature;}

	@Transient
	public String getInformation() {
		// Размеры таза
		return "Размеры таза: D Sp" + thePelvisDSp +
				" D Cr " + thePelvisDCr +
				" D Tr " + thePelvisDTr +
				"C ext " + thePelvisCExt +
				"C diag " + thePelvisCDiag +
				"C later " + thePelvisCLater +
				//Ромб Михаэлиса
				". Ромб Михаэлиса прод." + theRhombLongitudinal + " попер. " + theRhombTransversal +
				//Индекс Соловьева
				". Индекс Соловьева " + theSolovievIndex;
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
	private VocFetusPalpitationPlace theFetusPalpitationPlace;
	/** Частота сердцебиения плода */
	private Integer theFetusPalpitationRate;
	/** Характер сердцебиения плода */
	private VocFetusPalpitationNature theFetusPalpitationNature;
	/** Продольный размер ромба Михаэлиса */
	private Integer theRhombLongitudinal;
	/** Поперечный размер ромба Михаэлиса */
	private Integer theRhombTransversal;
	/** Индекс Соловьева */
	private BigDecimal theSolovievIndex;
	/** Окружность живота */
	private BigDecimal theAbdomenCircle;
	/** Высота матки над лоном */
	private BigDecimal theUterusHeight;
	/** Положение плода */
	private VocFetusLocation theFetusLocation;
	/** Предлежащая часть */
	private VocPreviusPart thePreviusPart;
	/** Высота стояния предлежащей части */
	private BigDecimal thePreviusPartHeight;
	/** Влагалищные выделения */
	private VocVaginalDischarge theVaginalDischarge;
	/** Родовая деятельность */
	private VocPregnancyActivity thePregnancyActivity;
	/** Размер таза DSp */
	private Integer thePelvisDSp;
	/** Размер таза DCr */
	private Integer thePelvisDCr;
	/** Размер таза DTr */
	private Integer thePelvisDTr;
	/** Размера таза CExt */
	private Integer thePelvisCExt;
	/** Размер таза СDiag */
	private Integer thePelvisCDiag;
	/** Размер таза CLater */
	private Integer thePelvisCLater;

}
