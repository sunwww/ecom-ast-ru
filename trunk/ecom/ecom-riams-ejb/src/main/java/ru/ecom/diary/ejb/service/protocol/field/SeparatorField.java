package ru.ecom.diary.ejb.service.protocol.field;

import ru.nuzmsh.util.StringUtil;

public class SeparatorField {
	public static StringBuilder getField(String aLabelName, String aColSpan) {
		StringBuilder data = new StringBuilder() ;
		/*
<td class="separator" colspan="4">
<div class="h3"><h3>Референтный интервал</h3></div></td>
 </tr>

		 */
		data.append("<td class=\"separator\"");
		if (!StringUtil.isNullOrEmpty(aColSpan))  data.append(" colspan=\"").append(aColSpan).append("\"");
		data.append(">");
		data.append("<div class=\"h3\"><h3>").append(aLabelName).append("</h3></div></td>");
		return data ;
	}

}
