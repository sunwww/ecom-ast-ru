package test.exceptions;

import ru.nuzmsh.ejb.exceptions.EJBExceptionCause;

/**
 * @author esinev
 * Date: 23.03.2006
 * Time: 14:58:14
 */
public class TestEJBExceptionCause {

    private static void throwException2() throws Exception {
        try {
            throw new NullPointerException("1") ;
        } catch (Exception e) {
            throw new EJBExceptionCause("err 2",e) ;
        }
    }

    private static void throwException() throws Exception {
        try {
            throwException2();
        } catch (Exception e) {
            throw new EJBExceptionCause(" 3 Ошибка главная",e) ;
        }
    }
    public static void main(String[] args) {
        try {
            throwException();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause().getMessage());
            System.out.println(e.getCause().getCause().getMessage());
//            e.printStackTrace() ;
        }
    }
}
