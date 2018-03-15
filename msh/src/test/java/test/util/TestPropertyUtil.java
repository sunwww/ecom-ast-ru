package test.util;

import java.lang.reflect.InvocationTargetException;

import ru.nuzmsh.util.PropertyUtil;

public class TestPropertyUtil {

	public static class Hello {
		/** Foo */
		public String getName() {
			return theName;
		}

		public void setName(String aName) {
			theName = aName;
		}

		/** Foo */
		private String theName;
	}
	
	public static class Foo {
		/** Hello */
		public Hello getHello() {
			return theHello;
		}

		public void setHello(Hello aHello) {
			theHello = aHello;
		}

		/** Hello */
		private Hello theHello;
	}
	
	public static void  main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Hello hello = new Hello() ;
		hello.setName("hello name");
		
		Foo foo = new Foo() ;
		foo.setHello(hello);
		
		try {
		Object obj = PropertyUtil.getPropertyValue(foo, "hello.name");
		System.out.println(obj);
		} catch (Exception e) {
			System.out.println(e) ;
			e.printStackTrace(System.out);
		}
	}
}
