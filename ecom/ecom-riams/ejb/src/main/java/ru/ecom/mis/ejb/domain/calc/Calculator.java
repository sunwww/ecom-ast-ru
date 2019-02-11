package ru.ecom.mis.ejb.domain.calc;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.diary.ejb.domain.protocol.parameter.voc.VocMeasureUnit;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;




/** Калькулятор человека */
@Comment("Калькулятор человека")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
public class Calculator extends BaseEntity{

	/** Название */
	@Comment("Название")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	private String theName;
	
	/** Имя создателя */
	@Comment("Имя создателя")
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;	}
	private String theUsername;
	
	/** Единица измерения */
	@Comment("Единица измерения")
	@OneToOne
	public VocMeasureUnit getValueOfResult() {return theValueOfResult;}
	public void setValueOfResult(VocMeasureUnit aValueOfResult) {theValueOfResult = aValueOfResult;	}
	private VocMeasureUnit theValueOfResult;
	
	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;	}
	private String theComment;


	/** Создавать дневник? */
	@Comment("Создавать дневник?")
	public Boolean getCreateDiary() {return theCreateDiary;}
	public void setCreateDiary(Boolean aCreateDiary) {theCreateDiary = aCreateDiary;	}
	private Boolean theCreateDiary;

	/** Тэг *.tag */
	@Comment("Тэг *.tag ")
	public String getTag() {return theTag;}
	public void setTag(String aTag) {theTag = aTag;	}
	private String theTag;
}
