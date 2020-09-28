package ru.ecom.mis.ejb.domain.prescription;
/**
 * Created by Milamesher on 14.09.2018.
 * Консультация специалиста
 */

import ru.ecom.diary.ejb.domain.Diary;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.medcase.voc.VocConsultingType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;

@Entity
@EntityListeners(DeleteListener.class)
public class WfConsultation extends Prescription {
    /** Тип консультации */
    private VocConsultingType theVocConsultingType;
    /** Заключение */
    private Diary theDiary;

    /** Тип консультации */
    @Comment("Тип консультации")
    @OneToOne
    public VocConsultingType getVocConsultingType() {return theVocConsultingType; }
    public void setVocConsultingType(VocConsultingType aVocConsultingType) { theVocConsultingType = aVocConsultingType; }

    /** Заключение */
    @Comment("Заключение")
    @OneToOne
    public Diary getDiary() { return theDiary; }
    public void setDiary(Diary aDiary) { theDiary = aDiary; }
}