package ru.nuzmsh.web.tree;

import org.apache.struts.action.ActionForm;

/**
 *
 */
public class TreeForm extends ActionForm {

    /** Идентификатор родителя */
    public String getParentId() { return theParentId ; }
    public void setParentId(String aParentId) { theParentId = aParentId ; }

    /** С какого начинать */
    public String getFromId() { return theFromId ; }
    public void setFromId(String aFromId) { theFromId = aFromId ; }

    /** Направление */
    public String getDirection() { return theDirection ; }
    public void setDirection(String aDirection) { theDirection = aDirection ; }

    /** Строка поиска */
    public String getSearch() { return theSearch ; }
    public void setSearch(String aSearch) { theSearch = aSearch ; }

    public boolean isForward() {
        return !"backward".equals(theDirection) ;
    }


    /** Строка поиска */
    private String theSearch ;
    /** Направление */
    private String theDirection = null ;
    /** С какого начинать */
    private String theFromId = null ;
    /** Идентификатор родителя */
    private String theParentId = null ;
}
