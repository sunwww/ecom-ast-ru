package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expert2.domain.voc.VocE2EntryFactor;
import ru.ecom.expert2.domain.voc.VocE2EntrySubType;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.ecom.expert2.domain.voc.VocE2VidSluch;
import ru.ecom.expert2.domain.voc.enums.VocListEntryTypeCode;
import ru.ecom.expert2.domain.voc.federal.*;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

@Entity
@UnDeletable
@AIndexes({
        @AIndex(properties = {"listEntry"})
        , @AIndex(properties = {"entryType"})
        , @AIndex(properties = {"lastname", "firstname", "middlename", "birthDate"})
        , @AIndex(properties = {"billDate", "billNumber"})
        , @AIndex(properties = {"historyNumber"})
        , @AIndex(properties = {"startDate"})
        , @AIndex(properties = {"finishDate"})
        , @AIndex(properties = {"serviceStream"})
        , @AIndex(properties = {"parentEntry"})
        , @AIndex(properties = {"reanimationEntry"})

})
@NamedQueries({
        @NamedQuery(name = "E2Entry.getAllByBillAndDate"
                , query = "from E2Entry e where billNumber=:billNumber and billDate=:billDate" +
                " and isDeleted is not true order by lastname, firstname, middlename "),
        @NamedQuery(name = "E2Entry.getAllByListEntryId"
                , query = "from E2Entry e where listEntry.id=:listEntryId" +
                " and isDeleted is not true"),
        @NamedQuery(name = "E2Entry.getAllByListEntryIdAndServiceStream"
                , query = "from E2Entry where listEntry.id=:listEntryId and serviceStream=:serviceStream" +
                " and isDeleted is not true "),
        @NamedQuery(name = "E2Entry.allByIds"
                , query = "from E2Entry e where id in (:ids)")
})
@Setter
@Getter
public class E2Entry extends BaseEntity {

    //сортировка от старым (более ранним) к новым (более поздним)
    public static final Comparator<E2Entry> oldFirst = (e1, e2) -> {
        if (e1.startDate == null || e2.startDate == null) {
            return 0;
        }
        int dayEquals = e1.startDate.compareTo(e2.startDate);

        if (dayEquals == 0) {
            if (e1.startTime == null || e2.startTime == null) {
                return 0;
            }
            return e1.startTime.compareTo(e2.startTime);
        }
        return dayEquals;
    };
    /**
     * Комментарии эксперта
     */
    private String comment;
    /**
     * Онкологические случаи
     */

    private Set<E2CancerEntry> cancerEntries;
    /**
     * Случаи введения лекарственного препарата (пока только covid-19)
     */
    private Set<E2DrugEntry> drugEntries;
    /**
     * Онкологический случай
     */
    private Boolean isCancer = false;
    /**
     * Вид случая (справочник VID_SLUCH)
     */

    private VocE2VidSluch vidSluch;
    /**
     * Цель посещения
     */

    private VocE2FondV025 visitPurpose;
    /**
     * Дата планируемой госпитализации
     */
    private Date planHospDate;
    /**
     * Дата направление на лечение
     */
    private Date directDate;
    /**
     * Иногородний
     */
    private Boolean isForeign = false;
    /**
     * Тип доп. диспансеризации
     */
    private String extDispType;
    /**
     * Возраст доп. диспансеризации
     */
    private String extDispAge;
    /**
     * Группа здоровья доп. диспансеризации
     */
    private String extDispHealthGroup;
    /**
     * Социальная группа доп. диспансеризации
     */
    private String extDispSocialGroup;
    /**
     * Назначения доп. диспансеризации
     */
    private String extDispAppointments;
    /**
     * Направлен на след. этап ДД
     */
    private Boolean extDispNextStage;
    /**
     * Результат диспансеризации
     */

    private VocE2FondV017 dispResult;
    /**
     * Признак детского возраста
     */
    private Boolean isChild;
    /**
     * Счет
     */

    private E2Bill bill;
    /**
     * Консультативно-диагностическое обращение
     */
    private Boolean isDiagnosticSpo;
    /**
     * Подтип записи
     */

    private VocE2EntrySubType subType;
    /**
     * Признак мобильной поликлиники
     */
    private Boolean isMobilePolyclinic;
    /**
     * Был произведен аборт по медицинским показаниям
     */
    private Boolean medicalAbort;
    /**
     * Доп. критерий КСГ
     */
    private String dopKritKSG;
    /**
     * Место приема пациента
     */
    private String workPlace;
    /**
     * Признак дефекта
     */
    private Boolean isDefect;
    /**
     * Случай объединен
     */
    private Boolean isUnion;
    /**
     * Расчет случая ФОМС
     */
    private String fondComment;
    /**
     * Удаленная запись
     */
    private Boolean isDeleted;
    /**
     * Случай реанимации
     */

    private E2Entry reanimationEntry;
    /**
     * Основная услуга случая
     */
    private String mainService;
    /**
     * Основной МКБ случая
     */
    private String mainMkb;
    /**
     * Не подавать на оплату
     */
    private Boolean doNotSend;
    /**
     * Условия оказания мед. помощи
     */

    private VocE2FondV006 medHelpUsl;
    /**
     * Вид медицинской помощи
     */

    private VocE2FondV008 medHelpKind;
    /**
     * Профиль оказания мед. помощи
     */

    private VocE2MedHelpProfile medHelpProfile;
    /**
     * Базовый тариф
     */
    private BigDecimal baseTarif;
    /**
     * Формула расчета цены
     */
    private String costFormulaString;
    /**
     * Позиция группировщика, по которой высчитан КСГ
     */

    private GrouperKSGPosition ksgPosition;
    /**
     * Отделение не входит в ОМС
     */
    private Boolean noOmcDepartment;
    /**
     * Итоговый коэффициент
     */

    private BigDecimal totalCoefficient;
    /**
     * Родительский случай
     */

    private E2Entry parentEntry;
    /**
     * Сложность лечения пациента
     */

    private Set<E2CoefficientPatientDifficultyEntryLink> patientDifficulty;
    /**
     * Причины неполной оплаты
     */
    private String notFullPaymentReason;
    /**
     * Способ оплаты медицинской помощи
     */

    private VocE2FondV010 idsp;
    /**
     * Тип записи
     */ //стационар, ВМП, пол-ка, подушевка, ДД
    private VocListEntryTypeCode entryType;
    /**
     * Тип файла
     */ //P, Z, DF, раки
    private String fileType;
    /**
     * Многоплодная беременность
     */
    private Boolean multiplyBirth;
    /**
     * Специальность врача по фонду V021
     */

    private VocE2FondV021 fondDoctorSpecV021;
    /**
     * Исход случая
     */

    private VocE2FondV012 fondIshod;
    /**
     * Результат оказания медицинской помощи
     */

    private VocE2FondV009 fondResult;
    /**
     * ОКАТО регистрации
     */
    @Deprecated // нафиг не нужно
    private String okatoReg;
    /**
     * policyMedcaseString
     */
    private String policyMedcaseString;
    /**
     * policyKinsmanString
     */

    private String policyKinsmanString;
    /**
     * policyPatientString
     */

    private String policyPatientString;
    /**
     * Количество рожденных детей
     */
    private Long newbornAmount;
    /**
     * Признак исправленной записи
     */
    private Boolean prnov;
    /**
     * Номер счета
     */

    private String billNumber = "";
    /**
     * Дата счета
     */
    private Date billDate;
    /**
     * Единый номер пациента (представителя)
     */
    private String commonNumber;
    /**
     * Тип полиса OMC
     */
    private String medPolicyType;
    /**
     * Серия полиса
     */
    private String medPolicySeries;
    /**
     * Номер полиса
     */
    private String medPolicyNumber;
    /**
     * Страх. компания (федеральный 5 значный код)
     */
    private String insuranceCompanyCode;
    /**
     * Название страх. компании
     */

    private String insuranceCompanyName;
    /**
     * ОГРН страховой компании
     */
    private String insuranceCompanyOgrn;
    /**
     * Регион нахождения страховой компании
     */
    private String insuranceCompanyTerritory;
    /**
     * ИД отделения СЛО
     */
    private Long departmentId;
    /**
     * Родовое отделение
     */
    private Boolean isChildBirthDepartment = false;
    /**
     * Услуги по случаю
     */

    private Set<EntryMedService> medServices;
    /**
     * Список диагнозов по случаю
     */

    private Set<EntryDiagnosis> diagnosis;
    /**
     * Полис представителя
     */
    private String kinsmanSnils;
    /**
     * КСГ
     */

    private VocKsg ksg;
    /**
     * Список операций
     */

    private String operationList;
    /**
     * Список выполненных назначений
     */

    private String prescriptionList;
    /**
     * Список диагнозов по случаю
     */

    private String diagnosisList;
    /**
     * Идентификатор пациента во внешней системе
     */
    private Long externalPatientId;
    /**
     * Фамилия представителя
     */
    private String kinsmanLastname;
    /**
     * Имя представитель
     */
    private String kinsmanFirstname;
    /**
     * Отчество представителя
     */
    private String kinsmanMiddlename;
    /**
     * Дата рождения представителя
     */
    private Date kinsmanBirthDate;
    /**
     * Пол представителя
     */
    private String kinsmanSex;
    /**
     * Тип родственной связи
     */
    private String kinsmanRole;
    /**
     * Фамилия пациента
     */
    private String lastname;
    /**
     * Имя пациента
     */
    private String firstname;
    /**
     * Отчество пациента
     */
    private String middlename;
    /**
     * СНИЛС пациента
     */
    private String patientSnils;
    /**
     * Гражданство пациента
     */
    private String nationality;
    /**
     * КЛАДР регистрации пациента (представителя)
     */
    private String kladrRegistration;
    /**
     * КЛАДР проживания пациента (представителя)
     */
    private String kladrReal;
    /**
     * Адрес проживания пациента (представителя)
     */
    private String addressRegistration;
    /**
     * Адрес проживания пациента (представителя)
     */
    private String addressReal;
    /**
     * Тип паспорта (ДУЛ)
     */
    private String passportType;
    /**
     * Серия паспорта
     */
    private String passportSeries;
    /**
     * Номер паспорта
     */
    private String passportNumber;
    /**
     * Дата выдачи паспорта
     */
    private Date passportDateIssued;
    /**
     * Кем выдан паспорт
     */
    private String passportWhomIssued;
    /**
     * Дата рождения пациента
     */
    private Date birthDate;
    /**
     * Пол пациента
     */
    private String sex;
    /**
     * Социальный статус пациента
     */
    private String socialStatus;
    /**
     * Каким по счету родился ребенок
     */
    private Long birthOrder;
    /**
     * Код ЛПУ
     */
    private String lpuCode;
    /**
     * ВМП - кол-во установленных стентов
     */
    private Long vmpStantAmount;
    /**
     * Номер талона ВМП
     */
    private String vmpTicketNumber;
    /**
     * Талон ВМП - дата выдачи талона
     */
    private Date vmpTicketDate;
    /**
     * Талон ВМП - дата плановой госпитализации
     */
    private Date vmpPlanHospDate;
    /**
     * Вид ВМП
     */
    private String vmpKind;
    /**
     * Метод ВМП
     */
    private String vmpMethod;
    /**
     * модель пациента ВМП
     */
    private Long vmpPatientModelId;
    /**
     * Поток обслуживания
     */
    private String serviceStream;
    /**
     * Были сообщения в полицию
     */
    private Boolean isCriminalMessage = false;
    /**
     * Находился по уходу за родственников
     */
    private Boolean hotelServices = false;
    /**
     * Тип стационара (Дневной, круглосуточный)
     */
    private String stacType;
    /**
     * ЛПУ прикрепления
     */
    private String attachedLpu;
    /**
     * Профиль помощи
     */
    private String helpKind;
    /**
     * Идентификатор случая во внешней системе
     */
    private Long externalId;
    /**
     * Идентификатор пред. случая во внешней системе
     */
    private Long externalPrevMedcaseId;
    /**
     * Идентификатор госпитализации во внешней системе
     */
    private Long externalParentId;
    /**
     * Заполнение
     */
    private E2ListEntry listEntry;
    /**
     * Дата начала случая
     */
    private Date startDate;
    /**
     * Время начала случая
     */
    private Time startTime;
    /**
     * Дата окончания случая
     */
    private Date finishDate;
    /**
     * Время окончания случая
     */
    private Time finishTime;
    /**
     * Количество календарных дней
     */
    private Long calendarDays;
    /**
     * Количество койкодней
     */
    private Long bedDays;
    /**
     * Номер истории болезни
     */
    private String historyNumber;
    /**
     * Название отделения
     */
    private String departmentName;
    /**
     * Тип отделения
     */
    private String departmentType;
    /**
     * Код отделения
     */
    private String departmentCode;
    /**
     * Код отделения длинный
     */
    private String departmentAddressCode;
    /**
     * ФИО врача
     */
    private String doctorName;
    /**
     * Должность врача
     */
    private String doctorWorkfunction;
    /**
     * СНИЛС врача
     */
    private String doctorSnils;
    /**
     * Экстренность
     */
    private Boolean isEmergency = false;
    /**
     * Направившее ЛПУ
     */
    private String directLpu;
    /**
     * Вес новорожденного
     */
    private Long newbornWeight;
    /**
     * Тип направившего ЛПУ
     */
    private String directLpuType;
    /**
     * Номер направление ФОМС
     */
    private String ticket263Number;
    /**
     * Результат госпитализации (vho.code||'#'||vrd.code||'#'||vhr.omcCode)
     */
    private String result;
    /**
     * Тип коек
     */
    private String bedSubType;
    /**
     * Дата начала госпитализации
     */
    private Date hospitalStartDate;
    /**
     * Дата окончания госпитализации
     */
    private Date hospitalFinishDate;
    /**
     * Услуги
     */
    private String services;
    /**
     * Цена случая
     */
    private BigDecimal cost;
    /**
     * Ручное редактирование КСГ
     */
    private Boolean isManualKsg = false;
    /**
     * Санкции
     */

    private Set<E2EntrySanction> sanctionList;
    /**
     * Ошибки проверки
     */

    private Set<E2EntryError> errorList;
    /**
     * Профиль койки
     */

    private VocE2FondV020 bedProfile;
    /**
     * Рост пациента
     */
    private Integer height;
    /**
     * Вес пациента
     */
    private Integer weigth;
    /**
     * Реабилитационная койка
     */
    private Boolean isRehabBed;
    /**
     * Особенности подачи
     */
    private Set<VocE2EntryFactor> factorList;
    /**
     * Место рождения
     */
    private String birthPlace;
    /**
     * Доп поле для группировки
     */
    private String addGroupFld = "";
    /**
     * Стоматологический случай
     */
    private Boolean isDentalCase;
    private Boolean isNedonosh = false;
    private String covidPrescriptions;
    /**
     * Данные дисп. учета (поставлен, снят, состоит)
     */
    private String dn;
    /**
     * Диагноз выявлен впервые
     */
    private Boolean firstTimeDiagnosis;


    public E2Entry() {
        addGroupFld = "";
        setIsDeleted(false);
    }

    @Transient
    /* Вычисляем основной диагноз по записи */
    public EntryDiagnosis getMainEntryDiagnosis() {
        Set<EntryDiagnosis> list = getDiagnosis();
        for (EntryDiagnosis d : list) {
            if (d.getRegistrationType() != null && d.getRegistrationType().getCode().equals("3")
                    && d.getPriority() != null && d.getPriority().getCode().equals("1")) {
                return d;
            }
        }
        for (EntryDiagnosis d : list) {
            if (d.getRegistrationType() != null && d.getRegistrationType().getCode().equals("4")
                    && d.getPriority() != null && d.getPriority().getCode().equals("1")) {
                return d;
            }
        }
        return list.iterator().next();
    }

    @OneToMany(mappedBy = "entry")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<E2CancerEntry> getCancerEntries() {
        return cancerEntries;
    }

    @OneToMany(mappedBy = "entry")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<E2DrugEntry> getDrugEntries() {
        return drugEntries;
    }

    @OneToOne
    public VocE2VidSluch getVidSluch() {
        return vidSluch;
    }

    @OneToOne
    public VocE2FondV025 getVisitPurpose() {
        return visitPurpose;
    }

    @OneToOne
    public VocE2FondV017 getDispResult() {
        return dispResult;
    }

    @OneToOne
    public E2Bill getBill() {
        return bill;
    }

    @OneToOne
    public VocE2EntrySubType getSubType() {
        return subType;
    }

    @OneToOne
    public E2Entry getReanimationEntry() {
        return reanimationEntry;
    }

    @OneToOne
    public VocE2FondV006 getMedHelpUsl() {
        return medHelpUsl;
    }

    @OneToOne
    public VocE2FondV008 getMedHelpKind() {
        return medHelpKind;
    }

    @OneToOne
    public VocE2MedHelpProfile getMedHelpProfile() {
        return medHelpProfile;
    }

    @OneToOne
    public GrouperKSGPosition getKsgPosition() {
        return ksgPosition;
    }

    @Column(precision = 15, scale = 12)
    public BigDecimal getTotalCoefficient() {
        return totalCoefficient;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public E2Entry getParentEntry() {
        return parentEntry;
    }

    @OneToMany(mappedBy = "entry")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<E2CoefficientPatientDifficultyEntryLink> getPatientDifficulty() {
        return patientDifficulty;
    }

    @OneToOne
    public VocE2FondV010 getIdsp() {
        return idsp;
    }

    @OneToOne
    public VocE2FondV021 getFondDoctorSpecV021() {
        return fondDoctorSpecV021;
    }

    @OneToOne
    public VocE2FondV012 getFondIshod() {
        return fondIshod;
    }

    @OneToOne
    public VocE2FondV009 getFondResult() {
        return fondResult;
    }

    @Column(length = 2500)
    public String getPolicyMedcaseString() {
        return policyMedcaseString;
    }

    @Column(length = 2500)
    public String getPolicyKinsmanString() {
        return policyKinsmanString;
    }

    @Column(length = 2500)
    public String getPolicyPatientString() {
        return policyPatientString;
    }

    @Column(nullable = false, columnDefinition = "character varying default ''")
    public String getBillNumber() {
        return billNumber;
    }

    @Column(length = 2500)
    public String getInsuranceCompanyName() {
        return insuranceCompanyName;
    }

    @OneToMany(mappedBy = "entry")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<EntryMedService> getMedServices() {
        return medServices;
    }

    @OneToMany(mappedBy = "entry")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<EntryDiagnosis> getDiagnosis() {
        return diagnosis;
    }

    @OneToOne
    public VocKsg getKsg() {
        return ksg;
    }

    @Column(length = 2500)
    public String getOperationList() {
        return operationList;
    }

    @Column(length = 250000)
    public String getPrescriptionList() {
        return prescriptionList;
    }

    @Column(length = 2500)
    public String getDiagnosisList() {
        return diagnosisList;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public E2ListEntry getListEntry() {
        return listEntry;
    }

    @OneToMany(mappedBy = "entry")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<E2EntrySanction> getSanctionList() {
        return sanctionList;
    }

    @OneToMany(mappedBy = "entry")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<E2EntryError> getErrorList() {
        return errorList;
    }

    @OneToOne
    public VocE2FondV020 getBedProfile() {
        return bedProfile;
    }

    @ManyToMany
    @JoinTable(name = "e2entry_factor", joinColumns = @JoinColumn(name = "entry_id")
            , inverseJoinColumns = @JoinColumn(name = "factor_id"))
    public Set<VocE2EntryFactor> getFactorList() {
        return factorList;
    }

    @Enumerated(EnumType.STRING)
    public VocListEntryTypeCode getEntryType() {
        return entryType;
    }

    /**
     * Тип заполнения
     */
    @Comment("Тип заполнения")
    @Transient
    public VocListEntryTypeCode getEntryListType() {
        return listEntry != null ? listEntry.getEntryType().getCode() : null;
    }

    /**
     * Список кодов услуг
     */
    @Comment("Список кодов услуг")
    @Transient
    public List<String> getMedServicesCodes() {
        List<String> list = new ArrayList<>();
        for (EntryMedService medService : getMedServices()) {
            if (medService.getMedService() != null) {
                list.add(medService.getMedService().getCode());
            }
        }
        Collections.sort(list);
        return list;
    }

    @Transient
    public String getPassportInfo() {
        return passportSeries + " N " + passportNumber;
    }

    @Transient
    public String getPolicyInfo() {
        return medPolicySeries + " N " + medPolicyNumber;
    }

    @Transient
    public boolean havePrevMedCase() {
        return getExternalPrevMedcaseId() != null && getExternalPrevMedcaseId() > 0L;
    }

    @PrePersist
    void onPrePersist() {
        if (isEmergency == null) {
            isEmergency = false;
        }
        if (isCriminalMessage == null) {
            isCriminalMessage = false;
        }
        if (noOmcDepartment == null) {
            noOmcDepartment = false;
        }
        if (addGroupFld == null) {
            addGroupFld = "";
        }
    }

    @Transient
    public String getCovidPrescriptions() {
        return covidPrescriptions;
    }

    public void setCovidPrescriptions(String covidPrescriptions) {
        this.covidPrescriptions = covidPrescriptions;
    }

    @Transient
    public Boolean getIsNedonosh() {
        return isNedonosh;
    }
}
