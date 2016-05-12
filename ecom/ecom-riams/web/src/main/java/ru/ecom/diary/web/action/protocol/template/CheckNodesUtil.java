package ru.ecom.diary.web.action.protocol.template;

import java.util.Collection;
import java.util.LinkedList;

import ru.ecom.diary.ejb.service.protocol.tree.CheckNode;

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
