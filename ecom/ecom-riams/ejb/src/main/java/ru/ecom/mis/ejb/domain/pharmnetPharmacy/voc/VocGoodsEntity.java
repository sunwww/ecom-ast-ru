package ru.ecom.mis.ejb.domain.pharmnetPharmacy.voc;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;

/**
 * Created by rkurbanov on 07.02.2018.
 */
@Comment("Наименования товаров")
@Entity
@Table(name = "vocGoods", schema="pharmnet")
@EntityListeners(DeleteListener.class)
public class VocGoodsEntity extends BaseEntity {

    private Integer regId;// Код товара. Целое число (4 байта).
    private Integer drugId;//Код наименования. Целое число (4 байта).
    private Integer formId;// Код формы выпуска. Целое число (4 байта).
    private Integer fabrId;// Код производителя. Целое число (4 байта).
    private String drug;// Наименование. Строка.
    private String form;//  Форма выпуска. Строка.
    private String fabr;//Производитель. Строка.
    private String mnn;// МНН. Строка.
    private Float countInPack;

    @Column(name = "regId")
    public Integer getRegId() {
        return regId;
    }
    public void setRegId(Integer regId) {
        this.regId = regId;
    }

    @Column(name = "drugId")
    public Integer getDrugId() {
        return drugId;
    }
    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }


    @Column(name = "formId")
    public Integer getFormId() {
        return formId;
    }
    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    @Column(name = "fabrId")
    public Integer getFabrId() {
        return fabrId;
    }
    public void setFabrId(Integer fabrId) {
        this.fabrId = fabrId;
    }


    @Column(name = "drug")
    public String getDrug() {
        return drug;
    }
    public void setDrug(String drug) {
        this.drug = drug;
    }


    @Column(name = "form")
    public String getForm() {
        return form;
    }
    public void setForm(String form) {
        this.form = form;
    }


    @Column(name = "fabr")
    public String getFabr() {
        return fabr;
    }
    public void setFabr(String fabr) {
        this.fabr = fabr;
    }


    @Column(name = "mnn")
    public String getMnn() {
        return mnn;
    }
    public void setMnn(String mnn) {
        this.mnn = mnn;
    }


    @Column(name = "countInPack")
    public Float getCountInPack() {
        return countInPack;
    }
    public void setCountInPack(Float countInPack) {
        this.countInPack = countInPack;
    }
}
