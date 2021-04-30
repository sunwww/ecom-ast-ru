package ru.ecom.mis.ejb.domain.extdisp.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Справочник возрастных групп дополнительной диспансеризации
 */
@Comment("Справочник возрастных групп дополнительной диспансеризации")
@Entity
@Table(schema = "SQLUser")
@Getter
@Setter
public class VocExtDispAgeGroup extends VocBaseEntity {
    /**
     * Вид диспансеризации
     */
    @Comment("Вид диспансеризации")
    @OneToOne
    public VocExtDisp getDispType() {
        return dispType;
    }

    /**
     * Возрастная группа для отчета
     */
    @Comment("Возрастная группа для отчета")
    @OneToOne
    public VocExtDispAgeReportGroup getReportGroup() {
        return reportGroup;
    }

    /**
     * Код диспансеризации
     */
    private String dispCode;
    /**
     * Возрастная группа для отчета
     */
    private VocExtDispAgeReportGroup reportGroup;
    /**
     * Вид диспансеризации
     */
    private VocExtDisp dispType;

    /**
     * Архивная
     */
    private Boolean isArchival;
}
