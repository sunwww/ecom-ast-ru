package ru.ecom.mis.ejb.domain.psychiatry;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsyhicHealthState;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;

/**
 * Состояние психического здоровья
 */
@Comment("Состояние психического здоровья")
@Entity
@AIndexes({
        @AIndex(properties = {"careCard"})
})
@Table(schema = "SQLUser")
@Getter
@Setter
public class PsychicHealthState extends BaseEntity {
    /**
     * Карта обратившегося за психиатрической помощью
     */
    @Comment("Карта обратившегося за психиатрической помощью")
    @ManyToOne
    public PsychiatricCareCard getCareCard() {
        return careCard;
    }

    /**
     * Карта обратившегося за психиатрической помощью
     */
    private PsychiatricCareCard careCard;
    /**
     * Дата начала
     */
    private Date startDate;

    /**
     * Вид состояния психического здоровья
     */
    @Comment("Вид состояния психического здоровья")
    @OneToOne
    public VocPsyhicHealthState getKind() {
        return kind;
    }

    /**
     * Вид состояния психического здоровья
     */
    private VocPsyhicHealthState kind;
    /**
     * Дата окончания
     */
    private Date finishDate;
    /**
     * Описание
     */
    private String notes;

    /**
     * Вид состояния психического здоровья
     */
    @Comment("Вид состояния психического здоровья")
    @Transient
    public String getKindInfo() {
        return kind != null ? kind.getName() : "";
    }

}
