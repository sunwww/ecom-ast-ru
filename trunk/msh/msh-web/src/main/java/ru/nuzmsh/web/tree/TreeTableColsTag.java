package ru.nuzmsh.web.tree;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Колонки для TreeTable
 *
 * @jsp.tag
 *          name = "treeTableCols"
 *  display-name = "Колонки для TreeTable"
 *  body-content = "scriptless"
 *   description = "Колонки для TreeTable"
 */
public class TreeTableColsTag extends SimpleTagSupport {

    public void doTag() throws JspException, IOException {
        getJspBody().invoke(getJspContext().getOut());
    }
}
