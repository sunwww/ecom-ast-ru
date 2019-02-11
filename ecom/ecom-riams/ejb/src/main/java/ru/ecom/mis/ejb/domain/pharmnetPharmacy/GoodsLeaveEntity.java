package ru.ecom.mis.ejb.domain.pharmnetPharmacy;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by rkurbanov on 07.02.2018.
 */
@Comment("Остатки")
@Entity
@Table(name = "goodsleave", schema="pharmnet")
public class GoodsLeaveEntity extends BaseEntity {

    private Integer storageId;
    private Integer branchId;
    private Integer regId; //товар
    private Integer naklDataId;//внешний документ
    private Float uQntOst; //количество упаковок
    private Float qntOst; //количество единиц
    private Float priceRoznWNDS; //цена розничная
    private Float priceOptWNDS; //цена закупочная
    private Float priceFabrNoNDS; //цена происзводителя
    private Float priceFabrWNDS;
    private Date docdate;
    private Date srokg;
    private String seria;
    private Boolean jv; //ЖНВЛС
    private Boolean brakLS; //брак
    private Boolean isEnd; //признак окончания
    private Long nextState;//следующее состояние
    private Long prevState;//предыдущее состояние


    @Basic
    @Column(name = "storageId")
    public Integer getStorageId() {
        return storageId;
    }
    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }

    @Basic
    @Column(name = "branchId")
    public Integer getBranchId() {
        return branchId;
    }
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    @Basic
    @Column(name = "regId")
    public Integer getRegId() {
        return regId;
    }
    public void setRegId(Integer regId) {
        this.regId = regId;
    }

    @Basic
    @Column(name = "naklDataId")
    public Integer getNaklDataId() {
        return naklDataId;
    }
    public void setNaklDataId(Integer naklDataId) {
        this.naklDataId = naklDataId;
    }

    @Basic
    @Column(name = "uQntOst")
    public Float getuQntOst() {
        return uQntOst;
    }
    public void setuQntOst(Float uQntOst) {
        this.uQntOst = uQntOst;
    }

    @Basic
    @Column(name = "qntOst")
    public Float getQntOst() {
        return qntOst;
    }
    public void setQntOst(Float qntOst) {
        this.qntOst = qntOst;
    }

    @Basic
    @Column(name = "priceRoznWNDS")
    public Float getPriceRoznWNDS() {
        return priceRoznWNDS;
    }
    public void setPriceRoznWNDS(Float priceRoznWNDS) {
        this.priceRoznWNDS = priceRoznWNDS;
    }

    @Basic
    @Column(name = "priceOptWNDS")
    public Float getPriceOptWNDS() {
        return priceOptWNDS;
    }
    public void setPriceOptWNDS(Float priceOptWNDS) {
        this.priceOptWNDS = priceOptWNDS;
    }

    @Basic
    @Column(name = "priceFabrNoNDS")
    public Float getPriceFabrNoNDS() {
        return priceFabrNoNDS;
    }
    public void setPriceFabrNoNDS(Float priceFabrNoNDS) {
        this.priceFabrNoNDS = priceFabrNoNDS;
    }

    @Basic
    @Column(name = "priceFabrWNDS")
    public Float getPriceFabrWNDS() {
        return priceFabrWNDS;
    }
    public void setPriceFabrWNDS(Float priceFabrWNDS) {
        this.priceFabrWNDS = priceFabrWNDS;
    }

    @Basic
    @Column(name = "docdate")
    public Date getDocdate() {
        return docdate;
    }
    public void setDocdate(Date docdate) {
        this.docdate = docdate;
    }

    @Basic
    @Column(name = "srokg")
    public Date getSrokg() {
        return srokg;
    }
    public void setSrokg(Date srokg) {
        this.srokg = srokg;
    }

    @Basic
    @Column(name = "seria")
    public String getSeria() {
        return seria;
    }
    public void setSeria(String seria) {
        this.seria = seria;
    }

    @Basic
    @Column(name = "jv")
    public Boolean getJv() {
        return jv;
    }
    public void setJv(Boolean jv) {
        this.jv = jv;
    }

    @Basic
    @Column(name = "brakLS")
    public Boolean getBrakLS() {
        return brakLS;
    }
    public void setBrakLS(Boolean brakLS) {
        this.brakLS = brakLS;
    }

    @Basic
    @Column(name = "isEnd")
    public Boolean getEnd() {
        return isEnd;
    }
    public void setEnd(Boolean end) {
        isEnd = end;
    }

    @Basic
    @Column(name = "nextState")
    public Long getNextState() {
        return nextState;
    }
    public void setNextState(Long nextState) {
        this.nextState = nextState;
    }

    @Basic
    @Column(name = "prevState")
    public Long getPrevState() {
        return prevState;
    }
    public void setPrevState(Long prevState) {
        this.prevState = prevState;
    }
}
