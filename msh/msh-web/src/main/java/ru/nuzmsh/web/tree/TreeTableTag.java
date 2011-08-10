package ru.nuzmsh.web.tree;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.web.tree.player.TreeTablePlayer;

/**
 * Дерево + таблица
 *
 * @jsp.tag
 *          name = "treeTable"
 *  display-name = "Дерево + таблица"
 *  body-content = "scriptless"
 *   description = "Дерево + таблица"
 */
public class TreeTableTag extends SimpleTagSupport {

    public void doTag() throws JspException, IOException {
        thePlayer = new TreeTablePlayer(theTreeModel, theTitle, theIdField, theParentIdField, theNameField) ;
        getJspBody().invoke(getJspContext().getOut());
        thePlayer.save(getJspContext()) ;
    }

    /**
     * В какое поле сохранять значение
     * @jsp.attribute   description = "В какое поле сохранять значение"
     *                     required = "true"
     *                  rtexprvalue = "true"
     */
    public String getInput() { return theInput ; }
    public void setInput(String aInput) { theInput = aInput ; }

    /**
     * Поля, содержащие название узла
     * @jsp.attribute   description = "Поля, содержащие название узла"
     *                     required = "true"
     *                  rtexprvalue = "true"
     */
    public String getNameField() { return theNameField ; }
    public void setNameField(String aNameField) { theNameField = aNameField ; }

    /** Поля, содержащие название узла */
    private String theNameField ;
    /**
     * Название дерева
     * @jsp.attribute   description = "Название дерева"
     *                     required = "true"
     *                  rtexprvalue = "true"
     */
    public String getTitle() { return theTitle ; }
    public void setTitle(String aTitle) { theTitle = aTitle ; }

    /**
     * Установить компонент
     * @jsp.attribute   description = "Установить компонент "
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public String getInstallTo() { return theInstallTo ; }
    public void setInstallTo(String aInstallTo) { theInstallTo = aInstallTo ; }

    /** Установить компонент  */
    private String theInstallTo ;
    /**
     * Поле с идентификатором
     * @jsp.attribute   description = "Поле с идентификатором"
     *                     required = "true"
     *                  rtexprvalue = "true"
     */
    public String getIdField() { return theIdField ; }
    public void setIdField(String aIdField) { theIdField = aIdField ; }

    /**
     * ИД Родителя
     * @jsp.attribute   description = "ИД Родителя"
     *                     required = "true"
     *                  rtexprvalue = "true"
     */
    public String getParentIdField() { return theParentIdField ; }
    public void setParentIdField(String aParentIdField) { theParentIdField = aParentIdField ; }

    /**
     * TreeModel в session
     *
     * @jsp.attribute   description = "Tree"
     *                     required = "true"
     *                  rtexprvalue = "true"
     *
     * */
    public String getTreeModel() { return theTreeModel ; }
    public void setTreeModel(String aTreeModel) { theTreeModel = aTreeModel ; }

    /** TreeModel в session */
    private String theTreeModel ;
    /** Поле с идентификатором */
    private String theIdField ;

    public static TreeTablePlayer findTreeTablePlayer(JspTag aTag) {
        if(aTag==null) throw new IllegalStateException("Не найден TreeTablePlayer") ;
        if(aTag instanceof TreeTableTag) {
            TreeTableTag treeTableTag = (TreeTableTag) aTag ;
            return treeTableTag.thePlayer ;
        } else if(aTag instanceof SimpleTag) {
            SimpleTag simpleTag = (SimpleTag) aTag ;
            return findTreeTablePlayer(simpleTag.getParent()) ;
        } else {
            throw new IllegalStateException("Неизвестный тип "+aTag.getClass()) ;
        }
    }

    /** ИД Родителя */
    private String theParentIdField ;
    private TreeTablePlayer thePlayer = null ;
    /** Название дерева */
    private String theTitle ;
    /** В какое поле сохранять значение */
    private String theInput ;
    
}
