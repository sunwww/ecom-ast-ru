package ru.nuzmsh.forms.validator.transforms;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Преобразование похожих символов из ЛАТ в РУС.
 * Например: Y -> У
 */
@Retention(RetentionPolicy.RUNTIME)
@Target ({ElementType.METHOD})
public @interface DoInputNonLat {
}
