package ru.ecom.mis.ejb.service.medcase;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AddressSheetPrintForm implements Serializable {
    /**
     * Дата составления 1
     */
    private String compilationDate;
    /**
     * Документ 1
     */
    private String document;
    /**
     * Национальность 1
     */
    private String nationality;
    /**
     * Место рождения 1
     */
    private String birthPlace;
    /**
     * Пол 1
     */
    private String sex;
    /**
     * Дата рождения 1
     */
    private String birthday;
    /**
     * Отчество 1
     */
    private String middlename;
    /**
     * Имя 1
     */
    private String firstname;
    /**
     * Фамилия
     */
    private String lastname;
    /**
     * Sn
     */
    private String sn;
    /**
     * Дата выписки 1
     */
    private String dischargeDate;
    /**
     * Адрес 1
     */
    private String address;


}
