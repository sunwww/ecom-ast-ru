package test.exceptions;

import ru.nuzmsh.ejb.exceptions.CopyException;

import javax.ejb.FinderException;

/**
 * @author esinev
 * Date: 17.04.2006
 * Time: 10:52:17
 */
public class TestCopyException {

    private void thr_1() throws ExceptionTest {
        throw new ExceptionTest("1", null) ;
    }

    private void thr_2() throws ExceptionTest {
        try {
            thr_1() ;
        } catch (ExceptionTest e) {
            throw new ExceptionTest("2", e) ;
        }
    }

    private void thr_3() throws ExceptionTest {
        try {
            thr_2() ;
        } catch (ExceptionTest e) {
            throw new ExceptionTest("3", e) ;
        }
    }

    private void thr_4() throws ExceptionTest {
        try {
            thr_3() ;
        } catch (ExceptionTest e) {
            throw new ExceptionTest("4", e) ;
        }
    }

    public static void main(String[] args) {
        TestCopyException test = new TestCopyException();
        try {
            test.thr_4() ;
        } catch (Exception e) {
            CopyException copyException = CopyException.copyException(e);
            test("test.exceptions.TestCopyException$ExceptionTest: 4",copyException) ;
            test("test.exceptions.TestCopyException$ExceptionTest: 3",copyException.getCause()) ;
            test("test.exceptions.TestCopyException$ExceptionTest: 2",copyException.getCause().getCause()) ;
            test("test.exceptions.TestCopyException$ExceptionTest: 1",copyException.getCause().getCause().getCause()) ;
            copyException.getCause().getCause().getCause().printStackTrace() ;
            //copyException.printStackTrace();
            //System.out.println("copyException = " + copyException);
//            System.out.println("copyException.getCause() = " + copyException.getCause());
        }
    }


    private static void test(String aMessage, Throwable aException) {
        if(!aMessage.equals(aException.getMessage())) {
            throw new IllegalStateException("Не совпадают: \n etalon "+aMessage+" \n cause  "+aException.getMessage()) ;
//            System.err.println("Не совпадают: \n etalon '"+aMessage+"' \n cause  '"+aException.getMessage()+"'");
        }
    }


    public class ExceptionTest extends Exception {
        public ExceptionTest(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
