package ru.nuzmsh.web.tree;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import ru.nuzmsh.commons.tree.ISearchResult;
import ru.nuzmsh.commons.tree.ITreeModel;

/**
 * @author esinev
 * Date: 03.04.2006
 * Time: 15:20:02
 */
public class TestTreeModel implements ITreeModel {

    /**
     * Список корневых вершин
     * @param aCount количество выводимых элементов
     * @return корневые вершины
     */
    public Collection listRoots(int aCount) {
        LinkedList<TestBean> list = new LinkedList<TestBean>();
        list.add(new TestBean("123","фвафыва"+new Date(),"123")) ;
        list.add(new TestBean("123","фвафыва","123")) ;
        list.add(new TestBean("123","фвафыва","123")) ;
        return list ;
    }

    public ISearchResult searchForward(Object aParentId, Object aFromId, String aSearchString, int aCount) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ISearchResult searchBackward(Object aParentId, Object aFromId, String aSearchString, int aCount) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object get(Object aId) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Список потомков, начиная с aFromId
     *
     * @param aParentId ид родителя
     * @param aFromId   с какого начинать
     * @param aCount    количество выводимых элементов
     * @return потомки
     */
    public Collection listForward(Object aParentId, Object aFromId, int aCount) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Список потомков в обратном порядке, начиная с aFromId
     *
     * @param aParentId ид родителя
     * @param aFromId   с какого начинать
     * @param aCount    количество выводимых элементьв
     * @return потомки
     */
    public Collection listBackward(Object aParentId, Object aFromId, int aCount) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Получение идентификатора родителя
     * @param aId идентификатор текущего узла
     * @return идентификатор родителя, null - если нет родителя
     */
    public Object getParentId(Object aId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Идентификатор первого потомка
     * @param aId ид. текущего элемента
     * @return ид. первого потомка, если null - нет потомков
     */
    public Object getFirstChildId(Object aId) {
        return null;
    }

    public class TestBean {

        public TestBean(String aCode, String aName, String aType) {
            theCode = aCode ;
            theName = aName ;
            theType = aType ;
        }

        /** Код  */
        public String getCode() { return theCode ; }
        public void setCode(String aCode) { theCode = aCode ; }

        /** Название */
        public String getName() { return theName ; }
        public void setName(String aName) { theName = aName ; }

        /** Тип */
        public String getType() { return theType ; }
        public void setType(String aType) { theType = aType ; }

        /** Тип */
        private String theType ;
        /** Название */
        private String theName ;
        /** Код  */
        private String theCode ;
    }


}
