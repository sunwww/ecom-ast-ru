package ru.ecom.expomc.ejb.services.registry;

import ru.ecom.ejb.services.voc.helper.ArrayAllValue;

/**
 * Группы для печати реестров
 */
public class GroupAllValueVoc extends ArrayAllValue {
    public GroupAllValueVoc() {
        addValue(String.valueOf(CreateRegistryForm.EXPORT_EXCEl), "Экспорт в Excel");
        addValue(String.valueOf(CreateRegistryForm.EXPORT_DBF), "Экспорт в DBF"); 
//        addValue(String.valueOf(CreateRegistryForm.GROUP_PENS), "Неработающие пенсионеры и инвалиды пенсионного возраста");
//        addValue(String.valueOf(CreateRegistryForm.GROUP_CHILD), "Дети и подростки");
//        addValue(String.valueOf(CreateRegistryForm.GROUP_OTHER), "Прочие группы неработающих");
    }

}
