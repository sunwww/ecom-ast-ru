package ru.ecom.diary.ejb.service.protocol.field;

import ru.nuzmsh.util.StringUtil;

public class RowField {
	public static StringBuilder getField(boolean aOpenIs, String aStyleId) {
		StringBuilder data = new StringBuilder() ;
		if (aOpenIs) {
			// открывающийся
			//data.append("msh:row") ;
			
			data.append("<tr");
			if (!StringUtil.isNullOrEmpty(aStyleId)) {
				data.append(" id='");
				data.append(aStyleId);
				data.append("'");
			}
			data.append(">");
		} else {
			// закрывающийся тэг
			//data.append("msh:row") ;
			data.append("</tr>") ;
		}
		return data ;
	}

}
