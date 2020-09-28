package ru.ecom.address.ejb.service;

import ru.ecom.address.ejb.domain.address.Address;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 21.11.2006
 * Time: 6:19:46
 * To change this template use File | Settings | File Templates.
 */
public interface ILocalAddressService {
    Address findAddressByKladr(String aKladrCode) ;
    Address getAddressForLevel(long aDomain, Address aAddress) ;
    
}
