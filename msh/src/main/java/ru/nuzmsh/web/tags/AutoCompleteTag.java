package ru.nuzmsh.web.tags;


import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ecs.Element;
import org.apache.ecs.xhtml.div;
import org.apache.ecs.xhtml.input;
import org.apache.log4j.Logger;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.web.tags.helper.JavaScriptContext;
import ru.nuzmsh.web.util.StripExceptionHelper;
import ru.nuzmsh.web.vochelper.VocHelperDelegate;
import ru.nuzmsh.web.vochelper.VocHelperLocateException;
import ru.nuzmsh.web.xhtml.Xa;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @jsp.tag name="autoComplete"
 *          display-name="Auto complete Field"
 *          body-content="empty"
 *          description="Autocomplete field JSP tag."
 */
public class AutoCompleteTag extends AbstractFieldTag {

    private static final Logger LOG = Logger.getLogger(AutoCompleteTag.class) ;

    /** 
	 * Показывать идентификатор в названии
	 * @jsp.attribute   description = "Показывать идентификатор в названии"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public boolean getShowId() {
		return theShowId;
	}

	public void setShowId(boolean aShowId) {
		theShowId = aShowId;
	}

	/** Показывать идентификатор в названии */
	private boolean theShowId = false;
	
    /** 
	 * Идентификатор родителя
	 * @jsp.attribute   description = "Идентификатор родителя"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public String getParentId() {
		return theParentId;
	}

	public void setParentId(String aParentId) {
		theParentId = aParentId;
	}


	/** 
	 * Родительский autocomplete
	 * @jsp.attribute   description = "Родительский autocomplete"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public String getParentAutocomplete() {
		return theParentAutocomplete;
	}

	public void setParentAutocomplete(String aParentAutocomplete) {
		theParentAutocomplete = aParentAutocomplete;
	}

    /**
     * Переход на просмотр
     * @jsp.attribute   description = "Переход на просмотр"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public String getViewAction() { return theViewAction ; }
    public void setViewAction(String aViewAction) { theViewAction = aViewAction ; }

    /** 
     * short просморт 
     * @jsp.attribute   description = "просмотр"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */

	public String getShortViewAction() {return theShortViewAction;}
	public void setShortViewAction(String aShortViewAction) {theShortViewAction = aShortViewAction;}

	/** short просморт */
	private String theShortViewAction;
    /** Переход на просмотр */
    private String theViewAction ;

    /**
     * Название справочника
     * @jsp.attribute   description="Название справочника"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getVocName() throws Exception {
        return theVocName!=null && !"".equals(theVocName) ? theVocName : super.getVocName() ;
    }

    public void setVocName(String aVocName) { theVocName = aVocName ; }

    /** Название справочника */
    private String theVocName = null ;


    protected Element getFieldElement() throws JspException {

        String vocname = "ERROR" ;
        try {
            vocname = getVocName() ;
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            e.printStackTrace();
        }
        String id = getFormattedValue() ;
        String name ;
        try {
            name = VocHelperDelegate.locateVocHelper().getNameById(pageContext, id, vocname, theParentId!=null ? new VocAdditional(getParentIdValue(pageContext)): null);
            if(theShowId) {
            	name = id + " "+name ;
            }
        } catch (Exception e) {
            LOG.warn("While getting name by id='"+id+"', from voc='"+vocname+"'", e);
            name = new StripExceptionHelper().strip(e) ;
        }
        if(name==null) name="" ;

        String fieldId = getProperty() ;
        String nameId  = fieldId + "Name" ;
        String divId = fieldId + "Div" ;
        String autoc = fieldId.replace('.', '_') +"Autocomplete" ;

        if(isViewOnly()) {
            if(!StringUtil.isNullOrEmpty(theViewAction) && !StringUtil.isNullOrEmpty(id)) {
            	div d = new div() ;
            	if (!StringUtil.isNullOrEmpty(theShortViewAction)) {
            		Xa a = new Xa() ;
            		a.setHref("javascript:void(0) ;") ;
            		StringBuilder onclick = new StringBuilder().append("getDefinition('") ; 
            		onclick.append(theShortViewAction) ;
            		if (theShortViewAction.indexOf('?')==-1) {onclick.append("?id=") ;} else {onclick.append("&id=") ;}
            		onclick.append(id).append("',event); ") ;
            		a.setOnClick(onclick.toString()) ;
            		a.setTagText("<img src='/skin/images/main/view1.png' alt='Просмотр' title='Просмотр' height='14' width='14'/>") ;
            		d.addElement(a) ;
            	}
                Xa a = new Xa();
                a.setHref(theViewAction+"?id="+id) ;
                a.setTagText(name) ;
            	
                d.addElement(a) ;
            	input inputField = new input();
                inputField.setName(getProperty());
                inputField.setID(getProperty());
                inputField.setValue(getFormattedValue());
                inputField.setReadOnly(true);
                inputField.setType("hidden");
                d.addElement(inputField);
                return d ;
            } else {
            	return getViewOnlyElement(name) ;
            }
        } else {

            StringBuilder styleClass = new StringBuilder("autocomplete");
            if(getHorizontalFill()) {
                styleClass.append(" horizontalFill") ;
            }
            if(getSize()>=50) {
                styleClass.append(" maxHorizontalSize") ;
            }
            try {
                if(isFieldRequired()) {
                    styleClass.append(" required") ;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            JavaScriptContext js = JavaScriptContext.getContext(pageContext, this) ;
            js.println("var " + autoc + " = new msh_autocomplete.Autocomplete() ;") ;
            js.println(autoc + ".setUrl('simpleVocAutocomplete/" + vocname + "') ;") ;
            js.println(autoc + ".setIdFieldId('" + fieldId + "') ;") ;
            js.println(autoc + ".setNameFieldId('" + nameId + "') ;") ;
            js.println(autoc + ".setDivId('" + divId + "') ;") ;
            js.println(autoc + ".setVocKey('" + vocname + "') ;") ;
            js.println(autoc + ".setVocTitle('" + getLabel() + "') ;") ;
            js.println(autoc + ".build() ;") ;
            
            if(!StringUtil.isNullOrEmpty(theParentId)) {
                js.println(autoc,".setParentId('",getParentIdValue(pageContext),"') ;") ;
            }
            if(!StringUtil.isNullOrEmpty(theParentAutocomplete)) {
                js.println(autoc,".setParent(",theParentAutocomplete,"Autocomplete) ;") ;
            }
            
            if(theShowId) {
                js.println(autoc,".setShowIdInName(true) ;") ;
            }

            String sb = String.format("<input type='hidden' size='1'  name='%1$s' value='%2$s' id='%1$s'/>", getProperty(), id) +
                    String.format("<input title='" + vocname + "' type='text' name='%1$sName' value='%2$s' id='%1$sName' size='%3$s' class='%4$s'/>"
                            , getProperty(), name, getSize() + "", styleClass) +
                    "<div id='" + divId + "'><span></span></div>";
            return new div(sb) ;
        }
    }

    private String getParentIdValue(PageContext aPageContext)  {
    	
    	
        HttpServletRequest request = (HttpServletRequest) aPageContext.getRequest() ;
    	String value = request.getParameter(theParentId) ;
    	if(value!=null) return value ;
    	
    	Object obj = aPageContext.findAttribute(theParentId) ;
    	if(obj!=null) {
    		return obj.toString() ;
    	} else {
        	int pos = theParentId.indexOf('.') ;
        	if(pos>0) {
        		String formName = theParentId.substring(0,pos) ;
        		Object form = aPageContext.findAttribute(formName) ;
        		String property = theParentId.substring(pos+1) ;
        		try {
            		obj = PropertyUtils.getProperty(form, property) ;
        		} catch(Exception e) {
        			obj = e.getMessage() ;
        			LOG.error("Ошибка получение parentId "+theParentId,e) ;
        		}
        	}
    	}
    	return obj!=null ? obj.toString() : theParentId ;
    }
    
    protected Element getFieldElementWindow() throws JspException {
        String vocname = "ERROR" ;
        try {
            vocname = getVocName() ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        String id = getFormattedValue() ;
        String name = null ;
        try {
            name = VocHelperDelegate.locateVocHelper().getNameById(pageContext, id, vocname,theParentId!=null ? new VocAdditional(theParentId): null);
        } catch (VocHelperLocateException e) {
            name = e.getMessage() ;
        }
        if(name==null) name="" ;

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<input type='hidden' name='%1$s' value='%2$s' id='%1$s'/>", getProperty(), id)) ;
        sb.append("<table border='0'><tr><td>") ;
        sb.append(String.format("<input readOnly='true' type='text' name='%1$sName' value='%2$s' id='%1$sName' size='%3$s'/>"
                , getProperty(), name, getSize()+"")) ;
        sb.append("</td><td>") ;
        sb.append(String.format("<input type='button' value='▼' onclick=\"showAutocompleteWindow('%1$s', '%2$sName', '%3$s', '%4$s')\" />"
                , getProperty(), getProperty(), id , vocname)) ;
        sb.append("</td></tr></table>") ;
        div d = new div(sb.toString());
        d.setClass("autocompleteSelect") ;
        return d;
    }

    protected String getUserInputFieldName() {
        return getProperty()+"Name" ;
    }

	/** Идентификатор родителя */
	private String theParentId;
	/** Родительский autocomplete */
	private String theParentAutocomplete = null ;

}