package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
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
@Setter
public class NewBornForm extends IdEntityForm{
	
	/** Умер до начала родовой деятельности */
	@Comment("Умер до начала родовой деятельности")
	@Persist
	public Boolean getDeadBeforeLabors() {return deadBeforeLabors;}
	/** Умер до начала родовой деятельности */
	private Boolean deadBeforeLabors;
	
	/** Пациент */
	@Comment("Пациент")
	@Persist 
	public Long getPatient() {return patient;}

	/** Кто принимал ребенка */
	@Comment("Кто принимал ребенка")
	@Persist
	public Long getChildDeliverer() {return childDeliverer;}

	/** Дата рождения */
	@Comment("Дата рождения")
	@Persist @DateString @DoDateString @Required
	public String getBirthDate() {return birthDate;}

	/** Время рождения */
	@Comment("Время рождения")
	@Persist @TimeString @DoTimeString @Required
	public String getBirthTime() {return birthTime;}

	/** Пол */
	@Comment("Пол")
	@Persist @Required
	public Long getSex() {return sex;}

	/** Вес при рождении */
	@Comment("Вес при рождении")
	@Persist @Required
	public String getBirthWeight() {return birthWeight;}

	/** Рост при рождении */
	@Comment("Рост при рождении")
	@Persist @Required
	public String getBirthHeight() {return birthHeight;}

	/** Окружность головы */
	@Comment("Окружность головы")
	@Persist @Required
	public String getHeadCircle() {return headCircle;}

	/** Окружность плеч */
	@Comment("Окружность плеч")
	@Persist
	public String getShouldersCircle() {return shouldersCircle;}

	/** Состояние при рождении */
	@Comment("Состояние при рождении")
	@Persist
	public String getBirthState() {return birthState;}

	/** Длина пуповины */
	@Comment("Длина пуповины")
	@Persist
	public String getCordLength() {return cordLength;}

	/** Прикрепление пуповины */
	@Comment("Прикрепление пуповины")
	@Persist
	public Long getCordAttaching() {return cordAttaching;}

	/** Зрелость */
	@Comment("Зрелость")
	@Persist @Required
	public Long getMaturity() {return maturity;}

	/** Родился живым */
	@Comment("Родился живым")
	@Persist @Required
	public Long getLiveBorn() {return liveBorn;}

	/** Вредное влияние матери: наследственность, заболевания, вредные привычки */
	@Comment("Вредное влияние матери: наследственность, вредные привычки")
	@Persist 
	public String getMothersHarmfulEffect() {return mothersHarmfulEffect;}

	/** Вредное влияние отца: наследственность, заболевания, вредные привычки */
	@Comment("Вредное влияние отца: наследственность, заболевания, вредные привычки")
	@Persist 
	public String getFathersHarmfulEffect() {return fathersHarmfulEffect;}

	/** Пороки развития */
	@Comment("Пороки развития")
	@Persist 
	public String getMalformations() {return malformations;}

	/** Проходимость пищевода */
	@Comment("Проходимость пищевода")
	@Persist
	public String getOesophagusPermeability() {return oesophagusPermeability;}

	/** Наличие ануса */
	@Comment("Наличие ануса")
	@Persist
	public String getAnus() {return anus;}

	/** Большой поперечный размер (см)*/
	@Comment("Большой поперечный размер (см)")
	@Persist
	public String getLongTransversalSize() {return longTransversalSize;}

	/** Малый поперечный размер (см)*/
	@Comment("Малый поперечный размер (см)")
	@Persist
	public String getShortTransversalSize() {return shortTransversalSize;}

	/** Большой косой размер (см) */
	@Comment("Большой косой размер (см)")
	@Persist
	public String getLongSkewSize() {return longSkewSize;}

	/** Размер большой косой окружности (см)*/
	@Comment("Размер большой косой окружности (см)")
	@Persist
	public String getLongSkewCircleSize() {return longSkewCircleSize;}

	/** Размер малой косой окружности (см) */
	@Comment("Размер малой косой окружности (см)")
	@Persist
	public String getShortSkewCircleSize() {return shortSkewCircleSize;}

	/** Малый косой размер */
	@Comment("Малый косой размер")
	@Persist
	public String getShortSkewSize() {return shortSkewSize;}

	/** Размер прямой окружности (см) */
	@Comment("Размер прямой окружности (см)")
	@Persist
	public String getDirectCircleSize() {return directCircleSize;}

	/** Прямой размер (см) */
	@Comment("Прямой размер (см)")
	@Persist
	public String getDirectSize() {return directSize;}

	/** Вид вставки */
	@Comment("Вид вставки")
	@Persist
	public String getSetView() {return setView;}

	/** Вставленная часть тела */
	@Comment("Вставленная часть тела")
	@Persist
	public String getSetPart() {return setPart;}

	/** Объем кровопотери (мл) */
	@Comment("Объем кровопотери (мл)")
	@Persist
	public String getHemorrhageVolume() {return hemorrhageVolume;}

	/** СМО */
	@Comment("СМО")
	@Persist
	public Long getMedCase() {return medCase;}

	/** Роды */
	@Comment("Роды")
	@Persist
	public Long getChildBirth() {return childBirth;}

	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public String getUsername() {return username;}

	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return createDate;}

	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return createTime;}

	/** Ребенок */
	@Comment("Ребенок")
	@Persist @Required
	public Long getChild() {return child;}

	/** Ребенок */
	private Long child;
	
	/** Время создания */
	private String createTime;
	/** Дата создания */
	private String createDate;
	/** Пользователь */
	private String username;

	/** Роды */
	private Long childBirth;
	/** Пациент */
	private Long patient;
	/** Кто принимал ребенка */
	private Long childDeliverer;
	/** Дата рождения */
	private String birthDate;
	/** Время рождения */
	private String birthTime;
	/** Пол */
	private Long sex;
	/** Вес при рождении */
	private String birthWeight;
	/** Рост при рождении */
	private String birthHeight;
	/** Окружность головы */
	private String headCircle;
	/** Окружность плеч */
	private String shouldersCircle;
	/** Состояние при рождении */
	private String birthState;
	
	/** Длина пуповины */
	private String cordLength;
	/** Прикрепление пуповины */
	private Long cordAttaching;
	/** Зрелость */
	private Long maturity;
	/** Родился живым */
	private Long liveBorn;
	/** Вредное влияние матери: наследственность, заболевания, вредные привычки */
	private String mothersHarmfulEffect;
	/** Вредное влияние отца: наследственность, заболевания, вредные привычки */
	private String fathersHarmfulEffect;
	/** Пороки развития */
	private String malformations;
	/** Проходимость пищевода */
	private String oesophagusPermeability;
	/** Наличие ануса */
	private String anus;
	/** Большой поперечный размер (см)*/
	private String longTransversalSize;
	/** Малый поперечный размер (см)*/
	private String shortTransversalSize;
	/** Большой косой размер (см) */
	private String longSkewSize;
	/** Размер большой косой окружности (см)*/
	private String longSkewCircleSize;
	/** Размер малой косой окружности (см) */
	private String shortSkewCircleSize;
	/** Малый косой размер */
	private String shortSkewSize;
	/** Размер прямой окружности (см) */
	private String directCircleSize;
	/** Прямой размер (см) */
	private String directSize;
	/** Вид вставки */
	private String setView;
	/** Вставленная часть тела */
	private String setPart;
	/** Объем кровопотери (мл) */
	private String hemorrhageVolume;
	/** СМО */
	private Long medCase;
	
	/** №истории болезни */
	@Comment("№истории болезни")
	public String getStatisticStub() {return statisticStub;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	public Long getServiceStream() {return serviceStream;}

	/** Экстренное поступление */
	@Comment("Экстренное поступление")
	public Boolean getEmergency() {return emergency;}
	/** Экстренное поступление */
	private Boolean emergency;
	/** Поток обслуживания */
	private Long serviceStream;
	/** №истории болезни */
	private String statisticStub;
	
	/** Оценка по Апгар */
	@Comment("Оценка по Апгар")
	@Persist
	public Long getApgarMark() {return apgarMark;}

	
	/** Оценка по Апгар */
	private Long apgarMark;
	
	/** Какой частью тела родился (головкой, ягодицами, ножками) */
	@Comment("Какой частью тела родился (головкой, ягодицами, ножками)")
	@Persist @Required
	public Long getPartBodyBorn() {return partBodyBorn;}

	/** Какой частью тела родился (головкой, ягодицами, ножками) */
	private Long partBodyBorn;
	
	/** Обвитие */
	@Comment("Обвитие")
	@Persist @Required
	public Long getEntanglement() {return entanglement;}

	/** Кратность обвития */
	@Comment("Кратность обвития")
	@Persist
	public Long getEntanglementMultiplicity() {return entanglementMultiplicity;}

	/** Где обвитие произошло */
	@Comment("Где обвитие произошло")
	@Persist
	public Long getWhereEntanglement() {return whereEntanglement;}

	/** Где обвитие произошло */
	private Long whereEntanglement;
	/** Кратность обвития */
	private Long entanglementMultiplicity;
	/** Обвитие */
	private Long entanglement;
	
	/** Отделение */
	@Comment("Отделение")
	@Persist
	public Long getDepartment() {return department;}

	/** Отделение */
	private Long department;
}
