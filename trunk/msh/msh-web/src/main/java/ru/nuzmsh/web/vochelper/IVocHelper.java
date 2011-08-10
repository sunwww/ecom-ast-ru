package ru.nuzmsh.web.vochelper;

import javax.servlet.jsp.PageContext;

import ru.nuzmsh.util.voc.VocAdditional;

/**
 * Сервис для работы со справочниками
 */
public interface IVocHelper {

    /**
     * Найти название по идентификатору
     * @param aPageContext
     * @param aId
     * @param aVocName
     * @return название
     */
    public String getNameById(PageContext aPageContext, String aId, String aVocName, VocAdditional aVocAdditional) ;

}
