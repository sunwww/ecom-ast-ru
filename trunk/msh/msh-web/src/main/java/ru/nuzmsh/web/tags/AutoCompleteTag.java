package ru.nuzmsh.web.tags;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ecs.Element;
import org.apache.ecs.xhtml.div;
import org.apache.ecs.xhtml.input;
import org.apache.ecs.xhtml.script;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.web.tags.helper.JavaScriptContext;
import ru.nuzmsh.web.util.StripExceptionHelper;
import ru.nuzmsh.web.vochelper.VocHelperDelegate;
import ru.nuzmsh.web.vochelper.VocHelperLocateException;
import ru.nuzmsh.web.xhtml.Xa;

/**
 * @jsp.tag name="autoComplete"
 *          display-name="Auto complete Field"
 *          body-content="empty"
 *          description="Autocomplete field JSP tag."
 */
public class AutoCompleteTag extends AbstractFieldTag {

    private final static Log LOG = LogFactory.getLog(AutoCompleteTag.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;

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
            		a.setOnClick(new StringBuilder().append("getDefinition('")
            				.append(theShortViewAction).append("?id=").append(id).append("',event); ").toString()) ;
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
//                span s = new span(name);
//                s.setClass("viewOnlyAutocomplete") ;
//                return s ;
            
        } else {

            StringBuffer styleClass = new StringBuffer("autocomplete");
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
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
//            String horFill = getHorizontalFill() ? "horizontalFill" : "" ;

            StringBuffer sb = new StringBuffer();

            sb.append(String.format("<input type='hidden' size='1'  name='%1$s' value='%2$s' id='%1$s'/>", getProperty(), id)) ;
            sb.append(String.format(new StringBuilder().append("<input title='").append(vocname).append("' type='text' name='%1$sName' value='%2$s' id='%1$sName' size='%3$s' class='%4$s'/>").toString()
                    , getProperty(), name, getSize()+"", styleClass)) ;

            sb.append("<div id='"+divId+"'><span></span></div>") ;
            JavaScriptContext js = JavaScriptContext.getContext(pageContext, this) ;
//            sb.append("$('"+getProperty()+"').focus = setFocusOnField('"+getProperty()+"Name') ;\n") ;
            js.println(new StringBuilder().append("var ").append(autoc).append(" = new msh_autocomplete.Autocomplete() ;").toString()) ;
            js.println(new StringBuilder().append(autoc).append(".setUrl('simpleVocAutocomplete/").append(vocname).append("') ;").toString()) ;
            js.println(new StringBuilder().append(autoc).append(".setIdFieldId('").append(fieldId).append("') ;").toString()) ;
            js.println(new StringBuilder().append(autoc).append(".setNameFieldId('").append(nameId).append("') ;").toString()) ;
            js.println(new StringBuilder().append(autoc).append(".setDivId('").append(divId).append("') ;").toString()) ;
            js.println(new StringBuilder().append(autoc).append(".setVocKey('").append(vocname).append("') ;").toString()) ;
            js.println(new StringBuilder().append(autoc).append(".setVocTitle('").append(getLabel()).append("') ;").toString()) ;
            js.println(new StringBuilder().append(autoc).append(".build() ;").toString()) ;
            
            if(!StringUtil.isNullOrEmpty(theParentId)) {
                js.println(autoc,".setParentId('",getParentIdValue(pageContext),"') ;") ;
            }
            if(!StringUtil.isNullOrEmpty(theParentAutocomplete)) {
                js.println(autoc,".setParent(",theParentAutocomplete,"Autocomplete) ;") ;
            }
            
            if(theShowId) {
                js.println(autoc,".setShowIdInName(true) ;") ;
            }

            return new div(sb.toString()) ;
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

        StringBuffer sb = new StringBuffer();
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
        if(true) return d ;


        // идентификатор
        input hiddenField = new input();
        hiddenField.setName(getProperty());
        hiddenField.setValue(getFormattedValue());
        hiddenField.setID(getProperty());
        hiddenField.setType("hidden") ;

        // название
        input inputField = new input();
        inputField.setName(getProperty()+"Name");
        inputField.setID(getProperty()+"Name");
        // inputField.setValue(getFormattedValue());
        inputField.setType("text");
        inputField.setTitle(getLabel() + getDataFieldName() +" "+vocname) ;
        inputField.setSize(getSize()) ;
        String styleClass = "form-autocomplete" ;
        if(getHorizontalFill()) {
            styleClass += " horizontalFilled" ;
        }
        inputField.setClass(styleClass) ;
//        if(getHorizontalFill()) {
//            inputField.setSize(50) ;
//        }


        //div d = new div();
        d.addElement(hiddenField) ;



        // showAutocomplete (aNameField, aIdField, aUrl)
        script javaScript = new script(
                String.format("showAutocomplete('%1$s', '%2$s', '%3$s') ;"
                        , getProperty()
                        , getUserInputFieldName()
                        , "vocAutocomplete/"+vocname)
        );
        javaScript.setType("text/javascript") ;


        d.addElement(javaScript) ;


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
