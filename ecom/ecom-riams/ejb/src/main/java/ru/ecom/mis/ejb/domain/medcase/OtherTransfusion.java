package ru.ecom.mis.ejb.domain.medcase;

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
public class OtherTransfusion extends Transfusion {
	
	/** Другая трансфузионная жидкость */
	@Comment("Другая трансфузионная жидкость")
	@OneToOne
	public VocOtherTransfusPreparation getOtherPreparation() {return theOtherPreparation;}
	public void setOtherPreparation(VocOtherTransfusPreparation aOtherPreparation) {theOtherPreparation = aOtherPreparation;}


	/** Другая трансфузионная жидкость */
	private VocOtherTransfusPreparation theOtherPreparation;

}
