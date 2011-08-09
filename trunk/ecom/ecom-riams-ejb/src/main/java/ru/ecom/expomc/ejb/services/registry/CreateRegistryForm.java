package ru.ecom.expomc.ejb.services.registry;


import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.fields.ComboBox;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

public class CreateRegistryForm extends BaseValidatorForm {

	/** Печать DBF*/
    public static final int EXPORT_EXCEl = 1 ;
    public static final int EXPORT_DBF = 2 ;
    
//    public static final int TEMPLATE_24HOUR = 3 ;
//    public static final int TEMPLATE_DAILY = 4 ;

    /** Шаблон */
    @Required
    @ComboBox(voc="regExcelTemplate")
public String getTemplate() { return theTemplate ; }
public void setTemplate(String aTemplate) { theTemplate = aTemplate ; }

/** Тип экспорта */
@Required
@ComboBox(voc="regExportType")
public int getExportType() { return theExportType ; }
public void setExportType(int aExportType) { theExportType = aExportType ; }

/** Тип экспорта */
private int theExportType ;
/** Шаблон */
private String theTemplate ;
/*    public ActionErrors validate(ActionMapping aMapping, HttpServletRequest aRequest) {
        ActionErrors errors =  super.validate(aMapping, aRequest);
        if(theGroup==GROUP_ALL) {
            if(StringUtil.isNullOrEmpty(theRegPen)) {
                errors.add("regPens", new ActionMessage("При экспорте DBF поле обязательное"));
            }
            if(StringUtil.isNullOrEmpty(theRegWorking)) {
                errors.add("regWorking", new ActionMessage("При экспорте DBF поле обязательное"));
            }
            if(StringUtil.isNullOrEmpty(theRegChild)) {
                errors.add("regChild", new ActionMessage("При экспорте DBF поле обязательное"));
            }
            if(StringUtil.isNullOrEmpty(theRegOther)) {
                errors.add("regOther", new ActionMessage("При экспорте DBF поле обязательное"));
            }
        }
        return errors ;
    }
*/
    /** Документ */
	public long getDocumentId() {
		return theDocumentId;
	}

	public void setDocumentId(long aDocumentId) {
		theDocumentId = aDocumentId;
	}


    /** Время импорта */
    @ComboBox(voc="timeByDocument")
    @Required
    public long getTimeImport() { return theTimeImport ; }
    public void setTimeImport(long aTimeImport) { theTimeImport = aTimeImport ; }

    /** Дневной стационар */
    @ComboBox(voc="timeByDocument")
	public long getTimeImportDs() { return theTimeImportDs ; }
	public void setTimeImportDs(long aTimeImportDs) { theTimeImportDs = aTimeImportDs ; }
	
    /** Страховая компания */
    @ComboBox(voc="timeOmcSk")
    public String getCompany() { return theCompany ; }
    public void setCompany(String aCompany) { theCompany = aCompany ; }

    /** Группа */
//    @ComboBox(voc="regExportGroup")
//    public int getGroup() { return theGroup ; }
//    public void setGroup(int aGroup) { theGroup = aGroup ; }

//    /** Номер счета */
//    public String getBillNumber() { return theBillNumber ; }
//    public void setBillNumber(String aBillNumber) { theBillNumber = aBillNumber ; }

    // реестры
    //
    //
    /** Реестр для работающих */
    public String getRegWorking() { return theRegWorking ; }
    public void setRegWorking(String aRegWorking) { theRegWorking = aRegWorking ; }

    /** Реестр для пенсионеров */
    public String getRegPens() { return theRegPen ; }
    public void setRegPens(String aRegPen) { theRegPen = aRegPen ; }

    /** Реестр для детей  55 */
	@Comment("Реестр для детей  55")
	public String getChild55() {
		return theChild55;
	}

	public void setChild55(String aChild55) {
		theChild55 = aChild55;
	}

	/** Реестр для подростков */
	@Comment("Реестр для подростков")
	public String getChild50() {
		return theChild50;
	}

	public void setChild50(String aChild50) {
		theChild50 = aChild50;
	}

	/** Реестр для подростков */
	private String theChild50;
	/** Реестр для детей  55 */
	private String theChild55;
    /** Реестр для детей */
    public String getRegChild() { return theRegChild ; }
    public void setRegChild(String aRegChild) { theRegChild = aRegChild ; }

    /** Реестр для прочих групп неработающих */
    public String getRegOther() { return theRegOther ; }
    public void setRegOther(String aRegOther) { theRegOther = aRegOther ; }

    // счета
    //
    //
    //
    /** Счет для работающих */
	public String getBillWorking() { return theBillWorking ; }
	public void setBillWorking(String aBillWorking) { theBillWorking = aBillWorking ; }
	
	/** Счет для работающих */
	private String theBillWorking ;
	
	/** Счет для Пенсионеров */
	public String getBillPens() { return theBillPens ; }
	public void setBillPens(String aBillPens) { theBillPens = aBillPens ; }
	
	/** Счет для детей */
	public String getBillChild() { return theChild ; }
	public void setBillChild(String aChild) { theChild = aChild ; }
	
	/** Счет для неработ */
	public String getBillOther() { return theBillOther ; }
	public void setBillOther(String aBillOther) { theBillOther = aBillOther ; }
	
    /** Счет для детей (55) */
	@Comment("Счет для детей (55)")
	public String getBillChild55() {
		return theBillChild55;
	}

	public void setBillChild55(String aBillChild55) {
		theBillChild55 = aBillChild55;
	}

	/** Счет для подростков */
	@Comment("Счет для подростков")
	public String getBillChild50() {
		return theBillChild50;
	}

	public void setBillChild50(String aBillChild50) {
		theBillChild50 = aBillChild50;
	}

	/** Счет для подростков */
	private String theBillChild50;
	/** Счет для детей (55) */
	private String theBillChild55;
    /** Формат экспорта */
	@Required
    @ComboBox(voc="formatByDocument")
	public long getFormat() { return theFormat ; }
	public void setFormat(long aFormat) { theFormat = aFormat ; }
	
	/** Формат экспорта */
	private long theFormat ;
    /** Дата счета */
    @DoDateString
    @DateString
    @Required
    public String getBillDate() { return theBillDate ; }
    public void setBillDate(String aBillDate) { theBillDate = aBillDate ; }

    /** Дата счета */
    private String theBillDate ;
    /** Реестр для детей */
    private String theRegChild ;
    /** Реестр для пенсионеров */
    private String theRegPen ;
    /** Реестр для работающих */
    private String theRegWorking ;
    /** Номер счета */
    private String theBillNumber ;
    /** Группа */
    private int theGroup ;
    /** Реестр для прочих групп неработающих */
    private String theRegOther ;
    /** Страховая компания */
    private String theCompany ;
	/** Документ */
	private long theDocumentId;
	/** Счет для неработ */
	private String theBillOther ;
	/** Счет для детей */
	private String theChild ;
	/** Счет для Пенсионеров */
	private String theBillPens ;
	/** Дневной стационар */
	private long theTimeImportDs ;
    /** Время импорта */
    private long theTimeImport ;
}
