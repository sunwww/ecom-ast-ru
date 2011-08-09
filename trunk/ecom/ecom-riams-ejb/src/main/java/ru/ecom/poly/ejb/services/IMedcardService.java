package ru.ecom.poly.ejb.services;

import java.util.List;

import ru.ecom.poly.ejb.form.MedcardForm;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 25.01.2007
 * Time: 21:55:28
 * To change this template use File | Settings | File Templates.
 */
public interface IMedcardService {
    List<MedcardForm> findMedCard(String aNumber);
	String getNewMedcardNumber();
}
