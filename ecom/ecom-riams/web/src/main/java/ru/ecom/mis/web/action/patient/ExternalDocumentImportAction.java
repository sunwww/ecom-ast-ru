package ru.ecom.mis.web.action.patient;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.spi.IIORegistry;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import ru.ecom.diary.ejb.service.protocol.IKdlDiaryService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.jaas.web.action.policy.ExportPolicyForm;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.mis.web.action.kdl.KdlAction;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import com.sun.imageio.plugins.jpeg.JPEGImageWriter;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageReaderSpi;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageWriterSpi;

public class ExternalDocumentImportAction extends BaseAction{

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws Exception {
		// TODO Auto-generated method stub
		BufferedImage image = null ;
    	try {
    		/*
    		System.out.println("ReaderMIMETypes") ;
        	printlist(ImageIO.getReaderMIMETypes()) ;
        	System.out.println("ReaderFormatNames") ;
        	printlist(ImageIO.getReaderFormatNames()) ;
        	System.out.println("WriterMIMETypes") ;
        	printlist(ImageIO.getWriterMIMETypes()) ;
        	System.out.println("WriterFormatNames") ;
        	printlist(ImageIO.getWriterFormatNames()) ;
        	*/
    		String path = aRequest.getParameter("url_image_tmp") ;
    		String path1 = aRequest.getParameter("url_image_tmp_comp") ;
    		
    		IPatientService service = Injection.find(aRequest).getService(IPatientService.class);
    		IKdlDiaryService serviceKdl = Injection.find(aRequest).getService(IKdlDiaryService.class);
    		
        	IIORegistry registry = IIORegistry.getDefaultInstance() ;
        	registry.registerServiceProvider(new TIFFImageWriterSpi()) ;
        	registry.registerServiceProvider(new TIFFImageReaderSpi()) ;
    		boolean isCompress = false ;
    		boolean isRecord = false ;
    		boolean isDeleteTmp = false ;
        	String url_image="" ;
        	Date dat = new Date() ;
        	String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
        	String dirmain = service.getImageDir() ;
        	String diradd = "" ;
    		url_image = dat.getTime()+"-"+username ;
    		Long objectId=null;
    		Long objectType = null ;
    		File tmp = null ;
    		float mcomp = 1f ;
    		//System.out.println(1) ;
    		ExportPolicyForm form = aForm!=null?(ExportPolicyForm)aForm:null ;
    		FormFile ffile = form!=null?form.getFile():null;
    		String parentType = form.getParentType()!=null&&!form.getParentType().equals("")?form.getParentType():"Patient";
    		String contentType = ffile!=null?ffile.getContentType().toLowerCase(): "";
    		System.out.println("==== TYPE="+contentType);
    		System.out.println("==== Parent_TYPE="+parentType);
    		//System.out.println("path="+path) ;
    		if (parentType.equals("Template")) {
    		dirmain = service.getConfigValue("jboss.userdocument.dir","/home/user/opt/jboss-4.0.4.GAi-postgres/server/default/data");	
    		String filePrefix="user";
    		System.out.println("SAVE_DIR="+dirmain+"/"+filePrefix+"_"+ffile.getFileName());
    		saveNotImage(ffile.getInputStream(), dirmain+"/"+filePrefix+"_"+ffile.getFileName());
    		service.insertExternalDocumentByObject("Template", objectId, objectType
    				, dirmain+"/"+filePrefix+"_"+ffile.getFileName(), dirmain+"/"+filePrefix+"_"+ffile.getFileName(), "", username) ;
    		return aMapping.findForward("uploaded") ;
    		} else if ((path!=null && !path.equals(""))||contentType.equals("application/pdf")) {
    			
	    		//System.out.println(2) ;
	    		//mcomp = 0.25f ;
	    		if (path!=null&& !path.equals("")){
	    			tmp = new File(dirmain+path) ;
		    		image = ImageIO.read(tmp) ;
		    		isDeleteTmp = true ;
		    		isRecord = true ;
		    		objectId= ConvertSql.parseLong(aRequest.getParameter("objectId")) ;
		    		objectType = ConvertSql.parseLong(aRequest.getParameter("objectType")) ;
	    		} else {
	    			objectId = form.getObjectId() ;
	            	objectType = form.getType() ;
	    		}
	    		
        		Calendar cal = Calendar.getInstance() ;
        		cal.setTime(dat) ;
        		int year = cal.get(Calendar.YEAR) ;
        		int month = cal.get(Calendar.MONTH)+1 ;
        		int day = cal.get(Calendar.DAY_OF_MONTH);
        		IWebQueryService wqservice = Injection.find(aRequest).getService(IWebQueryService.class) ;
        		String typeString = "unknown" ;
        		String sql = "select id,code from VocExternalDocumentType where id='"+objectType+"'" ;
        		Collection<WebQueryResult> list1 = wqservice.executeNativeSql(sql.toString(),1) ;
        		if (list1.size()>0) {
        			WebQueryResult wqr = list1.iterator().next() ;
        			if (wqr.get2()!=null && !wqr.get2().equals("")) typeString=String.valueOf(wqr.get2()) ;
        		}
        		diradd = "orig/"+typeString+"/"+year+"/"+month+"/"+day ;
        		isCompress = false ;
        		System.out.println(3) ;
	    	} else if (aForm!=null && ffile!=null) {
	    	//	 form = aForm!=null?(ExportPolicyForm)aForm:null ;
	    	//	contentType= form.getFile().getContentType().toLowerCase() ;
            	image = ImageIO.read(ffile.getInputStream()) ;
            	objectId = form.getObjectId() ;
            	objectType = form.getType() ;
            	aRequest.setAttribute("objectId", objectId) ;
            	aRequest.setAttribute("objectType", objectType) ;
            	if (contentType.equals("image/tiff")) {
            		//
            		
            	}
            	
             	url_image = ""+ dat.getTime() ;
            	diradd = "tmp/"+username+"" ;
            	isCompress=true;
            	mcomp = 0.25f ;
	    	} else {
	    		return aMapping.findForward("success") ;
	    	}
    		//System.out.println(4) ;
	    		//System.out.println("file="+form.getFile().getContentType()+" "+form.getFile().getInputStream()) ;
	    		try {
	            	

	    			//image = Scalr.resize(image, image.getWidth()/2,image.getHeight()/2) ;
	            	File file = new File(dirmain+diradd) ;
            		if (!file.exists()) {
            			file.mkdirs() ;
            		}
            		 
            		//save(image, dirmain+"proba/");
            		diradd=diradd+"/"+url_image ;
            		//ImageIO.write(image, "jpg", new File(dirmain+diradd+".jpg")) ;
            		//ImageIO.write(image, "jpg", new File(dirmain+diradd+".jpg")) ;
	            	//ImageIO.write(image, "TIFF", new File(dirmain+diradd+".tif")) ;
            		if (parentType.equals("Template")) {
            			//saveNotImage(ffile.getInputStream(),dirmain+"/"+diradd+".pdf");
            		}
            		else if (contentType!=null&&contentType.equals("application/pdf")) { //Сохраняем ПДФ как есть
            			System.out.println("=== "+ffile.getFileSize()+" <>"+dirmain+"/"+diradd+".pdf");
            			
            				saveNotImage(ffile.getInputStream(),dirmain+"/"+diradd+".pdf");
            				System.out.println("=== DOC_TYPE= "+form.getType()+"<>"+form.getObjectId());
            				if (form.getType()!=null) service.insertExternalDocumentByObject(parentType, form.getObjectId() , form.getType(), diradd+".pdf", diradd+".pdf", "", username) ;
            				aRequest.setAttribute("url_image", diradd+".pdf") ;
    		            	aRequest.setAttribute("url_image_comp", "") ;
    		            	aRequest.setAttribute("result_image", "Обработано и записано") ;

    		            	//Возвращаемся на страницу с визитов только для случая лечения
    		            	if (parentType.equals("MedCase")) return aMapping.findForward("uploaded");
            		} else {            		
                		
            		if (isRecord) {
	            		tmp.renameTo(new File(dirmain+diradd+".jpg"));
	            	} else {
	            	//	 else {
	                		save(image,mcomp,dirmain+diradd+".jpg",null,serviceKdl) ;
	                //	}
	            		
	            	}
	            	//image = ImageIO.read(new File(dirmain+diradd+".jpg"));
	            	String newName = dirmain+diradd+"-comp.jpg" ;
	            	if (isCompress) {
	            		float comp = service.getImageCompress()*mcomp ;
	            		System.out.println("compress...") ;
	            		//image = Scalr.resize(image, image.getWidth()/2,image.getHeight()/2) ;
		            	save(image,comp,newName,dirmain+diradd+".jpg",serviceKdl) ;
	            	} else {
	            		System.out.println(newName) ;
	            		System.out.println(path1) ;
	            		new File(dirmain+path1).renameTo(new File(newName));
	            	}
	            	if(isRecord) {
	            		service.insertExternalDocumentByObject("Patient", objectId, objectType
	            				, diradd+"-comp.jpg", diradd+".jpg", "", username) ;
	            		aRequest.setAttribute("result_image", "Обработано и записано") ;
		            	aRequest.setAttribute("url_image", diradd+"-comp.jpg") ;
	            	} else {
		            	aRequest.setAttribute("url_image", diradd+".jpg") ;
		            	aRequest.setAttribute("url_image_comp", diradd+"-comp.jpg") ;
	            	}
	    		
	    		}
	    		
	            	
	            	
	            }catch(Exception e) {
	            //	System.out.println(e.fillInStackTrace());
	            	e.printStackTrace();
	            	aRequest.setAttribute("result_image", "Ошибка при сохранении") ;
	            } 
	            
	    	
	    	
	    	
	    	
    	} catch(Exception e) {
    		System.out.println(e);
    		e.printStackTrace();
    		image = null ;
    		aRequest.setAttribute("result_image", "Ошибка при обработке") ;
    	}
    	
    	return aMapping.findForward("success") ;
    }
	public void save(BufferedImage aImage, Float aCompress, String aFileName,String aFileMainName,IKdlDiaryService aService) throws IOException  {
		
		if (aFileMainName!=null) {
			String cmd=aService.getDir("image.resize", "convert_ORIGFILE_NEWFILE") ;
			cmd = cmd.replaceAll("_", " ");
			cmd = cmd.replace("ORIGFILE", aFileMainName) ;
			cmd = cmd.replace("NEWFILE", aFileName) ;
			System.out.println("cmd="+cmd) ;
			String run=aService.run(cmd) ;
			if (run!=null &&run.equals("0")) {
			} else {
				System.out.println("    ---->> ERROR-->"+run) ;
				save(aImage, aCompress, aFileName,null,aService) ;
				
			}
		} else {
			if (aCompress.equals(1f)) {
				ImageIO.write(aImage, "jpg", new File(aFileName)) ;
			} else {
				FileImageOutputStream output = null ;
				try {
				Iterator<ImageWriter> iter =ImageIO.getImageWritersByFormatName("jpg") ;
				if (iter.hasNext()) {
					
					
					ImageWriter writer = iter.next() ;
					ImageWriteParam iwp = writer.getDefaultWriteParam();
					iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT) ;
					iwp.setCompressionQuality(aCompress) ;
					printlist(iwp.getCompressionTypes()) ;
					//iwp.setCompressionType("BI_RGB") ;
					
					File outFile = new File(aFileName) ;
					output = new FileImageOutputStream(outFile) ;
					writer.setOutput(output) ;
					IIOImage iioimage = new IIOImage(aImage,null,null) ;
					writer.write(null,iioimage,iwp) ;
					
				}
				}catch (IOException e) {
					// TODO: handle exception
				}finally {
		            //image.();
		        	if (output!=null) output.close() ;
		        }
			}
		}
	}
	public void saveNotImage(InputStream aInputStream, String aFileName) throws IOException  {
		 int count = 0 ;
	        FileOutputStream out = new FileOutputStream(aFileName);
	        byte[] buf = new byte[8192] ;
	        while ( (count=aInputStream.read(buf)) > 0) {
	            out.write(buf, 0, count) ;
	        }
	        out.close() ;
	}
	
	private void printlist(String[] aList) {
		for(String l:aList) {
			System.out.println("    "+l) ;
		}
	}
	

}
