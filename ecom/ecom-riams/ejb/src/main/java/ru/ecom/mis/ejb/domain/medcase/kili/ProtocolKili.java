package ru.ecom.mis.ejb.domain.medcase.kili;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.hospital.DeathCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocKiliConclusion;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Getter
@Setter
public class ProtocolKili extends BaseEntity {

    /**
     * Случай смерти
     */
    @Comment("Случай смерти")
    @ManyToOne
    public DeathCase getDeathCase() {
        return deathCase;
    }

    /**
     * Случай смерти
     */
    private DeathCase deathCase;


    /**
     * Дата проведения КИЛИ
     */
    private Date protocolDate;


    /**
     * Номер протокола
     */
    private String protocolNumber;

    /**
     * Список дефектов
     */
    @Comment("Список дефектов")
    @OneToMany(mappedBy = "protocol", cascade = CascadeType.ALL)
    public List<ProtocolKiliDefect> getDefects() {
        return defects;
    }

    /**
     * Список дефектов
     */
    private List<ProtocolKiliDefect> defects;

    /**
     * Решение КИЛИ
     */
    @Comment("Решение КИЛИ")
    @OneToOne
    public VocKiliConclusion getConclusion() {
        return conclusion;
    }

    /**
     * Решение КИЛИ
     */
    private VocKiliConclusion conclusion;

    /**
     * Дата создания
     */
    private Date createDate;


    /**
     * Время создания
     */
    private Time createTime;


    /**
     * Пользователь
     */
    private String createUsername;


    /**
     * Дата редактирования
     */
    private Date editDate;

    /**
     * Время редактирования
     */
    private Time editTime;


    /**
     * Редактирование пользователя
     */
    private String editUsername;

    /**
     * Примечание
     */
    private String protocolComment;
}