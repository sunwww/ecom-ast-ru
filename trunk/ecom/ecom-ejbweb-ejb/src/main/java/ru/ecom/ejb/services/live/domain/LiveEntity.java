package ru.ecom.ejb.services.live.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@AIndexes({
	@AIndex(name="class_action", unique = false, properties={"classname","action"})
})
@Table(schema="SQLUser")
public class LiveEntity {

	 /** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }

    /** Идентификатор сущности */
	@Comment("Идентификатор сущности")
	public String getEntityId() {
		return theEntityId;
	}

	public void setEntityId(String aEntityId) {
		theEntityId = aEntityId;
	}

    /** Название класса */
	@Comment("Название класса")
	public String getClassname() {
		return theClassname;
	}

	public void setClassname(String aClassname) {
		theClassname = aClassname;
	}

	/** Тип действия */
	@Comment("Тип действия")
	public int getAction() {
		return theAction;
	}

	public void setAction(int aAction) {
		theAction = aAction;
	}
	
	/** Транзакция */
	@Comment("Транзакция")
	@ManyToOne
	public LiveTransaction getTransaction() {
		return theTransaction;
	}

	public void setTransaction(LiveTransaction aTransaction) {
		theTransaction = aTransaction;
	}

	/** Свойства */
	@Comment("Свойства")
	@OneToMany(cascade=CascadeType.ALL, mappedBy="entity")
	public List<LiveProperty> getProperties() {
		return theProperties;
	}

	public void setProperties(List<LiveProperty> aProperties) {
		theProperties = aProperties;
	}

	/** Свойства */
	private List<LiveProperty> theProperties;
	/** Транзакция */
	private LiveTransaction theTransaction;
	/** Тип действия */
	private int theAction;
	/** Название класса */
	private String theClassname;
    /** Идентификатор */
    private long theId ;
	/** Идентификатор сущности */
	private String theEntityId;

}
