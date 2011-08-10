package ru.nuzmsh.forms.autocomplete;

import java.util.Collection;

/**
 * Поддержка autocomplete
 */
public interface IAutocompletable {

    Collection findLikeByPrimaryKey(String aPk, int aCount) throws javax.ejb.FinderException ;

    Collection findFromLast(String aSkipped, int aCount) throws javax.ejb.FinderException ;

    Collection findFromNext(String aFromId, int aCount) throws javax.ejb.FinderException ;

    Collection findFromPrevious(String aFromId, int aCount) throws javax.ejb.FinderException ;

    Collection findByQuerySlow(String aQuery, int aCount) throws javax.ejb.FinderException ;

}
