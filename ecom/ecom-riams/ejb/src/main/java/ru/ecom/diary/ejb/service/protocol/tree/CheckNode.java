package ru.ecom.diary.ejb.service.protocol.tree;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

@Setter
@Getter
public class CheckNode implements Serializable {

    public CheckNode(String aId, String aName, boolean aChecked) {
        id = aId;
        name = aName;
        checked = aChecked;

    }

    public boolean getChecked() {
        return checked;
    }

    /**
     * Идентификатор
     */
    private String id;

    /**
     * Список детей
     */
    private Collection<CheckNode> childs = new LinkedList<>();
    /**
     * Отмечен
     */
    private boolean checked = false;
    /**
     * Название
     */
    private String name = "NO_NAME";
}
