package ru.ecom.mis.ejb.form.lpu;

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
public class BedFundForm extends IdEntityForm {

    private String theAmount;
    private Long theReductionType;
    private Long theServiceStream;
    private Long theBedType;
    private Long theLpu;
    private Boolean theNoFood;
    private Boolean theForChild;
    private String theReductionTypeName;
    private String theServiceStreamName;
    private String theBedTypeName;
    private String theServiceTypeName;
    private String theVTE;
    private String theVTS;
    private String theDateStart;
    private String theTimeStart;
    private String theDateFinish;
    private String theTimeFinish;
    private Long theBedSubType;
    private String theBedSubTypeName;
    private Boolean theAddCaseDuration;
    private Boolean theIsRehab;
    private String theSnilsDoctorDirect263;
    private String theDepartmentAddressCode;

    @Comment("Количество коек")
    @Persist
    public String getAmount() {
        return theAmount;
    }

    public void setAmount(String aAmount) {
        theAmount = aAmount;
    }

    @Comment("Тип развертывания-свертывания")
    @Persist
    @Required
    public Long getReductionType() {
        return theReductionType;
    }

    public void setReductionType(Long aReductionType) {
        theReductionType = aReductionType;
    }

    @Comment("Поток обслуживания")
    @Persist
    @Required
    public Long getServiceStream() {
        return theServiceStream;
    }

    public void setServiceStream(Long aServiceStream) {
        theServiceStream = aServiceStream;
    }

    @Comment("Профиль коек")
    @Persist
    @Required
    public Long getBedType() {
        return theBedType;
    }

    public void setBedType(Long aBedType) {
        theBedType = aBedType;
    }

    @Comment("ЛПУ")
    @Persist
    public Long getLpu() {
        return theLpu;
    }

    public void setLpu(Long aLpu) {
        theLpu = aLpu;
    }

    @Comment("Для детей")
    @Persist
    public Boolean getForChild() {
        return theForChild;
    }

    public void setForChild(Boolean aForChild) {
        theForChild = aForChild;
    }

    @Comment("Без питания")
    @Persist
    public Boolean getNoFood() {
        return theNoFood;
    }

    public void setNoFood(Boolean aNoFood) {
        theNoFood = aNoFood;
    }

    @Comment("Тип свертывания (текст)")
    @Persist
    public String getReductionTypeName() {
        return theReductionTypeName;
    }

    public void setReductionTypeName(String aReductionTypeName) {
        theReductionTypeName = aReductionTypeName;
    }

    @Comment("Поток обслуживания (текст)")
    @Persist
    public String getServiceStreamName() {
        return theServiceStreamName;
    }

    public void setServiceStreamName(String aServiceStreamName) {
        theServiceStreamName = aServiceStreamName;
    }

    @Comment("Профиль коек (текст)")
    @Persist
    public String getBedTypeName() {
        return theBedTypeName;
    }

    public void setBedTypeName(String aBedTypeName) {
        theBedTypeName = aBedTypeName;
    }

    @Comment("Тип госпитального обслуживания (текст)")
    @Persist
    public String getServiceTypeName() {
        return theServiceTypeName;
    }

    public void setServiceTypeName(String aServiceTypeName) {
        theServiceTypeName = aServiceTypeName;
    }

    @Comment("Время окончания актуальности")
    @Persist
    public String getVTE() {
        return theVTE;
    }

    public void setVTE(String aVTE) {
        theVTE = aVTE;
    }

    @Comment("Время начала актуальности")
    @Persist
    public String getVTS() {
        return theVTS;
    }

    public void setVTS(String aVTS) {
        theVTS = aVTS;
    }

    @Comment("Дата начала актуальности")
    @Persist
    @Required
    @DateString
    @DoDateString
    public String getDateStart() {
        return theDateStart;
    }

    public void setDateStart(String aDateStart) {
        theDateStart = aDateStart;
    }

    @Comment("Время начала актуальности")
    @Persist
    @Required
    @TimeString
    @DoTimeString
    public String getTimeStart() {
        return theTimeStart;
    }

    public void setTimeStart(String aTimeStart) {
        theTimeStart = aTimeStart;
    }

    @Comment("Дата окончания актуальности")
    @Persist
    @DateString
    @DoDateString
    public String getDateFinish() {
        return theDateFinish;
    }

    public void setDateFinish(String aDateFinish) {
        theDateFinish = aDateFinish;
    }

    @Comment("Время окончания актуальности")
    @Persist
    @TimeString
    @DoTimeString
    public String getTimeFinish() {
        return theTimeFinish;
    }

    public void setTimeFinish(String aTimeFinish) {
        theTimeFinish = aTimeFinish;
    }

    @Comment("Тип койки")
    @Persist
    @Required
    public Long getBedSubType() {
        return theBedSubType;
    }

    public void setBedSubType(Long aBedSubType) {
        theBedSubType = aBedSubType;
    }

    @Comment("Тип койки (текст)")
    @Persist
    public String getBedSubTypeName() {
        return theBedSubTypeName;
    }

    public void setBedSubTypeName(String aBedSubTypeName) {
        theBedSubTypeName = aBedSubTypeName;
    }

    @Comment("День выписки и день поступления считать разными днями")
    @Persist
    public Boolean getAddCaseDuration() {
        return theAddCaseDuration;
    }

    public void setAddCaseDuration(Boolean aAddCaseDuration) {
        theAddCaseDuration = aAddCaseDuration;
    }

    @Comment("Реабилитационные")
    @Persist
    public Boolean getIsRehab() {
        return theIsRehab;
    }

    public void setIsRehab(Boolean aIsRehab) {
        theIsRehab = aIsRehab;
    }

    @Comment("По умолчанию снилс врача генерации направлений для 263 приказа")
    @Persist
    public String getSnilsDoctorDirect263() {
        return theSnilsDoctorDirect263;
    }

    public void setSnilsDoctorDirect263(String aSnilsDoctorDirect263) {
        theSnilsDoctorDirect263 = aSnilsDoctorDirect263;
    }

    @Persist
    @Comment("Код адреса отделения")
    public String getDepartmentAddressCode() {
        return theDepartmentAddressCode;
    }

    public void setDepartmentAddressCode(String aDepartmentAddressCode) {
        theDepartmentAddressCode = aDepartmentAddressCode;
    }

}
