package ru.ecom.expomc.ejb.services.messages;

import java.util.Collection;
import java.util.LinkedList;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;
import ru.ecom.expomc.ejb.domain.message.Message;
import ru.ecom.expomc.ejb.domain.message.MessageChange;
import ru.ecom.expomc.ejb.services.check.ICheck;
import ru.ecom.expomc.ejb.services.check.ICheckServiceLocal;
import ru.ecom.expomc.ejb.services.voc.allvalues.AllowedChecksAllValues;

/**
 * Сообщения о проверках
 */
@Stateless
@Remote(ICheckMessageService.class)
public class CheckMessageServiceBean implements ICheckMessageService {

	public long getCheckByMessage(long aMessageId) {
        Message message = theManager.find(Message.class, aMessageId) ;
        long checkId = message.getCheck().getId() ;
        return checkId ;
	}
	
    public Collection<String> getBadProperties(long aMessageId) {
        Message message = theManager.find(Message.class, aMessageId) ;
        long checkId = message.getCheck().getCheckId() ;
        Class checkClass = theAllowedChecksAllValues.getCheckClassById(checkId) ;
        Object obj;
		try {
			obj = checkClass.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}
        ICheck check = (ICheck) obj ;
        try {
			theCheckService.setCheckProperties(message.getCheck(), check) ;
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}
        return check.getBadProperties() ;
        
    }
    
    public Collection<MessageRow> listMessage(long aTimeId) {
        ImportTime time = theManager.find(ImportTime.class, aTimeId) ;
        LinkedList<MessageRow> ret = new LinkedList<MessageRow>();
        for (Message message : time.getMessages()) {
            MessageRow row = new MessageRow();
            row.setId(message.getId());
            row.setName(message.getCheck().getName()) ;
            row.setComment(message.getCheck().getComment()) ;
            row.setCheckTypeName(message.getCheck().getCheckTypeName()) ;
            ret.add(row) ;
        }
        return ret ;
//        return theManager.createQuery("from Message c where time = :timeId").setParameter("timeId", aTimeId ).getResultList() ;
    }

    public Object loadEntityByMessage(long aMessage) {

        Message message = theManager.find(Message.class, aMessage) ;
        String className = message.getCheck().getDocument().getEntityClassName() ;
        try {
            Class clazz = ClassLoaderHelper.getInstance().loadClass(className) ;
            return theManager.find(clazz, message.getDataId()) ;
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка загрузки: "+e,e) ;
        }
    }

    public Collection<MessageChangeRow> listChanges(long aMessage) {
        Message message = theManager.find(Message.class, aMessage) ;
        LinkedList<MessageChangeRow> ret = new LinkedList<MessageChangeRow>();

        for (MessageChange change : message.getChanges()) {
            MessageChangeRow row = new MessageChangeRow();
            row.setProperty(change.getPropertyName());
            row.setOldValue(change.getOldValue());
            row.setNewValue(change.getNewValue());
            row.setMessageId(aMessage);
            ret.add(row) ;
        }
        return ret ;
    }

    private final AllowedChecksAllValues theAllowedChecksAllValues = new AllowedChecksAllValues();
    
    @PersistenceContext
    private EntityManager theManager ;
    private @EJB ICheckServiceLocal theCheckService ;
}
