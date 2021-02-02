package ru.ecom.web.tags;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.taglib.html.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.AbstractGuidSimpleSupportTag;
import ru.nuzmsh.web.tags.helper.JavaScriptContext;
import ru.nuzmsh.web.util.IdeTagHelper;
import ru.nuzmsh.web.vochelper.VocHelperDelegate;
import ru.nuzmsh.web.vochelper.VocHelperLocateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Ввод нескольких значений из справочника
 */
public class OneToManyOneAutocompleteTag extends AbstractGuidSimpleSupportTag {

	public String getJson(Object aParentForm, String aProperty) throws IllegalAccessException,  InvocationTargetException {
        return (String) PropertyUtil.getPropertyValue(aParentForm,  aProperty) ;
    }

	public String getJson(Object aParentForm, String aProperty, String aVocName,PageContext aCtx) throws IllegalAccessException, InvocationTargetException {
        String value = (String) PropertyUtil.getPropertyValue(aParentForm,  aProperty) ;

    	if(value!=null && !value.equals("null") && !value.equals("")) {
            
	        JSONObject jsonObjOld = new JSONObject(value) ;
	        StringBuilder  jsonNew = new StringBuilder("{\"childs\":[");
	        JSONArray jsonArray = jsonObjOld.getJSONArray("childs") ;
	        
	        for (int i = 0; i < jsonArray.length(); i++) {
	        	
	        	JSONObject child = jsonArray.getJSONObject(i);
				String jsonId = String.valueOf(child.get("value"));
				
				if (!StringUtil.isNullOrEmpty(jsonId) || "0".equals(jsonId)) {
					if (i>0) jsonNew.append(",");
					jsonNew.append("{\"value\":");
					jsonNew.append(jsonId);
					String name ;
			        try {
						name = VocHelperDelegate.locateVocHelper().getNameById(aCtx, jsonId, aVocName, null);
			        } catch (VocHelperLocateException e) {
			            name = e.getMessage() ;
			        }
			        if(name==null) name="" ;
			        jsonNew.append(",\"name\":");
			        jsonNew.append("\"");
			        jsonNew.append(name.replace("\"", "\\\""));
			        jsonNew.append("\"}");
			       
				}
	        }
	        jsonNew.append("]}");
	        return jsonNew.toString();
	        
        }
    	return null ;
    }

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        PageContext ctx = (PageContext) getJspContext();

        ActionForm form = (ActionForm) ctx.getAttribute(Constants.BEAN_KEY, PageContext.REQUEST_SCOPE);
        out.print("<td class='label' title='");
        out.print(theLabel);
        out.print(" (");
        out.print(theProperty);
        out.print(")");
        out.print("'><span class='");
        boolean isViewOnly = false ;
        if(form instanceof BaseValidatorForm) {
            BaseValidatorForm validatorForm = (BaseValidatorForm) form ;
            isViewOnly = validatorForm.isViewOnly() ;
        }
        try {
        	if(isViewOnly) {
                out.print("viewOnlyLabel") ;
            } else if(isFieldRequired(form)) {
        	    out.print("required");
            }
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        out.print(" label'>");
        out.print(theLabel);
        out.print(":");
        out.print("</span></td>") ;


        out.print("<td colspan='") ;
        out.print(theColSpan) ;
        out.println("'>") ;
        
        out.print("<table><tr>");
        // IDE MODE
        IdeTagHelper.getInstance().printMarker(this, getJspContext());
        
        try {
            String json = getJson(form, theProperty,theVocName,ctx) ;
            out.print("<input type='hidden' name='");
            out.print(theProperty);
            out.print("' id='");
            out.print(theProperty);
            out.print("' value='");
            out.print(json);
            out.print("'/>");
        } catch (Exception e) {
            throw new JspException(e) ;
        }

        out.print("<div id='") ;
        out.print(getDivname()) ;
        out.print("'/>") ;
        out.println("</td>") ;
        out.print("</tr></table></td>");


        JavaScriptContext js = JavaScriptContext.getContext(getJspContext(), this);
        StringBuilder sb = new StringBuilder();
        sb.append("var theOtmoa_") ;
        sb.append(theProperty) ;
        sb.append(" = new msh.widget.OneToManyAutocompletes(") ;
        sb.append("$('").append(getDivname()).append("')") ;
        sb.append(", $('mainForm')") ;
        sb.append(", '").append(theProperty).append("'") ;
        sb.append(", '").append("'") ; //gde title?
        sb.append(", '").append(theVocName).append("'") ;
        sb.append(", ").append(isViewOnly) ;
        sb.append(", '").append(theParentId==null ? "" : getParentIdValue(ctx)).append("'") ;
        sb.append(", '").append(theParentAutocomplete).append("'") ;
        sb.append(", '").append(theViewAction).append("'") ;
        sb.append(") ;\n") ;

        sb.append("theOtmoa_") ;
        sb.append(theProperty) ;
        sb.append(".install() ;") ;

        js.println(sb.toString());
    }

    /** Заголовок */
    public String getLabel() { return theLabel ; }
    public void setLabel(String aLabel) { theLabel = aLabel ; }

    /** Заголовок */
    private String theLabel ;

    private String getDivname() {
        return "otmoa_tag_"+theProperty ;
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
        			obj = theParentId ;
        		}
        	}
    	}
    	return obj!=null ? obj.toString() : theParentId ;
    }


    /**
     * Название свойство
     */
    public String getProperty() {
        return theProperty;
    }

    public void setProperty(String aProperty) {
        theProperty = aProperty;
    }

    /**
     * Название свойство
     */
    private String theProperty;

    /**
     * Количество колонок
     */
    public int getColSpan() {
        return theColSpan;
    }

    public void setColSpan(int aColSpan) {
        theColSpan = aColSpan;
    }

    /**
     * Количество колонок
     */
    private int theColSpan;

    /**
     * Справочник
     */
    public String getVocName() {
        return theVocName;
    }

    public void setVocName(String aVocName) {
        theVocName = aVocName;
    }

    /** 
	 * Идентификатор родителя
	 */
	public String getParentId() {
		return theParentId;
	}

	public void setParentId(String aParentId) {
		theParentId = aParentId;
	}


	/** 
	 * Родительский autocomplete
	 */
	public String getParentAutocomplete() {
		return theParentAutocomplete;
	}

	public void setParentAutocomplete(String aParentAutocomplete) {
		theParentAutocomplete = aParentAutocomplete;
	}
	
	/** Url для просмотра */
	public String getViewAction() {return theViewAction;}
	public void setViewAction(String aViewAction) {	theViewAction = aViewAction;}

	/** Url для просмотра */
	private String theViewAction;

	/** Идентификатор родителя */
	private String theParentId;
	/** Родительский autocomplete */
	private String theParentAutocomplete = null ;
	/** Справочник */
    private String theVocName;

    boolean isFieldRequired(ActionForm aForm) throws Exception {
        Required required = (Required) getAnnotation(aForm, Required.class) ;
        return required!=null ;

    }
    protected Annotation getAnnotation (ActionForm aForm, Class aAnnotation) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    	return getAnnotation(aAnnotation, aForm, theProperty) ;
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
}
