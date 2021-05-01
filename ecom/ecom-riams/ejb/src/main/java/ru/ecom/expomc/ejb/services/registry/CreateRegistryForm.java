package ru.ecom.expomc.ejb.services.registry;


import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.fields.ComboBox;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@Getter
@Setter
@Deprecated //непонятно что за штука
public class CreateRegistryForm extends BaseValidatorForm {

	/** Печать DBF*/
    public static final int EXPORT_EXCEl = 1 ;
    public static final int EXPORT_DBF = 2 ;
    
    /** Шаблон */
    @Required
    @ComboBox(voc="regExcelTemplate")
public String getTemplate() { return template ; }

/** Тип экспорта */
@Required
@ComboBox(voc="regExportType")
public int getExportType() { return exportType ; }

/** Тип экспорта */
private int exportType ;
/** Шаблон */
private String template ;
    /** Документ */
	public long getDocumentId() {
		return documentId;
	}

    /** Время импорта */
    @ComboBox(voc="timeByDocument")
    @Required
    public long getTimeImport() { return timeImport ; }

    /** Дневной стационар */
    @ComboBox(voc="timeByDocument")
	public long getTimeImportDs() { return timeImportDs ; }

    /** Страховая компания */
    @ComboBox(voc="timeOmcSk")
    public String getCompany() { return company ; }

	/** Реестр для подростков */
	private String child50;
	/** Реестр для детей  55 */
	private String child55;

	/** Счет для работающих */
	private String billWorking ;

	/** Счет для подростков */
	private String billChild50;
	/** Счет для детей (55) */
	private String billChild55;
    /** Формат экспорта */
	@Required
    @ComboBox(voc="formatByDocument")
	public long getFormat() { return format ; }

	/** Формат экспорта */
	private long format ;
    /** Дата счета */
    @DoDateString
    @DateString
    @Required
    public String getBillDate() { return billDate ; }

    /** Дата счета */
    private String billDate ;
    /** Реестр для детей */
    private String regChild ;
    /** Реестр для пенсионеров */
    private String regPen ;
    /** Реестр для работающих */
    private String regWorking ;
    /** Номер счета */
    private String billNumber ;
    /** Группа */
    private int group ;
    /** Реестр для прочих групп неработающих */
    private String regOther ;
    /** Страховая компания */
    private String company ;
	/** Документ */
	private long documentId;
	/** Счет для неработ */
	private String billOther ;
	/** Счет для детей */
	private String child ;
	/** Счет для Пенсионеров */
	private String billPens ;
	/** Дневной стационар */
	private long timeImportDs ;
    /** Время импорта */
    private long timeImport ;
}
