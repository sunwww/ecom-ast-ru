package ru.ecom.mis.ejb.domain.pharmnetPharmacy.voc;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;

/**
 * Created by rkurbanov on 07.02.2018.
 */
@Comment("Справочник типов документов")
@Entity
@Table(name = "vocDocumentType", schema="pharmnet")
public class VocDocumentTypeEntity extends BaseEntity {

    private Integer docTypeId;
    private String docType;

    @Column (name = "docTypeId")
    public Integer getDocTypeId() {
        return docTypeId;
    }
    public void setDocTypeId(Integer docTypeId) {
        this.docTypeId = docTypeId;
    }

    @Column (name = "docType")
    public String getDocType() {
        return docType;
    }
    public void setDocType(String docType) {
        this.docType = docType;
    }
}
