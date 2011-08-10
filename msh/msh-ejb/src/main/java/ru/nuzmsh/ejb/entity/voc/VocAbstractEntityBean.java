package ru.nuzmsh.ejb.entity.voc;

import ru.nuzmsh.logicpool.api.ILogic;
import ru.nuzmsh.logicpool.api.LogicException;
import ru.nuzmsh.forms.autocomplete.IAutocompletable;
import ru.nuzmsh.ejb.exceptions.FinderExceptionCause;
import ru.nuzmsh.ejb.exceptions.CreateExceptionCause;
import ru.nuzmsh.util.StringUtil;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * @ejb.bean
 *            name="VocAbstractEntity"
 *            local-jndi-name="ejb/stac/VocAbstractEntity"
 *            type="BMP"
 *            view-type="local"
 *            generate="false"
 *
 * @ejb.home
 *            generate="false"
 *
 * @ejb.interface
 *            generate="false"
 *
 * @author ESinev
 *         Date: 01.11.2005
 *         Time: 11:20:34
 */
public abstract class VocAbstractEntityBean implements EntityBean, IAutocompletable {

    private final static Logger LOG = Logger.getLogger(VocAbstractEntityBean.class);


    protected abstract ILogic getLogic(EntityContext aContext) throws LogicException;

    protected abstract void closeEjb(ILogic aLogic) throws EJBException;

//    public VocAbstractEntityBean() {
//        theClassName = "NO_CLASS" ;
//        theFieldName = "NO_FIELD" ;
//    }

    public String getId() {
        return getEntityContext().getPrimaryKey().toString();
    }

    public VocAbstractEntityBean(String aVocName) {
        theVocName = aVocName;
        LOG.debug("VocAbstractEntityBean.init(): aVocName = " + aVocName);
//      String whosRefer = logic.getString("Voc", lpuId, "name", "Voc:^ZVocStac(\""+VocStacLUchConst.NAME+"\")") ;
    }


    public void ejbStore() throws EJBException, RemoteException {
        if (theCanSave) {
            String pk = getEntityContext().getPrimaryKey().toString();
            ILogic logic = null;
            try {
                logic = getLogic(getEntityContext());
                logic.setString("Voc", pk, "name", theName, "Voc:^ZVocStac(\"" + theVocName + "\"):0");
                logic.setString("Voc", pk, "omcCode", theOmcCode, "Voc:^ZVocStac(\"" + theVocName + "\"):0");
                logic.setString("Voc", pk, "isArchive"
                        , theArchive?"1":"2"
                        , "Voc:^ZVocStac(\"" + theVocName + "\"):0");
                theCanSave = false ;
            } catch (LogicException e) {
                throw new EJBException(e.getMessage());
            } finally {
                closeEjb(logic);
            }
        }
    }

    public void ejbLoad() throws EJBException, RemoteException {
        String pk = getEntityContext().getPrimaryKey().toString();
        ILogic logic = null;
        try {
            logic = getLogic(getEntityContext());
            theName = logic.getString("Voc", pk, "name", "Voc:^ZVocStac(\"" + theVocName + "\"):0");
            theOmcCode = logic.getString("Voc", pk, "omcCode", "Voc:^ZVocStac(\"" + theVocName + "\"):0");
            String archive = logic.getString("Voc", pk, "isArchive", "Voc:^ZVocStac(\"" + theVocName + "\"):0");
            theArchive = "1".equals(archive) ;
            theCanSave = false;
        } catch (LogicException e) {
            throw new EJBException(e.getMessage());
        } finally {
            closeEjb(logic);
        }
    }

    // todo
    protected java.lang.String helperEjbFindByPrimaryKey(java.lang.String aPk) throws FinderException {
        if(LOG.isDebugEnabled()) LOG.debug("aPk = '" + aPk + "'");

        ILogic logic = null;
        String ret;
        try {
            logic = getLogic(getEntityContext());
            boolean finded = logic.isExists("Voc", aPk, "Voc:^ZVocStac(\"" + theVocName + "\"):0");
            ret = finded ? aPk : null;
        } catch (LogicException e) {
            throw new EJBException(e.getMessage());
        } finally {
            closeEjb(logic);
        }
        return ret;
    }

    protected java.lang.Long helperEjbFindByPrimaryKey(java.lang.Long aPk) throws FinderException {
        if (aPk == null) {
            return null;
        } else {
            return helperEjbFindByPrimaryKey(aPk.toString()) != null ? aPk : null;
        }
    }

//    public abstract java.lang.String ejbFindByPrimaryKey(java.lang.String aPk) throws FinderException ;
//    public abstract java.lang.Long ejbFindByPrimaryKey(java.lang.Long aPk) throws FinderException ;

    public Collection findFromFirst(String aSkipped, int aCount) throws FinderException {
        return ejbFindFromFirst(aSkipped, aCount);
    }

    /**
     * @ejb.finder
     */
    public Collection ejbFindFromFirst(String aSkipped, int aCount) throws FinderException {
        ILogic logic = null;
        Collection ret;
        try {
            logic = getLogic(getEntityContext());
            String firstId = logic.getFirstId("Voc", "Voc:^ZVocStac(\"" + theVocName + "\"):0");
            LOG.debug("firstId = " + firstId);
            ret = ejbFindFromNext(firstId, aCount);
        } catch (LogicException e) {
            throw new FinderExceptionCause("Ошибка получения первого идентификатора справочника " + theVocName, e);
        } finally {
            closeEjb(logic);
        }
        return ret;
    }

    public Collection helperFindFrom(String aFromPk, int aCount, final boolean aForwardDirection) throws FinderException {
        if (aFromPk == null) throw new FinderException("Идентификатор не может быть пустым: aFromPk=" + aFromPk);
        if (aCount <= 0) throw new FinderException("Количество выбранных записей должно быть больше нуля. aCount=" + aCount);
        if (helperEjbFindByPrimaryKey(aFromPk) == null) throw new FinderException("Справочника с идентификатором '" + aFromPk + "' не найдено");

        ArrayList list = new ArrayList();
        String id = aFromPk;
        ILogic logic = null;
        try {
            logic = getLogic(getEntityContext());
            for (int i = 0; i < aCount && id != null; i++) {
                if (aForwardDirection) {
                    list.add(id);
                } else {
                    list.add(0, id);
                }

                id = aForwardDirection
                        ? logic.getNextId("Voc", id, "Voc:^ZVocStac(\"" + theVocName + "\"):0")
                        : logic.getPreviousId("Voc", id, "Voc:^ZVocStac(\"" + theVocName + "\"):0");
            }

        } catch (Exception e) {
            throw new FinderExceptionCause("Ошибка перебора справочника " + theVocName, e);
        } finally {
            closeEjb(logic);
        }
        return list;
    }

    public Collection findFromPrevious(String aFromPk, int aCount) throws FinderException {
        return ejbFindFromPrevious(aFromPk, aCount);
    }

    public Collection ejbFindFromPrevious(String aFromPk, int aCount) throws FinderException {
        return helperFindFrom(aFromPk, aCount, false);
    }

    public Collection findFromNext(String aFromPk, int aCount) throws FinderException {
        return ejbFindFromNext(aFromPk, aCount);
    }

    public Collection ejbFindFromNext(String aFromPk, int aCount) throws FinderException {
        return helperFindFrom(aFromPk, aCount, true);
    }

    public Collection findLikeByPrimaryKey(String aQuery, int aCount) throws FinderException {
        return ejbFindLikeByPrimaryKey(aQuery, aCount);
    }

    public Collection ejbFindLikeByPrimaryKey(String aQuery, int aCount) throws FinderException {
        String query = aQuery.toUpperCase();
        ILogic logic = null;
        Collection ret;
        try {
            logic = getLogic(getEntityContext());

            String id = logic.getPreviousId("Voc", query, "Voc:^ZVocStac(\"" + theVocName + "\"):0");

            id = logic.getNextId("Voc", id, "Voc:^ZVocStac(\"" + theVocName + "\"):0");

            ret = id != null
                    ? ejbFindFromNext(id, aCount)
                    : ejbFindFromFirst(null, aCount);

        } catch (LogicException e) {
            throw new FinderExceptionCause("Ошибка получения первого идентификатора справочника " + theVocName, e);
        } finally {
            closeEjb(logic);
        }
        return ret;
    }

    public Collection findByQuerySlow(String aQuery, int aCount) throws FinderException {
        return ejbFindByQuerySlow(aQuery, aCount);
    }

    public Collection ejbFindByQuerySlow(String aQuery, int aCount) throws FinderException {
        String query = aQuery.toUpperCase();
        ILogic logic = null;
        Collection ret;
        try {
            logic = getLogic(getEntityContext());
            String id = logic.getFirstId("Voc", "Voc:^ZVocStac(\"" + theVocName + "\"):0");
            while (id != null) {
                String name = logic.getString("Voc", id, "name", "Voc:^ZVocStac(\"" + theVocName + "\"):0");
                String upperID = id.toUpperCase();
                if (upperID.startsWith(query)) {
                    break;
                }
                if (name != null) {
                    if (name.toUpperCase().startsWith(query)) {
                        break;
                    }
                }
                id = logic.getNextId("Voc", id, "Voc:^ZVocStac(\"" + theVocName + "\"):0");
            }
            ret = id != null
                    ? ejbFindFromNext(id, aCount)
                    : ejbFindFromFirst(null, aCount);

        } catch (LogicException e) {
            throw new FinderExceptionCause("Ошибка получения первого идентификатора справочника " + theVocName, e);
        } finally {
            closeEjb(logic);
        }
        return ret;
    }

    public Collection ejbFindByAll() throws FinderException {
        Collection ret = new LinkedList();
        ILogic logic = null;
        try {
            logic = getLogic(getEntityContext());
            String id = logic.getFirstId(theVocName);
            LOG.debug("fist id=" + id);
            while (id != null) {
                LOG.debug("id=" + id);
                ret.add(id);
                id = logic.getNextId(theVocName, id);
            }
        } catch (LogicException e) {
            throw new EJBException(e.getMessage(), e);
        } finally {
            closeEjb(logic);
        }
        return ret;
    }

//    public Collection ejbFindByFistSubstring(String aQuery) throws FinderException {
//        if(aQuery!=null) aQuery = aQuery.toLowerCase() ;
//        Collection ret = new ArrayList() ;
//        ILogic logic = null ;
//        try {
//            logic = StacLogicPool.getLogic(getEntityContext()) ;
//            String id = logic.getFirstId(theClassName) ;
//            while(id!=null) {
//                String value = logic.getString(theClassName, id, theFieldName) ;
//                if(value!=null) {
//                    if(value.toLowerCase().startsWith(aQuery)) {
//                        ret.add(id) ;
//                    }
//                }
//                id = logic.getNextId(theClassName, id ) ;
//            }
//
//        } catch (LogicException e) {
//            throw new EJBException(e +"", e);
//        } finally {
//            StacLogicPool.closeEjb(logic) ;
//        }
//        return ret ;
//    }


    /**
     * Название
     * @ejb.interface-method
     */
    public void setName(String aName) {
        theCanSave = true;
        theName = aName;
    }

    /**
     * Название
     * @ejb.interface-method
     * @jboss.method-attributes read-only="true"
     *
     */
    public String getName() {
        return theName;
    }

    /**
     * Код ОМС
     *
     * @ejb.interface-method
     * @jboss.method-attributes read-only="true"
     *
     */
    public String getOmcCode() {
        return theOmcCode ;
    }

    /**
     * Код ОМС
     *
     * @ejb.interface-method
     */
    public void setOmcCode(String aOmcCode) {
        theCanSave = true;
        theOmcCode = aOmcCode ;
    }

    /**
     * Убран в архив
     *
     * @ejb.interface-method
     * @jboss.method-attributes read-only="true"
     */
    public boolean isArchive() {
        return theArchive ;
    }

    public void setArchive(boolean aArchive) {
        theCanSave = true;
        theArchive = aArchive ;
    }

    /** Убран в архив */
    private boolean theArchive ;
    ///////////////////////////////////////////////////////
    //  Интерфейс
    //
    public void setEntityContext(EntityContext aEntityContext) throws EJBException, RemoteException {
        theEntityContext = aEntityContext;
    }

    public void unsetEntityContext() throws EJBException, RemoteException {
        theEntityContext = null;
    }

    public void ejbRemove() throws RemoveException, EJBException, RemoteException {
        String pk = getEntityContext().getPrimaryKey().toString();
        ILogic logic = null;
        try {
            logic = getLogic(getEntityContext());
            logic.delete("Voc", pk, "Voc:^ZVocStac(\"" + theVocName + "\"):0");
        } catch (LogicException e) {
            throw new RemoveException(e.getMessage());
        } finally {
            closeEjb(logic);
        }
    }

    public void ejbActivate() throws EJBException, RemoteException {
    }

    public void ejbPassivate() throws EJBException, RemoteException {
    }

    protected EntityContext getEntityContext() {
        return theEntityContext;
    }

    protected Long helperEjbCreateLong() throws CreateException {
        return Long.valueOf(helperEjbCreateString()) ;
    }
    
    protected String helperEjbCreateString() throws CreateException {
        ILogic logic = null;
        long ret;
        try {
            logic = getLogic(getEntityContext());
            String lastId = logic.getLastId("Voc", "Voc:^ZVocStac(\"" + getVocName() + "\")");
            if (StringUtil.isNullOrEmpty(lastId)) {
                ret = 1;
            } else {
                try {
                    ret = Long.parseLong(lastId) + 1;
                } catch (Exception e) {
                    throw new IllegalStateException("Невозможно преобразовать последний идентификатор '" + lastId + "' в число");
                }
            }
        } catch (LogicException e) {
            throw new EJBException("Ошибка при выборе всех значений", e);
        } finally {
            closeEjb(logic);
        }
        try {
            if (helperEjbFindByPrimaryKey(ret + "") != null) {
                throw new IllegalStateException("С идентификатором " + ret + " уже есть объект " + getVocName());
            }
        } catch (FinderException e) {
            throw new CreateExceptionCause("Ошибка создания нового объекта " + getVocName(), e);
        }
        return ret + "";
    }



    protected String getVocName() {
        return theVocName;
    }

    private EntityContext theEntityContext;

    /** Название */
    private String theName;
    private boolean theCanSave = false;

    private final String theVocName;
    /** Код ОМС */
    private String theOmcCode ;

//    private VocValueMutable theValueObject = null ;
}
