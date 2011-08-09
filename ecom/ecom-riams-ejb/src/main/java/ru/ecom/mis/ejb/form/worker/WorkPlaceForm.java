package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 *Рабочее место
 *@author oegorova
 * 
 */
@EntityForm
@EntityFormPersistance(clazz= WorkFunction.class)
@Comment("Место работы")
@WebTrail(comment = "Место работы", nameProperties= "namePost", view="entityView-mis_workPlace.do")
@Parent(property="workBook", parentForm=WorkBookForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkPlace")
public class WorkPlaceForm extends IdEntityForm {
    /** Должность */
    @Persist
    @Comment("Должность")
    public Long getPost() {    return thePost ;}
    public void setPost(Long aPost ) {  thePost = aPost ; }

    /** Наименование должности */
    @Persist
    @Comment("Наименование должности")
    public String getNamePost() {    return theNamePost ;}
    public void setNamePost(String aNamePost ) {  theNamePost = aNamePost ; }

    /** Дата принятия */
    @Persist
    @Comment("Дата принятия")
    @DateString @DoDateString
    public String getDateOn() {    return theDateOn ;}
    public void setDateOn(String aDateOn ) {  theDateOn = aDateOn ; }

    /** Дата увольнения */
    @Persist
    @Comment("Дата увольнения")
    @DateString @DoDateString
    public String getDateOff() {    return theDateOff ;}
    public void setDateOff(String aDateOff ) {  theDateOff = aDateOff ; }

    /** На основании чего внесена запись о приеме (документ, его дата и номер) */
    @Persist
    @Comment("На основании чего внесена запись о приеме (документ, его дата и номер)")
    public String getBaseForCome() {    return theBaseForCome ;}
    public void setBaseForCome(String aBaseForCome ) {  theBaseForCome = aBaseForCome ; }

    /** Причина увольнения */
    @Persist
    @Comment("Причина увольнения")
    public Long getLeave() {    return theLeave ;}
    public void setLeave(Long aLeave ) {  theLeave = aLeave ; }

    /** На основании чего внесена запись об увольнении (документ, его дата и номер) */
    @Persist
    @Comment("На основании чего внесена запись об увольнении (документ, его дата и номер)")
    public String getBaseFroLeave() {    return theBaseFroLeave ;}
    public void setBaseFroLeave(String aBaseFroLeave ) {  theBaseFroLeave = aBaseFroLeave ; }

    /** Совместительство */
    @Persist
    @Comment("Совместительство")
    public Long getCombo() {    return theCombo ;}
    public void setCombo(Long aCombo ) {  theCombo = aCombo ; }

    /** Совместительство */
    @Persist
    @Comment("Совместительство")
    public String getNameCombo() {    return theNameCombo ;}
    public void setNameCombo(String aNameCombo ) {  theNameCombo = aNameCombo ; }

    /** Количество всего ставок */
    @Persist
    @Comment("Количество всего ставок")
    public Integer getFullRate() {    return theFullRate ;}
    public void setFullRate(Integer aFullRate ) {  theFullRate = aFullRate ; }

    /** Количество бюджетных ставок */
    @Persist
    @Comment("Количество бюджетных ставок")
    public Integer getBudjetRate() {    return theBudjetRate ;}
    public void setBudjetRate(Integer aBudjetRate ) {  theBudjetRate = aBudjetRate ; }

    /** Количество внебюджетных ставок */
    @Persist
    @Comment("Количество внебюджетных ставок")
    public Integer getOffBudjetRate() {    return theOffBudjetRate ;}
    public void setOffBudjetRate(Integer aOffBudjetRate ) {  theOffBudjetRate = aOffBudjetRate ; }

    /** Трудовая книга */
    @Persist
    @Comment("Трудовая книга")
    public Long getWorkBook() {    return theWorkBook ;}
    public void setWorkBook(Long aWorkBook ) {  theWorkBook = aWorkBook ; }

    /** Трудовая книга */
    private Long theWorkBook ;
    /** Количество внебюджетных ставок */
    private Integer theOffBudjetRate ;
    /** Количество бюджетных ставок */
    private Integer theBudjetRate ;
    /** Количество всего ставок */
    private Integer theFullRate ;
    /** Совместительство */
    private String theNameCombo ;
    /** Совместительство */
    private Long theCombo ;
    /** На основании чего внесена запись об увольнении (документ, его дата и номер) */
    private String theBaseFroLeave ;
    /** Причина увольнения */
    private Long theLeave ;
    /** На основании чего внесена запись о приеме (документ, его дата и номер) */
    private String theBaseForCome ;
    /** Дата увольнения */
    private String theDateOff ;
    /** Дата принятия */
    private String theDateOn ;
    /** Наименование должности */
    private String theNamePost ;
    /** Должность */
    private Long thePost ;
}
