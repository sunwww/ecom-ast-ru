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

    /** Группа КСГ */
    @Comment("Группа КСГ")
    @Persist
    public Long getGroup() {return theGroup;}
    public void setGroup(Long aGroup) {theGroup = aGroup;}
    private Long theGroup ;

    /** Год КСГ */
    @Comment("Год КСГ")
    @Persist
    public Integer getYear() {return theYear;}
    public void setYear(Integer aYear) {theYear = aYear;}
    private Integer theYear ;

    /** Сверхдлительный КСГ (45 дней)	*/
    @Comment("Длительный срок лечения КСГ")
    @Persist
    public Boolean getLongKsg() {return theLongKsg;}
    public void setLongKsg(Boolean aLongKsg) {theLongKsg = aLongKsg;}
    private Boolean theLongKsg ;

    /** Является операцией */
    @Comment("Является операцией")
    @Persist
    public Boolean getIsOperation() {return theIsOperation;}
    public void setIsOperation(Boolean aIsOperation) {theIsOperation = aIsOperation;}
    private Boolean theIsOperation ;

    /** Оплачивать в полном объеме */
    @Comment("Оплачивать в полном объеме")
    @Persist
    public Boolean getIsFullPayment() {return theIsFullPayment;}
    public void setIsFullPayment(Boolean aIsFullPayment) {theIsFullPayment = aIsFullPayment;}
    private Boolean theIsFullPayment ;

    /** Коэффициент затрат */
    @Comment("Коэффициент затрат")
    @Persist @Required
    public Double getKZ() {return theKZ;}
    public void setKZ(Double aKZ) {theKZ = aKZ;}
    private Double theKZ ;

    /** Профиль помощи */
    @Comment("Профиль помощи")
    @Persist
    public String getProfile() {return theProfile;}
    public void setProfile(String aProfile) {theProfile = aProfile;}
    private String theProfile ;

    /** Тип коек */
    @Comment("Тип коек")
    @Persist @Required
    public Long getBedSubType() {return theBedSubType;}
    public void setBedSubType(Long aBedSubType) {theBedSubType = aBedSubType;}
    private Long theBedSubType ;

    /** Не учитывать КУСмо */
    @Comment("Не учитывать КУСмо")
    @Persist
    public Boolean getDoNotUseCusmo() {return theDoNotUseCusmo;}
    public void setDoNotUseCusmo(Boolean aDoNotUseCusmo) {theDoNotUseCusmo = aDoNotUseCusmo;}
    private Boolean theDoNotUseCusmo ;

    /** Covid-19 КСГ */
    @Comment("Covid-19 КСГ")
    @Persist
    public Boolean getIsCovid19() {return theIsCovid19;}
    public void setIsCovid19(Boolean aIsCovid19) {theIsCovid19 = aIsCovid19;}
    private Boolean theIsCovid19 ;

    /** Код */
    @Comment("Код")
    @Persist
    public String getCode() { return theCode ; }
    public void setCode(String aCode) { theCode = aCode ; }
    private String theCode ;

    /** Название */
    @Comment("Название")
    @Persist
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }
    private String theName ;

}
