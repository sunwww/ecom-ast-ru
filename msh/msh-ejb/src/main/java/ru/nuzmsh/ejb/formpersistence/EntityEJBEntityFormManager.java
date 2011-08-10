package ru.nuzmsh.ejb.formpersistence;

import ru.nuzmsh.commons.formpersistence.IEntityFormManager;
import ru.nuzmsh.commons.formpersistence.IEntityFormManagerContext;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.commons.formpersistence.annotation.RolesToCreate;
import ru.nuzmsh.commons.formpersistence.annotation.RolesToEdit;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormEntityEJB;
import ru.nuzmsh.util.PropertyUtil;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Id;
import javax.rmi.PortableRemoteObject;
import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.ArrayList;
import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 */
public class EntityEJBEntityFormManager implements IEntityFormManager {

    private final static Log LOG = LogFactory.getLog(EntityEJBEntityFormManager.class);
    private final static boolean CAN_TRACE = LOG.isTraceEnabled();


    public EntityEJBEntityFormManager(String aJndiPrefix) throws NamingException {
        theInitialContext = new InitialContext();
        theJndiPrefix = aJndiPrefix;
    }

    public Collection listAll(Class aClass, IEntityFormManagerContext aContext) {
        Object home = findHome(aClass) ;
        try {
            Method listAllMethod = home.getClass().getMethod("findByAll") ;
            Collection entities = (Collection)listAllMethod.invoke(home) ;
            ArrayList ret = new ArrayList();
            Constructor constructor = aClass.getConstructor() ;
            for (Object entity : entities) {
                Object form = constructor.newInstance() ;
                load(form, entity) ;
                ret.add(form) ;
            }
            return ret ;
        } catch (Exception e) {
            throw new IllegalStateException("Ошибка получения списка объектов",e) ;
        }
    }

    private static Object findByPrimaryKey(Object aHome, Object aPrimaryKey, Class aPrimayKeyClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method findByPrimaryKeyMethod = aHome.getClass().getMethod("findByPrimaryKey", aPrimayKeyClass) ;
        return  findByPrimaryKeyMethod.invoke(aHome, aPrimaryKey) ;
    }

    public void load(Object aObject, IEntityFormManagerContext aContext) {
        try {
            Object id = getId(aObject) ;
            if(id==null) {
                throw new EJBException("Нет идентификатора у объекта") ;
            } else {
                Class idClass = id.getClass() ;
                Object home = findHome(aObject.getClass()) ;
                Object ejbObject = findByPrimaryKey(home, id, idClass) ;
//                Method findByPrimaryKeyMethod = home.getClass().getMethod("findByPrimaryKey", idClass) ; // todo
//                Object ejbObject = findByPrimaryKeyMethod.invoke(home, id) ;
                if(ejbObject==null) {
                    throw new EJBException("Нет EJB объекта с идентификатором "+id) ;
                } else {
                    load(aObject, ejbObject);
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Ошибка загрузки объекта "+aObject,e) ;
        }
    }

    private void load(Object aObject, Object aEjbObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class formClass = aObject.getClass() ;
        for (Method method : formClass.getMethods()) {
            if(method.getAnnotation(Persist.class)!=null || method.getAnnotation(Id.class)!=null) {
                Method setterMethod = PropertyUtil.getSetterMethod(formClass, method) ;
                Method ejbGetterMethod = aEjbObject.getClass().getMethod(method.getName()) ;
                setterMethod.invoke(aObject, ejbGetterMethod.invoke(aEjbObject)) ;
            }
        }
    }

    public void create(Object aForm, IEntityFormManagerContext aContext) {
        try {
            Class formClass = aForm.getClass() ;
            Object home = findHome(formClass);
            Class idClass = findIdClass(formClass);
            if (idClass == null) {
                throw new IllegalArgumentException("Нет описания идентификатора. Нет ни отдного свойства с аннотацией @Id.");
            }
            Class homeClass = home.getClass();
            Method findByPrimaryKey = homeClass.getMethod("findByPrimaryKey", idClass);
            if (CAN_TRACE) LOG.trace("findByPrimaryKeyMethod = " + findByPrimaryKey);
            Object id = getId(aForm);
            if(id!=null && !id.toString().equals("")) {
                Object ejbObject ;
                try {
                    ejbObject = id != null ? findByPrimaryKey.invoke(home, getId(aForm)) : null;
                } catch (Exception e) {
                    if (CAN_TRACE) LOG.warn("EXCEPTION from findByPrimaryKeyMethod: " + e.getClass()+" "+e);
                    if (CAN_TRACE) LOG.warn("e = " + e);

                    if(e instanceof InvocationTargetException) {
                        if(e.getCause() instanceof FinderException) {
                            ejbObject = null ;
                        } else {
                            throw e;
                        }
                    } else {
                        throw e ;
                    }
                }
                if(ejbObject!=null) {
                    throw new CreateException("Объект EJB с идентификатором '"+id+"' уже существует") ;
                } else {
                    Object entityObject ;
                    try {
                        Method createMethod = homeClass.getMethod("create", idClass);
                        entityObject = createMethod.invoke(home, id);
                    } catch(NoSuchMethodException e) {
                        if (CAN_TRACE) LOG.trace("e = " + e,e);
                        Method createMethod = homeClass.getMethod("create") ;
                        entityObject = createMethod.invoke(home);
                    }
                    setProperties(aForm, entityObject);
                }
            } else {
                Method createMethod = homeClass.getMethod("create");
                Object entityObject = createMethod.invoke(home);
                setProperties(aForm, entityObject);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании нового объекта", e);
        }
    }

    public void store(Object aForm, IEntityFormManagerContext aContext) {
        try {
            Class formClass = aForm.getClass() ;
            Object home = findHome(formClass);
            Class idClass = findIdClass(formClass);
            if (idClass == null) {
                throw new IllegalArgumentException("Нет описания идентификатора. Нет ни отдного свойства с аннотацией @Id.");
            }
            Class homeClass = home.getClass();
            Method findByPrimaryKey = homeClass.getMethod("findByPrimaryKey", idClass);
            Object id = getId(aForm);
            if(id!=null) {
                Object ejbObject = id != null ? findByPrimaryKey.invoke(home, getId(aForm)) : null;
                if(ejbObject==null) {
                    throw new EJBException("Нет объекта с идентификатором "+id) ;
                } else {
                    setProperties(aForm, ejbObject);
                }
            } else {
                throw new EJBException("У объекта нет идентификатора") ;
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при сохранении", e);
        }
    }

    public void storeOld(Object aObject, IEntityFormManagerContext aContext) {
        if (aObject == null) throw new IllegalArgumentException("Нет параметра aObject");

        Class clazz = aObject.getClass();
        EntityForm entityForm = (EntityForm) clazz.getAnnotation(EntityForm.class);
        if (entityForm == null) throw new IllegalArgumentException("У класса " + clazz + " нет аннотации @EntityForm, список доступных аннотаций " + arrayToString(clazz.getAnnotations()));

        EntityFormEntityEJB entityFormEJB = (EntityFormEntityEJB) clazz.getAnnotation(EntityFormEntityEJB.class);
        if (entityFormEJB == null) throw new IllegalArgumentException("У класса " + clazz + " нет аннотации @EntityFormEntityEJB, список доступных аннотаций " + arrayToString(clazz.getAnnotations()));

        try {
            // todo
            String jndiName = new StringBuilder(theJndiPrefix).append('/').append(entityFormEJB.jndiSimpleName()).toString();
            if (CAN_TRACE) LOG.trace("jndiName = " + jndiName);
            Object objRef = theInitialContext.lookup(theJndiPrefix + "/" + entityFormEJB.jndiSimpleName());
            if (CAN_TRACE) LOG.trace("objRef = " + objRef);
            Object home = PortableRemoteObject.narrow(objRef, entityFormEJB.home());
            if (CAN_TRACE) LOG.trace("home = " + home);

            Class idClass = findIdClass(clazz);
            if (CAN_TRACE) LOG.trace("idClass = " + idClass);
            if (idClass == null) {
                throw new IllegalArgumentException("Нет описания идентификатора. Нет ни отдного свойства с аннотацией @Id.");
            }
            Class homeClass = home.getClass();
            Method findByPrimaryKey = homeClass.getMethod("findByPrimaryKey", idClass);
            if (CAN_TRACE) LOG.trace("findByPrimaryKeyMethod = " + findByPrimaryKey);
            Object id = getId(aObject);
            if (CAN_TRACE) LOG.trace("id = " + id);
            Object ejbObject = id != null ? findByPrimaryKey.invoke(home, getId(aObject)) : null;
            if (CAN_TRACE) LOG.trace("ejbObject = " + ejbObject);
            if (id == null && ejbObject == null) {
                // todo обработать GENERATE
                throw new IllegalStateException("Ошибка сохранения/создания. Нет ни идентификатора, ни EntityEJB объекта");
            } else if (id != null && ejbObject == null) {
                // добавляем
                RolesToCreate createRoles = (RolesToCreate) clazz.getAnnotation(RolesToCreate.class);
                if (createRoles == null || checkRoles(createRoles.value(), aContext)) {
                    Method createMethod = homeClass.getMethod("create", idClass);
                    Object entityObject = createMethod.invoke(home, id);
                    setProperties(aObject, entityObject);
                } else {
                    throw new IllegalAccessException("Недостаточно прав для создания");
                }
            } else if (ejbObject != null) {
                RolesToEdit editRoles = (RolesToEdit) clazz.getAnnotation(RolesToEdit.class);
                if (editRoles == null || checkRoles(editRoles.value(), aContext)) {
                    setProperties(aObject, ejbObject);
                }
                // заменяем
            } else {
                throw new IllegalStateException("Неправильное состояние [id=" + id + ", ejbObject=" + ejbObject + "]");
            }

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при сохранении", e);
        }

    }


    private Object findHome(Class aFormClass) {
        Class clazz = aFormClass ;
        EntityForm entityForm = (EntityForm) clazz.getAnnotation(EntityForm.class);
        if (entityForm == null) throw new IllegalArgumentException("У класса " + clazz + " нет аннотации @EntityForm, список доступных аннотаций " + arrayToString(clazz.getAnnotations()));

        EntityFormEntityEJB entityFormEJB = (EntityFormEntityEJB) clazz.getAnnotation(EntityFormEntityEJB.class);
        if (entityFormEJB == null) throw new IllegalArgumentException("У класса " + clazz + " нет аннотации @EntityFormEntityEJB, список доступных аннотаций " + arrayToString(clazz.getAnnotations()));

        try {
            // todo
            String jndiName = new StringBuilder(theJndiPrefix).append('/').append(entityFormEJB.jndiSimpleName()).toString();
            if (CAN_TRACE) LOG.trace("jndiName = " + jndiName);
            Object objRef = theInitialContext.lookup(theJndiPrefix + "/" + entityFormEJB.jndiSimpleName());
            if (CAN_TRACE) LOG.trace("objRef = " + objRef);
            Object home = PortableRemoteObject.narrow(objRef, entityFormEJB.home());
            if (CAN_TRACE) LOG.trace("home = " + home);

            Class idClass = findIdClass(clazz);
            if (CAN_TRACE) LOG.trace("idClass = " + idClass);
            if (idClass == null) {
                throw new IllegalArgumentException("Нет описания идентификатора. Нет ни отдного свойства с аннотацией @Id.");
            }
            return home ;

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при сохранении", e);
        }

    }

    private String arrayToString(Object[] aObject) {
        if (aObject == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder("[ ");
            for (Object o : aObject) {
                sb.append(o);
                sb.append(", ");
            }
            sb.append(" ]");
            return sb.toString();
        }
    }

    private static void setProperties(Object aForm, Object aEntity) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ParseException {
        for (Method method : aForm.getClass().getMethods()) {
            if (method.getAnnotation(Persist.class) != null) {
                PropertyUtil.copyProperty(aEntity, aForm, method);
            }
        }
    }

    private static boolean checkRoles(String[] aRoles, IEntityFormManagerContext aContext) {
        return true;
    }

    private static Method findIdMethod(Class aClass) {
        Method ret = null;
        for (Method method : aClass.getMethods()) {
            if (method.getAnnotation(Id.class) != null) {
                ret = method;
                break;
            }
        }
        if (ret == null) throw new IllegalArgumentException("Нет описания идентификатора. Нет ни отдного свойства с аннотацией @Id.");
        return ret;
    }

    private static Object getId(Object aObject) throws IllegalAccessException, InvocationTargetException {
        Method method = findIdMethod(aObject.getClass());
        return method.invoke(aObject);
    }

    private static Class findIdClass(Class aClass) {
        return findIdMethod(aClass).getReturnType();
    }

    public void remove(Object aObject, IEntityFormManagerContext aContext) {
        if (CAN_TRACE) LOG.trace("Удаление " + aObject);
        Object home = findHome(aObject.getClass()) ;
        try {
            Object id = getId(aObject) ;
            Class idClass = findIdClass(aObject.getClass());
            Object ejbObject = findByPrimaryKey(home, id, idClass) ;
            if (CAN_TRACE) LOG.trace("ejbObject = " + ejbObject);
            if(ejbObject!=null) {
                Method method = ejbObject.getClass().getMethod("remove") ;
                method.invoke(ejbObject) ;
            } else {
                throw new EJBException("Нет EJB объекта с идентификатором "+id) ;
            }
        } catch (IllegalAccessException e) {
            throw new EJBException("Ошибка удаление объекта",e) ;
        } catch (InvocationTargetException e) {
            throw new EJBException("Ошибка удаление объекта",e) ;
        } catch (NoSuchMethodException e) {
            throw new EJBException("Ошибка удаление объекта",e) ;
        }
    }

    private final InitialContext theInitialContext;
    private final String theJndiPrefix;

}
