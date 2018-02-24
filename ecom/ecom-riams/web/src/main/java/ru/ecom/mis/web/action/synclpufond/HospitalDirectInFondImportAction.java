package ru.ecom.mis.web.action.synclpufond;

import java.io.File;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import ru.ecom.diary.ejb.service.protocol.IKdlDiaryService;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.xml.XmlUtil;
import ru.ecom.expomc.ejb.services.form.importformat.IImportFormatService;
import ru.ecom.expomc.ejb.services.importservice.ImportFileForm;
import ru.ecom.jaas.web.action.service.ServiceImportRolesForm;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.mis.web.action.bypassexport.AttachmentByLpuForm;
import ru.ecom.report.util.XmlDocument;
import ru.ecom.web.actions.monitor.MonitorActionForward;
import ru.ecom.web.util.ActionUtil;
import ru.ecom.web.util.FileUploadUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

public class HospitalDirectInFondImportAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, final HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
    	final IHospitalMedCaseService service = (IHospitalMedCaseService) Injection.find(aRequest).getService(IHospitalMedCaseService.class);
    	List<WebQueryResult> list = new LinkedList<WebQueryResult>() ;
    	final List<List> listFrom = new LinkedList<List>() ;
    	final List<WebQueryResult> listError = new LinkedList<WebQueryResult>() ;
    	
    	final String typeImport = ActionUtil.updateParameter("HospitalDirectDataInFond","typeImport","1", aRequest) ;
    	String typeView=ActionUtil.updateParameter("HospitalDirectDataInFond","typeView","1", aRequest) ;
    	String typeView1=ActionUtil.updateParameter("HospitalDirectDataInFond","typeView1","1", aRequest) ;
    	String typeDate=ActionUtil.updateParameter("HospitalDirectDataInFond","typeDate","1", aRequest) ;
    	//aRequest.setAttribute("HospitalDirectDataInFond"+"."+"typeMode", "4" );
    	String typeMode=ActionUtil.updateParameter("HospitalDirectDataInFond","typeMode","1", aRequest) ;
    	String typeEmergency=ActionUtil.updateParameter("HospitalDirectDataInFond","typeEmergency","1", aRequest) ;
    	String typeLpu=ActionUtil.updateParameter("HospitalDirectDataInFond","typeLpu","1", aRequest) ;
    	
    	String type =null;
    	boolean isErrorFile = false ;
    	final String filename ;
    	String contentType = null ;
    	String filenameError = null ;
         if (aForm!=null ) {
        	 
        	ServiceImportRolesForm form = (ServiceImportRolesForm)aForm ;
        	if (form.getFile()!=null ) {
        		contentType = form.getFile().getContentType() ;
        	} else {
        		contentType=null ;
        	}
        	if (contentType!=null&& contentType.toUpperCase().contains("XML")) {
        		filename=form.getFile().getFileName() ;
        		
        	InputStream in = null;
    		System.out.println("file="+contentType) ;
            try {
            	in =form.getFile().getInputStream() ;
               	Document doc = new SAXBuilder().build(in);
               	XmlDocument xmlDocError = new XmlDocument() ;
               	org.w3c.dom.Element root_error = xmlDocError.newElement(xmlDocError.getDocument(), "ZL_LIST", null);
                Element parConfigElement = doc.getRootElement();
                //System.out.println(new StringBuilder().append("		root=").append(parConfigElement).toString());
                Long i =Long.valueOf(1) ; int ind=0 ;
                for (Object o : parConfigElement.getChildren()) {
                    Element parEl = (Element) o;
                    if ("ZGLV".equals(parEl.getName())) {
                    	type = parEl.getChild("FILENAME").getText().trim().toUpperCase().substring(0,2) ;
                    //	System.out.println("type-------------"+type) ;
                    } else if("NPR".equals(parEl.getName())) {
                    	//System.out.println("parel=="+parEl.getChildren("REFREASON")) ;
                    	if (parEl.getChildren("REFREASON").isEmpty()) {
                    		//System.out.println("par===el=="+parEl.getChildren("REFREASON")) ;
	                    	WebQueryResult wqr = new WebQueryResult() ;
	                    	wqr.set1(getText(parEl,"N_NPR")) ;
	                    	wqr.set2(getDate(parEl,"D_NPR")) ;
	                    	wqr.set3(getText(parEl,"FOR_POM")) ;
	                    	wqr.set4(getText(parEl,"NCODE_MO")) ;
	                    	wqr.set5(getText(parEl,"DCODE_MO")) ;
	                    	wqr.set6(getText(parEl,"VPOLIS")) ;
	                    	wqr.set7(getText(parEl,"SPOLIS")) ;
	                    	wqr.set8(getText(parEl,"NPOLIS")) ;
	                    	wqr.set9(getText(parEl,"SMO")) ;
	                    	wqr.set10(getText(parEl,"SMO_OGRN")) ;
	                    	wqr.set11(getText(parEl,"SMO_OK")) ;
	                    	wqr.set12(getText(parEl,"SMO_NAM")) ;
	                    	wqr.set13(getText(parEl,"FAM")) ;
	                    	wqr.set14(getText(parEl,"IM")) ;
	                    	wqr.set15(getText(parEl,"OT")) ;
	                    	wqr.set16(getText(parEl,"W")) ;
	                    	wqr.set17(getDate(parEl,"DR")) ;
	                    	wqr.set18(getText(parEl,"CT")) ;
	                    	wqr.set19(getText(parEl,"DS1")) ;
	                    	wqr.set20(getText(parEl,"PROFIL")) ;
	                    	wqr.set21(getText(parEl,"IDDOKT")) ;
	                    	if (type.equals("N1")) {
	                    		wqr.set22(getDate(parEl,"DATE_1")) ;
	                    	} else {
	                    		wqr.set23(getDate(parEl,"DATE_1")) ;
	                    	}
	                    	wqr.set24(getText(parEl,"NHISTORY")) ;
	                    	wqr.set25(getTime(parEl,"TIME_1")) ;
	                    	wqr.set26(getDate(parEl,"DATE_2")) ;
	                    	wqr.set27(getText(parEl,"DET")) ;
	                    	wqr.set28(getText(parEl,"USL_OK")) ;
	                    	if (wqr.get1()!=null && !(""+wqr.get1()).equals("")){
	                    		list.add(wqr) ;
	                    		ind++ ;
	                    		if (ind>=70) {
	                    			listFrom.add(list) ;
	                    			System.out.println("Создается массив "+list.get(0).get1());
	                    			list = new LinkedList<WebQueryResult>() ;
	                    			ind=0 ;
	                    		}
	                    	}
                    	} else {
                    		isErrorFile = true ;
                    		org.w3c.dom.Element zap = xmlDocError.newElement(root_error, "NPR", null);
                    		String[] flds ={"N_NPR","D_NPR","FOR_POM","NCODE_MO","DCODE_MO"
                    				,"VPOLIS","SPOLIS","NPOLIS","SMO","SMO_OGRN","SMO_OK"
                    				,"SMO_NAM","FAM","IM","OT","W","DR","CT","DS1"
                    				,"PROFIL","IDDOKT","DATE_1","NHISTORY","TIME_1","DATE_2"
                    		} ;
                    		for (String fld:flds) {
                    			XmlUtil.recordElementInDocumentXml(xmlDocError,zap,fld,getText(parEl,fld),true,"") ;
                    		}
                    		String fld = "REFREASON" ;
                    		XmlUtil.recordElementInDocumentXml(xmlDocError,zap,fld,getChildText(parEl,fld),true,"") ;
                    	}
                    }
                }
        		if (ind>0) {
        			listFrom.add(list) ;
        		}
                if (isErrorFile) {
                	IKdlDiaryService serviceKdl = Injection.find(aRequest).getService(IKdlDiaryService.class) ;
                	String workDir=serviceKdl.getDir("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
                	filenameError = filename+"_error.xml" ;
                	XmlUtil.saveXmlDocument(xmlDocError,new File(workDir+"/"+filenameError)) ;
                } else {
                	//filenameError = null ;
                }
            }catch(Exception e) {
            	e.printStackTrace() ;
            	System.out.println(e.getMessage());
            } 
            finally {
                if (in!=null) in.close();
            }
        	} else if (contentType!=null&& contentType.toUpperCase().contains("DBF")) {
        		//final AttachmentByLpuForm form =(AttachmentByLpuForm)aForm ;
            	final long fileId = 0;
            	 //IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        	     
            	final long monitorId = monitorService.createMonitor();
               // System.out.println("00000000000 = 01");
            	if (form!=null ) {
            	//	System.out.println("00000000000 = 02");	
            		FormFile formFile = form.getFile();
            	
            		if (formFile!=null&&!formFile.equals("")) {
            	//		System.out.println("00000000000 = 03");
            	//		System.out.println("FormFile="+formFile.getFileSize());
            			final IImportFormatService serviceI = Injection.find(aRequest).getService(IImportFormatService.class);
            			 final String originalFileName = form.getFile().getFileName(); 
            		     final String tempFileName = FileUploadUtil.writeFile(formFile) ;
            	//	     System.out.println("HELLOOOO "+tempFileName);
            		    
            		     final ImportFileForm timeForm = new ImportFileForm();
            		        timeForm.setActualDateFrom(DateFormat.formatToDate(new java.util.Date()));
            		        timeForm.setActualDateTo(DateFormat.formatToDate(new java.util.Date()));
            		        timeForm.setImportFormat(form.getImportFormat());
            		        timeForm.setComment("AUTOIMPORT, Дата импорта = "+DateFormat.formatToDate(new java.util.Date()));
            		        
            		    //   new Thread() {
            		    //       public synchronized void run() {
            		                try {
            		                	serviceI.importFile(monitorId,fileId , tempFileName, originalFileName, timeForm);
            		                   } catch (Exception e) {
            		                	   e.printStackTrace();
            		                    throw new IllegalStateException(e) ;
            		                }
            		     //       }

            		    //    }.start();
            		        aRequest.setAttribute("impResult", "Файл успешно проимпортирован!");
            		} 
            	}
        	}
    	} 
        final String fileErrorF= filenameError!=null?filenameError:"" ;
        
        final long monitorId = monitorService.createMonitor() ;

    	final String typeId = type ;
    	final String contentTypeF = contentType ;
    	
    	if (!list.isEmpty()&&(typeImport.equals("1")||typeImport.equals("2"))) {
        new Thread() {
            public void run() {
            //	System.out.println("typeId-------------"+typeId) ;
            	if (contentTypeF!=null&& contentTypeF.toUpperCase().contains("DBF")) {
            		service.importDataFondForDBF(monitorId) ;
            	} else {
            		service.startMonitor(monitorId) ;
            		for (List<WebQueryResult> list:listFrom) {
            			System.out.println("Обработано "+list.get(0).get1());
            			service.importDataFond(monitorId,typeId, list) ;
            			service.addMonitor(monitorId, 10) ;
            		}
            		service.finishMonitor(monitorId) ;
            	}

            	
            }
        }.start() ;
        
        	return new MonitorActionForward(monitorId, new ActionForward(aMapping.findForward("success").getPath()+"?typeMode=2&infoImport=Импортировано&typeImport="+typeImport+"&errorFile="+fileErrorF)) ;
    	} else {
    		return new ActionForward(aMapping.findForward("success").getPath()+"?typeMode=2&typeImport="+typeImport+"&errorFile="+fileErrorF+"&infoImport=неверные параметры") ;
    	}
    }

    private String getText(Element aEl, String aParameter) {
    	return aEl!=null?
    			(aEl.getChild(aParameter)!=null && !aEl.getChild(aParameter).getText().trim().equals("")?(aEl.getChild(aParameter).getText().trim()):null)
    			:
    				null ;
    }
    private String getChildText(Element aEl, String aParameter) {
    	StringBuilder ret = new StringBuilder() ;
    	if (aEl!=null &&( aEl.getChild(aParameter)!=null) ) { 
    		for (Element e : (List<Element>) aEl.getChild(aParameter).getChildren() ) {
    			ret.append(", ").append(e.getName()).append(" ").append(e.getText()) ;
    		}
    	}
    	return ret.substring(2) ;
    }
    private String getDate(Element aEl,String aParameter) {
    		String value=getText(aEl,aParameter) ;
    		if (value==null) return null ;
    		if (value instanceof String) {
        		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd") ;
    			long l = 0 ;
    			
    			try {
    				l=f.parse(value).getTime() ;
    			} catch(Exception e) {
    				e.printStackTrace() ;
            		//String aFormat1 =  ;
        			f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss") ;
    				try {
    					l=f.parse(value).getTime() ;
    				
	    			} catch(Exception e1) {
	    				e1.printStackTrace() ;
	    				return null ;
	    			}
    			}
    			return f.format(l) ;
    		}
    		return null ;
    	
    }
    private Object getTime(Element aEl,String aParameter) {
    	String value=getText(aEl,aParameter) ;
    	try {
    		return value==null || value.equals("")?null:value ;
    	} catch (Exception e) {
    		e.printStackTrace() ;
    		return null ;
    	}
    }
}