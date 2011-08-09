package ru.ecom.ejb.services.entityform.jsinterceptor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.nuzmsh.forms.validator.MapForm;
import ru.nuzmsh.util.StringUtil;

public class JavaScriptFormInterceptorManager {

	private final static Logger LOG = Logger.getLogger(JavaScriptFormInterceptorManager.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();

	public static JavaScriptFormInterceptorManager getInstance() {
		return new JavaScriptFormInterceptorManager() ;
	}
	
	public void invoke(String aFunctionName, IEntityForm aForm, Object aEntity, Object aId, Class aStrutsFormClass, JavaScriptFormInterceptorContext aContext) {
		//if(aForm==null) throw new IllegalArgumentException("Нет формы") ;
			String strutsFormName = aForm!=null ? getStrutsFormName(aForm) : getStrutsFormName(aStrutsFormClass) ;
			String jsResourceName = "/META-INF/formjs/"+strutsFormName+".js" ;
			try {
				InputStream inputStream = theEcomConfig.getInputStream(jsResourceName, EjbEcomConfig.FORM_JS_PREFIX, false) ;
				if(inputStream!=null) {
					Context jsContext = Context.enter();
					jsContext.setOptimizationLevel(9);
					try {
						Scriptable scope = jsContext.initStandardObjects();
						
						Reader in = new InputStreamReader(inputStream, "utf-8") ;
						try {
							Script script = jsContext.compileReader(in, jsResourceName, 1,null);
							script.exec(jsContext, scope);
							
							Object o = scope.get(aFunctionName, scope);
							if(o instanceof Function) {
								Function f = (Function) o;
								Object[] args ;
								if(aForm==null && aEntity==null && aId!=null) {
									args = new Object[] {aId, aContext} ;
								} else if(aForm!=null && aEntity==null && aId==null) {
									args = new Object[] { aForm, aContext };
								} else if(aForm!=null && aEntity!=null && aId==null) {
									args = new Object[] { aForm, aEntity, aContext } ;
								} else {
									throw new IllegalStateException("Нельзя определить, какие аргументы передавать функции "
											+aFunctionName
											+" [aForm = "+aForm+", aEntity = "+aEntity+", aId = "+aId+"]") ;
								}
								Object result = f.call(jsContext, scope, scope, args);
								if (CAN_DEBUG) LOG.debug("invoke: result = " + result); 
							} else {
								if (CAN_DEBUG) LOG.debug("Нет функции " + aFunctionName+": "+o); 
							}
						} finally {
							in.close() ;
						}
					} finally {
						Context.exit();
					}
				}
			} catch (IOException e) {
				throw new IllegalStateException("Ошибка открытия ресурса "+jsResourceName) ;
			}
	}

	public String getStrutsFormName(String aClassName) {
		if(aClassName.startsWith("$$map$$")) {
			int pos = aClassName.indexOf("__");
			if(pos>0) {
				return aClassName.substring(7,pos) ;
			} else {
				return aClassName.substring(7) ;
			}
		} else {
			return aClassName ;
		}
	}

	public String getStrutsFormName(Class aClass) {
		return getStrutsFormName(aClass.getSimpleName());
		
	}
	
    private String getStrutsFormName(IEntityForm aForm) {
    	String ret ;
    	if(aForm instanceof MapForm) {
    		MapForm mapForm = (MapForm) aForm ;
    		if(StringUtil.isNullOrEmpty(mapForm.getStrutsFormName())) {
    			ret = getStrutsFormName(aForm.getClass());
    		} else {
        		ret = mapForm.getStrutsFormName() ;
    		}
    	} else {
    		ret = aForm.getClass().getSimpleName();
    	}
    	return ret ;
	}


	EjbEcomConfig theEcomConfig = EjbEcomConfig.getInstance(); 
	
}
