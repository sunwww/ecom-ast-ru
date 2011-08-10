package ru.nuzmsh.web.tree.player;

import java.awt.Point;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONWriter;

/**
 * Проигрывание компонента
 */
public class TreeTablePlayer {

    private final static Log LOG = LogFactory.getLog(TreeTablePlayer.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;


    public TreeTablePlayer(String aName, String aTitle, String aIdField, String aParentIdField, String aNameField) {
        theName = aName ;
        theIdField = aIdField ;
        theParentIdField = aParentIdField ;
        theTitle = aTitle ;
        theNameField = aNameField ;
    }


    public void addColumn(String aTitle, String aProperty) {
        theColumns.add(new TreeTableColumn(aTitle, aProperty)) ;
    }


    public static TreeTablePlayer find(String aName, HttpSession aSession) {
        TreeTablePlayer player = (TreeTablePlayer) aSession.getAttribute(aName+".Player") ;
        if(player==null) {
            throw new IllegalStateException("Нет в сессии "+aName+".Player") ;
        }

        return (TreeTablePlayer) aSession.getAttribute(aName+".Player") ;
    }

    public void save(JspContext aJspContext) {
        if (CAN_TRACE) LOG.trace("Cохранение Player в " + theName+".Player");
        aJspContext.setAttribute(theName+".Player", this, PageContext.SESSION_SCOPE);

    }

    public void playJSON(PrintWriter aOut, Collection aList, Collection aPath) throws Exception {
        JSONWriter j = new JSONWriter(aOut) ;
        //j.object().key("titles").value(new JSONArray(theColumns)) ;
//        j.object().key("titles").value(new JSONArray(theColumns)).endObject() ;
        j.object().key("titles").value(new JSONArray(theColumns));

        // путь
        j.key("path").array() ;
        for (Object path : aPath) {
            Object id = getProperty(path, theIdField) ;
            Object parentId = getProperty(path, theParentIdField) ;
            Object name = getProperty(path, theNameField) ;
            j.object().key("id").value(id) ;
            j.key("parentId").value(parentId) ;
            j.key("name").value(name) ;
            j.endObject() ;
        }
        j.endArray() ;


        // Строки
        j.key("rows").array() ; //.key("rows") ;

        Object lastId = null ;
        Object lastParentId = null ;
        Object firstId = null ;
        Object firstParentId = null ;
        boolean firstPassed = false ;


        for (Object row : aList) {
            Object id = getProperty(row, theIdField) ;
            Object parentId = getProperty(row, theParentIdField) ;
            if (CAN_TRACE) LOG.trace("id = " + id);

            lastId = id ;
            lastParentId = parentId ;
            if(!firstPassed) {
               firstId = id ;
                firstParentId = parentId ;
                firstPassed = true ;
            }

            j.object().key("id").value(id) ;
            j.key("cols") ;
            j.array() ;
            for (TreeTableColumn column : theColumns) {
                Object value = PropertyUtils.getProperty(row, column.getProperty()) ;
                j.value(value) ;
            }
            j.endArray() ;
            j.key("parentId").value(parentId).endObject() ;
        }
        j.endArray() ;

        j.key("firstId").value(firstId) ;
        j.key("firstParentId").value(firstParentId) ;

        j.key("lastId").value(lastId) ;
        j.key("lastParentId").value(lastParentId) ;
        j.endObject() ;

//        System.out.println(XML.toString(j, "asdf")) ;
    }

    /**
     * Use playJson()
     * @deprecated
     * @param out
     * @param aList
     * @throws Exception
     */
    public void play(PrintWriter out, Collection aList) throws Exception {
        out.println("<div id='"+theName+"Dialog' class='dialog'>") ;
        out.println("<h2>"+theTitle+"</h2>") ;
        out.println("<div class='rootPane'>") ;
        out.println("<table class='tabview sel tableArrow'>") ;
        out.println("<tr>") ;
        for (TreeTableColumn column : theColumns) {
            out.print("<th>") ;
            out.print(column.getTitle()) ;
            out.print("</th>") ;
        }
        out.println("</tr>") ;
        String funcName = theName + "123" ;     // todo
        Object lastId = null ;
        Object lastParentId = null ;
        Object fistId = null ;
        Object firstParentId = null ;
        boolean firstPassed = false ;
        for (Object row : aList) {

            Object id = getProperty(row, theIdField) ;
            lastId = id ;
            Object parentId = getProperty(row, theParentIdField) ;
            lastParentId = parentId ;
            String td = createTd(id) ;
            if(!firstPassed) {
                fistId = id ;
                firstParentId = parentId ;
                firstPassed = true ;
            }

            out.print("<tr onclick=''>") ;
            for (TreeTableColumn column : theColumns) {
                //StringBuilder td = new StringBuilder("<td onclick=\"").append(funcName).append("('").append("123").append("')\"").append(">");
                out.print(td) ;
                try {
                    out.print(PropertyUtils.getProperty(row, column.getProperty())) ;
                } catch (Exception e) {
                    LOG.error(e);
                    out.print(e) ;
                }
                out.print("</td>") ;
            }
            out.println("</tr>") ;
        }
        out.println("</table>") ;

        if(lastId!=null) {
            // previous
            out.println(createLink("Назад",fistId, firstParentId, false, null)) ;
        }
        // next
        if(lastId!=null) {
            out.println(createLink("Вперед", lastId, lastParentId, true, null)) ;
        }

        // search
        if(lastId!=null) {
            out.println(createLink("Вперед", lastId, lastParentId, true, "Астрах")) ;
        }

        out.println("<script type='text/javascript'>") ;
        out.println("  ") ;
        out.println("theTableArrow = new tablearrow.TableArrow('tab1') ;") ;
        out.println("</script>") ;
        out.println("</div> <!-- rootPane -->") ;
        out.println("</div> <!-- dialog   -->") ;
    }

    private String createLink(String aName, Object aId, Object aParentId, boolean aForward, String aSearchString) {
        StringBuilder sb = new StringBuilder();
        sb.append("<a href=\"javascript:") ;
        sb.append(createCall(aId, aParentId, aForward, aSearchString)) ;
        sb.append("\">") ;
        sb.append(aName) ;
        sb.append("</a>") ;
        return sb.toString();
    }
    private String createArgument(Object aValue) {
        StringBuilder sb = new StringBuilder();
        if(aValue!=null) {
            sb.append("\'").append(aValue).append('\'')  ;
        } else {
            sb.append("null") ;
        }
        return sb.toString() ;
    }


    private String getUpperCaseName() {
        return Character.toUpperCase(theName.charAt(0)) + theName.substring(1) ;
    }

    private String createCall(Object aId, Object aParentId, boolean aForward, String aSearchString) {
        StringBuilder sb = new StringBuilder();
        sb.append("the").append(getUpperCaseName()).append("TreeTable.").append(aSearchString!=null?"search":"go").append("(") ;
        sb.append(createArgument(aId)) ;
        sb.append(", ") ;
        sb.append(createArgument(aParentId)) ;
        sb.append(", ").append(aForward) ;
        sb.append(", ") ;
        sb.append(createArgument(aSearchString)) ;
        sb.append(")") ;
        return sb.toString() ;
    }
    private String createTd(Object aId) {
        StringBuilder sb = new StringBuilder();
        sb.append("<td onclick=\"") ;
        sb.append(createCall(null, aId, true, null)) ;
        sb.append("\">") ;
        return sb.toString() ;
    }

    private Object getProperty(Object aRow, String aPropertyName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return PropertyUtils.getProperty(aRow, aPropertyName) ;
    }


    private LinkedList<TreeTableColumn> theColumns = new LinkedList<TreeTableColumn>();
    private final String theName ;
    private final String theIdField ;
    private final String theParentIdField ;
    private final String theTitle ;
    private final String theNameField ;

    public Object getId(Object aObject) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return getProperty(aObject, theIdField) ;
    }

    public static void main(String[] args) throws Exception {
        ArrayList list = new ArrayList();
        list.add(new Point(1,2)) ;
        list.add(new Point(1,2)) ;
        list.add(new Point(1,2)) ;
        list.add(new Point(1,2)) ;
        list.add(new Point(1,2)) ;

        TreeTablePlayer player = new TreeTablePlayer("asdf","asdf","x", "y", "x");
        player.addColumn("Hello", "x");
        player.addColumn("Hello", "y");
        OutputStreamWriter out = new OutputStreamWriter(System.out);
        PrintWriter out2 = new PrintWriter(out);
        player.playJSON(out2, list, list );
        out.close() ;

    }


}
