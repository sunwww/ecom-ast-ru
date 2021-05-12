package ru.ecom.address.ejb.service;

/**
 *
 */
public class AddressPointCheck {

    public AddressPointCheck(String aNumber, String aBuilding, String aFlat) {
        if (aNumber == null) throw new IllegalArgumentException("Нет номера дома");
        houseNumber = aNumber;
        houseBuilding = aBuilding;
        flat = aFlat;
    }

    public AddressPointCheck(String aNumber, String aBuilding) {
        this(aNumber, aBuilding, null);
    }

    public AddressPointCheck(String aNumber) {
        this(aNumber, null);
    }


    /**
     * Номер дома
     */
    public String getNumber() {
        return houseNumber;
    }

    /**
     * Корпус
     */
    public String getBuilding() {
        return houseBuilding;
    }

    /**
     * Квартира
     */
    public String getFlat() {
        return flat;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("д. ");
        sb.append(houseNumber);
        if (houseBuilding != null) {
            sb.append(" корпус ").append(houseBuilding);
        }
        if (flat != null) {
            sb.append(" кв. ").append(flat);
        }
        return sb.toString();
    }

    /**
     * Квартира
     */
    private final String flat;
    /**
     * Корпус
     */
    private final String houseBuilding;
    /**
     * Номер дома
     */
    private final String houseNumber;
}
