package ru.nuzmsh.web.tree;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.web.tree.player.TreeTablePlayer;

/**
 *
 * Колонка для TreeTable
 *
 * @jsp.tag
 *          name = "treeTableColumn"
 *  display-name = "Колонка для TreeTable"
 *  body-content = "scriptless"
 *   description = "Колонка для TreeTable"
 *
 */
public class TreeTableColumnTag extends SimpleTagSupport {


    public void doTag() throws JspException, IOException {
        TreeTablePlayer player = TreeTableTag.findTreeTablePlayer(this) ;
        player.addColumn(theTitle, theProperty);
    }

    /**
     * Название колонки
     * @jsp.attribute   description = "Название колонки"
     *                     required = "true"
     *                  rtexprvalue = "true"
     */
    public String getTitle() { return theTitle ; }
    public void setTitle(String aTitle) { theTitle = aTitle ; }

    /**
     * Свойство
     *
     * @jsp.attribute   description = "Свойство"
     *                     required = "true"
     *                  rtexprvalue = "true"
     *
     */
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }

    /** Свойство */
    private String theProperty ;
    /** Название колонки */
    private String theTitle ;
}
