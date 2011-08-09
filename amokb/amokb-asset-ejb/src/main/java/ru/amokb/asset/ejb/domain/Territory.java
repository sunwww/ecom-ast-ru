package ru.amokb.asset.ejb.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Территория
	 */
	@Comment("Территория")
@Entity
@Table(schema="SQLUser")
public class Territory extends PermanentAsset{
	@OneToMany(mappedBy="territory")
	public List<Building> getBuildings() {
		return theBuildings;
	}
	public void setBuildings(List<Building> aBuildings) {
		theBuildings = aBuildings;
	}
	/**
	 * Здания
	 */
	private List<Building> theBuildings;
}
