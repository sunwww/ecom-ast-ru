package ru.ecom.diary.ejb.service.protocol.field;

import ru.ecom.diary.ejb.form.protocol.parameter.ParameterForm;
import ru.nuzmsh.util.StringUtil;

public class TextField {
	public static StringBuilder getField(ParameterForm aForm, String aProperty
			, String aLabelName	,String aValue
			, String aColSpan, String aSize
			, Boolean aHorizontalFill, Boolean aRequired
			, Boolean aViewOnly,StringBuilder aErrors
			) {
		StringBuilder field = new StringBuilder() ;
		field.append("<td");
		if (!StringUtil.isNullOrEmpty(aColSpan)) {
			field.append(" colspan=\"").append(aColSpan).append("\"");
		}
		field.append(" class=\"").append(aProperty).append("\"");
		field.append(">");
		if (aViewOnly) field.append("<span>");
		field.append("<input autocomplete=\"off\" title=\"").append(aLabelName)
			.append(aProperty)
			.append("\"") ;
		
		field.append(" class=\"");
		if (aViewOnly) {
			field.append(" viewOnly");
		} else {
			if (aRequired) field.append(" required");
		}
		if (aHorizontalFill) field.append(" horizontalFill");
			
		field.append("\"");
		field.append(" id=\"").append(aProperty).append("\"");
		field.append(" name=\"").append(aProperty).append("\"");
		if (!StringUtil.isNullOrEmpty(aSize)) {
			field.append(" size=\"").append(aSize).append("\"");
		}
		field.append(" value='" ).append(aValue==null?"":aValue) .append("'");
		if (aViewOnly) field.append(" readonly=\"readonly\"");
		field.append(" type='text'/>");
		if (aViewOnly) {
			field.append("</span>") ;
		} else {
			if (aErrors!=null) field.append(aErrors);
		}
		field.append("</td>") ;
		
		System.out.println("field=") ;
		System.out.println(field.toString()) ;
		
		return field;
	}
	public static StringBuilder getJavaScript(boolean aViewOnly,String aProperty,String aPropertyNext) {
		StringBuilder javaScript = new StringBuilder() ;
		if (!aViewOnly) {
			javaScript.append("//		 class ru.nuzmsh.web.tags.TextFieldTag\n");
			javaScript.append("		  eventutil.addEnterSupport('").append(aProperty).append("', '").append(aPropertyNext).append("')\n");
		}
		return javaScript;
	}

}
