package test.extdisp;

import org.junit.Test;
import ru.nuzmsh.util.date.AgeUtil;
import test.BaseTest;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class ExtDispAgeTest extends BaseTest {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    @Test
    public void testAgeGroup() throws ParseException {
    //    assertEquals("0.0",getAgeForDisp("15.01.2018","16.01.2018"));
        assertEquals("0.0",getAgeForDisp("15.01.2018","14.02.2018"));
        assertEquals("0.1",getAgeForDisp("15.01.2018","15.02.2018"));
        assertEquals("0.1",getAgeForDisp("15.01.2018","16.02.2018"));
        assertEquals("0.0",getAgeForDisp("15.01.2018","01.02.2018"));
        assertEquals("0.1",getAgeForDisp("15.01.2018","20.02.2018"));
        assertEquals("0.2",getAgeForDisp("15.01.2018","20.03.2018"));
        assertEquals("0.2",getAgeForDisp("15.01.2018","01.04.2018"));
        assertEquals("0.3",getAgeForDisp("15.01.2018","20.04.2018"));
        assertEquals("0.5",getAgeForDisp("15.01.2018","20.06.2018"));
        assertEquals("0.6",getAgeForDisp("15.01.2018","20.07.2018"));
        assertEquals("0.7",getAgeForDisp("15.01.2018","20.08.2018"));
        assertEquals("0.8",getAgeForDisp("15.01.2018","20.09.2018"));
        assertEquals("0.9",getAgeForDisp("15.01.2018","20.10.2018"));
        assertEquals("0.10",getAgeForDisp("15.01.2018","20.11.2018"));
        assertEquals("0.11",getAgeForDisp("15.01.2018","20.12.2018"));
        assertEquals("1",getAgeForDisp("15.01.2018","20.01.2019"));
        assertEquals("1.3",getAgeForDisp("15.01.2018","20.05.2019"));
        assertEquals("1.6",getAgeForDisp("15.01.2018","20.08.2019"));
        assertEquals("1.6",getAgeForDisp("15.01.2018","20.09.2019"));
        assertEquals("1.6",getAgeForDisp("15.01.2018","31.12.2019"));
        assertEquals("1.6",getAgeForDisp("15.01.2018","14.10.2019"));
        assertEquals("1.6",getAgeForDisp("15.01.2018","14.01.2020"));
        assertEquals("2",getAgeForDisp("15.01.2018","16.01.2020"));
        assertEquals("2",getAgeForDisp("15.01.2018","31.12.2020"));
        assertEquals("3",getAgeForDisp("15.01.2018","20.08.2021"));
        assertEquals("30",getAgeForDisp("15.01.2018","01.08.2048"));
        assertEquals("30",getAgeForDisp("15.01.2018","31.12.2048"));

    }


    private String getAgeForDisp(String aBirthday, String aFinishDate) throws ParseException {
        Date birthday = new Date(FORMAT.parse(aBirthday).getTime());
        Date finishDate = new Date(FORMAT.parse(aFinishDate).getTime());
            Calendar calB = Calendar.getInstance() ;
            calB.setTime(birthday) ;
            Calendar calF = Calendar.getInstance() ;
            calF.setTime(finishDate) ;
            boolean reMonth = (calF.get(Calendar.MONTH) == calB.get(Calendar.MONTH)) ;
            String age= AgeUtil.getAgeCache(finishDate, birthday, 1);
            //	System.out.println("age:"+age) ;
            int sb1 = age.indexOf('.') ;
            int sb2 = age.indexOf('.',sb1+1) ;
            String ageGroup;
            //int yearDif = Integer.valueOf(age.substring(0,sb1)).intValue() ;
            int yearDif = Integer.parseInt(age.substring(0,sb1)) ;
            //	System.out.println("yearDif:"+yearDif) ;
            //int monthDif = Integer.valueOf(age.substring(sb1+1, sb2)).intValue();
            int monthDif = Integer.parseInt(age.substring(sb1+1, sb2));
            //	System.out.println("monthDif:"+monthDif) ;
            //int dayDif =  Integer.valueOf(age.substring(sb2+1)).intValue() ;
            if (yearDif==0){
                ageGroup =yearDif+"."+monthDif ;
            } else if (yearDif==1){
                if (monthDif>=6) ageGroup="1.6";
                else if (monthDif>=3) ageGroup="1.3";
                else ageGroup = "1";
            } else {
                int year1=Integer.parseInt(aBirthday.substring(6)) ;
                int year2=Integer.parseInt(aFinishDate.substring(6)) ;
                if (year2<20) year2=year2+2000 ;
                if (year2<100) year2=year2+1900 ;
                ageGroup = ""+(year2-year1) ;
            }
       //     log("ageGroup  = "+ageGroup);
            return ageGroup;

    }

    private void log(String msg){
        System.out.println(msg);
    }
}
