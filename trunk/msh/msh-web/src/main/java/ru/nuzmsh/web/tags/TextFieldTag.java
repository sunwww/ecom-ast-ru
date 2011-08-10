package ru.nuzmsh.web.tags;

import javax.servlet.jsp.JspException;

import org.apache.ecs.Element;
import org.apache.ecs.xhtml.input;
import org.apache.ecs.xhtml.span;

import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.SnilsString;
import ru.nuzmsh.forms.validator.validators.TimeString;
import ru.nuzmsh.web.tags.helper.JavaScriptContext;

/**
 * @author ESinev
 * @jsp.tag name="textField"
 * display-name="Text Field"
 * body-content="empty"
 * description="Text Field JSP tag."
 */
public class TextFieldTag extends AbstractFieldTag {

	/**
     * Ввод пароля
     * @jsp.attribute   description="Ввод пароля"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public boolean getPasswordEnabled() { return thePasswordEnabled ; }
    public void setPasswordEnabled(boolean aPasswordEnabled) { thePasswordEnabled = aPasswordEnabled ; }

    protected Element getFieldElement() throws JspException {
    	
        //StringBuilder cssClass = new StringBuilder();

        if(isViewOnly()) {
        	return getViewOnlyElement(getFormattedValue()) ;
            //span s = new span(getFormattedValue());
            //s.setClass("viewOnlyTextField") ;
            //return s ;
        } else {
//        pageContext.
            input inputField = new input();
            inputField.setName(getProperty());
            inputField.setID(getProperty());
            inputField.setValue(getFormattedValue());
            if(!thePasswordEnabled) {
                inputField.setType("text");
            } else {
                inputField.setType("password");
            }

            try {
                StringBuilder cssClassName = new StringBuilder();
                if(isFieldRequired()) {
                    cssClassName.append("required") ;
                }
                if(isUpperCaseField()) {
                    cssClassName.append(" upperCase") ;
                }
                if(getAnnotation(DateString.class)!=null) {
                    JavaScriptContext.getContext(pageContext, this).println(
                            new StringBuilder(" new dateutil.DateField($('").append(getProperty()).append("'))")) ;
                } else if(getAnnotation(TimeString.class)!=null) {
                    JavaScriptContext.getContext(pageContext, this).println(
                            new StringBuilder(" new timeutil.TimeField($('").append(getProperty()).append("'))")) ;
                } else if(getAnnotation(SnilsString.class)!=null) {
                	JavaScriptContext.getContext(pageContext,this).println(
                			new StringBuilder(" new snilsutil.SnilsField($('").append(getProperty()).append("'))")) ;
                }
                if(getHorizontalFill()) {
                    cssClassName.append(" horizontalFill") ;
                }
                if(getSize()>=50) {
                	cssClassName.append(" maxHorizontalSize") ;
                }
                
                inputField.setClass(cssClassName.toString()) ;
            } catch (Exception e) {
                e.printStackTrace();
            }

            inputField.setTitle(getLabel() + getDataFieldName()) ;
            inputField.setSize(getSize()) ;

            return inputField ;
        }
    }

    protected String getFormattedValue() throws JspException {
        return !thePasswordEnabled ? super.getFormattedValue() : "";
    }
    

    private boolean isUpperCaseField() throws Exception {
        return getAnnotation(DoUpperCase.class)!=null ;
    }

    /** Ввод пароля */
    private boolean thePasswordEnabled = false ;

}
