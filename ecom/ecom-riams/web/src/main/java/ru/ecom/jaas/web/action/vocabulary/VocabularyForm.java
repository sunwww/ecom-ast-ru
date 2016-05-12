package ru.ecom.jaas.web.action.vocabulary;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;

public class VocabularyForm extends BaseValidatorForm {
	/** Короткое название */
	@Comment("Короткое название")
	public String getSimpleName() {return theSimpleName;}
	public void setSimpleName(String aSimpleName) {theSimpleName = aSimpleName;}

	/** Полное название */
	@Comment("Полное название")
	public String getClassname() {return theClassname;}
	public void setClassname(String aClassname) {theClassname = aClassname;}

	/** Количество записей */
	@Comment("Количество записей")
	public int getCount() {return theCount;}
	public void setCount(int aCount) {theCount = aCount;}

	/** Системный класс */
	@Comment("Системный класс")
	public boolean getIsSystem() {return theIsSystem;}
	public void setIsSystem(boolean aIsSystem) {theIsSystem = aIsSystem;}

	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	public VocabularyForm (String aClassname, String aSimpleName, String aComment, int aCount, boolean aIsSystem) {
		theClassname = aClassname ;
		theSimpleName = aSimpleName ;
		theComment = aComment ;
		theCount = aCount ;
		theIsSystem = aIsSystem ;
		
	}
	/** Комментарий */
	private String theComment;
	/** Системный класс */
	private boolean theIsSystem;
	/** Количество записей */
	private int theCount;
	/** Полное название */
	private String theClassname;
	/** Короткое название */
	private String theSimpleName;

}
