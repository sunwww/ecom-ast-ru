package ru.ecom.web.actions.js;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

import ru.nuzmsh.web.struts.BaseAction;

public class JavaScriptAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws Exception {

		StringTokenizer st = new StringTokenizer(aMapping.getParameter()
				.substring(1), "-");
		String dir = st.nextToken();
		String function = st.nextToken();
		Context jsContext = Context.enter();
		try {
			// jsContext.setOptimizationLevel(9);
			Scriptable scope = jsContext.initStandardObjects();

			String path = "/WEB-INF/actions/" + dir + "/" + dir + ".js";
			InputStream inputStream = getServlet().getServletContext().getResourceAsStream(path) ;
			if (inputStream == null) throw new IllegalStateException("Не найден " + path);
			
			InputStreamReader in = new InputStreamReader(inputStream, "utf-8");
			try {
				Script script = jsContext.compileReader(in, dir + ".js", 1,
						null);
				script.exec(jsContext, scope);
			} finally {
				in.close();
			}

			ActionJavaScriptContext ctx = new ActionJavaScriptContext(aMapping,
					aRequest);
			Object o = scope.get(function, scope);
			if(! (o instanceof Function)) {
				throw new IllegalStateException("В файле "+path+" нет функции "+function) ;
			} else {
				Function f = (Function) o;
				Object[] args = new Object[] { aForm, ctx };
				Object result = f.call(jsContext, scope, scope, args);
				NativeJavaObject ret = (NativeJavaObject) result;
				return (ActionForward) ret.unwrap();
			}
		} finally {
			Context.exit();
		}
	}

}
