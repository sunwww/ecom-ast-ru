package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.medcase.voc.VocOtherTransfusPreparation;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Переливания других трансфузионных жидкостей
 * @author azviagin
 *
 */

@Comment("Переливания других трансфузионных жидкостей")
@Entity
@Getter
@Setter
public class OtherTransfusion extends Transfusion {
	
	/** Другая трансфузионная жидкость */
	@Comment("Другая трансфузионная жидкость")
	@OneToOne
	public VocOtherTransfusPreparation getOtherPreparation() {return otherPreparation;}
	private VocOtherTransfusPreparation otherPreparation;

}
