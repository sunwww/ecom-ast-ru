package ru.ecom.expert2.form;

import lombok.Setter;
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
@Setter
public class E2EntryListForm extends IdEntityForm {

    /** Дата последней проверки */
    @Comment("Дата последней проверки")
    @Persist
    @DateString @DoDateString
    public String getCheckDate() {return checkDate;}
    /** Дата последней проверки */
    private String checkDate ;

    /** Время последней проверки */
    @Comment("Время последней проверки")
    @Persist
    @TimeString @DoTimeString
    public String getCheckTime() {return checkTime;}
    /** Время последней проверки */
    private String checkTime ;

    /** Черновик */
    @Comment("Черновик")
    @Persist
    public Boolean getIsDraft() {return isDraft;}
    /** Черновик */
    private Boolean isDraft ;

    /** Закрыто для редакторирования */
    @Comment("Закрыто для редакторирования")
    @Persist
    public Boolean getIsClosed() {return isClosed;}
    /** Закрыто для редакторирования */
    private Boolean isClosed ;


    /** Удаленная запись */
    @Comment("Удаленная запись")
    @Persist
    public Boolean getIsDeleted() {return isDeleted;}
    /** Удаленная запись */
    private Boolean isDeleted ;

    /** Имя заполнения */
    @Comment("Имя заполнения")
    @Persist
    public String getName() {return name;}
    /** Имя заполнения */
    private String name ;

    /** Дата начала периода */
    @Comment("Дата начала периода")
    @Persist @DateString @DoDateString
    public String getStartDate() {return startDate;}
    /** Дата начала */
    private String  startDate ;

    /** Дата окончания периода */
    @Comment("Дата окончания периода")
    @Persist @DateString @DoDateString
    public String  getFinishDate() {return finishDate;}
    /** Дата окончания периода */
    private String  finishDate ;

    /** Тип заполнения */
    @Comment("Тип заполнения")
    @Persist
    public Long getEntryType() {return entryType;}
    /** Тип заполнения */
    private Long  entryType ;

    /** Код ЛПУ, создавшее заполнение */
    @Comment("Код ЛПУ, создавшее заполнение")
    @Persist
    public String getLpuOmcCode() {return lpuOmcCode;}
    /** Код ЛПУ, создавшее заполнение */
    private String lpuOmcCode ;

    /** Формировать пустое заполнение */
    @Comment("Формировать пустое заполнение")
    public Boolean getCreateEmptyEntryList() {return createEmptyEntryList;}
    /** Формировать пустое заполнение */
    private Boolean createEmptyEntryList ;

    /** Номера ИБ для формирования случаев */
    @Comment("Номера ИБ для формирования случаев")
    public String getHistoryNumbers() {return historyNumbers;}
    /** Номера ИБ для формирования случаев */
    private String historyNumbers ;

    /** ИД монитора процесса проверки */
    @Comment("ИД монитора процесса проверки")
    @Persist
    public Long getMonitorId() {return monitorId;}
    /** ИД монитора процесса проверки */
    private Long monitorId ;

}
