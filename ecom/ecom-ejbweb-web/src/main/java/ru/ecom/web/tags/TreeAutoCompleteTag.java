package ru.ecom.web.tags;

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
import org.apache.ecs.xhtml.span;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.web.tags.AbstractFieldTag;
import ru.nuzmsh.web.tags.helper.JavaScriptContext;
import ru.nuzmsh.web.util.StripExceptionHelper;
import ru.nuzmsh.web.vochelper.VocHelperDelegate;
import ru.nuzmsh.web.vochelper.VocHelperLocateException;
import ru.nuzmsh.web.xhtml.Xa;

/**
 * @jsp.tag name="treeAutoComplete"
 *          display-name="Tree Auto complete Field"
 *          body-content="empty"
 *          description="Tree Autocomplete field JSP tag."
 */public class TreeAutoCompleteTag extends AbstractFieldTag {

    private final static Log LOG = LogFactory.getLog(TreeAutoCompleteTag.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;


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
        String nameId  = getUserInputFieldName()  ;
        String nameParentId = fieldId+"ParentName" ;
        String divId = fieldId + "Div" ;
        String autoc = fieldId.replace('.', '_') +"TreeAutocomplete" ;

        if(isViewOnly()) {
            if(!StringUtil.isNullOrEmpty(theViewAction) && !StringUtil.isNullOrEmpty(id)) {
                Xa a = new Xa();
                a.setHref(theViewAction+"?id="+id) ;
                a.setTagText(name) ;
            	div d = new div() ;
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
            StringBuffer styleClass = new StringBuffer("autocomplete");
            if(getHorizontalFill()) {
                styleClass.append(" horizontalFill") ;
            }
            if(getSize()>=50) {
                styleClass.append(" maxHorizontalSize") ;
            }
            try {
                /* todo
            	if(isFieldRequired()) {
                    styleClass.append(" required") ;
                }*/
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
           
            div divDefault = new div() ;
//            String horFill = getHorizontalFill() ? "horizontalFill" : "" ;
            input inputFieldId = new input() ;
            input inputFieldName = new input() ;
            input inputFieldParentId = new input() ;
            
            inputFieldId.setType("hidden") ; inputFieldId.setSize(1) ; inputFieldId.setValue(id) ; 
            inputFieldId.setName(getProperty()) ; inputFieldId.setID(getProperty()) ; 
            inputFieldId.addAttribute("autocomplete", "off") ;
            
            inputFieldParentId.setType("hidden") ; inputFieldParentId.setSize(1) ; 
            inputFieldParentId.setName(nameParentId) ; inputFieldParentId.setID(nameParentId) ; 
            inputFieldParentId.addAttribute("autocomplete", "off") ;
            
            inputFieldName.setType("text") ;
            inputFieldName.addAttribute("autocomplete", "off") ;
            inputFieldName.setName(nameId) ; inputFieldName.setID(nameId) ;
            inputFieldName.setValue(name) ; inputFieldName.setSize(getSize()) ;
            inputFieldName.setClass(styleClass.toString()) ;
            
            div divView =new div() ;
            divView.setID(divId) ;
            divView.addElement(new span()) ;
           
          
            StringBuffer sb = new StringBuffer();

            sb.append(String.format("<input type='hidden' size='1'  name='%1$s' value='%2$s' id='%1$s'/>", getProperty(), id)) ;
            sb.append(String.format(new StringBuilder().append("<input title='").append(vocname).append("' type='text' name='%1$sName' value='%2$s' id='%1$sName' size='%3$s' class='%4$s'/>").toString()
                    , getProperty(), name, getSize()+"", styleClass)) ;

            sb.append("<div id='"+divId+"'><span></span></div>") ;
           
            JavaScriptContext js = JavaScriptContext.getContext(pageContext, this) ;
//            sb.append("$('"+getProperty()+"').focus = setFocusOnField('"+getProperty()+"Name') ;\n") ;
            js.println(new StringBuilder().append("var ").append(autoc).append(" = new ecom_tree_autocomplete.Autocomplete() ;").toString()) ;
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
            
            divDefault.addElement(inputFieldId) ;
            divDefault.addElement(inputFieldName) ;
            divDefault.addElement(inputFieldParentId) ;
            divDefault.addElement(divView) ;
            return divDefault ;
            
            //return new div(sb.toString()) ;
            
            
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

    /** 
	 * Показывать идентификатор в названии
	 * @jsp.attribute   description = "Показывать идентификатор в названии"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public boolean getShowId() {return theShowId;}
	public void setShowId(boolean aShowId) {theShowId = aShowId;}
	
    /** 
	 * Идентификатор родителя
	 * @jsp.attribute   description = "Идентификатор родителя"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public String getParentId() {return theParentId;}
	public void setParentId(String aParentId) {theParentId = aParentId;}

	/** 
	 * Родительский autocomplete
	 * @jsp.attribute   description = "Родительский autocomplete"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public String getParentAutocomplete() {return theParentAutocomplete;}
	public void setParentAutocomplete(String aParentAutocomplete) {theParentAutocomplete = aParentAutocomplete;}

    /**
     * Переход на просмотр
     * @jsp.attribute   description = "Переход на просмотр"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public String getViewAction() { return theViewAction ; }
    public void setViewAction(String aViewAction) { theViewAction = aViewAction ; }

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
    


    protected String getUserInputFieldName() {
        return getProperty()+"Name" ;
    }
    /** Показывать идентификатор в названии */
	private boolean theShowId = false;
    /** Переход на просмотр */
    private String theViewAction ;
    /** Название справочника */
    private String theVocName = null ;
	/** Идентификатор родителя */
	private String theParentId;
	/** Родительский autocomplete */
	private String theParentAutocomplete = null ;
	private String theParentFieldId = null ;

}