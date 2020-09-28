package ru.ecom.mis.ejb.domain.equipment;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;

import javax.persistence.Entity;

@Entity
@Comment("Кассовое оборудование")
public class KkmEquipment extends Equipment {
    /** URL аппарата */
    @Comment("URL аппарата")
    @Persist
    public String getUrl() {return theUrl;}
    public void setUrl(String aUrl) {theUrl = aUrl;}
    private String theUrl;
}