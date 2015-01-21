package ru.ecom.ejb.services.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
	public static String parseString(Object aValue, boolean aIsVk) {
		return aValue==null?"":String.valueOf(aValue);
	}
	public static String parseString(Object aValue) {
		return aValue!=null?String.valueOf(aValue):null ;
	}
	public static boolean parseBoolean(Object aValue) {
		if (aValue==null) return false;
		if (parseString(aValue).equals("1")) return true ;
		return false ;
	}
	
	public static java.sql.Time parseTime(Object aValue) {
		if (aValue==null) return null ;
		if (aValue instanceof java.sql.Time) {
			return (java.sql.Time)aValue ;
		}
		
		return null ;
	}
	
	public static java.sql.Date parseDate(Object aValue) {
		if (aValue==null) return null ;
		if (aValue instanceof java.sql.Date) {
			return (java.sql.Date)aValue ;
		}
		if (aValue instanceof java.util.Date) {
			return new java.sql.Date(((java.util.Date)aValue).getTime()) ;
		}
		return null ;
	}
	
	public static java.sql.Date parseDate(Object aValue,int aAddDay) {
		java.sql.Date dat = parseDate(aValue) ;
		if (dat!=null) {
			Calendar cal = Calendar.getInstance() ;
			cal.setTime(dat) ;
			cal.add(Calendar.DATE, aAddDay) ;
			return new java.sql.Date(cal.getTime().getTime()) ;
		}
		return null ;
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
	
	public static String toWords(BigDecimal sum) {
		  System.out.println(sum) ;
		    BigDecimal TAUSEND = new BigDecimal(1000);
		    int i, mny;
		    StringBuffer result = new StringBuffer("");
		    BigDecimal divisor; // делитель
		    BigDecimal psum = sum;

		    int one = 1;
		    int four = 2;
		    int many = 3;

		    int hun = 4;
		    int dec = 3;
		    int dec2 = 2;

		    if (sum.equals(BigDecimal.ZERO))
		      return "ноль ";
		    if (sum.compareTo(BigDecimal.ZERO) < 0) {
		      result.append("минус ");
		      psum = psum.negate();
		    }

		    for (i = 0, divisor = BigDecimal.ONE; i < DG_POWER - 1; i++) {
		      divisor = divisor.multiply(TAUSEND);
		      if (sum.compareTo(divisor) < 0) {
		        i++;
		        break; // no need to go further
		      }
		    }
		    // start from previous value
		    for (; i >= 0; i--) {
		      mny = psum.divide(divisor).intValue();
		      psum = psum.remainder(divisor);
		      // str="";
		      if (mny == 0) {
		        // if(i>0) continue;
		        if (i == 0) {
		          result.append(a_power[i][one]);
		        }
		      } else {
		        if (mny >= 100) {
		          result.append(digit[mny / 100][hun]);
		          mny %= 100;
		        }
		        if (mny >= 20) {
		          result.append(digit[mny / 10][dec]);
		          mny %= 10;
		        }
		        if (mny >= 10) {
		          result.append(digit[mny - 10][dec2]);
		        } else {
		          if (mny >= 1)
		            result.append(digit[mny]["0".equals(a_power[i][0]) ? 0
		                : 1]);
		        }
		        switch (mny) {
		        case 1:
		          result.append(a_power[i][one]);
		          break;
		        case 2:
		        case 3:
		        case 4:
		          result.append(a_power[i][four]);
		          break;
		        default:
		          result.append(a_power[i][many]);
		          break;
		        }
		      }
		      divisor = divisor.divide(TAUSEND);
		    }
		    return result.toString();
		  }
	  private final static String[][] a_power = new String[][]{
		   {"0", ""            , ""             ,""              },  // 1
		   {"1", "тысяча "     , "тысячи "      ,"тысяч "        },  // 2
		   {"0", "миллион "    , "миллиона "    ,"миллионов "    },  // 3
		   {"0", "миллиард "   , "миллиарда "   ,"миллиардов "   },  // 4
		   {"0", "триллион "   , "триллиона "   ,"триллионов "   },  // 5
		   {"0", "квадриллион ", "квадриллиона ","квадриллионов "},  // 6
		   {"0", "квинтиллион ", "квинтиллиона ","квинтиллионов "},   // 7
		   {"0", "квинтиллион1 ", "квинтиллиона1 ","квинтиллионов1 "}   // 8
		  };

		  private final static String[][] digit = new String[][] {
		   {""       ,""       , "десять "      , ""            ,""          },
		   {"один "  ,"одна "  , "одиннадцать " , "десять "     ,"сто "      },
		   {"два "   ,"две "   , "двенадцать "  , "двадцать "   ,"двести "   },
		   {"три "   ,"три "   , "тринадцать "  , "тридцать "   ,"триста "   },
		   {"четыре ","четыре ", "четырнадцать ", "сорок "      ,"четыреста "},
		   {"пять "  ,"пять "  , "пятнадцать "  , "пятьдесят "  ,"пятьсот "  },
		   {"шесть " ,"шесть " , "шестнадцать " , "шестьдесят " ,"шестьсот " },
		   {"семь "  ,"семь "  , "семнадцать "  , "семьдесят "  ,"семьсот "  },
		   {"восемь ","восемь ", "восемнадцать ", "восемьдесят ","восемьсот "},
		   {"девять ","девять ", "девятнадцать ", "девяносто "  ,"девятьсот "}
		  };
		  public final static int DG_POWER=10;

}
