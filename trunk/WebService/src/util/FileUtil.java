package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileUtil {
	public static void main(String args[]) throws Exception{
/*		try{
			int test = 1/0;
		}
		catch(Exception ex){
			writeErrorFile(ex,"FileUtilError.txt");
		}
		StringUtil.print("exists",exists(getTempFileName("FileUtilError.txt"),true));
		StringUtil.print("rename", rename("FileUtilError.txt","FileUtilError.txt.copy"));*/
		
/*		
  		String fileName = "tempIsOpenedFile.txt";
 		StringUtil.print("isOpened",isOpened(fileName));
		RandomAccessFile file = new RandomAccessFile(fileName,"rw");
		file.writeChars("test");
		StringUtil.print("isOpened",isOpened(fileName));
		file.close();*/
		//System.out.println(waitExists(getTempFileName("error1.txt")));
		
		StringUtil.print("toString",toString("/home/user/ExtDisp.sql"));
		
	}
	public static String toString(String aFileName){
		StringBuilder sb = new StringBuilder();
		try {
			FileReader fr = new FileReader(aFileName);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			String ls = System.getProperty("line.separator");
			while ((line=br.readLine())!=null) {
				sb.append(line);
				sb.append(ls);
			}
			sb.deleteCharAt(sb.length()-1);
		} catch (FileNotFoundException e) {
			writeErrorFile(e,"FileUtilToStringError.txt");
			e.printStackTrace();
		} catch (IOException e) {
			writeErrorFile(e,"FileUtilToStringError.txt");
			e.printStackTrace();
		}
		return sb.toString();
	}
	public static boolean rename(String aFileNameFrom, String aFileNameTo){
		boolean ret = false;
		if(StringUtil.isNotEmpty(aFileNameFrom) && StringUtil.isNotEmpty(aFileNameTo)){
			if(exists(aFileNameFrom, false)){
				File fileFrom = new File(aFileNameFrom);
				File fileTo = new File(aFileNameTo);
				ret = fileFrom.renameTo(fileTo);
			}
		}
		return ret;
	}
	//don't work
	public static boolean isOpened(String aFileName) throws IOException{
		boolean isFileUnlocked = false;
		try {
		    org.apache.commons.io.FileUtils.touch(new File(aFileName));
		    isFileUnlocked = true;
		} catch (IOException e) {
		    isFileUnlocked = false;
		}
		return isFileUnlocked;
	}
	public static boolean createDirs(String aDirName){
		Boolean ret = false;
		if(StringUtil.isNotEmpty(aDirName)){
			ret = new File(aDirName).mkdirs();
		}
		return ret; 
	}
	
	public static boolean deleteFile(String aFileName){
		Boolean ret = false;
		if(StringUtil.isNotEmpty(aFileName)){
			File file = new File(aFileName);
			file.delete();
		}
		return ret; 
	}
	public static String getTempFileName(String aFileName){
		String ret = "";
		if(StringUtil.isNotEmpty(aFileName)){
			ret = SystemUtil.getRiamsTempDir()+aFileName;
		}
		return ret;
	}
	public static boolean waitExists(String aFileName){
		while(!exists(aFileName, true)){
			try {
				Thread.sleep(1000);
				System.out.println(SystemUtil.getCurrentTime());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	public static boolean exists(String aFileName, Boolean aCheckSize){
		boolean ret = false;
		File file = new File(aFileName);
		ret = file.exists();
		if(aCheckSize && file.length()<1) ret = false; 
		return ret;
	}
	public static BufferedWriter getBufferedWriter(String aFileName, String aCodePage){
		BufferedWriter writer = null;
		try {
			FileOutputStream fos = new FileOutputStream(aFileName);
			OutputStreamWriter osw = null;
			if(StringUtil.isNotEmpty(aCodePage)) osw = new OutputStreamWriter(fos, aCodePage);
			else osw = new OutputStreamWriter(fos);
			writer = new BufferedWriter(osw);
		} catch (IOException ex){
		    ex.printStackTrace();
		}  
		return writer;
	}
	public static void writeErrorFile(Exception aException, String aFileName){
		if(!aFileName.contains("/")) aFileName=getTempFileName(aFileName);
		BufferedWriter bw = getBufferedWriter(aFileName, "");
		String error = aException.toString();
		try {
			bw.write(error);
			bw.flush();
			bw.close();
			aException.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//aRegims= <groups (u - owner, g - owner group, o - others) and operation (+-) and regim (wrx),...>
	public static boolean chmod(String aFileName, String aRegims, boolean aRecursive){
		boolean ret = false; 
		if(StringUtil.isNotEmpty(aFileName) && StringUtil.isNotEmpty(aRegims)){
			if(exists(aFileName, false)) {
				StringBuilder sb = new StringBuilder();
				sb.append("chmod");
				if(aRecursive==true) sb.append(" -R");
				sb.append(" "+aRegims);
				sb.append(aFileName);
				SystemUtil.executeCommand(sb.toString());
			}
		}
		return ret;
	}

}
