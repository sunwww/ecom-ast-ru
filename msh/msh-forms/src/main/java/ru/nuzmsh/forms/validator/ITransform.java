package ru.nuzmsh.forms.validator;

/**
 * @author ESinev
 *         Date: 03.11.2005
 *         Time: 11:02:58
 */
public interface ITransform {

    /**
     * @return NULL если преобразовывать не нужно
     */
    Object transform(Object aObject) ;

}
