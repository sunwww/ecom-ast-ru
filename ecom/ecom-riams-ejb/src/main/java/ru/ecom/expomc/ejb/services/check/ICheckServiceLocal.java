package ru.ecom.expomc.ejb.services.check;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import ru.ecom.expomc.ejb.domain.check.Check;

public interface ICheckServiceLocal {
    void setCheckProperties(Check aCheck, ICheck aChecker) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ParseException ;

}
