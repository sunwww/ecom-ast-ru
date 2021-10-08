package ru.ecom.expomc.ejb.services.sync;

/**
 *
 */
public interface ISync {

    void sync(SyncContext aContext) throws Exception;
}
