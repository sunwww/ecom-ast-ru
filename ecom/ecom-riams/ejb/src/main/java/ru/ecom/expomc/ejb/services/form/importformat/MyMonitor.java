/**
 * @author ikouzmin 05.04.2007 10:02:11
 */

package ru.ecom.expomc.ejb.services.form.importformat;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.monitor.IMonitor;

public class MyMonitor implements IMonitor {
    private static final Logger LOG = Logger.getLogger(MyMonitor.class) ;


    double theInternalValue;
    double theValue;
    double theMaxValue;

    IMonitor theMonitor;
    MyMonitor theParentMonitor;
    double theInternalMinValue;
    double theInternalMaxValue;
    double theInternalRangeValue;


    public MyMonitor() {
        theMonitor = null;
        theParentMonitor = null;
        theMaxValue = 100;
        theValue = 0;
        setRange(0,100);
    }

    public double getValue() {
        return theValue;
    }
    
    public void attachMonitor (IMonitor aMonitor) {
        theMonitor = aMonitor;
        setValue(theValue);
    }

    public void setMaxValue(double maxValue) {
        theMaxValue = maxValue;
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

        LOG.info(message+this+" [" + theInternalMinValue+"-"+theInternalMaxValue+"] :"+theInternalValue+"\t--> "+
                theValue+"/"+theMaxValue);
    }
    public MyMonitor getSubMonitor(double toValue,double maxValue) {
        MyMonitor subMonitor = new MyMonitor();
        subMonitor.theMonitor = theMonitor;
        subMonitor.theParentMonitor=this;
        subMonitor.setMaxValue(maxValue);
        subMonitor.setRange(theInternalValue, getInternalValue(toValue));
        dump("mainMonitor");
        subMonitor.dump("subMonitor");
        theValue = toValue;
        dump("mainMonitorAfter");
        return subMonitor;
    }

    public void update() {
        calcInternalValue();
        setInternalValue(theInternalValue);
    }

    private void setInternalValue(double aValue) {
        if (theParentMonitor==null) {
            if (aValue > theInternalValue) {
                theMonitor.advice(aValue- theInternalValue);
                theInternalValue = aValue;
            }
        } else {
            theParentMonitor.setInternalValue(aValue);
        }
    }
    private double getInternalValue(double value) {
        return theInternalMinValue + value*theInternalRangeValue/theMaxValue;
    }

    private void calcInternalValue() {
        theInternalValue = getInternalValue(theValue);
    }

    private void setRange(double internalMinValue,double internalMaxValue) {
        theInternalMinValue = internalMinValue;
        theInternalMaxValue = internalMaxValue;
        theInternalRangeValue = internalMaxValue - internalMinValue;
        theInternalValue = getInternalValue(theValue);
    }

    public void setValue(double aValue) {
        theValue = aValue;
        setInternalValue(getInternalValue(theValue));
        dump("setValue");
    }


    private void adviceInternal(double aAdvice) {
        setInternalValue(theInternalValue +aAdvice);
    }

    public void advice(double aAdvice) {
        theValue += aAdvice;
        update();
        //dump("advice");
    }

    public void setText(String aText) {
        theMonitor.setText(aText);
    }

    public void finish(String aParameters) {
        theMonitor.finish(aParameters);
    }

    public boolean isCancelled() {
        return theMonitor.isCancelled();
    }

    public void error(String aMessage, Exception aException) {
        theMonitor.error(aMessage, aException);
    }


}