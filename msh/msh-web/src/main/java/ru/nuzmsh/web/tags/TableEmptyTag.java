package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @jsp.tag           name = "tableEmpty"
 *            display-name = "Выводит содержимое, если нет данных в таблице"
 *            body-content = "scriptless"
 *             description = "Выводит содержимое, если нет данных в таблице"
 */
public class TableEmptyTag extends TableNotEmptyTag {
    @Deprecated
    public void doTag() throws JspException, IOException {
        if(isEmpty()) {
            JspWriter out = getJspContext().getOut() ;
            getJspBody().invoke(out);
        }
    }

}
