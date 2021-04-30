package ru.ecom.expert2.form;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2KsgCoefficientHistory;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = E2KsgCoefficientHistory.class)
@Comment("Диагноз по записи")
@WebTrail(comment = "КСГ", nameProperties = "id", view = "entityParentView-e2_vocKsgKuksg.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Parent(property = "ksg", parentForm = VocKsgForm.class)
@Setter
public class E2KsgCoefficientHistoryForm extends IdEntityForm {

        /** КСГ */
        @Comment("КСГ")
        @Persist @Required
        public Long getKsg() {return ksg;}
        /** КСГ */
        private Long ksg ;

        /** Дата начала действия */
        @Comment("Дата начала действия")
        @Persist @Required
        @DateString @DoDateString
        public String getStartDate() {return startDate;}
        /** Дата начала действия */
        private String startDate ;

        /** Дата окончания действия */
        @Comment("Дата окончания действия")
        @Persist @Required
        @DateString @DoDateString
        public String getFinishDate() {return finishDate;}
        /** Дата окончания действия */
        private String finishDate ;

        /** Управляющий коэффициент */
        @Comment("Управляющий коэффициент")
        @Persist @Required
        public String getValue() {return value;}
        /** Управляющий коэффициент */
        private String value;
}
