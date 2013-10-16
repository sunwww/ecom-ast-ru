package ru.medos.web.persdata;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.spi.IIORegistry;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.medos.ejb.persdata.service.IDefaultService;
import ru.nuzmsh.web.struts.BaseAction;

import com.sun.media.imageioimpl.plugins.tiff.TIFFImageReaderSpi;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageWriterSpi;

public class DocumentFileImportAction extends BaseAction{

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws Exception {
		// TODO Auto-generated method stub
		BufferedImage image = null ;
    	try {
    		String path = aRequest.getParameter("url_image_tmp") ;
    		String path1 = aRequest.getParameter("url_image_tmp_comp") ;
    		
    		IDefaultService service = Injection.find(aRequest).getService(IDefaultService.class);
    		
        	IIORegistry registry = IIORegistry.getDefaultInstance() ;
        	registry.registerServiceProvider(new TIFFImageWriterSpi()) ;
        	registry.registerServiceProvider(new TIFFImageReaderSpi()) ;
    		boolean isCompress = false ;
    		boolean isRecord = false ;
    		boolean isDeleteTmp = false ;
        	String url_image="" ;
        	Date dat = new Date() ;
        	String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
        	String dirmain = service.getDir("tomcat.image.dir.persdata","/opt/tomcat/webapps/persdoc/") ;
        	String diradd = "" ;
    		url_image = dat.getTime()+"-"+username ;
    		Long objectId=null;
    		Long objectType = null ;
    		File tmp = null ;
    		float mcomp = 1f ;
    		//System.out.println(1) ;
    		
    		//System.out.println("path="+path) ;
    		if (path!=null && !path.equals("")) {
	    		//System.out.println(2) ;
	    		//mcomp = 0.25f ;
    			System.out.println(1) ;
	    		tmp = new File(dirmain+path) ;
	    		image = ImageIO.read(tmp) ;
	    		isDeleteTmp = true ;
	    		isRecord = true ;
	    		objectId= ConvertSql.parseLong(aRequest.getParameter("objectId")) ;
        		Calendar cal = Calendar.getInstance() ;
        		cal.setTime(dat) ;
        		int year = cal.get(Calendar.YEAR) ;
        		int month = cal.get(Calendar.MONTH)+1 ;
        		int day = cal.get(Calendar.DAY_OF_MONTH);
        		String typeString = "unknown" ;
        		System.out.println(2) ;
        		diradd = "orig/"+typeString+"/"+year+"/"+month+"/"+day ;
        		isCompress = false ;
        		System.out.println(3) ;
	    	} else if (aForm!=null) {
	    		ExportForm form = aForm!=null?(ExportForm)aForm:null ;
	    		String contentType= form.getFile().getContentType() ;
            	image = ImageIO.read(form.getFile().getInputStream()) ;
            	objectId = form.getObjectId() ;
            	objectType = form.getType() ;
            	aRequest.setAttribute("objectId", objectId) ;
            	aRequest.setAttribute("objectType", objectType) ;
            	if (contentType.toLowerCase().equals("image/tiff")) {
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
	            	if (isRecord) {
	            		tmp.renameTo(new File(dirmain+diradd+".jpg"));
	            	} else {
	            		save(image,mcomp,dirmain+diradd+".jpg",null,service) ;
	            	}
	            	//image = ImageIO.read(new File(dirmain+diradd+".jpg"));
	            	String newName = dirmain+diradd+"-comp.jpg" ;
	            	if (isCompress) {
	            		float comp = service.getImageCompress()*mcomp ;
	            		System.out.println("compress...") ;
	            		//image = Scalr.resize(image, image.getWidth()/2,image.getHeight()/2) ;
		            	save(image,comp,newName,dirmain+diradd+".jpg",service) ;
	            	} else {
	            		System.out.println(newName) ;
	            		System.out.println(path1) ;
	            		new File(dirmain+path1).renameTo(new File(newName));
	            	}
	            	if(isRecord) {
	            		service.insertExternalDocumentByObject("ComingDocument", objectId
	            				, diradd+"-comp.jpg", diradd+".jpg", "", username) ;
	            		aRequest.setAttribute("result_image", "Обработано и записано") ;
		            	aRequest.setAttribute("url_image", diradd+"-comp.jpg") ;
	            	} else {
		            	aRequest.setAttribute("url_image", diradd+".jpg") ;
		            	aRequest.setAttribute("url_image_comp", diradd+"-comp.jpg") ;
	            	}
	            }catch(Exception e) {
	            	System.out.println(e.fillInStackTrace());
	            	aRequest.setAttribute("result_image", "Ошибка при сохранении") ;
	            } 
	            
	    	
	    	
	    	
	    	
    	} catch(Exception e) {
    		System.out.println(e);
    		image = null ;
    		aRequest.setAttribute("result_image", "Ошибка при обработке") ;
    	}
    	
    	return aMapping.findForward("success") ;
    }
	public void save(BufferedImage aImage, Float aCompress, String aFileName,String aFileMainName,IDefaultService aService) throws IOException  {
		
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
	
	
	private void printlist(String[] aList) {
		for(String l:aList) {
			System.out.println("    "+l) ;
		}
	}
	

}
