package test;

import org.junit.jupiter.api.Test;
import ru.nuzmsh.util.LogicTokenizer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author esinev
 * Date: 09.06.2006
 * Time: 9:20:32
 */
public class TestLogicTokenizer {

    @Test
    void testEmpty() {
        LogicTokenizer st = new LogicTokenizer("###",'#');
        for(int i=0;i<4; i++) {
            st.hasNext() ;
        }
        st.hasNext() ;
        st.getNextString() ;
        st.hasNext() ;
        st.getNextString() ;
        assertEquals(1,1);
    }

}
