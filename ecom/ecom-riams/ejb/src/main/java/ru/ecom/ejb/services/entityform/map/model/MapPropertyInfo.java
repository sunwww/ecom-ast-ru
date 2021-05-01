package ru.ecom.ejb.services.entityform.map.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MapPropertyInfo {

    /**
     * Сохраняется ли в Entity
     */
    private boolean persist = false;
    /**
     * Название
     */
    private String name;
}
