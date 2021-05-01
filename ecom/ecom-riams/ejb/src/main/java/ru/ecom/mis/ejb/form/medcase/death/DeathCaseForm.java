package ru.ecom.mis.ejb.form.medcase.death;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.ejb.services.entityform.interceptors.*;
import ru.ecom.mis.ejb.domain.medcase.hospital.DeathCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDeathEvidence;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.medcase.death.interceptors.DeathCasePreCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.death.interceptors.DeathCaseSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.death.interceptors.DeathCaseViewInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Mkb;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@Comment("Случай смерти")
@EntityForm
@EntityFormPersistance(clazz=DeathCase.class)
@WebTrail(comment = "Случай смерти", nameProperties= "id", view="entityParentView-stac_deathCase.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/DeathCase")
@AViewInterceptors(
        @AEntityFormInterceptor(DeathCaseViewInterceptor.class)
)
@ASaveInterceptors(
        @AEntityFormInterceptor(DeathCaseSaveInterceptor.class)
)
@ACreateInterceptors( {
	@AEntityFormInterceptor(DeathCaseSaveInterceptor.class)
})
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(DeathCasePreCreateInterceptor.class)
)
@Setter

public class DeathCaseForm extends IdEntityForm{
	/** Мед. случай */
	@Comment("Мед. случай")
	@Persist @Required
	public Long getMedCase() {return medCase;}

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return patient;}

	/** Мать */
	@Comment("Мать")
	@Persist
	public Long getMother() {return mother;}

	/** Дата смерти */
	@Comment("Дата смерти")
	@DateString @DoDateString @Persist
	public String getDeathDate() {return deathDate;	}

	/** Время смерти */
	@Comment("Время смерти")
	@TimeString @DoTimeString @Persist
	public String getDeathTime() {return deathTime;}

	/** Доношенность */
	@Comment("Доношенность")
	@Persist
	public Long getIsPrematurity() {return isPrematurity;}

	/** Масса (вес) при рождении (грамм) */
	@Comment("Масса (вес) при рождении (грамм)")
	@Persist
	public Integer getBirthWeight() {return birthWeight;}

	/** Какой ребенок по счету у матери */
	@Comment("Какой ребенок по счету у матери")
	@Persist 
	public Integer getBabyNumber() {return babyNumber;}

	/** Место смерти */
	@Comment("Место смерти")
	@Persist
	public Long getDeathPlace() {return deathPlace;}

	/** Адрес места смерти */
	@Comment("Адрес места смерти")
	@Persist
	public Long getDeathPlaceAddress() {return deathPlaceAddress;}

	/** Причина смерти */
	@Comment("Причина смерти")
	@Persist
	public Long getDeathReason() {return deathReason;	}

	/** Дата травмы (отравления) */
	@Comment("Дата травмы (отравления)")
	@Persist @DateString @DoDateString
	public String getAccidentDate() {return accidentDate;}

	/** Место, при которых произошла травма (отравление) */
	@Comment("Место, при которых произошла травма (отравление)")
	@Persist
	public String getAccidentPlace() {return accidentPlace;}

	/** Обстоятельства, при которых произошла травма (отравление) */
	@Comment("Обстоятельства, при которых произошла травма (отравление)")
	@Persist
	public String getAccidentCircumstance() {return accidentCircumstance;}

	/** Врач (фельдшер) */
	@Comment("Врач (фельдшер)")
	@Persist @Required
	public Long getDeathWitness() {return deathWitness;}

	/** Причина смерти установлена */
	@Comment("Причина смерти установлена")
	@Persist
	public Long getDeathWitnessFunction() {return deathWitnessFunction;}

	/** На основании чего установлена смерть */
	@Comment("На основании чего установлена смерть")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=VocDeathEvidence.class)
	public String getDeathEvidence() {return deathEvidence;}

	/** Умерла после окончания родов */
	@Comment("Умерла после окончания родов")
	@Persist
	public Long getAfterPregnance() {return afterPregnance;}

	/** Место рождения */
	@Comment("Место рождения")
	@Persist
	public String getBirthPlace() {
		return birthPlace;
	}

	/** Место рождения */
	private String birthPlace;
	
	/** Адрес места рождения */
	@Comment("Адрес места рождения")
	@Persist
	public String getBirthPlaceAdress() {return birthPlaceAdress;}

	/** Адрес места рождения */
	private String birthPlaceAdress;
	/** Умерла после окончания родов */
	private Long afterPregnance;
	/** На основании чего установлена смерть */
	private String deathEvidence;
	/** Причина смерти установлена */
	private Long deathWitnessFunction;
	/** Врач (фельдшер) */
	private Long deathWitness;
	/** Обстоятельства, при которых произошла травма (отравление) */
	private String accidentCircumstance;
	/** Место, при которых произошла травма (отравление) */
	private String accidentPlace;
	/** Дата травмы (отравления) */
	private String accidentDate;
	/** Причина смерти */
	private Long deathReason;
	/** Адрес места смерти */
	private Long deathPlaceAddress;
	/** Место смерти */
	private Long deathPlace;
	/** Какой ребенок по счету у матери */
	private Integer babyNumber;
	/** Масса (вес) при рождении (грамм) */
	private Integer birthWeight;
	/** Доношенность */
	private Long isPrematurity;
	/** Время смерти */
	private String deathTime;
	/** Дата смерти */
	private String deathDate;
	/** Мать */
	private Long mother;
	/** Пациент */
	private Long patient;
	/** Мед. случай */
	private Long medCase;

	/** Ятрогения */
	@Comment("Ятрогения")
	@Persist
	public Long getLatrogeny() {return latrogeny;}

	/** Категория расхождения между диагнозами после патан. */
	@Comment("Категория расхождения между диагнозами после патан.")
	@Persist
	public Long getCategoryDifference() {return categoryDifference;}

	/** Описание причины смерти */
	@Comment("Описание причины смерти")
	@Persist
	public String getCommentReason() {return commentReason;}

	/** Комментарий к категории смерти */
	@Comment("Комментарий к категории смерти")
	@Persist
	public String getCommentCategory() {return commentCategory;}

	/** Номер в патологоанатомическом бюро */
	@Comment("Номер в патологоанатомическом бюро")
	@Persist
	public String getPostmortemBureauNumber() {return postmortemBureauNumber;}

	/** Дата ПАБ */
	@Comment("Дата ПАБ")
	@Persist @DateString @DoDateString
	public String getPostmortemBureauDt() {return postmortemBureauDt;}

	/** Квартира, где умер человек */
	@Comment("Квартира, где умер человек")
	@Persist
	public String getDeathPlaceFlatNumber() {return deathPlaceFlatNumber;}

	/** Дом, где умер человекhPlaceHouseNumber */
	@Comment("Дом, где умер человек")
	@Persist
	public String getDeathPlaceHouseNumber() {return deathPlaceHouseNumber;}

	/** Копрус дома, где умер человек */
	@Comment("Копрус дома, где умер человек")
	@Persist
	public String getDeathPlaceHouseBuilding() {return deathPlaceHouseBuilding;}

	/** Острота диагноза клинического */
	@Comment("Острота диагноза клинического")
	public Long getConcludingActuity() {return concludingActuity;}

	/** Патанатомический диагноз */
	@Comment("Патанатомический диагноз")
	public String getPathanatomicalDiagnos() {return pathanatomicalDiagnos;}

	/** Патанатомический диагноз по МКБ-10 */
	@Comment("Патанатомический диагноз по МКБ-10")
	@Mkb
	public Long getPathanatomicalMkb() {return pathanatomicalMkb;}

	/** Заключительный диагноз */
	@Comment("Заключительный диагноз")
	public String getConcludingDiagnos() {return concludingDiagnos;}

	/** Заключительный диагноз по МКБ-10 */
	@Comment("Заключительный диагноз по МКБ-10")
	@Mkb
	public Long getConcludingMkb() {return concludingMkb;}

	/** Заключительный диагноз по МКБ-10 */
	private Long concludingMkb;
	/** Заключительный диагноз */
	private String concludingDiagnos;
	/** Патанатомический диагноз по МКБ-10 */
	private Long pathanatomicalMkb;
	/** Патанатомический диагноз */
	private String pathanatomicalDiagnos;
	/** Острота диагноза клинического */
	private Long concludingActuity;
	/** Копрус дома, где умер человек */
	private String deathPlaceHouseBuilding;
	/** Дом, где умер человекhPlaceHouseNumber */
	private String deathPlaceHouseNumber;
	/** Квартира, где умер человек */
	private String deathPlaceFlatNumber;
	/** Дата ПАБ */
	private String postmortemBureauDt;
	/** Номер в патологоанатомическом бюро */
	private String postmortemBureauNumber;
	/** Комментарий к категории смерти */
	private String commentCategory;
	/** Описание причины смерти */
	private String commentReason;
	/** Категория расхождения между диагнозами после патан. */
	private Long categoryDifference;
	/** Ятрогения */
	private Long latrogeny;

	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return createDate;}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return editDate;}

	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return createTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return editTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return createUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private String editTime;
	/** Время создания */
	private String createTime;
	/** Дата редактирования */
	private String editDate;
	/** Дата создания */
	private String createDate;

	/** Тип диагноза категории расхождения */
	@Comment("Тип диагноза категории расхождения")
	@Persist
	public Long getDiagnosisDifference() {return diagnosisDifference;}

	/** Дата СМЭ */
	@Comment("Дата СМЭ")
	@Persist @DateString @DoDateString
	public String getDateForensic() {return dateForensic;}

	/** Произведено вскрытие */
	@Comment("Произведено вскрытие")
	@Persist
	public Boolean getIsAutopsy() {return isAutopsy;}

	/** Тип диагноза категории расхождения */
	private Long diagnosisDifference;
	/** Произведено вскрытие */
	private Boolean isAutopsy;
	/** Дата СМЭ */
	private String dateForensic;
	
	/** Код мкб */
	@Comment("Код мкб")
	@Persist @Required
	public Long getReasonMainMkb() {return reasonMainMkb;}

	/** Код мкб */
	private Long reasonMainMkb;
	
	/** Код мкб осложнения */
	@Comment("Код мкб осложнения")
	@Persist 
	public Long getReasonComplicationMkb() {return reasonComplicationMkb;}

	/** Код мкб осложнения */
	private Long reasonComplicationMkb;
	
	/** Код мкб сопутсвующий */
	@Comment("Код мкб сопутсвующий")
	@Persist 
	public Long getReasonConcomitantMkb() {return reasonConcomitantMkb;}

	/** Код мкб сопутсвующий */
	private Long reasonConcomitantMkb;
	/** Присутствие врача на вскрытие */
	@Comment("Присутствие врача на вскрытие")
	@Persist
	public Boolean getIsPresenceDoctorAutopsy() {return isPresenceDoctorAutopsy;}	

	/** Присутствие врача на вскрытие */
	private Boolean isPresenceDoctorAutopsy;
	
	/** Текст мкб осложнения */
	@Comment("Текст мкб осложнения")
	@Persist
	public String getReasonComplicationText() {return reasonComplicationText;}

	/** Сопутствующий диагноз текст */
	@Comment("Сопутствующий диагноз текст")
	@Persist
	public String getReasonConcomitantText() {return reasonConcomitantText;}

	/** Конкурирующее заболевание */
	@Comment("Конкурирующее заболевание")
	@Persist
	public String getCompetingDisease() {return competingDisease;}

	/** Сочетанное заболевание */
	@Comment("Сочетанное заболевание")
	@Persist
	public String getPolypathia() {return polypathia;}

	/** Фоновое заболевание */
	@Comment("Фоновое заболевание")
	@Persist
	public String getBackgroundDisease() {return backgroundDisease;}

	/** Мертворождение */
	@Comment("Мертворождение")
	@Persist
	public Boolean getIsNeonatologic() {return isNeonatologic;}

	/** История развития новорождённого - только для акушерских случаев */
	public String getNewBornHistory() {return newBornHistory;}

	/** Фоновое заболевание */
	private String backgroundDisease;
	/** Сочетанное заболевание */
	private String polypathia;
	/** Конкурирующее заболевание */
	private String competingDisease;
	/** Сопутствующий диагноз текст */
	private String reasonConcomitantText;	
	/** Текст мкб осложнения */
	private String reasonComplicationText;
	/** Мертворождение */
	private Boolean isNeonatologic;
	/** История развития новорождённого - только для акушерских случаев */
	private String newBornHistory;
}
