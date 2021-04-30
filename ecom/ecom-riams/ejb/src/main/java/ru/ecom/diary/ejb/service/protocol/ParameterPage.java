package ru.ecom.diary.ejb.service.protocol;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@SuppressWarnings("serial")
@Getter
@Setter
public class ParameterPage  implements Serializable{

	/** FormDate */
	private StringBuilder formData;
	/** JavaContext */
	private StringBuilder javaContext;

}
