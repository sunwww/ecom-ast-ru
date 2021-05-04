package ru.ecom.mis.ejb.domain.medcase.hospital;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

@Comment("Госпитализации данные фонда")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "hospitalMedCase" }) 
,@AIndex(properties = { "numberFond" })})
@Getter
@Setter
public class HospitalDataFond extends BaseEntity  implements IImportData {

	/** Направление */
	@Comment("Направление")
	@OneToOne
	public MedCase getDirectMedCase() {return directMedCase;}

	/** Госпитализация */
	@Comment("Госпитализация")
	@OneToOne
	public MedCase getHospitalMedCase() {return hospitalMedCase;}

	/** Пол */
	@Comment("Пол")
	@OneToOne
	public VocSex getSex() {return sex;}

	/** Дата направления */
	private Date directDate;
	/** Пол */
	private VocSex sex;
	/** Дата госпитализации */
	private Date hospDate;
	/** Дата предварительной госпитализации */
	private Date preHospDate;
	/** Профиль */
	private String profile;
	/** Дата рождения */
	private Date birthday;
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
	private MedCase hospitalMedCase;
	/** Направление */
	private MedCase directMedCase;
	
	/** Номер стат.карты */
	private String statCard;
	/** Телефон */
	private String phone;
	/** Диагноз */
	private String diagnosis;
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
	private String snils;
	/** Дата импорта */
	private Date importDate;
	
	/** Table1 */
	private Boolean isTable1;
	/** Table2 */
	private Boolean isTable2;

	/** Table3 */
	private Boolean isTable3;

	/** Table4 */
	private Boolean isTable4;

	/** Table5 */
	private Boolean isTable5;
	
	/** Дата выписки */
	private Date hospDischargeDate;
	/** Время госпитализации */
	private Time hospTime;
	

	/** Отказ от госпитализации */
	private Long deniedHospital;

	/** Файл */
	private String filename5;

	/** Дата импорта */
	private Date dateImport5;

	/** Файл */
	private String filename4;
	

	/** Дата импорта */
	private Date dateImport4;
	

	/** Файл */
	private String filename3;
	

	/** Дата импорта */
	private Date dateImport3;

	/** Файл */
	private String filename2;

	/** Дата импорта */
	private Date dateImport2;
	

	/** Файл */
	private String filename1;

	/** Дата импорта */
	private Date dateImport1;

	/** Тип помощи дн круг */
	private String bedSubType;
	
	/** Детский профиль */
	private String forChild;
	
	/** Без госпитализаций */
	private Boolean withoutHosp;

    /** Время импорта */
    @Column(name="voc_time")
    public long getTime() { return time ; }
    /** Время импорта */
    private long time ;
    
	/** Фамилия имя отчетство */
	private String fio;
}
