package ru.ecom.mis.ejb.service.expert;

import java.io.Serializable;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@SuppressWarnings("serial")
public class QualityEstimationRow  implements Serializable{
	/** Таблица со значениями */
	@Comment("Таблица со значениями")
	public String getTableColumn() {
		return theTableColumn;
	}

	public void setTableColumn(String aTableColumn) {
		theTableColumn = aTableColumn;
	}
	
	/** Javascript */
	@Comment("Javascript")
	public String getJavaScriptText() {
		return theJavaScriptText;
	}

	public void setJavaScriptText(String aJavaScriptText) {
		theJavaScriptText = aJavaScriptText;
	}

	/** Javascript */
	private String theJavaScriptText;

	/** Таблица со значениями */
	private String theTableColumn;
}
