package ru.ecom.address.ejb.service;

/**
 *
 */
public class AddressPointCheck {

    public AddressPointCheck(String aNumber, String aBuilding, String aFlat) {
    	if(aNumber==null) throw new IllegalArgumentException("Нет номера дома") ;
        theHouseNumber = aNumber ;
        theHouseBuilding = aBuilding ;
        theFlat = aFlat ;
    }
	
    public AddressPointCheck(String aNumber, String aBuilding) {
    	this(aNumber, aBuilding, null ) ;
    }

    public AddressPointCheck(String aNumber) {
    	this(aNumber, null) ;
    }

    
    /** Номер дома */
    public String getNumber() { return theHouseNumber ; }

    /** Корпус */
    public String getBuilding() { return theHouseBuilding ; }

    /** Квартира */
	public String getFlat() {
		return theFlat;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder() ;
		sb.append("д. ") ;
		sb.append(theHouseNumber) ;
		if(theHouseBuilding!=null) {
			sb.append(" корпус ").append(theHouseBuilding) ;
		}
		if(theFlat!=null) {
			sb.append(" кв. ").append(theFlat);
		}
		return sb.toString();
	}

	/** Квартира */
	private final String theFlat;
    /** Корпус */
    private final String theHouseBuilding ;
    /** Номер дома */
    private final String theHouseNumber ;
}
