package ru.ecom.mis.ejb.form.medcase.death;

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

public class DeathCaseForm extends IdEntityForm{
	/** Мед. случай */
	@Comment("Мед. случай")
	@Persist @Required
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}

	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	/** Мать */
	@Comment("Мать")
	@Persist
	public Long getMother() {return theMother;}
	public void setMother(Long aMother) {theMother = aMother;}

	/** Дата смерти */
	@Comment("Дата смерти")
	@DateString @DoDateString @Persist
	public String getDeathDate() {return theDeathDate;	}
	public void setDeathDate(String aDateDeath) {theDeathDate = aDateDeath;}

	/** Время смерти */
	@Comment("Время смерти")
	@TimeString @DoTimeString @Persist
	public String getDeathTime() {return theDeathTime;}
	public void setDeathTime(String aTimeDeath) {theDeathTime = aTimeDeath;}

	/** Доношенность */
	@Comment("Доношенность")
	@Persist
	public Long getIsPrematurity() {return theIsPrematurity;}
	public void setIsPrematurity(Long aIsPrematurity) {theIsPrematurity = aIsPrematurity;}

	/** Масса (вес) при рождении (грамм) */
	@Comment("Масса (вес) при рождении (грамм)")
	@Persist
	public Integer getBirthWeight() {return theBirthWeight;}
	public void setBirthWeight(Integer aBirthWeight) {theBirthWeight = aBirthWeight;}

	/** Какой ребенок по счету у матери */
	@Comment("Какой ребенок по счету у матери")
	@Persist 
	public Integer getBabyNumber() {return theBabyNumber;}
	public void setBabyNumber(Integer aBabyNumber) {theBabyNumber = aBabyNumber;}

	/** Место смерти */
	@Comment("Место смерти")
	@Persist
	public Long getDeathPlace() {return theDeathPlace;}
	public void setDeathPlace(Long aDeathPlace) {theDeathPlace = aDeathPlace;	}

	/** Адрес места смерти */
	@Comment("Адрес места смерти")
	@Persist
	public Long getDeathPlaceAddress() {return theDeathPlaceAddress;}
	public void setDeathPlaceAddress(Long aDeathPlaceAddress) {theDeathPlaceAddress = aDeathPlaceAddress;}

	/** Причина смерти */
	@Comment("Причина смерти")
	@Persist
	public Long getDeathReason() {return theDeathReason;	}
	public void setDeathReason(Long aDeathReason) {theDeathReason = aDeathReason;}

	/** Дата травмы (отравления) */
	@Comment("Дата травмы (отравления)")
	@Persist @DateString @DoDateString
	public String getAccidentDate() {return theAccidentDate;}
	public void setAccidentDate(String aAccidentDate) {theAccidentDate = aAccidentDate;}

	/** Место, при которых произошла травма (отравление) */
	@Comment("Место, при которых произошла травма (отравление)")
	@Persist
	public String getAccidentPlace() {return theAccidentPlace;}
	public void setAccidentPlace(String aAccidentPlace) {theAccidentPlace = aAccidentPlace;}

	/** Обстоятельства, при которых произошла травма (отравление) */
	@Comment("Обстоятельства, при которых произошла травма (отравление)")
	@Persist
	public String getAccidentCircumstance() {return theAccidentCircumstance;}
	public void setAccidentCircumstance(String aCircumstance) {theAccidentCircumstance = aCircumstance;}


	/** Врач (фельдшер) */
	@Comment("Врач (фельдшер)")
	@Persist @Required
	public Long getDeathWitness() {return theDeathWitness;}
	public void setDeathWitness(Long aDeathWitness) {theDeathWitness = aDeathWitness;}

	/** Причина смерти установлена */
	@Comment("Причина смерти установлена")
	@Persist
	public Long getDeathWitnessFunction() {return theDeathWitnessFunction;}
	public void setDeathWitnessFunction(Long aDeathWitnessFunction) {theDeathWitnessFunction = aDeathWitnessFunction;}

	/** На основании чего установлена смерть */
	@Comment("На основании чего установлена смерть")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=VocDeathEvidence.class)
	public String getDeathEvidence() {return theDeathEvidence;}
	public void setDeathEvidence(String aDeathEvidence) {theDeathEvidence = aDeathEvidence;	}

	/** Умерла после окончания родов */
	@Comment("Умерла после окончания родов")
	@Persist
	public Long getAfterPregnance() {return theAfterPregnance;}
	public void setAfterPregnance(Long aAfterPregnance) {theAfterPregnance = aAfterPregnance;}
	
	/** Место рождения */
	@Comment("Место рождения")
	@Persist
	public String getBirthPlace() {
		return theBirthPlace;
	}

	public void setBirthPlace(String aBirthPlace) {
		theBirthPlace = aBirthPlace;
	}

	/** Место рождения */
	private String theBirthPlace;
	
	/** Адрес места рождения */
	@Comment("Адрес места рождения")
	@Persist
	public String getBirthPlaceAdress() {return theBirthPlaceAdress;}
	public void setBirthPlaceAdress(String aBirthPlaceAdress) {theBirthPlaceAdress = aBirthPlaceAdress;}

	/** Адрес места рождения */
	private String theBirthPlaceAdress;
	/** Умерла после окончания родов */
	private Long theAfterPregnance;
	/** На основании чего установлена смерть */
	private String theDeathEvidence;
	/** Причина смерти установлена */
	private Long theDeathWitnessFunction;
	/** Врач (фельдшер) */
	private Long theDeathWitness;
	/** Обстоятельства, при которых произошла травма (отравление) */
	private String theAccidentCircumstance;
	/** Место, при которых произошла травма (отравление) */
	private String theAccidentPlace;
	/** Дата травмы (отравления) */
	private String theAccidentDate;
	/** Причина смерти */
	private Long theDeathReason;
	/** Адрес места смерти */
	private Long theDeathPlaceAddress;
	/** Место смерти */
	private Long theDeathPlace;
	/** Какой ребенок по счету у матери */
	private Integer theBabyNumber;
	/** Масса (вес) при рождении (грамм) */
	private Integer theBirthWeight;
	/** Доношенность */
	private Long theIsPrematurity;
	/** Время смерти */
	private String theDeathTime;
	/** Дата смерти */
	private String theDeathDate;
	/** Мать */
	private Long theMother;
	/** Пациент */
	private Long thePatient;
	/** Мед. случай */
	private Long theMedCase;

	/** Ятрогения */
	@Comment("Ятрогения")
	@Persist
	public Long getLatrogeny() {return theLatrogeny;}
	public void setLatrogeny(Long aLatrogeny) {theLatrogeny = aLatrogeny;}

	/** Категория расхождения между диагнозами после патан. */
	@Comment("Категория расхождения между диагнозами после патан.")
	@Persist
	public Long getCategoryDifference() {return theCategoryDifference;}
	public void setCategoryDifference(Long aCategoryDifference) {theCategoryDifference = aCategoryDifference;}

	/** Описание причины смерти */
	@Comment("Описание причины смерти")
	@Persist
	public String getCommentReason() {return theCommentReason;}
	public void setCommentReason(String aCommentDeathReason) {theCommentReason = aCommentDeathReason;}

	/** Комментарий к категории смерти */
	@Comment("Комментарий к категории смерти")
	@Persist
	public String getCommentCategory() {return theCommentCategory;}
	public void setCommentCategory(String aCommentCategoryDeath) {theCommentCategory = aCommentCategoryDeath;}

	/** Номер в патологоанатомическом бюро */
	@Comment("Номер в патологоанатомическом бюро")
	@Persist
	public String getPostmortemBureauNumber() {return thePostmortemBureauNumber;}
	public void setPostmortemBureauNumber(String aPostmortemBureauNumber) {thePostmortemBureauNumber = aPostmortemBureauNumber;}

	/** Дата ПАБ */
	@Comment("Дата ПАБ")
	@Persist @DateString @DoDateString
	public String getPostmortemBureauDt() {return thePostmortemBureauDt;}
	public void setPostmortemBureauDt(String aPostmortemBureauDt) {thePostmortemBureauDt = aPostmortemBureauDt;}

	/** Квартира, где умер человек */
	@Comment("Квартира, где умер человек")
	@Persist
	public String getDeathPlaceFlatNumber() {return theDeathPlaceFlatNumber;}
	public void setDeathPlaceFlatNumber(String aDeathPlaceFlatNumber) {theDeathPlaceFlatNumber = aDeathPlaceFlatNumber;}

	/** Дом, где умер человекhPlaceHouseNumber */
	@Comment("Дом, где умер человек")
	@Persist
	public String getDeathPlaceHouseNumber() {return theDeathPlaceHouseNumber;}
	public void setDeathPlaceHouseNumber(String aDeathPlaceHouseNumber) {theDeathPlaceHouseNumber = aDeathPlaceHouseNumber;}

	/** Копрус дома, где умер человек */
	@Comment("Копрус дома, где умер человек")
	@Persist
	public String getDeathPlaceHouseBuilding() {return theDeathPlaceHouseBuilding;}
	public void setDeathPlaceHouseBuilding(String aDeathPlaceHouseBuilding) {theDeathPlaceHouseBuilding = aDeathPlaceHouseBuilding;}

	/** Острота диагноза клинического */
	@Comment("Острота диагноза клинического")
	public Long getConcludingActuity() {return theConcludingActuity;}
	public void setConcludingActuity(Long aClinicalActuity) {theConcludingActuity = aClinicalActuity;}

	/** Патанатомический диагноз */
	@Comment("Патанатомический диагноз")
	public String getPathanatomicalDiagnos() {return thePathanatomicalDiagnos;}
	public void setPathanatomicalDiagnos(String aPathanatomicalDiagnos) {thePathanatomicalDiagnos = aPathanatomicalDiagnos;}	
	
	/** Патанатомический диагноз по МКБ-10 */
	@Comment("Патанатомический диагноз по МКБ-10")
	@Mkb
	public Long getPathanatomicalMkb() {return thePathanatomicalMkb;}
	public void setPathanatomicalMkb(Long aPathanatomicalMkb) {thePathanatomicalMkb = aPathanatomicalMkb;}

	/** Заключительный диагноз */
	@Comment("Заключительный диагноз")
	public String getConcludingDiagnos() {return theConcludingDiagnos;}
	public void setConcludingDiagnos(String aConcludingDiagnos) {theConcludingDiagnos = aConcludingDiagnos;}

	/** Заключительный диагноз по МКБ-10 */
	@Comment("Заключительный диагноз по МКБ-10")
	@Mkb
	public Long getConcludingMkb() {return theConcludingMkb;}
	public void setConcludingMkb(Long aConcludingMkb) {theConcludingMkb = aConcludingMkb;}

	/** Заключительный диагноз по МКБ-10 */
	private Long theConcludingMkb;
	/** Заключительный диагноз */
	private String theConcludingDiagnos;
	/** Патанатомический диагноз по МКБ-10 */
	private Long thePathanatomicalMkb;
	/** Патанатомический диагноз */
	private String thePathanatomicalDiagnos;
	/** Острота диагноза клинического */
	private Long theConcludingActuity;
	/** Копрус дома, где умер человек */
	private String theDeathPlaceHouseBuilding;
	/** Дом, где умер человекhPlaceHouseNumber */
	private String theDeathPlaceHouseNumber;
	/** Квартира, где умер человек */
	private String theDeathPlaceFlatNumber;
	/** Дата ПАБ */
	private String thePostmortemBureauDt;
	/** Номер в патологоанатомическом бюро */
	private String thePostmortemBureauNumber;
	/** Комментарий к категории смерти */
	private String theCommentCategory;
	/** Описание причины смерти */
	private String theCommentReason;
	/** Категория расхождения между диагнозами после патан. */
	private Long theCategoryDifference;
	/** Ятрогения */
	private Long theLatrogeny;

	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return theEditTime;}
	public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private String theEditTime;
	/** Время создания */
	private String theCreateTime;
	/** Дата редактирования */
	private String theEditDate;
	/** Дата создания */
	private String theCreateDate;

	/** Тип диагноза категории расхождения */
	@Comment("Тип диагноза категории расхождения")
	@Persist
	public Long getDiagnosisDifference() {return theDiagnosisDifference;}
	public void setDiagnosisDifference(Long aDiagnosisDifference) {theDiagnosisDifference = aDiagnosisDifference;}

	/** Дата СМЭ */
	@Comment("Дата СМЭ")
	@Persist @DateString @DoDateString
	public String getDateForensic() {return theDateForensic;}
	public void setDateForensic(String aDateForensic) {theDateForensic = aDateForensic;}

	/** Произведено вскрытие */
	@Comment("Произведено вскрытие")
	@Persist
	public Boolean getIsAutopsy() {return theIsAutopsy;}
	public void setIsAutopsy(Boolean aIsAutopsy) {theIsAutopsy = aIsAutopsy;}

	
	/** Тип диагноза категории расхождения */
	private Long theDiagnosisDifference;
	/** Произведено вскрытие */
	private Boolean theIsAutopsy;
	/** Дата СМЭ */
	private String theDateForensic;
	
	/** Код мкб */
	@Comment("Код мкб")
	@Persist @Required
	public Long getReasonMainMkb() {return theReasonMainMkb;}
	public void setReasonMainMkb(Long aReasonMainMkb) {theReasonMainMkb = aReasonMainMkb;}

	/** Код мкб */
	private Long theReasonMainMkb;
	
	/** Код мкб осложнения */
	@Comment("Код мкб осложнения")
	@Persist 
	public Long getReasonComplicationMkb() {return theReasonComplicationMkb;}
	public void setReasonComplicationMkb(Long aReasonComplicationMkb) {theReasonComplicationMkb = aReasonComplicationMkb;}

	/** Код мкб осложнения */
	private Long theReasonComplicationMkb;
	
	/** Код мкб сопутсвующий */
	@Comment("Код мкб сопутсвующий")
	@Persist 
	public Long getReasonConcomitantMkb() {return theReasonConcomitantMkb;}
	public void setReasonConcomitantMkb(Long aReasonConcomitantMkb) {theReasonConcomitantMkb = aReasonConcomitantMkb;}

	/** Код мкб сопутсвующий */
	private Long theReasonConcomitantMkb;
	/** Присутствие врача на вскрытие */
	@Comment("Присутствие врача на вскрытие")
	@Persist
	public Boolean getIsPresenceDoctorAutopsy() {return theIsPresenceDoctorAutopsy;}	
	public void setIsPresenceDoctorAutopsy(Boolean aIsPresenceDoctorAutopsy) {theIsPresenceDoctorAutopsy = aIsPresenceDoctorAutopsy;}

	/** Присутствие врача на вскрытие */
	private Boolean theIsPresenceDoctorAutopsy;
	
	/** Текст мкб осложнения */
	@Comment("Текст мкб осложнения")
	@Persist
	public String getReasonComplicationText() {return theReasonComplicationText;}
	public void setReasonComplicationText(String aReasonComplicationText) {theReasonComplicationText = aReasonComplicationText;}

	/** Сопутствующий диагноз текст */
	@Comment("Сопутствующий диагноз текст")
	@Persist
	public String getReasonConcomitantText() {return theReasonConcomitantText;}
	public void setReasonConcomitantText(String aReasonConcomitantText) {theReasonConcomitantText = aReasonConcomitantText;}

	/** Конкурирующее заболевание */
	@Comment("Конкурирующее заболевание")
	@Persist
	public String getCompetingDisease() {return theCompetingDisease;}
	public void setCompetingDisease(String aCompetingDisease) {theCompetingDisease = aCompetingDisease;}

	/** Сочетанное заболевание */
	@Comment("Сочетанное заболевание")
	@Persist
	public String getPolypathia() {return thePolypathia;}
	public void setPolypathia(String aPolypathia) {thePolypathia = aPolypathia;}

	/** Фоновое заболевание */
	@Comment("Фоновое заболевание")
	@Persist
	public String getBackgroundDisease() {return theBackgroundDisease;}
	public void setBackgroundDisease(String aBackgroundDisease) {theBackgroundDisease = aBackgroundDisease;}

	/** Мертворождение */
	@Comment("Мертворождение")
	@Persist
	public Boolean getIsNeonatologic() {return theIsNeonatologic;}
	public void setIsNeonatologic(Boolean aIsNeonatologic) {theIsNeonatologic = aIsNeonatologic;}

	/** История развития новорождённого - только для акушерских случаев */
	public String getNewBornHistory() {return theNewBornHistory;}
	public void setNewBornHistory(String aNewBornHistory) { theNewBornHistory=aNewBornHistory;}

	/** Фоновое заболевание */
	private String theBackgroundDisease;
	/** Сочетанное заболевание */
	private String thePolypathia;
	/** Конкурирующее заболевание */
	private String theCompetingDisease;
	/** Сопутствующий диагноз текст */
	private String theReasonConcomitantText;	
	/** Текст мкб осложнения */
	private String theReasonComplicationText;
	/** Мертворождение */
	private Boolean theIsNeonatologic;
	/** История развития новорождённого - только для акушерских случаев */
	private String theNewBornHistory;
}
