package ru.ecom.expomc.ejb.domain.med;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Comment("МКБ 10")
@Table(schema = "SQLUser")
@AIndexes({
        @AIndex(properties = {"code"}),
        @AIndex(properties = {"code", "name"})
})
@Getter
@Setter
public class VocIdc10 extends VocIdCodeName {

    /**
     * Начальный возраст
     */
    private Double ageFrom;

    /**
     * Конечный возраст
     */
    private Double ageTo;

    /**
     * Экстренность
     */
    private Boolean emergency;

    /**
     * Неактуаленный
     */
    private Boolean noActuality;

    /**
     * Родитель
     */
    @Comment("Родитель")
    @OneToOne
    public VocIdc10 getParent() {
        return parent;
    }

    /**
     * Родитель
     */
    private VocIdc10 parent;
    /**
     * Класс или блок
     */
    private Boolean isBlock;
    /**
     * Используется в ОМС
     */
    /**
     * Код в промеде
     **/
    private String promedCode;

    /**
     * Разрешено использовать без уточнения
     */
    private Boolean isPermitWithoutDot;

    /**
     * Дата начала актуальности
     */
    private Date dateFrom;

    /**
     * Дата окончания актуальности
     */
    private Date dateTo;

    /**
     * Резрешен для COVID-19
     */
    private Boolean allowCovid;
}
