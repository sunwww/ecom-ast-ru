package ru.ecom.expert2.domain.price;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class ExtDispPriceMedService extends BaseEntity {

    /** Цена ДД */
    @Comment("Цена ДД")
    @ManyToOne
    public ExtDispPrice getPrice() {return price;}
    private ExtDispPrice price;

    /** Услуга */
    private String medService;

    /** Обязательное */
    private Boolean isRequired;

}