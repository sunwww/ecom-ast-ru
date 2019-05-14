package ru.ecom.mis.ejb.domain.birth;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.birth.voc.VocBirthEntanglement;
import ru.ecom.mis.ejb.domain.birth.voc.VocBirthEntanglementMultiplicity;
import ru.ecom.mis.ejb.domain.birth.voc.VocBirthWhereEntanglement;
import ru.ecom.mis.ejb.domain.birth.voc.VocLiveBorn;
import ru.ecom.mis.ejb.domain.birth.voc.VocNewBorn;
import ru.ecom.mis.ejb.domain.birth.voc.VocNewBornCordAttaching;
import ru.ecom.mis.ejb.domain.birth.voc.VocNewBornMaturity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocColorIdentityPatient;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Новорожденный
 * @author azviagin
 *
 */

@Comment(" Новорожденный")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
		@AIndex(properties = { "patient" }) ,
		@AIndex(properties = { "medCase" }) ,
		@AIndex(properties = { "childBirth" }) 
	}
)
@EntityListeners(DeleteListener.class)
public class NewBorn extends BaseEntity{

	
	/** Умер до начала родовой деятельности */
	@Comment("Умер до начала родовой деятельности")
	public Boolean getDeadBeforeLabors() {return theDeadBeforeLabors;}
	public void setDeadBeforeLabors(Boolean aDeadBeforeLabors) {theDeadBeforeLabors = aDeadBeforeLabors;}
	/** Умер до начала родовой деятельности */
	private Boolean theDeadBeforeLabors;
	
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}
	
	/** Кто принимал ребенка */
	@Comment("Кто принимал ребенка")
	@OneToOne
	public WorkFunction getChildDeliverer() {return theChildDeliverer;}
	public void setChildDeliverer(WorkFunction aChildDeliverer) {theChildDeliverer = aChildDeliverer;}
	
	/** Дата рождения */
	@Comment("Дата рождения")
	public Date getBirthDate() {return theBirthDate;}
	public void setBirthDate(Date aBirthDate) {theBirthDate = aBirthDate;}
	
	/** Время рождения */
	@Comment("Время рождения")
	public Time getBirthTime() {return theBirthTime;}
	public void setBirthTime(Time aBirthTime) {theBirthTime = aBirthTime;}
	
	/** Пол */
	@Comment("Пол")
	@OneToOne
	public VocSex getSex() {return theSex;}
	public void setSex(VocSex aSex) {theSex = aSex;}
	
	/** Вес при рождении */
	@Comment("Вес при рождении")
	public Integer getBirthWeight() {return theBirthWeight;}
	public void setBirthWeight(Integer aBirthWeight) {theBirthWeight = aBirthWeight;}
	
	/** Рост при рождении */
	@Comment("Рост при рождении")
	public Integer getBirthHeight() {return theBirthHeight;}
	public void setBirthHeight(Integer aBirthHeight) {theBirthHeight = aBirthHeight;}
	
	/** Окружность головы */
	@Comment("Окружность головы")
	public Integer getHeadCircle() {return theHeadCircle;}
	public void setHeadCircle(Integer aHeadCircle) {theHeadCircle = aHeadCircle;}
	
	/** Окружность плеч */
	@Comment("Окружность плеч")
	public Integer getShouldersCircle() {return theShouldersCircle;}
	public void setShouldersCircle(Integer aShouldersCircle) {theShouldersCircle = aShouldersCircle;}
	
	/** Окружность груди */
	@Comment("Окружность груди")
	public Integer getBreastCircle() {return theBreastCircle;}
	public void setBreastCircle(Integer aBreastCircle) {theBreastCircle = aBreastCircle;}

	/** Окружность груди */
	private Integer theBreastCircle;
	/** Состояние при рождении */
	@Comment("Состояние при рождении")
	public String getBirthState() {return theBirthState;}
	public void setBirthState(String aBirthState) {theBirthState = aBirthState;}
	
		
	/** Длина пуповины */
	@Comment("Длина пуповины")
	public Integer getCordLength() {return theCordLength;}
	public void setCordLength(Integer aCordLength) {theCordLength = aCordLength;}
	
	/** Прикрепление пуповины */
	@Comment("Прикрепление пуповины")
	@OneToOne
	public VocNewBornCordAttaching getCordAttaching() {return theCordAttaching;}
	public void setCordAttaching(VocNewBornCordAttaching aCordAttaching) {theCordAttaching = aCordAttaching;}
	
	/** Зрелость */
	@Comment("Зрелость")
	@OneToOne
	public VocNewBornMaturity getMaturity() {return theMaturity;}
	public void setMaturity(VocNewBornMaturity aMaturity) {theMaturity = aMaturity;}
	
	/** Родился живым */
	@Comment("Родился живым")
	@OneToOne
	public VocLiveBorn getLiveBorn() {return theLiveBorn;}
	public void setLiveBorn(VocLiveBorn aLiveBorn) {theLiveBorn = aLiveBorn;}
	
	/** Вредное влияние матери: наследственность, заболевания, вредные привычки */
	@Comment("Вредное влияние матери: наследственность, вредные привычки")
	@Column(length=1024)
	public String getMothersHarmfulEffect() {return theMothersHarmfulEffect;}
	public void setMothersHarmfulEffect(String aMothersHarmfulEffect) {theMothersHarmfulEffect = aMothersHarmfulEffect;}
	
	/** Вредное влияние отца: наследственность, заболевания, вредные привычки */
	@Comment("Вредное влияние отца: наследственность, заболевания, вредные привычки")
	@Column(length=1024)
	public String getFathersHarmfulEffect() {return theFathersHarmfulEffect;}
	public void setFathersHarmfulEffect(String aFathersHarmfulEffect) {theFathersHarmfulEffect = aFathersHarmfulEffect;}
	
	/** Пороки развития */
	@Comment("Пороки развития")
	@Column(length=1024)
	public String getMalformations() {return theMalformations;}
	public void setMalformations(String aMalformations) {theMalformations = aMalformations;}
	
	/** Проходимость пищевода */
	@Comment("Проходимость пищевода")
	public String getOesophagusPermeability() {return theOesophagusPermeability;}
	public void setOesophagusPermeability(String aOesophagusPermeability) {theOesophagusPermeability = aOesophagusPermeability;	}
	
	/** Наличие ануса */
	@Comment("Наличие ануса")
	public Boolean getAnus() {return theAnus;}
	public void setAnus(Boolean aAnus) {theAnus = aAnus;}
	
	/** Большой поперечный размер (см)*/
	@Comment("Большой поперечный размер (см)")
	public BigDecimal getLongTransversalSize() {return theLongTransversalSize;}
	public void setLongTransversalSize(BigDecimal aLongTransversalSize) {theLongTransversalSize = aLongTransversalSize;}
	
	/** Малый поперечный размер (см)*/
	@Comment("Малый поперечный размер (см)")
	public BigDecimal getShortTransversalSize() {return theShortTransversalSize;}
	public void setShortTransversalSize(BigDecimal aShortTransversalSize) {theShortTransversalSize = aShortTransversalSize;}
	
	/** Большой косой размер (см) */
	@Comment("Большой косой размер (см)")
	public BigDecimal getLongSkewSize() {return theLongSkewSize;}
	public void setLongSkewSize(BigDecimal aLongSkewSize) {theLongSkewSize = aLongSkewSize;}
	
	/** Размер большой косой окружности (см)*/
	@Comment("Размер большой косой окружности (см)")
	public BigDecimal getLongSkewCircleSize() {return theLongSkewCircleSize;}
	public void setLongSkewCircleSize(BigDecimal aLongSkewCircleSize) {theLongSkewCircleSize = aLongSkewCircleSize;}
	
	/** Размер малой косой окружности (см) */
	@Comment("Размер малой косой окружности (см)")
	public BigDecimal getShortSkewCircleSize() {return theShortSkewCircleSize;}
	public void setShortSkewCircleSize(BigDecimal aShortSkewCircleSize) {theShortSkewCircleSize = aShortSkewCircleSize;}
	
	/** Малый косой размер */
	@Comment("Малый косой размер")
	public BigDecimal getShortSkewSize() {return theShortSkewSize;}
	public void setShortSkewSize(BigDecimal aShortSkewSize) {theShortSkewSize = aShortSkewSize;}
	
	/** Размер прямой окружности (см) */
	@Comment("Размер прямой окружности (см)")
	public BigDecimal getDirectCircleSize() {return theDirectCircleSize;}
	public void setDirectCircleSize(BigDecimal aDirectCircleSize) {theDirectCircleSize = aDirectCircleSize;}
	
	/** Прямой размер (см) */
	@Comment("Прямой размер (см)")
	public BigDecimal getDirectSize() {return theDirectSize;}
	public void setDirectSize(BigDecimal aDirectSize) {theDirectSize = aDirectSize;}
	
	/** Вид вставки */
	@Comment("Вид вставки")
	public String getSetView() {return theSetView;}
	public void setSetView(String aSetView) {theSetView = aSetView;}
	
	/** Вставленная часть тела */
	@Comment("Вставленная часть тела")
	public String getSetPart() {return theSetPart;}
	public void setSetPart(String aSetPart) {theSetPart = aSetPart;}
	
	/** Объем кровопотери (мл) */
	@Comment("Объем кровопотери (мл)")
	public Integer getHemorrhageVolume() {return theHemorrhageVolume;}
	public void setHemorrhageVolume(Integer aHemorrhageVolume) {theHemorrhageVolume = aHemorrhageVolume;}
	
	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	/** Роды */
	@Comment("Роды")
	@ManyToOne
	public ChildBirth getChildBirth() {return theChildBirth;}
	public void setChildBirth(ChildBirth aChildBirth) {theChildBirth = aChildBirth;}
	
	@Transient
	public String getLastname() {return thePatient!=null?thePatient.getLastname():"" ;}
	@Transient
	public String getFirstname() {return thePatient!=null?thePatient.getFirstname():"" ;}
	@Transient
	public String getMiddlename() {return thePatient!=null?thePatient.getMiddlename():"" ;}

	/** Пользователь */
	@Comment("Пользователь")
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}


	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}

	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}

	
	/** Ребенок */
	@Comment("Ребенок")
	@OneToOne
	public VocNewBorn getChild() {return theChild;}
	public void setChild(VocNewBorn aChild) {theChild = aChild;}

	/** Ребенок */
	private VocNewBorn theChild;
	/** Время создания */
	private Time theCreateTime;
	/** Дата создания */
	private Date theCreateDate;
	/** Пользователь */
	private String theUsername;
	/** Роды */
	private ChildBirth theChildBirth;
	/** Пациент */
	private Patient thePatient;
	/** Кто принимал ребенка */
	private WorkFunction theChildDeliverer;
	/** Дата рождения */
	private Date theBirthDate;
	/** Время рождения */
	private Time theBirthTime;
	/** Пол */
	private VocSex theSex;
	/** Вес при рождении */
	private Integer theBirthWeight;
	/** Рост при рождении */
	private Integer theBirthHeight;
	/** Окружность головы */
	private Integer theHeadCircle;
	/** Окружность плеч */
	private Integer theShouldersCircle;
	/** Состояние при рождении */
	private String theBirthState;


	/** Длина пуповины */
	private Integer theCordLength;
	/** Прикрепление пуповины */
	private VocNewBornCordAttaching theCordAttaching;
	/** Зрелость */
	private VocNewBornMaturity theMaturity;
	/** Родился живым */
	private VocLiveBorn theLiveBorn;
	/** Вредное влияние матери: наследственность, заболевания, вредные привычки */
	private String theMothersHarmfulEffect;
	/** Вредное влияние отца: наследственность, заболевания, вредные привычки */
	private String theFathersHarmfulEffect;
	/** Пороки развития */
	private String theMalformations;
	/** Проходимость пищевода */
	private String theOesophagusPermeability;
	/** Наличие ануса */
	private Boolean theAnus;
	/** Большой поперечный размер (см)*/
	private BigDecimal theLongTransversalSize;
	/** Малый поперечный размер (см)*/
	private BigDecimal theShortTransversalSize;
	/** Большой косой размер (см) */
	private BigDecimal theLongSkewSize;
	/** Размер большой косой окружности (см)*/
	private BigDecimal theLongSkewCircleSize;
	/** Размер малой косой окружности (см) */
	private BigDecimal theShortSkewCircleSize;
	/** Малый косой размер */
	private BigDecimal theShortSkewSize;
	/** Размер прямой окружности (см) */
	private BigDecimal theDirectCircleSize;
	/** Прямой размер (см) */
	private BigDecimal theDirectSize;
	/** Вид вставки */
	private String theSetView;
	/** Вставленная часть тела */
	private String theSetPart;
	/** Объем кровопотери (мл) */
	private Integer theHemorrhageVolume;
	
	/** СМО */
	private MedCase theMedCase;
	
	/** Оценка по Апгар */
	@Comment("Оценка по Апгар")
	public Long getApgarMark() {return theApgarMark;}
	public void setApgarMark(Long aApgarMark) {theApgarMark = aApgarMark;}

	
	/** Оценка по Апгар */
	private Long theApgarMark;
	
	/** Какой частью тела родился (головкой, ягодицами, ножками) */
	@Comment("Какой частью тела родился (головкой, ягодицами, ножками)")
	public Long getPartBodyBorn() {return thePartBodyBorn;}
	public void setPartBodyBorn(Long aPartBodyBorn) {thePartBodyBorn = aPartBodyBorn;}

	/** Какой частью тела родился (головкой, ягодицами, ножками) */
	private Long thePartBodyBorn;

	/** Обвитие */
	@Comment("Обвитие")
	@OneToOne
	public VocBirthEntanglement getEntanglement() {return theEntanglement;}
	public void setEntanglement(VocBirthEntanglement aEntanglement) {theEntanglement = aEntanglement;}

	/** Кратность обвития */
	@Comment("Кратность обвития")
	@OneToOne
	public VocBirthEntanglementMultiplicity getEntanglementMultiplicity() {return theEntanglementMultiplicity;}
	public void setEntanglementMultiplicity(VocBirthEntanglementMultiplicity aEntanglementMultiplicity) {theEntanglementMultiplicity = aEntanglementMultiplicity;}

	/** Где обвитие произошло */
	@Comment("Где обвитие произошло")
	@OneToOne
	public VocBirthWhereEntanglement getWhereEntanglement() {return theWhereEntanglement;}
	public void setWhereEntanglement(VocBirthWhereEntanglement aWhereEntanglement) {theWhereEntanglement = aWhereEntanglement;}

	/** Где обвитие произошло */
	private VocBirthWhereEntanglement theWhereEntanglement;
	/** Кратность обвития */
	private VocBirthEntanglementMultiplicity theEntanglementMultiplicity;
	/** Обвитие */
	private VocBirthEntanglement theEntanglement;
	
	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartment() {
		return theDepartment;
	}

	public void setDepartment(MisLpu aDepartment) {
		theDepartment = aDepartment;
	}

	/** Отделение */
	private MisLpu theDepartment;

	/** Диабет (браслет)*/
	@Comment("Диабет (браслет)")
	@OneToOne
	public VocColorIdentityPatient getDiabetIdentity() {return theDiabetIdentity;}
	public void setDiabetIdentity(VocColorIdentityPatient aDiabetIdentity) {theDiabetIdentity = aDiabetIdentity;}
	/** Диабет (браслет)*/
	private VocColorIdentityPatient theDiabetIdentity;
}
