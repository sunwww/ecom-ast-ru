package ru.nuzmsh.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import ru.nuzmsh.web.util.IdeTagHelper;

/**
 * @jsp.tag name="tableButton"
 *          display-name="TableButton"
 *          body-content="empty"
 *          description="Table Button JSP tag."
 */
public class TableButtonTag extends AbstractGuidSupportTag {

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
    public String getButtonName() { return theButtonName ; }
    public void setButtonName(String aButtonName) { theButtonName = aButtonName ; }

    /** Название колонки
     *
     * @jsp.attribute   description="Название сокращенное кнопки"
     *                  required="false"
     *                  rtexprvalue="true"
     *
     * */
    public String getButtonShortName() { return theButtonShortName ; }
    public void setButtonShortName(String aButtonShortName) { theButtonShortName = aButtonShortName ; }

    

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
    		IdeTagHelper.getInstance().showException(this, new Exception("TableButton должен быть внутри тэга Table"), pageContext);
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
	public String getButtonFunction() {return theButtonFunction;}
	public void setButtonFunction(String aButtonFunction) {theButtonFunction = aButtonFunction;}

	/** Дополнительные параметры */
	private String theButtonFunction;
	
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
	private String theButtonName ;
    /** Название сокращенное колонки */
    private String theButtonShortName ;
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
