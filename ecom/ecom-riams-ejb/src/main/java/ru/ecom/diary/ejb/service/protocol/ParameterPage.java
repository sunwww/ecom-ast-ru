package ru.ecom.diary.ejb.service.protocol;

import java.io.Serializable;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@SuppressWarnings("serial")
public class ParameterPage  implements Serializable{
	/** JavaContext */
	@Comment("JavaContext")
	public StringBuilder getJavaContext() {return theJavaContext;}
	public void setJavaContext(StringBuilder aJavaContext) {theJavaContext = aJavaContext;}

	/** FormDate */
	@Comment("FormDate")
	public StringBuilder getFormData() {return theFormData;}
	public void setFormData(StringBuilder aFormDate) {theFormData = aFormDate;}

	/** FormDate */
	private StringBuilder theFormData;
	/** JavaContext */
	private StringBuilder theJavaContext;

}
