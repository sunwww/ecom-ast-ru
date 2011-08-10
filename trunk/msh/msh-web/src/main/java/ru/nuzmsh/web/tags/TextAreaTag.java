package ru.nuzmsh.web.tags;

import javax.servlet.jsp.JspException;

import org.apache.ecs.Element;
import org.apache.ecs.xhtml.span;
import org.apache.ecs.xhtml.textarea;

import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.util.StringUtil;

/**
 * @author ESinev
 * @jsp.tag name="textArea"
 *          display-name="Text Area"
 *          body-content="empty"
 *          description="Text Area JSP tag."
 */
public class TextAreaTag extends AbstractFieldTag {

	public TextAreaTag() {
		if (getIsNotGoEnter()) {
			isEnter = false ;
		}
		setSize(50);
	}
    protected boolean isEnterSupports() { return isEnter ; }
    protected boolean isEscSupports() { return isEnter ; }
    
    private boolean isEnter = true;
    /** 
	 * Количество строк
	 * @jsp.attribute   description = "Количество строк"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public int getRows () {
		return theRows ;
	}

	public void setRows (int aRows ) {
		theRows  = aRows ;
	}

    /** 
	 * Синтакс
	 * @jsp.attribute   description = "Синтакс"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public String getSyntaxLanguage() {
		return theSyntaxLanguage;
	}

	public void setSyntaxLanguage(String aSyntaxLanguage) {
		theSyntaxLanguage = aSyntaxLanguage;
	}

	
    protected Element getFieldElement() throws JspException {

        if(isViewOnly()) {
            span s = new span(getFormattedValue());
            s.setClass("viewOnlyTextField") ;
            return s ;
        } else {
//        pageContext.
            textarea inputField = new textarea(theRows,getSize());
            inputField.addElement(getFormattedValue()) ;
            inputField.setName(getProperty());
            inputField.setID(getProperty());
//            inputField.setValue(getFormattedValue());
            try {
                StringBuffer cssClassName = new StringBuffer();
                if(isFieldRequired()) {
                    cssClassName.append("required") ;
                }
                if(isUpperCaseField()) {
                    cssClassName.append(" upperCase") ;
                }

                if(!StringUtil.isNullOrEmpty(theSyntaxLanguage)) {
                	cssClassName.append(" codepress ").append(theSyntaxLanguage) ;
                }
                if(getHorizontalFill()) {
                    cssClassName.append(" horizontalFill") ;
                } else if(getSize()>=50) {
                	cssClassName.append(" maxHorizontalSize") ;
                }               
                inputField.setClass(cssClassName.toString()) ;
            } catch (Exception e) {
                e.printStackTrace();
            }
            StringBuilder title = new StringBuilder().append(getLabel()) ;
            if (!getIsNotGoEnter()) {
                title.append(" Горячие клавиши:")
                	.append("ESC - переход на следующее поле,  CTRL+ENTER - сохранение протокола, SHIFT+ESC - отмена");
            }
            inputField.setTitle(title.toString()) ;
            
            return inputField ;
        }
    }
    
    /** Не надо переходить по Enter 
	 * @jsp.attribute   description = "Не надо переходить по Enter"
		 *                     required = "false"
		 *                  rtexprvalue = "true"
		 */
    public boolean getIsNotGoEnter() {
		return theIsNotGoEnter;
	}

	public void setIsNotGoEnter(boolean aIsNotGoEnter) {
		theIsNotGoEnter = aIsNotGoEnter;
	}

	/** Не надо переходить по Enter */
	private boolean theIsNotGoEnter;

    
    private boolean isUpperCaseField() throws Exception {
        return getAnnotation(DoUpperCase.class)!=null ;
    }
	
	/** Количество строк */
	private int theRows = 10;
	/** Синтакс */
	private String theSyntaxLanguage = "";

}
