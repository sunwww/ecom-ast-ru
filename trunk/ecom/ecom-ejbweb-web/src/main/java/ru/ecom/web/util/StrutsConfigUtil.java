package ru.ecom.web.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ModuleConfig;

import ru.ecom.ejb.services.entityform.map.MapClassLoader;
import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.web.map.WebMapClassLoaderHelper;

/**
 *
 */
public class StrutsConfigUtil {

    public Class findClassByFormName(HttpServletRequest aRequest, String aFormName) throws ClassNotFoundException {
        ModuleConfig moduleConfig = findModuleConfig(aRequest) ;
        FormBeanConfig formConfig = moduleConfig.findFormBeanConfig(aFormName) ;
        if(formConfig==null) throw new IllegalArgumentException("Нет формы "+aFormName+" в файле конфигурации Struts") ;
        //WebMapClassLoaderHelper loader = new WebMapClassLoaderHelper() ;
        //loader.setMapClassLoader(aRequest);
        Class ret = new MapClassLoader(Thread.currentThread().getContextClassLoader()).loadClass(formConfig.getType());
        //loader.unsetMapClassLoader();
        return ret ;
        //return theClassLoaderHelper.loadClass(formConfig.getType()) ;
    }

    public String findClassNameByFormName(HttpServletRequest aRequest, String aFormName) throws ClassNotFoundException {
        ModuleConfig moduleConfig = findModuleConfig(aRequest) ;
        FormBeanConfig formConfig = moduleConfig.findFormBeanConfig(aFormName) ;
        if(formConfig==null) throw new IllegalArgumentException("Нет формы "+aFormName+" в файле конфигурации Struts") ;
        //WebMapClassLoaderHelper loader = new WebMapClassLoaderHelper() ;
        //loader.setMapClassLoader(aRequest);
        return formConfig.getType() ;
    }
    
    public ModuleConfig findModuleConfig(HttpServletRequest aRequest) {
        return (ModuleConfig) aRequest.getAttribute("org.apache.struts.action.MODULE") ;

    }


    public String findFormNameByClass(Class aParentForm, HttpServletRequest aRequest) {
        ModuleConfig moduleConfig = findModuleConfig(aRequest) ;
        FormBeanConfig[] formBeanConfigs = moduleConfig.findFormBeanConfigs() ;
        String className = aParentForm.getName();
        if(className.indexOf("__")>0) {
        	className = className.substring(0, className.indexOf("__")) ;
        }
        String ret = null ;
        for (FormBeanConfig formBeanConfig : formBeanConfigs) {
            if(formBeanConfig.getType().equals(className)) {
                ret = formBeanConfig.getName() ;
                break ;
            }
        }
        if(ret==null) {
            throw new IllegalStateException("Нет формы класса "+aParentForm.getName()) ;
        }
        return ret;
    }

    //private final ClassLoaderHelper theClassLoaderHelper = ClassLoaderHelper.getInstance();

}
