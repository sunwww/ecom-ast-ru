package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.DispensaryCard;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 *Карта диспансерного наблюдения
 */
@EntityForm
@EntityFormPersistance(clazz = DispensaryCard.class)
@Comment("Карта диспансерного наблюдения")
@WebTrail(comment = "Карта диспансерного наблюдения", nameProperties = "id", view = "entityView-mis_dispensaryCard.do")
@EntityFormSecurityPrefix("/Policy/Mis/Patient/Dispensary")
@Parent(property = "patient", parentForm =PatientForm.class)
public class DispensaryCardForm extends IdEntityForm {
        @Comment("Пациент")
        @Persist
        public Long getPatient() {return thePatient;}
        public void setPatient(Long aPatient) {thePatient = aPatient;}
        /** Пациент */
        private Long thePatient ;

        /** Дата постановки на Д учет */
        @Comment("Дата постановки на Д учет")
        @Persist @DateString @DoDateString
        @Required
        public String getStartDate() {return theStartDate;}
        public void setStartDate(String aStartDate) {theStartDate = aStartDate;}
        /** Дата постановки на Д учет */
        private String theStartDate ;

        /** Дата снятия с учета */
        @Comment("Дата снятия с учета")
        @Persist @DateString @DoDateString
        public String getFinishDate() {return theFinishDate;}
        public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}
        /** Дата снятия с учета */
        private String theFinishDate ;

        /** Диагноз постановки на Д учет */
        @Comment("Диагноз постановки на Д учет")
        @Persist @Required
        public Long getDiagnosis() {return theDiagnosis;}
        public void setDiagnosis(Long aDiagnosis) {theDiagnosis = aDiagnosis;}
        /** Диагноз постановки на Д учет */
        private Long theDiagnosis ;

        /** Рабочая функция врача установившего наблюдение */
        @Comment("Рабочая функция врача установившего наблюдение")
        @Persist @Required
        public Long getWorkFunction() {return theWorkFunction;}
        public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}
        /** Рабочая функция врача установившего наблюдение */
        private Long theWorkFunction ;

        /** Причина снятия с учета */
        @Comment("Причина снятия с учета")
        @Persist
        public Long getEndReason() {return theEndReason;}
        public void setEndReason(Long aEndReason) {theEndReason = aEndReason;}
        /** Причина снятия с учета */
        private Long theEndReason ;

        /** Дата создания */
        @Comment("Дата создания")
        @Persist
        @DateString @DoDateString
        public String getCreateDate() {return theCreateDate;}
        public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
        /** Дата создания */
        private String theCreateDate ;

        /** Время создания */
        @Comment("Время создания")
        @Persist
        @TimeString @DoTimeString
        public String getCreateTime() {return theCreateTime;}
        public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
        /** Время создания */
        private String theCreateTime ;

        /** Создатель */
        @Comment("Создатель")
        public String getCreateUsername() {return theCreateUsername;}
        public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
        /** Создатель */
        private String theCreateUsername ;

        /** Редактировал */
        @Comment("Редактировал")
        public String getEditUsername() {return theEditUsername;}
        public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
        /** Редактировал */
        private String theEditUsername ;

        /** Дата редактирования */
        @Comment("Дата редактирования")
        @Persist
        @DateString @DoDateString
        public String getEditDate() {return theEditDate;}
        public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
        /** Дата редактирования */
        private String theEditDate ;

        /** Время редактирования */
        @Comment("Время редактирования")
        @Persist
        @TimeString @DoTimeString
        public String getEditTime() {return theEditTime;}
        public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
        /** Время редактирования */
        private String theEditTime ;

}
