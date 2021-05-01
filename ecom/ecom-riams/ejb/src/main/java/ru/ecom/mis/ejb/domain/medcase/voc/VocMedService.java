package ru.ecom.mis.ejb.domain.medcase.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.Date;

/**
 * Справочник медицинских услуг
 *
 * @author azviagin, stkacheva, vtsybulin
 */
@Comment("Справочник медицинских услуг")
@Entity
@Table(schema = "SQLUser")
@NamedQueries({
        @NamedQuery(name = "VocMedService.vocMedServiceByCode"
                , query = "from VocMedService where code=:code and finishDate is null")
})
@Getter
@Setter
public class VocMedService extends VocBaseEntity {

    /**
     * Запрещена для пола
     */
    @Comment("Запрещена для пола")
    @OneToOne
    public VocSex getNotForSex() {
        return notForSex;
    }

    /**
     * Запрещена для пола
     */
    private VocSex notForSex;


    /**
     * Входит в омс
     */
    private Boolean isOmc;
    /**
     * Тип услуги
     */
    private String serviceType;
    /**
     * Комментарий
     */
    private String comment;
    /**
     * Полное название
     */
    private String longName;
    /**
     * Дата начала действия
     */
    private Date startDate;
    /**
     * Дата окончания действия
     */
    private Date finishDate;
}
