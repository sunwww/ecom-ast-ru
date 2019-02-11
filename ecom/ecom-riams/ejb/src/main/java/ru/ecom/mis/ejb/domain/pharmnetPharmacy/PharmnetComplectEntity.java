package ru.ecom.mis.ejb.domain.pharmnetPharmacy;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by rkurbanov on 18.02.2018.
 */
@Comment("Комплект списания")
@Entity
@Table(name = "pharmnetComplect", schema="pharmnet")
public class PharmnetComplectEntity extends BaseEntity {

    private String name;
    private Long medService_id;

    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "medService_id")
    public Long getMedService_id() {
        return medService_id;
    }
    public void setMedService_id(Long medService_id) {
        this.medService_id = medService_id;
    }
}
