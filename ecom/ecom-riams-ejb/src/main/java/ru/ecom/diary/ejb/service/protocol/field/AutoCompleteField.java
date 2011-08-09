package ru.ecom.diary.ejb.service.protocol.field;

import ru.ecom.ejb.util.injection.EjbInjection;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.IVocService;
import ru.nuzmsh.util.voc.VocAdditional;

public class AutoCompleteField {
	public static StringBuilder getField(String aVocName, String aProperty
			, String aLabelName	,String aValue, String aParentId
			, String aColSpan, String aSize
			, Boolean aHorizontalFill, Boolean aRequired
			, Boolean aViewOnly , StringBuilder aErrors) {
		return getField(aVocName, aProperty
				, aLabelName	,aValue, aParentId
				, aColSpan, aSize
				, aHorizontalFill, aRequired
				, aViewOnly , aErrors,"1") ;
	}
	public static StringBuilder getField(String aVocName, String aProperty
			, String aLabelName	,String aValue, String aParentId
			, String aColSpan, String aSize
			, Boolean aHorizontalFill, Boolean aRequired
			, Boolean aViewOnly , StringBuilder aErrors, String aRowSpan
			) {
		//+//<td colspan="3" class="type"><div><input size="1" name="type" value="1" id="type" type="hidden">
		//+//<input autocomplete="off" title="parameterType" 
		//name="typeName" value="Числовой (Integer)" id="typeName" size="10" class="autocomplete horizontalFill required" type="text">
		//<div id="typeDiv"><span></span></div>
		//+//</div></td>
		//+//<td colspan="3" class="type"><span>
		//+//<input title="Числовой (Integer)" class="viewOnly horizontalFill" 
		//id="typeReadOnly" name="typeReadOnly" size="10" readonly="readonly" value="Числовой (Integer)">
		//+//<input id="type" value="1" name="type" type="hidden">
		//+//</span></td>
		

		StringBuilder field = new StringBuilder() ;
		//TODO value доделать !!!
		
		String value="";
		try {
			//(IVocService) theInjection.getService("VocValueService") 
			//VocValueServiceBean bean = new VocValueServiceBean();
			//value = bean.getNameById(aValue, aVocName, !StringUtil.isNullOrEmpty(aParentId) ? new VocAdditional(aParentId): null);
			 //IVocService service = (IVocService) EjbInjection.getInstance().getService("/VocValueService","remote");
			IVocService service =(IVocService) EjbInjection.getInstance().getService("IVocValueService","remote") ;
			value =  service.getNameById(aValue, aVocName, !StringUtil.isNullOrEmpty(aParentId) ? new VocAdditional(aParentId): null);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		field.append("<td");
		if (!StringUtil.isNullOrEmpty(aColSpan)) {
			field.append(" colspan=\"").append(aColSpan).append("\"");
		}
		if (!StringUtil.isNullOrEmpty(aRowSpan)) {
			field.append(" rowspan=\"").append(aRowSpan).append("\"");
		}
		field.append(" class=\"").append(aProperty).append("\"");
		field.append(">");
		if (aViewOnly) {
			field.append("<span>");
		} else {
			field.append("<div>") ;
		}
		field.append("<input name=\"").append(aProperty).append("\" value=\"").append(aValue).append("\" id=\"").append(aProperty).append(	"\" type=\"hidden\">");
		field.append("<input");
		if (aViewOnly) {
			field.append(" autocomplete=\"off\"");
			field.append(" title=\"").append(aLabelName).append(aProperty)	.append("\"") ;
		} else {
			field.append(" title=\"").append(aProperty).append("Name")	.append("\"") ;
		}
		/*/*/
		field.append(" class=\"");
		if (aViewOnly) {
			field.append("viewOnly");
		} else {
			field.append("autocomplete");
			if (aRequired) field.append(" required");
		}
		if (aHorizontalFill) field.append(" horizontalFill");
			
		field.append("\"");
		// id
		field.append(" id=\"").append(aProperty);
		if (aViewOnly) field.append("ReadOnly"); else field.append("Name");
		field.append("\"");
		//name
		field.append(" name=\"").append(aProperty);
		if (aViewOnly) field.append("ReadOnly"); else field.append("Name");
		field.append("\"");
		if (!StringUtil.isNullOrEmpty(aSize)) {
			field.append(" size=\"").append(aSize).append("\"");
		}

        field.append(" value=\"" ).append(value) .append("\"");
		if (aViewOnly) field.append(" readonly=\"readonly\"");
		field.append(" type='text'/>");
		
		if (aViewOnly) {
			field.append("</span>") ;
		} else {
			field.append("<div id=\"").append(aProperty).append("Div\"><span></span></div>");
			
			if (aErrors!=null) field.append(aErrors);
			field.append("</div>") ;
		}
		field.append("</td>") ;
		
		//System.out.println("field=") ;
		//System.out.println(field.toString()) ;
		
		return field;
	}
	public static StringBuilder getJavaScript(boolean aViewOnly,String aVocName, String aProperty,String aPropertyNext,String aLabelName) {
		StringBuilder javaScript = new StringBuilder() ;
		if (!aViewOnly) {
			javaScript.append("//		 class ru.nuzmsh.web.tags.AutoCompleteTag\n");
			
			javaScript.append("		var ").append(aProperty).append("Autocomplete = new msh_autocomplete.Autocomplete() ;\n");
			javaScript.append(aProperty).append("Autocomplete.setUrl('simpleVocAutocomplete/").append(aVocName).append("') ;\n");
			javaScript.append(aProperty).append("Autocomplete.setIdFieldId('").append(aProperty).append("') ;\n") ;
			javaScript.append(aProperty).append("Autocomplete.setNameFieldId('").append(aProperty).append("Name') ;\n") ;
			javaScript.append(aProperty).append("Autocomplete.setDivId('").append(aProperty).append("Div') ;\n") ;
			javaScript.append(aProperty).append("Autocomplete.setVocKey('").append(aVocName).append("') ;\n") ;
			javaScript.append(aProperty).append("Autocomplete.setVocTitle('").append(aLabelName).append("') ;\n") ;
			javaScript.append(aProperty).append("Autocomplete.build() ;\n") ;
			javaScript.append("\n") ;
			javaScript.append("//		 class ru.nuzmsh.web.tags.AutoCompleteTag\n") ;
			javaScript.append("		  eventutil.addEnterSupport('").append(aProperty).append("', '").append(aPropertyNext).append("');\n");
		}
		return javaScript ;
	}
}
