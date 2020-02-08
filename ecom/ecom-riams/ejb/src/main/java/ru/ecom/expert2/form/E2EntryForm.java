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
    private Boolean theIsRehabBed ;
    /** Рост пациента */
    private Integer theHeight ;
    /** Вес пациента */
    private Integer theWeigth ;
    /** Профиль койки */
    private Long theBedProfile ;
    /** Комментарии эксперта */
    private String theComment ;
    /** КДП */
    private Long theKdpVisit ;
    /** Родовое отделение */
    private Boolean theIsChildBirthDepartment ;
    /** Специальность врача по фонду */
    private Long theFondDoctorSpecV021 ;
    /** Онкологический случай */
    private Boolean theIsCancer ;
    /** Цель посещения */
    private Long theVisitPurpose ;
    /** Вид случая (справочник VID_SLUCH) */
    private Long theVidSluch ;
    /** Дата планируемой госпитализации */
    private String  thePlanHospDate ;
    /** Дата направление на лечение */
    private String theDirectDate ;
    /** Иногородний  */
    private Boolean theIsForeign =false;
    /** Тип доп. диспансеризации */
    private String theExtDispType ;
    /** Возраст доп. диспансеризации */
    private String theExtDispAge ;
    /** Группа здоровья доп. диспансеризации */
    private String theExtDispHealthGroup ;
    /** Социальная группа доп. диспансеризации */
    private String theExtDispSocialGroup ;
    /** Назначения доп. диспансеризации */
    private String theExtDispAppointments ;
    /** Направлен на след. этап ДД */
    private Boolean theExtDispNextStage ;
    /** Признак детского возраст */
    private Boolean theIsChild ;
    /** Счет */
    private Long theBill ;
    /** Консультативно-диагностическое обращение */
    private Boolean theIsDiagnosticSpo ;
    /** Количество календарных дней */
    private Long theCalendarDays ;
    /** Подтип записи */
    private Long theSubType ;
    /** Признак мобильной поликлиники */
    private Boolean theIsMobilePolyclinic ;
    /** Был произведен аборт по медицинским показаниям */
    private Boolean theMedicalAbort ;
    /** Доп. критерий КСГ */
    private String theDopKritKSG ;
    /** Место приема пациента */
    private String theWorkPlace ;
    /** Признак дефекта */
    private Boolean theIsDefect ;
    /** Случай объединен */
    private Boolean theIsUnion ;
    /** Расчет случая ФОМС */
    private String theFondComment ;
    /** Удаленная запись */
    private Boolean theIsDeleted ;
    /** Случай реанимации */
    private Long theReanimationEntry ;
    /** Количество рожденных детей */
    private Long theNewbornAmount ;
    /** Признак исправленной записи */
    private Boolean thePRNOV ;
    /** Единый номер пациента (представителя) */
    private String theCommonNumber ;
    /** Название страх. компании */
    private String theInsuranceCompanyName ;
    /** ОГРН страховой компании */
    private String theInsuranceCompanyOgrn ;
    /** Регион нахождения страховой компании */
    private String theInsuranceCompanyTerritory ;
    /** Профиль оказания мед. помощи */
    private Long theMedHelpProfile ;
    /** Основная услуга случая */
    private String theMainService ;
    /** Основной МКБ случая */
    private String theMainMkb ;
    /** Прерванный случай */
    private Boolean theIsBreakedCase ;
    /** Не подавать на оплату */
    private Boolean theDoNotSend ;
    /** Условия оказания мед. помощи */
    private Long theMedHelpUsl ;
    /** Вид медицинской помощи */
    private Long theMedHelpKind ;
    /** Номер счета */
    private String theBillNumber ;
    /** Дата счета */
    private String theBillDate ;
    /** Результат оказания медицинской помощи */
    private Long theFondResult ;
    /** Исход случая */
    private Long theFondIshod ;
    /** Многоплодная беременность */
    private Boolean theMultiplyBirth ;
    /** ТИп записи */
    private String theEntryType ;
    /** Тип файла */
    private String theFileType ;
    /** Способ оплаты медицинской помощи */
    private Long theIDSP ;
    /** Причины неполной оплаты */
    private String theNotFullPaymentReason ;
    /** Базовый тариф */
    private String theBaseTarif ;
    /** Формула расчета цены */
    private String theCostFormulaString ;
    /** Позиция группировщика, по которой высчитан КСГ */
    private Long theKsgPosition ;
    /** Отделение не входит в ОМС */
    private Boolean theNoOmcDepartment ;
    /** Итоговый коэффициент */
    private String theTotalCoefficient ;
    /** Родительский случай */
    private Long theParentEntry ;
    /** ОКАТО регистрации */
    private String theOkatoReg ;
    /** ОКАТО проживания */
    private String theOkatoReal ;
    /** КСГ */
    private Long  theKsg ;
    /** Список операций */
    private String theOperationList ;
    /** Номер истории болезни */
    private String theHistoryNumber ;
    /** Название отделения */
    private String theDepartmentName ;
    /** Тип отделения */
    private String theDepartmentType ;
    /** Код отделения */
    private String theDepartmentCode ;
    /** ФИО врача */
    private String theDoctorName ;
    /** Должность врача */
    private String theDoctorWorkfunction ;
    /** СНИЛС врача */
    private String theDoctorSnils ;
    /** Экстренность */
    private Boolean theIsEmergency ;
    /** Направившее ЛПУ */
    private String theDirectLpu ;
    /** Вес новорожденного */
    private Long theNewbornWeight ;
    /** Тип направившего ЛПУ */
    private String theDirectLpuType ;
    /** Номер направление ФОМС */
    private String theTicket263Number ;
    /** Результат госпитализации */
    private String theResult ;
    /** Тип коек */
    private String theBedSubType ;
    /** Дата начала госпитализации */
    private String theHospitalStartDate ;
    /** Дата окончания госпитализации */
    private String theHospitalFinishDate ;
    /** Достигнутый результат */
    private String theReachedResult ;
    /** Повторная госпитализация */
    private String theReHospitalization ;
    /** Услуги */
    private String theServices ;
    /** Цена случая */
    private String theCost ;
    /** Ручное редактирование цены */
    private Boolean theIsManualKsg ;
    /** ИД отделения СЛО */
    private Long theDepartmentId ;
    /** Серия полиса */
    private String theMedPolicySeries ;
    /** Номер полиса */
    private String theMedPolicyNumber ;
    /** Страх. компания (федеральный код) */
    private String theInsuranceCompanyCode ;
    /** Талон ВМП - дата выдачи талона */
    private String theVMPTicketDate ;
    /** Талон ВМП - дата плановой госпитализации */
    private String theVMPPlanHospDate ;
    /** Вид ВМП */
    private String theVMPKind ;
    /** Метод ВМП */
    private String theVMPMethod ;
    /** Поток обслуживания */
    private String theServiceStream ;
    /** Были сообщения в полицию */
    private Boolean theIsCriminalMessage ;
    /** Находился по уходу за родственников */
    private Boolean theHotelServices ;
    /** Тип стационара */
    private String theStacType ;
    /** ЛПУ прикрепления */
    private String theAttachedLpu ;
    /** Профиль помощи */
    private String theHelpKind ;
    /** Идентификатор случая во внешней системе */
    private Long theExternalId ;
    /** Идентификатор пред. случая во внешней системе */
    private Long theExternalPrevMedcaseId ;
    /** Идентификатор госпитализации во внешней системе */
    private Long theExternalParentId ;
    /** Вид полиса OMC */
    private String theMedPolicyType ;
    /** Заполнение */
    private Long theListEntry ;
    /** Дата начала случая */
    private String theStartDate ;
    /** Время начала случая */
    private String theStartTime ;
    /** Дата окончания случая */
    private String theFinishDate ;
    /** Время окончания случая */
    private String theFinishTime ;
    /** Количество койкодней */
    private Long theBedDays ;
    /** Пол представителя */
    private String theKinsmanSex ;
    /** Тип родственной связи */
    private String theKinsmanRole ;
    /** Фамилия пациента */
    private String theLastname ;
    /** Имя пациента */
    private String theFirstname ;
    /** Отчество пациента */
    private String theMiddlename ;
    /** СНИЛС пациента */
    private String thePatientSnils ;
    /** Гражданство пациента */
    private String theNationality ;
    /** КЛАДР регистрации пациента (представителя) */
    private String theKLADRRegistration ;
    /** КЛАДР проживания пациента (представителя) */
    private String theKLADRReal ;
    /** Адрес проживания пациента (представителя) */
    private String theAddressRegistration ;
    /** Адрес проживания пациента (представителя) */
    private String theAddressReal ;

    /** Тип паспорта (ДУЛ) */
    private Long thePassportType ;
    /** Серия паспорта */
    private String thePassportSeries ;
    /** Номер паспорта */
    private String thePassportNumber ;
    /** Дата выдачи паспорта */
    private String thePassportDateIssued ;
    /** Кем выдан паспорт */
    private String thePassportWhomIssued ;
    /** Дата рождения пациента */
    private String theBirthDate ;
    /** Пол пациента */
    private String theSex ;
    /** Социальный статус пациента */
    private String theSocialStatus ;
    /** Каким по счету родился ребенок */
    private Long theBirthOrder ;
    /** Код ЛПУ */
    private String theLpuCode ;
    /** ВМП - кол-во установленных стентов */
    private Long theVMPStantAmount ;
    /** Номер талона ВМП */
    private String theVMPTicketNumber ;
    /** Список диагнозов по случаю */
    private String theDiagnosisList ;
    /** Идентификатор пациента во внешней системе */
    private Long theExternalPatientId ;
    /** Полис представителя */
    private String theKinsmanSnils ;
    /** Фамилия представителя */
    private String theKinsmanLastname ;
    /** Имя представитель */
    private String theKinsmanFirstname ;
    /** Отчество представителя */
    private String theKinsmanMiddlename ;
    /** Дата рождения представителя */
    private String theKinsmanBirthDate ;


    @Comment("Вид медицинской помощи")
    @Persist
    public Long getMedHelpKind() {return theMedHelpKind;}
    public void setMedHelpKind(Long aMedHelpKind) {theMedHelpKind = aMedHelpKind;}

    @Comment("Условия оказания мед. помощи")
    @Persist
    public Long getMedHelpUsl() {return theMedHelpUsl;}
    public void setMedHelpUsl(Long aMedHelpUsl) {theMedHelpUsl = aMedHelpUsl;}

    @Comment("Базовый тариф")
    @Persist
    public String getBaseTarif() {return theBaseTarif;}
    public void setBaseTarif(String aBaseTarif) {theBaseTarif = aBaseTarif;}

    @Comment("Формула расчета цены")
    @Persist
    public String getCostFormulaString() {return theCostFormulaString;}
    public void setCostFormulaString(String aCostFormulaString) {theCostFormulaString = aCostFormulaString;}

    @Comment("Позиция группировщика, по которой высчитан КСГ")
    @Persist
    public Long getKsgPosition() {return theKsgPosition;}
    public void setKsgPosition(Long aKsgPosition) {theKsgPosition = aKsgPosition;}

    @Comment("Отделение не входит в ОМС")
    @Persist
    public Boolean getNoOmcDepartment() {return theNoOmcDepartment;}
    public void setNoOmcDepartment(Boolean aNoOmcDepartment) {theNoOmcDepartment = aNoOmcDepartment;}

    @Comment("Итоговый коэффициент")
    @Persist
    public String getTotalCoefficient() {return theTotalCoefficient;}
    public void setTotalCoefficient(String aTotalCoefficient) {theTotalCoefficient = aTotalCoefficient;}

    @Comment("Родительский случай")
    @Persist
    public Long getParentEntry() {return theParentEntry;}
    public void setParentEntry(Long aParentEntry) {theParentEntry = aParentEntry;}

    @Comment("ОКАТО регистрации")
    @Persist
    public String getOkatoReg() {return theOkatoReg;}
    public void setOkatoReg(String aOkatoReg) {theOkatoReg = aOkatoReg;}

    @Comment("ОКАТО проживания")
    @Persist
    public String getOkatoReal() {return theOkatoReal;}
    public void setOkatoReal(String aOkatoReal) {theOkatoReal = aOkatoReal;}

    @Comment("КСГ")
    @Persist
    public Long getKsg() {return theKsg;}
    public void setKsg(Long  aKsg) {theKsg = aKsg;}

    @Comment("Список операций")
    @Persist
    public String getOperationList() {return theOperationList;}
    public void setOperationList(String aOperationList) {theOperationList = aOperationList;}

    @Comment("Список диагнозов по случаю")
    @Persist
    public String getDiagnosisList() {return theDiagnosisList;}
    public void setDiagnosisList(String aDiagnosisList) {theDiagnosisList = aDiagnosisList;}

    @Comment("Идентификатор пациента во внешней системе")
    @Persist
    public Long getExternalPatientId() {return theExternalPatientId;}
    public void setExternalPatientId(Long aExternalPatientId) {theExternalPatientId = aExternalPatientId;}

    @Comment("СНИЛС представителя")
    @Persist
    public String getKinsmanSnils() {return theKinsmanSnils;}
    public void setKinsmanSnils(String aKinsmanSnils) {theKinsmanSnils = aKinsmanSnils;}

    @Comment("Фамилия представителя")
    @Persist
    public String getKinsmanLastname() {return theKinsmanLastname;}
    public void setKinsmanLastname(String aKinsmanLastname) {theKinsmanLastname = aKinsmanLastname;}

    @Comment("Имя представитель")
    @Persist
    public String getKinsmanFirstname() {return theKinsmanFirstname;}
    public void setKinsmanFirstname(String aKinsmanFirstname) {theKinsmanFirstname = aKinsmanFirstname;}

    @Comment("Отчество представителя")
    @Persist
    public String getKinsmanMiddlename() {return theKinsmanMiddlename;}
    public void setKinsmanMiddlename(String aKinsmanMiddlename) {theKinsmanMiddlename = aKinsmanMiddlename;}

    @Comment("Дата рождения представителя")
    @Persist @DateString @DoDateString
    public String getKinsmanBirthDate() {return theKinsmanBirthDate;}
    public void setKinsmanBirthDate(String aKinsmanBirthDate) {theKinsmanBirthDate = aKinsmanBirthDate;}

    @Comment("Пол представителя")
    @Persist
    public String getKinsmanSex() {return theKinsmanSex;}
    public void setKinsmanSex(String aKinsmanSex) {theKinsmanSex = aKinsmanSex;}

    @Comment("Тип родственной связи")
    @Persist
    public String getKinsmanRole() {return theKinsmanRole;}
    public void setKinsmanRole(String aKinsmanRole) {theKinsmanRole = aKinsmanRole;}

    @Comment("Фамилия пациента")
    @Persist
    public String getLastname() {return theLastname;}
    public void setLastname(String aLastname) {theLastname = aLastname;}

    @Comment("Имя пациента")
    @Persist
    public String getFirstname() {return theFirstname;}
    public void setFirstname(String aFirstname) {theFirstname = aFirstname;}

    @Comment("Отчество пациента")
    @Persist
    public String getMiddlename() {return theMiddlename;}
    public void setMiddlename(String aMiddlename) {theMiddlename = aMiddlename;}

    @Comment("СНИЛС пациента")
    @Persist
    public String getPatientSnils() {return thePatientSnils;}
    public void setPatientSnils(String aPatientSnils) {thePatientSnils = aPatientSnils;}

    @Comment("Гражданство пациента")
    @Persist
    public String getNationality() {return theNationality;}
    public void setNationality(String aNationality) {theNationality = aNationality;}

    @Comment("КЛАДР регистрации пациента (представителя)")
    @Persist
    public String getKLADRRegistration() {return theKLADRRegistration;}
    public void setKLADRRegistration(String aKLADRRegistration) {theKLADRRegistration = aKLADRRegistration;}

    @Comment("КЛАДР проживания пациента (представителя)")
    @Persist
    public String getKLADRReal() {return theKLADRReal;}
    public void setKLADRReal(String aKLADRReal) {theKLADRReal = aKLADRReal;}

    @Comment("Адрес проживания пациента (представителя)")
    @Persist
    public String getAddressRegistration() {return theAddressRegistration;}
    public void setAddressRegistration(String aAddressRegistration) {theAddressRegistration = aAddressRegistration;}

    @Comment("Адрес проживания пациента (представителя)")
    @Persist
    public String getAddressReal() {return theAddressReal;}
    public void setAddressReal(String aAddressReal) {theAddressReal = aAddressReal;}

    @Comment("Тип паспорта (ДУЛ)")
    @Persist
    public Long getPassportType() {return thePassportType;}
    public void setPassportType(Long aPassportType) {thePassportType = aPassportType;}

    @Comment("Серия паспорта")
    @Persist
    public String getPassportSeries() {return thePassportSeries;}
    public void setPassportSeries(String aPassportSeries) {thePassportSeries = aPassportSeries;}

    @Comment("Номер паспорта")
    @Persist
    public String getPassportNumber() {return thePassportNumber;}
    public void setPassportNumber(String aPassportNumber) {thePassportNumber = aPassportNumber;}

    @Comment("Дата выдачи паспорта")
    @Persist @DateString @DoDateString
    public String getPassportDateIssued() {return thePassportDateIssued;}
    public void setPassportDateIssued(String aPassportDateIssued) {thePassportDateIssued = aPassportDateIssued;}

    @Comment("Кем выдан паспорт")
    @Persist
    public String getPassportWhomIssued() {return thePassportWhomIssued;}
    public void setPassportWhomIssued(String aPassportWhomIssued) {thePassportWhomIssued = aPassportWhomIssued;}

    @Comment("Дата рождения пациента")
    @Persist @DateString @DoDateString
    public String getBirthDate() {return theBirthDate;}
    public void setBirthDate(String aBirthDate) {theBirthDate = aBirthDate;}

    @Comment("Пол пациента")
    @Persist
    public String getSex() {return theSex;}
    public void setSex(String aSex) {theSex = aSex;}

    @Comment("Социальный статус пациента")
    @Persist
    public String getSocialStatus() {return theSocialStatus;}
    public void setSocialStatus(String aSocialStatus) {theSocialStatus = aSocialStatus;}

    @Comment("Каким по счету родился ребенок")
    @Persist
    public Long getBirthOrder() {return theBirthOrder;}
    public void setBirthOrder(Long aBirthOrder) {theBirthOrder = aBirthOrder;}

    @Comment("Код ЛПУ")
    @Persist
    public String getLpuCode() {return theLpuCode;}
    public void setLpuCode(String aLpuCode) {theLpuCode = aLpuCode;}

    @Comment("ВМП - кол-во установленных стентов")
    @Persist
    public Long getVMPStantAmount() {return theVMPStantAmount;}
    public void setVMPStantAmount(Long aVMPStantAmount) {theVMPStantAmount = aVMPStantAmount;}

    @Comment("Номер талона ВМП")
    @Persist
    public String getVMPTicketNumber() {return theVMPTicketNumber;}
    public void setVMPTicketNumber(String aVMPTicketNumber) {theVMPTicketNumber = aVMPTicketNumber;}

    @Comment("Талон ВМП - дата выдачи талона")
    @Persist
    public String getVMPTicketDate() {return theVMPTicketDate;}
    public void setVMPTicketDate(String aVMPTicketDate) {theVMPTicketDate = aVMPTicketDate;}

    @Comment("Талон ВМП - дата плановой госпитализации")
    @Persist @DateString @DoDateString
    public String getVMPPlanHospDate() {return theVMPPlanHospDate;}
    public void setVMPPlanHospDate(String aVMPPlanHospDate) {theVMPPlanHospDate = aVMPPlanHospDate;}

    @Comment("Вид ВМП")
    @Persist
    public String getVMPKind() {return theVMPKind;}
    public void setVMPKind(String aVMPKind) {theVMPKind = aVMPKind;}

    @Comment("Метод ВМП")
    @Persist
    public String getVMPMethod() {return theVMPMethod;}
    public void setVMPMethod(String aVMPMethod) {theVMPMethod = aVMPMethod;}

    @Comment("Поток обслуживания")
    @Persist
    public String getServiceStream() {return theServiceStream;}
    public void setServiceStream(String aServiceStream) {theServiceStream = aServiceStream;}

    @Comment("Были сообщения в полицию")
    @Persist
    public Boolean getIsCriminalMessage() {return theIsCriminalMessage;}
    public void setIsCriminalMessage(Boolean aIsCriminalMessage) {theIsCriminalMessage = aIsCriminalMessage;}

    @Comment("Находился по уходу за родственников")
    @Persist
    public Boolean getHotelServices() {return theHotelServices;}
    public void setHotelServices(Boolean aHotelServices) {theHotelServices = aHotelServices;}

    @Comment("Тип стационара") //Дневной, круглосуточный
    @Persist
    public String getStacType() {return theStacType;}
    public void setStacType(String aStacType) {theStacType = aStacType;}

    @Comment("ЛПУ прикрепления")
    @Persist
    public String getAttachedLpu() {return theAttachedLpu;}
    public void setAttachedLpu(String aAttachedLpu) {theAttachedLpu = aAttachedLpu;}

    @Comment("Профиль помощи")
    @Persist
    public String getHelpKind() {return theHelpKind;}
    public void setHelpKind(String aHelpKind) {theHelpKind = aHelpKind;}

    @Comment("Идентификатор случая во внешней системе")
    @Persist
    public Long getExternalId() {return theExternalId;}
    public void setExternalId(Long aExternalId) {theExternalId = aExternalId;}

    @Comment("Идентификатор пред. случая во внешней системе")
    @Persist
    public Long getExternalPrevMedcaseId() {return theExternalPrevMedcaseId;}
    public void setExternalPrevMedcaseId(Long aExternalPrevMedcaseId) {theExternalPrevMedcaseId = aExternalPrevMedcaseId;}

    @Comment("Идентификатор госпитализации во внешней системе")
    @Persist
    public Long getExternalParentId() {return theExternalParentId;}
    public void setExternalParentId(Long aExternalParentId) {theExternalParentId = aExternalParentId;}

    @Comment("Вид полиса OMC")
    @Persist
    public String getMedPolicyType() {return theMedPolicyType;}
    public void setMedPolicyType(String aMedPolicyType) {theMedPolicyType = aMedPolicyType;}

    @Comment("Заполнение")
    @Persist
    public Long getListEntry() {return theListEntry;}
    public void setListEntry(Long aListEntry) {theListEntry = aListEntry;}

    @Comment("Дата начала случая")
    @Persist @DateString @DoDateString
    public String getStartDate() {return theStartDate;}
    public void setStartDate(String aStartDate) {theStartDate = aStartDate;}

    @Comment("Время начала случая")
    @Persist @TimeString @DoTimeString
    public String getStartTime() {return theStartTime;}
    public void setStartTime(String aStartTime) {theStartTime = aStartTime;}

    @Comment("Дата окончания случая")
    @Persist @DateString @DoDateString
    public String getFinishDate() {return theFinishDate;}
    public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}

    @Comment("Время окончания случая")
    @Persist @TimeString @DoTimeString
    public String getFinishTime() {return theFinishTime;}
    public void setFinishTime(String aFinishTime) {theFinishTime = aFinishTime;}

    @Comment("Количество койкодней")
    @Persist
    public Long getBedDays() {return theBedDays;}
    public void setBedDays(Long aBedDays) {theBedDays = aBedDays;}

    @Comment("Номер истории болезни")
    @Persist
    public String getHistoryNumber() {return theHistoryNumber;}
    public void setHistoryNumber(String aHistoryNumber) {theHistoryNumber = aHistoryNumber;}

    @Comment("Название отделения")
    @Persist
    public String getDepartmentName() {return theDepartmentName;}
    public void setDepartmentName(String aDepartmentName) {theDepartmentName = aDepartmentName;}

    @Comment("Тип отделения")
    @Persist
    public String getDepartmentType() {return theDepartmentType;}
    public void setDepartmentType(String aDepartmentType) {theDepartmentType = aDepartmentType;}

    @Comment("Код отделения")
    @Persist
    public String getDepartmentCode() {return theDepartmentCode;}
    public void setDepartmentCode(String aDepartmentCode) {theDepartmentCode = aDepartmentCode;}

    @Comment("ФИО врача")
    @Persist
    public String getDoctorName() {return theDoctorName;}
    public void setDoctorName(String aDoctorName) {theDoctorName = aDoctorName;}

    @Comment("Должность врача")
    @Persist
    public String getDoctorWorkfunction() {return theDoctorWorkfunction;}
    public void setDoctorWorkfunction(String aDoctorWorkfunction) {theDoctorWorkfunction = aDoctorWorkfunction;}

    @Comment("СНИЛС врача")
    @Persist
    public String getDoctorSnils() {return theDoctorSnils;}
    public void setDoctorSnils(String aDoctorSnils) {theDoctorSnils = aDoctorSnils;}

    @Comment("Экстренность")
    @Persist
    public Boolean getIsEmergency() {return theIsEmergency;}
    public void setIsEmergency(Boolean aIsEmergency) {theIsEmergency = aIsEmergency;}

    @Comment("Направившее ЛПУ")
    @Persist
    public String getDirectLpu() {return theDirectLpu;}
    public void setDirectLpu(String aDirectLpu) {theDirectLpu = aDirectLpu;}

    @Comment("Вес новорожденного")
    @Persist
    public Long getNewbornWeight() {return theNewbornWeight;}
    public void setNewbornWeight(Long aNewbornWeight) {theNewbornWeight = aNewbornWeight;}

    @Comment("Тип направившего ЛПУ")
    @Persist
    public String getDirectLpuType() {return theDirectLpuType;}
    public void setDirectLpuType(String aDirectLpuType) {theDirectLpuType = aDirectLpuType;}

    @Comment("Номер направление ФОМС")
    @Persist
    public String getTicket263Number() {return theTicket263Number;}
    public void setTicket263Number(String aTicket263Number) {theTicket263Number = aTicket263Number;}

    @Comment("Результат госпитализации")
    @Persist
    public String getResult() {return theResult;}
    public void setResult(String aResult) {theResult = aResult;}

    @Comment("Тип коек") //Круглосуточные, дневные
    @Persist
    public String getBedSubType() {return theBedSubType;}
    public void setBedSubType(String aBedSubType) {theBedSubType = aBedSubType;}

    @Comment("Дата начала госпитализации")
    @Persist @DateString @DoDateString
    public String getHospitalStartDate() {return theHospitalStartDate;}
    public void setHospitalStartDate(String aHospitalStartDate) {theHospitalStartDate = aHospitalStartDate;}

    @Comment("Дата окончания госпитализации")
    @Persist @DateString @DoDateString
    public String getHospitalFinishDate() {return theHospitalFinishDate;}
    public void setHospitalFinishDate(String aHospitalFinishDate) {theHospitalFinishDate = aHospitalFinishDate;}

    @Comment("Достигнутый результат")
    @Persist
    public String getReachedResult() {return theReachedResult;}
    public void setReachedResult(String aReachedResult) {theReachedResult = aReachedResult;}

    @Comment("Повторная госпитализация")
    @Persist
    public String getReHospitalization() {return theReHospitalization;}
    public void setReHospitalization(String aReHospitalization) {theReHospitalization = aReHospitalization;}

    @Comment("Услуги")
    @Persist
    public String getServices() {return theServices;}
    public void setServices(String aServices) {theServices = aServices;}

    @Comment("Цена случая")
    @Persist
    public String getCost() {return theCost;}
    public void setCost(String aCost) {theCost = aCost;}

    @Comment("Ручное редактирование цены")
    @Persist
    public Boolean getIsManualKsg() {return theIsManualKsg;}
    public void setIsManualKsg(Boolean aIsManualKsg) {theIsManualKsg = aIsManualKsg;}

    @Comment("ИД отделения СЛО")
    @Persist
    public Long getDepartmentId() {return theDepartmentId;}
    public void setDepartmentId(Long aDepartmentId) {theDepartmentId = aDepartmentId;}

    @Comment("Серия полиса")
    @Persist
    public String getMedPolicySeries() {return theMedPolicySeries;}
    public void setMedPolicySeries(String aMedPolicySeries) {theMedPolicySeries = aMedPolicySeries;}

    @Comment("Номер полиса")
    @Persist
    public String getMedPolicyNumber() {return theMedPolicyNumber;}
    public void setMedPolicyNumber(String aMedPolicyNumber) {theMedPolicyNumber = aMedPolicyNumber;}

    @Comment("Страх. компания (федеральный код)")
    @Persist
    public String getInsuranceCompanyCode() {return theInsuranceCompanyCode;}
    public void setInsuranceCompanyCode(String aInsuranceCompanyCode) {theInsuranceCompanyCode = aInsuranceCompanyCode;}

    @Persist
    @Comment("Результат оказания медицинской помощи")
    public Long getFondResult() {return theFondResult;}
    public void setFondResult(Long theFondResult) {this.theFondResult = theFondResult;}

    @Persist
    @Comment("Исход случая")
    public Long getFondIshod() {return theFondIshod;}
    public void setFondIshod(Long theFondIshod) {this.theFondIshod = theFondIshod;}

    @Persist
    @Comment("Многоплодная беременность")
    public Boolean getMultiplyBirth() {return theMultiplyBirth;}
    public void setMultiplyBirth(Boolean theMultiplyBirth) {this.theMultiplyBirth = theMultiplyBirth;}

    @Persist
    @Comment("Тип записи")
    public String getEntryType() {return theEntryType;}
    public void setEntryType(String theEntryType) {this.theEntryType = theEntryType;}

    @Persist
    @Comment("Тип файла") //P, Z, DF, раки
    public String getFileType() {return theFileType;}
    public void setFileType(String aFileType) {theFileType = aFileType;}

    /** Реабилитационная койка */
    @Comment("Реабилитационная койка")
    @Persist
    public Boolean getIsRehabBed() {return theIsRehabBed;}
    public void setIsRehabBed(Boolean aIsRehabBed) {theIsRehabBed = aIsRehabBed;}

    @Persist
    @Comment("Способ оплаты медицинской помощи")
    public Long getIDSP() {return theIDSP;}
    public void setIDSP(Long theIDSP) {this.theIDSP = theIDSP;}

    @Persist
    @Comment("Причины неполной оплаты")
    public String getNotFullPaymentReason() {return theNotFullPaymentReason;}
    public void setNotFullPaymentReason(String theNotFullPaymentReason) {this.theNotFullPaymentReason = theNotFullPaymentReason;}

    @Comment("Номер счета")
    @Persist
    public String getBillNumber() {return theBillNumber;}
    public void setBillNumber(String aBillNumber) {theBillNumber = aBillNumber;}

    @Comment("Дата счета")
    @Persist @DateString @DoDateString
    public String getBillDate() {return theBillDate;}
    public void setBillDate(String aBillDate) {theBillDate = aBillDate;}

    @Comment("Не подавать на оплату")
    @Persist
    public Boolean getDoNotSend() {return theDoNotSend;}
    public void setDoNotSend(Boolean aDoNotSend) {theDoNotSend = aDoNotSend;}

    @Comment("Прерванный случай")
    @Persist
    public Boolean getIsBreakedCase() {return theIsBreakedCase;}
    public void setIsBreakedCase(Boolean aIsBreakedCase) {theIsBreakedCase = aIsBreakedCase;}

    @Comment("Основная услуга случая")
    @Persist
    public String getMainService() {return theMainService;}
    public void setMainService(String aMainService) {theMainService = aMainService;}

    @Comment("Основной МКБ случая")
    @Persist
    public String getMainMkb() {return theMainMkb;}
    public void setMainMkb(String aMainMkb) {theMainMkb = aMainMkb;}

    @Comment("Профиль оказания мед. помощи")
    @Persist
    public Long getMedHelpProfile() {return theMedHelpProfile;}
    public void setMedHelpProfile(Long aMedHelpProfile) {theMedHelpProfile = aMedHelpProfile;}

    @Comment("Случай реанимации")
    @Persist
    public Long getReanimationEntry() {return theReanimationEntry;}
    public void setReanimationEntry(Long aReanimationEntry) {theReanimationEntry = aReanimationEntry;}

    @Comment("Количество рожденных детей")
    @Persist
    public Long getNewbornAmount() {return theNewbornAmount;}
    public void setNewbornAmount(Long aNewbornAmount) {theNewbornAmount = aNewbornAmount;}

    @Comment("Признак исправленной записи")
    @Persist
    public Boolean getPRNOV() {return thePRNOV;}
    public void setPRNOV(Boolean aPRNOV) {thePRNOV = aPRNOV;}

    @Comment("Единый номер пациента (представителя)")
    @Persist
    public String getCommonNumber() {return theCommonNumber;}
    public void setCommonNumber(String aCommonNumber) {theCommonNumber = aCommonNumber;}

    @Comment("Название страх. компании")
    @Persist
    public String getInsuranceCompanyName() {return theInsuranceCompanyName;}
    public void setInsuranceCompanyName(String aInsuranceCompanyName) {theInsuranceCompanyName = aInsuranceCompanyName;}

    @Comment("ОГРН страховой компании")
    @Persist
    public String getInsuranceCompanyOgrn() {return theInsuranceCompanyOgrn;}
    public void setInsuranceCompanyOgrn(String aInsuranceCompanyOgrn) {theInsuranceCompanyOgrn = aInsuranceCompanyOgrn;}

    @Comment("Регион нахождения страховой компании")
    @Persist
    public String getInsuranceCompanyTerritory() {return theInsuranceCompanyTerritory;}
    public void setInsuranceCompanyTerritory(String aInsuranceCompanyTerritory) {theInsuranceCompanyTerritory = aInsuranceCompanyTerritory;}

    @Comment("Удаленная запись")
    @Persist
    public Boolean getIsDeleted() {return theIsDeleted;}
    public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}

    @Comment("Случай объединен")
    @Persist
    public Boolean getIsUnion() {return theIsUnion;}
    public void setIsUnion(Boolean aIsUnion) {theIsUnion = aIsUnion;}

    @Comment("Расчет случая ФОМС")
    @Persist
    public String getFondComment() {return theFondComment;}
    public void setFondComment(String aFondComment) {theFondComment = aFondComment;}

    @Comment("Признак дефекта")
    @Persist
    public Boolean getIsDefect() {return theIsDefect;}
    public void setIsDefect(Boolean aIsDefect) {theIsDefect = aIsDefect;}

    @Comment("Место приема пациента")
    @Persist
    public String getWorkPlace() {return theWorkPlace;}
    public void setWorkPlace(String aWorkPlace) {theWorkPlace = aWorkPlace;}

    @Comment("Доп. критерий КСГ")
    @Persist
    public String getDopKritKSG() {return theDopKritKSG;}
    public void setDopKritKSG(String aDopKritKSG) {theDopKritKSG = aDopKritKSG;}

    @Comment("Был произведен аборт по медицинским показаниям")
    @Persist
    public Boolean getMedicalAbort() {return theMedicalAbort;}
    public void setMedicalAbort(Boolean aMedicalAbort) {theMedicalAbort = aMedicalAbort;}

    @Comment("Признак мобильной поликлиники")
    @Persist
    public Boolean getIsMobilePolyclinic() {return theIsMobilePolyclinic;}
    public void setIsMobilePolyclinic(Boolean aIsMobilePolyclinic) {theIsMobilePolyclinic = aIsMobilePolyclinic;}

    @Comment("Подтип записи")
    @Persist
    public Long getSubType() {return theSubType;}
    public void setSubType(Long aSubType) {theSubType = aSubType;}

    /** Количество календарных дней */
    @Comment("Количество календарных дней")
    @Persist
    public Long getCalendarDays() {return theCalendarDays;}
    public void setCalendarDays(Long aCalendarDays) {theCalendarDays = aCalendarDays;}

    @Comment("Консультативно-диагностическое обращение")
    @Persist
    public Boolean getIsDiagnosticSpo() {return theIsDiagnosticSpo;}
    public void setIsDiagnosticSpo(Boolean aIsDiagnosticSpo) {theIsDiagnosticSpo = aIsDiagnosticSpo;}
    @Comment("Счет")
    @Persist
    public Long getBill() {return theBill;}
    public void setBill(Long aBill) {theBill = aBill;}

    @Comment("Признак детского возраст")
    @Persist
    public Boolean getIsChild() {return theIsChild;}
    public void setIsChild(Boolean aIsChild) {theIsChild = aIsChild;}

    @Comment("Тип доп. диспансеризации")
    @Persist
    public String getExtDispType() {return theExtDispType;}
    public void setExtDispType(String aExtDispType) {theExtDispType = aExtDispType;}

    @Comment("Возраст доп. диспансеризации")
    @Persist
    public String getExtDispAge() {return theExtDispAge;}
    public void setExtDispAge(String aExtDispAge) {theExtDispAge = aExtDispAge;}

    @Comment("Группа здоровья доп. диспансеризации")
    @Persist
    public String getExtDispHealthGroup() {return theExtDispHealthGroup;}
    public void setExtDispHealthGroup(String aExtDispHealthGroup) {theExtDispHealthGroup = aExtDispHealthGroup;}

    @Comment("Социальная группа доп. диспансеризации")
    @Persist
    public String getExtDispSocialGroup() {return theExtDispSocialGroup;}
    public void setExtDispSocialGroup(String aExtDispSocialGroup) {theExtDispSocialGroup = aExtDispSocialGroup;}

    @Comment("Назначения доп. диспансеризации")
    @Persist
    public String getExtDispAppointments() {return theExtDispAppointments;}
    public void setExtDispAppointments(String aExtDispAppointments) {theExtDispAppointments = aExtDispAppointments;}

    @Comment("Направлен на след. этап ДД")
    @Persist
    public Boolean getExtDispNextStage() {return theExtDispNextStage;}
    public void setExtDispNextStage(Boolean aExtDispNextStage) {theExtDispNextStage = aExtDispNextStage;}

    @Comment("Иногородний ")
    @Persist
    public Boolean getIsForeign() {return theIsForeign;}
    public void setIsForeign(Boolean aIsForeign) {theIsForeign = aIsForeign;}

    @Comment("Дата направление на лечение")
    @Persist @DateString @DoDateString
    public String getDirectDate() {return theDirectDate;}
    public void setDirectDate(String aDirectDate) {theDirectDate = aDirectDate;}

    @Comment("Вид случая (справочник VID_SLUCH)")
    @Persist
    public Long getVidSluch() {return theVidSluch;}
    public void setVidSluch(Long aVidSluch) {theVidSluch = aVidSluch;}

    @Comment("Дата планируемой госпитализации")
    @Persist
    @DateString @DoDateString
    public String getPlanHospDate() {return thePlanHospDate;}
    public void setPlanHospDate(String  aPlanHospDate) {thePlanHospDate = aPlanHospDate;}

    @Comment("Цель посещения")
    @Persist
    public Long getVisitPurpose() {return theVisitPurpose;}
    public void setVisitPurpose(Long aVisitPurpose) {theVisitPurpose = aVisitPurpose;}

    @Comment("Онкологический случай")
    @Persist
    public Boolean getIsCancer() {return theIsCancer;}
    public void setIsCancer(Boolean aIsCancer) {theIsCancer = aIsCancer;}


    @Comment("Специальность врача по фонду V021")
    @Persist
    public Long getFondDoctorSpecV021() {return theFondDoctorSpecV021;}
    public void setFondDoctorSpecV021(Long aFondDoctorSpecV021) {theFondDoctorSpecV021 = aFondDoctorSpecV021;}

    @Comment("Родовое отделение")
    @Persist
    public Boolean getIsChildBirthDepartment() {return theIsChildBirthDepartment!=null?theIsChildBirthDepartment:false;}
    public void setIsChildBirthDepartment(Boolean aIsChildBirthDepartment) {theIsChildBirthDepartment = aIsChildBirthDepartment;}

    @Comment("КДП")
    @Persist
    public Long getKdpVisit() {return theKdpVisit;}
    public void setKdpVisit(Long aKdpVisit) {theKdpVisit = aKdpVisit;}

    @Comment("Комментарии эксперта")
    @Persist
    public String getComment() {return theComment;}
    public void setComment(String aComment) {theComment = aComment;}

    @Comment("Профиль койки")
    @Persist
    public Long getBedProfile() {return theBedProfile;}
    public void setBedProfile(Long aBedProfile) {theBedProfile = aBedProfile;}

    @Comment("Рост пациента")
    @Persist
    public Integer getHeight() {return theHeight;}
    public void setHeight(Integer aHeight) {theHeight = aHeight;}

    @Comment("Вес пациента")
    @Persist
    public Integer getWeigth() {return theWeigth;}
    public void setWeigth(Integer aWeigth) {theWeigth = aWeigth;}

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

    /** результат диспансеризации */
    @Comment("результат диспансеризации")
    @Persist
    public Long getDispResult() {return theDispResult;}
    public void setDispResult(Long aDispResult) {theDispResult = aDispResult;}
    /** результат диспансеризации */
    private Long theDispResult ;



}
