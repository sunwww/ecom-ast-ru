package ru.nuzmsh.web.tags.decorator;

/**
 * @author ESinev
 *         Date: 22.12.2005
 *         Time: 11:06:35
 */
public abstract class AbstractTableDecorator implements ITableDecorator {
    public String getRowCssClass(Object aRow) {
        return "" ;
    }

    public String getId(Object aRow) {
        return null;
    }
}
