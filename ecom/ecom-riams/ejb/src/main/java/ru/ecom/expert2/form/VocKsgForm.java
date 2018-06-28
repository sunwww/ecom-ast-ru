package ru.ecom.expert2.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = VocKsg.class)
@Comment("Диагноз по записи")
@WebTrail(comment = "КСГ", nameProperties = "id", view = "entityView-e2_vocKsg.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class VocKsgForm extends IdEntityForm {

    /** Код */
    @Comment("Код")
    @Persist
    public String getCode() { return theCode ; }
    public void setCode(String aCode) { theCode = aCode ; }

    /** Название */
    private String theCode ;

    /** Название */
    @Comment("Название")
    @Persist
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Название */
    private String theName ;
    /** Сверхдлительный КСГ (45 дней)	*/
    @Comment("Длительный срок лечения КСГ")
    @Persist
    public Boolean getLongKsg() {return theLongKsg;}
    public void setLongKsg(Boolean aLongKsg) {theLongKsg = aLongKsg;}
    /** Длительный срок лечения КСГ */
    private Boolean theLongKsg ;

    /** Является операцией */
    @Comment("Является операцией")
    @Persist
    public Boolean getIsOperation() {return theIsOperation;}
    public void setIsOperation(Boolean aIsOperation) {theIsOperation = aIsOperation;}
    /** Является операцией */
    private Boolean theIsOperation ;

    /** Оплачивать в полном объеме */
    @Comment("Оплачивать в полном объеме")
    @Persist
    public Boolean getIsFullPayment() {return theIsFullPayment;}
    public void setIsFullPayment(Boolean aIsFullPayment) {theIsFullPayment = aIsFullPayment;}
    /** Оплачивать в полном объеме */
    private Boolean theIsFullPayment ;

    /** Коэффициент затрат */
    @Comment("Коэффициент затрат")
    @Persist @Required
    public Double getKZ() {return theKZ;}
    public void setKZ(Double aKZ) {theKZ = aKZ;}
    /** Коэффициент затрат */
    private Double theKZ ;

    /** Профиль помощи */
    @Comment("Профиль помощи")
    @Persist
    public String getProfile() {return theProfile;}
    public void setProfile(String aProfile) {theProfile = aProfile;}
    /** Профиль помощи */
    private String theProfile ;

    /** Расширение для МКБ */
    @Comment("Расширение для МКБ")
    @Persist
    public Long getExtendsForIdc10() {
        return theExtendsForIdc10;
    }

    public void setExtendsForIdc10(Long aExtendsForIdc10) {
        theExtendsForIdc10 = aExtendsForIdc10;
    }

    /** МКБ10 */
    @Comment("МКБ10")
    @Persist
    public Long getIdc10() {
        return theIdc10;
    }
    public void setIdc10(Long aIdc10) {
        theIdc10 = aIdc10;
    }
    /** МКБ10 */
    private Long theIdc10;
    /** Расширение для МКБ */
    private Long theExtendsForIdc10;

    /** Уровень оказания для детей */
    @Comment("Уровень оказания для детей")
    @Persist
    public Long getChildLevel() {
        return theChildLevel;
    }

    /** Ср. количество дней для детей */
    @Comment("Ср. количество дней для детей")
    public String getChildDays() {
        return theChildDays;
    }

    public void setChildDays(String aChildDays) {
        theChildDays = aChildDays;
    }

    /** Уровень оказания для взрослых */
    @Comment("Уровень оказания для взрослых")
    @Persist
    public Long getAdultLevel() {
        return theAdultLevel;
    }

    public void setAdultLevel(Long aAdultLevel) {
        theAdultLevel = aAdultLevel;
    }

    /** Ср. кол-во дней для взрослых */
    @Comment("Ср. кол-во дней для взрослых")
    public String getAdultDays() {
        return theAdultDays;
    }

    public void setAdultDays(String aAdultDays) {
        theAdultDays = aAdultDays;
    }

    public void setChildLevel(Long aChildLevel) {
        theChildLevel = aChildLevel;
    }

    /** Ср. кол-во дней для взрослых */
    private String theAdultDays;
    /** Уровень оказания для взрослых */
    private Long theAdultLevel;
    /** Ср. количество дней для детей */
    private String theChildDays;

    /** Уровень оказания для детей */
    private Long theChildLevel;

    /** Тип коек */
    @Comment("Тип коек")
    @Persist @Required
    public Long getBedSubType() {return theBedSubType;}
    public void setBedSubType(Long aBedSubType) {theBedSubType = aBedSubType;}
    /** Тип коек */
    private Long theBedSubType ;

}
