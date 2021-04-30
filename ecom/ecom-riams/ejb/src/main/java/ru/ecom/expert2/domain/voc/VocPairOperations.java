package ru.ecom.expert2.domain.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;

@Entity
@Getter
@Setter
/** Парные операции */
public class VocPairOperations extends BaseEntity {

    /** Операция */
    @Comment("Операция")
    @OneToOne
    public VocMedService getMedService() {return medService;}
    private VocMedService medService;

    /** Дата начала действия */
    private Date startDate;

    /** Дата окончания действия */
    private Date finishDate;
}
