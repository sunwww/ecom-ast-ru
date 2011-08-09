package ru.ecom.jaas.web.action.role;

import ru.ecom.jaas.ejb.service.CheckNode;

import java.util.LinkedList;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 15.10.2006
 * Time: 22:15:30
 * To change this template use File | Settings | File Templates.
 */
public class CheckNodesUtil {

    public static void removeUnchecked(CheckNode aNode) {
        // 1. Удаляем если нет детей и не отмечен
        removeNoChildsAndNotChecked(null, aNode) ;
        // 2.
//        removeNoChildsAndNotChecked(null, aNode) ;
//        removeNoChildsAndNotChecked(null, aNode) ;
    }

    private static boolean removeNoChildsAndNotChecked(CheckNode aParent, CheckNode aNode) {
        boolean ret  ;
        if(aParent!=null && !aNode.getChecked() && aNode.getChilds().isEmpty()) {
            ret = true ;
        } else {
            LinkedList<CheckNode> removed = new LinkedList<CheckNode>();
            for (CheckNode node : aNode.getChilds()) {
                if(removeNoChildsAndNotChecked(aNode, node)) {
                    removed.add(node) ;
                }
            }
            Collection childs = aNode.getChilds() ;
            for (CheckNode node : removed) {
                childs.remove(node) ;
            }
            ret = !aNode.getChecked() && childs.isEmpty() ;
        }
        return ret ;
    }
}
