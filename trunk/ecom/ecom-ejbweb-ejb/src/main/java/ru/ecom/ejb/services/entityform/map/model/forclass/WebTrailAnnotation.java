package ru.ecom.ejb.services.entityform.map.model.forclass;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

public class WebTrailAnnotation extends AbstractClassAnnotation {
	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {
		return theComment;
	}

	public void setComment(String aComment) {
		theComment = aComment;
	}

	/** Свойства для отображения */
	@Comment("Свойства для отображения")
	public String[] getNameProperties() {
		return theNameProperties;
	}

	public void setNameProperties(String[] aNameProperties) {
		theNameProperties = aNameProperties;
	}

	/** Struts action для просмотра */
	@Comment("Struts action для просмотра")
	public String getView() {
		return theView;
	}

	public void setView(String aView) {
		theView = aView;
	}

	/** Struts action для списка */
	@Comment("Struts action для списка")
	public String getList() {
		return theList;
	}

	public void setList(String aList) {
		theList = aList;
	}

	/** Struts action для списка */
	private String theList;
	/** Struts action для просмотра */
	private String theView;
	
	/** Свойства для отображения */
	private String[] theNameProperties;
	
	/** Комментарий */
	private String theComment;
}
