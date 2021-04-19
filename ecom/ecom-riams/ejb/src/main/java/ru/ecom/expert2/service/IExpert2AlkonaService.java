package ru.ecom.expert2.service;

public interface IExpert2AlkonaService {

    /**Отправляем все случаи по ОМС в алькону как выписки*/
    void exportHospLeaveToAlkona(Long entryListId);

    /** Отправляем все госпитализации в алькону*/
    void exportHospToAlkona(Long entryListId, Boolean isEmergency);
}
