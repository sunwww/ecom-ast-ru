package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
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
@Setter
public class PregnanInspectionForm extends InspectionForm {
	/** Размер таза DSp */
	@Comment("Размер таза DSp")
	@Persist @Required @DoIntegerString @IntegerString
	public Integer getPelvisDSp() {return pelvisDSp;}

	/** Размер таза DCr */
	@Comment("Размер таза DCr")
	@Persist @Required @DoIntegerString @IntegerString
	public Integer getPelvisDCr() {return pelvisDCr;}

	/** Размер таза DTr */
	@Comment("Размер таза DTr")
	@Persist @Required @DoIntegerString @IntegerString
	public Integer getPelvisDTr() {return pelvisDTr;}

	/** Размера таза CExt */
	@Comment("Размера таза CExt")
	@Persist @Required @DoIntegerString @IntegerString
	public Integer getPelvisCExt() {return pelvisCExt;}

	/** Размер таза СDiag */
	@Comment("Размер таза СDiag")
	@Persist @Required @DoIntegerString @IntegerString
	public Integer getPelvisCDiag() {return pelvisCDiag;}

	/** Размер таза CLater */
	@Comment("Размер таза CLater")
	@Persist @DoIntegerString @IntegerString
	public Integer getPelvisCLater() {return pelvisCLater;}

	/** Продольный размер ромба Михаэлиса */
	@Comment("Продольный размер ромба Михаэлиса")
	@Persist @DoIntegerString @IntegerString
	public Integer getRhombLongitudinal() {return rhombLongitudinal;}

	/** Поперечный размер ромба Михаэлиса */
	@Comment("Поперечный размер ромба Михаэлиса")
	@Persist @DoIntegerString @IntegerString
	public Integer getRhombTransversal() {return rhombTransversal;}

	/** Индекс Соловьева */
	@Comment("Индекс Соловьева")
	@Persist @Required
	public String getSolovievIndex() {return solovievIndex;}

	/** Окружность живота */
	@Comment("Окружность живота")
	@Persist @Required
	public String getAbdomenCircle() {return abdomenCircle;}

	/** Высота матки над лоном */
	@Comment("Высота матки над лоном")
	@Persist 
	public String getUterusHeight() {return uterusHeight;}

	/** Положение плода */
	@Comment("Положение плода")
	@Persist
	public Long getFetusLocation() {return fetusLocation;}

	/** Предлежащая часть */
	@Comment("Предлежащая часть")
	@Persist
	public Long getPreviusPart() {return previusPart;}

	/** Высота стояния предлежащей части */
	@Comment("Высота стояния предлежащей части")
	@Persist 
	public String getPreviusPartHeight() {return previusPartHeight;}

	/** Родовая деятельность */
	@Comment("Родовая деятельность")
	@Persist
	public Long getPregnancyActivity() {return pregnancyActivity;}

	/** Влагалищные выделения */
	@Comment("Влагалищные выделения")
	@Persist
	public Long getVaginalDischarge() {return vaginalDischarge;}


	/** Место сердцебиения плода */
	@Comment("Место сердцебиения плода")
	@Persist
	public Long getFetusPalpitationPlace() {return fetusPalpitationPlace;}

	/** Частота сердцебиения плода */
	@Comment("Частота сердцебиения плода")
	@Persist @DoIntegerString @IntegerString
	public Integer getFetusPalpitationRate() {return fetusPalpitationRate;}

	
	/** Характер сердцебиения плода */
	@Comment("Характер сердцебиения плода")
	@Persist
	public Long getFetusPalpitationNature() {return fetusPalpitationNature;}

	/** Место сердцебиения плода */
	private Long fetusPalpitationPlace;
	/** Частота сердцебиения плода */
	private Integer fetusPalpitationRate;
	/** Характер сердцебиения плода */
	private Long fetusPalpitationNature;	
	/** Продольный размер ромба Михаэлиса */
	private Integer rhombLongitudinal;
	/** Поперечный размер ромба Михаэлиса */
	private Integer rhombTransversal;
	/** Индекс Соловьева */
	private String solovievIndex;
	/** Окружность живота */
	private String abdomenCircle;
	/** Высота матки над лоном */
	private String uterusHeight;
	/** Положение плода */
	private Long fetusLocation;
	/** Предлежащая часть */
	private Long previusPart;
	/** Высота стояния предлежащей части */
	private String previusPartHeight;
	/** Влагалищные выделения */
	private Long vaginalDischarge;
	/** Родовая деятельность */
	private Long pregnancyActivity;
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
