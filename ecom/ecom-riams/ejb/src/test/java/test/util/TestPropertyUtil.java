package test.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nuzmsh.util.PropertyUtil;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPropertyUtil {

	public static class Hello {
		/** Foo */
		public String getName() {
			return theName;
		}

		private void setName(String aName) {
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

		private void setHello(Hello aHello) {
			theHello = aHello;
		}

		/** Hello */
		private Hello theHello;
	}

	@Test
	@DisplayName("Тестируем поиск значений обхектов ")
	void makeTest() throws InvocationTargetException, IllegalAccessException {
		Hello hello = new Hello() ;
		hello.setName("hello name");
		Foo foo = new Foo() ;
		foo.setHello(hello);
		Object obj = PropertyUtil.getPropertyValue(foo, "hello.name");
		assertEquals(obj.toString(), "hello name");
	}
}
