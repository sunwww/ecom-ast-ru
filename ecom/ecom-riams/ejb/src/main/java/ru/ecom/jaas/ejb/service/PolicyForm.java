package ru.ecom.jaas.ejb.service;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Getter
@Setter
public class PolicyForm implements Serializable{
	public 	PolicyForm(String aKey,String aName,String aComment) {
		key = aKey ;
		name = aName;
		comment = aComment ;
	}
	/** Id в файле */
	private Long idInFile;
	/** Существует в базе? */
	private Boolean isExist;
	/** Комментарий */
	private String comment;
	/** Название */
	private String name;
	/** Ключ */
	private String key;

}
