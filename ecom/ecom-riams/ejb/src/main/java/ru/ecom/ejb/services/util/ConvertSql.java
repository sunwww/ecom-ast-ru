package ru.ecom.ejb.services.util;

import ru.nuzmsh.util.format.DateFormat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

public class ConvertSql {
	
	public static String getDay(Object aValue) {
		return getDatePart(aValue, "day");
	}

	public static String getYear(Object aValue) {
		return getDatePart(aValue, "year");
	}

    public static String getMonth(Object aValue) {
		return getDatePart(aValue, "month");
	}
	public static String getDatePart(Object aValue, String aDatePart) {
		java.sql.Date date = parseDate(aValue);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (aDatePart.equals("day")) {
			return ""+cal.get(Calendar.DATE);
		} else if (aDatePart.equals("month")) {
			if (cal.get(Calendar.MONTH)<9) {
				return "0"+(cal.get(Calendar.MONTH)+1);
			} else {
				return ""+(cal.get(Calendar.MONTH)+1);
			}
		} else if (aDatePart.equals("year")) {
			return ""+cal.get(Calendar.YEAR);
		} else {
			return ""+cal.getTime();
		}
	}
	
	public static Long parseLong(Object aValue) {
		if (aValue==null) return null ;
		if (aValue instanceof Integer) {
			return Long.valueOf((Integer) aValue) ;
		}
		if(aValue instanceof BigInteger) {
			BigInteger bigint = (BigInteger) aValue ;
			
			return bigint.longValue() ;
		} 
		if (aValue instanceof Number) {
			Number number = (Number) aValue ;
			return number.longValue() ;
		}
		if (aValue instanceof String) {
			return Long.valueOf((String) aValue);
		}
		return null ;
	}
	public static String parseString(Object aValue, boolean aIsVk) {
		return aValue==null?"":String.valueOf(aValue);
	}
	public static String parseString(Object aValue) {
		return aValue!=null?String.valueOf(aValue):null ;
	}
	public static boolean parseBoolean(Object aValue) {
		if (aValue==null) return false;
		return parseString(aValue).equals("1");
	}
	
	public static java.sql.Time parseTime(Object aValue) {
		if (aValue==null) return null ;
		if (aValue instanceof java.sql.Time) {
			return (java.sql.Time)aValue ;
		}
		
		return null ;
	}
	public static String getDay(java.sql.Date aDate) {
		if (aDate==null) return "" ;
		Calendar cal = Calendar.getInstance() ;
		cal.setTime(aDate) ;
		return ""+cal.get(Calendar.DAY_OF_MONTH) ;
	}
	public static String getYear(java.sql.Date aDate) {
		if (aDate==null) return "" ;
		Calendar cal = Calendar.getInstance() ;
		cal.setTime(aDate) ;
		return ""+cal.get(Calendar.YEAR) ;
	}
	public static String getMonth(java.sql.Date aDate,boolean aFullname) {
		return getMonth (aDate, aFullname, true);
	}
	public static String getMonth(java.sql.Date aDate,boolean aFullname, boolean aTitleMonth) {
		if (aDate==null) return "" ;
		Calendar cal = Calendar.getInstance() ;
		cal.setTime(aDate) ;
		
		if (Calendar.JANUARY==cal.get(Calendar.MONTH)) return aFullname?(aTitleMonth?"Январь":"января"):"01" ; 
		if (Calendar.FEBRUARY==cal.get(Calendar.MONTH)) return aFullname?(aTitleMonth?"Февраль":"февраля"):"02" ; 
		if (Calendar.MARCH==cal.get(Calendar.MONTH)) return aFullname?(aTitleMonth?"Март":"марта"):"03" ; 
		if (Calendar.APRIL==cal.get(Calendar.MONTH)) return aFullname?(aTitleMonth?"Апрель":"апреля"):"04" ; 
		if (Calendar.MAY==cal.get(Calendar.MONTH)) return aFullname?(aTitleMonth?"Май":"мая"):"05" ; 
		if (Calendar.JUNE==cal.get(Calendar.MONTH)) return aFullname?(aTitleMonth?"Июнь":"июня"):"06" ; 
		if (Calendar.JULY==cal.get(Calendar.MONTH)) return aFullname?(aTitleMonth?"Июль":"июля"):"07" ; 
		if (Calendar.AUGUST==cal.get(Calendar.MONTH)) return aFullname?(aTitleMonth?"Август":"августа"):"08" ; 
		if (Calendar.SEPTEMBER==cal.get(Calendar.MONTH)) return aFullname?(aTitleMonth?"Сентябрь":"сентября"):"09" ; 
		if (Calendar.OCTOBER==cal.get(Calendar.MONTH)) return aFullname?(aTitleMonth?"Октябрь":"октября"):"10" ; 
		if (Calendar.NOVEMBER==cal.get(Calendar.MONTH)) return aFullname?(aTitleMonth?"Ноябрь":"ноября"):"11" ; 
		if (Calendar.DECEMBER==cal.get(Calendar.MONTH)) return aFullname?(aTitleMonth?"Декабрь":"декабря"):"12" ; 
		return "" ;
	}
	public static java.sql.Date parseDate(Object aValue) {
		if (aValue==null) return null ;
		if (aValue instanceof java.sql.Date) {
			return (java.sql.Date)aValue ;
		} else if (aValue instanceof java.util.Date) {
			return new java.sql.Date(((java.util.Date)aValue).getTime()) ;
		} else if (aValue instanceof java.lang.String) {
			try {
				return DateFormat.parseSqlDate((String)aValue) ;
			} catch (Exception e) {
			}
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
		 if (in.indexOf('-')==-1) {
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
		if (aMkb2.indexOf('.')>-1) {
			return aMkb1.contains(aMkb2);
		}
		
		int ind = aMkb1.indexOf('.') ;
		return (ind==-1?aMkb1:aMkb1.substring(0,ind)).equals(aMkb2) ;
	}
	private static boolean soderjit(String aMkb1,String aMkb2) {
		int tst=0;
		int cnt1 = aMkb1.length() ;
		int cnt2 = aMkb2.length() ;
		for (int i=0 ;(i< cnt1&&i<cnt2) ;i++) {
			
			int c1 = Character.getNumericValue(aMkb1.charAt(i)) ;
			int c2 = Character.getNumericValue(aMkb2.charAt(i)) ;
			if (c1>c2) {
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
        return aString.toLowerCase()
                .replace("а", "a")
                .replace("б", "b")
                .replace("в", "v")
                .replace("г", "g")
                .replace("д", "d")
                .replace("е", "e")
                .replace("ё", "e")
                .replace("ж", "zh")
                .replace("з", "z")
                .replace("и", "i")
                .replace("й", "i")
                .replace("к", "k")
                .replace("л", "l")
                .replace("м", "m")
                .replace("н", "n")
                .replace("о", "o")
                .replace("п", "p")
                .replace("р", "r")
                .replace("с", "s")
                .replace("т", "t")
                .replace("у", "u")
                .replace("ф", "f")
                .replace("х", "h")
                .replace("ц", "ts")
                .replace("ч", "ch")
                .replace("ш", "sh")
                .replace("щ", "shch")
                .replace("ъ", "")
                .replace("ы", "y")
                .replace("ь", "")
                .replace("э", "e")
                .replace("ю", "yu")
                .replace("я", "ya");
	}
	
	public static String toWords(BigDecimal sum) {
		    BigDecimal TAUSEND = new BigDecimal(1000);
		    int i, mny;
		    StringBuilder result = new StringBuilder();
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
	  private static final String[][] a_power = new String[][]{
		   {"0", ""            , ""             ,""              },  // 1
		   {"1", "тысяча "     , "тысячи "      ,"тысяч "        },  // 2
		   {"0", "миллион "    , "миллиона "    ,"миллионов "    },  // 3
		   {"0", "миллиард "   , "миллиарда "   ,"миллиардов "   },  // 4
		   {"0", "триллион "   , "триллиона "   ,"триллионов "   },  // 5
		   {"0", "квадриллион ", "квадриллиона ","квадриллионов "},  // 6
		   {"0", "квинтиллион ", "квинтиллиона ","квинтиллионов "},   // 7
		   {"0", "квинтиллион1 ", "квинтиллиона1 ","квинтиллионов1 "}   // 8
		  };

		  private static final String[][] digit = new String[][] {
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
		  public static final int DG_POWER=10;

}
