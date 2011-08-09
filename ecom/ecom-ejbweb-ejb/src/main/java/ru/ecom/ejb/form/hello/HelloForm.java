package ru.ecom.ejb.form.hello;

import ru.ecom.ejb.domain.hello.Hello;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = Hello.class)
@Comment("Проба")
@WebTrail(comment = "Проба", nameProperties = "hello", view = "entityView-ecom_hello.do")
@EntityFormSecurityPrefix("/Policy/IdeMode/Hello")
@Parent(property="parent", parentForm=HelloForm.class)
public class HelloForm extends IdEntityForm{

	/** Проба */
	@Comment("Проба")
	@Persist
	public String getHello() {
		return theHello;
	}

	public void setHello(String aHello) {
		theHello = aHello;
	}

	/** Родитель */
	@Comment("Родитель")
	@Persist
	public Long getParent() {
		return theParent;
	}

	public void setParent(Long aParent) {
		theParent = aParent;
	}

	/** Связь один-к-одному */
	@Comment("Связь один-к-одному")
	@Persist
	public Long getLink() {
		return theLink;
	}

	public void setLink(Long aLink) {
		theLink = aLink;
	}

	/** Связь один-к-одному */
	private Long theLink;
	/** Родитель */
	private Long theParent;
	/** Проба */
	private String theHello;
}
