package ru.ecom.mis.ejb.service.expert;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@SuppressWarnings("serial")
@Getter
@Setter
public class QualityEstimationRow  implements Serializable{
	/** Javascript */
	private String javaScriptText;
	/** Таблица со значениями */
	private String tableColumn;
}
