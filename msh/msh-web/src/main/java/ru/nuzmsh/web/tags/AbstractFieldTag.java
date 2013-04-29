package ru.nuzmsh.web.tags;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ecs.Element;
import org.apache.ecs.MultiPartElement;
import org.apache.ecs.html.A;
import org.apache.ecs.xhtml.div;
import org.apache.ecs.xhtml.input;
import org.apache.ecs.xhtml.label;
import org.apache.ecs.xhtml.li;
import org.apache.ecs.xhtml.span;
import org.apache.ecs.xhtml.td;
import org.apache.ecs.xhtml.ul;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.fields.ALabel;
import ru.nuzmsh.forms.validator.fields.ComboBox;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.at.AField;
import ru.nuzmsh.util.at.AtUtil;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.forms.customize.FormCustomizeServiceHolder;
import ru.nuzmsh.web.struts.forms.customize.FormElementInfo;
import ru.nuzmsh.web.struts.forms.customize.IFormCustomizeService;
import ru.nuzmsh.web.tags.helper.JavaScriptContext;
import ru.nuzmsh.web.util.BaseValidatorFormUtil;
import ru.nuzmsh.web.util.DemoModeUtil;
import ru.nuzmsh.web.util.IdeTagHelper;
import ru.nuzmsh.web.util.StringSafeEncode;

/**
 * @jsp.tag name="field"
 *          display-name="Field"
 *          body-content="empty"
 *          description="Field JSP tag."
 */
public abstract class AbstractFieldTag extends TagSupport implements IGuidSupport {

    private final static Log LOG = LogFactory.getLog(AbstractFieldTag.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;


    public static final String LAST_DISPLAYED_FIELDNAME = "LAST_DISPLAYED_FIELD";
    public static final String LAST_DISPLAYED_FIELDNAME_IsEsc = "LAST_DISPLAYED_FIELD_ISESC";
    public static final String FIRST_DISPLAYED_FIELDNAME = "FIRST_DISPLAYED_FIELD";

    ///////////////////////////////////////////////////////
    //  Свойтсва
    //
    /** 
	 * Не показывать название
	 * @jsp.attribute   description = "Не показывать название"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public boolean getHideLabel() {
		return theHideLabel;
	}

	public void setHideLabel(boolean aHideLabel) {
		theHideLabel = aHideLabel;
	}

	/** Не показывать название */
	private boolean theHideLabel = false;
	
    /**
     * Название свойства
     *
     * @jsp.attribute   description="Название свойства у ActionForm"
     *                  required="true"
     *                  rtexprvalue="true"
     */
    public void setProperty(String aProperty) {  theProperty = aProperty;  }
    public String getProperty() { return theProperty; }

    /** 
     * Поле только для просмотра 
     * 
     * @jsp.attribute   description="Поле только для просмотра"
     *                  required="false"
     *                  rtexprvalue="true"
     */
	public boolean getViewOnlyField() {
		return theViewOnlyField;
	}

	public void setViewOnlyField(boolean aViewOnlyField) {
		theViewOnlyField = aViewOnlyField;
	}

	/** Только для просмотра */
	private boolean theViewOnlyField = false;
//    /**
//     * Список ролей для редактирования
//     * @jsp.attribute   description = "Список ролей для редактирования"
//     *                     required = "false"
//     *                  rtexprvalue = "true"
//     */
//    public String getEditRoles() { return theEditRoles ; }
//    public void setEditRoles(String aEditRoles) { theEditRoles = aEditRoles ; }
//
//    /** Cписок ролей для создания */
//    public String getCreateRoles() { return theCreateRoles ; }
//    public void setCreateRoles(String aCreateRoles) { theCreateRoles = aCreateRoles ; }

    /**
     * Размер поля
     * @jsp.attribute   description="Название"
     *                  required="false"
     *                  rtexprvalue="true"
     *
     * */
    public void setSize(int aSize) { theSize = aSize ; }
    public int getSize() { return theSize ; }

    /**
     * Название
     *
     * @jsp.attribute   description="Название"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public void setLabel(String aLabel) {  theLabel = aLabel;  }


    boolean isFieldRequired() throws Exception {
        Required required = (Required) getAnnotation(Required.class) ;
        return required!=null ;

    }

    /** 
	 * Guid
	 * @jsp.attribute   description = "Guid"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public String getGuid() {
		return theGuid;
	}

	public void setGuid(String aGuid) {
		theGuid = aGuid;
	}

    private String getLabelHtml() {
        String ret = getLabel() ;
        try {
            if( ! (this instanceof CheckBoxTag)) {
                if(! ret.endsWith(":") && !StringUtil.isNullOrEmpty(ret)) {
                    ret += ":" ;
                }
            }
            if(!isViewOnly()) {
                if(isFieldRequired()) {
                    ret = "<span class='required' title='Обязательное поле'>*</span>"+ret ;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return ret ;
    }

    public String getLabel() {
        String ret = theLabel!=null ? theLabel : getAnnotationLabel() ;
        if(ret!=null) ret = ret.replace(" ", "&nbsp;");
        return ret ;
    }

    protected String getAnnotationLabel() {
        String ret ;

        try {
            ALabel label = (ALabel) getAnnotation(ALabel.class) ;
            if(label!=null) {
                ret = label.label() ;
            } else {
                AField data = (AField) getAnnotation(AField.class) ;
                if(data!=null) {
                    ret = AtUtil.getComment(data) ;
                } else {
                    Comment comment = (Comment) getAnnotation(Comment.class) ;
                    if(comment!=null) {
                        ret = comment.value() ;
                    } else {
                        ret = "Не установлено свойтство ALabel или Data" ;
                    }
                }
            }
        } catch (Exception e) {
            ret = e+"" ;
        }
        return ret ;
    }

    
    protected boolean isViewOnly() {
    	return BaseValidatorFormUtil.isViewOnly(pageContext) || isDisabledField() || getViewOnlyField();
    }
    
    protected boolean isPrivateField() {
    	BaseValidatorForm form = BaseValidatorFormUtil.findBaseValidatorForm(pageContext);
    	return form!=null && form.isPrivateField(getProperty()) ; 
    }

    protected boolean isDisabledField() {
    	BaseValidatorForm form = BaseValidatorFormUtil.findBaseValidatorForm(pageContext);
    	return form!=null && form.isDisabledField(getProperty()) ; 
    }
    
    private ActionForm getForm() {
    	return BaseValidatorFormUtil.getForm(pageContext);
    }
    
    
    protected Annotation getAnnotation (Class aAnnotation) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    	return getAnnotation(aAnnotation, getForm(), theProperty) ;
    }
    
    protected static Annotation getAnnotation (Class aAnnotation, Object aForm, String aProperty) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    	if(aForm==null) {
    		// System.err.println("Нет формы при поиске аннотации "+aAnnotation.getSimpleName()+" у свойства "+aProperty);
    		return null ;
    	}
    	int pos = aProperty.indexOf('.') ;
    	if(pos>=0) {
    		String left = aProperty.substring(0, pos) ;
    		String right = aProperty.substring(pos+1);
    		Object obj = PropertyUtil.getPropertyValue(aForm, left) ;
    		return getAnnotation(aAnnotation, obj, right);
    	} else {
        	Method method = PropertyUtil.getMethodFormProperty(aForm.getClass(), aProperty) ;
            return method.getAnnotation(aAnnotation) ;
    	}
    }
    

    
//    String getGetterMethodName(String aPropertyName) {
//        if(aPropertyName==null || aPropertyName.length()<1) {
//            throw new IllegalArgumentException("Свойтсво property должно быть иметь длину больше 1. " +
//                    "Текущее значение:'"+aPropertyName+"'"+
//                    " Tag: "+getClass().getName() +
//                    " label: "+getLabel()
//            ) ;
//        }
//        String s = String.valueOf(aPropertyName.charAt(0)).toUpperCase() + aPropertyName.substring(1);
//        //System.out.println("tag: s = " + s);
//        return "get"+s ;
//    }

    /**
     * Количество колонок, занимаемое Названием поля
     * @jsp.attribute   description="Количество колонок, занимаемое Названием поля"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public void setLabelColSpan(int aLabelColSpan) { theLabelColSpan = aLabelColSpan ; }
    public int getLabelColSpan() { return theLabelColSpan ; }

    /** Количество колонок, занимаемое полем
     * @jsp.attribute   description="Количество колонок, занимаемое полем"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public void setFieldColSpan(int aFieldColSpan) { theFieldColSpan = aFieldColSpan ; }
    public int getFieldColSpan() { return theFieldColSpan ; }



    /** Заполнение по горизонтали
     * @jsp.attribute   description="Заполнение по горизонтали"
     *                  required="false"
     *                  rtexprvalue="true"
     * */
    public void setHorizontalFill(boolean aHorizontalFill) { theHorizontalFill = aHorizontalFill ; }
    public boolean getHorizontalFill() { return theHorizontalFill ; }
    ///////////////////////////////////////////////////////
    // PRIVATE METHODS
    //
    /**
     *
     */
    private String getBeanName() {
        FormTag tag = (FormTag) getFormTag(this);
        return tag!=null ? tag.getBeanName() : null ;
    }

    private FormTag getFormTag(Tag aTag) {
        if(aTag==null) return null ;

        if(aTag.getParent() instanceof FormTag) {
               return (FormTag) aTag.getParent() ;
        } else {
            return getFormTag(aTag.getParent()) ;
        }
    }

    public ul createError() throws JspException {
        ul ulError = new ul();
        ulError.setClass("fieldErrors");
        boolean hasErrors = false;
        ActionMessages errors = (ActionMessages) pageContext.getRequest().getAttribute("org.apache.struts.action.ERROR");
        if (errors != null && !errors.isEmpty()) {
            Iterator iterator = errors.get(theProperty);
            while (iterator.hasNext()) {
                hasErrors = true;
                ActionMessage msg = (ActionMessage) iterator.next();
                ulError.addElement(new li(msg.getKey()));
            }
        }
        return hasErrors ? ulError : null;
    }


    private String getCustomizeFormName() {
        String formName = getBeanName() ;
        if(formName==null) {
            ActionForm form = getForm() ;
            if(form!=null) {
                formName = form.getClass().getName() ;
            }
        }
        if(formName==null) {
            formName = "UNKOWN_FORM" ;
        }
        return formName ;
    }
    private FormElementInfo getElementInfo() {
        IFormCustomizeService customizeService = FormCustomizeServiceHolder.getService() ;
            FormElementInfo elementInfo ;
            if(customizeService!=null) {
                elementInfo = customizeService.findFormElementInfo(null, getCustomizeFormName(),getProperty()) ;
            } else {
                elementInfo = null ;
            }
        return elementInfo ;
    }

    private static void setColWidth(td aTd, Stack<String> aStack, int aColSpan) {
        if(aStack!=null && aColSpan==1 && !aStack.isEmpty()) {
        	String width = aStack.pop() ;
        	if(!"-".equals(width)) {
        		aTd.setWidth(width);
        	}
        }
    }
    
    public int doStartTag() throws JspException {
    	try {
        ul errors = createError();
        boolean hasErrors = errors != null;

        boolean isCustomize = "true".equals(pageContext.getRequest().getParameter("customizeMode")) ;

//        IFormCustomizeService customizeService = FormCustomizeServiceHolder.getService() ;
        FormElementInfo elementInfo = getElementInfo() ;

        if(!isCustomize && elementInfo!=null && elementInfo.isVisible()!=null && !elementInfo.isVisible() ) { // спрятать элемент
            try {
                JspWriter out = pageContext.getOut();
                out.print("<input type='hidden' name='") ;
                out.print(theProperty) ;
                out.print("' value='") ;
                out.print(getFormattedValue()) ;
                out.println("' />") ;
            }
            catch (IOException ioe) {
                throw new JspException("Ошибка вывода",ioe);
            }

        } else {
            Element fieldElement = isPrivateField() ? getPrivateFieldElement() : getFieldElement() ;
            if(elementInfo!=null) {
                if(!StringUtil.isNullOrEmpty(elementInfo.getLabel())) {
                    setLabel(elementInfo.getLabel());
                }
            }

            if(hasErrors) {
                String firstErrorId = (String) pageContext.getRequest().getAttribute("firstErrorId") ;
                if(firstErrorId==null) pageContext.getRequest().setAttribute("firstErrorId", theProperty);
            }
            
            //JspTag parent = getParent() ;
            //System.out.println(" row panel = "+parent);
            Stack<String> colsWidthStack = (Stack<String>) pageContext.getAttribute("colsWidthStack") ;
            // <td> <label ..></td>
            td tdLabel = new td();

            // idemode
            if(theIdeTagHelper.isInIdeMode(pageContext) && !StringUtil.isNullOrEmpty(getGuid())) {
                div d = new div() ; //getClass().getSimpleName()) ;
                d.setID(getGuid());
                theIdeTagHelper.saveJspPath(pageContext);
                d.setClass("idetag tagname"+getClass().getSimpleName());
                
                tdLabel.addElement(d);
            }
            
            tdLabel.setClass("label" + (hasErrors ? " errorLabel" : ""));
            tdLabel.setColSpan(getLabelColSpan()) ;
            
            setColWidth(tdLabel, colsWidthStack, getLabelColSpan());

            tdLabel.setTitle(
                    getLabel() +" ("+getProperty()+")"
            ) ;

            // не показывать название
            if(!theHideLabel) {
                Element labelElement = getLabelElement() ;
                tdLabel.addElement(labelElement) ;
            } else {
            	
            	tdLabel.setStyle("display:none") ;
            }

            if(isCustomize) {
                A a = new A();
                if(elementInfo!=null) {
                    a.setClass("customized") ;
                }
                StringBuilder url = new StringBuilder("formCustomizeEdit.do?formName=");
                url.append(getCustomizeFormName()) ;
                url.append("&elementName=") ;
                url.append(getProperty()) ;
                url.append("&origLabel=") ;
                url.append(theStringSafeEncode.encode(getLabel())) ;
                a.setHref(url.toString()) ;
                a.setTagText("●") ;
                tdLabel.addElement(a) ;
            }
            // <td> <input ..></td>
            td tdField = new td();
//        String styleClass = "field" + (hasErrors ? " errorField" : "")
//            + (theHorizontalFill ? " horizontalFill" : "") ;
            tdField.setColSpan(getFieldColSpan()) ;
            tdField.setClass(getProperty()) ;
            
            setColWidth(tdField, colsWidthStack, getFieldColSpan());


            // проверки на роли для редактирования
//        if (CAN_TRACE) LOG.trace("theEditRoles = " + theEditRoles);
//        if (CAN_TRACE) LOG.trace("fieldElement.getClass() = " + fieldElement.getClass());

//        if( !StringUtil.isNullOrEmpty(theEditRoles) && fieldElement instanceof input) {
//            input inputElement = (input) fieldElement  ;
//            boolean inRole = RolesHelper.checkRoles(theEditRoles, (HttpServletRequest) pageContext.getRequest()) ;
//            inputElement.setReadOnly(!inRole) ;
//            inputElement.setDisabled(!inRole) ;
////            inputElement.setTitle(inputElement.getAttribute("title") +" НЕТ "+theEditRoles) ;
//        }
//
//        ActionForm form = getForm() ;
//        BaseValidatorForm validatorForm = form instanceof BaseValidatorForm ? (BaseValidatorForm) form : null  ;
//
//        if(!StringUtil.isNullOrEmpty(theCreateRoles) && validatorForm!=null && validatorForm.isTypeCreate() && fieldElement instanceof input) {
//            input inputElement = (input) fieldElement  ;
//            boolean inRole = RolesHelper.checkRoles(theCreateRoles, (HttpServletRequest) pageContext.getRequest()) ;
//            inputElement.setReadOnly(!inRole) ;
//            inputElement.setDisabled(!inRole) ;
////            inputElement.setTitle(inputElement.getAttribute("title") +" НЕТ "+theCreateRoles) ;
//        }
//


            if(hasErrors) {
                div divField = new div();
                divField.setClass("errorField");
                divField.addElement(fieldElement) ;
                divField.addElement(errors) ;
                tdField.addElement(divField) ;
            } else {
                tdField.addElement(fieldElement) ;
            }


            try {
                JspWriter out = pageContext.getOut();
                out.println(tdLabel);
                out.println(tdField);
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    	} catch (Exception e) {
    		
    		IdeTagHelper.getInstance().showException(this, new Exception(theLabel+" : "+e.getMessage() ,e), pageContext);
    	}
        return EVAL_BODY_INCLUDE;
    }

    private static void printDump(Object aObject) throws Exception {
    	
    	PrintWriter out = new PrintWriter(new FileWriter("/tmp/dump.txt", true));
    	out.println(aObject);
    	out.close();
    }
    private void dumpObject(String aAppend, Object aObject) throws Exception {
    	//printDump("- "+aAppend+" "+aObject) ;
    	if(aObject==null) return ;
    	Class clazz = aObject.getClass() ;
    	Method[] methods = clazz.getMethods() ;
    	for(Method method : methods) {
    		if(!method.getName().equals("clone")) {
    			
	    		//printDump(method);
	    		if(method.getParameterTypes().length==0) {
	    			try {
	        			Object value = method.invoke(aObject);
	        			printDump(aAppend+"."+clazz.getSimpleName()+"."+method.getName()+" = "+value);
	        			if(value instanceof String
	        					|| value instanceof Boolean
	        					|| value instanceof Number
	        					|| value instanceof Annotation[]
	                            || value instanceof Class
	                            || value instanceof Class[]
	        					|| value instanceof Locale
	        					|| value instanceof StringBuffer
	        					|| value instanceof StringBuilder
	        					|| (value !=null && value.getClass().getSimpleName().equals("BodyContentImpl"))
	        					
	        					) {
	        			} else {
	        				dumpObject(aAppend+" ", value);
	        			}
	    			} catch (Exception e) {
	    				//printDump(e);
	    			}
	    		}
    		}
    	}
    }
    
    protected boolean isEnterSupports() { return true ; }
    protected boolean isEscSupports() { return false ; }
    
    public int doEndTag() throws JspException {
        int ret = super.doEndTag();
        if(isEnterSupports()) printEnterJavascript() ;
        //if(isEnterSupports()) printEnterJavascript() ;
        return ret ;
    }

    protected abstract Element getFieldElement() throws JspException;

    protected Element getLabelElement() {
    	MultiPartElement labelLabel ; 
    	if(isViewOnly()) {
    		span s = new span(getLabelHtml()) ;
    		s.setClass("viewOnlyLabel") ;
    		labelLabel = s ;
    	} else {
    		label l = new label() ;
    		l.setFor(getUserInputFieldName()) ;
            l.addElement(getLabelHtml());
    		if (isEscSupports()) {
    			//span s1 = new span("<br/><i>ESC - переход на следующее поле, <br/> CTRL+ENTER - сохранение протокола,<br/> SHIFT+ESC - отмена</i>") ;
    			//l.addElement(s1);
    		}
    		labelLabel = l ;
    	}
        labelLabel.setID(theProperty+"Label") ;
        return labelLabel ;
    }

    
    protected String getFormattedValue() throws JspException {
        String ret ;
        String beanName = getBeanName() ;
        if (CAN_TRACE) LOG.trace("beanName = " + beanName);
        if(beanName!=null) {
        	Object value ;
        	try {
        		value = TagUtils.getInstance().lookup(pageContext, getBeanName(), theProperty, null);
        	} catch (JspException e) {
        		throw new IllegalArgumentException(e);
        	}
            // Для ДЕМО-режима
            try {
                if(DemoModeUtil.isInDemoMode((HttpServletRequest) pageContext.getRequest())) {
                	value = DemoModeUtil.secureValue(value ) ;
                }
            } catch (Exception e) {
            	LOG.warn("Ошибка при удалении символов в DEMO режиме [value="+value+"]", e);
            }
            if (CAN_TRACE) LOG.trace(" value = " + value);
            ret =  formatValue(value) ;
            if (CAN_TRACE) LOG.trace(" after value = " + value);
        } else {
            ret =  "" ;
        }

        
        if(StringUtil.isNullOrEmpty(ret)) {
        	//System.out.println(beanName+"."+theProperty+" = "+pageContext.getRequest().getAttribute(beanName+"."+theProperty)) ;
        	if(pageContext.getRequest().getAttribute(beanName+"."+theProperty)!=null) {
        		ret = (String)pageContext.getRequest().getAttribute(beanName+"."+theProperty) ;
        	} else {
        		if(!isViewOnly()) {
                    FormElementInfo info = getElementInfo();
                    if(info!=null && !StringUtil.isNullOrEmpty(info.getDefaultValue())) {
                        ret = info.getDefaultValue() ;
                        if(ret.indexOf("${currentDate}")>=0) {
                        	ret = ret.replace("${currentDate}", DateFormat.formatToDate(new Date())) ;
                        } else if(ret.indexOf("${currentTime}")>=0){
                        	ret = ret.replace("${currentTime}", DateFormat.formatToTime(new Time(new Date().getTime()))) ;
                        }
                    }
        		}
        	}
        }
        return ret ;
    }

    protected String getDataFieldName()  {
        try {
            AField data = (AField) getAnnotation(AField.class) ;
            return data!=null ? AtUtil.getFieldName(data) : "NoneField" ;
        } catch (Exception e) {
            return e.getMessage() ;
        }
    }

    /**
     * Return the given value as a formatted <code>String</code>.  This
     * implementation escapes potentially harmful HTML characters.
     *
     * @param value The value to be formatted. <code>null</code> values will
     *              be returned as the empty String "".
     * @throws javax.servlet.jsp.JspException if a JSP exception has occurred
     * @since Struts 1.2
     */
    protected String formatValue(Object value)  {
        //if (CAN_TRACE) LOG.trace(" value.getClass() = " + value!=null ? value.getClass() : "null class");
        if (value == null) {
            return "";
        }
        if(value!=null && (value.getClass().equals(Integer.TYPE) || value.getClass().equals(Long.TYPE)) && value.toString().equals("0")) {
            return "" ;
        }
        if(value instanceof Long && 0==(Long)value) {
            return "" ;
        }
        if(value!=null && value.getClass().equals(Integer.class)) {
            Integer i = (Integer)value ;
            if(i.equals(0)) {
                return "" ;
            }
        }

        return TagUtils.getInstance().filter(value.toString());
    }

    protected String getVocName() throws Exception {
        AField afield = (AField) getAnnotation(AField.class) ;
        if(afield!=null) {
            return AtUtil.getVocName(afield) ;
        } else {
            ComboBox comboBox = (ComboBox) getAnnotation(ComboBox.class) ;
            if(comboBox!=null) {
                return comboBox.voc();
            } else {
                return "Нет справочника "+getProperty() ;
            }
        }
    }

    protected void printEnterJavascript() throws JspException {
    	String lastDisplayedField  =  (String) pageContext.getAttribute(LAST_DISPLAYED_FIELDNAME, PageContext.REQUEST_SCOPE);
        Boolean lastDisplayedIsEscField  =  (Boolean) pageContext.getAttribute(LAST_DISPLAYED_FIELDNAME_IsEsc, PageContext.REQUEST_SCOPE);
        String firstDisplayedField  =  (String) pageContext.getAttribute(FIRST_DISPLAYED_FIELDNAME, PageContext.REQUEST_SCOPE);

        String currentField = getUserInputFieldName() ;

        if(lastDisplayedField!=null && !isViewOnly()) {
            if(firstDisplayedField==null) {
                pageContext.setAttribute(FIRST_DISPLAYED_FIELDNAME, lastDisplayedField, PageContext.REQUEST_SCOPE) ;
            }
            try {
//                JspWriter out = pageContext.getOut();
            	//System.out.println("ll->"+lastDisplayedField+"--"+lastDisplayedIsEscField) ;
                if (lastDisplayedIsEscField) {
                	
                	printEscSupport(pageContext, lastDisplayedField, currentField, this);
                } else {
                	printEnterSupport(pageContext, lastDisplayedField, currentField, this);
                }
            }
            catch (IOException ioe) {
                throw new JspException(ioe) ;
            }

        }
        if(!isViewOnly()) {
        	//System.out.println(currentField+"--"+isEscSupports()) ;
        	pageContext.setAttribute(LAST_DISPLAYED_FIELDNAME, currentField, PageContext.REQUEST_SCOPE) ;
            pageContext.setAttribute(LAST_DISPLAYED_FIELDNAME_IsEsc, isEscSupports(), PageContext.REQUEST_SCOPE) ;
        }
    }

    public static void printEscSupport(PageContext aPageContext, String aCurrentField, String aNextField, Object aObject) throws IOException {
        JavaScriptContext js = JavaScriptContext.getContext(aPageContext, aObject);
        
        //js.println("//cur="+aCurrentField);
        //js.println("//next="+aNextField);
		js.print("eventutil.addEventListener($('");
		js.print(aCurrentField);
		js.print("'), 'keydown', function(aEvent) {try {if(aEvent.ctrlKey && eventutil.isKey(aEvent, eventutil.VK_ENTER)){\n") ;
		js.print("        		$('submitButton').click() ;\n") ;
		js.print("		  	} else if(aEvent.shiftKey && eventutil.isKey(aEvent, eventutil.VK_ESCAPE)) {\n") ;
		js.print("		            $('cancelButton').click() ;return false ;\n") ;
		js.print("	            } else if(eventutil.isKey(aEvent, eventutil.VK_ESCAPE)) {\n") ;
		js.print("					$('");js.print(aCurrentField);js.print("').blur() ;\n") ;
		js.print("		            \n") ;
		js.print("		                $('");js.print(aNextField);js.print("').focus() ;\n") ;
		js.print("		                $('");js.print(aNextField);js.print("').select() ;\n") ;
		js.print("		                return false ;\n") ;
		js.print("		            \n") ;							 
		js.print("	            }\n") ;
		js.print("		  	} catch(e) {}}) ;\n") ; 		
		
    }    
    public static void printEnterSupport(PageContext aPageContext, String aCurrentField, String aNextField, Object aObject) throws IOException {
        JavaScriptContext js = JavaScriptContext.getContext(aPageContext, aObject);
        //js.println("//cur="+aCurrentField);
        //js.println("//next="+aNextField);
        js.print("  eventutil.addEnterSupport(") ;
        js.print("'") ;
        js.print(aCurrentField) ;
        js.print("', '") ;
        js.print(aNextField) ;
        js.print("'") ;
        js.println(")") ;
    }
    protected String getUserInputFieldName() {
        return theProperty ;
    }

//    protected void addJavaScript(CharSequence aJavaScriptText) {
//        FormTag form = getFormTag(this) ;
//        //form.addaddJavaScript(aJavaScriptText) ;
//    }


    private Element getPrivateFieldElement() throws JspException {
    	span s = new span("Доступ запрещен");
        s.setClass("viewOnlyAccessDeniedField") ;
        return s ;
    }
    
    protected final Element getViewOnlyElement(String aValue) {
    	input inputField = new input();
        inputField.setName(getProperty()+"ReadOnly");
        inputField.setTitle(aValue) ;
        inputField.setID(getProperty()+"ReadOnly");
        inputField.setValue(aValue);
        inputField.setReadOnly(true);
        StringBuilder cssClassName = new StringBuilder("viewOnly");
        if(getHorizontalFill()) {
            cssClassName.append(" horizontalFill") ;
        }
        if(getSize()>=50) {
        	cssClassName.append(" maxHorizontalSize") ;
        }
        
        inputField.setClass(cssClassName.toString()) ;
        inputField.setSize(getSize()) ;
        
    	input inputHidden = new input();
    	inputHidden.setName(getProperty());
    	inputHidden.setID(getProperty());
    	try {
    		inputHidden.setValue(getFormattedValue());
    	} catch (JspException e) {
    		throw new IllegalStateException(e) ;
    	}
    	//inputHidden.setReadOnly(true);
    	inputHidden.setType("hidden");
    	
    	span d = new span() ;
    	d.addElement(inputField);
    	d.addElement(inputHidden);
        return d ;
    }

    /** Заполнение по горизонтали */
    private boolean theHorizontalFill = false ;
    /** Название свойства */
    private String theProperty      = null  ;
    /** Название  */
    private String theLabel         = null  ;
    /** Размер поля */
    private int theSize             = 10    ;
    /** Количество колонок, занимаемое Названием поля */
    private int theLabelColSpan     = 1     ;
    /** Количество колонок, занимаемок полем */
    private int theFieldColSpan     = 1     ;

    private StringSafeEncode theStringSafeEncode = new StringSafeEncode();
//    /** Список ролей для редактирования */
//    private String theEditRoles = null ;
//    /** Cписок ролей для создания */
//    private String theCreateRoles  = null ;

    private final IdeTagHelper theIdeTagHelper = IdeTagHelper.getInstance(); 
	/** Guid */
	private String theGuid;
}
