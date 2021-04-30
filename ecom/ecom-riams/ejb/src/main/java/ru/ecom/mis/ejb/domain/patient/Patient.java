package ru.ecom.mis.ejb.domain.patient;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcKodTer;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcOksm;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcQnp;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcStreetT;
import ru.ecom.mis.ejb.domain.birth.voc.VocNewBorn;
import ru.ecom.mis.ejb.domain.lpu.LpuArea;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressText;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.voc.*;
import ru.ecom.mis.ejb.domain.worker.voc.VocEducationType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;


/**
 * Пациент
 **/
@Entity
@AIndexes({
		  @AIndex(properties= {"patientSync"})
		, @AIndex(properties= {"snils"})
		, @AIndex(properties= {"passportNumber","passportSeries"})
		, @AIndex(properties= {"editDate"})
		, @AIndex(properties= {"createDate"})
		, @AIndex(properties= {"lastname","firstname","middlename", "birthday"})
		, @AIndex(properties= ("lpuArea"))
		, @AIndex(properties= ("lpuAreaAddressText"))
		, @AIndex(properties= ("lpu"))
		, @AIndex(properties= ("address"))
		, @AIndex(properties= ("nationality"))

})
@NamedQueries({
		@NamedQuery( name="Patient.getByLastAndFirstAndMiddleAndBirthday"
				, query="from Patient where lastname=:lastname and firstname=:firstname" +
				" and middlename=:middlename and birthday=:birthday order by lastname, firstname, middlename ")
})
@EntityListeners(DeleteListener.class)
@Table(schema="SQLUser")
@Getter
@Setter
public class Patient extends BaseEntity {

    /** Голосует в палате */
    private Boolean voteInRoom ;

	/** ППЗ */
	private Boolean ppz ;

	/** Не голосует */
	private Boolean notVote;

	/** Телефон */
	private String phone;

	/** Участок основного прикрепления*/
	@Comment("Участок основного прикрепления")
	@OneToOne
	@Deprecated
	public LpuArea getLpuArea() { return lpuArea ; }

	/** Адрес участка основного прикрепления */
	@Comment("Адрес участка основного прикрепления")
	@OneToOne
	@Deprecated
	public LpuAreaAddressText getLpuAreaAddressText() { return lpuAreaAddressText ; }

	/** ЛПУ основного прикрепления*/
	@Comment("ЛПУ основного прикрепления")
	@OneToOne
	@Deprecated
	public MisLpu getLpu() { return lpu ; }

	/** Район города и области для фонда */
	@Comment("Район города и области для фонда")
	@OneToOne
	public VocRayon getRayon() {return rayon;}

	/** Пол */
	@Comment("Пол")
	@OneToOne
	public VocSex getSex() { return sex ; }

	/** Тип удостоверения личности */
	@Comment("Тип удостоверения личности")
	@OneToOne
	public VocIdentityCard getPassportType() { return passportType ; }

	/** Социальный статус, в т.ч. и занятость */
	@Comment("Социальный статус, в т.ч. и занятость")
	@OneToOne
	public VocSocialStatus getSocialStatus() { return statusSocial ; }
	public void setSocialStatus(VocSocialStatus statusSocial) {
		this.statusSocial = statusSocial;
	}

	/** Адрес */
	@Comment("Адрес")
	@OneToOne
	public Address getAddress() { return address ; }

	/** Адрес проживания */
	@Comment("Адрес проживания")
	@OneToOne
	public Address getRealAddress() {return realAddress;}


	/** Корпус по адресу проживания */
	private String realHouseBuilding;

	/** Национальность */
	@Comment("Национальность")
	@OneToOne
	public VocEthnicity getEthnicity() {return ethnicity;}

	/** Семейное положение */
	@Comment("Семейное положение")
	@OneToOne
	public VocMarriageStatus getMarriageStatus() {return marriageStatus;}

	/** Вид образования */
	@Comment("Вид образования")
	@OneToOne
	public VocEducationType getEducationType() {return educationType;}


	/** Родственники */
	@Comment("Родственники")
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL )
	public List<Kinsman> getKinsmen() {return kinsmen;}

	@Transient
	public String getAddressRegistration() {
		if (address!=null ) return address.getAddressInfo(houseNumber, houseBuilding, flatNumber)  ;
		if (territoryRegistrationNonresident!=null) {
			StringBuilder nonres = new StringBuilder();
			nonres.append(" ").append(territoryRegistrationNonresident.getName()).append(", ") ;
			nonres.append(regionRegistrationNonresident) ;
			if (typeSettlementNonresident!=null) {
				nonres.append(" ").append(typeSettlementNonresident.getName()).append(" ") ;
			}  else {
				nonres.append(" нас.пункт ");
			}
			nonres.append(settlementNonresident) ;
			if (typeStreetNonresident!=null) {
				nonres.append(" ").append(typeStreetNonresident.getName()).append(" ");
			} else {
				nonres.append(" тип ул. ") ;
			}
			nonres.append(streetNonresident) ;
			if (!StringUtil.isNullOrEmpty(houseNonresident)) nonres.append(" д.").append(houseNonresident).append(" ") ;
			if (!StringUtil.isNullOrEmpty(buildingHousesNonresident)) nonres.append(" корп.").append(buildingHousesNonresident).append(" ");
			if (!StringUtil.isNullOrEmpty(apartmentNonresident)) nonres.append(" кв.").append(apartmentNonresident).append(" ");
			return nonres.toString() ;
		}
		return foreignRegistrationAddress!=null?foreignRegistrationAddress:"" ;
	}
	@Transient
	public String getAddressReal() {
		if (realAddress!=null ) return realAddress.getAddressInfo(realHouseNumber, realHouseBuilding, realFlatNumber)  ;

		return foreignRealAddress ;
	}


	/** Информация о пациенте */
	@Comment("Информация о пациенте")
	public String getPatientInfo() {
		return getFio();
	}
	/** Информация о пациенте */
	@Transient
	@Comment("Информация о пациенте")
	public String getFio() {
		StringBuilder sb = new StringBuilder();
		add(sb, lastname,"");
		add(sb, firstname," ");
		add(sb, middlename," ");
		if(getBirthdate()!=null) {
			add(sb, " г.р.", " ");
			add(sb, DateFormat.formatToDate(getBirthday())," ") ;
		}
		return sb.toString();
	}
	public Date getBirthday() {
		return getBirthdate();
	}
	public void setBirthday(Date date) {
		setBirthdate(date);
	}
	private String patientSync;

	private static void add(StringBuilder aSb, String aStr, String aPre) {
		if(!StringUtil.isNullOrEmpty(aStr)) {
			if(aSb.length()!=0) aSb.append(aPre) ;
			aSb.append(aStr) ;
		}
	}
	public void setPatientInfo(String aPatientInfo) { }
	@Comment("Информация по адресу")
	@Transient
	public String getAddressInfo() {
		return address!=null ? address.getAddressInfo(houseNumber, houseBuilding, flatNumber) : "" ;
	}
	@Comment ("Дом с корпусом")
	@Transient
	public String getAddressHouse() {
		StringBuilder ret= new StringBuilder () ;
		ret.append(houseNumber);
		if (houseBuilding!=null && !houseBuilding.equals("")) {
			ret.append("/").append(houseBuilding);
		}
		return ret.toString() ;
	}

	/** Гражданство */
	@Comment("Гражданство")
	@OneToOne
	public OmcOksm getNationality() {return nationality;}

	/** Территория регистрации  (иногороднего) */
	@Comment("Территория регистрации")
	@OneToOne
	public OmcKodTer getTerritoryRegistrationNonresident() {
		return territoryRegistrationNonresident;
	}


	/** Вид населенного пункта  (иногороднего) */
	@Comment("Вид населенного пункта")
	@OneToOne
	public OmcQnp getTypeSettlementNonresident() {
		return typeSettlementNonresident;
	}

	/** Тип наименования улицы  (иногороднего) */
	@Comment("Тип наименования улицы")
	@OneToOne
	public OmcStreetT getTypeStreetNonresident() {
		return typeStreetNonresident;
	}

	/** Дополнительный статус */
	@Comment("Дополнительный статус")
	@OneToOne
	public VocAdditionStatus getAdditionStatus() {return additionStatus;}

	/** Район проживания */
	@Comment("Район проживания")
	@OneToOne
	public VocRayon getRealRayon() {return realRayon;}

	/** Район проживания */
	private VocRayon realRayon;
	/** Почтовый индекс иногороднего */
	private String nonresidentZipcode;
	/** Почтовый индекс проживания */
	private String realZipcode;
	/** Почтовый индекс */
	private String zipcode;
	/** Дополнительный статус */
	private VocAdditionStatus additionStatus;

	/** Квартира (иногороднего) */
	private String apartmentNonresident;
	/** Корпус дома (иногороднего) */
	private String buildingHousesNonresident;
	/** Дом (иногороднего) */
	private String houseNonresident;
	/** Наименование улицы  (иногороднего) */
	private String streetNonresident;
	/** Тип наименования улицы  (иногороднего) */
	private OmcStreetT typeStreetNonresident;
	/** Населенный пункт  (иногороднего) */
	private String settlementNonresident;
	/** Вид населенного пункта  (иногороднего) */
	private OmcQnp typeSettlementNonresident;
	/** Район проживания  (иногороднего) */
	private String regionRegistrationNonresident;
	/** Территория регистрации  (иногороднего) */
	private OmcKodTer territoryRegistrationNonresident;

	/** Гражданство */
	private OmcOksm nationality;

	/** Участок основного прикрепления */
	private LpuArea lpuArea ;
	/** Адрес участка основного прикрепления */
	private LpuAreaAddressText lpuAreaAddressText ;
	/** ЛПУ основного прикрепления */
	private MisLpu lpu ;
	/** Район города и области для фонда */
	private VocRayon rayon;
	/** Имя */
	private String firstname ;
	/** Фамилия */
	private String lastname ;
	/** Отчества */
	private String middlename ;
	/** Дата рождения */
	private Date birthdate ;
	/** Пол */
	private VocSex sex ;
	/** Место работы */
	private String works ;
	/** ИНН */
	private Long inn ;
	/** Тип удостоверения личности */
	private VocIdentityCard passportType ;
	/** Социальный статус, в т.ч. и занятость */
	private VocSocialStatus statusSocial ;
	/** Номер паспорта */
	private String passportNumber ;
	/** Серия паспорта */
	private String passportSeries ;
	/** Дата выдачи */
	private Date passportDateIssued ;
	/** Кем выдан */
	private String passportWhomIssued ;
	/** СНИЛС */
	private String snils ;
	/** Адрес */
	private Address address ;
	/** Адрес проживания */
	private Address realAddress;
	/** Дом по адресу проживания */
	private String realHouseNumber;
	/** Квартира по адресу проживания */
	private String realFlatNumber;
	/** Дом */
	private String houseNumber ;
	/** Корпус */
	private String houseBuilding ;
	/** Квартира */
	private String flatNumber ;
	/** Дата смерти */
	private Date deathDate;
	/** Недействительность*/
	private Boolean noActuality;
	/** Национальность */
	private VocEthnicity ethnicity;
	/** Семейное положение */
	private VocMarriageStatus marriageStatus;
	/** Вид образования */
	private VocEducationType educationType;
	/** Должность */
	private String workPost;
	/** Примечание */
	private String notice;
	/** Иностранный адрес проживания */
	private String foreignRealAddress;
	/** Иностранный адрес регистрации */
	private String foreignRegistrationAddress;
	/** Родственники */
	private List<Kinsman> kinsmen;
	/**
	 * Горожанин
	 */
	private Boolean townsman;
	/**
	 * Участник ВОВ
	 */
	private Boolean greatPatrioticWarPaticipant;
	/**
	 * Число законченных классов общеобразовательной школы
	 */
	private Integer generalEducationGradeNumber;
	/**
	 * Источник средств существования
	 */
	@Comment("Источник средств существования")
	@OneToOne
	public VocLivelihoodSource getLivelihoodSource() {
		return livelihoodSource;
	}
	/**
	 * Источник средств существования
	 */
	private VocLivelihoodSource livelihoodSource;
	/**
	 * Условия проживания
	 */
	@Comment("Условия проживания")
	@OneToOne
	public VocResidenceConditions getResidenceConditions() {
		return residenceConditions;
	}
	/**
	 * Условия проживания
	 */
	private VocResidenceConditions residenceConditions;
	/**
	 * Проживает в семье
	 */
	private Boolean familyResident;
	/**
	 * Дееспособный
	 */
	private Boolean incapable;
	/**
	 * Дата первого заполнения
	 */
	private Date firstRegistrationDate;

	/** Новорожденный */
	@Comment("Новорожденный")
	@OneToOne
	public VocNewBorn getNewborn() {return newborn;}

	/** Новорожденный */
	private VocNewBorn newborn;
	/** Место рождения */
	private String birthPlace;
	/** Пользователь, последний редактировающий запись */
	private String editUsername;
	/** Дата последнего редактирования */
	private Date editDate;
	/** Пользователь, создавший запись */
	private String createUsername;
	/** Дата создания */
	private Date createDate;

	/** Код подразделения, выдавшего паспорт */
	private String passportCodeDivision;
	/** Кем выдан паспорт */
	@Comment("Кем выдан паспорт")
	@OneToOne
	public VocPassportWhomIssue getPassportDivision() {
		return passportDivision;
	}

	/** Кем выдан паспорт */
	private VocPassportWhomIssue passportDivision;
	/** Справочник по месту рождения */
	@Comment("Справочник по месту рождения")
	@OneToOne
	public VocPassportBirthPlace getPassportBirthPlace() {
		return passportBirthPlace;
	}

	/** Справочник по месту рождения */
	private VocPassportBirthPlace passportBirthPlace;

	/** Соотечественник */
	private Boolean isCompatriot;
	/** Единый номер застрахованного */
	private String commonNumber;

	/** Категория ребенка */
	@Comment("Категория ребенка")
	@OneToOne
	public VocCategoryChild getCategoryChild() {return categoryChild;}

	/** Категория ребенка */
	private VocCategoryChild categoryChild;

	/** Вес новорожденного */
	private Long newbornWeight;

	/** Цветовая характеристика */
	private Boolean colorType;

	private PatientFond patientFond;

	@Comment("Актуальная проверка по фонду")
	@OneToOne
	public PatientFond getPatientFond() {
		return patientFond;
	}

	private Boolean isCheckFondError=false;


	/** Цвета браслета пациента */
	private List<ColorIdentityPatient> colorsIdentity;

	/** Цвета браслета пациента */
	@Comment("Цвета браслета пациента")
	@ManyToMany
	public List<ColorIdentityPatient> getColorsIdentity() {return colorsIdentity;}
}
