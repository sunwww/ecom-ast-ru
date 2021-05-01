package ru.ecom.mis.ejb.form.patient;

import lombok.Setter;
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
@Setter
public class DispensaryCardForm extends IdEntityForm {
        @Comment("Пациент")
        @Persist
        public Long getPatient() {return patient;}
        /** Пациент */
        private Long patient ;

        /** Дата постановки на Д учет */
        @Comment("Дата постановки на Д учет")
        @Persist @DateString @DoDateString
        @Required
        public String getStartDate() {return startDate;}
        /** Дата постановки на Д учет */
        private String startDate ;

        /** Дата снятия с учета */
        @Comment("Дата снятия с учета")
        @Persist @DateString @DoDateString
        public String getFinishDate() {return finishDate;}
        /** Дата снятия с учета */
        private String finishDate ;

        /** Диагноз постановки на Д учет */
        @Comment("Диагноз постановки на Д учет")
        @Persist @Required
        public Long getDiagnosis() {return diagnosis;}
        /** Диагноз постановки на Д учет */
        private Long diagnosis ;

        /** Рабочая функция врача установившего наблюдение */
        @Comment("Рабочая функция врача установившего наблюдение")
        @Persist @Required
        public Long getWorkFunction() {return workFunction;}
        /** Рабочая функция врача установившего наблюдение */
        private Long workFunction ;

        /** Причина снятия с учета */
        @Comment("Причина снятия с учета")
        @Persist
        public Long getEndReason() {return endReason;}
        /** Причина снятия с учета */
        private Long endReason ;

        /** Дата создания */
        @Comment("Дата создания")
        @Persist
        @DateString @DoDateString
        public String getCreateDate() {return createDate;}
        /** Дата создания */
        private String createDate ;

        /** Время создания */
        @Comment("Время создания")
        @Persist
        @TimeString @DoTimeString
        public String getCreateTime() {return createTime;}
        /** Время создания */
        private String createTime ;

        /** Создатель */
        @Comment("Создатель")
        public String getCreateUsername() {return createUsername;}
        /** Создатель */
        private String createUsername ;

        /** Редактировал */
        @Comment("Редактировал")
        public String getEditUsername() {return editUsername;}
        /** Редактировал */
        private String editUsername ;

        /** Дата редактирования */
        @Comment("Дата редактирования")
        @Persist
        @DateString @DoDateString
        public String getEditDate() {return editDate;}
        /** Дата редактирования */
        private String editDate ;

        /** Время редактирования */
        @Comment("Время редактирования")
        @Persist
        @TimeString @DoTimeString
        public String getEditTime() {return editTime;}
        /** Время редактирования */
        private String editTime ;

}
