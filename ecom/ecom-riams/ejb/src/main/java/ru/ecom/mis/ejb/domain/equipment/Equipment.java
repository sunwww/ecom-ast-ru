package ru.ecom.mis.ejb.domain.equipment;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.equipment.voc.VocCreater;
import ru.ecom.mis.ejb.domain.equipment.voc.VocMarka;
import ru.ecom.mis.ejb.domain.equipment.voc.VocProvider;
import ru.ecom.mis.ejb.domain.equipment.voc.VocTypeEquip;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.List;

/**
 * @author azviagin
 */
@Entity
@Comment("Оборудование")
@Table(schema = "SQLUser")
@Getter
@Setter
public class Equipment extends BaseEntity {

    /**
     * ЛПУ, в которых используются
     */
    @Comment("ЛПУ, в которых используются")
    @ManyToMany
    public List<MisLpu> getOtherLpu() {
        return otherLpu;
    }

    /**
     * ЛПУ, в которых используются
     */
    private List<MisLpu> otherLpu;

    /**
     * Название
     */
    private String name;

    /**
     * Марка оборудования
     */
    @OneToOne
    public VocMarka getMarka() {
        return marka;
    }

    /**
     * Наименование марки оборудования
     */
    @Transient
    public String getNameMarka() {
        return marka != null ? marka.getName() : "";
    }

    public void setNameMarka(String aNameMarka) {
    }

    /**
     * Тип оборудования
     */
    @OneToOne
    public VocTypeEquip getTypeEquip() {
        return typeEquip;
    }

    /**
     * Наименование типа оборудования
     */
    @Transient
    public String getNameTypeEquip() {
        return typeEquip != null ? typeEquip.getName() : "";
    }

    public void setNameTypeEquip(String aNameTypeEquip) {
    }


    /**
     * Производитель
     */
    @OneToOne
    public VocCreater getCreater() {
        return creater;
    }

    /**
     * Поставщик
     */
    @OneToOne
    public VocProvider getProvider() {
        return provider;
    }


    /**
     * ЛПУ
     */
    @ManyToOne
    public MisLpu getLpu() {
        return lpu;
    }

    /**
     * ЛПУ
     */
    private MisLpu lpu;
    /**
     * Примечание
     */
    private String info;
    /**
     * Поставщик
     */
    private VocProvider provider;
    /**
     * Производитель
     */
    private VocCreater creater;
    /**
     * Год установки
     */
    @Deprecated
    private Integer stayYear;
    /**
     * Год выпуска
     */
    private Integer createYear;
    /**
     * Тип оборудования
     */
    private VocTypeEquip typeEquip;
    /**
     * Марка оборудования
     */
    private VocMarka marka;

}
