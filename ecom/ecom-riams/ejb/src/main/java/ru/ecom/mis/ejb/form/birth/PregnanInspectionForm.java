package ru.ecom.mis.ejb.form.birth;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.birth.PregnanInspection;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.validators.IntegerString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Осмотр беременной
 * @author stkacheva
 *
 */
@EntityForm
@EntityFormPersistance(clazz= PregnanInspection.class)
@Comment("Осмотр беременной")
@WebTrail(comment = "Осмотр беременной", nameProperties= "id", view="entitySubclassView-preg_inspection.do" ,list = "entityParentList-preg_inspection.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Inspection/Pregnancy")
public class PregnanInspectionForm extends InspectionForm {
	/** Размер таза DSp */
	@Comment("Размер таза DSp")
	@Persist @Required @DoIntegerString @IntegerString
	public Integer getPelvisDSp() {return thePelvisDSp;}
	public void setPelvisDSp(Integer aPelvisDSp) {thePelvisDSp = aPelvisDSp;	}

	/** Размер таза DCr */
	@Comment("Размер таза DCr")
	@Persist @Required @DoIntegerString @IntegerString
	public Integer getPelvisDCr() {return thePelvisDCr;}
	public void setPelvisDCr(Integer aPelvisDCr) {thePelvisDCr = aPelvisDCr;}

	/** Размер таза DTr */
	@Comment("Размер таза DTr")
	@Persist @Required @DoIntegerString @IntegerString
	public Integer getPelvisDTr() {return thePelvisDTr;}
	public void setPelvisDTr(Integer aPelvisDTr) {thePelvisDTr = aPelvisDTr;}

	/** Размера таза CExt */
	@Comment("Размера таза CExt")
	@Persist @Required @DoIntegerString @IntegerString
	public Integer getPelvisCExt() {return thePelvisCExt;}
	public void setPelvisCExt(Integer aPelvisCExt) {thePelvisCExt = aPelvisCExt;}
	
	/** Размер таза СDiag */
	@Comment("Размер таза СDiag")
	@Persist @Required @DoIntegerString @IntegerString
	public Integer getPelvisCDiag() {return thePelvisCDiag;}
	public void setPelvisCDiag(Integer aPelvisCDiag) {thePelvisCDiag = aPelvisCDiag;}
	
	/** Размер таза CLater */
	@Comment("Размер таза CLater")
	@Persist @DoIntegerString @IntegerString
	public Integer getPelvisCLater() {return thePelvisCLater;}
	public void setPelvisCLater(Integer aPelvisCLater) {thePelvisCLater = aPelvisCLater;}

	/** Продольный размер ромба Михаэлиса */
	@Comment("Продольный размер ромба Михаэлиса")
	@Persist @DoIntegerString @IntegerString
	public Integer getRhombLongitudinal() {return theRhombLongitudinal;}
	public void setRhombLongitudinal(Integer aRhombLongitudinal) {theRhombLongitudinal = aRhombLongitudinal;}

	/** Поперечный размер ромба Михаэлиса */
	@Comment("Поперечный размер ромба Михаэлиса")
	@Persist @DoIntegerString @IntegerString
	public Integer getRhombTransversal() {return theRhombTransversal;}
	public void setRhombTransversal(Integer aRhombTransversal) {theRhombTransversal = aRhombTransversal;}

	/** Индекс Соловьева */
	@Comment("Индекс Соловьева")
	@Persist @Required
	public String getSolovievIndex() {return theSolovievIndex;}
	public void setSolovievIndex(String aSolovievIndex) {theSolovievIndex = aSolovievIndex;}

	/** Окружность живота */
	@Comment("Окружность живота")
	@Persist @Required
	public String getAbdomenCircle() {return theAbdomenCircle;}
	public void setAbdomenCircle(String aAbdomenCircle) {theAbdomenCircle = aAbdomenCircle;}

	/** Высота матки над лоном */
	@Comment("Высота матки над лоном")
	@Persist 
	public String getUterusHeight() {return theUterusHeight;}
	public void setUterusHeight(String aUterusHeight) {theUterusHeight = aUterusHeight;}

	/** Положение плода */
	@Comment("Положение плода")
	@Persist
	public Long getFetusLocation() {return theFetusLocation;}
	public void setFetusLocation(Long aFetusLocation) {theFetusLocation = aFetusLocation;}

	/** Предлежащая часть */
	@Comment("Предлежащая часть")
	@Persist
	public Long getPreviusPart() {return thePreviusPart;}
	public void setPreviusPart(Long aPreviusPart) {thePreviusPart = aPreviusPart;}

	/** Высота стояния предлежащей части */
	@Comment("Высота стояния предлежащей части")
	@Persist 
	public String getPreviusPartHeight() {return thePreviusPartHeight;}
	public void setPreviusPartHeight(String aPreviusPartHeight) {thePreviusPartHeight = aPreviusPartHeight;}

	/** Родовая деятельность */
	@Comment("Родовая деятельность")
	@Persist
	public Long getPregnancyActivity() {return thePregnancyActivity;}
	public void setPregnancyActivity(Long aPregnancyActivity) {thePregnancyActivity = aPregnancyActivity;}
	
	/** Влагалищные выделения */
	@Comment("Влагалищные выделения")
	@Persist
	public Long getVaginalDischarge() {return theVaginalDischarge;}
	public void setVaginalDischarge(Long aVaginalDischarge) {theVaginalDischarge = aVaginalDischarge;}


	/** Место сердцебиения плода */
	@Comment("Место сердцебиения плода")
	@Persist
	public Long getFetusPalpitationPlace() {return theFetusPalpitationPlace;}
	public void setFetusPalpitationPlace(Long aFetusPalpitationPlace) {theFetusPalpitationPlace = aFetusPalpitationPlace;	}
	
	/** Частота сердцебиения плода */
	@Comment("Частота сердцебиения плода")
	@Persist @DoIntegerString @IntegerString
	public Integer getFetusPalpitationRate() {return theFetusPalpitationRate;}
	public void setFetusPalpitationRate(Integer aFetusPalpitationRate) {theFetusPalpitationRate = aFetusPalpitationRate;}

	
	/** Характер сердцебиения плода */
	@Comment("Характер сердцебиения плода")
	@Persist
	public Long getFetusPalpitationNature() {return theFetusPalpitationNature;}
	public void setFetusPalpitationNature(Long aFetusPalpitationNature) {theFetusPalpitationNature = aFetusPalpitationNature;}

	/** Место сердцебиения плода */
	private Long theFetusPalpitationPlace;
	/** Частота сердцебиения плода */
	private Integer theFetusPalpitationRate;
	/** Характер сердцебиения плода */
	private Long theFetusPalpitationNature;	
	/** Продольный размер ромба Михаэлиса */
	private Integer theRhombLongitudinal;
	/** Поперечный размер ромба Михаэлиса */
	private Integer theRhombTransversal;
	/** Индекс Соловьева */
	private String theSolovievIndex;
	/** Окружность живота */
	private String theAbdomenCircle;
	/** Высота матки над лоном */
	private String theUterusHeight;
	/** Положение плода */
	private Long theFetusLocation;
	/** Предлежащая часть */
	private Long thePreviusPart;
	/** Высота стояния предлежащей части */
	private String thePreviusPartHeight;
	/** Влагалищные выделения */
	private Long theVaginalDischarge;
	/** Родовая деятельность */
	private Long thePregnancyActivity;
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
