package ru.nuzmsh.web.tags;

import javax.servlet.jsp.JspException;

import org.apache.ecs.Element;
import org.apache.ecs.xhtml.input;
import org.apache.ecs.xhtml.span;

/**
 * @jsp.tag name="checkBox"
 * display-name="checkBox"
 * body-content="empty"
 * description="checkBox"
 */
public class CheckBoxTag extends AbstractFieldTag {


    protected Element getFieldElement() throws JspException {
        return super.getLabelElement();
    }

    private boolean isChecked() throws JspException {
        String value = getFormattedValue();
        return "true".equals(value);
    }

    public Element getLabelElement() {

//        if (isViewOnly()) {
//            try {
//                span s = new span(isChecked() ? "&radic;" : "◯");
//                s.setClass("viewOnlyCheckBox");
//                return s;
//            } catch (Exception e) {
//                e.printStackTrace() ;
//                return new span(e+"") ;
//            }
//        } else {
            input inputField = new input();
            inputField.setType("checkbox");
            inputField.setName(getProperty());
            inputField.setID(getProperty());
            try {
            	inputField.setChecked(isChecked());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(isViewOnly()) {
            	inputField.setDisabled(true);
            }

            return inputField;
//        }
    }
}
