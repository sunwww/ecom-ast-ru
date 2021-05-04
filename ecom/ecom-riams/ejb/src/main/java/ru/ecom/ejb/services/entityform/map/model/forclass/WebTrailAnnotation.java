package ru.ecom.ejb.services.entityform.map.model.forclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebTrailAnnotation extends AbstractClassAnnotation {
    /**
     * Struts action для списка
     */
    private String list;
    /**
     * Struts action для просмотра
     */
    private String view;

    /**
     * Свойства для отображения
     */
    private String[] nameProperties;

    /**
     * Комментарий
     */
    private String comment;
}
