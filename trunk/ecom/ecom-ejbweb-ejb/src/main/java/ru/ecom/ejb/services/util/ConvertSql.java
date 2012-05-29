package ru.ecom.ejb.services.util;

import java.math.BigInteger;

public class ConvertSql {
	public static Long parseLong(Object aValue) {
		Long ret =null;
		if (aValue==null) return ret ;
		if (aValue instanceof Integer) {
			return Long.valueOf((Integer) aValue) ;
		}
		if(aValue instanceof BigInteger) {
			BigInteger bigint = (BigInteger) aValue ;
			
			return bigint!=null?bigint.longValue() : null;
		} 
		if (aValue instanceof Number) {
			Number number = (Number) aValue ;
			return number!=null?number.longValue() : null ;
		}
		if (aValue instanceof String) {
			return Long.valueOf((String) aValue);
		}
		return ret ;
	}
	
	public static boolean ChInt(String Interval,String Mkb) {
		if (Mkb==null || Mkb.equals("null")) return false ; 
	 String end;
	 String fst;
	 boolean tst;
	 tst=false ;
	 String[] inter = Interval.split(",") ;
	 for (String in:inter) {
		 //System.out.println(in.indexOf("-")) ;
		 if (in.indexOf("-")==-1) {
			 tst=ChSub(Mkb,in) ;
		 } else {
			 String[] dop = in.split("-") ;
			 fst=dop[0] ;
			 end=dop[1] ;
			 
			 if (ChSub(Mkb,fst)) {
				 tst=true ;
				 break ;
			 }
			 if (ChSub(Mkb,end)) {
				 tst=true ;
				 break ;
			 }
			 //if ((mkb>Character.getNumericValue(fst.charAt(0)))&&(Character.getNumericValue(end.charAt(0))>mkb)) {
			 if (soderjit(Mkb,fst)&&soderjit(end,Mkb)) {
				 tst = true ; 
				 break ;
			 }
		 }
	 }
	 return tst ;
	}
	private static boolean ChSub(String aMkb1,String aMkb2) {
		if (aMkb2.indexOf(".")>-1) {
			return aMkb1.indexOf(aMkb2)>-1 ;
		}
		
		int ind = aMkb1.indexOf(".") ;
		//System.out.println(ind) ;
		return (ind==-1?aMkb1:aMkb1.substring(0,ind)).equals(aMkb2) ;
	}
	private static boolean soderjit(String aMkb1,String aMkb2) {
		int tst=0;
		int cnt1 = aMkb1.length() ;
		int cnt2 = aMkb2.length() ;
		//System.out.print("mkb1="+aMkb1+" mkb2="+aMkb2) ;
		for (int i=0 ;(i< cnt1&&i<cnt2) ;i++) {
			
			int c1 = Character.getNumericValue(aMkb1.charAt(i)) ;
			int c2 = Character.getNumericValue(aMkb2.charAt(i)) ;
			//System.out.print(" c1="+c1+"-"+aMkb1.charAt(i)+" c2="+c2+"-"+aMkb2.charAt(i)) ;
			if (c1>c2&&tst==0) {
				tst=1 ;
				break ;
			} else if (c1==c2) {
				
			} else {
				tst = 2 ;
				break ;
			}
		}
		return tst==1 ;
		
	}
	public static String translate(String aString) {
		aString = aString.toLowerCase() ;
		aString = aString.replaceAll("а", "a") ;
		aString = aString.replaceAll("б", "b") ;
		aString = aString.replaceAll("в", "v") ;
		aString = aString.replaceAll("г", "g") ;
		aString = aString.replaceAll("д", "d") ;
		aString = aString.replaceAll("е", "e") ;
		aString = aString.replaceAll("ё", "e") ;
		aString = aString.replaceAll("ж", "zh") ;
		aString = aString.replaceAll("з", "z") ;
		aString = aString.replaceAll("и", "i") ;
		aString = aString.replaceAll("й", "i") ;
		aString = aString.replaceAll("к", "k") ;
		aString = aString.replaceAll("л", "l") ;
		aString = aString.replaceAll("м", "m") ;
		aString = aString.replaceAll("н", "n") ;
		aString = aString.replaceAll("о", "o") ;
		aString = aString.replaceAll("п", "p") ;
		aString = aString.replaceAll("р", "r") ;
		aString = aString.replaceAll("с", "s") ;
		aString = aString.replaceAll("т", "t") ;
		aString = aString.replaceAll("у", "u") ;
		aString = aString.replaceAll("ф", "f") ;
		aString = aString.replaceAll("х", "h") ;
		aString = aString.replaceAll("ц", "ts") ;
		aString = aString.replaceAll("ч", "ch") ;
		aString = aString.replaceAll("ш", "sh") ;
		aString = aString.replaceAll("щ", "shch") ;
		aString = aString.replaceAll("ъ", "") ;
		aString = aString.replaceAll("ы", "y") ;
		aString = aString.replaceAll("ь", "") ;
		aString = aString.replaceAll("э", "e") ;
		aString = aString.replaceAll("ю", "yu") ;
		aString = aString.replaceAll("я", "ya") ;
		return aString ;
	}
	 /*
	 ;---check Mkb2 equals Mkb1  
	ChSub(Mkb1,Mkb2) 
	 s $zt="err"
	 i Mkb2["." q Mkb1=Mkb2
	 q $p(Mkb1,".")=Mkb2

*/
}
