package ru.ecom.mis.ejb.form.medcase.transfusion;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.Transfusion;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Форма "Переливание трансфузионных сред"
 * @author stkacheva
 *
 */
@EntityForm
@EntityFormPersistance(clazz= Transfusion.class)
@Comment("Переливание трансфузионных сред")
@WebTrail(comment = "Переливание трансфузионных сред", nameProperties= "id", view="entitySubclassView-trans_transfusion.do",list = "entityParentList-trans_transfusion.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Transfusion")
@Subclasses({BloodTransfusionForm.class, OtherTransfusionForm.class})
@Setter
public class TransfusionForm extends IdEntityForm {
	/** Дата начала */
	@Comment("Дата начала")
	@DateString @DoDateString @Persist @Required
	public String getStartDate() {return startDate;}

	/** Первичное */
	@Comment("Первичное")
	@Persist
	public Boolean getPrimaryCase() {return primaryCase;}

	/** СМО */
	@Comment("СМО")
	@Persist
	public Long getMedCase() {return medCase;}

	/** Показания к применению */
	@Comment("Показания к применению")
	@Persist @Required
	public Long getReason() {return reason;}

	/** Доза (мл) */
	@Comment("Доза (мл)")
	@Persist @Required
	public Integer getDoze() {return doze;}

	/** Серия */
	@Comment("Серия")
	@Persist @DoUpperCase
	public String getSeries() {return series;}

	/** Дата приготовления */
	@Comment("Дата приготовления")
	@Persist @DateString @DoDateString @Required
	public String getPreparationDate() {return preparationDate;}

	/** Изготовитель */
	@Comment("Изготовитель")
	@Persist @DoUpperCase @Required
	public Long getPreparator() {return preparator;}

	/** Способ переливания */
	@Comment("Способ переливания")
	@Persist
	public Long getTransfusionMethod() {return transfusionMethod;}

	/** Исполнитель */
	@Comment("Исполнитель")
	@Persist @Required
	public Long getExecutor() {return executor;}

	/** Трансфузионная реакция */
	@Comment("Трансфузионная реакция")
	@Persist
	public Long getTransfusionReaction() {return transfusionReaction;}

	/** Осложнения после переливания */
	@Comment("Осложнения после переливания")
	public String getComplications() {return complications;}

	/** Номер в журнале */
	@Comment("Номер в журнале")
	@Persist
	public Integer getJournalNumber() {return journalNumber;}

	/** Дата начала */
	private String startDate;
	/** Первичное */
	private Boolean primaryCase;
	/** СМО */
	private Long medCase;
	/** Показания к применению */
	private Long reason;
	/** Доза (мл) */
	private Integer doze;
	/** Серия */
	private String series;
	/** Дата приготовления */
	private String preparationDate;
	/** Изготовитель */
	private Long preparator;
	/** Способ переливания */
	private Long transfusionMethod;
	/** Исполнитель */
	private Long executor;
	/** Трансфузионная реакция */
	private Long transfusionReaction;
	/** Осложнения после переливания */
	private String complications;
	/** Номер в журнале */
	private Integer journalNumber;	
	
	/** Время переливание с */
	@Comment("Время переливание с")
	@TimeString @DoTimeString @Persist @Required
	public String getTimeFrom() {return timeFrom;}

	/** Время переливания по */
	@Comment("Время переливания по")
	@TimeString @DoTimeString @Persist @Required
	public String getTimeTo() {return timeTo;}

	/** Время переливания по */
	private String timeTo;
	/** Время переливание с */
	private String timeFrom;
	
	/** Фенотип */
	@Comment("Фенотип")
	@Persist
	public String getPhenotype() {return phenotype;}

	/** Фенотип C */
	@Comment("Фенотип C")
	@Persist
	public Boolean getPhenotypeC() {return phenotypeC;}

	/** Фенотип с */
	@Comment("Фенотип с")
	@Persist
	public Boolean getPhenotypec1() {return phenotypec1;}

	/** Фенотип Е */
	@Comment("Фенотип Е")
	@Persist
	public Boolean getPhenotypeD() {return phenotypeD;}

	/** Фенотип e */
	@Comment("Фенотип e")
	@Persist
	public Boolean getPhenotypeE() {return phenotypeE;}

	/** Фенотип E */
	@Comment("Фенотип E")
	@Persist
	public Boolean getPhenotypee1() {return phenotypee1;}

	/** Фенотип не определялся */
	@Comment("Фенотип не определялся")
	@Persist
	public Boolean getPhenotypeNone() {return phenotypeNone;}

	/** Фенотип E */
	private Boolean phenotypee1;	
	/** Фенотип e */
	private Boolean phenotypeE;
	/** Фенотип Е */
	private Boolean phenotypeD;
	/** Фенотип с */
	private Boolean phenotypec1;
	/** Фенотип C */
	private Boolean phenotypeC;
	/** Фенотип не определялся*/
	private Boolean phenotypeNone;
	/** Фенотип */
	private String phenotype;

	/** Срок годности */
	@Comment("Срок годности")
	@Persist @Required @DateString @DoDateString
	public String getExpirationDate() {return expirationDate;}

	/** Номер контейнера */
	@Comment("Номер контейнера")
	@Persist @Required
	public String getContainerNumber() {return containerNumber;}

	/** Исследования антител */
	@Comment("Исследования антител")
	@Persist @Required
	public Long getResearchAntibodies() {return researchAntibodies;}

	/** Выявленные антитела */
	@Comment("Выявленные антитела")
	@Persist
	public String getAntibodiesList() {return antibodiesList;}

	/** Трансфузионный анамнез */
	@Comment("Трансфузионный анамнез")
	@Persist @Required
	public Long getTransfusionHistory() {return transfusionHistory;}

	
	/** Трансфузионный по индив. подбору в прошлом */
	@Comment("Трансфузионный по индив. подбору в прошлом")
	@Persist @Required
	public Long getPersonalTransfusionHistory() {return personalTransfusionHistory;}

	/** Кол-во беременностей */
	@Comment("Кол-во беременностей")
	@Persist
	public String getCountPregnancy() {return countPregnancy;}

	/** Особенности беременности */
	@Comment("Особенности беременности")
	@Persist
	public Long getPregnancyHang() {return pregnancyHang;}

	/** Особенности беременности */
	private Long pregnancyHang;
	/** Кол-во беременностей */
	private String countPregnancy;
	/** Трансфузионный по индив. подбору в прошлом */
	private Long personalTransfusionHistory;
	/** Трансфузионный анамнез */
	private Long transfusionHistory;
	/** Выявленные антитела */
	private String antibodiesList;
	/** Исследования антител */
	private Long researchAntibodies;
	/** Номер контейнера */
	private String containerNumber;
	/** Срок годности */
	private String expirationDate;
	
	/** Реакции на переливания в прошлом */
	@Comment("Реакции на переливания в прошлом")
	@Persist @Required
	public Long getTransfusionReactionLast() {return transfusionReactionLast;}

	/** Реакции на переливания в прошлом */
	private Long transfusionReactionLast;
	
	/** Определение резус-принадлежности рециента производилось */
	@Comment("Определение резус-принадлежности рециента производилось")
	@Persist @Required
	public Long getDefinitionRhesus() {return definitionRhesus;}

	/** Определение резус-принадлежности рециента производилось */
	private Long definitionRhesus;
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return createUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getCreateDate() {return createDate;}

	/** Время создания */
	@Comment("Время создания")
	@Persist @TimeString @DoTimeString
	public String getCreateTime() {return createTime;	}

	/** Пользователь, которые последним редактировал запись */
	@Comment("Пользователь, которые последним редактировал запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Дата редакции */
	@Comment("Дата редакции")
	@Persist @DateString @DoDateString
	public String getEditDate() {return editDate;}

	/** Время редакции */
	@Comment("Время редакции")
	@Persist @TimeString @DoTimeString
	public String getEditTime() {return editTime;}

	/** Время редакции */
	private String editTime;
	/** Дата редакции */
	private String editDate;
	/** Пользователь, которые последним редактировал запись */
	private String editUsername;
	/** Время создания */
	private String createTime;
	/** Дата создания */
	private String createDate;
	/** Пользователь, создавший запись */
	private String createUsername;
}
