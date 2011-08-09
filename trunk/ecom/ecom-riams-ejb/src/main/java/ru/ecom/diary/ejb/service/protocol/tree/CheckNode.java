package ru.ecom.diary.ejb.service.protocol.tree;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

public class CheckNode implements Serializable {

    public CheckNode(String aId, String aName, boolean aChecked) {
        theId = aId;
        theName = aName;
        theChecked = aChecked;
        //theTypeParameterId = aTypeParameterId ;
        
    }

    /** Идентификатор */
    public String getId() { return theId ; }
    public void setId(String aId) { theId = aId ; }

    /** Идентификатор */
    private String theId ;
    /** Название */
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Отмечен */
    public boolean getChecked() { return theChecked ; }
    public void setChecked(boolean aChecked) { theChecked = aChecked ; }

    /** Список детей */
    public Collection<CheckNode> getChilds() { return theChilds ; }
    public void setChilds(Collection<CheckNode> aChilds) { theChilds = aChilds ; }

    /** Список детей */
    private Collection<CheckNode> theChilds = new LinkedList<CheckNode>();
    /** Отмечен */
    private boolean theChecked = false ;
    /** Название */
    private String theName = "NO_NAME";
}
