package ru.ecom.expomc.ejb.services.registry.print;

import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import ru.ecom.report.money.MoneyToString;

	/**
	 * Данные для печати реестра
	 */
    @Getter
    @Setter
	public class RegistryPrintHolder {

    /** Сумма */
    private BigDecimal summ = new BigDecimal(0);
    /** По районам */
    private List<RegistryPrintGroupHolder> regionGroups = new LinkedList<RegistryPrintGroupHolder>();
    /** Услуги */
    private List<RegistryPrintRenderHolder> renders = new LinkedList<RegistryPrintRenderHolder>();
    /** Количество записей */
    private long count ;
    /** Размер файла DBF */
    private long dbfSizeBytes ;
    /** Имя файла DBF */
    private String dbfFilename ;
    /** Период по */
    private Date periodTo ;
    /** Период с */
    private Date periodFrom ;
    /** Работающие или нет */
    private String working ;
    /** Категория ЛПУ */
    private String lpuCategoryName ;
    /** Наименование лечебного учреждения */
    private String lpuName ;
    /** Номер счета */
    private String billNumber ;
    /** Дата счета */
    private java.util.Date billDate ;
    /** Номер реестра */
    private String registryNumber ;

    private MoneyToString moneyToString = new MoneyToString();
    /** Название страховой компании */
    private String companyName ;
}
