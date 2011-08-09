package ru.ecom.expomc.ejb.services.importservice.factory;

import ru.ecom.expomc.ejb.domain.omcvoc.OmcRayon;
import ru.ecom.expomc.ejb.services.importservice.IImportEntityFactory;

/**
 * @author esinev 20.08.2006 22:31:35
 */
public class SimpleFactory implements IImportEntityFactory {

    public Object createNewEntity() {
        return new OmcRayon();
    }

}
