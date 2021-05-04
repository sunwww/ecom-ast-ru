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

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class NewBorn extends BaseEntity{
	/** Умер до начала родовой деятельности */
	private Boolean deadBeforeLabors;
	
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return patient;}

	/** Кто принимал ребенка */
	@Comment("Кто принимал ребенка")
	@OneToOne
	public WorkFunction getChildDeliverer() {return childDeliverer;}

	/** Пол */
	@Comment("Пол")
	@OneToOne
	public VocSex getSex() {return sex;}


	/** Окружность груди */
	private Integer breastCircle;
	/** Прикрепление пуповины */
	@Comment("Прикрепление пуповины")
	@OneToOne
	public VocNewBornCordAttaching getCordAttaching() {return cordAttaching;}

	/** Зрелость */
	@Comment("Зрелость")
	@OneToOne
	public VocNewBornMaturity getMaturity() {return maturity;}

	/** Родился живым */
	@Comment("Родился живым")
	@OneToOne
	public VocLiveBorn getLiveBorn() {return liveBorn;}

	/** Вредное влияние матери: наследственность, заболевания, вредные привычки */
	@Comment("Вредное влияние матери: наследственность, вредные привычки")
	@Column(length=1024)
	public String getMothersHarmfulEffect() {return mothersHarmfulEffect;}

	/** Вредное влияние отца: наследственность, заболевания, вредные привычки */
	@Comment("Вредное влияние отца: наследственность, заболевания, вредные привычки")
	@Column(length=1024)
	public String getFathersHarmfulEffect() {return fathersHarmfulEffect;}

	/** Пороки развития */
	@Comment("Пороки развития")
	@Column(length=1024)
	public String getMalformations() {return malformations;}

	/** Проходимость пищевода */
	@Comment("Проходимость пищевода")
	public String getOesophagusPermeability() {return oesophagusPermeability;}

	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return medCase;}

	/** Роды */
	@Comment("Роды")
	@ManyToOne
	public ChildBirth getChildBirth() {return childBirth;}

	@Transient
	public String getLastname() {return patient!=null?patient.getLastname():"" ;}
	@Transient
	public String getFirstname() {return patient!=null?patient.getFirstname():"" ;}
	@Transient
	public String getMiddlename() {return patient!=null?patient.getMiddlename():"" ;}


	
	/** Ребенок */
	@Comment("Ребенок")
	@OneToOne
	public VocNewBorn getChild() {return child;}

	/** Ребенок */
	private VocNewBorn child;
	/** Время создания */
	private Time createTime;
	/** Дата создания */
	private Date createDate;
	/** Пользователь */
	private String username;
	/** Роды */
	private ChildBirth childBirth;
	/** Пациент */
	private Patient patient;
	/** Кто принимал ребенка */
	private WorkFunction childDeliverer;
	/** Дата рождения */
	private Date birthDate;
	/** Время рождения */
	private Time birthTime;
	/** Пол */
	private VocSex sex;
	/** Вес при рождении */
	private Integer birthWeight;
	/** Рост при рождении */
	private Integer birthHeight;
	/** Окружность головы */
	private Integer headCircle;
	/** Окружность плеч */
	private Integer shouldersCircle;
	/** Состояние при рождении */
	private String birthState;


	/** Длина пуповины */
	private Integer cordLength;
	/** Прикрепление пуповины */
	private VocNewBornCordAttaching cordAttaching;
	/** Зрелость */
	private VocNewBornMaturity maturity;
	/** Родился живым */
	private VocLiveBorn liveBorn;
	/** Вредное влияние матери: наследственность, заболевания, вредные привычки */
	private String mothersHarmfulEffect;
	/** Вредное влияние отца: наследственность, заболевания, вредные привычки */
	private String fathersHarmfulEffect;
	/** Пороки развития */
	private String malformations;
	/** Проходимость пищевода */
	private String oesophagusPermeability;
	/** Наличие ануса */
	private Boolean anus;
	/** Большой поперечный размер (см)*/
	private BigDecimal longTransversalSize;
	/** Малый поперечный размер (см)*/
	private BigDecimal shortTransversalSize;
	/** Большой косой размер (см) */
	private BigDecimal longSkewSize;
	/** Размер большой косой окружности (см)*/
	private BigDecimal longSkewCircleSize;
	/** Размер малой косой окружности (см) */
	private BigDecimal shortSkewCircleSize;
	/** Малый косой размер */
	private BigDecimal shortSkewSize;
	/** Размер прямой окружности (см) */
	private BigDecimal directCircleSize;
	/** Прямой размер (см) */
	private BigDecimal directSize;
	/** Вид вставки */
	private String setView;
	/** Вставленная часть тела */
	private String setPart;
	/** Объем кровопотери (мл) */
	private Integer hemorrhageVolume;
	
	/** СМО */
	private MedCase medCase;
	
	/** Оценка по Апгар */
	private Long apgarMark;
	
	/** Какой частью тела родился (головкой, ягодицами, ножками) */
	private Long partBodyBorn;

	/** Обвитие */
	@Comment("Обвитие")
	@OneToOne
	public VocBirthEntanglement getEntanglement() {return entanglement;}

	/** Кратность обвития */
	@Comment("Кратность обвития")
	@OneToOne
	public VocBirthEntanglementMultiplicity getEntanglementMultiplicity() {return entanglementMultiplicity;}

	/** Где обвитие произошло */
	@Comment("Где обвитие произошло")
	@OneToOne
	public VocBirthWhereEntanglement getWhereEntanglement() {return whereEntanglement;}

	/** Где обвитие произошло */
	private VocBirthWhereEntanglement whereEntanglement;
	/** Кратность обвития */
	private VocBirthEntanglementMultiplicity entanglementMultiplicity;
	/** Обвитие */
	private VocBirthEntanglement entanglement;
	
	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartment() {
		return department;
	}

	/** Отделение */
	private MisLpu department;

	/** Диабет (браслет)*/
	@Comment("Диабет (браслет)")
	@OneToOne
	public VocColorIdentityPatient getDiabetIdentity() {return diabetIdentity;}
	private VocColorIdentityPatient diabetIdentity;
}
