package ru.ecom.jaas.ejb.service;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 */
@Setter
@Getter
public class CheckNode implements Serializable {

    public CheckNode(long aId, String aName, boolean aChecked) {
        id = aId;
        name = aName;
        checked = aChecked;
    }

    public CheckNode() {
    }
    public boolean getChecked() {
        return checked;
    }
    /** Идентификатор */
    private long id ;

    /** Список детей */
    private Collection<CheckNode> childs = new LinkedList<>();
    /** Отмечен */
    private boolean checked = false ;
    /** Название */
    private String name = "NO_NAME";
}
