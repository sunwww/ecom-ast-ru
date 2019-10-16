package test.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ecom.ejb.services.util.ConvertSql;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TestIdc10 {

	@Test
	@DisplayName("Проверка диагноза МКБ на вхождение в диапазон")
	void makeTest() {
	//	check("1", "01", "N70") ;
		String int1 = "A00-B99" ;
		String int2 = "B00.2-C09.5" ;
		print(int1, "null", false) ;
		print(int1, "N70", false) ;
		print(int2, "C09.1", true) ;
		print(int2, "C09.6", false) ;
		print(int2, "C09.5", true) ;
		print(int1, "B90.1", true) ;
		print(int1, "C09.1", false) ;
		print(int1, "C09.6", false) ;
		print(int1, "A00", true) ;
		print(int1, "c00", false) ;
		print(int2, "A00.1", false) ;
		print(int2, "B90.1", true) ;
		print(int2, "B90.5", true) ;
		print(int2, "B00.2", true) ;
		print(int2, "B00.20", true) ;
		print(int2, "B00.1", false) ;
	}
	void print(String aInterval,String aMkb, Boolean isTrue) {
		/*System.out.print("interval=") ;
		System.out.print(aInterval) ;
		System.out.print(" mkb=") ;
		System.out.print(aMkb) ;
		System.out.print(" > ") ;*/
		assertEquals(ConvertSql.ChInt(aInterval, aMkb), isTrue);
	}
}
