package ru.ecom.diary.ejb.service.protocol.tree;

public class CheckNodeByParameter extends CheckNode {

	public CheckNodeByParameter(String aId, String aName, boolean aChecked, Long aTypeParameterId) {
		super(aId, aName, aChecked);
		typeParameterId = aTypeParameterId ;
		// TODO Auto-generated constructor stub
	}
    /** Тип параметра */
	public Long getTypeParameterId() {return typeParameterId;}
	public void setTypeParameterId(Long aTypeParameterId) {typeParameterId = aTypeParameterId;}

	/** Тип параметра */
	private Long typeParameterId;
}
