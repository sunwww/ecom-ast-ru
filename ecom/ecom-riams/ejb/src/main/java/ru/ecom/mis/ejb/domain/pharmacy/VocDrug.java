package ru.ecom.mis.ejb.domain.pharmacy;

/**
 * Created by rkurbanov on 05.09.2017.
 */

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;


@Comment("Справочник лекарств")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)

public class VocDrug extends VocBaseEntity {

    private String tradeName;
    private String unit;
    private Integer packQn;
    private String type;
    private String packInfo;
    private String dosage;

    @Comment("Торговое название")
    public String getTradeName() {
        return tradeName;
    }
    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    @Comment("Количество в пачке")
    public Integer getPackQn() {
        return packQn;
    }
    public void setPackQn(Integer packQn) {
        this.packQn = packQn;
    }

    @Comment("Единица измерения")
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Comment("Тип")
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Comment("Инфорамция об упаковке")
    public String getPackInfo() {
        return packInfo;
    }
    public void setPackInfo(String packInfo) {
        this.packInfo = packInfo;
    }

    @Comment("Дозировка")
    public String getDosage() {
        return dosage;
    }
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }


}
