package ru.ecom.expomc.ejb.services.registry.voc;

import ru.ecom.ejb.services.voc.helper.ArrayAllValue;

/**
 * Форматы файлов для экспорта
 */
public class ListDataExportFileFormatAllValue extends ArrayAllValue {
    public ListDataExportFileFormatAllValue() {
        addValue("dbf", "DBF dBase 4.x");
        addValue("xls", "XLS Microsoft Excel");
    }
}
