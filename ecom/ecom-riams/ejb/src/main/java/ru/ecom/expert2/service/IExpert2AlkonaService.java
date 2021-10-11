package ru.ecom.expert2.service;

public interface IExpert2AlkonaService {

    /**Отправляем все случаи по ОМС в алькону как выписки*/
    void exportHospLeaveToAlkona(Long entryListId);

    /**
     * Выгрузка направлений на госпитализацию самих к себе
     * @param entryListId ИД заполнения
     */
    void exportDirectionsToAlkona(Long entryListId);

    /**
     * Выгрузка одного направления на госпитализацию
     * @param entryId ИД записи
     */
    String exportDirectionToAlkona(Long entryId);

    /**Отправляем случай по ОМС в алькону как выписку*/
    String exportHospLeaveEntryToAlkona(Long entryId);

    /** Отправляем все госпитализации в алькону*/
    void exportHospToAlkona(Long entryListId, Boolean isEmergency);

    /**  Отправляем одну госпитализацию в алькону*/
    String exportHospEntryToAlkona(Long entryId);
}
