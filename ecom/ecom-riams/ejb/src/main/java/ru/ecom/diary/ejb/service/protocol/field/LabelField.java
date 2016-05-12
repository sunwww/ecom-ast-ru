package ru.ecom.diary.ejb.service.protocol.field;

import ru.nuzmsh.util.StringUtil;

public class LabelField {
	public static StringBuilder getField(Boolean aViewOnly, Boolean aRequired, String aProperty, 
			String aNameLabel, String aColSpan, Boolean aHideLabel, StringBuilder aErrors) {
//		TODO			<td title="Короткое&nbsp;название (shortName)" class="label"><div id="b87e9cee-cf5d-43bc-b50d-1911d5e87e40" class="idetag tagnameTextFieldTag"><div class="gwt-Hyperlink im-IdeMenuWidget"><a href="#b87e9cee-cf5d-43bc-b50d-1911d5e87e40">TextFieldTag</a></div></div><label id="shortNameLabel" for="shortName"><span class="required" title="Обязательное поле">*</span>Короткое&nbsp;название:</label></td>
		StringBuilder label = new StringBuilder() ;
		label.append("<td");
		if (!StringUtil.isNullOrEmpty(aColSpan)) {
			label.append(" colspan=\"");
			label.append(aColSpan);
			label.append("\"");
		}
		label.append(" title=\"");
		label.append(aNameLabel).append("(").append(aProperty).append(")");
		label.append("\" class=\"label");
		if (aErrors!=null)  label.append(" errorLabel");
		label.append("\"") ;
		
		label.append(">");
		label.append("<label id=\"").append(aProperty).append("Label\" for=\"").append(aProperty)
				.append("\">");
		if ( !aViewOnly) {
			if (aRequired) label.append("<span class=\"required\" title=\"Обязательное поле\">*</span>");
		}
		
		label.append(aNameLabel).append(":");
		label.append("</label>");
		label.append("</td>");
		System.out.println("label=");
		System.out.println(label.toString());
		return label;
	}

}
