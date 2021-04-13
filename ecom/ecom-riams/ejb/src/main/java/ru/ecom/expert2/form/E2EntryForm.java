package ru.ecom.expert2.form;

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
public class E2EntryForm extends IdEntityForm {

//----------все поля ниже добавлены на jsp!!!
    /** Реабилитационная койка*/
    private Boolean isRehabBed;
    /** Рост пациента */
    private Integer height;
    /** Вес пациента */
    private Integer weigth;
    /** Профиль койки */
    private Long bedProfile;
    /** Комментарии эксперта */
    private String comment;
    /** КДП */
    private Long kdpVisit;
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
    /** Имя пациента */
    private String firstname;
    /** Отчество пациента */
    private String middlename;
    /** СНИЛС пациента */
    private String patientSnils;
    /** Гражданство пациента */
    private String nationality;
    /** КЛАДР регистрации пациента (представителя) */
    private String kladrRegistration;
    /** КЛАДР проживания пациента (представителя) */
    private String kladrReal;
    /** Адрес проживания пациента (представителя) */
    private String dddressRegistration;
    /** Адрес проживания пациента (представителя) */
    private String addressReal;

    /** Тип паспорта (ДУЛ) */
    private String passportType;
    /** Серия паспорта */
    private String passportSeries;
    /** Номер паспорта */
    private String passportNumber;
    /** Дата выдачи паспорта */
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
    public void setMedHelpKind(Long aMedHelpKind) {
        medHelpKind = aMedHelpKind;}

    @Comment("Условия оказания мед. помощи")
    @Persist
    public Long getMedHelpUsl() {return medHelpUsl;}
    public void setMedHelpUsl(Long aMedHelpUsl) {
        medHelpUsl = aMedHelpUsl;}

    @Comment("Базовый тариф")
    @Persist
    public String getBaseTarif() {return baseTarif;}
    public void setBaseTarif(String aBaseTarif) {
        baseTarif = aBaseTarif;}

    @Comment("Формула расчета цены")
    @Persist
    public String getCostFormulaString() {return costFormulaString;}
    public void setCostFormulaString(String aCostFormulaString) {
        costFormulaString = aCostFormulaString;}

    @Comment("Позиция группировщика, по которой высчитан КСГ")
    @Persist
    public Long getKsgPosition() {return ksgPosition;}
    public void setKsgPosition(Long aKsgPosition) {
        ksgPosition = aKsgPosition;}

    @Comment("Отделение не входит в ОМС")
    @Persist
    public Boolean getNoOmcDepartment() {return noOmcDepartment;}
    public void setNoOmcDepartment(Boolean aNoOmcDepartment) {
        noOmcDepartment = aNoOmcDepartment;}

    @Comment("Итоговый коэффициент")
    @Persist
    public String getTotalCoefficient() {return totalCoefficient;}
    public void setTotalCoefficient(String aTotalCoefficient) {
        totalCoefficient = aTotalCoefficient;}

    @Comment("Родительский случай")
    @Persist
    public Long getParentEntry() {return parentEntry;}
    public void setParentEntry(Long aParentEntry) {
        parentEntry = aParentEntry;}

    @Comment("ОКАТО регистрации")
    @Persist
    public String getOkatoReg() {return okatoReg;}
    public void setOkatoReg(String aOkatoReg) {
        okatoReg = aOkatoReg;}

    @Comment("КСГ")
    @Persist
    public Long getKsg() {return ksg;}
    public void setKsg(Long  aKsg) {
        ksg = aKsg;}

    @Comment("Список операций")
    @Persist
    public String getOperationList() {return operationList;}
    public void setOperationList(String aOperationList) {
        operationList = aOperationList;}

    @Comment("Список диагнозов по случаю")
    @Persist
    public String getDiagnosisList() {return diagnosisList;}
    public void setDiagnosisList(String aDiagnosisList) {
        diagnosisList = aDiagnosisList;}

    @Comment("Идентификатор пациента во внешней системе")
    @Persist
    public Long getExternalPatientId() {return externalPatientId;}
    public void setExternalPatientId(Long aExternalPatientId) {
        externalPatientId = aExternalPatientId;}

    @Comment("СНИЛС представителя")
    @Persist
    public String getKinsmanSnils() {return kinsmanSnils;}
    public void setKinsmanSnils(String aKinsmanSnils) {
        kinsmanSnils = aKinsmanSnils;}

    @Comment("Фамилия представителя")
    @Persist
    public String getKinsmanLastname() {return kinsmanLastname;}
    public void setKinsmanLastname(String aKinsmanLastname) {
        kinsmanLastname = aKinsmanLastname;}

    @Comment("Имя представитель")
    @Persist
    public String getKinsmanFirstname() {return kinsmanFirstname;}
    public void setKinsmanFirstname(String aKinsmanFirstname) {
        kinsmanFirstname = aKinsmanFirstname;}

    @Comment("Отчество представителя")
    @Persist
    public String getKinsmanMiddlename() {return kinsmanMiddlename;}
    public void setKinsmanMiddlename(String aKinsmanMiddlename) {
        kinsmanMiddlename = aKinsmanMiddlename;}

    @Comment("Дата рождения представителя")
    @Persist @DateString @DoDateString
    public String getKinsmanBirthDate() {return kinsmanBirthDate;}
    public void setKinsmanBirthDate(String aKinsmanBirthDate) {
        kinsmanBirthDate = aKinsmanBirthDate;}

    @Comment("Пол представителя")
    @Persist
    public String getKinsmanSex() {return kinsmanSex;}
    public void setKinsmanSex(String aKinsmanSex) {
        kinsmanSex = aKinsmanSex;}

    @Comment("Тип родственной связи")
    @Persist
    public String getKinsmanRole() {return kinsmanRole;}
    public void setKinsmanRole(String aKinsmanRole) {
        kinsmanRole = aKinsmanRole;}

    @Comment("Фамилия пациента")
    @Persist
    public String getLastname() {return lastname;}
    public void setLastname(String aLastname) {
        lastname = aLastname;}

    @Comment("Имя пациента")
    @Persist
    public String getFirstname() {return firstname;}
    public void setFirstname(String aFirstname) {
        firstname = aFirstname;}

    @Comment("Отчество пациента")
    @Persist
    public String getMiddlename() {return middlename;}
    public void setMiddlename(String aMiddlename) {
        middlename = aMiddlename;}

    @Comment("СНИЛС пациента")
    @Persist
    public String getPatientSnils() {return patientSnils;}
    public void setPatientSnils(String aPatientSnils) {
        patientSnils = aPatientSnils;}

    @Comment("Гражданство пациента")
    @Persist
    public String getNationality() {return nationality;}
    public void setNationality(String aNationality) {
        nationality = aNationality;}

    @Comment("КЛАДР регистрации пациента (представителя)")
    @Persist
    public String getKladrRegistration() {return kladrRegistration;}
    public void setKladrRegistration(String aKLADRRegistration) {
        kladrRegistration = aKLADRRegistration;}

    @Comment("КЛАДР проживания пациента (представителя)")
    @Persist
    public String getKladrReal() {return kladrReal;}
    public void setKladrReal(String aKLADRReal) {
        kladrReal = aKLADRReal;}

    @Comment("Адрес проживания пациента (представителя)")
    @Persist
    public String getAddressRegistration() {return dddressRegistration;}
    public void setAddressRegistration(String aAddressRegistration) {
        dddressRegistration = aAddressRegistration;}

    @Comment("Адрес проживания пациента (представителя)")
    @Persist
    public String getAddressReal() {return addressReal;}
    public void setAddressReal(String aAddressReal) {
        addressReal = aAddressReal;}

    @Comment("Тип паспорта (ДУЛ)")
    @Persist
    public String getPassportType() {return passportType;}
    public void setPassportType(String aPassportType) {
        passportType = aPassportType;}

    @Comment("Серия паспорта")
    @Persist
    public String getPassportSeries() {return passportSeries;}
    public void setPassportSeries(String aPassportSeries) {
        passportSeries = aPassportSeries;}

    @Comment("Номер паспорта")
    @Persist
    public String getPassportNumber() {return passportNumber;}
    public void setPassportNumber(String aPassportNumber) {
        passportNumber = aPassportNumber;}

    @Comment("Дата выдачи паспорта")
    @Persist @DateString @DoDateString
    public String getPassportDateIssued() {return passportDateIssued;}
    public void setPassportDateIssued(String aPassportDateIssued) {
        passportDateIssued = aPassportDateIssued;}

    @Comment("Кем выдан паспорт")
    @Persist
    public String getPassportWhomIssued() {return passportWhomIssued;}
    public void setPassportWhomIssued(String aPassportWhomIssued) {
        passportWhomIssued = aPassportWhomIssued;}

    @Comment("Дата рождения пациента")
    @Persist @DateString @DoDateString
    public String getBirthDate() {return birthDate;}
    public void setBirthDate(String aBirthDate) {
        birthDate = aBirthDate;}

    @Comment("Пол пациента")
    @Persist
    public String getSex() {return sex;}
    public void setSex(String aSex) {
        sex = aSex;}

    @Comment("Социальный статус пациента")
    @Persist
    public String getSocialStatus() {return socialStatus;}
    public void setSocialStatus(String aSocialStatus) {
        socialStatus = aSocialStatus;}

    @Comment("Каким по счету родился ребенок")
    @Persist
    public Long getBirthOrder() {return birthOrder;}
    public void setBirthOrder(Long aBirthOrder) {
        birthOrder = aBirthOrder;}

    @Comment("Код ЛПУ")
    @Persist
    public String getLpuCode() {return lpuCode;}
    public void setLpuCode(String aLpuCode) {
        lpuCode = aLpuCode;}

    @Comment("ВМП - кол-во установленных стентов")
    @Persist
    public Long getVmpStantAmount() {return vmpStantAmount;}
    public void setVmpStantAmount(Long aVMPStantAmount) {vmpStantAmount = aVMPStantAmount;}

    @Comment("Номер талона ВМП")
    @Persist
    public String getVmpTicketNumber() {return vmpTicketNumber;}
    public void setVmpTicketNumber(String aVMPTicketNumber) {vmpTicketNumber = aVMPTicketNumber;}

    @Comment("Талон ВМП - дата выдачи талона")
    @Persist
    public String getVmpTicketDate() {return vmpTicketDate;}
    public void setVmpTicketDate(String aVMPTicketDate) {
        vmpTicketDate = aVMPTicketDate;}

    @Comment("Талон ВМП - дата плановой госпитализации")
    @Persist @DateString @DoDateString
    public String getVmpPlanHospDate() {return vmpPlanHospDate;}
    public void setVmpPlanHospDate(String aVMPPlanHospDate) {
        vmpPlanHospDate = aVMPPlanHospDate;}

    @Comment("Вид ВМП")
    @Persist
    public String getVmpKind() {return vmpKind;}
    public void setVmpKind(String aVMPKind) {
        vmpKind = aVMPKind;}

    @Comment("Метод ВМП")
    @Persist
    public String getVmpMethod() {return vmpMethod;}
    public void setVmpMethod(String aVMPMethod) {
        vmpMethod = aVMPMethod;}

    @Comment("Поток обслуживания")
    @Persist
    public String getServiceStream() {return serviceStream;}
    public void setServiceStream(String aServiceStream) {
        serviceStream = aServiceStream;}

    @Comment("Были сообщения в полицию")
    @Persist
    public Boolean getIsCriminalMessage() {return isCriminalMessage;}
    public void setIsCriminalMessage(Boolean aIsCriminalMessage) {
        isCriminalMessage = aIsCriminalMessage;}

    @Comment("Находился по уходу за родственников")
    @Persist
    public Boolean getHotelServices() {return hotelServices;}
    public void setHotelServices(Boolean aHotelServices) {
        hotelServices = aHotelServices;}

    @Comment("Тип стационара") //Дневной, круглосуточный
    @Persist
    public String getStacType() {return stacType;}
    public void setStacType(String aStacType) {
        stacType = aStacType;}

    @Comment("ЛПУ прикрепления")
    @Persist
    public String getAttachedLpu() {return attachedLpu;}
    public void setAttachedLpu(String aAttachedLpu) {
        attachedLpu = aAttachedLpu;}

    @Comment("Профиль помощи")
    @Persist
    public String getHelpKind() {return helpKind;}
    public void setHelpKind(String aHelpKind) {
        helpKind = aHelpKind;}

    @Comment("Идентификатор случая во внешней системе")
    @Persist
    public Long getExternalId() {return externalId;}
    public void setExternalId(Long aExternalId) {
        externalId = aExternalId;}

    @Comment("Идентификатор пред. случая во внешней системе")
    @Persist
    public Long getExternalPrevMedcaseId() {return externalPrevMedcaseId;}
    public void setExternalPrevMedcaseId(Long aExternalPrevMedcaseId) {
        externalPrevMedcaseId = aExternalPrevMedcaseId;}

    @Comment("Идентификатор госпитализации во внешней системе")
    @Persist
    public Long getExternalParentId() {return externalParentId;}
    public void setExternalParentId(Long aExternalParentId) {
        externalParentId = aExternalParentId;}

    @Comment("Вид полиса OMC")
    @Persist
    public String getMedPolicyType() {return medPolicyType;}
    public void setMedPolicyType(String aMedPolicyType) {
        medPolicyType = aMedPolicyType;}

    @Comment("Заполнение")
    @Persist
    public Long getListEntry() {return listEntry;}
    public void setListEntry(Long aListEntry) {
        listEntry = aListEntry;}

    @Comment("Дата начала случая")
    @Persist @DateString @DoDateString
    public String getStartDate() {return startDate;}
    public void setStartDate(String aStartDate) {
        startDate = aStartDate;}

    @Comment("Время начала случая")
    @Persist @TimeString @DoTimeString
    public String getStartTime() {return startTime;}
    public void setStartTime(String aStartTime) {
        startTime = aStartTime;}

    @Comment("Дата окончания случая")
    @Persist @DateString @DoDateString
    public String getFinishDate() {return finishDate;}
    public void setFinishDate(String aFinishDate) {
        finishDate = aFinishDate;}

    @Comment("Время окончания случая")
    @Persist @TimeString @DoTimeString
    public String getFinishTime() {return finishTime;}
    public void setFinishTime(String aFinishTime) {
        finishTime = aFinishTime;}

    @Comment("Количество койкодней")
    @Persist
    public Long getBedDays() {return bedDays;}
    public void setBedDays(Long aBedDays) {
        bedDays = aBedDays;}

    @Comment("Номер истории болезни")
    @Persist
    public String getHistoryNumber() {return historyNumber;}
    public void setHistoryNumber(String aHistoryNumber) {
        historyNumber = aHistoryNumber;}

    @Comment("Название отделения")
    @Persist
    public String getDepartmentName() {return departmentName;}
    public void setDepartmentName(String aDepartmentName) {
        departmentName = aDepartmentName;}

    @Comment("Тип отделения")
    @Persist
    public String getDepartmentType() {return departmentType;}
    public void setDepartmentType(String aDepartmentType) {
        departmentType = aDepartmentType;}

    @Comment("Код отделения")
    @Persist
    public String getDepartmentCode() {return departmentCode;}
    public void setDepartmentCode(String aDepartmentCode) {
        departmentCode = aDepartmentCode;}

    @Comment("Код отделения длинный")
    @Persist
    public String getDepartmentAddressCode() {return departmentAddressCode;}
    public void setDepartmentAddressCode(String aDepartmentAddressCode) {
        departmentAddressCode = aDepartmentAddressCode;}

    @Comment("ФИО врача")
    @Persist
    public String getDoctorName() {return doctorName;}
    public void setDoctorName(String aDoctorName) {
        doctorName = aDoctorName;}

    @Comment("Должность врача")
    @Persist
    public String getDoctorWorkfunction() {return doctorWorkfunction;}
    public void setDoctorWorkfunction(String aDoctorWorkfunction) {
        doctorWorkfunction = aDoctorWorkfunction;}

    @Comment("СНИЛС врача")
    @Persist
    public String getDoctorSnils() {return doctorSnils;}
    public void setDoctorSnils(String aDoctorSnils) {
        doctorSnils = aDoctorSnils;}

    @Comment("Экстренность")
    @Persist
    public Boolean getIsEmergency() {return isEmergency;}
    public void setIsEmergency(Boolean aIsEmergency) {
        isEmergency = aIsEmergency;}

    @Comment("Направившее ЛПУ")
    @Persist
    public String getDirectLpu() {return directLpu;}
    public void setDirectLpu(String aDirectLpu) {
        directLpu = aDirectLpu;}

    @Comment("Вес новорожденного")
    @Persist
    public Long getNewbornWeight() {return newbornWeight;}
    public void setNewbornWeight(Long aNewbornWeight) {
        newbornWeight = aNewbornWeight;}

    @Comment("Тип направившего ЛПУ")
    @Persist
    public String getDirectLpuType() {return directLpuType;}
    public void setDirectLpuType(String aDirectLpuType) {
        directLpuType = aDirectLpuType;}

    @Comment("Номер направление ФОМС")
    @Persist
    public String getTicket263Number() {return ticket263Number;}
    public void setTicket263Number(String aTicket263Number) {
        ticket263Number = aTicket263Number;}

    @Comment("Результат госпитализации")
    @Persist
    public String getResult() {return result;}
    public void setResult(String aResult) {
        result = aResult;}

    @Comment("Тип коек") //Круглосуточные, дневные
    @Persist
    public String getBedSubType() {return bedSubType;}
    public void setBedSubType(String aBedSubType) {
        bedSubType = aBedSubType;}

    @Comment("Дата начала госпитализации")
    @Persist @DateString @DoDateString
    public String getHospitalStartDate() {return hospitalStartDate;}
    public void setHospitalStartDate(String aHospitalStartDate) {
        hospitalStartDate = aHospitalStartDate;}

    @Comment("Дата окончания госпитализации")
    @Persist @DateString @DoDateString
    public String getHospitalFinishDate() {return hospitalFinishDate;}
    public void setHospitalFinishDate(String aHospitalFinishDate) {
        hospitalFinishDate = aHospitalFinishDate;}

    @Comment("Услуги")
    @Persist
    public String getServices() {return services;}
    public void setServices(String aServices) {
        services = aServices;}

    @Comment("Цена случая")
    @Persist
    public String getCost() {return cost;}
    public void setCost(String aCost) {
        cost = aCost;}

    @Comment("Ручное редактирование цены")
    @Persist
    public Boolean getIsManualKsg() {return isManualKsg;}
    public void setIsManualKsg(Boolean aIsManualKsg) {
        isManualKsg = aIsManualKsg;}

    @Comment("ИД отделения СЛО")
    @Persist
    public Long getDepartmentId() {return departmentId;}
    public void setDepartmentId(Long aDepartmentId) {
        departmentId = aDepartmentId;}

    @Comment("Серия полиса")
    @Persist
    public String getMedPolicySeries() {return medPolicySeries;}
    public void setMedPolicySeries(String aMedPolicySeries) {
        medPolicySeries = aMedPolicySeries;}

    @Comment("Номер полиса")
    @Persist
    public String getMedPolicyNumber() {return medPolicyNumber;}
    public void setMedPolicyNumber(String aMedPolicyNumber) {
        medPolicyNumber = aMedPolicyNumber;}

    @Comment("Страх. компания (федеральный код)")
    @Persist
    public String getInsuranceCompanyCode() {return insuranceCompanyCode;}
    public void setInsuranceCompanyCode(String aInsuranceCompanyCode) {
        insuranceCompanyCode = aInsuranceCompanyCode;}

    @Persist
    @Comment("Результат оказания медицинской помощи")
    public Long getFondResult() {return fondResult;}
    public void setFondResult(Long theFondResult) {this.fondResult = theFondResult;}

    @Persist
    @Comment("Исход случая")
    public Long getFondIshod() {return fondIshod;}
    public void setFondIshod(Long theFondIshod) {this.fondIshod = theFondIshod;}

    @Persist
    @Comment("Многоплодная беременность")
    public Boolean getMultiplyBirth() {return multiplyBirth;}
    public void setMultiplyBirth(Boolean theMultiplyBirth) {this.multiplyBirth = theMultiplyBirth;}

    @Persist
    @Comment("Тип записи")
    public String getEntryType() {return entryType;}
    public void setEntryType(String theEntryType) {this.entryType = theEntryType;}

    @Persist
    @Comment("Тип файла") //P, Z, DF, раки
    public String getFileType() {return fileType;}
    public void setFileType(String aFileType) {
        fileType = aFileType;}

    /** Реабилитационная койка */
    @Comment("Реабилитационная койка")
    @Persist
    public Boolean getIsRehabBed() {return isRehabBed;}
    public void setIsRehabBed(Boolean aIsRehabBed) {
        isRehabBed = aIsRehabBed;}

    @Persist
    @Comment("Способ оплаты медицинской помощи")
    public Long getIdsp() {return idsp;}
    public void setIdsp(Long theIDSP) {this.idsp = theIDSP;}

    @Persist
    @Comment("Причины неполной оплаты")
    public String getNotFullPaymentReason() {return notFullPaymentReason;}
    public void setNotFullPaymentReason(String theNotFullPaymentReason) {this.notFullPaymentReason = theNotFullPaymentReason;}

    @Comment("Номер счета")
    @Persist
    public String getBillNumber() {return billNumber;}
    public void setBillNumber(String aBillNumber) {
        billNumber = aBillNumber;}

    @Comment("Дата счета")
    @Persist @DateString @DoDateString
    public String getBillDate() {return billDate;}
    public void setBillDate(String aBillDate) {
        billDate = aBillDate;}

    @Comment("Не подавать на оплату")
    @Persist
    public Boolean getDoNotSend() {return doNotSend;}
    public void setDoNotSend(Boolean aDoNotSend) {
        doNotSend = aDoNotSend;}

    @Comment("Основная услуга случая")
    @Persist
    public String getMainService() {return mainService;}
    public void setMainService(String aMainService) {
        mainService = aMainService;}

    @Comment("Основной МКБ случая")
    @Persist
    public String getMainMkb() {return mainMkb;}
    public void setMainMkb(String aMainMkb) {
        mainMkb = aMainMkb;}

    @Comment("Профиль оказания мед. помощи")
    @Persist
    public Long getMedHelpProfile() {return medHelpProfile;}
    public void setMedHelpProfile(Long aMedHelpProfile) {
        medHelpProfile = aMedHelpProfile;}

    @Comment("Случай реанимации")
    @Persist
    public Long getReanimationEntry() {return reanimationEntry;}
    public void setReanimationEntry(Long aReanimationEntry) {
        reanimationEntry = aReanimationEntry;}

    @Comment("Количество рожденных детей")
    @Persist
    public Long getNewbornAmount() {return newbornAmount;}
    public void setNewbornAmount(Long aNewbornAmount) {
        newbornAmount = aNewbornAmount;}

    @Comment("Признак исправленной записи")
    @Persist
    public Boolean getPrnov() {return prnov;}
    public void setPrnov(Boolean aPrnov) {
        prnov = aPrnov;}

    @Comment("Единый номер пациента (представителя)")
    @Persist
    public String getCommonNumber() {return commonNumber;}
    public void setCommonNumber(String aCommonNumber) {
        commonNumber = aCommonNumber;}

    @Comment("Название страх. компании")
    @Persist
    public String getInsuranceCompanyName() {return insuranceCompanyName;}
    public void setInsuranceCompanyName(String aInsuranceCompanyName) {
        insuranceCompanyName = aInsuranceCompanyName;}

    @Comment("ОГРН страховой компании")
    @Persist
    public String getInsuranceCompanyOgrn() {return insuranceCompanyOgrn;}
    public void setInsuranceCompanyOgrn(String aInsuranceCompanyOgrn) {
        insuranceCompanyOgrn = aInsuranceCompanyOgrn;}

    @Comment("Регион нахождения страховой компании")
    @Persist
    public String getInsuranceCompanyTerritory() {return insuranceCompanyTerritory;}
    public void setInsuranceCompanyTerritory(String aInsuranceCompanyTerritory) {
        insuranceCompanyTerritory = aInsuranceCompanyTerritory;}

    @Comment("Удаленная запись")
    @Persist
    public Boolean getIsDeleted() {return isDeleted;}
    public void setIsDeleted(Boolean aIsDeleted) {
        isDeleted = aIsDeleted;}

    @Comment("Случай объединен")
    @Persist
    public Boolean getIsUnion() {return isUnion;}
    public void setIsUnion(Boolean aIsUnion) {
        isUnion = aIsUnion;}

    @Comment("Расчет случая ФОМС")
    @Persist
    public String getFondComment() {return fondComment;}
    public void setFondComment(String aFondComment) {
        fondComment = aFondComment;}

    @Comment("Признак дефекта")
    @Persist
    public Boolean getIsDefect() {return isDefect;}
    public void setIsDefect(Boolean aIsDefect) {
        isDefect = aIsDefect;}

    @Comment("Место приема пациента")
    @Persist
    public String getWorkPlace() {return workPlace;}
    public void setWorkPlace(String aWorkPlace) {
        workPlace = aWorkPlace;}

    @Comment("Доп. критерий КСГ")
    @Persist
    public String getDopKritKSG() {return dopKritKSG;}
    public void setDopKritKSG(String aDopKritKSG) {
        dopKritKSG = aDopKritKSG;}

    @Comment("Был произведен аборт по медицинским показаниям")
    @Persist
    public Boolean getMedicalAbort() {return medicalAbort;}
    public void setMedicalAbort(Boolean aMedicalAbort) {
        medicalAbort = aMedicalAbort;}

    @Comment("Признак мобильной поликлиники")
    @Persist
    public Boolean getIsMobilePolyclinic() {return isMobilePolyclinic;}
    public void setIsMobilePolyclinic(Boolean aIsMobilePolyclinic) {
        isMobilePolyclinic = aIsMobilePolyclinic;}

    @Comment("Подтип записи")
    @Persist
    public Long getSubType() {return subType;}
    public void setSubType(Long aSubType) {
        subType = aSubType;}

    /** Количество календарных дней */
    @Comment("Количество календарных дней")
    @Persist
    public Long getCalendarDays() {return calendarDays;}
    public void setCalendarDays(Long aCalendarDays) {
        calendarDays = aCalendarDays;}

    @Comment("Консультативно-диагностическое обращение")
    @Persist
    public Boolean getIsDiagnosticSpo() {return isDiagnosticSpo;}
    public void setIsDiagnosticSpo(Boolean aIsDiagnosticSpo) {
        isDiagnosticSpo = aIsDiagnosticSpo;}
    @Comment("Счет")
    @Persist
    public Long getBill() {return bill;}
    public void setBill(Long aBill) {
        bill = aBill;}

    @Comment("Признак детского возраст")
    @Persist
    public Boolean getIsChild() {return isChild;}
    public void setIsChild(Boolean aIsChild) {
        isChild = aIsChild;}

    @Comment("Тип доп. диспансеризации")
    @Persist
    public String getExtDispType() {return extDispType;}
    public void setExtDispType(String aExtDispType) {
        extDispType = aExtDispType;}

    @Comment("Возраст доп. диспансеризации")
    @Persist
    public String getExtDispAge() {return extDispAge;}
    public void setExtDispAge(String aExtDispAge) {
        extDispAge = aExtDispAge;}

    @Comment("Группа здоровья доп. диспансеризации")
    @Persist
    public String getExtDispHealthGroup() {return extDispHealthGroup;}
    public void setExtDispHealthGroup(String aExtDispHealthGroup) {
        extDispHealthGroup = aExtDispHealthGroup;}

    @Comment("Социальная группа доп. диспансеризации")
    @Persist
    public String getExtDispSocialGroup() {return extDispSocialGroup;}
    public void setExtDispSocialGroup(String aExtDispSocialGroup) {
        extDispSocialGroup = aExtDispSocialGroup;}

    @Comment("Назначения доп. диспансеризации")
    @Persist
    public String getExtDispAppointments() {return extDispAppointments;}
    public void setExtDispAppointments(String aExtDispAppointments) {
        extDispAppointments = aExtDispAppointments;}

    @Comment("Направлен на след. этап ДД")
    @Persist
    public Boolean getExtDispNextStage() {return extDispNextStage;}
    public void setExtDispNextStage(Boolean aExtDispNextStage) {
        extDispNextStage = aExtDispNextStage;}

    @Comment("Иногородний ")
    @Persist
    public Boolean getIsForeign() {return isForeign;}
    public void setIsForeign(Boolean aIsForeign) {
        isForeign = aIsForeign;}

    @Comment("Дата направление на лечение")
    @Persist @DateString @DoDateString
    public String getDirectDate() {return directDate;}
    public void setDirectDate(String aDirectDate) {
        directDate = aDirectDate;}

    @Comment("Вид случая (справочник VID_SLUCH)")
    @Persist
    public Long getVidSluch() {return vidSluch;}
    public void setVidSluch(Long aVidSluch) {
        vidSluch = aVidSluch;}

    @Comment("Дата планируемой госпитализации")
    @Persist
    @DateString @DoDateString
    public String getPlanHospDate() {return planHospDate;}
    public void setPlanHospDate(String  aPlanHospDate) {
        planHospDate = aPlanHospDate;}

    @Comment("Цель посещения")
    @Persist
    public Long getVisitPurpose() {return visitPurpose;}
    public void setVisitPurpose(Long aVisitPurpose) {
        visitPurpose = aVisitPurpose;}

    @Comment("Онкологический случай")
    @Persist
    public Boolean getIsCancer() {return isCancer;}
    public void setIsCancer(Boolean aIsCancer) {
        isCancer = aIsCancer;}


    @Comment("Специальность врача по фонду V021")
    @Persist
    public Long getFondDoctorSpecV021() {return fondDoctorSpecV021;}
    public void setFondDoctorSpecV021(Long aFondDoctorSpecV021) {
        fondDoctorSpecV021 = aFondDoctorSpecV021;}

    @Comment("Родовое отделение")
    @Persist
    public Boolean getIsChildBirthDepartment() {return isChildBirthDepartment !=null && isChildBirthDepartment ;}
    public void setIsChildBirthDepartment(Boolean aIsChildBirthDepartment) {
        isChildBirthDepartment = aIsChildBirthDepartment;}

    @Comment("КДП")
    @Persist
    public Long getKdpVisit() {return kdpVisit;}
    public void setKdpVisit(Long aKdpVisit) {
        kdpVisit = aKdpVisit;}

    @Comment("Комментарии эксперта")
    @Persist
    public String getComment() {return comment;}
    public void setComment(String aComment) {
        comment = aComment;}

    @Comment("Профиль койки")
    @Persist
    public Long getBedProfile() {return bedProfile;}
    public void setBedProfile(Long aBedProfile) {
        bedProfile = aBedProfile;}

    @Comment("Рост пациента")
    @Persist
    public Integer getHeight() {return height;}
    public void setHeight(Integer aHeight) {
        height = aHeight;}

    @Comment("Вес пациента")
    @Persist
    public Integer getWeigth() {return weigth;}
    public void setWeigth(Integer aWeigth) {
        weigth = aWeigth;}

    /** Список выполненных назначений*/
    @Comment(" выполненных назначений")
    @Persist
    public String getPrescriptionList() {return thePrescriptionList;}
    public void setPrescriptionList(String aPrescriptionList) {thePrescriptionList = aPrescriptionList;}
    /** Список  выполненных назначений */
    private String thePrescriptionList ;

    //Ниже идут Нехранимые поля!!!

    /** Добавить услугу к случаю */
    @Comment("Добавить услугу к случаю")
    public Long getNewMedService() {return theNewMedService;}
    public void setNewMedService(Long aNewMedService) {theNewMedService = aNewMedService;}
    private Long theNewMedService ;

    /** Особенность случая */
    @Comment("Особенность случая")
    public Long getNewFactor() {return theNewFactor;}
    public void setNewFactor(Long aNewFactor) {theNewFactor = aNewFactor;}
    private Long theNewFactor ;

    /** Место рождения */
    @Comment("Место рождения")
    @Persist
    public String getBirthPlace() {return theBirthPlace;}
    public void setBirthPlace(String aBirthPlace) {theBirthPlace = aBirthPlace;}
    private String theBirthPlace ;

    /** Результат диспансеризации */
    @Comment("Результат диспансеризации")
    @Persist
    public Long getDispResult() {return theDispResult;}
    public void setDispResult(Long aDispResult) {theDispResult = aDispResult;}
    /** результат диспансеризации */
    private Long theDispResult ;

}
