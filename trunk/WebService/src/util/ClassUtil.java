package util;

public class ClassUtil {
	public static void main (String args[]){
		Object obj;
		obj = 1;
		print(obj+"="+getClassName(obj, false));
		obj = "1";
		print(obj+"="+getClassName(obj, false));
		obj = true;
		print(obj+"="+getClassName(obj, false));
		print(obj+"="+getClassName(obj, true));
	}
	public static String getClassName(Object aObject, Boolean aShort){
		String ret = "";
		if(aObject!=null){ 
			ret = aObject.getClass().getName();
			if(aShort!=null && aShort == true) ret=StringUtil.getLastPiece(ret, ".");
			}
		return ret;
	}
	
	private static void print(Object aObject){
		System.out.println(""+aObject);
	}

}
