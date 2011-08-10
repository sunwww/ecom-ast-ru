package ru.nuzmsh.ejb;

import javax.ejb.FinderException;
import java.util.Collection;

/**
 * Перебор значений
 */
public interface ICollectionNexter {
    /**
     * Следующий элемент
     * @param aCurrentPk текущий элемент. Если null - начинать сначала, если null и aForward = false - начинать с последнего
     * @param aForward   направление. true - вперед, false - назад
     * @param aCount     количество выбранных элементов
     * @return коллекцию элементов, Если пустая коллекция - элементов больше нет
     * @throws FinderException если не найден aCurrentPk
     */
    Collection findNext(String aCurrentPk, boolean aForward, int aCount) throws FinderException ;
}
