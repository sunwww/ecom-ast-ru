package ru.nuzmsh.util.voc;

import java.io.Serializable;

/**
 * Дополнительные параметры
 */
@SuppressWarnings("serial")
public class VocAdditional implements Serializable {

	public VocAdditional(String aParentId) {
        theParentId = aParentId ;
    }

    /** Родитель */
    public String getParentId() { return theParentId ; }

    /** Родитель */
    private final String theParentId ;
}
