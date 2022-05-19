package ru.ecom.mis.ejb.form.lpu;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.BedFund;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Коечный фонд
 *
 * @author oegorova
 */

@Comment("Коечный фонд")
@EntityForm
@EntityFormPersistance(clazz = BedFund.class)
@WebTrail(comment = "Коечный фонд", nameProperties = "id", view = "entityParentView-mis_bedFund.do")
@Parent(property = "lpu", parentForm = MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/BedFund")
@Setter
public class BedFundForm extends IdEntityForm {

    private String amount;
    private Long reductionType;
    private Long serviceStream;
    private Long bedType;
    private Long lpu;
    private Boolean noFood;
    private Boolean forChild;
    private String reductionTypeName;
    private String serviceStreamName;
    private String bedTypeName;
    private String serviceTypeName;
    private String vTE;
    private String vTS;
    private String dateStart;
    private String timeStart;
    private String dateFinish;
    private String timeFinish;
    private Long bedSubType;
    private String bedSubTypeName;
    private Boolean addCaseDuration;
    private Boolean isRehab;
    private String snilsDoctorDirect263;
    private String departmentAddressCode;
    private String vmpDepartmentAddressCode;

    @Comment("Количество коек")
    @Persist
    public String getAmount() {
        return amount;
    }

    @Comment("Тип развертывания-свертывания")
    @Persist
    @Required
    public Long getReductionType() {
        return reductionType;
    }

    @Comment("Поток обслуживания")
    @Persist
    @Required
    public Long getServiceStream() {
        return serviceStream;
    }

    @Comment("Профиль коек")
    @Persist
    @Required
    public Long getBedType() {
        return bedType;
    }

    @Comment("ЛПУ")
    @Persist
    public Long getLpu() {
        return lpu;
    }

    @Comment("Для детей")
    @Persist
    public Boolean getForChild() {
        return forChild;
    }

    @Comment("Без питания")
    @Persist
    public Boolean getNoFood() {
        return noFood;
    }

    @Comment("Тип свертывания (текст)")
    @Persist
    public String getReductionTypeName() {
        return reductionTypeName;
    }

    @Comment("Поток обслуживания (текст)")
    @Persist
    public String getServiceStreamName() {
        return serviceStreamName;
    }

    @Comment("Профиль коек (текст)")
    @Persist
    public String getBedTypeName() {
        return bedTypeName;
    }

    @Comment("Тип госпитального обслуживания (текст)")
    @Persist
    public String getServiceTypeName() {
        return serviceTypeName;
    }

    @Comment("Время окончания актуальности")
    @Persist
    public String getVTE() {
        return vTE;
    }

    @Comment("Время начала актуальности")
    @Persist
    public String getVTS() {
        return vTS;
    }

    @Comment("Дата начала актуальности")
    @Persist
    @Required
    @DateString
    @DoDateString
    public String getDateStart() {
        return dateStart;
    }

    @Comment("Время начала актуальности")
    @Persist
    @Required
    @TimeString
    @DoTimeString
    public String getTimeStart() {
        return timeStart;
    }

    @Comment("Дата окончания актуальности")
    @Persist
    @DateString
    @DoDateString
    public String getDateFinish() {
        return dateFinish;
    }

    @Comment("Время окончания актуальности")
    @Persist
    @TimeString
    @DoTimeString
    public String getTimeFinish() {
        return timeFinish;
    }

    @Comment("Тип койки")
    @Persist
    @Required
    public Long getBedSubType() {
        return bedSubType;
    }

    @Comment("Тип койки (текст)")
    @Persist
    public String getBedSubTypeName() {
        return bedSubTypeName;
    }

    @Comment("День выписки и день поступления считать разными днями")
    @Persist
    public Boolean getAddCaseDuration() {
        return addCaseDuration;
    }

    @Comment("Реабилитационные")
    @Persist
    public Boolean getIsRehab() {
        return isRehab;
    }

    @Comment("По умолчанию снилс врача генерации направлений для 263 приказа")
    @Persist
    public String getSnilsDoctorDirect263() {
        return snilsDoctorDirect263;
    }

    @Persist
    @Comment("Код адреса отделения")
    public String getDepartmentAddressCode() {
        return departmentAddressCode;
    }
    @Persist
    @Comment("Код адреса отделения для ВМП случаев")
    public String getVmpDepartmentAddressCode() {
        return vmpDepartmentAddressCode;
    }

}
