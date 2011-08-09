package ru.ecom.jboss.system;

import java.net.MalformedURLException;

import org.jboss.system.JBossRMIClassLoader;

public class RMIClassLoaderBubldeEntryFix extends JBossRMIClassLoader {
	// Constructors --------------------------------------------------

	/**
	 * Required constructor
	 */
	public RMIClassLoaderBubldeEntryFix() {
		super() ;
	}

	/**
	 * Just delegate
	 */
	@Override
	public Class loadClass(String codebase, String name, ClassLoader ignored)
			throws MalformedURLException, ClassNotFoundException {

		// System.out.println("codebame = "+codebase+", name="+name) ;

		if (codebase != null && codebase.startsWith("bundleentry://")) {
			return Thread.currentThread().getContextClassLoader().loadClass(
					name);
		} else {
			return super.loadClass(codebase, name, ignored);
		}
	}

}
