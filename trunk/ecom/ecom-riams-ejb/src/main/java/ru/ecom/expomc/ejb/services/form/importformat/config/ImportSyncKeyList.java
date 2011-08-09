/**
 * Список ключей для синхронизации записи
 * @author ikouzmin 15.03.2007 13:56:23
 */
package ru.ecom.expomc.ejb.services.form.importformat.config;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

public class ImportSyncKeyList {
    List<ImportSyncKey> theKeys;

    ImportSyncKeyList(ImportEntity entity, EntityManager theManager)  {
        theKeys = entity.getKeyQueries(theManager);
    }

    @Deprecated
    ImportSyncKeyList(String entityName, String aProperties, String aSelects, EntityManager aManager)  {
        theKeys = new ArrayList<ImportSyncKey>();
        String[] properties = aProperties.split(";");
        String[] selects = aSelects.split(";");

        for (int i=0; i<properties.length; i++) {
            ImportSyncKey importSyncKey = new ImportSyncKey();
            importSyncKey.setProperty(properties[i]);
            importSyncKey.setSelect(selects[i]);
            theKeys.add(importSyncKey);
        }
    }

    public String findId(Object anElement) {
        for (int j = 0; j < theKeys.size(); j++) {
            ImportSyncKey importSyncKey = theKeys.get(j);
            String id = importSyncKey.findIdObject(anElement);
            if (id != null) return id;
        }
        return null;
    }

    //= entity.getKeyQueries(theManager);
}

