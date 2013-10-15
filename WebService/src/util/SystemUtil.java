package util;

import java.io.IOException;

public class SystemUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getEnvVariable("JAVA_HOME"));
		System.out.println(getEnvVariable("JBOSS_HOME"));
		System.out.println(getRiamsDsConf());

	}
	public static String getRiamsTempDir(){
		String dirName = "/tmp/riams/";
		if(!FileUtil.exists(dirName, false)) FileUtil.createDirs(dirName);
		return dirName;
	}
	public static String getRiamsDsConf(){
		return "/etc/riams/riams-ds.xml";
	}
	public static String getPostgresqlDriverFile(){
		return "/etc/riams/postgresql.jar";
	}
	public static String getEnvVariable(String aVariableName){
		return aVariableName==null? "" : System.getenv(aVariableName);
	}
	public static long getCurrentTime(){
		return new java.util.Date().getTime();
	}
	public static void delay(int aTimeMs){
		if(aTimeMs<1) aTimeMs=1000;
		try {
			Thread.sleep(aTimeMs);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void executeCommand(String aCommand){
		if(StringUtil.isNotEmpty(aCommand))
			try {
				Runtime.getRuntime().exec(aCommand);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
