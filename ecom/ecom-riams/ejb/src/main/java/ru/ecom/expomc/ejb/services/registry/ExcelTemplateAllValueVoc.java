package ru.ecom.expomc.ejb.services.registry;

import ru.ecom.ejb.services.voc.helper.ArrayAllValue;

/**
 * Группы для печати реестров
 */
public class ExcelTemplateAllValueVoc extends ArrayAllValue {
    public ExcelTemplateAllValueVoc() {
        addValue("registry.xls", "Круглосуточный стационар");
        addValue("registry_ds.xls", "Дневной стационар"); 
        addValue("registry_poly.xls", "Поликлиника"); 
    }

}
