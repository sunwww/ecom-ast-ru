package ru.ecom.mis.ejb.domain.prescription;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Выполнениe назначения
 *
 * @author azviagin
 */

@Comment("Выполнениe назначения")
@Entity
@Table(schema = "SQLUser")
@AIndexes({
        @AIndex(properties = {"prescription"})
})
@Getter
@Setter
public class PrescriptionFulfilment extends BaseEntity {
    /**
     * Дата выполнения
     */
    private Date fulfilDate;
    /**
     * Время выполнения
     */
    private Time fulfilTime;

    /**
     * Исполнитель
     */
    @Comment("Исполнитель")
    @OneToOne
    public Worker getExecutor() {
        return executor;
    }

    /**
     * Исполнитель
     */
    private Worker executor;

    /**
     * Комментарии
     */
    private String comments;

    /**
     * Дата и время регистрации
     */
    private Timestamp registionTimeStamp;

    /**
     * Назначение
     */
    @Comment("Назначение")
    @ManyToOne
    public Prescription getPrescription() {
        return prescription;
    }

    /**
     * Назначение
     */
    private Prescription prescription;


    /**
     * Рабочая функция исполнителя
     */
    @Comment("Рабочая функция исполнителя")
    @OneToOne
    public WorkFunction getExecutorWorkFunction() {
        return executorWorkFunction;
    }

    /**
     * Дата создания записи
     */
    private Date dateCreate;
    /**
     * Пользователь
     */
    private String username;
    /**
     * Рабочая функция исполнителя
     */
    private WorkFunction executorWorkFunction;

    @Transient
    public String getExecutorInfo() {
        return executorWorkFunction != null ? executorWorkFunction.getWorkerInfo() : "";
    }
}