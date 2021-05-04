package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник новорожденный: одноплодный, 1 из многоплодных, 2 из многоплодных, 3..4...5")
@Entity
@Getter
@Setter
public class VocNewBorn extends VocBaseEntity {
	/** Какой по счету ребенок */
	private String birthOrder;
	/** Многоплодный */
	private Boolean isPolycarpous;
}
