package ru.ecom.diary.ejb.service.protocol.tree;

public class CheckNodeByParameter extends CheckNode {

	public CheckNodeByParameter(String aId, String aName, boolean aChecked, Long aTypeParameterId) {
		super(aId, aName, aChecked);
		theTypeParameterId = aTypeParameterId ;
		// TODO Auto-generated constructor stub
	}
    /** Тип параметра */
	public Long getTypeParameterId() {return theTypeParameterId;}
	public void setTypeParameterId(Long aTypeParameterId) {theTypeParameterId = aTypeParameterId;}

	/** Тип параметра */
	private Long theTypeParameterId;
}
