package ru.nuzmsh.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import ru.nuzmsh.web.util.IdeTagHelper;

/**
 * @jsp.tag name="tableColumn"
 *          display-name="TableColumn"
 *          body-content="empty"
 *          description="Table Column JSP tag."
 */
public class TableColumnTag extends AbstractGuidSupportTag {

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
     * @jsp.attribute   description="Название колонки"
     *                  required="false"
     *                  rtexprvalue="true"
     *
     * */
    public String getColumnName() { return theColumnName ; }
    public void setColumnName(String aColumnName) { theColumnName = aColumnName ; }

    /** Является ли поле идентификатором
     * @jsp.attribute   description="Является ли поле идентификатором"
     *                  required="false"
     *                  rtexprvalue="true"
     * */
    public boolean isIdentificator() { return theIdentificator ; }
    public void setIdentificator(boolean aIdentificator) { theIdentificator = aIdentificator ; }

    /** Является ли поле идентификатором */
    private boolean theIdentificator = false ;
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
    		IdeTagHelper.getInstance().showException(this, new Exception("TableColumn должен быть внутри тэга Table"), pageContext);
    	}
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        return EVAL_PAGE ;
    }
    
    /** Получить итог по полю
     * @jsp.attribute   description="Получить итог по полю"
     *                  required="false"
     *                  rtexprvalue="true"
     * */
	public boolean getIsCalcAmount() {
		return theIsCalcAmount;
	}

	public void setIsCalcAmount(boolean aIsCalcAmount) {
		theIsCalcAmount = aIsCalcAmount;
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
	/** String */
	private boolean theIsCalcAmount;
    /** Название колонки */
    private String theColumnName ;
    /** Название свойства */
    private String theProperty ;
    /** CSS Класс */
    private String theCssClass = null ;
	/** Роль *
     * @jsp.attribute   description="Роль"
     *                  required="false"
     *                  rtexprvalue="true"
     */
 	public String getRole() {return theRole;}
 	public void setRole(String aRole) {theRole = aRole;}

 	/** Роль */
 	private String theRole;


    /**
     * Ширина столбца
     * @jsp.attribute   description="Ширина столбца"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getWidth() {return theWidth;}
    public void setWidth(String aWidth) {theWidth = aWidth;}

    /** Ширина столбца */
    private String theWidth;

    /** Является ли столбец hidden
     * @jsp.attribute   description="Является ли столбец hidden"
     *                  required="false"
     *                  rtexprvalue="true"
     * */
    public boolean getHidden() { return theHidden ; }
    public void setHidden(boolean aHidden) { theHidden = aHidden ; }

    /** Является ли поле идентификатором */
    private boolean theHidden = false ;
}