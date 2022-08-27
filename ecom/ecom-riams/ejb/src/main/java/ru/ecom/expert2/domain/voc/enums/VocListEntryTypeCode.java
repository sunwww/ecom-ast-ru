package ru.ecom.expert2.domain.voc.enums;

public enum VocListEntryTypeCode {
    POLYCLINICKDO, //DEPRECATED
    HOSPITAL, //случаи стационарного лечения
    VMP,
    POL_KDP,  //КДП более нету, теперь всё - неотложка. Передалеть на поликлинику
    POLYCLINICCOVIDTYPE,
    EXTDISP,
    POLYCLINIC,
    SERVICE,
    COVID_TEMP
}
