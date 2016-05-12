package ru.ecom.mis.ejb.form.equipment;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.equipment.Equipment;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz= Equipment.class)
@Comment("Оборудование")
@WebTrail(comment = "Оборудование", nameProperties= "nameTypeEquip", view="entityView-mis_equipment.do")
@Parent(property="lpu", parentForm= MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Equipment/Equipment")
public class EquipmentForm extends IdEntityForm {

    /** Название */
	@Comment("Название")
	@Persist
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	/** Название */
	private String theName;
	
	/** Марка оборудования */
    @Persist
    @Comment("Марка оборудования")
    public Long getMarka() {    return theMarka ;}
    public void setMarka(Long aMarka ) {  theMarka = aMarka ; }

    /** Марка оборудовани */
    @Persist
    @Comment("Марка оборудовани")
    public String getNameMarka() {    return theNameMarka ;}
    public void setNameMarka(String aNameMarka ) {  theNameMarka = aNameMarka ; }

    /** Тип оборудования */
    @Persist
    @Comment("Тип оборудования")
    public Long getTypeEquip() {    return theTypeEquip ;}
    public void setTypeEquip(Long aTypeEquip ) {  theTypeEquip = aTypeEquip ; }

    /** Наименование типа оборудования */
    @Persist
    @Comment("Наименование типа оборудования")
    public String getNameTypeEquip() {    return theNameTypeEquip ;}
    public void setNameTypeEquip(String aNameTypeEquip ) {  theNameTypeEquip = aNameTypeEquip ; }

    /** Год выпуска */
    @Persist
    @Comment("Год выпуска")
    public Integer getCreateYear() {    return theCreateYear ;}
    public void setCreateYear(Integer aCreateYear ) {  theCreateYear = aCreateYear ; }

    /** Год установки */
    @Persist
    @Comment("Год установки")
    public Integer getStayYear() {    return theStayYear ;}
    public void setStayYear(Integer aStayYear ) {  theStayYear = aStayYear ; }

    /** Производитель */
    @Persist
    @Comment("Производитель")
    public Long getCreater() {    return theCreater ;}
    public void setCreater(Long aCreater ) {  theCreater = aCreater ; }

    /** Поставщик */
    @Persist
    @Comment("Поставщик")
    public Long getProvider() {    return theProvider ;}
    public void setProvider(Long aProvider ) {  theProvider = aProvider ; }

    /** Примечание */
    @Persist
    @Comment("Примечание")
    public String getInfo() {    return theInfo ;}
    public void setInfo(String aInfo ) {  theInfo = aInfo ; }

    /** ЛПУ */
    @Persist
    @Comment("ЛПУ")
    public Long getLpu() {    return theLpu ;}
    public void setLpu(Long aLpu ) {  theLpu = aLpu ; }

    /** ЛПУ */
    private Long theLpu ;
    /** Примечание */
    private String theInfo ;
    /** Поставщик */
    private Long theProvider ;
    /** Производитель */
    private Long theCreater ;
    /** Год установки */
    private Integer theStayYear ;
    /** Год выпуска */
    private Integer theCreateYear ;
    /** Наименование типа оборудования */
    private String theNameTypeEquip ;
    /** Тип оборудования */
    private Long theTypeEquip ;
    /** Наименование марки оборудования */
    private String theNameMarka ;
    /** Марка оборудования */
    private Long theMarka ;
}
