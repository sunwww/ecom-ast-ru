/**
 * @author ikouzmin 05.04.2007 10:02:11
 */

package ru.ecom.expomc.ejb.services.form.importformat;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.monitor.IMonitor;

public class MyMonitor implements IMonitor {
    private static final Logger LOG = Logger.getLogger(MyMonitor.class) ;


    double internalValue;
    double value;
    double maxValue;

    IMonitor monitor;
    MyMonitor parentMonitor;
    double internalMinValue;
    double internalMaxValue;
    double internalRangeValue;


    public MyMonitor() {
        monitor = null;
        parentMonitor = null;
        maxValue = 100;
        value = 0;
        setRange(0,100);
    }

    public double getValue() {
        return value;
    }
    
    public void attachMonitor (IMonitor aMonitor) {
        monitor = aMonitor;
        setValue(value);
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public MyMonitor getSubMonitor(double toValue) {
        return getSubMonitor(toValue,100);
    }

    public void dump () {
        dump(null);
    }

    public void dump (String message) {
        if (message==null) {
            message ="";
        } else {
            message = message+": ";
        }

        LOG.info(message+this+" [" + internalMinValue+"-"+internalMaxValue+"] :"+internalValue+"\t--> "+
                value+"/"+maxValue);
    }
    public MyMonitor getSubMonitor(double toValue,double maxValue) {
        MyMonitor subMonitor = new MyMonitor();
        subMonitor.monitor = monitor;
        subMonitor.parentMonitor=this;
        subMonitor.setMaxValue(maxValue);
        subMonitor.setRange(internalValue, getInternalValue(toValue));
        dump("mainMonitor");
        subMonitor.dump("subMonitor");
        value = toValue;
        dump("mainMonitorAfter");
        return subMonitor;
    }

    public void update() {
        calcInternalValue();
        setInternalValue(internalValue);
    }

    private void setInternalValue(double aValue) {
        if (parentMonitor==null) {
            if (aValue > internalValue) {
                monitor.advice(aValue- internalValue);
                internalValue = aValue;
            }
        } else {
            parentMonitor.setInternalValue(aValue);
        }
    }
    private double getInternalValue(double value) {
        return internalMinValue + value*internalRangeValue/maxValue;
    }

    private void calcInternalValue() {
        internalValue = getInternalValue(value);
    }

    private void setRange(double internalMinValue,double internalMaxValue) {
        this.internalMinValue = internalMinValue;
        this.internalMaxValue = internalMaxValue;
        internalRangeValue = internalMaxValue - internalMinValue;
        internalValue = getInternalValue(value);
    }

    public void setValue(double aValue) {
        value = aValue;
        setInternalValue(getInternalValue(value));
        dump("setValue");
    }


    public void advice(double aAdvice) {
        value += aAdvice;
        update();
    }

    public void setText(String aText) {
        monitor.setText(aText);
    }

    public void finish(String aParameters) {
        monitor.finish(aParameters);
    }

    public boolean isCancelled() {
        return monitor.isCancelled();
    }

    public void error(String aMessage, Exception aException) {
        monitor.error(aMessage, aException);
    }


}