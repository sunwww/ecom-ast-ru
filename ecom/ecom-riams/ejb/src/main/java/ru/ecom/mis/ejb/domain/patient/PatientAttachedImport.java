package ru.ecom.mis.ejb.domain.patient;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.ecom.mis.ejb.domain.patient.voc.VocRayon;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;

@Comment("Пациент прикрепление")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
	@AIndex(properties = { "lastname","firstname","middlename","birthday" }),
	@AIndex(properties = { "commonNumber" })
})
@Getter
@Setter
public class PatientAttachedImport extends BaseEntity implements IImportData {

	public static final String STATUS_CHECK_TYPE_AUTOMATIC="A" ;
	public static final String STATUS_CHECK_TYPE_PACKAGE="P" ;
	public static final String STATUS_CHECK_TYPE_MANUAL="M" ;

    /** Единый номер застрахованного */
	@Comment("Единый номер застрахованного")
    @AFormatFieldSuggest({"ENP" })
	public String getCommonNumber() {return commonNumber;}

	/** Фамилия */
	@Comment("Фамилия")
	@AFormatFieldSuggest({"FAM" })
	public String getLastname() {return lastname;}

	/** Имя */
	@Comment("Имя")
	@AFormatFieldSuggest({"IM" })
	public String getFirstname() {return firstname;}

	/** Отчество */
	@Comment("Отчество")
	@AFormatFieldSuggest({"OT" })
	public String getMiddlename() {return middlename;}

	/** Пол */
	@Comment("Пол")
	@AFormatFieldSuggest({"CW" })
	public String getSex() {return sex;}

	/** Дата рождения */
	@Comment("Дата рождения")
	@AFormatFieldSuggest({"DR" })
	public Date getBirthday() {return birthday;}

	/** СНИЛС */
	@Comment("СНИЛС")
	@AFormatFieldSuggest({"SS" })
	public String getSnils() {return snils;}

	/** Тип документа */
	@Comment("Тип документа")
	@AFormatFieldSuggest({"DOCTP" })
	public String getDocType() {return docType;}

	/** Серия документа */
	@Comment("Серия документа")
	@AFormatFieldSuggest({"DOCS" })
	public String getDocSeries() {return docSeries;}

	/** Номер документа */
	@Comment("Номер документа")
	@AFormatFieldSuggest({"DOCN" })
	public String getDocNumber() {return docNumber;}

	/** Дата выдачи документа */
	@Comment("Дата выдачи документа")
	@AFormatFieldSuggest({"DOCDT" })
	public Date getDocDateIssued() {return docDateIssued;}

	/** Место рождения */
	@Comment("Место рождения")
	@AFormatFieldSuggest({"MR" })
	public String getBirthPlace() {return birthPlace;}

	/** Бомж */
	@Comment("Бомж")
	@AFormatFieldSuggest({"BOMJ" })
	public String getBomj() {return bomj;}

	/** Область */
	@Comment("Область")
	@AFormatFieldSuggest({"SUBJ" })
	public String getRegion() {return region;}

	/** RN& */
	@Comment("RN&")
	@AFormatFieldSuggest({"RN" })
	public String getRn() {return rn;}

	/** Индекс */
	@Comment("Индекс")
	@AFormatFieldSuggest({"INDX" })
	public String getIndex() {return index;}

	/** Район наименование */
	@Comment("Район наименование")
	@AFormatFieldSuggest({"RNNAME" })
	public String getRayonName() {return rayonName;}

	/** Город */
	@Comment("Город")
	@AFormatFieldSuggest({"CITY" })
	public String getCity() {return city;}

	/** NP& */
	@Comment("NP&")
	@AFormatFieldSuggest({"NP" })
	public String getNp() {return np;}

	/** Улица */
	@Comment("Улица")
	@AFormatFieldSuggest({"UL" })
	public String getStreet() {return street;}

	/** Дом */
	@Comment("Дом")
	@AFormatFieldSuggest({"DOM" })
	public String getHouse() {return house;}

	/** Корпус */
	@Comment("Корпус")
	@AFormatFieldSuggest({"KOR" })
	public String getHousing() {return housing;}

	/** Квартира */
	@Comment("Квартира")
	@AFormatFieldSuggest({"KV" })
	public String getApartment() {return apartment;}

	/** Страховая компания */
	@Comment("Страховая компания")
	@AFormatFieldSuggest({"Q" })
	public String getInsCompName() {return insCompName;}

	/** Тип полиса */
	@Comment("Тип полиса")
	@AFormatFieldSuggest({"OPDOC" })
	public String getPolicyType() {return policyType;}

	/** Серия полиса */
	@Comment("Серия полиса")
	@AFormatFieldSuggest({"SPOL" })
	public String getPolicySeries() {return policySeries;}

	/** Номер полиса */
	@Comment("Номер полиса")
	@AFormatFieldSuggest({"NPOL" })
	public String getPolicyNumber() {return policyNumber;}

	/** Дата начала действия полиса */
	@Comment("Дата начала действия полиса")
	@AFormatFieldSuggest({"DBEG" })
	public Date getPolicyDateFrom() {return policyDateFrom;}

	/** Дата окончания действия полиса */
	@Comment("Дата окончания действия полиса")
	@AFormatFieldSuggest({"DEND" })
	public Date getPolicyDateTo() {return policyDateTo;}

	/** Дата окончания действия полиса */
	private Date policyDateTo;
	/** Дата начала действия полиса */
	private Date policyDateFrom;
	/** Номер полиса */
	private String policyNumber;
	/** Серия полиса */
	private String policySeries;
	/** Тип полиса */
	private String policyType;
	/** Страховая компания */
	private String insCompName;
	/** Квартира */
	private String apartment;
	/** Корпус */
	private String housing;
	/** Дом */
	private String house;
	/** Улица */
	private String street;
	/** NP& */
	private String np;
	/** Город */
	private String city;
	/** Район наименование */
	private String rayonName;
	/** Индекс */
	private String index;
	/** RN& */
	private String rn;
	/** Область */
	private String region;
	/** Бомж */
	private String bomj;
	/** Место рождения */
	private String birthPlace;
	/** Дата выдачи документа */
	private Date docDateIssued;
	/** Номер документа */
	private String docNumber;
	/** Серия документа */
	private String docSeries;
	/** Тип документа */
	private String docType;
	/** СНИЛС */
	private String snils;
	/** Дата рождения */
	private Date birthday;
	/** Пол */
	private String sex;
	/** Отчество */
	private String middlename;
	/** Имя */
	private String firstname;
	/** Фамилия */
	private String lastname;
	/** Единый номер застрахованного */
	private String commonNumber;
	

	/** Полис */
	@Comment("Полис")
	@OneToOne
	public MedPolicy getMedPolicy() {return medPolicy;}

	/** Адрес */
	@Comment("Адрес")
	@OneToOne
	public Address getAddressRegistration() {return addressRegistration;}


	/** Город врем */
	private String cityT;
	/** Адрес */
	private Address addressRegistration;
	/** Полис */
	private MedPolicy medPolicy;
	/** Пациент */
	private Long patient;

	/** Обновлен пациент */
	private Boolean isUpdatePatient;
	/** Создан новый пациент */
	private Boolean isCreateNewPatient;

    private long time ;
    
	/** Номер полиса измененный */
	private String policyNumberEdit;
	/** Серия полиса измененная */
	private String policySeriesEdit;
	
	/** Страховая компания */
	@Comment("Страховая компания")
	@OneToOne
	public RegInsuranceCompany getInsuranceCompany() {
		return insuranceCompany;
	}

	/** Страховая компания */
	private RegInsuranceCompany insuranceCompany;
	/** Район */
	@Comment("Район")
	@OneToOne
	public VocRayon getRayon() {return rayon;}

	/** Район */
	private VocRayon rayon;
	
	/** Страна */
	@Comment("Страна")
	@AFormatFieldSuggest({"CN" })
	public String getCountry() {
		return country;
	}

	/** Страна */
	private String country;
	
	/** Lpuauto */
	@Comment("Lpuauto")
	@AFormatFieldSuggest({"LPUAUTO" })
	public String getLpuauto() {return lpuauto;}

	/** Lpuauto */
	private String lpuauto;
	
	/** Lpu */
	@Comment("Lpu")
	@AFormatFieldSuggest({"LPU" })
	public String getLpu() {return lpu;}

	/** Lpu */
	private String lpu;
	
	/** Дата прикрепления */
	@Comment("Дата прикрепления")
	@AFormatFieldSuggest({"LPUDT" })
	public Date getLpuDateFrom() {return lpuDateFrom;}

	/** Дата прикрепления */
	private Date lpuDateFrom;
	
	/** Дата открепления */
	@Comment("Дата открепления")
	@AFormatFieldSuggest({"LPUDX" })
	public Date getLpuDateTo() {return lpuDateTo;}

	/** Дата открепления */
	private Date lpuDateTo;
	/** Кем выдан паспорт */
	@Comment("Кем выдан паспорт")
	@AFormatFieldSuggest({"DOCORG" })
	public String getDocWhom() {
		return docWhom;
	}

	/** Кем выдан паспорт */
	private String docWhom;

	/** Идентификатор ФОМС */
	@Comment("Идентификатор ФОМС")
	@AFormatFieldSuggest({"PID"})
	public String getFondId() {return fondId;}
	/** Идентификатор ФОМС */
	private String fondId;
	
	/** Код подразделения */
	@Comment("Код подразделения")
	@AFormatFieldSuggest({"KODPODR"})
	public String getDepartment() {return department;}
	/** Код подразделения */
	private String department;
	
	/** СНИЛС участкового врача */
	@Comment("СНИЛС участкового врача")
	@AFormatFieldSuggest({"SSD"})
	public String getDoctorSnils() {return doctorSnils;}
	/** СНИЛС участкового врача */
	private String doctorSnils;
	
	/** Телефон */
	private String phone;
	
	/** Участок */
	private String areaNumber;
}
