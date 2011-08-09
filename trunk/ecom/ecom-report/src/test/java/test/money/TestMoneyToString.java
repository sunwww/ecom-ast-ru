package test.money;

import ru.ecom.report.money.MoneyToString;

/**
 * @author esinev
 * Date: 29.09.2006
 * Time: 9:09:56
 */
public class TestMoneyToString {
    public static void main(String[] args) {
        MoneyToString ms = new MoneyToString();
        for(double i=0; i<0.21; i+=0.01) {
            System.out.println(i + "  = " + ms.transform(i));
        }
        System.out.println("==============");
        for(double i=1; i<25; i+=1) {
            System.out.println(i + "  = " + ms.transform(i));
        }

    }
}
