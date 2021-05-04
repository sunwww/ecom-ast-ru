package ru.ecom.mis.ejb.domain.equipment;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;

import javax.persistence.Entity;

@Entity
@Comment("Кассовое оборудование")
@Getter
@Setter
public class KkmEquipment extends Equipment {
    /** URL аппарата */
    @Comment("URL аппарата")
    @Persist
    public String getUrl() {return url;}
    private String url;
}