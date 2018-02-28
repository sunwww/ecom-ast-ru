package ru.ecom.mis.ejb.domain.pharmnetPharmacy;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by rkurbanov on 07.02.2018.
 */
@Comment("Внешний документ.Тело")
@Entity
@Table(name = "externalDocumentRow", schema="pharmnet")
public class ExternalDocumentRowEntity extends BaseEntity{

    private Integer documentId;//+
    private Integer sortIndex;// Порядковый номер строки в документе. Целое число (4 байта).+
    private Long naklDataId;// Айди строки (Код партии товара). Целое число (8 байт).
    private Integer regId;// Код товара. Целое число (4 байта).+
    private Float quantity;// Количество. Число с плавающей точкой (точность 15,5).+
    private Float priceFabrNoNDS;// Цена производителя без НДС. Число с плавающей точкой (точность 15,2).+
    private Float priceFabrWNDS;// Цена производителя с НДС. Число с плавающей точкой (точность 15,2).+
    private Float priceOptNoNDS;// Цена оптовая (закупочная) без НДС. Число с плавающей точкой (точность 15,2).+
    private Float priceOptWNDS;// Цена оптовая (закупочная) с НДС. Число с плавающей точкой (точность 15,2).+
    private Float priceRoznWNDS;// Цена розничная (отпускная) с НДС. Число с плавающей точкой (точность 15,2).+
    private Float reestrPrice;// Цена реестра. Число с плавающей точкой (точность 15,2).+
    private Float reestrPriceSpr;// Цена реестра (справочно). Число с плавающей точкой (точность 15,2).+
    private Integer nds;// НДС. Целое число (4 байта).+
    private Date srokG;// Срок годности. Дата. Формат yyyy-MM-dd.+
    private String seria;// Серия. Строка (50).+
    private String barCodeFabr;// Штрих-код (производителя). Строка (50)+
    private String barCodeInner;// Штрих-код (внутренний). Строка (13)+
    private Boolean isDisable;// Признак удаления. Логическое.+
    private Boolean jv;// Признак ЖНВЛС. Логическое.+
    private Long naklDataRId;// Айди строки в парном расходном документе. Целое число (8 байт).+
    private Long firstNaklDataId;// Айди строки приходного документа (Код родительской партии). Целое число (8 байт).+

    private Float sumRoznWNDS;// Сумма розничная с НДС. Число с плавающей точкой (точность 15,5).+
    private Float fixedPrice;// Фиксированная цена на момент продажи. Число с плавающей точкой (точность 15,2).+
    private String comment;// Комментарий к строке акта на списание. Строка (150)+

    private Boolean isEnded;//+
    private Integer typeoperationId;//+

    @Column(name = "documentId")
    public Integer getDocumentId() {
        return documentId;
    }
    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    @Column(name = "sortIndex")
    public Integer getSortIndex() {
        return sortIndex;
    }
    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }

    @Column (name = "naklDataId")
    public Long getNaklDataId() {
        return naklDataId;
    }
    public void setNaklDataId(Long naklDataId) {
        this.naklDataId = naklDataId;
    }

    @Column (name = "regId")
    public Integer getRegId() {
        return regId;
    }
    public void setRegId(Integer regId) {
        this.regId = regId;
    }

    @Column (name = "quantity")
    public Float getQuantity() {
        return quantity;
    }
    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    @Column (name = "priceFabrNoNDS")
    public Float getPriceFabrNoNDS() {
        return priceFabrNoNDS;
    }
    public void setPriceFabrNoNDS(Float priceFabrNoNDS) {
        this.priceFabrNoNDS = priceFabrNoNDS;
    }

    @Column (name = "priceFabrWNDS")
    public Float getPriceFabrWNDS() {
        return priceFabrWNDS;
    }
    public void setPriceFabrWNDS(Float priceFabrWNDS) {
        this.priceFabrWNDS = priceFabrWNDS;
    }

    @Column (name = "priceOptNoNDS")
    public Float getPriceOptNoNDS() {
        return priceOptNoNDS;
    }
    public void setPriceOptNoNDS(Float priceOptNoNDS) {
        this.priceOptNoNDS = priceOptNoNDS;
    }

    @Column (name = "priceOptWNDS")
    public Float getPriceOptWNDS() {
        return priceOptWNDS;
    }
    public void setPriceOptWNDS(Float priceOptWNDS) {
        this.priceOptWNDS = priceOptWNDS;
    }

    @Column (name = "priceRoznWNDS")
    public Float getPriceRoznWNDS() {
        return priceRoznWNDS;
    }
    public void setPriceRoznWNDS(Float priceRoznWNDS) {
        this.priceRoznWNDS = priceRoznWNDS;
    }

    @Column (name = "reestrPrice")
    public Float getReestrPrice() {
        return reestrPrice;
    }
    public void setReestrPrice(Float reestrPrice) {
        this.reestrPrice = reestrPrice;
    }

    @Column (name = "reestrPriceSpr")
    public Float getReestrPriceSpr() {
        return reestrPriceSpr;
    }
    public void setReestrPriceSpr(Float reestrPriceSpr) {
        this.reestrPriceSpr = reestrPriceSpr;
    }

    @Column (name = "nds")
    public Integer getNds() {
        return nds;
    }
    public void setNds(Integer nds) {
        this.nds = nds;
    }

    @Column (name = "srokG")
    public Date getSrokG() {
        return srokG;
    }
    public void setSrokG(Date srokG) {
        this.srokG = srokG;
    }

    @Column (name = "seria")
    public String getSeria() {
        return seria;
    }
    public void setSeria(String seria) {
        this.seria = seria;
    }

    @Column (name = "barCodeFabr")
    public String getBarCodeFabr() {
        return barCodeFabr;
    }
    public void setBarCodeFabr(String barCodeFabr) {
        this.barCodeFabr = barCodeFabr;
    }

    @Column (name = "barCodeInner")
    public String getBarCodeInner() {
        return barCodeInner;
    }
    public void setBarCodeInner(String barCodeInner) {
        this.barCodeInner = barCodeInner;
    }

    @Column (name = "disable")
    public Boolean getDisable() {
        return isDisable;
    }
    public void setDisable(Boolean disable) {
        isDisable = disable;
    }

    @Column (name = "jv")
    public Boolean getJv() {
        return jv;
    }
    public void setJv(Boolean jv) {
        this.jv = jv;
    }

    @Column (name = "naklDataRId")
    public Long getNaklDataRId() {
        return naklDataRId;
    }
    public void setNaklDataRId(Long naklDataRId) {
        this.naklDataRId = naklDataRId;
    }

    @Column (name = "firstNaklDataId")
    public Long getFirstNaklDataId() {
        return firstNaklDataId;
    }
    public void setFirstNaklDataId(Long firstNaklDataId) {
        this.firstNaklDataId = firstNaklDataId;
    }

    @Column (name = "sumRoznWNDS")
    public Float getSumRoznWNDS() {
        return sumRoznWNDS;
    }
    public void setSumRoznWNDS(Float sumRoznWNDS) {
        this.sumRoznWNDS = sumRoznWNDS;
    }

    @Column (name = "fixedPrice")
    public Float getFixedPrice() {
        return fixedPrice;
    }
    public void setFixedPrice(Float fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    @Column (name = "comment")
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Basic
    @Column(name = "isEnded")
    public Boolean getEnded() {
        return isEnded;
    }
    public void setEnded(Boolean ended) {
        isEnded = ended;
    }

    @Basic
    @Column(name = "typeoperationId")
    public Integer getTypeoperationId() {
        return typeoperationId;
    }
    public void setTypeoperationId(Integer typeoperationId) {
        this.typeoperationId = typeoperationId;
    }

}
