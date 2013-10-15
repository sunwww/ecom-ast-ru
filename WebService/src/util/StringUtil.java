package util;

import java.util.ArrayList;


public class StringUtil {
	public static void main(String args[]){
		/*String str;
		str="10";
		print("isNumber "+str+"="+isNumber(str));
		str="10.";
		print("isNumber "+str+"="+isNumber(str));
		str="10.1";
		print("isNumber "+str+"="+isNumber(str));
		str="10.1a";
		print("isNumber "+str+"="+isNumber(str));
		str="a10.1";
		print("isNumber "+str+"="+isNumber(str));
		str="10..1";
		print("isNumber "+str+"="+isNumber(str));
		str=".10.1";
		print("isNumber "+str+"="+isNumber(str));
		str="10.1.";
		print("isNumber "+str+"="+isNumber(str));
		str="-0.1";
		print("isNumber "+str+"="+isNumber(str));
		str="--10.1";
		print("isNumber "+str+"="+isNumber(str));
		print("java.lang.int".lastIndexOf("."));*/
		
/*		print(getPiece("java.lang.int",".",1));
		print(getPiece("java.lang.int",".",2));
		print(getPiece("java.lang.int",".",3));
		print(getPiece(".java.lang.int",".",1));
		print(getPiece(".java.lang.int",".",2));
		print(getPiece(".java.lang.int",".",3));
		print(getLastPiece(".java.lang.int.","."));
		print(getLastPiece(".java.lang.int","."));*/
		
/*		print(".java.lang.int".indexOf(".",-1));
		print(".java.lang.int".indexOf(".",0));
		print(".java.lang.int".indexOf(".",1));
		print(".java.lang.int".indexOf(".",2));
		

		print(".java.lang.int".substring(0,2));
		print(".java.lang.int".substring(1,3));*/
		
/*		split("java.lang.int",".");
		split(".java.lang.int",".");
		split("java.lang.int.",".");
		split("java..lang.int",".");*/
		
		print(isTrue(""));
		print(isTrue("123"));
		print(isTrue("1"));
		print(isTrue("0"));
		print(isTrue(1));
		print(isTrue(0));
		print(isTrue(true));
		print(isTrue(false));

	}
	public static ArrayList<String> split(String aString, String aDelimiter){
		ArrayList<String> ret = new ArrayList<String>();
		int beginIndex = -1;
		int i=0;
		do {		
			ret.add(i,getPieceFromIndex(aString, aDelimiter, beginIndex));
			beginIndex=aString.indexOf(aDelimiter, beginIndex+1);
			i=i+1;
		} while(beginIndex!=-1);
		return ret;
	}
	public static String getPiece(String aString, String aDelimiter, int aPosition){	
		String ret="";
		int beginIndex = -1;
		int i=1;
		do {
			if(aPosition==i) ret=getPieceFromIndex(aString, aDelimiter, beginIndex);
			beginIndex=aString.indexOf(aDelimiter, beginIndex+1);
			i=i+1;
		} while(beginIndex!=-1);
		return ret;
	}
	public static String getLastPiece(String aString, String aDelimiter){
		String ret = "";
		int beginIndex = aString.lastIndexOf(aDelimiter);
		ret = beginIndex==-1 ? aString : getPieceFromIndex(aString, aDelimiter, beginIndex);
		return ret;
	}
	private static String getPieceFromIndex(String aString, String aDelimiter, int aBeginIndex){
		String ret="";
		int endIndex=aString.indexOf(aDelimiter, aBeginIndex+1);
		if(endIndex==-1) {
				endIndex=aString.length();
		}
		ret=aString.substring(aBeginIndex+1, endIndex);
		return ret;
		}
	public static String getPiece1(String aString, String aDelimeter, int aPosition){
		String ret = "";
		if(isNotEmpty(aString) && isNotEmpty(aDelimeter) && isNotEmpty(aPosition)){
			int i = 0;
			int beginIndex=0;
			int endIndex=0;
			boolean end=false;
			while(end==false){
				i = i+1;
				if(aPosition==i) {
					endIndex = aString.indexOf(aDelimeter, beginIndex+1);
					if(endIndex==-1) endIndex=aString.length();
					ret=aString.substring(beginIndex+1, endIndex);
					beginIndex=-1;
					end=true;
				} 
				beginIndex = aString.indexOf(aDelimeter, beginIndex+1);
				if(beginIndex==-1) end=true;
			}
		}
		return ret;
	}
	public static boolean isNotEmpty(int aInteger){
		return aInteger!=0;
	}
	public static boolean isNotEmpty(String aString){  
	   return aString != null && aString.trim()!="";  
	}
	public static boolean isNumber(String aString){
		Boolean res = false;
		if(isNotEmpty(aString)) res=aString.matches("^-{0,1}\\d+\\.{0,1}\\d*$");
		return res;
	}
	public static boolean isTrue(String aValue){
		boolean ret = false;
		if (aValue!=null) ret = aValue.equals("1") ? true : false;
		return ret;
	}
	public static boolean isTrue(int aValue){
		return aValue>0? true : false;
	}
	public static boolean isTrue(boolean aValue){
		return aValue;
	}
	public static boolean isTrue(Boolean aValue){
		return aValue==null ? false : aValue;
	}
	private static void print(Object aObject){
		System.out.println(""+aObject);
	}
	public static void print(String aTitle, Object aObject){
		System.out.println(aTitle+"="+aObject);
	}
}
