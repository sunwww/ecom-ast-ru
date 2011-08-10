package ru.nuzmsh.web.tree.player;

/**
 * Колонка
 */
public class TreeTableColumn {

    
    public TreeTableColumn(String aTitle, String aProperty) {
        theProperty = aProperty ;
        theTitle = aTitle ;
    }

    public String toString() {
        return theTitle ;
    }

    /** Свойство для колонки */
    public String getProperty() { return theProperty ; }

    /** Название колонки */
    public String getTitle() { return theTitle ; }

    /** Свойство для колонки */
    private final String theProperty ;
    /** Название колонки */
    private final String theTitle ;
}
