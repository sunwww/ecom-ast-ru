package ru.ecom.report.rtf;

import ru.ecom.report.replace.IValueGetter;
import ru.ecom.report.replace.ReplaceHelper;
import ru.ecom.report.replace.SetValueException;

import java.io.*;
import java.util.List;
import java.util.StringTokenizer;

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

        try(LineNumberReader in = new LineNumberReader(new InputStreamReader(new FileInputStream(getInputFile()), getEncoding()));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getOutputFile()), getEncoding()))) {
            String line ;
            int forCount = 0;
            StringBuilder forText = new StringBuilder();
            String forParam = "" ;
            int cntId = 0 ;
            while ( (line=in.readLine())!=null) {
            	cntId++ ;
            	boolean next = true ;
            	if (cntId==1) {
            		if ( line.startsWith("cntSymbol=")) {
	            		line = line.replace("cntSymbol=", "") ;
	            		try {
	            			theMaxLineLength = Integer.parseInt(line) ;
	            		} catch (Exception e) {
	            			theMaxLineLength = 77 ;
						}
	            		next = false ;
            		} else {
            			theMaxLineLength = 77 ;
            		}
            	} 
            	if (next) {
                boolean isBeginFor = OdtPrintFileDriver.isBeginFor(line) ;
				boolean isEndFor = OdtPrintFileDriver.isEndFor(line) ;
            	if (forCount==0 && !isBeginFor) {
            		recordLine(out,aReplaceHelper, aValueGetter, line);
            	} else {
            		if (isBeginFor) {
            			//System.out.println("Начало цикла") ;
            			forCount = forCount+1 ;
            			if (forCount>1) {
            				forText.append(line).append("\n") ;
            			} else {
            				forParam=line ;
            				forText = new StringBuilder();
            			}
            		} else if (isEndFor) {
            			forCount = forCount-1 ;
            			if (forCount==0) {
            				//System.out.println("Окончание цикла") ;
            				try {
            					forLine(out,aReplaceHelper, aValueGetter,forParam, forText.toString());
            					forText = new StringBuilder() ;
            					forParam = "" ;
            				} catch (Exception e) {
            					out.println("ОШИБКА!!!!!!!!!!!"+e.getStackTrace()) ;
            				}
            			} else {
            				forText.append(line).append("\n") ;
            			}
            		} else {
            			forText.append(line).append("\n");
            		}
            	}
            }}
        } catch (Exception e) {
            throw new RtfPrintException("Ошибка печати",e);
        }
    }

	/**
	 * Вернуть файл с символами для замены перед печатью на матричном принтере
	 *
	 * @return File
	 */
    private File getTxtMatrixFile() {
		return new File(theTemplateDir, "bad_matrix_change.txt") ;
	}

	/**
	 * Заменить в строке недопустимые для печати символы
	 *
	 * @param outStr String Строка, в которой нужно заменить символы
	 * @return String изменённая строка
	 */
	private String changeStringForMatrixPrinter(String outStr) {
		String res=outStr;
		String line ;
		try (LineNumberReader in = new LineNumberReader(new InputStreamReader(new FileInputStream(getTxtMatrixFile()), "UTF-8"))) {
			while ( (line=in.readLine())!=null) {
				String[] syms = line.split(" ");
				if (syms.length>0 && !syms[0].equals("")) {
					if (syms.length > 1 && !syms[1].equals("") && !syms[1].equals("?"))
						res = res.replace(syms[0], syms[1]);
					else
						res = res.replace(syms[0], "");
				}
			}
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		res = res.replace("<text:line-break/>","\n");
		return res;
	}
    private void recordLine(PrintWriter aOut,ReplaceHelper aReplaceHelper, IValueGetter aValueGetter, String aLine) {
    	try {
			String outStr = aReplaceHelper.replaceWithValues(aLine, aValueGetter).toString();
    		getMultyLine(changeStringForMatrixPrinter(outStr), aOut) ;
    	} catch (SetValueException e) {
    		e.printStackTrace();
    	}
    }
    private  void forLine(PrintWriter aOut,ReplaceHelper aReplaceHelper, IValueGetter aValueGetter,String aParam, String aLine) {
		try {
			String[] strs = aLine.split("\n") ;
			int forCount = 0;
            StringBuilder forText = new StringBuilder();
            String forParam="" ;
        	StringTokenizer st = new StringTokenizer(aParam, " \t:");
            st.nextToken() ;
            String name = st.nextToken() ;
            String expression = st.nextToken() ;
            List list = (List) aValueGetter.getValue(expression) ;
        	for (Object val:list) {
        		aValueGetter.set(name, val);
				for (String str: strs) {
	                boolean isBeginFor = OdtPrintFileDriver.isBeginFor(str) ;
	        		boolean isEndFor = OdtPrintFileDriver.isEndFor(str) ;
	            	if (forCount==0 && !isBeginFor) {
	            		recordLine(aOut,aReplaceHelper, aValueGetter, str);   
	            	} else {
	            		if (isBeginFor) {
	            			forCount = forCount+1 ;
	            			if (forCount>1) {
	            				forText.append(str).append("\n") ;
	            			} else {
	            				forParam = str ;
	            				forText = new StringBuilder();
	            			}
	            			
	            		} else if (isEndFor) {
	             			forCount = forCount-1 ;
	            			if (forCount==0) {
	            				try {
	            					forLine(aOut,aReplaceHelper, aValueGetter,forParam,forText.toString());
	            					forText=new StringBuilder();
	            				} catch (Exception e) {
	            					aOut.println("ОШИБКА FOR!!!!!!!!!!!"+e.getStackTrace()) ;
	            				}
	            			} else {
	            				forText.append(str).append("\n") ;
	            			}
	            		} else {
	            			forText.append(str).append("\n") ;
	            		}
	            	}
					
				}
				
        	}
		} catch (SetValueException e) {
			e.printStackTrace();
		}
        
    }
    
    
    private void getMultyLine(String aStr, PrintWriter aOut) {
    	//aStr.replace("\n\n", "\n") ;
    	boolean print = true ;
    	while (aStr.length()>theMaxLineLength) {
    		//System.out.println("str="+aStr) ;
    		int lastInd ;
    		String str ;//= aStr.substring(0,theMaxLineLength-1) ;;
    		if (aStr.substring(theMaxLineLength-1,theMaxLineLength).equals(" ")) {
    			
        		lastInd = theMaxLineLength ;
    		} else {
    			str = aStr.substring(0,theMaxLineLength-1) ;
    			int lastInd1 = str.lastIndexOf('.') ;
    			int lastInd2 = str.lastIndexOf(',') ;
        		lastInd = str.lastIndexOf(' ') ;
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