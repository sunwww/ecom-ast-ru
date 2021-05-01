package ru.ecom.ejb.services.entityform.map;

import lombok.Getter;

@Getter
public class StrutsMapTransform {

    public StrutsMapTransform(String aReturnType, String aCastType, String[] aAnnotations) {
        returnType = aReturnType;
        castType = aCastType;
        annotations = aAnnotations;
    }

    public StrutsMapTransform(String aReturnType, String aCastType) {
        this(aReturnType, aCastType, null);
    }

    public StrutsMapTransform(String aReturnType) {
        this(aReturnType, aReturnType, null);
    }

    private final String returnType;
    private final String castType;
    private final String[] annotations;
}
