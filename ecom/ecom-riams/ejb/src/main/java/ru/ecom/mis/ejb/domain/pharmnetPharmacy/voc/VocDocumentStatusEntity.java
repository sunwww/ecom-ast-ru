package ru.ecom.mis.ejb.domain.pharmnetPharmacy.voc;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;

/**
 * Created by rkurbanov on 07.02.2018.
 */
@Comment("Справочник статусов документов")
@Entity
@Table(name = "vocDocumentStatus", schema="pharmnet")
public class VocDocumentStatusEntity extends BaseEntity{

    private Integer statusId;
    private String name;

    @Column (name = "status_id")
    public Integer getStatusId() {
        return statusId;
    }
    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    @Column (name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
