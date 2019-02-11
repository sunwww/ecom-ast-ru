package ru.nuzmsh.web.tags;

import org.apache.log4j.Logger;
import ru.nuzmsh.util.StringUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.util.Collection;

/**
 * @jsp.tag         name = "tableNotEmpty"
 *          display-name = "Выводит содержимое, если есть данные в таблице"
 *          body-content = "scriptless"
 *           description = "Выводит содержимое, если есть данные в таблице. Использовать толко внутри msh:table"
 */
public class TableNotEmptyTag extends AbstractGuidSimpleSupportTag {

    private static final Logger LOG = Logger.getLogger(TableNotEmptyTag.class) ;
    private static final boolean CAN_TRACE = LOG.isDebugEnabled() ;


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
        if (CAN_TRACE) LOG.info("isEmpty(): theName = " + theName);
        if(!StringUtil.isNullOrEmpty(theName)) {
            Collection col = (Collection) getJspContext().findAttribute(theName) ;
            if (CAN_TRACE) LOG.info("isEmpty(): col = " + col);
            ret = col==null || col.isEmpty();
        } else {
            TableTag table = (TableTag) getParent() ;
            if (CAN_TRACE) LOG.info("isEmpty(): table = " + table);
            if (CAN_TRACE) LOG.info("isEmpty(): table = " + table.isEmpty());
            ret = table.isEmpty() ;
        }
        if (CAN_TRACE) LOG.info("isEmpty() : ret = " + ret);
        return ret ;
    }

    /** Коллекция */
    private String theName = null ;

}
