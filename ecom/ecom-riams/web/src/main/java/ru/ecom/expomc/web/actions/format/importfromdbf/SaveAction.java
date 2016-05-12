package ru.ecom.expomc.web.actions.format.importfromdbf;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.expomc.ejb.domain.format.Field;
import ru.ecom.expomc.ejb.services.form.format.FieldForm;
import ru.ecom.expomc.ejb.services.form.format.IFormatService;
import ru.ecom.expomc.ejb.services.form.format.PropertySuggest;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.ForwardUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.dbf.DbfFile;
import ru.nuzmsh.util.dbf.DbfField;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * Импорт из DBF
 */
public class SaveAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        IParentEntityFormService service = EntityInjection.find(aRequest).getParentEntityFormService();
        IFormatService formatService = Injection.find(aRequest).getService(IFormatService.class) ;

        DbfImportForm form = (DbfImportForm) aForm ;
        DbfFile dbfFile = new DbfFile();
        dbfFile.load(form.getFile().getInputStream());
        long formatId = form.getId();
        int index = 0 ;
        for (DbfField field : dbfFile.getDbfFields()) {
            if (!form.getDoNotImportWhatWhen() || !isWhatWhenEtcField(field.getName())) {
                index++ ;
                FieldForm fieldForm = service.prepareToCreate(FieldForm.class, form.getId());
                fieldForm.setSerialNumber(index);
                fieldForm.setFormat(formatId);
                fieldForm.setDbfSize(field.getLength());
                fieldForm.setDbfDecimal(field.getDecimalLength());
                fieldForm.setName(field.getName());
                fieldForm.setDbfType(getDbfType(field));
                PropertySuggest suggest = formatService.findPropertySuggest(field.getName(), formatId);
                if(suggest!=null) {
                    fieldForm.setProperty(suggest.getProperty());
                    fieldForm.setComment(suggest.getComment());
                }
                service.create(fieldForm) ;
            }
        }

        return ForwardUtil.createIdRedirectForward(aMapping.findForward("success"), form.getId()) ;
    }

    private boolean isWhatWhenEtcField(String aFieldName) {
       aFieldName = aFieldName.toUpperCase();
    	return aFieldName.equals("WHAT")
                || aFieldName.equals("WHEND")
                || aFieldName.equals("WHENT")
                || aFieldName.equals("WHO") ;
    }

    private int getDbfType(DbfField aField) {
        switch(aField.getType()) {
            case DbfField.CHAR: return Field.TEXT ;
            case DbfField.DATE: return Field.DATE ;
            case DbfField.NUMERIC: return Field.NUMERIC ;
            case DbfField.BOOLEAN: return Field.BOOLEAN ;
            //case 'L':return Field.BOOLEAN;
        }
        throw new IllegalArgumentException("Неизвестный тип поля: "+aField.getType()+" "+aField) ;
    }

}
