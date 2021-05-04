package ru.ecom.expert2.domain.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV020;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV021;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;

/**
 * Справочник профилей медицинской помощи
 */

@Entity
@Getter
@Setter
public class VocE2MedHelpProfile extends VocBaseEntity {
    private Date startDate;
    private Date finishDate;
    private Boolean noActuality;
    private String profileK;
    private VocE2FondV021 medSpecV021;
    private String defaultDepartmentCode;

    @Comment("Мед. специальность V021 по профилю")
    @OneToOne
    public VocE2FondV021 getMedSpecV021() {
        return medSpecV021;
    }


}
