package ru.ecom.ejb.services.monitor;

import java.io.Serializable;

/**
 * Статус монитора
 */
public class RemoteMonitorStatus implements Serializable {

    public RemoteMonitorStatus(String aName, String aText, double aMaximum, double aCurrent, boolean aIsFinish, String aFinishedParameters) {
        theText = aText ;
        theMax = aMaximum;
        theCurrent = aCurrent ;
        theFinish = aIsFinish;
        theFinishedParameters = aFinishedParameters ;
        theName = aName;
    }

    /** Название */
    public String getName() { return theName ; }

    /** Название */
    private final String theName ;

    /** Закончен */
    public boolean isFinish() { return theFinish ; }

    /** Закончен */
    private final boolean theFinish ;
    /** Текущая позиция */
    public double getCurrent() { return theCurrent ; }

    /** Максимально */
    public double getMax() { return theMax ; }

    /** Текст */
    public String getText() { return theText ; }

    /** Параметры окончания */
    public String getFinishedParameters() { return theFinishedParameters ; }

    /** Параметры окончания */
    private final String theFinishedParameters ;
    /** Текущая позиция */
    private final double theCurrent ;
    /** Текст */
    private final String theText ;
    /** Максимально */
    private final double theMax ;
}
