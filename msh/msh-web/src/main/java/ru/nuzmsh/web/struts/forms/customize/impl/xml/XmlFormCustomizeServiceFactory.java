package ru.nuzmsh.web.struts.forms.customize.impl.xml;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;

/**
 * @author esinev
 * Date: 13.07.2006
 * Time: 9:33:50
 */
public class XmlFormCustomizeServiceFactory implements ObjectFactory {

    private final XmlFormCustomizeService theService = new XmlFormCustomizeService();
    public Object getObjectInstance(Object aObj, Name aName, Context aCtx, Hashtable<?, ?> aEnvironment) throws Exception {
        return theService ;
    }
}
