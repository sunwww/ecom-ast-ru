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
/** Сочетанные операции */
@Getter
@Setter
public class VocCombinedOperations extends BaseEntity {

    /** Операция 1 */
    @Comment("Операция 1")
    @OneToOne
    public VocMedService getMedService1() {
        return medService1;
    }

    private VocMedService medService1;

    /**
     * Операция 2
     */
    @Comment("Операция 2")
    @OneToOne
    public VocMedService getMedService2() {
        return medService2;
    }

    private VocMedService medService2;

    /*Код сложности КСЛП*/
    private String difficultyCode;

    /**
     * Дата начала действия
     */
    private Date startDate;

    /**
     * Дата окончания действия
     */
    private Date finishDate;
}
