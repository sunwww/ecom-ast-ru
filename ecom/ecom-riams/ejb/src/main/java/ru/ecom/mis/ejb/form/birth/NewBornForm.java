package ru.ecom.mis.ejb.form.birth;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.birth.NewBorn;
import ru.ecom.mis.ejb.form.birth.interceptors.NewBornPreCreateInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Новорожденный
 * @author azviagin,stkacheva
 *
 */

@EntityForm
@EntityFormPersistance(clazz=NewBorn.class)
@Comment(" Новорожденный")
@WebTrail(comment = " Новорожденный", nameProperties= "id", 
		view="entityParentView-preg_newBorn.do", 
		list = "entityParentList-preg_newBorn.do")
@Parent(property="childBirth", parentForm= ChildBirthForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/NewBorn")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(NewBornPreCreateInterceptor.class)
)
public class NewBornForm extends IdEntityForm{
	
	/** Умер до начала родовой деятельности */
	@Comment("Умер до начала родовой деятельности")
	@Persist
	public Boolean getDeadBeforeLabors() {return theDeadBeforeLabors;}
	public void setDeadBeforeLabors(Boolean aDeadBeforeLabors) {theDeadBeforeLabors = aDeadBeforeLabors;}
	/** Умер до начала родовой деятельности */
	private Boolean theDeadBeforeLabors;
	
	/** Пациент */
	@Comment("Пациент")
	@Persist 
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}
	
	/** Кто принимал ребенка */
	@Comment("Кто принимал ребенка")
	@Persist
	public Long getChildDeliverer() {return theChildDeliverer;}
	public void setChildDeliverer(Long aChildDeliverer) {theChildDeliverer = aChildDeliverer;}
	
	/** Дата рождения */
	@Comment("Дата рождения")
	@Persist @DateString @DoDateString @Required
	public String getBirthDate() {return theBirthDate;}
	public void setBirthDate(String aBirthDate) {theBirthDate = aBirthDate;}
	
	/** Время рождения */
	@Comment("Время рождения")
	@Persist @TimeString @DoTimeString @Required
	public String getBirthTime() {return theBirthTime;}
	public void setBirthTime(String aBirthTime) {theBirthTime = aBirthTime;}
	
	/** Пол */
	@Comment("Пол")
	@Persist @Required
	public Long getSex() {return theSex;}
	public void setSex(Long aSex) {theSex = aSex;}
	
	/** Вес при рождении */
	@Comment("Вес при рождении")
	@Persist @Required
	public String getBirthWeight() {return theBirthWeight;}
	public void setBirthWeight(String aBirthWeight) {theBirthWeight = aBirthWeight;}
	
	/** Рост при рождении */
	@Comment("Рост при рождении")
	@Persist @Required
	public String getBirthHeight() {return theBirthHeight;}
	public void setBirthHeight(String aBirthHeight) {theBirthHeight = aBirthHeight;}
	
	/** Окружность головы */
	@Comment("Окружность головы")
	@Persist @Required
	public String getHeadCircle() {return theHeadCircle;}
	public void setHeadCircle(String aHeadCircle) {theHeadCircle = aHeadCircle;}
	
	/** Окружность плеч */
	@Comment("Окружность плеч")
	@Persist
	public String getShouldersCircle() {return theShouldersCircle;}
	public void setShouldersCircle(String aShouldersCircle) {theShouldersCircle = aShouldersCircle;}
	
	/** Состояние при рождении */
	@Comment("Состояние при рождении")
	@Persist
	public String getBirthState() {return theBirthState;}
	public void setBirthState(String aBirthState) {theBirthState = aBirthState;}
	
	/** Длина пуповины */
	@Comment("Длина пуповины")
	@Persist
	public String getCordLength() {return theCordLength;}
	public void setCordLength(String aCordLength) {theCordLength = aCordLength;}
	
	/** Прикрепление пуповины */
	@Comment("Прикрепление пуповины")
	@Persist
	public Long getCordAttaching() {return theCordAttaching;}
	public void setCordAttaching(Long aCordAttaching) {theCordAttaching = aCordAttaching;}
	
	/** Зрелость */
	@Comment("Зрелость")
	@Persist @Required
	public Long getMaturity() {return theMaturity;}
	public void setMaturity(Long aMaturity) {theMaturity = aMaturity;}
	
	/** Родился живым */
	@Comment("Родился живым")
	@Persist @Required
	public Long getLiveBorn() {return theLiveBorn;}
	public void setLiveBorn(Long aLiveBorn) {theLiveBorn = aLiveBorn;}
	
	/** Вредное влияние матери: наследственность, заболевания, вредные привычки */
	@Comment("Вредное влияние матери: наследственность, вредные привычки")
	@Persist 
	public String getMothersHarmfulEffect() {return theMothersHarmfulEffect;}
	public void setMothersHarmfulEffect(String aMothersHarmfulEffect) {theMothersHarmfulEffect = aMothersHarmfulEffect;}
	
	/** Вредное влияние отца: наследственность, заболевания, вредные привычки */
	@Comment("Вредное влияние отца: наследственность, заболевания, вредные привычки")
	@Persist 
	public String getFathersHarmfulEffect() {return theFathersHarmfulEffect;}
	public void setFathersHarmfulEffect(String aFathersHarmfulEffect) {theFathersHarmfulEffect = aFathersHarmfulEffect;}
	
	/** Пороки развития */
	@Comment("Пороки развития")
	@Persist 
	public String getMalformations() {return theMalformations;}
	public void setMalformations(String aMalformations) {theMalformations = aMalformations;}
	
	/** Проходимость пищевода */
	@Comment("Проходимость пищевода")
	@Persist
	public String getOesophagusPermeability() {return theOesophagusPermeability;}
	public void setOesophagusPermeability(String aOesophagusPermeability) {theOesophagusPermeability = aOesophagusPermeability;}
	
	/** Наличие ануса */
	@Comment("Наличие ануса")
	@Persist
	public String getAnus() {return theAnus;}
	public void setAnus(String aAnus) {theAnus = aAnus;}
	
	/** Большой поперечный размер (см)*/
	@Comment("Большой поперечный размер (см)")
	@Persist
	public String getLongTransversalSize() {return theLongTransversalSize;}
	public void setLongTransversalSize(String aLongTransversalSize) {theLongTransversalSize = aLongTransversalSize;}
	
	/** Малый поперечный размер (см)*/
	@Comment("Малый поперечный размер (см)")
	@Persist
	public String getShortTransversalSize() {return theShortTransversalSize;}
	public void setShortTransversalSize(String aShortTransversalSize) {theShortTransversalSize = aShortTransversalSize;}
	
	/** Большой косой размер (см) */
	@Comment("Большой косой размер (см)")
	@Persist
	public String getLongSkewSize() {return theLongSkewSize;}
	public void setLongSkewSize(String aLongSkewSize) {theLongSkewSize = aLongSkewSize;}
	
	/** Размер большой косой окружности (см)*/
	@Comment("Размер большой косой окружности (см)")
	@Persist
	public String getLongSkewCircleSize() {return theLongSkewCircleSize;}
	public void setLongSkewCircleSize(String aLongSkewCircleSize) {theLongSkewCircleSize = aLongSkewCircleSize;}
	
	/** Размер малой косой окружности (см) */
	@Comment("Размер малой косой окружности (см)")
	@Persist
	public String getShortSkewCircleSize() {return theShortSkewCircleSize;}
	public void setShortSkewCircleSize(String aShortSkewCircleSize) {theShortSkewCircleSize = aShortSkewCircleSize;}
	
	/** Малый косой размер */
	@Comment("Малый косой размер")
	@Persist
	public String getShortSkewSize() {return theShortSkewSize;}
	public void setShortSkewSize(String aShortSkewSize) {theShortSkewSize = aShortSkewSize;}
	
	/** Размер прямой окружности (см) */
	@Comment("Размер прямой окружности (см)")
	@Persist
	public String getDirectCircleSize() {return theDirectCircleSize;}
	public void setDirectCircleSize(String aDirectCircleSize) {theDirectCircleSize = aDirectCircleSize;}
	
	/** Прямой размер (см) */
	@Comment("Прямой размер (см)")
	@Persist
	public String getDirectSize() {return theDirectSize;}
	public void setDirectSize(String aDirectSize) {theDirectSize = aDirectSize;}
	
	/** Вид вставки */
	@Comment("Вид вставки")
	@Persist
	public String getSetView() {return theSetView;}
	public void setSetView(String aSetView) {theSetView = aSetView;}
	
	/** Вставленная часть тела */
	@Comment("Вставленная часть тела")
	@Persist
	public String getSetPart() {return theSetPart;}
	public void setSetPart(String aSetPart) {theSetPart = aSetPart;}
	
	/** Объем кровопотери (мл) */
	@Comment("Объем кровопотери (мл)")
	@Persist
	public String getHemorrhageVolume() {return theHemorrhageVolume;}
	public void setHemorrhageVolume(String aHemorrhageVolume) {theHemorrhageVolume = aHemorrhageVolume;}
	
	/** СМО */
	@Comment("СМО")
	@Persist
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}

	/** Роды */
	@Comment("Роды")
	@Persist
	public Long getChildBirth() {return theChildBirth;}
	public void setChildBirth(Long aChildBirth) {theChildBirth = aChildBirth;}

	


	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}

	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}

	/** Ребенок */
	@Comment("Ребенок")
	@Persist @Required
	public Long getChild() {return theChild;}
	public void setChild(Long aChild) {theChild = aChild;}

	/** Ребенок */
	private Long theChild;
	
	/** Время создания */
	private String theCreateTime;
	/** Дата создания */
	private String theCreateDate;
	/** Пользователь */
	private String theUsername;

	/** Роды */
	private Long theChildBirth;
	/** Пациент */
	private Long thePatient;
	/** Кто принимал ребенка */
	private Long theChildDeliverer;
	/** Дата рождения */
	private String theBirthDate;
	/** Время рождения */
	private String theBirthTime;
	/** Пол */
	private Long theSex;
	/** Вес при рождении */
	private String theBirthWeight;
	/** Рост при рождении */
	private String theBirthHeight;
	/** Окружность головы */
	private String theHeadCircle;
	/** Окружность плеч */
	private String theShouldersCircle;
	/** Состояние при рождении */
	private String theBirthState;
	
	/** Длина пуповины */
	private String theCordLength;
	/** Прикрепление пуповины */
	private Long theCordAttaching;
	/** Зрелость */
	private Long theMaturity;
	/** Родился живым */
	private Long theLiveBorn;
	/** Вредное влияние матери: наследственность, заболевания, вредные привычки */
	private String theMothersHarmfulEffect;
	/** Вредное влияние отца: наследственность, заболевания, вредные привычки */
	private String theFathersHarmfulEffect;
	/** Пороки развития */
	private String theMalformations;
	/** Проходимость пищевода */
	private String theOesophagusPermeability;
	/** Наличие ануса */
	private String theAnus;
	/** Большой поперечный размер (см)*/
	private String theLongTransversalSize;
	/** Малый поперечный размер (см)*/
	private String theShortTransversalSize;
	/** Большой косой размер (см) */
	private String theLongSkewSize;
	/** Размер большой косой окружности (см)*/
	private String theLongSkewCircleSize;
	/** Размер малой косой окружности (см) */
	private String theShortSkewCircleSize;
	/** Малый косой размер */
	private String theShortSkewSize;
	/** Размер прямой окружности (см) */
	private String theDirectCircleSize;
	/** Прямой размер (см) */
	private String theDirectSize;
	/** Вид вставки */
	private String theSetView;
	/** Вставленная часть тела */
	private String theSetPart;
	/** Объем кровопотери (мл) */
	private String theHemorrhageVolume;
	/** СМО */
	private Long theMedCase;
	
	/** №истории болезни */
	@Comment("№истории болезни")
	public String getStatisticStub() {return theStatisticStub;}
	public void setStatisticStub(String aStatisticStub) {theStatisticStub = aStatisticStub;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}

	/** Экстренное поступление */
	@Comment("Экстренное поступление")
	public Boolean getEmergency() {return theEmergency;}
	public void setEmergency(Boolean aEmergency) {theEmergency = aEmergency;}

	
	/** Экстренное поступление */
	private Boolean theEmergency;
	/** Поток обслуживания */
	private Long theServiceStream;
	/** №истории болезни */
	private String theStatisticStub;
	
	/** Оценка по Апгар */
	@Comment("Оценка по Апгар")
	@Persist
	public Long getApgarMark() {return theApgarMark;}
	public void setApgarMark(Long aApgarMark) {theApgarMark = aApgarMark;}

	
	/** Оценка по Апгар */
	private Long theApgarMark;
	
	/** Какой частью тела родился (головкой, ягодицами, ножками) */
	@Comment("Какой частью тела родился (головкой, ягодицами, ножками)")
	@Persist @Required
	public Long getPartBodyBorn() {return thePartBodyBorn;}
	public void setPartBodyBorn(Long aPartBodyBorn) {thePartBodyBorn = aPartBodyBorn;}

	/** Какой частью тела родился (головкой, ягодицами, ножками) */
	private Long thePartBodyBorn;
	
	/** Обвитие */
	@Comment("Обвитие")
	@Persist @Required
	public Long getEntanglement() {return theEntanglement;}
	public void setEntanglement(Long aEntanglement) {theEntanglement = aEntanglement;}

	/** Кратность обвития */
	@Comment("Кратность обвития")
	@Persist
	public Long getEntanglementMultiplicity() {return theEntanglementMultiplicity;}
	public void setEntanglementMultiplicity(Long aEntanglementMultiplicity) {theEntanglementMultiplicity = aEntanglementMultiplicity;}

	/** Где обвитие произошло */
	@Comment("Где обвитие произошло")
	@Persist
	public Long getWhereEntanglement() {return theWhereEntanglement;}
	public void setWhereEntanglement(Long aWhereEntanglement) {theWhereEntanglement = aWhereEntanglement;}

	/** Где обвитие произошло */
	private Long theWhereEntanglement;
	/** Кратность обвития */
	private Long theEntanglementMultiplicity;
	/** Обвитие */
	private Long theEntanglement;
	
	/** Отделение */
	@Comment("Отделение")
	@Persist
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}

	/** Отделение */
	private Long theDepartment;
}
