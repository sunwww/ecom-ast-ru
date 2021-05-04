package ru.ecom.ejb.services.entityform.map.model.forclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParentAnnotation extends AbstractClassAnnotation {

    /**
     * Родительская форма
     */
    private String parentForm;
    /**
     * Свойство
     */
    private String property;
}
