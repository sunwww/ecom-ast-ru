package ru.ecom.expert2.form;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2Entry;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = E2Entry.class)
@Comment("Случай в заполнении")
@WebTrail(comment = "Запись в заполнении", nameProperties = {"id","lastname","historyNumber"}, view = "entityView-e2_entry.do")
@Parent(property = "listEntry", parentForm = E2EntryListForm.class)
@EntityFormSecurityPrefix("/Policy/E2")
@Setter
public class E2EntryForm extends IdEntityForm {

//----------все поля ниже добавлены на jsp!!!
    /** Реабилитационная койка*/
    private Boolean isRehabBed;
    /** Стоматологический случай*/
    private Boolean isDentalCase;
    /** Рост пациента */
    private Integer height;
    /** Вес пациента */
    private Integer weigth;
    /** Профиль койки */
    private Long bedProfile;
    /** Комментарии эксперта */
    private String comment;
    /** Родовое отделение */
    private Boolean isChildBirthDepartment;
    /** Специальность врача по фонду */
    private Long fondDoctorSpecV021;
    /** Онкологический случай */
    private Boolean isCancer;
    /** Цель посещения */
    private Long visitPurpose;
    /** Вид случая (справочник VID_SLUCH) */
    private Long vidSluch;
    /** Дата планируемой госпитализации */
    private String planHospDate;
    /** Дата направление на лечение */
    private String directDate;
    /** Иногородний  */
    private Boolean isForeign =false;
    /** Тип доп. диспансеризации */
    private String extDispType;
    /** Возраст доп. диспансеризации */
    private String extDispAge;
    /** Группа здоровья доп. диспансеризации */
    private String extDispHealthGroup;
    /** Социальная группа доп. диспансеризации */
    private String extDispSocialGroup;
    /** Назначения доп. диспансеризации */
    private String extDispAppointments;
    /** Направлен на след. этап ДД */
    private Boolean extDispNextStage;
    /** Признак детского возраст */
    private Boolean isChild;
    /** Счет */
    private Long bill;
    /** Консультативно-диагностическое обращение */
    private Boolean isDiagnosticSpo;
    /** Количество календарных дней */
    private Long calendarDays;
    /** Подтип записи */
    private Long subType;
    /** Признак мобильной поликлиники */
    private Boolean isMobilePolyclinic;
    /** Был произведен аборт по медицинским показаниям */
    private Boolean medicalAbort;
    /** Доп. критерий КСГ */
    private String dopKritKSG;
    /** Место приема пациента */
    private String workPlace;
    /** Признак дефекта */
    private Boolean isDefect;
    /** Случай объединен */
    private Boolean isUnion;
    /** Расчет случая ФОМС */
    private String fondComment;
    /** Удаленная запись */
    private Boolean isDeleted;
    /** Случай реанимации */
    private Long reanimationEntry;
    /** Количество рожденных детей */
    private Long newbornAmount;
    /** Признак исправленной записи */
    private Boolean prnov;
    /** Единый номер пациента (представителя) */
    private String commonNumber;
    /** Название страх. компании */
    private String insuranceCompanyName;
    /** ОГРН страховой компании */
    private String insuranceCompanyOgrn;
    /** Регион нахождения страховой компании */
    private String insuranceCompanyTerritory;
    /** Профиль оказания мед. помощи */
    private Long medHelpProfile;
    /** Основная услуга случая */
    private String mainService;
    /** Основной МКБ случая */
    private String mainMkb;
    /** Не подавать на оплату */
    private Boolean doNotSend;
    /** Условия оказания мед. помощи */
    private Long medHelpUsl;
    /** Вид медицинской помощи */
    private Long medHelpKind;
    /** Номер счета */
    private String billNumber;
    /** Дата счета */
    private String billDate;
    /** Результат оказания медицинской помощи */
    private Long fondResult;
    /** Исход случая */
    private Long fondIshod;
    /** Многоплодная беременность */
    private Boolean multiplyBirth;
    /** ТИп записи */
    private String entryType;
    /** Тип файла */
    private String fileType;
    /** Способ оплаты медицинской помощи */
    private Long idsp;
    /** Причины неполной оплаты */
    private String notFullPaymentReason;
    /** Базовый тариф */
    private String baseTarif;
    /** Формула расчета цены */
    private String costFormulaString;
    /** Позиция группировщика, по которой высчитан КСГ */
    private Long ksgPosition;
    /** Отделение не входит в ОМС */
    private Boolean noOmcDepartment;
    /** Итоговый коэффициент */
    private String totalCoefficient;
    /** Родительский случай */
    private Long parentEntry;
    /** ОКАТО регистрации */
    private String okatoReg;
    /** КСГ */
    private Long ksg;
    /** Список операций */
    private String operationList;
    /** Номер истории болезни */
    private String historyNumber;
    /** Название отделения */
    private String departmentName;
    /** Тип отделения */
    private String departmentType;
    /** Код отделения */
    private String departmentCode;
    /** Код отделения длинный*/
    private String departmentAddressCode;
    /** ФИО врача */
    private String doctorName;
    /** Должность врача */
    private String doctorWorkfunction;
    /** СНИЛС врача */
    private String doctorSnils;
    /** Экстренность */
    private Boolean isEmergency;
    /** Направившее ЛПУ */
    private String directLpu;
    /** Вес новорожденного */
    private Long newbornWeight;
    /** Тип направившего ЛПУ */
    private String directLpuType;
    /** Номер направление ФОМС */
    private String ticket263Number;
    /** Результат госпитализации */
    private String result;
    /** Тип коек */
    private String bedSubType;
    /** Дата начала госпитализации */
    private String hospitalStartDate;
    /** Дата окончания госпитализации */
    private String hospitalFinishDate;
    /** Услуги */
    private String services;
    /** Цена случая */
    private String cost;
    /** Ручное редактирование цены */
    private Boolean isManualKsg;
    /** ИД отделения СЛО */
    private Long departmentId;
    /** Серия полиса */
    private String medPolicySeries;
    /** Номер полиса */
    private String medPolicyNumber;
    /** Страх. компания (федеральный код) */
    private String insuranceCompanyCode;
    /** Талон ВМП - дата выдачи талона */
    private String vmpTicketDate;
    /** Талон ВМП - дата плановой госпитализации */
    private String vmpPlanHospDate;
    /** Вид ВМП */
    private String vmpKind;
    /** Метод ВМП */
    private String vmpMethod;
    /** Поток обслуживания */
    private String serviceStream;
    /** Были сообщения в полицию */
    private Boolean isCriminalMessage;
    /** Находился по уходу за родственников */
    private Boolean hotelServices;
    /** Тип стационара */
    private String stacType;
    /** ЛПУ прикрепления */
    private String attachedLpu;
    /** Профиль помощи */
    private String helpKind;
    /** Идентификатор случая во внешней системе */
    private Long externalId;
    /** Идентификатор пред. случая во внешней системе */
    private Long externalPrevMedcaseId;
    /** Идентификатор госпитализации во внешней системе */
    private Long externalParentId;
    /** Вид полиса OMC */
    private String medPolicyType;
    /** Заполнение */
    private Long listEntry;
    /** Дата начала случая */
    private String startDate;
    /** Время начала случая */
    private String startTime;
    /** Дата окончания случая */
    private String finishDate;
    /** Время окончания случая */
    private String finishTime;
    /** Количество койкодней */
    private Long bedDays;
    /** Пол представителя */
    private String kinsmanSex;
    /** Тип родственной связи */
    private String kinsmanRole;
    /** Фамилия пациента */
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
    private String passportDateIssued;
    /** Кем выдан паспорт */
    private String passportWhomIssued;
    /** Дата рождения пациента */
    private String birthDate;
    /** Пол пациента */
    private String sex;
    /** Социальный статус пациента */
    private String socialStatus;
    /** Каким по счету родился ребенок */
    private Long birthOrder;
    /** Код ЛПУ */
    private String lpuCode;
    /** ВМП - кол-во установленных стентов */
    private Long vmpStantAmount ;
    /** Номер талона ВМП */
    private String vmpTicketNumber ;
    /** Список диагнозов по случаю */
    private String diagnosisList;
    /** Идентификатор пациента во внешней системе */
    private Long externalPatientId;
    /** Полис представителя */
    private String kinsmanSnils;
    /** Фамилия представителя */
    private String kinsmanLastname;
    /** Имя представитель */
    private String kinsmanFirstname;
    /** Отчество представителя */
    private String kinsmanMiddlename;
    /** Дата рождения представителя */
    private String kinsmanBirthDate;


    @Comment("Вид медицинской помощи")
    @Persist
    public Long getMedHelpKind() {return medHelpKind;}

    @Comment("Условия оказания мед. помощи")
    @Persist
    public Long getMedHelpUsl() {return medHelpUsl;}

    @Comment("Базовый тариф")
    @Persist
    public String getBaseTarif() {return baseTarif;}

    @Comment("Формула расчета цены")
    @Persist
    public String getCostFormulaString() {return costFormulaString;}

    @Comment("Позиция группировщика, по которой высчитан КСГ")
    @Persist
    public Long getKsgPosition() {return ksgPosition;}

    @Comment("Отделение не входит в ОМС")
    @Persist
    public Boolean getNoOmcDepartment() {return noOmcDepartment;}

    @Comment("Итоговый коэффициент")
    @Persist
    public String getTotalCoefficient() {return totalCoefficient;}

    @Comment("Родительский случай")
    @Persist
    public Long getParentEntry() {return parentEntry;}

    @Comment("ОКАТО регистрации")
    @Persist
    public String getOkatoReg() {return okatoReg;}

    @Comment("КСГ")
    @Persist
    public Long getKsg() {return ksg;}

    @Comment("Список операций")
    @Persist
    public String getOperationList() {return operationList;}

    @Comment("Список диагнозов по случаю")
    @Persist
    public String getDiagnosisList() {return diagnosisList;}

    @Comment("Идентификатор пациента во внешней системе")
    @Persist
    public Long getExternalPatientId() {return externalPatientId;}

    @Comment("СНИЛС представителя")
    @Persist
    public String getKinsmanSnils() {return kinsmanSnils;}

    @Comment("Фамилия представителя")
    @Persist
    public String getKinsmanLastname() {return kinsmanLastname;}

    @Comment("Имя представитель")
    @Persist
    public String getKinsmanFirstname() {return kinsmanFirstname;}

    @Comment("Отчество представителя")
    @Persist
    public String getKinsmanMiddlename() {return kinsmanMiddlename;}

    @Comment("Дата рождения представителя")
    @Persist @DateString @DoDateString
    public String getKinsmanBirthDate() {return kinsmanBirthDate;}

    @Comment("Пол представителя")
    @Persist
    public String getKinsmanSex() {return kinsmanSex;}

    @Comment("Тип родственной связи")
    @Persist
    public String getKinsmanRole() {return kinsmanRole;}

    @Comment("Фамилия пациента")
    @Persist
    public String getLastname() {return lastname;}

    @Comment("Имя пациента")
    @Persist
    public String getFirstname() {return firstname;}

    @Comment("Отчество пациента")
    @Persist
    public String getMiddlename() {return middlename;}

    @Comment("СНИЛС пациента")
    @Persist
    public String getPatientSnils() {return patientSnils;}

    @Comment("Гражданство пациента")
    @Persist
    public String getNationality() {return nationality;}

    @Comment("КЛАДР регистрации пациента (представителя)")
    @Persist
    public String getKladrRegistration() {
        return kladrRegistration;
    }

    @Comment("КЛАДР проживания пациента (представителя)")
    @Persist
    public String getKladrReal() {
        return kladrReal;
    }

    @Comment("Адрес проживания пациента (представителя)")
    @Persist
    public String getAddressRegistration() {return addressRegistration;}

    @Comment("Адрес проживания пациента (представителя)")
    @Persist
    public String getAddressReal() {return addressReal;}

    @Comment("Тип паспорта (ДУЛ)")
    @Persist
    public String getPassportType() {return passportType;}

    @Comment("Серия паспорта")
    @Persist
    public String getPassportSeries() {return passportSeries;}

    @Comment("Номер паспорта")
    @Persist
    public String getPassportNumber() {return passportNumber;}

    @Comment("Дата выдачи паспорта")
    @Persist @DateString @DoDateString
    public String getPassportDateIssued() {return passportDateIssued;}

    @Comment("Кем выдан паспорт")
    @Persist
    public String getPassportWhomIssued() {return passportWhomIssued;}

    @Comment("Дата рождения пациента")
    @Persist @DateString @DoDateString
    public String getBirthDate() {return birthDate;}

    @Comment("Пол пациента")
    @Persist
    public String getSex() {return sex;}

    @Comment("Социальный статус пациента")
    @Persist
    public String getSocialStatus() {return socialStatus;}

    @Comment("Каким по счету родился ребенок")
    @Persist
    public Long getBirthOrder() {return birthOrder;}

    @Comment("Код ЛПУ")
    @Persist
    public String getLpuCode() {return lpuCode;}

    @Comment("ВМП - кол-во установленных стентов")
    @Persist
    public Long getVmpStantAmount() {return vmpStantAmount;}

    @Comment("Номер талона ВМП")
    @Persist
    public String getVmpTicketNumber() {return vmpTicketNumber;}

    @Comment("Талон ВМП - дата выдачи талона")
    @Persist
    public String getVmpTicketDate() {return vmpTicketDate;}

    @Comment("Талон ВМП - дата плановой госпитализации")
    @Persist @DateString @DoDateString
    public String getVmpPlanHospDate() {return vmpPlanHospDate;}

    @Comment("Вид ВМП")
    @Persist
    public String getVmpKind() {return vmpKind;}

    @Comment("Метод ВМП")
    @Persist
    public String getVmpMethod() {return vmpMethod;}

    @Comment("Поток обслуживания")
    @Persist
    public String getServiceStream() {return serviceStream;}

    @Comment("Были сообщения в полицию")
    @Persist
    public Boolean getIsCriminalMessage() {return isCriminalMessage;}

    @Comment("Находился по уходу за родственников")
    @Persist
    public Boolean getHotelServices() {return hotelServices;}

    @Comment("Тип стационара") //Дневной, круглосуточный
    @Persist
    public String getStacType() {return stacType;}

    @Comment("ЛПУ прикрепления")
    @Persist
    public String getAttachedLpu() {return attachedLpu;}

    @Comment("Профиль помощи")
    @Persist
    public String getHelpKind() {return helpKind;}

    @Comment("Идентификатор случая во внешней системе")
    @Persist
    public Long getExternalId() {return externalId;}

    @Comment("Идентификатор пред. случая во внешней системе")
    @Persist
    public Long getExternalPrevMedcaseId() {return externalPrevMedcaseId;}

    @Comment("Идентификатор госпитализации во внешней системе")
    @Persist
    public Long getExternalParentId() {return externalParentId;}

    @Comment("Вид полиса OMC")
    @Persist
    public String getMedPolicyType() {return medPolicyType;}

    @Comment("Заполнение")
    @Persist
    public Long getListEntry() {return listEntry;}

    @Comment("Дата начала случая")
    @Persist @DateString @DoDateString
    public String getStartDate() {return startDate;}

    @Comment("Время начала случая")
    @Persist @TimeString @DoTimeString
    public String getStartTime() {return startTime;}

    @Comment("Дата окончания случая")
    @Persist @DateString @DoDateString
    public String getFinishDate() {return finishDate;}

    @Comment("Время окончания случая")
    @Persist @TimeString @DoTimeString
    public String getFinishTime() {return finishTime;}

    @Comment("Количество койкодней")
    @Persist
    public Long getBedDays() {return bedDays;}

    @Comment("Номер истории болезни")
    @Persist
    public String getHistoryNumber() {return historyNumber;}

    @Comment("Название отделения")
    @Persist
    public String getDepartmentName() {return departmentName;}

    @Comment("Тип отделения")
    @Persist
    public String getDepartmentType() {return departmentType;}

    @Comment("Код отделения")
    @Persist
    public String getDepartmentCode() {return departmentCode;}

    @Comment("Код отделения длинный")
    @Persist
    public String getDepartmentAddressCode() {return departmentAddressCode;}

    @Comment("ФИО врача")
    @Persist
    public String getDoctorName() {return doctorName;}

    @Comment("Должность врача")
    @Persist
    public String getDoctorWorkfunction() {return doctorWorkfunction;}

    @Comment("СНИЛС врача")
    @Persist
    public String getDoctorSnils() {return doctorSnils;}

    @Comment("Экстренность")
    @Persist
    public Boolean getIsEmergency() {return isEmergency;}

    @Comment("Направившее ЛПУ")
    @Persist
    public String getDirectLpu() {return directLpu;}

    @Comment("Вес новорожденного")
    @Persist
    public Long getNewbornWeight() {return newbornWeight;}

    @Comment("Тип направившего ЛПУ")
    @Persist
    public String getDirectLpuType() {return directLpuType;}

    @Comment("Номер направление ФОМС")
    @Persist
    public String getTicket263Number() {return ticket263Number;}

    @Comment("Результат госпитализации")
    @Persist
    public String getResult() {return result;}

    @Comment("Тип коек") //Круглосуточные, дневные
    @Persist
    public String getBedSubType() {return bedSubType;}

    @Comment("Дата начала госпитализации")
    @Persist @DateString @DoDateString
    public String getHospitalStartDate() {return hospitalStartDate;}

    @Comment("Дата окончания госпитализации")
    @Persist @DateString @DoDateString
    public String getHospitalFinishDate() {return hospitalFinishDate;}

    @Comment("Услуги")
    @Persist
    public String getServices() {return services;}

    @Comment("Цена случая")
    @Persist
    public String getCost() {return cost;}

    @Comment("Ручное редактирование цены")
    @Persist
    public Boolean getIsManualKsg() {return isManualKsg;}

    @Comment("ИД отделения СЛО")
    @Persist
    public Long getDepartmentId() {return departmentId;}

    @Comment("Серия полиса")
    @Persist
    public String getMedPolicySeries() {return medPolicySeries;}

    @Comment("Номер полиса")
    @Persist
    public String getMedPolicyNumber() {return medPolicyNumber;}

    @Comment("Страх. компания (федеральный код)")
    @Persist
    public String getInsuranceCompanyCode() {return insuranceCompanyCode;}

    @Persist
    @Comment("Результат оказания медицинской помощи")
    public Long getFondResult() {return fondResult;}

    @Persist
    @Comment("Исход случая")
    public Long getFondIshod() {return fondIshod;}

    @Persist
    @Comment("Многоплодная беременность")
    public Boolean getMultiplyBirth() {return multiplyBirth;}

    @Persist
    @Comment("Тип записи")
    public String getEntryType() {return entryType;}

    @Persist
    @Comment("Тип файла") //P, Z, DF, раки
    public String getFileType() {return fileType;}

    /** Реабилитационная койка */
    @Comment("Реабилитационная койка")
    @Persist
    public Boolean getIsRehabBed() {return isRehabBed;}

    @Comment("Стоматологический ли случай")
    @Persist
    public Boolean getIsDentalCase () {return isDentalCase;}

    @Persist
    @Comment("Способ оплаты медицинской помощи")
    public Long getIdsp() {return idsp;}

    @Persist
    @Comment("Причины неполной оплаты")
    public String getNotFullPaymentReason() {return notFullPaymentReason;}

    @Comment("Номер счета")
    @Persist
    public String getBillNumber() {return billNumber;}

    @Comment("Дата счета")
    @Persist @DateString @DoDateString
    public String getBillDate() {return billDate;}

    @Comment("Не подавать на оплату")
    @Persist
    public Boolean getDoNotSend() {return doNotSend;}

    @Comment("Основная услуга случая")
    @Persist
    public String getMainService() {return mainService;}

    @Comment("Основной МКБ случая")
    @Persist
    public String getMainMkb() {return mainMkb;}

    @Comment("Профиль оказания мед. помощи")
    @Persist
    public Long getMedHelpProfile() {return medHelpProfile;}

    @Comment("Случай реанимации")
    @Persist
    public Long getReanimationEntry() {return reanimationEntry;}

    @Comment("Количество рожденных детей")
    @Persist
    public Long getNewbornAmount() {return newbornAmount;}

    @Comment("Признак исправленной записи")
    @Persist
    public Boolean getPrnov() {return prnov;}

    @Comment("Единый номер пациента (представителя)")
    @Persist
    public String getCommonNumber() {return commonNumber;}

    @Comment("Название страх. компании")
    @Persist
    public String getInsuranceCompanyName() {return insuranceCompanyName;}

    @Comment("ОГРН страховой компании")
    @Persist
    public String getInsuranceCompanyOgrn() {return insuranceCompanyOgrn;}

    @Comment("Регион нахождения страховой компании")
    @Persist
    public String getInsuranceCompanyTerritory() {return insuranceCompanyTerritory;}

    @Comment("Удаленная запись")
    @Persist
    public Boolean getIsDeleted() {return isDeleted;}

    @Comment("Случай объединен")
    @Persist
    public Boolean getIsUnion() {return isUnion;}

    @Comment("Расчет случая ФОМС")
    @Persist
    public String getFondComment() {return fondComment;}

    @Comment("Признак дефекта")
    @Persist
    public Boolean getIsDefect() {return isDefect;}

    @Comment("Место приема пациента")
    @Persist
    public String getWorkPlace() {return workPlace;}

    @Comment("Доп. критерий КСГ")
    @Persist
    public String getDopKritKSG() {return dopKritKSG;}

    @Comment("Был произведен аборт по медицинским показаниям")
    @Persist
    public Boolean getMedicalAbort() {return medicalAbort;}

    @Comment("Признак мобильной поликлиники")
    @Persist
    public Boolean getIsMobilePolyclinic() {return isMobilePolyclinic;}

    @Comment("Подтип записи")
    @Persist
    public Long getSubType() {return subType;}

    /** Количество календарных дней */
    @Comment("Количество календарных дней")
    @Persist
    public Long getCalendarDays() {return calendarDays;}

    @Comment("Консультативно-диагностическое обращение")
    @Persist
    public Boolean getIsDiagnosticSpo() {return isDiagnosticSpo;}
    @Comment("Счет")
    @Persist
    public Long getBill() {return bill;}

    @Comment("Признак детского возраст")
    @Persist
    public Boolean getIsChild() {return isChild;}

    @Comment("Тип доп. диспансеризации")
    @Persist
    public String getExtDispType() {return extDispType;}

    @Comment("Возраст доп. диспансеризации")
    @Persist
    public String getExtDispAge() {return extDispAge;}

    @Comment("Группа здоровья доп. диспансеризации")
    @Persist
    public String getExtDispHealthGroup() {return extDispHealthGroup;}

    @Comment("Социальная группа доп. диспансеризации")
    @Persist
    public String getExtDispSocialGroup() {return extDispSocialGroup;}

    @Comment("Назначения доп. диспансеризации")
    @Persist
    public String getExtDispAppointments() {return extDispAppointments;}

    @Comment("Направлен на след. этап ДД")
    @Persist
    public Boolean getExtDispNextStage() {return extDispNextStage;}

    @Comment("Иногородний ")
    @Persist
    public Boolean getIsForeign() {return isForeign;}

    @Comment("Дата направление на лечение")
    @Persist @DateString @DoDateString
    public String getDirectDate() {return directDate;}

    @Comment("Вид случая (справочник VID_SLUCH)")
    @Persist
    public Long getVidSluch() {return vidSluch;}

    @Comment("Дата планируемой госпитализации")
    @Persist
    @DateString @DoDateString
    public String getPlanHospDate() {return planHospDate;}

    @Comment("Цель посещения")
    @Persist
    public Long getVisitPurpose() {return visitPurpose;}

    @Comment("Онкологический случай")
    @Persist
    public Boolean getIsCancer() {return isCancer;}


    @Comment("Специальность врача по фонду V021")
    @Persist
    public Long getFondDoctorSpecV021() {return fondDoctorSpecV021;}

    @Comment("Родовое отделение")
    @Persist
    public Boolean getIsChildBirthDepartment() {return isChildBirthDepartment !=null && isChildBirthDepartment ;}

    @Comment("Комментарии эксперта")
    @Persist
    public String getComment() {return comment;}

    @Comment("Профиль койки")
    @Persist
    public Long getBedProfile() {return bedProfile;}

    @Comment("Рост пациента")
    @Persist
    public Integer getHeight() {return height;}

    @Comment("Вес пациента")
    @Persist
    public Integer getWeigth() {return weigth;}

    /** Список выполненных назначений*/
    @Comment(" выполненных назначений")
    @Persist
    public String getPrescriptionList() {return prescriptionList;}
    /** Список  выполненных назначений */
    private String prescriptionList ;

    //Ниже идут Нехранимые поля!!!

    /** Добавить услугу к случаю */
    @Comment("Добавить услугу к случаю")
    public Long getNewMedService() {return newMedService;}
    private Long newMedService ;

    /** Особенность случая */
    @Comment("Особенность случая")
    public Long getNewFactor() {return newFactor;}
    private Long newFactor ;

    /** Место рождения */
    @Comment("Место рождения")
    @Persist
    public String getBirthPlace() {return birthPlace;}
    private String birthPlace ;

    /** Результат диспансеризации */
    @Comment("Результат диспансеризации")
    @Persist
    public Long getDispResult() {return dispResult;}
    /** результат диспансеризации */
    private Long dispResult ;

}
