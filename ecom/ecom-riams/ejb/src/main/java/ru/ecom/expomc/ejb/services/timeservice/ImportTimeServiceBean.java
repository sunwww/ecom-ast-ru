package ru.ecom.expomc.ejb.services.timeservice;

import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;
import ru.nuzmsh.util.PropertyUtil;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

/**
 *
 */
@Stateless
@Local(IImportTimeService.class)
public class ImportTimeServiceBean implements IImportTimeService {

    @Override
    public String getName(Class aClass, String aCode, Date aActualDate) {
        Object o = findByCode(aClass, aCode, aActualDate);
        if (o != null) {
            try {
                return (String) PropertyUtil.getPropertyValue(o, "name");
            } catch (Exception e) {
                throw new IllegalStateException("Ошибка получения наименования по коду", e);
            }
        } else {
            return null;
        }
    }

    @Override
    public <T> T findByCode(java.lang.Class<T> aClass, String aCode, Date aActualDate) {
        List<T> found = manager.createQuery("from " + aClass.getSimpleName() +
                " where code = :code and time=:time")
                .setParameter("code", aCode)
                .setParameter("time", findTime(aClass, aActualDate))
                .getResultList();
        if (!isEmpty(found)) {
            return found.iterator().next();
        } else {
            return null;
        }
    }

    @Override
    public long findTime(Class aClass, java.util.Date aActualDate) {
        List<ImportDocument> docs = manager.createNamedQuery("ImportDocument.findByName")
                .setParameter("entityClassName", aClass.getName()).getResultList();
        if (isEmpty(docs)) {
            throw new IllegalArgumentException("Не зарегистрирован документ класса " + aClass.getName());
        }
        ImportDocument doc = docs.iterator().next();
        if (doc == null) {
            throw new IllegalArgumentException("Не зарегистрирован документ класса " + aClass.getName());
        }
        Collection<ImportTime> times = doc.getTimes();
        if (isEmpty(times)) {
            throw new IllegalStateException("Нет данных по документу " + doc.getComment());
        }
        // todo брать актуальные
        long id = -1;
        for (ImportTime importTime : times) {
            id = importTime.getId();
        }
        return id;
    }

    private boolean isEmpty(Collection aCollection) {
        return aCollection == null || aCollection.isEmpty();
    }

    @PersistenceContext
    private EntityManager manager;

}
