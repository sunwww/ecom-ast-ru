package ru.ecom.diary.ejb.service.protocol;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@SuppressWarnings("serial")
@Getter
@Setter
public class ParameterType implements Serializable{

	/** Тип */
	private String type;
	/** ID */
	private Long id;
	/** Название */
	private String name;

}
