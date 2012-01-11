package test.entity;

import ru.ecom.ejb.services.util.ConvertSql;


public class TestIdc10 {
	public static void main(String args[] ) {
		check("1", "01", "N70") ;
		String int1 = "A00-B99" ;
		String int2 = "B00.2-C09.5" ;
		print(int1, "null") ;
		print(int1, "N70") ;
		print(int2, "C09.1") ;
		print(int2, "C09.6") ;
		print(int2, "C09.5") ;
		
		print(int1, "B90.1") ;
		print(int1, "C09.1") ;
		print(int1, "C09.6") ;
		print(int1, "A00") ;
		print(int1, "c00") ;
		print(int2, "A00.1") ;
		print(int2, "B90.1") ;
		print(int2, "B90.5") ;
		print(int2, "B00.2") ;
		print(int2, "B00.20") ;
		print(int2, "B00.1") ;
	}
	private static void print(String aInterval,String aMkb) {
		System.out.print("interval=") ;
		System.out.print(aInterval) ;
		System.out.print(" mkb=") ;
		System.out.print(aMkb) ;
		System.out.print(" > ") ;
		System.out.println(ConvertSql.ChInt(aInterval, aMkb)) ;
	}
	private static void check(String sex, String reason,String mkb) {
		System.out.println("mkb--->"+mkb) ;
		System.out.println("reason--->"+mkb) ;
		System.out.println("sex--->"+mkb) ;
		String str ="";
		if (reason.equals("09")) {
			if (sex.equals("1")) {str=str+","+"96,102" ;} else {str=str+","+"97,103" ;}
		} else if (reason.equals("08")) {
			if (sex.equals("1")) {str=str+","+"98,102" ;} else {str=str+","+"99,103" ;}
		} else if (reason.equals("03")) {
			if (sex.equals("1")) {str=str+","+"100,102" ;} else {str=str+","+"101,103" ;}
		} else if (reason.equals("05")) {
			if (sex.equals("1")) {str=str+","+"104" ;} else {str=str+","+"104" ;}
		} else { 
    		if (ConvertSql.ChInt("A00-B99",mkb)) {if (sex.equals("1")) {str=str+","+"1" ;} else {str=str+","+"2" ;}}
    		if (ConvertSql.ChInt("A00-A09",mkb)) {if (sex.equals("1")) {str=str+","+"3" ;} else {str=str+","+"4" ;}}
    		if (ConvertSql.ChInt("A15-A19",mkb)) {if (sex.equals("1")) {str=str+","+"5" ;} else {str=str+","+"6" ;}}
    		if (ConvertSql.ChInt("B15-B19",mkb)) {if (sex.equals("1")) {str=str+","+"7" ;} else {str=str+","+"8" ;}}
    		if (ConvertSql.ChInt("C00-D48",mkb)) {if (sex.equals("1")) {str=str+","+"9" ;} else {str=str+","+"10" ;}}
    		if (ConvertSql.ChInt("C00-D09",mkb)) {if (sex.equals("1")) {str=str+","+"11" ;} else {str=str+","+"12" ;}}
    		if (ConvertSql.ChInt("D50-D89",mkb)) {if (sex.equals("1")) {str=str+","+"13" ;} else {str=str+","+"14" ;}}
    		if (ConvertSql.ChInt("E00-E90",mkb)) {if (sex.equals("1")) {str=str+","+"15" ;} else {str=str+","+"16" ;}}
    		if (ConvertSql.ChInt("E10-E14",mkb)) {if (sex.equals("1")) {str=str+","+"17" ;} else {str=str+","+"18" ;}}
    		if (ConvertSql.ChInt("E10",mkb)) {if (sex.equals("1")) {str=str+","+"19" ;} else {str=str+","+"20" ;}}
    		if (ConvertSql.ChInt("F00-F99",mkb)) {if (sex.equals("1")) {str=str+","+"21" ;} else {str=str+","+"22" ;}}
    		if (ConvertSql.ChInt("G00-G99",mkb)) {if (sex.equals("1")) {str=str+","+"23" ;} else {str=str+","+"24" ;}}
    		if (ConvertSql.ChInt("G50-G72",mkb)) {if (sex.equals("1")) {str=str+","+"25" ;} else {str=str+","+"26" ;}}
    		if (ConvertSql.ChInt("H00-H59",mkb)) {if (sex.equals("1")) {str=str+","+"27" ;} else {str=str+","+"28" ;}}
    		if (ConvertSql.ChInt("H60-H95",mkb)) {if (sex.equals("1")) {str=str+","+"29" ;} else {str=str+","+"30" ;}}
    		if (ConvertSql.ChInt("J00-J99",mkb)) {if (sex.equals("1")) {str=str+","+"31" ;} else {str=str+","+"32" ;}}
    		if (ConvertSql.ChInt("J00-J09",mkb)) {if (sex.equals("1")) {str=str+","+"33" ;} else {str=str+","+"34" ;}}
    		if (ConvertSql.ChInt("J10-J13",mkb)) {if (sex.equals("1")) {str=str+","+"35" ;} else {str=str+","+"36" ;}}
    		if (ConvertSql.ChInt("J20-J25",mkb)) {if (sex.equals("1")) {str=str+","+"37" ;} else {str=str+","+"38" ;}}
    		if (ConvertSql.ChInt("J60-J69",mkb)) {if (sex.equals("1")) {str=str+","+"39" ;} else {str=str+","+"40" ;}}
    		if (ConvertSql.ChInt("J00-J99",mkb)) {if (sex.equals("1")) {str=str+","+"41" ;} else {str=str+","+"42" ;}}
    		if (ConvertSql.ChInt("J00,J01,J04,J05,J06",mkb)) {if (sex.equals("1")) {str=str+","+"43" ;} else {str=str+","+"44" ;}}
    		if (ConvertSql.ChInt("J02-J03",mkb)) {if (sex.equals("1")) {str=str+","+"45" ;} else {str=str+","+"46" ;}}
    		if (ConvertSql.ChInt("J10-J11",mkb)) {if (sex.equals("1")) {str=str+","+"47" ;} else {str=str+","+"48" ;}}
    		if (ConvertSql.ChInt("J12-J18",mkb)) {if (sex.equals("1")) {str=str+","+"49" ;} else {str=str+","+"50" ;}}
    		if (ConvertSql.ChInt("J40-J43",mkb)) {if (sex.equals("1")) {str=str+","+"51" ;} else {str=str+","+"52" ;}}
    		if (ConvertSql.ChInt("J45,J46",mkb)) {if (sex.equals("1")) {str=str+","+"53" ;} else {str=str+","+"54" ;}}
    		if (ConvertSql.ChInt("J60,J66",mkb)) {if (sex.equals("1")) {str=str+","+"55" ;} else {str=str+","+"56" ;}}
    		if (ConvertSql.ChInt("K00-K93",mkb)) {if (sex.equals("1")) {str=str+","+"57" ;} else {str=str+","+"58" ;}}
    		if (ConvertSql.ChInt("K25-K26",mkb)) {if (sex.equals("1")) {str=str+","+"59" ;} else {str=str+","+"60" ;}}
    		if (ConvertSql.ChInt("K29",mkb)) {if (sex.equals("1")) {str=str+","+"61" ;} else {str=str+","+"62" ;}}
    		if (ConvertSql.ChInt("K70-K86",mkb)) {if (sex.equals("1")) {str=str+","+"63" ;} else {str=str+","+"64" ;}}
    		if (ConvertSql.ChInt("L00-L99",mkb)) {if (sex.equals("1")) {str=str+","+"65" ;} else {str=str+","+"66" ;}}
    		if (ConvertSql.ChInt("L00-L08",mkb)) {if (sex.equals("1")) {str=str+","+"67" ;} else {str=str+","+"68" ;}}
    		if (ConvertSql.ChInt("M00-M99",mkb)) {if (sex.equals("1")) {str=str+","+"69" ;} else {str=str+","+"70" ;}}
    		if (ConvertSql.ChInt("M05-M06,M08.0",mkb)) {if (sex.equals("1")) {str=str+","+"71" ;} else {str=str+","+"72" ;}}
    		if (ConvertSql.ChInt("N00-N99",mkb)) {if (sex.equals("1")) {str=str+","+"73" ;} else {str=str+","+"74" ;}}
    		if (ConvertSql.ChInt("N00-N39",mkb)) {if (sex.equals("1")) {str=str+","+"75" ;} else {str=str+","+"76" ;}}
    		if (ConvertSql.ChInt("N70-N76",mkb)) {str=str+","+"77" ;}
    		if (ConvertSql.ChInt("Q00-Q99",mkb)) {str=str+","+"78" ;}
    		if (ConvertSql.ChInt("O00-O99",mkb)) {if (sex.equals("1")) {str=str+","+"79" ;} else {str=str+","+"80" ;}}
    		if (ConvertSql.ChInt("R00-R99",mkb)) {if (sex.equals("1")) {str=str+","+"81" ;} else {str=str+","+"82" ;}}
    		if (ConvertSql.ChInt("S00-T98",mkb)) {if (sex.equals("1")) {str=str+","+"83" ;} else {str=str+","+"84" ;}}
    		if (ConvertSql.ChInt("S00,S10,S20,S30,S40,S50,S60,S70,S80,S90,T00,T09.0,T11.0,T13.0,T14.0",mkb)) {if (sex.equals("1")) {str=str+","+"85" ;} else {str=str+","+"86" ;}}
    		if (ConvertSql.ChInt("S02,S06",mkb)) {if (sex.equals("1")) {str=str+","+"87" ;} else {str=str+","+"88" ;}}
    		if (ConvertSql.ChInt("S42,S52,S62,S72,S82,S92,T02.2-T02.6,T10,T12",mkb)) {if (sex.equals("1")) {str=str+","+"89" ;} else {str=str+","+"90" ;}}
    		if (ConvertSql.ChInt("S03,S13,S23,S33,S43,S53,S63,S73,S83,S93,T03,T09.2,T11.2,T13.2,T14.3",mkb)) {if (sex.equals("1")) {str=str+","+"91" ;} else {str=str+","+"92" ;}}
    		if (sex.equals("1")) {str=str+","+"93,102" ;} else {str=str+","+"94,103" ;}
    		if (ConvertSql.ChInt("O03-O07",mkb)) {str=str+","+"95" ;}
    		//if (ConvertSql.ChInt("",mkb)) {if (sex.equals("1")) {str=str+","+"" ;} else {str=str+","+"" ;}}
    		//if (ConvertSql.ChInt("",mkb)) {if (sex.equals("1")) {str=str+","+"" ;} else {str=str+","+"" ;}}
    		//if (ConvertSql.ChInt("",mkb)) {if (sex.equals("1")) {str=str+","+"" ;} else {str=str+","+"" ;}}
		}
		System.out.println("str--->"+str.substring(1)) ;
	}

}
