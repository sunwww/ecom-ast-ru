package ru.ecom.expert2.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.expert2.domain.E2ListEntry;
import ru.ecom.expert2.form.interceptors.EntryListCreateInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Список записей (заполнение
 */

@EntityForm
@EntityFormPersistance(clazz = E2ListEntry.class)
@Comment("Заполнение")
@WebTrail(comment = "Заполнение", nameProperties = {"id","name"}, view = "entityView-e2_entryList.do")
@EntityFormSecurityPrefix("/Policy/E2")
@ACreateInterceptors({
        @AEntityFormInterceptor(EntryListCreateInterceptor.class)
})
public class E2EntryListForm extends IdEntityForm {

    /** Дата последней проверки */
    @Comment("Дата последней проверки")
    @Persist
    @DateString @DoDateString
    public String getCheckDate() {return theCheckDate;}
    public void setCheckDate(String aCheckDate) {theCheckDate = aCheckDate;}
    /** Дата последней проверки */
    private String theCheckDate ;

    /** Время последней проверки */
    @Comment("Время последней проверки")
    @Persist
    @TimeString @DoTimeString
    public String getCheckTime() {return theCheckTime;}
    public void setCheckTime(String aCheckTime) {theCheckTime = aCheckTime;}
    /** Время последней проверки */
    private String theCheckTime ;

    /** Черновик */
    @Comment("Черновик")
    @Persist
    public Boolean getIsDraft() {return theIsDraft;}
    public void setIsDraft(Boolean aIsDraft) {theIsDraft = aIsDraft;}
    /** Черновик */
    private Boolean theIsDraft ;

    /** Закрыто для редакторирования */
    @Comment("Закрыто для редакторирования")
    @Persist
    public Boolean getIsClosed() {return theIsClosed;}
    public void setIsClosed(Boolean aIsClosed) {theIsClosed = aIsClosed;}
    /** Закрыто для редакторирования */
    private Boolean theIsClosed ;


    /** Удаленная запись */
    @Comment("Удаленная запись")
    @Persist
    public Boolean getIsDeleted() {return theIsDeleted;}
    public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}
    /** Удаленная запись */
    private Boolean theIsDeleted ;

    /** Имя заполнения */
    @Comment("Имя заполнения")
    @Persist
    public String getName() {return theName;}
    public void setName(String aName) {theName = aName;}
    /** Имя заполнения */
    private String theName ;

    /** Дата начала периода */
    @Comment("Дата начала периода")
    @Persist @DateString @DoDateString
    public String getStartDate() {return theStartDate;}
    public void setStartDate(String  aStartDate) {theStartDate = aStartDate;}
    /** Дата начала */
    private String  theStartDate ;

    /** Дата окончания периода */
    @Comment("Дата окончания периода")
    @Persist @DateString @DoDateString
    public String  getFinishDate() {return theFinishDate;}
    public void setFinishDate(String  aFinishDate) {theFinishDate = aFinishDate;}
    /** Дата окончания периода */
    private String  theFinishDate ;

    /** Тип заполнения */
    @Comment("Тип заполнения")
    @Persist
    public Long getEntryType() {return theEntryType;}
    public void setEntryType(Long  aEntryType) {theEntryType = aEntryType;}
    /** Тип заполнения */
    private Long  theEntryType ;

    /** Код ЛПУ, создавшее заполнение */
    @Comment("Код ЛПУ, создавшее заполнение")
    @Persist
    public String getLpuOmcCode() {return theLpuOmcCode;}
    public void setLpuOmcCode(String aLpuOmcCode) {theLpuOmcCode = aLpuOmcCode;}
    /** Код ЛПУ, создавшее заполнение */
    private String theLpuOmcCode ;

    /** Формировать пустое заполнение */
    @Comment("Формировать пустое заполнение")
    public Boolean getCreateEmptyEntryList() {return theCreateEmptyEntryList;}
    public void setCreateEmptyEntryList(Boolean aCreateEmptyEntryList) {theCreateEmptyEntryList = aCreateEmptyEntryList;}
    /** Формировать пустое заполнение */
    private Boolean theCreateEmptyEntryList ;

    /** Номера ИБ для формирования случаев */
    @Comment("Номера ИБ для формирования случаев")
    public String getHistoryNumbers() {return theHistoryNumbers;}
    public void setHistoryNumbers(String aHistoryNumbers) {theHistoryNumbers = aHistoryNumbers;}
    /** Номера ИБ для формирования случаев */
    private String theHistoryNumbers ;

    /** ИД монитора процесса проверки */
    @Comment("ИД монитора процесса проверки")
    @Persist
    public Long getMonitorId() {return theMonitorId;}
    public void setMonitorId(Long aMonitorId) {theMonitorId = aMonitorId;}
    /** ИД монитора процесса проверки */
    private Long theMonitorId ;

}
