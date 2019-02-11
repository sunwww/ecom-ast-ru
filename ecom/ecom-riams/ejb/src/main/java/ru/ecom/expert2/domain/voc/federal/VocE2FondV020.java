package ru.ecom.expert2.domain.voc.federal;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Классификатор медицинских специальностей
 */
@Entity
public class VocE2FondV020 extends VocBaseFederal {
    @PrePersist
    void prePersist() {}

    @PreUpdate
    void preUpdate() {}

    /** Главный специалист в диагностическом СПО */
    @Comment("Главный специалист в диагностическом СПО")
    public Boolean getIsKdoChief() {return theIsKdoChief;}
    public void setIsKdoChief(Boolean aIsKdoChief) {theIsKdoChief = aIsKdoChief;}
    /** Главный специалист в диагностическом СПО */
    private Boolean theIsKdoChief ;
}
