package ru.ecom.ejb.services.live.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.List;

@Entity
@AIndexes({
	@AIndex(name="class_action", unique = false, properties={"classname","action"})
})
@Table(schema="SQLUser")
@Getter
@Setter
public class LiveEntity {

	 /** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return id ; }

	/** Транзакция */
	@Comment("Транзакция")
	@ManyToOne
	public LiveTransaction getTransaction() {
		return transaction;
	}


	/** Свойства */
	@Comment("Свойства")
	@OneToMany(cascade=CascadeType.ALL, mappedBy="entity")
	public List<LiveProperty> getProperties() {
		return properties;
	}

	/** Свойства */
	private List<LiveProperty> properties;
	/** Транзакция */
	private LiveTransaction transaction;
	/** Тип действия */
	private int action;
	/** Название класса */
	private String classname;
    /** Идентификатор */
    private long id ;
	/** Идентификатор сущности */
	private String entityId;

}
