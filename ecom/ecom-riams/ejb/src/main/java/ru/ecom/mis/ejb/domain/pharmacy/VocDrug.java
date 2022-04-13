package ru.ecom.mis.ejb.domain.pharmacy;

/**
 * Created by rkurbanov on 05.09.2017.
 */

import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.expert2.domain.voc.federal.VocE2FondN020;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Comment("Справочник лекарств")
@Entity
@Table(schema = "SQLUser")
@EntityListeners(DeleteListener.class)
@Setter
@AIndexes(value = {@AIndex(properties = {"vocDrug"})
})
public class VocDrug extends VocBaseEntity {

    private String tradeName;
    private String unit;
    private Integer packQn;
    private String type;
    private String packInfo;
    private String dosage;
    private VocE2FondN020 vocDrug;
    private Boolean isCovidDrug;

    @Comment("Лекарство применяется для лечения covid-19")
    public Boolean getIsCovidDrug() {
        return isCovidDrug;
    }

    @Comment("Федеральный справочник лекарств")
    @ManyToOne
    public VocE2FondN020 getVocDrug() {
        return vocDrug;
    }

    @Comment("Торговое название")
    public String getTradeName() {
        return tradeName;
    }

    @Comment("Количество в пачке")
    public Integer getPackQn() {
        return packQn;
    }

    @Comment("Единица измерения")
    public String getUnit() {
        return unit;
    }

    @Comment("Тип")
    public String getType() {
        return type;
    }

    @Comment("Инфорамция об упаковке")
    public String getPackInfo() {
        return packInfo;
    }

    @Comment("Дозировка")
    public String getDosage() {
        return dosage;
    }


}
