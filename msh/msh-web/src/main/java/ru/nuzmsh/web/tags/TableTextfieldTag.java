package ru.nuzmsh.web.tags;

import ru.nuzmsh.web.util.IdeTagHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

/**
 * @jsp.tag name="tableTextfield"
 *          display-name="TableTextfield"
 *          body-content="empty"
 *          description="Table Textfield JSP tag."
 */
public class TableTextfieldTag extends AbstractGuidSupportTag {

    /**
     * CSS Класс
     * @jsp.attribute   description="CSS Класс"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getCssClass() { return theCssClass ; }
    public void setCssClass(String aCssClass) { theCssClass = aCssClass ; }

    /** Название колонки
     *
     * @jsp.attribute   description="Название кнопки"
     *                  required="false"
     *                  rtexprvalue="true"
     *
     * */
    public String getTextfieldName() { return theTextfieldName ; }
    public void setTextfieldName(String aTextfieldName) { theTextfieldName = aTextfieldName ; }

    /** Название колонки
     *
     * @jsp.attribute   description="Название сокращенное кнопки"
     *                  required="false"
     *                  rtexprvalue="true"
     *
     * */
    public String getTextfieldShortName() { return theTextfieldShortName ; }
    public void setTextfieldShortName(String aTextfieldShortName) { theTextfieldShortName = aTextfieldShortName ; }



    /**
     * Название свойства
     * @jsp.attribute   description="Название свойства"
     *                  required="true"
     *                  rtexprvalue="true"
     *
     * */
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }

    public int doStartTag() throws JspException {
        Tag parent = getParent() ;
        if(parent instanceof TableTag) {
            TableTag tableTag = (TableTag) parent ;
            tableTag.add(this,getRole());
        } else {
            IdeTagHelper.getInstance().showException(this, new Exception("TableTextfield должен быть внутри тэга Table"), pageContext);
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        return EVAL_PAGE ;
    }


    /** Дополнительные параметры
     * @jsp.attribute   description="Дополнительные параметры"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getAddParam() {return theAddParam;}
    public void setAddParam(String aAddParam) {theAddParam = aAddParam;}

    /** Дополнительные параметры */
    private String theAddParam;
    /** Дополнительные параметры
     * @jsp.attribute   description="Функция кнопки"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getTextfieldFunction() {return theTextfieldFunction;}
    public void setTextfieldFunction(String aTextfieldFunction) {theTextfieldFunction = aTextfieldFunction;}

    /** Дополнительные параметры */
    private String theTextfieldFunction;

    /** Роль *
     * @jsp.attribute   description="Роль"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getRole() {return theRole;}
    public void setRole(String aRole) {theRole = aRole;}

    /** Роль */
    private String theRole;
    /** Название колонки */
    private String theTextfieldName ;
    /** Название сокращенное колонки */
    private String theTextfieldShortName ;
    /** Название свойства */
    private String theProperty ;
    /** CSS Класс */
    private String theCssClass = null ;


    /**
     * Не выводить шапку таблицы
     * @jsp.attribute   description = "Не выводить, если нет значения"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public boolean getHideIfEmpty() {return theHideIfEmpty;}
    public void setHideIfEmpty(boolean aHideIfEmpty) {theHideIfEmpty = aHideIfEmpty;}

    /** Не выводить шапку таблицы */
    private boolean theHideIfEmpty = false;
}