package ru.nuzmsh.web.tags;

import org.apache.log4j.Logger;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;

/**
 *
 * @jsp.tag  name="ifPropertyIsTrue"
 *          display-name="Выводить блок, если поле формы - истина, либо не пустое"
 *          body-content="scriptless"
 *          description="Секция"
 *
 */
public class IfPropertyIsTrueTag extends AbstractGuidSimpleSupportTag {
    private static final Logger LOG = Logger.getLogger(IfPropertyIsTrueTag.class);

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut() ;
        PageContext ctx = (PageContext) getJspContext() ;
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest() ;
        Object form = request.getAttribute(theFormName);
        if (form instanceof BaseValidatorForm) {
            BaseValidatorForm entityForm = (BaseValidatorForm) form;
            Object value ;
            try {
                value = PropertyUtil.getPropertyValue(entityForm,thePropertyName);
            } catch (Exception e) {
                LOG.error("ERROR "+e.getMessage(),e);
                value = null;
            }
            boolean showTag ;
            if (value == null) {
                showTag = false;
            } else if (value instanceof Boolean) {
                 showTag = (boolean) value;
            } else if (value instanceof String) {
                showTag = !StringUtil.isNullOrEmpty(((String) value));
            } else {
                LOG.warn("unknown value type = "+value+" <> "+value.getClass().getSimpleName());
                showTag = true; //Если дата, время, число и оно не null, считаем что оно не пустое
            }
            if (showTag ^ theInvert) {
                getJspBody().invoke(out);
            }
        }
    }

    /** Название поля
     * @jsp.attribute description="Название поля, "
     *                  requires="true"
     *                  rtexprvalue = "true"
     * */
    public String getPropertyName() {return thePropertyName;}
    public void setPropertyName(String aPropertyName) {thePropertyName = aPropertyName;}
    /** Название поля */
    private String thePropertyName ;

    /** Название поля
     * @jsp.attribute description="Название формы"
     *                  requires="true"
     *                  rtexprvalue = "true"
     * */
    public String getFormName() {return theFormName;}
    public void setFormName(String aFormName) {theFormName = aFormName;}
    /** Имя формы */
    private String theFormName ;

    /** Инвертировать условие
     *
     * @jsp.attribute description="Инвертировать условие, т.е. показывать блок если условие не выполняется"
     *  requires="false"
     *  rtexprvalue = "true"
     * */
    public Boolean getInvert() {return theInvert;}
    public void setInvert(Boolean aInvert) {theInvert = aInvert;}
    /** Инвертировать условие */
    private Boolean theInvert = false;
}