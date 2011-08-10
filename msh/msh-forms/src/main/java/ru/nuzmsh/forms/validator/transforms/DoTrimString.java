package ru.nuzmsh.forms.validator.transforms;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * @author ESinev
 *         Date: 03.11.2005
 *         Time: 11:25:53
 */
@Retention(RetentionPolicy.RUNTIME)
@Target ({ElementType.METHOD})
public @interface DoTrimString {
}
