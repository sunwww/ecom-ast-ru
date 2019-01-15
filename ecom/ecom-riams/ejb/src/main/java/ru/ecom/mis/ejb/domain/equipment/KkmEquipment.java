package ru.ecom.mis.ejb.domain.equipment;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Comment("Кассовое оборудование")
@Table(schema="SQLUser")
public class KkmEquipment extends Equipment {
    /** URL аппарата */
    @Comment("URL аппарата")
    @Persist
    public String getUrl() {return theUrl;}
    public void setUrl(String aUrl) {theUrl = aUrl;}
    /** URL аппарата */
    private String theUrl;
}