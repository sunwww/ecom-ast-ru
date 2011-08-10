package ru.nuzmsh.web.tags;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.nuzmsh.util.StringUtil;

/**
 * @jsp.tag         name = "tableNotEmpty"
 *          display-name = "Выводит содержимое, если есть данные в таблице"
 *          body-content = "scriptless"
 *           description = "Выводит содержимое, если есть данные в таблице. Использовать толко внутри msh:table"
 */
public class TableNotEmptyTag extends AbstractGuidSimpleSupportTag {

    private final static Log LOG = LogFactory.getLog(TableNotEmptyTag.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;


    /**
     * Коллекция
     *
     * @jsp.attribute   description = "Коллекция"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    public void doTag() throws JspException, IOException {
    	// IDE
    	printIdeStart();
    	
        if(!isEmpty()) {
            JspWriter out = getJspContext().getOut() ;
            getJspBody().invoke(out);
        }
        printIdeEnd() ;
    }

    boolean isEmpty() {
        boolean ret  ;
        if (CAN_TRACE) LOG.trace("isEmpty(): theName = " + theName);
        if(!StringUtil.isNullOrEmpty(theName)) {
            Collection col = (Collection) getJspContext().findAttribute(theName) ;
            if (CAN_TRACE) LOG.trace("isEmpty(): col = " + col);
            ret = col==null || col.isEmpty();
        } else {
            TableTag table = (TableTag) getParent() ;
            if (CAN_TRACE) LOG.trace("isEmpty(): table = " + table);
            if (CAN_TRACE) LOG.trace("isEmpty(): table = " + table.isEmpty());
            ret = table.isEmpty() ;
        }
        if (CAN_TRACE) LOG.trace("isEmpty() : ret = " + ret);
        return ret ;
    }

    /** Коллекция */
    private String theName = null ;

}
