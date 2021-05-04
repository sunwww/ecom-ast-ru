package ru.ecom.ejb.services.monitor;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Статус монитора
 */
@Getter
public class RemoteMonitorStatus implements Serializable {

    public RemoteMonitorStatus(String aName, String aText, double aMaximum, double aCurrent, boolean aIsFinish, String aFinishedParameters) {
        text = aText ;
        max = aMaximum;
        current = aCurrent ;
        finish = aIsFinish;
        finishedParameters = aFinishedParameters ;
        name = aName;
    }

    /** Название */
    private final String name ;

    /** Закончен */
    public boolean isFinish() { return finish ; }

    /** Закончен */
    private final boolean finish ;

    /** Параметры окончания */
    private final String finishedParameters ;
    /** Текущая позиция */
    private final double current ;
    /** Текст */
    private final String text ;
    /** Максимально */
    private final double max ;
}
