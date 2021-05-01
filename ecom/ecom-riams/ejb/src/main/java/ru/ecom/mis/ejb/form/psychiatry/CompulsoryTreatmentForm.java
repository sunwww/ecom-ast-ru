package ru.ecom.mis.ejb.form.psychiatry;


import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.psychiatry.CompulsoryTreatment;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = CompulsoryTreatment.class)
@Comment("Принудительное лечение")
@WebTrail(comment = "Принудительное лечение", nameProperties= "orderNumber",list="entityParentList-psych_compulsoryTreatment.do", view="entityParentView-psych_compulsoryTreatment.do", listComment="список по пациенту")
@Parent(property="careCard", parentForm=PsychiatricCareCardForm.class,orderBy="decisionDate")
@EntityFormSecurityPrefix("/Policy/Mis/Psychiatry/CareCard/CompulsoryTreatment")
@Setter
public class CompulsoryTreatmentForm extends IdEntityForm {
	/**
	  * Порядковый номер лечения
	  */
	 @Comment("Порядковый номер лечения")
	 @Persist @Required
	 public int getOrderNumber() {
	  return orderNumber;
	 }
	 /**
	  * Порядковый номер лечения
	  */
	 private int orderNumber;
	 /**
	  * Дата решения суда
	  */
	 @Comment("Дата решения суда")
	 @DateString @DoDateString @Persist @Required
	 public String getDecisionDate() {
	  return decisionDate;
	 }
	 /**
	  * Дата решения суда
	  */
	 private String decisionDate;
	 /**
	  * Описание решения
	  */
	 @Comment("Описание решения")
	 @Persist
	 public String getDecisionNotes() {
	  return decisionNotes;
	 }
	 /**
	  * Описание решения
	  */
	 private String decisionNotes;
	 /**
	  * Карта обратившегося за психиатрической помощью
	  */
	 @Comment("Карта обратившегося за психиатрической помощью")
	 @Persist @Required
	 public Long getCareCard() {
	  return careCard;
	 }
	 /**
	  * Карта обратившегося за психиатрической помощью
	  */
	 private Long careCard;

	 /**
	  * Вид принудительного лечения
	  */
	 @Comment("Вид принудительного лечения")
	 @Persist @Required
	 public Long getKind() {
	  return kind;
	 }
	 /**
	  * Вид принудительного лечения
	  */
	 private Long kind;
	 /**
	  * Суд, принявший решение
	  */
	 @Comment("Суд, принявший решение")
	 @Persist @Required
	 public Long getLawCourt() {
	  return lawCourt;
	 }
	 /**
	  * Суд, принявший решение
	  */
	 private Long lawCourt;
	 /**
	  * Статья уголовного кодекса
	  */
	 @Comment("Статья уголовного кодекса")
	 @Persist @Required
	 public Long getCrimainalCodeArticle() {
	  return crimainalCodeArticle;
	 }
	 /**
	  * Статья уголовного кодекса
	  */
	 private Long crimainalCodeArticle;
	 /**
	  * Психиатрическая экспертиза
	  */
	 @Comment("Психиатрическая экспертиза")
	 @Persist
	 public Long getPsychatricExamination() {
	  return psychatricExamination;
	 }
	 /**
	  * Психиатрическая экспертиза
	  */
	 private Long psychatricExamination;

	/** Статья уголовного кодекса (инфо) */
	@Comment("Статья уголовного кодекса (инфо)")
	@Persist
	public String getCrimainalCodeArticleInfo() {
		return crimainalCodeArticleInfo;
	}

	/** Статья уголовного кодекса (инфо) */
	private String crimainalCodeArticleInfo;
	
	/** Вид принудительного лечения (инфо) */
	@Comment("Вид принудительного лечения (инфо)")
	@Persist
	public String getKindInfo() {
		return kindInfo;
	}

	/** Вид принудительного лечения (инфо) */
	private String kindInfo;
	
	/** Суд, принявший решений (инфо) */
	@Comment("Суд, принявший решений (инфо)")
	@Persist
	public String getLawCourtInfo() {
		return lawCourtInfo;
	}

	/** Суд, принявший решений (инфо) */
	private String lawCourtInfo;
	
	/** Психиатрическая экспертиза */
	@Comment("Психиатрическая экспертиза")
	@Persist
	public String getPsychatricExaminationInfo() {
		return psychatricExaminationInfo;
	}

	/** Психиатрическая экспертиза */
	private String psychatricExaminationInfo;
	
	 /** Дата регистрации */
	@Comment("Дата регистрации")
	@Persist @DateString @DoDateString @Required
	public String getRegistrationDate() {
		return registrationDate;
	}

	/** Дата регистрации */
	private String registrationDate;
	
	
	 /** Дата замены */
	@Comment("Дата замены")
	@Persist @DoDateString @DateString
	public String getDateReplace() {return dateReplace;}

	/** Суд, заменивший тип принудительного лечения */
	@Comment("Суд, заменивший тип принудительного лечения")
	@Persist
	public Long getLawCourtReplace() {return lawCourtReplace;}

	/** Решение заменено на */
	@Comment("Решение заменено на")
	@Persist
	public Long getCourtDecisionReplace() {
		return courtDecisionReplace;
	}

	/** Решение заменено на */
	private Long courtDecisionReplace;
	/** Суд, заменивший тип принудительного лечения */
	private Long lawCourtReplace;
	/** Дата замены */
	private String dateReplace;
	/** Дата регистрации замены */
	@Comment("Дата регистрации замены")
	@Persist @DateString @DoDateString
	public String getRegistrationReplaceDate() {
		return registrationReplaceDate;
	}

	/** Дата регистрации замены */
	private String registrationReplaceDate;
	 /** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return createDate;}
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist @DoDateString @DateString
	public String getEditDate() {return editDate;}
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return createUsername;}
	/** Пользователь, последний редактировавший запись */
	@Comment("Пользователь, последний редактировавший запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Время создания */
	@Comment("Время создания")
	@Persist @TimeString @DoTimeString
	public String getCreateTime() {return createTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@Persist @TimeString @DoTimeString
	public String getEditTime() {return editTime;}

	/** Время редактрования */
	private String editTime;
	/** Время создания */
	private String createTime;	/** Пользователь, последний редактировавший запись */
	private String editUsername;
	/** Пользователь, создавший запись */
	private String createUsername;
	/** Дата редактирования */
	private String editDate;
	/** Дата создания */
	private String createDate;
}
