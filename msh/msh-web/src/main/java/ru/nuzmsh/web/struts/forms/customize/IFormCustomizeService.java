package ru.nuzmsh.web.struts.forms.customize;

import java.util.Collection;

import ru.nuzmsh.commons.auth.ILoginInfo;

/**
 * Изменение вида формы (STAC-124)
 */
public interface IFormCustomizeService {

    void start() ;
    void stop() ;

    FormElementInfo findFormElementInfo(ILoginInfo aLoginInfo, String aFormName, String aElementName) ;

    void saveFormElementInfo(ILoginInfo aLoginInfo, String aFormName, FormElementInfo aInfo) ;

    /** Список измененных форм */
    Collection<FormInfo> listCustomizedForms() throws FormCustomizeException ;

    Collection<FormElementInfo> listCustomizedElements(String aFormName) throws FormCustomizeException ;
}
