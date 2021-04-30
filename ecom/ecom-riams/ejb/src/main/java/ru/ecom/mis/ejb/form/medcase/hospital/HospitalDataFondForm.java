package ru.ecom.mis.ejb.form.medcase.hospital;


import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.hospital.HospitalDataFond;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

@Comment("Данные фонда по 263 приказу")
@EntityForm
@EntityFormPersistance(clazz= HospitalDataFond.class)
@WebTrail(comment = "Данные фонда по 263 приказу", nameProperties= "id", view="entityParentView-stac_dataFond.do")
//@Parent(property="surgicalOperation", parentForm= SurgicalOperationForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/HospitalDataFond")
@Setter
public class HospitalDataFondForm extends IdEntityForm{

	/** Направление */
	@Persist @Comment("Направление")
	public Long getDirectMedCase() {return directMedCase;}

	/** Госпитализация */
	@Persist @Comment("Госпитализация")
	public Long getHospitalMedCase() {return hospitalMedCase;}

	/** Экстренность */
	@Persist @Comment("Экстренность")
	public Integer getEmergency() {return emergency;}

	/** Номер направления из фонда */
	@Persist @Comment("Номер направления из фонда")
	public String getNumberFond() {return numberFond;}

	/** Фамилия */
	@Persist @Comment("Фамилия")
	public String getLastname() {return lastname;}

	/** Имя */
	@Persist @Comment("Имя")
	public String getFirstname() {return firstname;}

	/** Отчетсво */
	@Persist @Comment("Отчетсво")
	public String getMiddlename() {return middlename;}

	/** Дата рождения */
	@Persist @Comment("Дата рождения")
	@DateString @DoDateString
	public String getBirthday() {return birthday;}

	/** Профиль */
	@Persist @Comment("Профиль")
	public String getProfile() {return profile;}

	/** Дата предварительной госпитализации */
	@Persist @Comment("Дата предварительной госпитализации")
	@DateString @DoDateString
	public String getPreHospDate() {return preHospDate;}

	/** Дата госпитализации */
	@Persist @Comment("Дата госпитализации")
	@DateString @DoDateString
	public String getHospDate() {return hospDate;}

	/** Пол */
	@Persist @Comment("Пол")
	public Long getSex() {return sex;}

	/** Дата направления */
	@Persist @Comment("Дата направления")
	@DateString @DoDateString
	public String getDirectDate() {return directDate;}

	/** Дата направления */
	private String directDate;
	/** Пол */
	private Long sex;
	/** Дата госпитализации */
	private String hospDate;
	/** Дата предварительной госпитализации */
	private String preHospDate;
	/** Профиль */
	private String profile;
	/** Дата рождения */
	private String birthday;
	/** Отчетсво */
	private String middlename;
	/** Имя */
	private String firstname;
	/** Фамилия */
	private String lastname;
	/** Номер направления из фонда */
	private String numberFond;
	/** Экстренность */
	private Integer emergency;
	/** Госпитализация */
	private Long hospitalMedCase;
	/** Направление */
	private Long directMedCase;
	
	/** Диагноз */
	@Persist @Comment("Диагноз")
	public String getDiagnosis() {return diagnosis;}

	/** Телефон */
	@Persist @Comment("Телефон")
	public String getPhone() {return phone;}

	/** Номер стат.карты */
	@Persist @Comment("Номер стат.карты")
	public String getStatCard() {return statCard;}

	/** Номер стат.карты */
	private String statCard;
	/** Телефон */
	private String phone;
	/** Диагноз */
	private String diagnosis;
	
	/** Вид полиса */
	@Persist @Comment("Вид полиса")
	public String getTypePolicy() {return typePolicy;}

	/** Серия полиса */
	@Persist @Comment("Серия полиса")
	public String getSeriesPolicy() {return seriesPolicy;}

	/** Номер полиса */
	@Persist @Comment("Номер полиса")
	public String getNumberPolicy() {return numberPolicy;}

	/** СМО */
	@Persist @Comment("СМО")
	public String getSmo() {return smo;}

	/** СМО ОГРН */
	@Persist @Comment("СМО ОГРН")
	public String getSmoOgrn() {return smoOgrn;}

	/** СМО ОКАТО */
	@Persist @Comment("СМО ОКАТО")
	public String getSmoOkato() {return smoOkato;}

	/** СМО наименование */
	@Persist @Comment("СМО наименование")
	public String getSmoName() {return smoName;}

	/** Форма помощи */
	@Persist @Comment("Форма помощи")
	public String getFormHelp() {return formHelp;}

	/** Направ. ЛПУ */
	@Persist @Comment("Направ. ЛПУ")
	public String getOrderLpuCode() {return orderLpuCode;}

	/** Куда направлен пациент */
	@Persist @Comment("Куда направлен пациент")
	public String getDirectLpuCode() {return directLpuCode;}

	/** Куда направлен пациент */
	private String directLpuCode;
	/** Направ. ЛПУ */
	private String orderLpuCode;
	/** Форма помощи */
	private String formHelp;
	/** СМО наименование */
	private String smoName;
	/** СМО ОКАТО */
	private String smoOkato;
	/** СМО ОГРН */
	private String smoOgrn;
	/** СМО */
	private String smo;
	/** Номер полиса */
	private String numberPolicy;
	/** Серия полиса */
	private String seriesPolicy;
	/** Вид полиса */
	private String typePolicy;
	
	/** Снилс */
	@Persist @Comment("Снилс")
	public String getSnils() {return snils;}

	/** Снилс */
	private String snils;
	
	/** Дата импорта */
	@Persist @Comment("Дата импорта")
	@DateString @DoDateString
	public String getImportDate() {return importDate;}

	/** Дата импорта */
	private String importDate;
	
	/** Table1 */
	@Persist @Comment("Table1")
	public Boolean getIsTable1() {return isTable1;}

	/** Table1 */
	private Boolean isTable1;
	/** Table2 */
	@Persist @Comment("Table2")
	public Boolean getIsTable2() {return isTable2;}

	/** Table2 */
	private Boolean isTable2;
	/** Table3 */
	@Persist @Comment("Table3")
	public Boolean getIsTable3() {return isTable3;}

	/** Table3 */
	private Boolean isTable3;
	/** Table4 */
	@Persist @Comment("Table4")
	public Boolean getIsTable4() {return isTable4;}

	/** Table4 */
	private Boolean isTable4;
	/** Table4 */
	@Persist @Comment("Table5")
	public Boolean getIsTable5() {return isTable5;}

	/** Table5 */
	private Boolean isTable5;
	
	/** Время госпитализации */
	@Persist @Comment("Время госпитализации")
	@TimeString @DoTimeString
	public String getHospTime() {return hospTime;}

	/** Дата выписки */
	@Persist @Comment("Дата выписки")
	@DateString @DoDateString
	public String getHospDischargeDate() {return hospDischargeDate;}

	/** Дата выписки */
	private String hospDischargeDate;
	/** Время госпитализации */
	private String hospTime;
	
	/** Отказ от госпитализации */
	@Persist @Comment("Отказ от госпитализации")
	public Long getDeniedHospital() {return deniedHospital;}

	/** Отказ от госпитализации */
	private Long deniedHospital;
	
	/** Файл */
	@Persist @Comment("Файл")
	public String getFilename5() {return filename5;}

	/** Файл */
	private String filename5;
	
	/** Дата импорта */
	@Persist @Comment("Дата импорта")
	@DateString @DoDateString
	public String getDateImport5() {return dateImport5;}

	/** Дата импорта */
	private String dateImport5;
	
	/** Файл */
	@Persist @Comment("Файл")
	public String getFilename4() {return filename4;}

	/** Файл */
	private String filename4;
	
	/** Дата импорта */
	@Persist @Comment("Дата импорта")
	@DateString @DoDateString
	public String getDateImport4() {return dateImport4;}

	/** Дата импорта */
	private String dateImport4;
	
	/** Файл */
	@Persist @Comment("Файл")
	public String getFilename3() {return filename3;}

	/** Файл */
	private String filename3;
	
	/** Дата импорта */
	@Persist @Comment("Дата импорта")
	@DateString @DoDateString
	public String getDateImport3() {return dateImport3;}

	/** Дата импорта */
	private String dateImport3;
	
	/** Файл */
	@Persist @Comment("Файл")
	public String getFilename2() {return filename2;}

	/** Файл */
	private String filename2;
	
	/** Дата импорта */
	@Persist @Comment("Дата импорта")
	@DateString @DoDateString
	public String getDateImport2() {return dateImport2;}

	/** Дата импорта */
	private String dateImport2;
	

	/** Файл */
	@Persist @Comment("Файл")
	public String getFilename1() {return filename1;}

	/** Файл */
	private String filename1;
	
	/** Дата импорта */
	@Persist @Comment("Дата импорта")
	@DateString @DoDateString
	public String getDateImport1() {return dateImport1;}

	/** Дата импорта */
	private String dateImport1;
	
	
	/** Тип помощи дн круг */
	@Comment("Тип помощи дн круг")
	@Persist
	public String getBedSubType() {
		return bedSubType;
	}

	/** Тип помощи дн круг */
	private String bedSubType;


	
	/** Детский профиль */
	@Persist @Comment("Детский профиль")
	public String getForChild() {return forChild;}
	/** Детский профиль */
	private String forChild;
	
	/** Без госпитализаций */
	@Persist @Comment("Без госпитализаций")
	public Boolean getWithoutHosp() {return withoutHosp;}

	/** Без госпитализаций */
	private Boolean withoutHosp;

    /** Время импорта */
    public long getTime() { return time ; }

    /** Время импорта */
    private long time ;
    
    /** Фамилия имя отчетство */
	@Persist @Comment("Фамилия имя отчетство")
	public String getFio() {return fio;}

	/** Фамилия имя отчетство */
	private String fio;

}
