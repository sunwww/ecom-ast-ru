package ru.ecom.mis.ejb.domain.pharmnetPharmacy;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;

/**
 * Created by rkurbanov on 18.02.2018.
 */
@Comment("Комплект списания")
@Entity
@Table(name = "pharmnetComplectRow", schema="pharmnet")
public class PharmnetComplectRowEntity extends BaseEntity {

    private Integer regid;
    private Float count;
    private Long complectid;

    @Column(name = "regid")
    public Integer getRegid() {
        return regid;
    }
    public void setRegid(Integer regid) {
        this.regid = regid;
    }

    @Column(name = "count")
    public Float getCount() {
        return count;
    }
    public void setCount(Float count) {
        this.count = count;
    }

    @Column(name = "complectid")
    public Long getComplectid() {
        return complectid;
    }
    public void setComplectid(Long complectid) {
        this.complectid = complectid;
    }
}
