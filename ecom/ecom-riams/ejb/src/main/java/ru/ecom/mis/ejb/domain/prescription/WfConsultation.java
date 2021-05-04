package ru.ecom.mis.ejb.domain.prescription;
/**
 * Created by Milamesher on 14.09.2018.
 * Консультация специалиста
 */

import lombok.Getter;
import lombok.Setter;
import ru.ecom.diary.ejb.domain.Diary;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.medcase.voc.VocConsultingType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;

@Entity
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public class WfConsultation extends Prescription {
    /** Тип консультации */
    private VocConsultingType vocConsultingType;
    /** Заключение */
    private Diary diary;

    /** Тип консультации */
    @Comment("Тип консультации")
    @OneToOne
    public VocConsultingType getVocConsultingType() {return vocConsultingType; }

    /** Заключение */
    @Comment("Заключение")
    @OneToOne
    public Diary getDiary() { return diary; }
}