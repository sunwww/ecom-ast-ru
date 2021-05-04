package ru.ecom.mis.ejb.form.equipment;

import lombok.Setter;
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
@Setter
public class EquipmentForm extends IdEntityForm {

    /** Название */
	@Comment("Название")
	@Persist
	public String getName() {return name;}
	/** Название */
	private String name;
	
	/** Марка оборудования */
    @Persist
    @Comment("Марка оборудования")
    public Long getMarka() {    return marka ;}

    /** Марка оборудовани */
    @Persist
    @Comment("Марка оборудовани")
    public String getNameMarka() {    return nameMarka ;}

    /** Тип оборудования */
    @Persist
    @Comment("Тип оборудования")
    public Long getTypeEquip() {    return typeEquip ;}

    /** Наименование типа оборудования */
    @Persist
    @Comment("Наименование типа оборудования")
    public String getNameTypeEquip() {    return nameTypeEquip ;}

    /** Год выпуска */
    @Persist
    @Comment("Год выпуска")
    public Integer getCreateYear() {    return createYear ;}

    /** Год установки */
    @Persist
    @Comment("Год установки")
    public Integer getStayYear() {    return stayYear ;}

    /** Производитель */
    @Persist
    @Comment("Производитель")
    public Long getCreater() {    return creater ;}

    /** Поставщик */
    @Persist
    @Comment("Поставщик")
    public Long getProvider() {    return provider ;}

    /** Примечание */
    @Persist
    @Comment("Примечание")
    public String getInfo() {    return info ;}

    /** ЛПУ */
    @Persist
    @Comment("ЛПУ")
    public Long getLpu() {    return lpu ;}

    /** ЛПУ */
    private Long lpu ;
    /** Примечание */
    private String info ;
    /** Поставщик */
    private Long provider ;
    /** Производитель */
    private Long creater ;
    /** Год установки */
    private Integer stayYear ;
    /** Год выпуска */
    private Integer createYear ;
    /** Наименование типа оборудования */
    private String nameTypeEquip ;
    /** Тип оборудования */
    private Long typeEquip ;
    /** Наименование марки оборудования */
    private String nameMarka ;
    /** Марка оборудования */
    private Long marka ;
}
