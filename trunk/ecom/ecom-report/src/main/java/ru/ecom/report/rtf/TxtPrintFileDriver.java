package ru.ecom.report.rtf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;

import org.jdom.Element;

import ru.ecom.report.replace.IValueGetter;
import ru.ecom.report.replace.ReplaceHelper;
import ru.ecom.report.replace.SetValueException;

public class TxtPrintFileDriver implements IPrintFileDriver {

	public TxtPrintFileDriver() {
	}

	private String theId ;
	private File theTemplateDir ;
	private String theKey ;
	private File theWorkDir ;
	
	public IPrintFileDriver newInstance(String id, File templateDir, String key, String theWorkDir) {
		TxtPrintFileDriver d = new TxtPrintFileDriver() ;
		d.theId = id ;
		d.theTemplateDir = templateDir ;
		d.theKey = key ;
		d.theWorkDir = new File(theWorkDir) ;
		return d;
	}

	public boolean isRtfReplaceMode() {
		return false ;
	}

	public void buildFile() {
	}
	public void buildFile(boolean aRemovedTempFile) {
	}

	public File getInputFile() {
		return new File(theTemplateDir, theKey+".txt") ;
	}

	public File getOutputFile() {
		return new File(theWorkDir, getResultFilename()) ;
	}

	public String getResultFilename() {
		return theKey+"-"+theLogin+"-"+theId+".txt" ;
	}

	public boolean isAccept(File aDir, String aKeyName) {
		
		return new File(aDir, aKeyName+".txt").exists() ;
	}


	public void prepare() {
	}

	public String getEncoding() {
		return "Cp866" ;
	}
    public void print(ReplaceHelper aReplaceHelper,  IValueGetter aValueGetter) throws RtfPrintException {
    	//File aTemplateFile = getInputFile() ;
    	//File aOutputFile = getOutputFile() ;
    	//String aEncoding = getEncoding() ;
        LineNumberReader in = null ;
        PrintWriter out = null ;
        try {

            in = new LineNumberReader(new InputStreamReader(new FileInputStream(getInputFile()), getEncoding()));
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getOutputFile()), getEncoding()));
            String line ;
            StringBuilder sb = new StringBuilder();
            int forCount = 0;
            String forText = ""; 
            String forParam = "" ;
            int cntId = 0 ;
            while ( (line=in.readLine())!=null) {
            	cntId++ ;
            	boolean next = true ;
            	if (cntId==1 && line!=null ) {
            		if ( line.startsWith("cntSymbol=")) {
	            		line = line.replace("cntSymbol=", "") ;
	            		try {
	            			theMaxLineLength = Integer.valueOf(line) ;
	            		} catch (Exception e) {
	            			theMaxLineLength = 77 ;
						}
	            		next = false ;
            		} else {
            			theMaxLineLength = 77 ;
            		}
            	} 
            	if (next) {
                sb.append(line) ;
                //System.out.println(line) ;
                boolean isBeginFor = OdtPrintFileDriver.isBeginFor(line) ;
        		boolean isEndFor = OdtPrintFileDriver.isEndFor(line) ;
            	if (forCount==0 && !isBeginFor) {
            		//System.out.println("Нет цикла") ;
            		recordLine(out,aReplaceHelper, aValueGetter, line);   
            	} else {
            		if (isBeginFor) {
            			//System.out.println("Начало цикла") ;
            			forCount = forCount+1 ;
            			if (forCount>1) {
            				forText = forText + line+"\n" ;
            			} else {
            				forParam=line ;
            				forText = "";
            			}
            			//forText = line ;
            			
            		} else if (isEndFor) {
            			
            			
            			forCount = forCount-1 ;
            			if (forCount==0) {
            				//System.out.println("Окончание цикла") ;
            				try {
            					forLine(out,aReplaceHelper, aValueGetter,forParam, forText);
            					forText = "" ;
            					forParam = "" ;
            				} catch (Exception e) {
            					out.println("ОШИБКА!!!!!!!!!!!"+e.getStackTrace()) ;
            				}
            			} else {
            				forText = forText + line+"\n" ;
            				//System.out.println("Один из циклов закончился цикла") ;
            			}
            		} else {
            			//System.out.println("Продолжение цикла") ;
            			forText = forText+line +"\n";
            		}
            	}
                
               // System.out.println(outStr) ;
               
            }}
            //String outStr = aReplaceHelper.replaceWithValues(sb.toString(), aValueGetter).toString() ;
            //out.print(outStr) ;
        } catch (Exception e) {
            throw new RtfPrintException("Ошибка печати",e);
        } finally {
            if(out!=null) out.close() ;
            if(in!=null) try { in.close() ; } catch (Exception e) {
                throw new RtfPrintException("Ошибка закрытия файла: "+e.getMessage(),e) ;
            }
        }
    }
    private  void recordLine(PrintWriter aOut,ReplaceHelper aReplaceHelper, IValueGetter aValueGetter, String aLine) {
    	try {
    		String outStr = aReplaceHelper.replaceWithValues(aLine, aValueGetter).toString();
    		getMultyLine(outStr, aOut) ;
    	} catch (SetValueException e) {
    		e.printStackTrace();
    	}
    	
    }
    private  void forLine(PrintWriter aOut,ReplaceHelper aReplaceHelper, IValueGetter aValueGetter,String aParam, String aLine) {
		try {
			String[] strs = aLine.split("\n") ;
			int forCount = 0;
            String forText = ""; 
            String forParam="" ;
        	StringTokenizer st = new StringTokenizer(aParam, " \t:");
            st.nextToken() ;
            String name = st.nextToken() ;
            String expression = st.nextToken() ;
            List list = (List) aValueGetter.getValue(expression) ;
            //aValueGetter.set(name, list) ;
            //System.out.println("ЦИКЛ===========") ;
			//System.out.println("forText="+aLine) ;
			//System.out.println("forParam="+aParam) ;
			//System.out.println("cnt="+strs.length) ;
            
        	for (Object val:list) {
        		//System.out.println(name) ;
        		//System.out.println(val) ;
        		aValueGetter.set(name, val);
				for (String str: strs) {
	                boolean isBeginFor = OdtPrintFileDriver.isBeginFor(str) ;
	        		boolean isEndFor = OdtPrintFileDriver.isEndFor(str) ;
	            	if (forCount==0 && !isBeginFor) {
	            		//System.out.println("Нет цикла") ;
	            		//System.out.println("=== str="+str) ;
	            		recordLine(aOut,aReplaceHelper, aValueGetter, str);   
	            	} else {
	            		if (isBeginFor) {
	            			forCount = forCount+1 ;
	            			if (forCount>1) {
	            				forText = forText + str+"\n" ;
	            			} else {
	            				forParam = str ;
	            				forText = "";
	            			}
	            			
	            		} else if (isEndFor) {
	             			forCount = forCount-1 ;
	            			if (forCount==0) {
	            				try {
	            					forLine(aOut,aReplaceHelper, aValueGetter,forParam,forText); 
	            					forText="";
	            				} catch (Exception e) {
	            					aOut.println("ОШИБКА FOR!!!!!!!!!!!"+e.getStackTrace()) ;
	            				}
	            			} else {
	            				forText = forText+str+"\n" ;
	            			}
	            		} else {
	            			forText = forText+str+"\n" ;
	            		}
	            	}
					
				}
				
        	}
		} catch (SetValueException e) {
			e.printStackTrace();
		}
        
    }
    
    
    private  void getMultyLine(String aStr, PrintWriter aOut) {
    	//aStr.replace("\n\n", "\n") ;
    	boolean print = true ;
    	while (aStr.length()>theMaxLineLength) {
    		//System.out.println("str="+aStr) ;
    		int lastInd ;
    		String str = aStr.substring(0,theMaxLineLength-1) ;;
    		if (aStr.substring(theMaxLineLength-1,theMaxLineLength).equals(" ")) {
    			
        		lastInd = theMaxLineLength ;
    		} else {
    			str = aStr.substring(0,theMaxLineLength-1) ;
    			int lastInd1 = str.lastIndexOf(".") ;
    			int lastInd2 = str.lastIndexOf(",") ;
        		lastInd = str.lastIndexOf(" ") ;
        		boolean adding = true ;
        		if (lastInd1<theMaxLineLength && lastInd1>lastInd) {
        			adding = false ;
        			lastInd = lastInd1+1 ;
        		}
        		if (lastInd1<theMaxLineLength && lastInd2>lastInd) {
        			adding = false ;
        			lastInd = lastInd2+1 ;
        		}
        		if (lastInd==-1) {
        			lastInd=theMaxLineLength-1 ;
        			adding = false ;
        		} 
        		if (adding) lastInd=lastInd+1 ;
    		}
    		
    		str = aStr.substring(0,lastInd) ;
    		aOut.println(str) ;
    		if (aStr.length()>=lastInd){
    			aStr = aStr.substring(lastInd) ;
    		} else {
    			aStr = "" ;
    			print = false ;
    		}
    		
    		
    		
    	}
    	if (print) aOut.println(aStr) ;

    }
	

	/** Максимальная длина строки для текстового файла */
	private int theMaxLineLength;
	/** Логин */
	public String getLogin() {return theLogin;}
	public void setLogin(String aLogin) {theLogin = aLogin;}

	/** Логин */
	private String theLogin;
}
