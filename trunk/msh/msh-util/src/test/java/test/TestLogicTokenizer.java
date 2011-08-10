package test;

import junit.framework.TestCase;
import ru.nuzmsh.util.LogicTokenizer;

/**
 * @author esinev
 * Date: 09.06.2006
 * Time: 9:20:32
 */
public class TestLogicTokenizer extends TestCase {

    public void testEmpty() {
        LogicTokenizer st = new LogicTokenizer("###",'#');
        for(int i=0;i<4; i++) {
            st.hasNext() ;
            System.out.println("st.getNextString() = " + st.getNextString());
        }
        st.hasNext() ;
        st.getNextString() ;
        st.hasNext() ;
        st.getNextString() ;
    }

}
