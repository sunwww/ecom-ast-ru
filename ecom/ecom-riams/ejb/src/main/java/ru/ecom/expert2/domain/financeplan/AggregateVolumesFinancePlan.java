package ru.ecom.expert2.domain.financeplan;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.VocE2VidSluch;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
/** Аггрегированная таблица с выполнением финансового плана*/
public class AggregateVolumesFinancePlan extends BaseEntity {

    /** Тип финансового плана */
    @Comment("Тип финансового плана")
    public String getType() {return theType;}
    public void setType(String aType) {theType = aType;}
    /** Тип финансового плана */
    private String theType ;
    /** Год */
    @Comment("Год")
    public Integer getYear() {return theYear;}
    public void setYear(Integer aYear) {theYear = aYear;}
    /** Год */
    private Integer theYear ;

    /** Месяц */
    @Comment("Месяц")
    public Integer getMonth() {return theMonth;}
    public void setMonth(Integer aMonth) {theMonth = aMonth;}
    /** Месяц */
    private Integer theMonth ;

    /** Профиль медицинской помощи */
    @Comment("Профиль медицинской помощи")
    public Long getMedHelpProfile() {return theMedHelpProfile;}
    public void setMedHelpProfile(Long aMedHelpProfile) {theMedHelpProfile = aMedHelpProfile;}
    /** Профиль медицинской помощи */
    private Long theMedHelpProfile ;

    /** Имя профиля мед. помощи */
    @Comment("Имя профиля мед. помощи")
    public String getMedHelpProfileName() {return theMedHelpProfileName;}
    public void setMedHelpProfileName(String aMedHelpProfileName) {theMedHelpProfileName = aMedHelpProfileName;}
    /** Имя профиля мед. помощи */
    private String theMedHelpProfileName ;

    /** Отделение */
    @Comment("Отделение")
    public Long getDepartment() {return theDepartment;}
    public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
    /** Отделение */
    private Long theDepartment ;

    /** Название отделения */
    @Comment("Название отделения")
    public String getDepartmentName() {return theDepartmentName;}
    public void setDepartmentName(String aDepartmentName) {theDepartmentName = aDepartmentName;}
    /** Название отделения */
    private String theDepartmentName ;

    /** Тип коек */
    @Comment("Тип коек")
    public Long getBedSubType() {return theBedSubType;}
    public void setBedSubType(Long aBedSubType) {theBedSubType = aBedSubType;}
    /** Тип коек */
    private Long theBedSubType ;

    /** Имя типа коек */
    @Comment("Имя типа коек")
    public String getBedSubTypeName() {return theBedSubTypeName;}
    public void setBedSubTypeName(String aBedSubTypeName) {theBedSubTypeName = aBedSubTypeName;}
    /** Имя типа коек */
    private String theBedSubTypeName ;

    /** КСГ */
    @Comment("КСГ")
    public Long getKsg() {return theKsg;}
    public void setKsg(Long aKsg) {theKsg = aKsg;}
    /** КСГ */
    private Long theKsg ;

    /** Группа КСГ */
    @Comment("Группа КСГ")
    public Long getKsgGroup() {return theKsgGroup;}
    public void setKsgGroup(Long aKsgGroup) {theKsgGroup = aKsgGroup;}
    /** Группа КСГ */
    private Long theKsgGroup ;

    /** Имя группы КСГ */
    @Comment("Группа КСГ")
    public String getKsgGroupName() {return theKsgGroupName;}
    public void setKsgGroupName(String aKsgGroupName) {theKsgGroupName = aKsgGroupName;}
    /** Имя группы КСГ */
    private String theKsgGroupName ;


    /** Имя КСГ */
    @Comment("Имя КСГ")
    public String getKsgName() {return theKsgName;}
    public void setKsgName(String aKsgName) {theKsgName = aKsgName;}
    /** Имя КСГ */
    private String theKsgName ;

    /** Количество по плану */
    @Comment("Количество по плану")
    public Long getPlanCount() {return thePlanCount;}
    public void setPlanCount(Long aPlanCount) {thePlanCount = aPlanCount;}
    /** Количество по плану */
    private Long thePlanCount ;

    /** Сумма по плану */
    @Comment("Сумма по плану")
    public BigDecimal getPlanCost() {return thePlanCost;}
    public void setPlanCost(BigDecimal aPlanCost) {thePlanCost = aPlanCost;}
    /** Сумма по плану */
    private BigDecimal thePlanCost ;

    /** Количество по факту */
    @Comment("Количество по факту")
    public Long getFactCount() {return theFactCount;}
    public void setFactCount(Long aFactCount) {theFactCount = aFactCount;}
    /** Количество по факту */
    private Long theFactCount ;

    /** Сумма по факту */
    @Comment("Сумма по факту")
    public BigDecimal getFactCost() {return theFactCost;}
    public void setFactCost(BigDecimal aFactCost) {theFactCost = aFactCost;}
    /** Сумма по факту */
    private BigDecimal theFactCost ;

    /** Вид случая */
    @Comment("Вид случая")
    @OneToOne
    public VocE2VidSluch getVidSluch() {return theVidSluch;}
    public void setVidSluch(VocE2VidSluch aVidSluch) {theVidSluch = aVidSluch;}
    /** Вид случая */
    private VocE2VidSluch theVidSluch ;

    /** Метод ВМП */
    @Comment("Метод ВМП")
    public String getVmp() {return theVmp;}
    public void setVmp(String aVmp) {theVmp = aVmp;}
    /** Метод ВМП */
    private String theVmp ;

    /** Метод ВМП (название) */
    @Comment("Метод ВМП (название)")
    public String getVmpName() {return theVmpName;}
    public void setVmpName(String aVmpName) {theVmpName = aVmpName;}
    /** Метод ВМП (название) */
    private String theVmpName ;


}
