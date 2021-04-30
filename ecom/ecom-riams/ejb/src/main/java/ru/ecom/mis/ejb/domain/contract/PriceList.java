package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Прейскурант
 */
@Comment("Прейскурант")
@Entity
@Table(schema = "SQLUser")
@AIndexes(value = {@AIndex(properties = {"name"})})
@Getter
@Setter
public class PriceList extends BaseEntity {
    @OneToMany(mappedBy = "priceList", cascade = CascadeType.ALL)
    public List<PricePosition> getPositions() {
        return positions;
    }

    /**
     * Позиции прейскуранта
     */
    private List<PricePosition> positions;

    /**
     * Название
     */
    private String name;

    /**
     * Дата начала действия
     */
    private Date dateFrom;

    /**
     * Дата окончания действия
     */
    private Date dateTo;

    @Transient
    public String getVocPriceInfo() {
        return "";
    }

    /**
     * Используется по умолчанию
     */
    private Boolean isDefault;

}
