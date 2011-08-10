package ru.nuzmsh.web.tree;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.nuzmsh.commons.tree.ISearchResult;
import ru.nuzmsh.commons.tree.ITreeModel;
import ru.nuzmsh.web.tree.player.TreeTablePlayer;

/**
 *
 */
public class TreeAction extends Action {
    private static final int MAX_COUNT = 10;
    private final static Log LOG = LogFactory.getLog(TreeAction.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;



    public ActionForward execute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest
            , HttpServletResponse aResponse) throws Exception {

        aRequest.setCharacterEncoding("UTF-8");
        aResponse.setContentType("text/html; charset=utf-8");

        String key = aMapping.getParameter();

        if (key == null) {
            throw new IllegalStateException("Нет параметра parameter в action");
        }
        ITreeModel model = (ITreeModel) aRequest.getSession().getAttribute(key) ;
        if(model==null) {
            throw new IllegalStateException("Нет ITreeModel в сессии с атрибутом "+key) ;
        }

        TreeForm form = (TreeForm) aForm ;
        if(form==null) {
            throw new IllegalStateException("Нет формы TreeForm") ;
        }

        PrintWriter out = aResponse.getWriter() ;

        TreeTablePlayer player = TreeTablePlayer.find(key, aRequest.getSession());

        if (CAN_TRACE) LOG.trace("form.getParentId() = " + form.getParentId());
        if (CAN_TRACE) LOG.trace("form.getFromId() = " + form.getFromId());
        if(form.getParentId()==null && form.getFromId()!=null) {
            Object parentId = model.getParentId(form.getFromId()) ;
            form.setParentId(parentId==null?null:parentId.toString());
        }
        if (CAN_TRACE) LOG.trace("after form.getParentId() = " + form.getParentId());

        Object fromId  ;
        if(form.getSearch()!=null) {
            ISearchResult result = form.isForward()
                    ? model.searchForward(form.getParentId(), form.getFromId(), form.getSearch(),-1 )
                    : model.searchBackward(form.getParentId(), form.getFromId(), form.getSearch(),-1 )
                    ;
            if(result.isSearchComplete()) {
                fromId = result.getId() ;
            } else {
                fromId = form.getFromId() ; //throw new Exception("Строка '"+form.getSearch()+"' пока не найдена") ;
            }
        } else {
            fromId = form.getFromId() ;
        }
        Collection col = form.isForward()
                ? model.listForward(form.getParentId(), fromId, MAX_COUNT)
                : model.listBackward(form.getParentId(), fromId, MAX_COUNT) ;
        if(col==null) col = new LinkedList() ;
        // добавляем строки, если переход назад и мало строк
        if(col.size()<MAX_COUNT && !form.isForward()) {
            addToBackward(col, player, form.getParentId(), model);
        }

        // ПУТЬ
        LinkedList path = new LinkedList();
        addPath(path, model, form.getParentId()) ;

        player.playJSON(out, col, path);

//        out.println("hello") ;

        return null ;
    }

    private void addPath(LinkedList aPath , ITreeModel aModel, Object aId) throws Exception {
        if(aId!=null) {
            aPath.add(0, aModel.get(aId)) ;
            Object parentId = aModel.getParentId(aId) ;
            addPath(aPath, aModel, parentId) ;
        }
    }

    private static void addToBackward(Collection col, TreeTablePlayer aPlayer, Object aParentId, ITreeModel aModel) throws Exception {
        Object obj = col.toArray()[col.size()-1] ;
        Object fromId = aPlayer.getId(obj) ;
        Collection col2 = aModel.listForward(aParentId, fromId, MAX_COUNT-col.size()+1) ;
        if(!col2.isEmpty()) {
            Iterator it = col2.iterator() ;
            it.next() ;
            it.remove();
            col.addAll(col2) ;
        }
    }
}
